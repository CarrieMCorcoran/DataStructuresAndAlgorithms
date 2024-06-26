package assignment2application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Carrie
 */
public class Recommendation
{
    private HTRestaurants restaurantTable;
    //private FrequencyTableStrings wordsFromAllReviews;
    private HTObjects restaurantNameList;
    private int reviewCount;
    private HTObjects [] restaurantClusters;
    private String [] medoids;
    private Restaurant [] restMedoids;
    private extHash allFiles;
    private HTStrings clusterFiles [];
    
    private final int NUM_CLUSTERS = 10;
    
    public Recommendation()
    {
        restaurantTable = new HTRestaurants();
        restaurantNameList = new HTObjects();
        //wordsFromAllReviews = new FrequencyTableStrings();
        reviewCount = 0;
        
        fillRestaurantTables();
        fillReviewTables();
        
        allFiles = new extHash();
        clusterFiles = new HTStrings[10];
        for(int count = 0; count < 10; count++)
            clusterFiles[count] = new HTStrings();
        loadEHT();
        loadClusters();

    }
    
    public boolean checkName(String name)
    {
        boolean present = false;
        if(allFiles.contains(name))
            present = true;
        return present;
    }
    
    public String getID(String name)
    {
        FileInputStream in;
        ObjectInputStream objIn;
        String fileName = allFiles.find(name);
        String ID = "";
        Restaurant rest;
        try
        {
            in = new FileInputStream(fileName);
            objIn = new ObjectInputStream(in);
            rest = (Restaurant) objIn.readObject();
            objIn.close();
            ID = rest.getId();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
        return ID;
    }
    public void createFiles()
    {
        restaurantTable.createFiles();        
    }
    
    public String [] kMedoids()
    {
        String [] results = new String [NUM_CLUSTERS];
        Restaurant selection;
        Restaurant [] medoids = new Restaurant[NUM_CLUSTERS];
        Restaurant [] newMedoids = new Restaurant[NUM_CLUSTERS];
        Restaurant [] allRestaurants = restaurantTable.toArray();
        Random rand = new Random();
        ArrayList<Restaurant>[] clusters = new ArrayList[NUM_CLUSTERS];
        double minDistance;
        double newDistance;
        double totalDistance = 0;
        int cluster;
        int which;
        boolean duplicate;


        //Finding original k-Medoids and clusters
        for(int count = 0; count < NUM_CLUSTERS; count++)
        {
            //Create each cluster in the array
            clusters[count] = new ArrayList<>();
            
            //Do while loop to check there aren't duplicate restaurants in selection (this is unlikely but possible)
            do
            {
                duplicate = false;

                //Select a random restaurant from the array
                selection = allRestaurants[rand.nextInt(allRestaurants.length)];

                for(int index = 0; index < count; index++)
                {
                    if(selection == medoids[index])
                        duplicate = true;
                }

            }while(duplicate);

            //If the restaurant isn't a duplicate, put it in the array
            medoids[count] = selection;        
        }

        //For each restaurant, find its distance to each k-Medoid and put it in the cluster with the smallest distance
        for(int count = 0; count < allRestaurants.length; count++)
        {
            minDistance = distance(medoids[0], allRestaurants[count]);
            cluster = 0;

            for(int index = 1; index < NUM_CLUSTERS; index++)
            {
                newDistance = distance(medoids[index],allRestaurants[count]);
                if(newDistance < minDistance)
                {
                    minDistance = newDistance;
                    cluster = index;
                }
            }

            clusters[cluster].add(allRestaurants[count]);
            
            //Add minDistance to total
            totalDistance += minDistance;
        }

        
        System.out.println("Total Distance: " + totalDistance + "\nOriginal medoids:");
        for(int count = 0; count < medoids.length; count++)
        {
            System.out.println(medoids[count]);
        }
        
        //Cycle through this process 10 times to get the minimum distance
        for(int cycle = 0; cycle < 10; cycle++)
        {
            newDistance = 0;
            
            //Get new k-medoids out of the original clusters
            for(int count = 0; count < NUM_CLUSTERS; count++)
            {
                which = rand.nextInt(clusters[count].size());
                newMedoids[count] = clusters[count].get(which);
            
                clusters[count].clear();
                
            }
            
            //For each restaurant, find its distance to each k-Medoid and put it in the cluster with the smallest distance
            for(int count = 0; count < allRestaurants.length; count++)
            {
                minDistance = distance(medoids[0], allRestaurants[count]);
                cluster = 0;

                for(int index = 1; index < NUM_CLUSTERS; index++)
                {
                    newDistance = distance(medoids[index],allRestaurants[count]);
                    if(newDistance < minDistance)
                    {
                        minDistance = newDistance;
                        cluster = index;
                    }
                }

                clusters[cluster].add(allRestaurants[count]);
                newDistance += minDistance;
            }

            //If the new max distance is smaller than the old, the new k-medoids are better
            if(newDistance < totalDistance)
            {
                totalDistance = newDistance;
                for(int index = 0; index < NUM_CLUSTERS; index++)
                {
                    medoids[index] = newMedoids[index];
                }
            }
            
            System.out.println("Total Distance: " + totalDistance);            
        }
        
        System.out.println("Final Distance: " + totalDistance + "\nClusters:");
        
        for(int count = 0; count < medoids.length; count++)
        {
            System.out.println(medoids[count]);
            results[count] = medoids[count].getId();
        }
        
        
        
        return results;
    }
    public void saveKMedoids()
    {
        String [] medoids;
        int again = 0;
        Scanner scan = new Scanner(System.in);
        String [] newMedoids = new String [10];
        do
        {
            medoids = kMedoids();
            System.out.println("Press 0 for a new set or 1 to save this set");
            again = scan.nextInt();
        }while(again == 0);
        
        FileOutputStream out;
        ObjectOutputStream objOut;
        FileInputStream in;
        ObjectInputStream objIn;

        
        try
        {

                out = new FileOutputStream("medoids.txt");
                objOut = new ObjectOutputStream(out);
                for(int count = 0; count < 10; count++)
                    objOut.writeObject(medoids[count]);
                objOut.flush();
                objOut.close();
            
/*
            in = new FileInputStream("medoids.txt");
            objIn = new ObjectInputStream(in);
            for(int count = 0; count < 10; count++)
            {
                newMedoids[count] =(String)objIn.readObject();
                System.out.println(newMedoids[count]);
            }
            objIn.close();
*/
            
            
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void createMainHash()
    {
        FileOutputStream out;
        ObjectOutputStream objOut;
        FileInputStream in;
        ObjectInputStream objIn;
        Restaurant rest;
        
        try
        {
            for(int index = 0; index < 10000; index++)
            {
                in = new FileInputStream("restaurant" + index + ".txt");
                objIn = new ObjectInputStream(in);
                rest = (Restaurant) objIn.readObject();
                objIn.close();

                allFiles.add(rest.getName(), "restaurant" + index + ".txt");

            }
                out = new FileOutputStream("ext.txt");
                objOut = new ObjectOutputStream(out);

                objOut.writeObject(allFiles);
                objOut.flush();
                objOut.close();
            

            in = new FileInputStream("ext.txt");
            objIn = new ObjectInputStream(in);
            
            allFiles = (extHash)objIn.readObject();
            objIn.close();

            System.out.println(allFiles.find("Tony B's Pizzeria"));
            
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } 
    }
    
    public void loadEHT()
    {
        FileInputStream in;
        ObjectInputStream objIn;         
        try
        {
            in = new FileInputStream("ext.txt");
            objIn = new ObjectInputStream(in);
            allFiles = (extHash)objIn.readObject();
            objIn.close();
            System.out.println(allFiles.contains("Tony B's Pizzeria"));
            
        }catch(Exception e)
        {
            System.out.println(e);
        }
    }
            
    
    public void loadClusters()
    {
        medoids = new String[10];
        restMedoids = new Restaurant[10];
        try
        {
            FileInputStream in;
            ObjectInputStream objIn;            
        
            in = new FileInputStream("medoids.txt");
            objIn = new ObjectInputStream(in);
            for(int count = 0; count < 10; count++)
            {
                medoids[count] = (String)objIn.readObject();
                restMedoids[count] = restaurantTable.findObject(medoids[count]);
            }
            objIn.close();
            
            for(int count = 0; count < 10; count++)
            {
                in = new FileInputStream("cluster"+count+".txt");
                objIn = new ObjectInputStream(in);
                clusterFiles[count] = (HTStrings)objIn.readObject();
            objIn.close();
            }
            
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
    }
    
    public void createClusters()
    {
        FileInputStream in;
        ObjectInputStream objIn;
        FileOutputStream out;
        ObjectOutputStream objOut;
        medoids = new String[10];
        restMedoids = new Restaurant[10];
        Restaurant rest;
        double distance;
        int whichCluster;
        double bestClusterDistance;

        

        try
        {
            
        
            in = new FileInputStream("medoids.txt");
            objIn = new ObjectInputStream(in);
            for(int count = 0; count < 10; count++)
            {
                medoids[count] = (String)objIn.readObject();
                restMedoids[count] = restaurantTable.findObject(medoids[count]);
            }
            objIn.close();




            for(int index = 0; index < 10000; index++)
            {
                in = new FileInputStream("restaurant" + index + ".txt");
                objIn = new ObjectInputStream(in);
                rest = (Restaurant) objIn.readObject();
                objIn.close();

                whichCluster = 0;
                bestClusterDistance = distance(rest, restMedoids[0]);

                for(int count = 1; count < 10; count++)
                {
                    distance = distance(rest, restMedoids[count]);
                    if (distance < bestClusterDistance)
                        whichCluster = count;
                }

                clusterFiles[whichCluster].add("restaurant" + index + ".txt");


            }
            
            for(int count = 0; count < 10; count++)
            {
                out = new FileOutputStream("cluster" + count + ".txt");
                objOut = new ObjectOutputStream(out);

                objOut.writeObject(clusterFiles[count]);
                objOut.flush();
                objOut.close();                
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
          
    }
    
    public Restaurant nearestMed(String ID)
    {
        int found = -1;
        String fileName = allFiles.find(ID);
        
        for(int count = 0; count < 10 && found == -1; count++)
        {
            if(clusterFiles[count].contains(fileName))
                found = count;
        }
        
        return restMedoids[found];
    }
    
    public int whichCluster(String ID)
    {
        int found = -1;
        String fileName = allFiles.find(ID);  
        
        for(int count = 0; count < 10 && found == -1; count++)
        {
            if(clusterFiles[count].contains(fileName))
                found = count;
        }
        
        return found;
    }

    public void testCode()
    {

        FileInputStream in;
        ObjectInputStream objIn;
        Restaurant rest;
        
        try
        {
            
                in = new FileInputStream("restaurant1.txt");
                objIn = new ObjectInputStream(in);
                rest = (Restaurant) objIn.readObject();
                objIn.close();

                System.out.println(rest);

            
            
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } 
        
        /*
        Restaurant test;
        
        //Quick test code- checks if a restaurant matches key I pulled from the file and prints restaurant data
        if(restaurantTable.contains("psD0qpCK-iENFO9c8hmnkw"))
           System.out.println(restaurantTable.findObject("psD0qpCK-iENFO9c8hmnkw"));
        
        //Tests restaurant name list by matching the name to the approriate key
        System.out.println(restaurantNameList.findObject("Famous Crab Cakes"));
        
        
        //Tests frequency table code by printing out the table that corresponds to this id
        test = (Restaurant)restaurantTable.findObject("psD0qpCK-iENFO9c8hmnkw");
        
        test.printReviewTable();
        

        
        for(int count = 0; count < 10; count++)
        {
            System.out.println(testArray[count]);
        }*/
        
        //System.out.println(getID("Tony B's Pizzeria"));
        

    }
    public void testCode2(){
        Restaurant test;
        Restaurant [] results;
        
        if(restaurantTable.contains("Spq-2DVE8nilFhSFk0jLug"))
        {
        //verifying the table contains the example.
        //I chose a pizza place because there are plenty of examples of pizza places, so there SHOULD be similar
        
        
            //print the similarRestaurants array
            Restaurant target = (Restaurant) restaurantTable.findObject("Spq-2DVE8nilFhSFk0jLug");
            System.out.println(target);
            results = similarRestaurants("Spq-2DVE8nilFhSFk0jLug");
            System.out.println(results[0]);
            System.out.println(results[1]);
                    
            //System.out.println(restaurantTable.findObject(similarRestaurants(target)[0].getId()));// find the restaurant in the table that is number 1 on the similar restaurants of the target
            //System.out.println(restaurantTable.findObject(similarRestaurants(target)[1].getId()));
        }
        /*
        if(restaurantTable.contains("ByEfpxv69HFqBYU3_r7HtA"))
            System.out.println(restaurantTable.findObject("ByEfpxv69HFqBYU3_r7HtA"));
        //verifying the table contains the example.
        //I chose a pizza place because there are plenty of examples of pizza places, so there SHOULD be similar
        
        
            //print the similarRestaurants array
            Restaurant target = restaurantTable.findObject("ByEfpxv69HFqBYU3_r7HtA");
            Restaurant[] results = similarRestaurants(target);
            for(int count = 0; count < 2; count++)
            {
                System.out.println(results[count]);
            }
        */
    }
    
    public void fillReviewTables()
    {
        Scanner scan;
        String id;
        String reviewText;
        Restaurant rest;
        String [] words;
        
        try
        {
            //Opens up file for reading using ; to know when term stops/starts
            scan = new Scanner(new File("Yelp_First_30000_Reviews_Sanitized.csv"));
            scan.useDelimiter(";");
            
            //Skips first line since it's a table header
            scan.nextLine();
            
            while(scan.hasNext())
            {
                //Skipping over parts of the data we're not using
                scan.next();
                scan.next();
                
                //Grab business id
                id = scan.next();
                
                //if this business is in our system
                if(restaurantTable.contains(id))
                {
                    //Finds corresponding Restaurant object
                    rest = (Restaurant)restaurantTable.findObject(id);
                    
                    //Skipping over more stuff we don't need
                    for(int x = 0; x < 4; x++)
                        scan.next();
                    
                    //Grabs review text and converts it all to lowercase
                    reviewText = scan.next().toLowerCase();
                    
                    
                    //Checks for and removes quotation marks around review text
                    if(reviewText.charAt(0) == '"')
                        reviewText = reviewText.substring(1);
                    
                    if(reviewText.charAt(reviewText.length() - 1) == '"')
                        reviewText = reviewText.substring(0, reviewText.length() - 1);

                    //Splits string into array of words
                    words = reviewText.split(" ");
                    
                    
                    //Iterating over each word in array
                    for(int x = 0; x < words.length; x++)
                    {
                        //This prevents words of length 0 caused by multiple spaces                    
                        if(words[x].length() > 0)
                        {
                            //Put word in appropriate tables
                            rest.addReviewWord(words[x]);
                            //wordsFromAllReviews.add(words[x]);
                        }
                    }
                    
                    //Add to counts
                    rest.addReviewCount();
                    reviewCount++;
                    
                }
                
                
                
                
                scan.nextLine();
            }
            
            
            //Always close the file
            scan.close();
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void fillRestaurantTables()
    {
        Scanner scan;
        String id;
        String name;
        String address;
        String city;
        String state;
        String zipCode;
        double latitude;
        double longitude;
        String categories;
        String [] categoryArray;
        HTStrings categoryTable;
        Restaurant rest;
        RestaurantName rn;
        
        
        try
        {
            //Opens up file for reading using ; to know when term stops/starts
            scan = new Scanner(new File("Yelp_First_30000_Businesses.csv"));
            scan.useDelimiter(";");

            //Skips first since since it's a table header
            scan.nextLine();
            
            //While there are lines left in the file and we don't have all the restaurants we need
            while(scan.hasNext() && restaurantTable.count() < 10000)
            {
                //Grabs each term and puts it in correct variable
                id = scan.next();
                name = scan.next();
                address = scan.next();
                city = scan.next();
                state = scan.next();
                zipCode = scan.next();
                latitude = Double.parseDouble(scan.next());
                longitude = Double.parseDouble(scan.next());
                
                //Skips terms we're not using
                for(int x = 0; x < 4; x++)
                    scan.next();
            
                //Grabs category lists and converts it to lowercase
                categories = scan.next().toLowerCase();
                
                //Checks if business is a restaurant (some restaurants are not labeled as such but have "food" category
                if(categories.contains("restaurant") || categories.contains("food"))
                {
                    //Splits categories into array and loads each term in array into an HT for categories
                    categoryArray = categories.split(", ");
                    categoryTable = new HTStrings();
                    for(int index = 0; index < categoryArray.length; index++)
                    {
                        categoryTable.add(categoryArray[index]);
                    }

                    //Creates restaurant object and puts it in HT for objects
                    rest = new Restaurant(id, name, address, city, state, zipCode, latitude, longitude, categoryTable);
                    restaurantTable.add(id,rest);
                    
                    //Creates restaurantName object and puts it in HT for name lookup
                    rn = new RestaurantName(name, id);
                    restaurantNameList.add(name,rn);
                }
                scan.nextLine();
            }
            
            //Carrie testing stuff- remove later
            
            //restaurantTable.printAll();

            /*

*/          
            //Always close your files
            scan.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }        
    }
        //compare the frequency of one word in one restraunt review to that in another given restaurant
        //^iterate through all the words to get a measure of similar words. Produce a number
        //^iterate through all the restaurants with shared categories. As you iterate, store the 3 most similar. 
    int frequencySimilarity(Restaurant a, Restaurant b){
        //find the similarity between 2 restaurants
        int difference = 1; // in strings, comparison is counted in distance, not degree of similarity. This'll be a game of golf
        int cursor;
        for(int i = 0; i < a.getReviewWordsTable().toArray().length; i++){ //for as long athere are words in the review 
            b.getReviewWordCount(a.getReviewWordsTable().toArray()[i]);//this returns the number of times b has this word
            cursor = Math.abs((b.getReviewWordCount(a.getReviewWordsTable().toArray()[i]) - a.getReviewWordCount(a.getReviewWordsTable().toArray()[i])));
            difference = difference + cursor;
        }
        return difference;
    }
    double categorySimilarity(Restaurant a, Restaurant b){
        int similarity = 0;
        for(int i = 0; i < a.getCategories().length; i++){ //for a, getCategories
            if(b.containsCategory(a.getCategories()[i])){ //see if it's in b
                similarity++;//if so, then increase the similarity
            }
        }
        return (double)similarity/a.getCategories().length;
    }
    public double distance(Restaurant a, Restaurant b)
    {
        double latitude1 = a.getLatitude(),
                longitude1 = a.getLongitude(),
                latitude2 = b.getLatitude(),
                longitude2 = b.getLongitude();
        double dist, latitude, longitude, x;
        final double RADIUS = 6371;
        
        //distance between latitudes an longitudes
        latitude = Math.toRadians(latitude2 - latitude1);
        longitude = Math.toRadians(longitude2 - longitude1);
        
        //convert latitudes to radians
        latitude1 = Math.toRadians(latitude1);
        latitude2 = Math.toRadians(latitude2);
        
	//apply formulae
        x = Math.pow(Math.sin(latitude / 2), 2) +
                   Math.pow(Math.sin(longitude / 2), 2) *
                   Math.cos(latitude1) *
                   Math.cos(latitude2);
        
        x = 2 * Math.asin(Math.sqrt(x));
        
        dist = RADIUS * x;
        return dist;
    }
    
   Restaurant[] similarRestaurants(String name){
       
       String fileName = allFiles.find(name);
       FileInputStream in;
        ObjectInputStream objIn;            
        Restaurant target;
        int cluster;
        Restaurant compare;
        String [] arr;    
        Restaurant reccomendations[] = new Restaurant[2];
       try
       {
            in = new FileInputStream(fileName);
            objIn = new ObjectInputStream(in);
            target = (Restaurant)objIn.readObject();

            objIn.close();
            cluster = whichCluster(name);
            arr = clusterFiles[cluster].toArray();
            
            boolean added = false;
            //simetstore is similarity metric of the stored, simetadd is similarity metric of that which is being added
            double simetstore, simetadd;
            int count = 0;
            //categorical similarity, frequency similarity, distance similarity
            double catsimstore, catsimadd, freqsimstore, freqsimadd, disimstore, disimadd;

            //make an array for keeping the 2 most similar restaurants
            for(int i = 0; i < arr.length; i++){  //iterating through the restaurants
            {
                in = new FileInputStream(arr[i]);
                objIn = new ObjectInputStream(in);
                compare = (Restaurant)objIn.readObject();

                objIn.close();
                if(!(target.getId().equals(compare.getId())))
                {   
                    

                    if(count == 0){
                        reccomendations[0] = compare;//if the thing is empty, just put it in.
                        count++;
                    }else if (count == 1)
                    {
                            //variable store for categotical similarities
                            catsimstore = categorySimilarity(target, reccomendations[0]);
                            catsimadd = categorySimilarity(target, compare);
                            //variable store for frequency similarities
                            freqsimstore = 1/ frequencySimilarity(target, reccomendations[0]);
                            freqsimadd = 1/ frequencySimilarity(target, compare);
                            //variable store for distance similarities
                            disimstore = 1/ distance(target, reccomendations[0]);
                            disimadd = 1/ distance(target, (compare));

                            //similarity metric is the category similarity * the inverse of frequency similarity, * the inverse of distance
                            simetstore = (catsimstore * freqsimstore * disimstore);
                            simetadd = (catsimadd * freqsimadd * disimadd);
                            //if the stored one is should be replaced
                            if((simetstore < simetadd)){
                                reccomendations[1] = reccomendations[0];
                                reccomendations[0] = compare;
                            }
                            else
                            {
                                reccomendations[1] = compare;
                            }
                            count++;
                    }
                    else
                    {
                        added = false;
                        for(int j = 0; j < 2 && !added; j++){
                            //variable store for categotical similarities
                            catsimstore = categorySimilarity(target, reccomendations[j]);
                            catsimadd = categorySimilarity(target, compare);
                            //variable store for frequency similarities
                            freqsimstore = 1/ frequencySimilarity(target, reccomendations[j]);
                            freqsimadd = 1/ frequencySimilarity(target, compare);
                            //variable store for distance similarities
                            disimstore = 1/ distance(target, reccomendations[j]);
                            disimadd = 1/ distance(target, (compare));

                            //similarity metric is the category similarity * the inverse of frequency similarity, * the inverse of distance
                            simetstore = (catsimstore + freqsimstore + disimstore);
                            simetadd = (catsimadd + freqsimadd + disimadd);
                            //if the stored one is should be replaced
                            if((simetstore < simetadd)){
                                added = true;
                                if(j == 0)
                                {
                                    reccomendations[1] = reccomendations[0];
                                    reccomendations[0] = compare;

                                }
                                else
                                {
                                    reccomendations[1] = compare;
                                }


                            }

                        }
                    }
                }
            }
            
        }
       }
        catch(Exception e)
        {
        System.out.println(e);
        e.printStackTrace();
        }
        return reccomendations;
    }
}
