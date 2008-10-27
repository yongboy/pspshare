package org.gameye.psp.image.config;

import java.util.Calendar;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ClearTaskTool extends TimerTask {

	private static boolean isRunning = false;
	private static boolean flag = true;
	private static final int C_SCHEDULE_HOUR = 23;
	private static ServletContext context;
	private Log log = LogFactory.getLog(ClearTaskTool.class);
	
//	private WorkServiceImpl workService;

	public ClearTaskTool(ServletContext context) {
		this.context = context;
	}

	@Override
    public void run(){
        Calendar cal = Calendar.getInstance();
        if (!isRunning){
            if (C_SCHEDULE_HOUR == cal.get(Calendar.HOUR_OF_DAY) && flag) {
                isRunning = true;
//                workService.autoWorkOff();
                isRunning = false;
                flag = false;
                log.info("指定任务执行结束");
            }
        } else{
            log.info("上一次任务执行还未结束");
        }
        if(C_SCHEDULE_HOUR != cal.get(Calendar.HOUR_OF_DAY)){
            flag = true;
        }
      }

}
