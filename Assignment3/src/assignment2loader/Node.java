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
public class Node implements Comparable <Node>
{
    private ArrayList <Edge> edges;
    private Restaurant rest;
    private double best;
    private int pqIndex;
    private Node parent;

    public Node(Restaurant r)
    {
        rest = r;
        edges = new ArrayList<Edge>();
        parent = null;
        
    }

    public Node getParent()
    {
        return parent;
    }

    public void setParent(Node parent)
    {
        this.parent = parent;
    }

    public int getPqIndex()
    {
        return pqIndex;
    }

    public void setPqIndex(int pqIndex)
    {
        this.pqIndex = pqIndex;
    }
    
    

    public void addEdge (Node n, double weight)
    {
        Edge e = new Edge(this, n, weight);
        edges.add(e);
    }

    public ArrayList <Edge> getEdges()
    {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges)
    {
        this.edges = edges;
    }

    public Restaurant getRest()
    {
        return rest;
    }

    public void setRest(Restaurant rest)
    {
        this.rest = rest;
    }

    public double getBest()
    {
        return best;
    }

    public void setBest(double best)
    {
        this.best = best;
    }
    
    @Override
    public int compareTo(Node o)
    {
        return Double.compare(best, o.best);
    }

    
}
