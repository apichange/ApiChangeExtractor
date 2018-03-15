package apiChangeExtractor.mybatis.bean;

public class Apichange {
	private int apichangeId;
	private int repositoryId;
	private String website;
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
	private int innerRepeatNum;
	private int outerRepeatNum;
	private int exampleId;
	private int parameterPosition;
	//fourth
	private String commitLog;
	private String oldMI;
	private String newMI;
	
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
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

	public String getNewMethodName() { return newMethodName; }
	public void setNewMethodName(String newMethodName) {
		this.newMethodName = newMethodName;
	}

	public int getOldParameterNum(){return oldParameterNum;}
	public void setOldParameterNum(int oldParameterNum){this.oldParameterNum = oldParameterNum;}

	public int getNewParameterNum(){return newParameterNum;}
	public void setNewParameterNum(int newParameterNum){this.newParameterNum = newParameterNum;}

	public String getOldParameterType() {
		return oldParameterType;
	}
	public void setOldParameterType(String oldParameterType){
		this.oldParameterType = oldParameterType;
	}

	public String getNewParameterType() {
		return newParameterType;
	}
	public void setNewParameterType(String newParameterType){
		this.newParameterType = newParameterType;
	}

	public String getOldParameterName() {
		return oldParameterName;
	}
	public void setOldParameterName(String oldParameterName){
		this.oldParameterName = oldParameterName;
	}

	public String getNewParameterName() {
		return newParameterName;
	}
	public void setNewParameterName(String newParameterName){
		this.newParameterName = newParameterName;
	}

	
	public int getInnerRepeatNum() {
		return innerRepeatNum;
	}
	public void setInnerRepeatNum(int innerRepeatNum) {
		this.innerRepeatNum = innerRepeatNum;
	}
	public int getOuterRepeatNum() {
		return outerRepeatNum;
	}
	public void setOuterRepeatNum(int outerRepeatNum) {
		this.outerRepeatNum = outerRepeatNum;
	}
	public String getCommitLog() {
		return commitLog;
	}
	public void setCommitLog(String commitLog) {
		this.commitLog = commitLog;
	}
	public String getOldMI() {
		return oldMI;
	}
	public void setOldMI(String oldMI) {
		this.oldMI = oldMI;
	}
	public String getNewMI() {
		return newMI;
	}
	public void setNewMI(String newMI) {
		this.newMI = newMI;
	}
	public int getExampleId() {
		return exampleId;
	}
	public void setExampleId(int exampleId) {
		this.exampleId = exampleId;
	}
	public int getParameterPosition() {
		return parameterPosition;
	}
	public void setParameterPosition(int parameterPosition) {
		this.parameterPosition = parameterPosition;
	}
	public String toString(){
		 String res ="apichangeId:"+this.apichangeId+"\n"
				+ "repositoryId:"+this.repositoryId+"\n"
				+ "website:"+this.website+"\n"
				+ "commitId:"+this.commitId+"\n"
				+ "parentCommitId:"+this.parentCommitId+"\n"
				+ "oldFileName:"+this.oldFileName+"\n"
				+ "newFileName:"+this.newFileName+"\n"
				+ "oldLineNumber:"+this.oldLineNumber+"\n"
				+ "newLineNumber:"+this.newLineNumber+"\n"
				+ "changeType:"+this.changeType+"\n"
				+ "oldContent:"+this.oldContent+"\n"
				+ "newContent:"+this.newContent+"\n"
				+ "oldCompleteClassName:"+this.oldCompleteClassName+"\n"
				+ "newCompleteClassName:"+this.newCompleteClassName+"\n"
				+ "oldMethodName:"+this.oldMethodName+"\n"
				+ "newMethodName:"+this.newMethodName+"\n"
				+ "oldParameterNum:"+this.oldParameterNum+"\n"
				+ "newParameterNum:"+this.newParameterNum+"\n"
				+ "oldParameterType:"+this.oldParameterType+"\n"
				+ "newParameterType:"+this.newParameterType+"\n"
				+ "oldParameterName:"+this.oldParameterName+"\n"
				+ "newParameterName:"+this.newParameterName+"\n"
				+ "innerRepeatNum:"+this.innerRepeatNum+"\n"
				+ "outerRepeatNum:"+this.outerRepeatNum+"\n"
				+ "commitLog:"+this.commitLog+"\n"
				+ "oldMI:"+this.oldMI+"\n"
				+ "newMI:"+this.newMI+"\n"
				+ "exampleId:"+this.exampleId+"\n"
				+ "parameterPosition:"+this.parameterPosition+"\n";
		return res;
	}
}
