package com.czj.storm;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import com.czj.domain.App;
import com.czj.domain.Rule;
import com.czj.domain.User;
import com.czj.service.AllService;
import com.czj.utils.*;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by 11273 on 2018-2-26.
 */

public class CheckBolt extends BaseRichBolt {
    OutputCollector collector = null;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        try {
            String logInfo = input.getString(0);
            int appId = input.getInteger(1);

            //获取规则keyWord列表
            List<Rule> ruleList = RuleUtil.getRuleListByAppId(appId);

            //判断是否触发了报警规则
            for(Rule rule : ruleList){
                if(logInfo.toLowerCase().contains(rule.getKeyword())){
                    //符合规则，发送邮件报警。需要准备收件人和邮件内容
                    //1. 获取相关信息
                     App app = RuleUtil.getAppByAppId(appId);
                     if(app==null){
                         throw new RuntimeException("该应用不存在！！");
                     }

                    //2. 先获取收件人列表
                    List<User> userList = RuleUtil.getUserListByAppId(appId);
                    if(userList != null){
                        for(User user : userList){
                            if(user.getIsValid().equals("1")){
                                //该人有效，发送邮件
                                //拼凑邮件内容
                                //String content = user.getName()+",您好！  系统【"+app.getName()+"】触发了报警【"+rule.getName()+"】，触发关键字为：【"+rule.getKeyword()+"】。报警信息：【"+logLine+"】。  所在日志路径：【"+app.getLogpath()+"】，请尽快处理！";
                                StringBuilder content = new StringBuilder(user.getName())
                                        .append(",您好！  系统【")
                                        .append(app.getName())
                                        .append("】触发了报警【")
                                        .append(rule.getName())
                                        .append("】，触发关键字为：【")
                                        .append(rule.getKeyword())
                                        .append("】。报警信息：【")
                                        .append(logInfo)
                                        .append("】。  所在日志路径：【")
                                        .append(app.getLogpath())
                                        .append("】，请尽快处理！");
                                //发送邮件
                                //System.out.println("发邮件前：---"+user.getEmail()+"----"+content);

                                //TODO  :交给线程池执行邮件发送
                                MyThreadPool.exec(new MailTask(user.getEmail(),content.toString()));
                                MyThreadPool.exec(new DBTask(content.toString(),appId,rule.getId()));

                                //EmailUtil.sendEmail(user.getEmail(),content.toString());
                                //往数据库中记录报警信息。
                                //RuleUtil.addRecord(content.toString(),appId,rule.getId());
                            }
                        }
                    }
                }
            }
            //kafkaSpout已经启用ack-fail机制的，不加此句会一直重发
            collector.ack(input);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
