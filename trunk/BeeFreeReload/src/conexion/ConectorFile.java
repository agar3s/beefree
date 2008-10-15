package conexion;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.io.file.FileSystemRegistry;

import com.sun.lwuit.Image;

import logica.CentralDatos;

public class ConectorFile {

	Thread hilo;
	byte estado;
	final byte lectura = 1;
	final byte escrituraTxt = 2;
	final byte escrituraImagen = 3;
	final byte listar = 4;
	String titulo, contenido, root, pic;
	byte[] buf;
	byte[] image;
	private static ConectorFile miConectorFile;

	public static ConectorFile getConectorFile() {
		if (miConectorFile == null) {
			miConectorFile = new ConectorFile();
		}
		return miConectorFile;

	}

	private ConectorFile() {

		SelectRoot();
		root = "file:///" + root;

		try {
			FileConnection conn = (FileConnection) Connector.open(root
					+ "beeFree/");
			conn.mkdir();
			conn.close();
		} catch (IOException e) {
		}
		root = root + "beeFree/";
	}

	public void escribirImagen(String titulo, byte[] image) {
		this.image = image;
		this.titulo = titulo;
		FileConnection conn;
		try {
			conn = (FileConnection) Connector.open(root + titulo
					+ CentralDatos.enconding, Connector.READ_WRITE);
			if (!conn.exists()) {
				conn.create();
			}
			OutputStream outSt = conn.openOutputStream();
			// PrintStream printSt = new PrintStream(outSt);
			// printSt.println(contenido);
			outSt.write(image);
			outSt.close();
			conn.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void leerImagen(String pic) {
		estado = lectura;
		this.pic = pic;
		FileConnection conn;
		try {
			System.out.println(root);
			conn = (FileConnection) Connector.open(root + pic, Connector.READ);
			InputStream is = conn.openInputStream();

			CentralDatos.fotoBytebusqueda = Image.createImage(is);

			// byte[] b = null;
			// int tamanio = is.available();
			// b = new byte[tamanio];
			// is.read(b);
			// CentralDatos.fotoBytebusqueda=b;
			//			
			is.close();
			is = null;
			conn.close();

		} catch (IOException e2) {
			e2.printStackTrace();

		}
	}

	/*
	 * este me lista todas las posibles ubicaciones de guardado en memoria por
	 * lo general es memoria interna, y tarjeta de memoria
	 */
	public void SelectRoot() {
		Enumeration rootListEnum = FileSystemRegistry.listRoots();
		while (rootListEnum.hasMoreElements()) {
			root = (String) rootListEnum.nextElement();
		}
	}

	public void borrarFoto(String pic) {
		FileConnection conn = null;
		try {
			conn = (FileConnection) Connector.open(root + pic, Connector.WRITE);
			conn.delete();
			conn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
