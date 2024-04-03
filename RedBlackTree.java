class RedBlackTreeNode {
    int key;
    RedBlackTreeNode parent;
    RedBlackTreeNode left;
    RedBlackTreeNode right;
    boolean isRed;

    public RedBlackTreeNode(int key) {
        this.key = key;
        isRed = true;
    }
}

public class RedBlackTree {
    private RedBlackTreeNode root;
    private final RedBlackTreeNode NIL;

    public RedBlackTree() {
        NIL = new RedBlackTreeNode(Integer.MIN_VALUE);
        NIL.isRed = false;
        root = NIL;
    }
    private void rightRotate(RedBlackTreeNode y) {
        RedBlackTreeNode x = y.left;
        y.left = x.right;
        if (x.right != NIL)
            x.right.parent = y;
        x.parent = y.parent;
        if (y.parent == NIL)
            root = x;
        else if (y == y.parent.left)
            y.parent.left = x;
        else
            y.parent.right = x;
        x.right = y;
        y.parent = x;
    }

    private void leftRotate(RedBlackTreeNode x) {
        RedBlackTreeNode y = x.right;
        x.right = y.left;
        if (y.left != NIL)
            y.left.parent = x;
        y.parent = x.parent;
        if (x.parent == NIL)
            root = y;
        else if (x == x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;
        y.left = x;
        x.parent = y;
    }

   

    private void insertFixup(RedBlackTreeNode z) {
        while (z.parent.isRed) {
            if (z.parent == z.parent.parent.left) {
                RedBlackTreeNode y = z.parent.parent.right;
                if (y.isRed) {
                    z.parent.isRed = false;
                    y.isRed = false;
                    z.parent.parent.isRed = true;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.right) {
                        z = z.parent;
                        leftRotate(z);
                    }
                    z.parent.isRed = false;
                    z.parent.parent.isRed = true;
                    rightRotate(z.parent.parent);
                }
            } else {
                RedBlackTreeNode y = z.parent.parent.left;
                if (y.isRed) {
                    z.parent.isRed = false;
                    y.isRed = false;
                    z.parent.parent.isRed = true;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rightRotate(z);
                    }
                    z.parent.isRed = false;
                    z.parent.parent.isRed = true;
                    leftRotate(z.parent.parent);
                }
            }
        }
        root.isRed = false;
    }

    public void insert(int key) {
        RedBlackTreeNode z = new RedBlackTreeNode(key);
        RedBlackTreeNode y = NIL;
        RedBlackTreeNode x = root;
        while (x != NIL) {
            y = x;
            if (z.key < x.key)
                x = x.left;
            else
                x = x.right;
        }
        z.parent = y;
        if (y == NIL)
            root = z;
        else if (z.key < y.key)
            y.left = z;
        else
            y.right = z;
        z.left = NIL;
        z.right = NIL;
        z.isRed = true;
        insertFixup(z);
    }

    private void transplant(RedBlackTreeNode u, RedBlackTreeNode v) {
        if (u.parent == NIL)
            root = v;
        else if (u == u.parent.left)
            u.parent.left = v;
        else
            u.parent.right = v;
        v.parent = u.parent;
    }

    private RedBlackTreeNode minimum(RedBlackTreeNode x) {
        while (x.left != NIL)
            x = x.left;
        return x;
    }
    private RedBlackTreeNode search(RedBlackTreeNode x, int key) {
        while (x != NIL && key != x.key) {
            if (key < x.key)
                x = x.left;
            else
                x = x.right;
        }
        return x;
    }

    private void inorder(RedBlackTreeNode x) {
        if (x != NIL) {
            inorder(x.left);
            System.out.print(x.key + " ");
            inorder(x.right);
        }
    }

    public void inorderTraversal() {
        inorder(root);
    }
    private void deleteFixup(RedBlackTreeNode x) {
        while (x != root && !x.isRed) {
            if (x == x.parent.left) {
                RedBlackTreeNode w = x.parent.right;
                if (w.isRed) {
                    w.isRed = false;
                    x.parent.isRed = true;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                if (!w.left.isRed && !w.right.isRed) {
                    w.isRed = true;
                    x = x.parent;
                } else {
                    if (!w.right.isRed) {
                        w.left.isRed = false;
                        w.isRed = true;
                        rightRotate(w);
                        w = x.parent.right;
                    }
                    w.isRed = x.parent.isRed;
                    x.parent.isRed = false;
                    w.right.isRed = false;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                RedBlackTreeNode w = x.parent.left;
                if (w.isRed) {
                    w.isRed = false;
                    x.parent.isRed = true;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }
                if (!w.right.isRed && !w.left.isRed) {
                    w.isRed = true;
                    x = x.parent;
                } else {
                    if (!w.left.isRed) {
                        w.right.isRed = false;
                        w.isRed = true;
                        leftRotate(w);
                        w = x.parent.left;
                    }
                    w.isRed = x.parent.isRed;
                    x.parent.isRed = false;
                    w.left.isRed = false;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.isRed = false;
    }

    public void delete(int key) {
        RedBlackTreeNode z = search(root, key);
        if (z == NIL)
            return;
        RedBlackTreeNode y = z;
        RedBlackTreeNode x;
        boolean yOriginalColor = y.isRed;
        if (z.left == NIL) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == NIL) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.isRed;
            x = y.right;
            if (y.parent == z)
                x.parent = y;
            else {
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.isRed = z.isRed;
        }
        if (!yOriginalColor)
            deleteFixup(x);
    }



    public static void main(String[] args) {
        RedBlackTree t = new RedBlackTree();
        t.insert(7);
        t.insert(3);
        t.insert(1);
        t.insert(10);
        t.insert(2);
        t.insert(8);
        t.insert(9);
        t.insert(6);

        System.out.println("Inorder Traversal:");
        t.inorderTraversal();
        System.out.println();

        System.out.println("Deleting key 1...");
        t.delete(1);

        System.out.println("Inorder Traversal after deletion:");
        t.inorderTraversal();
        System.out.println();
    }
}
