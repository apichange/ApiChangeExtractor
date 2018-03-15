package apiChangeExtractor.mybatis.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisFactory {
	
	private static class SingletonHolder {  
		private static SqlSessionFactory sqlSessionFactoryLocal;
		private static SqlSessionFactory sqlSessionFactoryGithub;
		private static SqlSessionFactory sqlSessionFactoryTemp;
		
		static {
			String resource = "resources/configuration.xml";
			//localhost
			try(InputStream inputStream = Resources.getResourceAsStream(resource)){
				if(sqlSessionFactoryLocal == null){
					sqlSessionFactoryLocal = new SqlSessionFactoryBuilder().build(inputStream, "development");
				}
			}catch (FileNotFoundException fileNotFoundException) {
	            fileNotFoundException.printStackTrace();
	        }
	        catch (IOException iOException) {
	            iOException.printStackTrace();
	        }
			//github
			try(InputStream inputStream = Resources.getResourceAsStream(resource)){
				if(sqlSessionFactoryGithub == null){
					sqlSessionFactoryGithub = new SqlSessionFactoryBuilder().build(inputStream, "github");
				}
			}catch (FileNotFoundException fileNotFoundException) {
	            fileNotFoundException.printStackTrace();
	        }
	        catch (IOException iOException) {
	            iOException.printStackTrace();
	        }
			//temp
			try(InputStream inputStream = Resources.getResourceAsStream(resource)){
				if(sqlSessionFactoryTemp == null){
					sqlSessionFactoryTemp = new SqlSessionFactoryBuilder().build(inputStream, "temp");
				}
			}catch (FileNotFoundException fileNotFoundException) {
	            fileNotFoundException.printStackTrace();
	        }
	        catch (IOException iOException) {
	            iOException.printStackTrace();
	        }
		}
	}

	public static SqlSessionFactory getSqlSessionFactoryLocal(){
		return SingletonHolder.sqlSessionFactoryLocal;
	}
	public static SqlSessionFactory getSqlSessionFactoryGithub(){
		return SingletonHolder.sqlSessionFactoryGithub;
	}
	public static SqlSessionFactory getSqlSessionFactoryTemp(){
		return SingletonHolder.sqlSessionFactoryTemp;
	}
}
