import se.kth.id1020.TinySearchEngineBase;
import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Document;
import se.kth.id1020.util.Word;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Oscar on 16-11-30.
 */
public class TinySearchEngine implements TinySearchEngineBase {
    BinarySearchTree bst = new BinarySearchTree();

    public void insert(Word word, Attributes attributes) {
        if(word.word.toLowerCase().replaceAll("[^a-z]", "@").charAt(0) != '@')
            bst.addNode(word, attributes);
    }

    public List<Document> search(String s) {
        s = s.toLowerCase().replaceAll("[^a-z ]", "");
        if(s.trim().isEmpty())
            return null;
        String property = "";
        String[] arguments;
        boolean sort = false;
        boolean ascending = true;
        if(s.contains(" ")) {
            if (s.contains("orderby")) {
                sort = true;
                int index = s.indexOf("orderby");
                arguments = s.split(" ");
                property = arguments[arguments.length - 2];
                if(arguments[arguments.length - 1].matches("desc"))
                    ascending = false;
                arguments = s.substring(0, index).split(" ");
            } else {
                arguments = s.split(" ");
            }

            List<WordNode.AboutWord> aboutList = new ArrayList<WordNode.AboutWord>();
            for (String i:arguments) {
                WordNode tempNode = bst.search(i, bst.letterArr[i.charAt(0) - 'a']);
                if (tempNode == null)
                    continue;
                LinkedList<WordNode.AboutWord> temp = tempNode.getAboutList();
                for (int j = 0; j < temp.size(); j++) {
                    boolean alreadyExists = false;
                    for (int k = 0; k < aboutList.size() - 1; k++) {
                        if (aboutList.get(k).getAttributes().document == temp.get(j).getAttributes().document) {
                            alreadyExists = true;
                            break;
                        }
                    }
                    if (!alreadyExists)
                        aboutList.add(temp.get(j));
                }
            }
            if(sort)
                BubbleSort.sort(aboutList, property, ascending);
            return createDocument(aboutList);

        }
        return bst.search(s);
    }

    private List<Document> createDocument(List<WordNode.AboutWord> list) {
        List<Document> documentList = new ArrayList<Document>();
        for (int i = 0; i < list.size(); i++)
            documentList.add(list.get(i).getAttributes().document);

        return documentList;
    }

}
