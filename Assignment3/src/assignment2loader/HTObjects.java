/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2loader;

import java.util.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//Sligtly modified hash table to accomodate key/object pairs
public class HTObjects implements java.io.Serializable 
{
    static final class Node 
    {
	Object key;
	Node next;
	// int count;
	Object value;
        
	Node(Object k, Node n, Object v) 
        { 
            key = k;
            next = n;
            value = v;
        }
    }
    Node[] table = new Node[8]; // always a power of 2
    int size = 0;
    
    boolean contains(Object key) 
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
    
    Object findObject(Object key)
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
    
    void add(Object key, Object value) 
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

    public Object[] toArray()
    {
        Object[] tbl = new Object[size];
        int index = 0;
        for (int i = 0; i < table.length; ++i)
            for (Node e = table[i]; e != null; e = e.next)
            {
                tbl[index] = e.key;
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
    
    void remove(Object key) 
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
    
    private void writeObject(ObjectOutputStream s) throws Exception 
    {
	s.defaultWriteObject();
	s.writeInt(size);
	for (int i = 0; i < table.length; ++i) 
        {
	    for (Node e = table[i]; e != null; e = e.next) 
            {
		s.writeObject(e.key);
	    }
	}
    }
    /*
    private void readObject(ObjectInputStream s) throws Exception 
    {
	s.defaultReadObject();
	int n = s.readInt();
	for (int i = 0; i < n; ++i)
	    add(s.readObject());
    }
*/
}
