package cn.edu.fudan.se.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import apiChangeExtractor.mybatis.bean.Api;
import apiChangeExtractor.mybatis.bean.Apichange;
import apiChangeExtractor.mybatis.bean.ExistRepository;
import apiChangeExtractor.mybatis.bean.ProjectInfo;
import apiChangeExtractor.mybatis.bean.Repository;
import apiChangeExtractor.mybatis.dao.ApichangeDao;
import apiChangeExtractor.mybatis.dao.CounterDao;
import apiChangeExtractor.mybatis.dao.ExistRepositoryDao;
import apiChangeExtractor.mybatis.dao.ProjectInfoDao;
import apiChangeExtractor.mybatis.dao.RepositoryDao;
import apiChangeExtractor.util.PathUtils;

public class MybatisTest {
	ApichangeDao apichangeDao = new ApichangeDao();
	RepositoryDao repositoryDao = new RepositoryDao();
	ExistRepositoryDao existRepositoryDao = new ExistRepositoryDao();
	ProjectInfoDao projectInfoDao = new ProjectInfoDao();
	@Test
	public void testAddOneApiChange(){
		apichangeDao.insertOneApichange(new Apichange());
	}
	@Test
	public void testAddApiChangeList(){
		List<Apichange> list = new ArrayList<Apichange>();
		list.add(new Apichange());
		list.add(new Apichange());
		list.add(new Apichange());
		list.add(new Apichange());
		list.add(new Apichange());
		apichangeDao.insertApichangeList(list);
	}
	
	@Test
	public void testSelectAllRepository(){
		List<Repository> list = repositoryDao.selectAll();
		list.stream().forEach(r->System.out.println(r.getAddress()));
	}	
	@Test
	public void testSelectByStar(){
		List<Repository> list = repositoryDao.selectByStar(1000);
		System.out.println(list.size());
//		list.stream().forEach(r->System.out.println(r.getAddress()));
	}
	@Test
	public void testSelectRepositoryInScope(){
		int start = 4000000;
		int end = 4100000;
		List<Repository> list = repositoryDao.selectInScope(start, end);
		for(Repository r : list)
			System.out.println(r.getWebsite()+": "+r.getAddress());
		String repositoryPath1 = "D:/javaee/parser/ApiChangeExtractor";
		String repositoryPath2 = "D:/github/ChangeExtractor";
		String repositoryPath3 = "D:/github/SEDataExtractor";
		String repositoryPath4 = "D:/javaee/LykProject";
//		String repositoryPath5 = "D:/github/checkstyle";
		Repository repository1 = new Repository(-1, repositoryPath1);
		Repository repository2 = new Repository(-2, repositoryPath2);
		Repository repository3 = new Repository(-3, repositoryPath3);
		Repository repository4 = new Repository(-4, repositoryPath4);
//		Repository repository5 = new Repository(-5, repositoryPath5);
		System.out.println(repository1.getWebsite()+": "+repository1.getAddress());
		System.out.println(repository2.getWebsite()+": "+repository2.getAddress());
		System.out.println(repository3.getWebsite()+": "+repository3.getAddress());
		System.out.println(repository4.getWebsite()+": "+repository4.getAddress());
	}
	
	@Test
	public void testExistRepositoryDao(){
		List<ExistRepository>  list = existRepositoryDao.selectNotRun();
		System.out.println(list.size());
	}
	
	@Test
	public void testProjectInfoDao(){
		ProjectInfo info = new ProjectInfo(2000, "test", 1, 2, 3, 4,5,6);
		projectInfoDao.insertProjectInfo(info);
		ProjectInfo info1 = new ProjectInfo(2000, "test", 100, 200, 300, 400,500,600);
		info1.setFilterTime(1);info1.setMatchTime(1);info1.setDiffTime(1);info1.setCountTime(1);info1.setTotalTime(1);
		projectInfoDao.updateProjectInfo(info1);
	}
	
	@Test
	public void testCounter(){
		List<Api> apis = apichangeDao.selectAPIList();
		for(Api api : apis){
			List<Integer> list = CounterDao.getInstance().selectOneApiCount(api.getClassName(), api.getMethodName());
			double result = 1;
			for(Integer i : list)
				result*=((double)i);
			System.out.println(result+"-"+list.size()+":"+list);
			result = Math.pow(result, 1/list.size());
			CounterDao.getInstance().updateTestRate(result, api.getClassName(), api.getMethodName());
			System.out.println(result+"-"+list.size()+":"+list);
		}
	}
	
	@Test
	public void testRepeatCounter(){
		List<Apichange> apichanges = ApichangeDao.getInstance().selectAllParameterNum();
		System.out.println(apichanges.size());
		Map<Integer, Set<String>> map = new HashMap<>();
		for(Apichange apichange : apichanges){
			int id = apichange.getRepositoryId();
			if(map.get(id)==null) 
				map.put(id, new HashSet<>());
			map.get(id).add(apichange.getCommitId());
		}
		System.out.println(map.size());
		int count = 0;
		for(Integer id : map.keySet()){
			count+= map.get(id).size();
//			ExistRepository e = ExistRepositoryDao.getInstance().selectByRepositoryId(id);
//			e.setAddress(PathUtils.changeHighWebsite2Path(e.getWebsite()));
//			e.setRepositoryId(e.getId());
//			System.out.println(e.getAddress());
		}
		System.out.println(count);
	}
}
