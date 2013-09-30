/**
 * Taller de Sistemas Operativos Avanzados - 2013B
 * Simental Magaña Marcos Eleno Joaquín.
 * Sección D04 - Tarea B
 */
public class Parametros {
	
	public void printArray(byte[] array){
		for(int i = 0; i< array.length; i++)
			System.out.print("["+array[i]+"]");
		System.out.println();
	}
	
	/*
	 * receive a short and separate into bytes.
	 * Most significant byte goes at the beginning of the array:
	 * 			   most		  less
	 * data  = | 00000001 | 00000010 |
	 * 			   byte 1	  byte 2
	 * array = [ 00000001 ][ 00000010 ]
	 */
	public byte[] receiveShort(short data){
		byte[] byteArray = new byte[2];
		/* saved from most to less significant */
		byteArray[0] = (byte) (data >> 8);
		byteArray[1] = (byte) data;
		return byteArray;
	}
	
	/*
	 * receive an int and separate into bytes.
	 * Most significant byte goes at the beginning of the array:
	 * 			  most		  					 less
	 * data  = 00001000 | 01111110 | 10011001 | 10000001
	 * 		     byte 1    byte 2    byte 3    byte 4
	 * array = [00001000][01111110][10011001][10000001]
	 */
	public byte[] receiveInt(int data){
		byte[] byteArray = new byte[4];
		/* saved from most to less significant */
		for(int i = 3; i >= 0; i--){
			byteArray[i] = (byte) data; 
			data >>= 8;
		}
		return byteArray;
	}
		
	public short bytesToShort(byte[] array){
		short bytesValue = 0x0;
		bytesValue = (short)((array[1]      & 0x00FF) | 
							 (array[0] << 8 & 0xFF00));
		return bytesValue;
	}
	
	public int bytesToInt(byte[] array){
		int bytesValue = 0x0;
		bytesValue = (int)( (array[3]       & 0x000000FF) | 
							(array[2] << 8  & 0x0000FF00) | 
							(array[1] << 16 & 0x00FF0000) | 
							(array[0] << 24 & 0xFF000000));
		return bytesValue;
	}
	
	public static void main(String[] args) {
		
		/* 		8		126		153		129	  */
		/* 00001000 01111110 10011001 10000001*/
		byte[] array;
		int integerValue = 142514561;
		short shortValue = (short) 153;
		Parametros p;
		p = new Parametros();
		
		array = p.receiveShort(shortValue);
		System.out.print("Valor de Array para short: "); p.printArray(array);
		System.out.println("Valor del arrayToShort: "+p.bytesToShort(array));
		
		array = p.receiveInt(integerValue);
		System.out.print("Valor de Array para int: "); p.printArray(array);
		System.out.println("Valor del arrayToInt: "+p.bytesToInt(array));
		
	}

}
