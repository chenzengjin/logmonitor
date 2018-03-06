package com.czj.utils;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by 11273 on 2018-2-26.
 */
public class DBCPUtil {
    //得到数据源
    private static DataSource ds;
    static {
        try {
            //读取配置文件
            InputStream in=DBCPUtil.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
            Properties props=new Properties();
            props.load(in);
            //得到数据源实例
            ds= BasicDataSourceFactory.createDataSource(props);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource()
    {
        return ds;
    }
    public static Connection getConnection()
    {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
