package com.meidi.dbaccess;

import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;

/**
 * 数据源
 *
 * @author
 * @creation 2012-8-16 下午9:38:07
 */
public class DBAccess {

    private static DBAccess instance = null;

    public static DBAccess getInstance() {
        synchronized (DBAccess.class) {
            if (instance == null) {
                instance = new DBAccess();
            }
        }
        return instance;
    }

    public DBAccess() {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() throws Exception {
        JAXPConfigurator.configure(new InputStreamReader(DBAccess.class.getClassLoader().getResourceAsStream("proxool.xml"), "UTF-8"), false);

    }

    public Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
            con = DriverManager.getConnection("proxool.md");
        } catch (Exception e) {

        }
        return con;
    }
}
