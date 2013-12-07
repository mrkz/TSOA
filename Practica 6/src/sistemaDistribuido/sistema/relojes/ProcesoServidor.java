package sistemaDistribuido.sistema.relojes;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.util.Escribano;
import sistemaDistribuido.util.Pausador;

/**
 * 
 */
public class ProcesoServidor extends Proceso{

	/**
	 * 
	 */
	public ProcesoServidor(Escribano esc){
		super(esc);
	}

	
	/**
	 * Edited: Simental Magaña Marcos Eleno Joaquín
	 * Método para empaquetar respuesa que dará el servidor
	 * entrada:	byte[]	arreglo de bytes de la respuesta que el servidor entregará [data]
	 * salida:	byte[]  paquete de respuesta [OFFSET dataTAM data]
	 */
	private byte[] packageData(byte[] data){
		short dataTam = (short) data.length;
		byte[] byteDataTam = toByte(dataTam);
		byte[] newPackage = new byte[OFFSET+(BYTES_IN_SHORT*2)+dataTam];

		for(int i = 0; i < OFFSET; i++){
			newPackage[i] = 0;
		}
		/* insert dataTam (short) */
		for(int i = OFFSET+BYTES_IN_SHORT, j = 0; j < byteDataTam.length; i++,j++){
			newPackage[i] = byteDataTam[j];
		}
		/* insert data (data.length bytes) */
		for(int i = OFFSET+(BYTES_IN_SHORT*2), j = 0; j < data.length; i++, j++){
			newPackage[i] = data[j];
		}
		return newPackage;
	}
	
	/**
	 * Edited: Simental Magaña Marcos Eleno Joaquín
	 * Para práctica 2
	 * Se agrega método para obtener dirección de origen de mensaje
	 * para regresar respuesta del servidor
	 */
	private int getOrigin(byte[] solServidor){
		byte[] origin = new byte[4];
		System.arraycopy(solServidor, 0, origin, 0, 4);
		return bytesToInt(origin);
	}
	
	/**
	 * Edited: Simental Magaña Marcos Eleno Joaquín
	 * Para práctica 2
	 * Se agrega método para convertir direccion de origen a entero
	 */
	private int bytesToInt(byte[] array){
		int bytesValue = 0x0;
		bytesValue = (int)( (array[3]       & 0x000000FF) | 
							(array[2] << 8  & 0x0000FF00) | 
							(array[1] << 16 & 0x00FF0000) | 
							(array[0] << 24 & 0xFF000000));
		return bytesValue;
	}

	/**
	 * 
	 */
	public void run(){
		imprimeln("Inicio de Proceso servidor.");
		byte[] solServidor=new byte[MAX_BUFFER];
		byte[] respServidor; //1024
		int origin;
		while(continuar()){
/*			imprimeln("Invocando a Receive.");
			Nucleo.receive(dameID(),solServidor);
			imprimeln("Procesando petición recibida del cliente");
			respServidor = procesaLlamada(solServidor);
			imprimeln("Generando mensaje a ser enviado, llenando los campos necesarios");
			respServidor = packageData(respServidor);
			Pausador.pausa(1000);  //sin esta línea es posible que Servidor solicite send antes que Cliente solicite receive
			imprimeln("Señalamiento al núcleo para envío de mensaje");
			origin = getOrigin(solServidor);
			System.out.println("El origen del paquete es: "+origin);
			Nucleo.send(origin,respServidor);
*/
		}
	}
}
