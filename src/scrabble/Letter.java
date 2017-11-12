package scrabble;

import java.util.ArrayList;
import java.util.List;

public class Letter {

	protected char letter;

	static List<Character> bag = new ArrayList<Character>();
	private static int[] numLetters = { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
			10, 10, 10, 10, 10, 10, 10 }; //to be updated

	public Letter(char c) {
		letter = c;
	}

	// Please fix this ungodly repetition
	public int points(char letter) {
		if (letter == 'A')
			return 1;
		if (letter == 'B')
			return 2;
		if (letter == 'C')
			return 3;
		if (letter == 'D')
			return 4;
		if (letter == 'E')
			return 5;
		if (letter == 'F')
			return 6;
		if (letter == 'G')
			return 7;
		if (letter == 'H')
			return 8;
		if (letter == 'I')
			return 9;
		if (letter == 'J')
			return 10;
		if (letter == 'K')
			return 11;
		if (letter == 'L')
			return 12;
		if (letter == 'M')
			return 13;
		if (letter == 'N')
			return 14;
		if (letter == 'O')
			return 15;
		if (letter == 'P')
			return 16;
		if (letter == 'Q')
			return 17;
		if (letter == 'R')
			return 18;
		if (letter == 'S')
			return 19;
		if (letter == 'T')
			return 20;
		if (letter == 'U')
			return 21;
		if (letter == 'V')
			return 22;
		if (letter == 'W')
			return 23;
		if (letter == 'X')
			return 24;
		if (letter == 'Y')
			return 25;
		if (letter == 'Z')
			return 26;

		return 0;
	}

	public void resetBag() {
		for (int i = 0; i < 27; i++) {
			for (int j = 1; j <= numLetters[i]; j++) {
				if (i == 27) {
					bag.add('*');
				} else {
					bag.add((char) (i + 65));
				}
			}
		}
	}
	
	private int getRandom() {
		return (int) (Math.random() * (bag.size() - 1));
	}

	public char removeLetter() {
		int index = getRandom();
		char c = ' ';
		if (index >= 0 && index < bag.size()) {
			c = bag.get(index);
			bag.remove(index);
		}
		return c;
	}
}
