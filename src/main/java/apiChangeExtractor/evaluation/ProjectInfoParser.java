package apiChangeExtractor.evaluation;

import java.io.IOException;

import apiChangeExtractor.gitReader.GitReader;
import apiChangeExtractor.mybatis.bean.ExistRepository;
import apiChangeExtractor.mybatis.bean.ProjectInfo;
import apiChangeExtractor.mybatis.dao.ProjectInfoDao;

public class ProjectInfoParser {
	ExistRepository repository;
	public ProjectInfoParser(ExistRepository repository){
		this.repository = repository;
	}
	public void parse(){
		GitReader gitReader = new GitReader(repository.getAddress());
		try {
			gitReader.init();
		} catch (IOException e) {
			gitReader=null;
		}
		if(gitReader == null) return;
		
		int[] loc = ClocCounter.getLoc(repository.getAddress());
		
		int commits;
		if(gitReader.getCommits()==null){
			commits=0;
		}else{
			commits = gitReader.getCommits().size();
		}
		
		int bugCommits;
		if(gitReader.getCommitsAboutBug()==null){
			bugCommits=0;
		}else{
			bugCommits = gitReader.getCommitsAboutBug().size();
		}
		ProjectInfo result = new ProjectInfo(repository.getId(), repository.getWebsite(), Integer.parseInt(repository.getStars()), loc[0], loc[1], loc[2], commits, bugCommits);
		ProjectInfoDao.getInstance().insertProjectInfo(result);
	}
	public void updateMoreFive(){
		GitReader gitReader = new GitReader(repository.getAddress());
		try {
			gitReader.init();
		} catch (IOException e) {
			gitReader=null;
		}
		if(gitReader == null) return;
		
		ProjectInfo info = new ProjectInfo();
		info.setRepositoryId(repository.getId());
		info.setMoreFiveBugCommits(gitReader.getMoveFive());
		ProjectInfoDao.getInstance().updateProjectInfoByMoreFive(info);
	}
}
