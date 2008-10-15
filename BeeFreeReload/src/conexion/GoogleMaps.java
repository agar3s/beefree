package conexion;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import logica.CentralDatos;

import com.sun.lwuit.Image;

public class GoogleMaps implements Runnable, ICargable {
	Thread hilo;
	StringBuffer buffer;

	String URL = "";
	boolean terminado = false;

	public GoogleMaps() {
		super();
	}

	public void retrieveStaticImage(double lng, double lat) {
		terminado = false;
		buffer = new StringBuffer();
		buffer.append("http://maps.google.com/staticmap?center=");
		buffer.append(lat);
		buffer.append(",");
		buffer.append(lng);
		buffer.append("&format=jpg&zoom=");
		buffer.append(CentralDatos.zoom);
		buffer.append("&size=");
		buffer.append(CentralDatos.anchoImagen);
		buffer.append("x");
		buffer.append(CentralDatos.altoImagen);
		buffer.append("&maptype=mobile");

		URL = buffer.toString();
		hilo = new Thread(this);
		hilo.start();
	}

	public void run() {
		HttpConnection hc = null;
		InputStream is = null;

		try {
			hc = (HttpConnection) Connector.open(URL);
			hc.setRequestMethod(HttpConnection.GET);

			is = hc.openInputStream();
			CentralDatos.imagenMapa = Image.createImage(is);

			is.close();
			hc.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			is = null;
			hc = null;
		}
		System.out.println("imagen traida");
		terminado = true;

	}

	public boolean isTerminado() {
		return terminado;
	}

	public void setDatos() {

	}
}