package apiChangeExtractor.mybatis.bean;

public class ChangeExample {
	private int exampleId;
	private String changeType;
	private String oldCompleteClassName;
	private String newCompleteClassName;
	private String oldMethodName;
	private String newMethodName;
	private int parameterPosition;
	private int outerRepeatNum;
	
	public ChangeExample() {
		// TODO Auto-generated constructor stub
	}
	public int getExampleId() {
		return exampleId;
	}
	public void setExampleId(int exampleId) {
		this.exampleId = exampleId;
	}
	public String getChangeType() {
		return changeType;
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
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
	public int getOuterRepeatNum() {
		return outerRepeatNum;
	}
	public void setOuterRepeatNum(int outerRepeatNum) {
		this.outerRepeatNum = outerRepeatNum;
	}
	public int getParameterPosition() {
		return parameterPosition;
	}
	public void setParameterPosition(int parameterPosition) {
		this.parameterPosition = parameterPosition;
	}
}
