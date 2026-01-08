package com.github.exam.controller;

import com.github.exam.convention.Result;
import com.github.exam.model.vo.UserReqVO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vik
 * @date 2026-01-08
 * @description
 */
@RequestMapping("/user")
@RestController
public class UserLoginController {


    private final AuthenticationManager authManager;

    // Constructor Injection. Never use @Autowired on fields.
    public UserLoginController(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody UserReqVO req) {
        if (req == null || req.getAccount() == null || req.getPassword() == null) {
            return Result.fail("Missing credentials");
        }

        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getAccount(), req.getPassword())
            );
            return Result.success("Token: " + auth.getName()); // Mocking a token return

        } catch (BadCredentialsException e) {
            return Result.fail("Invalid username or password");
        }
    }

}
