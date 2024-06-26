/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2loader;

/**
 *
 * @author Carrie
 */
public class Edge implements Comparable<Edge>
{
    private Node src;
    private Node dst;
    private double weight;

    public Edge(Node s, Node d, double w)
    {
        src = s;
        dst = d;
        weight = w;
    }

    public Node getSrc()
    {
        return src;
    }

    public void setSrc(Node src)
    {
        this.src = src;
    }

    public Node getDst()
    {
        return dst;
    }

    public void setDst(Node dst)
    {
        this.dst = dst;
    }

    public double getWeight()
    {
        return weight;
    }

    public void setWeight(double weight)
    {
        this.weight = weight;
    }
    
    
    
    @Override
    public int compareTo(Edge o)
    {
        return Double.compare(weight, o.weight);
    }

}
