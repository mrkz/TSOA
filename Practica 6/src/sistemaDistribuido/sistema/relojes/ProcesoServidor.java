package sistemaDistribuido.sistema.relojes;


import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.util.Escribano;
import sistemaDistribuido.visual.clienteServidor.ServidorFrame;

/**
 * 
 */
public class ProcesoServidor extends Proceso{

	
	private int  tiempoActual, valorH, tiempoEstProp, valorI;
	private static final int VALORSEGUNDOS = 1000, valorN = 100;
	ServidorFrame parent;
	
	public ProcesoServidor(Escribano esc){
		super(esc);
	}
	
	public void setParentFrame(ServidorFrame parent){
		this.parent = parent;
	}
	
	public void setTiempoActual(int newVal){
		tiempoActual = newVal;
		parent.setTiempoActualGUI(tiempoActual);
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
	
	public void setTiempoEstProp(int newVal){
		 tiempoEstProp = newVal;
	}
	
	public int getTiempoEstProp(){
		return tiempoEstProp;
	}
	
	public void setValorI(int newVal){
		 valorI = newVal;
	}
	
	public int getValorI(){
		return valorI;
	}
	

	/**
	 * 
	 */
	public void run(){
		byte[] solicitudAlServidor = new byte[MAX_BUFFER];
		
		imprimeln("Inicio de Proceso servidor.");
		new ActualizadorDeTiempo().start();
		while(continuar()){
			imprimeln("Invocando a Receive.");
			Nucleo.receive(dameID(),solicitudAlServidor);
			imprimeln("Procesando petición recibida del cliente");
			new HiloTrabajador(solicitudAlServidor).start();
		}
	}
	
	class HiloTrabajador extends Thread{

		byte[] solicitud = new byte[MAX_BUFFER];
		public HiloTrabajador(byte[] sol){
			System.arraycopy(sol, 0, solicitud, 0, sol.length);
		}
		/**
		 * Edited: Simental Magaña Marcos Eleno Joaquín
		 * Método para empaquetar respuesa que dará el hilo trabajador del servidor
		 * entrada:	vacío
		 * salida:	byte[]  paquete de respuesta [OFFSET tiempoActual ValorI]
		 */
		private byte[] packageData(){
			
			byte[] newPackage = new byte[OFFSET + TAM_HORA + TAM_I];
			byte[] bytesTiempoActual = intToByteArray(getTiempoActual()),
				   bytesValorI = intToByteArray(valorI);
			System.arraycopy(bytesTiempoActual, 0, newPackage, OFFSET, TAM_HORA);
			System.arraycopy(bytesValorI, 0, newPackage, OFFSET+TAM_HORA, TAM_I);
			return newPackage;
		}
		
		@Override
		public void run() {
			int origin;
			byte[] respuesta;
			imprimeln("Generando mensaje a ser enviado, llenando los campos necesarios");
			respuesta = packageData();
			origin = getOrigin(solicitud);
			imprimeln("Simulando tiempo estimado de Propagación");
			try {
				sleep(getTiempoEstProp());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			imprimeln("Señalamiento al núcleo para envío de mensaje");
			Nucleo.send(origin,respuesta);
		}
		
	}
	
	class ActualizadorDeTiempo extends Thread{

		@Override
		public void run() {
			try{
				while(true){
					sleep(VALORSEGUNDOS/valorH);
					setTiempoActual(getTiempoActual()+valorN);
				}
			}
			catch (InterruptedException e1){
				e1.printStackTrace();
			}
			
		}
		
	}
}
