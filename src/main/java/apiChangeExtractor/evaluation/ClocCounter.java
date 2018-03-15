package apiChangeExtractor.evaluation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class ClocCounter {
	public static int[] getLoc(String path){
		try {
			Process process = Runtime.getRuntime().exec("cloc "+path);
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			
			int[] result = new int[3];
			String s = null;
			while ((s = br.readLine()) != null) {
				if(s.startsWith("Java ")){
					StringTokenizer st = new StringTokenizer(s);
					st.nextToken();//name of Language
					st.nextToken();//num of files
					result[0] = Integer.parseInt(st.nextToken());//lines of blank
					result[1] = Integer.parseInt(st.nextToken());//lines of comment
					result[2] = Integer.parseInt(st.nextToken());//code
					break;
				}
			}
			process.waitFor();
			return result;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IO Error: "+path);
		}
		return null;
	}
}
