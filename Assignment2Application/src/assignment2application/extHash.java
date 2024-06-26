package assignment2application;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
/**
 *
 * @author SarahWong
 * read object
 * write object
 * something to iterate through everything
 * 
 */
public class extHash implements Serializable{
    static int size;
    Directory objects;
    extHash(){
        objects = new Directory();
        size = 0;
    }
    Directory getDirectory(){
        return this.objects;
    }
    
    void add(String key, String value){
        objects.add(key, value);
    }
    boolean contains(String key){
         return objects.contains(key);
    }
    String find(String key){
        return objects.find(key);
    }
    
    TwoStrings [] toArray()
    {
        return objects.toArray();
    }
    
    static final class directoryNode implements Serializable{
        Bucket dump;
        int bits;
        directoryNode(){
        }
        directoryNode(Bucket newb){
            dump = newb;
        }
        void setDump(Bucket prime){
            dump = prime;
        }
        
        
    }static final class Bucket implements Serializable{
        ArrayList<bucketNode> contents;
        int overflows;
       
        Bucket(){ 
            overflows = 1;
            contents = new ArrayList<bucketNode>(0);
        }
        Bucket(int depth){
            overflows = depth;
            contents = new ArrayList<bucketNode>(0);
        }
    }static final class bucketNode implements Serializable{
        String key, value;
        bucketNode(String newkey, String newvalue){
            key = newkey;
            value = newvalue;
        }
        bucketNode(){
            
        }
    }
    static final class Directory implements Serializable{
        ArrayList<directoryNode> addresses;
        int globalDepth;
        int BUCKET_CAP = 64;
        ArrayList<Bucket>allBuckets;
        
        Directory(){
            addresses = new ArrayList<directoryNode>(8);
            globalDepth = 1;
            allBuckets = new ArrayList<Bucket>();
            directoryNode ben = new directoryNode();
            directoryNode bean = new directoryNode();
            addresses.add(ben);
            addresses.add(bean);
            ben.setDump(new Bucket());
            bean.setDump(new Bucket());
            allBuckets.add(ben.dump);
            allBuckets.add(bean.dump);
        }

    void split(directoryNode splitter){
        splitter.dump.overflows++;
        int overflows = splitter.dump.overflows;
        int place = (int)Math.pow(2,overflows);
        Bucket n1 = new Bucket(overflows);
        Bucket n2 = new Bucket(overflows);
        allBuckets.add(n1);
        allBuckets.add(n2);
        int globalPlace;
        directoryNode n;

        
        Bucket old = splitter.dump;
        allBuckets.remove(old);
        ArrayList<directoryNode> splitting = new ArrayList<directoryNode>();
        //is the global depth (how big the directory is) the same as the overflows (how much the local is discriminating?
        
        //the directoryNode's "bits" stores all the bits of the most recent hashcode. We at any given time only care about the rightmost bits, number of which dictated by the depth
        //
            //Things that can happen: 
                //if they match, that means that we don't have anywhere to expand to.
                    //if they can't expand, that means the directory has to change
                    //**increment both the global depth, and this specific bucket's overflows
                    //make a new directory twice the size of the original. 
                    //iterate through the directoryNodes and look at their buckets. If their bucket overflows match the global (which have both been iterated) then 
                    //
                //if global depth is bigger, that means we can re-address directory nodes
                    //this^ would require we check the entire directory for nodes that refer to this bucket

 /*
                    10
                    11
                    
                    
                    */                   
                    
                    
        if(globalDepth == splitter.dump.overflows){
            globalDepth++;
            globalPlace = (int)Math.pow(2,globalDepth);
            splitter.dump = n2;
            for(int count = 0; count < addresses.size(); count++)
            {
                if(addresses.get(count) == splitter)
                {
                    n = new directoryNode();
                    n.bits = splitter.bits + (int)Math.pow(2, globalDepth - 1);
                    n.dump = n1;
                    splitting.add(n);
                }
                else
                {
                    n = new directoryNode();
                    n.bits = addresses.get(count).bits + (int)Math.pow(2, globalDepth - 1);
                    n.dump = addresses.get(count).dump;
                    splitting.add(n);
                }
            }
            
            for(int count = 0; count < splitting.size(); count++)
            {
                addresses.add(splitting.get(count));
            }
            

            //we have nowhere to expand into.(or if you prefer, the buckets and the directory agree how specific they're being)
            //if that happens, then the directory has to become more specific. This will match the 2 new buckets this split creates
            //Make a new directory twice the size it was. 
                //iterate through the newly made directory. Make sure everything has a directory object. 
            //Make 2 new buckets
            //"add" everything in the old bucket into the directory. 
        }else
        {
            //the bucket must become more specific. However, the directory is already more specific, so we will be making 2 more buckets that are 1 bit more specific than the one they're replacing
            //make 2 new buckets, and denote that they are both 1 bit more specific than the originating bucket.
            
            
            for(int count = 0; count < addresses.size(); count++)
                //iterates through the directory objects
            {
                if(old == addresses.get(count).dump)
                    //if this directory node points to the bucket we're trying to split
                {
                    if((place & addresses.get(count).bits) == 0)
                    {
                        addresses.get(count).dump = n1;
                    }
                    else
                    {
                        addresses.get(count).dump = n2;
                    }
                }
                    
            }
        }    
        for(int count = 0; count < old.contents.size(); count++)
        {
            int hash = old.contents.get(count).key.hashCode();
            int bucketAddress = hash & (this.addresses.size() -1);
            this.addresses.get(bucketAddress).dump.contents.add(new bucketNode(old.contents.get(count).key, old.contents.get(count).value));
            this.addresses.get(bucketAddress).bits = bucketAddress;
        }
            
            
            
        
    }
    public void add(String key, String value){
        int hash = key.hashCode();
        size++;
        int bucketAddress = hash & (this.addresses.size() -1);
        if(this.addresses.get(bucketAddress) == null){
            //if the bucket address doesn't have a directory node, add a directory node with a bucket in it
            this.addresses.set(bucketAddress, new directoryNode(new Bucket()));
        }else if(this.addresses.get(bucketAddress).dump == null){
            //if the bucket address has a directory node, but no bucket, then add a bucket to the directory node
            this.addresses.get(bucketAddress).dump = new Bucket();
        }
        
        //if it's not full, just add it
        this.addresses.get(bucketAddress).dump.contents.add(new bucketNode(key, value));
        this.addresses.get(bucketAddress).bits = bucketAddress;
        
        if((this.addresses.get(bucketAddress).dump.contents.size()) > BUCKET_CAP){
            //if we overflow, split. Split will handle increments

            split(this.addresses.get(bucketAddress));

        }


    }
    public boolean contains(String key){
        int hash = key.hashCode();
        int bucketAddress = hash & (this.addresses.size() -1);
        for(int i = 0; i < this.addresses.get(bucketAddress).dump.contents.size(); i++){
            if(this.addresses.get(bucketAddress).dump.contents.get(i).key.equals(key)){
                return true;
            }
        }
        return false;
    }
    public String find(String key){
        int hash = key.hashCode();
        int bucketAddress = hash & (this.addresses.size() -1);
        for(int i = 0; i < this.addresses.get(bucketAddress).dump.contents.size(); i++){
            if(this.addresses.get(bucketAddress).dump.contents.get(i).key.equals(key)){
                return this.addresses.get(bucketAddress).dump.contents.get(i).value;
            }
        }
        throw new NoSuchElementException();
    }
    
    public TwoStrings[] toArray()
    {
        TwoStrings [] stuff = new TwoStrings[size];
        Bucket b;
        int count = 0;
        String k;
        String v;
                
	for (int i = 0; i < allBuckets.size(); ++i) 
        {
            b = allBuckets.get(i);
	    for (int index = 0; index < b.contents.size(); index++) 
            {

                k = b.contents.get(index).key;
                v = b.contents.get(index).value;
                stuff[count] = new TwoStrings(k,v);
                count++;

            }
        }
        return stuff;
    }

    private void writeObject(ObjectOutputStream s) throws Exception {
	s.defaultWriteObject();
	s.writeInt(size);
        Bucket b;
	for (int i = 0; i < allBuckets.size(); ++i) {
            b = allBuckets.get(i);
	    for (int count = 0; count < b.contents.size(); count++) {
		if(b.contents.get(count) != null)
                    s.writeObject(b.contents.get(count));
	    }
	}
    }
    private void readObject(ObjectInputStream s) throws Exception {
	s.defaultReadObject();
	int n = s.readInt();
        bucketNode b;

	for (int i = 0; i < n; ++i)
        {
            b =(bucketNode)s.readObject();
	    add(b.key,b.value);
        }
    }
    
    }
    
    /*
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException
    {
        extHash test = new extHash();
        extHash test2 = new extHash();

        test.add("a", "b");
        test.add("c", "d");
        test.add("e", "f");
        test.add("g", "h");
        
        System.out.println(test.getDirectory().find("a"));
        for(int count = 0; count < 100; count++)
        {
            //System.out.println("Adding " + count);
            test.getDirectory().add("" + count, "6");
        }
        TwoStrings [] arr = test.toArray();
        
        for(int count = 0; count < arr.length; count++)
        {
            System.out.println(arr[count]);
        }
        /*
        FileOutputStream out;
        ObjectOutputStream objOut;
        FileInputStream in;
        ObjectInputStream objIn;
        try
        {

                out = new FileOutputStream("ext.txt");
                objOut = new ObjectOutputStream(out);

                    objOut.writeObject(test);
                objOut.flush();
                objOut.close();
            

            in = new FileInputStream("ext.txt");
            objIn = new ObjectInputStream(in);
            
            test2 = (extHash)objIn.readObject();
            objIn.close();

            
            
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        System.out.println(test2.find("42"));    
        
    }*/
}
