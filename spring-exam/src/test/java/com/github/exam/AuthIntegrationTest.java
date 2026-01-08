package com.github.exam;

import com.github.exam.convention.Result;
import com.github.exam.model.vo.UserReqVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class AuthIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    // Requirement 1: GET /hello requires authentication
    @Test
    void testHelloEndpointSecurity() {
        // 1. Unauthenticated -> 401
        ResponseEntity<String> unauthResponse = restTemplate.getForEntity("/hello", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, unauthResponse.getStatusCode());

        // 2. Authenticated -> 200 "Hello World"
        ResponseEntity<String> authResponse = restTemplate
            .withBasicAuth("test", "123456")
            .getForEntity("/hello", String.class);
        
        assertEquals(HttpStatus.OK, authResponse.getStatusCode());
        assertEquals("Hello World", authResponse.getBody());
    }

    // Requirement 2: POST /user/login validation
    @Test
    void testLoginEndpoint() {
        // Prepare Request
        UserReqVO request = new UserReqVO();
        request.setAccount("test");
        request.setPassword("123456");

        // 1. Valid Login
        ResponseEntity<Result> validResp = restTemplate.postForEntity("/user/login", request, Result.class);
        assertEquals(HttpStatus.OK, validResp.getStatusCode());
        assertEquals(Result.SUCCESS_CODE, validResp.getBody().getCode());

        // 2. Invalid Login
        request.setPassword("wrongpass");
        ResponseEntity<Result> invalidResp = restTemplate.postForEntity("/user/login", request, Result.class);
        assertEquals(HttpStatus.OK, invalidResp.getStatusCode()); // HTTP 200, but Logic Fail
        assertEquals(Result.FAIL_CODE, invalidResp.getBody().getCode());
    }
}