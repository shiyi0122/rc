package com.hna.hka.archive.management.system.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.apache.commons.lang3.time.DateUtils;

/**
 * @ClassName DateUtil
 * @Description 时间操作工具类
 * @Author 郭凯
 * @Date 2020/4/26 14:51
 * @Version 1.0
 **/
public class DateUtil {

    /**
     * 静态常量
     */
    public static final String C_TIME_PATTON_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String C_DATE_PATTON_DEFAULT = "yyyy-MM-dd";
    public static final String C_DATE_PATTON_DEFAULT_NOMIDLINE = "yyyy";
    public static final String C_DATE_PATTON_YEAR_MONTH = "yyyy-MM";
    public static final String C_DATE_PATTON_MONTH_DAY = "MM/dd";

    public static final int C_ONE_SECOND = 1000;
    public static final int C_ONE_MINUTE = 60 * C_ONE_SECOND;
    public static final long C_ONE_HOUR = 60 * C_ONE_MINUTE;
    public static final long C_ONE_DAY = 24 * C_ONE_HOUR;

    public static final String DAYBEGIN = "00:00:00";
    public static final String DAYEND = "23:59:59";

    public static String currentDateTime(){
        SimpleDateFormat formatter = new SimpleDateFormat(C_TIME_PATTON_DEFAULT);
        return formatter.format(crutDateTime());
    }

    public static Date crutDateTime(){
        Date curtDate = new Date(System.currentTimeMillis());
        return curtDate;
    }

    public static String crutDate(){
        SimpleDateFormat formatter = new SimpleDateFormat(C_DATE_PATTON_DEFAULT);
        return formatter.format(crutDateTime());
    }

    /**
     * @Author 郭凯
     * @Description 得到分钟
     * @Date 14:30 2020/6/23
     * @Param
     * @return
    **/
    public static long timeConversionMoney(String dateTime) throws Exception{
        Date parse = DateUtil.StringToDateTime(dateTime);
        Date d = new Date(System.currentTimeMillis());
        long time1 = (d.getTime() - parse.getTime());
        long times =  time1 / 1000 / 60;
        return times;
    }

    /**
     * 将String类型的日期时间转换成java.util.Date类型的数据
     * @param dateTime
     * @return
     * @throws ParseException
     */
    public static Date StringToDateTime(String dateTime) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date bdate = formatter.parse(dateTime);
        return bdate;
    }

    /**
     * 判断是工作日还是周末
     * @return
     */
    public static boolean getWeekend() {
        Calendar cal=Calendar.getInstance();
        cal.setTime(new Date());
        if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
            return true;//为周六、周日
        }else {
            return false;//为周一至周五
        }
    }

    /**
     * 订单生成随机时间
     * @return
     */
    public static String crutDatess(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        return formatter.format(date);
    }

    public static Date localDate2Date(LocalDate localDate) {
        if(null == localDate) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * 根据开始时间和结束时间得到使用时长
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public static long timeConversion(String startTime,String endTime) throws ParseException {
        Date parse = DateUtil.StringToDateTime(startTime);
        Date d = DateUtil.StringToDateTime(endTime);
        long time1 = (d.getTime() - parse.getTime());
        long times =  time1 / 1000 / 60;
        return times;
    }
    
    /**
     * 
    * @Author 郭凯
    * @Description: 根据当前时间和结束时间得到剩余天数
    * @Title: caculateTotalTime
    * @date  2020年12月24日 上午9:26:26 
    * @param @param startTime
    * @param @param endTime
    * @param @return
    * @param @throws ParseException
    * @return int
    * @throws
     */
    public static int caculateTotalTime(String startTime,String endTime) throws ParseException{
		SimpleDateFormat formatter =   new SimpleDateFormat( "yyyy-MM-dd" );
		Date date1=null;
		Date date = formatter.parse(startTime);
	    long ts = date.getTime();
		date1 =  parseDate(endTime);
		long ts1 = date1.getTime();
		long ts2=ts1-ts;
		int totalTime = 0;
		totalTime=(int) (ts2/(24*3600*1000)+1);		
		return totalTime;	
	}
    
    private static String[] parsePatterns = {"yyyy-MM-dd","yyyy年MM月dd日",
	        "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd",
	        "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyyMMdd"
	        };
    public static Date parseDate(String endTime) {
        if (endTime == null) {
            return null;
        }
        try {
            return DateUtils.parseDate(endTime, parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }
    
    /**
     * java转成指定日期格式的字符串
     * @param str
     * @return
     * @throws ParseException
     */
    public static String checkDate(String str){
        SimpleDateFormat format = new SimpleDateFormat(C_DATE_PATTON_DEFAULT);
        Date date = new Date(str);
        
        return format.format(date);
    }

    /**
     * @Method
     * @Author 郭凯
     * @Version  1.0
     * @Description java转换时间格式转换成年月
     * @Return
     * @Date 2021/6/18 15:38
     */
    public static String getYearsDate(String str) throws ParseException {
        DateFormat format = new SimpleDateFormat(C_DATE_PATTON_YEAR_MONTH);
        Date date = format.parse(str);

        return format.format(date);
    }

    /**
     * @Method
     * @Author 郭凯
     * @Version  1.0
     * @Description java转换时间格式转换成年
     * @Return
     * @Date 2021/6/18 15:38
     */
    public static String getYearDate(String str) throws ParseException {
        DateFormat format = new SimpleDateFormat(C_DATE_PATTON_DEFAULT_NOMIDLINE);
        Date date = format.parse(str);

        return format.format(date);
    }

    /**
    * @Author 郭凯
    * @Description: 判断String是不是时间格式
    * @Title: isValidDate
    * @date  2021年1月7日 下午2:49:02 
    * @param @param str
    * @param @return
    * @return boolean
    * @throws
     */
    public static boolean isValidDate(String str) {
        boolean convertSuccess=true;
         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
         try {
            format.setLenient(false);
            format.parse(str);
         } catch (ParseException e) {
            // e.printStackTrace();
             convertSuccess=false;
         }
         return convertSuccess;
  }

	/**
	 * @Method
	 * @Author 郭凯
	 * @Version  1.0
	 * @Description 根据时分获取间隔分钟
	 * @Return
	 * @Date 2021/6/19 15:46
	 */
    public static Long getMinutes(String startTime,String endTime) throws ParseException {
        if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)){
            return null;
        }
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long diff;
        //获得两个时间的毫秒时间差异
        diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
        long times =  diff / 1000 / 60;
        return times;
    }

    private static Date getDateAdd(int days){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -days);
        return c.getTime();
    }

    /**
     * 根据当前时间和传进来的数值获取日期(正数为当前日期减正数，负数为当前日期减负数)
     * @param i
     * @return
     */
    public static String getLast12Months(int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -i);
        Date m = c.getTime();
        return sdf.format(m);
    }

    /**
     * @Method
     * @Author 郭凯
     * @Version  1.0
     * @Description 获取具体日期
     * @Return
     * @Date 2021/6/22 18:35
     */
    private static List<String> getDaysBetwwen(int days){ //最近几天日期
        List<String> dayss = new ArrayList<>();
        Calendar start = Calendar.getInstance();
        start.setTime(getDateAdd(days));
        Long startTIme = start.getTimeInMillis();
        Calendar end = Calendar.getInstance();
        end.setTime(new Date());
        Long endTime = end.getTimeInMillis();
        Long oneDay = 1000 * 60 * 60 * 24l;
        Long time = startTIme;
        while (time <= endTime) {
            Date d = new Date(time);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            dayss.add(df.format(d));
            time += oneDay;
        }
        return dayss;
    }

    /**
     * @Method
     * @Author 郭凯
     * @Version  1.0
     * @Description 获取具体月份
     * @Return
     * @Date 2021/6/22 18:57
     */
    public static List<String> getSixMonth(String date,int a) {
        //返回值
        List<String> list = new ArrayList<String>();
        int month = Integer.parseInt(date.substring(5, 7));
        int year = Integer.parseInt(date.substring(0, 4));
        for (int i = a; i >= 0; i--) {
            if (month > a) {
                if (month - i >= 10) {
                    list.add(year + "-" + String.valueOf(month - i));
                } else {
                    list.add(year + "-0" + String.valueOf(month - i));
                }
            } else {
                if (month - i <= 0) {
                    if (month - i + 12 >= 10) {
                        list.add(String.valueOf(year - 1) + "-" + String.valueOf(month - i + 12));
                    } else {
                        list.add(String.valueOf(year - 1) + "-0" + String.valueOf(month - i + 12));
                    }
                } else {
                    if (month - i >= 10) {
                        list.add(String.valueOf(year) + "-" + String.valueOf(month - i));
                    } else {
                        list.add(String.valueOf(year) + "-0" + String.valueOf(month - i));
                    }
                }
            }
        }
        return list;

    }

    /**
     * @Method
     * @Author 郭凯
     * @Version  1.0
     * @Description 获取间隔月、天
     * @Return
     * @Date 2021/6/22 19:20
     */
    public static int getDate(String[] data,String type){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate data1 = LocalDate.parse(data[1], fmt);
        LocalDate data2 = LocalDate.parse(data[0], fmt);
        Period a = Period.between(data2,data1);
        if ("1".equals(type)){
            int time = a.getMonths();
            if (time == 0){
                time = time + 1;
            }
            return time;
        }else{
            return a.getDays();
        }
    }

    /**
     * @Method
     * @Author 郭凯
     * @Version  1.0
     * @Description 获取月份
     * @Return
     * @Date 2021/6/23 10:55
     */
    public static String getMonth(String time,int number) throws ParseException {

        String[] item = time.split("-");

        int year = Integer.parseInt(item[0]);

        int month = Integer.parseInt(item[1]);

        if((month - number) <= 0){
            month = month + 12 - number;

            year = year -1;

        }else {
            month = month - number;

        }
        String time1 = year + "-" + month;
        Date date = new SimpleDateFormat("yyyy-MM").parse(time1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(date);
    }

    /**
     * @Method
     * @Author 郭凯
     * @Version  1.0
     * @Description 获取同期日期
     * @Return 
     * @Date 2021/6/23 14:02
     */
    public static String getLastYearCurrentDate(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date =sdf.parse(str);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -1);
        return sdf.format(calendar.getTime());
    }

    /**
     * @Method
     * @Author 郭凯
     * @Version  1.0
     * @Description 获取同期日期(天)
     * @Return
     * @Date 2021/6/23 14:02
     */
    public static String getLastYearCurrentDateDay(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date =sdf.parse(str);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -1);
        return sdf.format(calendar.getTime());
    }

    /**
     * @Method
     * @Author 郭凯
     * @Version  1.0
     * @Description 获取同期年
     * @Return
     * @Date 2021/6/23 14:02
     */
    public static String getLastYearCurrentDateYear(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date =sdf.parse(str);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -1);
        return sdf.format(calendar.getTime());
    }

    /**
     * @Method
     * @Author 郭凯
     * @Version  1.0
     * @Description
     * @Return 获取昨天日期
     * @Date 2021/7/4 15:15
     */
    public static String calcYesterday(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
        Date date1 = simpleDateFormat.parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        cal.add(Calendar.DATE, -1);
        Date date2 = cal.getTime();
        SimpleDateFormat format2= new SimpleDateFormat("yyyy-MM-dd");
        String dateYesterday = format2.format(date2);
        return dateYesterday;
    }

    /**
     * @Method
     * @Author 郭凯
     * @Version  1.0
     * @Description
     * @Return 获取上个月月份
     * @Date 2021/7/4 15:21
     */
    public static String calcLastMonth(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");//注意月份是MM
        Date date1 = simpleDateFormat.parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        cal.add(Calendar.MONTH, -1);
        Date date3 = cal.getTime();
        SimpleDateFormat format3= new SimpleDateFormat("yyyy-MM");
        String dateLastMonth = format3.format(date3);
        return dateLastMonth;
    }

    /**
     * @Method
     * @Author 郭凯
     * @Version  1.0
     * @Description 获取上年年份
     * @Return
     * @Date 2021/7/4 16:48
     */
    public static String calcLastYear(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        Date date1 = simpleDateFormat.parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        cal.add(Calendar.YEAR, -1);
        Date date3 = cal.getTime();
        SimpleDateFormat format3= new SimpleDateFormat("yyyy");
        String dateLastMonth = format3.format(date3);
        return dateLastMonth;
    }

    public static long dateDiff(String startTime, String endTime) throws Exception {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long nd = 1000*24*60*60;//一天的毫秒数
        long nh = 1000*60*60;//一小时的毫秒数
        long nm = 1000*60;//一分钟的毫秒数
        long ns = 1000;//一秒钟的毫秒数
        long diff;
        //获得两个时间的毫秒时间差异
        diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
        long day = diff/nd;//计算差多少天
        long hour = diff%nd/nh;//计算差多少小时
        long min = diff%nd%nh/nm;//计算差多少分钟
        long sec = diff%nd%nh%nm/ns;//计算差多少秒//输出结果
        System.out.println("时间相差："+day+"天"+hour+"小时"+min+"分钟"+sec+"秒。");
        return min ;
    }

    /**
     * @Method
     * @Author 郭凯
     * @Version  1.0
     * @Description 根据日期计算出间隔天数
     * @Return
     * @Date 2021/7/16 13:11
     */
    public static String findDates(String stime, String etime) throws ParseException {
        List<String> allDate = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date dBegin = sdf.parse(stime);
        Date dEnd = sdf.parse(etime);
        allDate.add(sdf.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            allDate.add(sdf.format(calBegin.getTime()));
        }
        return String.valueOf(allDate.size());
    }

    /**
     * @Method
     * @Author 郭凯
     * @Version  1.0
     * @Description 根据日期计算出间隔月数
     * @Return
     * @Date 2021/7/21 9:40
     */
    public static String findMonths(String stime, String etime) throws ParseException {
        List<String> allDate = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        Date dBegin = sdf.parse(stime);
        Date dEnd = sdf.parse(etime);
        allDate.add(sdf.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.MONTH, 1);
            allDate.add(sdf.format(calBegin.getTime()));
        }
        return String.valueOf(allDate.size());
    }

    /**
     * @Method
     * @Author 郭凯
     * @Version  1.0
     * @Description 根据日期计算出间隔月数
     * @Return
     * @Date 2021/7/21 9:43
     */
    public static String findYears(String stime, String etime) throws ParseException {
        List<String> allDate = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

        Date dBegin = sdf.parse(stime);
        Date dEnd = sdf.parse(etime);
        allDate.add(sdf.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.YEAR, 1);
            allDate.add(sdf.format(calBegin.getTime()));
        }
        return String.valueOf(allDate.size());
    }

    /**
     * @Method
     * @Author 郭凯
     * @Version  1.0
     * @Description 根据日期计算出间隔天数
     * @Return
     * @Date 2021/7/16 13:11
     */
    public static List<String> betweenDays(String stime, String etime) throws ParseException {
        List<String> allDate = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date dBegin = sdf.parse(stime);
        Date dEnd = sdf.parse(etime);
        allDate.add(sdf.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            allDate.add(sdf.format(calBegin.getTime()));
        }
        return allDate;
    }

    /**
     * @Method
     * @Author 郭凯
     * @Version  1.0
     * @Description 根据日期计算出间隔月数
     * @Return
     * @Date 2021/7/21 9:40
     */
    public static List<String> betweenMonths(String stime, String etime) throws ParseException {
        List<String> allDate = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        Date dBegin = sdf.parse(stime);
        Date dEnd = sdf.parse(etime);
        allDate.add(sdf.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.MONTH, 1);
            allDate.add(sdf.format(calBegin.getTime()));
        }
        return allDate;
    }

    /**
     * @Method
     * @Author 郭凯
     * @Version  1.0
     * @Description 根据日期计算出间隔月数
     * @Return
     * @Date 2021/7/21 9:43
     * @return
     */
    public static List<String> betweenYears(String stime, String etime) throws ParseException {
        List<String> allDate = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

        Date dBegin = sdf.parse(stime);
        Date dEnd = sdf.parse(etime);
        allDate.add(sdf.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.YEAR, 1);
            allDate.add(sdf.format(calBegin.getTime()));
        }
        return allDate;
    }

    //获取一天中的小时段
    public static   List<String>  dateHoursList(Date date) throws ParseException {
        List<String> list = new ArrayList<>();

        /*设置日期格式*/
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        /*将日期转为字符串*/
        String today = df.format(date);
//        String today = date;
        /*将字符串转为Date类型*/
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        /*从当前日期的00:00开始*/
        Date parse = simpleDateFormat.parse(today + " 00:00:00");

        /*输出开始日期时间*/
        String format = simpleDateFormat.format(parse);
        System.out.println(format);
        list.add(format);
        /*循环47次(24*2-1获得每半个小时的时间    第一天00:00到第二天00:00)*/
        for (int y = 0; y < 23; y++) {

            if (y==23){
                /*将指定时间依次加30分钟,循环到23:30为止(以此类推,如果是获取按小时划分, +60分钟)*/
                Calendar c = Calendar.getInstance();
                /*设置指定时间*/
                c.setTime(parse);
                /*将设置的时间加上60分钟*/
                c.add(Calendar.MINUTE, 59);
                c.add(Calendar.SECOND,59);
                /*获得加上60分钟后的时间*/
                Date dateTime = c.getTime();
                /*将新时间赋值后再循环*/
                parse = dateTime;
                /*将date转为字符串*/
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format2 = sdf.format(dateTime);
                list.add(format2);
            }else{
                /*将指定时间依次加30分钟,循环到23:30为止(以此类推,如果是获取按小时划分, +60分钟)*/
                Calendar c = Calendar.getInstance();
                /*设置指定时间*/
                c.setTime(parse);
                /*将设置的时间加上60分钟*/
                c.add(Calendar.HOUR, 1);
                /*获得加上60分钟后的时间*/
                Date dateTime = c.getTime();
                /*将新时间赋值后再循环*/
                parse = dateTime;
                /*将date转为字符串*/
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format2 = sdf.format(dateTime);
                list.add(format2);
            }




//            System.out.println(format2);
        }
        return list;
    }


    /**
     * 给时间增加相应的月数
     * @param time 时间
     * @param i 增加的月数
     * @return
     */
    public static String addMouth(String time,int i){
        String s = "";
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化一下
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(time));
            calendar.add(Calendar.MONTH, i);
            s = sdf.format(calendar.getTime());

        }catch (Exception e){

            e.printStackTrace();
        }

        return s;
    }

    /**
     * 给时间增加相应的年
     * @param time 时间
     * @param i 增加的月数
     * @return
     */
    public static String addYear(String time,int i){
        String s = "";
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");//格式化一下
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(time));
            calendar.add(Calendar.YEAR, i);
            s = sdf.format(calendar.getTime());

        }catch (Exception e){

            e.printStackTrace();
        }

        return s;
    }


    /**
     * 给时间增加相应的天数
     * @param time 时间
     * @param i 增加的月数
     * @return
     */
    public static String addDay(String time,int i){
        String s = "";
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化一下
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(time));
            calendar.add(Calendar.DATE, i);
            s = sdf.format(calendar.getTime());

        }catch (Exception e){

            e.printStackTrace();
        }

        return s;
    }






    /**
     * 判断时间是否在这个时间段内
     * @param startValidity
     * @param endValidity
     * @return
     */
    public static boolean isEffectiveDates(String startValidity, String endValidity) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date nowTime = new Date();
        Date startDate = new Date();
        Date endDate = new Date();
        try {
            startDate = formatter.parse(startValidity);
            endDate = formatter.parse(endValidity);

        }catch (Exception e){
            e.printStackTrace();
        }



        if (nowTime.getTime() == startDate.getTime()
                || nowTime.getTime() == endDate.getTime()) {
            return true;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startDate);

        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        return date.after(begin) && date.before(end);

    }


    /**
     * 获取指定月份天数
     * @param
     * @param
     * @return
     */
    public static int getMonthDay(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

    }


    /**
     * 两个时间获取相差多少小时分钟
     * @param
     * @return
     */
    public static String getMonthHourMonth(String month) {


        int floor = 0;
        int fen = 0;
        String result = null;
        try {

            int hours = (int) Math.floor(Long.parseLong(month) / 60);
            int minute = Integer.parseInt(month) % 60;
//            System.err.println("两个时间差"+hours + "小时" + minute + "分钟");
            if(hours == 0){
                result =  minute + "分钟";
            }else{
                result = hours + "小时" + minute + "分钟";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 两个时间获取相差多少小时分钟
     * @param
     * @return
     */
    public static String getTimeHourMonth(String beginTime,String endTime) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginTimeDate = new Date();
        Date endTimeDate = new Date();
        int floor = 0;
        int fen = 0;
        String result = null;
        try {
            beginTimeDate = simpleDateFormat.parse(beginTime);
            endTimeDate = simpleDateFormat.parse(endTime);
            long begin = beginTimeDate.getTime();
            long end = endTimeDate.getTime();
            int day = (int) ((end - begin) / (1000 * 60 * 60 * 24));//两个时间天数差
            int hour = (int) ((end - begin) / (1000 * 60 * 60));//两个时间小时差
            int minute = (int) ((end - begin) / (1000 * 60));//两个时间分钟差
            floor = (int) Math.floor(minute / 60);//分钟计算小时
            fen = minute % 60;//分钟计算
            if (floor == 0 && fen == 0){
                result = 0 + "分钟";
            }else if (floor == 0 && fen != 0){
                result =  fen + "分钟";
            }else{
                result = floor + "小时" + fen + "分钟";
            }
//            System.err.println("两个时间差"+floor + "小时" + fen + "分钟");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }






    public static void main(String[] args) throws ParseException {
//        System.out.println(getMonthDay("2021-07"));
//        System.out.println(calcLastMonth("2021-12"));
//        System.out.println(betweenYears("2021","2021"));
//        System.out.println(dateHoursList(new Date()));
//        System.out.println(addYear("2021",1));
//        System.out.println(addMouth("2023-01",-1));
//        System.out.println(betweenMonths("2022-10","2022-10"));
//        System.out.println(getLast12Months(1));
//        System.out.println(getMonth("2022-10",1));
//        System.out.println(findMonths("2022-05","2022-05"));
//          System.out.println(DateUtil.crutDate());
//        System.out.println(timeConversion("2022-10-01 20:00:00","2022-10-01 20:00:20"));
        System.out.println(getMonthHourMonth("60"));
        //        int i = cn.hutool.core.date.DateUtil.thisHour(true);
//        System.out.println(i);
    }


}
