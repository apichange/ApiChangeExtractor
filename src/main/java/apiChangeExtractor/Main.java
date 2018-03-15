package apiChangeExtractor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import apiChangeExtractor.mybatis.bean.ExistRepository;
import apiChangeExtractor.task.ExistRepositoryTask;
import apiChangeExtractor.util.FileUtils;

public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	private LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
	private ExecutorService service = new MyThreadPool(4, 4, 0, TimeUnit.MINUTES, queue);
	
	public static void main(String[] args) {
		Main main = new Main();
		main.startByFile(args[0]);
	}
	
	public void startByFile(String file){
		List<ExistRepository> list = getDataByFile(file);
		try {
			extractExistRepositoriesByGumTree(list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn(e.getMessage());
		}	
		shutdownMain();
	}
	
	public void shutdownMain(){
		service.shutdown();
	}
	
	private List<ExistRepository> getDataByFile(String path){
		List<String> pathList = FileUtils.getPathByFile(path);
		List<ExistRepository> list = new ArrayList<>(); 
		for(int i = 0; i < pathList.size(); i++){
			ExistRepository e = new ExistRepository();
			e.setRepositoryId(i);
			e.setAddress(pathList.get(i));
			list.add(e);
		}
		return list;
	}
	
	public void extractExistRepositoriesByGumTree(List<ExistRepository> list){
		for(ExistRepository r : list){
			ExistRepositoryTask task = new ExistRepositoryTask(r);
			service.submit(task);
		}
	}
}
