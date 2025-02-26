public class queue {
    int front, rear, capacity;
    truck[] queue;

    // Constructor to initialize the queue
    queue(int c) {
        front = 0;
        rear = -1;
        capacity = c;
        queue = new truck[capacity];
    }

    // Function to insert an element at the rear of the queue
    void enqueue(truck TData) {
        rear = (rear+1)%capacity;
        queue[rear] = TData;
    }

    // Function to delete an element from the front of the queue
    void dequeue() {
        queue[front]= null;
        front = (front+1)%capacity;
    }

    public truck front() {
        return queue[front];
    }


}