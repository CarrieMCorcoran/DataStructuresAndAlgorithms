package assignment2loader;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author seren
 */
public class PanelsForGUI extends JPanel
{
    private JPanel titlePanel, textBoxPanel, resultsPanel, resultsPanel1,
            resultsPanel2,resultsPanel3,resultsPanel4,resultsPanel5,resultsPanel6,
            resultsPanel7, resultsPanel8, resultsPanel9, resultsPanel10, 
            resultsPanel11, resultsPanel12, resultsPanel13, resultsPanel14, 
            resultsPanel15, resultsPanel16, titlePanel1, titlePanel2, titlePanel3,
            titlePanel4;
    private JLabel lblTitle, lblInstructions, lblExamples, lblSource, lblRest1,
            lblRest2, lblRest3, lblRest4, lblRest5, lblAlt1, lblAlt2, lblAlt3,
            lblAlt4, lblAlt5, lblAlt6, lblAlt, lblRest6, lblRest7, lblAlt7,
            lblDisjointSet;
    private JTextField txtName;
    private JButton btEnter;
    private Listener listen;
    private Recommendation rec;
    
    
    public PanelsForGUI()
    {
        //Creating font objects and listener object
        Font titleFont = new Font("TImes New Roman", Font.BOLD, 30);
        Font textFont = new Font("Times New Roman", Font.PLAIN, 16);
        listen = new Listener();
        
        rec = new Recommendation();
        
        //Setting up panel color and layout
        this.setBackground(Color.white);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        //Setting up first panel containing title and instructions
        titlePanel = new JPanel();
        titlePanel.setBackground(Color.white);
        titlePanel.setPreferredSize(new Dimension(1200,150));
        titlePanel.setLayout(new GridLayout(4,1));
        
        //Setting up labels for first panel
        lblTitle = new JLabel("Restaurant Recommender");
        lblTitle.setForeground(new Color(102,0,153));
        lblTitle.setFont(titleFont);
        lblInstructions = new JLabel("Enter a restaurant name to get a path to that restaurant's set's center");
        lblInstructions.setFont(textFont);
        lblExamples = new JLabel("Try these examples: Tony B's Pizzeria, Roast Coffeehouse and Wine Bar, Elbow Room Bar");
        lblExamples.setFont(textFont);
        lblDisjointSet = new JLabel("Number of disjoint sets: " + rec.numOfDisjointSets());
        lblDisjointSet.setFont(textFont);
        

       
        
        titlePanel1 = new JPanel();
        titlePanel1.setBackground(Color.white);
        titlePanel2 = new JPanel();
        titlePanel2.setBackground(Color.white);
        titlePanel3 = new JPanel();
        titlePanel3.setBackground(Color.white);
        titlePanel4 = new JPanel();
        titlePanel4.setBackground(Color.white);
        
        //Adding labels to panel
        titlePanel1.add(lblTitle);
        titlePanel2.add(lblInstructions);
        titlePanel3.add(lblExamples);
        titlePanel4.add(lblDisjointSet);
        
        titlePanel.add(titlePanel1);
        titlePanel.add(titlePanel2);
        titlePanel.add(titlePanel3);
        titlePanel.add(titlePanel4);
        
        //Adding titlePanel to main panel
        this.add(titlePanel);
        
        //Setting up second panel containing text box
        textBoxPanel = new JPanel();
        textBoxPanel.setBackground(Color.white);
        textBoxPanel.setPreferredSize(new Dimension(1200,100));
        
        //Setting up text box and button
        txtName = new JTextField(20);
        txtName.setFont(textFont);
        btEnter = new JButton("Enter");
        btEnter.setFont(textFont);
             
        txtName.addActionListener(listen);
        btEnter.addActionListener(listen);
        
        //Adding text box and button to panel
        textBoxPanel.add(txtName);
        textBoxPanel.add(btEnter);
        
        //Adding panel to main panel
        this.add(textBoxPanel);
        
        //Creating panel to display results
        resultsPanel = new JPanel();
        resultsPanel.setPreferredSize(new Dimension(1200,300));
        resultsPanel.setBackground(Color.white);
        resultsPanel1 = new JPanel();
        resultsPanel1.setBackground(Color.white);
        resultsPanel2 = new JPanel();
        resultsPanel2.setBackground(Color.white);
        resultsPanel3 = new JPanel();
        resultsPanel3.setBackground(Color.white);
        resultsPanel4 = new JPanel();
        resultsPanel4.setBackground(Color.white);
        resultsPanel5 = new JPanel();
        resultsPanel5.setBackground(Color.white);
        resultsPanel6 = new JPanel();
        resultsPanel6.setBackground(Color.white);
        resultsPanel7 = new JPanel();
        resultsPanel7.setBackground(Color.white);
        resultsPanel8 = new JPanel();
        resultsPanel8.setBackground(Color.white);
        resultsPanel9 = new JPanel();
        resultsPanel9.setBackground(Color.white);
        resultsPanel10 = new JPanel();
        resultsPanel10.setBackground(Color.white);
        resultsPanel11 = new JPanel();
        resultsPanel11.setBackground(Color.white);
        resultsPanel12 = new JPanel();
        resultsPanel12.setBackground(Color.white);
        resultsPanel13 = new JPanel();
        resultsPanel13.setBackground(Color.white);
        resultsPanel14 = new JPanel();
        resultsPanel14.setBackground(Color.white);
        resultsPanel15 = new JPanel();
        resultsPanel15.setBackground(Color.white);
        resultsPanel16 = new JPanel();
        resultsPanel16.setBackground(Color.white);
        
        
        resultsPanel.setLayout(new GridLayout(8,2));
        
        //Creating labels to display results and adding them to panels
        lblSource = new JLabel("");
        lblSource.setFont(textFont);
        resultsPanel1.add(lblSource);
        
        lblAlt = new JLabel("");
        lblAlt.setFont(textFont);
        resultsPanel2.add(lblAlt);
  
        lblRest1 = new JLabel("");
        lblRest1.setFont(textFont);
        resultsPanel3.add(lblRest1);

        lblAlt1 = new JLabel("");
        lblAlt1.setFont(textFont);
        resultsPanel4.add(lblAlt1);     
    
        lblRest2 = new JLabel("");
        lblRest2.setFont(textFont);
        resultsPanel5.add(lblRest2);
        
        lblAlt2 = new JLabel("");
        lblAlt2.setFont(textFont);
        resultsPanel6.add(lblAlt2);
        
        lblRest3 = new JLabel("");
        lblRest3.setFont(textFont);
        resultsPanel7.add(lblRest3);
        
        lblAlt3 = new JLabel("");
        lblAlt3.setFont(textFont);
        resultsPanel8.add(lblAlt3);
        
        lblRest4 = new JLabel("");
        lblRest4.setFont(textFont);
        resultsPanel9.add(lblRest4);
        
        lblAlt4 = new JLabel("");
        lblAlt4.setFont(textFont);
        resultsPanel10.add(lblAlt4);
        
        lblRest5 = new JLabel("");
        lblRest5.setFont(textFont);
        resultsPanel11.add(lblRest5);    
        
        lblAlt5 = new JLabel("");
        lblAlt5.setFont(textFont);
        resultsPanel12.add(lblAlt5);
        
        lblRest6 = new JLabel("");
        lblRest6.setFont(textFont);
        resultsPanel13.add(lblRest6);
        
        lblAlt6 = new JLabel("");
        lblAlt6.setFont(textFont);
        resultsPanel14.add(lblAlt6);
        
        lblRest7 = new JLabel("");
        lblRest7.setFont(textFont);
        resultsPanel15.add(lblRest7);
        
        lblAlt7 = new JLabel("");
        lblAlt7.setFont(textFont);
        resultsPanel16.add(lblAlt7);
        
        
        
        
        

        resultsPanel.add(resultsPanel1);
        resultsPanel.add(resultsPanel2);
        resultsPanel.add(resultsPanel3);
        resultsPanel.add(resultsPanel4);
        resultsPanel.add(resultsPanel5);
        resultsPanel.add(resultsPanel6);
        resultsPanel.add(resultsPanel7);
        resultsPanel.add(resultsPanel8);
        resultsPanel.add(resultsPanel9);
        resultsPanel.add(resultsPanel10);
        resultsPanel.add(resultsPanel11);
        resultsPanel.add(resultsPanel12);
        resultsPanel.add(resultsPanel13);
        resultsPanel.add(resultsPanel14);
        resultsPanel.add(resultsPanel15);
        resultsPanel.add(resultsPanel16);
        
        
        //Adding results panel to main panel
        this.add(resultsPanel);
        
    }
    
    private class Listener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            
            ArrayList<Restaurant> [] results;
            ArrayList<Restaurant> path;
            ArrayList<Restaurant> alt;
            String ID;
            String [] mainPath = new String[8];
            String [] altPath = new String[7];
            String text = txtName.getText();
            Restaurant rest;
            
            if(rec.checkName(text))
            {   
                ID = rec.getID(text);

                
                results = rec.findShortestPath(ID);
                path = results[0];
                alt = results[1];
                if(path.size() <= 8)
                {
                    for(int count = 0; count < 8; count++)
                    {
                        if(count < path.size() - 1)
                        {
                            rest = path.get(count);
                            mainPath[count] = rest.getName() + ", " + rest.getStreetName()
                                    + " " + rest.getCity() + " " + rest.getState();
                        }
                        else if (count == path.size() - 1)
                        {
                            rest = path.get(count);
                            mainPath[count] = "Destination: " + rest.getName() + ", " + rest.getStreetName()
                                    + " " + rest.getCity() + " " + rest.getState();
                        }
                        else
                        {
                            mainPath[count] = "";
                        }
                    }

                    for(int count = 0; count < 7; count++)
                    {
                        if(count < alt.size())
                        {
                            rest = alt.get(count);
                            altPath[count] = rest.getName() + ", " + rest.getStreetName()
                                    + " " + rest.getCity() + " " + rest.getState();
                        }
                        else
                        {
                            altPath[count] = "";
                        }
                    }
                }
                else
                {
                    for(int count = 0; count < 6; count++)
                    {

                        rest = path.get(count);
                        mainPath[count] = rest.getName() + ", " + rest.getStreetName()
                                + " " + rest.getCity() + " " + rest.getState();

                    }
                    mainPath[6] = "...";
                    rest = path.get(path.size()- 1);
                    mainPath[7] = rest.getName() + ", " + rest.getStreetName()
                                + " " + rest.getCity() + " " + rest.getState();
                    
                    for(int count = 0; count < 5; count++)
                    {

                        rest = alt.get(count);
                        altPath[count] = "Destination: " + rest.getName() + ", " + rest.getStreetName()
                                + " " + rest.getCity() + " " + rest.getState();

                    }
                    altPath[5] = "...";
                    rest = alt.get(alt.size()-1);
                    altPath[6] = rest.getName() + ", " + rest.getStreetName()
                                + " " + rest.getCity() + " " + rest.getState();
                    
                }
                lblSource.setText(mainPath[0]);
                lblRest1.setText(mainPath[1]);
                lblRest2.setText(mainPath[2]);
                lblRest3.setText(mainPath[3]);
                lblRest4.setText(mainPath[4]);
                lblRest5.setText(mainPath[5]);
                lblRest6.setText(mainPath[6]);
                lblRest7.setText(mainPath[7]);
                lblAlt.setText("Alternate Steps: ");
                lblAlt1.setText(altPath[0]);
                lblAlt2.setText(altPath[1]);
                lblAlt3.setText(altPath[2]);
                lblAlt4.setText(altPath[3]);
                lblAlt5.setText(altPath[4]);
                lblAlt6.setText(altPath[5]);
                lblAlt7.setText(altPath[6]);
                
                
            }
            else
            {
                lblSource.setText("Sorry, that's not in our system.");
                lblRest1.setText("");
                lblAlt.setText("");
                lblRest2.setText("");
                lblRest3.setText("");
                lblRest4.setText("");
                lblRest5.setText("");
                lblRest6.setText("");
                lblRest7.setText("");
                lblAlt1.setText("");
                lblAlt2.setText("");
                lblAlt3.setText("");
                lblAlt4.setText("");
                lblAlt5.setText("");
                lblAlt6.setText("");
                lblAlt7.setText("");
            }

            
        }
        
    }
            
}
