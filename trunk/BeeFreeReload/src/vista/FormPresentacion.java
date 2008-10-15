package vista;

import java.io.IOException;

import logica.Constantes;

import com.sun.lwuit.Font;
import com.sun.lwuit.Form;
import com.sun.lwuit.Graphics;
import com.sun.lwuit.Image;
import com.sun.lwuit.util.Resources;

public class FormPresentacion extends Form implements Runnable, IPaginable {

	Thread hilo;
	Image iguana, BeeFree;
	int cont = 0;
	
	Font f;
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

		Resources r;
		try {
			r = Resources.open("/businessTheme.res");
			iguana= r.getImage("PresentacionIm");
			BeeFree= r.getImage("PresentacionBF");
			System.out.println(iguana);
			System.out.println(BeeFree);
			f= r.getFont("SampleFont");
			//iguana= iguana.scaled(width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		try {
//
//			pro = Image.createImage("/iguana.png");
//
//			im = pro.scaledSmallerRatio(getWidth(), getHeight());
//			pro = im;
//			im = im.modifyAlpha((byte) -50);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		hilo = new Thread(this);
		hilo.start();
		System.out.println(getWidth());
		System.out.println(getHeight());
	}

	public void keyPressed(int keyCode) {
		super.keyPressed(keyCode);
		if (keyCode != -6 && keyCode != -7) {
			if(cont<25){
				cont=26;
			}else{
				Paginador.getPaginador().adelante = true;
				Paginador.getPaginador().setCurrent(Constantes.LOGIN_VIS);
			}
		}

	}

	public void paint(Graphics g) {

		// super.paint(g);
		g.setFont(f);
		g.setColor(0xFFFFFF);
		g.fillRect(0, 0, getWidth(), getHeight());

		
		if(cont>25){
			
			g.drawImage(BeeFree, (getWidth() - BeeFree.getWidth()) / 2, (getHeight() - BeeFree
					.getHeight()) / 2);
		}else{
			g.drawImage(iguana, (getWidth() - iguana.getWidth()) / 2, (getHeight() - iguana
					.getHeight()) / 2);
		}
		
		if(cont%7!=0){
			g.setColor(0X131313);
			g.drawString("Presiona cualquier tecla", getWidth()/2-100, getHeight()-50);
		}
	}

	public void run() {
		while (true) {
			try {
				repaint();
					cont = cont + 1;
					if(cont>50){
						cont=26;
					}
				Thread.sleep(100);
				System.gc();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
