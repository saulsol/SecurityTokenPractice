package com.example.securitytokenpractice.util;


import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
public class JWTUtil {

    @Value("${jwt.secret}")
    private String key;

    public String generateToken(Map<String, Object> valueMap, int days){

        log.info("generateKey... " + key);

        // 헤더 부분
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ","JWT");
        headers.put("alg","HS256");

        // payload 부분 설정
        Map<String, Object> payloads = new HashMap<>();
        payloads.putAll(valueMap);


        // 테스트 시에는 짧은 유효시간
        int time = (60 * 24) * days; //테스트는 분단위로 나중에 60*24 (일)단위로 변경

        String jwtStr = Jwts.builder()
                .setHeader(headers)
                .setClaims(payloads)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(time).toInstant()))
                .signWith(SignatureAlgorithm.HS256, key.getBytes())
                .compact();

        // iat, exp 시간을 알고 싶다면 jwt.io 페이지에 가서 키로 만든 JWT 토큰을 파싱을 해보자

        return jwtStr;
    }

    public Map<String, Object> validateToken(String token) throws JwtException {
        // claim 주장하다 : 클라이언트가 나 인증된 유저라고 주장한다.
        Map<String,Object> claim = Jwts.parser() // parser -> 파싱하는 친구 : 기존에 받은 JWT 토큰을 파싱한다.
                .setSigningKey(key.getBytes()) //
                .parseClaimsJws(token) // 파싱 및 검증, 실패시 에러
                .getBody();

        return claim;
    }

}
