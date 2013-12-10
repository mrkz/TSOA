package sistemaDistribuido.sistema.exclusionMutua;

import sistemaDistribuido.sistema.exclusionMutua.ProcesoCliente;
import sistemaDistribuido.sistema.exclusionMutua.MicroNucleoFrame;
import sistemaDistribuido.sistema.exclusionMutua.ProcesoFrame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Choice;
import java.awt.Button;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClienteFrame extends ProcesoFrame{
	private static final long serialVersionUID=1;
	private ProcesoCliente proc;
	private Choice velocidadReloj;
	private TextField fTiempoActual, fValorH, fDistMaxReloj, fTasaMaxAlej, fDelta2Ro, fValorN;
	private Button botonInicio;
	private static final int LENTO = 0, PERFECTO = 1, RAPIDO = 2;

	public ClienteFrame(MicroNucleoFrame frameNucleo){
		super(frameNucleo,"Cliente");
		add("South",construirCamposCliente());
		identificador.add(new Label("d/2p"));
		fDelta2Ro = new TextField(5);
		fDelta2Ro.setEditable(false);
		identificador.add(fDelta2Ro);
		identificador.add(new Label("N"));
		fValorN = new TextField(5);
		fValorN.setEditable(false);
		identificador.add(fValorN);
		
		
		setSize(750, 300);
		validate();
		proc = new ProcesoCliente(this);
		fijarProceso(proc);
	}
	
	public void setDelta2Ro(int newVal){
		fDelta2Ro.setText(""+newVal);
	}
	
	public void setValorN(int newVal){
		fValorN.setText(""+newVal);
	}

	public Panel construirCamposCliente(){
		Panel panelPrincipal = new Panel();
		
		panelPrincipal.add(new Label("Vel"));
		velocidadReloj = new Choice();
		velocidadReloj.add("Lento");
		velocidadReloj.add("Perfecto");
		velocidadReloj.add("Rápido");
		panelPrincipal.add(velocidadReloj);
		
		panelPrincipal.add(new Label("Tiempo Inicial"));
		fTiempoActual = new TextField(9);
		panelPrincipal.add(fTiempoActual);
		
		panelPrincipal.add(new Label("H"));
		fValorH = new TextField(5);
		panelPrincipal.add(fValorH);
		
		panelPrincipal.add(new Label("DisMaxReloj"));
		fDistMaxReloj = new TextField(5);
		panelPrincipal.add(fDistMaxReloj);
		
		panelPrincipal.add(new Label("TasaMaxAlej"));
		fTasaMaxAlej = new TextField(5);
		panelPrincipal.add(fTasaMaxAlej);
		
		botonInicio = new Button("Solicitar");
		botonInicio.addActionListener(new ManejadorSolicitud());
		panelPrincipal.add(botonInicio);
		
		/*fTiempoActual, fValorH, fDistMaxReloj, fTipoReloj, fTasaMaxAlej;*/
		return panelPrincipal;
	}
	
	public void setTiempoActual(int newVal){
		fTiempoActual.setText(""+newVal);
	}

	class ManejadorSolicitud implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			String com = e.getActionCommand();
			if (com.equals("Solicitar")){
				botonInicio.setEnabled(false);
				velocidadReloj.setEnabled(false);
				if(velocidadReloj.getSelectedIndex() == LENTO)
					proc.setTipoReloj(LENTO);
				else if(velocidadReloj.getSelectedIndex() == RAPIDO)
					proc.setTipoReloj(RAPIDO);
				else
					proc.setTipoReloj(PERFECTO);
				try{
					proc.setTiempoActual(Integer.parseInt(fTiempoActual.getText()));
					proc.setValorH(Integer.parseInt(fValorH.getText()));
					proc.setDistMaxReloj(Integer.parseInt(fDistMaxReloj.getText()));
					proc.setTasaMaxAlej(Float.parseFloat(fTasaMaxAlej.getText()));
				}
				catch (NumberFormatException e1){
					imprimeln("Campos vacios... Llenando con parametros predeterminados");
					proc.setTiempoActual(0);
					proc.setValorH(10);
					proc.setDistMaxReloj(25000);
					proc.setTasaMaxAlej((float)0.2);
				}
				imprimeln("======================================");
				imprimeln("Tiempo Actual: "+proc.getTiempoActual());
				imprimeln("H: "+proc.getValorH());
				imprimeln("Distorsion maxima de reloj: "+proc.getDistMaxReloj());
				imprimeln("Tasa maxima de alejamiento: "+proc.getTasaMaxAlej());
				imprimeln("======================================");
				proc.start();
				
			}
			else{
				System.out.println("Botón desconocido...");
				System.exit(ERROR);
			}
		}
	}
}
