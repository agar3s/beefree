package vista;

import java.io.IOException;

import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;

import logica.CentralDatos;
import logica.Constantes;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Font;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.MediaComponent;
import com.sun.lwuit.TextField;
import com.sun.lwuit.geom.Dimension;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.util.Resources;

public class FormPaginableInicio extends Form implements IPaginable {

	private static FormPaginableInicio miFormPaginableInicio;
	private Label l;
	private TextField t;
	static public Player p;

	private FormPaginableInicio() {

	}

	public static FormPaginableInicio getFormPaginableInicio() {
		if (miFormPaginableInicio == null) {
			miFormPaginableInicio = new FormPaginableInicio();
			miFormPaginableInicio.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

		}
		return miFormPaginableInicio;

	}

	public void destruir() {
		if (Constantes.VIS_CURRENT == Constantes.BEEFREE_VIS) {
			if (p != null) {
				try {
					p.stop();
					p.deallocate();
					p = null;
				} catch (MediaException e) {
					e.printStackTrace();
				}
			}
		}
		removeAll();
		removeAllCommands();
		miFormPaginableInicio = null;
		System.gc();
	}

	public void setLogin() {
		setTitle("I.Guana Bee Free");

		l = new Label("Login");
		addComponent(l);
		t = new TextField();
		t.setConstraint(TextField.ANY);
		t.setColumns(20);
		addComponent(t);
		l = new Label("password");
		addComponent(l);
		t = new TextField();
		t.setConstraint(TextField.PASSWORD);
		t.setColumns(20);
		addComponent(t);

		Constantes.BOTONuno.setText("Iniciar sesión");
		addComponent(Constantes.BOTONuno);

		Constantes.BOTONdos.setText("Registrarse");
		addComponent(Constantes.BOTONdos);

		addCommand(new Command(Constantes.SALIR_COM));
	}

	public void setMenuPrincipal() {
		try {
			Resources r = Resources.open("/businessTheme.res");
			r.getImage("explorar");

			setTitle("Menu Principal");

			Font f = Font.createSystemFont(Font.FACE_PROPORTIONAL,
					Font.STYLE_BOLD, Font.SIZE_LARGE);

			Constantes.BOTONuno.setText("Bee Free");
			Constantes.BOTONuno.setIcon(r.getImage("beeFree"));
			Constantes.BOTONuno.setTextPosition(Button.BOTTOM);
			Constantes.BOTONuno.getStyle().setBorder(null);
			Constantes.BOTONuno.getStyle().setFont(f, true);
			Constantes.BOTONuno.getStyle().setBgTransparency(128);
			addComponent(Constantes.BOTONuno);

			Constantes.BOTONdos.setText("Explorar");
			Constantes.BOTONdos.setIcon(r.getImage("explorar"));
			Constantes.BOTONdos.setTextPosition(Button.BOTTOM);
			Constantes.BOTONdos.getStyle().setBorder(null);
			Constantes.BOTONdos.getStyle().setFont(f);
			Constantes.BOTONdos.getStyle().setBgTransparency(128);
			addComponent(Constantes.BOTONdos);

			Constantes.BOTONtres.setText("Noticias");
			Constantes.BOTONtres.setIcon(r.getImage("noticias"));
			Constantes.BOTONtres.setTextPosition(Button.BOTTOM);
			Constantes.BOTONtres.getStyle().setBorder(null);
			Constantes.BOTONtres.getStyle().setFont(f);
			Constantes.BOTONtres.getStyle().setBgTransparency(128);
			addComponent(Constantes.BOTONtres);

			Constantes.BOTONcuatro.setText("Opciones");
			Constantes.BOTONcuatro.setIcon(r.getImage("opciones"));
			Constantes.BOTONcuatro.setTextPosition(Button.BOTTOM);
			Constantes.BOTONcuatro.getStyle().setBorder(null);
			Constantes.BOTONcuatro.getStyle().setFont(f);
			Constantes.BOTONcuatro.getStyle().setBgTransparency(128);
			addComponent(Constantes.BOTONcuatro);

			Constantes.BOTONcinco.setText("Ayuda");
			Constantes.BOTONcinco.setIcon(r.getImage("ayuda"));
			Constantes.BOTONcinco.setTextPosition(Button.BOTTOM);
			Constantes.BOTONcinco.getStyle().setBorder(null);
			Constantes.BOTONcinco.getStyle().setFont(f, true);
			Constantes.BOTONcinco.getStyle().setBgTransparency(128);
			addComponent(Constantes.BOTONcinco);

			Constantes.BOTONseis.setText("Cerrar sesión");
			Constantes.BOTONseis.setIcon(r.getImage("cerrarSesion"));
			Constantes.BOTONseis.setTextPosition(Button.BOTTOM);
			Constantes.BOTONseis.getStyle().setBorder(null);
			Constantes.BOTONseis.getStyle().setFont(f, true);
			Constantes.BOTONseis.getStyle().setBgTransparency(128);
			addComponent(Constantes.BOTONseis);

		} catch (IOException ioe) {
			System.out.println("Couldn't load theme.");
		}

	}

	public void setCrearCuenta() {

		setTitle("I.Guana Bee Free");

		l = new Label("Login");
		addComponent(l);
		t = new TextField();
		t.setConstraint(TextField.ANY);
		addComponent(t);
		l = new Label("Correo electronico");
		addComponent(l);
		t = new TextField();
		t.setConstraint(TextField.EMAILADDR);
		addComponent(t);

		Constantes.BOTONuno.setText("Enviar");
		addComponent(Constantes.BOTONuno);

		addCommand(new Command(Constantes.ATRAS_COM));

	}

	public void setMediaComponent() {

		setLayout(new BorderLayout());
		// setTitle("Bee free");
		try {

			// en emulador funciona con video
			try {
				p = Manager.createPlayer("capture://image");
			} catch (Exception e) {
				p = Manager.createPlayer("capture://video");
			}
			// pero en la vida real funciona con image
			p.prefetch();
			p.realize();
			// p.start();

			MediaComponent mc = new MediaComponent(p) {
				public Dimension getPreferredSize() {
					return new Dimension(50, 50);
				}
			};

			mc.setFocusable(false);
			mc.setFocusPainted(false);

			Dimension d = mc.getPreferredSize();
			if (d.getHeight() > getHeight() - 50)
				d = new Dimension(getWidth(), getHeight() - 50);
			mc.setSize(d);
			mc.getStyle().setMargin(5, 5, 5, 5);

			addComponent(BorderLayout.CENTER, mc);

			Constantes.BOTONuno.setText("Tomar Foto");

			addComponent(BorderLayout.NORTH, Constantes.BOTONuno);
//			addComponent(BorderLayout.SOUTH, new Label(""));
//			addComponent(BorderLayout.EAST, new Label(""));
//			addComponent(BorderLayout.WEST, new Label(""));
			addCommand(new Command(Constantes.ATRAS_COM));

			mc.start();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MediaException e) {
			e.printStackTrace();
		}
	}

	public void setVistaPrevia() {
		setTitle("Vista Previa");
		Image foto = Image.createImage(CentralDatos.fotoPreviaByte, 0,
				CentralDatos.fotoPreviaByte.length);

		if (getWidth() < foto.getWidth()) {
			foto = foto.scaledWidth(getWidth());
		}
		if (getHeight() < foto.getHeight()) {
			foto = foto.scaledHeight(getHeight());
		}

		addComponent(new Button(foto));
		addCommand(new Command(Constantes.ATRAS_COM));
		addCommand(new Command(Constantes.GUARDAR_COM));

	}

	public void setNoticias() {

		setTitle("Noticias nuevas");

		List lista = new List();
		lista.setFixedSelection(List.FIXED_NONE_CYCLIC);
		lista.getStyle().setBgTransparency(128);

		for (int i = 0; i < CentralDatos.noticias.length-1; i++) {
			
			lista.addItem(CentralDatos.noticias[i]);
		}
		addComponent(lista);

		addCommand(new Command(Constantes.ATRAS_COM));
		this.setScrollable(false);

	}
}
