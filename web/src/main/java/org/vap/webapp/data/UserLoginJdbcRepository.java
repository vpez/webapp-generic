package org.vap.webapp.data;

import org.vap.webapp.data.mysql.JdbcRepository;
import org.vap.webapp.model.UserLogin;

import javax.sql.DataSource;

/**
 * @author Vahe Pezeshkian
 * June 02, 2018
 */
public class UserLoginJdbcRepository extends JdbcRepository<UserLogin> implements Repository<UserLogin> {

    public UserLoginJdbcRepository(DataSource dataSource) {
        super(dataSource, "UserLogin", new UserLoginRowMapper());
    }

    @Override
    public UserLogin insert(UserLogin instance) {
        String sql  = String.format("INSERT INTO UserLogin(userId, timestamp, host) VALUES ('%s', %s, '%s')",
                instance.getUserId(), instance.getTimestamp(), instance.getHost());

        getJdbcTemplate().execute(sql);
        return instance;
    }

    @Override
    public UserLogin update(UserLogin instance) {
        String sql  = String.format("REPLACE INTO UserLogin(userId, timestamp, host) VALUES ('%s', %s, '%s')",
                instance.getUserId(), instance.getTimestamp(), instance.getHost());

        getJdbcTemplate().execute(sql);
        return instance;
    }
}
