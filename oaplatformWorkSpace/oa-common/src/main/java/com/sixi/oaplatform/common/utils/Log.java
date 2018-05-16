package com.sixi.oaplatform.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
	public static Logger getLogger(Class<?> cla){
		return LoggerFactory.getLogger(cla);
	}
}
