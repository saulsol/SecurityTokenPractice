package com.example.securitytokenpractice.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JWTUtilTest {


    @Autowired
    JWTUtil jwtUtil;

    @Test
    @DisplayName("토큰 생성 테스트 -> 성공")
    public void testGenerate(){
        Map<String, Object> claimMap = Map.of("mid", "ABCDE");
        String jwtStr = jwtUtil.generateToken(claimMap, 1);
        System.out.println(jwtStr);
    }

    @Test
    @DisplayName("유효시간이 지난 토큰 테스트 -> 실패")
    public void testValidate(){
        // 유효 시간이 지난 JWT 토큰
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODkyMzYxOTQsIm1pZCI6IkFCQ0RFIiwiaWF0IjoxNjg5MjM2MTM0fQ.n5LNr5VA2HyfaKkOgVwvcYv0cLkCvmh-70GI1h6lT4k";

        Map<String, Object> claim = jwtUtil.validateToken(token);

        System.out.println(claim);
    }



}