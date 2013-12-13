package sistemaDistribuido.sistema.exclusionMutua;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
//import sistemaDistribuido.sistema.relojes.Proceso;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.util.Escribano;
import sistemaDistribuido.sistema.exclusionMutua.ClienteFrame;

/**
 * 
 */
public class ProcesoCliente extends Proceso{

	/**
	 * Edited: Simental Magaña Marcos Eleno Joaquín
	 * para práctica 7
	 * solicitoRecurso -> si false, entonces quiero liberar recurso,
	 * 					  si verdadero, entonces quiero pedir un recurso
	 */
	private short recursoSolicitado = ARCHIVO;
	private boolean solicitoRecurso = false;
	private ClienteFrame parent;

	
	public ProcesoCliente(Escribano esc){
		super(esc);
		parent = (ClienteFrame) esc;
	}
	
	public void solicitaRecurso(short recurso){
		recursoSolicitado = recurso;
		solicitoRecurso = true;
	}
	
	public void liberaRecurso(short recurso){
		solicitoRecurso = false;
	}
	
	private void solicitaRecurso(){
		byte[] solicitudCliente = null;
		byte[] respServidor = null;
		imprime("Se solicito recurso ");
		switch(recursoSolicitado){
		case ARCHIVO:
			imprimeln("ARCHIVO");
			solicitudCliente = generaPaquete(SOLICITUD_ARCHIVO);
			break;
		case PUERTO:
			imprimeln("PUERTO");
			solicitudCliente = generaPaquete(SOLICITUD_PUERTO);
			break;
		case IMPRESORA:
			imprimeln("IMPRESORA");
			solicitudCliente = generaPaquete(SOLICITUD_IMPRESORA);
			break;
		case MEMORIA:
			imprimeln("MEMORIA");
			solicitudCliente = generaPaquete(SOLICITUD_MEMORIA);
			break;
		}
		enviaPaquete(solicitudCliente);
		respServidor = recibePaquete();
		if(recursoOcupado(respServidor)){
			imprimeln("Recurso que solicite esta ocupado");
			parent.esperoRecurso();
			enviaPaquete(generaPaquete(ESPERO_RECURSO));
			respServidor = recibePaquete(); // aquí ya tengo el recurso porque segunda respuesta fue un OK
		}
		imprime("Recibi recurso ");
		if(recursoSolicitado == ARCHIVO) imprimeln("ARCHIVO");
		else if(recursoSolicitado == PUERTO) imprimeln("PUERTO");
		else if(recursoSolicitado == IMPRESORA) imprimeln("IMPRESORA");
		else if(recursoSolicitado == MEMORIA) imprimeln("MEMORIA");
		parent.habilitaLiberacion(recursoSolicitado);
	}
	
	private void liberaRecurso(){
		byte[] solicitudCliente = null;
		imprime("Liberando recurso ");
		switch(recursoSolicitado){
		case ARCHIVO:
			imprimeln("ARCHIVO");
			solicitudCliente = generaPaquete(LIBERA_ARCHIVO);
			break;
		case PUERTO:
			imprimeln("PUERTO");
			solicitudCliente = generaPaquete(LIBERA_PUERTO);
			break;
		case IMPRESORA:
			imprimeln("IMPRESORA");
			solicitudCliente = generaPaquete(LIBERA_IMPRESORA);
			break;
		case MEMORIA:
			imprimeln("MEMORIA");
			solicitudCliente = generaPaquete(LIBERA_MEMORIA);
			break;
		}
		enviaPaquete(solicitudCliente);
		imprime("Liberando recurso ");
		if(recursoSolicitado == ARCHIVO) imprimeln("ARCHIVO");
		else if(recursoSolicitado == PUERTO) imprimeln("PUERTO");
		else if(recursoSolicitado == IMPRESORA) imprimeln("IMPRESORA");
		else if(recursoSolicitado == MEMORIA) imprimeln("MEMORIA");
		parent.habilitaSolicitud();
	}
	
	private void enviaPaquete(byte[] paquete){
		imprimeln("Señalamiento al núcleo para envío de mensaje");
		Nucleo.send(248, paquete);
	}
	
	private byte[] recibePaquete(){
		byte[] respServidor = new byte[MAX_BUFFER];
		imprimeln("Invocando a Receive.");
		Nucleo.receive(dameID(), respServidor);
		return respServidor;
	}
	
	private boolean recursoOcupado(byte[] paquete){
		short esRecursoOcupado;
		byte[] bytesEsRecursoOcupado = new byte[BYTES_IN_SHORT];
		System.arraycopy(paquete, OFFSET+BYTES_IN_SHORT, bytesEsRecursoOcupado, 0, BYTES_IN_SHORT);
		esRecursoOcupado = ToShort(bytesEsRecursoOcupado);
		return (esRecursoOcupado == RECURSO_OCUPADO);
	}
	
	private byte[] generaPaquete(short tipoPaquete){
		byte[] solicitudAServidor = new byte[OFFSET + BYTES_IN_SHORT + BYTES_IN_SHORT],
			   bytesSolicitud = new byte[BYTES_IN_SHORT];
		
		imprimeln("Generando mensaje a ser enviado, llenando los campos necesarios");
		switch(tipoPaquete){
		case SOLICITUD_ARCHIVO:
			bytesSolicitud = toByte(SOLICITUD_ARCHIVO);
			break;
		case SOLICITUD_IMPRESORA:
			bytesSolicitud = toByte(SOLICITUD_IMPRESORA);
			break;
		case SOLICITUD_MEMORIA:
			bytesSolicitud = toByte(SOLICITUD_MEMORIA);
			break;
		case SOLICITUD_PUERTO:
			bytesSolicitud = toByte(SOLICITUD_PUERTO);
			break;
		case LIBERA_ARCHIVO:
			bytesSolicitud = toByte(LIBERA_ARCHIVO);
			break;
		case LIBERA_IMPRESORA:
			bytesSolicitud = toByte(LIBERA_IMPRESORA);
			break;
		case LIBERA_MEMORIA:
			bytesSolicitud = toByte(LIBERA_MEMORIA);
			break;
		case LIBERA_PUERTO:
			bytesSolicitud = toByte(LIBERA_PUERTO);
			break;
		case ESPERO_RECURSO:
			bytesSolicitud = toByte(ESPERO_RECURSO);
			break;
		default:
			imprimeln("Tipo de Paquete Erroneo: "+tipoPaquete);
			//System.exit(1);
			break;
		}
		System.arraycopy(bytesSolicitud, 0, solicitudAServidor, OFFSET+BYTES_IN_SHORT, BYTES_IN_SHORT);
		return solicitudAServidor;
		
	}

	/**
	 * 
	 */
	public void run(){
		imprimeln("Inicia Proceso Cliente");
		while(continuar()){
			Nucleo.suspenderProceso();
			if(solicitoRecurso){
				solicitaRecurso();
			}else{// quiero liberar recurso
				liberaRecurso();
			}
			
		}
	}
}
