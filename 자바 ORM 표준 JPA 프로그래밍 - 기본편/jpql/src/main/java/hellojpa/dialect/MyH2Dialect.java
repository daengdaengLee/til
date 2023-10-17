package hellojpa.dialect;

import org.hibernate.dialect.H2Dialect;

// 설정에 등록
public class MyH2Dialect extends H2Dialect {
    public MyH2Dialect() {
        super();
        // registerFunction
    }
}
