package logica;

import com.sun.lwuit.Button;

public class Constantes {

	public static final String ACEPTAR_COM = "Aceptar";
	public static final String SALIR_COM = "Salir";
	public static final String ATRAS_COM = "Atras";
	public static final String SIGUIENTE_COM = "Siguiente";
	public static final String GUARDAR_COM = "Guardar";
	public static final String ANTERIOR_COM = "Anterior";
	public static final String CANCELAR_COM = "Cancelar";
	public static final String DETALLES_COM = "Ver Detalles";
	public static final String REPETIR_COM = "Repetir";
	public static final String AGREGAR_LUG_COM = "Agregar lugar";
	public static final String INS_TEX_COM = "Insertar texto";
	public static final String ELIMINAR_COM = "Eliminar";
	public static final String EDITAR_COM = "Editar";

	public static Button BOTONuno = new Button();
	public static Button BOTONdos = new Button();
	public static Button BOTONtres = new Button();
	public static Button BOTONcuatro = new Button();
	public static Button BOTONcinco = new Button();
	public static Button BOTONseis = new Button();

	public static byte VIS_CURRENT = 0;

	public static final byte PRESENTACION_VIS = 0;
	public static final byte LOGIN_VIS = 1;
	public static final byte CREAR_CUENTA_VIS = 2;
	public static final byte INFO_NUEVAS_NOTICIAS_DIA = 3;
	public static final byte MENU_PRINCIPAL_VIS = 4;
	public static final byte AYUDA_VIS = 5;
	public static final byte BEEFREE_VIS = 6;
	public static final byte OPCIONES_VIS = 7;
	public static final byte EXPLORAR_VIS = 8;
	public static final byte NOTICIAS_VIS = 9;

	public static final byte AYUDA_TOMAR_VIS = 10;
	public static final byte AYUDA_BUSCAR_VIS = 11;
	public static final byte AYUDA_EDITAR_VIS = 12;
	public static final byte AYUDA_SUBIR_VIS = 13;

	public static final byte CAMBIAR_CONTRASENIA_VIS = 14;
	public static final byte ACTUALIZAR_VIS = 15;
	public static final byte INVITAR_AMIGOS_VIS = 16;
	public static final byte CAMBIAR_CONTRASEÑA_FIRST_VIS = 17;
	public static final byte CONF_INVITACION_DIA = 18;
	public static final byte INFO_INVITACION_ENVIADA_DIA = 19;

	public static final byte NOTICIAS_DETALLE_VIS = 20;

	public static final byte BUSCAR_IMAGEN_VIS = 21;
	public static final byte BUSCAR_MAPAM_VIS = 22;
	public static final byte MIS_FOTOS_VIS = 23;
	public static final byte BUSCAR_COORDENADA_VIS = 24;

	public static final byte RESULTADOS_BUSQUEDA_VIS = 25;
	public static final byte DETALLES_BI_VIS = 26;
	public static final byte CONFIRMACION_GUARDAR_DIA = 27;

	public static final byte DETALLES_GM_VIS = 28;
	public static final byte DETALLES_MIS_IMAGENES_VIS = 29;
	public static final byte RESULTADOS_MIS_IMAGENES_VIS = 30;
	public static final byte CONF_ELIMINAR_FOTO_DIA = 31;
	public static final byte CONF_REEMPLAZAR_FOTO_DIA = 32;
	public static final byte DETALLES_COORD_VIS = 33;
	public static final byte SELECCIONAR_COORD_MANUAL_VIS = 34;

	public static final byte FOTO_PREVIA_VIS = 35;
	public static final byte ADD_DETALLE_FOTO_VIS = 36;
	public static final byte TRANSMISION_WEB_DIA = 37;
	public static final byte NUEVO_LUGAR_VIS = 38;
	static public final byte NUEVO_LUGAR_DIA = 39;
	public static final byte SELEC_COORD_MANUAL_VIS = 40;
	public static final byte LOGIN_INCORRECTO_DIA = 41;
	public static final byte CAMBIO_CONTRASENIA_VALIDO_DIA = 42;
	public static final byte CAMBIO_CONTRASENIA_INVALIDO_DIA = 43;
	public static final byte EDITAR_VIS = 44;

	public static final byte BUSCAR_COORD_MANUAL_VIS = 45;
	public static final byte MOSTRAR_UBICACION_FOTO_MAPA = 46;
	public static final byte RESULTADOS_BUSQUEDA_COORD_VIS = 47;
	public static final byte MI_PERFIL_VIS = 48;
	public static final byte PRIMERA_VEZ_CONTRA_VIS = 49;

	// lista de ruta de conexiones:

	public static final String HTTP = "http://www.maratonmovil2.com/drupalBF/";

	public static final String HTTP_LOGIN = "login.php";
	public static final String HTTP_NOTICE = "noticias.php";
	public static final String HTTP_REGISTRO = "user/register";
	public static final String HTTP_LOCATION_SEARCH = "locationsearch.php";
	public static final String HTTP_IN_PERFIL = "getprofile.php";
	public static final String HTTP_OUT_PERFIL = "profile.php";
	public static final String UPLOADER = "uploader2.php";
	public static final String HTTP_BUSCAR = "busqueda.php";
	public static final String HTTP_CONTRASENIA = "password.php";
	public static final String HTTP_TRAER_COMENTARIO = "traercomentario.php";
	public static final String HTTP_ENVIAR_COMMENT = "agregarcomentario.php";
	public static final String HTTP_COMPLETA = "completa.php";
	public static final String HTTP_LOCATION_SEARCH2 = "locationsearch2.php";

}
