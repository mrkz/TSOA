import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * Taller de Sistemas Operativos Avanzados - 2013B
 * Simental Magaña Marcos Eleno Joaquín.
 * Sección D04 - Tarea 1
 */
public class Encapsulamiento {


	public static void main(String[] args) {
		
		/*para el look n feel del S.O.*/
		try{
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)	{
		 	e.printStackTrace();
		}
		/* hasta aquí el look n feel */
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		String[] date = null;
		int d, m, y;
		MiFecha fecha;
		Window mainWindow;
		fecha = new MiFecha();
		mainWindow = new Window(fecha);
		mainWindow.setVisible(true);
		/* to be replaced for GUI */
		while(true){
			System.out.println("Current Date: "+fecha);
			System.out.println("New Date (DD/MM/YYYY): ");
			try {
				line = in.readLine();
				date = line.split("/");
				d = Integer.parseInt(date[0]);
				m = Integer.parseInt(date[1]);
				y = Integer.parseInt(date[2]);
				fecha.setDate(d, m, y);
			}
			catch (NumberFormatException n){}
			catch (ArrayIndexOutOfBoundsException a){}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		/* end of GUI replacement */
	}
}

class MiFecha{
	private int day, month, year;
	
	public MiFecha(){
		/* Set today date */
		
		Calendar cal = Calendar.getInstance();
		day = cal.get(Calendar.DATE);
		month = cal.get(Calendar.MONTH) + 1;
		year = cal.get(Calendar.YEAR);
	}
	
	/*-*-*-*-*-*-*-*-*-*-*-*	Public methods	*-*-*-*-*-*-*-*-*-*-*-*/
	
	public int getDay(){
		return day;
	}
	
	public int getMonth(){
		return month;
	}
	
	public int getYear(){
		return year;
	}
	
	public void setDay(int newDay){
		day = newDay;
	}
	
	public void setMonth(int newMonth){
		month = newMonth;
	}
	
	public void setYear(int newYear){
		year = newYear;
	}
	
	public boolean setDate(int newDay, int newMonth, int newYear){
		boolean dateChanged = false;
		if (isValidDate(newDay, newMonth, newYear)){
			setDay(newDay);
			setMonth(newMonth);
			setYear(newYear);
			dateChanged = true;
		}
		return dateChanged;
	}
	
	public String toString(){
		return (""+day+"/"+month+"/"+year);
	}
	
	/*-*-*-*-*-*-*-*-*-*-*-*	Private methods	*-*-*-*-*-*-*-*-*-*-*-*/
	
	private boolean isValidDate(int day, int month, int year){
		boolean value = false;
		if(_isValidDay(day) &&  _isValidMonth(month)){
			switch(month){
			case 2:
				if(_isLeapYear(year)){
					value = (day < 30) ? true : false;
				}
				else
					value = (day < 29) ? true : false;
				break;
			case 4: case 6: case 9: case 11:
				value = (day < 31) ? true : false;
				break;
			/* it's a month with 31 days */
			default: value = true; break;
			}
		}
		return value;
	}
	
	private boolean _isValidMonth(int month){
		return (month > 0 && month < 13);
	}
	
	private boolean _isValidDay(int day){
		return (day > 0 && day < 32);
	}
	
	private boolean _isLeapYear(int year){
		boolean leapYear = false;
		if(year >= 0 && year % 4 == 0){
			if(year % 100 == 0){
				if(year % 400 == 0){
					leapYear = true;
				}
				else{
					leapYear = false;
				}
			}
			else{
				leapYear = true;
			}
		}
		return leapYear;
	}
	
}

class Window extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private MiFecha date;
	public static final int WIDTH = 200, HEIGHT = 200;
	private JPanel currentDatePanel = null, newDatePanel = null;
	private JPanel inputPanel;
	private JLabel currentDateString = null;
	private JTextField dayField, monthField, yearField;
	private JButton applyDateButton = null;
	 
	public Window(MiFecha fecha){
		super("Practica 1 - TSOA");
		setSize(WIDTH, HEIGHT);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		date = fecha;
		currentDatePanel = new JPanel();
		newDatePanel = new JPanel();
		newDatePanel.setLayout(new BoxLayout(newDatePanel, BoxLayout.Y_AXIS));
		dayField = new JTextField(2);
		monthField = new JTextField(2);
		yearField = new JTextField(4);
		inputPanel = new JPanel(new FlowLayout());
		applyDateButton = new JButton("Apply");
		currentDatePanel.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY, 1, true),
				  				   "Current Date:"));
		currentDateString = new JLabel(fecha.toString());
		currentDatePanel.add(currentDateString);
		newDatePanel.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY, 1, true),
							   "New date:"));
		dayField.setText(date.getDay()+"");
		monthField.setText(date.getMonth()+"");
		yearField.setText(date.getYear()+"");
		inputPanel.add(dayField);
		inputPanel.add(monthField);
		inputPanel.add(yearField);
		newDatePanel.add(inputPanel);
		newDatePanel.add(applyDateButton);
		add(currentDatePanel);
		add(newDatePanel);
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		
	}
}
