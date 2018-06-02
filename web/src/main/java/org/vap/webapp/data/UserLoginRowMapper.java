package org.vap.webapp.data;

import org.springframework.jdbc.core.RowMapper;
import org.vap.webapp.model.UserLogin;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vahe Pezeshkian
 * June 02, 2018
 */
public class UserLoginRowMapper implements RowMapper<UserLogin> {
    @Override
    public UserLogin mapRow(ResultSet resultSet, int i) throws SQLException {
        UserLogin login = new UserLogin();
        login.setUserId(resultSet.getString("userId"));
        login.setTimestamp(resultSet.getLong("timestamp"));
        login.setHost(resultSet.getString("host"));
        return login;
    }
}
