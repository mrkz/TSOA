/**
 * Taller de Sistemas Operativos Avanzados - 2013B
 * Simental Magaña Marcos Eleno Joaquín.
 * Sección D04 - Tarea 2
 */
public class Conversor {
	
	public Conversor(){
	}

	public void printFormat(String s, int pipe){
		System.out.print(s);
		System.out.print((pipe == 0 ) ? (" | ") : (""));
	}
	
	public void printBinary(byte a){
		System.out.print("| ");
		int tmp = 0x80;
		for	(int i = 0, pipe = 1; i < Byte.SIZE; i++, pipe++){
			printFormat(((tmp&a) == 0) ? "0" : "1", pipe % 8);
			tmp >>>= 1;
		}
		System.out.println();
	}
	
	public void printBinary(short a){
		System.out.print("| ");
		int tmp = 0x8000;
		for	(int i = 0, pipe = 1; i < Short.SIZE; i++, pipe++){
			printFormat(((tmp&a) == 0) ? "0" : "1", pipe % 8);
			tmp >>>= 1;
		}
		System.out.println();
	}
	
	public void printBinary(int a){
		System.out.print("| ");
		int tmp = Integer.MIN_VALUE;
		for	(int i = 0, pipe = 1; i < Integer.SIZE; i++, pipe++){
			printFormat(((tmp&a) == 0) ? "0" : "1", pipe % 8);
			tmp >>>= 1;
		}
		System.out.println();
	}
	
	public void printBinary(long a){
		System.out.print("| ");
		long tmp = Long.MIN_VALUE;
		for	(int i = 0, pipe = 1; i < Long.SIZE; i++, pipe++){
			printFormat(((tmp&a) == 0) ? "0" : "1", pipe % 8);
			tmp = tmp >>> 1;
		}
		System.out.println();
	}

	public static void main(String[] args) {
		
		byte  b = -128; // same binary representation for int b = 0x80;
		short s = 0x80;
		
	 	Conversor converter;
	 	converter = new Conversor();

	 	converter.printBinary(1);
	 	converter.printBinary(2);
	 	converter.printBinary(128);
	 	converter.printBinary(256);
	 	converter.printBinary(1024);
	 	converter.printBinary(2147483647);
	 	converter.printBinary(-1);
	 	converter.printBinary(-2);
	 	converter.printBinary(-127);
	 	converter.printBinary(-128);
	 	converter.printBinary(-2147483648);
	 	converter.printBinary(b);
	 	converter.printBinary(s);
	 	converter.printBinary(1L);
	 	converter.printBinary(2L);
	 	converter.printBinary(128L);
	 	converter.printBinary(256L);
	 	converter.printBinary(1024L);
	 	converter.printBinary(Long.MAX_VALUE);
	 	converter.printBinary(-1L);
	 	converter.printBinary(-2L);
	 	converter.printBinary(-127L);
	 	converter.printBinary(-128L);
	 	converter.printBinary(Long.MIN_VALUE);
	}

}
