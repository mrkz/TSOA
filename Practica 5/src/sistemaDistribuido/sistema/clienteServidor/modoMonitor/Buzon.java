package sistemaDistribuido.sistema.clienteServidor.modoMonitor;

import java.util.LinkedList;
/**
 * Edited: Simental Magaña Marcos Eleno Joaquín
 * Para práctica 5
 * Se agrega Clase Buzon para uso de esta en
 * MicroNucleo.java como atributo tablaBuzones
 */
public class Buzon {
	protected static final int MAX_BUFFER = 1024;
	
	private LinkedList<byte[]> buzon;
	public Buzon(){
		buzon = new LinkedList<byte[]>();
	}
	
	public boolean insert(byte[] message){
		
		boolean copied = false;
		byte[] copy = new byte[message.length];
		if(canInsert()){
			System.arraycopy(message,0,copy,0, message.length);
			buzon.offer(copy);
			copied = true;
		}
		else{
			copied = false;
		}	
		return copied;
	}
	
	public byte[] getNextMessage(){
		byte[] message;
		message = buzon.poll();
		return message;
	}
	
	private boolean canInsert(){
		return (buzon.size() < 3);
	}
	
	public boolean isEmpty(){
		return (buzon.size() == 0);
	}
}
