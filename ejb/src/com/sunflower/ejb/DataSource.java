package com.sunflower.ejb;

import oracle.jdbc.pool.OracleDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

/**
 * Created by denysburlakov on 03.12.14.
 */
public class DataSource {
    private static OracleDataSource dataSource;
    private static Logger logger = Logger.getLogger(DataSource.class);

    public static void setDataSource(){
        try {
            Locale.setDefault(new Locale("EN"));
            dataSource = new OracleDataSource();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        dataSource.setURL("jdbc:oracle:thin:@//194.44.143.139:1521/XE");
        dataSource.setUser("sunflower");
        dataSource.setPassword("sunflower14");
    }
    public static OracleDataSource getDataSource(){
        return dataSource;
    }

    public static void closeConnection(Connection connection){
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        }
    }
}