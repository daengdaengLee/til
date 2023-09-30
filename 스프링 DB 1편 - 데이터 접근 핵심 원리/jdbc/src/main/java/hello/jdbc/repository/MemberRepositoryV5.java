package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

/**
 * JdbcTemplate 사용
 */
@Slf4j
public class MemberRepositoryV5 implements MemberRepository {
    private final JdbcTemplate template;

    public MemberRepositoryV5(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        var sql = """
                INSERT INTO member(member_id, money) VALUES (?, ?)
                """;
        this.template.update(sql, member.getMemberId(), member.getMoney());
        return member;
    }

    @Override
    public Member findById(String memberId) {
        var sql = """
                SELECT * FROM member WHERE member_id = ?
                """;
        return this.template.queryForObject(sql, this.memberRowMapper(), memberId);
    }

    @Override
    public void update(String memberId, int money) {
        var sql = """
                UPDATE member SET money = ? WHERE member_id = ?
                """;
        this.template.update(sql, money, memberId);
    }

    @Override
    public void delete(String memberId) {
        var sql = """
                DELETE FROM member WHERE member_id = ?
                """;
        this.template.update(sql, memberId);
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            var member = new Member();
            member.setMemberId(rs.getString("member_id"));
            member.setMoney(rs.getInt("money"));
            return member;
        };
    }
}
