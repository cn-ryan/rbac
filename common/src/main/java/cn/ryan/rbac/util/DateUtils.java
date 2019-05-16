package cn.ryan.rbac.util;

import cn.ryan.rbac.exeception.BusinessException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 系统日期格式化类
 *
 * @author ryan
 * @create 2019-04-24 15:35
 **/
@Data
@Slf4j
public final class DateUtils {
    private static volatile DateUtils instance;
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
    private static final String HOUR_PATTERN = "yyyy-MM-dd HH";
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String MONTH_PATTERN = "yyyy-MM";
    private static final String YEAR_PATTERN = "yyyy";
    private static final String MINUTE_ONLY_PATTERN = "mm";
    private static final String HOUR_ONLY_PATTERN = "HH";
    private static int COUNT = 0;

    private DateUtils(){
        if(COUNT > 0){
            log.error(DateUtils.class.getName() + "has been initialized twice");
            throw new BusinessException(DateUtils.class.getName() + "has been initialized twice");
        }
        COUNT ++;
    }

    public static DateUtils getInstance(){
        if(instance == null){
            synchronized (DateUtils.class){
                if(instance == null){
                    instance = new DateUtils();
                }
            }
        }
        return instance;
    }
    
    /*
    *@Description 日期相加减天数
    *@Param [date, days, includeTime]
    *@Return java.util.Date
    *@Author ryan
    *@Date 2019/4/24
    *@Time 15:42
    */
    public Date dateAdd(Date date, int days, boolean includeTime) throws ParseException {
        if(date == null){
            date = new Date();
        }
        if(!includeTime){
            SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.DATE_PATTERN);
            date = sdf.parse(sdf.format(date));
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    /**
     * 时间格式化成字符串
     * @param date Date
     * @param pattern StrUtils.DATE_TIME_PATTERN || StrUtils.DATE_PATTERN， 如果为空，则为yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public String dateFormat(Date date, String pattern){
        if("".equals(pattern)){
            pattern = DateUtils.DATE_PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
    /**
     * 时间格式化成字符串
     * @param date Date
     * @return
     * @throws ParseException
     */
    public String dateTimeFormat(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.DATE_TIME_PATTERN);
        return sdf.format(date);
    }
    /**
     * 字符串解析成时间对象
     * @param dateTimeString String
     * @param pattern StrUtils.DATE_TIME_PATTERN || StrUtils.DATE_PATTERN，如果为空，则为yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public Date dateParse(String dateTimeString, String pattern) throws ParseException{
        if("".equals(pattern)){
            pattern = DateUtils.DATE_PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(dateTimeString);
    }

    /**
     * 将日期时间格式成只有日期的字符串（可以直接使用dateFormat，Pattern为Null进行格式化）
     * @param dateTime Date
     * @return
     * @throws ParseException
     */
    public String dateTimeToDateString(Date dateTime){
        String dateTimeString = DateUtils.getInstance().dateFormat(dateTime, DateUtils.DATE_TIME_PATTERN);
        return dateTimeString.substring(0, 10);
    }

    /**
     * 当时、分、秒为00:00:00时，将日期时间格式成只有日期的字符串，
     * 当时、分、秒不为00:00:00时，直接返回
     * @param dateTime Date
     * @return
     * @throws ParseException
     */
    public String dateTimeToDateStringIfTimeEndZero(Date dateTime) throws ParseException{
        String dateTimeString = DateUtils.getInstance().dateFormat(dateTime, DateUtils.DATE_TIME_PATTERN);
        if(dateTimeString.endsWith("00:00:00")){
            return dateTimeString.substring(0, 10);
        }else{
            return dateTimeString;
        }
    }

    /**
     * 将日期时间格式成日期对象，和dateParse互用
     * @param dateTime Date
     * @return Date
     * @throws ParseException
     */
    public Date dateTimeToDate(Date dateTime) throws ParseException{
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 时间加减小时
     * @param startDate 要处理的时间，Null则为当前时间
     * @param hours 加减的小时
     * @return Date
     */
    public Date dateAddHours(Date startDate, int hours) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.HOUR, c.get(Calendar.HOUR) + hours);
        return c.getTime();
    }

    /**
     * 时间加减分钟
     * @param startDate 要处理的时间，Null则为当前时间
     * @param minutes 加减的分钟
     * @return
     */
    public Date dateAddMinutes(Date startDate, int minutes) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + minutes);
        return c.getTime();
    }

    /**
     * 时间加减秒数
     * @param startDate 要处理的时间，Null则为当前时间
     * @param seconds
     * @return
     */
    public Date dateAddSeconds(Date startDate, int seconds) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.SECOND, c.get(Calendar.SECOND) + seconds);
        return c.getTime();
    }

    /**
     * 时间加减天数
     * @param startDate 要处理的时间，Null则为当前时间
     * @param days 加减的天数
     * @return Date
     */
    public Date dateAddDays(Date startDate, int days) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.DATE, c.get(Calendar.DATE) + days);
        return c.getTime();
    }

    /**
     * 时间加减月数
     * @param startDate 要处理的时间，Null则为当前时间
     * @param months 加减的月数
     * @return Date
     */
    public Date dateAddMonths(Date startDate, int months) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + months);
        return c.getTime();
    }

    /**
     * 时间加减年数
     * @param startDate 要处理的时间，Null则为当前时间
     * @param years 加减的年数
     * @return Date
     */
    public Date dateAddYears(Date startDate, int years) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.YEAR, c.get(Calendar.YEAR) + years);
        return c.getTime();
    }

    /**
     * 时间比较（如果myDate>compareDate返回1，<返回-1，相等返回0）
     * @param myDate 时间
     * @param compareDate 要比较的时间
     * @return int
     */
    public int dateCompare(Date myDate, Date compareDate) {
        Calendar myCal = Calendar.getInstance();
        Calendar compareCal = Calendar.getInstance();
        myCal.setTime(myDate);
        compareCal.setTime(compareDate);
        return myCal.compareTo(compareCal);
    }

    /**
     * 获取两个时间中最小的一个时间
     * @param date
     * @param compareDate
     * @return
     */
    public Date dateMin(Date date, Date compareDate) {
        if(date == null){
            return compareDate;
        }
        if(compareDate == null){
            return date;
        }
        if(1 == dateCompare(date, compareDate)){
            return compareDate;
        }else if(-1 == dateCompare(date, compareDate)){
            return date;
        }
        return date;
    }

    /**
     * 获取两个时间中最大的一个时间
     * @param date
     * @param compareDate
     * @return
     */
    public Date dateMax(Date date, Date compareDate) {
        if(date == null){
            return compareDate;
        }
        if(compareDate == null){
            return date;
        }
        if(1 == dateCompare(date, compareDate)){
            return date;
        }else if(-1 == dateCompare(date, compareDate)){
            return compareDate;
        }
        return date;
    }

    /**
     * 获取两个日期（不含时分秒）相差的天数，不包含今天
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    public int dateBetween(Date startDate, Date endDate) throws ParseException {
        Date dateStart = dateParse(dateFormat(startDate, DATE_PATTERN), DATE_PATTERN);
        Date dateEnd = dateParse(dateFormat(endDate, DATE_PATTERN), DATE_PATTERN);
        return (int) ((dateEnd.getTime() - dateStart.getTime())/1000/60/60/24);
    }

    /**
     * 获取两个日期（不含时分秒）相差的天数，包含今天
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    public int dateBetweenIncludeToday(Date startDate, Date endDate) throws ParseException {
        return dateBetween(startDate, endDate) + 1;
    }

    /**
     * 获取日期时间的年份，如2017-02-13，返回2017
     * @param date
     * @return
     */
    public int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取日期时间的月份，如2017年2月13日，返回2
     * @param date
     * @return
     */
    public int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日期时间的第几天（即返回日期的dd），如2017-02-13，返回13
     * @param date
     * @return
     */
    public int getDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DATE);
    }

    /**
     * 获取日期时间当月的总天数，如2017-02-13，返回28
     * @param date
     * @return
     */
    public int getDaysOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     * 获取日期时间当年的总天数，如2017-02-13，返回2017年的总天数
     * @param date
     * @return
     */
    public int getDaysOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    /**
     * 根据时间获取当月最大的日期
     * <li>2017-02-13，返回2017-02-28</li>
     * <li>2016-02-13，返回2016-02-29</li>
     * <li>2016-01-11，返回2016-01-31</li>
     * @param date Date
     * @return
     * @throws Exception
     */
    public Date maxDateOfMonth(Date date) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int value = cal.getActualMaximum(Calendar.DATE);
        return dateParse(dateFormat(date, MONTH_PATTERN) + "-" + value, null);
    }

    /**
     * 根据时间获取当月最小的日期，也就是返回当月的1号日期对象
     * @param date Date
     * @return
     * @throws Exception
     */
    public Date minDateOfMonth(Date date) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int value = cal.getActualMinimum(Calendar.DATE);
        return dateParse(dateFormat(date, MONTH_PATTERN) + "-" + value, null);
    }

    /*
    *@Description 获取当前时间格式 yyy-MM-dd
    *@Param []
    *@Return java.util.Date
    *@Author ryan
    *@Date 2019/4/24
    *@Time 15:53
    */
    public Date getCurrentDate() throws ParseException {
       return this.getCurrentDate(DateUtils.DATE_PATTERN);
    }

    /*
    *@Description 获取当前指定时间
    *@Param [pattern]
    *@Return java.util.Date
    *@Author ryan
    *@Date 2019/4/24
    *@Time 15:53
    */
    public Date getCurrentDate(String pattern) throws ParseException {
        Date date = new Date();
        if("".equals(pattern)){
            pattern = DateUtils.DATE_PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        date = sdf.parse(sdf.format(date));
        return date;
    }

    /*
     *@Description 获取当前时间格式 yyyy-MM-dd : hh:mm:ss
     *@Param []
     *@Return java.util.Date
     *@Author ryan
     *@Date 2019/4/24
     *@Time 15:53
     */
    public Date getCurrentTime() {
        return this.getCurrentTime(DateUtils.DATE_TIME_PATTERN);
    }

    /*
     *@Description 获取当前指定时间
     *@Param [pattern]
     *@Return java.util.Date
     *@Author ryan
     *@Date 2019/4/24
     *@Time 15:53
     */
    public Date getCurrentTime(String pattern){
        Date date = new Date();
        if(StringUtils.isBlank(pattern)){
            pattern = DateUtils.DATE_TIME_PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            date = sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
