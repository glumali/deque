/******************************************************************************
 *  Name:    Greg Umali
 * 
 *  Description: Implements a Deque, a data structure where elements can be
 *  added or removed from either end (the head or the tail) of the Deque.
 *
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
   
    // tracks how many items there are in the 
    private int iNodeCount;
    // maintains pointers to head and tail of Deque
    private Node<Item> oHead;
    private Node<Item> oTail;
    
    // construct an empty deque
    // overall structure: linked list, with variables pointing to the
    // head (start) and the tail (end) of the Deque
    public Deque() {
        iNodeCount = 0;
        
        oHead = null;
        oTail = null;
    }
   
    // is the deque empty?
    public boolean isEmpty() {
        return (iNodeCount == 0);
    }
   
    // return the number of items on the deque
    public int size() {
        return iNodeCount;
    }
   
    // add the item to the front
    public void addFirst(Item item) {
        
        if (item == null) throw new NullPointerException();
        
        // encapsulate the item in a Node object
        Node<Item> newNode = new Node<Item>();
        newNode.payload = item;
        
        // if the Deque is empty
        if (iNodeCount == 0) {
            oHead = newNode;
            oTail = newNode;
            iNodeCount++;
        }
        
        else {
            // set the next variable of the new Node
            newNode.next = oHead;
            // set the prev variable of the old first Node
            oHead.prev = newNode;
            // reassign the oHead variable
            oHead = newNode;
            iNodeCount++;
        }
    }
   
    // add the item to the end
    public void addLast(Item item) {
        
        if (item == null) throw new NullPointerException();
        
        // encapsulate the item in a Node object
        Node<Item> newNode = new Node<Item>();
        newNode.payload = item;
        
        // if the Deque is empty
        if (iNodeCount == 0) {
            oHead = newNode;
            oTail = newNode;
            iNodeCount++;
        }
        
        else {
            // set the prev variable of the new Node
            newNode.prev = oTail;
            // set the next variable of the old last Node
            oTail.next = newNode;
            // reassign the oTail variable
            oTail = newNode;
            iNodeCount++;
        }
    }
   
    // remove and return the item from the front
    public Item removeFirst() {
        
        if (iNodeCount == 0) throw new NoSuchElementException();
        
        // track the Node that we're about to remove
        Node<Item> removedNode = oHead;
        // avoids multiple function calls
        Node<Item> secondNode = oHead.next;
        
        if (secondNode != null) {
            // set the prev variable of the new oHead node
            secondNode.prev = null;
            
            // reassign the oHead variable
            oHead = secondNode;
        }
        
        iNodeCount--;
        return removedNode.payload;
    }
   
    // remove and return the item from the end
    public Item removeLast() {
        
        if (iNodeCount == 0) throw new NoSuchElementException();
        
        // track the Node that we're about to remove
        Node<Item> removedNode = oTail;
        // avoids multiple function calls
        Node<Item> secondToLastNode = oTail.prev;
        
        if (secondToLastNode != null) {
            // set the next variable of the new oTail node
            secondToLastNode.next = null;
            
            // reassign the oTail variable
            oTail = secondToLastNode;
        }
        
        iNodeCount--;
        return removedNode.payload;
    }
   
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
   
    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current = oHead;
        
        public boolean hasNext() { return current != null; }
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item payload = current.payload;
            current = current.next;
            return payload;
        }
        public void remove() { throw new UnsupportedOperationException(); }
    }
    
    private static class Node<Item> {     
        // points to prev Node (null if at the beginning)
        private Node<Item> prev;
        
        // points to next Node (null if at the end)
        private Node<Item> next;
        
        // payload data stored in each Node
        private Item payload;
    }
    
        // unit testing (required)
    public static void main(String[] args) {
        System.out.println("Creating new Deque...");
        Deque<String> testDeque = new Deque<String>();
        
        System.out.println("Current deque size: " + testDeque.size());
        
        if (testDeque.isEmpty()) { System.out.println("Deque empty."); }
        else { System.out.println("Deque not empty."); }
        
        System.out.println("\nAdding three strings to the Deque");
        testDeque.addFirst("String 1");
        testDeque.addFirst("String 2");
        testDeque.addLast("String 3");
        
        for (String i : testDeque) {
            System.out.println("Item: " + i);
        }
        System.out.println("Current deque size: " + testDeque.size());
        
        if (testDeque.isEmpty()) { System.out.println("Deque empty."); }
        else { System.out.println("Deque not empty."); }
        
        System.out.println("\nRemoving two strings from the Deque");
        System.out.println("Removing " + testDeque.removeFirst());
        System.out.println("Removing " + testDeque.removeLast());
        System.out.println("Current deque size: " + testDeque.size());
        
        if (testDeque.isEmpty()) { System.out.println("Deque empty."); }
        else { System.out.println("Deque not empty."); }
        
        System.out.println("\nRemoving one string from the Deque");
        System.out.println("Removing " + testDeque.removeFirst());
        
        System.out.println("\nAdding one string to the Deque");
        testDeque.addFirst("String 4");
        System.out.println("Current deque size: " + testDeque.size());
        
        System.out.println("\nRemoving one string from the Deque");
        System.out.println("Removing " + testDeque.removeLast());
        
        System.out.println("Current deque size: " + testDeque.size());
        
        if (testDeque.isEmpty()) { System.out.println("Deque empty."); }
        else { System.out.println("Deque not empty."); }
    }
}