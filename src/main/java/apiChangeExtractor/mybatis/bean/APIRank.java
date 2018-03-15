package apiChangeExtractor.mybatis.bean;

public class APIRank {
	private int apiId;
	private String className;
	private String methodName;
	private int bugTotal;
	private int notbugTotal;
	private int bugR;
	private int notbugR;
	private double testRate;
	private double bugRank;
	private double notbugRank;

	
	public int getNotbugTotal() {
		return notbugTotal;
	}
	public void setNotbugTotal(int notbugTotal) {
		this.notbugTotal = notbugTotal;
	}
	public int getBugR() {
		return bugR;
	}
	public void setBugR(int bugR) {
		this.bugR = bugR;
	}
	public int getNotbugR() {
		return notbugR;
	}
	public void setNotbugR(int notbugR) {
		this.notbugR = notbugR;
	}
	public double getTestRate() {
		return testRate;
	}
	public void setTestRate(double testRate) {
		this.testRate = testRate;
	}
	public double getBugRank() {
		return bugRank;
	}
	public void setBugRank(double bugRank) {
		this.bugRank = bugRank;
	}
	public double getNotbugRank() {
		return notbugRank;
	}
	public void setNotbugRank(double notbugRank) {
		this.notbugRank = notbugRank;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public int getApiId() {
		return apiId;
	}
	public void setApiId(int apiId) {
		this.apiId = apiId;
	}
	public int getBugTotal() {
		return bugTotal;
	}
	public void setBugTotal(int bugTotal) {
		this.bugTotal = bugTotal;
	}
}
