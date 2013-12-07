package sistemaDistribuido.visual.clienteServidor;

import sistemaDistribuido.sistema.relojes.ProcesoServidor;
import sistemaDistribuido.visual.clienteServidor.MicroNucleoFrame;
import sistemaDistribuido.visual.clienteServidor.ProcesoFrame;
import java.awt.Button;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServidorFrame extends ProcesoFrame{
  private static final long serialVersionUID=1;
  private ProcesoServidor proc;
  private TextField fTiempoActual, fValorH, fTiempoEstProp, fValorI;
  private Button botonInicio;
  private int  tiempoActual, valorH, tiempoEstProp, valorI;

  public ServidorFrame(MicroNucleoFrame frameNucleo){
    super(frameNucleo,"Servidor de Tiempo");
    add("South",construirCamposServidor());
    setSize(600, 300);
    proc=new ProcesoServidor(this);
    fijarProceso(proc);
  }

  private Panel construirCamposServidor() {
	Panel panelPrincipal = new Panel();
	fTiempoActual = new TextField(5);
	panelPrincipal.add(new Label("Tiempo actual:"));
	panelPrincipal.add(fTiempoActual);
	fValorH = new TextField(5);
	panelPrincipal.add(new Label("H:"));
	panelPrincipal.add(fValorH);
	fTiempoEstProp = new TextField(5);
	panelPrincipal.add(new Label("Tiempo Est. Prop:"));
	panelPrincipal.add(fTiempoEstProp);
	fValorI = new TextField(5);
	panelPrincipal.add(new Label("I:"));
	panelPrincipal.add(fValorI);
	botonInicio = new Button("Iniciar");
	botonInicio.addActionListener(new ManejadorBoton());
	panelPrincipal.add(botonInicio);
	return panelPrincipal;
  }
  
  class ManejadorBoton implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			String com=e.getActionCommand();
			if (com.equals("Iniciar")){
				try {
					tiempoActual = Integer.parseInt(fTiempoActual.getText());
					valorH = Integer.parseInt(fValorH.getText());
					tiempoEstProp = Integer.parseInt(fTiempoEstProp.getText());
					valorI = Integer.parseInt(fValorI.getText());
				} catch (NumberFormatException e1) {
					System.out.println("Uno o más campos vacíos, colocando valores por defecto...");
					tiempoActual = 0;
					valorH = 10;
					tiempoEstProp = 0;
					valorI = 0;
				}
				botonInicio.setEnabled(false);
				imprimeln("======================================");
				imprimeln("Tiempo Actual: "+tiempoActual);
				imprimeln("H: "+valorH);
				imprimeln("Tiempo Estimado de Propagacion: "+tiempoEstProp);
				imprimeln("I: "+valorI);
				imprimeln("======================================");
				proc.start();
			}
		}
	}
}