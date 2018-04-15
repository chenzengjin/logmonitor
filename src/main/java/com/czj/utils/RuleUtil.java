package com.czj.utils;

import com.czj.domain.App;
import com.czj.domain.Rule;
import com.czj.domain.User;
import com.czj.service.AllService;

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
    //用于缓存appId和对应所有App对象
    static private HashMap<Integer, App> appMap = new HashMap<Integer, App>();
    //缓存appId和对应的所有联系人列表
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

    //静态代码块，在类加载时就加载数据库数据到缓存中
    static{
//        initRuleMap();
//        initAppMap();
        UpdateInfoUtil.timerUpdateInfoFromMysql(60);
    }

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



    static public HashMap<Integer, List<Rule>> getRuleMap(){
        return ruleMap;
    }
    static public HashMap<Integer, App> getAppMap(){
        return appMap;
    }

    static public boolean isContainAppId(int appId){
//        System.out.println("ppppppppppppp"+ruleMap);
//        System.out.println(ruleMap.get(appId));
        return ruleMap.containsKey(appId);
    }

    /**
     * 根据appId获取相应的规则列表
     * @param appId
     * @return
     */
    public static List<Rule> getRuleListByAppId(int appId) {
        return ruleMap.get(appId);

    }

    public static App getAppByAppId(int appId) {
        if(appMap.containsKey(appId)){
            return appMap.get(appId);
        }else{
            return null;
        }

    }

    public static void addRecord(String content, int appId, int id) {
        service.addRecord(content, appId, id);
    }

    public static boolean isVaildFromAppId(int appId) {
        if(appMap.containsKey(appId)){
            if("1".equals(appMap.get(appId).getIsValid())){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }

    }
}
