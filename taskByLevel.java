package Companys.Intuit;

import java.util.*;
import java.util.HashMap;
import java.util.Map;

public class taskByLevel {
    public static void main(String[] args) {
        //do cook before eat
        String[][] tasks = {{"cook", "eat"},{"study", "eat"},{"sleep", "study"}};
        System.out.println(taskByLevel(tasks));
    }

    public static List<List<String>> taskByLevel(String[][] tasks) {

        Map<String, Set<String>> map = new HashMap<>();
        Map<String, Integer> indegreemap = new HashMap<>();
        Set<String> alltasks = new HashSet<>();

        for (String[] task : tasks) {//task[0]=cook  task[1]=eat   先-后
            map.putIfAbsent(task[0], new HashSet<>());
            map.get(task[0]).add(task[1]);
            alltasks.add(task[0]);
            alltasks.add(task[1]);
            indegreemap.put(task[1], indegreemap.getOrDefault(task[1], 0) + 1);
        }

        Queue<String> queue = new LinkedList<>();
        for (String s : alltasks) {
            if (!indegreemap.containsKey(s)) { //degre=0
                queue.offer(s);
            }
        }

        List<List<String>> res = new ArrayList<>();
        while (!queue.isEmpty()) {
            List<String> list = new ArrayList<>(); //每层一个list
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String ele = queue.poll();
                list.add(ele);
                if (map.containsKey(ele)) {
                    for (String s : map.get(ele)) {
                        indegreemap.put(s, indegreemap.get(s) - 1);
                        if (indegreemap.get(s) == 0) {
                            queue.offer(s);
                        }
                    }
                }

            }
            res.add(list);
        }
        return res;
    }
}
