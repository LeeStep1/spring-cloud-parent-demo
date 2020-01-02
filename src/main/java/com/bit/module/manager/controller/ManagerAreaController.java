package com.bit.module.manager.controller;


import com.bit.base.vo.BaseVo;
import com.bit.module.manager.service.AreaService;
import com.bit.module.miniapp.bean.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 采集居民中的一标三实的区划代码
 *
 * @author chenduo
 * @email ${email}
 * @date 2019-12-23 10:29:33
 */
@RestController
@RequestMapping("/manager/area")
public class ManagerAreaController {

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

	/**
	 * 地域查询
	 * @param area
	 * @return
	 */
	@PostMapping("/listPage")
	public BaseVo<Area> listPage(@RequestBody Area area){
		return areaService.listPage(area);
	}

	/**
	 * 更新地域信息
	 * @param area
	 * @return
	 */
	@PutMapping("/updateArea")
	public BaseVo updateArea(@RequestBody Area area){
		return areaService.updateArea(area);
	}

	/**
	 * 查询子节点
	 * @param arCode
	 * @return
	 */
	@GetMapping("/getChildArea/{arCode}")
	public BaseVo getChildArea(@PathVariable(value = "arCode")String arCode){
		return areaService.getChildArea(arCode);
	}
}
