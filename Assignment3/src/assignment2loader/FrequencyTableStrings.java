package assignment2loader;

import java.util.*;
import java.io.*;
class FrequencyTableStrings implements java.io.Serializable 
{
    static final class Node implements java.io.Serializable
    {
	String key;
	Node next;
	int count;
	// Object value;
	Node(String k, Node n) 
        { 
            key = k; 
            next = n;
            count = 1;
        }
        Node(String k, Node n, int c)
        {
            key = k;
            next = n;
            count = c;
        }
    }
    Node[] table = new Node[8]; // always a power of 2
    int size = 0;
    
    public int getCount(String key) 
    {
	int h = key.hashCode();
	int i = h & (table.length - 1);
	for (Node e = table[i]; e != null; e = e.next) 
        {
	    if (key.equals(e.key))
		return e.count;
	}
	return 0;
    }
    public void add(String key) 
    {
	int h = key.hashCode();
	int i = h & (table.length - 1);
	for (Node e = table[i]; e != null; e = e.next) 
        {
	    if (key.equals(e.key))
            {
                e.count++;
		return;
            }
	}
	table[i] = new Node(key, table[i]);
	++size;
	if ((float)size/table.length >= 0.75f)
	    resize();
    }
    public void add(Node n)
    {
        int h = n.key.hashCode();
	int i = h & (table.length - 1);
	for (Node e = table[i]; e != null; e = e.next) 
        {
	    if (n.key.equals(e.key))
            {
		return;
            }
	}
        n.next = table[i];
	table[i] = n;
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
		newTable[j] = new Node(e.key, newTable[j], e.count);
	    }
	}
	table = newTable;
    }
    
    public void removeOne(String key)
    {
	int h = key.hashCode();
	int i = h & (table.length - 1);
	Node e = table[i], p = null;
	while (e != null) 
        {
	    if (key.equals(e.key)) 
            {
                if(e.count > 1)
                    e.count--;
                else
                {
                    if (p == null)
                        table[i] = e.next;
                    else
                        p.next = e.next;
                    break;
                }
	    }
	    p = e;
	    e = e.next;
	}        
    }
    
    public void removeAll(String key) 
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
    }
    public void printAll() 
    {
        for (int i = 0; i < table.length; ++i)
            for (Node e = table[i]; e != null; e = e.next)
                System.out.println(e.key + " " + e.count);
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
    
    public String toString()
    {
        String returning = "";
        
        for (int i = 0; i < table.length; ++i)
            for (Node e = table[i]; e != null; e = e.next)
                returning += e.key + " " + e.count + "; ";
        return returning;
    }
    
    public int count()
    {
        int count = 0;
        Node x;
        
        for(int index = 0; index < table.length; index++)
        {
            x = table[index];
            while(x != null)
            {
                count+= x.count;
                x = x.next;
            }
        }
        
        return count;
    }
    
    private void writeObject(ObjectOutputStream s) throws Exception {
	s.defaultWriteObject();
	s.writeInt(size);
	for (int i = 0; i < table.length; ++i) {
	    for (Node e = table[i]; e != null; e = e.next) {
		s.writeObject(e);
	    }
	}
        
    }
    private void readObject(ObjectInputStream s) throws Exception {
	s.defaultReadObject();
	int n = s.readInt();
        Node node;

	for (int i = 0; i < n; ++i)
        {
            node =(Node)s.readObject();
	    add(node);
        }
    }

}



