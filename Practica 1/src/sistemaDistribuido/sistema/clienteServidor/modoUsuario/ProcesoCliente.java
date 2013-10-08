package sistemaDistribuido.sistema.clienteServidor.modoUsuario;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.util.Escribano;

/**
 * 
 */
public class ProcesoCliente extends Proceso{

	/**
	 * Edited: Simental Magaña Marcos Eleno Joaquín
	 * Atributos codop y data agregados
	 */
	private short codop;
	private String data;
	
	/**
	 * Edited: Simental Magaña Marcos Eleno Joaquín
	 * Método para empaquetar mensaje
	 * entrada: vacía
	 * salida: byte[]
	 * Estructura del paquete:
	 * 		OFFSET(p/practica 2) | short | short	| byte* (cero o más bytes)
	 * 			DIRS			 | CODOP | DATA_TAM	| DATA
	 * [0][0][0][0] [0][0][0][0]  [0][0]   [0][0]     [...]
	 */
	private byte[] packageData(){
		short dataTam = (short) data.length();
		byte [] byteCodop, byteData, byteDataTam;
		byteCodop 	= toByte(codop);
		byteData 	= data.getBytes();
		byteDataTam = toByte(dataTam);
		byte [] solCliente = new byte[OFFSET + byteCodop.length + byteDataTam.length +byteData.length];
		/* FIXME: insert dir1, dir2 (now OFFSET) */
		/* insert codop */
		for(int i = OFFSET-1, j = 0; j < byteCodop.length; j++, i++){
			solCliente[i] = byteCodop[j];
		}
		/* insert data length */
		for(int i = OFFSET+byteCodop.length-1,j = 0; j < byteDataTam.length; j++,i++){
			solCliente[i] = byteDataTam[j];
		}
		/* insert data*/
		for(int i = OFFSET+byteCodop.length+byteDataTam.length-1, j = 0; j < byteData.length; j++, i++){
			solCliente[i] = byteData[j];
		}
		return solCliente;
	}
	
	public void setData(short codop, String data){
		this.codop = codop;
		this.data  = data;
	}
	
	public ProcesoCliente(Escribano esc){
		super(esc);
		start();
	}

	/**
	 * 
	 */
	public void run(){
		imprimeln("Inicio de Proceso Cliente");
		Nucleo.suspenderProceso();
		imprimeln("Generando mensaje a ser enviado, llenando los campos necesarios");
		byte[] solCliente = packageData();
		byte[] respCliente=new byte[MAX_BUFFER]; //1024
		byte dato;
		imprimeln("Señalamiento al núcleo para envío de mensaje");
		Nucleo.send(248,solCliente); //esta no se mueve
		imprimeln("Invocando a Receive.");
		Nucleo.receive(dameID(),respCliente); // esta tampoco
		imprimeln("Procesando respuesta recibida del servidor");
		/* TODO: procesar respuesta del servidor */
		dato=respCliente[0];
		imprimeln("Respuesta del servidor: "+dato);
				// aquí termina el codigo
	}
}
