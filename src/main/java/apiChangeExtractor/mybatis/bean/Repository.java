package apiChangeExtractor.mybatis.bean;

import apiChangeExtractor.util.PathUtils;

public class Repository {
   private Integer repositoryId;
   private String githubId;
   private String repositoryName;
   private String userId;
   private String userName;
   private String website;
   private String stars;
   private String address;

   public Repository(){
   }
   public Repository(int repositoryId, String address){
	   this.repositoryId = repositoryId;
	   this.address = address;
   }
	public Integer getRepositoryId() {
		return repositoryId;
	}
	public void setRepositoryId(Integer repositoryId) {
		this.repositoryId = repositoryId;
	}
	public String getGithubId() {
		return githubId;
	}
	public void setGithubId(String githubId) {
		this.githubId = githubId;
	}
	public String getRepositoryName() {
		return repositoryName;
	}
	public void setRepositoryName(String repositoryName) {
		this.repositoryName = repositoryName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getStars() {
		return stars;
	}
	public void setStars(String stars) {
		this.stars = stars;
	}
	public String getAddress() {
		if(address==null) this.address = PathUtils.changeWebsite2Path(website);
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
