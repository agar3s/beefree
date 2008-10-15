package vista;

import logica.Constantes;

import com.sun.lwuit.Dialog;

public class DialogPaginable {

	private static DialogPaginable miDialogPaginable;

	private DialogPaginable() {
	}

	public static DialogPaginable getDialogPaginable() {
		if (miDialogPaginable == null) {
			miDialogPaginable = new DialogPaginable();
		}
		return miDialogPaginable;

	}

	public int contadorNoticias;

	public byte setInfo(byte dia) {
		byte result = 0;

		switch (dia) {
		case Constantes.INFO_NUEVAS_NOTICIAS_DIA:
			Dialog
					.show(
							"Nuevas noticias",
							"usted tiene "
									+ contadorNoticias
									+ " noticias nuevas, por favor revise el apartado de noticias",
							Dialog.TYPE_INFO, null, "ok", null);
			result = Constantes.MENU_PRINCIPAL_VIS;
			break;
		case Constantes.CONF_INVITACION_DIA:

			break;
		case Constantes.INFO_INVITACION_ENVIADA_DIA:

			break;
		case Constantes.CONFIRMACION_GUARDAR_DIA:

			break;
		case Constantes.CONF_ELIMINAR_FOTO_DIA:
			boolean eliminar = Dialog.show("desea eliminar??",
					"esta seguro que desea eliminar la foto del sistema?",
					"si", "no");
			if (eliminar) {
				result = 1;
			} else {
				result = 0;
			}

			break;
		case Constantes.CONF_REEMPLAZAR_FOTO_DIA:

			break;
		case Constantes.TRANSMISION_WEB_DIA:

			break;
		case Constantes.NUEVO_LUGAR_DIA:

			break;
		case Constantes.LOGIN_INCORRECTO_DIA:
			Dialog.show("titulo", "mucho texto...perdon poquito texto",
					Dialog.TYPE_ERROR, null, "ok", null);
			result = Constantes.LOGIN_VIS;
			break;

		case Constantes.CAMBIO_CONTRASENIA_VALIDO_DIA:
			Dialog.show("cambiar contraseña", "contraseña cambiada con exito",
					Dialog.TYPE_INFO, null, "ok", null);
			result = Constantes.OPCIONES_VIS;
			break;

		case Constantes.CAMBIO_CONTRASENIA_INVALIDO_DIA:
			Dialog.show("cambiar contraseña",
					"su datos son incorrectos, por favor verifiquelos",
					Dialog.TYPE_ERROR, null, "ok", null);
			result = Constantes.CAMBIAR_CONTRASENIA_VIS;
			break;

		}

		return result;
	}

	public void destruir() {
		miDialogPaginable = null;
	}

}
