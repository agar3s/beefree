package vista;

import java.io.IOException;

import logica.Constantes;

import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.Graphics;
import com.sun.lwuit.Image;
import com.sun.lwuit.layouts.BoxLayout;

public class FormPaginableAyuda extends Form implements IPaginable, Runnable {

	private Thread hilo;
	private static FormPaginableAyuda miFormAyuda;

	public byte exenaActual;

	private FormPaginableAyuda() {

	}

	public static FormPaginableAyuda getFormAyuda() {
		if (miFormAyuda == null) {
			miFormAyuda = new FormPaginableAyuda();
			miFormAyuda.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		}
		return miFormAyuda;

	}

	public void setMenuAyuda() {
		exenaActual = 0;
		removeAllCommands();
		setTitle("Ayuda");

		Constantes.BOTONuno.setText("Tomar fotos");
		addComponent(Constantes.BOTONuno);

		Constantes.BOTONdos.setText("buscar fotos");
		addComponent(Constantes.BOTONdos);

		Constantes.BOTONtres.setText("Editar fotos");
		addComponent(Constantes.BOTONtres);

		Constantes.BOTONcuatro.setText("Subir fotos");
		addComponent(Constantes.BOTONcuatro);

		addCommand(new Command(Constantes.ATRAS_COM), 0);

		repaint();
	}

	public void setReproducirExena() {
		removeAll();
		setTitle(null);
		addCommand(new Command(Constantes.REPETIR_COM), 1);
		addCommand(new Command(Constantes.ANTERIOR_COM), 1);
		addCommand(new Command(Constantes.SIGUIENTE_COM), 1);
		repaint();
		hilo = new Thread(this);
		hilo.start();

	}

	public void paint(Graphics g) {
		super.paint(g);
		switch (exenaActual) {
		case Constantes.AYUDA_TOMAR_VIS:
			try {
				g.drawImage(Image.createImage("/iguana.png"), 0, 0);

			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case Constantes.AYUDA_BUSCAR_VIS:

			break;
		case Constantes.AYUDA_EDITAR_VIS:

			break;
		case Constantes.AYUDA_SUBIR_VIS:

			break;

		default:
			break;
		}

	}

	public void destruir() {

		removeAll();
		removeAllCommands();
		miFormAyuda = null;
		System.gc();
	}

	public void run() {
		switch (exenaActual) {

		case Constantes.AYUDA_TOMAR_VIS:

			break;
		case Constantes.AYUDA_BUSCAR_VIS:

			break;
		case Constantes.AYUDA_EDITAR_VIS:

			break;
		case Constantes.AYUDA_SUBIR_VIS:

			break;
		}

	}

}
