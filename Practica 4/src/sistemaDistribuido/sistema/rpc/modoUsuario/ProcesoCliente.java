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
	 * Simental Magaña Marcos Eleno Joaquín
	 */
	public void run(){
		int resultado;
		
		imprimeln("Proceso cliente en ejecucion.");
		imprimeln("Esperando datos para continuar.");
		Nucleo.suspenderProceso();
		imprimeln("Salio de suspenderProceso");
		
		resultado = lib.sumatoria(sumatoriaArgs.length, sumatoriaArgs);
		imprimeln("sumatoria = "+resultado);
		
		resultado = lib.multiplicatoria(multiplicatoriaArgs.length, multiplicatoriaArgs);
		imprimeln("multiplicatoria = "+resultado);
		
		resultado = lib.division(dividendo, divisor);
		imprimeln("division = "+resultado);
		
		resultado = lib.absoluto(abs);
		imprimeln("Absoluto = "+resultado);

		imprimeln("Fin del cliente.");
	}
}
