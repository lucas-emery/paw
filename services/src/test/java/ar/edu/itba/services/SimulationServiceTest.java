package ar.edu.itba.services;

import ar.edu.itba.interfaces.dao.MatchDao;
import ar.edu.itba.interfaces.dao.MatchStateDao;
import ar.edu.itba.interfaces.service.MatchService;
import ar.edu.itba.model.*;
import ar.edu.itba.model.utils.Point;
import org.junit.Test;
import org.mockito.Mock;

import java.util.*;

import static ar.edu.itba.model.utils.simulation.MyTeam.AWAY;
import static ar.edu.itba.model.utils.simulation.MyTeam.HOME;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DaoConfiguration.class, SimulationServiceTest.SimulationServiceConfig.class})
public class SimulationServiceTest {

    List<Player> players = new ArrayList<>();
    Formation f1,f2;
    Team t1,t2;

        @Bean
        public MatchStateDao matchStateDao(){
            return mock(MatchStateDao.class);
        }

        @Bean
        public MatchDao matchDao(){
            return mock(MatchDao.class);
        }

        @Bean
        public MatchService matchService(){
            return mock(MatchService.class);
        }
    }

    @Autowired
    private SimulationServiceImpl simulationService;

    private List<Player> players;
    private Formation f1,f2;
    private Team t1,t2;
    private List<Event> events;
    private Grid grid;
    private GridNode gridNode;
    private SimulationNode sNode1, sNode2;
    private MatchStatus matchStatus;

    private Player dummy(){
        return new Player(0,0,"pepe",20,10,80,80,70,70,70,70,100,10,new Date(),false);
    }

    public SimulationServiceTest(){
        Map<Player,Point> map1 = new HashMap<>(), map2 = new HashMap<>();
        List<Player> sub1 = new ArrayList<>(), sub2 = new ArrayList<>();

        map1.put(dummy(),new Point(0,4));
        map1.put(dummy(),new Point(1,1));
        map1.put(dummy(),new Point(1,3));
        map1.put(dummy(),new Point(1,5));
        map1.put(dummy(),new Point(1,7));
        map1.put(dummy(),new Point(3,4));
        map1.put(dummy(),new Point(4,1));
        map1.put(dummy(),new Point(4,7));
        map1.put(dummy(),new Point(5,4));
        map1.put(dummy(),new Point(7,3));
        map1.put(dummy(),new Point(7,5));


        map2.put(dummy(),new Point(0,4));
        map2.put(dummy(),new Point(1,1));
        map2.put(dummy(),new Point(1,3));
        map2.put(dummy(),new Point(1,5));
        map2.put(dummy(),new Point(1,7));
        map2.put(dummy(),new Point(3,4));
        map2.put(dummy(),new Point(4,1));
        map2.put(dummy(),new Point(4,7));
        map2.put(dummy(),new Point(5,4));
        map2.put(dummy(),new Point(7,3));
        map2.put(dummy(),new Point(7,5));

        for(int i=0;i<7;i++){
            sub1.add(dummy());
            sub2.add(dummy());
        }

        f1 = new Formation(0,dummy(),dummy(),dummy(),map1,sub1,50,50);
        f2 = new Formation(1,dummy(),dummy(),dummy(),map2,sub2,50,50);

        t1 = new Team(0,"River",mock(League.class), mock(Stadium.class),f1,new ArrayList<Player>(), new ArrayList<Player>(),100,100,100,new ArrayList<Receipt>(),new ArrayList<BankLoan>(),100);
        t2 = new Team(1,"Boca",mock(League.class), mock(Stadium.class),f2,new ArrayList<Player>(), new ArrayList<Player>(),100,100,100,new ArrayList<Receipt>(),new ArrayList<BankLoan>(),100);
    }


    @Test
    public void influenceTest(){
        SimulationServiceImpl ss = new SimulationServiceImpl();
        List<Match> list = new ArrayList<>();
        int cases = 100;

        for (int i = 0; i < cases; i++) {
            list.add(new Match(0,t1,t2,mock(League.class),new Date(),0,0,0,0,null,false,new ArrayList<Event>()));
        }

        ss.simulateFixture(1L,list);

        float homePoints = 0, awayPoints = 0, homeGoals = 0, awayGoals = 0;
        for(Match m : list){
            homeGoals += m.getHomeScore();
            awayGoals += m.getAwayScore();
            homePoints += m.getHomePoints();
            awayPoints += m.getAwayPoints();
        }

        System.out.println("Home pts avg= " + homePoints/cases);
        System.out.println("Away pts avg= " + awayPoints/cases);

    }


}