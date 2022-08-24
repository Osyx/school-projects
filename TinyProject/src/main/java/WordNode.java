import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.PartOfSpeech;
import se.kth.id1020.util.Word;

import java.util.LinkedList;

/**
 * Created by Oscar on 16-11-30.
 */
class WordNode {
    private String word;
    private LinkedList<AboutWord> aboutList = new LinkedList<AboutWord>();
    WordNode left;
    WordNode right;

    WordNode(Word word, Attributes attributes) {
        this.word = word.word;
        AboutWord temp = new AboutWord(attributes, word.pos);
        this.aboutList.add(temp);
    }

    public void addOccurence(Attributes attributes, Word word) {
        if (aboutList.get(aboutList.size() - 1).getAttributes().document == attributes.document) {
            aboutList.get(aboutList.size() - 1).increaseCount();
        } else {
            AboutWord temp = new AboutWord(attributes, word.pos);
            this.aboutList.add(temp);
        }
    }

    public LinkedList<AboutWord> getAboutList() {
        return aboutList;
    }

    public String getWord() {
        return word;
    }

    public class AboutWord {
        Attributes attributes;
        PartOfSpeech pos;
        int count = 1;

        AboutWord(Attributes attributes, PartOfSpeech pos) {
            this.attributes = attributes;
            this.pos = pos;
        }

        public Attributes getAttributes() {
            return attributes;
        }

        public PartOfSpeech getPos() {
            return pos;
        }

        public int getCount() {
            return count;
        }

        public void increaseCount() {
            count++;
        }


    }
}
