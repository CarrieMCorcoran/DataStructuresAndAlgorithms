/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2loader;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Carrie
 */
    public class PQ
    {
        private Node[] array;
        private int size;
        final int MAX = 99999;
        
        public static int leftOf(int k)
        {
            return (k << 1) + 1;
        }
        
        public static int rightOf(int k)
        {
            return leftOf(k) + 1;
        }
        
        public static int parentOf(int k)
        {
            return (k - 1) >>> 1;
        }
        
        PQ(ArrayList <Node> nodes, Node root)
        {
            //Sets up array of node objects
            int k = 1;
            array = new Node[nodes.size()];
            root.setBest(0);
            root.setPqIndex(0);
            array[0] = root;
            
            
            
            //Puts each node in array
            for (Node p: nodes)
            {
                p.setParent(null);
                if(!(p.equals(root)))
                {
                    p.setBest(MAX);
                    array[k] = p;
                    p.setPqIndex(k);
                    k++;
                }
            }
            size = k;
            

            
        }
        
        //Shifts the tree references based on a new shortest path
        void resift(Node x)
        {
            int k = x.getPqIndex();
            int parent;
            Node p;
            
            
            assert(array[k] == x);
            
            while(k > 0)
            {
                parent = parentOf(k);
                p = array[parent];
                
                if(x.compareTo(p) >= 0)
                    break;
                array[k] = p;
                p.setPqIndex(k);
                k = parent;
            }
            array[k] = x;
            x.setPqIndex(k);
        }
        
        /*Reorganizes the priority queue into an appropriate tree structure
        based on the best value to find shortest path
        */
        Node poll()
        {
            int n = size;
            Node least = null;
            Node x;
            Node c;
            Node r;
            int k;
            int child;
            int right;
            
            //If the array is empty, return null
            if (n == 0)
                return null;
            
            //Assign least to first array element
            least = array[0];
            
            //If no path to the first element has been assigned, return null
            if(least.getBest() == MAX)
                return null;
            
            size = --n;
            
            if(n > 0)
            {
                //Remove last element in array
                x = array[n];
                array[n] = null;
                
                k = 0;
                
                while((child = leftOf(k)) < n)
                {
                    c = array[child];
                    right = child + 1;
                    
                    if(right < n)
                    {
                        r = array[right];
                        if(c.compareTo(r) > 0)
                        {
                            c = r;
                            child = right;
                        }
                    }
                    if(x.compareTo(c) <= 0)
                    {
                        break;
                    }
                    array[k] = c;
                    c.setPqIndex(k);
                    k = child;
                }
                array[k] = x;
                x.setPqIndex(k);
                
            }
             
            return least;
        }
    }
