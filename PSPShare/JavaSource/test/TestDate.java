package test;

import java.util.Date;

public class TestDate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(new Date().toLocaleString());
		System.out.println(new Date().toGMTString());
		
		System.out.println(new Date().toString());

	}

}
