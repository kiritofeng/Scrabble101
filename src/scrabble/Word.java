package scrabble;

public class Word {

	public int row, col;
	public String word;
	public boolean isHorizontal;
	
	public Word() {
		this(0, 0, "", true);
	}
	
	public Word(int row, int col, String word, boolean isHorizontal){
		this.row = row;
		this.col = col;
		this.isHorizontal = isHorizontal;
		this.word = word;
	}

}
