package apiChangeExtractor.util;

public class PathUtils {
	private static final String ROOT_PATH = "";
	public static String changeWebsite2Path(String s){
		if(s==null) return s;
		String temp = s.replace("https://github.com/", "");
		temp = temp.replace("/", "-");
		return ROOT_PATH+temp;
	}
	
	public static String changeWebsite2Path(String rootPath, String s){
		if(s==null) return s;
		String temp = s.replace("https://github.com/", "");
		temp = temp.replace("/", "-");
		return rootPath==null? temp : rootPath+temp;
	}
	
	public static String getUnitName(String s){
		if(s==null) return s;
		String[] temp = s.replaceAll("\\\\", "/").split("/");
		String t = temp[temp.length-1];
		temp = t.split("\\.");
		t = temp[0];
		return t;
	}

	public static String changeHighWebsite2Path(String s) {
		if(s==null) return s;
		String temp = s.replace("https://github.com/", "");
		return temp;
	}
}
