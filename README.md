# Spring Security를 이용해 JWT Token 인증 방식 구현

## EndPoints

- POST /api/v1/users/join => username, password로 회원가입
- POST /api/v1/users/login => username, password로 로그인 => 성공시 JWT Token 발급
- POST /api/v1/users/review => JWT Token이 유효한지 체크 후 통과하면 리뷰 등록

