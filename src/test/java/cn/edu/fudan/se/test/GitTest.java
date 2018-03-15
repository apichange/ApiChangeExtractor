package cn.edu.fudan.se.test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

import apiChangeExtractor.bean.ChangeFile;
import apiChangeExtractor.bean.ChangeLine;
import apiChangeExtractor.gitReader.GitReader;
import apiChangeExtractor.util.FileUtils;

public class GitTest {
	String repositoryPath1 = "D:/javaee/parser/ApiChangeExtractor";
	String repositoryPath2 = "D:/github/ChangeExtractor";
	String repositoryPath3 = "D:/github/spring-framework";
	String repositoryPath4 = "D:/github/h2o-3";
	String repositoryPath5 = "D:/github/checkstyle";
	String repositoryPath6 = "D:/github/ApiChangePattern";
	String repositoryPath7 = "D:/github/graylog2-server";
	GitReader reader = new GitReader(repositoryPath1);
	GitReader reader5 = new GitReader(repositoryPath5);
	GitReader reader6 = new GitReader(repositoryPath6);
	GitReader reader7 = new GitReader(repositoryPath7);
	
	public void init(GitReader r){
		try {
			r.init();
		} catch (IOException e) {
			e.printStackTrace();
			r =null;
		}
	}
	@Test
	public void testGetLastCommit(){
		init(reader7);
		RevCommit last = reader7.getLastCommit();
		System.out.println(last.getFullMessage());
		System.out.println("===================================================");
		System.out.println(last.getShortMessage());
	}
	
	@Test
	public void testWalkCommit(){
		init(reader);
		RevCommit last = reader.getLastCommit();
		reader.walkCommit(last);
	}
	
	@Test
	public void testGetChangeFiles(){
		init(reader);
		RevCommit last = reader.getLastCommit();
		reader.getChangeFiles(last);
	}
	
	@Test
	public void testGetFileContentByCommitId(){
		init(reader);
		reader.getFileContentByCommitId("69d371c442e74bd03ff56a0f4cdb220599c0167a", "src/test/resources/com/puppycrawl/tools/checkstyle/checks/misc/avoidescapedunicodecharacters/InputAllEscapedUnicodeCharacters.java");
	}
	
	@Test
	public void testGetCommits(){
		init(reader6);
		System.out.println(reader6.getCommits().size());
	}
	
	@Test
	public void testGetTwoRevision(){
		init(reader7);
		String userDirPath = System.getProperty("user.dir");
		String tempDirPath = userDirPath + "/" + UUID.randomUUID().toString();
		File tempDir = new File(tempDirPath);
		tempDir.mkdirs();
		String f = "graylog2-server/src/main/java/org/graylog2/rest/resources/roles/RolesResource.java";
		byte[] newContent = reader7.getFileContentByCommitId("6dfb1b164c84a6cb138209d329545edbcac615ed", f);
		byte[] oldContent = reader7.getFileContentByCommitId("dcb1d941d2fad6513ba17fccd7d5abaf40eb02f0", f);
		File newFile = FileUtils.writeBytesToFile(newContent, tempDirPath, "RolesResource.v1");
		File oldFile = FileUtils.writeBytesToFile(oldContent, tempDirPath, "RolesResource.v2");
	}
	
	@Test
	public void testSeeDiff(){
		init(reader);
		reader.getChangeFiles(reader.getOneCommit("4f3a70d1ab940e38d445a3cf73fcc74c8c248831"));
	}
	@Test
	public void testAddOrDelete(){
		init(reader);
		for(RevCommit c : reader.getCommits()){
			System.out.println(c.getId()+"=================================================================================================================");
			reader.getChangeFilesId(c);
		}
	}
	@Test
	public void testSeeChangeFile(){
		init(reader);
		List<ChangeFile> files = reader.getChangeFiles(reader.getOneCommit("4f3a70d1ab940e38d445a3cf73fcc74c8c248831"));
		for(ChangeFile f:files){
			System.out.println("================================================================================================================================================");
			printChangeFile(f);
		}
	}
	
	@Test
	public void testCommitFilter(){
		init(reader5);
		System.out.println(t(reader5.getOneCommit("16f91dda1a15e0e3c3d7142a159f17db72c06d0f").getFullMessage()));
//		for(RevCommit c : reader5.getCommits()){
//			System.out.println(c.getId()+"=================================================================================================================");
//
//			System.out.println(t(c.getFullMessage()));
//		}
	}
	public String t(String s){
		return s+"\n"+reader.isAboutBug(s);
	}
	
	public void printChangeFile(ChangeFile f){
		System.out.println("++++ "+f.getNewPath());
		System.out.println("---- "+f.getOldPath());
		for(ChangeLine l: f.getChangeLines()){
			if(!"CONTENT".equals(l.getType()))
				System.out.println(l.getLineNum()+"   "+l.getSequence());
			else
				System.out.println(l.getLineNum()+"/"+l.getOldNum()+"   "+l.getSequence());
		}
	}
	
	@Test
	public void testCountCode(){
		init(reader5);
//		System.out.println(reader5.getOneCommit("16f91dda1a15e0e3c3d7142a159f17db72c06d0f").getFullMessage());
		reader5.getChangeFilesId(reader5.getOneCommit("16f91dda1a15e0e3c3d7142a159f17db72c06d0f"));
	}
}
