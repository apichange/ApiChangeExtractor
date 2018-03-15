package apiChangeExtractor.evaluation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import apiChangeExtractor.mybatis.bean.ProjectInfo;
import apiChangeExtractor.mybatis.dao.ProjectInfoDao;

public class ExcelOperator {
	public static void main(String[] args){
	    @SuppressWarnings("resource")
		Workbook workbook = new HSSFWorkbook();
	    Sheet sheet = workbook.createSheet("test");
	    int rowIndex = 0;
	    List<ProjectInfo> list = ProjectInfoDao.getInstance().selectAllInfo();
	    
	    for(ProjectInfo project : list)
	    {
	        Row row = sheet.createRow(rowIndex);
	        row.createCell(0).setCellValue(project.getRepositoryId());
	        row.createCell(1).setCellValue(project.getWebsite());
	        row.createCell(2).setCellValue(project.getCodeLoc());
	        row.createCell(3).setCellValue(project.getStars());
	        row.createCell(4).setCellValue(project.getCommits());
	        row.createCell(5).setCellValue(project.getBugCommits());
	        row.createCell(6).setCellValue(project.getAordBugCommits());
	        row.createCell(7).setCellValue(project.getFilterTime());
	        row.createCell(8).setCellValue(project.getDiffTime());
	        row.createCell(9).setCellValue(project.getMatchTime());
	        row.createCell(10).setCellValue(project.getCountTime());
	        row.createCell(11).setCellValue(project.getTotalTime());
	        rowIndex++;
	    }

	    FileOutputStream fileOutputStream = null;
	    try {
	        fileOutputStream = new FileOutputStream(new File("test.xls"));
	        workbook.write(fileOutputStream);
	        fileOutputStream.close();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }finally{
            try {
            	if(fileOutputStream!=null) fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
}
