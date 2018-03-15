package apiChangeExtractor.mybatis.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import apiChangeExtractor.mybatis.bean.ProjectInfo;
import apiChangeExtractor.mybatis.mapper.ProjectInfoMapper;

public class ProjectInfoDao {
	private SqlSessionFactory sqlSessionFactory = MybatisFactory.getSqlSessionFactoryTemp();
	private static class SingletonHolder {  
		private static ProjectInfoDao singleton;
		static {
			singleton = new ProjectInfoDao();
		}
	}
	public static ProjectInfoDao getInstance(){
		return SingletonHolder.singleton;
	}

	public void insertProjectInfo(ProjectInfo info){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try{
			sqlSession.getMapper(ProjectInfoMapper.class).insertProjectInfo(info);
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
	}
	public void updateProjectInfo(ProjectInfo info){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try{
			sqlSession.getMapper(ProjectInfoMapper.class).updateProjectInfo(info);
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
	}
	public void updateProjectInfoByMoreFive(ProjectInfo info){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try{
			sqlSession.getMapper(ProjectInfoMapper.class).updateProjectInfoByMoreFive(info);
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
	}
	

	public List<ProjectInfo> selectAllInfo(){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<ProjectInfo> result = null;
		try{
			result = sqlSession.getMapper(ProjectInfoMapper.class).selectAllInfo();
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
		return result;
	}
}
