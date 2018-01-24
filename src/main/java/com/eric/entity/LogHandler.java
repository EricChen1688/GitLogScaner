package com.eric.entity;

import cn.hutool.core.io.file.FileReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chen 2018/1/23
 */
public class LogHandler implements FileReader.ReaderHandler<List<LogEntity>> {

    public List<LogEntity> handle(BufferedReader reader) throws IOException {
        String line = null;
        List<LogEntity> entities = new ArrayList<LogEntity>();
        List<String> lines = new ArrayList<String>();
        while((line = reader.readLine()) != null){
            if(line.contains("commit")&&!line.contains(".")&&!line.contains(":")&&!line.contains("(")){
                if (lines.size()>1){
                    LogEntity logfilter = logfilter(lines);
                    if (logfilter!=null) entities.add(logfilter);
                }
                lines = new ArrayList<String>();
                if (!line.trim().equals(""))
                    lines.add(line);
            }else {
                if (!line.trim().equals(""))
                    lines.add(line);
            }
        }
        return entities;
    }

    /**
     *log过滤算法
     * @param lines 每次提交
     * @return 本次提交实体类 Author名字，count 本次提交代码量
     */
    private LogEntity logfilter(List<String> lines){
        if (!checkData(lines)) return null;
        LogEntity entity = new LogEntity();
        //统计前三个
        for (int i = 0;i<3;i++){
            String line = lines.get(i);
            if (line.contains("Author:"))
                entity.setAuthor(line.substring(8,line.indexOf("<")).trim());

        }
        for (int i =3;i<lines.size();i++){
            String line = lines.get(i);
            if (line.contains("+")){
                if(line.trim().length()>2&&!line.contains("}")){
                    if(line.indexOf("+")==0&&line.charAt(1)!='+'){
                        entity.setCount(entity.getCount()+1);
                    }
                }

            }
        }
        return entity;
    }

    private boolean checkData(List<String> lines){
        if (lines.size()<5) return false;
        for (int i = 0;i<5;i++){
            String line = lines.get(i);
            if (line.contains("2017 +0800")) return true;
        }
        return false;
    }

}
