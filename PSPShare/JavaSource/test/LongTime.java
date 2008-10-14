package test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.gameye.psp.image.utils.DateHelper;

public class LongTime {
	
	/**
	 * 得到当日开始时间
	 * 
	 * @return
	 */
	public static Date getCurrDayStartTime() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	
	/**
	 * 得到当日结束时间
	 * @return
	 */
	public static Date getCurrDayEndTime() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	/**
	 * 得到当前周一开始时间
	 * 
	 * @return
	 */
	public static Date getCurrWeekFirstDay() {
		Calendar calendar = new GregorianCalendar();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 得到当前周末时间结束时间
	 * 
	 * @return
	 */
	public static Date getCurrWeekLastDay() {
		Calendar calendar = new GregorianCalendar();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6);
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);

		return calendar.getTime();
	}
	
	/**
	 * 得到本月的第一天开始时间
	 * 
	 * @return
	 */
	public static Date getCurrMonthFirstDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar
				.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		return calendar.getTime();
	}

	/**
	 * 得到本月的最后一天结束时间
	 * 
	 * @return
	 */
	public static Date getCurrMonthLastDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar
				.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}


	/**
	 * 取得某天所在周的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		return c.getTime();
	}

	/**
	 * 取得某天所在周的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
		return c.getTime();
	}

	/**
	 * 得到本月的第一天开始时间
	 * 
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar
				.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.setTime(date);

		return calendar.getTime();
	}

	/**
	 * 得到本月的最后一天结束时间
	 * 
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar
				.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.setTime(date);
		return calendar.getTime();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long longDate = System.currentTimeMillis();
		System.out.println(longDate);
		Date longD = new Date(longDate);
		System.out.println(longD.toString());

		long dte = 1123832087937L;
		Date date = new Date(dte);
		System.out.println("有Date对象得到的Long 值 ：" + date.getTime());
		String dateStr = DateHelper.formatDate(date, "yyyy-MM-dd HH:mm:ss");
		System.out.println(dateStr);

		GregorianCalendar cal = new GregorianCalendar();
		Date now = new Date();
		cal.setTime(now);
		cal.setFirstDayOfWeek(GregorianCalendar.MONDAY); //设置一个星期的第一天为星期1，默认是星期日
		SimpleDateFormat dateutil = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss");
		System.out.println(" 今天= " + dateutil.format(cal.getTime())); // 今天

		cal.add(GregorianCalendar.DATE, -1);
		System.out.println(" 昨天= " + dateutil.format(cal.getTime())); // 昨天

		cal.set(GregorianCalendar.DAY_OF_WEEK, 1);
		System.out.println(" 本周一= " + dateutil.format(cal.getTime())); // 本周1

		// cal.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.MONDAY);

		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
		System.out.println(" 本月一号= " + dateutil.format(getCurrMonthFirstDay())); // 本月1日
		// GregorianCalendar.MONTH;
		cal.set(GregorianCalendar.DAY_OF_MONTH, GregorianCalendar.MONTH);
		System.out
				.println(" 本月最后一天= " + dateutil.format(getCurrMonthLastDay())); // 本月1日

		// cal.set(GregorianCalendar.DAY_OF_WEEK,1);
		System.out.println(" 本周一= "
				+ dateutil.format(getFirstDayOfWeek(new Date()))); // 本周1

		// cal.set(GregorianCalendar.DAY_OF_WEEK,1);
		System.out.println(" 本周一= "
				+ dateutil.format(getFirstDayOfWeek(new Date()))); // 本周1

		System.out
				.println("周一开始时间\n " + dateutil.format(getCurrWeekFirstDay()));

		System.out.println("周末结束时间\n " + dateutil.format(getCurrWeekLastDay()));

		System.out.println("本月开始时间\n "
				+ dateutil.format(getCurrMonthFirstDay()));

		System.out
				.println("本月结束时间\n " + dateutil.format(getCurrMonthLastDay()));

		System.out.println("周一\n "
				+ dateutil.format(getFirstDayOfWeek(new Date())));

		System.out.println("周末\n "
				+ dateutil.format(getLastDayOfWeek(new Date())));

		System.out.println("本月一号\n "
				+ dateutil.format(getFirstDayOfMonth(new Date())));

		System.out.println("本月最后\n "
				+ dateutil.format(getLastDayOfMonth(new Date())));
		
		System.out.println( "当日开始时间\n" + dateutil.format(getCurrDayStartTime()) );
		
		System.out.println( "当日结束时间\n" + dateutil.format(getCurrDayEndTime()) );
		
		System.out.println( "测试时间 : " + dateutil.format(new Date(1223992335207L)));

		// getWeekFirstDay
		// System.out.println(" 本周一2= " + dateutil.format(getWeekFirstDay()));
		// // 本周1
	}

}
