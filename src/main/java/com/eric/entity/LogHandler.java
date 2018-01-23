package com.eric.entity;

import cn.hutool.core.io.file.FileReader;
import com.eric.utils.logUtils;

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
                    entities.add(logfilter(lines));
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
        if (lines.size()<3){
            LogEntity entity = new LogEntity();
            entity.setAuthor("本次提交不足3行，跳过");
            entity.setCount(0);
            return entity;
        }
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

}
