package apiChangeExtractor;


import java.io.IOException;
import java.util.List;

import org.junit.Test;

import apiChangeExtractor.mybatis.bean.APIRank;
import apiChangeExtractor.mybatis.bean.Api;
import apiChangeExtractor.mybatis.bean.Apichange;
import apiChangeExtractor.mybatis.bean.ChangeExample;
import apiChangeExtractor.mybatis.bean.InnerChangeExample;
import apiChangeExtractor.mybatis.dao.ApichangeDao;


public class RepeatCounter {
	
	@Test
	public void updateTopApi(){
		ApichangeDao dao = new ApichangeDao();
		List<APIRank> list = dao.selectAPIs();
		System.out.println("Load top APi list, size: "+list.size());
		for(int i=0;i<list.size();i++){
			APIRank api = list.get(i);
			if(api!=null){
				int res = dao.countAPIBugR(api);
				api.setBugR(res);
				dao.updateAPIBugR(api);
			}
			System.out.println("Process have done: "+(i*100.00/list.size())+"%");
		}
	}
	public void countAndInsert(){	
		ApichangeDao dao = new ApichangeDao();
		List<ChangeExample> list = dao.selectChangeExampleFromApichange();
			dao.insertExampleList(list);

		list = dao.selectAllExample();
		int size = list.size();
		int s1 = 0;
		int s2 = size/5;
		int s3 = size*2/5;
		int s4 = size*3/5;
		int s5 = size*4/5;
		
		
		UpdateThread thread1 = new UpdateThread(dao);
		thread1.setList(list);
		thread1.setIndex(s1, s2);
		UpdateThread thread2 = new UpdateThread(dao);
		thread2.setList(list);
		thread2.setIndex(s2, s3);
		UpdateThread thread3 = new UpdateThread(dao);
		thread3.setList(list);
		thread3.setIndex(s3, s4);
		UpdateThread thread4 = new UpdateThread(dao);
		thread4.setList(list);
		thread4.setIndex(s4, s5);
		UpdateThread thread5 = new UpdateThread(dao);
		thread5.setList(list);
		thread5.setIndex(s5, size);
		
		List<InnerChangeExample> list2 = dao.selectInnerChangeExampleFromApichange();
		dao.insertInnerExampleList(list2);		
	}
	private class UpdateThread implements Runnable{
		private List<ChangeExample> list;
		private int startAt;
		private int endAt;
		private ApichangeDao dao;
		UpdateThread(ApichangeDao dao){
			this.dao = dao;
		}
		@Override
		public void run() {
			for(int i = this.startAt;i < this.endAt;i++){
				ChangeExample example = list.get(i);
				Apichange apichange = new Apichange();
				apichange.setChangeType(example.getChangeType());
				apichange.setOldCompleteClassName(example.getOldCompleteClassName());
				apichange.setNewCompleteClassName(example.getNewCompleteClassName());
				apichange.setOldMethodName(example.getOldMethodName());
				apichange.setNewMethodName(example.getNewMethodName());
				apichange.setParameterPosition(example.getParameterPosition());
				apichange.setExampleId(example.getExampleId());
				
				dao.updateApichangeExampleId(apichange);
			}
		}

		public void setList(List<ChangeExample> list) {
			this.list = list;
		}

		public void setIndex(int startAt,int endAt) {
			this.startAt = startAt;
			this.endAt = endAt;
		}
		
	}
	public void updateTopApi2() throws IOException{
		ApichangeDao dao = new ApichangeDao();
		List<APIRank> list = dao.selectAPIs();
		System.out.println("Load top APi list, size: "+list.size());
		for(int i=0;i<list.size();i++){
			APIRank api = list.get(i);
			if(api!=null){
				int res = dao.countAPIBugTotal(api);
				api.setBugTotal(res);
				dao.updateAPIBugTotal(api);
			}
			System.out.println("Process have done: "+(i*100.00/list.size())+"%");
		}
	}
	
	public void countTopApi(){
		ApichangeDao dao = new ApichangeDao();
		List<Api> list = dao.selectAPIList();
		System.out.println(list.size());
		for(int i=0;i<list.size();i++){
			Api api = list.get(i);
			if(api!=null){
				APIRank apirank = new APIRank();
				apirank.setClassName(api.getClassName());
				apirank.setMethodName(api.getMethodName());
				dao.insertApi(apirank);
			}
		}
	}

}
