package test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.gameye.psp.image.entity.Image;
import org.gameye.psp.image.entity.Tag;
import org.gameye.psp.image.entity.Type;
import org.gameye.psp.image.service.IImageService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		ITypeService typeS = (ITypeService)getBean("typeService");
//		Type t = new Type();
//		t.setTitle("hello~");
//		t.setAuthor("testla");
//		t.setDate(new Date());
//		typeS.addType(t);
//		log.info("保存结束");
		

		
		IImageService imageS = (IImageService)getBean("imageService");

		
		
		Image image = new Image();
		image.setAuthor("test");
		image.setContent("the content~");
		image.setDate(System.currentTimeMillis());
		image.setDescription("description...");
		image.setId("123456789");
		image.setIp("127.0.0.1");
		image.setNowName("000001.jpg");
		image.setOldName("测试.jpg");
		image.setPostfix(".jpg");
		image.setTitle("呵呵");
		
//		Type t = new Type();
//		t.setTitle("风景");
//		t.setAuthor("test");
//		t.setDate(new Date());
//		
//		image.setType(t);
		
		
		Tag tag = new Tag();
		tag.setAuthor("test");
		tag.setDate(System.currentTimeMillis());
//		tag.setId(20);
		tag.setName("心情");
		Set<Tag>set = new HashSet<Tag>();
		
		set.add(tag);
		tag.setImage(image);
		
		image.setTags(set);
		
		imageS.saveImage(image);
		
		log.info("保存结束");
		
		Image img = imageS.getImage("123456789");
		
		log.info("is null ?" + (img==null));
		log.info(img.getNowName());
		
		
	}

	public static Object getBean(String beanID) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"SpringConfig.xml");
		return ctx.getBean(beanID);
	}

	private static Logger log = Logger.getLogger("");

}
