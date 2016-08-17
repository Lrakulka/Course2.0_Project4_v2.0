package com.epam.service;

import com.epam.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class ListenerServiceImp implements
        ApplicationListener<ApplicationEvent> {

    private HttpSession session;
    private UserService userService;

    @Autowired
    public ListenerServiceImp(HttpSession session, UserService userService) {
        this.session = session;
        this.userService = userService;
    }

    /**
     * Listen user authorization and attach user data to session
     * @param event - app events
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof InteractiveAuthenticationSuccessEvent) {
            String email = ((UserDetails) ((InteractiveAuthenticationSuccessEvent) event)
                    .getAuthentication().getPrincipal()).getUsername();
            User user = userService.findByEmail(email);
            session.setAttribute("userName", new StringBuilder(user.getFirstName()).append(" ")
                    .append(userService.findByEmail(email).getLastName()));
            session.setAttribute("user", user);
        }
    }
}