package cn.edu.fudan.se.test;

import org.junit.Test;

import apiChangeExtractor.GumTreeExtractor;

public class GumTest {
	String repositoryPath1 = "D:/javaee/parser/ApiChangeExtractor";
	String repositoryPath2 = "D:/github/ChangeExtractor";
	String repositoryPath3 = "D:/github/SEDataExtractor";
	String repositoryPath4 = "D:/javaee/LykProject";
	String repositoryPath5 = "D:/github/checkstyle";
	GumTreeExtractor extractor = new GumTreeExtractor(repositoryPath1,-1);
	@Test
	public void testExtract(){
		extractor.extractActions();
	}
}
