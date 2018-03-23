package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;

/**
 * BMI Calculator with GUI
 * @author Micha³ G³ogowski, Alicja Badower
 * @version 0.2
 */

public class BMI_calc_GUI_MichalGlogowski_AlicjaBadower {
	
	//Private static fields containing weight, height and nick entered by the user (with '_s' there are Strings, with '_d' double numbers)
	//BMI - field for the result of the calculations
	private static String weight_s = "";
	private static String height_s = "";
	private static String BMI_s = "";
	private static String nick_s = "";
	private static double weight_d = 0;
	private static double height_d = 0;
	private static double BMI = 0;
	
	/**
	 * Private static field for the save file's destination
	 */
	private static File file = new File("data.txt");
	
	//Private static fields containing components of the window and DialogBoxes
	private static JFrame window;
	private static JLabel label1, label2, label3, label4, dialogLabel1, dialogLabel2, aboutLabel1, aboutLabel2, aboutLabel3;
	private static JTextField weight, height, nick;
	private static JDialog dialog, aboutDialog, historyDialog;
	private static JButton calculateButton, about, close, history;
	private static JSeparator separator;
	
	
	/**
	 * Private method responsible for parsing strings to doubles, calculating BMI and rounding result to two digits after the comma
	 */
	private static void calculate()
	{
		//Update Strings before working on them
		updateStrings();
		
		//Change potential commas to dots
		weight_s = commaToDot(weight_s);
		height_s = commaToDot(height_s);
		
		//Parsed strings to double
    	weight_d = Double.parseDouble(weight_s);
    	height_d = Double.parseDouble(height_s);
    	
    	//Calculated BMI
    	BMI = weight_d / height_d / height_d;
    	
    	//Rounding to two digits after comma
    	BMI = round(BMI);
    	weight_d = round(weight_d);
    	height_d = round(height_d);
    	
    	//Saving rounded numbers to strings
    	BMI_s = String.valueOf(BMI);
    	weight_s = String.valueOf(weight_d);
    	height_s = String.valueOf(height_d);
	}
	
	/**
	 * Private method responsible for rounding double numbers up to 2 digits after comma
	 * @param arg number to round
	 * @return number rounded up to two digits after comma
	 */
	private static double round(double arg)
	{
		arg = arg * 100;
		arg = Math.round(arg);
		arg = arg / 100;
		return arg;
	}
	
	/**
	 * Private method responsible for checking BMI and returning correct description
	 * @return text depends on BMI value
	 */
	private static String selectCategory()
	{
		String text;
		if(BMI<15){text = "Very severely underweight";}
		else if(BMI<16){text = "Severely underweight";}
		else if(BMI<18.5){text = "Underweight";}
		else if(BMI<25){text = "Normal (healthy weight)";}
		else if(BMI<30){text = "Overweight";}
		else if(BMI<35){text = "Obese Class I (Moderately obese)";}
		else if(BMI<40){text = "Obese Class II (Severely obese)";}
		else{text = "Obese Class III (Very severely obese)";}
		
		return text;
	}
	
	/**
	 * Private method responsible for updating Strings containing values from JTextFields
	 */
	private static void updateStrings()
	{
		weight_s = weight.getText();
		height_s = height.getText();
		nick_s = nick.getText();
	}
	
	/**
	 * Private method responsible for checking String if it contains comma instead of dot.
	 * If there is comma, commaToDot changes it to dot allowing parsing that string to double.
	 * @param s string to check
	 * @return string with dot instead of comma
	 */
	private static String commaToDot(String s) {
		return s.replace(',','.');
	}

	
	/**
	 * Private static method that displays the saved file's content in a JDialog
	 */
	private static void historyDialog(){
		
        historyDialog = new JDialog(window, "History");
        historyDialog.setLayout(null);
        historyDialog.setResizable(false);

        try
		 {
			 FileReader in = new FileReader(file);
			 
			 //BufferedReader created to load lines of text instead of simple characters
			 BufferedReader buffReader = new BufferedReader(in);
			 
			 //Local variable to keep line of read text from file
			 String line = "";
			 
			 //Iterator used for placing labels and button in correct places and resizing JDialog
			 int i = 0;

			 //Do until EOF is not reached (specified by returning null by method readLine)
			 while( (line = buffReader.readLine()) != null)
			 {
				//Create local label and set its location depending on i value
				JLabel local = new JLabel(line);
				local.setLocation(10,10+20*i);
				local.setSize(330,25);
				local.setVisible(true);
				historyDialog.add(local);
				 
				i++;
			 }
			 
			 //Close readers
			 buffReader.close();
			 in.close();
			 
			 //Change the DialogBox's size depending on number of lines in file
			 historyDialog.setSize(350,90+20*i);
			 historyDialog.setVisible(true);
			 historyDialog.setLocationRelativeTo(null);
			 
			 //Close button
			 JButton closeHistory = new JButton();
			 historyDialog.add(closeHistory);
			 closeHistory.setVisible(true);
			 closeHistory.setText("Close");
			 closeHistory.setSize(90,25);
			 closeHistory.setLocation(130,20+20*i);
			 closeHistory.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					historyDialog.dispose();
				}
			 });
		 }
		 catch(IOException e)
		 {
			 JOptionPane.showMessageDialog(window, "Could not load the file");
		 }
    }
	
	
	/**
	 * Private method responsible for initializing components of the window and DialogBoxes
	 */
	private static void init()
	{
		//MAIN WINDOW
		window = new JFrame();
		window.setSize(400,270);
		window.setLayout(null);
		window.setResizable(false);
		window.setTitle("BMI Calculator");
		window.setLocationRelativeTo(null);							//center window
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
		label1 = new JLabel();
		window.add(label1);
		label1.setSize(350,25);
		label1.setLocation(30,20);
		label1.setText("Enter values into boxes below and press 'Calculate' button");
		label1.setVisible(true);
		
		label2 = new JLabel();
		window.add(label2);
		label2.setSize(100,25);
		label2.setLocation(30,60);
		label2.setText("Weight [kg]");
		label2.setVisible(true);
		
		label3 = new JLabel();
		window.add(label3);
		label3.setSize(100,25);
		label3.setLocation(30,90);
		label3.setText("Height [m]");
		label3.setVisible(true);
		
		label4 = new JLabel();
		window.add(label4);
		label4.setSize(100,25);
		label4.setLocation(30,120);
		label4.setText("Nick");
		label4.setVisible(true);
		
		weight = new JTextField();
		window.add(weight);
		weight.setSize(70,20);
		weight.setLocation(300,60);
		weight.setVisible(true);
		
		height = new JTextField();
		window.add(height);
		height.setSize(70,20);
		height.setLocation(300,90);
		height.setVisible(true);
		
		nick = new JTextField();
		window.add(nick);
		nick.setSize(70,20);
		nick.setLocation(300,120);
		nick.setVisible(true);
		
		calculateButton = new JButton();
		window.add(calculateButton);
		calculateButton.setSize(90,25);
		calculateButton.setLocation(280,160);
		calculateButton.setText("Calculate");
		calculateButton.setVisible(true);
		
		history = new JButton();
		window.add(history);
		history.setSize(90,25);
		history.setLocation(180,160);
		history.setText("History");
		history.setVisible(true);
		
		calculateButton.addActionListener(new ActionListener()
		{  
		    public void actionPerformed(ActionEvent e)
		    {
		    	try
		    	{
		    		//When the button is pressed try to calculate BMI and display it in the DialogBox
		    		calculate();
		    		dialogLabel1.setText("Calculated BMI: " + BMI);
		    		dialogLabel2.setText(selectCategory());
		    		dialog.setTitle("BMI");
		    		dialog.setVisible(true);
		    		
            		//Take first 11 letters of nicknames
            		//It is made due to limited width of the DialogBox in which it is displayed
            		if(nick_s.length()>11) nick_s = nick_s.substring(0, 11);
		    		
            		//Save to file
            		try
            		{
                		FileWriter out = new FileWriter(file,true);	//the second argument allows to append text to existing file
                		out.write(nick_s + ": ");
                		out.write("BMI=" + BMI_s + ", ");
                		out.write("weight=" + weight_s + ", ");
                		out.write("height=" + height_s + "\r\n");	//add entry to next line
                		out.close();								//flush and close the stream
            		}
            		catch(IOException ex)
            		{
            			JOptionPane.showMessageDialog(window, "Save was not possible!");	
            		}
		    		
		    	}
		    	catch(NumberFormatException ex)
		    	{
		    		//Otherwise the DialogBox will inform user about wrong data in the fields
		    		dialogLabel1.setText("NumberFormatException");
		    		dialogLabel2.setText("Please check if the fields are correctly filled");
		    		dialog.setTitle("ERROR");
		    		dialog.setVisible(true);
		    	}
		    }
	    });
		
	    //Load the history of calculations
		history.addActionListener(new ActionListener()
		{  
		    public void actionPerformed(ActionEvent e)
		    {
		    	 historyDialog();
		    }
	    } );
			
		separator = new JSeparator();
		window.add(separator);
		separator.setLocation(0,195);
		separator.setSize(400,1);
		separator.setVisible(true);
		
		about = new JButton();
		window.add(about);
		about.setLocation(180,205);
		about.setSize(90,25);
		about.setText("About");
		about.setVisible(true);
		
		close = new JButton();
		window.add(close);
		close.setSize(90,25);
		close.setLocation(280,205);
		close.setText("Close");
		close.setVisible(true);
		

		//DIALOG BOX and components
		dialog = new JDialog();
		dialog.setSize(275,130);
		dialog.setResizable(false);
		dialog.setLayout(null);
		dialog.setLocationRelativeTo(null);		//center DialogBox
		dialog.setVisible(false);				//DialogBox is visible only when user interacts 
		
		dialogLabel1 = new JLabel();
		dialog.add(dialogLabel1);
		dialogLabel1.setSize(230,25);
		dialogLabel1.setLocation(10,10);
		dialogLabel1.setVisible(true);
		
		dialogLabel2 = new JLabel();
		dialog.add(dialogLabel2);
		dialogLabel2.setSize(280,25);
		dialogLabel2.setLocation(10,30);
		dialogLabel2.setVisible(true);
		
		JButton okButton = new JButton();
		dialog.add(okButton);
		okButton.setSize(70,25);
		okButton.setLocation(103,65);
		okButton.setText("OK");
		okButton.setVisible(true);
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});
		
		
		//'ABOUT' DIALOG BOX and components
		aboutDialog = new JDialog();
		aboutDialog.setTitle("BMI Calculator v0.2");
		aboutDialog.setSize(150,150);
		aboutDialog.setResizable(false);
		aboutDialog.setLayout(null);
		aboutDialog.setLocationRelativeTo(null);		//center DialogBox
		aboutDialog.setVisible(false);					//DialogBox is visible only when user interacts 
		
		aboutLabel1 = new JLabel();
		aboutDialog.add(aboutLabel1);
		aboutLabel1.setSize(100,25);
		aboutLabel1.setLocation(10,10);
		aboutLabel1.setText("Created by:");
		aboutLabel1.setVisible(true);
		
		aboutLabel2 = new JLabel();
		aboutDialog.add(aboutLabel2);
		aboutLabel2.setSize(100,25);
		aboutLabel2.setLocation(10,30);
		aboutLabel2.setText("Micha³ G³ogowski");
		aboutLabel2.setVisible(true);
		
		aboutLabel3 = new JLabel();
		aboutDialog.add(aboutLabel3);
		aboutLabel3.setSize(100,25);
		aboutLabel3.setLocation(10,50);
		aboutLabel3.setText("Alicja Badower");
		aboutLabel3.setVisible(true);
		
		JButton okButton2 = new JButton();
		aboutDialog.add(okButton2);
		okButton2.setSize(70,25);
		okButton2.setLocation(40,85);
		okButton2.setText("OK");
		okButton2.setVisible(true);
		okButton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aboutDialog.setVisible(false);
			}
		});
	}

	public static void main(String[] args)
	{
		init();
		
		//Add Listener to calculateButton which detects events
		calculateButton.addActionListener(new ActionListener()
		{  
		    public void actionPerformed(ActionEvent e)
		    {
		    	try
		    	{
		    		//When the button is pressed try to calculate BMI and display it in the DialogBox
		    		calculate();
		    		dialogLabel1.setText("Calculated BMI: " + BMI);
		    		dialogLabel2.setText(selectCategory());
		    		dialog.setTitle("BMI");
		    		dialog.setVisible(true);
		    	}
		    	catch(NumberFormatException ex)
		    	{
		    		//Otherwise the DialogBox will inform user about wrong data in the fields
		    		dialogLabel1.setText("NumberFormatException");
		    		dialogLabel2.setText("Please check if the fields are correctly filled");
		    		dialog.setTitle("ERROR");
		    		dialog.setVisible(true);
		    	}
		    }
	    } );
		
		about.addActionListener(new ActionListener()
				{
					//When 'About' button is pressed
					@Override
					public void actionPerformed(ActionEvent e) {
						aboutDialog.setVisible(true);
					}
				});
		
		close.addActionListener(new ActionListener()
		{
			//When 'Close' button is pressed
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
}
