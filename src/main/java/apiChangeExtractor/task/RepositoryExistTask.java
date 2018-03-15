package apiChangeExtractor.task;

import java.io.IOException;
import java.util.concurrent.Callable;

import apiChangeExtractor.gitReader.GitReader;
import apiChangeExtractor.mybatis.bean.ExistRepository;
import apiChangeExtractor.mybatis.bean.Repository;
import apiChangeExtractor.mybatis.dao.ExistRepositoryDao;
import apiChangeExtractor.util.PathUtils;

public class RepositoryExistTask implements Callable<String> {
	private Repository repository;
	
	public RepositoryExistTask(){
	}
	
	public RepositoryExistTask(Repository repository){
		this.repository = repository;
	}
	
	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	@Override
	public String call() throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append(repository.getRepositoryId());
		ExistRepositoryDao dao = new ExistRepositoryDao();
		
		GitReader gitReader = new GitReader(PathUtils.changeWebsite2Path("/home/fdse/Downloads/GithubJavaRepositories/", repository.getWebsite()));
		try {
			gitReader.init();
			dao.insertOneExistRepository(new ExistRepository(repository.getRepositoryId(),Integer.parseInt(repository.getGithubId()),repository.getRepositoryName(),Integer.parseInt(repository.getUserId()),repository.getUserName(),repository.getWebsite(),repository.getStars(),PathUtils.changeWebsite2Path("/home/fdse/Downloads/GithubJavaRepositories/", repository.getWebsite())));
			return builder.toString();
		} catch (IOException e) {
		}
		gitReader = new GitReader(PathUtils.changeWebsite2Path("/home/fdse/Downloads/GithubJavaRepositories2/", repository.getWebsite()));
		try {
			gitReader.init();
			dao.insertOneExistRepository(new ExistRepository(repository.getRepositoryId(),Integer.parseInt(repository.getGithubId()),repository.getRepositoryName(),Integer.parseInt(repository.getUserId()),repository.getUserName(),repository.getWebsite(),repository.getStars(),PathUtils.changeWebsite2Path("/home/fdse/Downloads/GithubJavaRepositories2/", repository.getWebsite())));
		} catch (IOException e) {
		}
		return builder.toString();
	}
}