package org.gameye.psp.image.utils;
/**
 * 
 * @author Majian
 */
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
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

		SequenceCreator random = new SequenceCreator();
		for (int i = 0; i < 20; i++) {
//			System.out.println("UID: " + random.getUID());
		}

	} 
}
