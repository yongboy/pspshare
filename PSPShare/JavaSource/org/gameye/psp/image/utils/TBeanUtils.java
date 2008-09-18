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

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;



/**
 * <p>Utility methods for populating JavaBeans properties via reflection.</p>
 *
 * <p>The implementations are provided by {@link TBeanUtilsBean}.
 * These static utility methods use the default instance.
 * More sophisticated behaviour can be provided by using a <code>BeanUtilsBean</code> instance.</p>
 *
 * @author Craig R. McClanahan
 * @author Ralph Schaer
 * @author Chris Audley
 * @author Rey Fran�ois
 * @author Gregor Ra�man
 * @version $Revision: 1.40 $ $Date: 2004/02/28 13:18:33 $
 * @see TBeanUtilsBean
 */

public class TBeanUtils extends BeanUtils{

    /**
     * <p>Copy property values from the origin bean to the destination bean
     * for all cases where the property names are the same.</p>
     *
     * <p>For more details see <code>BeanUtilsBean</code>.</p>
     *
     * @see TBeanUtilsBean#copyProperties
     */
    public static void copyProperties(Object dest, Object orig)
        throws IllegalAccessException, InvocationTargetException {
        
        TBeanUtilsBean.getInstance().copyProperties(dest, orig);
    }


   
}
