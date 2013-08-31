/**
 * Taller de Sistemas Operativos Avanzados - 2013B
 * Simental Magaña Marcos Eleno Joaquín.
 * Sección D04 - Tarea 3
 */
public class CadenaBytes {
	
	private byte[] message = null;
	
	public CadenaBytes(String s){
		setByteArray(s, s.length());
	}
	
	public void setByteArray(String str, int length){
		message = new byte[length+1];
		message[0] = (byte) length;
		System.arraycopy(str.getBytes(), 0, message, 1, length);
	}
	
	public byte[] getByteArray(){
		return message;
	}
	
	public void printByteArray(){
		for(int i = 0 ; i < message.length ; i++){
			System.out.print(message[i]+" ");
		}
	}
	
	public String byteArrayToString(){
		int length = message[0];
		String originalMessage = "";
		for (int i = 1; i < length + 1; i++){
			originalMessage+= (char) message[i];
		}
		return originalMessage;
	}
		
	public static void main(String[] args) {
		CadenaBytes handler = new CadenaBytes("the answer to the life, the universe and everything is 42.");
		handler.printByteArray();
		System.out.println();
		System.out.println(handler.byteArrayToString());
	}

}
