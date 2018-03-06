package com.czj.storm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;

/**
 * Created by 11273 on 2018-2-26.
 */
public class StormMain {
    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {

        //0. 调用定时方法，从mysql更新规则等信息
        //UpdateInfoUtil.timerUpdateInfoFromMysql(60);

//        Thread t1 = new Thread(new UpdateInfoUtil());
//        t1.start();



        //1、准备一个TopologyBuilder
        TopologyBuilder topologyBuilder = new TopologyBuilder();

        /*准备kafka的Spout,四个参数
        参数1：需要一个KafkaSpout对象
        参数2：topic名
        参数3：在zk中的目录 ，运行topology后产生该目录
        参数4：id  ，在上面目录下产生以该ID为名字的文件夹，该文件夹下就有该topic下被处理（被KafkaSpout读取）的partition的信息
        */
        KafkaSpout kafkaSpout = new KafkaSpout(new SpoutConfig(new ZkHosts("shizhan:2181,shizhan01:2181,shizhan02:2181"), "logmonitor", "/mySpout_logmonitor", "kafkaSpout"));

        /**
         * 参数1：名字
         * 参数2：自定义的类
         * 参数3：并发度，即设置线程数量
         */
        topologyBuilder.setSpout("kafkaSpout", kafkaSpout, 3); //kafka分区partition只有3个，故设置4个线程会有一个空闲
        topologyBuilder.setBolt("filterBolt",new FilterBolt(),4).shuffleGrouping("kafkaSpout");
        topologyBuilder.setBolt("checkBolt",new CheckBolt(),4).localOrShuffleGrouping("filterBolt");

        //2、创建一个configuration，用来指定当前topology 需要的worker的数量
        Config config =  new Config();
        config.setNumWorkers(2); //我的集群上有两个supervisor

        //3、提交任务  -----两种模式 本地模式和集群模式


        if(args.length>0 && args!=null){
            StormSubmitter.submitTopology(args[0],config,topologyBuilder.createTopology());
        }else{
            //没输入topologs名字就运行本地模式
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology("logmonitor",config,topologyBuilder.createTopology());
        }
        //集群模式
//        StormSubmitter.submitTopology("mywordcount",config,topologyBuilder.createTopology());

        //本地模式
//        LocalCluster localCluster = new LocalCluster();
//        localCluster.submitTopology("f_k_s",config,topologyBuilder.createTopology());

    }
}
