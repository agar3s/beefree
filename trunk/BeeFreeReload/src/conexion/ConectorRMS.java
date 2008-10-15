package conexion;

import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;
import javax.microedition.rms.RecordStoreNotOpenException;

import logica.CentralDatos;
import logica.StringTokenizer;
import logica.foto.Foto;

public class ConectorRMS {

	private ConectorFile connFile;

	private String almacenFoto = "fotitos";
	private String almacenLugares = "lugaritos";
	private String almacenNoEnviadas = "noEnvio";
	RecordStore rsFoto;
	RecordStore rsLugaritos;
	RecordStore rsNoEnviado;
	byte[] info;
	
	private static ConectorRMS miConectorRMS;

	private ConectorRMS() {
		connFile = ConectorFile.getConectorFile();
//		 try {
//		 RecordStore.deleteRecordStore(almacenFoto);
//		 RecordStore.deleteRecordStore(almacenLugares);
//		 } catch (RecordStoreNotFoundException e) {
//		 e.printStackTrace();
//		 } catch (RecordStoreException e) {
//		 e.printStackTrace();
//		 }
	}
	
	public static ConectorRMS getConectorRMS() {
		if (miConectorRMS == null) {
			miConectorRMS = new ConectorRMS();
		}
		return miConectorRMS;
		
	}
	
	public void guardarIDFotosNoEnviadas(){
//		TODO el metodo de sincronizar debe usar este almacen para enviar las fotos q no han sido enviadas
		byte[] info=(CentralDatos.idFotoNoEnviada+"").getBytes();
		try {
			rsNoEnviado=RecordStore.openRecordStore(almacenNoEnviadas, true);
			rsNoEnviado.addRecord(info, 0, info.length);
			rsNoEnviado.closeRecordStore();
			
		} catch (RecordStoreNotOpenException e) {
			e.printStackTrace();
		} catch (RecordStoreFullException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}
	}
	
	public void guardarFotografiaLocal(byte[] foto, String datosFoto,
			String datosLugares) {

		StringBuffer sb = new StringBuffer();

		sb.append("BF").append(CentralDatos.fechaActualFotoPrevia);

		connFile.escribirImagen(sb.toString(), foto);

		sb = new StringBuffer();
		sb.append(datosFoto).append("++").append("BF").append(
				CentralDatos.fechaActualFotoPrevia).append(
				CentralDatos.enconding);
		datosFoto = sb.toString();

		info = datosFoto.getBytes();

		try {
			CentralDatos.idFotoNoEnviada=rsFoto.addRecord(info, 0, info.length);

			info = datosLugares.getBytes();

			rsLugaritos.addRecord(info, 0, info.length);

		} catch (RecordStoreNotOpenException e) {
			e.printStackTrace();
		} catch (RecordStoreFullException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}

	}
/**solo es llamado desde descarga de fotos*/
	public void guardarFotografiaLocal( String datosFoto,
			String datosLugares, long fecha) {
		
		StringBuffer sb = new StringBuffer("");

		sb.append(datosFoto).append("++").append("BF").append(
				fecha).append(CentralDatos.enconding);
		datosFoto = sb.toString();

		
		
		info = datosFoto.getBytes();

		try {
			rsFoto.addRecord(info, 0, info.length);

			info = datosLugares.getBytes();

			rsLugaritos.addRecord(info, 0, info.length);

		} catch (RecordStoreNotOpenException e) {
			e.printStackTrace();
		} catch (RecordStoreFullException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}

	}

	
	
	public void abrirAlmacenFoto() {
		try {
			rsFoto = RecordStore.openRecordStore(almacenFoto, true);
			rsLugaritos = RecordStore.openRecordStore(almacenLugares, true);
		} catch (RecordStoreFullException e) {
			e.printStackTrace();
		} catch (RecordStoreNotFoundException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}
	}

	public void cerrarAlmacenFoto() {
		try {
			rsFoto.closeRecordStore();
			rsLugaritos.closeRecordStore();
		} catch (RecordStoreNotOpenException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}
	}

	public void eliminarFotografia(Foto fotoDetalles) {
		abrirAlmacenFoto();

		try {
			rsFoto.deleteRecord(fotoDetalles.idRms);
			rsLugaritos.deleteRecord(fotoDetalles.idRms);

			connFile.borrarFoto(fotoDetalles.pic);

			// removemos el item de lso resultados de busqueda, para no buscar
			// dos veces

			for (int i = 0; i < CentralDatos.resultadosBusqueda.length; i++) {
				if (CentralDatos.resultadosBusqueda[i] == fotoDetalles) {
					Foto[] aux = new Foto[CentralDatos.resultadosBusqueda.length - 1];
					int pos = 0;
					for (int j = 0; j < aux.length; j++) {
						aux[j] = CentralDatos.resultadosBusqueda[pos];
						pos++;
						if (i == pos) {
							pos++;
						}
					}
					i = CentralDatos.resultadosBusqueda.length;
					CentralDatos.resultadosBusqueda = aux;
				}
			}
			System.gc();

		} catch (RecordStoreNotOpenException e) {
			e.printStackTrace();
		} catch (InvalidRecordIDException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}

		cerrarAlmacenFoto();

	}

	public void busquedaLocal(String tipo, String criterioBusqueda) {
		abrirAlmacenFoto();

		RecordEnumeration re = null;
		if (tipo.compareTo("Sitio turistico") == 0) {
			try {
				re = rsLugaritos.enumerateRecords(new FiltroBusquedaLocal(
						criterioBusqueda, "Sitio turistico"), null, true);
			} catch (RecordStoreNotOpenException e) {
				e.printStackTrace();
			}
		} else if (tipo.compareTo("Ciudad") == 0) {
			try {
				re = rsLugaritos.enumerateRecords(new FiltroBusquedaLocal(
						criterioBusqueda, "Ciudad"), null, true);
			} catch (RecordStoreNotOpenException e) {
				e.printStackTrace();
			}
		} else if (tipo.compareTo("Tag") == 0) {
			try {
				re = rsFoto.enumerateRecords(new FiltroBusquedaLocal(
						criterioBusqueda, "Tag"), null, true);
			} catch (RecordStoreNotOpenException e) {
				e.printStackTrace();
			}

		} else if (tipo.compareTo("misFotos") == 0) {
			try {
//				re = rsFoto.enumerateRecords(new FiltroBusquedaLocal(
//						criterioBusqueda, "misFotos"), null, true);
//XXX busca todas lsa fotos no solo las mias				
				CentralDatos.busquedaLocal = true;
				re = rsFoto.enumerateRecords(null, null, true);
			} catch (RecordStoreNotOpenException e) {
				e.printStackTrace();
			}
		}
		try {
			CentralDatos.cantidadResultados = re.numRecords();
		} catch (Exception e) {
			CentralDatos.cantidadResultados = -1;
		}
		
		if (re != null || re.numRecords() != 0) {
			int id;
			byte[] infoFo, infoLug;
			String auxFo, auxLug;
			CentralDatos.resultadosBusqueda = new Foto[re.numRecords()];
			for (int i = 0; i < re.numRecords(); i++) {
				try {

					id = re.nextRecordId();
					infoFo = rsFoto.getRecord(id);
					auxFo = new String(infoFo);

					infoLug = rsLugaritos.getRecord(id);
					auxLug = new String(infoLug);

					CentralDatos.resultadosBusqueda[i] = crearFoto(auxFo,
							auxLug, id);
				} catch (InvalidRecordIDException e) {
					e.printStackTrace();
				} catch (RecordStoreNotOpenException e) {
					e.printStackTrace();
				} catch (RecordStoreException e) {
					e.printStackTrace();
				}
			}
		}
		cerrarAlmacenFoto();
	}

	private Foto crearFoto(String auxFo, String auxLug, int idRms) {

		String name;
		String sitioTuristico;
		String ciudad;
		String pic;
		String descripcion;
		String tags;
		long fecha;
		double longitud;
		double latitud;
		boolean privada;
		boolean favorita;
		String duenio;
		int nid;

		
		StringTokenizer st = new StringTokenizer(auxFo, "++");


		name = st.nextElement();
		descripcion = st.nextElement();
		tags = st.nextElement();
		fecha = Long.parseLong(st.nextElement());
		longitud = Double.parseDouble(st.nextElement());
		latitud = Double.parseDouble(st.nextElement());
		if (st.nextElement().toLowerCase().compareTo("false") == 0) {
			privada = false;
		} else {
			privada = true;
		}
		if (st.nextElement().toLowerCase().compareTo("false") == 0) {
			favorita = false;
		} else {
			favorita = true;
		}
		duenio = st.nextElement();
		nid=Integer.parseInt(st.nextElement());
		pic = st.nextElement();


		st = new StringTokenizer(auxLug, "++");
		ciudad = st.nextElement();
		sitioTuristico = st.nextElement();

		return new Foto(name, sitioTuristico, ciudad, pic, descripcion, tags,
				fecha, longitud, latitud, privada, favorita, idRms, duenio,nid);
	}

}
