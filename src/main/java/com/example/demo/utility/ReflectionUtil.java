package com.example.demo.utility;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionUtil {

	private static Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

	private static ReflectionUtil refObj = null;

	private ReflectionUtil() {

	}

	public static ReflectionUtil getInstance() {
		logger.info("**************Inside getInstance Method***********************");
		if (refObj == null) {
			refObj = new ReflectionUtil();
			setUpMethods();
		}
		return refObj;
	}

	private static HashMap<String, String> objectBeanMap = new HashMap<String, String>() {
		{
			put("College", "com.example.demo.beans.College");
			put("Person", "com.example.demo.beans.Person");
			put("Son", "com.example.demo.beans.Son");
			put("Daughter", "com.example.demo.beans.Daughter");
			put("PanCard", "com.example.demo.beans.PanCard");
			put("AadharCard", "com.example.demo.beans.AadharCard");
		}
	};

	private static HashMap<String, HashMap> objGetterPropsMap = new HashMap<String, HashMap>();
	private static HashMap<String, HashMap> objSetterPropsMap = new HashMap<String, HashMap>();

	public static void setUpMethods() {
		logger.info("***********************Inside setUpMethod************************");
		for (Iterator iterator = objectBeanMap.keySet().iterator(); iterator.hasNext();) {
			String objName = (String) iterator.next();

			HashMap<String, Method> propsGetMethodMap = new HashMap<String, Method>();
			HashMap<String, Method> propsSetMethodMap = new HashMap<String, Method>();

			try {
				Class<?> cls = Class.forName(objectBeanMap.get(objName));
				for (PropertyDescriptor pd : Introspector.getBeanInfo(cls).getPropertyDescriptors()) {
					if (!"class".equals(pd.getName())) {
						if (pd.getReadMethod() != null) {
							propsGetMethodMap.put(pd.getName(), pd.getReadMethod());
						}
						if (pd.getWriteMethod() != null) {
							propsSetMethodMap.put(pd.getName(), pd.getWriteMethod());
						}
					}
				}

			} catch (final ClassNotFoundException | IntrospectionException ex) {
				logger.error("Inside catch Block of setUpMethod");
			}

			objGetterPropsMap.put(objName, propsGetMethodMap);
			objSetterPropsMap.put(objName, propsSetMethodMap);

		}

	}

	public HashMap<String, HashMap> getObjGetterPropsMap() {
		return objGetterPropsMap;
	}

	public HashMap<String, HashMap> getObjSetterPropsMap() {
		return objSetterPropsMap;
	}

	public Method getSetterMethod(String objectName, String propName) {
		HashMap propMethod = getObjSetterPropsMap().get(objectName);
		return (Method) propMethod.get(propName);
	}

	public Method getGetterMethod(String objectName, String propName) {
		HashMap propMethod = getObjGetterPropsMap().get(objectName);
		return (Method) propMethod.get(propName);
	}

}
