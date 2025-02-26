// Class to represent a truck with unique ID, load, and maximum capacity

public class truck {

    // Unique identifier for the truck

    private Integer ID;

    // Current load of the truck
    private Integer load;

    // Maximum capacity that the truck can hold (constant)

    final Integer maximumCapacity;

    // Constructor to initialize a truck with an ID and maximum capacity, with load defaulted to 0

    public truck(int ID,int maximumCapacity){
        this.ID = ID; // Set truck ID
        this.maximumCapacity=maximumCapacity; // Set truck's maximum capacity
        this.load=0;// Initialize load to 0
    }

    // Overloaded constructor to initialize a truck with an ID, maximum capacity, and initial load
    public truck(int ID,int maximumCapacity,int load){
        this.ID = ID;// Set truck ID

        this.maximumCapacity=maximumCapacity;// Set truck's maximum capacity
        this.load=load;// Set initial load
    }

    // Getter for truck ID

    public Integer getID() {
        return ID;
    }

    // Getter for current load of the truck

    public Integer getLoad() {
        return load;
    }


    // Method to add a specified load to the truck
    // If the load exceeds the maximum capacity, the load is reset to 0
    public void  addingLoad(int newLoad){
        this.load = this.load + newLoad;// Add the new load to the current load
        if (this.load >= maximumCapacity){
            load = 0;// Reset load if it exceeds maximum capacity
        }
    }

    // Getter for the maximum capacity of the truck

    public Integer getMaximumCapacity() {
        return maximumCapacity;
    }



}
