//package ru.kolosov.CRUD.security;
//
//import jakarta.security.auth.message.config.AuthConfigProvider;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import ru.kolosov.CRUD.service.UsersService;
//
//
//@Component
//public class AuthProviderImp implements AuthenticationProvider {
//
//    @Autowired
//    private UsersService usersService;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String login = authentication.getName();
//        UserDetails userDetails = usersService.loadUserByUsername(login);
//        String password = authentication.getCredentials().toString();
//        if (!password.equals(userDetails.getPassword())) {
//            throw new BadCredentialsException("incorrect password");
//        }
//        return new UsernamePasswordAuthenticationToken(userDetails, password);
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return true;
//    }
//}
