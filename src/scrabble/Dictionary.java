package scrabble;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dictionary {
	
	private static final File dictFile = new File("ENABLEDict");
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
		try {
			sc = new Scanner(dictFile);
			while (sc.hasNextLine()) {
				dictTrie.insert(sc.nextLine());
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isWord(String word) {
		return dictTrie.find(word);
	}
	
}
