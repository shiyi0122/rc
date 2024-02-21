package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hna.hka.archive.management.assetsSystem.dao.PeopleCountingMapper;
import com.hna.hka.archive.management.assetsSystem.service.PeopleCountingService;
import com.hna.hka.archive.management.system.dao.SysOrderMapper;
import com.hna.hka.archive.management.system.util.DateUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/2/11 10:01
 * 人流统计设备
 */
@Service
public class PeopleCountingServiceImpl implements PeopleCountingService {

    @Autowired
    PeopleCountingMapper peopleCountingMapper;
    @Autowired
    SysOrderMapper sysOrderMapper;

    @Override
    public Map<String, Object> getPeopleCountingList(Long spotId, String beginDate, String endDate) {

        Map<String, Object> map = new HashMap<>();
        List<String> sumNumber = new ArrayList<>();
        List<Double> aList = new ArrayList<>();
        List<Double> bList = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0.00");


        spotId = 83102483267695l;
        Long parkingId = 93011283451711l;
        //总单数 / 人数
        Double a = 0d;
        //总金额 / 人数
        Double b = 0d;


        Map map1 = IUseOthers(spotId.toString(), beginDate, endDate);
        sumNumber =(List<String>) map1.get("list");

        try{
            //获取间隔日期
            List<String> dateList = DateUtil.betweenDays(beginDate, endDate);
            for (int i = 0; i < dateList.size(); i++) {
                //人数
                String s = sumNumber.get(i);
                //获取订单数量
                Integer orderNumber = sysOrderMapper.getOrderNumberBySpotIdAndParkIdAndDate(spotId,parkingId,dateList.get(i));
                //获取订单金额
                Double orderMoney = sysOrderMapper.getOrderMoneyBySpotIdAndParkIdAndDate(spotId,parkingId,dateList.get(i));

                if (orderNumber != null && orderNumber != 0 && !"0".equals(s)){
                    a = Double.parseDouble(df.format(orderNumber /  Double.parseDouble(s)));
                }else{
                    a = 0d;
                }

                if (orderMoney != null && orderMoney != 0 && !"0".equals(s)){

                    b = Double.parseDouble(df.format(orderMoney / Double.parseDouble(s)));

                }else{
                    b = 0d;
                }
                aList.add(a);
                bList.add(b);
            }
            map.put("dateList",dateList);
            map.put("peopleList",sumNumber);
            map.put("orderNumberList",aList);
            map.put("moneyList",bList);
        }catch (Exception e){

            e.printStackTrace();

        }

        return map;
    }


    public Map  IUseOthers(String spotId,String beginDate,String endDate){
        boolean flag = false;
        Map<String, Object> map = new HashMap<>();
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod("http://screen.topsroboteer.com:8085/bigPid/sysScenicSpotWifiData/getPeopleNumber");//写网址
//        PostMethod postMethod = new PostMethod("http://localhost:8085/bigPid/sysScenicSpotWifiData/getPeopleNumber");//写网址

        postMethod.setRequestHeader("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        try{
//            postMethod.addParameter("spotId",spotId);
            postMethod.addParameter("beginDate",beginDate);
            postMethod.addParameter("endDate",endDate);
            int status = client.executeMethod(postMethod);

            //获取返回信息

//            JSONObject jsonObject = JSONObject.fromObject(postMethod.getResponseBodyAsString());
            JSONArray objects = JSON.parseArray(postMethod.getResponseBodyAsString());
            List<String> BigPidSpots = JSONObject.parseArray(objects.toJSONString(), String.class);
            map.put("list",BigPidSpots);

        }catch(HttpException e){
            e.printStackTrace();

        }catch(IOException e){
            e.printStackTrace();

        }finally{
            if(postMethod != null){
                postMethod.releaseConnection();
            }
        }
        return map;
    }



}
