package sistemaDistribuido.sistema.clienteServidor.modoUsuario;


import microKernelBasedSystem.system.clientServer.userMode.threadPackage.SystemProcess;
import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoMonitor.MicroNucleo;
import sistemaDistribuido.util.Escribano;

/**
 * 
 */
public abstract class Proceso extends SystemProcess{
	protected MicroNucleo nucleo;
	
	/**
	 * Edited: Simental Magaña Marcos Eleno Joaquín
	 * Atributos BYTES_IN_SHORT, MAX_BUFFER y OFFSET agregados.
	 */
	protected static final int BYTES_IN_SHORT = 2;
	protected static final int MAX_BUFFER = 1024;
	protected static final int OFFSET = 8;
	/**
	 * 
	 */
	public Proceso(Escribano esc){
		super(Nucleo.nucleo,esc);
		this.nucleo=Nucleo.nucleo;
	}
	/**
	 * Edited: Simental Magaña Marcos Eleno Joaquín
	 * Método para convertir un short a un arreglo de bytes
	 * entrada:	short
	 * salida:	byte[]
	 */
	protected byte[] toByte(short value){
		byte[] byteArray = new byte[BYTES_IN_SHORT];
		/* saved from most to less significant */
		byteArray[0] = (byte) (value >> 8);
		byteArray[1] = (byte) value;
		return byteArray;
	}
	/**
	 * Edited: Simental Magaña Marcos Eleno Joaquín
	 * Método para convertir un arreglo de bytes a un short
	 * entrada:	byte[]
	 * salida:	short
	 */
	protected short ToShort(byte[] array){
		short bytesValue = 0x0;
		bytesValue = (short)((array[1]      & 0x00FF) | 
							 (array[0] << 8 & 0xFF00));
		return bytesValue;
	}

	/**
	 * Solo para compatibilidad con versiones 2007B y anteriores, no necesario de 2008A en adelante
	 */
	public Proceso(MicroNucleo nucleo,Escribano esc){
		this(esc);
		start();
	}
	/**
	 * 
	 */
	protected void imprime(String s){
		super.print(s);
	}

	/**
	 * 
	 */
	protected void imprimeln(String s){
		super.println(s);
	}

	/**
	 * 
	 */
	public final int dameID(){
		return (int)super.getID();
	}

	/**
	 * 
	 */
	public final boolean continuar(){
		return super.continueExecuting();
	}

	/**
	 * Solo para compatibilidad con versiones 2007B y anteriores, no necesario de 2008A en adelante
	 */
	public void terminar(){
	}

	/**
	 * Actividad normal del proceso mientras est� activo
	 */
	public void run(){
	}

	/**
	 * Actividades a realizar tras recibir la se�al de terminaci�n del proceso
	 */
	protected void shutdown(){
		terminar();
	}
}
