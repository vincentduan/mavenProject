package cn.ac.iie.utils;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class SortUtils {

    public static Map<Integer, String> sortMap(Map<String, Integer> map) {
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        for(Map.Entry<String, Integer> entry: entries) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            treeMap.put(value, key);
        }
        return treeMap;
    }

}
