package sistemaDistribuido.sistema.exclusionMutua;

import sistemaDistribuido.sistema.exclusionMutua.ProcesoServidor;
import sistemaDistribuido.sistema.exclusionMutua.MicroNucleoFrame;
import sistemaDistribuido.sistema.exclusionMutua.ProcesoFrame;

public class ServidorFrame extends ProcesoFrame{
  private static final long serialVersionUID=1;
  private ProcesoServidor proc;

  public ServidorFrame(MicroNucleoFrame frameNucleo){
    super(frameNucleo,"Proceso Coordinador");
    setSize(400, 300);
    proc = new ProcesoServidor(this);
    fijarProceso(proc);
    proc.start();
  }
}