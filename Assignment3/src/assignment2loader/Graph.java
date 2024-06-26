/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2loader;

import java.util.ArrayList;

/**
 *
 * @author Carrie
 */
public class Graph
{
    ArrayList<Node> nodes;

    public Graph(ArrayList<Node> nodes)
    {
        this.nodes = nodes;
    }
    
    ArrayList<Restaurant> [] BuildShortestPathTree(Node root, Node destination)
    {
        PQ pq = new PQ(nodes, root);
        Node p;
        Node s;
        Node d;
        double w;
        Node alt;
        ArrayList<Restaurant> path = new ArrayList<Restaurant>();
        ArrayList<Restaurant> alternate = new ArrayList<Restaurant>();
        ArrayList<Restaurant> [] results;
        results = new ArrayList[2];
        
        //while there are still nodes to be searched
        while((p = pq.poll()) != null)        
        {
            //look through each of the node's edges
            for(Edge e: p.getEdges())
            {
                //Calculate weight of new edge
                s = e.getSrc();
                d = e.getDst();
                w = s.getBest() + e.getWeight();
                
                
                //If new weight is better, update
                if(w < d.getBest())
                {
                    d.setParent(s);
                    d.setBest(w);
                    pq.resift(d);
                }
                

            }
        }
        
        //Saves the path and alternate paths into arrayLists
        d = destination;
        while(d != null)
        {
            path.add(d.getRest());
            p = d.getParent();
            if(p != null)
            {
                alt = p.getEdges().get(0).getDst();
                if(alt.equals(d))
                    alt = p.getEdges().get(1).getDst();
                alternate.add(alt.getRest());
            }
            d = p;
        }
        results[0] = path;
        results[1] = alternate;
        return results;
        
        
        
    }
}
