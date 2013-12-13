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
		case ESPERO_RECURSO: break;
		case SOLICITUD_ARCHIVO:
			if(!archivoOcupado){
				tomaRecurso(ARCHIVO);
				respuesta = generaPaquete(RECURSO_OK);
			}else{
				imprimeln("recurso ARCHIVO ocupado");
				imprimeln("Encolando solicitud de recurso ARCHIVO");
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
				imprimeln("recurso IMPRESORA ocupado");
				imprimeln("Encolando solicitud de recurso IMPRESORA");
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
				imprimeln("recurso MEMORIA ocupado");
				imprimeln("Encolando solicitud de recurso MEMORIA");
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
				imprimeln("recurso PUERTO ocupado");
				imprimeln("Encolando solicitud de recurso PUERTO");
				encolaSolicitud(PUERTO, solicitud);
				respuesta = generaPaquete(RECURSO_OCUPADO);
			}
			enviaPaquete(destino, respuesta);
			break;
		case LIBERA_ARCHIVO:
			if(colaArchivo.size() > 0){
				destino = getOrigin(colaArchivo.poll());
				imprimeln("Se libero recurso ARCHIVO, fue tomado por proceso "+destino);
				enviaPaquete(destino, generaPaquete(RECURSO_OK));
			}else{
				liberaRecurso(ARCHIVO);
			}
			break;
		case LIBERA_IMPRESORA:
			if(colaImpresora.size() > 0){
				destino = getOrigin(colaImpresora.poll());
				imprimeln("Se libero recurso IMPRESORA, fue tomado por proceso "+destino);
				enviaPaquete(destino, generaPaquete(RECURSO_OK));
			}else{
				liberaRecurso(IMPRESORA);
			}
			break;
		case LIBERA_MEMORIA:
			if(colaMemoria.size() > 0){
				destino = getOrigin(colaMemoria.poll());
				imprimeln("Se libero recurso MEMORIA, fue tomado por proceso "+destino);
				enviaPaquete(destino, generaPaquete(RECURSO_OK));
			}else{
				liberaRecurso(MEMORIA);
			}
			break;
		case LIBERA_PUERTO:
			if(colaPuerto.size() > 0){
				destino = getOrigin(colaPuerto.poll());
				imprimeln("Se libero recurso PUERTO, fue tomado por proceso "+destino);
				enviaPaquete(destino, generaPaquete(RECURSO_OK));
			}else{
				liberaRecurso(PUERTO);
			}
			break;
		}
	}
	
	private void enviaPaquete(int destino, byte[] paquete){
		imprimeln("Señalamiento al núcleo para envío de mensaje");
		System.out.println("Enviando paquete a proceso "+destino);
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
		imprime("Entregando recurso ");
		switch(recurso){
		case IMPRESORA:
			impresoraOcupada = true;
			imprimeln("IMPRESORA");
			break;
		case ARCHIVO:
			archivoOcupado = true;
			imprimeln("ARCHIVO");
			break;
		case MEMORIA:
			memoriaOcupada = true;
			imprimeln("MEMORIA");
			break;
		case PUERTO:
			puertoOcupado = true;
			imprimeln("PUERTO");
			break;
		}
	}
	
	private void liberaRecurso(short recurso){
		imprime("Liberando recurso ");
		switch(recurso){
		case IMPRESORA:
			impresoraOcupada = false;
			imprimeln("IMPRESORA");
			break;
		case ARCHIVO:
			archivoOcupado = false;
			imprimeln("ARCHIVO");
			break;
		case MEMORIA:
			memoriaOcupada = false;
			imprimeln("MEMORIA");
			break;
		case PUERTO:
			puertoOcupado = false;
			imprimeln("PUERTO");
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
