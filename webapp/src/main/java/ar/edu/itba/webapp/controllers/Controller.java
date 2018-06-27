package ar.edu.itba.webapp.controllers;

import ar.edu.itba.interfaces.service.TeamService;
import ar.edu.itba.interfaces.service.UserService;
import ar.edu.itba.model.Team;
import ar.edu.itba.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

public class Controller {

    @Autowired
    private UserService us;


    @ModelAttribute
    public User loggedUser() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final User user = us.findByUsername(auth == null ? "" : auth.getName());
        return user;
    }
}
