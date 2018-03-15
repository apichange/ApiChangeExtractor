package apiChangeExtractor.task;

import java.util.concurrent.Callable;

import apiChangeExtractor.GumTreeExtractor;
import apiChangeExtractor.mybatis.bean.Repository;

public class GumTreeParseTask implements Callable<String> {
	private Repository repository;
	
	public GumTreeParseTask(){
	}
	
	public GumTreeParseTask(Repository repository){
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
		GumTreeExtractor extractor = new GumTreeExtractor(repository);
		extractor.extractActions();
		StringBuilder builder = new StringBuilder();
		builder.append(repository.getRepositoryId());
		return builder.toString();
	}
}
