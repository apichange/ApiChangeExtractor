package apiChangeExtractor.bean;

public class MethodCall {
	private String completeClassName;
	private String methodName;
	private String parameter;
	public MethodCall(String completeClassName, String methodNameAndparameter){
		this.completeClassName = completeClassName;
		deal(methodNameAndparameter);
	}
	public void deal(String s){
		if(s ==null||"".equals(s)){
			this.methodName = null;
			this.parameter=null;
			return;
		}
		String[] method = s.split("\\(|\\)");
		if(method.length>2){
			this.methodName = s;
			this.parameter=null;
		}else if(method.length==2){
			this.methodName = method[0];
			this.parameter = method[1];
		}else{
			this.methodName = method[0];
			this.parameter = null;
		}
	}
	public String getCompleteClassName() {
		return completeClassName;
	}
	public void setCompleteClassName(String completeClassName) {
		this.completeClassName = completeClassName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
}
