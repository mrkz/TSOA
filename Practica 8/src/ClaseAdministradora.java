/**
 * Taller de Sistemas Operativos Avanzados - 2013B
 * Simental Magaña Marcos Eleno Joaquín.
 * Sección D04 - Tarea 8
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Hashtable;

public class ClaseAdministradora {

	private Hashtable<Integer, Materia> table;
	private int counter;
	
	public ClaseAdministradora(){
		table = new Hashtable<Integer, Materia>();
		counter = 0;
	}	
	
	public int addElement(String code, String name){
		Materia newCourse = new Materia(name, code);
		table.put(((Integer)getCounter() + 1), newCourse);
		setCounter(getCounter()+1);
		printAll();
		return getCounter();
	}
	
	public Materia getElement(int index){
		Materia course = null;
		try{
			course = table.get(((Integer)index));
		}
		catch (NullPointerException e){}
		return course;
	}
	
	public Materia deleteElement(int index){
		Materia deleted = null;
		try{
			deleted = table.remove(((Integer)index));
		}
		catch (NullPointerException e){}
		printAll();
		return deleted;
	}
	
	private void printElement(int index){
		Materia course;
		course = getElement(index);
		System.out.println(index+"         \t|"+
						   course.getCode()+"\t|"+
						   course.getName());
	}
	
	public void printAll(){
		if (table.isEmpty()){
			System.out.println("Sin elementos...");
		}
		else{
			System.out.println("id_secuencial\t|clave\t|nombre_materia");
			for (Enumeration<Integer> e = table.keys(); e.hasMoreElements();){
				printElement(e.nextElement().intValue());
			}
		}
	}
	
	public int getCounter(){
		return counter;
	}
	
	public void setCounter(int newVal){
		counter = newVal;
	}
	
	public boolean hasKey(int index){
		return table.containsKey(((Integer)index));
	}
	
	public boolean hasElements(){
		return !table.isEmpty();
	}
	
	public static void main(String[] args) {
		String option = null, name, code;
		int index;
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		ClaseAdministradora adm = new ClaseAdministradora();
		do{
			System.out.println("1) Nueva Materia");
			System.out.println("2) Baja Materia");
			System.out.println("3) Mostrar Materia");
			System.out.println("4) Mostrar todas las Materias");
			System.out.println("S) Salir");
			System.out.print( "Op: ");
			try {
				option = bf.readLine();
			}catch (IOException e){}
			try{
				switch(option){
				case "1":
					System.out.print("Código de nueva materia: ");
					code = bf.readLine();
					System.out.print("Nombre de nueva materia: ");
					name = bf.readLine();
					adm.addElement(code, name);
					break;
				case "2":
					if(!adm.hasElements()){
						System.out.println("No hay elementos que eliminar...");
						continue;
					}
					System.out.print("Indice de materia a eliminar: ");
					index = Integer.parseInt(bf.readLine());
					if (adm.hasKey(index)){
						adm.deleteElement(index);
					}
					else{
						System.out.println("Valor no encontrado...");
					}
					break;
				case "3":
					if(!adm.hasElements()){
						System.out.println("Sin elementos...");
						continue;
					}
					System.out.print("indice de materia a mostrar: ");
					index = Integer.parseInt(bf.readLine());
					if (adm.hasKey(index)){
						System.out.println("id_secuencial\t|clave\t|nombre_materia");
						adm.printElement(index);
					}
					else{
						System.out.println("Valor no encontrado...");
					}
					break;
				case "4":
					adm.printAll();
					break;
				default:
					break;
				}
			}
			catch (IOException e){}
			catch (NumberFormatException e){
				System.out.println("Formato no válido: Se esperaba un número");
			}
		}while(!option.equalsIgnoreCase("s"));
	}

}

class Materia{
	
	private String name, code;
	
	public Materia(String name, String code){
		this.name = name;
		this.code = code;
	}
	
	public String getName(){
		return name;
	}
	
	public String getCode(){
		return code;
	}
}
