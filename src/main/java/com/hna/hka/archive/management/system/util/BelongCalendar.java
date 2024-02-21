package com.hna.hka.archive.management.system.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.util
 * @ClassName: BelongCalendar
 * @Author: 郭凯
 * @Description: 判断是否有效工具类
 * @Date: 2020/10/15 10:02
 * @Version: 1.0
 */
public class BelongCalendar {

    /**
     * @Author 郭凯
     * @Description 判断时间是否在时间段内
     * @Date 10:02 2020/10/15
     * @Param
     * @return
    **/
    public static boolean  belongCalendar(Date nowTime, Date beginTime,
                                         Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        date.add(Calendar.SECOND,1);
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        end.add(Calendar.HOUR,23);
        end.add(Calendar.MINUTE,59);
        end.add(Calendar.SECOND,59);
        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
        try {
            String format = df.format(new Date());
            Date  now = df.parse(format);
            Date beginTime = df.parse("2023-01-03");
            Date endTime = df.parse("2023-01-03");
            boolean b = belongCalendar(now, beginTime, endTime);
            System.out.println(b);
        }    catch (ParseException parseException) {
            parseException.printStackTrace();
        }




    }

}
