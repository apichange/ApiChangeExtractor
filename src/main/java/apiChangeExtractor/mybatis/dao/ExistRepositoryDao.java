package apiChangeExtractor.mybatis.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import apiChangeExtractor.mybatis.bean.ExistRepository;
import apiChangeExtractor.mybatis.mapper.ExistRepositoryMapper;

public class ExistRepositoryDao {
	private SqlSessionFactory sqlSessionFactory = MybatisFactory.getSqlSessionFactoryTemp();
	private static class SingletonHolder {  
		private static ExistRepositoryDao singleton;
		static {
			singleton = new ExistRepositoryDao();
		}
	}
	public static ExistRepositoryDao getInstance(){
		return SingletonHolder.singleton;
	}


	public List<ExistRepository> selectAll(){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<ExistRepository> rList = null;
		try{
			rList = sqlSession.getMapper(ExistRepositoryMapper.class).selectAllRepository();
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}	
		return rList;
	}
	
	public List<ExistRepository> selectInScope(int start, int end){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<ExistRepository> rList = null;
		try{
			rList = sqlSession.getMapper(ExistRepositoryMapper.class).selectInScope(start, end);
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}	
		return rList;
	}
	
	public void insertOneExistRepository(ExistRepository existRepository){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			sqlSession.getMapper(ExistRepositoryMapper.class).insertExistRepository(existRepository);;
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
	}

	public List<ExistRepository> selectHigh(){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<ExistRepository> rList = null;
		try{
			rList = sqlSession.getMapper(ExistRepositoryMapper.class).selectHighRepository();
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}	
		return rList;
	}
	
	public List<ExistRepository> selectNotRun(){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<ExistRepository> rList = null;
		try{
			rList = sqlSession.getMapper(ExistRepositoryMapper.class).selectNotRun();
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}	
		return rList;
	}
	
	public ExistRepository selectByRepositoryId(int id){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		ExistRepository res = null;
		try{
			res = sqlSession.getMapper(ExistRepositoryMapper.class).selectByRepositoryId(id);
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}	
		return res;
	}
}
