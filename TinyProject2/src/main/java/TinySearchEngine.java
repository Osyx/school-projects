import se.kth.id1020.TinySearchEngineBase;
import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Document;
import se.kth.id1020.util.Sentence;
import se.kth.id1020.util.Word;

import java.util.*;

/**
 * Created by Oscar on 16-11-30.
 */
public class TinySearchEngine implements TinySearchEngineBase {
    BinarySearchTree bst = new BinarySearchTree();

    public void preInserts() {

    }

    public void insert(Sentence sentence, Attributes attributes) {
        for(Word word : sentence.getWords()) {
            if (word.word.toLowerCase().replaceAll("[^a-z]", "@").charAt(0) != '@')
                bst.addNode(word, attributes);
        }
    }

    public void postInserts() {

    }

    public List<Document> search(String s) {
        String property = "";
        String[] arguments;
        boolean sort = false;
        boolean ascending = true;
        List<WordNode.AboutWord> aboutList;

        if (s.contains("orderby")) {
            sort = true;
            int index = s.indexOf("orderby");
            arguments = s.split(" ");
            property = arguments[arguments.length - 2];
            if(arguments[arguments.length - 1].matches("desc"))
                ascending = false;
            arguments = s.substring(0, index).split(" ");
        } else
            arguments = s.split(" ");

        aboutList = postToInfix(arguments);

        if(sort)
            BubbleSort.sort(aboutList, property, ascending);
        return createDocument(aboutList);
    }

    public List<WordNode.AboutWord> postToInfix(String[] arr) {
        Stack<String> operatorStack = new Stack<String>();
        Stack<String> operandStack = new Stack<String>();
        List<WordNode.AboutWord> aboutList = new ArrayList<WordNode.AboutWord>();
        for(String string:arr)
            System.out.println(string);
        String operand;

        for(int arrayI = arr.length - 1; arrayI >= 0; arrayI--) {
            System.out.println(arr[arrayI]);
            if(arr[arrayI].length() > 1) {
                System.out.println("Found operand " + arr[arrayI]);
                operandStack.push(arr[arrayI]);
            }
            else {
                System.out.println("Found operator " + arr[arrayI]);
                if(arr[arrayI].equals("|")) {
                    for (int i = 0; i < 2; i++) {
                        operand = operandStack.pop();
                        WordNode tempNode = bst.search(operand, bst.letterArr[operand.charAt(0) - 'a']);
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
                }
                if(arr[arrayI].equals("+")) {
                    for (int i = 0; i < 2; i++) {
                        arr[arrayI] = operandStack.pop();
                        WordNode tempNode = bst.search(arr[arrayI], bst.letterArr[arr[arrayI].charAt(0) - 'a']);
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
                            if (alreadyExists)
                                aboutList.add(temp.get(j));
                        }
                    }
                }

                if(arr[arrayI].equals("-")) {
                    for (int i = 0; i < 2; i++) {
                        arr[arrayI] = operandStack.pop();
                        WordNode tempNode = bst.search(arr[arrayI], bst.letterArr[arr[arrayI].charAt(0) - 'a']);
                        if (tempNode == null)
                            continue;
                        LinkedList<WordNode.AboutWord> temp = tempNode.getAboutList();
                        for (int j = 0; j < temp.size(); j++) {
                            boolean alreadyExists = false;
                            for (int k = 0; k < aboutList.size() - 1; k++) {
                                if (aboutList.get(k).getAttributes().document == temp.get(j).getAttributes().document) {
                                    aboutList.remove(k);
                                    break;
                                }
                            }
                            if (!alreadyExists)
                                aboutList.add(temp.get(j));
                        }
                    }
                }
            }
        }
        return aboutList;
    }

    public String infix(String s) {
        return null;
    }

    private List<Document> createDocument(List<WordNode.AboutWord> list) {
        List<Document> documentList = new ArrayList<Document>();
        for (int i = 0; i < list.size(); i++)
            documentList.add(list.get(i).getAttributes().document);

        return documentList;
    }

}
