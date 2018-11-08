/**
* 1. 给你一个Amazon S3 的 link，打开是一个几万行的文本文件，每一行有两个字段，第一个是 word（全是大写字母），第二个是该 word 的频数，用 tab 分割字段。已知该文件按照频数降序排列，给你 N 和 K 要求找到所有长度不小于2不大于N的 top-K 个词和其出现的频数。（要求从link中读入文本文件）

2. 对第一问得到的 top-K 个词中的每一个，找到其存在于这K个词中的 1-step 近邻（以任意数据结构返回）。
任意词 A 的 1-step 近邻的定义：在 A 的任意位置插入任意的一个大写字母，并对其乱序产生的所有词的集合。


3. 基于 1，2 问的答案，给一个 start word 和一个 end word，返回从 start word 到 end word 的最大路径。（类似LC Word Ladder）
要求 path 中的每一步都是前一步的 1-step 近邻且存在于第1问得到的词表中，路径的大小是路径中每个词的频数之和。
 */
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class TextTopK {
    String url;
    int K;
    int N;

    public TextTopK(String url, int k, int n) {
        this.url = url;
        K = k;
        N = n;
    }

    //1.
    public Map<String, String> topKFromUrl() throws Exception{
        Map<String, String> res = new HashMap<>();
        try {
            //Scanner scanner = new Scanner(new URL(url).openStream(),"UTF-8").useDelimiter("\\n");
            Scanner scanner = new Scanner(new URL(url).openStream()).useDelimiter("\\n"); //new Scanner(inputStream)
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                addToTopK(line, K, N, res);
                if(res.size() == K){
                    break;
                }
            }
            scanner.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }

    public void addToTopK(String line, int K, int N, Map<String, String> res){
        String[] parts = line.split("\t");
        String word = parts[0];
        String freq = parts[1];
        if(word.length() >= 2 && word.length() <= N){
            res.put(word, freq);
        }
    }

//2.
    public Map<String,Set<String>>  getStepWords(List<String[]> text){
        Map<String,Set<String>> result = new HashMap<>(); //<word, set<stepWords>>
        Map<String,List<String>> dict = new HashMap<>(); //<key, List<words>>
        // build dictionary map: key -> list of word
        for(String[] line : text){
            String word = line[0];
            String key = getKey(word,' ');
            dict.putIfAbsent(key, new ArrayList<>());
            dict.get(key).add(word);
        }
        // build result map: word -> set of stepWord
        for(String[] line : text){
            String word = line[0];
            Set<String> stepSet = new HashSet<>();
            for(char ch = 'A'; ch < 'Z'; ch++){
                String stepKey = getKey(word,ch); //step's key
                if(dict.containsKey(stepKey)){
                    stepSet.addAll(dict.get(stepKey));
                }
            }
            result.put(word,stepSet);
        }

    return result;

    }

    //word和字符c组合生成的所有可能词的key
    //巧妙，用int[]来表示key
    //word包括的所有字母，和要加入的新字母，位置上全部为1
    public String getKey(String word, char c){
        int[] charKey = new int[26];
        for(char ch : word.toCharArray()){
            charKey[ch - 'A']++;
        }
        if(c != ' '){
            charKey[c - 'A']++;
        }
        return Arrays.toString(charKey);  // int[]{a, b, c}的String形式为“[a, b, c]”
    }

    //3.


    public void printMap(Map<String, Set<String>> map){
        for(String key : map.keySet()){
            System.out.print(key + ": {" );
            for(String v : map.get(key)){
                System.out.print(v + " ");
            }
            System.out.println("}");
        }
    }
    public void printList(List<String> list){
        System.out.println("printing list...");
        for(String s : list){
            System.out.print(s + " ");
        }
        System.out.println();
    }

    public static void main(String[] args){
//        String[] text = {"DOES     5","LOVE    4","PIN     4","AETA    4","AMEX    3","PINT    3","TEA     3",
//                "DOE    2", "EAT    2"};
        List<String[]> text = new ArrayList<>();
        text.add(new String[]{"a", "4"});
        text.add(new String[]{"ab", "4"});
        text.add(new String[]{"xa", "3"});
        text.add(new String[]{"abc", "3"});
        text.add(new String[]{"xxa", "2"});
//        TextTopK t = new TextTopK(9, 4, text);
//        t.printMap(t.topK());
//        t.printList(t.getAllStepWords());
    }
}
