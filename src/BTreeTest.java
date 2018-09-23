
/**
 * Objective - To use and understand binary search trees
 *
 * @author Isaias Leos Ayala
 */
public class BTreeTest {

    /**
     * Method that returns the height of the B-Tree
     *
     * @param T
     * @return height of the B-Tree
     */
    public static int getHeight(BTreeNode T) {
        if (T.isLeaf) {
            return 0;
        }
        return 1 + getHeight(T.c[0]);//Add up the height
    }

    /**
     * Method for binary search trees as guide, write a method to display a
     * B-tree given a reference to its root.
     *
     * @param T B-Tree
     * @param x0 x most left value
     * @param x1 x most right left value
     * @param y y coordinates
     * @param y_inc how many levels are the tree going to be in
     */
    public static void draw_tree(BTreeNode T, double x0, double x1, double y, double y_inc) {
        double xm = (x0 + x1) / 2;
        double yn = y - y_inc;
        if (!T.isLeaf) {
            double increase = (((x1 + xm) / 2.0) - ((x0 + xm) / 2.0)) / T.n;
            double space = increase / 2.0;//space between children of different parent
            for (int i = 0; i <= T.n; i++) {
                if (i == 0) {//Draw the left side of the tree
                    StdDraw.line(xm, y, ((x1 + xm) / 2), yn);//Take care of the line for the left children
                    draw_tree(T.c[0], ((x0 + xm) / 2) - space, ((x0 + xm) / 2) + space, yn, y_inc);
                } else if (i == T.n) {//Draw the right side of the tree
                    StdDraw.line(xm, y, (x0 + xm) / 2, yn);//Take care of the line for the right children
                    draw_tree(T.c[T.n], ((x1 + xm) / 2) - space, ((x1 + xm) / 2) + space, yn, y_inc);
                } else if (i != 0 && i != T.n) {//Draw any middle trees
                    StdDraw.line(xm, y, ((x0 + xm) / 2) + (increase * i), yn);//Take care of the line
                    draw_tree(T.c[i], ((x0 + xm) / 2) + (increase * i) - space, ((x0 + xm) / 2) + (increase * i) + space, yn, y_inc);//Take care of the recursive call
                }
            }
        }
        int square = 5;
        for (int i = 0; i < T.n; i++) {//Draw the squares
            int sep = i * (square * 2);
            StdDraw.setPenColor(StdDraw.WHITE);//To clear the spot
            StdDraw.filledSquare(xm + sep, y, square);//Clear the spot of where the squares are gonna appear
            StdDraw.setPenColor(StdDraw.BLACK);//Reset back to default
            StdDraw.square(xm + sep, y, square);//Draw the square
            StdDraw.text(xm + sep, y, Integer.toString(T.item[i]));//Draw the text
        }

    }

    /**
     * Extracts the items in the B-tree into a sorted array
     *
     * @param T
     * @param Array
     * @param index
     * @return
     */
    public static int bTreeToArray(BTreeNode T, int[] Array, int index) {
        if (T.isLeaf) {//Extra that children
            for (int i = 0; i < T.n; i++) {
                Array[index] = T.item[i];
                index++;
            }
            return index;
        } else {
            for (int i = 0; i < T.n; i++) {
                index = bTreeToArray(T.c[i], Array, index);
                Array[index] = T.item[i];//Assign the item to the array
                index++;//Increment
            }
            index = bTreeToArray(T.c[T.n], Array, index);//Recursive call for leftover children
        }
        return index;
    }

    /**
     * Return the minimum element in the tree at a given depth d
     *
     * @param T B-Tree
     * @param depth the minimum depth your looking in
     * @return the lowest item in the given depth
     */
    public static int minAtDepth(BTreeNode T, int depth) {
        if (depth == 0) {//if at the correct depth
            return T.item[0];//return the leftmost value
        } else {//if the correct depth is found return the smallest at that depth
            return minAtDepth(T.c[0], depth - 1);
        }
    }

    /**
     * Return the maximum element in the tree at a given depth d
     *
     * @param T B-Tree
     * @param depth the minimum depth your looking in
     * @return the highest item in the given depth
     */
    public static int maxAtDepth(BTreeNode T, int depth) {
        if (depth == 0) {//if at the correct depth
            return T.item[T.n - 1];//return the rightmost value
        } else {//if the correct depth is found return the largest at that depth
            return maxAtDepth(T.c[T.n], depth - 1);
        }
    }

    /**
     * Return the number of nodes in the tree at a given depth d.
     *
     * @param T B-Tree
     * @param depth the minimum depth your looking in
     * @return how many nodes are at the given depth
     */
    public static int nodesAtDepth(BTreeNode T, int depth) {
        if (depth == 0) {//if your at the depth
            return 1;//you found the node at the depth
        } else {
            int numberN = 0;//keep track of the nodes
            for (int i = 0; i < T.n; i++) {
                numberN += nodesAtDepth(T.c[i], depth - 1);//look through the parents
            }
            numberN += nodesAtDepth(T.c[T.n], depth - 1);//leftover parents
            return numberN;//return the number of nodes that that depth
        }
    }

    /**
     * Print all the items in the tree at a given depth d
     *
     * @param T B-Tree
     * @param depth the minimum depth your looking in
     */
    public static void printAscendingAtDepth(BTreeNode T, int depth) {
        if (depth == 0) {//Print the nodes found at the depth required
            for (int i = 0; i < T.n; i++) {
                System.out.print(T.item[i] + " ");
            }
            return;
        }
        for (int i = 0; i <= T.n; i++) {//look through the parents
            printAscendingAtDepth(T.c[i], depth - 1);
        }
    }

    public static void printAscending(BTreeNode T) {
        if (T.isLeaf) {//Print the nodes found at the depth required
            for (int i = 0; i < T.n; i++) {
                System.out.print(T.item[i] + " ");
            }
            return;
        }
        for (int i = 0; i < T.n; i++) {//look through the parents

            printAscending(T.c[i]);
            System.out.print(T.item[i] + " ");
        }
        printAscending(T.c[T.n]);
    }

    /**
     * Return the number of leafs in the tree that are full.
     *
     * @param T B-Tree
     * @return the number of children that are full
     */
    public static int ifChildrenAreFull(BTreeNode T) {
        int count = 0;//keep track of the children that are full
        if (T.isLeaf) {//Make sure its a child
            if (T.isFull()) {//Check if the child is full
                return 1;
            }
        } else {
            for (int i = 0; i < T.n; i++) {
                count += ifChildrenAreFull(T.c[i]);
            }
            count += ifChildrenAreFull(T.c[T.n]);
        }
        return count;
    }

    /**
     * Return the number of non-leaves in the tree that are full.
     *
     * @param T B-Tree
     * @return the number of parents that are full
     */
    public static int ifParentsAreFull(BTreeNode T) {
        int count = 0;//keep track of the parents that are full
        if (!T.isLeaf) {//Make sure it isn't a child
            if (T.isFull()) {//Check if its full
                return 1;
            }
        } else {
            for (int i = 0; i < T.n; i++) {
                count += ifChildrenAreFull(T.c[i]);
            }
            count += ifChildrenAreFull(T.c[T.n]);
        }
        return count;
    }

    /**
     * Given a key k, return the depth at which it is found in the tree, of -1
     * if k is not in the tree.
     *
     * @param T B-Tree
     * @param key
     * @return
     */
    public static int depthOfK(BTreeNode T, int key) {
        int i = 0;
        while (i < T.n && key > T.item[i]) {
            i++;
        }
        if (i == T.n || key < T.item[i]) {
            if (T.isLeaf) {
                return -1;
            } else {
                int d = depthOfK(T.c[i], key);
                if (d == -1) {
                    return -1;
                } else {
                    return 1 + d;
                }
            }
        } else {
            return 0;
        }
    }

    /**
     * Given a key k, print all the keys that are in the same node as k.
     *
     * @param T B-Tree
     * @param key
     */
    public static void printAtKNode(BTreeNode T, int key) {
        int i = 0;
        while (i < T.n && key > T.item[i]) {
            i++;
        }
        if (i == T.n || key < T.item[i]) {
            if (T.isLeaf) {
                return;
            } else {
                printAtKNode(T.c[i], key);
            }
        } else {
            for (int j = 0; j < T.n; j++) {
                System.out.println(T.item[j] + " ");
            }
        }
    }

    public static int q(BTreeNode T, int d) {
        int s = 0;
        if (d == 0) {
            for (int i = 0; i < T.n; i++) {
                s += T.item[i];

            }
            return s;
        }
        if (!T.isLeaf) {
            for (int i = 0; i <= T.n; i++) {
                s += q(T.c[i], d - 1);
            }
        }
        return s;
    }

    public static void main(String[] args) {
        int x_max = 400;
        int y_max = 400;
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, x_max);
        StdDraw.setYscale(0, y_max);
        StdDraw.setPenColor(StdDraw.BLACK);

        int[] S
                = {
                    8, 9, 11, 4, 7, 12, 13, 17, 24, 15, 27, 28, 30, 33, 34, 37, 40, 42, 50, 55
                };
        BTree B = new BTree(3);
        //Build B-tree from array
        for (int i = 0; i < S.length; i++) {
            B.insert(S[i]);
        }
        BTreeNode T = B.root;

        System.out.println(q(T, 0));
        System.out.println(q(T, 1));
        System.out.println(q(T, 2));
        System.out.println(q(T, 10));
        int[] sortedArray = new int[S.length];
        bTreeToArray(T, sortedArray, 0);
        printAscending(T);
        System.out.print("BTree to Sorted Array: ");
        for (int i = 0; i < sortedArray.length; i++) {
            System.out.print(sortedArray[i] + " ");
        }
        System.out.println("");

        draw_tree(T, 0, x_max, y_max - 5, (y_max - 10.0) / getHeight(T));//draw tree

        int currentDepth = 0;
        int smallestNode = minAtDepth(T, currentDepth);
        System.out.println("Smallest element at " + currentDepth + " depth: " + smallestNode);

        int largestNode = maxAtDepth(T, currentDepth);
        System.out.println("Largest element at " + currentDepth + " depth: " + largestNode);

        System.out.print("Items at depth " + currentDepth + ": ");
        printAscendingAtDepth(T, currentDepth);//8,12,28,34

        System.out.println("");
        int numOfChildren = ifChildrenAreFull(T);
        System.out.println("Number of children nodes that are full: " + numOfChildren);

        int numOfParents = ifParentsAreFull(T);
        System.out.println("Number of children nodes that are full: " + numOfParents);

        int numNodes = nodesAtDepth(T, currentDepth);
        System.out.println("Number of Nodes at K Depth: " + numNodes);

        int finding = 17;
        int itemAtDepth = depthOfK(T, finding);
        System.out.println(finding + " was found at depth: " + itemAtDepth);
    }
}
