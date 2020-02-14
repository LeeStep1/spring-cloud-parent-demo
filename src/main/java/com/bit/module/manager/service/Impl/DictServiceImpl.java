package com.bit.module.manager.service.Impl;

import com.bit.base.service.BaseService;
import org.springframework.stereotype.Service;
import com.bit.module.manager.dao.DictDao;
import com.bit.module.manager.bean.Dict;
import com.bit.module.manager.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.bit.base.vo.BaseVo;

import java.util.List;

@Service("dictService")
public class DictServiceImpl extends BaseService implements DictService {

	@Autowired
	private DictDao dictDao;


	/**
	 * 新增数据
	 * @param dict
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo add(Dict dict){
             dictDao.addDict(dict);
		return successVo();
		}

	/**
	 * 编辑数据
	 * @param dict
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo update(Dict dict) {
		 dictDao.updateDict(dict);
		return successVo();
	}

	/**
	 * 删除数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo delete(Long id) {
		 dictDao.delDictById(id);
		return successVo();
	}

	/**
	 * 多参数查询数据
	 * @param dict
	 * @author chenduo
	 * @since ${date}
	 * @return List<Dict>
	 */
	@Override
	public BaseVo findByModule(Dict dict) {
		List<Dict> dictList = dictDao.findByParam(dict);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(dictList);
		return baseVo;
	}

	/**
	 * 单查数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 * @return Dict
	 */
	@Override
	public BaseVo reflectById(Long id) {
		Dict dict =	dictDao.getDictById(id);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(dict);
		return baseVo;
	}


}