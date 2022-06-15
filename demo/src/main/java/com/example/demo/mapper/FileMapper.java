package com.example.demo.mapper;

import com.example.demo.model.fileModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface FileMapper {
    List<fileModel> getFile();
}