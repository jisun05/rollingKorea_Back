<details>
<summary style="font-size: 3em; font-weight: bold;">English🗺️</summary>

# **RollingKorea Project**
- An API that provides historical sites in Korea for foreigners.
- Development Period: 15 December 2024 – present<br>
- Team Members: 1 person<br>

- Swagger documentation is in progress ->


# Tech Stack
- **Language**: Java<br>
- **Framework**: Spring 6.2.1 / Spring Boot 3.4.1<br>
- **JDK**: 21<br>
- **Build Tool**: Gradle<br>
- **Database**: MySQL <br>
- **Server**: Local <br>
- **CI/CD**: In progress<br>


# Project Structure
--To be determined

# ERD
<img src="https://github.com/user-attachments/assets/6164d67d-cc2d-4763-bae5-748e6eb07f52" width="1000" height="400" alt="">

# Features
The features are described simply so that non-developers can easily understand what functionalities are available.<br>
For detailed information, please refer to the sequence diagrams below.

<details>
<summary>Users</summary>

- Sign-up and login through the website
- Social login (Google) authentication
  - The front-end sends the login social type for processing. Example: NO_SOCIAL / GOOGLE

</details>

<details>
<summary>Historical Sites</summary>

- View historical sites information
- Search for historical sites

</details>

<details>
<summary>My Page</summary>

- View and edit user profile

</details>

<details>
<summary>Ranking</summary>

- Ranking batch execution every Monday morning
- View top10-ranked historical sites based on user's likes.

</details>

<details>
<summary>Comments</summary>

- Create comments
- Edit and delete own comments
- Like and reply to comments
- ADMIN can also delete any comments

</details>

<details>
<summary>Replies</summary>

- Create nested replies to comments
- Edit and delete own replies
- ADMIN can also delete any replies

</details>

# Sequence Diagram
Login<br>
%%{init: {
  'themeVariables': {
    'fontSize': '16px',
    'actorFontSize': '16px',
    'noteFontSize': '14px'
  }
}}%%
```mermaid
sequenceDiagram
    participant U as 사용자
    participant B as 브라우저
    participant DNS as DNS 서버
    participant S as 웹 서버
    participant Bundler as Webpack 번들
    participant IndexJS as src/index.js
    participant AppJS as App.jsx
    participant Layout as components/Layout.jsx
    participant LoginModal as features/auth/LoginModal.jsx
    participant LoginOauth2 as features/auth/LoginOauth2.jsx
    participant GoogleSDK as @react-oauth/google
    participant Net as 네트워크
    participant Tomcat as Embedded Tomcat
    participant FCP as FilterChainProxy
    participant CORS as CorsFilter
    participant JWTF as JwtAuthenticationFilter
    participant Disp as DispatcherServlet
    participant HMap as HandlerMapping
    participant ReqConv as HttpMessageConverter (Request)
    participant HAdapt as HandlerAdapter
    participant Controller as UserController
    participant Service as UserServiceImpl
    participant Repo as UserRepository
    participant DB as 데이터베이스
    participant TokenProv as TokenProvider
    participant ResConv as HttpMessageConverter (Response)
    participant LS as localStorage
    participant AuthCtx as AuthProvider

    %% 1. 브라우저 초기 로드
    U->>B: URL 입력 & Enter
    B->>DNS: 도메인 → IP 조회
    DNS-->>B: IP 반환
    B->>S: HTTP GET /
    S-->>B: build/index.html 전송
    B->>Bundler: 번들 스크립트 로드
    Bundler->>IndexJS: src/index.js 실행
    IndexJS->>AppJS: ReactDOM.render(<App/>)
    AppJS->>Layout: Layout 마운트 (Nav 포함)

    %% 2. 로그인 모달 & 구글 SDK
    U->>Layout: LogIn 버튼 클릭
    Layout->>LoginModal: show Modal
    LoginModal->>LoginOauth2: render LoginOauth2
    LoginOauth2->>GoogleSDK: render <GoogleLogin>
    GoogleSDK->>U: OAuth2 팝업 오픈
    U->>GoogleSDK: 계정 선택 & 승인
    GoogleSDK-->>LoginOauth2: onSuccess(credentialResponse)

    %% 3. 프론트→백 요청
    LoginOauth2->>Net: POST /api/google/login { idToken }
    Net-->>Tomcat: HTTP 요청 도달
    Tomcat->>FCP: ApplicationFilterChain.doFilter

    %% 4. 시큐리티 필터
    FCP->>CORS: CorsFilter.doFilter
    CORS-->>FCP: chain.doFilter
    FCP->>JWTF: JwtAuthenticationFilter.doFilter
    JWTF-->>FCP: chain.doFilter
    FCP->>Disp: chain.doFilter → DispatcherServlet

    %% 5. DispatcherServlet → 컨트롤러
    Disp->>HMap: 매핑 검색 (/api/google/login, POST)
    HMap-->>Disp: UserController.googleLogin()
    Disp->>ReqConv: JSON → GoogleLoginRequest 역직렬화
    ReqConv-->>HAdapt: GoogleLoginRequest
    HAdapt->>Controller: googleLogin(request)

    %% 6. 백엔드 처리
    Controller->>Controller: verify(idToken)
    Controller->>Service: findOrCreateGoogleUser()
    Service->>Repo: findByLoginId(email)
    Repo->>DB: SELECT ...
    DB-->>Repo: 결과
    alt 신규 사용자
        Service->>Repo: save(new User)
        Repo->>DB: INSERT ...
        DB-->>Repo: 새 User
    end
    Repo-->>Service: User 반환
    Service-->>Controller: User
    Controller->>TokenProv: generateToken(user)
    TokenProv-->>Controller: JWT
    Controller-->>ResConv: CreateAccessTokenResponse

    %% 7. 응답 직렬화 & 전송
    ResConv-->>Disp: JSON 바이트
    Disp->>Tomcat: HTTP 200 + JSON
    Tomcat-->>Net: 응답 전송

    %% 8. 프론트 응답 처리
    Net-->>LoginOauth2: JSON 수신
    LoginOauth2->>LS: localStorage.setItem(token)
    LoginOauth2->>LoginModal: onLoginSuccess()

    %% 9. 상태 업데이트 & UI 반영
    LoginModal->>AuthCtx: login() → isLoggedIn=true
    LoginModal->>LoginModal: handleClose()
    AuthCtx-->>Layout: Context 변경
    Layout->>B: Nav 재렌더링 (My Page & Logout 표시)
```

<details>
<summary>Test Execution Status</summary>


</details>

<details>
<summary>Technical Challenges</summary>

</details>

<details>
<summary>Troubleshooting</summary>



</details>

<details>
<summary>Lessons Learned & Error Handling</summary>


</details>

<details>
<summary>Study Notes</summary>



</details>

<details>
<summary>Errors</summary>


</details>

<details>
<summary>Reflections on the Project</summary>


</details>
</details>
<details>
<summary style="font-size: 3em; font-weight: bold;">Korean🗺️</summary>

# **RollingKorea Project**
- 외국인들에게 한국의 역사적 명소를 제공하기 위한 API 입니다.
- 개발 기간 : 24.12.15 ~ now<br>
- 참여 인원 : 1명<br>

- Swagger 문서는 준비중입니다 ->


# 기술 스택
- **Language**: Java<br>
- **Framework**: Spring 6.2.1 / Spring Boot 3.4.1<br>
- **JDK**: 21<br>
- **Build Tool**: Gradle<br>
- **Database**: MySQL <br>
- **Server**: Local <br>
- **CI/CD**: 준비중<br>


# 프로젝트 구조
--예정

# ERD
<img src="https://github.com/user-attachments/assets/6164d67d-cc2d-4763-bae5-748e6eb07f52" width="1000" height="400" alt="">

# 기능설명
개발자가 아닌, 누구나 어떤 기능이 있는지 확인할 수 있도록 간단히 작성했습니다.<br>
기능에 대한 상세내용은 아래의 시퀀스 다이어그램을 확인부탁드리겠습니다.

<details>
<summary>회원</summary>

- 사이트를 통해 회원 가입 및 로그인
- 소셜 로그인(구글) 인증 후 로그인
  + 프론트에서 로그인 소셜 타입을 전달받아 사용 EX ) NO_SOCIAL / GOOGLE

</details>

<details>
<summary>유적지</summary>

-

</details>

<details>
<summary>마이페이지</summary>

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
  + ADMIN 도 삭제 가능

</details>

# 시퀀스 다이어그램
각 서비스마다 자세히 flow 를 나타내기 위해 작성했습니다.(준비중)<br>

# 테스트 진행 여부

< src = https://www.notion.so/19365f59b80881b19becf3e79a247028?v=19365f59b8088179a63a000ca0099981&pvs=4>

# 기술적 도전

# 트러블 슈팅

# 프로젝트를 진행하면서 학습한 내용과 에러 조치

# 학습 내용정리

# ERROR

# 프로젝트를 하면서 느낀 점
</details>
