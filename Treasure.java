
import java.util.*;

public class Treasure {
    int[][] dir = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    //1. 有一个由（-1，0）组成的board：-1 是wall，0是可以走的。给你一个cell = 0的position, 返回它上下左右四个方向的是0 的neighbors
    public List<int[]> getNeighbors(int[][] matrix, int x, int y){
        List<int[]> res = new ArrayList<>();
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0) return res;
        int n = matrix.length, m = matrix[0].length;
        for(int d = 0; d < 4; d++){
            int xx = x + dir[d][0];
            int yy = y + dir[d][1];
            if(xx < 0 || xx >= n || yy < 0 || yy >= m) continue;
            if(matrix[xx][yy] == 0){
                res.add(new int[]{xx, yy});
            }
        }
        return res;
    }

    //2.给你一个cell = 0的end position，判断这个board的其他的所有的0是否能reach到它
    int connected = 0;
    public boolean canReach(int[][] matrix, int x, int y){
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0) return false;
        int n = matrix.length, m = matrix[0].length;
        if (matrix[x][y] != 0) return false;
        int total = 0;
        //计算total 0
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(matrix[i][j] == 0){
                    total++;
                }
            }
        }
        //dfs计算connected 0
        dfs(matrix, x, y, m, n);

        return total == connected;
    }

    public void dfs(int[][] matrix, int x, int y, int m, int n){
        if(x < 0 || x >= n || y < 0 || y >= m || matrix[x][y] != 0) return;
        matrix[x][y] = 1; //勿忘
        connected++;
        for(int d = 0; d < 4; d++){
            int xx = x + dir[d][0];
            int yy = y + dir[d][1];
            dfs(matrix, xx, yy, m, n);
        }

    }

    //3. board里面有1，为treasures，给一个start position和end position。
    // 要找到一条shorted path, from start position to end postion. 并且经过所有的1.不能走重复的格子
    int minStep = Integer.MAX_VALUE;
    public List<int[]> shortestPathWithTreasure(int[][] matrix, int startX, int startY, int endX, int endY){
        List<int[]> res = new ArrayList<>();
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0) return res;
        int n = matrix.length, m = matrix[0].length;
        int totalTreasure = 0;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(matrix[i][j] == 1){
                    totalTreasure++;
                }
            }
        }
        //System.out.println("total:"+totalTreasure);
        findPathDfs(matrix, m, n, startX, startY, endX, endY, totalTreasure,0,  new ArrayList<>(), res);
        //System.out.println("minStep:"+minStep);
        return res;
    }

//    public int[] bfs(int[][] matrix, int m, int n, int x, int y, int endX, int endY, int total,
//                    int treasure, List<int[]> path){
//        Queue<int[]> queue = new LinkedList<>();
//        queue.offer(new int[]{x, y});
//        while(!queue.isEmpty()){
//            int[] temp = queue.poll();
//            if(matrix[temp[0]][temp[1]] == 1){
//                return temp;
//            }
//            for(int[] neighbor : getNeighbors(matrix,temp[0],temp[1])){
//                if()
//            }
//        }
//    }

    public void findPathDfs(int[][] matrix, int m, int n, int x, int y, int endX, int endY, int total,
                            int treasure,  List<int[]> path, List<int[]> res){
        if(x < 0 || x >= n || y < 0 || y >= m || (matrix[x][y] != 0 && matrix[x][y] != 1)) return;
        if(matrix[x][y] == 1){
            treasure++;
        }
        int original = matrix[x][y];
        matrix[x][y] = 2; //mark as visited
        path.add(new int[]{x, y});
        if(x == endX && y == endY){
            if(treasure == total){
                if(path.size() < minStep){
                    minStep = path.size();
                    res.clear();
                    res.addAll(path);
                }
                matrix[x][y] = original;
                path.remove(path.size() - 1);
                return;
            }
        }
        for(int d = 0; d < 4; d++){
            int xx = x + dir[d][0];
            int yy = y + dir[d][1];
            findPathDfs(matrix, m, n, xx, yy, endX, endY, total, treasure, path, res);
        }
        matrix[x][y] = original;
        path.remove(path.size() - 1);

    }


    public void printListArray(List<int[]> list){
        for(int[] pos : list){
            System.out.print(pos[0] + "," + pos[1]);
            System.out.println();
        }
    }

    public static void main(String[] args){
        Treasure t = new Treasure();
        int[][] matrix = new int[][]{
                { 0,  0,  0, 0, -1 },
                { 0, -1, -1, -1, 0 },
                { 0,  0,  0, -1, 0 },
                { 0, -1,  0, 0, -1 },
                { 0,  0,  0, 0,  0 },
                { 0,  0,  0, 0,  0 },
        };
        t.printListArray(t.getNeighbors(matrix, 1, 3));
        System.out.println(t.canReach(matrix, 0, 0));

        int[][] treasure = new int[][] {
                {  1,  0,  0, 0 },
                {  0, -1,  1, 1 },
                {  0,  0,  0, 0 }

        };
        t.printListArray(t.shortestPathWithTreasure(treasure, 0, 0, 1, 3));
    }

}
