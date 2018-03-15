package apiChangeExtractor.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CounterMapper {
	List<Integer> selectOneApiCount(@Param("className")String className, @Param("methodName")String methodName);
	void updateTestRate(@Param("testRate")double testRate, @Param("className")String className, @Param("methodName")String methodName);
}
