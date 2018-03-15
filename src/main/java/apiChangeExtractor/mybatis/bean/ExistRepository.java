package apiChangeExtractor.mybatis.bean;

public class ExistRepository { 
	private Integer id;
	private Integer repositoryId;
	private Integer githubId;
	private String name;
	private Integer userId;
	private String userName;
	private String website;
	private String stars;
	private String address;
	
	public ExistRepository(){
		super();
	}
	public ExistRepository(Integer repositoryId, Integer githubId, String name,
			Integer userId, String userName, String website, String stars,
			String address) {
		super();
		this.repositoryId = repositoryId;
		this.githubId = githubId;
		this.name = name;
		this.userId = userId;
		this.userName = userName;
		this.website = website;
		this.stars = stars;
		this.address = address;
	}
	public Integer getRepositoryId() {
		return repositoryId;
	}
	public void setRepositoryId(Integer repositoryId) {
		this.repositoryId = repositoryId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getGithubId() {
		return githubId;
	}
	public void setGithubId(Integer githubId) {
		this.githubId = githubId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
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
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
