package com.eric.utils;

import cn.hutool.core.io.file.FileReader;
import com.eric.entity.LogEntity;
import com.eric.entity.LogHandler;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chen 2018/1/23
 */
public class logUtils {

    public static List<LogEntity> getLogEntities(List<String> files){
        List<LogEntity> entities = new ArrayList<LogEntity>();
        for (String file :files) {
            try {
                List<LogEntity> logEntity = getLogEntity(file);
                if (logEntity!=null&& logEntity.size()>1)
                    entities.addAll(logEntity);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        return entities;
    }

    public static List<LogEntity> getLogEntity(String filePath)throws Exception{
        FileReader fileReader = new FileReader(filePath, Charset.forName("UTF-8"));
        return fileReader.read(new LogHandler());
    }

}
