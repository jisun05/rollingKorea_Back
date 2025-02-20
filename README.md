# 🔵 English
# **RollingKorea Project**
- An API that provides historical sites in Korea for foreigners.
- Development Period: 24.12.15 ~ now<br>
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
- Get recommendations for historical sites based on user preferences

</details>

<details>
<summary>My Page</summary>

- View and edit user profile
- Check user history and visited places
- Manage account settings

</details>

<details>
<summary>Ranking</summary>

- Ranking batch execution every Monday morning
- View top-ranked historical sites based on user visits and interactions

</details>

<details>
<summary>Comments</summary>

- Create comments on historical sites
- Edit and delete own comments
- Like and reply to comments

</details>

<details>
<summary>Replies</summary>

- Create nested replies to comments
- Edit and delete own replies
- ADMIN can also delete any replies

</details>

# Sequence Diagram
Written to show detailed flow for each service. (In progress)<br>

<details>
<summary>Test Execution Status</summary>

- Unit tests for core features
- Integration tests for API stability
- Load testing for performance evaluation

</details>

<details>
<summary>Technical Challenges</summary>

- Optimizing database queries for large datasets
- Implementing secure authentication and authorization mechanisms
- Handling concurrent API requests efficiently

</details>

<details>
<summary>Troubleshooting</summary>

- Issues faced and how they were resolved
- Debugging key problems in service implementation
- Performance optimizations and enhancements

</details>

<details>
<summary>Lessons Learned & Error Handling</summary>

- Key takeaways from development
- Effective error handling strategies
- Best practices for API development

</details>

<details>
<summary>Study Notes</summary>

- Research on API security measures
- Learning advanced Spring Boot features
- Database indexing and performance improvements

</details>

<details>
<summary>Errors</summary>

- Common errors encountered during development
- Debugging techniques and resolutions

</details>

<details>
<summary>Reflections on the Project</summary>

- Personal insights and challenges faced
- Future improvements and next steps

</details>

---

### 🔵 Korean
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

# 기술적 도전

# 트러블 슈팅

# 프로젝트를 진행하면서 학습한 내용과 에러 조치

# 학습 내용정리

# ERROR

# 프로젝트를 하면서 느낀 점

