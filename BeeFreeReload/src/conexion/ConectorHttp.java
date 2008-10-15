package conexion;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import logica.CentralDatos;
import logica.StringTokenizer;


/**
 * <h1> ConectorHttp </h1>
 * 
 * @author Agares Esta clase se puede instanciar una sola vez, de manera
 *         estatica haciendo uso del singleton. permite hacer un request a un
 *         servicio web y retorna una respuesta
 * @see getResponse()
 * 
 */
public class ConectorHttp implements Runnable, ICargable {

	private Thread hilo;

	private static ConectorHttp miConector;

	private String respuesta; // respuesta a la peticion
	private String URL; // URL especifica del servicio web
	private String servicio; // Parametros de entrada al servicio web

	private boolean postMethod = false;

	private boolean terminado = false;

	/**
	 * Constructor privado
	 */
	private ConectorHttp() {

	}

	public void iniciarSesion() {

	}

	/**
	 * este metodo se encarga de conectar con el url y el servicio dado
	 * guardando la respuesta de la conexion realizada
	 */
	public void conectarPost() {

		HttpConnection hc;
		try {
			// hc = (HttpConnection) Connector.open(URL+"?"+servicio,
			// Connector.READ_WRITE);
			hc = (HttpConnection) Connector.open(URL, Connector.READ_WRITE);
			// Informamos del tipo de petición que vamos a efectuar
			hc.setRequestMethod(HttpConnection.POST);

			if (CentralDatos.cookie != null) {
				hc.setRequestProperty("cookie", CentralDatos.cookie);
			}
			// Establecemos algunos campos de cabecera
			hc.setRequestProperty("User-Agent",
					"Profile/MIDP-2.0 Configuration/CLDC-1.0");
			hc.setRequestProperty("Content-Language", "es-ES");
			// 2) Send header information. Required for POST to work! //lo
			// encontre despues XD
			hc.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			// se abre el stream de salida
			servicio = servicio + "&key=SESS55beed18a6a346ca4f214bbcf2af6775";
			OutputStream os = hc.openOutputStream();
			System.out.println("request: " + servicio);
			// escribimos el mensaje de servicio y cerramos el stream
			os.write(servicio.getBytes());

			// abrimos el stream de entrada
			InputStream is = hc.openInputStream();

			// si la respuesta de la coneccion es correcta
			if (hc.getResponseCode() == HttpConnection.HTTP_OK) {
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
				os.close();

			} else {
				// si la respuesta no es correcta, guardamos la causa
				respuesta = hc.getResponseMessage();
			}
			// System.out.println("res " + respuesta);

		} catch (IOException e) {
			e.printStackTrace();
			respuesta = "error";
		}

		CentralDatos.respuesta = respuesta;

		System.out.println("cookie: " + CentralDatos.cookie);
		if (CentralDatos.cookie == null) {

		}
		terminado = true;
	}

	/**
	 * este metodo se encarga de conectar con el url y el servicio dado
	 * guardando la respuesta de la conexion realizada
	 */
	public void conectarGet() {
		HttpConnection hc;
		System.out.println("url: "+URL);
		try {
			hc = (HttpConnection) Connector.open(URL + "?" + servicio,
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
				respuesta = hc.getResponseMessage();
			}

		} catch (IOException e) {
			e.printStackTrace();
			respuesta = "error";
		}
		if (CentralDatos.cookie == null){
			System.out.println(respuesta);
			StringTokenizer tok= new StringTokenizer(respuesta,"-.-");
			
			setCookie(tok.nextElement());
			
			CentralDatos.primeraVez= tok.nextElement().compareTo("0")==0;
			System.out.println("primera "+CentralDatos.primeraVez);
		}
		CentralDatos.respuesta = respuesta;
		System.out.println("respuesta: "+CentralDatos.respuesta);
		terminado = true;
	}

	// solo funciona para maraton movil 2
	public void setCookie(String session) {
		CentralDatos.cookie = "SESS55beed18a6a346ca4f214bbcf2af6775="
				+ session + "; path=/; domain=.maratonmovil2.com";
		System.out.println("setCokkie " + CentralDatos.cookie);
	}

	/**
	 * este metodo es estatico para evitar que se generen multiples conexiones
	 * al tiempo.
	 * 
	 * @param URL
	 *            direccion a la cual se desea conectar HTTP type
	 * @param servicio
	 *            parametros de entrada que se desean enviar en la conexion
	 * @param postMethod
	 *            define si se trabaja con Post o GET
	 * 
	 */
	public static String enviarConexion(String URL, String servicio,
			boolean postMethod) {
		if (miConector == null) {
			miConector = new ConectorHttp();
		}
		miConector.terminado = false;
		miConector.respuesta = null;
		miConector.URL = URL;
		servicio= servicio.toLowerCase();

		servicio.replace('á', 'a');
		servicio.replace('é', 'e');
		servicio.replace('í', 'i');
		servicio.replace('ó', 'o');
		servicio.replace('ú', 'u');
		servicio.replace('ä', 'a');
		servicio.replace('ë', 'e');
		servicio.replace('ï', 'i');
		servicio.replace('ö', 'o');
		servicio.replace('ü', 'u');
		servicio.replace('ñ', 'n');
		
		miConector.servicio = servicio;

		miConector.postMethod = postMethod;

		miConector.hilo = new Thread(miConector);
		miConector.hilo.start();

		// si esta sicronizado espera hasta que se obtenga la respuesta
		/*
		 * if (sincronizado) { while (miConector.hilo != null &&
		 * miConector.hilo.isAlive()){ //miConector.hilo.wait(); } }
		 */
		return miConector.respuesta;

	}

	/**
	 * obtiene la respuesta del servidor, solo si esta clase no esta intentando
	 * conectarse con algun servicio
	 */
	public static String getResponse() {
		if (miConector == null) {
			miConector = new ConectorHttp();
		}

		return miConector.respuesta;
	}

	/**
	 * metodo para realizar la conexion
	 * 
	 * @author Agares
	 * @category
	 *         <h1>hilo </h1>
	 *         {@link #ConectorHttp()}
	 */
	public void run() {
		if (postMethod)
			miConector.conectarPost();
		else
			miConector.conectarGet();
	}

	public static void cerrarSesion() {
		CentralDatos.cookie = null;

	}

	public boolean isTerminado() {
		return terminado;
	}

	public void setDatos() {

	}

	public static ConectorHttp getMiConector() {
		if (miConector == null) {
			miConector = new ConectorHttp();
		}
		return miConector;
	}

}
