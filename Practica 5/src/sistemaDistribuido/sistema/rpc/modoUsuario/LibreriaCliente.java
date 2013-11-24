package sistemaDistribuido.sistema.rpc.modoUsuario;

//import sistemaDistribuido.sistema.rpc.modoMonitor.RPC;  //para pr�ctica 4
import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.rpc.modoMonitor.RPC;
import sistemaDistribuido.sistema.rpc.modoUsuario.Libreria;
import sistemaDistribuido.util.Escribano;

public class LibreriaCliente extends Libreria{

	/**
	 * 
	 */
	protected static final int MAX_BUFFER = 1024;
	// Agregado P4 Simental Magaña Marcos
	public static final String NAMESERVER = "KEPLER",
							   VERSION = "3.1";
	public static final int RPC_ERROR = -99999;
	
	public LibreriaCliente(Escribano esc){
		super(esc);
	}
	
	/*
	 * Agregado práctica 3
	 * Modificado práctica 4
	 * Simental Magaña Marcos Eleno Joaquín
	 */
	@Override
	protected void sumatoria() {
		int ans = 0;
		int asaDest;
		asaDest = RPC.importarInterfaz(NAMESERVER, VERSION);
		if(asaDest != RPC_ERROR){
			imprimeln("Preparando mensaje para Sumatoria");
			byte[] respServidor = new byte[MAX_BUFFER];
			byte[] data = packageData(SUMATORIA, arrayIntToArrayBytes(popArray()));
			byte[] byteOrigin = new byte[BYTES_IN_INT];
			imprimeln("Enviando mensaje");
			Nucleo.send(asaDest, data);
			System.arraycopy(data, 0, byteOrigin, 0, BYTES_IN_INT);
			Nucleo.receive(Nucleo.dameIdProceso(), respServidor);
			imprimeln("Recibiendo mensaje");
			ans = unpackageAnswer(respServidor);
		}
		else{
			ans =  RPC_ERROR;
		}
		parameters.push(new Integer(ans));
		
	}
	
	/*
	 * Agregado práctica 3
	 * Modificado práctica 4
	 * Simental Magaña Marcos Eleno Joaquín
	 */
	@Override
	protected void multiplicatoria() {
		int ans = 0;
		int asaDest;
		asaDest = RPC.importarInterfaz(NAMESERVER, VERSION);
		if(asaDest != RPC_ERROR){
			imprimeln("Preparando mensaje para Multiplicatoria");
			byte[] respServidor = new byte[MAX_BUFFER];
			byte[] data = packageData(MULTIPLICATORIA, arrayIntToArrayBytes(popArray()));
			byte[] byteOrigin = new byte[BYTES_IN_INT];
			imprimeln("Enviando mensaje");
			Nucleo.send(asaDest, data);
			System.arraycopy(data, 0, byteOrigin, 0, BYTES_IN_INT);
			Nucleo.receive(Nucleo.dameIdProceso(), respServidor);
			imprimeln("Recibiendo mensaje");
			ans = unpackageAnswer(respServidor);
		}
		else{
			ans = RPC_ERROR;
		}
		parameters.push(new Integer(ans));
		
	}

	/*
	 * Agregado práctica 3
	 * Modificado práctica 4
	 * Simental Magaña Marcos Eleno Joaquín
	 */
	@Override
	protected void absoluto() {
		int ans = 0;
		int asaDest;
		asaDest = RPC.importarInterfaz(NAMESERVER, VERSION);
		if(asaDest != RPC_ERROR){
			imprimeln("Preparando mensaje para Absoluto");
			byte[] respServidor = new byte[MAX_BUFFER];
			byte[] data = packageData(ABSOLUTO, arrayIntToArrayBytes(popArray()));
			byte[] byteOrigin = new byte[BYTES_IN_INT];
			imprimeln("Enviando mensaje");
			Nucleo.send(asaDest, data);
			System.arraycopy(data, 0, byteOrigin, 0, BYTES_IN_INT);
			Nucleo.receive(Nucleo.dameIdProceso(), respServidor);
			imprimeln("Recibiendo mensaje");
			ans = unpackageAnswer(respServidor);
		}
		else{
			ans = RPC_ERROR;
		}
		parameters.push(new Integer(ans));
		
	}

	/*
	 * Agregado práctica 3
	 * Modificado práctica 4
	 * Simental Magaña Marcos Eleno Joaquín
	 */
	@Override
	protected void division() {
		int ans = 0;
		int asaDest;
		asaDest = RPC.importarInterfaz(NAMESERVER, VERSION);
		if(asaDest != RPC_ERROR){
			byte[] respServidor = new byte[MAX_BUFFER];
			imprimeln("Preparando mensaje para Division");
			byte[] data = packageData(DIVISION, arrayIntToArrayBytes(popArray()));
			byte[] byteOrigin = new byte[BYTES_IN_INT];
			imprimeln("Enviando mensaje");
			Nucleo.send(asaDest, data);
			System.arraycopy(data, 0, byteOrigin, 0, BYTES_IN_INT);
			Nucleo.receive(Nucleo.dameIdProceso(), respServidor);
			imprimeln("Recibiendo mensaje");
			ans = unpackageAnswer(respServidor);
		}
		else{
			ans = RPC_ERROR;
		}
		parameters.push(new Integer(ans));
		
	}
	
	/*
	 * Agregado práctica 3
	 * Simental Magaña Marcos Eleno Joaquín
	 * Método para sacar elementos de la pila
	 */
	public int[] popArray(){
		int [] array = new int[parameters.size()];
		for(int i = 0 ; i  < array.length; i++){
			array[i] = parameters.pop();
		}
		return array;
	}
	
	/*
	 * Agregado práctica 3
	 * Simental Magaña Marcos Eleno Joaquín
	 */
	public int[] intToIntArray(int a){
		int[] array = new int[1];
		array[0] = a;
		return array;
	}
	
	/*
	 * Agregado práctica 3
	 * Simental Magaña Marcos Eleno Joaquín
	 */
	public byte[] arrayIntToArrayBytes(int[] array){
		byte[] arrayBytes = new byte[array.length * BYTES_IN_INT];
		byte[] byteInt;
		for(int i = 0; i < array.length; i++){
			byteInt = intToByteArray(array[i]);
			for(int j = 0; j < BYTES_IN_INT; j++){
				arrayBytes[(i*4)+j] = byteInt[j];
			}
		}
		return arrayBytes;
	}
	
	/*
	 * Agregado práctica 3
	 * Simental Magaña Marcos Eleno Joaquín
	 */
	private int unpackageAnswer(byte[] data){
		imprimeln("Desempaquetando respuesta");
		int answerFromSolServidor = 0;
		byte[] byteAnswer = new byte[BYTES_IN_INT];
		System.arraycopy(data, OFFSET + (BYTES_IN_SHORT * 2), byteAnswer, 0, BYTES_IN_INT);
		answerFromSolServidor = byteArrayToInt(byteAnswer);
		return answerFromSolServidor;
	}
	
	
	/*
	 * Agregado práctica 3
	 * Simental Magaña Marcos Eleno Joaquín
	 * 
	 * package
	 * [4 source][4 destiny][2 codop][[2 data length] data]
	 */
	private byte[] packageData(short codop, byte[] data){
		short dataTam = (short) data.length;
		byte [] codopBytes  = shortToByteArray(codop),
				byteDataTam = shortToByteArray(dataTam),
				solCliente  = new byte[OFFSET + (BYTES_IN_SHORT * 2) + data.length];
		/* insert codop */
		for(int i = OFFSET, j = 0; j < codopBytes.length; j++, i++){
			solCliente[i] = codopBytes[j];
		}
		/* insert data length */
		for(int i = OFFSET+BYTES_IN_SHORT,j = 0; j < byteDataTam.length; j++,i++){
			solCliente[i] = byteDataTam[j];
		}
		/* insert data*/
		for(int i = OFFSET+(BYTES_IN_SHORT * 2), j = 0; j < data.length; j++, i++){
			solCliente[i] = data[j];
		}
		
		return solCliente;
	}

}