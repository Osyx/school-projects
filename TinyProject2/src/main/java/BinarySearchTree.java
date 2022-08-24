import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Document;
import se.kth.id1020.util.Word;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Oscar on 16-11-30.
 */
public class BinarySearchTree<Key extends Comparable<Key>, Value> {
    WordNode[] letterArr = new WordNode[26];

    public void addNode(Word word, Attributes attributes) {
        if(letterArr[word.word.toLowerCase().charAt(0) - 'a'] == null) {
            WordNode node = new WordNode(word, attributes);
            letterArr[word.word.toLowerCase().charAt(0) - 'a'] = node;
        } else
            addingNode(letterArr[word.word.toLowerCase().charAt(0) - 'a'], word, attributes);
    }

    public void addingNode(WordNode head, Word word, Attributes attributes) {
        switch(compareWord(head.getWord().toLowerCase(), word.word.toLowerCase())) {
            case 1: //Höger
                if(head.right == null)
                    head.right = new WordNode(word, attributes);
                else
                    addingNode(head.right, word, attributes);
                break;

            case -1: //Vänster
                if(head.left == null)
                    head.left = new WordNode(word, attributes);
                else
                    addingNode(head.left, word, attributes);
                break;

            case 0: //Samma
                head.addOccurence(attributes, word);
                break;
        }
    }

    public int compareWord(String oldWord, String newWord) {
        int shortestLength = oldWord.length();
        if(oldWord.length() > newWord.length())
            shortestLength = newWord.length();

        for (int i = 0; i < shortestLength; i++) {
            if(oldWord.charAt(i) != newWord.charAt(i)) {
                if (oldWord.charAt(i) - 'a' < newWord.charAt(i) - 'a')
                    return 1;
                else
                    return -1;
            }
        }

        if (oldWord.length() > newWord.length())
            return -1;
        else if (oldWord.length() < newWord.length())
            return 1;
        else
            return 0;
    }

    public List<Document> search(String s) {
        List<Document> documentList = new ArrayList<Document>();
        s = s.toLowerCase();
        if(letterArr[s.charAt(0) - 'a'] == null)
            return null;

        WordNode foundNode = search(s, letterArr[s.charAt(0) - 'a']);
        if (foundNode == null) return null;
        LinkedList<WordNode.AboutWord> aboutList = foundNode.getAboutList();

        for (int i = 0; i < aboutList.size(); i++)
            documentList.add(aboutList.get(i).getAttributes().document);
        return documentList;
    }

    public WordNode search(String s, WordNode head) {
        if (head == null) return null;
        int compared = compareWord(head.getWord().toLowerCase(), s.toLowerCase());
        if (compared == 0) return head;
        if (compared == -1) return search(s, head.left);
        if (compared == 1) return search(s, head.right);
        return null;
    }
}
