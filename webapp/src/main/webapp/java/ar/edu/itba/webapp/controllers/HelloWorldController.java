package ar.edu.itba.webapp.controllers;

import ar.edu.itba.interfaces.service.PlayerService;
import ar.edu.itba.interfaces.service.TeamService;
import ar.edu.itba.interfaces.service.UserService;
import ar.edu.itba.model.Team;
import ar.edu.itba.model.User;
import ar.edu.itba.webapp.form.ChooseTeamForm;
import ar.edu.itba.webapp.form.UserForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@org.springframework.stereotype.Controller
public class HelloWorldController extends Controller{

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldController.class);

    @Autowired
    private UserService us;

    @Autowired
    private PlayerService player;

    @Autowired
    private TeamService team;

    @RequestMapping("/")
    public ModelAndView index(@ModelAttribute("registerForm") final UserForm form) {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/create", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return index(form);
        }
        final User u = us.create(form.getUsername(), form.getPassword(), "", new Date());
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(value = "/json", produces = "application/json")
    @ResponseBody
    public Object json() {
        return loggedUser();
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @RequestMapping("/user")
    public ModelAndView index(@RequestParam(value = "userId", required = true) final int id) {
        final ModelAndView mav = new ModelAndView("user");
        mav.addObject("user", us.findById(id));
        return mav;
    }

    @RequestMapping(value="/hello")
    public ModelAndView printUser() {
        final ModelAndView mav = new ModelAndView("hello");
        return mav;
    }

}