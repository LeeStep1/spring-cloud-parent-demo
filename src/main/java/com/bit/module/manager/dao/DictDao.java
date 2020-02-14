package com.bit.module.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bit.module.manager.bean.Dict;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 字典表
 * 
 * @author chenduo
 * @email ${email}
 * @date 2020-02-14 09:06:31
 */
@Repository
public interface DictDao extends BaseMapper<Dict>{

	/**
    * 根据id单查记录
    * @param id
    */
	Dict getDictById(Long id);


	/**
    * 多参数查询
    * @return
    */
	List<Dict> findByParam(Dict dict);

	/**
	* 新增记录
    */
	void addDict(Dict dict);

	/**
    * 编辑记录
    */
	void updateDict(Dict dict);

	/**
    * 删除记录
    */
	void delDictById(Long id);

	
}
