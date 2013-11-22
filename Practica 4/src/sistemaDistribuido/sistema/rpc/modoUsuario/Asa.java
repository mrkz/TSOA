package sistemaDistribuido.sistema.rpc.modoUsuario;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.ParMaquinaProceso;

/**
* Edited: Simental Magaña Marcos Eleno Joaquín
* Para práctica 4
* Se agrega Clase InfoProceso para uso de esta en asa
*/
public class Asa implements ParMaquinaProceso{
        private int idProceso;
        private String ipProceso;
        
        public Asa(int id, String ip){
                setId(id);
                setIp(ip);
        }
        
        private void setId(int newId){
                idProceso = newId;
        }
        
        private void setIp(String newIp){
                ipProceso = newIp;
        }

		@Override
		public String dameIP() {
			return ipProceso;
		}

		@Override
		public int dameID() {
			return idProceso;
		}
}