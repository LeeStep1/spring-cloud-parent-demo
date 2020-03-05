package com.bit.module.manager.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.base.exception.BusinessException;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
import com.bit.common.informationEnum.ElevatorTypeEnum;
import com.bit.common.wxenum.ResultCode;
import com.bit.module.manager.bean.Category;
import com.bit.module.manager.bean.FileInfo;
import com.bit.module.manager.bean.ProjectEleOrder;
import com.bit.module.manager.dao.ElevatorTypeDao;
import com.bit.module.manager.dao.FileInfoDao;
import com.bit.module.manager.dao.ProjectEleOrderDao;
import com.bit.module.manager.service.ElevatorTypeService;
import com.bit.module.manager.vo.ElevatorTypePageVO;
import com.bit.module.manager.vo.ElevatorTypeVO;
import com.bit.module.miniapp.bean.ElevatorType;
import com.bit.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service("elevatorTypeService")
public class ElevatorTypeServiceImpl extends BaseService implements ElevatorTypeService {

	@Autowired
	private ElevatorTypeDao elevatorTypeDao;
	@Autowired
	private ProjectEleOrderDao projectEleOrderDao;

	@Value("${server.servlet.context-path}")
	private String contextPath;
	@Value("${upload.imagesPath}")
	private String imagePath;


	/**
	 * 新增数据
	 *
	 * @param elevatorType
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo add(ElevatorType elevatorType) {
		elevatorTypeDao.addElevatorType(elevatorType);
		return successVo();
	}

	/**
	 * 编辑数据
	 *
	 * @param elevatorType
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo update(ElevatorType elevatorType) {
		if (elevatorType.getPicture().contains("/quotation/images/")){
			elevatorType.setPicture(elevatorType.getPicture().replace("/quotation/images/",""));
		}
		elevatorTypeDao.updateElevatorType(elevatorType);
		return successVo();
	}

	/**
	 * 删除数据
	 *
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo delete(Long id) {
		ElevatorType elevatorTypeById = elevatorTypeDao.getElevatorTypeById(id);
		if (elevatorTypeById==null){
			throw new BusinessException("记录不存在");
		}
		ProjectEleOrder order = new ProjectEleOrder();
		order.setElevatorTypeId(elevatorTypeById.getId());
		List<ProjectEleOrder> byParam = projectEleOrderDao.findByParam(order);
		if (CollectionUtils.isNotEmpty(byParam)){
			throw new BusinessException("存在关联数据不能删除");
		}
		elevatorTypeDao.delElevatorTypeById(id);
		return successVo();
	}



	/**
	 * 单查数据
	 *
	 * @param id
	 * @return ElevatorType
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	public BaseVo reflectById(Long id) {
		ElevatorType elevatorTypeById = elevatorTypeDao.getElevatorTypeById(id);
		ElevatorTypeVO elevatorTypeVO = new ElevatorTypeVO();
		//设置图片地址
		if (StringUtil.isNotEmpty(elevatorTypeById.getPicture())){
			String pic = contextPath + "/images/" + elevatorTypeById.getPicture();
			elevatorTypeById.setPicture(pic);
		}
		BeanUtils.copyProperties(elevatorTypeById, elevatorTypeVO);
		elevatorTypeVO.setTypeEnumName(ElevatorTypeEnum.getValueByCode(elevatorTypeVO.getType()));

		BaseVo baseVo = new BaseVo();
		baseVo.setData(elevatorTypeVO);
		return baseVo;
	}

	/**
	 * 电梯类型列表查询
	 *
	 * @return
	 */
	@Override
	public BaseVo elevatorTypeListPage(ElevatorTypePageVO elevatorTypePageVO) {
		Page<ElevatorTypeVO> page = new Page<>(elevatorTypePageVO.getPageNum(), elevatorTypePageVO.getPageSize());
		IPage<ElevatorTypeVO> elevatorTypePage = elevatorTypeDao.elevatorTypeListPage(page, elevatorTypePageVO);
		if (CollectionUtils.isNotEmpty(elevatorTypePage.getRecords())) {
			for (ElevatorTypeVO vo : elevatorTypePage.getRecords()) {
				vo.setTypeEnumName(ElevatorTypeEnum.getValueByCode(vo.getType()));
				//设置图片地址
				if (StringUtil.isNotEmpty(vo.getPicture())){
					String pic = contextPath + "/images/" + vo.getPicture();
					vo.setPicture(pic);
				}
			}
		}
		BaseVo baseVo = new BaseVo();
		baseVo.setData(elevatorTypePage);
		return baseVo;
	}

	/**
	 * 电梯类型
	 * @return
	 */
	@Override
	public BaseVo categoryList() {
		List<Category> catoryList = ElevatorTypeEnum.getCatoryList();
		BaseVo baseVo = new BaseVo();
		baseVo.setData(catoryList);
		return baseVo;
	}

	/**
	 * paramsKey验重
	 * @param elevatorType
	 * @return
	 */
	@Override
	public BaseVo distinctParams(ElevatorType elevatorType) {
		BaseVo baseVo = new BaseVo();
		List<ElevatorType> byParam = elevatorTypeDao.findByParam(elevatorType);
		if (CollectionUtils.isNotEmpty(byParam)){
			baseVo.setCode(ResultCode.PARAMS_KEY_EXIST.getCode());
			baseVo.setMsg(ResultCode.PARAMS_KEY_EXIST.getInfo());
		}else {
			baseVo.setCode(ResultCode.PARAMS_KEY_NOT_EXIST.getCode());
			baseVo.setMsg(ResultCode.PARAMS_KEY_NOT_EXIST.getInfo());
		}
		return baseVo;
	}

	/**
	 * 电梯类型全查
	 * @return
	 */
	@Override
	public BaseVo findAll() {
		List<ElevatorType> byParam = elevatorTypeDao.findByParam(null);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(byParam);
		return baseVo;
	}

	/**
	 * 上传图片
	 * @param multipartFile
	 * @return
	 */
	@Override
	public BaseVo uploadImage(MultipartFile multipartFile,String fileName) {
		if (multipartFile==null){
			throw new BusinessException("文件是空");
		}
		OutputStream out =null;
		OutputStream toClient = null;
		String filePath = "ele/"+fileName;
		try {

			byte[] buffer = multipartFile.getBytes();
			File file = new File(imagePath+"ele\\"+fileName);
			if (file.exists()){
				deleteFile(file);
			}else {
				file.createNewFile();
			}
			out = new FileOutputStream(file);
			toClient = new BufferedOutputStream(out);
			toClient.write(buffer);
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			try {
				toClient.flush();
				toClient.close();
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		BaseVo baseVo = new BaseVo();
		baseVo.setData(filePath);
		return baseVo;
	}

	//递归删除文件夹
	private boolean deleteFile(File dirFile){
		// 如果dir对应的文件不存在，则退出
		if (!dirFile.exists()) {
			return false;
		}

		if (dirFile.isFile()) {
			return dirFile.delete();
		} else {

			for (File file : dirFile.listFiles()) {
				deleteFile(file);
			}
		}

		return dirFile.delete();

	}
}