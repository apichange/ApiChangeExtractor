package cn.edu.fudan.se.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {

	private static final Logger logger = LoggerFactory.getLogger(LogTest.class);
	@Test
	public void testExtract(){
		logger.warn("test");
	}
}
