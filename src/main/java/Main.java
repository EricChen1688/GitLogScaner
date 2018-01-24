import com.eric.entity.LogEntity;
import com.eric.utils.fileUtils;
import com.eric.utils.logUtils;

import java.util.*;

/**
 * @author Chen 2018/1/23
 */
public class Main {
    private static final Map<String,Integer> map = new LinkedHashMap<String, Integer>();

    public static void main(String[] args) {
        List<String> files = fileUtils.getFiles("D:\\qq接收的文件\\code-log\\code-log");
        List<LogEntity> entities = logUtils.getLogEntities(files);
        System.out.println("本年度共提交"+entities.size()+"次,");
        for (LogEntity entity : entities){
            if (map.get(entity.getAuthor()) == null)
                map.put(entity.getAuthor(),entity.getCount());
            else {
                String key =entity.getAuthor();
                Integer value = map.get(key)+entity.getCount();
                map.put(key,value);
            }
        }
        System.out.println("提交详情：单位（行）,");
//        System.out.println(map);
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            System.out.println(entry.getKey()+","+entry.getValue()+",");
        }
    }
}
