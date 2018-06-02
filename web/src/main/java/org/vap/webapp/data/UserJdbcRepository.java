package org.vap.webapp.data;

import org.springframework.dao.DuplicateKeyException;
import org.vap.webapp.data.mysql.JdbcRepository;
import org.vap.webapp.model.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;

/**
 * @author Vahe Pezeshkian
 * June 02, 2018
 */
public class UserJdbcRepository extends JdbcRepository<User> implements Repository<User> {

    public UserJdbcRepository(DataSource dataSource) {
        super(dataSource, "User", new UserRowMapper());
    }

    @Override
    public User insert(User instance) {

        try {
            instance.setId(generateId());

            String sql = String.format("INSERT User(id, username, password) VALUE ('%s','%s','%s')",
                    instance.getId(), instance.getUsername(), instance.getPassword());

            getJdbcTemplate().execute(sql);

            return instance;
        } catch (DuplicateKeyException e) {
            return null;
        }
    }

    @Override
    public List<User> getByValue(String attribute, Object value) {
        String sql = String.format("SELECT * FROM User WHERE %s = '%s'", attribute, Objects.toString(value));
        return getJdbcTemplate().query(sql, rowMapper);
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM User";
        return getJdbcTemplate().query(sql, rowMapper);
    }
}
