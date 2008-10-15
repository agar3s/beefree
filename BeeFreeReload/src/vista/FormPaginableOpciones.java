package vista;

import java.util.Enumeration;

import javax.microedition.pim.Contact;
import javax.microedition.pim.ContactList;
import javax.microedition.pim.PIM;
import javax.microedition.pim.PIMException;

import logica.CentralDatos;
import logica.Constantes;

import com.sun.lwuit.CheckBox;
import com.sun.lwuit.ComboBox;
import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.TextField;
import com.sun.lwuit.layouts.BoxLayout;

import conexion.SMSSender;

public class FormPaginableOpciones extends Form implements IPaginable {

	private static FormPaginableOpciones miFormPaginableOpciones;
	private TextField t;
	private Label l;

	private String nombresContactos[];
	private String telContactos[];
	public CheckBox checkList[];

	private FormPaginableOpciones() {

	}

	public static FormPaginableOpciones getFormPaginableOpciones() {
		if (miFormPaginableOpciones == null) {
			miFormPaginableOpciones = new FormPaginableOpciones();
			miFormPaginableOpciones.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		}
		return miFormPaginableOpciones;

	}

	public void setMenuOpciones() {

		setTitle("Opciones");

		Constantes.BOTONuno.setText("Cambiar password");
		addComponent(Constantes.BOTONuno);

		Constantes.BOTONdos.setText("Sincronizar");
		addComponent(Constantes.BOTONdos);

		Constantes.BOTONtres.setText("Invitar amigos");
		addComponent(Constantes.BOTONtres);

		Constantes.BOTONcuatro.setText("Mi perfil");
		addComponent(Constantes.BOTONcuatro);

		addCommand(new Command(Constantes.ATRAS_COM));

	}

	public void setFormCambiarContrasenia() {
		setTitle("Cambiar password");

		l = new Label("escriba su nuevo pass");
		addComponent(l);

		t = new TextField();
		t.setConstraint(TextField.PASSWORD);
		addComponent(t);

		l = new Label("confirme su nuevo pass");
		addComponent(l);

		t = new TextField();
		t.setConstraint(TextField.PASSWORD);
		addComponent(t);

		Constantes.BOTONuno.setText("Enviar cambios");
		addComponent(Constantes.BOTONuno);
		
		addCommand(new Command(Constantes.ATRAS_COM));
	}

	public void setListaContactos() {

		listarContactos();
		int tam = nombresContactos.length;

		checkList = new CheckBox[tam];
		for (int i = 0; i < tam; i++) {
			checkList[i] = new CheckBox();
			checkList[i].setText(nombresContactos[i]);
			addComponent(checkList[i]);
		}
		if (tam == 0) {
			telContactos = new String[3];

			telContactos[0] = "3134099178";
			telContactos[1] = "3134099179";
			telContactos[2] = "3134099180";

			checkList = new CheckBox[3];
			checkList[0] = new CheckBox();
			checkList[0].setText("fici");
			addComponent(checkList[0]);
			checkList[1] = new CheckBox();
			checkList[1].setText("fassci");
			addComponent(checkList[1]);
			checkList[2] = new CheckBox();
			checkList[2].setText("ficiopp");
			addComponent(checkList[2]);

		}
		addCommand(new Command(Constantes.ATRAS_COM));
		addCommand(new Command(Constantes.ACEPTAR_COM));

	}

	public void destruir() {

		removeAll();
		removeAllCommands();
		miFormPaginableOpciones = null;
		System.gc();

	}

	public void listarContactos() {
		// con esta linea sabemos si el movil soporta pim api. retorna null si
		// no es asi
		String s = System.getProperty("microedition.pim.version");
		System.out.println(s);

		// el pim solo se puede instancias una vez y mediante un singleton
		PIM singleton = PIM.getInstance();

		ContactList list = null;

		try {
			list = (ContactList) singleton.openPIMList(PIM.CONTACT_LIST,
					PIM.READ_ONLY);
			boolean nameFormated = list
					.isSupportedField(Contact.FORMATTED_NAME);
			Enumeration todos = list.items();

			int cont = 0;
			// cuenta la cantidad de registros existentes
			while (todos.hasMoreElements()) {
				todos.nextElement();
				cont++;
			}

			// inicializa los arrays con el tamaño indicado
			nombresContactos = new String[cont];
			telContactos = new String[cont];

			todos = list.items();
			cont = 0;
			String namessss[] = null;
			int tel = 0;
			Contact contac;
			while (todos.hasMoreElements()) {
				contac = (Contact) todos.nextElement();

				if (nameFormated) {
					nombresContactos[cont] = contac.getString(
							Contact.FORMATTED_NAME, 0);
				} else {
					namessss = contac.getStringArray(Contact.NAME, 0);
					nombresContactos[cont] = namessss[Contact.NAME_GIVEN] + " "
							+ namessss[Contact.NAME_FAMILY];
				}

				tel = contac.countValues(Contact.TEL);
				for (int j = 0; j < tel; j++) {
					if ((contac.getAttributes(Contact.TEL, j) != 0)) {
						// tel number
						telContactos[cont] = contac.getString(Contact.TEL, j);
					}
				}

				cont++;

			}
		} catch (PIMException e) {
			e.printStackTrace();
		}
	}

	public void setDetallesPerfilDeUsuario() {
		
		setTitle("Perfil de usuario");

		l = new Label("nombre");
		addComponent(l);

		t = new TextField();
		t.setConstraint(TextField.ANY);
		t.setText(CentralDatos.perfilNombre);
		addComponent(t);

		l = new Label("apellido");
		addComponent(l);

		t = new TextField();
		t.setConstraint(TextField.ANY);
		t.setText(CentralDatos.perfilApellido);
		addComponent(t);

		l = new Label("telefono");
		addComponent(l);

		t = new TextField();
		t.setConstraint(TextField.NUMERIC);
		t.setText(CentralDatos.perfilTelefono);
		addComponent(t);

		l = new Label("ciudad");
		addComponent(l);

		t = new TextField();
		t.setConstraint(TextField.ANY);
		t.setText(CentralDatos.perfilCiudad);
		addComponent(t);

		l = new Label("pais");
		addComponent(l);

		t = new TextField();
		t.setConstraint(TextField.ANY);
		t.setText(CentralDatos.perfilPais);
		addComponent(t);

		l = new Label("profesion");
		addComponent(l);

		t = new TextField();
		t.setConstraint(TextField.ANY);
		t.setText(CentralDatos.perfilProfesion);
		addComponent(t);

		l = new Label("sexo");
		addComponent(l);

		List la = new List();
		la.addItem("masculino");
		la.addItem("femenino");

		ComboBox cb = new ComboBox(la.getModel());

		if (CentralDatos.perfilSexo.compareTo("masculino") == 0) {
			cb.setSelectedIndex(0);
		} else {
			cb.setSelectedIndex(1);
		}

		addComponent(cb);

		l = new Label("lugares preferidos");
		addComponent(l);

		TextArea ta = new TextArea();
		ta.setConstraint(TextField.ANY);
		ta.setRows(5);
		ta.setText(CentralDatos.perfilSitios);
		addComponent(ta);

		l = new Label("gustos");
		addComponent(l);

		ta = new TextArea();
		ta.setConstraint(TextField.ANY);
		ta.setRows(5);
		ta.setText(CentralDatos.perfilGustos);
		addComponent(ta);

		Constantes.BOTONuno.setText("Guardar");

		addComponent(Constantes.BOTONuno);
		addCommand(new Command(Constantes.ATRAS_COM));

	}

	public void invitar() {
		int cont = 0;
		for (int i = 0; i < checkList.length; i++) {
			if (checkList[i].isSelected()) {
				cont++;
			}
		}
		String numero[] = new String[cont];
		cont = 0;
		for (int i = 0; i < checkList.length; i++) {
			if (checkList[i].isSelected()) {
				numero[cont] = telContactos[i];
				cont++;
			}
		}

		SMSSender.sendMessage(numero);
	}

}
