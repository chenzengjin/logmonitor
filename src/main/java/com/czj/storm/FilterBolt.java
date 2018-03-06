package com.czj.storm;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.czj.utils.RuleUtil;

import java.util.Map;

/**
 * Created by 11273 on 2018-2-26.
 */
public class FilterBolt extends BaseRichBolt {
    OutputCollector collector = null;
    //AllService service = new AllService();
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }




    @Override
    public void execute(Tuple input) {

        try{
            //获取一行日志信息
            //传过来的是字节数组
            String logLine = new String((byte[])input.getValue(0));
            System.out.println("logline-----------------------------"+logLine+"----------------------------------------------------");


            //获取应用id
            String[] strings = logLine.split(":::");
            int appId = Integer.parseInt(strings[0]);
            System.out.println("appid-----------------------------"+appId+"----------------------------------------------------");

            //判断该应用shi是否需要监控
            System.out.println("------["+RuleUtil.isContainAppId(appId)+"]");
            if(RuleUtil.isContainAppId(appId)){
                ///需要监控,发送给CheckBolt，带上应用id
                System.out.println("iiiii");
                collector.emit(new Values(logLine,appId));

            }else{
                //不需要监控
                System.out.println("nnnnnn");
            }
            //kafkaSpout已经启用ack-fail机制的，不加此句会一直重发
            collector.ack(input);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }


    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("logLine","appId"));
    }
}