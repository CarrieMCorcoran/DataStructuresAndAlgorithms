package assignment2loader;

import java.util.*;
import java.io.*;
class HTStrings implements java.io.Serializable 
{
    static final class Node implements java.io.Serializable 
    {
	String key;
	Node next;
	// int count;
	// Object value;
	Node(String k, Node n) { key = k; next = n; }
    }
    Node[] table = new Node[8]; // always a power of 2
    int size = 0;
    
    public boolean contains(String key) 
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
    public void add(String key) 
    {
	int h = key.hashCode();
	int i = h & (table.length - 1);
	for (Node e = table[i]; e != null; e = e.next) 
        {
	    if (key.equals(e.key))
		return;
	}
	table[i] = new Node(key, table[i]);
	++size;
	if ((float)size/table.length >= 0.75f)
	    resize();
    }
    public void resize() 
    {
	Node[] oldTable = table;
	int oldCapacity = oldTable.length;
	int newCapacity = oldCapacity << 1;
	Node[] newTable = new Node[newCapacity];
	for (int i = 0; i < oldCapacity; ++i) {
	    for (Node e = oldTable[i]; e != null; e = e.next) 
            {
		int h = e.key.hashCode();
		int j = h & (newTable.length - 1);
		newTable[j] = new Node(e.key, newTable[j]);
	    }
	}
	table = newTable;
    }
    public void remove(String key) 
    {
	int h = key.hashCode();
	int i = h & (table.length - 1);
	Node e = table[i], p = null;
	while (e != null) 
        {
	    if (key.equals(e.key)) 
            {
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
    public void printAll() 
    {
        for (int i = 0; i < table.length; ++i)
            for (Node e = table[i]; e != null; e = e.next)
                System.out.println(e.key);
    }
    
    public String toString()
    {
        String returning = "";
        
        for (int i = 0; i < table.length; ++i)
            for (Node e = table[i]; e != null; e = e.next)
                returning += e.key + "; ";
        return returning;
    }
    
    public String[] toArray()
    {
        String[] tbl = new String[size];
        int index = 0;
        for (int i = 0; i < table.length; ++i)
            for (Node e = table[i]; e != null; e = e.next)
            {
                tbl[index] = e.key;
                index++;
            }
        return tbl;
    }
    
    public int count()
    {
        return size;
    }
    
    private void writeObject(ObjectOutputStream s) throws Exception {
	s.defaultWriteObject();
	s.writeInt(size);
	for (int i = 0; i < table.length; ++i) {
	    for (Node e = table[i]; e != null; e = e.next) {
		s.writeObject(e.key);
	    }
	}
    }
    private void readObject(ObjectInputStream s) throws Exception {
	s.defaultReadObject();
	int n = s.readInt();
	for (int i = 0; i < n; ++i)
	    add((String)s.readObject());
    }

}


