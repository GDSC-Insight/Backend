package com.example.GDSC_insight.config.jwt;

import com.example.GDSC_insight.config.auth.domain.CorporatePrincipalDetails;
import com.example.GDSC_insight.config.auth.domain.IndividualPrincipalDetails;
import com.example.GDSC_insight.repository.CorporateRepository;
import com.example.GDSC_insight.repository.IndividualRepository;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

// 인가
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final CorporateRepository corporateRepository;
    private final IndividualRepository individualRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, CorporateRepository corporateRepository,
            IndividualRepository individualRepository, JwtTokenProvider jwtTokenProvider) {
        super(authenticationManager);
        this.corporateRepository = corporateRepository;
        this.individualRepository = individualRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    /**
     * 헤더에서 AccessToken 추출 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
     */
    public String extractAccessToken(HttpServletRequest request) {
        return request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");
    }

    /**
     * AccessToken에서 Email 추출 추출 전에 JWT.require()로 검증기 생성 verify로 AceessToken 검증 후, ㄴ-> 이게인증이기 때문에
     * AuthenticationManager도 필요X SecurityContext에 직접 접근 -> 세션을 만들때 자동으로 UserDetailsService에 있는 loadByUsername이 호출됨.
     * 유효하다면 getClaim()으로 이메일 추출 유효하지 않다면 빈 Optional 객체 반환
     */
    public Optional<String> extractEmail(String accessToken) {
        try {
            return Optional.ofNullable(jwtTokenProvider.getUsernameFromToken(accessToken)); // claim에서 username 가져오기
        } catch (Exception e) {
            log.error("액세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader(JwtProperties.HEADER_STRING);
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UserDetails user = null;
        String token = extractAccessToken(request);
        if (jwtTokenProvider.validateToken(token)) {
            String username = extractEmail(token).orElse(null);
            String role = jwtTokenProvider.getRoleFromToken(token); // 역할 추출
            log.error("tlqkf : " + role);
            log.error("tlqkf username: " + username);
            if (username != null) {

                if ("ROLE_CORPORATE".equals(role)) {
                    user = new CorporatePrincipalDetails(corporateRepository.findByLoginId(username).orElse(null));
                } else if ("ROLE_INDIVIDUAL".equals(role)) {
                    user = new IndividualPrincipalDetails(individualRepository.findByLoginId(username).orElse(null));
                }
                log.error("tlqkf!!!!!!!!!!!!!!! user: " + user.getUsername());
                if (user != null) {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(user, null,
                            user.getAuthorities());
                    log.error("tlqkf!들어와야해 ㅠㅠ user: " + user.getUsername());
                    CorporatePrincipalDetails principal = (CorporatePrincipalDetails) authentication.getPrincipal();
                    log.error("tlqkf!들어와야해 ㅠㅠ authentication " + principal.getUser());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        chain.doFilter(request, response);
    }

}
