package logica;

import com.sun.lwuit.Image;

import logica.foto.Foto;

public class CentralDatos {

	public static String loginActual;
	public static String contraActual;
	public static byte[] fotoPreviaByte;
	public static double latitud;
	public static double longitud;
	public static String nombreFotoPrevia = "";
	public static String ciudadFotoPrevia = "";
	public static String sitioTuristicoFotoPrevia = "";
	public static String descripcionFotoPrevia = "";
	public static long fechaActualFotoPrevia = 0;
	public static String[] tagsArrayFotoPrevia = null;
	public static String tagsFotoPrevia = "";
	public static boolean fotoPrivada;
	public static boolean fotoFavorita;
	public static int idFotoNoEnviada=-1;

	public static final String enconding = ".jpeg";

	public static String criterioBusqueda = "";
	
	public static Foto[] resultadosBusqueda;
	public static Image fotoBytebusqueda;
	public static int cantidadResultados;
	
	public static int indiceLista;
	
	public static Foto fotoDetalles;

	public static boolean haveLocation;
	public static boolean haveGPS;

	public static String[] noticias;

	public static Image imagenMapa;

	public static byte zoom;

	public static int viewPortX, viewPortY;

	public static boolean busquedaLocal;

	public static String perfilNombre = "";
	public static String perfilApellido = "";
	public static String perfilTelefono = "";
	public static String perfilCiudad = "";
	public static String perfilPais = "";
	public static String perfilProfesion = "";
	public static String perfilSexo = "";
	public static String perfilSitios = "";
	public static String perfilGustos = "";

	public static boolean GPSLISTO = false;

	public static String nuevoSitioTuristico = "";
	public static String nuevoCiudad = "";
	
	
	public static int anchoImagen;
	public static int altoImagen;
	public static double consultaLatitud;
	public static double consultaLongitud;
	public static String respuesta;
	public static String cookie;
	public static int UID;
	
	public static boolean trajoLugares;
	
	public static Ciudad[] ciudades;

	public static boolean primeraVez=false;
	public static int maximoEnLista=10;
//	este numero es usado para multiplicar el indice y saber en q resultado se esta
//	en caso de q existan mas de 10 resultados de busqueda
	public static int factorDePantallas=1;
	public static boolean buscar;
}
