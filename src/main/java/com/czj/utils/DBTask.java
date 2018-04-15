package com.czj.utils;

/**
 * Created by 11273 on 2018-4-11.
 */
public class DBTask implements  Runnable{

    private String content ;
    private int appId;
    private int ruleId;

    public DBTask(){

    }
    public DBTask(String con,int appId,int ruleId){
        this.content = con;
        this.appId = appId;
        this.ruleId = ruleId;
    }



    @Override
    public void run() {
        RuleUtil.addRecord(content,appId,ruleId);
    }

}
