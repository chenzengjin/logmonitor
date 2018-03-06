package com.czj.utils;

import com.czj.domain.App;
import com.czj.domain.Rule;
import com.czj.domain.User;
import com.czj.service.AllService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 1. 缓存从数据库中查出来的规则。
 * 2. 定时从数据库中更新规则。
 *
 * Created by 11273 on 2018-2-26.
 */
public class RuleUtil {

    static private AllService service = new AllService();
    //appId为key,以对应Rule列表为value。只从数据库中取出有效的（isValid=1）Rule
    static private HashMap<Integer, List<Rule>> ruleMap = new HashMap<Integer, List<Rule>>();
    //用于缓存appId和对应App对象
    static private HashMap<Integer, App> appMap = new HashMap<Integer, App>();
    //缓存appId和对应的联系人列表
    static private HashMap<Integer, List<User>> userMap = null;


    static public void init(){
        userMap = service.getAllValidUser();
    }
    static public void initRuleMap(){
        //1. 查出所有的规则，以appId为key,以keyWord的list为value
        ruleMap = service.getAllRule();
    }

    static public void initAppMap(){

        appMap = service.getAllApp();
    }

    /* static {
         init();
     }*/
    public static HashMap<Integer, List<User>> getUserMap(){
        return userMap;
    }

    public static List<User> getUserListByAppId(int appId) {

        if(userMap.containsKey(appId)){
            return userMap.get(appId);
        }else{
            return null;
        }
    }




    //静态代码块，在类加载时就加载ruleMap
    static{
//        initRuleMap();
//        initAppMap();
        UpdateInfoUtil.timerUpdateInfoFromMysql(60);
    }

    static public HashMap<Integer, List<Rule>> getRuleMap(){
        return ruleMap;
    }
    static public HashMap<Integer, App> getAppMap(){
        return appMap;
    }

    static public boolean isContainAppId(int appId){
        System.out.println("ppppppppppppp"+ruleMap);
        System.out.println(ruleMap.get(appId));
        return ruleMap.containsKey(appId);
    }

    public static List<Rule> getRuleListByAppId(int appId) {
        return ruleMap.get(appId);

    }

    public static App getAppByAppId(int appId) {
        return appMap.get(appId);
    }

    public static void addRecord(String content, int appId, int id) {
        service.addRecord(content, appId, id);
    }
}
