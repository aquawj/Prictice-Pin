
/**
 * 给一些<studenID, course name>，返回每两个学生上的一样的课
eg. ("1", "A"), ("2", "B"), ("5", "E"), ("1", "B"), ("2", "A"), ("2", "E")
返回 ("1", "2") -> ["A","B"]
("1", "5") -> []
("2", "5") -> ["E"]
2. 􁕳􀓞􀔶<course, course>􀕤􁤒prerequisite􀒅􀝝􀹍􀓞􀹵􀝢􁤈􁌱􁪠􀮆􀒅􁬬􀢧􀓾􁳵􁌱􁮎
pre-post
eg. ("A", "C"), ("B", "D"), ("D", "A"), ("G", "E"), ("C", "F"), ("E", "B")
􀠔􀓞􁪠􀮆􀔅 G->E->B->D->A->C->F
􁬬􀢧D
3. 􁕳􀓞􀔶<course, course>􀕤􁤒prerequisite􀒅􁬬􀢧􀾯􀹵􁪠􀮆􀓾􁳵􁌱􁮎􁜓􁧞􀌶
eg. ("A", "B"), ("B", "D"), ("E", "B"), ("E", "C"), ("C", "F"), ("E", "F")
􁪠􀮆􀔅 ABD, EBD, ECF, EF
􁬬􀢧B,C,E*/
import java.util.*;
public class Course {

    //1. 时间O(m^2 + n) 空间 O(m) m:student number < n
    public List<Set<String>> getPairs(List<String> list) { // O(m^2) m:student number
        List<Set<String>> res = new ArrayList<>();
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                Set<String> pair = new HashSet<>();
                pair.add(list.get(i));
                pair.add(list.get(j));
                res.add(pair);
            }
        }
        return res;
    }

    public Map<Set<String>, Set<String>> getSameClasses(List<String[]> list){
        Map<Set<String>, Set<String>> res = new HashMap<>(); //pair: set<shareCourse>
        Map<String, Set<String>> studentMap = new HashMap<>(); //student: set<couse> 空间O(m)
        for(String[] item : list){  //时间O(n)
            studentMap.putIfAbsent(item[0], new HashSet<>());
            studentMap.get(item[0]).add(item[1]);
        }

        List<Set<String>> pairs = getPairs(new ArrayList<>(studentMap.keySet()));
        for(Set<String> pair : pairs){ //O(pairs) < O(n)
            res.putIfAbsent(pair, new HashSet<>());
            String[] pairStr = new String[2];
            int idx = 0;
            for(String str : pair){
                pairStr[idx++] = str;
            }
            Set<String> set = studentMap.get(pairStr[0]);
            set.retainAll(studentMap.get(pairStr[1]));
            res.put(pair, new HashSet<>(set));
        }
        return res;
    }
    //2. one path
    public String findPathMid(List<String[]> list){
        Set<String> set = new HashSet<>();
        List<String> path = new ArrayList<>();
        Map<String, String> map = new HashMap<>();

        for(String[] prePair : list){
            set.add(prePair[0]);
            set.add(prePair[1]);
            map.put(prePair[0], prePair[1]); // pre, post
        }
        String first = "";
        for(String str : set){
            if(!map.containsValue(str)){ // containsValue, 即这门课不是任何课的post
                first = str;
            }
        }
        path.add(first);
        while(map.containsKey(first)){
            path.add(map.get(first));
            first = map.get(first);
        }
        for(String str : path){
            System.out.print(str + " ");
        }
        System.out.println();
        if(path.size() % 2 == 0){
            return path.get(path.size() / 2 - 1);
        }else{
            return path.get(path.size() / 2);
        }
    }

    public void printMap(Map<Set<String>, Set<String>> map){
        for(Set<String> key : map.keySet()){
            System.out.print("( ");
            for(String s : key){
                System.out.print(s + " ");
            }
            System.out.print(")" + ": {");
            if(map.get(key).size() == 0){
                System.out.println("}");
            }else{
                for(String v : map.get(key)){
                    System.out.print(v + " ");
                }
                System.out.println("}");
            }

        }
    }

    public static void main(String[] args){
        Course c = new Course();
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{"1", "A"});
        list.add(new String[]{"2", "B"});
        list.add(new String[]{"5", "E"});
        list.add(new String[]{"1", "B"});
        list.add(new String[]{"2", "A"});
        list.add(new String[]{"2", "E"});
        c.printMap(c.getSameClasses(list));

        List<String[]> preList = new ArrayList<>();
        preList.add(new String[]{"A", "C"});
        preList.add(new String[]{"B", "D"});
        preList.add(new String[]{"D", "A"});
        preList.add(new String[]{"G", "E"});
        preList.add(new String[]{"C", "F"});
        preList.add(new String[]{"E", "B"});
        System.out.println(c.findPathMid(preList));

    }
}
