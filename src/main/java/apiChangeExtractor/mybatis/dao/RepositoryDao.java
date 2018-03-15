package apiChangeExtractor.mybatis.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import apiChangeExtractor.mybatis.bean.Repository;
import apiChangeExtractor.mybatis.mapper.RepositoryMapper;

public class RepositoryDao {
	private SqlSessionFactory sqlSessionFactory = MybatisFactory.getSqlSessionFactoryGithub();
	
	private static class SingletonHolder {  
		private static RepositoryDao repostoryDao = new RepositoryDao();
	}
	
	public static RepositoryDao getInstance(){
		return SingletonHolder.repostoryDao;
	}
	
	public List<Repository> selectAll(){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<Repository> rList = null;
		try{
			rList = sqlSession.getMapper(RepositoryMapper.class).selectAllRepository();
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}	
		return rList;
	}
	
	public List<Repository> selectInScope(int start, int end){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<Repository> rList = null;
		try{
			rList = sqlSession.getMapper(RepositoryMapper.class).selectInScope(start, end);
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}	
		return rList;
	}
	
	public List<Repository> selectByStar(int stars){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<Repository> rList = null;
		try{
			rList = sqlSession.getMapper(RepositoryMapper.class).selectByStar(stars);
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}	
		return rList;
	}
}
