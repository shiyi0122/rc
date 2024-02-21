package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.hna.hka.archive.management.assetsSystem.dao.OperationalAnalysisMapper;
import com.hna.hka.archive.management.assetsSystem.model.ScenicSpot;
import com.hna.hka.archive.management.assetsSystem.service.OperationalAnalysisService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-27 10:43
 **/
@Service
public class OperationalAnalysisServiceImpl implements OperationalAnalysisService {


    @Autowired
    OperationalAnalysisMapper mapper;

    @Override
    public HashMap<String, ArrayList> timeAnalysis(Integer type, String beginDate, String endDate) throws ParseException {
        List<HashMap<String, Object>> timeSort = mapper.timeSort(type, beginDate, endDate , 1);

        final BigDecimal[] total = {new BigDecimal(0)};

        timeSort.forEach(map -> {
            List<HashMap<String, Object>> spotId = mapper.timeList(type, beginDate, endDate, map.get("spotId") , 1);
            spotId.forEach(objectHashMap -> {
                map.put((String) objectHashMap.get("checkDate"), objectHashMap.get("totalTime") != null ?((BigDecimal) objectHashMap.get("totalTime")).divide(new BigDecimal(60), 2, 4) : null);
            });
            if (map.get("totalTime") != null) {
                total[0] = total[0].add((BigDecimal) map.get("totalTime"));
            }
        });

        HashMap<String, ArrayList> map = new LinkedHashMap<>();
        map.put("spotName", new ArrayList());

        if (type == 1) {
            List<String> list = DateUtil.betweenMonths(beginDate, endDate);
            for (String s : list) {
                map.put(s, new ArrayList());
            }
        } else if (type == 2) {
            List<String> list = DateUtil.betweenYears(beginDate, endDate);
            for (String s : list) {
                map.put(s, new ArrayList());
            }
        }

        timeSort.forEach(stringObjectHashMap ->
                map.forEach((key, value) ->
                        value.add(stringObjectHashMap.get(key))
                ));

        if (timeSort != null && timeSort.size() != 0) {
            StringBuilder builder = new StringBuilder();
            builder.append("总运营时长为" + total[0].divide(new BigDecimal(60), 2, 4) + "小时");
            List<HashMap> list = mapper.fitList();
            list.forEach(value -> {
                Integer begin = (Integer) value.get("BEGIN");
                Integer end = (Integer) value.get("END");
                BigDecimal totalTime = new BigDecimal(0D);
                for (int i = (begin == null ? 0 : (begin - 1)); i < (end == null ? timeSort.size() : end); i++) {
                    if (timeSort.get(i).get("totalTime") != null) {
                        totalTime = totalTime.add((BigDecimal) timeSort.get(i).get("totalTime"));
                    }
                }
                if (begin != null && end != null) {
                    builder.append(",前" + begin + "-" + end + "名占比");
                } else if (begin != null && end == null) {
                    builder.append("," + begin + "名之后占比");
                } else if (begin == null && end != null) {
                    builder.append(",前1-" + end + "名占比");
                } else {
                    builder.append(",总体占比");
                }
                builder.append(totalTime.multiply(new BigDecimal(100)).divide(total[0], 2, 4) + "%");
            });
            map.put("message", new ArrayList() {{
                add(builder.toString());
            }});
        }
        return map;
    }

    @Override
    public List fitList() {
        return mapper.fitList();
    }

    @Override
    public Integer fitAdd(Integer begin, Integer end) {
        return mapper.fitAdd(IdUtils.getSeqId(), begin, end);
    }

    @Override
    public Integer fitEdit(Long id, Integer begin, Integer end) {
        return mapper.fitEdit(id, begin, end);
    }

    @Override
    public Integer fitDelete(Long id) {
        return mapper.fitDelete(id);
    }

    @Override
    public HashMap<String, ArrayList> amountAnalysis(Integer type, String beginDate, String endDate) throws ParseException {
        List<HashMap<String, Object>> timeSort = mapper.timeSort(type, beginDate, endDate , 2);

        final BigDecimal[] total = {new BigDecimal(0)};

        timeSort.forEach(map -> {
            List<HashMap<String, Object>> spotId = mapper.timeList(type, beginDate, endDate, map.get("spotId") , 2);
            spotId.forEach(objectHashMap -> {
                map.put((String) objectHashMap.get("checkDate"), objectHashMap.get("totalTime"));
            });
            if (map.get("totalTime") != null) {
                total[0] = total[0].add((BigDecimal) map.get("totalTime"));
            }
        });

        HashMap<String, ArrayList> map = new LinkedHashMap<>();
        map.put("spotName", new ArrayList());

        if (type == 1) {
            List<String> list = DateUtil.betweenMonths(beginDate, endDate);
            for (String s : list) {
                map.put(s, new ArrayList());
            }
        } else if (type == 2) {
            List<String> list = DateUtil.betweenYears(beginDate, endDate);
            for (String s : list) {
                map.put(s, new ArrayList());
            }
        }

        timeSort.forEach(stringObjectHashMap ->
                map.forEach((key, value) ->
                        value.add(stringObjectHashMap.get(key))
                ));

        if (timeSort != null && timeSort.size() != 0) {
            StringBuilder builder = new StringBuilder();
            builder.append("总运营流水为" + total[0] + "元");
            List<HashMap> list = mapper.fitList();
            list.forEach(value -> {
                Integer begin = (Integer) value.get("BEGIN");
                Integer end = (Integer) value.get("END");
                BigDecimal totalTime = new BigDecimal(0D);
                for (int i = (begin == null ? 0 : (begin - 1)); i < (end == null ? timeSort.size() : end); i++) {
                    if (timeSort.get(i).get("totalTime") != null) {
                        totalTime = totalTime.add((BigDecimal) timeSort.get(i).get("totalTime"));
                    }
                }
                if (begin != null && end != null) {
                    builder.append(",前" + begin + "-" + end + "名占比");
                } else if (begin != null && end == null) {
                    builder.append("," + begin + "名之后占比");
                } else if (begin == null && end != null) {
                    builder.append(",前1-" + end + "名占比");
                } else {
                    builder.append(",总体占比");
                }
                builder.append(totalTime.multiply(new BigDecimal(100)).divide(total[0], 2, 4) + "%");
            });
            map.put("message", new ArrayList() {{
                add(builder.toString());
            }});
        }
        return map;
    }


    @Override
    public  HashMap<String, List> fitRevenue(Integer type, String beginDate, String endDate,String spotId,String provinceId) throws ParseException {

        if ("all".equals(spotId)){
            spotId = "";
        }

        if ("all".equals(provinceId)){
            provinceId = "";
        }


        if (type == 1) {
            beginDate = beginDate+"-01";
            endDate = endDate+"-12";
        }else if (type == 3){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Calendar calendar = new GregorianCalendar();
            Date date1 = sdf.parse(beginDate);
            try {
                calendar.setTime(date1); //放入你的日期
            } catch (Exception e) {
                e.printStackTrace();
            }
            Integer actualMaximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            beginDate =beginDate+"-01";
            endDate=endDate+"-"+actualMaximum.toString();
        }

        List<HashMap<String, Object>> timeSort = mapper.fitRevenue(type, beginDate, endDate , 2,spotId,provinceId);


        List<Object> realList = new  ArrayList<>();
        timeSort.forEach(map -> {
//            List<HashMap<String, Object>> spotIdList = mapper.timeList(type, beginDate, endDate, map.get("spotId") , 2);
            if (map.get("realAmout") != null) {
                Object realAmout = map.get("realAmout");
                realList.add(realAmout);
            }
        });
//
        HashMap<String, List> map = new LinkedHashMap<>();
//
        if (type == 1) {
//            beginDate = beginDate+"-01";
//            endDate = endDate+"-12";
            List<String> list = DateUtil.betweenMonths(beginDate, endDate);
//            for (String s : list) {
//                map.put(s, new ArrayList());
//            }
            map.put("dateTime",list);
        } else if (type == 3){
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
//            Calendar calendar = new GregorianCalendar();
//            Date date1 = sdf.parse(beginDate);
//            try {
//                calendar.setTime(date1); //放入你的日期
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            Integer actualMaximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//
//            String beginDateNew =beginDate+"-01";
//            String endDateNew=endDate+"-"+actualMaximum.toString();
            List<String> list = DateUtil.betweenDays(beginDate, endDate);
            map.put("dateTime",list);
        }
            map.put("realAmout",timeSort);
//        timeSort.forEach(stringObjectHashMap ->
//                map.forEach((key, value) ->
//                        value.add(stringObjectHashMap.get(key))
//                ));
            map.put("real",realList);
        return map;
    }

    /**
     * 获取全部景区列表
     * @return
     */
    @Override
    public  List<ScenicSpot>  spotList(String provinceId) {
        Long aLong;
        if ("all".equals(provinceId)){
            aLong = null;
        }else {
            aLong = Long.valueOf(provinceId);
        }

       List<ScenicSpot>  map = mapper.spotList(aLong);

       return map;
    }

    /**
     * 获取省份列表
     * @return
     */
    @Override
    public List<ScenicSpot> provinceList() {

        List<ScenicSpot> list = mapper.provinceList();

        return list;
    }
}
