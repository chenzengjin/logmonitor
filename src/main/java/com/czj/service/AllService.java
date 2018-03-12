package com.czj.service;

import com.czj.dao.AllDao;
import com.czj.domain.App;
import com.czj.domain.Rule;
import com.czj.domain.User;
import com.czj.utils.ConstantClass;
import jline.internal.TestAccessible;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 11273 on 2018-2-26.
 */
public class AllService{

    AllDao dao = new AllDao();

    /**
     * 根据应用ID，从应用表中查询是否需要监控
     * @param appId
     * @return
     */
    public boolean isMonitor(int appId) {
        String v = dao.idAppValid(appId);
        if(ConstantClass.VALID.equals(v)){
            return true;
        }else{
            return false;
        }
    }


    public List<String> getRuleByAppId(int appId) {
        return dao.getRuleByAppId(appId);
    }


    /**
     * 获取所有有效规则，封装到HashMap<Integer,List<String>>中
     * @return
     */
    public HashMap<Integer,List<Rule>> getAllRule() {
        HashMap<Integer, List<Rule>> map = new HashMap<Integer, List<Rule>>();
        List<Rule> allRule = dao.getAllRule();  //list内每一个Object数组成员即代表了一行记录，其中Object[0]为appId,Object[1]为keyword

        //遍历封装
        for(Rule rule : allRule){
            if(map.containsKey(rule.getAppId())){
                //map内已有该appId
                map.get(rule.getAppId()).add(rule);
            }else{
                //map内还没有该appId
                ArrayList<Rule> list = new ArrayList<Rule>();

                list.add(rule);
                map.put(rule.getAppId(),list);
            }
        }
        return map;
    }

    /**
     * 获取所有有效联系人，封装在HashMap<integer appId, List<User>>中
     * @return
     */
    public HashMap<Integer, List<User>> getAllValidUser() {
        HashMap<Integer, List<User>> map = new HashMap<Integer, List<User>>();
        User user = null;
        ArrayList<User> userList = new ArrayList<User>();

        //先获取所有应用（不管是否有效）的id,和对应的userId字符串,返回list<appId,userId串>
        List<Object[]> list = dao.getAllAppIdAndUserId();

        //遍历封装
        for(Object[] objs : list){

            //取出userId串，切割成userId数组
            String[] userIds = ((String)objs[1]).split(ConstantClass.SPLIT_USERID);
            //遍历封装User对象
            userList.clear();
            for(String userId : userIds){
                user = dao.getUserByUserId(Integer.parseInt(userId));
                if(user!=null){
                    userList.add(user);
                }

            }

            //封装到map中
            map.put((int)objs[0],userList);
        }
        return map;
    }


    public HashMap<Integer, App> getAllApp() {
        HashMap<Integer, App> map = new HashMap<Integer, App>();
        List<App> appList = dao.getAllApps();
        for(App app : appList){
            map.put(app.getId(),app);
        }
        return map;
    }

    public void addRecord(String content, int appId, int id) {
        dao.addRecord(new Date(),content, appId, id,ConstantClass.VALID);
    }


    /*@Test
    public void testDb(){
        System.out.println(dao.idAppValid(1));
    }*/
}
