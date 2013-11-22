package sistemaDistribuido.sistema.rpc.modoMonitor;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;   //para pr�ctica 4
import sistemaDistribuido.sistema.rpc.modoUsuario.Asa;
import sistemaDistribuido.sistema.rpc.modoUsuario.ProgramaConector;

public class RPC{
	private static ProgramaConector conector;
	public static final int RPC_ERROR = -99999;

	/**
	 * 
	 */
	public static void asignarConector(ProgramaConector con){
		conector = con;
		conector.inicializar();
	}

	/**
	 * Efectua la llamada de busqueda en el conector.
	 * Regresa un dest para la llamada a send(dest,message).
	 */
	public static int importarInterfaz(String nombreServidor,String version){
		Asa asa;
		int interfaz = RPC_ERROR;
		asa = conector.busqueda(nombreServidor, version);
		if(asa != null){
			Nucleo.nucleo.registraEnTablaEmision(asa);
			interfaz = asa.dameID();
		}
		return interfaz;
	}

	/**
	 * Modificado práctica 4
	 * Simental Magaña Marcos Eleno Joaquín
	 * 
	 * Efectua la llamada a registro en el conector.
	 * Regresa una identificacionUnica para el deregistro.
	 */
	public static int exportarInterfaz(String nombreServidor,String version,Asa asa){
		return conector.registro(nombreServidor,version,asa);
	}

	/**
	 * Modificado práctica 4
	 * Simental Magaña Marcos Eleno Joaquín
	 * 
	 * Efectua la llamada a deregistro en el conector.
	 * Regresa el status del deregistro, true significa llevado a cabo.
	 */
	public static boolean deregistrarInterfaz(String nombreServidor,String version,int identificacionUnica){
		return conector.desregistrar(nombreServidor, version, identificacionUnica);

	}
}