package kr.carz.savecar.config;

import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String error = "ID or password does not match.";

        if(exception instanceof BadCredentialsException) {
            error = "ID or password does not match.";
        } else if(exception instanceof InternalAuthenticationServiceException) {
            error = "ID or password does not match.";
        } else if(exception instanceof DisabledException) {
            error = "The account is disabled. Please contact the website administrator.";
        } else if(exception instanceof CredentialsExpiredException) {
            error = "Your password has expired. Please contact the website administrator.";
        }

        setDefaultFailureUrl("/admin/index?error=true&exception="+error);

        super.onAuthenticationFailure(request,response,exception);
    }
}
