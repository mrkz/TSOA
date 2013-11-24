package sistemaDistribuido.sistema.rpc.modoUsuario;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.util.Escribano;

/**
 * 
 */
public class ProcesoCliente extends Proceso{
	private Libreria lib;
	
	
	/**
	 * Atributos agregados para práctica 3
	 * Simental Magaña Marcos Eleno Joaquín.
	 */
	private int[] sumatoriaArgs, multiplicatoriaArgs;
	private int divisor, dividendo, abs;
	/**
	 * Atributos agregados para práctica 4
	 * Simental Magaña Marcos Eleno Joaquín.
	 */
	public static final int RPC_ERROR = -99999;
				  

	/**
	 * 
	 */
	public ProcesoCliente(Escribano esc){
		super(esc);
		//lib=new LibreriaServidor(esc);  //primero debe funcionar con esta para subrutina servidor local
		lib=new LibreriaCliente(esc);  //luego con esta comentando la anterior, para subrutina servidor remota
		start();
	}
	
	/*
	 * Agregado práctica 3
	 * Simental Magaña Marcos Eleno Joaquín
	 */
	public void setSumatoriaArgs(int[] args){
		sumatoriaArgs = args;
	}
	
	/*
	 * Agregado práctica 3
	 * Simental Magaña Marcos Eleno Joaquín
	 */
	public void setMultiplicatoriaArgs(int[] args){
		multiplicatoriaArgs = args;
	}
	
	/*
	 * Agregado práctica 3
	 * Simental Magaña Marcos Eleno Joaquín
	 */
	public void setDivisionArgs(int dividendo, int divisor){
		this.divisor   = divisor;
		this.dividendo = dividendo;
	}
	
	/*
	 * Agregado práctica 3
	 * Simental Magaña Marcos Eleno Joaquín
	 */
	public void setAbsoluto(int val){
		abs = val;
	}
	
	/*
	 * Agregado práctica 3
	 * Simental Magaña Marcos Eleno Joaquín
	 */
	public int sumatoria(int len, int[] array){
		return lib.sumatoria(len, array);
	}
	
	/*
	 * Agregado práctica 3
	 * Simental Magaña Marcos Eleno Joaquín
	 */
	public int multiplicatoria(int len, int[] array){
		return lib.multiplicatoria(len, array);
	}
	
	/*
	 * Agregado práctica 3
	 * Simental Magaña Marcos Eleno Joaquín
	 */
	public int division(int dividendo, int divisor){
		return lib.division(dividendo, divisor);
	}
	
	/*
	 * Agregado práctica 3
	 * Simental Magaña Marcos Eleno Joaquín
	 */
	public int absoluto(int val){
		return lib.absoluto(val);
	}
	

	/*
	 * Modificado práctica 3
	 * Modificado práctica 4: se agrega try/catch para 
	 *  cliente que se cierra sin realizar operaciones
	 * Simental Magaña Marcos Eleno Joaquín
	 */
	public void run(){
		int resultado;
		
		imprimeln("Proceso cliente en ejecucion.");
		imprimeln("Esperando datos para continuar.");
		Nucleo.suspenderProceso();
		imprimeln("Salio de suspenderProceso");
		
		try{
			imprimeln("Solicitando sumatoria a libreria...");
			resultado = lib.sumatoria(sumatoriaArgs.length, sumatoriaArgs);
			if(resultado != RPC_ERROR){
				imprimeln("sumatoria = "+resultado);
			}else {	imprimeln("Servidor no disponible..."); }

			imprimeln("Solicitando multiplicatoria a libreria...");
			resultado = lib.multiplicatoria(multiplicatoriaArgs.length, multiplicatoriaArgs);
			if(resultado != RPC_ERROR){
				imprimeln("multiplicatoria = "+resultado);
			}else {	imprimeln("Servidor no disponible..."); }

			imprimeln("Solicitando division a libreria...");
			resultado = lib.division(dividendo, divisor);
			if(resultado != RPC_ERROR){
				imprimeln("division = "+resultado);
			}else {	imprimeln("Servidor no disponible..."); }

			imprimeln("Solicitando absoluto a libreria...");
			resultado = lib.absoluto(abs);
			if(resultado != RPC_ERROR){
				imprimeln("Absoluto = "+resultado);
			}else {	imprimeln("Servidor no disponible..."); }

		}
		catch (NullPointerException e){
			/* no se realizó ninguna operación con el cliente */
		}
		imprimeln("Fin del cliente.");
	}
}
