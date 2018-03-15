package apiChangeExtractor.bean;

public class ChangeRecord {

	private int apichangeId;
	private int repositoryId;
	private String commitId;
	private String parentCommitId;
	private String newFileName;
	private String oldFileName;
	private int oldLineNumber;
	private int newLineNumber;
	private String changeType;
	private String oldContent;
	private String newContent;
	private String oldCompleteClassName;
	private String newCompleteClassName;
	private String oldMethodName;
	private String newMethodName;
	private int oldParameterNum;
	private int newParameterNum;
	private String oldParameterType;
	private String newParameterType;
	private String oldParameterName;
	private String newParameterName;
	
	public int getApichangeId() {
		return apichangeId;
	}
	public void setApichangeId(int apichangeId) {
		this.apichangeId = apichangeId;
	}
	public int getRepositoryId() {
		return repositoryId;
	}
	public void setRepositoryId(int repositoryId) {
		this.repositoryId = repositoryId;
	}
	public String getCommitId() {
		return commitId;
	}
	public void setCommitId(String commitId) {
		this.commitId = commitId;
	}
	public String getParentCommitId() {
		return parentCommitId;
	}
	public void setParentCommitId(String parentCommitId) {
		this.parentCommitId = parentCommitId;
	}
	public String getNewFileName() {
		return newFileName;
	}
	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}
	public String getOldFileName() {
		return oldFileName;
	}
	public void setOldFileName(String oldFileName) {
		this.oldFileName = oldFileName;
	}
	public int getOldLineNumber() {
		return oldLineNumber;
	}
	public void setOldLineNumber(int oldLineNumber) {
		this.oldLineNumber = oldLineNumber;
	}
	public int getNewLineNumber() {
		return newLineNumber;
	}
	public void setNewLineNumber(int newLineNumber) {
		this.newLineNumber = newLineNumber;
	}
	public String getChangeType() {
		return changeType;
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	public String getOldContent() {
		return oldContent;
	}
	public void setOldContent(String oldContent) {
		this.oldContent = oldContent;
	}
	public String getNewContent() {
		return newContent;
	}
	public void setNewContent(String newContent) {
		this.newContent = newContent;
	}
	public String getOldCompleteClassName() {
		return oldCompleteClassName;
	}
	public void setOldCompleteClassName(String oldCompleteClassName) {
		this.oldCompleteClassName = oldCompleteClassName;
	}
	public String getNewCompleteClassName() {
		return newCompleteClassName;
	}
	public void setNewCompleteClassName(String newCompleteClassName) {
		this.newCompleteClassName = newCompleteClassName;
	}
	public String getOldMethodName() {
		return oldMethodName;
	}
	public void setOldMethodName(String oldMethodName) {
		this.oldMethodName = oldMethodName;
	}
	public String getNewMethodName() {
		return newMethodName;
	}
	public void setNewMethodName(String newMethodName) {
		this.newMethodName = newMethodName;
	}
	public int getOldParameterNum() {
		return oldParameterNum;
	}
	public void setOldParameterNum(int oldParameterNum) {
		this.oldParameterNum = oldParameterNum;
	}
	public int getNewParameterNum() {
		return newParameterNum;
	}
	public void setNewParameterNum(int newParameterNum) {
		this.newParameterNum = newParameterNum;
	}
	public String getOldParameterType() {
		return oldParameterType;
	}
	public void setOldParameterType(String oldParameterType) {
		this.oldParameterType = oldParameterType;
	}
	public String getNewParameterType() {
		return newParameterType;
	}
	public void setNewParameterType(String newParameterType) {
		this.newParameterType = newParameterType;
	}
	public String getOldParameterName() {
		return oldParameterName;
	}
	public void setOldParameterName(String oldParameterName) {
		this.oldParameterName = oldParameterName;
	}
	public String getNewParameterName() {
		return newParameterName;
	}
	public void setNewParameterName(String newParameterName) {
		this.newParameterName = newParameterName;
	}

	
}
