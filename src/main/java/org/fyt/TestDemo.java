package org.fyt;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class TestDemo {


    public static void main(String[] args) {
        //不使用中间变量互换两个数的值
//        int a=20;
//        int b=30;
//        a=a*b;
//        b=a/b;
//        a=a/b;
//        System.out.println("a="+a+"\n"+"b="+b);
        //类型为ArrayList<HashMap<String,Integer>>的一个对象list
        //请写出java代码，计算其中所有整数值的和，并找出值最小的HashMap对象
        ArrayList list=new ArrayList();
        HashMap<String,Integer> map=new HashMap<>();
        map.put("key1",5);
        map.put("key2",10);
        map.put("key3",15);
        list.add(map);
        int total=0;
        //遍历list中map的value值
        for (int i=0;i<list.size();i++) {
            //获取list中的map对象
            HashMap map1 = (HashMap) list.get(i);
            Iterator iterator = map1.keySet().iterator();
            while (iterator.hasNext()) {
                //获取map的key值
                String key = (String) iterator.next();
                //根据key取value
                Object value1 = map1.get(key);
                int  value = Integer.parseInt(String.valueOf(value1));
                total=total+value;
                System.out.println("value:"+value+"\n"+"整数值总数："+total);
                //判断value1的类型  Integer
                //System.out.println(value1.getClass());
            }
        }

    }

}