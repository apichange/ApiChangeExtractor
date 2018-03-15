package apiChangeExtractor.task;

import java.util.concurrent.Callable;

import apiChangeExtractor.GumTreeExtractor;
import apiChangeExtractor.mybatis.bean.ExistRepository;

public class ExistRepositoryTask implements Callable<String> {
	private ExistRepository repository;
	
	public ExistRepositoryTask(){
	}
	
	public ExistRepositoryTask(ExistRepository repository){
		this.repository = repository;
	}
	
	public ExistRepository getRepository() {
		return repository;
	}

	public void setRepository(ExistRepository repository) {
		this.repository = repository;
	}

	@Override
	public String call() throws Exception {
		GumTreeExtractor extractor = new GumTreeExtractor(repository);
		extractor.extractActions();
		extractor.clearSource();
		
		StringBuilder builder = new StringBuilder();
		builder.append(repository.getRepositoryId());
		return builder.toString();
	}
}