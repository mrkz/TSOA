package sistemaDistribuido.sistema.clienteServidor.modoUsuario;

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
	
	private boolean createFile(String fileName){
		return true;
	}
	
	private boolean deleteFile(String fileName){
		return true;
	}
	
	private boolean writeFile(String fileName, String lineToWrite){
		return true;
	}
	
	private String readFile(String fileName){
		return "leido";
	}
	
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
			log+= (deleteFile(fileName)) ? (fileName +"eliminado") :
										   (fileName +"no eliminado");
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
	
	private byte[] procesaLlamada(byte[] arrayBytes){
		short codop, dataLength;
		String message = "";
		byte[] data,
			   byteCodop 	= new byte[BYTES_IN_SHORT],
			   byteDataTam	= new byte[BYTES_IN_SHORT];
		
		/* FIXME: extract dir1, dir2 (now OFFSET)*/
		/* extract codop */
		for(int i = OFFSET-1, j = 0; j < byteCodop.length; j++, i++){
			byteCodop[j] = arrayBytes[i];
		}
		/* extract data length */
		for(int i = OFFSET+byteCodop.length-1, j=0; j < byteDataTam.length; j++, i++){
			byteDataTam[j] = arrayBytes[i];
		}
		codop 		= ToShort(byteCodop);
		dataLength	= ToShort(byteDataTam);
		data = new byte[dataLength];
		/* get data */
		for(int i = OFFSET+byteCodop.length+byteDataTam.length-1, j = 0; j < dataLength; j++, i++){
			data[j] = arrayBytes[i];
		}
		message = (HacerOperacion(codop, (new String(data)).split(",")));
		System.out.println(message);
		return message.getBytes();
	}

	/**
	 * 
	 */
	public void run(){
		imprimeln("Inicio de Proceso servidor.");
		byte[] solServidor=new byte[MAX_BUFFER];
		byte[] respServidor; //1024
		while(continuar()){
			imprimeln("Invocando a Receive.");
			Nucleo.receive(dameID(),solServidor);
			imprimeln("Procesando petición recibida del cliente");
			respServidor = procesaLlamada(solServidor);
			//dato=solServidor[0];//desempaqueta
			// se hace el manejo de datos
			//imprimeln("el cliente envió un "+dato);
			//respServidor=new byte[20]; //1024
			//respServidor[0]=(byte)(dato*dato);
			Pausador.pausa(1000);  //sin esta línea es posible que Servidor solicite send antes que Cliente solicite receive
			imprimeln("Señalamiento al núcleo para envío de mensaje");
			Nucleo.send(0,respServidor);
		}
	}
}
