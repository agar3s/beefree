package vista;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import logica.CentralDatos;

import com.sun.lwuit.Display;

import conexion.ConectorRMS;

public class MidletBeeFree extends MIDlet {
	private static MidletBeeFree miMidletBeeFree;

	public MidletBeeFree() {
		miMidletBeeFree = this;

	}

	public static MidletBeeFree getMidletBeeFree() {

		return miMidletBeeFree;

	}

	public void destruir() {
		miMidletBeeFree = null;
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {

	}

	protected void pauseApp() {

	}

	protected void startApp() throws MIDletStateChangeException {
		String havelocation = System
				.getProperty("microedition.location.version");
		if (havelocation != null) {
			CentralDatos.haveLocation = true;
		} else {
			CentralDatos.haveLocation = false;
		}

//		 new ConectorRMS();
		Display.init(this);
		Paginador.getPaginador();

	}

}
