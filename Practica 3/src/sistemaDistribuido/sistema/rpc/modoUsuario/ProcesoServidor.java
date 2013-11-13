package sistemaDistribuido.sistema.rpc.modoUsuario;

//import sistemaDistribuido.sistema.rpc.modoMonitor.RPC;   //para pr�ctica 4
import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.util.Escribano;

/**
 * 
 */
public class ProcesoServidor extends Proceso{
	private LibreriaServidor ls;   //para pr�ctica 3
	public static final short SUMATORIA = 0,
			  				  MULTIPLICATORIA = 1,
			  				  DIVISION = 2,
			  				  ABSOLUTO = 3;
	public static final int BYTES_IN_INT = 4;

	/**
	 * 
	 */
	public ProcesoServidor(Escribano esc){
		super(esc);
		ls=new LibreriaServidor(esc);   //para pr�ctica 3
		start();
	}

	/**
	 * Resguardo del servidor
	 * Modificado práctica 3
	 * Simental Magaña Marcos Eleno Joaquín
	 */
	public void run(){
		byte[] message = new byte[MAX_BUFFER];
		byte[] respServidor;
		byte[] bytesDestiny = new byte[BYTES_IN_INT];
		int destiny;
		imprimeln("Proceso servidor en ejecucion.");
		//idUnico=RPC.exportarInterfaz("FileServer", "3.1", asa)  //para pr�ctica 4

		while(continuar()){
			Nucleo.receive(dameID(),message);
			System.arraycopy(message, 0, bytesDestiny, 0, BYTES_IN_INT);
			destiny = byteArrayToInt(bytesDestiny);
			respServidor = procesaLlamada(message);
			Nucleo.send( destiny, respServidor);
		}

		//RPC.deregistrarInterfaz(nombreServidor, version, idUnico)  //para pr�ctica 4
	}

	private byte[] procesaLlamada(byte[] message) {
		short codop;
		int res;
		int [] parameters;
		byte [] codopBytes = new byte[BYTES_IN_SHORT],
				answer = packageAnswer(0); // default package with zeros
		System.arraycopy(message, OFFSET, codopBytes, 0, BYTES_IN_SHORT);
		codop = byteArrayToShort(codopBytes);
		switch(codop){
		case SUMATORIA:
			parameters = unpackageArray(message);
			res = ls.sumatoria(parameters.length, parameters);
			answer = packageAnswer(res);
			break;
		case MULTIPLICATORIA:
			parameters = unpackageArray(message);
			res = ls.multiplicatoria(parameters.length, parameters);
			answer = packageAnswer(res);
			break;
		case DIVISION:
			parameters = unpackageArray(message);
			res = ls.division(parameters[0], parameters[1]);
			answer = packageAnswer(res);
			break;
		case ABSOLUTO:
			parameters = unpackageArray(message);
			res = ls.absoluto(parameters[0]);
			answer = packageAnswer(res);
			break;
		default:
			break;
		}
		return answer;
	}
	
	private byte[] packageAnswer(int res) {
		/*
		 * Package structure:
		 * [4 origin][4 destiny][2 codop][[2 (short) data size] data]
		 */
		byte[] answer = new byte[OFFSET + (BYTES_IN_SHORT*2) + BYTES_IN_INT],
			   byteAns = new byte[BYTES_IN_INT],
			   byteAnsSize = new byte[BYTES_IN_SHORT];
		
		byteAns = intToByteArray(res);
		byteAnsSize = toByte((short)1); // 1 = number of arguments
		/* insert data size bytes*/
		for(int i = OFFSET + BYTES_IN_SHORT , j = 0; j < byteAnsSize.length; i++, j++){
			answer[i] = byteAnsSize[j];
		}
		/* insert data bytes*/
		for(int i = OFFSET + BYTES_IN_SHORT + byteAnsSize.length, j = 0;
				j < byteAns.length; i++, j++){
			answer[i] = byteAns[j];
		}
		return answer;
	}

	public int[] byteArrayToIntArray(byte[] array){
		short nParameters;
		byte [] byteNParameters = new byte[BYTES_IN_SHORT];
		System.arraycopy(array, 0, byteNParameters, 0, BYTES_IN_SHORT);
		nParameters = byteArrayToShort(byteNParameters);
		int[] intArray = new int[nParameters / BYTES_IN_INT];
		byte[] intBytes = new byte[BYTES_IN_INT];
		int value;
		for(int i= 0 ; i < nParameters/BYTES_IN_INT; i++){
			for(int j = 0; j < BYTES_IN_INT; j++){
				intBytes[j] = array[(i * BYTES_IN_INT) + j +2]; // 2 bytes del short de tamaño
			}
			value = byteArrayToInt(intBytes);
			intArray[i] = value;
		}
		return intArray;
	}
	
	public int[] unpackageArray(byte[] message){
		byte[] data = new byte[message.length - (OFFSET + BYTES_IN_SHORT)];
		int [] dataInt, toReturn;
		System.arraycopy(message, OFFSET+BYTES_IN_SHORT, data, 0, message.length - (OFFSET + BYTES_IN_SHORT));
		dataInt = byteArrayToIntArray(data);
		toReturn = new int[dataInt.length - 1];
		System.arraycopy(dataInt, 1, toReturn, 0, dataInt.length-1);
		return toReturn;
		//return dataInt;
	}
	
	private int byteArrayToInt(byte[] intBytes) {
		int bytesValue = 0x0;
		bytesValue = (int)( (intBytes[3]       & 0x000000FF) | 
							(intBytes[2] << 8  & 0x0000FF00) | 
							(intBytes[1] << 16 & 0x00FF0000) | 
							(intBytes[0] << 24 & 0xFF000000));
		return bytesValue;
	}
	
	public short byteArrayToShort(byte[] array){
		short bytesValue = 0x0;
		bytesValue = (short)((array[1]      & 0x00FF) | 
							 (array[0] << 8 & 0xFF00));
		return bytesValue;
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
}
