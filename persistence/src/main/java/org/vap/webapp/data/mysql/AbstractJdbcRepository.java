package org.vap.webapp.data.mysql;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.vap.webapp.data.Persistent;

import javax.sql.DataSource;
import java.util.UUID;

/**
 * @author Vahe Pezeshkian
 * June 02, 2018
 */
public class AbstractJdbcRepository<T extends Persistent> {

    private DataSource dataSource;
    protected String table;
    protected RowMapper<T> rowMapper;

    public AbstractJdbcRepository(DataSource dataSource, String table, RowMapper<T> rowMapper) {
        this.dataSource = dataSource;
        this.table = table;
        this.rowMapper = rowMapper;
    }

    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    protected String generateId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
