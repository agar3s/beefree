package conexion;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import vista.Paginador;

import com.sun.lwuit.Image;

import logica.CentralDatos;
import logica.Constantes;

public class PreLoader implements ICliente, ICargable, Runnable{
	
	private static PreLoader miPreLoader;
	int index=-1;
	
	Thread hilo;
	String URL;
	boolean terminado=false;
	
	private PreLoader(){
		setDatos();
	}
	
	public static PreLoader getPreloader(){
		//XXX cuarta parte de la carga
		if(miPreLoader==null)
			miPreLoader= new PreLoader();
		return miPreLoader;
	}

	public void actualizar() {
		///XXX QUINTA opcion
		System.out.println("actualizada");
		switch(Constantes.VIS_CURRENT){
			case Constantes.BUSCAR_IMAGEN_VIS:
				System.out.println("5 entro perfecto");
				Paginador.getPaginador().setCurrent(Constantes.RESULTADOS_BUSQUEDA_VIS);
			break;
		}
		
	}

	public void realizarPeticion() {
		
	}

	public boolean isTerminado() {
		
		return terminado;
	}

	public void setDatos() {
		//XXX Cuarta parte iterativo
		System.out.println("index de busqueda: "+index);
		index++;
		if(index<CentralDatos.cantidadResultados){
			URL= CentralDatos.resultadosBusqueda[index].URL;
			StringBuffer buffer= new StringBuffer("");
			
			for(int i=0; i<URL.length(); i++){
				char c= URL.charAt(i);
				if(c==' '){
					buffer.append("%20");
				}else{
					buffer.append(c);
				}
			}
			URL= buffer.toString();
			
			hilo= new Thread(this);
			hilo.start();
		}else{
			terminado=true;
		}
	}

	public void run() {
		HttpConnection hc;
		try {
			hc = (HttpConnection) Connector.open(Constantes.HTTP+URL, Connector.READ_WRITE);
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

			// escribimos el mensaje de servicio y cerramos el stream

			// abrimos el stream de entrada
			InputStream is = hc.openInputStream();

			// si la respuesta de la coneccion es correcta
			if (hc.getResponseCode() == HttpConnection.HTTP_OK) {
				CentralDatos.resultadosBusqueda[index].foto= Image.createImage(is);
			} else {
				// si la respuesta no es correcta, guardamos la causa
			}
			// System.out.println("res " + respuesta);
			is.close();
			hc.close();
			is= null;
			hc= null;
		} catch (IOException e) {
			
		}
		
		setDatos();
		System.gc();
	}

}
