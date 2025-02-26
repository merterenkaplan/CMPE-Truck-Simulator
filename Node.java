// Class to represent a node in a binary tree (specifically for AVL trees)
// Each node contains a parking lot object and has left and right child pointers

public class Node {
    // The parking lot object contained in this node
    public parkingLot parkingLotInNode;

    // Pointers to the left and right child nodes
    public Node left;
    public Node right;

    // Height of the node, used in AVL tree balancing
    public int height;

    // Constructor to initialize a node with a given parking lot
    public Node(parkingLot parkingLotInNode){
        left = null; // Initialize left child as null
        right = null;// Initialize right child as null
        this.parkingLotInNode = parkingLotInNode; // Set the parking lot in this node
        height = 0; // Initialize height of this node as 0
    }
}
