import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;



public class Main {
    // Main class that runs the entire program.

    public static void main(String[] args) throws IOException {

        // Main entry point of the program. Sets up the program, handles file I/O, and reads commands.
        StringBuilder writerToOutput = new StringBuilder();
        // StringBuilder to accumulate output before writing to output file.
        double x = System.currentTimeMillis();
        // Start time capture for performance measurement.
        File file = new File(args[0]);
        // Define input file from which commands are read.
        Scanner scanner = new Scanner(file);
        // Scanner for reading input from the file.
        FileWriter writer1 = new FileWriter(args[1]);
        BufferedWriter writer = new BufferedWriter(writer1);
        HashMap hash = new HashMap<>();
        // HashMap to store parking lots by capacity constraint.

        AvlTree addtruckavaible = new AvlTree();
        // AVL Trees to manage different parking lot statuses.
        AvlTree readyavaible = new AvlTree();
        // AVL Trees to manage different parking lot statuses.
        AvlTree loadavaible = new AvlTree();
        // AVL Trees to manage different parking lot statuses.
        int i =0 ;
        while (scanner.hasNextLine()) {
            i ++;
            try{
                String Line = scanner.next();
                if (Line.equals("create_parking_lot")){
                    // Command to create a new parking lot with specified constraints.
                    int capacityConstraint = scanner.nextInt();
                    int truckLimit = scanner.nextInt();
                    parkingLot newparkinglot = new parkingLot(capacityConstraint,truckLimit);
                    hash.put(capacityConstraint,newparkinglot);
                    creatingParkingLot(capacityConstraint,truckLimit,addtruckavaible);

                } else if (Line.equals("delete_parking_lot")) {
                    // Command to delete a parking lot by its capacity constraint.
                    int capacityConstraint = scanner.nextInt();
                    deletingparkinglot(capacityConstraint,addtruckavaible,readyavaible,loadavaible,hash);
                    hash.remove(capacityConstraint);
                } else if (Line.equals("add_truck")) {
                    // Command to add a truck with an ID and max capacity to a parking lot.
                    int ID = scanner.nextInt();
                    int maxCapacity = scanner.nextInt();
                    AddingATruck(maxCapacity,ID,addtruckavaible,readyavaible,hash,writerToOutput,i);
                } else if (Line.equals("ready")) {
                    // Command to mark a parking lot as ready for loading operations.
                    int capacityConstraint = scanner.nextInt();
                    ready(capacityConstraint,readyavaible,loadavaible,hash,writerToOutput,i);
                } else if (Line.equals("load")) {
                    // Command to load a specified amount into a parking lot.
                    int capacityConstraint = scanner.nextInt();
                    int loadAmount = scanner.nextInt();
                    load(capacityConstraint,loadAmount,loadavaible,readyavaible,addtruckavaible,hash,writerToOutput);

                } else if (Line.equals("count")) {
                    // Command to count trucks in lots with capacities exceeding specified constraint.
                    int capacityConstraint = scanner.nextInt();
                    count(capacityConstraint,hash,writerToOutput);


                }

            } catch (NoSuchElementException e) {
            }


        }

        writer.write(writerToOutput.toString());
        writer.flush();
        scanner.close();
        writer.close();
        System.out.println((System.currentTimeMillis()-x)/1000.0);
    }

    public static void creatingParkingLot(int capacity_constraint, int truck_limit,AvlTree addtruckavaible){
        // Method to create and add a new parking lot to the available truck AVL tree.
        parkingLot newLot = new parkingLot(capacity_constraint,truck_limit);
        if (capacity_constraint>0){
            addtruckavaible.insert(newLot);

        }
    }
    public static void deletingparkinglot(int capacity_constraint,AvlTree addtruckavaible,AvlTree readyavaible,AvlTree loadavaible,HashMap hash){
        // Method to delete a parking lot from all AVL trees and the hash map.
        parkingLot parkingLotToDelete= hash.get(capacity_constraint);
        addtruckavaible.delete(parkingLotToDelete);
        readyavaible.delete(parkingLotToDelete);
        loadavaible.delete(parkingLotToDelete);
    }
    public static void AddingATruck(int capacity_constraint,int ID,AvlTree addtruckavaible,AvlTree readyavaible,HashMap hash,StringBuilder writerToOutput,int i) throws IOException {
        // StringBuilder to accumulate output before writing to output file.
        ArrayList<parkingLot> arrayListToAddingTruck = new ArrayList<>();
        if(!addtruckavaible.searche(capacity_constraint)){
            if (addtruckavaible.findPredecessor(capacity_constraint)==null){
                writerToOutput.append(-1+ System.lineSeparator());
            } else {
                parkingLot parkingLotToAddingTruck= hash.get(addtruckavaible.findPredecessor(capacity_constraint).parkingLotInNode.capacityConstraint);
                parkingLotToAddingTruck.addingATruck(ID,capacity_constraint,0);
                if (hash.get(addtruckavaible.findPredecessor(capacity_constraint).parkingLotInNode.capacityConstraint).isfull()){
                    arrayListToAddingTruck.add(parkingLotToAddingTruck);
                }
                readyavaible.insert(parkingLotToAddingTruck);

                writerToOutput.append(Integer.toString((addtruckavaible.findPredecessor(capacity_constraint).parkingLotInNode.capacityConstraint))+ System.lineSeparator());

            }
        }else {
            parkingLot parkingLotToAddingTruck= hash.get((capacity_constraint));
            hash.get(capacity_constraint).addingATruck(ID,capacity_constraint,0);
            if (hash.get((capacity_constraint)).isfull()){
                arrayListToAddingTruck.add(parkingLotToAddingTruck);

            }
            readyavaible.insert(parkingLotToAddingTruck);


            writerToOutput.append(Integer.toString(capacity_constraint)+ System.lineSeparator());
        }
        if (!arrayListToAddingTruck.isEmpty()){
            addtruckavaible.delete(arrayListToAddingTruck.get(0));

        }

    }
    public static int addTruckForLoad(int capacity_constraint,int ID,AvlTree addtruckavaible,AvlTree readyavaible,HashMap hash,StringBuilder writerToOutput,int maxCapatiy,int load){
        // StringBuilder to accumulate output before writing to output file.
        ArrayList<parkingLot> arrayListToAddingTruckForLoad = new ArrayList<>();
        int newParkingLoadCapacity ;
        if(!addtruckavaible.searche(capacity_constraint)){
            if (addtruckavaible.findPredecessor(capacity_constraint)==null){
                newParkingLoadCapacity = -1;

            } else {
                parkingLot parkingLotToAddingTruckForLoad= hash.get(addtruckavaible.findPredecessor(capacity_constraint).parkingLotInNode.capacityConstraint);
                parkingLotToAddingTruckForLoad.addingATruck(ID,maxCapatiy,load);
                if (hash.get(addtruckavaible.findPredecessor(capacity_constraint).parkingLotInNode.capacityConstraint).isfull()){
                    arrayListToAddingTruckForLoad.add(parkingLotToAddingTruckForLoad);
                }
                readyavaible.insert(parkingLotToAddingTruckForLoad);
                newParkingLoadCapacity = addtruckavaible.findPredecessor(capacity_constraint).parkingLotInNode.capacityConstraint;


            }
        }else {
            parkingLot parkingLotToAddingTruckForLoad= hash.get((capacity_constraint));
            hash.get(capacity_constraint).addingATruck(ID,maxCapatiy,load);
            if (hash.get((capacity_constraint)).isfull()){
                arrayListToAddingTruckForLoad.add(parkingLotToAddingTruckForLoad);

            }
            readyavaible.insert(parkingLotToAddingTruckForLoad);
            newParkingLoadCapacity = capacity_constraint;

        }
        if (!arrayListToAddingTruckForLoad.isEmpty()){
            addtruckavaible.delete(arrayListToAddingTruckForLoad.get(0));

        }
        return newParkingLoadCapacity;
    }


    public static void ready(int capacity_constraint,AvlTree readyavaible,AvlTree loadavaible,HashMap hash,StringBuilder writerToOutput,int i){
        // StringBuilder to accumulate output before writing to output file.
        if (readyavaible.searche(capacity_constraint)){
            parkingLot parkingLoadToReady= hash.get((capacity_constraint));
            writerToOutput.append(Integer.toString(parkingLoadToReady.waitingTrucks.front().getID())+" " + Integer.toString(parkingLoadToReady.capacityConstraint) + System.lineSeparator());
            parkingLoadToReady.ready();
            loadavaible.insert(parkingLoadToReady);

            if (parkingLoadToReady.waitingTruckNumber==0){
                readyavaible.delete(parkingLoadToReady);
            }
        }else if (readyavaible.findNextGreaterTraditional(capacity_constraint)!=null){

            parkingLot parkingLoadToReady = hash.get(readyavaible.findNextGreaterTraditional(capacity_constraint).parkingLotInNode.capacityConstraint);
            writerToOutput.append(Integer.toString(parkingLoadToReady.waitingTrucks.front().getID()) +" " +Integer.toString(parkingLoadToReady.capacityConstraint) + System.lineSeparator());
            parkingLoadToReady.ready();
            loadavaible.insert(parkingLoadToReady);

            if (parkingLoadToReady.waitingTruckNumber==0){
                readyavaible.delete(parkingLoadToReady);
            }

        }
        else {

            writerToOutput.append(-1+ System.lineSeparator());

        }

    }
    public static void load(int capacity_constraint,int load_amount,AvlTree loadavaible,AvlTree readyavaible,AvlTree addtruckavaible,HashMap hash,StringBuilder writerToOutput){
        // StringBuilder to accumulate output before writing to output file.
        ArrayList<String> arrayListForWriting = new ArrayList<>();

        int i = 0 ;
        while(load_amount!=0){
            if (loadavaible.searche(capacity_constraint)){
                parkingLot parkingLotToLoad= hash.get((capacity_constraint));
                if (parkingLotToLoad.capacityConstraint<=load_amount){
                    int[] values =parkingLotToLoad.recievingALoad(capacity_constraint);
                    addtruckavaible.insert(parkingLotToLoad);
                    int newparkingload = addTruckForLoad(values[2]-values[1],values[0],addtruckavaible,readyavaible,hash,writerToOutput,values[2],values[1]);
                    if (hash.get(capacity_constraint).readyTruckNumber==0){
                        loadavaible.delete(parkingLotToLoad);
                    }
                    arrayListForWriting.add(Integer.toString(values[0]));
                    arrayListForWriting.add(" ");
                    arrayListForWriting.add(Integer.toString(newparkingload));
                    arrayListForWriting.add(" - ");
                    load_amount = load_amount-capacity_constraint;
                }
                else {
                    int[] values =parkingLotToLoad.recievingALoad(load_amount);
                    addtruckavaible.insert(parkingLotToLoad);
                    int newparkingload = addTruckForLoad(values[2]-values[1],values[0],addtruckavaible,readyavaible,hash,writerToOutput,values[2],values[1]);
                    if (hash.get((capacity_constraint)).readyTruckNumber==0){
                        loadavaible.delete(parkingLotToLoad);
                    }
                    load_amount = 0;
                    arrayListForWriting.add(Integer.toString(values[0]));
                    arrayListForWriting.add(" ");
                    arrayListForWriting.add(Integer.toString(newparkingload));
                    arrayListForWriting.add(" - ");

                }
            }else if (loadavaible.findNextGreaterTraditional(capacity_constraint)!=null){
                parkingLot parkingLotToLoad= hash.get(loadavaible.findNextGreaterTraditional(capacity_constraint).parkingLotInNode.capacityConstraint);
                if (parkingLotToLoad.capacityConstraint<=load_amount){
                    int[] values =parkingLotToLoad.recievingALoad(loadavaible.findNextGreaterTraditional(capacity_constraint).parkingLotInNode.capacityConstraint);
                    addtruckavaible.insert(parkingLotToLoad);
                    int newparkingload = addTruckForLoad(values[2]-values[1],values[0],addtruckavaible,readyavaible,hash,writerToOutput,values[2],values[1]);
                    load_amount = load_amount-loadavaible.findNextGreaterTraditional(capacity_constraint).parkingLotInNode.capacityConstraint;
                    arrayListForWriting.add(Integer.toString(values[0]));
                    arrayListForWriting.add(" ");
                    arrayListForWriting.add(Integer.toString(newparkingload));
                    arrayListForWriting.add(" - ");

                    if (hash.get(loadavaible.findNextGreaterTraditional(capacity_constraint).parkingLotInNode.capacityConstraint).readyTruckNumber==0){
                        loadavaible.delete(parkingLotToLoad);
                    }


                } else {
                    int[] values =parkingLotToLoad.recievingALoad(load_amount);
                    addtruckavaible.insert(parkingLotToLoad);
                    int newparkingload = addTruckForLoad(values[2]-values[1],values[0],addtruckavaible,readyavaible,hash,writerToOutput,values[2],values[1]);
                    if (hash.get(loadavaible.findNextGreaterTraditional(capacity_constraint).parkingLotInNode.capacityConstraint).readyTruckNumber==0){
                        loadavaible.delete(parkingLotToLoad);
                    }
                    load_amount = 0;
                    arrayListForWriting.add(Integer.toString(values[0]));
                    arrayListForWriting.add(" ");
                    arrayListForWriting.add(Integer.toString(newparkingload));
                    arrayListForWriting.add(" - ");


                }

            }else {
                if (i == 0){
                    arrayListForWriting.add("-1");
                }
                load_amount = 0;

            }

            i++;
        }
        int j = arrayListForWriting.size();
        for (int a=0 ;a<j;a++){
            if (j>1){
                if (a==j-1){
                    if (arrayListForWriting.get(a)==" - "){
                    }
                    else {
                        writerToOutput.append(arrayListForWriting.get(a));
                    }
                }else {writerToOutput.append(arrayListForWriting.get(a));}
            }else {
                writerToOutput.append(arrayListForWriting.get(a));
            }
        }

        writerToOutput.append(System.lineSeparator());
    }
    public static void count(int capacity,HashMap hash,StringBuilder writerToOutput){
        // StringBuilder to accumulate output before writing to output file.
        int totalCount = 0;
        for (int i = capacity+1; i<500000;i++){
            if (hash.get(i)!=null) {
                totalCount += hash.get(i).truckNumber;
            }
        }
        writerToOutput.append(totalCount + System.lineSeparator());

    }
}

