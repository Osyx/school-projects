/**
 * Created by Oscar on 16-12-09.
 */
class DepthFirstSearch {
    private boolean[] included;
    private int count;

    DepthFirstSearch(Paths.Node[] nodeList) {
        included = new boolean[nodeList.length];
    }

    private void dfs(Paths.Node[] nodeList, int current) {
        included[current] = true;
        if(nodeList[current] != null) {
            Paths.Node currentNode = nodeList[current];
            while (currentNode != null) {
                if (!included[currentNode.getNeighbor()])
                    dfs(nodeList, currentNode.getNeighbor());
                currentNode = currentNode.getNext();
            }
        }
    }

    int countSubtrees(Paths.Node[] nodeList) {
        for (int i = 0; i < nodeList.length; i++) {
            if(!included[i]) {
                dfs(nodeList, i);
                count++;
            }
        }
        return count;
    }
}
