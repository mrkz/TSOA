package sistemaDistribuido.sistema.rpc.modoUsuario;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.ParMaquinaProceso;
import sistemaDistribuido.visual.rpc.DespleganteConexiones;
import java.util.Hashtable;
import java.util.Set;
import java.util.Iterator;

public class ProgramaConector{
	private DespleganteConexiones desplegante;
	private Hashtable<Integer,Object> conexiones;   //las llaves que provee DespleganteConexiones

	/**
	 * 
	 */
	public ProgramaConector(DespleganteConexiones desplegante){
		this.desplegante=desplegante;
	}

	/**
	 * Inicializar tablas en programa conector
	 */
	public void inicializar(){
		conexiones=new Hashtable<Integer,Object>();
	}

	/**
	 * Remueve tuplas visualizadas en la interfaz gr�fica registradas en tabla conexiones
	 */
	private void removerConexiones(){
		Set<Integer> s=conexiones.keySet();
		Iterator<Integer> i=s.iterator();
		while(i.hasNext()){
			desplegante.removerServidor(((Integer)i.next()).intValue());
			i.remove();
		}
	}

	/**
	 * Al solicitar que se termine el proceso, por si se implementa como tal
	 */
	public void terminar() {
		removerConexiones();
		desplegante.finalizar();
	}

	public int registro(String nombreServidor, String version,
						 ParMaquinaProceso asa) {
		DataOfServer newServer = new DataOfServer(nombreServidor, version);
		int idUnica = desplegante.agregarServidor(nombreServidor, version, asa.dameIP(), Integer.toString(asa.dameID()));
		conexiones.put(new Integer(idUnica), newServer);
		return idUnica;
		
	}
}


class DataOfServer{
	
	private String hostname, version;
	public DataOfServer(String hostname, String version){
		this.hostname = hostname;
		this.version = version;
	}
	
	public void setName(String newName){
		hostname = newName;
	}
	
	public void setVersion(String newVersion){
		version = newVersion;
	}
	
	public String getServerName(){
		return hostname;
	}
	
	public String getServerVersion(){
		return version;
	}
}