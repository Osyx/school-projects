import java.util.Iterator;

/**
 * Created by Oscar on 16-11-25.
 */
public class Driver {
    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.put("hej");
        trie.put("hej");
        trie.put("hello");
        trie.put("hemma");
        trie.put("hejsan");
        trie.put("hejsansvejsan");
        System.out.println(trie.get("hej"));
        System.out.println(trie.get("hello"));
        System.out.println(trie.get("hejsan"));
        System.out.println(trie.get(""));
        System.out.println("Count: " + trie.count("he"));
        System.out.println("Count: " + trie.countDistinct("he"));
        Iterator iterator = trie.iterator();
        iterator.next();
    }
}
