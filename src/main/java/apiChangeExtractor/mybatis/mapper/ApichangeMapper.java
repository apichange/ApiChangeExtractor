package apiChangeExtractor.mybatis.mapper;

import java.util.List;

import apiChangeExtractor.mybatis.bean.APIRank;
import apiChangeExtractor.mybatis.bean.Api;
import apiChangeExtractor.mybatis.bean.Apichange;
import apiChangeExtractor.mybatis.bean.ChangeExample;
import apiChangeExtractor.mybatis.bean.InnerChangeExample;

public interface ApichangeMapper {
	public void insertApichange(Apichange apichange);
	public void insertApichangeList(List<Apichange> apichanges);
	public void updateRepeat(Apichange apichange);
	public int countInnerRepeat(Apichange apichange);
	public int countOuterRepeat(Apichange apichange);


	public int countAll();
	public List<Apichange> selectAll();
	public List<ChangeExample> selectAllExample();
	public void updateInnerRepeat(Apichange apichange);
	public void updateOuterRepeat(Apichange apichange);

	public List<ChangeExample> selectExample(ChangeExample example);

	public void insertExample(ChangeExample example);
	public void insertExampleList(List<ChangeExample> examples);
	public int countExample(ChangeExample example);

	public void updateExampleOuterRepeatNum(ChangeExample example);
	public void insertInnerExample(InnerChangeExample innerExample);
	public void insertInnerExampleList(List<InnerChangeExample> innerExamples);
	public List<InnerChangeExample> selectInnerExample(InnerChangeExample innerExample);
	public void updateInnerExample(InnerChangeExample innerExample);
	public List<Api> selectAPIList();
	public List<Apichange> selectAPIListWithExampleId(Api api);
	public void insertApi(APIRank apirank);
	public List<APIRank> selectAPIs();
	public List<ChangeExample> selectExamplesByApi(Api api);
	public List<Apichange> selectAllApiChangeTest();
	public void insertApichangeFix(Apichange apichange);
	public List<Apichange> selectAllParameterNum();
	public void updateParameterPosition(Apichange apichange);
	public int countAPIBugR(APIRank apirank);
	public void updateAPIBugR(APIRank apirank);
	public int countAPIBugTotal(APIRank apirank);
	public void updateAPIBugTotal(APIRank apirank);
	
	public List<ChangeExample> selectChangeExampleFromApichange();
	public List<InnerChangeExample> selectInnerChangeExampleFromApichange();
	public void updateApichangeExampleId(Apichange apichange);
	
}
