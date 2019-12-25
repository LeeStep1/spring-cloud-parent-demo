package com.bit.module.miniapp.controller;


import com.bit.base.vo.BaseVo;
import com.bit.module.manager.service.AreaService;
import com.bit.module.miniapp.bean.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.web.bind.annotation.*;


/**
 * 采集居民中的一标三实的区划代码
 *
 * @author chenduo
 * @email ${email}
 * @date 2019-12-23 10:29:33
 */
@RestController
@RequestMapping("/wx/area")
public class AreaController {

    @Autowired
    private AreaService areaService;


	/**
	 * 查询下级地域
	 * @param id
	 * @return
	 */
	@PostMapping("/getAreas")
    public BaseVo getAreas(@RequestParam(value = "id",required = false)String id){
		return areaService.getAreas(id);
	}

	/**
	 * 地域缓存树
	 * @return
	 */
	@PostMapping("/getAreasCacheTree")
	public BaseVo getAreasCacheTree(){
		return areaService.getAreasCacheTree();
	}
}
