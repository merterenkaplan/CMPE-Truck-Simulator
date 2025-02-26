// AVL Tree implementation in Java
// This class defines the structure and operations of an AVL tree,
// which is a self-balancing binary search tree where the difference between heights
// of left and right subtrees cannot be more than one for all nodes.
public class AvlTree{
    public Node root0 = null;
     int height(Node N) {
        if (N == null){
            return -1;
        }
        return N.height;
    }


    // Right rotation method to maintain AVL tree balance
    // This rotates the subtree rooted at node 'y' to the right
     Node rightRotate(Node y) {
        Node x = y.left;
        Node N2 = x.right;

        x.right = y;
        y.left = N2;


        y.height = 1 + Math.max(height(y.left),
                height(y.right));
        x.height = 1 + Math.max(height(x.left),
                height(x.right));

        return x;
    }

    // Left rotation method to maintain AVL tree balance
    // This rotates the subtree rooted at node 'y' to the left
     Node leftRotate(Node y) {
        Node x = y.right;
        Node T2 = x.left;

        x.left = y;
        y.right = T2;

        y.height = 1 + Math.max(height(y.left),
                height(y.right));
        x.height = 1 + Math.max(height(x.left),
                height(x.right));

        return x;
    }


    // Method to get the balance factor of a node
    // The balance factor is the difference between heights of left and right subtrees
     int getBalance(Node N) {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }


    // Helper method to find the node with the maximum value in a subtree

    private Node findMax(Node node) {
        while (node.right != null) {
            node = node.right;// Move to the rightmost node
        }
        return node;
    }

    public Node findPredecessor(int key) {
        Node current = root0;
        Node predecessor = null;

        while (current != null) {
            if (key > current.parkingLotInNode.capacityConstraint) {
                // Track the last smaller node as the potential predecessor
                predecessor = current;
                current = current.right;
            } else if (key < current.parkingLotInNode.capacityConstraint) {
                current = current.left;
            } else {
                // Node with the key found
                if (current.left != null) {
                    // Find the maximum in the left subtree
                    predecessor = findMax(current.left);
                }
                break;
            }
        }
        return predecessor;
    }
    public Node findNextGreaterTraditional(int targetCapacity) { // This method is used to find the next greater node in the first search.
        Node current = root0;
        Node nextBigger = null;

        while (current != null) {
            if (targetCapacity < current.parkingLotInNode.capacityConstraint) {
                nextBigger = current;
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return nextBigger;
    }






    // Method to insert a new key into the AVL tree
    // Returns the new root of the subtree after insertion and balancing
    public Node insert(Node N, parkingLot P) {
        // Perform normal BST insertion
        if (N == null) {
            return new Node(P);
        }

        if (P.capacityConstraint < N.parkingLotInNode.capacityConstraint) {
            N.left = insert(N.left, P);
        } else if (P.capacityConstraint > N.parkingLotInNode.capacityConstraint) {
            N.right = insert(N.right, P);
        } else {
            return N; // Duplicates not allowed in AVL tree
        }

        // Update height of this ancestor node
        N.height = 1 + Math.max(height(N.left), height(N.right));

        // Get the balance factor to check if this node is unbalanced
        int balance = getBalance(N);

        // Rotation cases
        // Left-Left Case
        if (balance > 1 && P.capacityConstraint < N.left.parkingLotInNode.capacityConstraint) {
            return rightRotate(N);
        }

        // Right-Right Case
        if (balance < -1 && P.capacityConstraint > N.right.parkingLotInNode.capacityConstraint) {
            return leftRotate(N);
        }

        // Left-Right Case
        if (balance > 1 && P.capacityConstraint > N.left.parkingLotInNode.capacityConstraint) {
            N.left = leftRotate(N.left);
            return rightRotate(N);
        }

        // Right-Left Case
        if (balance < -1 && P.capacityConstraint < N.right.parkingLotInNode.capacityConstraint) {
            N.right = rightRotate(N.right);
            return leftRotate(N);
        }

        return N; // Return the unchanged node pointer
    }
    public void insert(parkingLot key) {
        root0 = insert(root0, key);
    }
    Node minValueNode(Node N) {
        Node current = N;

        // loop down to find the leftmost leaf
        while (current.left != null)
            current = current.left;

        return current;
    }


    // Method to delete a node with a given key from the AVL tree

    Node deleteNode(Node N, parkingLot P){
        // STEP 1: Perform standard BST delete
        if (N == null){
            return N;
        }
        // If the key to be deleted is smaller than the root's key,
        // then it lies in left subtree
        if(P.capacityConstraint<N.parkingLotInNode.capacityConstraint){
            N.left = deleteNode(N.left, P);
        }else if (P.capacityConstraint>N.parkingLotInNode.capacityConstraint) {// If the key to be deleted is greater than the root's key,
            // then it lies in right subtree
            N.right = deleteNode(N.right, P);
        }
        // if key is same as root's key, then this is the node to be deleted
        else{
            // node with only one child or no child
            if ((N.left == null) || (N.right == null)) {
                Node temp = N.left != null ? N.left : N.right;
                // No child case
            if (temp == null) {
                temp = N;
                N= null;} else {// One child case
                N = temp;// Copy the contents of the non-empty child
                    }
                }else {
                // node with two children: Get the inorder successor (smallest in the right subtree)
                Node temp = minValueNode(N.right);
                // Copy the inorder successor's data to this node
                N.parkingLotInNode = temp.parkingLotInNode;
                // Delete the inorder successor
                N.right = deleteNode(N.right, temp.parkingLotInNode);
            }
        }if (N == null) { // If the tree had only one node then return
            return N;
        }
        // STEP 2: Update height of the current node
        N.height = Math.max(height(N.left), height(N.right)) + 1;
        // STEP 3: Get the balance factor of this node (to check whether this node became unbalanced)
        int balance = getBalance(N);
        // If this node becomes unbalanced, then there are 4 cases
        // Left Left Case
        if (balance > 1 && getBalance(N.left) >= 0) {
            return rightRotate(N);
        }
        // Left Right Case
        if (balance > 1 && getBalance(N.left) < 0) {
            N.left = leftRotate(N.left);
            return rightRotate(N);
        }
        // Right Right Case
        if (balance < -1 && getBalance(N.right) <= 0){
            return leftRotate(N);
        }
        // Right Left Case
        if (balance < -1 && getBalance(N.right) > 0) {
            N.right = rightRotate(N.right);
            return leftRotate(N);
        }
        return N;

    }
    public void delete(parkingLot key) {
        root0 = deleteNode(root0, key);
    }

    public boolean search(Node root, int capacityConstraint)
    {
        // Base Cases: root is null or key is present at
        // root
        if (root == null )
            return false;
        if (root.parkingLotInNode.capacityConstraint==capacityConstraint){
            return true;
        }
        // Key is greater than root's key
        if (root.parkingLotInNode.capacityConstraint < capacityConstraint)
            return search(root.right, capacityConstraint);

        // Key is smaller than root's key
        return search(root.left, capacityConstraint);
    }

    // helper method for search
    public boolean searche(int capacityConstraint) {
        boolean root2 = search(root0,capacityConstraint);
        return root2;
    }

}
