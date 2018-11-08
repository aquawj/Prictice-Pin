import java.util.ArrayList;
import java.util.List;

/**
找int[][] matrix里的用0表示的box的范围。范围可以用左上，右下或者类似的表示。
        第一小问：只有一个box
        第二小问：有多个box。这一问应该和面试官讨论有两个box重合的情况。
        第三小问：box是不规则的。每个box的定义变成这个box的所有的点的集合
*/
public class Rectangle {
    //1. 只有一个box
    //找到的第一个0点就是左上角；只需要while找右下角边界
    static int[] rectangle1 (int[][] matrix) {
        int[] res = new int[4];
        int row = matrix.length, col = matrix[0].length;
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                if(matrix[i][j] == 0) {
                    int down = i, right = j;
                    while(down < row) {
                        if(matrix[down][j] == 0) {
                            down++;
                        } else {
                            break;
                        }
                    }
                    while(right < col) {
                        if(matrix[i][right] == 0) {
                            right++;
                        } else {
                            break;
                        }
                    }
                    return new int[]{i, j, down-1, right-1};
                }
            }
        }
        return res;
    }
    // 多个box
    static List<int[]> rectangleMulti(int[][] matrix) {
        List<int[]> res = new ArrayList<>();
        int row = matrix.length, col = matrix[0].length;
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                if(matrix[i][j] == 0) {
                    int iRight = i, jDown = j;
                    while(iRight < row) {
                        if(matrix[iRight][j] == 0) {
                            iRight++;
                        } else {
                            break;
                        }
                    }
                    while(jDown < col) {
                        if(matrix[i][jDown] == 0) {
                            jDown++;
                        } else {
                            break;
                        }
                    }
                    //如果没有重合：
                    res.add(new int[]{i, j, iRight-1, jDown-1}); //只有这2行不同
                    fill(i,j, iRight-1, jDown-1, matrix); //访问过的fill为1，不影响后面的
                    //如果有重合？
                }
            }
        }
        //printListArray("", res);
        return res;
    }

    //i1-i2,j1-j2范围，全部填充为1
    static void fill(int i1, int j1, int i2, int j2, int[][] matrix) {
        for(int i = i1; i <= i2; i++) {
            for(int j = j1; j <= j2; j++) {
                matrix[i][j] = 1;
            }
        }
    }

    //3. 不规则形状
    //返回该形状包括的所有点
    static List<List<int[]>> irregular(int[][] matrix) { //2维list：list的每个list元素是一个形状
        List<List<int[]>> res = new ArrayList<>();
        int row = matrix.length, col = matrix[0].length;
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                if(matrix[i][j] == 0) {
                    List<int[]> cur = new ArrayList<>(); //当前形状
                    dfs(matrix, i, j, row, col, cur);
                    res.add(cur);
                }
            }
        }
        return res;
    }

    static void dfs(int[][] matrix, int i, int j, int row, int col, List<int[]> cur) {
        int[][] dir = new int[][] {{-1,0}, {1,0}, {0,-1}, {0,1}};
        if(i<0 || i>= row || j<0 || j>=col || matrix[i][j] == 1) {
            return;
        }
        cur.add(new int[]{i,j});
        matrix[i][j] = 1;
        for(int[] k: dir) {
            int i2 = i+k[0], j2 = j+k[1];
            dfs(matrix, i2, j2, row, col, cur);
        }
    }
}
