package sistemaDistribuido.sistema.rpc.modoUsuario;

import java.util.Stack;

import sistemaDistribuido.util.Escribano;

public abstract class Libreria{
	private Escribano esc;
	/*
	 * Atributos agregado para práctica 3
	 * Simental Magaña Marcos Eleno Joaquín
	 */
	protected Stack<Integer> parameters;
	public static final int OFFSET = 8,
							BYTES_IN_SHORT = 2,
							BYTES_IN_INT = 4;
	public static final short SUMATORIA = 0,
							  MULTIPLICATORIA = 1,
							  DIVISION = 2,
							  ABSOLUTO = 3;
	

	/**
	 * 
	 */
	public Libreria(Escribano esc){
		this.esc=esc;
		parameters = new Stack<Integer>();
	}

	/**
	 * 
	 */
	protected void imprime(String s){
		esc.imprime(s);
	}

	/**
	 * 
	 */
	protected void imprimeln(String s){
		esc.imprimeln(s);
	}
	
	/*
	 * Agregado práctica 3
	 * Simental Magaña Marcos Eleno Joaquín
	 */
	protected abstract void sumatoria();
	protected abstract void multiplicatoria();
	protected abstract void absoluto();
	protected abstract void division();
	
	/*
	 * Agregado práctica 3
	 * Simental Magaña Marcos Eleno Joaquín
	 * Se insertarn N+1 elementos
	 */
	public int sumatoria(int len, int[] array) {
		for (int i = len-1; i >= 0; i--){
			parameters.push(new Integer(array[i]));
		}
		parameters.push(new Integer(len));
		imprimeln("Llamando al resguardo sumatoria");
		sumatoria();
		return parameters.pop().intValue();
	}
	
	/*
	 * Agregado práctica 3
	 * Simental Magaña Marcos Eleno Joaquín
	 * Se insertarn N+1 elementos
	 */
	public int multiplicatoria(int len, int[] array) {
		for (int i = len-1; i >= 0; i--){
			parameters.push(new Integer(array[i]));
		}
		parameters.push(new Integer(len));
		imprimeln("Llamando al resguardo multipliatoria");
		multiplicatoria();
		return parameters.pop().intValue();
	}
	
	/*
	 * Agregado práctica 3
	 * Simental Magaña Marcos Eleno Joaquín
	 */
	public int division(int dividendo,int divisor){
		parameters.push(new Integer(divisor));
		parameters.push(new Integer(dividendo));
		parameters.push(new Integer(2));
		imprimeln("Llamando al resguardo division");
		division();
		return parameters.pop().intValue();
	}
	
	/*
	 * Agregado práctica 3
	 * Simental Magaña Marcos Eleno Joaquín
	 */
	public int absoluto(int val) {
		parameters.push(new Integer(val));
		parameters.push(new Integer(1));
		imprimeln("Llamando al resguardo absoluto");
		absoluto();
		return parameters.pop().intValue();
	}
	
	/*
	 * Agregado práctica 3
	 * Simental Magaña Marcos Eleno Joaquín
	 * 
	 * receive an int and separate into bytes.
	 * Most significant byte goes at the beginning of the array:
	 * 			  most		  					 less
	 * data  = 00001000 | 01111110 | 10011001 | 10000001
	 * 		     byte 1    byte 2    byte 3    byte 4
	 * array = [00001000][01111110][10011001][10000001]
	 */
	public byte[] intToByteArray(int data){
		byte[] byteArray = new byte[4];
		/* saved from most to less significant */
		for(int i = 3; i >= 0; i--){
			byteArray[i] = (byte) data; 
			data >>= 8;
		}
		return byteArray;
	}
	
	/*
	 * Agregado práctica 3
	 * Simental Magaña Marcos Eleno Joaquín
	 */
	public int byteArrayToInt(byte[] array){
		int bytesValue = 0x0;
		bytesValue = (int)( (array[3]       & 0x000000FF) | 
							(array[2] << 8  & 0x0000FF00) | 
							(array[1] << 16 & 0x00FF0000) | 
							(array[0] << 24 & 0xFF000000));
		return bytesValue;
	}
	
	/*
	 * Agregado práctica 3
	 * Simental Magaña Marcos Eleno Joaquín
	 * 
	 * receive a short and separate into bytes.
	 * Most significant byte goes at the beginning of the array:
	 * 			   most		  less
	 * data  = | 00000001 | 00000010 |
	 * 			   byte 1	  byte 2
	 * array = [ 00000001 ][ 00000010 ]
	 */
	public byte[] shortToByteArray(short data){
		byte[] byteArray = new byte[2];
		/* saved from most to less significant */
		byteArray[0] = (byte) (data >> 8);
		byteArray[1] = (byte) data;
		return byteArray;
	}
	/*
	 * Agregado práctica 3
	 * Simental Magaña Marcos Eleno Joaquín
	 */
	public short byteArrayToShort(byte[] array){
		short bytesValue = 0x0;
		bytesValue = (short)((array[1]      & 0x00FF) | 
							 (array[0] << 8 & 0xFF00));
		return bytesValue;
	}

}