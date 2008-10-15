package conexion;

import javax.microedition.location.Coordinates;
import javax.microedition.location.Criteria;
import javax.microedition.location.Location;
import javax.microedition.location.LocationException;
import javax.microedition.location.LocationProvider;

import logica.CentralDatos;

import com.sun.lwuit.Dialog;

public class conectorGPS implements Runnable, ICargable {

	Criteria criterio;
	LocationProvider lp;
	Location loc;
	Coordinates coor;

	Thread hilo;

	public void obtenerPosicion() {
		CentralDatos.GPSLISTO = false;
		hilo = new Thread(this);
		hilo.start();
	}

	public void run() {
		CentralDatos.haveGPS = false;
		criterio = new Criteria();
		criterio.setHorizontalAccuracy(50);
		criterio.setVerticalAccuracy(50);

		try {
			lp = LocationProvider.getInstance(criterio);
			loc = lp.getLocation(-1);
			coor = loc.getQualifiedCoordinates();
			CentralDatos.latitud = coor.getLatitude();
			CentralDatos.longitud = coor.getLongitude();

			CentralDatos.haveGPS = true;
		} catch (LocationException e) {
			Dialog.show("location", "" + "gps no disponible, buscando en red", "ok", "back");
			CentralDatos.haveGPS = false;

			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		CentralDatos.GPSLISTO = true;
	}

	public boolean isTerminado() {
		return CentralDatos.GPSLISTO;
	}

	public void setDatos() {

	}

}
