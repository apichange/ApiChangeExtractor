package apiChangeExtractor.mybatis.mapper;

import java.util.List;

import apiChangeExtractor.mybatis.bean.ProjectInfo;

public interface ProjectInfoMapper {
	public void insertProjectInfo(ProjectInfo info);
	public void updateProjectInfo(ProjectInfo info);
	public void updateProjectInfoByMoreFive(ProjectInfo info);
	List<ProjectInfo> selectAllInfo();
}
