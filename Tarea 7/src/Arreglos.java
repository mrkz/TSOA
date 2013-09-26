
public class Arreglos{

	private static final String VALIDCHARACTERS="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final int MAX_TAM = 60;
	private Boolean[] changed;
	
	public Arreglos(){
		fillVector(MAX_TAM);
	}
	
	public String fillVector(int tam){
		String str = "";
		for(int i = 0; i < tam; i++){
			str+=getRandomChar()+"";
		}
		return str;
	}
	
	private char getRandomChar(){
		int idx = (int)(Math.random() * VALIDCHARACTERS.length());
		return VALIDCHARACTERS.charAt(idx);
	}
	
	public void printNewString(char [] str, Boolean [] changed, Boolean complete){
		for(int i = 0; i < changed.length; i++){
			if(changed[i] || complete){
				System.out.print(str[i]);
			}
		}
		System.out.println();
	}
	
	private Boolean[] setNewString(char [] originalString, char [] string) {
		int length = string.length;
		int kTimes = MAX_TAM / length;
		changed = new Boolean[length * kTimes];
		for(int i = 0, j = 0; i < kTimes * length; i++, j = (j + 1) % length){
			changed[i] = (originalString[i] == string[j]) ? false : true;
			if(changed[i])
				originalString[i] = string[j];
		}
		return changed;
	}
	
	public static void main(String[] args) {
		char [] originalString;
		Boolean [] changed;
		String toCopy = "cadenita";
		Arreglos arreglo;
		arreglo = new Arreglos();
		originalString = new char[Arreglos.MAX_TAM];
		originalString = arreglo.fillVector(Arreglos.MAX_TAM).toCharArray();
		System.out.println(originalString);
		changed = arreglo.setNewString(originalString,
							 toCopy.toUpperCase().toCharArray());
		arreglo.printNewString(originalString, changed, true);
		arreglo.printNewString(originalString, changed, false);
		
		
	}
}
