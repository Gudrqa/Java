package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;

/**
 * BMI Calculator with GUI
 * @author Michal Glogowski, Alicja Badower
 * @version 0.4
 * License MIT
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
	
	//Arrays of Strings in which are stored all language descriptions
	private static String[] polish, english, japanese;

	//String array filled with currently used language description
	private static String[] localeS;
	
	//Object of wrapper class forLocale
	private static forLocale locale;
	
	//The number of lines in language file
	private final static int lineCountOfLanguageFile = 32;
	
	/**
	 * Method responsible for loading language content from external file
	 * @param path path of the file
	 * @param lineCount number of lines in that file
	 * @return array of Strings in which every element is the line of file
	 */
	private static String[] loadLanguageFile(String path, int lineCount)
	{
		//Variable to keep lines of read text from file
		String[] txtFile = new String[lineCount];
		
		try
		{
			FileReader in = new FileReader(path);
			 
			//BufferedReader created to load lines of text instead of simple characters
			BufferedReader buffReader = new BufferedReader(in);
			
			for(int i=0; i<lineCount; i++)
			{
				txtFile[i] = buffReader.readLine();
			}
			 
			//Close readers
			buffReader.close();
			in.close();
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(window, "Could not load file \"" + path + "\"");
			System.exit(0);		//When language file could not be loaded close the application
		}
			 
		return txtFile;
	}

	/**
	 * Private static field for the save file's destination
	 */
	private static File file = new File("data.txt");
	
	//Private static fields containing components of the window and DialogBoxes
	private static JFrame window;
	private static JLabel label1, label2, label3, label4, dialogLabel1, dialogLabel2, aboutLabel1, aboutLabel2, aboutLabel3;
	private static JTextField weight, height, nick;
	private static JDialog dialog, aboutDialog, historyDialog, languageDalog;
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

    	//If locale is set to English then convert their funny metrics to SI
    	if(localeS==english) convert();
    	
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
	 * Method responsible for converting English imperial units to SI
	 */
	private static void convert()
	{
		weight_d = weight_d * 0.45359237;
		height_d = height_d * 0.3048;
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
		if(BMI<15) text = localeS[8];
		else if(BMI<16) text = localeS[9];
		else if(BMI<18.5) text = localeS[10];
		else if(BMI<25) text = localeS[11];
		else if(BMI<30) text = localeS[12];
		else if(BMI<35) text = localeS[13];
		else if(BMI<40) text = localeS[14];
		else text = localeS[15];
		
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
		
        historyDialog = new JDialog(window, localeS[3]);
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
			 closeHistory.setText(localeS[6]);
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
			 JOptionPane.showMessageDialog(window, localeS[16]);
		 }
    }
	
	
	/**
	 * Private method responsible for initializing components of the window and DialogBoxes
	 */
	private static void init()
	{
		languageDalog.dispose();

		//MAIN WINDOW
		window = new JFrame();
		window.setSize(400,270);
		window.setLayout(null);
		window.setResizable(false);
		window.setTitle(localeS[17]);
		window.setLocationRelativeTo(null);							//center window
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
		label1 = new JLabel();
		window.add(label1);
		label1.setSize(350,25);
		label1.setLocation(30,20);
		label1.setText(localeS[7]);
		label1.setVisible(true);
		
		label2 = new JLabel();
		window.add(label2);
		label2.setSize(100,25);
		label2.setLocation(30,60);
		label2.setText(localeS[0]);
		label2.setVisible(true);
		
		label3 = new JLabel();
		window.add(label3);
		label3.setSize(100,25);
		label3.setLocation(30,90);
		label3.setText(localeS[1]);
		label3.setVisible(true);
		
		label4 = new JLabel();
		window.add(label4);
		label4.setSize(100,25);
		label4.setLocation(30,120);
		label4.setText(localeS[2]);
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
		calculateButton.setText(localeS[4]);
		calculateButton.setVisible(true);
		
		history = new JButton();
		window.add(history);
		history.setSize(100,25);
		history.setLocation(170,160);
		history.setText(localeS[3]);
		history.setVisible(true);
		
		calculateButton.addActionListener(new ActionListener()
		{  
		    public void actionPerformed(ActionEvent e)
		    {
		    	try
		    	{
		    		//When the button is pressed try to calculate BMI and display it in the DialogBox
		    		calculate();
		    		dialogLabel1.setText(localeS[22] + BMI);
		    		dialogLabel2.setText(selectCategory());
		    		dialog.setTitle(localeS[23]);
		    		dialog.setVisible(true);
		    		
            		//Take first 11 letters of nicknames
            		//It is made due to limited width of the DialogBox in which it is displayed
            		if(nick_s.length()>11) nick_s = nick_s.substring(0, 11);
		    		
            		//Save to file
            		try
            		{
						Date currentDate = new Date();

                		FileWriter out = new FileWriter(file,true);	//the second argument allows to append text to existing file
						out.write(locale.dateFormat.format(currentDate)+"\n");
                		out.write(nick_s + ": ");
                		out.write(localeS[23]+"=" + BMI_s + ", ");
                		out.write(localeS[27] + locale.numberFormatLocale.format(weight_d) + "kg" +", ");
                		out.write(localeS[28] + locale.numberFormatLocale.format(height_d) + "m" +"\r\n");	//add entry to next line
                		out.close();								//flush and close the stream
            		}
            		catch(IOException ex)
            		{
            			JOptionPane.showMessageDialog(window, localeS[29]);
            		}
		    		
		    	}
		    	catch(NumberFormatException ex)
		    	{
		    		//Otherwise the DialogBox will inform user about wrong data in the fields
		    		dialogLabel1.setText(localeS[24]);
		    		dialogLabel2.setText(localeS[25]);
		    		dialog.setTitle(localeS[26]);
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
		about.setLocation(170,205);
		about.setSize(100,25);
		about.setText(localeS[5]);
		about.setVisible(true);
		
		close = new JButton();
		window.add(close);
		close.setSize(90,25);
		close.setLocation(280,205);
		close.setText(localeS[6]);
		close.setVisible(true);
		

		//DIALOG BOX and components
		dialog = new JDialog();
		dialog.setSize(335,130);
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
		dialogLabel2.setSize(320,25);
		dialogLabel2.setLocation(10,30);
		dialogLabel2.setVisible(true);
		
		JButton okButton = new JButton();
		dialog.add(okButton);
		okButton.setSize(70,25);
		okButton.setLocation(132,65);
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
		aboutDialog.setTitle(localeS[18]);
		aboutDialog.setSize(150,150);
		aboutDialog.setResizable(false);
		aboutDialog.setLayout(null);
		aboutDialog.setLocationRelativeTo(null);		//center DialogBox
		aboutDialog.setVisible(false);					//DialogBox is visible only when user interacts 
		
		aboutLabel1 = new JLabel();
		aboutDialog.add(aboutLabel1);
		aboutLabel1.setSize(100,25);
		aboutLabel1.setLocation(10,10);
		aboutLabel1.setText(localeS[19]);
		aboutLabel1.setVisible(true);
		
		aboutLabel2 = new JLabel();
		aboutDialog.add(aboutLabel2);
		aboutLabel2.setSize(100,25);
		aboutLabel2.setLocation(10,30);
		aboutLabel2.setText(localeS[20]);
		aboutLabel2.setVisible(true);
		
		aboutLabel3 = new JLabel();
		aboutDialog.add(aboutLabel3);
		aboutLabel3.setSize(100,25);
		aboutLabel3.setLocation(10,50);
		aboutLabel3.setText(localeS[21]);
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
		
		//Add Listener to calculateButton which detects events
		calculateButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					//When the button is pressed try to calculate BMI and display it in the DialogBox
					calculate();
					dialogLabel1.setText(localeS[22] + BMI);
					dialogLabel2.setText(selectCategory());
					dialog.setTitle(localeS[23]);
					dialog.setVisible(true);
				}
				catch(NumberFormatException ex)
				{
					//Otherwise the DialogBox will inform user about wrong data in the fields
					dialogLabel1.setText(localeS[24]);
					dialogLabel2.setText(localeS[25]);
					dialog.setTitle(localeS[26]);
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

	/**
	 * Method responsible for displaying DialogBox which asks user which language he/she wants to use
	 */
	private static void languageDialog()
	{
		polish = new String[lineCountOfLanguageFile];
		english = new String[lineCountOfLanguageFile];
		japanese = new String[lineCountOfLanguageFile];
		
		//Load language files
		polish = loadLanguageFile("polish.txt",lineCountOfLanguageFile);
		english = loadLanguageFile("english.txt",lineCountOfLanguageFile);
		japanese = loadLanguageFile("japanese.txt",lineCountOfLanguageFile);
		
		languageDalog = new JDialog(window, "Choose your language");
		languageDalog.setLayout(null);
		languageDalog.setResizable(false);

		languageDalog.setSize(175,200);
		languageDalog.setVisible(true);
		languageDalog.setLocationRelativeTo(null);

		JButton setLocation = new JButton();
		languageDalog.add(setLocation);
		setLocation.setVisible(true);
		setLocation.setText("Find my location");
		setLocation.setSize(130,25);
		setLocation.setLocation(20,20);

		JButton polski = new JButton();
		languageDalog.add(polski);
		polski.setVisible(true);
		polski.setText("Polski");
		polski.setSize(130,25);
		polski.setLocation(20,60);

		JButton angielski = new JButton();
		languageDalog.add(angielski);
		angielski.setVisible(true);
		angielski.setText("English");
		angielski.setSize(130,25);
		angielski.setLocation(20,90);

		JButton japonski = new JButton();
		languageDalog.add(japonski);
		japonski.setVisible(true);
		japonski.setText("日本語");
		japonski.setSize(130,25);
		japonski.setLocation(20,120);

		languageDalog.setVisible(true);

		japonski.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				localeS=japanese;
				locale.setLocale(new Locale("ja","JP"));
				init();
			}
		});
		angielski.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				localeS=english;
				locale.setLocale(new Locale("en"));
				init();
			}
		});
		polski.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				localeS=polish;
				locale.setLocale(new Locale("pl","PL"));
				init();
			}
		});
		setLocation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (locale.locale.getDisplayCountry()=="Polska") localeS=polish;
				else if(locale.locale.getDisplayCountry()=="Japan") localeS=japanese;
				else localeS=english;

				init();
			}
		});
	}

	public static void main(String[] args)
	{
		locale = new forLocale();
		languageDialog();
	}

}

/**
 * Wrapper class responsible for correct number and date formatting
 */
class forLocale {

    public Locale locale;
    public NumberFormat numberFormatLocale;
    public DateFormat dateFormat;

    /**
     * Set default values for class.
     * If user wont use setLocale method this will be the preset for data representation.
     */
    forLocale()
    {
        locale = Locale.getDefault();
        numberFormatLocale = NumberFormat.getInstance(Locale.ITALY);
        dateFormat = DateFormat.getDateInstance(DateFormat.FULL, locale);
    }
    
    /**
     * Set locale, number format and dateFormat. This method is used when user decides to choose on of the predefined languages (not auto selected one).
     * @param l locale which will be pattern for fields in class wrapper
     */
    public void setLocale(Locale l)
    {
    	locale = l;
    	numberFormatLocale = NumberFormat.getInstance(l);
    	dateFormat = DateFormat.getDateInstance(DateFormat.FULL, l);
    }
}

