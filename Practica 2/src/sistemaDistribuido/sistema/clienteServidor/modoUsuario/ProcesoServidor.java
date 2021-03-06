package sistemaDistribuido.sistema.clienteServidor.modoUsuario;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
		start();
	}
	
	/**
	 * Edited: Simental Magaña Marcos Eleno Joaquín
	 * Método para crear un archivo
	 * entrada:	String,	 Nombre del archivo a crear
	 * salida:	boolean, true si operación exitosa, false en caso contrario
	 */
	private boolean createFile(String fileName){
		boolean success = false;
		File newFile = new File(fileName);
		if(!newFile.exists()){
			try {
				success = newFile.createNewFile();
			} catch (IOException e) {}
		}
		return success;
	}
	
	/**
	 * Edited: Simental Magaña Marcos Eleno Joaquín
	 * Método para borrar un archivo
	 * entrada:	String,	 Nombre del archivo a borrar
	 * salida:	boolean, true si operación exitosa, false en caso contrario
	 */
	private boolean deleteFile(String fileName){
		boolean success = false;
		File newFile = new File(fileName);
		try{
			success = newFile.delete();
		} catch (SecurityException e) {}
		return success;
	}
	
	/**
	 * Edited: Simental Magaña Marcos Eleno Joaquín
	 * Método para escribir a un archivo
	 * entrada:	(String, String)
	 * 	String:	Nombre del archivo en el que se va a escribir
	 *  String:	Cadena de texto a escribir en el archivo
	 * salida:	boolean, true si operación exitosa, false en caso contrario
	 */
	private boolean writeFile(String fileName, String lineToWrite){
		boolean success = false;
		BufferedWriter br;
		File newFile = new File(fileName);
		try {
			if(newFile.exists()){
				br = new BufferedWriter(new FileWriter(newFile));
				br.write(lineToWrite);
				br.close();
				success = true;
			}
		} catch (IOException e) {}
		return success;
	}
	
	/**
	 * Edited: Simental Magaña Marcos Eleno Joaquín
	 * Método para leer desde un archivo
	 * entrada:	String:	Nombre del archivo a leer
	 * salida:	String:	Cadena de texto leída desde el archivo
	 */
	private String readFile(String fileName){
		String success = "Error al leer desde archivo '"+fileName+"'";
		BufferedReader br;
		File newFile = new File(fileName);
		try {
			if(newFile.exists()){
				br = new BufferedReader(new FileReader(newFile));
				success = br.readLine();
				br.close();
			}
		} catch (IOException e) {}
		return success;
	}
	
	/**
	 * Edited: Simental Magaña Marcos Eleno Joaquín
	 * Método para convertir un short a un arreglo de bytes
	 * entrada: (short, String[])
	 *     short: 	operación a realizar por el servidor
	 *     String[]	arreglo de argumentos para la operación
	 * salida:	(String) respuesta de la operación realizada
	 */
	private String HacerOperacion(short operacion, String[] args){

		String fileName = "default.txt",
			   toWrite = "default line";
		String log = "";
		int length = (args.length > 2) ? 2 : args.length;
		switch(length){
		case 2:
			toWrite = args[1];
		case 1:
			fileName = args[0];
			break;
		default: break;
		}
		switch(operacion){
		case 0:
			log+=(createFile(fileName)) ?  (fileName+" creado") : 
										   (fileName +" no creado");
			imprimeln("Se solicitó servicio 'Crear' con el nombre de archivo: "+fileName);
			break;
		case 1:
			log+= (deleteFile(fileName)) ? (fileName +" eliminado") :
										   (fileName +" no eliminado");
			imprimeln("Se solicitó servicio 'Eliminar' con el nombre de archivo: "+fileName);
			break;
		case 2:
			log+= readFile(fileName);
			imprimeln("Se solicitó servicio 'Leer' con el nombre de archivo: "+fileName);
			break;
		case 3:
			log+= (writeFile(fileName, toWrite)) ? (fileName +" Escrito: "+toWrite) :
												   (fileName +" Error: No se pudo escribir");
			imprimeln("Se solicitó servicio 'Escribir' con el nombre de archivo: "+fileName+
					  " y el texto: '"+toWrite+"'");
			break;
		default: break;
		}
		return log;
	}
	
	/**
	 * Edited: Simental Magaña Marcos Eleno Joaquín
	 * Método para desempaquetar y procesar la llamada requerida por el cliente 
	 * entrada:	byte[]	paquete recibido del cliente
	 * salida:	byte[]	respuesta de la llamada realizada por el servidor
	 */
	private byte[] procesaLlamada(byte[] arrayBytes){
		short codop, dataLength;
		String message = "";
		byte[] data,
			   byteCodop 	= new byte[BYTES_IN_SHORT],
			   byteDataTam	= new byte[BYTES_IN_SHORT];
		
		/* extract codop */
		for(int i = OFFSET, j = 0; j < byteCodop.length; j++, i++){
			byteCodop[j] = arrayBytes[i];
		}
		/* extract data length (short)*/
		for(int i = OFFSET+BYTES_IN_SHORT, j = 0 ; j < BYTES_IN_SHORT; i++, j++){
			byteDataTam[j] = arrayBytes[i];
		}
		codop 		= ToShort(byteCodop);
		dataLength	= ToShort(byteDataTam);
		data = new byte[dataLength];
		/* get data */
		for(int i = OFFSET+(BYTES_IN_SHORT*2), j = 0; j < dataLength; j++, i++){
			data[j] = arrayBytes[i];
		}
		message = (HacerOperacion(codop, (new String(data)).split(",")));
		return message.getBytes();
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
			imprimeln("Invocando a Receive.");
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
		}
	}
}
