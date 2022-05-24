package jm.task.core.jdbc.dao;

import java.sql.SQLException;

interface SQLCloseable extends AutoCloseable  {
    @Override void close() throws SQLException;
}
