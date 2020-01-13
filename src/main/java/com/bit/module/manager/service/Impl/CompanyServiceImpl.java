package com.bit.module.manager.service.Impl;

import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.Company;
import com.bit.module.manager.dao.CompanyDao;
import com.bit.module.manager.service.CompanyService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2020-01-03
 **/
@Service("companyService")
public class CompanyServiceImpl extends BaseService implements CompanyService {

	@Autowired
	private CompanyDao companyDao;

	/**
	 * 全部公司
	 *
	 * @return
	 */
	@Override
	public BaseVo getCompany() {
		List<Company> list = companyDao.selectList(null);
		BaseVo rs = new BaseVo();
		rs.setData(list);
		return rs;
	}

	/**
	 * 公司树
	 *
	 * @return
	 */
	@Override
	public BaseVo companyTree() {
		List<Company> all = companyDao.selectList(null);
		//根节点
		List<Company> rootList = new ArrayList<>();
		//递归调用
		if (CollectionUtils.isNotEmpty(all)){
			for (Company company : all) {
				if (company.getLevel().equals(1)){
					rootList.add(company);
				}
			}
		}
		for (Company company : rootList) {
			company.setChildList(getChildList(company,all,company.getId()));
		}
		BaseVo baseVo = new BaseVo();
		baseVo.setData(rootList);
		return baseVo;
	}

	/**
	 * 递归查询子节点
	 * @param company
	 * @param all
	 * @param id
	 * @return
	 */
	public List<Company> getChildList(Company company,List<Company> all,Long id){
		List<Company> list =new ArrayList<>();
		for (Company cp : all) {
			if (cp.getParentId().equals(id)){
				list.add(cp);
			}
		}
		company.setChildList(list);
		for (Company aa : list) {
			aa.setChildList(getChildList(aa,all,aa.getId()));
		}

		if (list.size() == 0) {
			return null;
		}
		return list;
	}


}
