package test;

import org.gameye.psp.image.utils.MD5;

public class GenPasswordMD5 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String pass = "123456";
		String md5 = new MD5(pass).compute();
		System.out.println(md5);

	}

}
