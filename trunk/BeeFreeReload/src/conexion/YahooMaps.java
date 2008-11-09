package conexion;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import logica.CentralDatos;

import com.sun.lwuit.Image;

public class YahooMaps implements Runnable, ICargable {
	Thread hilo;
	StringBuffer buffer;

	String URL = "";
	boolean terminado = false;

	public YahooMaps() {
		super();
	}

	public void retrieveStaticImage(double lng, double lat) {
		terminado = false;
		buffer = new StringBuffer();
		buffer.append("http://local.yahooapis.com/MapsService/V1/mapImage?appid=YD-4g6HBf0_JX0yq2IsdnV1Ne9JTpKxQ3Miew--&latitude=");
		buffer.append(lat);
		buffer.append("&longitude=");
		buffer.append(lng);
		buffer.append("&image_type=gif&zoom=");
		buffer.append(CentralDatos.zoom);
		buffer.append("&image_height=");
		buffer.append(CentralDatos.anchoImagen);
		buffer.append("&image_width=");
		buffer.append(CentralDatos.altoImagen);
		//buffer.append(");

		URL = buffer.toString();
		hilo = new Thread(this);
		hilo.start();
	}

	public void run() {
		
		String respuesta="";
		
		HttpConnection hc;
		System.out.println("url: "+URL);
		try {
			hc = (HttpConnection) Connector.open(URL,
					Connector.READ_WRITE);

			// Informamos del tipo de petición que vamos a efectuar
			hc.setRequestMethod(HttpConnection.GET);

			// Establecemos algunos campos de cabecera
			hc.setRequestProperty("User-Agent",
			"Profile/MIDP-2.0 Configuration/CLDC-1.0");
			hc.setRequestProperty("Content-Language", "es-ES");

			// abrimos el stream de entrada
			InputStream is = hc.openInputStream();

			// si la respuesta de la coneccion es correcta
			if (hc.getResponseCode() == HttpConnection.HTTP_OK) {
				// System.out.println(hc.getHeaderField("Set-Cookie"));
				// obtenemos la longitud de la respuesta
				int lon = (int) hc.getLength();

				if (lon != -1) {
					// escribimos un byte array para escribir la respuesta
					byte datos[] = new byte[lon];
					is.read(datos, 0, datos.length);
					respuesta = new String(datos);

				} else {
					// abrimos un stream de bytes
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					int ch;
					while ((ch = is.read()) != -1)
						baos.write(ch);
					respuesta = new String(baos.toByteArray());
					baos.close();
				}

				// cerramos el stream de entrada
				is.close();
			} else {
				// si la respuesta no es correcta, guardamos la causa
				respuesta = "error";
			}

		} catch (IOException e) {
			e.printStackTrace();
			respuesta = "error";
		}
		System.out.println("repuesta= "+respuesta);
		if(respuesta.compareTo("error")==0){
			System.out.println(respuesta.substring(0,6));
			
		}else if(respuesta.compareTo("The following errors were detected: Invalid Value: zoom")==0){
			respuesta= "error";
		}else if(respuesta.substring(0,6).compareTo("<Error")==0){
			respuesta= "error";
		}
		
		if(respuesta.compareTo("error")!=0){
			System.out.println("instance"+'\"'+">");
			int begin= respuesta.indexOf("instance"+'\"'+">")+10;
			int fin= respuesta.indexOf("</Result>");
			URL=respuesta.substring(begin, fin);
			System.out.println(URL);
			hc = null;
			InputStream is = null;

			try {
				hc = (HttpConnection) Connector.open(URL);
				hc.setRequestMethod(HttpConnection.GET);
				hc.setRequestProperty("User-Agent",
				"Profile/MIDP-2.0 Configuration/CLDC-1.0");
				hc.setRequestProperty("Content-Language", "es-ES");

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
		}
		terminado = true;
		
	}

	public boolean isTerminado() {
		return terminado;
	}

	public void setDatos() {

	}
}