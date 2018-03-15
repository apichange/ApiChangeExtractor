package apiChangeExtractor.mybatis.bean;

public class ProjectInfo {
	private int repositoryId;
	private String website;
	private int stars;
	private int blankLoc;
	private int commentLoc;
	private int codeLoc;
	private int commits;
	private int bugCommits;
	private int aordBugCommits;
	private int filterTime;
	private int diffTime;
	private int matchTime;
	private int countTime;
	private int totalTime;
	private int moreFiveBugCommits;
	
	public ProjectInfo(){}
	public ProjectInfo(int repositoryId, String website, int stars, int blankLoc, int commentLoc, int codeLoc,int commits,int bugCommits){
		this.repositoryId = repositoryId;
		this.website = website;
		this.stars = stars;
		this.blankLoc = blankLoc;
		this.commentLoc = commentLoc;
		this.codeLoc = codeLoc;
		this.commits = commits;
		this.bugCommits = bugCommits;
	}
	
	public int getMoreFiveBugCommits() {
		return moreFiveBugCommits;
	}
	public void setMoreFiveBugCommits(int moreFiveBugCommits) {
		this.moreFiveBugCommits = moreFiveBugCommits;
	}
	public int getRepositoryId() {
		return repositoryId;
	}
	public void setRepositoryId(int repositoryId) {
		this.repositoryId = repositoryId;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public int getStars() {
		return stars;
	}
	public void setStars(int stars) {
		this.stars = stars;
	}
	public int getBlankLoc() {
		return blankLoc;
	}
	public void setBlankLoc(int blankLoc) {
		this.blankLoc = blankLoc;
	}
	public int getCommentLoc() {
		return commentLoc;
	}
	public void setCommentLoc(int commentLoc) {
		this.commentLoc = commentLoc;
	}
	public int getCodeLoc() {
		return codeLoc;
	}
	public void setCodeLoc(int codeLoc) {
		this.codeLoc = codeLoc;
	}
	public int getCommits() {
		return commits;
	}
	public void setCommits(int commits) {
		this.commits = commits;
	}
	public int getBugCommits() {
		return bugCommits;
	}
	public void setBugCommits(int bugCommits) {
		this.bugCommits = bugCommits;
	}
	public int getAordBugCommits() {
		return aordBugCommits;
	}
	public void setAordBugCommits(int aordBugCommits) {
		this.aordBugCommits = aordBugCommits;
	}
	public int getFilterTime() {
		return filterTime;
	}
	public void setFilterTime(int filterTime) {
		this.filterTime = filterTime;
	}
	public int getDiffTime() {
		return diffTime;
	}
	public void setDiffTime(int diffTime) {
		this.diffTime = diffTime;
	}
	public int getMatchTime() {
		return matchTime;
	}
	public void setMatchTime(int matchTime) {
		this.matchTime = matchTime;
	}
	public int getCountTime() {
		return countTime;
	}
	public void setCountTime(int countTime) {
		this.countTime = countTime;
	}
	public int getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}
	

}
