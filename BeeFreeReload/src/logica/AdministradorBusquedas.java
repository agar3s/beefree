package logica;

import vista.ManejadorConexiones;
import vista.Paginador;

import com.sun.lwuit.ComboBox;
import com.sun.lwuit.Container;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.TextField;

import conexion.ConectorRMS;

public class AdministradorBusquedas {

	private static AdministradorBusquedas miAdministradorBusquedas;
	private ConectorRMS conRMS;

	private AdministradorBusquedas() {
		
	}

	public static AdministradorBusquedas getAdministradorBusquedas() {
		if (miAdministradorBusquedas == null) {
			miAdministradorBusquedas = new AdministradorBusquedas();
		}
		return miAdministradorBusquedas;

	}

	public void busquedaPerzonalizada() {
		String aux;
		String tipoBusqueda;
		String lugar;

		Container contenedorForm = (Container) Paginador.getPaginador().current
				.getComponentAt(1);

		aux = ((TextField) contenedorForm.getComponentAt(1)).getText();
		if (aux == null || aux.compareTo("") == 0) {
			Dialog.show("buscar?",
					"por favor verifique el criterio de busqueda", "ok", null);
		} else {

			CentralDatos.criterioBusqueda = aux;

			tipoBusqueda = ((ComboBox) contenedorForm.getComponentAt(3))
					.getSelectedItem().toString();

			lugar = ((ComboBox) contenedorForm.getComponentAt(5))
					.getSelectedItem().toString();

			if (lugar.compareTo("mi movil") == 0) {
				conRMS = ConectorRMS.getConectorRMS();
				CentralDatos.busquedaLocal = true;
				conRMS.busquedaLocal(tipoBusqueda,
						CentralDatos.criterioBusqueda);

			} else {
				
				CentralDatos.busquedaLocal = false;
				// conexion comunidad
				int crit=0;
				if (tipoBusqueda.compareTo("Ciudad") == 0) {
					crit=1;
				} else if (tipoBusqueda.compareTo("Sitio") == 0) {
					crit=2;
				} else {			// sitio turistico
					crit=3;
				}
				ManejadorConexiones.getManejadorConexiones().buscarWeb(CentralDatos.criterioBusqueda,crit);

			}
		}
	}

	public void destruir() {
		miAdministradorBusquedas = null;
	}

}
