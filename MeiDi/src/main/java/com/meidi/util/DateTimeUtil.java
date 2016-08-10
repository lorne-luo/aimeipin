package com.meidi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 对日期进行格式化处理的类，由于java的SimpleDateFormat对null不进行处理，所以对其进行封装 使用静态函数
 */
public class DateTimeUtil {

    // 缺省的格式
    public static String shortintDateFormatText = "yyyyMMdd";
    public static String shortDateFormatText = "yyyy-MM-dd";
    private static String longDateFormatText = "yyyy年MM月dd日";
    private static String shortDateTimeFormatText = "yyyy-MM-dd HH:mm:ss";
    private static String longDateTimeFormatText = "yyyy年MM月dd日 HH时mm分ss秒";
    private static String shortTimeToMinuteFormatText = "HH:mm";
    private static String shortTimeToSecondFormatText = "HH:mm:ss";
    private static String longTimeToMinuteFormatText = "HH时mm分";
    private static String longTimeToSecondFormatText = "HH时mm分ss秒";
    private static String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    public static String getShortintDateFormatText() {
        return shortintDateFormatText;
    }

    public static String getShortDateFormatText() {
        return shortDateFormatText;
    }

    public static String getLongDateFormatText() {
        return longDateFormatText;
    }

    public static String getShortDateTimeFormatText() {
        return shortDateTimeFormatText;
    }

    public static String getLongDateTimeFormatText() {
        return longDateTimeFormatText;
    }

    public static String getShortTimeToMinuteFormatText() {
        return shortTimeToMinuteFormatText;
    }

    public static String getShortTimeToSecondFormatText() {
        return shortTimeToSecondFormatText;
    }

    public static String getLongTimeToMinuteFormatText() {
        return longTimeToMinuteFormatText;
    }

    public static String getLongTimeToSecondFormatText() {
        return longTimeToSecondFormatText;
    }

    private static SimpleDateFormat getShortintDateSDF() {
        return new SimpleDateFormat(shortintDateFormatText);
    }

    private static SimpleDateFormat getShortDateSDF() {
        return new SimpleDateFormat(shortDateFormatText);
    }

    private static SimpleDateFormat getLongDateSDF() {
        return new SimpleDateFormat(longDateFormatText);
    }

    private static SimpleDateFormat getShortDateTimeSDF() {
        return new SimpleDateFormat(shortDateTimeFormatText);
    }

    private static SimpleDateFormat getLongDateTimeSDF() {
        return new SimpleDateFormat(longDateTimeFormatText);
    }

    private static SimpleDateFormat getShortTimeToMinuteSDF() {
        return new SimpleDateFormat(shortTimeToMinuteFormatText);
    }

    private static SimpleDateFormat getShortTimeToSecondSDF() {
        return new SimpleDateFormat(shortTimeToSecondFormatText);
    }

    private static SimpleDateFormat getLongTimeToMinuteSDF() {
        return new SimpleDateFormat(longTimeToMinuteFormatText);
    }

    private static SimpleDateFormat getLongTimeToSecondSDF() {
        return new SimpleDateFormat(longTimeToSecondFormatText);
    }

    private static SimpleDateFormat getSDF(String formatText) {
        return new SimpleDateFormat(formatText);
    }

    public static Date getShortDate(String dateString) {
        if (dateString == null || dateString.length() == 0) {
            return null;
        } else {
            try {
                return getShortDateSDF().parse(dateString);
            } catch (ParseException e) {
                return null;
            }
        }
    }

    public static Date getLongDate(String dateString) {

        if (dateString == null || dateString.length() == 0) {
            return null;
        } else {
            try {
                return getLongDateSDF().parse(dateString);
            } catch (ParseException e) {
                return null;
            }
        }
    }

    public static Date getShortDateTime(String dateString) {

        if (dateString == null || dateString.length() == 0) {
            return null;
        } else {
            try {
                return getShortDateTimeSDF().parse(dateString);
            } catch (ParseException e) {
                return null;
            }
        }
    }

    public static Date getLongDateTime(String dateString) {

        if (dateString == null || dateString.length() == 0) {
            return null;
        } else {
            try {
                return getLongDateTimeSDF().parse(dateString);
            } catch (ParseException e) {
                return null;
            }
        }
    }

    public static Date getShortTimeToMinute(String dateString) {

        if (dateString == null || dateString.length() == 0) {
            return null;
        } else {
            try {
                return getShortTimeToMinuteSDF().parse(dateString);
            } catch (ParseException e) {
                return null;
            }
        }
    }

    public static Date getShortTimeToSecond(String dateString) {

        if (dateString == null || dateString.length() == 0) {
            return null;
        } else {
            try {
                return getShortTimeToSecondSDF().parse(dateString);
            } catch (ParseException e) {
                return null;
            }
        }
    }

    public static Date getLongTimeToMinute(String dateString) {

        if (dateString == null || dateString.length() == 0) {
            return null;
        } else {
            try {
                return getLongTimeToMinuteSDF().parse(dateString);
            } catch (ParseException e) {
                return null;
            }
        }
    }

    public static Date getLongTimeToSecond(String dateString) {

        if (dateString == null || dateString.length() == 0) {
            return null;
        } else {
            try {
                return getLongTimeToSecondSDF().parse(dateString);
            } catch (ParseException e) {
                return null;
            }
        }
    }

    public static Date getDate(String dateString, String formatText) {

        if (dateString == null || dateString.length() == 0) {
            return null;
        } else {
            try {
                return getSDF(formatText).parse(dateString);
            } catch (ParseException e) {
                return null;
            }
        }
    }

    public static String getShortintDateString(Date date) {

        if (date == null) {
            return "";
        } else {
            return getShortintDateSDF().format(date);
        }
    }

    public static String getLongDateString(Date date) {

        if (date == null) {
            return "";
        } else {
            return getLongDateSDF().format(date);
        }
    }


    public static String getShortDateString(Date date) {

        if (date == null) {
            return "";
        } else {
            return getShortDateSDF().format(date);
        }
    }

    public static String getShortDateTimeString(Date date) {

        if (date == null) {
            return "";
        } else {
            return getShortDateTimeSDF().format(date);
        }
    }

    public static String getLongDateTimeString(Date date) {

        if (date == null) {
            return "";
        } else {
            return getLongDateTimeSDF().format(date);
        }
    }

    public static String getShortTimeToMinuteString(Date date) {

        if (date == null) {
            return "";
        } else {
            return getShortTimeToMinuteSDF().format(date);
        }
    }

    public static String getShortTimeToSecondString(Date date) {

        if (date == null) {
            return "";
        } else {
            return getShortTimeToSecondSDF().format(date);
        }
    }

    public static String getLongTimeToMinuteString(Date date) {

        if (date == null) {
            return "";
        } else {
            return getLongTimeToMinuteSDF().format(date);
        }
    }

    public static String getLongTimeToSecondString(Date date) {

        if (date == null) {
            return "";
        } else {
            return getLongTimeToSecondSDF().format(date);
        }
    }

    public static String getString(Date date, String formatText) {

        if (date == null) {
            return "";
        } else {
            return getSDF(formatText).format(date);
        }
    }

    public static String getWeekdayString(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        return dayNames[day - 1];
    }

    public static boolean DatePartEquals(Calendar calendar1, Calendar calendar2) {

        return (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) && calendar1.get(Calendar.DATE) == calendar2.get(Calendar.DATE));
    }

    public static String dateTimeToShortDate(String dateTime) {
        return getShortDateString(DateTimeUtil.getShortDateTime(dateTime));
    }

    public static String addTimeToDate(String dateTime, int days) {
        String time = "";
        if (!MdCommon.isEmpty(dateTime)) {
            Date date = getShortDateTime(dateTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, days);
            time = getShortDateTimeString(cal.getTime());
        }
        return time;
    }

    /**
     * 对日期进行加减
     * @param dateTime
     * @param days
     * @return
     */
    public static Date addDateTimeToDateTime(Date dateTime, int days){
        Date newDateTime = null;
        if(!MdCommon.isEmpty(dateTime)){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateTime);
            calendar.add(Calendar.DATE, days);
            newDateTime = calendar.getTime();
        }
        return newDateTime;
    }

    public static String getDateTimeA(Date time) {
        if (time == null) {
            return "";
        }
        SimpleDateFormat sf = new SimpleDateFormat("MM-dd");
        return sf.format(time);
    }

    public static void main(String[] args) {
        System.out.println(addTimeToDate("1987-09-27 17:07:24", 3650));
        System.out.println(addDateTimeToDateTime(new Date(), 2));
    }

    public static String age2Birthday(int age) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(new Date());
        age = age * -1;
        gc.add(1, age);
        Date ret = gc.getTime();
        return getShortintDateSDF().format(ret).substring(0, 6);
    }

    public static int diffDays(Date date11, Date date22) {
        long date1 = date11.getTime();
        long date2 = date22.getTime();
        if (date2 > date1) {
            date2 = date2 + date1;
            date1 = date2 - date1;
            date2 = date2 - date1;
        }  
        /* 
         * Canlendar 该类是一个抽象类  
         * 提供了丰富的日历字段 
         *  
         * 本程序中使用到了 
         * Calendar.YEAR    日期中的年份 
         * Calendar.DAY_OF_YEAR     当前年中的天数 
         * getActualMaximum(Calendar.DAY_OF_YEAR) 返回今年是 365 天还是366天 
         */
        Calendar calendar1 = Calendar.getInstance(); // 获得一个日历  
        calendar1.setTimeInMillis(date1); // 用给定的 long 值设置此 Calendar 的当前时间值。  

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        // 先判断是否同年  
        int y1 = calendar1.get(Calendar.YEAR);
        int y2 = calendar2.get(Calendar.YEAR);

        int d1 = calendar1.get(Calendar.DAY_OF_YEAR);
        int d2 = calendar2.get(Calendar.DAY_OF_YEAR);
        int maxDays = 0;
        int day = 0;
        if (y1 - y2 > 0) {
            day = numerical(maxDays, d1, d2, y1, y2, calendar2);
        } else {
            day = d1 - d2;
        }
        return day;
//		Calendar calendar1 = Calendar.getInstance();
//		Calendar calendar2 = Calendar.getInstance();
//		calendar1.setTime(date1);
//		long time1 = calendar1.getTimeInMillis();
//		calendar2.setTime(date2);
//		long time2 = calendar2.getTimeInMillis();
//		int days = (int)(time1-time2)/(1000*60*60*24);
//		return days;
    }

    /**
     * 日期间隔计算
     * 计算公式(示例):
     * 20121230 - 20071001
     * 取出20121230这一年过了多少天 d1 = 365     取出20071001这一年过了多少天 d2 = 274
     * 如果2007年这一年有366天就要让间隔的天数+1，因为2月份有29日。
     *
     * @param maxDays  用于记录一年中有365天还是366天
     * @param d1       表示在这年中过了多少天
     * @param d2       表示在这年中过了多少天
     * @param y1       当前为2010年
     * @param y2       当前为2012年
     * @param calendar 根据日历对象来获取一年中有多少天
     * @return 计算后日期间隔的天数
     */
    public static int numerical(int maxDays, int d1, int d2, int y1, int y2, Calendar calendar) {
        int day = d1 - d2;
        int betweenYears = y1 - y2;
        List<Integer> d366 = new ArrayList<Integer>();

        if (calendar.getActualMaximum(Calendar.DAY_OF_YEAR) == 366) {
            System.out.println(calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
            day += 1;
        }

        for (int i = 0; i < betweenYears; i++) {
            // 当年 + 1 设置下一年中有多少天  
            calendar.set(Calendar.YEAR, (calendar.get(Calendar.YEAR)) + 1);
            maxDays = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
            // 第一个 366 天不用 + 1 将所有366记录，先不进行加入然后再少加一个  
            if (maxDays != 366) {
                day += maxDays;
            } else {
                d366.add(maxDays);
            }
            // 如果最后一个 maxDays 等于366 day - 1  
            if (i == betweenYears - 1 && betweenYears > 1 && maxDays == 366) {
                day -= 1;
            }
        }

        for (int i = 0; i < d366.size(); i++) {
            // 一个或一个以上的366天  
            if (d366.size() >= 1) {
                day += d366.get(i);
            }
//          else{  
//              day -= 1;  
//          }  
        }
        return day;
    }

}
