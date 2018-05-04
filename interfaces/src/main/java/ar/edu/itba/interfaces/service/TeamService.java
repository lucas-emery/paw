package ar.edu.itba.interfaces.service;

import ar.edu.itba.model.*;

import java.util.List;

public interface TeamService {

    Team create(String name, League league, Stadium stadium, Formation formation, List<Player> players, YouthAcademy youthAcademy, Integer fanTrust, Integer boardTrust, Integer money);

    Team findById(long id);

}
