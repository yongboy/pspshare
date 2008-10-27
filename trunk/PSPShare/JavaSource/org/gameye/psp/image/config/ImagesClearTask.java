package org.gameye.psp.image.config;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ImagesClearTask implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
		timer.cancel();
		log.info("定时器销毁");
	}

	public void contextInitialized(ServletContextEvent event) {
		log.info("定时器开始启动");
		timer = new Timer(true);
		ClearTaskTool task = new ClearTaskTool(event.getServletContext());
		timer.schedule(task, 0, 60 * 60 * 1000);
		log.info("已经添加任务调度表");
	}

	private static Timer timer;
	private static Log log = LogFactory.getLog(ImagesClearTask.class);
}
