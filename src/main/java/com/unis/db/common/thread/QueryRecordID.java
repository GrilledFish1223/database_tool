package com.unis.db.common.thread;

import java.util.*;

/**
 * @author xuli
 * @date 2019/2/12
 */
public class QueryRecordID {

    private static final int RECORDID_COUNT = 100;

    /**
     * 随机100个recordID对应30张表,直接返回sql
     *
     * @return 返回recordID 数组
     */
    public static String[] randomRecordID() {
        Random random = new Random();
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < RECORDID_COUNT; i++) {
            int recordID = random.nextInt(35000000);
            int number = (recordID / 5000000) + 20181201;
            if (map.containsKey(number)) {
                map.get(number).add(recordID);
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(recordID);
                map.put(number, list);
            }
        }
        String[] array = new String[map.size()];
        int i = 0;
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            StringBuilder sb = new StringBuilder("select * from viid_vehicle.vehiclestructured_a040200_" + entry.getKey() + " where recordid in (");
            Iterator<Integer> it = entry.getValue().iterator();
            boolean flag = true;
            while (it.hasNext()) {
                if (!flag) {
                    sb.append("," + it.next());
                } else {
                    sb.append(it.next());
                    flag = false;
                }
            }
            sb.append(")");
            array[i++] = sb.toString();
        }
        return array;
    }

}
