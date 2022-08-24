import se.kth.id1020.Driver;
import se.kth.id1020.TinySearchEngineBase;

/**
 * Created by Oscar on 16-11-30.
 */
public class RunnerClass {
    public static void main(String[] args) throws Exception {
            TinySearchEngineBase searchEngine = new TinySearchEngine();
            Driver.run(searchEngine);
        }

}
