# CMPE-Truck-Simulator
## Project Overview
The CMPE Truck Simulator is a project for CmpE 250 (Data Structures and Algorithms) in Fall 2024. The project simulates managing a fleet of trucks with different capacities, ensuring efficient job assignments while following specific rules for parking, loading, and moving trucks.
## Key Features
1.Truck Management

   - Each truck has a unique ID and a maximum capacity.
   - Trucks cannot be loaded beyond their capacity.
   - Trucks move between parking lots based on capacity constraints.

2.Parking Lot System

   -Each parking lot has a capacity constraint and a truck limit.
   -Trucks are initially placed in a waiting section and must be moved to a ready section before accepting loads.
   -If a parking lot is full, trucks are placed in the next available smaller lot.

3.Loading and Moving Trucks

  -Loads are assigned to the earliest ready truck in a parking lot.
  -If a truck reaches its full capacity, it unloads and moves to a new parking lot.
  -If trucks in a lot cannot fully receive a load, the excess is transferred to the next available larger parking lot.

4.Operations & Commands

  -Create a Parking Lot (create_parking_lot <capacity> <limit>)
  -Delete a Parking Lot (delete_parking_lot <capacity>)
  -Add a Truck (add_truck <truck_id> <capacity>)
  -Make a Truck Ready (ready <capacity>)
  -Load Trucks in a Parking Lot (load <capacity> <amount>)
  -Count Trucks in Larger Lots (count <capacity>)


5. Constraints & Performance
  -The project must be implemented in Java using ArrayLists only.
  -Maximum time per test case: 25 seconds.
  -Large-scale data handling with up to 500,000 trucks and operations.




## How to Run
   Compile the code:

  javac *.java

  Run the program with input/output files:

  java Main <land_file> <travel_time_file> <mission_file>
