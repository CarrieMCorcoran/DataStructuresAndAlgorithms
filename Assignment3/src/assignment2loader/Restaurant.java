
package assignment2loader;

/*
 PLEASE DO NOT CHANGE THIS CODE.  CHANGING THIS CODE WILL BREAK ALL THE SERIALIZABLE STUFF AND ALSO MAKE CARRIE CRY.  
TRUST ME THIS HAS BEEN TESTED
 */
public class Restaurant implements java.io.Serializable
{
    private String id;
    private String name;
    private String streetName;
    private String city;
    private String state;
    private String zipCode;
    private double longitude;
    private double latitude;
    private int numReviews;
    private HTStrings categories;
    private FrequencyTableStrings reviewWords;

    public Restaurant(String id, String name, String streetName, String city, String state, String zipCode, double longitude, double latitude, HTStrings categories)
    {
        this.id = id;
        this.name = name;
        this.streetName = streetName;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.longitude = longitude;
        this.latitude = latitude;
        this.categories = categories;
        reviewWords = new FrequencyTableStrings();
        
        numReviews = 0;
    }
    

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getStreetName()
    {
        return streetName;
    }

    public void setStreetName(String streetName)
    {
        this.streetName = streetName;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    //Update once HT method to convert to array is done
    public String[] getCategories()
    {
        String[] categoryArray = categories.toArray();
        
        return categoryArray;
    }
    
    public FrequencyTableStrings getReviewWordsTable()
    {
        return reviewWords;
    }
    
    public void addReviewWord(String word)
    {
        reviewWords.add(word);
    }
    
    public int getReviewWordCount(String word)
    {
        return reviewWords.getCount(word);
    }
    public boolean containsCategory(String target){
        this.getCategories();
        for(int i = 0; i > this.getCategories().length; i++){
            if(target.equals(this.getCategories()[i])){
                return true;
            }
        }
        return false;
    }
    public void addReviewCount()
    {
        numReviews++;
    }
    
    public void printReviewTable()
    {
        System.out.println(reviewWords.toString());
    }
    
    
    
    @Override
    public String toString()
    {
        return "Restaurant{" + "id=" + id + ", name=" + name + ", streetName=" + streetName + ", city=" + city + ", state=" + state + ", zipCode=" + zipCode + ", longitude=" + longitude + ", latitude=" + latitude + ", NumReviews=" + numReviews + ", categories=" + categories + '}';
    }
    
    
}
