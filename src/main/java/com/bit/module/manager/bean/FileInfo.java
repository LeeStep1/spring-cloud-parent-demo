package com.bit.module.manager.bean;

import lombok.Data;

@Data
public class FileInfo {

    private Long id;

    private String fileName;

    private String path;

    private String suffix;

    private Long fileSize;

    private Integer fileTime;

}
