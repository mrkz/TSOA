package sistemaDistribuido.sistema.clienteServidor.modoMonitor;

import java.util.Hashtable;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.MicroNucleoBase;

/**
 * 
 */
public final class MicroNucleo extends MicroNucleoBase{
	private static MicroNucleo nucleo=new MicroNucleo();
	
	/**
	 * Edited: Simental Magaña Marcos Eleno Joaquín
	 * Para práctica 2
	 * Atributo Table agregado
	 */
	private Hashtable<Integer, InfoProceso> tablaEmision;

	/**
	 * Edited: Simental Magaña Marcos Eleno Joaquín
	 * Para práctica 2
	 * Se agrega inicialización de tablaEmision
	 */
	private MicroNucleo(){
		tablaEmision = new Hashtable<Integer, InfoProceso>();
	}

	/**
	 * 
	 */
	public final static MicroNucleo obtenerMicroNucleo(){
		return nucleo;
	}

	/*---Metodos para probar el paso de mensajes entre los procesos cliente y servidor en ausencia de datagramas.
    Esta es una forma incorrecta de programacion "por uso de variables globales" (en este caso atributos de clase)
    ya que, para empezar, no se usan ambos parametros en los metodos y fallaria si dos procesos invocaran
    simultaneamente a receiveFalso() al reescriir el atributo mensaje---*/
	byte[] mensaje;

	public void sendFalso(int dest,byte[] message){
		System.arraycopy(message,0,mensaje,0,message.length);
		notificarHilos();  //Reanuda la ejecucion del proceso que haya invocado a receiveFalso()
	}

	public void receiveFalso(int addr,byte[] message){
		mensaje=message;
		suspenderProceso();
	}
	/*---------------------------------------------------------*/

	/**
	 * 
	 */
	protected boolean iniciarModulos(){
		return true;
	}

	/**
	 * 
	 */
	protected void sendVerdadero(int dest,byte[] message){
		/* comentar linea de send falso*/
		sendFalso(dest,message);
		message = fillAddress(dest, message);
		//imprimeln("El proceso invocante es el "+super.dameIdProceso());
		
		//lo siguiente aplica para la práctica #2
		ParMaquinaProceso pmp=dameDestinatarioDesdeInterfaz();
		System.out.println("Existe proceso en tabla de emisión? :"+tablaEmision.containsKey(pmp.dameID()));
		imprimeln("Enviando mensaje a IP="+pmp.dameIP()+" ID="+pmp.dameID());
		//suspenderProceso();   //esta invocacion depende de si se requiere bloquear al hilo de control invocador
		
	}

	/**
	 * 
	 */
	protected void receiveVerdadero(int addr,byte[] message){
		/* se comenta receiveFalso */
		receiveFalso(addr,message);
		//el siguiente aplica para la práctica #2
		//suspenderProceso();
	}

	/**
	 * Para el(la) encargad@ de direccionamiento por servidor de nombres en pr�ctica 5  
	 */
	protected void sendVerdadero(String dest,byte[] message){
	}

	/**
	 * Para el(la) encargad@ de primitivas sin bloqueo en pr�ctica 5
	 */
	protected void sendNBVerdadero(int dest,byte[] message){
	}

	/**
	 * Para el(la) encargad@ de primitivas sin bloqueo en pr�ctica 5
	 */
	protected void receiveNBVerdadero(int addr,byte[] message){
	}

	/**
	 * 
	 */
	public void run(){

		while(seguirEsperandoDatagramas()){
			/* Lo siguiente es reemplazable en la práctica #2,
			 * sin esto, en práctica #1, según el JRE, puede incrementar el uso de CPU
			 */ 
			try{
				/* este sleep se va o se reduce a 5K o 1K */
				sleep(60000);
			}catch(InterruptedException e){
				System.out.println("InterruptedException");
			}
		}
	}
	
	/**
	 * Edited: Simental Magaña Marcos Eleno Joaquín
	 * Para práctica 2
	 * Se agrega Método para llenar campos proceso Origen y 
	 * proceso destino
	 */
	private byte[] fillAddress(int dest, byte[] message){
		byte [] filled = message,
				origin = intToByteArray(super.dameIdProceso()),
				destiny= intToByteArray(dest); 
		for(int i = 0; i < origin.length; i++){
			filled[i] = origin[i];
		}
		for(int i = origin.length, j = 0; i < (destiny.length + origin.length); i++ , j++){
			filled[i] = destiny[j];
		}
		
		return filled;
	}
	
	/**
	 * Edited: Simental Magaña Marcos Eleno Joaquín
	 * Para práctica 2
	 * Se agrega Método para convertir entero a byte[]
	 */
	private byte[] intToByteArray(int data){
		byte[] byteArray = new byte[4];
		/* saved from most to less significant */
		for(int i = 3; i >= 0; i--){
			byteArray[i] = (byte) data; 
			data >>= 8;
		}
		return byteArray;
	}
}

/**
 * Edited: Simental Magaña Marcos Eleno Joaquín
 * Para práctica 2
 * Se agrega Clase InfoProceso
 */
class InfoProceso{
	private int idProceso;
	private String ipProceso;
	
	public InfoProceso(int id, String ip){
		setId(id);
		setIp(ip);
	}
	
	private void setId(int newId){
		idProceso = newId;
	}
	
	private void setIp(String newIp){
		ipProceso = newIp;
	}
	
	public int getId(){
		return idProceso;
	}
	
	public String getIp(){
		return ipProceso;
	}
}