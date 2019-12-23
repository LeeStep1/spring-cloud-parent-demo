package com.bit.module.manager.dao;

import com.bit.module.manager.bean.FileInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文件对象的crud
 * @author lyj
 */
@Repository
public interface FileInfoDao {


    /**
     * @param fileInfo
     */
    void insert(FileInfo fileInfo);


    /**
     * @param fileId
     * @return
     */
    List<FileInfo> query(@Param("fileId") Long fileId);

    /**
     * 批量获取图片地址
     * @author liyang
     * @date 2019-10-21
     * @param fileId : 图片ID集合
     * @return : List<FileInfo>
    */
    List<FileInfo> queryByIds(@Param("fileId") List<Long> fileId);

    /**
     * 根据id主键查询
     * @param id
     * @return
     */
    FileInfo findById(@Param(("id")) Long id);

    /**
     * 根据id批量删除
     * @param ids
     */
    void batchDelete(@Param(value = "ids") List<Long> ids);


}
