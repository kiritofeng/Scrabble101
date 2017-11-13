package scrabble;

public class LettersUtil {

    private MisofTree mt;
    private static final int[]values = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
    public LettersUtil() {
        //Let each letter have 10
        mt = new MisofTree(26);
        refill();
    }

    public char getRandomLetter() {
        if(mt.size()==0) refill();
        int i = mt.query((int)(Math.random()*mt.size())+1);
        mt.remove(i);
        return (char)(i+'A');
    }

    public boolean isEmpty() {
        return mt.size()==0;
    }

    public void refill() {
         for(int i='A';i<='Z';i++) {
             while(mt.remove(i-'A'));
             for(int j=0;j<10;j++)
                 mt.add(i-'A');
        }
    }

    public static int getValue(char c) {
        return values[c-'A'];
    }
}
