package org.fyt;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;


public class ESDemo
{
    //集群管理
    @Test
    public void test() throws UnknownHostException
    {
        //指定ES集群
        Settings settings =Settings.builder().put("cluster.name","es-6.5.4-test").build();
        //创建访问es服务器的客户端
        TransportClient client=new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"),9300));

        ClusterHealthResponse healths=client.admin().cluster().prepareHealth().get();
        String clusterName=healths.getClusterName();
        System.out.println("集群名称："+clusterName);
        //关闭客户端
        client.close();
    }
    @Test
    public void add() throws IOException
    {
        //指定ES集群
        Settings settings =Settings.builder().put("cluster.name","es-6.5.4-test").build();
        //创建访问es服务器的客户端
        TransportClient client=new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"),9300));

       //添加文档
        XContentBuilder doc= XContentFactory.jsonBuilder()
                .startObject()
                .field("Id","1")
                .field("name","张三")
                .field("age","25")
                .field("address","雨花区铁心桥街道")
                .endObject();
        IndexResponse response =client.prepareIndex("车辆信息表","blog","1")
                .setSource(doc).get();
        //测试创建是否成功
        System.out.println("索引名称:" + response.getIndex() + "\n类型:" + response.getType()
                + "\n文档ID:" + response.getId() + "\n当前实例状态:" + response.status());
        //关闭客户端
        client.close();
    }
@Test
    public void delete() throws UnknownHostException{
        //指定ES集群
        Settings settings =Settings.builder().put("cluster.name","es-6.5.4-test").build();
        //创建访问es服务器的客户端
        TransportClient client=new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"),9300));
        //删除文档
        DeleteResponse deleteResponse = client.prepareDelete("人物信息表1", "person", "1").get();

          System.out.println("deleteResponse索引名称:" + deleteResponse.getIndex() + "\n deleteResponse类型:" + deleteResponse.getType()
                  + "\n deleteResponse文档ID:" + deleteResponse.getId() + "\n当前实例deleteResponse状态:" + deleteResponse.status());
    }


    @Test
    public void select() throws UnknownHostException{
        //指定ES集群
        Settings settings =Settings.builder().put("cluster.name","es-6.5.4-test").build();
        //创建访问es服务器的客户端
        TransportClient client=new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"),9300));
        //从es中查询数据
        GetResponse response=client.prepareGet("人物信息表","person","1").execute().actionGet();

        System.out.println(response.getSource());
        client.close();
    }


    @Test
    public void update() throws IOException,InterruptedException, ExecutionException {
        //指定ES集群
        Settings settings =Settings.builder().put("cluster.name","es-6.5.4-test").build();
        //创建访问es服务器的客户端
        TransportClient client=new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"),9300));
        //更新数据
        UpdateRequest request=new UpdateRequest();
        request.index("人物信息表")
                .type("person")
                .id("1")
                .doc(
                        XContentFactory.jsonBuilder().startObject()
                        .field("name","李四")
                        .endObject()
                );
        UpdateResponse response=client.update(request).get();
        System.out.println(response.status());
    }




}
