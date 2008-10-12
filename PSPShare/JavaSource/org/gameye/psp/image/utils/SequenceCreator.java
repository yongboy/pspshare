package org.gameye.psp.image.utils;
/**
 * 
 * @author Majian
 */
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;
//
public class SequenceCreator {
	private SecureRandom seeder;
	private String midString;
	
	public SequenceCreator() 
	{		
	}

	private void init() 
	{
		try {
			InetAddress inet = InetAddress.getLocalHost();
			byte[] bytes = inet.getAddress();

			String hexAddress = hexFormat(getInt(bytes), 8);
			String hash = hexFormat(System.identityHashCode(this), 8);

			midString = hexAddress + hash;

			seeder = new SecureRandom();
			seeder.nextInt();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	synchronized public String getUID()
	{
		init();
		StringBuffer buf = new StringBuffer();
	
		long time = System.currentTimeMillis();
		int timeLow = (int) time & 0xFFFFFFFF;
		int node = seeder.nextInt();
	
		buf.append(hexFormat(timeLow, 8)).append(midString).append(hexFormat(node, 8));
	
		return buf.toString();
	}
	protected static Set<String> setL = new HashSet<String>();
	synchronized public long getLongID()
	{
		init();
		StringBuffer buf = new StringBuffer();
	
		long time = System.currentTimeMillis();
		int timeLow = (int) time & 0xFFFFFFFF;
		int node = seeder.nextInt();
//		seeder.nextLong();
	
		buf.append(Math.abs(timeLow)).append(Math.abs(node));
		String s = buf.toString();
		setL.add("time-" + Long.toString(time).length());
		setL.add("timeLow-" + Integer.toString(timeLow).length());
		setL.add("node-" + Integer.toString(node).length());
		setL.add("s-" + s.length());
		int maxLen = 19;
		int reduce =maxLen - s.length();
		if(reduce == 0)
			return Long.parseLong(s);
		else{
			for(int i=0; i < reduce; i++){
				buf.append("0");
			}
			return Long.parseLong(buf.toString());
		}
	}

	
	

	private String hexFormat(int number, int digits) {
		// TODO Auto-generated method stub
		String hex = Integer.toHexString(number).toUpperCase();
		
		if (hex.length() >= digits) {
			return hex.substring(0, digits);
		} else {
			return hex;
		}

	}

	private int getInt(byte[] bytes) {
		// TODO Auto-generated method stub
		int size = (bytes.length > 32) ? 32 : bytes.length;
		int result = 0;

		for (int i = size - 1; i >= 0; i--) {
			if (i == (size - 1))
				result += bytes[i];
			else
				result += (bytes[i] << 4 * (size - 1 - i));
		}
		return result;

	}
	
	public static void main(String[] args) throws Exception {
//		long s = System.currentTimeMillis();
//		Set <Long> sets = new HashSet<Long>();
//		long s1 = -1;
//		
//		for (int i = 0; i < 100; i++) {
//			SequenceCreator random = new SequenceCreator();
//			s1 = random.getLongID();
//			sets.add(s1);
//			System.out.println("UID: " + s1);
//		}
//		long e = System.currentTimeMillis();
//		System.out.println("使用时间 : " + (e-s));
//		System.out.println("循环100次 ： " + sets.size());
//		for(String i : setL){
//			System.out.println(i);
//		}
//		for(int i = 0; i < 100; i ++ ){
//		long demo1 = new SequenceCreator().getLongID();
//		long demo2 = new SequenceCreator().getLongID();
//		
//		if(demo2>demo1){
//			System.out.println("正解:" + (demo2-demo1));
//		}else{
//			System.out.println("错解:" + (demo2-demo1));
//		}
//		}
		
		
		for(int i=0; i <100;i++){
		long time = System.currentTimeMillis();
//		int timeLow = (int) time & 0xFFFFFFFF;
		SecureRandom seeder = new SecureRandom();
		seeder.nextInt();
		int node = seeder.nextInt();
		StringBuilder sb = new StringBuilder();
		sb.append(time);
		sb.append(Math.abs(node));
		System.out.println(sb.toString());
		}
	} 
}
