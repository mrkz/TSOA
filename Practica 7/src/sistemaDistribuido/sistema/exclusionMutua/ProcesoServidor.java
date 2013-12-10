package sistemaDistribuido.sistema.exclusionMutua;


import java.util.LinkedList;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.util.Escribano;

/**
 * 
 */
public class ProcesoServidor extends Proceso{

	
	private LinkedList<byte[]> colaArchivo, colaPuerto, colaImpresora, colaMemoria;
	private boolean archivoOcupado, puertoOcupado, impresoraOcupada, memoriaOcupada;
	private static final short ARCHIVO = 0, PUERTO = 1, IMPRESORA = 2, MEMORIA = 3;
	
	public ProcesoServidor(Escribano esc){
		super(esc);
		colaArchivo = new LinkedList<byte[]>();
		colaPuerto = new LinkedList<byte[]>();
		colaImpresora= new LinkedList<byte[]>();
		colaMemoria = new LinkedList<byte[]>();
		archivoOcupado = puertoOcupado = impresoraOcupada = memoriaOcupada = false;
	}
	
	private void encolaSolicitud(int recurso, byte[] solicitud){
		byte[] copiaSol = new byte[solicitud.length];
		System.arraycopy(solicitud, 0, copiaSol, 0, solicitud.length);
		switch(recurso){
		case ARCHIVO:
			colaArchivo.offer(copiaSol);
			break;
		case PUERTO:
			colaPuerto.offer(copiaSol);
			break;
		case IMPRESORA:
			colaImpresora.offer(copiaSol);
			break;
		case MEMORIA:
			colaMemoria.offer(copiaSol);
			break;
		}
	}
	
	private byte[] generaPaquete(short tipoPaquete){
		byte[] respuestaRecursoOcupado = new byte[OFFSET + BYTES_IN_SHORT + BYTES_IN_SHORT],
			   bytesRespuesta = new byte[BYTES_IN_SHORT];
		
		imprimeln("Generando mensaje a ser enviado, llenando los campos necesarios");
		switch(tipoPaquete){
		case RECURSO_OK:
			bytesRespuesta = toByte(RECURSO_OK);
			break;
		case RECURSO_OCUPADO:
			bytesRespuesta = toByte(RECURSO_OCUPADO);
			break;
		default:
			imprimeln("Tipo de Paquete Erroneo: "+tipoPaquete);
			System.exit(1);
			break;
		}
		System.arraycopy(bytesRespuesta, 0, respuestaRecursoOcupado, OFFSET+BYTES_IN_SHORT, BYTES_IN_SHORT);
		return respuestaRecursoOcupado;
		
	}
	
	private void procesaLlamada(byte[] solicitud){
		short tipoSol = dameTipoSolicitud(solicitud);;
		int destino = getOrigin(solicitud);
		byte[] respuesta;
		switch(tipoSol){
		case SOLICITUD_ARCHIVO:
			if(!archivoOcupado){
				tomaRecurso(ARCHIVO);
				respuesta = generaPaquete(RECURSO_OK);
			}else{
				encolaSolicitud(ARCHIVO, solicitud);
				respuesta = generaPaquete(RECURSO_OCUPADO);
			}
			enviaPaquete(destino, respuesta);
			break;
		case SOLICITUD_IMPRESORA:
			if(!impresoraOcupada){
				tomaRecurso(IMPRESORA);
				respuesta = generaPaquete(RECURSO_OK);
			}else{
				encolaSolicitud(IMPRESORA, solicitud);
				respuesta = generaPaquete(RECURSO_OCUPADO);
			}
			enviaPaquete(destino, respuesta);
			break;
		case SOLICITUD_MEMORIA:
			if(!memoriaOcupada){
				tomaRecurso(MEMORIA);
				respuesta = generaPaquete(RECURSO_OK);
			}else{
				encolaSolicitud(MEMORIA, solicitud);
				respuesta = generaPaquete(RECURSO_OCUPADO);
			}
			enviaPaquete(destino, respuesta);
			break;
		case SOLICITUD_PUERTO:
			if(!puertoOcupado){
				tomaRecurso(PUERTO);
				respuesta = generaPaquete(RECURSO_OK);
			}else{
				encolaSolicitud(PUERTO, solicitud);
				respuesta = generaPaquete(RECURSO_OCUPADO);
			}
			enviaPaquete(destino, respuesta);
			break;
		case LIBERA_ARCHIVO:
			if(colaArchivo.size() > 0){
				destino = getOrigin(colaArchivo.poll());
				enviaPaquete(destino, generaPaquete(RECURSO_OK));
				imprimeln("Se libero recurso ARCHIVO, fue tomado por proceso "+destino);
			}else{
				liberaRecurso(ARCHIVO);
			}
			break;
		case LIBERA_IMPRESORA:
			if(colaImpresora.size() > 0){
				destino = getOrigin(colaImpresora.poll());
				enviaPaquete(destino, generaPaquete(RECURSO_OK));
				imprimeln("Se libero recurso IMPRESORA, fue tomado por proceso "+destino);
			}else{
				liberaRecurso(IMPRESORA);
			}
			break;
		case LIBERA_MEMORIA:
			if(colaMemoria.size() > 0){
				destino = getOrigin(colaMemoria.poll());
				enviaPaquete(destino, generaPaquete(RECURSO_OK));
				imprimeln("Se libero recurso MEMORIA, fue tomado por proceso "+destino);
			}else{
				liberaRecurso(MEMORIA);
			}
			break;
		case LIBERA_PUERTO:
			if(colaPuerto.size() > 0){
				destino = getOrigin(colaPuerto.poll());
				enviaPaquete(destino, generaPaquete(RECURSO_OK));
				imprimeln("Se libero recurso PUERTO, fue tomado por proceso "+destino);
			}else{
				liberaRecurso(PUERTO);
			}
			break;
		}
	}
	
	private void enviaPaquete(int destino, byte[] paquete){
		imprimeln("Señalamiento al núcleo para envío de mensaje");
		Nucleo.send(destino, paquete);
	}
	
	private short dameTipoSolicitud(byte[] solicitud){
		short tipoSol;
		byte[] bytesTipoSol = new byte[BYTES_IN_SHORT];
		System.arraycopy(solicitud, OFFSET+BYTES_IN_SHORT, bytesTipoSol, 0, BYTES_IN_SHORT);
		tipoSol = ToShort(bytesTipoSol);
		return tipoSol;
	}
	
	private void tomaRecurso(short recurso){
		switch(recurso){
		case IMPRESORA:
			impresoraOcupada = true;
			break;
		case ARCHIVO:
			archivoOcupado = true;
			break;
		case MEMORIA:
			memoriaOcupada = true;
			break;
		case PUERTO:
			puertoOcupado = true;
			break;
		}
	}
	
	private void liberaRecurso(short recurso){
		switch(recurso){
		case IMPRESORA:
			impresoraOcupada = false;
			break;
		case ARCHIVO:
			archivoOcupado = false;
			break;
		case MEMORIA:
			memoriaOcupada = false;
			break;
		case PUERTO:
			puertoOcupado = false;
			break;
		}
	}

	/**
	 * 
	 */
	public void run(){
		byte[] solicitudAlServidor = new byte[MAX_BUFFER];
		imprimeln("Inicio de Proceso servidor.");
		while(continuar()){
			imprimeln("Invocando a Receive.");
			Nucleo.receive(dameID(),solicitudAlServidor);
			imprimeln("Procesando petición recibida del cliente");
			procesaLlamada(solicitudAlServidor);
		}
	}
}
