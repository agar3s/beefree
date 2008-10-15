package vista;

import logica.Constantes;

import com.sun.lwuit.ComboBox;
import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.TextField;
import com.sun.lwuit.layouts.BoxLayout;

public class FormPaginableExplorar extends Form implements IPaginable {

	private static FormPaginableExplorar miFormPaginableExplorar;
	private Label l;
	private TextField t;
	public ComboBox criterio;
	public ComboBox dominio;
	public List resultados;

	private FormPaginableExplorar() {

	}

	public static FormPaginableExplorar getFormPaginableExplorar() {
		if (miFormPaginableExplorar == null) {
			miFormPaginableExplorar = new FormPaginableExplorar();
			miFormPaginableExplorar.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		}
		return miFormPaginableExplorar;

	}

	public void setMenuExplorar() {

		setTitle("Explorar");

		Constantes.BOTONuno.setText("Mis fotos");
		addComponent(Constantes.BOTONuno);

		Constantes.BOTONdos.setText("Buscar fotos");
		addComponent(Constantes.BOTONdos);

		Constantes.BOTONtres.setText("Buscar por coordenadas");
		addComponent(Constantes.BOTONtres);

		Constantes.BOTONcuatro.setText("Explorar el mundo");
		addComponent(Constantes.BOTONcuatro);

		addCommand(new Command(Constantes.ATRAS_COM));

	}

	public void setFormBuscar() {

		setTitle("Buscar");

		l = new Label("que desea buscar?");
		addComponent(l);

		t = new TextField();
		t.setColumns(40);

		addComponent(t);

		l = new Label("tipo de busqueda");
		addComponent(l);

		List list = new List();
		list.addItem("Tag");
		list.addItem("Ciudad");
		list.addItem("Sitio turistico");

		criterio = new ComboBox(list.getModel());
		addComponent(criterio);

		l = new Label("buscar en");
		addComponent(l);

		list = new List();
		list.addItem("Comunidad");
		list.addItem("mi movil");

		dominio = new ComboBox(list.getModel());

		addComponent(dominio);

		Constantes.BOTONuno.setText("Buscar");

		addComponent(Constantes.BOTONuno);

		addCommand(new Command(Constantes.ATRAS_COM));
	}

	public void destruir() {
		removeAll();
		removeAllCommands();

		miFormPaginableExplorar = null;
		System.gc();
	}
}
