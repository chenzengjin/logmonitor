package com.czj.utils;

import com.czj.domain.User;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 11273 on 2018-3-1.
 */
public class UpdateInfoUtil{

    static Timer timer;

    static public void timerUpdateInfoFromMysql(int seconds){
        timer = new Timer();
        timer.scheduleAtFixedRate(new MysqlTimerTask(),0,seconds*1000);  //一分钟从数据库更新一次
    }

    /*@Override
    public void run() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new MysqlTimerTask(),0,60000);  //一分钟从数据库更新一次
    }*/

    static class MysqlTimerTask extends TimerTask{
       @Override
       public void run() {
           System.out.println("开始执行3个更新方法。。。。。。");
           RuleUtil.initAppMap();
           RuleUtil.initRuleMap();
           RuleUtil.init();
           System.out.println("已经执行3个更新方法.....");
           System.out.println("----1"+RuleUtil.getRuleMap()+"1----------");
           System.out.println("----2"+RuleUtil.getAppMap()+"2----------");
           System.out.println("----3"+RuleUtil.getUserMap()+"3----------");
           HashMap<Integer, List<User>> userMap = RuleUtil.getUserMap();
           for(int i : userMap.keySet()){
               List<User> users = userMap.get(i);
               for(User u : users){
                   System.out.println(u);
                   System.out.println("++++++++++++++++++++++++++++");
               }
           }
       }
   }

}
