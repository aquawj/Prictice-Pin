package Companys.Intuit;

import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Given an ordered list of employees who used their badge to enter or exit the room,
 * write a function that returns two collections: 
 * // 1. All employees who didn't use their badge while exiting the room –
 * they recorded an enter without a matching exit.
 * // 2. All employees who didn't use their badge while entering the room  –
 * they recorded an exit without a matching enter.*/
public class BadgeRecordsEnterExit {

    //1. 刷卡进，没有刷卡出；
    //2. 没刷卡进，刷开出
    public static void badgeRecordsEnterExit(String[][] records) {

        Map<String, Integer> map = new HashMap<>();
        List<String> enterWithoutBadge = new ArrayList<>();
        List<String> exitWithoutBadge = new ArrayList<>();
        //重点部分，enter +1， exit -1
        for (String[] record : records) {
            int count = 0;
            if (record[1].equals("enter")) {
                count = 1;
            }
            if (record[1].equals("exit")) {
                count = -1;
            }
            map.put(record[0], map.getOrDefault(record[0], 0) + count);
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() > 0) {
                exitWithoutBadge.add(entry.getKey());
            }
            if (entry.getValue() < 0) {
                enterWithoutBadge.add(entry.getKey());
            }
        }

        System.out.println(exitWithoutBadge);
        System.out.println(enterWithoutBadge);
    }

    public void moreOften(int[][] records){
        Map<String,List<Integer>> map = new HashMap<>();
        for(int[] r : records) {
            List<Integer> list = map.get(r[0]);
            if(list == null) {
                list = new ArrayList<>();
            }
            list.add(convert(r[1]));
            Map.put(r[0],list);
        }
        for(Map.Entry<String,List<Integer>> entry: map.entrySet()) {
            List<Integer> list = entry.getValue();
            if(list.size()>=3){
                Collections.sort(list);
                for(int i=0; i<list.size();i++) {
                    int curr = list.get(i);
                    int index = find(list,curr+60);
                    if(index-i>=3) {
                        res.addAll(list,i,index);
                        break;
                    }
                }
            }

        }
    }

    public static void main(String[] args) {
        String[][] records = {{"Martha","exit"},
                {"Paul","enter"},
                {"Martha","enter"},
                {"Martha","exit"},
                {"Jennifer","enter"},
                {"Paul","enter"},
                {"Curtis","enter"},
                {"Paul","exit"},
                {"Martha","enter"},
                {"Martha","exit"},
                {"Jennifer","exit"}
        };
        badgeRecordsEnterExit(records);

    }
}
