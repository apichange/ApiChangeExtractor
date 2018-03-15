package apiChangeExtractor.mybatis.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import apiChangeExtractor.mybatis.mapper.CounterMapper;

public class CounterDao {
	private SqlSessionFactory sqlSessionFactory = MybatisFactory.getSqlSessionFactoryTemp();
	private static class SingletonHolder {  
		private static CounterDao singleton;
		static {
			singleton = new CounterDao();
		}
	}
	public static CounterDao getInstance(){
		return SingletonHolder.singleton;
	}
	
	public List<Integer> selectOneApiCount(String className,String methodName){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<Integer> rList = null;
		try{
			rList = sqlSession.getMapper(CounterMapper.class).selectOneApiCount(className, methodName);
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}	
		return rList;
	}
	
	public void updateTestRate(double testRate, String className,String methodName){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			sqlSession.getMapper(CounterMapper.class).updateTestRate(testRate, className, methodName);
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}	
	}
}
