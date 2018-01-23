package com.eric.utils;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chen 2018/1/23
 */
public class fileUtils {
    /**
     * 获取指定目录下的文件
     * @param filePath
     * @return
     */
    public static List<String> getFiles(String filePath){
        List<String> filelist = new ArrayList<String>();
        File root = new File(filePath);
        File[] files = root.listFiles();
        if (files == null) return filelist;
        for(File file:files){
            if(file.isDirectory()){
                getFiles(file.getAbsolutePath());
                filelist.add(file.getAbsolutePath());
                System.out.println("子目录及其文件"+file.getAbsolutePath());
            }else{
                filelist.add(file.getAbsolutePath());
            }
        }
        return filelist;
    }
}
