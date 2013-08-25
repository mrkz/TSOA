import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

/**
 * Taller de Sistemas Operativos Avanzados - 2013B
 * Simental Magaña Marcos Eleno Joaquín.
 * Sección D04 - Tarea 1
 */
public class Encapsulamiento {


	public static void main(String[] args) {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		String[] date = null;
		int d, m, y;
		MiFecha fecha;
		fecha = new MiFecha();
		System.out.println(fecha);
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
				System.out.println(d+" "+m+" "+y);
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
