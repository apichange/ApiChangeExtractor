package apiChangeExtractor.bean;

public class ChangeLine {
	private int lineNum;
	private String sequence;
	private String type;
	private int oldNum = -1;
	public ChangeLine(int lineNum, String sequence,String type){
		this.lineNum = lineNum;
		this.sequence=sequence;
		this.type = type;
	}
	public ChangeLine(int lineNum, String sequence,String type, int oldNum){
		this.lineNum = lineNum;
		this.sequence=sequence;
		this.type = type;
		this.oldNum = oldNum;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLineNum() {
		return lineNum;
	}
	public void setLineNum(int lineNum) {
		this.lineNum = lineNum;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public int getOldNum() {
		return oldNum;
	}
	public void setOldNum(int oldNum) {
		this.oldNum = oldNum;
	}
	
}
