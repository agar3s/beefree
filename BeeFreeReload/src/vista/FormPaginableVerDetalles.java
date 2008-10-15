package vista;

import logica.CentralDatos;
import logica.Constantes;

import com.sun.lwuit.Button;
import com.sun.lwuit.ButtonGroup;
import com.sun.lwuit.CheckBox;
import com.sun.lwuit.ComboBox;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Font;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.RadioButton;
import com.sun.lwuit.TabbedPane;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;

public class FormPaginableVerDetalles extends Form implements IPaginable {

	private static FormPaginableVerDetalles miFormPaginableVerDetalles;
	private TabbedPane pestania;
	public TextField t,nvCity;
	private Label lnvCity;
	RadioButton rb;
	ButtonGroup group;
	private int indiceSeleccion;
	ComboBox st, cb;
	public ComboBox citys;
	Font fNom=Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
	Font fTit=Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_ITALIC, Font.SIZE_MEDIUM);
	Font fcom=Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL);

	public boolean editandoSitioTuristico = false;

	private Container fotografia, detalles, comentarios;

	private FormPaginableVerDetalles() {
//		CentralDatos.nuevoSitioTuristico = "";
	}

	public void setFormAgregarSitioTuristico() {

		setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		setTitle("Nuevo Sitio Turistico");

		addComponent(new Label("nombre sitio"));

		t = new TextField(CentralDatos.nuevoSitioTuristico);

		addComponent(t);

		addComponent(new Label("Ciudad"));

		List l=new List();
		if(CentralDatos.ciudades!=null){
			
			for (int i = 0; i < CentralDatos.ciudades.length; i++) {
				l.addItem(CentralDatos.ciudades[i].ciudad);
			}
		}
		
		l.addItem("otro");
		citys=new ComboBox(l.getModel());
		
		addComponent(citys);
		
		if(CentralDatos.ciudades==null){
			
				lnvCity=new Label("nombre de ciudad");
				addComponent(lnvCity);
				nvCity=new TextField(CentralDatos.nuevoCiudad);
				addComponent(nvCity);
		}
		
		citys.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				
				if(citys.getSelectedItem().toString()=="otro"){
					removeComponent(Constantes.BOTONuno);
					lnvCity=new Label("nombre de ciudad");
					addComponent(lnvCity);
					nvCity=new TextField(CentralDatos.nuevoCiudad);
					addComponent(nvCity);
					addComponent(Constantes.BOTONuno);
				}else{
					if(lnvCity!=null){
						removeComponent(lnvCity);
					}
					if(nvCity!=null){
						CentralDatos.nuevoCiudad=nvCity.getText();
						removeComponent(nvCity);
					}
				}
				System.gc();
			}
		});
		Constantes.BOTONuno.setText("Guardar");
		addComponent(Constantes.BOTONuno);

		addCommand(new Command(Constantes.ATRAS_COM));
	}

	public void setFormAgregarDetalles() {

		setLayout(new BoxLayout(BoxLayout.Y_AXIS));

		setTitle("Informacion de la imagen");

		// Nombre de la imagen
		// o Ciudad
		// o Sitio Turístico
		// descripcion
		// tags
		// o Coordenadas geográficas.

		// 0
		addComponent(new Label("nombre de la foto"));
		// 1
		t = new TextField();
		t.setText(CentralDatos.nombreFotoPrevia);
		addComponent(t);

		List list = new List();
		if (CentralDatos.ciudades != null && CentralDatos.ciudades.length > 0) {

			for (int i = 0; i < CentralDatos.ciudades.length; i++) {
				list.addItem(CentralDatos.ciudades[i].ciudad);
			}
			cb = new ComboBox(list.getModel());
			cb.setSelectedIndex(0);
			list = new List();
			for (int i = 0; i < CentralDatos.ciudades[0].sitioTuristico.length; i++) {
				list.addItem(CentralDatos.ciudades[0].sitioTuristico[i]);
			}
			st = new ComboBox(list.getModel());

			cb.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.gc();
					indiceSeleccion = cb.getSelectedIndex();
					List l = new List();
					for (int i = 0; i < CentralDatos.ciudades[indiceSeleccion].sitioTuristico.length; i++) {
						l
								.addItem(CentralDatos.ciudades[indiceSeleccion].sitioTuristico[i]);
					}
					st.setModel(l.getModel());
				}

			});
			
		} else {
			cb = new ComboBox(list.getModel());
			st = new ComboBox(list.getModel());

			Dialog
					.show(
							"sitios turisticos",
							"no existen ciudades, o sitios turisticos cercanos en el sistema, por favor agreguelos usando agregar lugar",
							"ok", null);
		}
		
		for (int i = 0; i < cb.getModel().getSize(); i++) {
			if(cb.getModel().getItemAt(i).toString().compareTo(CentralDatos.ciudadFotoPrevia)==0){
				cb.setSelectedIndex(i);
			}
		}
		
		for (int i = 0; i < st.getModel().getSize(); i++) {
			if(st.getModel().getItemAt(i).toString().compareTo(CentralDatos.sitioTuristicoFotoPrevia)==0){
				st.setSelectedIndex(i);
			}
		}

		addComponent(new Label("ciudad"));
		addComponent(cb);
		addComponent(new Label("sitio turistico"));
		addComponent(st);

		addComponent(new Label("Descripción"));

		TextArea ta = new TextArea();

		ta.setRows(5);
		ta.setText(CentralDatos.descripcionFotoPrevia);
		addComponent(ta);

		addComponent(new Label("Tags (separados con coma)"));

		ta = new TextArea();
		ta.setRows(5);
		ta.setText(CentralDatos.tagsFotoPrevia);
		addComponent(ta);

		addComponent(new Label("longitud: " + CentralDatos.longitud));
		addComponent(new Label("latitud: " + CentralDatos.latitud));

		CheckBox chb = new CheckBox("agregar a privadas?");

		chb.setSelected(CentralDatos.fotoPrivada);
		addComponent(chb);

		chb = new CheckBox("agregar a favoritas?");

		chb.setSelected(CentralDatos.fotoFavorita);
		addComponent(chb);

		Constantes.BOTONuno.setText("Guardar");

		addComponent(Constantes.BOTONuno);

		addCommand(new Command(Constantes.ATRAS_COM));
		addCommand(new Command(Constantes.AGREGAR_LUG_COM));
		
		try{
	
			if(CentralDatos.nuevoCiudad.compareTo("")!=0){
				
				int indCity = 0;
				int indSItio = 0;
				for (int i = 0; i < CentralDatos.ciudades.length; i++) {
					if(CentralDatos.ciudades[i].ciudad.compareTo(CentralDatos.nuevoCiudad)==0){
						indCity=i;
						i=CentralDatos.ciudades.length+1;
					}
				}
				
				cb.setSelectedIndex(indCity);
				
				List l=new List();
				
				for (int i = 0; i < CentralDatos.ciudades[indCity].sitioTuristico.length; i++) {
					l.addItem(CentralDatos.ciudades[indCity].sitioTuristico[i]);
					System.out.println("i: "+ i);
					System.out.println(CentralDatos.ciudades[indCity].sitioTuristico[i]);
					System.out.println(CentralDatos.nuevoSitioTuristico);
					if(CentralDatos.ciudades[indCity].sitioTuristico[i].compareTo(CentralDatos.nuevoSitioTuristico)==0){
						indSItio=i;
					}
				}
				st.setModel(l.getModel());
				System.out.println(indSItio+"   indiecito");
				st.setSelectedIndex(indSItio);
				
			}
			
		}catch (Exception e) {
		}
		
	}

	public void setFormDetalles() {
		setTitle("detalles de la imagen");

		pestania = new TabbedPane(Component.TOP);

		fotografia = new Container(new BoxLayout(BoxLayout.Y_AXIS));

		Image foto = CentralDatos.fotoDetalles.foto;
		if (getWidth() < foto.getWidth()) {
			foto = foto.scaledWidth(getWidth());
		}
		if (getHeight() < foto.getHeight()) {
			foto = foto.scaledHeight(getHeight());
		}

		fotografia.addComponent(new Button(foto));
		pestania.addTab("Foto", fotografia);

		detalles = new Container(new BoxLayout(BoxLayout.Y_AXIS));

		// Nombre de la imagen
		// o Ciudad
		// o Sitio Turístico
		// descripcion
		// o Coordenadas geográficas.
		// publica
		// privada
		// propietario
		detalles.addComponent(new Label("nombre de la foto"));

		t = new TextField(CentralDatos.fotoDetalles.name);
		t.setEditable(false);
		detalles.addComponent(t);
		detalles.addComponent(new Label("ciudad"));
		t = new TextField(CentralDatos.fotoDetalles.ciudad);
		t.setEditable(false);
		detalles.addComponent(t);
		detalles.addComponent(new Label("sitio turistico"));

		t = new TextField(CentralDatos.fotoDetalles.sitioTuristico);
		t.setEditable(false);
		detalles.addComponent(t);
		detalles.addComponent(new Label("Descripción"));

		TextArea ta = new TextArea(CentralDatos.fotoDetalles.descipcion, 5, 20);
		ta.setEditable(false);
		detalles.addComponent(ta);
		detalles.addComponent(new Label("longitud"));

		t = new TextField(CentralDatos.fotoDetalles.longitud + "");
		t.setEditable(false);
		detalles.addComponent(t);
		detalles.addComponent(new Label("latitud"));

		t = new TextField(CentralDatos.fotoDetalles.latitud + "");
		t.setEditable(false);
		detalles.addComponent(t);

		detalles.addComponent(new Label("la foto es "));
		if (CentralDatos.fotoDetalles.privada) {
			t = new TextField("privada");

		} else {
			t = new TextField("publica");
		}

		t.setEditable(false);
		detalles.addComponent(t);

		if (CentralDatos.fotoDetalles.isFavorita) {
			detalles.addComponent(new Label("es tu favorita"));
		} else {
			detalles.addComponent(new Label("no es tu favorita"));
		}

		detalles.addComponent(new Label("el propietario de esta foto es"));
		
		if(CentralDatos.fotoDetalles.duenio!=null)
		t = new TextField(CentralDatos.fotoDetalles.duenio);
		else
			t = new TextField("otro");
		t.setEditable(false);
		detalles.addComponent(t);

		pestania.addTab("Detalles", detalles);

		if(CentralDatos.busquedaLocal){
			
			comentarios = new Container(new BoxLayout(BoxLayout.Y_AXIS));
			Constantes.BOTONuno.setText("Ver comentarios");

			comentarios.addComponent(Constantes.BOTONuno);
			pestania.addTab("Comentarios", comentarios);
		}
		
		addComponent(pestania);

		addCommand(new Command(Constantes.ATRAS_COM));
		switch (Constantes.VIS_CURRENT) {
		case Constantes.RESULTADOS_BUSQUEDA_VIS:
			// debe vaildarse si se hizo busqueda local o no, para poner los
			// comandos necesarios
			if (CentralDatos.busquedaLocal) {
				addCommand(new Command(Constantes.ELIMINAR_COM));
			} else {

				addCommand(new Command(Constantes.GUARDAR_COM));
			}
			break;

		case Constantes.RESULTADOS_BUSQUEDA_COORD_VIS:
			addCommand(new Command(Constantes.GUARDAR_COM));
			break;

		case Constantes.BUSCAR_COORDENADA_VIS:
			addCommand(new Command(Constantes.GUARDAR_COM));
			break;

		case Constantes.RESULTADOS_MIS_IMAGENES_VIS:
			addCommand(new Command(Constantes.EDITAR_COM));
			addCommand(new Command(Constantes.ELIMINAR_COM));
			break;

		case Constantes.EDITAR_VIS:
			addCommand(new Command(Constantes.EDITAR_COM));
			addCommand(new Command(Constantes.ELIMINAR_COM));
			break;
		}

	}
	
	public void ponerComentarios(){
		comentarios.removeAll();
		if(CentralDatos.comentarios!=null){
			
			
			Label nom;
			Label tit;
			Label com;
			
			
			for (int i = 0; i < CentralDatos.comentarios.length; i++) {
				
				nom=new Label();
				nom.getStyle().setFont(fNom);
				nom.setTickerEnabled(true);
				nom.setText(CentralDatos.comentarios[i].nombreUsuario);
				comentarios.addComponent(nom);
				
				tit=new Label();
				tit.getStyle().setFont(fTit);
				tit.setTickerEnabled(true);
				tit.setText(CentralDatos.comentarios[i].titulo);
				comentarios.addComponent(tit);
				
				com=new Label();
				com.getStyle().setFont(fcom);
				com.setTickerEnabled(true);
				com.setText(CentralDatos.comentarios[i].comentario);
				comentarios.addComponent(com);
				
			}
			
		}else{
			comentarios.addComponent(new Label("no hay comentarios"));
		}
		
		comentarios.addComponent(new Label("agrega uno nuevo"));
		
		TextArea tar = new TextArea(5, 20);
		tar.setEditable(true);
		
		comentarios.addComponent(tar);
		Constantes.BOTONdos.setText("Enviar");
		comentarios.addComponent(Constantes.BOTONdos);
		
	}

	public static FormPaginableVerDetalles getFormPaginableVerDetalles() {
		if (miFormPaginableVerDetalles == null) {
			miFormPaginableVerDetalles = new FormPaginableVerDetalles();

		}
		return miFormPaginableVerDetalles;

	}

	public void destruir() {

		removeAll();
		removeAllCommands();

		if (pestania != null)
			pestania.removeAll();
		if (fotografia != null)
			fotografia.removeAll();
		if (detalles != null)
			detalles.removeAll();
		if (comentarios != null)
			comentarios.removeAll();

		fotografia = null;
		detalles = null;
		comentarios = null;
		t = null;
		group = null;
		rb = null;

		miFormPaginableVerDetalles = null;
		System.gc();
	}

}
