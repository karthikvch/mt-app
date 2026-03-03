//package com.javatech.filter;
//
//import com.javatech.service.TokenBlacklistService;
//import com.javatech.util.JwtUtil;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//@Component
//public class JwtAuthenticationFilter implements GlobalFilter, Ordered {
//
//    private final JwtUtil jwtUtil;
//    private final TokenBlacklistService blacklistService;
//
//    public JwtAuthenticationFilter(JwtUtil jwtUtil,
//                                   TokenBlacklistService blacklistService) {
//        this.jwtUtil = jwtUtil;
//        this.blacklistService = blacklistService;
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange,
//                             GatewayFilterChain chain) {
//
//        String path = exchange.getRequest().getURI().getPath();
//
//        // Allow auth APIs without JWT
//        if (path.startsWith("/auth")) {
//            return chain.filter(exchange);
//        }
//
//        String authHeader =
//                exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//
//        String jwt = authHeader.substring(7);
//
//        // 1️⃣ Blacklist check (use jti in real prod)
//        if (blacklistService.isBlacklisted(jwt)) {
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//
//        // 2️⃣ JWT validation
//        if (!jwtUtil.isTokenValid(jwt)) {
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//
//        // 3️⃣ Extract user
//        String username = jwtUtil.extractUserName(jwt);
//
//        // 4️⃣ Add header for rate limiting (PER USER)
//        ServerWebExchange mutatedExchange =
//                exchange.mutate()
//                        .request(builder ->
//                                builder.header("X-User-Id", username))
//                        .build();
//
//        return chain.filter(mutatedExchange);
//    }
//
//    @Override
//    public int getOrder() {
//        return -1; // Run before rate limiter
//    }
//}
//
