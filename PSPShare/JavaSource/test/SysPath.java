package test;

public class SysPath {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new SysPath().dao();

	}
	
	private void dao(){
		System.out.println(this.getClass().getClassLoader().getResource(".").getPath());
		System.out.println(this.getClass().getClassLoader().getResource("/").getPath());
		System.out.println(this.getClass().getClassLoader().getResource("").getPath());
		System.out.println(this.getClass().getClassLoader().getResource("..").getPath());

	}

}
