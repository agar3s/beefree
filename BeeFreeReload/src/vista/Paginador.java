package vista;

import java.io.IOException;

import logica.CentralDatos;
import logica.Constantes;
import logica.ManejadorEventos;

import com.sun.lwuit.Component;
import com.sun.lwuit.Font;
import com.sun.lwuit.Form;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.plaf.UIManager;
import com.sun.lwuit.util.Resources;

public class Paginador {

	public Form current;
	public boolean adelante = false;

	private static Paginador miPaginador;

	public ManejadorEventos eventico;

	public static Paginador getPaginador() {

		if (miPaginador == null) {
			miPaginador = new Paginador();
		}
		return miPaginador;

	}

	public void destruir() {
		miPaginador = null;
	}

	private Paginador() {

		try {
			Resources r = Resources.open("/businessTheme.res");
			UIManager.getInstance().setThemeProps(r.getTheme("businessTheme"));
		} catch (IOException ioe) {
			System.out.println("Couldn't load theme.");
		}

		eventico = new ManejadorEventos();

		setCurrent(Constantes.LOGIN_VIS);

	}

	public void setCurrent(byte next) {
		resetButtons();
		System.gc();
		System.out.println("index: "+next);
		if (current != null) {
			((IPaginable) current).destruir();
		}
		switch (next) {
		case Constantes.PRESENTACION_VIS:

			current = FormPresentacion.getContainerPresentacion();

			break;

		case Constantes.LOGIN_VIS:
			current = FormPaginableInicio.getFormPaginableInicio();
			((FormPaginableInicio) current).setLogin();

			break;

		case Constantes.MENU_PRINCIPAL_VIS:
			current = FormPaginableInicio.getFormPaginableInicio();
			((FormPaginableInicio) current).setMenuPrincipal();

			break;

		case Constantes.CREAR_CUENTA_VIS:
			current = FormPaginableInicio.getFormPaginableInicio();
			((FormPaginableInicio) current).setCrearCuenta();

			break;

		case Constantes.BEEFREE_VIS:
			current = FormPaginableInicio.getFormPaginableInicio();
			((FormPaginableInicio) current).setMediaComponent();

			break;

		case Constantes.FOTO_PREVIA_VIS:
			current = FormPaginableInicio.getFormPaginableInicio();
			((FormPaginableInicio) current).setVistaPrevia();

			break;

		case Constantes.EXPLORAR_VIS:
			current = FormPaginableExplorar.getFormPaginableExplorar();
			((FormPaginableExplorar) current).setMenuExplorar();

			break;

		case Constantes.BUSCAR_IMAGEN_VIS:
			current = FormPaginableExplorar.getFormPaginableExplorar();
			((FormPaginableExplorar) current).setFormBuscar();
			CentralDatos.resultadosBusqueda = null;

			break;

		case Constantes.RESULTADOS_BUSQUEDA_VIS:
			current = ListResultadosBusquedaRender
					.getListResultadosBusquedaRender();
			((ListResultadosBusquedaRender) current)
					.setResultadosBusquedaSencilla();

			break;

		case Constantes.RESULTADOS_MIS_IMAGENES_VIS:
			current = ListResultadosBusquedaRender
					.getListResultadosBusquedaRender();
			((ListResultadosBusquedaRender) current).setResultadosMisImagenes();

			break;

		case Constantes.AYUDA_VIS:
			current = FormPaginableAyuda.getFormAyuda();
			((FormPaginableAyuda) current).setMenuAyuda();

			break;

		case Constantes.OPCIONES_VIS:
			current = FormPaginableOpciones.getFormPaginableOpciones();
			((FormPaginableOpciones) current).setMenuOpciones();

			break;
		case Constantes.CAMBIAR_CONTRASENIA_VIS:
			current = FormPaginableOpciones.getFormPaginableOpciones();
			((FormPaginableOpciones) current).setFormCambiarContrasenia();

			break;
		case Constantes.PRIMERA_VEZ_CONTRA_VIS:
			current = FormPaginableOpciones.getFormPaginableOpciones();
			((FormPaginableOpciones) current).setFormCambiarContrasenia();

			break;			

		case Constantes.DETALLES_BI_VIS:
			current = FormPaginableVerDetalles.getFormPaginableVerDetalles();
			((FormPaginableVerDetalles) current).setFormDetalles();
			break;

		case Constantes.DETALLES_COORD_VIS:
			current = FormPaginableVerDetalles.getFormPaginableVerDetalles();
			((FormPaginableVerDetalles) current).setFormDetalles();
			break;

		case Constantes.DETALLES_MIS_IMAGENES_VIS:
			current = FormPaginableVerDetalles.getFormPaginableVerDetalles();
			((FormPaginableVerDetalles) current).setFormDetalles();
			break;

		case Constantes.DETALLES_GM_VIS:
			current = FormPaginableVerDetalles.getFormPaginableVerDetalles();
			((FormPaginableVerDetalles) current).setFormDetalles();
			break;

		case Constantes.ADD_DETALLE_FOTO_VIS:
			
			
			
			current = FormPaginableVerDetalles.getFormPaginableVerDetalles();
			((FormPaginableVerDetalles) current).setFormAgregarDetalles();
			break;

		case Constantes.NUEVO_LUGAR_VIS:
			current = FormPaginableVerDetalles.getFormPaginableVerDetalles();
			((FormPaginableVerDetalles) current).setFormAgregarSitioTuristico();
			break;

		case Constantes.EDITAR_VIS:
			current = FormPaginableEdicion.getFormPaginableEdicion();
			break;

		case Constantes.BUSCAR_COORDENADA_VIS:
			current = FormNavegador.getFormNavegador();
			((FormNavegador) current).setFormNavegar();

			break;
		case Constantes.SELECCIONAR_COORD_MANUAL_VIS:
			current = FormNavegador.getFormNavegador();
			((FormNavegador) current).setFormNavegarManual();
			break;

		case Constantes.MOSTRAR_UBICACION_FOTO_MAPA:
			current = FormNavegador.getFormNavegador();
			((FormNavegador) current).setMostrarUbicacionFoto();
			break;

		case Constantes.BUSCAR_COORD_MANUAL_VIS:
			current = FormNavegador.getFormNavegador();
			((FormNavegador) current).setBuscarCoordenadas();
			break;

		case Constantes.NOTICIAS_VIS:
			current = FormPaginableInicio.getFormPaginableInicio();
			((FormPaginableInicio) current).setNoticias();
			break;

		case Constantes.INVITAR_AMIGOS_VIS:
			current = FormPaginableOpciones.getFormPaginableOpciones();
			((FormPaginableOpciones) current).setListaContactos();
			break;

		case Constantes.RESULTADOS_BUSQUEDA_COORD_VIS:
			current = ListResultadosBusquedaRender
					.getListResultadosBusquedaRender();
			((ListResultadosBusquedaRender) current)
					.setResultadosBusquedaCoordenada();
			break;
		case Constantes.MI_PERFIL_VIS:
			current = FormPaginableOpciones.getFormPaginableOpciones();
			((FormPaginableOpciones) current).setDetallesPerfilDeUsuario();
			break;
		}
		
		current.setTransitionInAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, !adelante, 400));
		
//		current.setTransitionInAnimator(Transition3D.createRotation(500,
//				!adelante));

		Constantes.VIS_CURRENT = next;
		current.setCommandListener(eventico);
		current.show();

	}

	public void resetButtons() {
		Constantes.BOTONuno.setIcon(null);
		Constantes.BOTONdos.setIcon(null);
		Constantes.BOTONtres.setIcon(null);
		Constantes.BOTONcuatro.setIcon(null);
		Constantes.BOTONcinco.setIcon(null);
		Constantes.BOTONseis.setIcon(null);

		Constantes.BOTONuno.setAlignment(Component.CENTER);
		Constantes.BOTONdos.setAlignment(Component.CENTER);
		Constantes.BOTONtres.setAlignment(Component.CENTER);
		Constantes.BOTONcuatro.setAlignment(Component.CENTER);
		Constantes.BOTONcinco.setAlignment(Component.CENTER);
		Constantes.BOTONseis.setAlignment(Component.CENTER);

		Constantes.BOTONuno.setTextPosition(Component.BOTTOM);
		Constantes.BOTONdos.setTextPosition(Component.BOTTOM);
		Constantes.BOTONtres.setTextPosition(Component.BOTTOM);
		Constantes.BOTONcuatro.setTextPosition(Component.BOTTOM);
		Constantes.BOTONcinco.setTextPosition(Component.BOTTOM);
		Constantes.BOTONseis.setTextPosition(Component.BOTTOM);

		Font f = Font.createSystemFont(Font.FACE_PROPORTIONAL,
				Font.STYLE_PLAIN, Font.SIZE_MEDIUM);

		Constantes.BOTONuno.getStyle().setFont(f);
		Constantes.BOTONuno.getStyle().setBgTransparency(128);

		Constantes.BOTONdos.getStyle().setFont(f);
		Constantes.BOTONdos.getStyle().setBgTransparency(128);

		Constantes.BOTONtres.getStyle().setFont(f);
		Constantes.BOTONtres.getStyle().setBgTransparency(128);

		Constantes.BOTONcuatro.getStyle().setFont(f);
		Constantes.BOTONcuatro.getStyle().setBgTransparency(128);

		Constantes.BOTONcinco.getStyle().setFont(f);
		Constantes.BOTONcinco.getStyle().setBgTransparency(128);

		Constantes.BOTONseis.getStyle().setFont(f);
		Constantes.BOTONseis.getStyle().setBgTransparency(128);

	}

}
