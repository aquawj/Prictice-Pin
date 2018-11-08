package Companys.Intuit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 1     2  3
 /  \  /      \
 4    5        6.
                 \
                 7
 输入是int[][] input, input[0]是input[1] 的parent，
 比如 {{1,4}, {1,5}, {2,5}, {3,6}, {6,7}}会形成上面的图
 第一问是只有0个parents和只有1个parent的节点
 第二问是两个指定的点有没有公共祖先.
 第三问是就一个点的最远祖先，如果有好几个就只需要输出一个就好，举个栗子，这里5的最远祖先可以是1或者2，输出任意一个就可以

 时间很紧，一个钟头基本上说说oa的思路就过去一刻钟了，然后还要分析时间复杂度和空间辅助度
 */
public class CommonAncestor {

    //1. 输出只有1个和0个parent的individual.

    static void printparents (String[][] datasets) {
        Set<String> parents = new HashSet<>();
        Map<String, Integer> map = new HashMap<>(); //<child, number of parent>
        for (String[] data: datasets) {
            parents.add(data[0]);
            map.put(data[1], map.getOrDefault(data[1], 0) + 1);
        }
        // one parent
        List<String> oneParent = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                oneParent.add(entry.getKey());
            }
        }
        //zero parent：在set不在map的
        List<String> zeroParent = new ArrayList<>();
        for (String s : parents) {
            if (!map.containsKey(s)) {
                zeroParent.add(s);
            }
        }
        System.out.println("one parent :");
        System.out.println(oneParent);
        System.out.println("zero parent :");
        System.out.println(zeroParent);
    }

    //2. 两个点是否有share parents

    static boolean shareAncestor(String[] idPair, String[][] dataset) {
        if (dataset == null || dataset.length == 0) {
            return false;
        }

        //key: child   value: parents
        Map<String, Set<String>> map = new HashMap<>();
        for (String[] s : dataset) {
            map.putIfAbsent(s[1], new HashSet<>());
            map.get(s[1]).add(s[0]);
        }

        if (!map.containsKey(idPair[0])||!map.containsKey(idPair[1])) {
            return false;
        }
        Set<String> set1 = new HashSet<>();
        dfs(map, idPair[0], set1);
        Set<String> set2 = new HashSet<>();
        dfs(map, idPair[1], set2);

        for (String s : set1) {
            if (set2.contains(s)) {
                return true;
            }
        }
        return false;
    }

    static void dfs(Map<String, Set<String>> map, String s, Set<String> set) {
        if (!map.containsKey(s)) {
            return;
        }
        for (String parent : map.get(s)) {
            if (set.contains(parent)) {
                continue;
            }
            set.add(parent);
            dfs(map, parent, set);
        }
    }



    // 3. 最远祖先 BFS
    static List<String> longestAncestor(String[][] dataset, String child) {
        Map<String, Set<String>> map = new HashMap<>(); //还是 child-parent set
        for (String[] data : dataset) {
            map.putIfAbsent(data[1], new HashSet<>());
            map.get(data[1]).add(data[0]);
        }
        Queue<String> queue = new LinkedList<>();
        queue.offer(child); //存它所有的parent
        List<String> res = new ArrayList<>();
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String ele = queue.poll();
                if (!map.containsKey(ele)) {
                    res.add(ele); //只有这里加结果。这个parent已经不再有parent了
                }
                if (map.containsKey(ele)) {
                    for (String parent : map.get(ele)) {
                        queue.offer(parent);
                    }
                }
            }
        }
        return res;
    }

    // DO NOT MODIFY MAIN()
    public static void main(String[] args) {
        String[] idPair = {"3", "8"};
        String[][] dataset = {{"1","3"},{"2","3"},{"3","6"},{"5","6"},{"5","7"},{"4","5"},{"4","8"},{"8","9"}};
        //System.out.println(shareAncestor(idPair, dataset));
        //printparents(dataset);
        System.out.println(longestAncestor(dataset, "6"));
//        String[] arg0 = new String[]{};
//        List<String[]> arg1 = new ArrayList<String[]>();
//        String line;
//        boolean first_arg = true;
//        try (BufferedReader br = new BufferedReader(new
//                InputStreamReader(System.in))) {
//            while ((line = br.readLine()) != null) {
//                if (line.equals("")) {
//                    continue;
//                }
//                if(first_arg) {
//                    arg0 = line.split(" ");
//                    first_arg = false;
//                } else {
//                    arg1.add(line.split(" "));
//                } }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//        }
//        String[][] friendships = arg1.toArray(new String[arg1.size()][]);
//        if(shareAncestor(arg0, friendships)) {
//            System.out.println("true");
//        } else {
//            System.out.println("false");
//        }
    }

}
