package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.hna.hka.archive.management.assetsSystem.dao.RTStatisticsMapper;
import com.hna.hka.archive.management.assetsSystem.service.RTStatisticsService;
import com.hna.hka.archive.management.system.dao.SysOrderMapper;
import com.hna.hka.archive.management.system.dao.SysRobotDispatchLogMapper;
import com.hna.hka.archive.management.system.dao.SysRobotMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotMapper;
import com.hna.hka.archive.management.system.model.SysRobotDispatchLog;
import com.hna.hka.archive.management.system.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-25 10:42
 **/
@Service
public class RTStatisticsServiceImpl implements RTStatisticsService {

    @Autowired
    RTStatisticsMapper mapper;

    @Autowired
    SysScenicSpotMapper sysScenicSpotMapper;

    @Autowired
    SysOrderMapper sysOrderMapper;

    @Autowired
    SysRobotMapper sysRobotMapper;

    @Autowired
    SysRobotDispatchLogMapper sysRobotDispatchLogMapper;

    @Override
    public List<HashMap> timeList(String order, Long companyId, Long spotId, Integer type, String status, Integer dateType, String startDate, String endDate, Integer timeType, Double totalTime, String robotCode, Integer pageNum, Integer pageSize) {
        DecimalFormat df = new DecimalFormat("#.00");
        List<HashMap> hashMaps = mapper.timeList(order, companyId, spotId, type, status, dateType, startDate, endDate, timeType, totalTime, robotCode, pageNum, pageSize);
        for (HashMap hashMap : hashMaps) {
            Long spotIdN = (Long) hashMap.get("spotId");
            Integer robotRunNum = mapper.getSpotAndDateByRobot(spotIdN, startDate, endDate, dateType);
            Long robotCount = sysRobotMapper.getSpotIdByRobotCount(spotIdN);
            if (!StringUtils.isEmpty(robotRunNum) && robotRunNum != 0 && !StringUtils.isEmpty(robotCount) && robotCount != 0) {
                Double lyl = (robotRunNum / robotCount.doubleValue()) * 100;
                hashMap.put("lyl", df.format(lyl));
            } else {
                hashMap.put("lyl", 0);
            }
        }
        return hashMaps;
    }

    @Override
    public HashMap getTimeCount(Long companyId, Long spotId, Integer type, String status, Integer dateType, String startDate, String endDate, Integer timeType, Double totalTime, String robotCode) {
        return mapper.getTimeCount(companyId, spotId, type, status, dateType, startDate, endDate, timeType, totalTime, robotCode);
    }

    @Override
    public List<HashMap> companyList(Long spotId) {
        return mapper.companyList(spotId);
    }

    @Override
    public List<HashMap> spotList(Long companyId) {
        return mapper.spotList(companyId);
    }

    @Override
    public List<HashMap> amountList(Integer type, String robotCode, String order, String payType, Long companyId, Long spotId, String status, Integer dateType, String startDate, String endDate, Integer amountType, Double totalAmount, Integer pageNum, Integer pageSize) {
        List<HashMap> hashMaps = mapper.amountList(type, robotCode, order, payType, companyId, spotId, status, dateType, startDate, endDate, amountType, totalAmount, pageNum, pageSize);

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        DecimalFormat df = new DecimalFormat("0");
        try {

            for (HashMap hashMap : hashMaps) {
                //计算机器人加权平均数
                String times = endDate;
                Double robotNum = 0d;
                Long scenicSpotId = (Long) hashMap.get("spotId");
                String scenicSpotName = (String) hashMap.get("spotName");
                BigDecimal realAmount = (BigDecimal) hashMap.get("realAmount");
                BigDecimal yysc = (BigDecimal) hashMap.get("yysc");
//                Long count = (Long) hashMap.get("COUNT");
                Long count = sysRobotMapper.getSpotIdByRobotCount(scenicSpotId);
                List<String> dateList = sysRobotDispatchLogMapper.getRobotDispatchLogDate(scenicSpotName, startDate, endDate);
                for (String date : dateList) {
                    String dates = DateUtil.findDates(date, times);
                    robotNum = robotNum + (Double.parseDouble(dates) * count);
                    times = date;
                    Integer countIn = sysRobotDispatchLogMapper.getRobotDispatchLogInCount(scenicSpotName, date);
                    Integer countOut = sysRobotDispatchLogMapper.getRobotDispatchLogOutCount(scenicSpotName, date);
                    if (countIn > 0) {
                        count = count - countIn + countOut;
                    } else {
                        count = count + countOut - countIn;
                    }
                }
                if (robotNum != 0) {
                    count = Long.parseLong(df.format(robotNum / 30));
                }

                Double money = realAmount.doubleValue();
//                money = mapper.getSpotAmountMoney(scenicSpotId.toString(),startDate,endDate);
                Double pjjyje = 0d;
                Double pjyysc = 0d;

                Double time = 0d;
                time = mapper.getSpotOperateTime(scenicSpotId.toString(), startDate, endDate);

                if (!StringUtils.isEmpty(money) && money != 0) {

                    pjjyje = Double.parseDouble(decimalFormat.format(money / count));

                    hashMap.put("avgsc", pjjyje);
//                    String lastYearCurrentDateDayS = DateUtil.getLastYearCurrentDateDay(startDate);
//                    String lastYearCurrentDateDayD = DateUtil.getLastYearCurrentDateDay(endDate);
//                    Double spotAmountMoney = mapper.getSpotAmountMoney(scenicSpotId.toString(), lastYearCurrentDateDayS, lastYearCurrentDateDayD);
//                    if (spotAmountMoney != null ){
//
//                        hb =  (money - spotAmountMoney) / spotAmountMoney *100;
//
//                    }
//                    String dates = DateUtil.findDates(startDate, endDate);
//                    Integer day= Integer.parseInt(dates) - 2*Integer.parseInt(dates);
//                    String tbs = DateUtil.addDay(startDate, day);
//                    String tbd = DateUtil.addDay(endDate, day);
//                    Double spotAmountMoneytb = mapper.getSpotAmountMoney(scenicSpotId.toString(), tbs, tbd);
//                    if (spotAmountMoneytb != null ){
//
//                        tb =  (money - spotAmountMoneytb) / spotAmountMoneytb *100;
//
//                    }
//                }

                    if (!StringUtils.isEmpty(yysc) && yysc.doubleValue() != 0) {
                        pjyysc = Double.parseDouble(decimalFormat.format(yysc.doubleValue() / count));
//                        yysc = time / 60;
//                        pjyysc = Double.parseDouble(decimalFormat.format( yysc / count));
                        hashMap.put("avgyysc", pjyysc);
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return hashMaps;
    }

    @Override
    public HashMap getAmountCount(Integer type, String robotCode, String order, String payType, Long companyId, Long spotId, String status, Integer dateType, String startDate, String endDate, Integer amountType, Double totalAmount) {
        return mapper.getAmountCount(type, robotCode, order, payType, companyId, spotId, status, dateType, startDate, endDate, amountType, totalAmount);
    }

    @Override
    public List<HashMap> spotTimeList(String order, Long companyId, Long spotId, Integer type, String status, Integer dateType, String startDate, String endDate, Integer timeType, Double totalTime, String robotCode, Integer pageNum, Integer pageSize) {

        DecimalFormat df = new DecimalFormat("#.00");
        List<HashMap> hashMaps = mapper.spotTimeList(order, companyId, spotId, type, status, dateType, startDate, endDate, timeType, totalTime, robotCode, pageNum, pageSize);
        for (HashMap hashMap : hashMaps) {
            Long spotIdN = (Long) hashMap.get("spotId");
            Integer robotRunNum = mapper.getSpotAndDateByRobot(spotIdN, startDate, endDate, dateType);
            Long robotCount = sysRobotMapper.getSpotIdByRobotCount(spotIdN);
            if (!StringUtils.isEmpty(robotRunNum) && robotRunNum != 0 && !StringUtils.isEmpty(robotCount) && robotCount != 0) {
                Double lyl = (robotRunNum / robotCount.doubleValue()) * 100;
                hashMap.put("lyl", df.format(lyl));
            } else {
                hashMap.put("lyl", 0);
            }
        }
        return hashMaps;
    }

    @Override
    public HashMap getSpotTimeCount(Long companyId, Long spotId, Integer type, String status, Integer dateType, String startDate, String endDate, Integer timeType, Double totalTime, String robotCode) {
        return mapper.getSpotTimeCount(companyId, spotId, type, status, dateType, startDate, endDate, timeType, totalTime, robotCode);
    }

    @Override
    public List<HashMap> spotAmountList(Integer type, String robotCode, String order, String payType, Long companyId, Long spotId, String status, Integer dateType, String startDate, String endDate, Integer amountType, Double totalAmount, Integer pageNum, Integer pageSize) {
        if (endDate.equals(DateUtil.crutDate())) {
            return mapper.spotAmountListNew(type, robotCode, order, payType, companyId, spotId, status, dateType, startDate, endDate, amountType, totalAmount, pageNum, pageSize);
        }
        return mapper.spotAmountList(type, robotCode, order, payType, companyId, spotId, status, dateType, startDate, endDate, amountType, totalAmount, pageNum, pageSize);
    }

    @Override
    public HashMap getSpotAmountCount(Integer type, String robotCode, String order, String payType, Long companyId, Long spotId, String status, Integer dateType, String startDate, String endDate, Integer amountType, Double totalAmount) {
        return mapper.getSpotAmountCount(type, robotCode, order, payType, companyId, spotId, status, dateType, startDate, endDate, amountType, totalAmount);
    }

    @Override
    public List<HashMap> timeChart(Long companyId, Long spotId, Integer dateType, String startDate, String endDate) {
        return mapper.timeChart(companyId, spotId, dateType, startDate, endDate);
    }

    @Override
    public List<HashMap> amountChart(Long companyId, Long spotId, Integer dateType, String startDate, String endDate) {
        return mapper.amountChart(companyId, spotId, dateType, startDate, endDate);
    }

    @Override
    public List<HashMap> spotAmountListNew(Integer type, String robotCode, String order, String payType, Long companyId, Long spotId, String status, Integer dateType, String startDate, String endDate, Integer amountType, Double totalAmount, Integer pageNum, Integer pageSize) {
        List<HashMap> hashMaps = new ArrayList<>();
        if (endDate.equals(DateUtil.crutDate())) {
            hashMaps = mapper.spotAmountListNew(type, robotCode, order, payType, companyId, spotId, status, dateType, startDate, endDate, amountType, totalAmount, pageNum, pageSize);

//            HashMap hashMap = hashMaps.get(0);
//            List<SysScenicSpot> spotList = sysScenicSpotMapper.getScenicSpotOperated();
//            for (SysScenicSpot sysScenicSpot : spotList) {
//
//            int i =  sysOrderMapper.getSpotIdByOrder(sysScenicSpot.getScenicSpotId(),startDate,endDate);
//
//            if (i==0){
//                hashMap.put("spotId",sysScenicSpot.getScenicSpotId());
//                hashMap.put("spotName",sysScenicSpot.getScenicSpotName());
//                hashMap.put("ROBOT_CODE",null);
//                hashMap.put("avgsc",null);
//                hashMap.put("avgyysc",null);
//                hashMap.put("hb",0);
//                hashMap.put("jxz",0);
//                hashMap.put("mbwcbl",null);
//                hashMap.put("realAmount",null);
//                hashMap.put("target",null);
//                hashMap.put("tb",0);
//
//                hashMap.put("wuyingshou",1);
//                hashMap.put("yysc",null);
//
//                hashMaps.add(hashMap);
//            }
//            }
//
//            List<HashMap> hashMaps1 = this.ListSplit(hashMaps, pageSize, pageNum);
//            List<HashMap> page = this.page(hashMaps, pageSize, pageNum);

//            return  page;
            return hashMaps;
        }


        return mapper.spotAmountList(type, robotCode, order, payType, companyId, spotId, status, dateType, startDate, endDate, amountType, totalAmount, pageNum, pageSize);

    }

    @Override
    public HashMap getSpotAmountCountNew(Integer type, String robotCode, String order, String payType, Long companyId, Long spotId, String status, Integer dateType, String startDate, String endDate, Integer amountType, Double totalAmount) {
        HashMap spotAmountCountNew = mapper.getSpotAmountCountNew(type, robotCode, order, payType, companyId, spotId, status, dateType, startDate, endDate, amountType, totalAmount);
        Integer operateNum = sysScenicSpotMapper.getScenicSpotOperateNum(1);
        spotAmountCountNew.put("count", operateNum);

        return spotAmountCountNew;

    }


    @Override
    public List<Map<String, Object>> spotAmountlistNewTow(Integer type, String robotCode, String order, String payType, Long companyId, Long spotId, String status, Integer dateType, String startDate, String endDate, Integer amountType, Double totalAmount, Integer pageNum, Integer pageSize) {
        List<Map<String, Object>> spotAmountCountNewTow = mapper.spotAmountListNewTow(type, robotCode, order, payType, companyId, spotId, status, dateType, startDate, endDate, amountType, totalAmount, pageNum, pageSize);
        List<Map<String, Object>> page = new ArrayList<>();
        Map<String, Object> search = new HashMap<>();

        try {
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
//            List<HashMap> spotAmountCountNewTow =   mapper.spotAmountListNewTow(type, robotCode, order, payType, companyId, spotId, status, dateType, startDate, endDate, amountType, totalAmount, pageNum , pageSize);
            for (Map hashMap : spotAmountCountNewTow) {
                //计算机器人加权平均数
                String times = endDate;
                Double robotNum = 0d;
                Long scenicSpotId = (Long) hashMap.get("SCENIC_SPOT_ID");
                String scenicSpotName = (String) hashMap.get("SCENIC_SPOT_NAME");
                Long count = (Long) hashMap.get("COUNT");
                List<String> dateList = sysRobotDispatchLogMapper.getRobotDispatchLogDate(scenicSpotName, startDate, endDate);
                for (String date : dateList) {
                    String dates = DateUtil.findDates(date, times);
                    robotNum = robotNum + (Double.parseDouble(dates) * count);
                    times = date;
                    Integer countIn = sysRobotDispatchLogMapper.getRobotDispatchLogInCount(scenicSpotName, date);
                    Integer countOut = sysRobotDispatchLogMapper.getRobotDispatchLogOutCount(scenicSpotName, date);
                    if (countIn > 0) {
                        count = count - countIn + countOut;
                    } else {
                        count = count + countOut - countIn;
                    }

                }
                if (robotNum != 0) {
                    count = Long.parseLong(decimalFormat.format(robotNum / 30));
                }
                Double money = 0d;

                money = mapper.getSpotAmountMoney(scenicSpotId.toString(), startDate, endDate);

                Double pjjyje = 0d;
                Double pjyysc = 0d;
                Double tb = 0d;
                Double hb = 0d;

                Double time = 0d;
                time = mapper.getSpotOperateTime(scenicSpotId.toString(), startDate, endDate);

                if (!StringUtils.isEmpty(money) && money != 0) {

                    pjjyje = money / count;

                    String lastYearCurrentDateDayS = DateUtil.getLastYearCurrentDateDay(startDate);
                    String lastYearCurrentDateDayD = DateUtil.getLastYearCurrentDateDay(endDate);
                    Double spotAmountMoney = mapper.getSpotAmountMoney(scenicSpotId.toString(), lastYearCurrentDateDayS, lastYearCurrentDateDayD);
                    if (spotAmountMoney != null) {

                        hb = (money - spotAmountMoney) / spotAmountMoney * 100;

                    }
                    String dates = DateUtil.findDates(startDate, endDate);
                    Integer day = Integer.parseInt(dates) - 2 * Integer.parseInt(dates);
                    String tbs = DateUtil.addDay(startDate, day);
                    String tbd = DateUtil.addDay(endDate, day);
                    Double spotAmountMoneytb = mapper.getSpotAmountMoney(scenicSpotId.toString(), tbs, tbd);
                    if (spotAmountMoneytb != null) {

                        tb = (money - spotAmountMoneytb) / spotAmountMoneytb * 100;

                    }
                }
                Double yysc = 0d;
                if (!StringUtils.isEmpty(time) && time != 0) {
                    yysc = time / 60;
                    pjyysc = yysc / count;


                }
                Integer onlineOrderNumber = 0;
                onlineOrderNumber = mapper.onlineOrderNumber(scenicSpotId.toString());

                Integer dates = 0;
                if (StringUtils.isEmpty(money) || money == 0) {
                    dates = Integer.parseInt(DateUtil.findDates(startDate, endDate));
                }

                hashMap.put("checkDate", startDate + "~" + endDate);
                hashMap.put("spotId", scenicSpotId);
                hashMap.put("spotName", hashMap.get("SCENIC_SPOT_NAME"));
                if (StringUtils.isEmpty(money)) {
                    hashMap.put("realAmount", 0.0);
                } else {
                    hashMap.put("realAmount", decimalFormat.format(money));
                }
                hashMap.put("tb", decimalFormat.format(tb));
                hashMap.put("hb", decimalFormat.format(hb));
                hashMap.put("avgsc", decimalFormat.format(pjjyje));
                hashMap.put("avgyysc", decimalFormat.format(pjyysc));
                hashMap.put("wuyingshou", dates);
                if (StringUtils.isEmpty(time)) {
                    hashMap.put("yysc", 0);
                } else {
                    hashMap.put("yysc", decimalFormat.format(yysc));
                }

                hashMap.put("target", null);
                hashMap.put("mbwcbl", null);
                hashMap.put("jxz", onlineOrderNumber);


            }

            Collections.sort(spotAmountCountNewTow, new Comparator<Map<String, Object>>() {

                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    double realAmount = Double.parseDouble(o1.get("realAmount").toString());
                    double realAmount1 = Double.parseDouble(o2.get("realAmount").toString());

                    Double i = realAmount1 - realAmount;

                    return i.intValue();
                }
            });


            if (!StringUtils.isEmpty(pageNum) && !StringUtils.isEmpty(pageSize)) {
                page = this.subList(spotAmountCountNewTow, pageNum, pageSize);
                return page;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return spotAmountCountNewTow;

    }

    @Override
    public List<HashMap> amountByTime(Long spotId, Integer dateType, String startDate, String endDate) {
        //dateType:如果为null查全国各自当天的总金额
        if (dateType == null) {
            return mapper.amountAll(spotId, startDate, endDate);
            //dateType==3：查每年各个景区的总金额
        }else if (dateType == 3) {
            return mapper.amountByYear(spotId, startDate, endDate);
            //dateType==2：查每个月各个景区的总金额
        } else if (dateType == 2) {
            return mapper.amountByMonth(spotId, startDate, endDate);
            //dateType==1:查每天各个景区的总金额
        } else if (dateType == 1){
            return mapper.amountByDay(spotId, startDate, endDate);
            //dateType==4:全国各个月的总
        }else if (dateType == 4){
            return mapper.amountMonthAll(spotId,startDate,endDate);
            //dateType==5:全国各年的总和
        }else {
            int i = Integer.parseInt(endDate);
            i +=1;
            endDate = String.valueOf(i);
            return mapper.amountYearAll(spotId,startDate,endDate);
        }
    }


    //subList手动分页，page为第几页，rows为每页个数
    public static List<Map<String, Object>> subList(List<Map<String, Object>> list, int page, int rows) throws Exception {
        List<Map<String, Object>> listSort = new ArrayList<>();
        int size = list.size();
//        int pageStart = page == 1 ? 0 : (page - 1) * rows;//截取的开始位置
        int pageStart = page;
//        int pageEnd = size < page * rows ? size : page * rows;//截取的结束位置
        int pageEnd = page + rows;
        if (size > pageStart) {
            listSort = list.subList(pageStart, pageEnd);
        }

        return listSort;

    }
//    //list分页
//    public List<Map<String,Object>> page(List<Map<String,Object>> dataList, int pageSize, int currentPage) {
//        List<Map<String,Object>> hashMap = new ArrayList<>();
//        if (dataList != null && dataList.size() > 0) {
//            int currIdx = (currentPage > 1 ? (currentPage - 1) * pageSize : 0);
//            for (int i = 0; i < pageSize && i < dataList.size() - currIdx; i++) {
//                Map data = dataList.get(currIdx + i);
//                hashMap.add(data);
//            }
//        }
//        return hashMap ;
//    }


}
