/******************************************************************************
 *  Name:    Greg Umali
 * 
 *  Description: Implements a RandomizedQueue, a data structure where elements 
 *  removed are chosen uniformly from the elements in the RandomizedQueue.
 *
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
   
    private int iNumElements;
    private Item[] aRandQueue;
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        aRandQueue = (Item[]) new Object[1];
        iNumElements = 0;
    }
   
    // is the queue empty?
    public boolean isEmpty() {
        return (iNumElements == 0);
    }
   
    // return the number of items on the queue
    public int size() {
        return iNumElements;
    }
    
    // (for debugging) return the physical size of the array
    private int physSize() {
        return aRandQueue.length;
    }
   
    // add the item
    public void enqueue(Item item) {
        
        if (item == null) throw new NullPointerException();
        
        // resizes if queue is full
        if (iNumElements == aRandQueue.length) resize(2 * aRandQueue.length);
        // uses iNumElements as the index to insert, and increments it
        aRandQueue[iNumElements++] = item;
    }
    
    // change the physical size of the array in which elements are contained
    // (adapted from lecture code)
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < iNumElements; i++) copy[i] = aRandQueue[i];
        aRandQueue = copy;
    }
   
    // remove and return a random item
    public Item dequeue() {
        
        if (iNumElements == 0) throw new NoSuchElementException();
        
        // choose a random index among all the items in the queue
        int iRandIdx = StdRandom.uniform(iNumElements);
        // switch that item with the last item
        Item randItem = aRandQueue[iRandIdx];
        aRandQueue[iRandIdx] = aRandQueue[iNumElements - 1];
        aRandQueue[iNumElements - 1] = null;
        // remove and return the last item, decrementing iNumElements
        iNumElements--;
        
        // if at 1/4 capacity, shrink array
        if (iNumElements <= (aRandQueue.length / 4) && 
            (aRandQueue.length / 2) > 0) {
            resize(aRandQueue.length / 2);
        }
        
        return randItem;
    }
   
    // return a random item (but do not remove it)
    public Item sample() {
        
        if (iNumElements == 0) throw new NoSuchElementException();
        
        // choose a random index among all the items in the queue
        int iRandIdx = StdRandom.uniform(iNumElements);
        // return that item
        return aRandQueue[iRandIdx];
    }
   
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    
    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] random = (Item[]) new Object[iNumElements];
        private int iIdx;
        
        public RandomizedQueueIterator() {
            // will be used to track which element we are on
            iIdx = 0;
            
            // initialize the random ordering of elements
            for (int i = 0; i < iNumElements; i++) {
                random[i] = aRandQueue[i];
            }
            
            // shuffle the random array
            for (int i = 0; i < iNumElements; i++) {
                int randIdx = StdRandom.uniform(i + 1);
                Item temp = random[i];
                random[i] = random[randIdx];
                random[randIdx] = temp;
            }
        }
        
        public boolean hasNext() { 
            return iIdx < iNumElements; 
        }
        
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item payload = random[iIdx];
            iIdx++;
            return payload;
        }
        
        public void remove() { throw new UnsupportedOperationException(); }
    }
   
    // unit testing (required)
    public static void main(String[] args) {
        System.out.println("Creating new RandQueue...");
        RandomizedQueue<Double> testRandQueue = new RandomizedQueue<Double>();
        
        System.out.println("Current RandQueue size: " + testRandQueue.size());
        System.out.println("Physical size: " + testRandQueue.physSize());
        
        if (testRandQueue.isEmpty()) { System.out.println("RandQueue empty."); }
        else { System.out.println("RandQueue not empty."); }
        
        System.out.println("\nAdding five doubles to the RandQueue");
        testRandQueue.enqueue(1.0);
        testRandQueue.enqueue(2.0);
        testRandQueue.enqueue(3.0);
        testRandQueue.enqueue(4.0);
        testRandQueue.enqueue(5.0);
        
        System.out.println("Physical size: " + testRandQueue.physSize());
        
        System.out.println("\nRandom printing once...");
        for (double i : testRandQueue) {
            System.out.println("Item: " + i);
        }
        
        System.out.println("\nRandom printing twice...");
        for (double i : testRandQueue) {
            System.out.println("Item: " + i);
        }
        
        System.out.println("\nOne more for good luck...");
        for (double i : testRandQueue) {
            System.out.println("Item: " + i);
        }
        
        System.out.println("\nCurrent RandQueue size: " + testRandQueue.size());
        
        if (testRandQueue.isEmpty()) { System.out.println("RandQueue empty."); }
        else { System.out.println("RandQueue not empty."); }
        
        System.out.println("\nRemoving two doubles from the RandQueue");
        System.out.println("Removing " + testRandQueue.dequeue());
        System.out.println("Removing " + testRandQueue.dequeue());
        System.out.println("Current RandQueue size: " + testRandQueue.size());
        
        System.out.println("\nRandom printing once...");
        for (double i : testRandQueue) {
            System.out.println("Item: " + i);
        }
        
        System.out.println("Random Sample: " + testRandQueue.sample());
        System.out.println("Random Sample: " + testRandQueue.sample());
        
        if (testRandQueue.isEmpty()) { System.out.println("RandQueue empty."); }
        else { System.out.println("RandQueue not empty."); }
        
        System.out.println("\nRemoving two doubles from the RandQueue");
        System.out.println("Removing " + testRandQueue.dequeue());
        System.out.println("Removing " + testRandQueue.dequeue());
        System.out.println("Current RandQueue size: " + testRandQueue.size());
        
        System.out.println("Physical size: " + testRandQueue.physSize());
        
        System.out.println("Removing " + testRandQueue.dequeue());
        System.out.println("Current RandQueue size: " + testRandQueue.size());
        
        if (testRandQueue.isEmpty()) { System.out.println("RandQueue empty."); }
        else { System.out.println("RandQueue not empty."); }
    }
}