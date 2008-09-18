package org.gameye.psp.image.dao.base;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *  Hibernate Dao 辅助操作类
 * @author Majian
 */
public class SqlHelper {

	public SqlHelper() {}

	/**
	 * @param sqlName
	 * @return String
	 */
	public String getSql(String sqlName) {
		return null;
	}

	/**
	 * 解板生成SQLCOUNT
	 * 
	 * @param sql
	 *            String:
	 * @throws UnifyException:
	 * @throws Exception
	 */
	public static String parseCountSql(String sql) {
		int fromIndex = 0;
		int selectIndex = 0;
		int orderIndex = 0;
		int lastRBracketIndex = 0;

		StringBuffer newQuery = new StringBuffer();
		String trimQuery = sql.trim();
		if (trimQuery == null || trimQuery.length() <= 0)
			;

		String hqlUpper = trimQuery.toUpperCase();
		fromIndex = hqlUpper.indexOf("FROM");
		selectIndex = hqlUpper.indexOf("SELECT");
		orderIndex = hqlUpper.lastIndexOf("ORDER BY");
		lastRBracketIndex = hqlUpper.lastIndexOf(")");

		if (selectIndex < 0 || fromIndex < 0 || (selectIndex + 6) >= fromIndex)
			;

		newQuery.append("select count(*)  ");
		if (orderIndex > 0 && lastRBracketIndex < orderIndex)
			newQuery.append(trimQuery.substring(0, orderIndex));
		else
			newQuery.append(trimQuery);

		return newQuery.toString();
	}

	/**
	 * 去除hql的select 子句，未考虑union的情况
	 * 
	 * @param queryString
	 * @return
	 */
	public static String removeSelect(String queryString) {
		int beginPos = queryString.toLowerCase().indexOf("from");
		return queryString.substring(beginPos);
	}

	/**
	 * 去除hql的orderby 子句
	 * 
	 * @param queryString
	 * @return
	 */
	public static String removeOrders(String queryString) {
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(queryString);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 取出列表的第一个对象. 如果列表为空返回Null,如果有多于一个对象,抛出异常.
	 */
	public static <E> E uniqueResult(Collection<E> results) {
		if (results == null || results.isEmpty())
			return null;
		if (results.size() > 1)
			throw new IllegalArgumentException(
					"the Collection size is larger than 1");
		return results.iterator().next();
	}
}
