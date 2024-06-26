package assignment2loader;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFrame;
import org.w3c.dom.Text;

/**
 *
 * @author Carrie
 */
public class Assignment3
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException
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
        
        
        //Recommendation rec = new Recommendation();
        //rec.findShortestPath("ri7LOsk5W_xGVfxhXFHY5Q");
        
        
        /*
        rec.createClusters();
        
        HTObjects test = new HTObjects();
        test.add("this", "that");
        System.out.println(test.findObject("this"));
        /*
        EHTObjects test = new EHTObjects();
        
        test.add("a", "b");
        test.add("c", "d");
        test.add("e", "f");
        test.add("g", "h");
        for(int count = 0; count < 10000; count++)
        {
            //System.out.println("Adding " + count);
            test.add("" + count, "6");
        }
        
        System.out.println(test.find("42"));
*/
                
                
    }
    
}