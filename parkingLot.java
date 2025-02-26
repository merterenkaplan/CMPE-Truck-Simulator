public class parkingLot {

    // Maximum capacity constraint for the parking lot
    public int capacityConstraint;

    // Queues to manage trucks waiting and ready for loading

    public queue waitingTrucks;
    public queue readyTrucks;

    // Counters to keep track of truck numbers in different states

    public int truckNumber;
    public int readyTruckNumber;
    public int waitingTruckNumber;

    // Maximum number of trucks allowed in the parking lot

    public int trucklimit;

    // Constructor to initialize the parking lot with given constraints

    public parkingLot(int capacityConstraint, int trucklimit){
        this.trucklimit=trucklimit;
        this.capacityConstraint = capacityConstraint;
        this.waitingTrucks = new queue(trucklimit);
        this.readyTrucks = new queue(trucklimit);
        this.truckNumber = 0;
        this.readyTruckNumber = 0;
        this.waitingTruckNumber = 0;
    }


    // Method to add a truck to the waiting queue
    // Takes truck ID, capacity, and current load as parameters
    public void addingATruck(int truck_id,int capacity,int load){
        truck atruck = new truck(truck_id,capacity,load);// Create a new truck
        waitingTrucks.enqueue(atruck);// Add truck to waiting queue
        waitingTruckNumber++;// Increment waiting truck count
        truckNumber++; // Increment total truck count

    }

    // Method to move a truck from waiting to ready queue

    public void ready(){
        readyTrucks.enqueue(waitingTrucks.front());// Move the front truck from waiting to ready queue
        waitingTrucks.dequeue();// Remove the truck from waiting queue
        waitingTruckNumber--;// Decrement waiting truck count
        readyTruckNumber++;// Increment ready truck count
    }



    // Method to receive a load on the front truck in the ready queue
    // Takes load amount and returns truck's ID, load, and max capacity
    public int[] recievingALoad(int loadAmount){
        truck truckToLoad = readyTrucks.front();// Get the truck at the front of ready queue
        readyTrucks.dequeue(); // Remove the truck from ready queue
        readyTruckNumber--; // Decrement ready truck count
        truckNumber--;// Decrement total truck count
        truckToLoad.addingLoad(loadAmount);// Add the specified load to the truck
        return new int[]{truckToLoad.getID(),truckToLoad.getLoad(),truckToLoad.getMaximumCapacity()};
    }
    // Method to check if the parking lot is full

    public boolean isfull(){
        if (trucklimit>truckNumber){
            return false;
        }
        return true;
    }

}
