package test;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.gameye.psp.image.service.IImageHandleService;
import org.gameye.psp.image.service.IImageProvider;
import org.gameye.psp.image.service.impl.ImageHandleServiceImpl;
import org.gameye.psp.image.service.impl.ImageProviderImpl;

public class GenSnail {
	private static Logger log = Logger.getLogger("");

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		IImageProvider iip = new ImageProviderImpl();
		IImageHandleService iihs = new ImageHandleServiceImpl(iip);

		String path = "E:/eclipse 3.4/workspace/ShareImage/WebContent/images/";
		File dir = new File(path);
		if (dir.isFile())
			return;
		File[] files = dir.listFiles();
		String source = null;
		String target = null;
		int width = 80;
		int height = 45;
		int i = 0;
		for (File f : files) {
			if (f.getName().endsWith(".db"))
				continue;
			
			// log.info("source:\n" + f.getPath());
			target = path + "small/" + f.getName();
			// log.info("target:\n" + target);
			if(new File(target).exists())
				continue;
			log.info("开始处理图片:" + f.getName());
			iihs.generate(f.getPath(), target, width, height, false);

			log.info("处理图片 " + f.getName() + " 结束!");
			i ++;
		}
		log.info("处理图片 : " + i);
		log.info("处理缩略图结束");

		// iihs.generate(source, target, width, height, proportion);

	}

}
