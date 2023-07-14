package com.example.securitytokenpractice.security.handler;

import com.example.securitytokenpractice.util.JWTUtil;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("Login Success Handler........");

        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // 응답 http 타입 지정

        log.info(authentication);
        log.info(authentication.getName()); // 사용자 이름

        Map<String, Object> claim = Map.of("mid", authentication.getName());

        // ACCESS TOKEN : 유효 기간 1일
        String accessToken = jwtUtil.generateToken(claim, 1);

        // REFRESH TOKEN : 유효 기간 30일
        String refreshToken = jwtUtil.generateToken(claim, 30);

        // generateToken을 두 번 호출했기 때문에 Key 가 두 번 호출된다.

        Gson gson = new Gson();

        Map<String, String> keyMap = Map.of("accessToken" , accessToken, "refreshToken", refreshToken);
        String jsonStr = gson.toJson(keyMap); // GSON을 통해 JSON 객체 형태로 반환

        response.getWriter().println(jsonStr);

    }
}
