package vista;

import java.util.Calendar;
import java.util.TimeZone;

import com.sun.lwuit.ComboBox;
import com.sun.lwuit.Container;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.TextField;

import logica.AdministradorFotos;
import logica.CentralDatos;
import logica.Ciudad;
import logica.Constantes;
import logica.StringTokenizer;
import logica.foto.Foto;
import conexion.ICliente;

public class ManejadorConexiones implements ICliente {

	private static ManejadorConexiones miManejadorConexiones;

	private ManejadorConexiones() {
	}

	public static ManejadorConexiones getManejadorConexiones() {
		if (miManejadorConexiones == null) {
			miManejadorConexiones = new ManejadorConexiones();
		}
		return miManejadorConexiones;
	}

	private void procesarLugares(String respuesta) {

		StringTokenizer st, stsitios;
		Ciudad c;
		String nombre;
		String sitios[];
		if (respuesta.compareTo("") != 0) {

			respuesta = respuesta.substring(0, respuesta.length() - 2);
			StringTokenizer ciudades = new StringTokenizer(respuesta, "+/");
			CentralDatos.ciudades = new Ciudad[ciudades.tokens];
			for (int i = 0; i < ciudades.tokens; i++) {
				st = new StringTokenizer(ciudades.nextElement(), ":");

				nombre = st.nextElement();

				stsitios = new StringTokenizer(st.nextElement(), "+");

				sitios = new String[stsitios.tokens];

				for (int j = 0; j < sitios.length; j++) {
					sitios[j] = stsitios.nextElement();
				}

				c = new Ciudad(nombre, sitios);

				CentralDatos.ciudades[i] = c;

				System.gc();
			}
		} else {
			CentralDatos.ciudades = null;
		}

	}

	public void actualizar() {

		System.out.println("RESPUESTA: " + CentralDatos.respuesta);

		byte current = Constantes.VIS_CURRENT;
		// para lugares el current es SELECCIONAR_COORD_MANUAL_VIS o
		// FOTO_PREVIA_VIS

		switch (current) {
		case Constantes.LOGIN_VIS:
			if (CentralDatos.cookie != null) {
				
				if(CentralDatos.primeraVez){
					
					Paginador.getPaginador().adelante=true;
					Paginador.getPaginador().setCurrent(
							Constantes.PRIMERA_VEZ_CONTRA_VIS);
					
					
				}else{
					
					Paginador.getPaginador().adelante = true;
					Paginador.getPaginador().setCurrent(
							Constantes.MENU_PRINCIPAL_VIS);
				}
				

			} else {
				try {
					int id = Integer.parseInt(CentralDatos.respuesta);
					if (id != 0) {
						CentralDatos.UID = id;
						DialogCargando
								.getDialogCargando()
								.iniciarCarga(
										this,
										DialogCargando.CONEXION_HTTP_GET,
										"http://www.maratonmovil2.com/drupalBF/login2.php",
										"id=" + CentralDatos.UID);

					} else {
						Dialog
								.show(
										"Login incorrecto",
										"por favor verifique su nombre de usuario y/o contraseña",
										"", "aceptar");
					}
				} catch (Exception e) {

				}
				// Paginador.getPaginador().setCurrent(Constantes.);
				System.out.println("Login incorrecto");
			}
			break;

		case Constantes.CREAR_CUENTA_VIS:

			if (CentralDatos.respuesta.compareTo("Found") == 0
					|| CentralDatos.respuesta.compareTo("found") == 0) {
				Dialog
						.show(
								"registro",
								"Cuenta de I.Guana Bee Free creada exitosamente, su contraseña fue enviada a su correo electronico",
								"ok", null);

				Paginador.getPaginador().adelante = false;
				Paginador.getPaginador().setCurrent(Constantes.LOGIN_VIS);

			} else {
				Dialog
						.show(
								"registro",
								"lo sentimos los datos ingresados ya se encuentran en nuestro servidor, o estan mal escritos, por favor verifiquelos",
								"ok", null);
			}
			break;

		case Constantes.MENU_PRINCIPAL_VIS:
			if (CentralDatos.respuesta != null) {
				StringTokenizer token = new StringTokenizer(
						CentralDatos.respuesta, "\n");

				int i = 0;
				while (token.hasNext()) {
					System.out.println(token.nextElement());
					i++;
				}
				CentralDatos.noticias = new String[i];
				token = new StringTokenizer(CentralDatos.respuesta, "\n");

				i = 0;
				while (token.hasNext()) {
					CentralDatos.noticias[i] = token.nextElement();
					i++;
				}
				Paginador.getPaginador().setCurrent(Constantes.NOTICIAS_VIS);

			}
			break;
		case Constantes.PRIMERA_VEZ_CONTRA_VIS:
			
			if (CentralDatos.respuesta != null) {

				int r = Integer.parseInt(CentralDatos.respuesta.trim());
				if (r == 1) {
					Dialog.show("cambio password",
							"su contrasenia fue cambiada exitosamente", "ok",
							null);

					Paginador.getPaginador()
							.setCurrent(Constantes.MENU_PRINCIPAL_VIS);

				} else {
					Dialog
							.show("cambio password",
									"no fue posible cambiar su contrasenia",
									"ok", null);
				}
			}
			
			break;
		case Constantes.CAMBIAR_CONTRASENIA_VIS:
			if (CentralDatos.respuesta != null) {

				int r = Integer.parseInt(CentralDatos.respuesta.trim());
				if (r == 1) {
					Dialog.show("cambio password",
							"su contrasenia fue cambiada exitosamente", "ok",
							null);

					Paginador.getPaginador()
							.setCurrent(Constantes.OPCIONES_VIS);

				} else {
					Dialog
							.show("cambio password",
									"no fue posible cambiar su contrasenia",
									"ok", null);
				}
			}
			break;
			
		case Constantes.MI_PERFIL_VIS:
			if (CentralDatos.respuesta != null
					&& CentralDatos.respuesta.trim().compareTo("1") == 0) {

				Dialog
						.show(
								"actualizado",
								"sus datos de perfil fueron actualizados correctamente",
								"ok", null);

				Paginador.getPaginador().adelante = false;
				Paginador.getPaginador().setCurrent(Constantes.OPCIONES_VIS);
			}
			break;

		case Constantes.FOTO_PREVIA_VIS:

			Calendar c = Calendar.getInstance(TimeZone.getDefault());
			CentralDatos.fechaActualFotoPrevia = c.getTime().getTime();

			if (CentralDatos.haveGPS) {

				if (!CentralDatos.trajoLugares) {

					CentralDatos.trajoLugares = true;
					ManejadorConexiones.getManejadorConexiones().traerLugares();

				} else {

					procesarLugares(CentralDatos.respuesta);

					Paginador.getPaginador().adelante = true;
					Paginador.getPaginador().setCurrent(
							Constantes.ADD_DETALLE_FOTO_VIS);
				}

			} else {
				Paginador.getPaginador().setCurrent(
						Constantes.SELECCIONAR_COORD_MANUAL_VIS);
			}

			break;

		case Constantes.SELECCIONAR_COORD_MANUAL_VIS:

			procesarLugares(CentralDatos.respuesta);

			Paginador.getPaginador().adelante = true;
			Paginador.getPaginador()
					.setCurrent(Constantes.ADD_DETALLE_FOTO_VIS);

			break;
			
		case Constantes.ADD_DETALLE_FOTO_VIS:
			Dialog.show("envio realizado", CentralDatos.respuesta, "ok", null);
			Paginador.getPaginador().setCurrent(Constantes.BEEFREE_VIS);
			int nid = 0 ;
			try{
				
				nid =Integer.parseInt(CentralDatos.respuesta.substring(3, CentralDatos.respuesta.length()));
				AdministradorFotos.getAdministradorFotos().guardarFoto(nid);
				
				
			}catch (Exception e) {
				Dialog.show("error con el nid",nid+"", "ok", null);
			}
			
			break;

		case Constantes.OPCIONES_VIS:


			StringTokenizer st = new StringTokenizer(CentralDatos.respuesta,
					";;");
			
			CentralDatos.perfilNombre = st.nextElement();
			CentralDatos.perfilApellido = st.nextElement();
			CentralDatos.perfilTelefono = st.nextElement();
			CentralDatos.perfilCiudad = st.nextElement();
			CentralDatos.perfilPais = st.nextElement();
			CentralDatos.perfilProfesion = st.nextElement();
			CentralDatos.perfilSexo = st.nextElement();
			CentralDatos.perfilSitios = st.nextElement();
			CentralDatos.perfilGustos = st.nextElement();

			Paginador.getPaginador().adelante = true;
			Paginador.getPaginador().setCurrent(Constantes.MI_PERFIL_VIS);
			break;
			
		case Constantes.BUSCAR_IMAGEN_VIS:
			System.out.println("CentralDatos.respuesta");
			
			StringTokenizer tok= new StringTokenizer(CentralDatos.respuesta,"><");
			
			CentralDatos.cantidadResultados= tok.tokens;
			CentralDatos.resultadosBusqueda= new Foto[CentralDatos.cantidadResultados];
			
			int i=0;
			Foto foto;
			StringTokenizer datos;
			 nid=0;
			String nombre="";
			String url="";
			String ciudad="";
			String sitio="";
			
			while(tok.hasNext()){
				datos= new StringTokenizer(tok.nextElement(),"+@");
				
				nid= Integer.parseInt(datos.nextElement());
				nombre= datos.nextElement();
				url= datos.nextElement();
				ciudad= datos.nextElement();
				sitio= datos.nextElement();
				
				foto=new Foto(nombre,sitio, ciudad,null,null, null,0,0,0,false, false, 0, null, nid);
				foto.URL= url;
				CentralDatos.resultadosBusqueda[i]= foto;
				
			}
			
			
			break;
		
		}

		Paginador.getPaginador().current.show();
	}

	public void realizarPeticion() {

	}

	public void login(String login, String pass) {

		DialogCargando.getDialogCargando().iniciarCarga(this,
				DialogCargando.CONEXION_HTTP_POST,
				Constantes.HTTP + Constantes.HTTP_LOGIN,
				"name=" + login + "&pass=" + pass);

	}

	public void cargarNoticias(String param) {
		DialogCargando.getDialogCargando().iniciarCarga(this,
				DialogCargando.CONEXION_HTTP_POST,
				Constantes.HTTP + Constantes.HTTP_NOTICE, param);

	}

	public void registrar(String param) {
		DialogCargando.getDialogCargando().iniciarCarga(this,
				DialogCargando.CONEXION_HTTP_POST,
				Constantes.HTTP + Constantes.HTTP_REGISTRO, param);

	}

	public void updatePerfil(Container contenedorForm) {

		CentralDatos.perfilNombre = ((TextField) contenedorForm
				.getComponentAt(1)).getText();
		if (CentralDatos.perfilNombre.compareTo("") == 0) {
			CentralDatos.perfilNombre = " ";
		}
		CentralDatos.perfilApellido = ((TextField) contenedorForm
				.getComponentAt(3)).getText();
		if (CentralDatos.perfilApellido.compareTo("") == 0) {
			CentralDatos.perfilApellido = " ";
		}
		CentralDatos.perfilTelefono = ((TextField) contenedorForm
				.getComponentAt(5)).getText();
		if (CentralDatos.perfilTelefono.compareTo("") == 0) {
			CentralDatos.perfilTelefono = " ";
		}
		CentralDatos.perfilCiudad = ((TextField) contenedorForm
				.getComponentAt(7)).getText();
		if (CentralDatos.perfilCiudad.compareTo("") == 0) {
			CentralDatos.perfilCiudad = " ";
		}
		CentralDatos.perfilPais = ((TextField) contenedorForm.getComponentAt(9))
				.getText();
		if (CentralDatos.perfilPais.compareTo("") == 0) {
			CentralDatos.perfilPais = " ";
		}
		CentralDatos.perfilProfesion = ((TextField) contenedorForm
				.getComponentAt(11)).getText();
		if (CentralDatos.perfilProfesion.compareTo("") == 0) {
			CentralDatos.perfilProfesion = " ";
		}
		CentralDatos.perfilSexo = ((ComboBox) contenedorForm.getComponentAt(13))
				.getSelectedItem().toString();
		CentralDatos.perfilSitios = ((TextArea) contenedorForm
				.getComponentAt(15)).getText();
		if (CentralDatos.perfilSitios.compareTo("") == 0) {
			CentralDatos.perfilSitios = " ";
		}
		CentralDatos.perfilGustos = ((TextArea) contenedorForm
				.getComponentAt(17)).getText();
		if (CentralDatos.perfilGustos.compareTo("") == 0) {
			CentralDatos.perfilGustos = " ";
		}
		StringBuffer sb = new StringBuffer();

		sb.append("nombre=").append(CentralDatos.perfilNombre).append(
				"&apellido=").append(CentralDatos.perfilApellido).append(
				"&tel=").append(CentralDatos.perfilTelefono).append("&ciudad=")
				.append(CentralDatos.perfilCiudad).append("&pais=").append(
						CentralDatos.perfilPais).append("&profesion=").append(
						CentralDatos.perfilProfesion).append("&sexo=").append(
						CentralDatos.perfilSexo).append("&lugares=").append(
						CentralDatos.perfilSitios).append("&gustos=").append(
						CentralDatos.perfilGustos).append("&uid=").append(
						CentralDatos.UID);

		 String param = sb.toString();
		 DialogCargando.getDialogCargando().iniciarCarga(this,
		 DialogCargando.CONEXION_HTTP_POST,Constantes.HTTP+Constantes.HTTP_OUT_PERFIL , param);

	}

	public void cambiarContrasenia(Container contenedorForm) {

		String nuevContrasenia = ((TextField) contenedorForm.getComponentAt(1))
				.getText();
		String confContrasenia = ((TextField) contenedorForm.getComponentAt(3))
				.getText();

		if (nuevContrasenia.compareTo(CentralDatos.loginActual) != 0) {
				if (nuevContrasenia.compareTo(confContrasenia) == 0) {

						String param = "pass=" + nuevContrasenia;

						// respuesta en actualizar
						DialogCargando.getDialogCargando().iniciarCarga(this,
								DialogCargando.CONEXION_HTTP_POST,
								Constantes.HTTP + Constantes.HTTP_CONTRASENIA,
								param);

					
				} else {
					// las nuevas contrasenias no coinciden
					Dialog.show("error",
							"las nuevas contrasenias no coinciden entre ellas",
							"ok", null);
				}

		} else {
			// login no debe ser contrasenia
			Dialog.show("error",
					"su nombre de usuario no puede ser su contrasenia", "ok",
					null);
		}

	}

	public void enviarFoto() {
		DialogCargando.getDialogCargando().iniciarCarga(this,
				DialogCargando.CONEXION_UPLOAD_FOTO,
				Constantes.HTTP + Constantes.UPLOADER, null);		
	}

	public void obtenerGPS() {
		DialogCargando.getDialogCargando().iniciarCarga(this,
				DialogCargando.CONEXION_GPS, null, null);

	}

	public void traerLugares() {

		String param = "lat=" + CentralDatos.latitud + "&lon="
				+ CentralDatos.longitud;

		DialogCargando.getDialogCargando().iniciarCarga(this,
				DialogCargando.CONEXION_HTTP_POST,
				Constantes.HTTP + Constantes.HTTP_LOCATION_SEARCH, param);
	}

	public void traerInfoPerfil() {

		String param = "uid=" + CentralDatos.UID;

		DialogCargando.getDialogCargando().iniciarCarga(this,
				DialogCargando.CONEXION_HTTP_POST,
				Constantes.HTTP + Constantes.HTTP_IN_PERFIL, param);
	}

	public void descargarFoto() {
//		TODO ojo con foto detalles.!!
		DialogCargando.getDialogCargando().iniciarCarga(this,DialogCargando.CONEXION_DOWNLOAD, CentralDatos.fotoDetalles.URL,null);
		
		//DialogCargando.getDialogCargando().iniciarCarga(this, DialogCargando.CONEXION_HTTP_POST, "http://www.informit.com/display/InformIT/images/header/informit.png", "");		
	}

	public void buscarWeb(String busq, int crit) {
		DialogCargando.getDialogCargando().iniciarCarga(this, DialogCargando.CONEXION_HTTP_POST,Constantes.HTTP+Constantes.HTTP_BUSCAR ,"busq="+busq+"&crit="+crit );
	}
}
