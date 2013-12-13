package sistemaDistribuido.sistema.exclusionMutua;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.exclusionMutua.ProcesoCliente;
import sistemaDistribuido.sistema.exclusionMutua.MicroNucleoFrame;
import sistemaDistribuido.sistema.exclusionMutua.ProcesoFrame;
import java.awt.Button;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;

public class ClienteFrame extends ProcesoFrame{
	private static final long serialVersionUID=1;
	private ProcesoCliente proc;
	// archivo, puerto, impresora, memoria
	private Button bSolArchivo, bSolPuerto, bSolImpresora, bSolMemoria,
				   bLibArchivo, bLibPuerto, bLibImpresora, bLibMemoria;

	public ClienteFrame(MicroNucleoFrame frameNucleo){
		super(frameNucleo,"Proceso Cliente");
		add("South",construirBotonesRecursos());
		setSize(400, 400);
		validate();
		proc = new ProcesoCliente(this);
		fijarProceso(proc);
		proc.start();
	}
	
	private void inicializaBotones(){
		ManejadorSolicitud ms = new ManejadorSolicitud();
		bSolArchivo = new Button("Solicitar recurso ARCHIVO");
		bSolPuerto = new Button("Solicitar recurso PUERTO");
		bSolImpresora = new Button("Solicitar recurso IMPRESORA");
		bSolMemoria = new Button("Solicitar recurso MEMORIA");
		bLibArchivo = new Button("Liberar recurso ARCHIVO");
		bLibPuerto = new Button("Liberar recurso PUERTO");
		bLibImpresora = new Button("Liberar recurso IMPRESORA");
		bLibMemoria = new Button("Liberar recurso MEMORIA");
		bloqueaBotonesLiberacion();
		bSolArchivo.addActionListener(ms);
		bSolPuerto.addActionListener(ms);
		bSolImpresora.addActionListener(ms);
		bSolMemoria.addActionListener(ms);
		bLibArchivo.addActionListener(ms);
		bLibPuerto.addActionListener(ms);
		bLibImpresora.addActionListener(ms);
		bLibMemoria.addActionListener(ms);
	}
	
	private Panel construirBotonesRecursos() {
		Panel panelPrincipal = new Panel(), tmp;
		panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
		
		inicializaBotones();
		tmp = new Panel();
		tmp.add(bSolArchivo);
		tmp.add(bLibArchivo);
		panelPrincipal.add(tmp);
		tmp = new Panel();
		tmp.add(bSolPuerto);
		tmp.add(bLibPuerto);
		panelPrincipal.add(tmp);
		tmp = new Panel();
		tmp.add(bSolImpresora);
		tmp.add(bLibImpresora);
		panelPrincipal.add(tmp);
		tmp = new Panel();
		tmp.add(bSolMemoria);
		tmp.add(bLibMemoria);
		panelPrincipal.add(tmp);
		return panelPrincipal;
		
	}
	
	private void bloqueaBotonesSolicitud(){
		bSolArchivo.setEnabled(false);
		bSolPuerto.setEnabled(false);
		bSolImpresora.setEnabled(false);
		bSolMemoria.setEnabled(false);
	}
	
	private void desbloqueaBotonesSolicitud(){
		bSolArchivo.setEnabled(true);
		bSolPuerto.setEnabled(true);
		bSolImpresora.setEnabled(true);
		bSolMemoria.setEnabled(true);
	}
	
	private void bloqueaBotonesLiberacion(){
		bLibArchivo.setEnabled(false);
		bLibPuerto.setEnabled(false);
		bLibImpresora.setEnabled(false);
		bLibMemoria.setEnabled(false);
	}
	
	
	public void habilitaLiberacion(short recurso) {
		bloqueaBotonesSolicitud();
		bloqueaBotonesLiberacion();
		switch(recurso){
		case IMPRESORA:
			bLibImpresora.setEnabled(true);
			break;
		case PUERTO:
			bLibPuerto.setEnabled(true);
			break;
		case ARCHIVO:
			bLibArchivo.setEnabled(true);
			break;
		case MEMORIA:
			bLibMemoria.setEnabled(true);
			break;
		default:
			imprimeln("Solicitud de liberación de recurso desconocido");
			imprimeln("Bloqueando cliente...");
			break;
			
		}
		
	}
	
	public void habilitaSolicitud() {
		
		bloqueaBotonesLiberacion();
		desbloqueaBotonesSolicitud();
	}
	
	public void esperoRecurso(){
		bloqueaBotonesLiberacion();
		bloqueaBotonesSolicitud();
	}

	class ManejadorSolicitud implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			String com = e.getActionCommand();
			switch(com){
			case "Solicitar recurso ARCHIVO":
				proc.solicitaRecurso(ARCHIVO);
				break;
			case "Solicitar recurso PUERTO":
				proc.solicitaRecurso(PUERTO);
				break;
			case "Solicitar recurso IMPRESORA":
				proc.solicitaRecurso(IMPRESORA);
				break;
			case "Solicitar recurso MEMORIA":
				proc.solicitaRecurso(MEMORIA);
				break;
			case "Liberar recurso ARCHIVO":
				proc.liberaRecurso(ARCHIVO);
				break;
			case "Liberar recurso PUERTO":
				proc.liberaRecurso(PUERTO);
				break;
			case "Liberar recurso IMPRESORA":
				proc.liberaRecurso(IMPRESORA);
				break;
			case "Liberar recurso MEMORIA":
				proc.liberaRecurso(MEMORIA);
				break;
			default:
				System.out.println("Botón desconocido...");
				//System.exit(1);
					break;
				
			}
			Nucleo.reanudarProceso(proc);
		}
	}
}
