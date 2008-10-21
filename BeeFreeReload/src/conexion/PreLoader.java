package conexion;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import vista.DialogCargando;
import vista.Paginador;

import com.sun.lwuit.Dialog;
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
		index=-1;
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
		System.out.println("actualizada "+Constantes.VIS_CURRENT);
		System.out.println("busquedas: "+CentralDatos.buscar+" "+CentralDatos.busquedaLocal);
		switch(Constantes.VIS_CURRENT){
			case Constantes.BUSCAR_IMAGEN_VIS:
				System.out.println("HP!!!!!"); 
				//Dialog.show("Actualizar", "entro a la linea 44", "ok",null);
				System.out.println("5 entro perfecto");
				Paginador.getPaginador().setCurrent(Constantes.RESULTADOS_BUSQUEDA_VIS);
			break;
			case Constantes.BUSCAR_COORD_MANUAL_VIS:
				//Dialog.show("Actualizar", "entro a la linea 44", "ok",null);
				Paginador.getPaginador().setCurrent(Constantes.RESULTADOS_BUSQUEDA_COORD_VIS);
				break;
			default:
				System.out.println("5 no entro perfecto "+Constantes.VIS_CURRENT+ " "+Constantes.BUSCAR_IMAGEN_VIS);
				break;
		}
		miPreLoader= null;
		
	}

	public void realizarPeticion() {
		
	}

	public boolean isTerminado() {
		
		return terminado;
	}

	public void setDatos() {
		//XXX Cuarta parte iterativo
		System.out.println("index de busqueda: "+index+" d "+CentralDatos.cantidadResultados);
		//Dialog.show("seteo de datos", "index de busqueda: "+index+ " de "+CentralDatos.cantidadResultados, "ok",null);
		index++;
		if(index==1)
			DialogCargando.getDialogCargando().showModeless();
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
			//Dialog.show("Termino el seteo de datos!", ":D: ", "ok",null);
			terminado=true;
		}
	}

	public void run() {
		HttpConnection hc;
		try {
			hc = (HttpConnection) Connector.open(Constantes.HTTP+URL, Connector.READ_WRITE);
			//Dialog.show("antes del query", "URL de descarga: "+Constantes.HTTP+URL, "ok",null);
			System.out.println(Constantes.HTTP+URL);
			// Informamos del tipo de petición que vamos a efectuar
			hc.setRequestMethod(HttpConnection.GET);

			if (CentralDatos.cookie != null) {
				hc.setRequestProperty("cookie", CentralDatos.cookie);
			}else{
				Dialog.show("cookie null!", ":S:", "ok",null);
			}
			// Establecemos algunos campos de cabecera
			hc.setRequestProperty("User-Agent",
					"Profile/MIDP-2.0 Configuration/CLDC-1.0");
			hc.setRequestProperty("Content-Language", "es-ES");
			// 2) Send header information. Required for POST to work! //lo
			// encontre despues XD
//			hc.setRequestProperty("Content-Type",
//					"application/x-www-form-urlencoded");

			// escribimos el mensaje de servicio y cerramos el stream

			// abrimos el stream de entrada
			InputStream is = hc.openInputStream();

			// si la respuesta de la coneccion es correcta
			if (hc.getResponseCode() == HttpConnection.HTTP_OK) {
				CentralDatos.resultadosBusqueda[index].foto= Image.createImage(is);
			} else {
				Dialog.show("Error en la conexion", "http: "+hc.getResponseMessage(), "ok",null);
				// si la respuesta no es correcta, guardamos la causa
			}
			// System.out.println("res " + respuesta);
			is.close();
			hc.close();
			is= null;
			hc= null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setDatos();
		System.gc();
	}

}
