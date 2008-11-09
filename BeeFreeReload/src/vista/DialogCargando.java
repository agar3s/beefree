package vista;

import java.util.Hashtable;

import logica.CentralDatos;

import com.sun.lwuit.Dialog;
import com.sun.lwuit.Font;
import com.sun.lwuit.Graphics;
import com.sun.lwuit.animations.CommonTransitions;

import conexion.ConectorDownload;
import conexion.ConectorHttp;
import conexion.YahooMaps;
import conexion.HttpMultipartRequest;
import conexion.ICargable;
import conexion.ICliente;
import conexion.conectorGPS;

public class DialogCargando extends Dialog implements Runnable {

	final static byte CONEXION_FILE = 1;
	final static byte CONEXION_GPS = 2;
	final static byte CONEXION_HTTP_GET = 3;
	final static byte CONEXION_HTTP_POST = 7;
	final static byte CONEXION_GOOGLE_MAP = 4;
	final static byte CONEXION_UPLOAD_FOTO = 5;
	final static byte CONEXION_SMS = 6;
	final static byte CONEXION_DOWNLOAD = 8;
	final static byte CONEXION_PREVIEWS = 9;

	int cont;
	boolean cargando = false;
	Thread hilo;
	int x = getWidth() / 2;
	int y = getHeight() / 2;

	ICliente cliente;
	ICargable conexion;

	private static DialogCargando miDialogCargando;

	private DialogCargando() {
		setTransitionInAnimator(CommonTransitions.createSlide(
				CommonTransitions.SLIDE_VERTICAL, false, 250));
		setTransitionOutAnimator(CommonTransitions.createSlide(
				CommonTransitions.SLIDE_VERTICAL, true, 250));

	}

	public static DialogCargando getDialogCargando() {
		if (miDialogCargando == null) {
			miDialogCargando = new DialogCargando();
		}
		return miDialogCargando;
	}

	public void iniciarCarga(ICliente cliente, byte tipoConexion, String URL,
			String param) {
		cargando = true;
		this.cliente = cliente;
		System.out.println(cliente);
		switch (tipoConexion) {
		case CONEXION_FILE:
			// Modify
			// conexion= ConectorFile.getConectorFile();
			break;
		case CONEXION_GOOGLE_MAP:
			// modify
			conexion = new YahooMaps();
			((YahooMaps) conexion).retrieveStaticImage(CentralDatos.longitud,
					CentralDatos.latitud);

			break;
		case CONEXION_GPS:
			// Modify
			conexion = new conectorGPS();
			((conectorGPS) conexion).obtenerPosicion();
			break;
		case CONEXION_HTTP_POST:
			conexion = ConectorHttp.getMiConector();
			ConectorHttp.enviarConexion(URL, param, true);
			break;
		case CONEXION_HTTP_GET:
			conexion = ConectorHttp.getMiConector();
			ConectorHttp.enviarConexion(URL, param, false);
			break;
		case CONEXION_SMS:
			// Modify
			// conexion= SMSSender.sendMessage(/)
			break;
		case CONEXION_UPLOAD_FOTO:
			// Modify
			Hashtable params = new Hashtable();
			
			params.put("uid", ""+CentralDatos.UID);
			params.put("ciudad", quitarAcentos(CentralDatos.ciudadFotoPrevia));
			params.put("descripcion", quitarAcentos(CentralDatos.descripcionFotoPrevia));
			params.put("user", ""+CentralDatos.loginActual);
			params.put("nombre", quitarAcentos(CentralDatos.nombreFotoPrevia));
			params.put("sitio", quitarAcentos(CentralDatos.sitioTuristicoFotoPrevia));
			params.put("tags", quitarAcentos(CentralDatos.tagsFotoPrevia));
			params.put("fecha", "" + CentralDatos.fechaActualFotoPrevia);
			params.put("privada", CentralDatos.fotoPrivada ? "1" : "0");
			params.put("latitud", "" + CentralDatos.latitud);
			params.put("longitud", "" + CentralDatos.longitud);
			params.put("favorito", CentralDatos.fotoFavorita ? "1" : "0");
			params.put("size", ""+CentralDatos.fotoPreviaByte.length);
			try {
				conexion = new HttpMultipartRequest(URL, params, "imagen",
						CentralDatos.nombreFotoPrevia+".jpeg", "image/jpeg",
						CentralDatos.fotoPreviaByte);
				((HttpMultipartRequest) conexion).send();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case CONEXION_DOWNLOAD:
			conexion= new ConectorDownload(URL);
			break;
		
		case CONEXION_PREVIEWS:
			conexion= (ICargable) cliente;
			break;
		}

		hilo = new Thread(this);
		hilo.start();
		repaint();
		showModeless();
		System.out.println("agora si");
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(0X000000);

		g.setColor(0XFFFFFF);
		StringBuffer buff = new StringBuffer("cargando");
		for (int i = 0; i < cont; i++) {
			buff.append(".");
		}
		String cargando = buff.toString();
		Font f = g.getFont();
		int ancho = f.charsWidth(cargando.toCharArray(), 0, cargando.length());
		g.drawString(cargando, (getWidth() - ancho) / 2, getHeight() / 2);

		// g.fillRect(getWidth()/2-cont, getHeight()/2, cont*2, 20);

	}

	public void run() {
		while (!conexion.isTerminado()) {

			repaint();
			cont++;
			try {
				Thread.sleep(300);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			cont++;
			if (cont > 5) {
				cont = 0;
			}

		}

		cliente.actualizar();

	}
	
	public String quitarAcentos(String palabras){
		
		palabras= palabras.toLowerCase();

		palabras= palabras.replace('á', 'a');
		palabras= palabras.replace('é', 'e');
		palabras= palabras.replace('í', 'i');
		palabras= palabras.replace('ó', 'o');
		palabras= palabras.replace('ú', 'u');
		palabras= palabras.replace('ä', 'a');
		palabras= palabras.replace('ë', 'e');
		palabras= palabras.replace('ï', 'i');
		palabras= palabras.replace('ö', 'o');
		palabras= palabras.replace('ü', 'u');
		palabras= palabras.replace('ñ', 'n');
		
		return palabras;
	}

}
