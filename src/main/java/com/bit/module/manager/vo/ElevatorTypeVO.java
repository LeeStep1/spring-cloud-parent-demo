package com.bit.module.manager.vo;

import com.bit.module.manager.bean.FileInfo;
import lombok.Data;

/**
 * @Description
 * @Author chenduo
 * @Date 2019/12/25 9:27
 **/
@Data
public class ElevatorTypeVO {
	/**
	 *
	 */
	private Long id;
	/**
	 * 类别名称
	 */
	private String typeName;
	/**
	 * 1:客梯,2:别墅梯，3:货梯，4：扶梯
	 */
	private Integer type;
	/**
	 * 所属系列
	 */
	private String series;
	/**
	 * 运算用key
	 */
	private String paramsKey;
	/**
	 * 图片地址
	 */
	private String picture;
	/**
	 * 附件信息
	 */
	private FileInfo fileInfo;
	/**
	 * 电梯类型名称
	 */
	private String typeEnumName;
}
