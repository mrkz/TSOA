package sistemaDistribuido.visual.rpc;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.rpc.modoUsuario.ProcesoCliente;
import sistemaDistribuido.visual.clienteServidor.ProcesoFrame;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Button;
import java.awt.Label;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClienteFrame extends ProcesoFrame{
	private static final long serialVersionUID=1;
	private ProcesoCliente proc;
	private TextField campo1,campo2,campo3,campo4, campo5;
	private Button botonSolicitud;
	/* atributo SUMA, MULTIPLICACION agregados p/ Práctica 3 */
	private static final boolean SUMA = false, MULTIPLICACION = true;

	public ClienteFrame(RPCFrame frameNucleo){
		super(frameNucleo,"Cliente de Archivos");
		add("South",construirPanelSolicitud());
		validate();
		proc=new ProcesoCliente(this);
		fijarProceso(proc);
	}
	
	/*
	 * Agregado práctica 3
	 * Simental Magaña Marcos Eleno Joaquín
	 * type = true  -> elemento neutro multiplicativo
	 * type = false -> elemento neutro aditivo
	 */
	private int[] parseValues(String [] array, boolean type){
		int[] arrayToInt = new int[array.length];
		for(int i = 0; i < array.length; i++){
			try{
				arrayToInt[i] = Integer.parseInt(array[i]);
			}
			catch (NumberFormatException e){
				if(type){
					arrayToInt[i] = 1;
				}
				else{
					arrayToInt[i] = 0;
				}
			}
		}
		return arrayToInt;
	}
	
	private int parseIntField(String str){
		int parameter = 1;
		try{
			parameter = Integer.parseInt(str);
		}
		catch (NumberFormatException e){
		}
		return parameter;
	}

	public Panel construirPanelSolicitud(){
		Panel pSolicitud,pcodop1,pcodop2,pcodop3,pcodop4,pboton;
		pSolicitud=new Panel();
		pcodop1=new Panel();
		pcodop2=new Panel();
		pcodop3=new Panel();
		pcodop4=new Panel();
		pboton=new Panel();
		campo1=new TextField(10);
		campo2=new TextField(10);
		campo3=new TextField(10);
		campo4=new TextField(10);
		campo5=new TextField(10);
		pSolicitud.setLayout(new GridLayout(5,1));

		pcodop1.add(new Label("Sumatoria "));
		pcodop1.add(new Label("n Param:"));
		pcodop1.add(campo1);

		pcodop2.add(new Label("Producto "));
		pcodop2.add(new Label("n Param:"));
		pcodop2.add(campo2);

		pcodop3.add(new Label("División "));
		pcodop3.add(new Label("Divisor:"));
		pcodop3.add(campo3);
		pcodop3.add(new Label("Dividendo:"));
		pcodop3.add(campo4);

		pcodop4.add(new Label("Valor Absoluto "));
		pcodop4.add(new Label("Paramametro:"));
		pcodop4.add(campo5);

		botonSolicitud=new Button("Solicitar");
		pboton.add(botonSolicitud);
		botonSolicitud.addActionListener(new ManejadorSolicitud());

		pSolicitud.add(pcodop1);
		pSolicitud.add(pcodop2);
		pSolicitud.add(pcodop3);
		pSolicitud.add(pcodop4);
		pSolicitud.add(pboton);

		return pSolicitud;
	}

	class ManejadorSolicitud implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int [] parameters;
			String com=e.getActionCommand();
			if (com.equals("Solicitar")){
				botonSolicitud.setEnabled(false);
				//atiendo primera solicitud
				parameters = parseValues(campo1.getText().split(" "),SUMA);
				proc.setSumatoriaArgs(parameters);
				//atiendo segunda solicitud
				parameters = parseValues(campo2.getText().split(" "), MULTIPLICACION);
				proc.setMultiplicatoriaArgs(parameters);
				//atiendo tercera solicitud
				proc.setDivisionArgs(parseIntField(campo3.getText()),parseIntField(campo4.getText()));
				//atiendo cuarta solicitud
				proc.setAbsoluto(parseIntField(campo5.getText()));
				
				Nucleo.reanudarProceso(proc);
			}
		}
	}
}
