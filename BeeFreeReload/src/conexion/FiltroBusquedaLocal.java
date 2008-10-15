package conexion;

import javax.microedition.rms.RecordFilter;

import logica.StringTokenizer;

public class FiltroBusquedaLocal implements RecordFilter {

	String criterio;
	String tipo;

	public FiltroBusquedaLocal(String criterio, String tipo) {

		this.criterio = criterio.toLowerCase().trim();

		this.tipo = tipo;

	}

	public boolean matches(byte[] arg) {

		String dato = new String(arg);
		StringTokenizer st = new StringTokenizer(dato, "++");
		String info;
		String tags;

		if (tipo.compareTo("Tag") == 0) {
			st.nextElement();
			st.nextElement();
			tags = st.nextElement();
			tags = tags.toLowerCase();

			String tgs[] = procesarTags(tags);

			for (int i = 0; i < tgs.length; i++) {
				if (tgs[i].compareTo(criterio) == 0) {
					return true;
				}
			}

			return false;
		} else if (tipo.compareTo("misFotos") == 0) {
			// centralDatos.nombreFotoPrevia + "++"
			// + CentralDatos.descripcionFotoPrevia + "++"
			// + CentralDatos.tagsFotoPrevia + "++"
			// + CentralDatos.fechaActualFotoPrevia + "++"
			// + CentralDatos.longitud + "++" + CentralDatos.latitud
			// + "++" + CentralDatos.fotoPrivada, favorita,duenio;
			st.nextElement();
			st.nextElement();
			st.nextElement();
			st.nextElement();
			st.nextElement();
			st.nextElement();
			st.nextElement();
			st.nextElement();
			info = st.nextElement().toLowerCase();
			System.out.println("busqueda mis fotos: " + info);

			if (info.compareTo(criterio) == 0) {
				return true;

			} else {
				return false;
			}

		}
		// ciudad ++ sitio
		else if (tipo.compareTo("Ciudad") == 0) {
			info = st.nextElement();
			info = info.toLowerCase().trim();
			if (info.indexOf(criterio) == -1) {
				return false;

			} else {
				return true;
			}
		} else {

			st.nextElement();
			info = st.nextElement();
			info = info.toLowerCase().trim();
			if (info.indexOf(criterio) == -1) {
				return false;

			} else {
				return true;
			}
		}

	}

	public String[] procesarTags(String aux) {

		String tags[] = null;

		if (aux.charAt(aux.length() - 1) == ',') {
			aux = aux.substring(0, aux.length() - 1);
		}

		StringTokenizer st = new StringTokenizer(aux, ",");

		tags = new String[st.tokens];

		for (int i = 0; i < tags.length; i++) {
			tags[i] = st.nextElement();
			tags[i] = tags[i].trim();
			System.gc();
		}

		return tags;
	}

}
