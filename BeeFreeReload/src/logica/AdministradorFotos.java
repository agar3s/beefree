package logica;

import com.sun.lwuit.CheckBox;
import com.sun.lwuit.ComboBox;
import com.sun.lwuit.Container;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.TextField;

import conexion.ConectorRMS;

import vista.FormPaginableVerDetalles;
import vista.Paginador;

public class AdministradorFotos {

	private static AdministradorFotos miAdministradorFotos;
	ConectorRMS conrms;

	private AdministradorFotos() {

	}

	public static AdministradorFotos getAdministradorFotos() {
		if (miAdministradorFotos == null) {
			miAdministradorFotos = new AdministradorFotos();
		}
		return miAdministradorFotos;

	}

	public void formatearCamposFoto() {

		CentralDatos.nombreFotoPrevia = "";
		CentralDatos.ciudadFotoPrevia = "";
		CentralDatos.sitioTuristicoFotoPrevia = "";
		CentralDatos.descripcionFotoPrevia = "";
		CentralDatos.tagsFotoPrevia = "";
		CentralDatos.fechaActualFotoPrevia = 0;
		CentralDatos.longitud = 0;
		CentralDatos.latitud = 0;
		// CentralDatos.fotoPreviaByte = null;
		CentralDatos.fotoPrivada = false;
		CentralDatos.fotoFavorita = false;

	}

	public boolean guardarInfoAddFoto(FormPaginableVerDetalles current) {

		String aux;

		Container contenedorForm = (Container) Paginador.getPaginador().current
				.getComponentAt(1);

		aux = ((TextField) contenedorForm.getComponentAt(1)).getText();
		if (aux == null || aux.compareTo("") == 0) {
			return false;
		} else {
			CentralDatos.nombreFotoPrevia = aux;
		}
		try{
			
			aux = ((ComboBox) contenedorForm.getComponentAt(3)).getSelectedItem()
			.toString();
			
			if (aux == null || aux.compareTo("") == 0) {
				return false;
			} else {
				CentralDatos.ciudadFotoPrevia = aux;
			}
			
			aux = ((ComboBox) contenedorForm.getComponentAt(5)).getSelectedItem()
			.toString();
			
			if (aux == null || aux.compareTo("") == 0) {
				return false;
			} else {
				CentralDatos.sitioTuristicoFotoPrevia = aux;
			}
			aux = ((TextArea) contenedorForm.getComponentAt(7)).getText();
			
			if (aux == null || aux.compareTo("") == 0) {
				return false;
			} else {
				CentralDatos.descripcionFotoPrevia = aux;
			}
			
			aux = ((TextArea) contenedorForm.getComponentAt(9)).getText();
			
			if (aux == null || aux.compareTo("") == 0) {
				return false;
			} else {
				
				CentralDatos.tagsFotoPrevia = aux;
				// }
			}
			CentralDatos.fotoPrivada = ((CheckBox) contenedorForm
					.getComponentAt(12)).isSelected();
			
			CentralDatos.fotoFavorita = ((CheckBox) contenedorForm
					.getComponentAt(13)).isSelected();
			
		
		System.gc();
		return true;
		}catch (NullPointerException e) {
			System.gc();
			Dialog.show("error", "no es posible guardar la imagen, debe asociarla a una ciudad y a un sitio turistico", "ok",null);
			return false;
		}

	}


	public void guardarFoto(int nid){
		if (conrms == null) {
			conrms = ConectorRMS.getConectorRMS();
		}
		
		String datosRmsFoto = CentralDatos.nombreFotoPrevia + "++"
		+ CentralDatos.descripcionFotoPrevia + "++"
		+ CentralDatos.tagsFotoPrevia + "++"
		+ CentralDatos.fechaActualFotoPrevia + "++"
		+ CentralDatos.longitud + "++" + CentralDatos.latitud + "++"
		+ CentralDatos.fotoPrivada + "++" + CentralDatos.fotoFavorita
		+ "++" + CentralDatos.loginActual+"++"+nid;
		
		String datosRmsSitio = CentralDatos.ciudadFotoPrevia + "++"
		+ CentralDatos.sitioTuristicoFotoPrevia;
		
		conrms.abrirAlmacenFoto();
		conrms.guardarFotografiaLocal(CentralDatos.fotoPreviaByte,
				datosRmsFoto, datosRmsSitio);
		conrms.cerrarAlmacenFoto();
	
	}
	
	public void destruir() {
		miAdministradorFotos = null;
	}

	public void guardarCampos() {
		
		String aux;
		Container contenedorForm = (Container) Paginador.getPaginador().current
				.getComponentAt(1);

		aux = ((TextField) contenedorForm.getComponentAt(1)).getText();
		CentralDatos.nombreFotoPrevia = aux;
		try {
			aux = ((ComboBox) contenedorForm.getComponentAt(3))
					.getSelectedItem().toString();
			CentralDatos.ciudadFotoPrevia = aux;
		} catch (NullPointerException e) {
			CentralDatos.ciudadFotoPrevia = "";
		}

		try {
			aux = ((ComboBox) contenedorForm.getComponentAt(5))
					.getSelectedItem().toString();
			CentralDatos.sitioTuristicoFotoPrevia = aux;

		} catch (NullPointerException e) {
			CentralDatos.sitioTuristicoFotoPrevia = "";
		}

		aux = ((TextArea) contenedorForm.getComponentAt(7)).getText();

		CentralDatos.descripcionFotoPrevia = aux;

		aux = ((TextArea) contenedorForm.getComponentAt(9)).getText();
		CentralDatos.tagsFotoPrevia = aux;

		((CheckBox) contenedorForm.getComponentAt(12))
				.setSelected(CentralDatos.fotoPrivada);

		((CheckBox) contenedorForm.getComponentAt(13))
				.setSelected(CentralDatos.fotoFavorita);

	}
}
