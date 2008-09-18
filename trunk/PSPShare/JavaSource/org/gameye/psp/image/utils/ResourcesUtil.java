package org.gameye.psp.image.utils;

import org.apache.struts.util.MessageResources;

public class ResourcesUtil {


	
    public static String getMessage(String strKey) {
        return messages.getMessage(strKey);
    }
    
    public static String getMessage(String strKey, Object[] o) {
        return messages.getMessage(strKey, o);
    }

    protected static MessageResources messages = MessageResources
            .getMessageResources("messageResource_zh_CN");
}
