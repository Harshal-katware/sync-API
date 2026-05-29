////package com.example.Sync.config;
////
////import com.example.Sync.util.JwtUtil;
////import jakarta.servlet.*;
////import jakarta.servlet.http.*;
////import lombok.RequiredArgsConstructor;
////import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
////import org.springframework.security.core.context.SecurityContextHolder;
////import org.springframework.stereotype.Component;
////import org.springframework.web.filter.OncePerRequestFilter;
////import java.io.IOException;
////import java.util.Collections;
////
////@Component
////@RequiredArgsConstructor
////public class JwtAuthenticationFilter extends OncePerRequestFilter {
////
////    private final JwtUtil jwtUtil;
////
////    @Override
////    protected void doFilterInternal(HttpServletRequest request,
////                                    HttpServletResponse response,
////                                    FilterChain filterChain)
////            throws ServletException, IOException {
////
////        String header = request.getHeader("Authorization");
////
////        if (header != null && header.startsWith("Bearer ")) {
////            String token = header.substring(7);
////
////            if (!jwtUtil.validateToken(token)) {
////                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
////                return;
////            }
////
////
////            // ✅ Set authentication in SecurityContext
////            String email = jwtUtil.getEmailFromToken(token);
////            UsernamePasswordAuthenticationToken authentication =
////                    new UsernamePasswordAuthenticationToken(
////                            email,
////                            null,
////                            Collections.emptyList()
////
////            // ✅ YE MISSING THA — Security context mein set karo
////            String email = jwtUtil.getEmailFromToken(token);
////            UsernamePasswordAuthenticationToken authentication
////                    new UsernamePasswordAuthenticationToken(
////                            email, null, Collections.emptyList());
////            SecurityContextHolder.getContext().setAuthentication(authentication);
////        }
////
////        filterChain.doFilter(request, response);
////    }
////}
//
//
//package com.example.Sync.config;
//
//import com.example.Sync.util.JwtUtil;
//import jakarta.servlet.*;
//import jakarta.servlet.http.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtUtil jwtUtil;
//    private final UserRepository userRepository;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String header = request.getHeader("Authorization");
//
//        if (header != null && header.startsWith("Bearer ")) {
//            String token = header.substring(7);
//
//            if (!jwtUtil.validateToken(token)) {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                return;
//            }
//
//            String email = jwtUtil.getEmailFromToken(token);
//
//            // ✅ Check subscription
//            userRepository.findByEmail(email).ifPresent(user -> {
//                if (user.getSubscriptionEnd() != null &&
//                        user.getSubscriptionEnd().isBefore(java.time.LocalDate.now()) &&
//                        user.getSubscriptionStatus() != User.SubscriptionStatus.EXPIRED) {
//                    user.setSubscriptionStatus(User.SubscriptionStatus.EXPIRED);
//                    userRepository.save(user);
//                }
//            });
//
//            UsernamePasswordAuthenticationToken authentication =
//                    new UsernamePasswordAuthenticationToken(
//                            email, null, Collections.emptyList()
//                    );
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}

package com.example.Sync.config;

import com.example.Sync.entity.User;
import com.example.Sync.repository.UserRepository;
import com.example.Sync.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getServletPath();

        return path.startsWith("/api/auth/");

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            if (!jwtUtil.validateToken(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            String email = jwtUtil.getEmailFromToken(token);

            // ✅ Check and update subscription expiry
            userRepository.findByEmail(email).ifPresent(user -> {
                if (user.getSubscriptionEnd() != null &&
                        user.getSubscriptionEnd().isBefore(LocalDate.now()) &&
                        user.getSubscriptionStatus() != User.SubscriptionStatus.EXPIRED) {
                    user.setSubscriptionStatus(User.SubscriptionStatus.EXPIRED);
                    userRepository.save(user);
                }
            });

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            email, null, Collections.emptyList()
                    );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}