package logica;

import com.sun.lwuit.Font;
import com.sun.lwuit.Graphics;

public class Bocadillo {

	int x, y;
	String texto;
	int direccion = 0;

	int ancho, alto;
	int width = 0;

	public Bocadillo(int x, int y, String texto) {
		super();
		this.x = x;
		this.y = y;
		this.texto = texto;

		int size = texto.length();
		if (size > 20) {
			ancho = 200;
		}
		Font f = Font.getDefaultFont();

		width = f.charsWidth(texto.toCharArray(), 0, texto.length()) / (100);

		alto = f.getHeight() * (width + 1);

		if (width > 0)
			ancho = 100;
		else
			ancho = f.charsWidth(texto.toCharArray(), 0, texto.length());

		System.out.println(texto);
		System.out.println("ancho: " + ancho);

	}

	public void pintar(Graphics g) {
		System.out.println("pinteseee");
		g.setColor(0XFFFFFF);
		g.fillRect(x + 5, y, ancho + 30, alto);
		g.fillTriangle(x, y, x + 5, y - 5, x + 5, y + 5);
		g.setColor(0X000000);
		int cont = texto.length() / width;
		for (int i = 0; i < width; i++) {
			g.drawChars(texto.toCharArray(), i * cont, cont, x + 7, y
					+ g.getFont().getHeight() * i);
		}
		int lww = texto.length() % width;
		g.drawChars(texto.toCharArray(), texto.length() - lww, lww, x + 7, y
				+ g.getFont().getHeight() * width);

	}

}
