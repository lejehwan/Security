# Security

day01- init/setting

day02- basic Login

day03,04,05- OAuth2.0 Login(Google, Facebook, Naver)- input App Key!!
___
OAuth2.0: 인증을 위한 표준 프로토콜,
          Authorization Server를 통해 회원정보를 인증하고, 발급받은 Access Token을 통해 API 서비스를 이용 가능

- 유저가 로그인 요청을 함
- Access Token을 받기 위한 Resource Server( 개인정보를 가진 서버 ex->구글 )로 연결됨
- 로그인이 되었다면, 이전 요청(client_id)와 서버의 redirect_id가 동일한지 확인함
- 일치한다면, scope(email,profile)에 대한 기능을 넘겨줄 승인여부 확인
- 동의한다면, authorization_code라는 임시 pw 발급
- 이후 리다이렉트 되어 개발자의 서버는 authorizaion_code를 가지고, Resource Server에게 Access Token을 요청함
- 유저의 정보가 필요할 때면 Access Token을 통해 접근함

하지만 이러한 요청들이 들어옴에 따라 유효한지 확인하는 검증이 필요하다. 이때 DB에서 조회하고 갱신시 업데이트 등을 해주어야 함.
그러므로 Access Toek의 유효성 검사 및 권한 확인을 위한 Auth Server에서 병목현상 및 서버의 부하가 발생할 수 있다.

- 구글 => https://console.cloud.google.com/apis
- 페이스북 => https://developers.facebook.com/?locale=ko_KR
- 네이버 => https://developers.naver.com/main/
