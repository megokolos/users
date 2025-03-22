package ru.kolosov.CRUD.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.kolosov.CRUD.model.User;
import ru.kolosov.CRUD.service.crud.UsersService;

import java.io.IOException;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UsersService usersService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String login = authentication.getName();
        User user = usersService.findByLogin(login);
        Long userId = user.getId();
        if (user.getRoles().stream().anyMatch(role -> role.getRole().equals("ROLE_ADMIN"))) {
            System.out.println("Перенаправление админа на /admin/users");
            getRedirectStrategy().sendRedirect(request, response, "/admin/users");
        } else if (user.getRoles().stream().anyMatch(role -> role.getRole().equals("ROLE_USER"))) {
            System.out.println("Перенаправление админа на /users" + userId);
            getRedirectStrategy().sendRedirect(request, response, "/users/" + userId);
        }
    }
}