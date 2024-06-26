package assignment2loader;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Carrie
 */
public class TwoStrings
{
    private String str1;
    private String str2;

    public TwoStrings(String str1, String str2)
    {
        this.str1 = str1;
        this.str2 = str2;
    }

    public String getStr1()
    {
        return str1;
    }

    public void setStr1(String str1)
    {
        this.str1 = str1;
    }

    public String getStr2()
    {
        return str2;
    }

    public void setStr2(String str2)
    {
        this.str2 = str2;
    }

    @Override
    public String toString()
    {
        return "TwoStrings{" + "str1=" + str1 + ", str2=" + str2 + '}';
    }
    
    
}
