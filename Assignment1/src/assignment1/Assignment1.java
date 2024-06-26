/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assignment1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Carrie
 */
public class Assignment1
{


    
    public static void main(String[] args)
    {
        //Recommendation rec = new Recommendation();
        
        //rec.testCode();
        
        
        
        //GUI Code- commented out for ease of working on recommendation stuff
        
        
        JFrame frame;
        PanelsForGUI panel = new PanelsForGUI();
        
        //Creates frame (the window the GUI appears in
        frame = new JFrame("Restaurant Recommender");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        

        
        frame.setContentPane(panel);
        
        //Tells the frame to appear- always has to be last
        frame.pack();
        frame.setVisible(true);
        

    }
    
   
    

    
}
