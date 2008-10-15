package logica.foto;

import logica.CentralDatos;

import com.sun.lwuit.Image;

public class Foto {

	public String name;
	public String sitioTuristico;
	public String ciudad;
	public String descipcion;
	public String tags;
	public long fecha;
	public double longitud, latitud;
	public boolean privada;
	public Image foto;
	public int idRms;
	public boolean isFavorita;
	public String duenio;
	public String pic;
	public int nid;
	
	public String URL;

	public Foto(String name, String sitioTuristico, String ciudad, String pic,
			String descripcion, String tags, long fecha, double longitud,
			double latitud, boolean privada, boolean isFavorita, int idRms,
			String duenio,int nid) {

		this.name = name;
		this.sitioTuristico = sitioTuristico;
		this.ciudad = ciudad;
		this.pic = pic;
		this.descipcion = descripcion;
		this.tags = tags;
		this.fecha = fecha;
		this.longitud = longitud;
		this.latitud = latitud;
		this.privada = privada;
		this.idRms = idRms;
		this.isFavorita = isFavorita;
		this.duenio = duenio;
		this.nid=nid;

	}
	
	public String getDatosRMS(){
		String datosRmsFoto = name + "++"
		+ descipcion + "++"
		+ tags + "++"
		+ fecha + "++"
		+ longitud + "++" + latitud + "++"
		+ privada + "++" + isFavorita
		+ "++" + CentralDatos.loginActual+"++"+nid;
		
		return datosRmsFoto;
	}
	
	public String getSitioRMS(){
		String datosRmsSitio = ciudad + "++"
		+ sitioTuristico;
		return datosRmsSitio;
	}

	public void destruir() {
		name = null;
		sitioTuristico = null;
		pic = null;
		this.name = null;
		this.sitioTuristico = null;
		this.ciudad = null;
		this.pic = null;
		this.descipcion = null;
		this.tags = null;
	}

}
