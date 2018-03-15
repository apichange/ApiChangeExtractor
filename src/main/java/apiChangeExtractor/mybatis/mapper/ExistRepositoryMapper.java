package apiChangeExtractor.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import apiChangeExtractor.mybatis.bean.ExistRepository;

public interface ExistRepositoryMapper {
    List<ExistRepository> selectAllRepository();
    List<ExistRepository> selectInScope(@Param("start")int start, @Param("end")int end);
    void insertExistRepository(ExistRepository existRepository);
    

    List<ExistRepository> selectHighRepository();
    List<ExistRepository> selectNotRun();
    
    ExistRepository selectByRepositoryId(int id);
}
