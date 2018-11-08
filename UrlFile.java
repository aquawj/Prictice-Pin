package Intuit;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class UrlFile {


  public Map<String,Set<String>>  stepFrom(List<String[]> words){
    Map<String,Set<String>> result = new HashMap<>();

    // [1,0,0,2,0,1,0,2, 0,0,0]    apple alepp
    Map<String,List<String>> dictionary = new HashMap<>();
    for(String[] word : words){
      String key = getKey(word[0],' ');
      if(dictionary.containsKey(key)){
        dictionary.get(key).add(word[0]);
      }
      else{
        List<String> list = new ArrayList<>();
        list.add(word[0]);
        dictionary.put(key,list);
      }
    }

    for(String[] word : words){
      String name = word[0];
      Set<String> wordsStepAway = new HashSet<>();
      for(char ch = 'A'; ch < 'Z'; ch++){
        String key = getKey(name,ch); //step's key
        if(dictionary.containsKey(key)){
          wordsStepAway.addAll(dictionary.get(key));
        }
      }
      result.put(word[0],wordsStepAway);
    }

    return result;

  }

  private String getKey(String name, char ch){
    int[] count = new int[26];
    if (ch != ' '){
      count[ch-'A']++;
    }
    for(char part : name.toCharArray()){
      count[part-'A']++;
    }
    return Arrays.toString(count);
  }

  public List<String[]> readTopFromURL(String url, int N, int K) throws MalformedURLException {
    List<String[]> results = new ArrayList<>();
//    openStream()
//    Opens a connection to this URL and returns an InputStream for reading from that connection.
    try {
//      Scanner scanner = new Scanner(new URL(url).openStream(),"UTF-8").useDelimiter("\\n");
      Scanner scanner = new Scanner(System.in).useDelimiter("\n");
      while(scanner.hasNextLine()){
        String line = scanner.nextLine();
        getTopN(line,N,K, results);
        if(results.size() == N){
          break;
        }
      }
      scanner.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return results;
  }

  private void getTopN(String line, int N, int K, List<String[]> results){
    String[] contents = line.split("\t");
    if(contents[0].length() >= 2 && contents[0].length() <= K ){
      results.add(contents);
    }
  }
  public static void main(String[] args){
    UrlFile obj = new UrlFile();
    try{
      for(String[] arr : obj.readTopFromURL("Fds",2,2))
      System.out.println(Arrays.toString(arr));
    }
    catch(Exception e){
      e.printStackTrace();
    }

    String[][] str = {{"A","1"},{"B","1"},{"C","1"},{"AA","1"},{"BA","1"},{"AC","1"},{"CB","1"},{"ABC","1"},{"ACA","1"},{"CAB","1"}};
    List<String[]> input = Arrays.asList(str);

    LinkedHashMap<String,Long> map = new LinkedHashMap<>();
    map.put("A",(long)1);
    map.put("B",(long)1);
    map.put("C",(long)1);
    map.put("AA",(long)1);
    map.put("BA",(long)1);
    map.put("CB",(long)1);
    map.put("ABC",(long)1);
    map.put("ACA",(long)1);
    map.put("CAB",(long)1);
    System.out.println(obj.step(input));

  }


  public List<String[]> getUrl(String url,int N, int k){
    Scanner scanner = null;
    List<String[]> result = new ArrayList<>();
    try{
      scanner = new Scanner(new URL(url).openStream(),"UTF-8").useDelimiter("\n");
      while(scanner.hasNext()){
        String next = scanner.nextLine();
        process(result,next,k);
        if(result.size() == N){
          break;
        }
      }
    }catch(Exception e){
      e.printStackTrace();
    }finally{
      if(scanner != null){
        scanner.close();
      }
    }
    return result;
  }

  private void process(List<String[]> result, String line, int k){
    String[] parts = line.split("\t");
    if(parts[0].length() >= 2 && parts[0].length() <= k){
      result.add(parts);
    }
  }

  public Map<String,Set<String>> step(List<String[]> list){
    Map<String,Set<String>> diction = getDiction(list);

    Map<String,Set<String>> result = new HashMap<>();

    for(String[] parts : list){
      String name = parts[0];
      Set<String> set = new HashSet<>();
      for(char ch = 'A'; ch < 'Z'; ch++){
        String key = getKey3(name,ch);
        if(diction.containsKey(key)){
          set.addAll(diction.get(key));
        }
      }
      result.put(name,set);
    }
    return result;
  }

  private Map<String,Set<String>> getDiction(List<String[]> list){
    Map<String,Set<String>> map = new HashMap<>();
    for(String[] arr:list){
      String name = arr[0];
      String key = getKey3(name,' ');
      if(map.containsKey(key)){
        map.get(key).add(name);
      }
      else{
        Set<String> set = new HashSet<>();
        set.add(name);
        map.put(key,set);
      }
    }
    return map;
  }

  private String getKey3(String name, char ch){
    int[] count = new int[26];
    if(ch != ' '){
      count[ch-'A']++;
    }

    for(char c : name.toCharArray()){
      count[c-'A']++;
    }
    return Arrays.toString(count);
  }


//  public LinkedHashMap<String,Long> parseUrl(String url,int N, int K){
//    LinkedHashMap<String,Long> res = new LinkedHashMap<>();
//    Scanner scanner = null;
//    try{
//       scanner = new Scanner(new URL(url).openStream(),"UTF-8").useDelimiter("\n");
//  scanner = new Scanner(new URL(url).openStrean(),"UTF-8").useDelimiter("\n");
//      while(scanner.hasNextLine()){
//        String line = scanner.nextLine();
//        getTop(line,res,K);
//        if(res.size() == N){
//          break;
//        }
//      }
//    }
//    catch(Exception e){
//      e.printStackTrace();
//    }
//    finally{
//      if(scanner != null){
//        scanner.close();
//      }
//    }
//    return res;
//  }

  public Map<String,Set<String>> stepAway(LinkedHashMap<String,Long> input){
    Map<String,List<String>> dictionary = new HashMap<>();

    for(Map.Entry<String,Long> entry : input.entrySet()){
      String name = entry.getKey();
      String key = getKey2(name,' ');
      if(dictionary.containsKey(key)){
        dictionary.get(key).add(name);
      }
      else{
        List<String> set = new ArrayList<>();
        set.add(name);
        dictionary.put(key,set);
      }
    }

    Map<String,Set<String>> result = new HashMap<>();
    for(String name : input.keySet()){
      Set<String> set = new HashSet<>();
      for(char ch = 'A' ; ch <= 'Z'; ch++){
        String key = getKey2(name,ch);
        if(dictionary.containsKey(key)){
          set.addAll(dictionary.get(key));
        }
      }
      result.put(name,set);
    }
    return result;
  }

  private String getKey2(String name, char ch){
    int[] count = new int[26];
    if(ch != ' '){
      count[ch-'A']++;
    }
    for(char c : name.toCharArray()){
      count[c-'A']++;
    }
    return Arrays.toString(count);
  }

}
