package sistemaDistribuido.sistema.exclusionMutua;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
//import sistemaDistribuido.sistema.relojes.Proceso;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.util.Escribano;
import sistemaDistribuido.visual.clienteServidor.ClienteFrame;

/**
 * 
 */
public class ProcesoCliente extends Proceso{

	/**
	 * Edited: Simental Magaña Marcos Eleno Joaquín
	 * para práctica 6
	 * Atributos tiempoActual, valorH, distMaxReloj, tipoReloj, 
	 *           tasaMaxAlej, TAM_INT agregados
	 */
	private int tiempoActual, valorH, distMaxReloj, tipoReloj, valorN, valorNOriginal;
	private int tiempoParaSigSincronizacion, numInterrupciones, t0, t1, tiempoEstProp;
	private float tasaMaxAlej;
	private ClienteFrame parent;
	private static final int TAM_INT = 4;
	private static final int VALORSEGUNDOS = 1000;
	private static final int LENTO = 0, RAPIDO = 2;
	private int tiempoADormir = 3000;
	
	public ProcesoCliente(Escribano esc){
		super(esc);
	}
	
	public void setParentFrame(ClienteFrame parent) {
		this.parent = parent;
		
	}
	public void setTiempoActual(int newVal){
		tiempoActual = newVal;
		parent.setTiempoActual(newVal);
	}
	
	public int getTiempoActual(){
		return tiempoActual;
	}
	
	public void setValorH(int newVal){
		valorH = newVal;
	}
	
	public int getValorH(){
		return valorH;
	}
	
	public void setDistMaxReloj(int newVal){
		distMaxReloj = newVal;
	}
	public int getDistMaxReloj(){
		return distMaxReloj;
	}
	
	public void setTipoReloj(int newVal){
		tipoReloj = newVal;
	}
	
	public int getTipoReloj(){
		return tipoReloj;
	}
	
	public void setTasaMaxAlej(float newVal){
		tasaMaxAlej = newVal;
	}
	
	public float getTasaMaxAlej(){
		return tasaMaxAlej;
	}
	
	/**
	 * Edited: Simental Magaña Marcos Eleno Joaquín
	 * Método para empaquetar mensaje de solicitud
	 * entrada: vacía
	 * salida: byte[]
	 * Estructura del paquete:
	 * 		   OFFSET           | int
	 * 			DIRS            | TAM_INT
	 * [0][0][0][0] [0][0][0][0]  
	 */
	private byte[] packageData(){
		return new byte[OFFSET + TAM_INT];
	}
	
	private int desempaquetaValorI(byte[] respCliente){
		int valorI = 1;
		byte[] bytesValorI = new byte[TAM_I];
		System.arraycopy(respCliente, OFFSET+TAM_INT, bytesValorI, 0, TAM_I);
		valorI = bytesToInt(bytesValorI);
		System.out.println("ProcesoCliente: desempaquetaValorI: valor: "+valorI);
		return valorI;
	}
	
	private int desempaquetaHoraServidor(byte[] respCliente){
		int horaServidor = 0;
		byte[] bytesHoraServidor = new byte[TAM_HORA];
		System.arraycopy(respCliente, OFFSET, bytesHoraServidor, 0, TAM_HORA);
		horaServidor = bytesToInt(bytesHoraServidor);
		System.out.println("ProcesoCliente: desempaquetaHoraServidor: hora: "+horaServidor);
		return horaServidor;
	}
	
	private boolean esAU(byte[] data){
		byte[] bytesAU = new byte[2];
		short au;
		System.arraycopy(data, 10, bytesAU, 0, BYTES_IN_SHORT);
		au = bytesToShort(bytesAU);
		return (au == -1);
	}
	
	/**
	 * Edited: Simental Magaña Marcos Eleno Joaquín
	 * para práctica 6
	 * Se agrega Método para convertir byte[] a short
	 */
	protected short bytesToShort(byte[] array){
		short bytesValue = 0x0;
		bytesValue = (short)((array[1]      & 0x00FF) | 
							 (array[0] << 8 & 0xFF00));
		return bytesValue;
	}
	

	/**
	 * 
	 */
	public void run(){
		int difTiempo;
		int valorI = 0, horaServidor;
		imprimeln("Inicio de Proceso Cliente");
		valorN = VALORSEGUNDOS/valorH;
		if(getTipoReloj() == LENTO){
			numInterrupciones =  (int)(valorH - (valorH * getTasaMaxAlej()));
		}
		else if(getTipoReloj() == RAPIDO){
			numInterrupciones = (int)(valorH + (valorH * getTasaMaxAlej()));
		}
		else { //reloj perfecto
			numInterrupciones = valorH;
		}
		new ActualizadorDeTiempo().start();
		valorNOriginal = valorN;
		//tiempoParaSigSincronizacion = delta / 2 * RO
		tiempoParaSigSincronizacion = (int)(getDistMaxReloj() / (2 * getTasaMaxAlej()));
		imprimeln("Tiempo para sincronizar: "+tiempoParaSigSincronizacion);
		parent.setDelta2Ro(tiempoParaSigSincronizacion);
		parent.setValorN(valorN);
		while(continuar()){
			try {
				sleep(tiempoADormir);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			valorN = valorNOriginal;
			parent.setValorN(valorN);
			imprimeln("Iniciando proceso de Sincronizacion");
			byte[] respCliente=new byte[MAX_BUFFER]; //1024
			imprimeln("Señalamiento al núcleo para envío de mensaje");
			t0 = getTiempoActual();
			Nucleo.send(248,packageData()); //esta no se mueve
			imprimeln("Invocando a Receive.");
			Nucleo.receive(dameID(),respCliente); // esta tampoco
			t1 = getTiempoActual();
			imprimeln("Procesando respuesta recibida");
			if(esAU(respCliente)){
				tiempoEstProp = t1-t0;
				tiempoADormir = getDistMaxReloj() - tiempoEstProp;
				continue;
			}
			imprimeln("Calculando tiempo estimado de propagación");
			tiempoEstProp = t1-t0-valorI;
			valorI = desempaquetaValorI(respCliente);
			horaServidor = desempaquetaHoraServidor(respCliente);
			imprimeln("Se obtuvo I: "+valorI);
			imprimeln("Hora del servidor: "+horaServidor);
			
			horaServidor = horaServidor+(tiempoEstProp / 2);
			imprimeln("Hora actualizada del servidor: "+horaServidor);
			difTiempo = horaServidor - getTiempoActual();
			imprimeln("Diferencia de tiempo: "+difTiempo);
			tiempoADormir = getDistMaxReloj() - tiempoEstProp;
			System.out.println("El hilo dormira: "+(tiempoADormir));
			imprimeln("El hilo dormira: "+(tiempoADormir));
			valorN = valorN + (difTiempo / ( tiempoADormir / valorH)); 
			parent.setValorN(valorN);
			
		}
	}
	
	class ActualizadorDeTiempo extends Thread{

		@Override
		public void run() {
			try{
				while(true){
					sleep(VALORSEGUNDOS / numInterrupciones);
					setTiempoActual(getTiempoActual() + valorN);
				}
			}
			catch (InterruptedException e1){
				e1.printStackTrace();
			}
			
		}
		
	}
}
