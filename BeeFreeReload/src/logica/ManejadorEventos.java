package logica;

import java.util.Calendar;
import java.util.TimeZone;

import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.VideoControl;

import vista.DialogPaginable;
import vista.FormPaginableAyuda;
import vista.FormPaginableInicio;
import vista.FormPaginableOpciones;
import vista.FormPaginableVerDetalles;
import vista.ListResultadosBusquedaRender;
import vista.ManejadorConexiones;
import vista.MidletBeeFree;
import vista.Paginador;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;

import conexion.ConectorHttp;
import conexion.ConectorRMS;

public class ManejadorEventos implements ActionListener {

	Container contenedorForm;

	public ManejadorEventos() {

		Constantes.BOTONuno.addActionListener(this);
		Constantes.BOTONdos.addActionListener(this);
		Constantes.BOTONtres.addActionListener(this);
		Constantes.BOTONcuatro.addActionListener(this);
		Constantes.BOTONcinco.addActionListener(this);
		Constantes.BOTONseis.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		contenedorForm = (Container) Paginador.getPaginador().current
				.getComponentAt(1);
		//
		// for (int i = 0; i < contenedorForm.getComponentCount(); i++) {
		// System.out.println(contenedorForm.getComponentAt(i) + " i: " + i);
		// }

		switch (Constantes.VIS_CURRENT) {

		case Constantes.LOGIN_VIS:
			if (e.getSource() instanceof Command) {
				if (e.getSource().toString().compareTo(Constantes.SALIR_COM) == 0) {

					MidletBeeFree.getMidletBeeFree().notifyDestroyed();

				}
			} else if (e.getSource() instanceof Button) {
				// INCIAR SESION
				if ((Button) e.getSource() == Constantes.BOTONuno) {

					String login = ((TextField) contenedorForm
							.getComponentAt(1)).getText();

					String pass = ((TextField) contenedorForm.getComponentAt(3))
							.getText();

					if (login != null && login.compareTo("") != 0) {

						CentralDatos.loginActual = login;
						CentralDatos.contraActual = pass;
						ManejadorConexiones.getManejadorConexiones().login(
								login, pass);

					} else {
						DialogPaginable.getDialogPaginable().setInfo(
								Constantes.LOGIN_INCORRECTO_DIA);

					}
					// CREAR CUENTA
				} else if ((Button) e.getSource() == Constantes.BOTONdos) {
					Paginador.getPaginador().adelante = true;
					Paginador.getPaginador().setCurrent(
							Constantes.CREAR_CUENTA_VIS);

				}
			}

			break;
		case Constantes.MENU_PRINCIPAL_VIS:
			if (e.getSource() instanceof Button) {
				Button miBoton = (Button) e.getSource();

				// bee free
				if (miBoton == Constantes.BOTONuno) {
					Paginador.getPaginador().adelante = true;
					Paginador.getPaginador().setCurrent(Constantes.BEEFREE_VIS);

				}
				// explorar
				else if (miBoton == Constantes.BOTONdos) {
					Paginador.getPaginador().adelante = true;
					Paginador.getPaginador()
							.setCurrent(Constantes.EXPLORAR_VIS);

				}
				// noticias
				else if (miBoton == Constantes.BOTONtres) {
					Paginador.getPaginador().adelante = true;
					// Paginador.getPaginador()
					// .setCurrent(Constantes.NOTICIAS_VIS);
					ManejadorConexiones.getManejadorConexiones()
							.cargarNoticias("uid=" + CentralDatos.UID);
				}
				// opciones
				else if (miBoton == Constantes.BOTONcuatro) {
					Paginador.getPaginador().adelante = true;
					Paginador.getPaginador()
							.setCurrent(Constantes.OPCIONES_VIS);
				}
				// ayuda
				else if (miBoton == Constantes.BOTONcinco) {
					Paginador.getPaginador().adelante = true;
					Paginador.getPaginador().setCurrent(Constantes.AYUDA_VIS);
				}
				// cerrar sesion
				else if (miBoton == Constantes.BOTONseis) {
					ConectorHttp.cerrarSesion();
					Paginador.getPaginador().adelante = false;
					Paginador.getPaginador().setCurrent(Constantes.LOGIN_VIS);

				}
			}

			break;

		case Constantes.OPCIONES_VIS:
			if (e.getSource() instanceof Button) {
				Button miBoton = (Button) e.getSource();

				// Cambiar pass
				if (miBoton == Constantes.BOTONuno) {
					Paginador.getPaginador().adelante = true;
					Paginador.getPaginador().setCurrent(
							Constantes.CAMBIAR_CONTRASENIA_VIS);

				}
				// Sincronizar
				else if (miBoton == Constantes.BOTONdos) {
					// TODO sincronizar :S
				}
				// Invitar amigos
				else if (miBoton == Constantes.BOTONtres) {
					Paginador.getPaginador().adelante = true;
					Paginador.getPaginador().setCurrent(
							Constantes.INVITAR_AMIGOS_VIS);

				}
				// editar perfil
				else if (miBoton == Constantes.BOTONcuatro) {

					ManejadorConexiones.getManejadorConexiones()
							.traerInfoPerfil();

				}

			} else if (e.getSource() instanceof Command) {

				Command comm = (Command) e.getSource();
				if (comm.toString() == Constantes.ATRAS_COM) {
					Paginador.getPaginador().adelante = false;
					Paginador.getPaginador().setCurrent(
							Constantes.MENU_PRINCIPAL_VIS);
				}
			}

			break;
		case Constantes.PRIMERA_VEZ_CONTRA_VIS:

			if (e.getSource() instanceof Button) {
				Button miBoton = (Button) e.getSource();

				if (miBoton == Constantes.BOTONuno) {

					ManejadorConexiones.getManejadorConexiones()
							.cambiarContrasenia(contenedorForm);

				}
			} else if (e.getSource() instanceof Command) {
				Command comm = (Command) e.getSource();
				if (comm.toString() == Constantes.ATRAS_COM) {
					Paginador.getPaginador().adelante = false;
					Paginador.getPaginador().setCurrent(Constantes.LOGIN_VIS);
				}
			}

			break;

		case Constantes.CAMBIAR_CONTRASENIA_VIS:
			if (e.getSource() instanceof Button) {
				Button miBoton = (Button) e.getSource();

				// Enviar nueva contrasenia
				if (miBoton == Constantes.BOTONuno) {

					ManejadorConexiones.getManejadorConexiones()
							.cambiarContrasenia(contenedorForm);

				}
			} else if (e.getSource() instanceof Command) {
				Command comm = (Command) e.getSource();
				if (comm.toString() == Constantes.ATRAS_COM) {
					Paginador.getPaginador().adelante = false;
					Paginador.getPaginador()
							.setCurrent(Constantes.OPCIONES_VIS);
				}
			}

			break;

		case Constantes.EXPLORAR_VIS:

			if (e.getSource() instanceof Button) {
				Button button = (Button) e.getSource();

				if (button == Constantes.BOTONuno) {
					Paginador.getPaginador().adelante = true;

					ConectorRMS rms = ConectorRMS.getConectorRMS();
					rms.busquedaLocal("misFotos", CentralDatos.loginActual);

					if (CentralDatos.resultadosBusqueda == null
							|| CentralDatos.cantidadResultados == 0) {
						Dialog.show("busqueda",
								"no se encontraron fotos en su movil", "ok",
								null);
					} else {
						CentralDatos.buscar = true;
						Paginador.getPaginador().setCurrent(
								Constantes.RESULTADOS_MIS_IMAGENES_VIS);
					}

				}
				// buscar imagenes
				else if (button == Constantes.BOTONdos) {

					Paginador.getPaginador().adelante = true;
					Paginador.getPaginador().setCurrent(
							Constantes.BUSCAR_IMAGEN_VIS);

				} else if (button == Constantes.BOTONtres) {
					Paginador.getPaginador().adelante = true;

					Paginador.getPaginador().setCurrent(
							Constantes.BUSCAR_COORD_MANUAL_VIS);

					// explorar el mundo
				} else if (button == Constantes.BOTONcuatro) {
					Paginador.getPaginador().adelante = true;

					Paginador.getPaginador().setCurrent(
							Constantes.BUSCAR_COORDENADA_VIS);

				}

			} else if (e.getSource() instanceof Command) {
				Command comm = (Command) e.getSource();
				if (comm.toString() == Constantes.ATRAS_COM) {
					Paginador.getPaginador().adelante = false;
					Paginador.getPaginador().setCurrent(
							Constantes.MENU_PRINCIPAL_VIS);
				}
			}

			break;

		case Constantes.AYUDA_VIS:
			if (e.getSource() instanceof Button) {
				Button miBoton = (Button) e.getSource();
				// TOMAR FOTOS
				if (miBoton == Constantes.BOTONuno) {

					// ManejadorConexiones.getManejadorConexiones().descargarFoto();

					((FormPaginableAyuda) Paginador.getPaginador().current).exenaActual = Constantes.AYUDA_TOMAR_VIS;
				}
				// BUSCAR FOTOS
				else if (miBoton == Constantes.BOTONdos) {
					((FormPaginableAyuda) Paginador.getPaginador().current).exenaActual = Constantes.AYUDA_BUSCAR_VIS;
				}
				// EDITAR FOTOS
				else if (miBoton == Constantes.BOTONtres) {
					((FormPaginableAyuda) Paginador.getPaginador().current).exenaActual = Constantes.AYUDA_EDITAR_VIS;
				}
				// SUBIR FOTOS
				else if (miBoton == Constantes.BOTONcuatro) {
					((FormPaginableAyuda) Paginador.getPaginador().current).exenaActual = Constantes.AYUDA_SUBIR_VIS;
				}
				((FormPaginableAyuda) Paginador.getPaginador().current)
						.setReproducirExena();

			} else if (e.getSource() instanceof Command) {
				Command comm = (Command) e.getSource();
				if (comm.toString() == Constantes.ATRAS_COM) {

					if (((FormPaginableAyuda) Paginador.getPaginador().current).exenaActual == 0) {
						Paginador.getPaginador().adelante = false;
						Paginador.getPaginador().setCurrent(
								Constantes.MENU_PRINCIPAL_VIS);
					} else {
						((FormPaginableAyuda) Paginador.getPaginador().current)
								.setMenuAyuda();
					}

				}
			}

			break;

		case Constantes.BUSCAR_IMAGEN_VIS:

			if (e.getSource() instanceof Button) {
				Button button = (Button) e.getSource();

				if (button == Constantes.BOTONuno) {

					// se conecta y se pone los resultados en la central de
					// datos

					AdministradorBusquedas.getAdministradorBusquedas()
							.busquedaPerzonalizada();

					if (CentralDatos.busquedaLocal) {
						if (CentralDatos.resultadosBusqueda == null
								|| CentralDatos.cantidadResultados == 0) {
							Dialog.show("busqueda",
									"no se encontraron fotos en su movil",
									"ok", null);
						} else {
							Paginador.getPaginador().adelante = true;
							CentralDatos.buscar = true;
							Paginador.getPaginador().setCurrent(
									Constantes.RESULTADOS_BUSQUEDA_VIS);
						}
					}

				}

			} else if (e.getSource() instanceof Command) {
				Command comm = (Command) e.getSource();
				if (comm.toString() == Constantes.ATRAS_COM) {
					Paginador.getPaginador().adelante = false;
					Paginador.getPaginador()
							.setCurrent(Constantes.EXPLORAR_VIS);
				}
			}

			break;

		case Constantes.RESULTADOS_MIS_IMAGENES_VIS:
			if (e.getSource() instanceof Command) {
				Command comm = (Command) e.getSource();
				if (comm.toString().compareTo(Constantes.DETALLES_COM) == 0) {
					CentralDatos.fotoDetalles = CentralDatos.resultadosBusqueda[CentralDatos.indiceLista
							+ ((CentralDatos.factorDePantallas - 1) * 10)];

					Paginador.getPaginador().adelante = true;
					Paginador.getPaginador().setCurrent(
							Constantes.DETALLES_MIS_IMAGENES_VIS);
				} else if (comm.toString().compareTo(Constantes.ATRAS_COM) == 0) {
					Paginador.getPaginador().adelante = false;
					Paginador.getPaginador()
							.setCurrent(Constantes.EXPLORAR_VIS);
				} else if (comm.toString().compareTo(Constantes.ANTERIOR_COM) == 0) {
					CentralDatos.factorDePantallas--;
					((ListResultadosBusquedaRender) Paginador.getPaginador().current)
							.repintarLista();

				} else if (comm.toString().compareTo(Constantes.SIGUIENTE_COM) == 0) {

					CentralDatos.factorDePantallas++;
					((ListResultadosBusquedaRender) Paginador.getPaginador().current)
							.repintarLista();

				}

			}
			break;

		case Constantes.RESULTADOS_BUSQUEDA_VIS:
			if (e.getSource() instanceof Command) {
				Command comm = (Command) e.getSource();
				if (comm.toString().compareTo(Constantes.DETALLES_COM) == 0) {
					Paginador.getPaginador().adelante = true;

					CentralDatos.fotoDetalles = CentralDatos.resultadosBusqueda[CentralDatos.indiceLista
							+ ((CentralDatos.factorDePantallas - 1) * 10)];

					// FIXME
					// Paginador.getPaginador().setCurrent(
					// Constantes.DETALLES_BI_VIS);
					ManejadorConexiones.getManejadorConexiones()
							.traerFotoPrevia();

				} else if (comm.toString().compareTo(Constantes.ATRAS_COM) == 0) {
					// CentralDatos.resultadosBusqueda = null;
					// CentralDatos.cantidadResultados = 0;
					Paginador.getPaginador().adelante = false;
					Paginador.getPaginador().setCurrent(
							Constantes.BUSCAR_IMAGEN_VIS);
				} else if (comm.toString().compareTo(Constantes.ANTERIOR_COM) == 0) {
					CentralDatos.factorDePantallas--;
					((ListResultadosBusquedaRender) Paginador.getPaginador().current)
							.repintarLista();

				} else if (comm.toString().compareTo(Constantes.SIGUIENTE_COM) == 0) {

					CentralDatos.factorDePantallas++;
					((ListResultadosBusquedaRender) Paginador.getPaginador().current)
							.repintarLista();

				}

			}
			break;

		case Constantes.RESULTADOS_BUSQUEDA_COORD_VIS:
			if (e.getSource() instanceof Command) {
				Command comm = (Command) e.getSource();
				if (comm.toString().compareTo(Constantes.DETALLES_COM) == 0) {
					Paginador.getPaginador().adelante = true;

					Paginador.getPaginador().setCurrent(
							Constantes.DETALLES_COORD_VIS);
				} else if (comm.toString().compareTo(Constantes.ATRAS_COM) == 0) {
					Paginador.getPaginador().adelante = false;
					Paginador.getPaginador().setCurrent(
							Constantes.BUSCAR_COORD_MANUAL_VIS);
				} else if (comm.toString().compareTo(Constantes.ANTERIOR_COM) == 0) {
					CentralDatos.factorDePantallas--;
					((ListResultadosBusquedaRender) Paginador.getPaginador().current)
							.repintarLista();

				} else if (comm.toString().compareTo(Constantes.SIGUIENTE_COM) == 0) {

					CentralDatos.factorDePantallas++;
					((ListResultadosBusquedaRender) Paginador.getPaginador().current)
							.repintarLista();

				}

			}
			break;

		case Constantes.CREAR_CUENTA_VIS:
			if (e.getSource() instanceof Command) {

				if (((Command) e.getSource()).toString().compareTo(
						Constantes.ATRAS_COM) == 0) {
					Paginador.getPaginador().adelante = false;
					Paginador.getPaginador().setCurrent(Constantes.LOGIN_VIS);
				}
			} else if (e.getSource() instanceof Button) {
				Button button = (Button) e.getSource();
				if (button == Constantes.BOTONuno) {

					String login = ((TextField) contenedorForm
							.getComponentAt(1)).getText();
					String correo = ((TextField) contenedorForm
							.getComponentAt(3)).getText();

					if (login == null || login.compareTo("") == 0
							|| correo == null || correo.compareTo("") == 0) {

						Dialog.show("registro",
								"por favor verifique los datos del registro",
								"ok", null);
					} else {
						ManejadorConexiones.getManejadorConexiones().registrar(
								"name=" + login + "&mail=" + correo
										+ "&form_id=user_register");

					}
				}
			}

			break;

		case Constantes.BEEFREE_VIS:
			// ACEPTAR
			if (e.getSource() instanceof Button) {
				if (e.getSource() == Constantes.BOTONuno) {
					// MediaComponent mc= (MediaComponent)
					// ((Form)contenedorForm.getComponentAt(1)).getComponentAt(0);
					Player p = FormPaginableInicio.p;
					VideoControl vControl = (VideoControl) p
							.getControl("VideoControl");

					try {
						byte data[];
						try {
							data = vControl
									.getSnapshot("encoding=jpeg&width=640&height=480");
						} catch (Exception a) {
							data = vControl.getSnapshot("encoding=jpeg");
						}

						CentralDatos.fotoPreviaByte = data;
						p.stop();
						p.deallocate();
						// mc.stop();
						vControl.setVisible(false);
						vControl = null;
						p = null;

						Paginador.getPaginador().adelante = true;
						Paginador.getPaginador().setCurrent(
								Constantes.FOTO_PREVIA_VIS);
					} catch (MediaException me) {

						me.printStackTrace();
					}
				}
			} else if (e.getSource() instanceof Command) {
				Command comm = (Command) e.getSource();
				if (comm.toString().compareTo(Constantes.ATRAS_COM) == 0) {
					Paginador.getPaginador().adelante = false;
					Paginador.getPaginador().setCurrent(
							Constantes.MENU_PRINCIPAL_VIS);
				}

			}

			break;

		case Constantes.DETALLES_BI_VIS:
			CentralDatos.guardando = false;
			if (e.getSource() instanceof Command) {
				Command comm = (Command) e.getSource();
				if (comm.toString().compareTo(Constantes.ATRAS_COM) == 0) {
					Paginador.getPaginador().adelante = false;
					CentralDatos.buscar = false;
					Paginador.getPaginador().setCurrent(
							Constantes.RESULTADOS_BUSQUEDA_VIS);
				} else if (comm.toString().compareTo(Constantes.GUARDAR_COM) == 0) {
					CentralDatos.guardando = true;
					// TODO aca se descarga una imagen de la red
					ManejadorConexiones.getManejadorConexiones()
							.descargarFoto();

				} else if (comm.toString().compareTo(Constantes.ELIMINAR_COM) == 0) {
					int resultado = DialogPaginable.getDialogPaginable()
							.setInfo(Constantes.CONF_ELIMINAR_FOTO_DIA);
					if (resultado == 1) {

						ConectorRMS rms = ConectorRMS.getConectorRMS();
						rms.eliminarFotografia(CentralDatos.fotoDetalles);
						CentralDatos.fotoDetalles = null;
						// TODO probar en eliminar, q el item no salga en la
						// lista
						Paginador.getPaginador().adelante = false;
						CentralDatos.buscar = false;
						Paginador.getPaginador().setCurrent(
								Constantes.RESULTADOS_BUSQUEDA_VIS);
					}
				}
			}
			if (e.getSource() instanceof Button) {
				Button button = (Button) e.getSource();

				if (button == Constantes.BOTONuno) {
					CentralDatos.guardando = false;
					ManejadorConexiones.getManejadorConexiones()
							.traerComentario();

				} else if (button == Constantes.BOTONdos) {
					ManejadorConexiones.getManejadorConexiones()
							.enviarComentario();

				}

			}
			break;

		case Constantes.DETALLES_COORD_VIS:
			if (e.getSource() instanceof Command) {
				Command comm = (Command) e.getSource();
				if (comm.toString().compareTo(Constantes.ATRAS_COM) == 0) {
					Paginador.getPaginador().adelante = false;
					// FIXME ojo aca.... tampoco buscar de nuevo
					CentralDatos.buscar = false;
					CentralDatos.busquedaLocal = false;
					Paginador.getPaginador().setCurrent(
							Constantes.RESULTADOS_BUSQUEDA_COORD_VIS);
				}
			}
			break;

		case Constantes.DETALLES_GM_VIS:
			if (e.getSource() instanceof Command) {
				Command comm = (Command) e.getSource();
				if (comm.toString().compareTo(Constantes.ATRAS_COM) == 0) {
					Paginador.getPaginador().adelante = false;
					Paginador.getPaginador().setCurrent(
							Constantes.BUSCAR_COORDENADA_VIS);
				} else if (comm.toString().compareTo(Constantes.GUARDAR_COM) == 0) {

				}
			}
			break;

		case Constantes.DETALLES_MIS_IMAGENES_VIS:
			if (e.getSource() instanceof Command) {
				Command comm = (Command) e.getSource();
				if (comm.toString().compareTo(Constantes.ATRAS_COM) == 0) {
					Paginador.getPaginador().adelante = false;
					CentralDatos.buscar = false;
					Paginador.getPaginador().setCurrent(
							Constantes.RESULTADOS_MIS_IMAGENES_VIS);
				} else if (comm.toString().compareTo(Constantes.EDITAR_COM) == 0) {
					Paginador.getPaginador().adelante = true;
					Paginador.getPaginador().setCurrent(Constantes.EDITAR_VIS);
				} else if (comm.toString().compareTo(Constantes.ELIMINAR_COM) == 0) {

					int resultado = DialogPaginable.getDialogPaginable()
							.setInfo(Constantes.CONF_ELIMINAR_FOTO_DIA);
					if (resultado == 1) {

						CentralDatos.buscar = false;
						ConectorRMS rms = ConectorRMS.getConectorRMS();
						rms.eliminarFotografia(CentralDatos.fotoDetalles);
						CentralDatos.fotoDetalles = null;

						Paginador.getPaginador().adelante = false;
						Paginador.getPaginador().setCurrent(
								Constantes.RESULTADOS_MIS_IMAGENES_VIS);
					}

				}
			}
			if (e.getSource() instanceof Button) {
				Button button = (Button) e.getSource();

				if (button == Constantes.BOTONuno) {
					ManejadorConexiones.getManejadorConexiones()
							.traerComentario();

				} else if (button == Constantes.BOTONdos) {
					ManejadorConexiones.getManejadorConexiones()
							.enviarComentario();
				}

			}
			break;

		case Constantes.EDITAR_VIS:
			if (e.getSource() instanceof Command) {
				Command comm = (Command) e.getSource();
				if (comm.toString().compareTo(Constantes.ATRAS_COM) == 0) {
					Paginador.getPaginador().adelante = false;
					Paginador.getPaginador().setCurrent(
							Constantes.DETALLES_MIS_IMAGENES_VIS);
				}
			}
			break;

		case Constantes.FOTO_PREVIA_VIS:
			if (e.getSource() instanceof Command) {
				Command comm = (Command) e.getSource();
				if (comm.toString().compareTo(Constantes.ATRAS_COM) == 0) {

					Paginador.getPaginador().adelante = false;
					Paginador.getPaginador().setCurrent(Constantes.BEEFREE_VIS);

				} else if (comm.toString().compareTo(Constantes.GUARDAR_COM) == 0) {

					Paginador.getPaginador().adelante = true;
					if (CentralDatos.haveLocation) {

						CentralDatos.trajoLugares = false;
						ManejadorConexiones.getManejadorConexiones()
								.obtenerGPS();

					} else {
						Paginador.getPaginador().setCurrent(
								Constantes.SELECCIONAR_COORD_MANUAL_VIS);
					}
				}
			}

			break;

		case Constantes.ADD_DETALLE_FOTO_VIS:
			if (e.getSource() instanceof Command) {
				Command comm = (Command) e.getSource();
				if (comm.toString().compareTo(Constantes.ATRAS_COM) == 0) {

					Paginador.getPaginador().adelante = false;
					AdministradorFotos.getAdministradorFotos()
							.formatearCamposFoto();
					Paginador.getPaginador().setCurrent(
							Constantes.FOTO_PREVIA_VIS);
				} else if (comm.toString()
						.compareTo(Constantes.AGREGAR_LUG_COM) == 0) {

					Paginador.getPaginador().adelante = true;
					AdministradorFotos.getAdministradorFotos().guardarCampos();
					Paginador.getPaginador().setCurrent(
							Constantes.NUEVO_LUGAR_VIS);

				}

			}
			if (e.getSource() instanceof Button) {
				Button button = (Button) e.getSource();
				// GUARDAR IMAGEN
				if (button == Constantes.BOTONuno) {

					boolean datosCorrectos = AdministradorFotos
							.getAdministradorFotos().guardarInfoAddFoto(
									(FormPaginableVerDetalles) Paginador
											.getPaginador().current);

					if (datosCorrectos) {
						ManejadorConexiones.getManejadorConexiones()
								.enviarFoto();

					} else {
						Dialog
								.show(
										"verifique sus datos",
										"por favor verifique que ha ingresado correctamente los datos",
										"ok", null);
					}
				}
			}
			break;

		case Constantes.NUEVO_LUGAR_VIS:
			if (e.getSource() instanceof Command) {
				Command comm = (Command) e.getSource();
				if (comm.toString().compareTo(Constantes.ATRAS_COM) == 0) {

					Paginador.getPaginador().adelante = false;
					Paginador.getPaginador().setCurrent(
							Constantes.ADD_DETALLE_FOTO_VIS);
				}

			}
			if (e.getSource() instanceof Button) {
				Button button = (Button) e.getSource();
				if (button == Constantes.BOTONuno) {

					FormPaginableVerDetalles detalles = (FormPaginableVerDetalles) Paginador
							.getPaginador().current;

					CentralDatos.nuevoSitioTuristico = detalles.t.getText();

					if (CentralDatos.nuevoSitioTuristico != null
							&& CentralDatos.nuevoSitioTuristico.compareTo("") != 0) {

						if (detalles.citys.getModel().getSize() - 1 == detalles.citys
								.getSelectedIndex()) {
							// es otro
							CentralDatos.nuevoCiudad = detalles.nvCity
									.getText();

							if (CentralDatos.nuevoCiudad != null
									&& CentralDatos.nuevoCiudad.compareTo("") != 0) {
								String aux[] = { CentralDatos.nuevoSitioTuristico };
								Ciudad c = new Ciudad(CentralDatos.nuevoCiudad,
										aux);

								if (CentralDatos.ciudades == null) {
									CentralDatos.ciudades = new Ciudad[0];
								}

								Ciudad auc[] = new Ciudad[CentralDatos.ciudades.length + 1];
								for (int i = 0; i < CentralDatos.ciudades.length; i++) {
									auc[i] = CentralDatos.ciudades[i];
								}
								auc[auc.length - 1] = c;
								CentralDatos.ciudades = auc;

								Paginador.getPaginador().adelante = false;
								Paginador.getPaginador().setCurrent(
										Constantes.ADD_DETALLE_FOTO_VIS);

							} else {
								Dialog
										.show(
												"Ciudad Erronea",
												"El campo de ciudad no puede estar vacio",
												"ok", null);
							}

						} else {

							CentralDatos.nuevoCiudad = detalles.citys
									.getSelectedItem().toString();

							int indice = detalles.citys.getSelectedIndex();
							String[] sitios = CentralDatos.ciudades[indice].sitioTuristico;
							String[] aux = new String[sitios.length + 1];

							for (int i = 0; i < sitios.length; i++) {
								aux[i] = sitios[i];
							}

							aux[aux.length - 1] = CentralDatos.nuevoSitioTuristico;
							CentralDatos.ciudades[indice].sitioTuristico = aux;

							Paginador.getPaginador().adelante = false;
							Paginador.getPaginador().setCurrent(
									Constantes.ADD_DETALLE_FOTO_VIS);
						}

					} else {
						Dialog.show("Lugar Erroneo",
								"El campo de nuevo no puede estar vacio", "ok",
								null);
					}
				}
			}
			break;

		case Constantes.MI_PERFIL_VIS:
			if (e.getSource() instanceof Command) {
				Command comm = (Command) e.getSource();
				if (comm.toString().compareTo(Constantes.ATRAS_COM) == 0) {
					Paginador.getPaginador().adelante = false;
					Paginador.getPaginador()
							.setCurrent(Constantes.OPCIONES_VIS);
				}

			}
			if (e.getSource() instanceof Button) {
				Button button = (Button) e.getSource();
				// GUARDAR PERFIL
				if (button == Constantes.BOTONuno) {

					ManejadorConexiones.getManejadorConexiones().updatePerfil(
							contenedorForm);

				}
			}
			break;

		case Constantes.BUSCAR_COORDENADA_VIS:
			if (e.getSource() instanceof Command) {
				Command comm = (Command) e.getSource();
				if (comm.toString().compareTo(Constantes.ATRAS_COM) == 0) {

					Paginador.getPaginador().adelante = false;
					Paginador.getPaginador()
							.setCurrent(Constantes.EXPLORAR_VIS);
				} else if (comm.toString().compareTo(Constantes.DETALLES_COM) == 0) {

					Paginador.getPaginador().adelante = true;
					Paginador.getPaginador().setCurrent(
							Constantes.DETALLES_GM_VIS);
				}

			}
			break;

		case Constantes.SELECCIONAR_COORD_MANUAL_VIS:
			if (e.getSource() instanceof Command) {
				Command comm = (Command) e.getSource();
				if (comm.toString().compareTo(Constantes.ATRAS_COM) == 0) {

					Paginador.getPaginador().adelante = false;
					Paginador.getPaginador().setCurrent(
							Constantes.FOTO_PREVIA_VIS);
				} else if (comm.toString().compareTo(Constantes.ACEPTAR_COM) == 0) {

					Calendar c = Calendar.getInstance(TimeZone.getDefault());
					CentralDatos.fechaActualFotoPrevia = c.getTime().getTime();

					ManejadorConexiones.getManejadorConexiones().traerLugares();

				}

			}
			break;
		case Constantes.NOTICIAS_VIS:
			if (e.getSource() instanceof Command) {
				Command comm = (Command) e.getSource();
				if (comm.toString().compareTo(Constantes.ATRAS_COM) == 0) {
					Paginador.getPaginador().adelante = false;
					Paginador.getPaginador().setCurrent(
							Constantes.MENU_PRINCIPAL_VIS);
				}
			}
			break;

		case Constantes.INVITAR_AMIGOS_VIS:
			if (e.getSource() instanceof Command) {
				Command comm = (Command) e.getSource();
				if (comm.toString().compareTo(Constantes.ATRAS_COM) == 0) {
					Paginador.getPaginador().adelante = false;
					Paginador.getPaginador()
							.setCurrent(Constantes.OPCIONES_VIS);
				} else if (comm.toString().compareTo(Constantes.ACEPTAR_COM) == 0) {
					((FormPaginableOpciones) Paginador.getPaginador().current)
							.invitar();
				}
			}
			break;

		case Constantes.BUSCAR_COORD_MANUAL_VIS:
			if (e.getSource() instanceof Command) {
				Command comm = (Command) e.getSource();
				if (comm.toString().compareTo(Constantes.ATRAS_COM) == 0) {
					Paginador.getPaginador().adelante = false;
					Paginador.getPaginador()
							.setCurrent(Constantes.EXPLORAR_VIS);
				} else if (comm.toString().compareTo(Constantes.ACEPTAR_COM) == 0) {
					// OBtener Coordenadas y el radio de busqueda y filtrar
					Paginador.getPaginador().adelante = true;
					CentralDatos.buscar = true;
					Paginador.getPaginador().setCurrent(
							Constantes.RESULTADOS_BUSQUEDA_COORD_VIS);
				}
			}
			break;

		}

	}

}
