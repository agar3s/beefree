package conexion;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import logica.CentralDatos;

public class HttpMultipartRequest implements Runnable, ICargable {

	Thread hilo;
	static final String BOUNDARY = "----------V2ymHFg03ehbqgZCaKO6jy";

	byte[] postBytes = null;
	String url = null;
	
	boolean terminado=false;
	
	
	public HttpMultipartRequest(String url, Hashtable params, String fileField,
			String fileName, String fileType, byte[] fileBytes)
			throws Exception {
		terminado=false;
		this.url = url;

		String boundary = getBoundaryString();

		String boundaryMessage = getBoundaryMessage(boundary, params,
				fileField, fileName, fileType);

		String endBoundary = "\r\n--" + boundary + "--\r\n";

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		bos.write(boundaryMessage.getBytes());

		bos.write(fileBytes);

		bos.write(endBoundary.getBytes());

		this.postBytes = bos.toByteArray();

		bos.close();
	}

	String getBoundaryString() {
		return BOUNDARY;
	}

	String getBoundaryMessage(String boundary, Hashtable params,
			String fileField, String fileName, String fileType) {
		StringBuffer res = new StringBuffer("--").append(boundary).append(
				"\r\n");

		Enumeration keys = params.keys();

		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			String value = (String) params.get(key);

			res.append("Content-Disposition: form-data; name=\"").append(key)
					.append("\"\r\n").append("\r\n").append(value).append(
							"\r\n").append("--").append(boundary)
					.append("\r\n");
		}
		res.append("Content-Disposition: form-data; name=\"").append(fileField)
				.append("\"; filename=\"").append(fileName).append("\"\r\n")
				.append("Content-Type: ").append(fileType).append("\r\n\r\n");

		return res.toString();
	}

	public void send() {
		hilo = new Thread(this);
		hilo.start();

	}

	byte[] res;

	public void run() {
		HttpConnection hc = null;

		InputStream is = null;

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {

			hc = (HttpConnection) Connector.open(url);

			hc.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + getBoundaryString());

			hc.setRequestMethod(HttpConnection.POST);
			hc.setRequestProperty("cookie", CentralDatos.cookie);
			hc.setRequestProperty("User-Agent",
					"Profile/MIDP-2.0 Configuration/CLDC-1.0");

			hc.setRequestProperty("Content-Language", "es-ES");

			OutputStream dout = hc.openOutputStream();

			dout.write(postBytes);

			dout.close();

			int ch;

			is = hc.openInputStream();

			while ((ch = is.read()) != -1) {
				bos.write(ch);
			}
			res = bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bos != null)
					bos.close();

				if (is != null)
					is.close();

				if (hc != null)
					hc.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		CentralDatos.respuesta= new String(res);
		terminado=true;

	}

	public boolean isTerminado() {
		return terminado;
	}

	public void setDatos() {

	}

}
