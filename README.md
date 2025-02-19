# **RollingKorea Project**
- 외국인들에게 한국의 역사적 명소를 제공하기 위한 API 입니다.
- 개발 기간 : 24.12.15 ~ now<br>
- 참여 인원 : 1명<br>

- Swagger 문서는 준비중입니다 ->


# 기술 스택
- Language : Java<br>
- Framework : Spring 6.2.1 / Springboot 3.4.1<br>
- JDK : 21<br>
- BuildTool : Gradle<br>
- DB : MySQL <br>
- Server : Local <br>
- CI / CD : 준비중<br>



# 프로젝트 구조
--예정

# ERD
<img src="https://github.com/user-attachments/assets/6164d67d-cc2d-4763-bae5-748e6eb07f52"  width="1000" height="400" alt="">

# 기능설명
개발자가 아닌, 누구나 어떤 기능이 있는지 확인할 수 있도록 간단히 작성했습니다.<br>
기능에 대한 상세내용은 아래의 시퀀스 다이어그램을 확인부탁드리겠습니다.

<details>
<summary>회원</summary>

- 사이트를 통해 회원 가입 및 로그인
- 소셜 로그인(구글) 인증 후 로그인

  +프론트에서 로그인 소셜 타입을 전달받아 사용 EX ) NO_SOCIAL / GOOGLE

</details>


<details>
<summary>유적지</summary>

- 

</details>


<details>
<summary>마이페이지</summary>

- 상품 등록

  +상품 이미지는 1장 이상 필수로 입력

  +상품 이미지는 AWS S3 에 저장

  +동일한 이름으로는 등록 불가

- 상품 전체 조회
- 상품 단품 조회
- 상품 키워드 검색
- 상품 수정
- 상품 삭제
- 상품 가격 변경 확인

  +장바구니에서 주문 화면으로 넘어가기 전 가격 일치 여부 확인 (상품 가격 수정 가능성)
</details>

<details>
<summary>랭킹</summary>

- 월요일 오전마다 랭킹 배치 수행

</details>


<details>
<summary>코멘트</summary>

- 코멘트 생성

</details>


<details>
<summary>댓글</summary>

- 대댓글 생성
  +ADMIN 도 삭제 가능
</details>


# 시퀀스 다이어그램
각 서비스마다 자세히 flow 를 나타내기 위해 작성했습니다.(준비중)<br>

# 테스트 진행 여부

# 기술적 도전

# 트러블 슈팅

# 프로젝트를 진행하면서 학습한 내용과 에러 조치

### 학습 내용정리

### ERROR

# 프로젝트를 하면서 느낀 점
