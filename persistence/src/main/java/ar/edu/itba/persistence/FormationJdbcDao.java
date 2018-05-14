package ar.edu.itba.persistence;

import ar.edu.itba.interfaces.dao.FormationDao;
import ar.edu.itba.model.Formation;
import ar.edu.itba.model.Player;
import ar.edu.itba.model.utils.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class FormationJdbcDao implements FormationDao{

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

    @Override
    public Formation create(Map<Player, Point> formation, int pressure, int attitude, Player captain, Player freeKickTaker, Player penaltyTaker) {
        final Map<String, Object> args = new HashMap<>();
        final Map<String, Object> args2 = new HashMap<>();

        args.put("pressure", pressure);
        args.put("attitude", attitude);
        args.put("captain", captain.getId());
        args.put("penaltyTaker", penaltyTaker);
        args.put("freeKickTaker", freeKickTaker);

        final Number formationId = jdbcInsertFormation.executeAndReturnKey(args);

        for (Map.Entry<Player, Point> entry : formation.entrySet()) {

            args2.put("formation", formationId);
            args2.put("player", entry.getKey());
            args2.put("x", entry.getValue().getX());
            args2.put("y", entry.getValue().getY());
//            type?????

            jdbcInsertPlaysAs.execute(args2);
        }

        return new Formation(formationId.longValue(), captain, freeKickTaker, penaltyTaker, null, null, pressure, attitude);
    }

    @Override
    public boolean save(Formation formation) {
        //TODO
        return false;
    }

    @Override
    public Formation findById(long id) {

        //ToDo

        return null;
    }
}
