
/** ******************************************************
 ** Simple Program to implement insertions              **
 ** and traversals on B-trees                           **
 ** This file includes basic operations on B-tree nodes **
 ** Programmed by Olac Fuentes                          **
 ** Last modified October 9, 2017                       **
 ** Report bugs to me                                   **
 ******************************************************** */
public class BTreeNode {

    public int n;          // Actual number of items on the node
    public boolean isLeaf; // Boolean indicator 
    public int[] item;     // Items stored in the node. They are sorted ion ascending order
    public BTreeNode[] c;  // Children of node. Items in c[i] are less than item[i] (if it exists) 
    // and greater than item[i-1] if it exists

    public BTreeNode(int t) {  // Build empty node                
        isLeaf = true;
        item = new int[2 * t - 1];   // Array sizes are set to maximum possible size
        c = new BTreeNode[2 * t];
        n = 0;	                  // Number of elements is zero, since node is empty
    }

    public boolean isFull() {
        return n == item.length;
    }

    public void insert(int newKey) {
        // Instert new item to current node
        // We make sure that the current node is not full by checking and
        // splitting if necessary before descending to node

        //System.out.println("inserting " + newKey); //Debugging code
        int t = c.length / 2;
        int i = n - 1;
        if (isLeaf) {
            while ((i >= 0) && (newKey < item[i])) { //Shift item greater than newKey to left
                item[i + 1] = item[i];
                i--;
            }
            n++;//Update number of items in node
            item[i + 1] = newKey;//Insert new item
        } else {
            while ((i >= 0) && (newKey < item[i])) {
                i--;
            }
            int insertChild = i + 1;  // Subtree where new item must be inserted
            if (c[insertChild].isFull()) {
                // The root of the subtree where new item will be inserted has to be split
                // We promote the median of that root to the current node and
                // update items and references accordingly
                // Debugging code
                // System.out.println("This is the full node we're going to break ");				
                // c[insertChild].printNodes();
                // System.out.println("going to promote " + c[insertChild].item[T-1]);
                n++;
                c[n] = c[n - 1];
                for (int j = n - 1; j > insertChild; j--) {
                    c[j] = c[j - 1];
                    item[j] = item[j - 1];
                }
                item[insertChild] = c[insertChild].item[t - 1];
                c[insertChild].n = t - 1;
                BTreeNode newNode = new BTreeNode(t);
                for (int k = 0; k < t - 1; k++) {
                    newNode.c[k] = c[insertChild].c[k + t];
                    newNode.item[k] = c[insertChild].item[k + t];
                }
                newNode.c[t - 1] = c[insertChild].c[2 * t - 1];
                newNode.n = t - 1;
                newNode.isLeaf = c[insertChild].isLeaf;
                c[insertChild + 1] = newNode;
                if (newKey < item[insertChild]) {
                    c[insertChild].insert(newKey);
                } else {
                    c[insertChild + 1].insert(newKey);
                }
            } else {
                c[insertChild].insert(newKey);  //No need to split node
            }
        }
    }

    public void print() {
        //Prints all items in the tree in ascending order
        if (isLeaf) {
            for (int i = 0; i < n; i++) {
                System.out.print(item[i] + " ");
            }
            //System.out.println();
        } else {
            for (int i = 0; i < n; i++) {
                c[i].print();
                System.out.print(item[i] + " ");
            }
            c[n].print();
        }
    }

    public void printNodes() {
        //Prints all items in the tree, node by node, using preorder
        //It also prints the indicator of whether a node is a leaf
        //Used mostly for debugging purposes
        for (int i = 0; i < n; i++) {
            System.out.print(item[i] + " ");
        }
        System.out.println(isLeaf);
        if (!isLeaf) {
            for (int i = 0; i <= n; i++) {
                c[i].printNodes();
            }
        }
    }
}
