package apiChangeExtractor.bean;

import java.util.HashSet;
import java.util.Set;

public class JdkSequence {
	private int lineNum;
	private String stmt;
	private Set<MethodCall> apiList;
	public JdkSequence(int lineNum, String stmt){
		this.lineNum = lineNum;
		this.stmt = stmt;
		this.apiList = new HashSet<MethodCall>(); 
	}
	public int getLineNum() {
		return lineNum;
	}
	public void setLineNum(int lineNum) {
		this.lineNum = lineNum;
	}
	public String getStmt() {
		return stmt;
	}
	public void setStmt(String stmt) {
		this.stmt = stmt;
	}
	public Set<MethodCall> getApiList() {
		return apiList;
	}
	public void setApiList(Set<MethodCall> apiList) {
		this.apiList = apiList;
	}
	
}
