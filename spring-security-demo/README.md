# TIL for 스프링 시큐리티

스프링 시큐리티에 대해 공부한 내용을 정리한 프로젝트입니다.

## UserDetailsServiceAutoConfiguration

- 위치 : `org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration`
- `AuthenticationManager`, `AuthenticationProvider`, `UserDetailsService` 타입의 빈이 모두 없을 때 자동 설정
- 내부에서 `InMemoryUserDetailsManager` 객체를 빈으로 등록, 해당 객체는 `UserDetailsService` 타입의 빈
- 기본 생성 유저
  - `org.springframework.boot.autoconfigure.security.SecurityProperties.User`
  - 기본 `name` 은 `"user"`, 기본 `password` 는 `UUID.randomUUID().toString()`, 기본 `roles` 는 빈 리스트
  - `spring.security.user` 설정으로 `name`, `password`, `roles` 변경 가능
    - `password` 설정할 때 `PasswordEncoder` 를 사용 중이라면 해당 객체로 인코딩한 결과를 설정으로 사용해야 함

## PasswordEncoder

- 사용자가 가입할 때 입력한 비밀번호를 암호화
  - 저장하기 전에 반드시 암호화해서 저장해야 함
- 사용자가 로그인할 때 입력한 비밀번호와 암호화해서 저장한 비밀번호가 일치하는지 확인 
