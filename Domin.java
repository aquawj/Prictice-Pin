import java.util.*;

public class Domin {

    //1. 统计每个domin和子domin的点击数
    public static void domainClick(String[][] domains) {
        Map<String, Integer> map = new HashMap<>();
        for(String[] domainclick : domains) {
            String domain = domainclick[0];
            String[] doms = domain.split("\\.");
            int click = Integer.parseInt(domainclick[1]);
            for (int i = 0; i < doms.length; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = i; j < doms.length; j++) {
                    sb.append(doms[j]).append('.');
                }
                sb.setLength(sb.length() - 1);
                map.put(sb.toString(), map.getOrDefault(sb.toString(), 0) + click);
            }
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());

        }
    }
    //2. 给每个user访问历史记录，找出两个user之间longest continuous common history

    public static List<String> longestHistory(String[] one, String[] two){
        int[][] dp = new int[one.length+1][two.length+1]; //dp[i][j]表示到one[i]和two[j]结尾的最长共同记录的length
        int max = 0; //最长长度
        int end = -1; //最长长度的结束点
        for(int i=1;i<one.length+1;i++){
            for(int j=1; j<two.length+1;j++) {
                if(one[i-1].equals(two[j-1])) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                }else{
                    dp[i][j] = 0;
                }
                if(dp[i][j] > max){
                    max = dp[i][j];
                    end = i-1;
                }
            }
        }
        List<String> list = new ArrayList<>();
        for(int i =end; i > end-max; i--){
            list.add(one[i]);
        }
        Collections.reverse(list);
        printList("longestHistory: ", list);
        return list;
    }


    static void printList(String s, List<String> list) {
        System.out.println(s);
        for(String i: list){
            System.out.println(i + " --> ");
        }
        System.out.println();
    }

    static void printMap(String s, Map<String, Integer> map) {
        System.out.println(s);
        for(String ss: map.keySet()){
            System.out.println(ss + " --> " + map.get(ss));
        }
        System.out.println();
    }

    // Driver Code
    public static void main(String args[])
    {
        Map<String, Integer> map = new HashMap<>();
        map.put("google.com", 60);
        map.put("yahoo.com", 50);
        map.put("sports.yahoo.com", 80);

        //count(map);
        String[] user11 = new String[] {"3234.html", "xys.html", "7hsaa.html"};
        String[] user22 = new String[] {"3234.html", "sdhsfjdsh.html", "xys.html", "7hsaa.html"};
        String[] user0 = new String[] {"/nine.html", "/four.html", "/six.html", "/seven.html", "/one.html"};
        String[] user1 = new String[] {"/one.html", "/two.html", "/three.html", "/four.html", "/six.html"};
        String[] user2 = new String[] {"/nine.html", "/two.html", "/three.html", "/four.html", "/six.html", "/seven.html" };
        String[] user3 = new String[] {"/three.html", "/eight.html"};
        longestHistory(user11, user22);
        longestHistory(user0, user2);
        longestHistory(user1, user2);
        longestHistory(user0, user3);
        longestHistory(user1, user3);
    }
}
