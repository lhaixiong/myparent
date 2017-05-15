package lhxbase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author PCY
 * @date 2014年8月22日
 */
public class DateUtil {

	private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final long NW = 7 * 1000 * 24 * 60 * 60;// 一周的毫秒数
	public static final long ND = 1000 * 24 * 60 * 60;// 一天的毫秒数
	public static final long NH = 1000 * 60 * 60;// 一小时的毫秒数
	public static final long NM = 1000 * 60;// 一分钟的毫秒数
	public static final long NS = 1000;// 一秒钟的毫秒数

	/**
	 * 格式 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 * @author PCY
	 * @date 2014年8月22日
	 */
	public static String format(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		return dateFormat.format(date);
	}

	public static String format(Date date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	public static Date parse(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取两个时间的小时差
	 * 
	 * @param minDate
	 * @param maxDate
	 * @return
	 * @author PCY
	 * @date 2014年8月22日
	 */
	public static long diffHour(Date minDate, Date maxDate) {
		long diff = maxDate.getTime() - minDate.getTime();
		long day = diff / ND;// 计算差多少天
		long hour = diff % ND / NH + day * 24;// 计算差多少小时
		return hour;
	}

	/**
	 * 获得两个日期间的差值(秒)
	 * 
	 * @param minDate
	 * @param maxDate
	 * @return
	 * @author PCY
	 * @date 2014年8月25日
	 */
	public static long diffMinute(Date minDate, Date maxDate) {
		long min = minDate.getTime();
		long max = maxDate.getTime();
		return (max - min) / NM;
	}

	/**
	 * 获得两个日期间的差值(秒)
	 * 
	 * @param minDate
	 * @param maxDate
	 * @return
	 * @author PCY
	 * @date 2014年8月25日
	 */
	public static long diffSecond(Date minDate, Date maxDate) {
		long min = minDate.getTime();
		long max = maxDate.getTime();
		return (max - min) / NS;
	}

	/**
	 * 获得两个日期间的差值(秒)
	 * 
	 * @param minTime
	 *            小的时间
	 * @param maxTime
	 *            大的时间
	 * @return
	 * @since 2015年3月11日 下午8:05:26
	 */
	public static int diffSecond(long minTime, long maxTime) {
		return (int) ((maxTime - minTime) / NS);
	}

	/**
	 * 判断指定日期是不是前些天
	 * 
	 * @param d
	 * @return
	 * @author PCY
	 * @date 2013-9-13
	 */
	public static boolean isPreDay(Date d) {
		if (d == null) {
			return false;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int year = cal.get(Calendar.YEAR);
		int date = cal.get(Calendar.DAY_OF_YEAR);
		cal.setTime(d);
		int pyear = cal.get(Calendar.YEAR);
		int pdate = cal.get(Calendar.DAY_OF_YEAR);
		if (pyear < year || (pyear == year && pdate < date))
			return true;
		return false;
	}

	/**
	 * 两个日期是否为同一天
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isSameDay(Date d1, Date d2) {
		if (d1 == null || d2 == null) {
			return false;
		}
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);
		boolean syear = c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR);
		boolean sday = c1.get(Calendar.DAY_OF_YEAR) == c2
				.get(Calendar.DAY_OF_YEAR);
		return syear && sday;
	}

	/**
	 * 根据日期获得秒数
	 * 
	 * @param date
	 * @return
	 * @author PCY
	 * @date 2014年8月26日
	 */
	public static long getDateSecond(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis() / NS;
	}

	/**
	 * 获取两个Date对象之间的天数
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return
	 */
	public static int getDays(Date startDate, Date endDate) {
		if (startDate == null) {
			startDate = new Date();
		}
		long start = startDate.getTime();
		long end = endDate.getTime();
		int diffDays = (int) ((end - start) / ND);
		return diffDays;
	}

	/**
	 * 计算两个日期之间相隔的天数
	 * 
	 * @data 2015年1月8日 下午2:23:57
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int diffDays(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return 0;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		long start = calendar.getTime().getTime();

		calendar.setTime(endDate);
		long end = calendar.getTime().getTime();
		int diffDays = (int) ((end - start) / ND);

		return diffDays;
	}

	public static int diffDays(long start, long end) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(start);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		int diffDays = (int) ((end - calendar.getTimeInMillis()) / ND);
		return diffDays;
	}

	/**
	 * 增加天单位时间
	 * 
	 * @param date
	 * @param second
	 * @return
	 * @author PCY
	 * @date 2014年12月18日
	 */
	public static Date addDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
	}

	/**
	 * 增加小时单位时间
	 * 
	 * @param date
	 * @param hour
	 * @return
	 * @author PCY
	 * @date 2014年9月17日
	 */
	public static Date addHour(Date date, int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, hour);
		return calendar.getTime();
	}

	/**
	 * 增加秒单位时间
	 * 
	 * @param date
	 * @param second
	 * @return
	 * @author PCY
	 * @date 2014年12月18日
	 */
	public static Date addSecond(Date date, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, second);
		return calendar.getTime();
	}

	/**
	 * 获取今天星期几 一二三四五六日对应：1234567
	 * 
	 * @return
	 */
	public static int getTodayWeekOfDay() {
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		return day == 1 ? 7 : (day - 1);
	}

	/**
	 * 获取指定的日期是星期几
	 * 
	 * @param d
	 * @return
	 * @since 2016年8月11日 下午2:55:23
	 * @author PCY
	 */
	public static int getWeekOfDay(Date d) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		return day == 1 ? 7 : (day - 1);
	}

	/**
	 * 判断是否在时间内
	 * 
	 * @param minDate
	 * @param maxDate
	 * @param judge
	 * @return
	 * @author PCY
	 * @date 2014年12月18日
	 */
	public static boolean isBetween(Date minDate, Date maxDate, Date judge) {
		if (minDate == null || maxDate == null || judge == null) {
			return false;
		}
		if (minDate.after(maxDate))
			return false;
		if (judge.equals(minDate))
			return true;
		return judge.after(minDate) && judge.before(maxDate);
	}

	/**
	 * 根据指定的时间格式获今天的时间
	 * 
	 * @param timeStr
	 *            格式 HH[:mm][:ss]
	 * @return
	 * @since 2016年5月10日 下午9:01:52
	 */
	public static Date getTodayTime(String timeStr) {
		String[] times = timeStr.split(":");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int hou = Integer.parseInt(times[0]);
		calendar.set(Calendar.HOUR_OF_DAY, hou);
		if (times.length > 1) {
			int mim = Integer.parseInt(times[1]);
			calendar.set(Calendar.MINUTE, mim);
		} else {
			calendar.set(Calendar.MINUTE, 0);
		}
		if (times.length > 2) {
			int sec = Integer.parseInt(times[2]);
			calendar.set(Calendar.SECOND, sec);
		} else {
			calendar.set(Calendar.SECOND, 0);
		}
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取今天是这个月的几号
	 * 
	 * @return
	 * @since 2015年10月26日 下午6:06:33
	 */
	public static int getTodayMonthOfDay() {
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return day;
	}

	/**
	 * 获取最大的日期
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 * @since 2015年11月4日 下午4:40:50
	 * @author PCY
	 */
	public static Date getMax(Date d1, Date d2) {
		if (d1 == null && d2 == null)
			return null;
		if (d1 == null)
			return d2;
		if (d2 == null)
			return d1;
		return d1.getTime() > d2.getTime() ? d1 : d2;
	}

	/**
	 * 获取今周的日期
	 * 
	 * @param d
	 *            周几
	 * @param h
	 *            小时
	 * @param m
	 *            分钟
	 * @param s
	 *            秒
	 * @return
	 * @since 2015年11月28日 下午6:00:05
	 * @author PCY
	 */
	public static Date getDayOfWeek(int d, int h, int m, int s) {
		Calendar calendar = Calendar.getInstance();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek == 0)
			dayOfWeek = 7;
		calendar.add(Calendar.DATE, -dayOfWeek + d);
		calendar.set(Calendar.HOUR_OF_DAY, h);
		calendar.set(Calendar.MINUTE, m);
		calendar.set(Calendar.SECOND, s);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 把Date日期部分转为int类型(统计用)
	 * 
	 * @param date
	 * @return
	 * @since 2016年4月20日 下午3:28:37
	 */
	public static int dateToInt(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null)
			cal.setTime(date);
		int statDate = cal.get(Calendar.YEAR) * 10000
				+ (cal.get(Calendar.MONTH) + 1) * 100 + cal.get(Calendar.DATE);
		return statDate;
	}

	/**
	 * 获得一天的开始时间
	 * 
	 * @param date
	 * @return
	 * @since 2016年4月20日 下午5:38:13
	 */
	public static String getBeginTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date start = new Date(cal.getTimeInMillis());
		return DateUtil.format(start);
	}

	/**
	 * 判定一个日期是否在当前周 (周一~周日)
	 * 
	 * @param date
	 * @return
	 * @since 2016年4月23日 下午6:36:31
	 */
	public static boolean isInThisWeek(Date date) {
		if (date == null)
			return false;
		Calendar mon = Calendar.getInstance();
		long now = mon.getTimeInMillis();
		mon.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 获取本周一的日期
		mon.set(Calendar.HOUR_OF_DAY, 0);
		mon.set(Calendar.MINUTE, 0);
		mon.set(Calendar.SECOND, 0);
		mon.set(Calendar.MILLISECOND, 0);
		Calendar sun = Calendar.getInstance();
		sun.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		sun.set(Calendar.HOUR_OF_DAY, 23);
		sun.set(Calendar.MINUTE, 59);
		sun.set(Calendar.SECOND, 59);
		sun.set(Calendar.MILLISECOND, 999);
		if (now < sun.getTimeInMillis()) {
			mon.add(Calendar.WEEK_OF_YEAR, -1);
		} else {
			sun.add(Calendar.WEEK_OF_YEAR, 1);
		}
		long time = date.getTime();
		if (time < mon.getTimeInMillis()) {
			return false;
		}
		if (time > sun.getTimeInMillis()) {
			return false;
		}
		return true;
	}

	/**
	 * 设置今天的时间
	 * 
	 * @param hour
	 * @param mimute
	 * @return
	 * @since 2015年10月28日 下午2:34:53
	 * @author PCY
	 */
	public static Date setTime(Date date, int hour, int mimute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, mimute);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 将"00:00",12:00"格式字符串组转换成多个calendar
	 * 
	 * @param expression
	 * @return
	 * @since 2016年7月29日 上午1:27:49
	 * @author levy
	 */
	public static List<Date> parseStr2MoreCal(String[] resetTimeStr) {
		List<Date> list = new ArrayList<Date>();
		for (String hourMin : resetTimeStr) {
			if (hourMin.isEmpty())
				continue;
			Date date = getTodayTime(hourMin);
			list.add(date);
		}
		return list;
	}

	/**
	 * 是否是不同月
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 * @since 2016年8月4日 下午4:30:02
	 */
	public static boolean isDiffMonth(Date d1, Date d2) {
		Calendar c = Calendar.getInstance();
		c.setTime(d1);
		int month1 = c.get(Calendar.MONTH);
		c.setTime(d2);
		int month2 = c.get(Calendar.MONTH);
		return month1 != month2;
	}

	/**
	 * 获取指定时间
	 * 
	 * @param date
	 * @param timeStr
	 * @return
	 * @since 2016年11月4日 下午4:22:55
	 * @author PCY
	 */
	public static Date getTime(Date date, String timeStr) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String[] times = timeStr.split(":");
		int hou = Integer.parseInt(times[0]);
		calendar.set(Calendar.HOUR_OF_DAY, hou);
		if (times.length > 1) {
			int mim = Integer.parseInt(times[1]);
			calendar.set(Calendar.MINUTE, mim);
		} else {
			calendar.set(Calendar.MINUTE, 0);
		}
		if (times.length > 2) {
			int sec = Integer.parseInt(times[2]);
			calendar.set(Calendar.SECOND, sec);
		} else {
			calendar.set(Calendar.SECOND, 0);
		}
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取下周的星期几
	 * 
	 * @param day
	 * @return
	 * @since 2016年11月4日 下午5:01:15
	 * @author PCY
	 */
	public static Date getDayOfNextWeek(int day) {
		Calendar calendar = Calendar.getInstance();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek == 0)
			dayOfWeek = 7;
		int d = 7 - dayOfWeek + day;
		calendar.add(Calendar.DATE, d);
		return calendar.getTime();
	}

}
