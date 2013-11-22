package sistemaDistribuido.sistema.rpc.modoUsuario;

import sistemaDistribuido.visual.rpc.DespleganteConexiones;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;
import java.util.Iterator;

public class ProgramaConector{
	private DespleganteConexiones desplegante;
	private Hashtable<Integer,DataOfServer> conexiones;   //las llaves que provee DespleganteConexiones

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
		conexiones=new Hashtable<Integer,DataOfServer>();
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

	/**
	 * Agregado práctica 4
	 * Simental Magaña Marcos Eleno Joaquín
	 */
	public int registro(String nombreServidor, String version,
						 Asa asa) {
		DataOfServer newServer = new DataOfServer(nombreServidor, version, asa);
		int idUnica = desplegante.agregarServidor(nombreServidor, version, asa.dameIP(), Integer.toString(asa.dameID()));
		conexiones.put(new Integer(idUnica), newServer);
		return idUnica;
		
	}
	
	/**
	 * Agregado práctica 4
	 * Simental Magaña Marcos Eleno Joaquín
	 */
	public boolean desregistrar(String nombreServidor, String version,
								int identificacionUnica) {
		boolean success = false;
		DataOfServer serverToDelete;
		serverToDelete = conexiones.get(identificacionUnica);
		if(nombreServidor.equalsIgnoreCase(serverToDelete.getName()) &&
		   version.equalsIgnoreCase(serverToDelete.getVersion())){
			conexiones.remove(identificacionUnica);
			desplegante.removerServidor(identificacionUnica);
			success = true;
		}
		return success;
	}

	public Asa busqueda(String nombreServidor, String version) {
		Asa process = null;
		for(Enumeration<DataOfServer> e = conexiones.elements();
			e.hasMoreElements();){
			DataOfServer server = e.nextElement();
			if(nombreServidor.equalsIgnoreCase(server.getName()) && 
			   version.equalsIgnoreCase(server.getVersion())){
				process = server.getAsa();
			}
		}
		return process;
	}
}

/**
 * Datos del servidor, Nombre y Versión
 * Agregado práctica 4
 * Simental Magaña Marcos Eleno Joaquín
 */
class DataOfServer{
	
	private String hostname, version;
	private Asa asa;
	public DataOfServer(String hostname, String version, Asa asa){
		this.hostname = hostname;
		this.version = version;
		this.asa = asa;
	}
	
	public void setName(String newName){
		hostname = newName;
	}
	
	public void setAsa(Asa newAsa){
		asa = newAsa;
	}
	
	public void setVersion(String newVersion){
		version = newVersion;
	}
	
	public String getName(){
		return hostname;
	}
	
	public String getVersion(){
		return version;
	}
	
	public Asa getAsa(){
		return asa;
	}
}