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
	protected static final int BYTES_IN_SHORT = 2, MAX_BUFFER = 1024,
							   OFFSET = 8, TAM_HORA = 4, TAM_I = 4;
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
	 * Edited: Simental Magaña Marcos Eleno Joaquín
	 * para práctica 6
	 * Método para obtener el origen del mensaje recibido
	 * entrada: byte[]
	 * salida: int
	 * 
	 */
	protected int getOrigin(byte[] solServidor){
		byte[] origin = new byte[4];
		System.arraycopy(solServidor, 0, origin, 0, 4);
		return bytesToInt(origin);
	}
	
	/**
	 * Edited: Simental Magaña Marcos Eleno Joaquín
	 * Para práctica 6
	 * Método para convertir direccion de origen a entero
	 * entrada: byte[]
	 * salida: int
	 */
	protected int bytesToInt(byte[] array){
		int bytesValue = 0x0;
		bytesValue = (int)( (array[3]       & 0x000000FF) | 
							(array[2] << 8  & 0x0000FF00) | 
							(array[1] << 16 & 0x00FF0000) | 
							(array[0] << 24 & 0xFF000000));
		return bytesValue;
	}
	
	/**
	 * Edited: Simental Magaña Marcos Eleno Joaquín
	 * Para práctica 6
	 * Método para convertir entero a byte[]
	 * entrada: int
	 * salida: byte[]
	 */
	protected byte[] intToByteArray(int data){
		byte[] byteArray = new byte[4];
		/* saved from most to less significant */
		for(int i = 3; i >= 0; i--){
			byteArray[i] = (byte) data; 
			data >>= 8;
		}
		return byteArray;
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
