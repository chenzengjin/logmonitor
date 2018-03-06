package com.czj.storm;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import com.czj.domain.App;
import com.czj.domain.Rule;
import com.czj.domain.User;
import com.czj.service.AllService;
import com.czj.utils.EmailUtil;
import com.czj.utils.RuleUtil;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by 11273 on 2018-2-26.
 */
public class CheckBolt extends BaseRichBolt {
    OutputCollector collector = null;
    //AllService service = new AllService();  //CheckBolt继承的类中死心啊了序列化接口，猜测此类CheckBolt会被序列化，故Allservice类也要序列化，比较麻烦

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        try {
            String logLine = input.getString(0);
            int appId = input.getInteger(1);

            //获取规则keyWord列表
            List<Rule> ruleList = RuleUtil.getRuleListByAppId(appId);


            //判断是否触发了报警规则
            for(Rule rule : ruleList){
                if(logLine.toLowerCase().contains(rule.getKeyword())){
                    //符合规则，发送邮件报警。需要准备收件人和邮件内容
                    //1. 获取相关信息
                     App app = RuleUtil.getAppByAppId(appId);

                    //2. 先获取收件人列表
                    List<User> userList = RuleUtil.getUserListByAppId(appId);
                    if(userList != null){
                        for(User user : userList){
                            if(user.getIsValid().equals("1")){
                                //该人有效，发送邮件
                                //拼凑邮件内容
                                String content = user.getName()+",您好！  系统【"+app.getName()+"】触发了报警【"+rule.getName()+"】，触发关键字为：【"+rule.getKeyword()+"】。报警信息：【"+logLine+"】。  所在日志路径：【"+app.getLogpath()+"】，请尽快处理！";
                                //发送邮件
                                System.out.println("发邮件前：---"+user.getEmail()+"----"+content);
                                EmailUtil.sendEmail(user.getEmail(),content);
                                //往数据库中记录报警信息。
                                RuleUtil.addRecord(content,appId,rule.getId());
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
    @Test
    public void t2(){

       /* CheckBolt c = new CheckBolt();
        String a="abcdef";
        String b="";
        String c=a+b;
        System.out.print(c=="abcdef");*/
        int a1=1;

        int b1=1;

        int c1=2;

        int d1=a1+b1;

        Integer a = 1;

        Integer b = 2;

        Integer c = 3;

        Integer d = 3;

        Integer e = 321;

        Integer f = 321;

        Long g = 3L;

        System.out.println(a1==b1);   //true  结果1

        System.out.println(c1==d1);   //true  结果2

        System.out.println(c==d);   //true  结果3

        System.out.println(e==f);   //false  结果4
    }
   /* @Test
    public void testRuleUtil(){
        HashMap<Integer, List<String>> map = RuleUtil.getMap();
        System.out.println(map);

        App app = RuleUtil.getAppByAppId(1);
        System.out.println(app);
    }*/

   /*@Test
   public void testExecute(){
       int i = 2;
       while(i>0){
           try {
               String logLine = "  java test error";
               int appId = 1;


               //获取规则keyWord列表
               List<Rule> ruleList = RuleUtil.getRuleListByAppId(appId);


               //判断是否触发了报警规则
               for(Rule rule : ruleList){
                   if(logLine.toLowerCase().contains(rule.getKeyword())){
                       //符合规则，发送邮件报警。需要准备收件人和邮件内容
                       //1. 获取相关信息
                       App app = RuleUtil.getAppByAppId(appId);

                       //2. 先获取收件人列表
                       List<User> userList = RuleUtil.getUserListByAppId(appId);
                       if(userList != null){
                           for(User user : userList){
                               if(user.getIsValid().equals("1")){
                                   //该人有效，发送邮件
                                   //拼凑邮件内容
                                   String content = user.getName()+",您好！  系统【"+app.getName()+"】触发了报警【"+rule.getName()+"】，触发关键字为：【"+rule.getKeyword()+"】。报警信息：【"+logLine+"】。  所在日志路径：【"+app.getLogpath()+"】，请尽快处理！";
                                   //发送邮件
                                   System.out.println("发邮件前：---"+user.getEmail()+"----"+content);
                                   EmailUtil.sendEmail(user.getEmail(),content);
                                   //往数据库中记录报警信息。
                                   service.addRecord(content,appId,rule.getId());
                               }
                           }
                       }
                   }
               }
               i = 0;
           }catch (Exception e){
               if(i>1){
                   try {
                       Thread.sleep(2000);
                   } catch (InterruptedException e1) {
                       e1.printStackTrace();
                   }
                   i--;
               }else{
                   i--;
                   e.printStackTrace();

               }
           }
       }


   }*/


    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
