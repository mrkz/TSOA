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

	/**
	 * 
	 */
	public void run(){
		imprimeln("Proceso servidor en ejecucion.");
		byte[] solServidor=new byte[20]; //1024
		byte[] respServidor; //1024
		byte dato;
		while(continuar()){
			Nucleo.receive(dameID(),solServidor);
			dato=solServidor[0];//desempaqueta
			// se hace el manejo de datos
			imprimeln("el cliente envió un "+dato);
			respServidor=new byte[20]; //1024
			respServidor[0]=(byte)(dato*dato);
			Pausador.pausa(1000);  //sin esta línea es posible que Servidor solicite send antes que Cliente solicite receive
			imprimeln("enviando respuesta");
			Nucleo.send(0,respServidor);
		}
	}
}
