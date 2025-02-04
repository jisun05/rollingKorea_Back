//package history.traveler.rollingkorea.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import history.traveler.rollingkorea.config.jwt.JwtFactory;
//import history.traveler.rollingkorea.global.config.secutiry.token.JwtProperties;
//import history.traveler.rollingkorea.global.config.secutiry.token.RefreshToken;
//import history.traveler.rollingkorea.user.domain.User;
//import history.traveler.rollingkorea.global.config.secutiry.token.CreateAccessTokenRequest;
//import history.traveler.rollingkorea.global.config.secutiry.token.RefreshTokenRepository;
//import history.traveler.rollingkorea.user.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.Map;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class TokenApiControllerTest {
//
//    @Autowired
//    protected MockMvc mockMvc;
//    @Autowired
//    protected ObjectMapper objectMapper;
//    @Autowired
//    private WebApplicationContext context;
//    @Autowired
//    JwtProperties jwtProperties;
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    RefreshTokenRepository refreshTokenRepository;
//
//    @BeforeEach
//    public void mockMvcSetUp(){
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
//                .build();
//        userRepository.deleteAll();
//    }
//
//    @DisplayName("createNewAccessToken: make a new Access Token")
//    @Test
//    public void createNewAccessToken() throws Exception {
//        //given
//        final String url = "/api/token";
//
//        User testUser = userRepository.save(User.builder()
//                .email("user@gmail.com")
//                .password("test")
//                .build());
//
//        String refreshToken = JwtFactory.builder()
//                .claims(Map.of("id", testUser.getUserId()))
//                .build()
//                .createToken(jwtProperties);
//
//        refreshTokenRepository.save(new RefreshToken(testUser.getUserId(), refreshToken));
//
//        CreateAccessTokenRequest request = new CreateAccessTokenRequest();
//        request.setRefreshToken(refreshToken);
//        final String requestBody = objectMapper.writeValueAsString(request);
//
//
//        ResultActions resultActions = mockMvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(requestBody));
//
//        resultActions
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.accessToken").isNotEmpty());
//    }
//}
