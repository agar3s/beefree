package vista;

import java.io.IOException;

import logica.Constantes;

import com.sun.lwuit.Form;
import com.sun.lwuit.Graphics;
import com.sun.lwuit.Image;

public class FormPresentacion extends Form implements Runnable, IPaginable {

	Thread hilo;
	Image im, pro;
	int cont = 0;
	private static FormPresentacion miContainerPresentacion;

	public static FormPresentacion getContainerPresentacion() {
		if (miContainerPresentacion == null) {
			miContainerPresentacion = new FormPresentacion();
		}
		return miContainerPresentacion;

	}

	public void destruir() {
		miContainerPresentacion = null;
	}

	private FormPresentacion() {
		super();

		try {

			pro = Image.createImage("/iguana.png");

			im = pro.scaledSmallerRatio(getWidth(), getHeight());
			pro = im;
			im = im.modifyAlpha((byte) -50);
		} catch (IOException e) {
			e.printStackTrace();
		}
		hilo = new Thread(this);
		hilo.start();
		System.out.println(getWidth());
		System.out.println(getHeight());
	}

	public void keyPressed(int keyCode) {
		super.keyPressed(keyCode);
		if (keyCode != -6 && keyCode != -7) {
			Paginador.getPaginador().adelante = true;
			Paginador.getPaginador().setCurrent(Constantes.LOGIN_VIS);
		}

	}

	public void paint(Graphics g) {

		// super.paint(g);
		g.setColor(0xFFFFFF);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.drawImage(im, (getWidth() - im.getWidth()) / 2, (getHeight() - im
				.getHeight()) / 2);
	}

	public void run() {
		while (true) {
			try {
				repaint();
				if (cont > 122) {

				} else {
					cont = cont + 5;
					im = pro.modifyAlpha((byte) cont);

				}
				Thread.sleep(50);
				System.gc();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
