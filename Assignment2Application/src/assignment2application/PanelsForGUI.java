package assignment2application;

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
            resultsPanel2,resultsPanel3,resultsPanel4,resultsPanel5,resultsPanel6;
    private JLabel lblTitle, lblInstructions, lblExamples, lblRest1Name,
            lblRest1Address, lblRest2Name,lblRest2Address,
                    lblCluster,lblKMed,
            lblRest3Name,lblRest3Address;
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
        this.setLayout(new GridLayout(3,1));
        
        //Setting up first panel containing title and instructions
        titlePanel = new JPanel();
        titlePanel.setBackground(Color.white);
        titlePanel.setPreferredSize(new Dimension(600,150));
        
        //Setting up labels for first panel
        lblTitle = new JLabel("Restaurant Recommender");
        lblTitle.setForeground(new Color(102,0,153));
        lblTitle.setFont(titleFont);
        lblInstructions = new JLabel("Enter a restaurant name to get a recommendation of 2 similar restaurants and the cluster information.");
        lblInstructions.setFont(textFont);
        lblExamples = new JLabel("Try these examples: Tony B's Pizzeria, Super Sushi Kyo Hin, Bar-B-Cutie");
        lblExamples.setFont(textFont);
        
        //Adding labels to panel
        titlePanel.add(lblTitle);
        titlePanel.add(lblInstructions);
        titlePanel.add(lblExamples);
       
        //Adding titlePanel to main panel
        this.add(titlePanel);
        
        //Setting up second panel containing text box
        textBoxPanel = new JPanel();
        textBoxPanel.setBackground(Color.white);
        textBoxPanel.setPreferredSize(new Dimension(600,200));
        
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
        
        resultsPanel.setLayout(new GridLayout(6,1));
        
        //Creating labels to display results and adding them to panels
        lblRest1Name = new JLabel("");
        lblRest1Name.setFont(textFont);
        resultsPanel1.add(lblRest1Name);
        
        lblRest1Address = new JLabel("");
        lblRest1Address.setFont(textFont);
        resultsPanel2.add(lblRest1Address);
        
  
        lblRest2Name = new JLabel("");
        lblRest2Name.setFont(textFont);
        resultsPanel3.add(lblRest2Name);

        lblRest2Address = new JLabel("");
        lblRest2Address.setFont(textFont);
        resultsPanel4.add(lblRest2Address);     
    
        lblCluster = new JLabel("");
        lblCluster.setFont(textFont);
        resultsPanel5.add(lblCluster);
        
        lblKMed = new JLabel("");
        lblKMed.setFont(textFont);
        resultsPanel6.add(lblKMed);

        resultsPanel.add(resultsPanel1);
        resultsPanel.add(resultsPanel2);
        resultsPanel.add(resultsPanel3);
        resultsPanel.add(resultsPanel4);
        resultsPanel.add(resultsPanel5);
        resultsPanel.add(resultsPanel6);
        
        //Adding results panel to main panel
        this.add(resultsPanel);
        
    }
    
    private class Listener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            
            Restaurant [] results;
            Restaurant med;
            String ID;
            String rest1Name;
            String rest1Address;
            String rest2Name;
            String rest2Address;
            String text = txtName.getText();
            if(rec.checkName(text))
            {   
                ID = rec.getID(text);

                
                results = rec.similarRestaurants(text);
                med = rec.nearestMed(text);
                rest1Name = results[0].getName();
                rest1Address = results[0].getStreetName() + ", " + results[0].getCity() + ", " + results[0].getState() + ", " + results[0].getZipCode();
                rest2Name = results[1].getName();
                rest2Address = results[1].getStreetName() + ", " + results[1].getCity() + ", " + results[1].getState() + ", " + results[1].getZipCode();
                lblCluster.setText("Cluster number: " + rec.whichCluster(text));
                lblKMed.setText("K-Median: " + med.getName() + ", " + med.getCity() + " " + med.getState());
                lblRest1Name.setText(rest1Name);
                lblRest1Address.setText(rest1Address);
                lblRest2Name.setText(rest2Name);
                lblRest2Address.setText(rest2Address);
                
            }
            else
            {
                lblRest1Name.setText("Sorry, that's not in our system.");
                lblRest1Address.setText("");
                lblCluster.setText("");
                lblRest2Name.setText("");
                lblRest2Address.setText("");
                lblKMed.setText("");
            }

            
        }
        
    }
            
}
