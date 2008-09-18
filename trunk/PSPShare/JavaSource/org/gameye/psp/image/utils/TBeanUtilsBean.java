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


import java.beans.IndexedPropertyDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ContextClassLoaderLocal;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.MappedPropertyDescriptor;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.feipy.framework.web.servlet.SqlDateConverter;


/**
 * <p>JavaBean property population methods.</p>
 *
 * <p>This class provides implementations for the utility methods in
 * {@link TBeanUtils}.
 * Different instances can be used to isolate caches between classloaders
 * and to vary the value converters registered.</p>
 *
 * @author Craig R. McClanahan
 * @author Ralph Schaer
 * @author Chris Audley
 * @author Rey Fran�ois
 * @author Gregor Ra�man
 * @version $Revision: 1.16 $ $Date: 2004/02/28 13:18:33 $
 * @see TBeanUtils
 * @since 1.7
 */

public class TBeanUtilsBean extends BeanUtilsBean{
    // ------------------------------------------------------ Private Class Variables
	
    /** 
     * Contains <code>BeanUtilsBean</code> instances indexed by context classloader.
     */
    private static final ContextClassLoaderLocal 
            beansByClassLoader = new ContextClassLoaderLocal() {
                        // Creates the default instance used when the context classloader is unavailable
                        protected Object initialValue() {
                            return new TBeanUtilsBean();
                        }
                    };
    
    
    /** 
     * Sets the instance which provides the functionality for {@link TBeanUtils}.
     * This is a pseudo-singleton - an single instance is provided per (thread) context classloader.
     * This mechanism provides isolation for web apps deployed in the same container. 
     */
    public synchronized static void setInstance(TBeanUtilsBean newInstance) {
        beansByClassLoader.set(newInstance);
    }

    // --------------------------------------------------------- Attributes

    /**
     * Logging for this instance
     */
    private Log log = LogFactory.getLog(TBeanUtils.class);
    
    /** Used to perform conversions between object types when setting properties */
    private ConvertUtilsBean convertUtilsBean;
    
    /** Used to access properties*/
    private TPropertyUtilsBean propertyUtilsBean;

    // --------------------------------------------------------- Constuctors

    /** 
     * <p>Constructs an instance using new property 
     * and conversion instances.</p>
     */
    public TBeanUtilsBean() {
        this(new ConvertUtilsBean(), new TPropertyUtilsBean());
    }

    /** 
     * <p>Constructs an instance using given property and conversion instances.</p>
     *
     * @param convertUtilsBean use this <code>ConvertUtilsBean</code> 
     * to perform conversions from one object to another
     * @param propertyUtilsBean use this <code>PropertyUtilsBean</code>
     * to access properties
     */
    public TBeanUtilsBean(		
                            ConvertUtilsBean convertUtilsBean, 
                            TPropertyUtilsBean propertyUtilsBean) {
                            
        this.convertUtilsBean = convertUtilsBean;
        this.propertyUtilsBean = propertyUtilsBean;
    }

    // --------------------------------------------------------- Public Methods

    /** 
     * Gets the instance which provides the functionality for {@link BeanUtils}.
     * This is a pseudo-singleton - an single instance is provided per (thread) context classloader.
     * This mechanism provides isolation for web apps deployed in the same container. 
     */
    public synchronized static BeanUtilsBean getInstance() {
        return (TBeanUtilsBean) beansByClassLoader.get();
    }

    public void copyProperties(Object dest, Object orig)
        throws IllegalAccessException, InvocationTargetException {

        // Validate existence of the specified beans
        if (dest == null) {
            throw new IllegalArgumentException
                    ("No destination bean specified");
        }
        if (orig == null) {
            throw new IllegalArgumentException("No origin bean specified");
        }
        if (log.isDebugEnabled()) {
            log.debug("BeanUtils.copyProperties(" + dest + ", " +
                      orig + ")");
        }

        // Copy the properties, converting as necessary
        if (orig instanceof DynaBean) {
            DynaProperty origDescriptors[] =
                ((DynaBean) orig).getDynaClass().getDynaProperties();
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if (getPropertyUtils().isWriteable(dest, name)) {
                    Object value = ((DynaBean) orig).get(name);
                    copyProperty(dest, name, value);
                }
            }
        } else if (orig instanceof Map) {
            Iterator names = ((Map) orig).keySet().iterator();
            while (names.hasNext()) {
                String name = (String) names.next();
                if (getPropertyUtils().isWriteable(dest, name)) {
                    Object value = ((Map) orig).get(name);
                    copyProperty(dest, name, value);
                }
            }
        } else /* if (orig is a standard JavaBean) */ {
            PropertyDescriptor origDescriptors[] =
                getPropertyUtils().getPropertyDescriptors(orig);
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
 
                if ("class".equals(name)) {
                    continue; // No point in trying to set an object's class
                }
                
                
                Class orgtype = origDescriptors[i].getPropertyType();
                String orgtypeName = orgtype.getName();
                
                Class desttype = ((TPropertyUtilsBean)getPropertyUtils()).getDestType(dest, name);
                if(desttype == null)continue;
                String desttypeName = desttype.getName();
                
                //end 
                if ( orgtypeName.equals(desttypeName) && getPropertyUtils().isReadable(orig, name) &&
                    getPropertyUtils().isWriteable(dest, name)) {
                    try {
                        Object value =
                            getPropertyUtils().getSimpleProperty(orig, name);
                        copyProperty(dest, name, value);
                    } catch (NoSuchMethodException e) {
                        ; // Should not happen
                    }
                }
            }
        }

    }

    public void copyProperty(Object bean, String name, Object value)
    throws IllegalAccessException, InvocationTargetException {

    // Trace logging (if enabled)
    if (log.isTraceEnabled()) {
        StringBuffer sb = new StringBuffer("  copyProperty(");
        sb.append(bean);
        sb.append(", ");
        sb.append(name);
        sb.append(", ");
        if (value == null) {
            sb.append("<NULL>");
        } else if (value instanceof String) {
            sb.append((String) value);
        } else if (value instanceof String[]) {
            String values[] = (String[]) value;
            sb.append('[');
            for (int i = 0; i < values.length; i++) {
                if (i > 0) {
                    sb.append(',');
                }
                sb.append(values[i]);
            }
            sb.append(']');
        } else {
            sb.append(value.toString());
        }
        sb.append(')');
        log.trace(sb.toString());
    }

    // Resolve any nested expression to get the actual target bean
    Object target = bean;
    int delim = name.lastIndexOf(PropertyUtils.NESTED_DELIM);
    if (delim >= 0) {
        try {
            target =
                getPropertyUtils().getProperty(bean, name.substring(0, delim));
        } catch (NoSuchMethodException e) {
            return; // Skip this property setter
        }
        name = name.substring(delim + 1);
        if (log.isTraceEnabled()) {
            log.trace("    Target bean = " + target);
            log.trace("    Target name = " + name);
        }
    }

    // Declare local variables we will require
    String propName = null;          // Simple name of target property
    Class type = null;               // Java type of target property
    int index = -1;                  // Indexed subscript value (if any)
    String key = null;               // Mapped key value (if any)

    // Calculate the target property name, index, and key values
    propName = name;
    int i = propName.indexOf(PropertyUtils.INDEXED_DELIM);
    if (i >= 0) {
        int k = propName.indexOf(PropertyUtils.INDEXED_DELIM2);
        try {
            index =
                Integer.parseInt(propName.substring(i + 1, k));
        } catch (NumberFormatException e) {
            ;
        }
        propName = propName.substring(0, i);
    }
    int j = propName.indexOf(PropertyUtils.MAPPED_DELIM);
    if (j >= 0) {
        int k = propName.indexOf(PropertyUtils.MAPPED_DELIM2);
        try {
            key = propName.substring(j + 1, k);
        } catch (IndexOutOfBoundsException e) {
            ;
        }
        propName = propName.substring(0, j);
    }

    // Calculate the target property type
    if (target instanceof DynaBean) {
        DynaClass dynaClass = ((DynaBean) target).getDynaClass();
        DynaProperty dynaProperty = dynaClass.getDynaProperty(propName);
        if (dynaProperty == null) {
            return; // Skip this property setter
        }
        type = dynaProperty.getType();
    } else {
        PropertyDescriptor descriptor = null;
        try {
            descriptor =
                getPropertyUtils().getPropertyDescriptor(target, name);
            if (descriptor == null) {
                return; // Skip this property setter
            }
        } catch (NoSuchMethodException e) {
            return; // Skip this property setter
        }
        type = descriptor.getPropertyType();
        if (type == null) {
            // Most likely an indexed setter on a POJB only
            if (log.isTraceEnabled()) {
                log.trace("    target type for property '" +
                          propName + "' is null, so skipping ths setter");
            }
            return;
        }
    }
    if (log.isTraceEnabled()) {
        log.trace("    target propName=" + propName + ", type=" +
                  type + ", index=" + index + ", key=" + key);
    }

    // Convert the specified value to the required type and store it
    if (index >= 0) {                    // Destination must be indexed
        Converter converter = getConvertUtils().lookup(type.getComponentType());
        if (converter != null) {
            log.trace("        USING CONVERTER " + converter);
            value = converter.convert(type, value);
        }
        try {
            getPropertyUtils().setIndexedProperty(target, propName,
                                             index, value);
        } catch (NoSuchMethodException e) {
            throw new InvocationTargetException
                (e, "Cannot set " + propName);
        }
    } else if (key != null) {            // Destination must be mapped
        // Maps do not know what the preferred data type is,
        // so perform no conversions at all
        // FIXME - should we create or support a TypedMap?
        try {
            getPropertyUtils().setMappedProperty(target, propName,
                                            key, value);
        } catch (NoSuchMethodException e) {
            throw new InvocationTargetException
                (e, "Cannot set " + propName);
        }
    } else {                             // Destination must be simple
        Converter converter = getConvertUtils().lookup(type);
        if (converter != null) {
            log.trace("        USING CONVERTER " + converter);
            value = converter.convert(type, value);
        }
        try {
            getPropertyUtils().setSimpleProperty(target, propName, value);
        } catch (NoSuchMethodException e) {
            throw new InvocationTargetException
                (e, "Cannot set " + propName);
        }
    }

}


/**
 * <p>Return the entire set of properties for which the specified bean
 * provides a read method. This map contains the to <code>String</code>
 * converted property values for all properties for which a read method
 * is provided (i.e. where the getReadMethod() returns non-null).</p>
 *
 * <p>This map can be fed back to a call to
 * <code>BeanUtils.populate()</code> to reconsitute the same set of
 * properties, modulo differences for read-only and write-only
 * properties, but only if there are no indexed properties.</p>
 *
 * @param bean Bean whose properties are to be extracted
 *
 * @exception IllegalAccessException if the caller does not have
 *  access to the property accessor method
 * @exception InvocationTargetException if the property accessor method
 *  throws an exception
 * @exception NoSuchMethodException if an accessor method for this
 *  property cannot be found
 */
public Map describe(Object bean)
        throws IllegalAccessException, InvocationTargetException,
        NoSuchMethodException {

    if (bean == null) {
    //            return (Collections.EMPTY_MAP);
        return (new java.util.HashMap());
    }
    
    if (log.isDebugEnabled()) {
        log.debug("Describing bean: " + bean.getClass().getName());
    }
        
    Map description = new HashMap();
    if (bean instanceof DynaBean) {
        DynaProperty descriptors[] =
            ((DynaBean) bean).getDynaClass().getDynaProperties();
        for (int i = 0; i < descriptors.length; i++) {
            String name = descriptors[i].getName();
            description.put(name, getProperty(bean, name));
        }
    } else {
        PropertyDescriptor descriptors[] =
            getPropertyUtils().getPropertyDescriptors(bean);
        for (int i = 0; i < descriptors.length; i++) {
            String name = descriptors[i].getName();
            if (descriptors[i].getReadMethod() != null)
                description.put(name, getProperty(bean, name));
        }
    }
    return (description);

}


/**
 * Return the value of the specified array property of the specified
 * bean, as a String array.
 *
 * @param bean Bean whose property is to be extracted
 * @param name Name of the property to be extracted
 *
 * @exception IllegalAccessException if the caller does not have
 *  access to the property accessor method
 * @exception InvocationTargetException if the property accessor method
 *  throws an exception
 * @exception NoSuchMethodException if an accessor method for this
 *  property cannot be found
 */
public String[] getArrayProperty(Object bean, String name)
        throws IllegalAccessException, InvocationTargetException,
        NoSuchMethodException {

    Object value = getPropertyUtils().getProperty(bean, name);
    if (value == null) {
        return (null);
    } else if (value instanceof Collection) {
        ArrayList values = new ArrayList();
        Iterator items = ((Collection) value).iterator();
        while (items.hasNext()) {
            Object item = items.next();
            if (item == null) {
                values.add((String) null);
            } else {
                // convert to string using convert utils
                values.add(getConvertUtils().convert(item));
            }
        }
        return ((String[]) values.toArray(new String[values.size()]));
    } else if (value.getClass().isArray()) {
        int n = Array.getLength(value);
        String results[] = new String[n];
        for (int i = 0; i < n; i++) {
            Object item = Array.get(value, i);
            if (item == null) {
                results[i] = null;
            } else {
                // convert to string using convert utils
                results[i] = getConvertUtils().convert(item);
            }
        }
        return (results);
    } else {
        String results[] = new String[1];
        results[0] = value.toString();
        return (results);
    }

}


/**
 * Return the value of the specified indexed property of the specified
 * bean, as a String.  The zero-relative index of the
 * required value must be included (in square brackets) as a suffix to
 * the property name, or <code>IllegalArgumentException</code> will be
 * thrown.
 *
 * @param bean Bean whose property is to be extracted
 * @param name <code>propertyname[index]</code> of the property value
 *  to be extracted
 *
 * @exception IllegalAccessException if the caller does not have
 *  access to the property accessor method
 * @exception InvocationTargetException if the property accessor method
 *  throws an exception
 * @exception NoSuchMethodException if an accessor method for this
 *  property cannot be found
 */
public String getIndexedProperty(Object bean, String name)
        throws IllegalAccessException, InvocationTargetException,
        NoSuchMethodException {

    Object value = getPropertyUtils().getIndexedProperty(bean, name);
    return (getConvertUtils().convert(value));

}


/**
 * Return the value of the specified indexed property of the specified
 * bean, as a String.  The index is specified as a method parameter and
 * must *not* be included in the property name expression
 *
 * @param bean Bean whose property is to be extracted
 * @param name Simple property name of the property value to be extracted
 * @param index Index of the property value to be extracted
 *
 * @exception IllegalAccessException if the caller does not have
 *  access to the property accessor method
 * @exception InvocationTargetException if the property accessor method
 *  throws an exception
 * @exception NoSuchMethodException if an accessor method for this
 *  property cannot be found
 */
public String getIndexedProperty(Object bean,
                                        String name, int index)
        throws IllegalAccessException, InvocationTargetException,
        NoSuchMethodException {

    Object value = getPropertyUtils().getIndexedProperty(bean, name, index);
    return (getConvertUtils().convert(value));

}


/**
 * Return the value of the specified indexed property of the specified
 * bean, as a String.  The String-valued key of the required value
 * must be included (in parentheses) as a suffix to
 * the property name, or <code>IllegalArgumentException</code> will be
 * thrown.
 *
 * @param bean Bean whose property is to be extracted
 * @param name <code>propertyname(index)</code> of the property value
 *  to be extracted
 *
 * @exception IllegalAccessException if the caller does not have
 *  access to the property accessor method
 * @exception InvocationTargetException if the property accessor method
 *  throws an exception
 * @exception NoSuchMethodException if an accessor method for this
 *  property cannot be found
 */
public String getMappedProperty(Object bean, String name)
        throws IllegalAccessException, InvocationTargetException,
        NoSuchMethodException {

    Object value = getPropertyUtils().getMappedProperty(bean, name);
    return (getConvertUtils().convert(value));

}


/**
 * Return the value of the specified mapped property of the specified
 * bean, as a String.  The key is specified as a method parameter and
 * must *not* be included in the property name expression
 *
 * @param bean Bean whose property is to be extracted
 * @param name Simple property name of the property value to be extracted
 * @param key Lookup key of the property value to be extracted
 *
 * @exception IllegalAccessException if the caller does not have
 *  access to the property accessor method
 * @exception InvocationTargetException if the property accessor method
 *  throws an exception
 * @exception NoSuchMethodException if an accessor method for this
 *  property cannot be found
 */
public String getMappedProperty(Object bean,
                                       String name, String key)
        throws IllegalAccessException, InvocationTargetException,
        NoSuchMethodException {

    Object value = getPropertyUtils().getMappedProperty(bean, name, key);
    return (getConvertUtils().convert(value));

}


/**
 * Return the value of the (possibly nested) property of the specified
 * name, for the specified bean, as a String.
 *
 * @param bean Bean whose property is to be extracted
 * @param name Possibly nested name of the property to be extracted
 *
 * @exception IllegalAccessException if the caller does not have
 *  access to the property accessor method
 * @exception IllegalArgumentException if a nested reference to a
 *  property returns null
 * @exception InvocationTargetException if the property accessor method
 *  throws an exception
 * @exception NoSuchMethodException if an accessor method for this
 *  property cannot be found
 */
public String getNestedProperty(Object bean, String name)
        throws IllegalAccessException, InvocationTargetException,
        NoSuchMethodException {

    Object value = getPropertyUtils().getNestedProperty(bean, name);
    return (getConvertUtils().convert(value));

}


/**
 * Return the value of the specified property of the specified bean,
 * no matter which property reference format is used, as a String.
 *
 * @param bean Bean whose property is to be extracted
 * @param name Possibly indexed and/or nested name of the property
 *  to be extracted
 *
 * @exception IllegalAccessException if the caller does not have
 *  access to the property accessor method
 * @exception InvocationTargetException if the property accessor method
 *  throws an exception
 * @exception NoSuchMethodException if an accessor method for this
 *  property cannot be found
 */
public String getProperty(Object bean, String name)
        throws IllegalAccessException, InvocationTargetException,
        NoSuchMethodException {

    return (getNestedProperty(bean, name));

}


/**
 * Return the value of the specified simple property of the specified
 * bean, converted to a String.
 *
 * @param bean Bean whose property is to be extracted
 * @param name Name of the property to be extracted
 *
 * @exception IllegalAccessException if the caller does not have
 *  access to the property accessor method
 * @exception InvocationTargetException if the property accessor method
 *  throws an exception
 * @exception NoSuchMethodException if an accessor method for this
 *  property cannot be found
 */
public String getSimpleProperty(Object bean, String name)
        throws IllegalAccessException, InvocationTargetException,
        NoSuchMethodException {

    Object value = getPropertyUtils().getSimpleProperty(bean, name);
    return (getConvertUtils().convert(value));

}


/**
 * <p>Populate the JavaBeans properties of the specified bean, based on
 * the specified name/value pairs.  This method uses Java reflection APIs
 * to identify corresponding "property setter" method names, and deals
 * with setter arguments of type <code>String</code>, <code>boolean</code>,
 * <code>int</code>, <code>long</code>, <code>float</code>, and
 * <code>double</code>.  In addition, array setters for these types (or the
 * corresponding primitive types) can also be identified.</p>
 * 
 * <p>The particular setter method to be called for each property is
 * determined using the usual JavaBeans introspection mechanisms.  Thus,
 * you may identify custom setter methods using a BeanInfo class that is
 * associated with the class of the bean itself.  If no such BeanInfo
 * class is available, the standard method name conversion ("set" plus
 * the capitalized name of the property in question) is used.</p>
 * 
 * <p><strong>NOTE</strong>:  It is contrary to the JavaBeans Specification
 * to have more than one setter method (with different argument
 * signatures) for the same property.</p>
 *
 * <p><strong>WARNING</strong> - The logic of this method is customized
 * for extracting String-based request parameters from an HTTP request.
 * It is probably not what you want for general property copying with
 * type conversion.  For that purpose, check out the
 * <code>copyProperties()</code> method instead.</p>
 *
 * @param bean JavaBean whose properties are being populated
 * @param properties Map keyed by property name, with the
 *  corresponding (String or String[]) value(s) to be set
 *
 * @exception IllegalAccessException if the caller does not have
 *  access to the property accessor method
 * @exception InvocationTargetException if the property accessor method
 *  throws an exception
 */
public void populate(Object bean, Map properties)
    throws IllegalAccessException, InvocationTargetException {

    // Do nothing unless both arguments have been specified
    if ((bean == null) || (properties == null)) {
        return;
    }
    if (log.isDebugEnabled()) {
        log.debug("BeanUtils.populate(" + bean + ", " +
                properties + ")");
    }

    // Loop through the property name/value pairs to be set
    Iterator names = properties.keySet().iterator();
    while (names.hasNext()) {

        // Identify the property name and value(s) to be assigned
        String name = (String) names.next();
        if (name == null) {
            continue;
        }
        Object value = properties.get(name);

        // Perform the assignment for this property
        setProperty(bean, name, value);

    }

}


/**
 * <p>Set the specified property value, performing type conversions as
 * required to conform to the type of the destination property.</p>
 *
 * <p>If the property is read only then the method returns 
 * without throwing an exception.</p>
 *
 * <p>If <code>null</code> is passed into a property expecting a primitive value,
 * then this will be converted as if it were a <code>null</code> string.</p>
 *
 * <p><strong>WARNING</strong> - The logic of this method is customized
 * to meet the needs of <code>populate()</code>, and is probably not what
 * you want for general property copying with type conversion.  For that
 * purpose, check out the <code>copyProperty()</code> method instead.</p>
 *
 * <p><strong>WARNING</strong> - PLEASE do not modify the behavior of this
 * method without consulting with the Struts developer community.  There
 * are some subtleties to its functionality that are not documented in the
 * Javadoc description above, yet are vital to the way that Struts utilizes
 * this method.</p>
 *
 * @param bean Bean on which setting is to be performed
 * @param name Property name (can be nested/indexed/mapped/combo)
 * @param value Value to be set
 *
 * @exception IllegalAccessException if the caller does not have
 *  access to the property accessor method
 * @exception InvocationTargetException if the property accessor method
 *  throws an exception
 */
public void setProperty(Object bean, String name, Object value)
    throws IllegalAccessException, InvocationTargetException {

    // Trace logging (if enabled)
    if (log.isTraceEnabled()) {
        StringBuffer sb = new StringBuffer("  setProperty(");
        sb.append(bean);
        sb.append(", ");
        sb.append(name);
        sb.append(", ");
        if (value == null) {
            sb.append("<NULL>");
        } else if (value instanceof String) {
            sb.append((String) value);
        } else if (value instanceof String[]) {
            String values[] = (String[]) value;
            sb.append('[');
            for (int i = 0; i < values.length; i++) {
                if (i > 0) {
                    sb.append(',');
                }
                sb.append(values[i]);
            }
            sb.append(']');
        } else {
            sb.append(value.toString());
        }
        sb.append(')');
        log.trace(sb.toString());
    }

    // Resolve any nested expression to get the actual target bean
    Object target = bean;
    int delim = findLastNestedIndex(name);
    if (delim >= 0) {
        try {
            target =
                getPropertyUtils().getProperty(bean, name.substring(0, delim));
        } catch (NoSuchMethodException e) {
            return; // Skip this property setter
        }
        name = name.substring(delim + 1);
        if (log.isTraceEnabled()) {
            log.trace("    Target bean = " + target);
            log.trace("    Target name = " + name);
        }
    }

    // Declare local variables we will require
    String propName = null;          // Simple name of target property
    Class type = null;               // Java type of target property
    int index = -1;                  // Indexed subscript value (if any)
    String key = null;               // Mapped key value (if any)

    // Calculate the property name, index, and key values
    propName = name;
    int i = propName.indexOf(PropertyUtils.INDEXED_DELIM);
    if (i >= 0) {
        int k = propName.indexOf(PropertyUtils.INDEXED_DELIM2);
        try {
            index =
                Integer.parseInt(propName.substring(i + 1, k));
        } catch (NumberFormatException e) {
            ;
        }
        propName = propName.substring(0, i);
    }
    int j = propName.indexOf(PropertyUtils.MAPPED_DELIM);
    if (j >= 0) {
        int k = propName.indexOf(PropertyUtils.MAPPED_DELIM2);
        try {
            key = propName.substring(j + 1, k);
        } catch (IndexOutOfBoundsException e) {
            ;
        }
        propName = propName.substring(0, j);
    }

    // Calculate the property type
    if (target instanceof DynaBean) {
        DynaClass dynaClass = ((DynaBean) target).getDynaClass();
        DynaProperty dynaProperty = dynaClass.getDynaProperty(propName);
        if (dynaProperty == null) {
            return; // Skip this property setter
        }
        type = dynaProperty.getType();
    } else {
        PropertyDescriptor descriptor = null;
        try {
            descriptor =
                getPropertyUtils().getPropertyDescriptor(target, name);
            if (descriptor == null) {
                return; // Skip this property setter
            }
        } catch (NoSuchMethodException e) {
            return; // Skip this property setter
        }
        if (descriptor instanceof MappedPropertyDescriptor) {
            if (((MappedPropertyDescriptor) descriptor).getMappedWriteMethod() == null) {
                if (log.isDebugEnabled()) {
                    log.debug("Skipping read-only property");
                }
                return; // Read-only, skip this property setter
            }
            type = ((MappedPropertyDescriptor) descriptor).
                getMappedPropertyType();
        } else if (descriptor instanceof IndexedPropertyDescriptor) {
            if (((IndexedPropertyDescriptor) descriptor).getIndexedWriteMethod() == null) {
                if (log.isDebugEnabled()) {
                    log.debug("Skipping read-only property");
                }
                return; // Read-only, skip this property setter
            }
            type = ((IndexedPropertyDescriptor) descriptor).
                getIndexedPropertyType();
        } else {
            if (descriptor.getWriteMethod() == null) {
                if (log.isDebugEnabled()) {
                    log.debug("Skipping read-only property");
                }
                return; // Read-only, skip this property setter
            }
            type = descriptor.getPropertyType();
        }
    }

    // Convert the specified value to the required type
    Object newValue = null;
    if (type.isArray() && (index < 0)) { // Scalar value into array
        if (value == null) {
            String values[] = new String[1];
            values[0] = (String) value;
            newValue = getConvertUtils().convert((String[]) values, type);
        } else if (value instanceof String) {
            String values[] = new String[1];
            values[0] = (String) value;
            newValue = getConvertUtils().convert((String[]) values, type);
        } else if (value instanceof String[]) {
            newValue = getConvertUtils().convert((String[]) value, type);
        } else {
            newValue = value;
        }
    } else if (type.isArray()) {         // Indexed value into array
        if (value instanceof String) {
            newValue = getConvertUtils().convert((String) value,
                                            type.getComponentType());
        } else if (value instanceof String[]) {
            newValue = getConvertUtils().convert(((String[]) value)[0],
                                            type.getComponentType());
        } else {
            newValue = value;
        }
    } else {                             // Value into scalar
        if ((value instanceof String) || (value == null)) {
            newValue = getConvertUtils().convert((String) value, type);
        } else if (value instanceof String[]) {
            newValue = getConvertUtils().convert(((String[]) value)[0],
                                            type);
        } else if (getConvertUtils().lookup(value.getClass()) != null) {
            newValue = getConvertUtils().convert(value.toString(), type);
        } else {
            newValue = value;
        }
    }

    // Invoke the setter method
    try {
        if (index >= 0) {
            getPropertyUtils().setIndexedProperty(target, propName,
                                             index, newValue);
        } else if (key != null) {
            getPropertyUtils().setMappedProperty(target, propName,
                                            key, newValue);
        } else {
            getPropertyUtils().setProperty(target, propName, newValue);
        }
    } catch (NoSuchMethodException e) {
        throw new InvocationTargetException
            (e, "Cannot set " + propName);
    }

}

private int findLastNestedIndex(String expression)
{
    // walk back from the end to the start 
    // and find the first index that 
    int bracketCount = 0;
    for (int i = expression.length() - 1; i>=0 ; i--) {
        char at = expression.charAt(i);
        switch (at) {
            case PropertyUtils.NESTED_DELIM:
                if (bracketCount < 1) {
                    return i;
                }
                break;
                
            case PropertyUtils.MAPPED_DELIM:
            case PropertyUtils.INDEXED_DELIM:
                // not bothered which
                --bracketCount;
                break;
            
            case PropertyUtils.MAPPED_DELIM2:
            case PropertyUtils.INDEXED_DELIM2:
                // not bothered which
                ++bracketCount;
                break;            
        }
    }
    // can't find any
    return -1;
}
    /** 
     * Gets the <code>ConvertUtilsBean</code> instance used to perform the conversions.
     */
    public ConvertUtilsBean getConvertUtils() {
        return convertUtilsBean;
    }
    
    /**
     * Gets the <code>PropertyUtilsBean</code> instance used to access properties.
     */
    public PropertyUtilsBean getPropertyUtils() {
        return (TPropertyUtilsBean)propertyUtilsBean;
    }
}
