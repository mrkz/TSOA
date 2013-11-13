package sistemaDistribuido.sistema.rpc.modoUsuario;

//import sistemaDistribuido.sistema.rpc.modoMonitor.RPC;  //para pr�ctica 4
import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.rpc.modoUsuario.Libreria;
import sistemaDistribuido.util.Escribano;

public class LibreriaCliente extends Libreria{

	/**
	 * 
	 */
	protected static final int MAX_BUFFER = 1024;
	
	public LibreriaCliente(Escribano esc){
		super(esc);
	}

	/**
	 * Ejemplo de resguardo del cliente suma
	 */
	protected void suma(){
		int asaDest=0;
		//...

		//asaDest=RPC.importarInterfaz(nombreServidor, version)  //para pr�ctica 4
		Nucleo.send(asaDest,null);
		//...
	}

	@Override
	protected void sumatoria() {
		int ans = 0;
		byte[] respServidor = new byte[MAX_BUFFER];
		byte[] data = packageData(SUMATORIA, arrayIntToArrayBytes(popArray()));
		byte[] byteOrigin = new byte[BYTES_IN_INT];
		Nucleo.send(248, data);
		System.arraycopy(data, 0, byteOrigin, 0, BYTES_IN_INT);
		Nucleo.receive(Nucleo.dameIdProceso(), respServidor);
		ans = unpackageAnswer(respServidor);
		parameters.push(new Integer(ans));
		
	}

	@Override
	protected void multiplicatoria() {
		int ans = 0;
		byte[] respServidor = new byte[MAX_BUFFER];
		byte[] data = packageData(MULTIPLICATORIA, arrayIntToArrayBytes(popArray()));
		byte[] byteOrigin = new byte[BYTES_IN_INT];
		Nucleo.send(248, data);
		System.arraycopy(data, 0, byteOrigin, 0, BYTES_IN_INT);
		Nucleo.receive(Nucleo.dameIdProceso(), respServidor);
		ans = unpackageAnswer(respServidor);
		parameters.push(new Integer(ans));
		
	}

	@Override
	protected void absoluto() {
		int ans = 0;
		byte[] respServidor = new byte[MAX_BUFFER];
		byte[] data = packageData(ABSOLUTO, arrayIntToArrayBytes(popArray()));
		byte[] byteOrigin = new byte[BYTES_IN_INT];
		Nucleo.send(248, data);
		System.arraycopy(data, 0, byteOrigin, 0, BYTES_IN_INT);
		Nucleo.receive(Nucleo.dameIdProceso(), respServidor);
		ans = unpackageAnswer(respServidor);
		parameters.push(new Integer(ans));
		
	}

	@Override
	protected void division() {
		int ans = 0;
		byte[] respServidor = new byte[MAX_BUFFER];
		byte[] data = packageData(DIVISION, arrayIntToArrayBytes(popArray()));
		byte[] byteOrigin = new byte[BYTES_IN_INT];
		Nucleo.send(248, data);
		System.arraycopy(data, 0, byteOrigin, 0, BYTES_IN_INT);
		Nucleo.receive(Nucleo.dameIdProceso(), respServidor);
		ans = unpackageAnswer(respServidor);
		parameters.push(new Integer(ans));
		
	}
	
	public int[] popArray(){
		int [] array = new int[parameters.size()];
		for(int i = 0 ; i  < array.length; i++){
			array[i] = parameters.pop();
		}
		return array;
	}
	
	public int[] intToIntArray(int a){
		int[] array = new int[1];
		array[0] = a;
		return array;
	}
	
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
	
	private int unpackageAnswer(byte[] data){
		int answerFromSolServidor = 0;
		byte[] byteAnswer = new byte[BYTES_IN_INT];
		System.arraycopy(data, OFFSET + (BYTES_IN_SHORT * 2), byteAnswer, 0, BYTES_IN_INT);
		answerFromSolServidor = byteArrayToInt(byteAnswer);
		return answerFromSolServidor;
	}
	
	
	/*
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