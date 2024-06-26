package assignment2application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

//Sligtly modified hash table to accomodate key/object pairs
public class HTRestaurants implements Serializable
{
    static final class Node implements Serializable
    {
	String key;
	Node next;
	// int count;
	Restaurant value;
        
	Node(String k, Node n, Restaurant v) 
        { 
            key = k;
            next = n;
            value = v;
        }
    }
    Node[] table = new Node[8]; // always a power of 2
    int size = 0;
    
    boolean contains(String key) 
    {
	int h = key.hashCode();
	int i = h & (table.length - 1);
	for (Node e = table[i]; e != null; e = e.next) 
        {
	    if (key.equals(e.key))
		return true;
	}
	return false;
    }
    
    Restaurant findObject(String key)
    {
        int h = key.hashCode();
	int i = h & (table.length - 1);
	for (Node e = table[i]; e != null; e = e.next) 
        {
	    if (key.equals(e.key))
		return e.value;
	}
	throw new NoSuchElementException();
    }
    
    void add(String key, Restaurant value) 
    {
	int h = key.hashCode();
	int i = h & (table.length - 1);
	for (Node e = table[i]; e != null; e = e.next) 
        {
	    if (key.equals(e.key))
		return;
	}
	table[i] = new Node(key, table[i], value);
	++size;
	if ((float)size/table.length >= 0.75f)
	    resize();
    }

    public Restaurant[] toArray()
    {
        Restaurant[] tbl = new Restaurant[size];
        int index = 0;
        for (int i = 0; i < table.length; ++i)
            for (Node e = table[i]; e != null; e = e.next)
            {
                tbl[index] = e.value;
                index++;
            }
        return tbl;
    }
    
    void resize() 
    {
	Node[] oldTable = table;
	int oldCapacity = oldTable.length;
	int newCapacity = oldCapacity << 1;
	Node[] newTable = new Node[newCapacity];
	for (int i = 0; i < oldCapacity; ++i) 
        {
	    for (Node e = oldTable[i]; e != null; e = e.next) 
            {
		int h = e.key.hashCode();
		int j = h & (newTable.length - 1);
		newTable[j] = new Node(e.key, newTable[j], e.value);
	    }
	}
	table = newTable;
    }
    
    void remove(String key) 
    {
	int h = key.hashCode();
	int i = h & (table.length - 1);
	Node e = table[i], p = null;
	while (e != null) {
	    if (key.equals(e.key)) {
		if (p == null)
		    table[i] = e.next;
		else
		    p.next = e.next;
		break;
	    }
	    p = e;
	    e = e.next;
	}
        size--;
    }
    
    void printAll() 
    {
        for (int i = 0; i < table.length; ++i)
            for (Node e = table[i]; e != null; e = e.next)
                System.out.println(e.value);
    }
    
    public int count()
    {
        return size;
    }
    
    public void createFiles()
    {
        Restaurant [] arr = toArray();
        HTStrings test = new HTStrings();
        FileOutputStream out;
        FileInputStream in;
        ObjectOutputStream objOut;
        ObjectInputStream objIn;
        Restaurant rest;
        
        try
        {
            for(int index = 0; index < arr.length; index++)
            {
                out = new FileOutputStream("restaurant" + index + ".txt");
                objOut = new ObjectOutputStream(out);
                objOut.writeObject(arr[index]);
                objOut.flush();
                objOut.close();
            }

            in = new FileInputStream("restaurant" + 59 + ".txt");
            objIn = new ObjectInputStream(in);
            rest = (Restaurant) objIn.readObject();
            objIn.close();
            
            System.out.println(rest);
            System.out.println(rest.getReviewWordsTable());
            
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }


        
    }
    
    private void writeObject(ObjectOutputStream s) throws Exception {
	s.defaultWriteObject();
	s.writeInt(size);
	for (int i = 0; i < table.length; ++i) {
	    for (Node e = table[i]; e != null; e = e.next) {
		s.writeObject(e.value);
	    }
	}
    }
    private void readObject(ObjectInputStream s) throws Exception {
	s.defaultReadObject();
	int n = s.readInt();
        Restaurant r;
	for (int i = 0; i < n; ++i)
        {
            r = (Restaurant)s.readObject();
	    add(r.getId(),r);
        }
    }
}