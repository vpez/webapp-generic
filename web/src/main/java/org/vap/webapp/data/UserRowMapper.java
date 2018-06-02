package org.vap.webapp.data;

import org.springframework.jdbc.core.RowMapper;
import org.vap.webapp.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vahe Pezeshkian
 * June 02, 2018
 */
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getString("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }
}