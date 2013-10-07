package sistemaDistribuido.sistema.clienteServidor.modoUsuario;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.util.Escribano;

/**
 * 
 */
public class ProcesoCliente extends Proceso{

	/**
	 * 
	 */
	public ProcesoCliente(Escribano esc){
		super(esc);
		start();
	}

	/**
	 * 
	 */
	public void run(){
		imprimeln("Proceso cliente en ejecucion.");
		imprimeln("Esperando datos para continuar.");
		Nucleo.suspenderProceso();
		imprimeln("Hola =)");
		byte[] solCliente=new byte[20]; //1024
		byte[] respCliente=new byte[20]; //1024
		// desde aqu� va el codigo :D
		// visual/clienteServidor/clienteFrame.java Para obtener los mensajes de la ventana
		byte dato;
		solCliente[0]=(byte)10;
		Nucleo.send(248,solCliente); //esta no se mueve
		Nucleo.receive(dameID(),respCliente); // esta tampoco
		dato=respCliente[0];
		imprimeln("el servidor me envi� un "+dato);
				// aqu� termina el codigo
	}
}
