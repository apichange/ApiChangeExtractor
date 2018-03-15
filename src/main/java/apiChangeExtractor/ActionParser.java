package apiChangeExtractor;

import java.util.List;

import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jgit.revwalk.RevCommit;

import apiChangeExtractor.PatternMatcher.ChangeType;
import apiChangeExtractor.bean.ChangeFile;
import apiChangeExtractor.bean.JdtMethodCall;
import apiChangeExtractor.bean.RenameList;
import apiChangeExtractor.evaluation.ETimer;
import apiChangeExtractor.gumtreeParser.GumTreeDiffParser;
import apiChangeExtractor.mybatis.bean.Apichange;
import apiChangeExtractor.mybatis.bean.ChangeExample;
import apiChangeExtractor.mybatis.bean.InnerChangeExample;
import apiChangeExtractor.mybatis.dao.ApichangeDao;

import com.github.gumtreediff.actions.model.Action;
import com.github.gumtreediff.actions.model.Insert;
import com.github.gumtreediff.actions.model.Update;
import com.github.gumtreediff.tree.ITree;
import com.github.gumtreediff.tree.Tree;

public class ActionParser {
	private GumTreeDiffParser diff;
	private ApichangeDao dao;
	private String webRoot;
	private ChangeFile changeFile;
	private int repositoryId;
	private RevCommit thisCommit;
	private ETimer timer;
	
	public ActionParser(RevCommit thisCommit, GumTreeDiffParser diff, String webRoot, ChangeFile changeFile, int repositoryId, ETimer timer){
		this.thisCommit = thisCommit;
		this.diff = diff;
		dao = new ApichangeDao();
		this.webRoot = webRoot;
		this.changeFile = changeFile;
		this.repositoryId = repositoryId;
		this.timer =timer;
	}
	public RenameList parseOneAction(Action a, RenameList rl){
		if(a.getNode()==null || diff.dstTC==null)
		    return rl;
		
		PatternMatcher pm = new PatternMatcher();
	    ChangeType changeType = pm.matchOneAction(a, diff.dstTC, diff, rl);
		Apichange apichange = new Apichange();
		ChangeExample example = new ChangeExample();
		InnerChangeExample innerExample = new InnerChangeExample();
		
		if(!ChangeType.NOT_FOUND.toString().equals(changeType.toString()))
		{
			apichange.setCommitLog(thisCommit.getFullMessage());
			apichange.setRepositoryId(repositoryId);
			apichange.setWebsite(webRoot+changeFile.getCommitId());
			apichange.setCommitId(changeFile.getCommitId());
			apichange.setParentCommitId(changeFile.getParentCommitId());
			apichange.setNewFileName(changeFile.getNewPath());
			apichange.setOldFileName(changeFile.getOldPath());
			ITree newNode = null; 
			ITree oldNode = null;	
			apichange.setChangeType(changeType.toString());
			if(a instanceof Insert)
			{
				newNode = a.getNode();	
			}
			if(a instanceof Update)
			{
				oldNode = a.getNode();
				newNode = diff.getMapping().getDst(oldNode);							
			}
			if(oldNode!=null)
			{
				apichange.setOldLineNumber(oldNode.getStartLineNumber());
				apichange.setOldContent(((Tree)oldNode).getAstNode().toString());
				String tempOldParameterName = new String();
				List<ITree> oldChildren= oldNode.getParent().getChildren();
				if(oldChildren.size()>=2)	
				{
					for(int j=2;j<oldChildren.size();j++)
					{
					    tempOldParameterName = tempOldParameterName+oldChildren.get(j).getLabel();
					    if(j!=oldChildren.size()-1)
					    	tempOldParameterName = tempOldParameterName +",";
					}
				}
				apichange.setOldParameterName(tempOldParameterName);
				
				JdtMethodCall oldCall = null;
				if(((Tree)oldNode.getParent()).getAstNode() instanceof MethodInvocation){	
					MethodInvocation tempMI = (MethodInvocation)((Tree)oldNode.getParent()).getAstNode();
					oldCall = diff.getJdkMethodCall(tempMI);
					apichange.setOldMI(tempMI.toString());
				}
				if(oldCall!=null)
				{
					apichange.setOldCompleteClassName(oldCall.getInvoker());
					apichange.setOldMethodName(oldCall.getMethodName());
					apichange.setOldParameterNum(oldCall.getParameters().size());
					apichange.setOldParameterType(oldCall.getParameterString());
				}
				else
					return rl;
			}
			if(newNode!=null)
			{
				apichange.setNewLineNumber(newNode.getStartLineNumber());//map去找
				apichange.setNewContent(((Tree)newNode).getAstNode().toString());
				String tempNewParameterName = new String();
				List<ITree> newChildren= newNode.getParent().getChildren();
				if(newChildren.size()>=2)	
				{
					for(int j=2;j<newChildren.size();j++)
					{
					    tempNewParameterName = tempNewParameterName+newChildren.get(j).getLabel();
					    if(j!=newChildren.size()-1)
					    	tempNewParameterName = tempNewParameterName +",";
					}
				}
				apichange.setNewParameterName(tempNewParameterName);
				JdtMethodCall newCall = null;
				if(((Tree)newNode.getParent()).getAstNode() instanceof MethodInvocation){
					MethodInvocation tempMI = (MethodInvocation)((Tree)newNode.getParent()).getAstNode();
					newCall = diff.getJdkMethodCall(tempMI);
					apichange.setNewMI(tempMI.toString());
				}
				if(newCall!=null)
				{
					
					apichange.setNewCompleteClassName(newCall.getInvoker());							
					apichange.setNewMethodName(newCall.getMethodName());
					apichange.setNewParameterNum(newCall.getParameters().size());
					apichange.setNewParameterType(newCall.getParameterString());	
					if(newCall.isJdk())
					{
						if(timer!=null) timer.startTimer();
						
						List<ChangeExample> res = null;
						List<InnerChangeExample> res2 = null;
						example.setChangeType(apichange.getChangeType());
						example.setNewCompleteClassName(apichange.getNewCompleteClassName());
						example.setOldCompleteClassName(apichange.getOldCompleteClassName());
						example.setOldMethodName(apichange.getOldMethodName());
						example.setNewMethodName(apichange.getNewMethodName());
						example.setOuterRepeatNum(apichange.getOuterRepeatNum());
						innerExample.setRepositoryId(apichange.getRepositoryId());
						res = dao.selectExample(example);
						if(res == null || res.size()==0){
							example.setOuterRepeatNum(1);
							dao.insertExample(example);
							res = dao.selectExample(example);
	
							System.out.println("New ExampleId:"+res.get(0).getExampleId());
							apichange.setExampleId(res.get(0).getExampleId());
							
							innerExample.setExampleId(res.get(0).getExampleId());
							innerExample.setInnerRepeatNum(1);
							dao.insertInnerExample(innerExample);
						}
						else{
							example.setExampleId(res.get(0).getExampleId());
							apichange.setExampleId(res.get(0).getExampleId());
							innerExample.setExampleId(res.get(0).getExampleId());
							
							res2 = dao.selectInnerExample(innerExample);
							if(res2 == null || res2.size()==0){
								innerExample.setInnerRepeatNum(1);
								dao.insertInnerExample(innerExample);
								example.setOuterRepeatNum(res.get(0).getOuterRepeatNum()+1);
								dao.updateExampleOuterRepeatNum(example);
							}
							else{
								innerExample.setInnerRepeatNum(res2.get(0).getInnerRepeatNum()+1);
								innerExample.setInnerExampleId(res2.get(0).getInnerExampleId());
								dao.updateInnerExample(innerExample);
							}	
						}
						if(timer!=null) timer.endTimer();
						dao.insertOneApichange(apichange);
					}
				}	
			}									
		}
		return rl;
	}
	
	public RenameList fixChangeParameter(Action a, RenameList rl){
		if(a.getNode()==null || diff.dstTC==null)
		    return rl;
		
		PatternMatcher pm = new PatternMatcher();
	    ChangeType changeType = pm.matchOneAction(a, diff.dstTC, diff, rl);
		Apichange apichange = new Apichange();
		
		if("src/test/java/io/reactivex/XFlatMapTest.java".equals(changeFile.getOldPath()))
			System.out.println("change type: "+changeType.toString());
		if(ChangeType.CHANGE_PAREMETER.toString().equals(changeType.toString()))
		{
			apichange.setCommitLog(thisCommit.getFullMessage());
			apichange.setRepositoryId(repositoryId);
			apichange.setWebsite(webRoot+changeFile.getCommitId());
			apichange.setCommitId(changeFile.getCommitId());
			apichange.setParentCommitId(changeFile.getParentCommitId());
			apichange.setNewFileName(changeFile.getNewPath());
			apichange.setOldFileName(changeFile.getOldPath());
			ITree newNode = null; 
			ITree oldNode = null;	
			apichange.setChangeType(changeType.toString());
			if(a instanceof Insert)
			{
				newNode = a.getNode();	
				
			}
			if(a instanceof Update)
			{
				oldNode = a.getNode();
				newNode = diff.getMapping().getDst(oldNode);							
			}
			if(oldNode!=null)
			{
				apichange.setOldLineNumber(oldNode.getStartLineNumber());
				apichange.setOldContent(((Tree)oldNode).getAstNode().toString());
				String tempOldParameterName = new String();
				List<ITree> oldChildren= oldNode.getParent().getChildren();
				if(oldChildren.size()>=2)	
				{
					for(int j=2;j<oldChildren.size();j++)
					{
					    tempOldParameterName = tempOldParameterName+oldChildren.get(j).getLabel();
					    if(j!=oldChildren.size()-1)
					    	tempOldParameterName = tempOldParameterName +",";
					}
				}
				apichange.setOldParameterName(tempOldParameterName);
				
				JdtMethodCall oldCall = null;
				if(((Tree)oldNode.getParent()).getAstNode() instanceof MethodInvocation){	
					MethodInvocation tempMI = (MethodInvocation)((Tree)oldNode.getParent()).getAstNode();
					oldCall = diff.getJdkMethodCall(tempMI);
					apichange.setOldMI(tempMI.toString());
				}
				if(oldCall!=null)
				{
					apichange.setOldCompleteClassName(oldCall.getInvoker());
					apichange.setOldMethodName(oldCall.getMethodName());
					apichange.setOldParameterNum(oldCall.getParameters().size());
					apichange.setOldParameterType(oldCall.getParameterString());
					ITree parent = oldNode.getParent();
					int position  = parent.getChildPosition(oldNode);
					apichange.setParameterPosition(position-2);
				}
				else
					return rl;
			}
			if(newNode!=null)
			{
				apichange.setNewLineNumber(newNode.getStartLineNumber());//map去找
				apichange.setNewContent(((Tree)newNode).getAstNode().toString());
				String tempNewParameterName = new String();
				List<ITree> newChildren= newNode.getParent().getChildren();
				if(newChildren.size()>=2)	
				{
					for(int j=2;j<newChildren.size();j++)
					{
					    tempNewParameterName = tempNewParameterName+newChildren.get(j).getLabel();
					    if(j!=newChildren.size()-1)
					    	tempNewParameterName = tempNewParameterName +",";
					}
				}
				apichange.setNewParameterName(tempNewParameterName);
				JdtMethodCall newCall = null;
				if(((Tree)newNode.getParent()).getAstNode() instanceof MethodInvocation){
					MethodInvocation tempMI = (MethodInvocation)((Tree)newNode.getParent()).getAstNode();
					newCall = diff.getJdkMethodCall(tempMI);
					apichange.setNewMI(tempMI.toString());
				}
				if(newCall!=null)
				{
					
					apichange.setNewCompleteClassName(newCall.getInvoker());							
					apichange.setNewMethodName(newCall.getMethodName());
					apichange.setNewParameterNum(newCall.getParameters().size());
					apichange.setNewParameterType(newCall.getParameterString());	
					if(newCall.isJdk())
					{
						if(apichange.getRepositoryId()==1)
							System.out.println(apichange.toString());
						dao.updateParameterPosition(apichange);
					}
				}	
			}									
		}
		return rl;
	}
	
	
}
