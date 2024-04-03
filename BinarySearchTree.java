class TNode {
    int key;
    TNode left, right;

    public TNode(int item) {
        key = item;
        left = right = null;
    }
}

public class BinarySearchTree {
    TNode root;

    public BinarySearchTree() {
        root = null;
    }
    public TNode search(TNode root, int key) {
        if (root == null || root.key == key)
            return root;

        if (root.key < key)
            return search(root.right, key);

        return search(root.left, key);
    }

    
    public void insert(int key) {
        root = insertRec(root, key);
    }

    private TNode insertRec(TNode root, int key) {
        if (root == null) {
            root = new TNode(key);
            return root;
        }

        if (key < root.key)
            root.left = insertRec(root.left, key);
        else if (key > root.key)
            root.right = insertRec(root.right, key);

        return root;
    }

    
    
    
    public void deleteKey(int key) {
        root = deleteRec(root, key);
    }

    private TNode deleteRec(TNode root, int key) {
        if (root == null)
            return root;

        if (key < root.key)
            root.left = deleteRec(root.left, key);
        else if (key > root.key)
            root.right = deleteRec(root.right, key);
        else {
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            root.key = minValue(root.right);

            root.right = deleteRec(root.right, root.key);
        }

        return root;
    }

    private int minValue(TNode root) {
        int minv = root.key;
        while (root.left != null) {
            minv = root.left.key;
            root = root.left;
        }
        return minv;
    }

    
    public static void main(String[] args) {
        BinarySearchTree t = new BinarySearchTree();

        
        t.insert(5);
        t.insert(3);
        t.insert(10);
        t.insert(4);
        t.insert(2);
        t.insert(6);
        t.insert(8);

        
        TNode foundNode = t.search(t.root, 2);
        System.out.println("Search for 2: " + (foundNode != null ? "Found" : "Not found"));
        
        t.deleteKey(2);
        System.out.println("After deleting 2:");
        foundNode = t.search(t.root, 2);
        System.out.println("Search for 2: " + (foundNode != null ? "Found" : "Not found"));
    }
}
