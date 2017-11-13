package scrabble;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dictionary {

	private static Trie dictTrie;
	private static Dictionary dictionary;
	
	private Dictionary() {
		InitDictionary();
	}
	
	public static Dictionary getDictionary() {
		if (dictionary == null) {
			dictionary = new Dictionary();
		}
		return dictionary;
	}

	private static void InitDictionary() {	
		dictTrie = new Trie();		
		Scanner sc;
		sc = new Scanner(Dictionary.class.getResourceAsStream("ENABLEDict"));
		while (sc.hasNextLine()) {
			dictTrie.insert(sc.nextLine());
		}
		sc.close();
	}
	
	public boolean isWord(String word) {
		return dictTrie.find(word);
	}
	
}
