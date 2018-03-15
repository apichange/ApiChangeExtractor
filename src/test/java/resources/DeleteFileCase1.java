package resources;

import java.io.File;

public class DeleteFileCase1 {
	public void test(String s){
		File f = new File(s);
		if(!f.delete()){
			System.out.println(s);
		}
	}
}
