package com.example.demo;

import com.example.demo.model.fileModel;
import com.example.demo.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class fileService {
    @Autowired
    public FileMapper mapper;


    public List<fileModel> getFile() {
        return mapper.getFile();
    }


//    public final FileMapper mapper;
//
//    public fileService(FileMapper mapper) {
//        this.mapper = mapper;
//    }
//
//    public List<fileModel> getFile() {
//        return mapper.getFile();
//    }
}
