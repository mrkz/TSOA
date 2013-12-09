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

  public ServidorFrame(MicroNucleoFrame frameNucleo){
    super(frameNucleo,"Servidor de Tiempo");
    Panel panelServidor = construirCamposServidor();
    add("South",panelServidor);
    setSize(600, 300);
    proc=new ProcesoServidor(this);
    proc.setParentFrame(this);
    fijarProceso(proc);
  }

  private Panel construirCamposServidor() {
	Panel panelPrincipal = new Panel();
	fTiempoActual = new TextField(9);
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
  
  public void setTiempoActualGUI(int newVal){
	  fTiempoActual.setText(""+newVal);
  }
  
  class ManejadorBoton implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			String com=e.getActionCommand();
			if (com.equals("Iniciar")){
				try {
					proc.setTiempoActual(Integer.parseInt(fTiempoActual.getText()));
					proc.setValorH(Integer.parseInt(fValorH.getText()));
					proc.setTiempoEstProp(Integer.parseInt(fTiempoEstProp.getText()));
					proc.setValorI(Integer.parseInt(fValorI.getText()));
				} catch (NumberFormatException e1) {
					System.out.println("Uno o más campos vacíos, colocando valores por defecto...");
					proc.setTiempoActual(0);
					proc.setValorH(10);
					proc.setTiempoEstProp(0);
					proc.setValorI(0);
				}
				botonInicio.setEnabled(false);
				imprimeln("======================================");
				imprimeln("Tiempo Actual: "+proc.getTiempoActual());
				imprimeln("H: "+proc.getValorH());
				imprimeln("Tiempo Estimado de Propagacion: "+proc.getTiempoEstProp());
				imprimeln("I: "+proc.getValorI());
				imprimeln("======================================");
				proc.start();
			}
		}
	}
}