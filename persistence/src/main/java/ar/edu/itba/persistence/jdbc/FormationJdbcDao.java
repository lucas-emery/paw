package ar.edu.itba.persistence.jdbc;

import ar.edu.itba.interfaces.dao.FormationDao;
import ar.edu.itba.model.Formation;
import ar.edu.itba.model.Player;
import ar.edu.itba.model.utils.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class FormationJdbcDao{

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsertFormation, jdbcInsertPlaysAs;

    private static final RowMapper<Formation> ROW_MAPPER = new RowMapper<Formation>() {
        @Override
        public Formation mapRow(ResultSet rs, int i) throws SQLException {

        long id = rs.getLong("formationid");
        int pressure = rs.getInt("pressure");
        int attitude = rs.getInt("attitude");
        int captainId = rs.getInt("captain");
        int penaltyTakerId = rs.getInt("penaltyTaker");
        int freeKickTakerId = rs.getInt("freekickTaker");

        return new Formation(id, captainId, freeKickTakerId, penaltyTakerId, null, null, pressure, attitude);
        }
    };

    private static final ResultSetExtractor<List<Formation>> RESULT_SET_EXTRACTOR = new ResultSetExtractor<List<Formation>>() {
        @Override
        public List<Formation> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<Formation> formations = new LinkedList<>();
            Formation currFormation = null;
            Map<Long, Point> currStarters = null;
            List<Long> currSubstitutes = null;
            while (rs.next()) {
                long id = rs.getLong("formationid");
                if(currFormation == null) {
                    currFormation = ROW_MAPPER.mapRow(rs, 0);
                    currStarters = new HashMap<>();
                    currSubstitutes = new LinkedList<>();
                } else if (currFormation.getId() != id) {
                    currFormation.setStartersIds(currStarters);
                    currFormation.setSubstitutesIds(currSubstitutes);
                    formations.add(currFormation);

                    currFormation = ROW_MAPPER.mapRow(rs, 0);
                    currStarters = new HashMap<>();
                    currSubstitutes = new LinkedList<>();
                }

                String type = rs.getString("type");
                if(type != null) {
                    Formation.PlaysAs playsAs = Formation.PlaysAs.valueOf(type);
                    long playerId = rs.getLong("player");
                    switch (playsAs) {
                        case STARTER:
                            int x = rs.getInt("x");
                            int y = rs.getInt("y");
                            currStarters.put(playerId, new Point(x, y));
                            break;
                        case SUBSTITUTE:
                            currSubstitutes.add(playerId);
                            break;
                    }
                }
            }
            if(currFormation != null) {
                currFormation.setStartersIds(currStarters);
                currFormation.setSubstitutesIds(currSubstitutes);
                formations.add(currFormation);
            }
            return formations;
        }
    };



    @Autowired
    public FormationJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsertFormation = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("formation")
                .usingGeneratedKeyColumns("formationid");
        jdbcInsertPlaysAs = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("playsAs")
                .usingGeneratedKeyColumns("playsasid");
    }

    public Formation create(Map<Player, Point> starters, List<Player> substitutes, int pressure, int attitude, Player captain, Player freeKickTaker, Player penaltyTaker) {
        Map<String, Object> args = new HashMap<>();

        args.put("pressure", pressure);
        args.put("attitude", attitude);
        args.put("captain", captain.getId());
        args.put("penaltyTaker", penaltyTaker.getId());
        args.put("freeKickTaker", freeKickTaker.getId());

        final Number formationId = jdbcInsertFormation.executeAndReturnKey(args);

        for (Map.Entry<Player, Point> entry : starters.entrySet()) {
            args = new HashMap<>();

            args.put("formation", formationId);
            args.put("player", entry.getKey().getId());
            args.put("x", entry.getValue().getX());
            args.put("y", entry.getValue().getY());
            args.put("type", Formation.PlaysAs.STARTER.toString());

            jdbcInsertPlaysAs.execute(args);
        }

        for (Player player : substitutes) {
            args = new HashMap<>();

            args.put("formation", formationId);
            args.put("player", player.getId());
            args.put("type", Formation.PlaysAs.SUBSTITUTE.toString());

            jdbcInsertPlaysAs.execute(args);
        }

        return new Formation(formationId.longValue(), captain, freeKickTaker, penaltyTaker, starters, substitutes, pressure, attitude);
    }

    public Formation create(Map<Long, Point> starters, List<Long> substitutes, int pressure, int attitude, long captain, long freeKickTaker, long penaltyTaker) {
        Map<String, Object> args = new HashMap<>();

        args.put("pressure", pressure);
        args.put("attitude", attitude);
        args.put("captain", captain);
        args.put("penaltyTaker", penaltyTaker);
        args.put("freeKickTaker", freeKickTaker);

        final Number formationId = jdbcInsertFormation.executeAndReturnKey(args);

        for (Map.Entry<Long, Point> entry : starters.entrySet()) {
            args = new HashMap<>();

            args.put("formation", formationId);
            args.put("player", entry.getKey());
            args.put("x", entry.getValue().getX());
            args.put("y", entry.getValue().getY());
            args.put("type", Formation.PlaysAs.STARTER.toString());

            jdbcInsertPlaysAs.execute(args);
        }

        for (Long player : substitutes) {
            args = new HashMap<>();

            args.put("formation", formationId);
            args.put("player", player);
            args.put("type", Formation.PlaysAs.SUBSTITUTE.toString());

            jdbcInsertPlaysAs.execute(args);
        }

        return new Formation(formationId.longValue(), captain, freeKickTaker, penaltyTaker, starters, substitutes, pressure, attitude);
    }

    public Formation save(Formation formation) {
        long formationId = formation.getId();

        jdbcTemplate.update("UPDATE formation SET pressure = ?, attitude = ?, penaltytaker = ?, freekicktaker = ?, " +
                "captain = ? WHERE formationid = ?", formation.getPressure(), formation.getAttitude(),
                formation.getPenaltyTakerId(), formation.getFreeKickTakerId(), formation.getCaptainId(), formationId);


        jdbcTemplate.update("DELETE FROM playsas WHERE formation = ?", formationId);

        Map<String, Object> args;

        for (Map.Entry<Long, Point> entry : formation.getStartersIds().entrySet()) {
            args = new HashMap<>();

            args.put("formation", formationId);
            args.put("player", entry.getKey());
            args.put("x", entry.getValue().getX());
            args.put("y", entry.getValue().getY());
            args.put("type", Formation.PlaysAs.STARTER.toString());

            jdbcInsertPlaysAs.execute(args);
        }

        for (Long player : formation.getSubstitutesIds()) {
            args = new HashMap<>();

            args.put("formation", formationId);
            args.put("player", player);
            args.put("type", Formation.PlaysAs.SUBSTITUTE.toString());

            jdbcInsertPlaysAs.execute(args);
        }

        return formation;
    }

    public Formation findById(long id) {
        final List<Formation> list = jdbcTemplate.query("SELECT * FROM formation LEFT JOIN playsas ON formationid = playsas.formation WHERE formationid = ? ORDER BY formationid", RESULT_SET_EXTRACTOR, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
