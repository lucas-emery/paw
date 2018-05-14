package ar.edu.itba.webapp.controllers;

import ar.edu.itba.interfaces.service.ContractService;
import ar.edu.itba.interfaces.service.FormationService;
import ar.edu.itba.interfaces.service.PlayerService;
import ar.edu.itba.interfaces.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@org.springframework.stereotype.Controller
public class FormationController extends Controller{

    @Autowired
    private FormationService formationService;

    @RequestMapping("/formation")
    public ModelAndView formation() {
        ModelAndView mav = new ModelAndView("formation");
        mav.addObject("formation", formationService.findById(getTeam().getFormationId()));
        mav.addObject("players", getTeam().getPlayers());
        return mav;
    }
}
