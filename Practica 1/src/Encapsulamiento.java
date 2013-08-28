import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		
		MiFecha fecha;
		Window mainWindow;
		fecha = new MiFecha();
		mainWindow = new Window(fecha);
		mainWindow.setVisible(true);
	}
}

class MiFecha{
	private int day, month, year;
	public static final int JANUARY = 1, FEBRUARY = 2, MARCH = 3,
							APRIL = 4, MAY = 5, JUNE = 6, JULY = 7,
							AUGUST = 8, SEPTEMBER = 9, OCTOBER = 10,
							NOVEMBER = 11, DECEMBER = 12;
	
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
		if(isValidDate(newDay, month, year)){
			day = newDay;
		}
	}
	
	public void setMonth(int newMonth){
		if(isValidDate(day, newMonth, year)){
			month = newMonth;
		}
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
			case FEBRUARY:
				if(_isLeapYear(year)){
					value = (day < 30) ? true : false;
				}
				else
					value = (day < 29) ? true : false;
				break;
			case APRIL: case JUNE: case SEPTEMBER: case NOVEMBER:
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
		applyDateButton.addActionListener(this);
		currentDatePanel.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY, 1, true),
				  				   "Current Date:"));
		currentDateString = new JLabel(fecha.toString());
		currentDatePanel.add(currentDateString);
		newDatePanel.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY, 1, true),
							   "New date(DD/MM/YYY):"));
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
	
	public void resetFields(){
		dayField.setText("");
		monthField.setText("");
		yearField.setText("");
		dayField.requestFocus();
	}
	
	public void updateCurrentDate(){
		currentDateString.setText(date.toString());
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		int newDay, newMonth, newYear;
		try{
			newDay = Integer.parseInt(dayField.getText());
			newMonth = Integer.parseInt(monthField.getText());
			newYear = Integer.parseInt(yearField.getText());
			if(date.setDate(newDay, newMonth, newYear)){
				updateCurrentDate();
			}
			
		}
		catch (NumberFormatException e){}
		resetFields();
		
	}
}
