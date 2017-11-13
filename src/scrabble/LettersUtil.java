package scrabble;

public class LettersUtil {

    private MisofTree mt;
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
}
