package org.vap.webapp.data.mysql;

import org.springframework.jdbc.core.RowMapper;
import org.vap.webapp.data.Persistent;
import org.vap.webapp.data.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;

/**
 * @author Vahe Pezeshkian
 * June 02, 2018
 */
public class JdbcRepository<T extends Persistent> extends AbstractJdbcRepository<T> implements Repository<T> {

    public JdbcRepository(DataSource dataSource, String table, RowMapper<T> rowMapper) {
        super(dataSource, table, rowMapper);
    }

    @Override
    public T getById(String id) {

        return null;
    }

    @Override
    public List<T> getAll() {
        return null;
    }

    @Override
    public List<T> getByValue(String attribute, Object value) {
        String sql = String.format("SELECT * FROM %s WHERE %s = '%s'", table, attribute, Objects.toString(value));
        return getJdbcTemplate().query(sql, rowMapper);
    }

    @Override
    public T insert(T instance) {
        return null;
    }

    @Override
    public T update(T instance) {
        return null;
    }

    @Override
    public boolean deleteById(String id) {
        return false;
    }
}
