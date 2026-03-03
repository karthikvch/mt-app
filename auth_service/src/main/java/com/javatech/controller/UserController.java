package com.javatech.controller;

import com.javatech.model.User;
import com.javatech.repository.UserRepository;
import com.javatech.service.TokenBlacklistService;
import com.javatech.service.UserService;
import com.javatech.util.JwtUtil;
import io.jsonwebtoken.Claims;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final JwtUtil jwtUtil;
    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    public UserController(UserRepository userRepository, UserService userService, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    @GetMapping
    @DeleteMapping
    @PatchMapping
    public User register(@RequestBody User user) {
        //return userRepository.save(user);
        return userService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return userService.verify(user);
      /*var u = userRepository.findByUserName(user.getUserName());
        if(!Objects.isNull(u))
            return "success";
        return "failure";*/
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {

        String token = request.getHeader("Authorization").substring(7);

        Claims claims =jwtUtil.extractClaim(token);
        String jti = claims.getId();
        long exp = claims.getExpiration().toInstant().getEpochSecond();

        // 🔐 Revoke
        //tokenBlacklistService.revokeToken(jti, exp);

        // 🧾 Audit / DB ops
        //userService.updateLastLogout(authentication.getName());

        return ResponseEntity.ok(Map.of("message", "Logged out"));
    }


}