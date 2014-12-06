package com.sunflower.ejb;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by denysburlakov on 03.12.14.
 */
public class DataSource {
    private static javax.sql.DataSource dataSource;

    public static void setDataSource(){
        try {
            InitialContext ic = new InitialContext();
            dataSource =  (javax.sql.DataSource) ic.lookup("jdbc_local/XE");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
    public static javax.sql.DataSource getDataSource(){
        return dataSource;
    }
}
