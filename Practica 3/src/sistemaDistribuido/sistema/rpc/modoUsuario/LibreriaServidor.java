package sistemaDistribuido.sistema.rpc.modoUsuario;

import sistemaDistribuido.sistema.rpc.modoUsuario.Libreria;
import sistemaDistribuido.util.Escribano;

public class LibreriaServidor extends Libreria{

	/**
	 * 
	 */
	public LibreriaServidor(Escribano esc){
		super(esc);
	}

	/**
	 * Ejemplo de servidor suma verdadera
	 */
	protected void suma(){
		//saca parametros de pila

		//devuelve valor izquierdo
	}

	@Override
	protected void sumatoria() {
		int[] values = popArray();
		int res = 0;
		for(int i = 0; i < values.length; i++){
			res+=values[i];
		}
		parameters.push(new Integer(res));
	}

	@Override
	protected void multiplicatoria() {
		int[] values = popArray();
		int res = 1;
		for(int i = 0; i < values.length; i++){
			res*=values[i];
		}
		parameters.push(new Integer(res));
	}

	@Override
	protected void division() {
		int [] values = popArray();
		int res = 0;
		if(values[1] != 0){
			res = values[0]/values[1];
		}
		parameters.push(new Integer(res));
		
	}
	
	@Override
	protected void absoluto() {
		int [] values = popArray();
		int res = 0;
		if(values[0] < 0){
			res = values[0] * -1;
		}
		else{
			res = values[0];
		}
		parameters.push(new Integer(res));
		
	}
	
	private int[] popArray(){
		int nParameters = parameters.pop().intValue();
		int[] array = new int[nParameters];
		for(int i = 0; i < nParameters; i++){
			array[i] = parameters.pop().intValue();
		}
		return array;
	}

}