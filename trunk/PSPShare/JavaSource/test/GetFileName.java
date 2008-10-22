package test;

public class GetFileName {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String path = "d:/dae/dasfdsafa.jpg";
		
//		path = "d:\\deom\\waht.iso";
//		
//		int index = path.lastIndexOf('\\');
//		String fileName = path.substring(index+1);
		System.out.println("fileName : \n" + getFileName(path));
		
		
		path = "d:\\deom\\waht.iso";
		System.out.println("fileName : \n" + getFileName(path));
	}
	
	public static String getFileName(String path){
		int index = path.lastIndexOf('\\');
		if(index == -1){
			index = path.lastIndexOf('/');
		}
		return path.substring(index+1);
	}
}
