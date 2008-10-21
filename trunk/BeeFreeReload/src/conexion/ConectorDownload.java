package conexion;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.io.ContentConnection;

import logica.CentralDatos;
import logica.Constantes;
import logica.foto.Foto;

import com.sun.lwuit.Image;

public class ConectorDownload implements Runnable, ICargable{

	ContentConnection conexionContenido;
	String url;
	Thread hilo;
	byte[] imageData;
	private boolean terminado=false;
	
	
	public ConectorDownload(String url) {
		super();
		terminado= false;
		this.url = url;
		hilo = new Thread(this);
		hilo.start();
	}

	public void run() {
		StringBuffer buffer= new StringBuffer("");
		
		for(int i=0; i<url.length(); i++){
			char c= url.charAt(i);
			if(c==' '){
				buffer.append("%20");
			}else{
				buffer.append(c);
			}
		}
		url= buffer.toString();
		try {

			conexionContenido = (ContentConnection) Connector.open(url);
			
			DataInputStream iStrm = conexionContenido.openDataInputStream(); 
			ByteArrayOutputStream bStrm = null; 

			try
			{
			// ContentConnection includes a length method
	
			int length = (int) conexionContenido.getLength();
			if (length != -1){
				imageData = new byte[length];

				// Read the png into an array
				// iStrm.read(imageData);
				iStrm.readFully(imageData);
			}
			else // Length not available...
			{ 
				bStrm = new ByteArrayOutputStream();
				int ch;
				while ((ch = iStrm.read()) != -1)
					bStrm.write(ch);
				imageData = bStrm.toByteArray();
				bStrm.close(); 
			}

			
			}catch(Exception e){
				e.printStackTrace();
			}
			iStrm.close();
			conexionContenido.close();
			
			bStrm=null;
			iStrm= null;
			conexionContenido=null;
			System.gc();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		setDatos();
		
	}

	public byte[] getByteArray(Image image) {

		// Allocate new memory for the int-array and fill the array in the
		// getRGB method.
		int[] raw = image.getRGB();

		byte[] rawByte = new byte[image.getWidth() * image.getHeight() * 4];// ARGB
		int a, r, g, b;
		int n = 0;

		// loop through the int-array and get the 4 bytes of each value to the
		// byte-array.
		// since java isn't supporting unsigned bytes we need to store the ARGB
		// values from -128 to 127
		// where -128 = 128, -127 = 129 and -1 = 255
		for (int i = 0; i < raw.length; i++) {

			// Right-shift the values to fit in a byte.
			int ARGB = raw[i];
			System.out.println(ARGB);
			a = (ARGB & 0xFF000000) >> 24;
			r = (ARGB & 0xFF0000) >> 16;
			g = (ARGB & 0xFF00) >> 8;
			b = (ARGB & 0xFF);

			rawByte[n] = (byte) b;
			rawByte[n + 1] = (byte) g;
			rawByte[n + 2] = (byte) r;
			rawByte[n + 3] = (byte) a;
			n += 4;
		}

		raw = null;
		return rawByte;
	}

	public boolean isTerminado() {
		return terminado;
	}

	public void setDatos() {
		if(Constantes.VIS_CURRENT==Constantes.DETALLES_BI_VIS ){
			Foto temp=CentralDatos.fotoDetalles;
	
			ConectorRMS rms= ConectorRMS.getConectorRMS();
			rms.abrirAlmacenFoto();
			rms.guardarFotografiaLocal(temp.getDatosRMS(), temp.getSitioRMS(),temp.fecha);
			rms.cerrarAlmacenFoto();
			ConectorFile.getConectorFile().escribirImagen("BF"+temp.fecha, imageData);
		}else if(Constantes.VIS_CURRENT== Constantes.RESULTADOS_BUSQUEDA_VIS){
			CentralDatos.fotoDetalles.foto= Image.createImage(imageData,0,imageData.length);
		}
		terminado= true;
	}

}
