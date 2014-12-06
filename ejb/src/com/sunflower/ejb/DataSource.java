package com.sunflower.ejb;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.SQLException;

/**
 * Created by denysburlakov on 03.12.14.
 */
public class DataSource {
    private static OracleDataSource dataSource;
    public static void setDataSource(){
        try {
            dataSource = new OracleDataSource();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dataSource.setURL("jdbc:oracle:thin:@//194.44.143.139:1521/XE");
        dataSource.setUser("sunflower");
        dataSource.setPassword("sunflower14");
    }
    public static OracleDataSource getDataSource(){
        return dataSource;
    }
}