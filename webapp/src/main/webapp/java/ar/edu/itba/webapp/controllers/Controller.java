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

    @Autowired
    private TeamService team;

    @ModelAttribute
    public User loggedUser() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final User user = us.findByUsername(auth.getName());
        return user;
    }

    public Team getTeam() {
        return team.findById(loggedUser().getTeamId());
        //return null;
    }

    public long getTeamId () {
        return loggedUser().getTeamId();
        //return 0;
    }

}
