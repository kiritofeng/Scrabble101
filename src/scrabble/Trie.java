package scrabble;

public class Trie {
    private class Node {
        Node[] nxt;

        public Node() {
            nxt = new Node[27];
        }
    }

    private Node root;

    public Trie() {
        root = new Node();
    }

    public void insert(String S) {
        S = S.toLowerCase();
        Node N = root;
        for(int i = 0; i < S.length(); i++) {
            if(N.nxt[S.charAt(i)-'a'] == null)
                N.nxt[S.charAt(i)-'a'] = new Node();
            N = N.nxt[S.charAt(i)-'a'];
        }
        N.nxt[26] = new Node();
    }

    public boolean find(String S) {
        S = S.toLowerCase();
        Node N = root;
        for(int i = 0; i < S.length(); i++) {
            if(N.nxt[S.charAt(i)-'a'] == null)
                return false;
            else
                N = N.nxt[S.charAt(i)-'a'];
        }
        return N.nxt[26] != null;
    }
}
