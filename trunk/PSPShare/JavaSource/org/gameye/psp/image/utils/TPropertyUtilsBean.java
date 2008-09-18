/*
 * Copyright 2001-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 

package org.gameye.psp.image.utils;




import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;




public class TPropertyUtilsBean extends PropertyUtilsBean{
  
	
    /**
     * add by Majian
     * 得到指定属性名的类型
     * **/    
    public Class getDestType(Object bean, String name) {

        // Validate method parameters
        if (bean == null) {
            throw new IllegalArgumentException("No bean specified");
        }
        if (name == null) {
            throw new IllegalArgumentException("No name specified");
        }

        // Return the requested result
        if (bean instanceof DynaBean) {
            // All DynaBean properties are writeable
            return null;
        } else {
            try {
                PropertyDescriptor desc =
                    getPropertyDescriptor(bean, name);
                if (desc != null) {
                	Class c = desc.getPropertyType();
                    return c;
                } else {
                    return null;
                }
            } catch (IllegalAccessException e) {
                return null;
            } catch (InvocationTargetException e) {
                return null;
            } catch (NoSuchMethodException e) {
                return null;
            }
        }

    }


   
}
