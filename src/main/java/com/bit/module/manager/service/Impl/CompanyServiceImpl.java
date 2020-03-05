package com.bit.module.manager.service.Impl;

import com.bit.base.exception.BusinessException;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.Company;
import com.bit.module.manager.bean.CompanyRate;
import com.bit.module.manager.bean.UserCompany;
import com.bit.module.manager.dao.CompanyDao;
import com.bit.module.manager.dao.CompanyRateDao;
import com.bit.module.manager.dao.UserCompanyDao;
import com.bit.module.manager.dao.UserDao;
import com.bit.module.manager.service.CompanyService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2020-01-03
 **/
@Service("companyService")
public class CompanyServiceImpl extends BaseService implements CompanyService {

	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private UserCompanyDao userCompanyDao;
	@Autowired
	private CompanyRateDao companyRateDao;


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
	public BaseVo companyTree(Company company) {
		List<Company> all = companyDao.selectList(null);
		List<Company> byParam = companyDao.companyTreeSearch(company);

		//根节点
		List<Company> rootList = new ArrayList<>();
		//递归调用
		if (CollectionUtils.isNotEmpty(byParam)){

			//找出来最小的等级
			Set<Integer> set = new HashSet<>();
			for (Company cp : byParam) {
				if (!set.contains(cp.getLevel())){
					set.add(cp.getLevel());
				}
			}

			Integer min = 0;
			if (CollectionUtils.isNotEmpty(set)){
				min = Collections.min(set);
			}

			for (Company cp : byParam){
				if (min.equals(cp.getLevel())){
					rootList.add(cp);
				}
			}
		}
		for (Company cp : rootList) {
			cp.setChildList(getChildList(cp,all,cp.getId()));
		}
		BaseVo baseVo = new BaseVo();
		baseVo.setData(rootList);
		return baseVo;
	}

	/**
	 * 新增公司
	 * @return
	 */
	@Override
	@Transactional
	public BaseVo addCompany(Company company) {
		companyDao.insert(company);
		BaseVo rs=new BaseVo();
		rs.setData(company);
		return rs;
	}

	/**
	 * 更新公司
	 * @param company
	 * @return
	 */
	@Override
	@Transactional
	public BaseVo updateCompany(Company company) {
		companyDao.updateCompany(company);
		return successVo();
	}

	/**
	 * 删除公司
	 * @param id
	 * @return
	 */
	@Override
	@Transactional
	public BaseVo delCompany(Long id) {
		Company companyById = companyDao.getCompanyById(id);
		if (companyById==null){
			throw new BusinessException("公司不存在");
		}
		//判断有没有子公司
		Company company = new Company();
		company.setParentId(id);
		List<Company> subCompany = companyDao.findByParam(company);
		if (CollectionUtils.isNotEmpty(subCompany)){
			throw new BusinessException("公司下有子公司，不能删除");
		}

		//查询公司下有没有人
		UserCompany userCompany = new UserCompany();
		userCompany.setCompanyId(id);
		//正常用户
		userCompany.setStatus(1);
		List<UserCompany> byParam = userCompanyDao.findByParam(userCompany);
		if (CollectionUtils.isNotEmpty(byParam)){
			throw new BusinessException("公司下有用户，不能删除");
		}
		//查询公司下有没有下浮率
		CompanyRate rate = new CompanyRate();
		rate.setCompanyId(id);
		List<CompanyRate> rateParam = companyRateDao.findByParam(rate);
		if (CollectionUtils.isNotEmpty(rateParam)){
			throw new BusinessException("公司下有下浮率，不能删除");
		}
		//删除公司
		companyDao.delCompanyById(id);

		return successVo();
	}

	/**
	 * 单查公司
	 * @param id
	 * @return
	 */
	@Override
	public BaseVo reflectById(Long id) {
		Company companyById = companyDao.getCompanyById(id);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(companyById);
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
