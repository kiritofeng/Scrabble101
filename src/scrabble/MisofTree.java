package scrabble;

public class MisofTree {
    //Backing
    private int size;
    private int[] cnt;
    private int[][] misof;

    public MisofTree(int size) {
        this.size = 0;
        cnt = new int[size];
        misof=new int[(int)(Math.log(size)/Math.log(2))+2][size];
    }

    public void add(int i) {
        size++;
        cnt[i]++;
        for(int lvl = 0; lvl < misof.length; lvl++) {
            misof[lvl][i]++;
            i /= 2;
        }
    }

    public boolean remove(int i) {
        if(cnt[i] == 0) return false;
        size--;
        for(int lvl = 0; lvl < misof.length; lvl++) {
            misof[lvl][i]--;
            i /= 2;
        }
        return true;
    }

    public int query(int i) {
        int res = 0;
        for(int lvl = misof.length-1; lvl >= 0; lvl--) {
            res *= 2;
            if(misof[lvl][res]<i) {
                i -= misof[lvl][res++];
            }
        }
        return res;
    }

    public int size() {
        return size;
    }
}
