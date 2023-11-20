package study.querydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(onConstructor = @__({@QueryProjection}))
public class MemberTeamDto {
    private Long memberId;
    private String username;
    private int age;
    private Long teamId;
    private String teamName;
}
