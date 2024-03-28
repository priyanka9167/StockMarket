package org.example.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Credentials;
import org.example.model.SecurityProperties;
import org.example.model.Users;
import org.example.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@Slf4j
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    SecurityService securityService;

    @Autowired
    SecurityProperties restSecProps;

    @Autowired
    CookieUtils cookieUtils;

    @Autowired
    SecurityProperties securityProps;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            verifyToken(request);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }
        filterChain.doFilter(request,response);
    }

    private void verifyToken(HttpServletRequest request) throws FirebaseAuthException {
        String session = null;
        FirebaseToken decodedToken = null;
        Credentials.CredentialType type = null;
        boolean strictServerSessionEnabled = securityProps.getFirebaseProps().isEnableStrictServerSession();
        Cookie sessionCookie = cookieUtils.getCookie("session");
        String token = securityService.getBearerToken(request);
        logger.info(token);
        try {
            if(sessionCookie != null){
                session = sessionCookie.getValue();
                decodedToken = FirebaseAuth.getInstance().verifySessionCookie(session,
                        securityProps.getFirebaseProps().isEnableCheckSessionRevoked());
                type = Credentials.CredentialType.SESSION;
            }
            else if(!strictServerSessionEnabled){
                if(token != null && !token.equalsIgnoreCase("undefined")){
                    decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
                    type = Credentials.CredentialType.ID_TOKEN;
                }
            }
        }
        catch (FirebaseAuthException e){
            e.printStackTrace();
            log.error("Firebase Exceptions:: ", e.getLocalizedMessage());
        }

        Users users = firebaseTokenToUserDto(decodedToken);

        if(users != null){
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(users, new Credentials(type, decodedToken, token,session), null);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private Users firebaseTokenToUserDto(FirebaseToken decodedToken){
        Users users = null;
        if (decodedToken != null){
            users = new Users();
            users.setId(decodedToken.getUid());
            users.setName(decodedToken.getName());
            users.setEmail(decodedToken.getEmail());
            System.out.println("===========Authenicated===========");
            System.out.println(decodedToken.getUid());
        }
        return users;
    }

}
