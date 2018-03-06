package com.czj.dao;

import com.czj.domain.App;
import com.czj.domain.Rule;
import com.czj.domain.User;
import com.czj.utils.DBCPUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;
import org.junit.Test;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by 11273 on 2018-2-26.
 */
public class AllDao {

    private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());

    /**
     * 根据应用id ,在应用表内查询isValid的值，为“0”表示不监控
     * @param appId
     * @return
     */
    public String idAppValid(int appId) {
        String sql = "select isValid from app where id = ?;";
        try {
            String v = qr.query(sql, new ScalarHandler<String>(1),appId);
            System.out.println("------------allDao idAppValid");
            return v;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据应用ID，从rule表中查所有规则
     * @param appId
     * @return
     */
    public List<String> getRuleByAppId(int appId) {
        String sql = "select keyword from rule where id = ?;";
        List<String> keyWordList = null;
        try {
            keyWordList = qr.query(sql, new ColumnListHandler<String>(1),appId);
            System.out.println("------------allDao getRuleByAppId");
            return keyWordList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Rule> getAllRule() {
        String sql = "select * from rule where isValid='1';";
        List<Rule> list = null;
        try {
            list = qr.query(sql, new BeanListHandler<Rule>(Rule.class));
            System.out.println("------------allDao getAllRule");
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 先获取所有应用（不管是否有效）的id,和对应的userId字符串,封装在list<appId,userId串>中
     */
    public List<Object[]> getAllAppIdAndUserId() {
        String sql = "select id,userId from app;";
        List<Object[]> list = null;
        try {
            list = qr.query(sql, new ArrayListHandler());
            System.out.println("------------allDao getAllAppIdAndUserId");
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public User getUserByUserId(int userId) {
        String sql = "select * from user where id=?;";
        try {
            System.out.println("------------allDao getUserByUserId");
            return qr.query(sql, new BeanHandler<User>(User.class),userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<App> getAllApps() {
        String sql = "select * from app";
        List<App> appList = null;
        try {
            appList = qr.query(sql, new BeanListHandler<App>(App.class));
            System.out.println("------------allDao getAllApps");
            return appList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addRecord(Date date, String content, int appId, int id, String s) {
        String sql = "insert into record(noticetime,info,appId,roleId,isEmail) values(?,?,?,?,?);";
        try {
            qr.update(sql, date,content,appId,id,s);
        } catch (SQLException e) {
            new RuntimeException(e);
        }
    }
}
