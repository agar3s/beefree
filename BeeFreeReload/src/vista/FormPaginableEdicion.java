package vista;


import logica.CentralDatos;
import logica.Constantes;

import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.Graphics;
import com.sun.lwuit.Image;

public class FormPaginableEdicion extends Form implements IPaginable {

	private static FormPaginableEdicion miFormPaginableEdicion;
	private Image buffer;
	private Graphics gBuffer;
	Image editable;
	private int x, y, xSelect, ySelect;

	final int left = -3;
	final int right = -4;
	final int top = -1;
	final int bottom = -2;
	final int fire = -5;

	public boolean seleccionado, editando;
	//private Bocadillo bocadito;

	private FormPaginableEdicion() {

		addCommand(new Command(Constantes.ATRAS_COM));
		addCommand(new Command(Constantes.GUARDAR_COM));
		addCommand(new Command(Constantes.INS_TEX_COM));

		buffer = Image.createImage(getWidth(), getHeight());

	
			editable = CentralDatos.fotoDetalles.foto;
		

		gBuffer = buffer.getGraphics();

		if (getWidth() < editable.getWidth()) {
			editable = editable.scaledWidth(getWidth());
		}
		if (getHeight() < editable.getHeight()) {
			editable = editable.scaledHeight(getHeight());
		}

		x = getWidth() / 2;
		y = (getHeight() - 30) / 2;

		seleccionado = false;
/*
		bocadito = new Bocadillo(
				20,
				26,
				"Este es un texto largo donde se supone se debe dividir en muchos pedazitos, eso es asi es la vida, la vida trist, jajajaja ke risa tan graciosa!!!! huyyy ke mal");
*/
	}

	public void keyPressed(int keyCode) {
		super.keyPressed(keyCode);
		System.out.println(keyCode);
		if (keyCode == left) {
			x = x - 5;
		} else if (keyCode == right) {
			x = x + 5;
		} else if (keyCode == top) {
			y = y - 5;
		} else if (keyCode == bottom) {
			y = y + 5;
		} else if (keyCode == fire) {
			if (seleccionado) {
				int ancho = Math.abs(x - xSelect);
				int alto = Math.abs(y - ySelect);
				editable = buffer.subImage(x < xSelect ? x + 1 : xSelect + 1,
						y < ySelect ? y + 1 : ySelect + 1, ancho - 2, alto - 2,
						false);
				seleccionado = false;
			} else {
				seleccionado = true;
			}

			xSelect = x;
			ySelect = y;
		}
		repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);

		g.clipRect(0, 0, getWidth(), getHeight() - 30);
		gBuffer.setColor(0X000000);
		gBuffer.fillRect(0, 0, getWidth(), getHeight() - 30);

		int difx = Math.abs(buffer.getWidth() - editable.getWidth());
		int dify = Math.abs(buffer.getHeight() - (editable.getHeight() - 30));

		gBuffer.drawImage(editable, difx / 2, dify / 2);

		gBuffer.setColor(0XFF6600);
		gBuffer.fillRect(x - 1, y - 5, 3, 11);
		gBuffer.fillRect(x - 5, y - 1, 11, 3);

		if (seleccionado) {
			int ancho = Math.abs(x - xSelect);
			int alto = Math.abs(y - ySelect);
			gBuffer.drawRect(x < xSelect ? x : xSelect, y < ySelect ? y
					: ySelect, ancho, alto);
		}
		gBuffer.drawRect(x - 7, y - 1, 11, 3);
	//	bocadito.pintar(gBuffer);
		gBuffer.drawRect(x - 8, y - 1, 11, 3);
		g.drawImage(buffer, 0, 0);

	}

	public static FormPaginableEdicion getFormPaginableEdicion() {
		if (miFormPaginableEdicion == null) {
			miFormPaginableEdicion = new FormPaginableEdicion();
		}
		return miFormPaginableEdicion;

	}

	public void destruir() {
		removeAll();
		removeAllCommands();
		miFormPaginableEdicion = null;
		System.gc();
	}
}
