package com.example.securitytokenpractice.repository;
import com.example.securitytokenpractice.domain.APIUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.IntStream;

@SpringBootTest
public class APIUserRepositoryTests {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private APIUserRepository aPIUserRepository;

    @Test
    public void testInserts(){
        IntStream.rangeClosed(1,100).forEach( i -> {
                APIUser apiUser = APIUser.builder()
                        .mid("apiUser" + i)
                        .mpw(passwordEncoder.encode("1111"))
                        .build();

                aPIUserRepository.save(apiUser);
            }
        );

    }
}
