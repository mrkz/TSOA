public class Proceso {

	public Proceso() {
		
	}
	
	private void referencia(int[] ar, int[] ar2) {
		/* method change values for ar and ar2, no matter variable length */
		System.out.print("Proceso.referencia (ar):\n");
		for(int i = 0; i < ar.length; i++){
			ar[i] = ar[i] << 2;
			System.out.print("  ["+i+"]: "+ar[i]);
		}
		System.out.println();
		System.out.print("Proceso.referencia:(ar2):\n");
		for(int i = 0; i < ar2.length; i++){
			ar2[i] = ar2[i] << 4;
			System.out.print("  ["+i+"]: "+ar2[i]);
		}
		System.out.println();
	}

	private void valor(int a, int a2) {
		a = a2 * a;
		a2 = 42;
		System.out.println("Proceso.valor:\n  a: "+a+"\n a2: "+a2);
		
	}

	public static void main(String[] args) {
		Proceso process = new Proceso();
		int a = 8;
		int ar[] = new int[1];
		ar[0] = a;
		System.out.println("Main(var a) before Proceso.valor: "+a);
		process.valor(a, a);
		System.out.println("Main(var a) after Proceso.valor: "+a);
		System.out.println("Main(var ar[]) before Proceso.referencia: "+ar[0]);
		process.referencia(ar,ar);
		System.out.println("Main(var ar[]) after Proceso.referencia:"+ar[0]);
			

	}
}
