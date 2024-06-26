/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assignment2application;

import javax.swing.JFrame;

/**
 *
 * @author Carrie
 */
public class Assignment2Application
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
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
