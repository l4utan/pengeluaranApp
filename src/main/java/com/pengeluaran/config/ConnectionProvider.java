package com.pengeluaran.config;

import java.sql.Connection;
import java.sql.SQLException;

/** Abstraksi sumber koneksi. Repository bergantung pada ini, bukan pada PostgreSQL. */
public interface ConnectionProvider {

    Connection getConnection() throws SQLException;
}
