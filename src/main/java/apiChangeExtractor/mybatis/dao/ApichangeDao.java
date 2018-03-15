package apiChangeExtractor.mybatis.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import apiChangeExtractor.mybatis.bean.APIRank;
import apiChangeExtractor.mybatis.bean.Api;
import apiChangeExtractor.mybatis.bean.Apichange;
import apiChangeExtractor.mybatis.bean.ChangeExample;
import apiChangeExtractor.mybatis.bean.InnerChangeExample;
import apiChangeExtractor.mybatis.mapper.ApichangeMapper;

public class ApichangeDao {
	private SqlSessionFactory sqlSessionFactory = MybatisFactory.getSqlSessionFactoryTemp();
	private static class SingletonHolder {  
		private static ApichangeDao singleton;
		static {
			singleton = new ApichangeDao();
		}
	}
	public static ApichangeDao getInstance(){
		return SingletonHolder.singleton;
	}
	
	public void insertOneApichange(Apichange apichange){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try{
			sqlSession.getMapper(ApichangeMapper.class).insertApichange(apichange);;
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
	}
	
	public void insertApichangeList(List<Apichange> apichanges){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			sqlSession.getMapper(ApichangeMapper.class).insertApichangeList(apichanges);
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
	}
	
	public void updateRepeat(Apichange apichange){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			sqlSession.getMapper(ApichangeMapper.class).updateRepeat(apichange);;
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
	}
	
	public int countInnerRepeat(Apichange apichange){
		int res = 0;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			res = sqlSession.getMapper(ApichangeMapper.class).countInnerRepeat(apichange);;
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
		return res;
	}
	
	public int countOuterRepeat(Apichange apichange){
		int res = 0;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			res = sqlSession.getMapper(ApichangeMapper.class).countOuterRepeat(apichange);;
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
		return res;
	}
	public int countAll(){
		int res = 0;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			res = sqlSession.getMapper(ApichangeMapper.class).countAll();;
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
		return res;
	}
	
	public List<Apichange> selectAll(){
		List<Apichange> res = null;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			res = sqlSession.getMapper(ApichangeMapper.class).selectAll();;
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
		return res;
	}
	public List<ChangeExample> selectAllExample(){
		List<ChangeExample> res = null;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			res = sqlSession.getMapper(ApichangeMapper.class).selectAllExample();;
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
		return res;
	}
	
	public List<ChangeExample> selectExample(ChangeExample example){
		List<ChangeExample> res = null;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			res = sqlSession.getMapper(ApichangeMapper.class).selectExample(example);;
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
		return res;
	}


	public void insertExample(ChangeExample example){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			sqlSession.getMapper(ApichangeMapper.class).insertExample(example);;
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
	}
	public void insertExampleList(List<ChangeExample> examples){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			sqlSession.getMapper(ApichangeMapper.class).insertExampleList(examples);;
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
	}

	public void updateExampleOuterRepeatNum(ChangeExample example){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			sqlSession.getMapper(ApichangeMapper.class).updateExampleOuterRepeatNum(example);
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
	}


	public void updateInnerRepeat(Apichange apichange) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			sqlSession.getMapper(ApichangeMapper.class).updateInnerRepeat(apichange);;
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
	}

	public void updateOuterRepeat(Apichange apichange) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			sqlSession.getMapper(ApichangeMapper.class).updateOuterRepeat(apichange);;
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
	}





	public int countExample(ChangeExample example){
		int res = 0;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			res = sqlSession.getMapper(ApichangeMapper.class).countExample(example);;
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
		return res;
	}
	
	public void insertInnerExample(InnerChangeExample innerExample){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try{
			sqlSession.getMapper(ApichangeMapper.class).insertInnerExample(innerExample);
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
	}
	public void insertInnerExampleList(List<InnerChangeExample> innerExamples){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			sqlSession.getMapper(ApichangeMapper.class).insertInnerExampleList(innerExamples);
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
	}
	public List<InnerChangeExample> selectInnerExample(InnerChangeExample innerExample){
		List<InnerChangeExample> res = null;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			res = sqlSession.getMapper(ApichangeMapper.class).selectInnerExample(innerExample);;
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
		return res;
	}
	public void updateInnerExample(InnerChangeExample innerExample){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			sqlSession.getMapper(ApichangeMapper.class).updateInnerExample(innerExample);;
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
	}
	
	public List<Api> selectAPIList(){
		List<Api> res = null;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			res = sqlSession.getMapper(ApichangeMapper.class).selectAPIList();;
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
		return res;
	}

	public List<Apichange> selectAPIListWithExampleId(Api api){
		List<Apichange> res = null;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			res = sqlSession.getMapper(ApichangeMapper.class).selectAPIListWithExampleId(api);;
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
		return res;
	}

	public void insertApi(APIRank apirank){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try{
			sqlSession.getMapper(ApichangeMapper.class).insertApi(apirank);;
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
	}
	public List<APIRank> selectAPIs(){
		List<APIRank> res = null;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			res = sqlSession.getMapper(ApichangeMapper.class).selectAPIs();;
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
		return res;
	}
	public List<ChangeExample> selectExamplesByApi(Api api){
		List<ChangeExample> res = null;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			res = sqlSession.getMapper(ApichangeMapper.class).selectExamplesByApi(api);;
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
		return res;
	}

	public List<Apichange> selectAllApiChangeTest(){
		List<Apichange> res = null;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			res = sqlSession.getMapper(ApichangeMapper.class).selectAllApiChangeTest();
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
		return res;
	}
	public void insertApichangeFix(Apichange apichange){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try{
			sqlSession.getMapper(ApichangeMapper.class).insertApichangeFix(apichange);
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
	}
	
	public List<Apichange> selectAllParameterNum(){
		List<Apichange> res = null;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			res = sqlSession.getMapper(ApichangeMapper.class).selectAllParameterNum();
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
		return res;
	}
	public void updateParameterPosition(Apichange apichange){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			sqlSession.getMapper(ApichangeMapper.class).updateParameterPosition(apichange);;
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
	}
	public int countAPIBugR(APIRank apirank){
		int res = 0;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			res = sqlSession.getMapper(ApichangeMapper.class).countAPIBugR(apirank);
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
		return res;
		
	}
	public void updateAPIBugR(APIRank apirank){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			sqlSession.getMapper(ApichangeMapper.class).updateAPIBugR(apirank);;
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
	}
	public int countAPIBugTotal(APIRank apirank){
		int res = 0;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			res = sqlSession.getMapper(ApichangeMapper.class).countAPIBugTotal(apirank);
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
		return res;
	}
	public void updateAPIBugTotal(APIRank apirank){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			sqlSession.getMapper(ApichangeMapper.class).updateAPIBugTotal(apirank);;
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
	}
	public List<ChangeExample> selectChangeExampleFromApichange(){
		List<ChangeExample> res = null;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			res = sqlSession.getMapper(ApichangeMapper.class).selectChangeExampleFromApichange();
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
		return res;
	}
	public List<InnerChangeExample> selectInnerChangeExampleFromApichange(){
		List<InnerChangeExample> res = null;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			res = sqlSession.getMapper(ApichangeMapper.class).selectInnerChangeExampleFromApichange();
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
		return res;
		
	}
	public void updateApichangeExampleId(Apichange apichange){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			sqlSession.getMapper(ApichangeMapper.class).updateApichangeExampleId(apichange);;
			sqlSession.commit();
		}finally {
		    sqlSession.close();
		}
	}
}
