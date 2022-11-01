package org.scytec.interview.services;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// database connection holder
public enum DBCH {
    INSTANCE;

    private Connection currentConnection;
    private DSLContext dslContext;

    private synchronized Connection getConnection() {
        try {
            if (null == currentConnection || currentConnection.isClosed()) {
                currentConnection = DriverManager.getConnection("jdbc:h2:./db/db;");
                currentConnection.setAutoCommit(false);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return currentConnection;
    }

    public DSLContext getDslContext() {
        if (null == dslContext)
            dslContext = DSL.using(getConnection(), SQLDialect.H2);

        return dslContext;
    }
}
