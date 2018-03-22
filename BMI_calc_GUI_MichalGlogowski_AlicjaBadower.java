package app;

import java.awt.GridLayout;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

/**
 * BMI Calculator with GUI
 * @author Micha³ G³ogowski, Alicja Badower
 * @version 0.1
 */

public class BMI_calc_GUI_MichalGlogowski_AlicjaBadower {
	
	/**
	 * Private static fields containing weight and height typed by the user (with '_s' there are Strings, with '_d' double numbers)
	 * BMI - field for result of the calculations
	 */
	private static String weight_s = "";
	private static String height_s = "";
	private static String BMI_s = "";
	private static String nick_s = "";
	private static double weight_d = 0;
	private static double height_d = 0;
	private static double BMI = 0;
	
	//String fields used for correctly formatting text that is send to file
	//Further there are going to be '\t' characters, depending on size of texts
	private static String tabNick = "";
	private static String tabBMI = "";
	private static String tabWeight = "";
	
	
	private static File file = new File("data.txt");
	
	/**
	 * Private static fields containing components of the window and DialogBoxes
	 */
	private static JFrame window;
	private static JLabel label1, label2, label3, label4, dialogLabel1, dialogLabel2, aboutLabel1, aboutLabel2, aboutLabel3;
	private static JTextField weight, height, nick;
	private static JDialog dialog, aboutDialog, saveDialog;
	private static JButton calculateButton, about, close, saveButton;
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
    	BMI = BMI * 100;
    	BMI = Math.round(BMI);
    	BMI = BMI / 100;
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
	}
	
	/**
	 * Private method responsible for checking String if it contains comma instead of dot.
	 * When there is comma, commaToDot changes it to dot allowing parsing that string to double.
	 * @param s string to check
	 * @return string with dot instead of comma
	 */
	
	private static String commaToDot(String s) {
		
		return s.replace(',',',');
		
	}
	
	
	/**
	 * Private method responsible for initializing components of the window and DialogBoxes
	 */
	private static void init()
	{
		//MAIN WINDOW
		window = new JFrame();
		window.setSize(400,245);
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
		window.add(label3);
		label4.setSize(100,25);
		label4.setLocation(30,120);
		label4.setText("Height [m]");
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
		calculateButton.setLocation(280,120);
		calculateButton.setText("Calculate");
		calculateButton.setVisible(true);
		
		saveButton = new JButton();
		window.add(saveButton);
		saveButton.setSize(90,25);
		saveButton.setLocation(280,150);
		saveButton.setText("History");
		saveButton.setVisible(true);
		
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
		    		
		    		FileOutputStream file = new FileOutputStream("C:\\temp\\save.txt");
		    		
		    		String s2=String.valueOf(BMI);
		    		
		    		String s=nick.getText()+" "+weight.getText()+"kg "+height.getText()+"m BMI= s2\n";
		    		
		    		byte b[]=s.getBytes();
		    		
		    		file.write(b);
		    		file.close();
		    		
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
		
		
	    private static JDialog saveDialogC;


	    private static void saveDialog(){

	        JFrame frame = new JFrame();
	
	        saveDialogC = new JDialog(frame, "SAVE", true);

	      //  saveDialogC.setLayout(new FlowLayout());

	        saveDialogC.setLocationRelativeTo(null);

	        saveDialogC.setSize(400,400);

	        saveDialogC.setVisible(true);

	        
	        BufferedReader save = null;
	        
	            save = new BufferedReader(new FileReader("C:\\temp\\save.txt"));
	            String line;
	            while ((line = save.readLine()) != null) {
	                
	            	saveDialogC.add(new JLabel(line));
	            	
	            }

	    }

		
		
		
		
	                		//READ
		
		saveButton.addActionListener(new ActionListener()
		{  
		    public void actionPerformed(ActionEvent e)
		    {
		    	try
		    	{
		    		saveDialog();
		    		
		    	}
		    	catch(NumberFormatException ex)
		    	{
		    		//Otherwise the DialogBox will inform user about wrong data in the fields
		    		dialogLabel1.setText("Cannot read the file");
		    		dialogLabel2.setText("Please try again");
		    		dialog.setTitle("ERROR");
		    		dialog.setVisible(true);
		    	}
		    }
	    } );
			
			
			
		
		separator = new JSeparator();
		window.add(separator);
		separator.setLocation(0,160);
		separator.setSize(400,1);
		separator.setVisible(true);
		
		about = new JButton();
		window.add(about);
		about.setLocation(180,175);
		about.setSize(90,25);
		about.setText("About");
		about.setVisible(true);
		
		close = new JButton();
		window.add(close);
		close.setSize(90,25);
		close.setLocation(280,175);
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
		aboutDialog.setTitle("BMI Calculator v0.1");
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
	    