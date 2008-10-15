package vista;

import logica.CentralDatos;
import logica.Constantes;
import logica.foto.Foto;

import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.ListCellRenderer;

import conexion.ConectorFile;

public class ListResultadosBusquedaRender extends Form implements IPaginable {

	List lista;
	Image fotoPrevia = null;
	// Image resFotos[] = null;
	Foto[] listaFotos;

	int contactWidth = getWidth() / 5;
	int contactHeight = (int) (contactWidth * 0.7);

	private static ListResultadosBusquedaRender miListResultadosBusquedaRender;
	private FotosRenderer fcr;

	private ListResultadosBusquedaRender() {
		fcr=new FotosRenderer();
	}

	public String getName() {
		return "Scrolling";
	}
	
	public void repintarLista(){
		removeComponent(lista);
		removeAllCommands();
		lista = createList(CentralDatos.resultadosBusqueda);
		addComponent(BorderLayout.CENTER, lista);
		addCommand(new Command(Constantes.ATRAS_COM));
		addCommand(new Command(Constantes.DETALLES_COM));
		CentralDatos.indiceLista=0;
		repaint();
	}

	protected void execute() {
		// disable the scroll on the Form.
		CentralDatos.indiceLista = 0;
		setTitle("Resultados de Busqueda");
		addCommand(new Command(Constantes.ATRAS_COM));
		addCommand(new Command(Constantes.DETALLES_COM));
		
		setScrollable(false);
		setLayout(new BorderLayout());
		if(CentralDatos.buscar){
			CentralDatos.factorDePantallas=1;
			if(CentralDatos.busquedaLocal){
				ConectorFile cf = ConectorFile.getConectorFile();
				
				for (int i = 0; i < CentralDatos.resultadosBusqueda.length; i++) {
					
					cf.leerImagen(CentralDatos.resultadosBusqueda[i].pic);
					
					CentralDatos.resultadosBusqueda[i].foto = CentralDatos.fotoBytebusqueda;
					System.gc();
				}
				}
		}

		lista = createList(CentralDatos.resultadosBusqueda);
		
		addComponent(BorderLayout.CENTER, lista);
		
	}

	private List createList(Foto[] fotos) {
		
		Foto[] lista;
		
		if(fotos.length>=CentralDatos.maximoEnLista){
			int tamanio=CentralDatos.maximoEnLista;

			if(CentralDatos.factorDePantallas<=1){
				addCommand(new Command(Constantes.SIGUIENTE_COM));
			}else{
				addCommand(new Command(Constantes.ANTERIOR_COM));
				
				if(fotos.length>CentralDatos.factorDePantallas*CentralDatos.maximoEnLista){
					addCommand(new Command(Constantes.SIGUIENTE_COM));	
				}else{
					tamanio=fotos.length%10;	
				}
			}
			
			
			Foto[] aux=new Foto[tamanio];
			
			for (int i = 0; i < aux.length; i++) {
				aux[i]=fotos[i+((CentralDatos.factorDePantallas-1)*10)];
			}
			lista=aux;
			
		}else{
			lista=fotos;
		}
		final int indiceMaximo=lista.length;
		List list = new List(lista) {
			public void keyPressed(int key) {
				if (key == -4) {
					key = -2;
				} else if (key == -3) {
					key = -1;
				}
				if (key == -2) {
					if (CentralDatos.indiceLista == indiceMaximo - 1) {
						CentralDatos.indiceLista = 0;
					} else {
						CentralDatos.indiceLista++;
					}

				} else if (key == -1) {
					if (CentralDatos.indiceLista == 0) {
						CentralDatos.indiceLista = indiceMaximo - 1;
					} else {
						CentralDatos.indiceLista--;
					}
				}

				super.keyPressed(key);

			}
		};
		list.setFixedSelection(List.FIXED_LEAD);
		list.getStyle().setBgTransparency(0);
		list.setListCellRenderer(fcr);
		return list;
	}

	class FotosRenderer extends Container implements ListCellRenderer {

		private Label name = new Label("");
		private Label sitioTuristico = new Label("");
		private Label ciudad = new Label("");
		private Label pic = new Label("");

		private Label focus = new Label("");

		public FotosRenderer() {
			
			name.getStyle().setBgTransparency(128);
			name.setTickerEnabled(true);
			sitioTuristico.getStyle().setBgTransparency(128);
			sitioTuristico.setTickerEnabled(true);
			ciudad.getStyle().setBgTransparency(128);
			ciudad.setTickerEnabled(true);
			pic.getStyle().setBgTransparency(128);
			pic.setTickerEnabled(true);
			
			setLayout(new BorderLayout());
			addComponent(BorderLayout.WEST, pic);
			Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
			name.getStyle().setBgTransparency(0);
			sitioTuristico.getStyle().setBgTransparency(0);
			ciudad.getStyle().setBgTransparency(0);
			cnt.addComponent(name);
			cnt.addComponent(ciudad);
			cnt.addComponent(sitioTuristico);
			addComponent(BorderLayout.CENTER, cnt);
			focus.getStyle().setBgTransparency(100);
			focus.getStyle().setFgColor(0X000000);
			focus.getStyle().setBgColor(0XFFFFFF);
		}

		public Component getListCellRendererComponent(List list, Object value,
				int index, boolean isSelected) {

			Foto foto = (Foto) value;
			name.setText(foto.name);
			sitioTuristico.setText(foto.sitioTuristico);
			ciudad.setText(foto.ciudad);
			Image i = foto.foto;
			i = i.scaled(contactWidth, contactHeight);
			pic.setIcon(i);
			System.gc();
			return this;
		}

		public Component getListFocusComponent(List list) {
			return focus;
		}

	}

	public void destruir() {
		removeAll();
		removeAllCommands();
		//CentralDatos.resultadosBusqueda=null;
		//CentralDatos.cantidadResultados=-1;
		lista = null;
		fotoPrevia = null;
		if (listaFotos != null) {

			for (int i = 0; i < listaFotos.length; i++) {
				listaFotos[i].destruir();
				listaFotos[i] = null;
			}
			listaFotos = null;
		}

		miListResultadosBusquedaRender = null;
		System.gc();
	}

	public static ListResultadosBusquedaRender getListResultadosBusquedaRender() {
		if (miListResultadosBusquedaRender == null) {
			miListResultadosBusquedaRender = new ListResultadosBusquedaRender();
		}
		return miListResultadosBusquedaRender;

	}

	public void setResultadosBusquedaSencilla() {
		// carga los resultados desde...
		execute();
	}

	public void setResultadosBusquedaCoordenada() {
		// carga los resultados desde...
		execute();
	}

	public void setResultadosMisImagenes() {
		// carga los resultados desde...
		execute();
	}

}
