package conexion;

import java.io.IOException;

import javax.microedition.io.Connector;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

public class SMSSender implements Runnable, ICargable {

	String numero[];
	Thread miHilo;
	private static SMSSender miSMSSender;

	private String message;

	private SMSSender() {
		message = "HOLA! te invito a que entres a www.maratonmovil2.com/drupalBF y disfrutes la libertad de enviar y compartir fotos :D, BEE FREE!!";

	}

	public static void sendMessage(String[] numero) {
		if (miSMSSender == null) {
			miSMSSender = new SMSSender();
		}
		miSMSSender.numero = numero;
		miSMSSender.miHilo = new Thread(miSMSSender);
		miSMSSender.miHilo.start();
	}

	public void run() {

		MessageConnection mc;
		TextMessage msj;
		String address;
		for (int i = 0; i < numero.length; i++) {

			address = "sms://" + numero[i];
			try {
				mc = (MessageConnection) Connector.open(address);
				msj = (TextMessage) mc
						.newMessage(MessageConnection.TEXT_MESSAGE);
				// binary.setAddress(address);
				msj.setPayloadText(message);

				mc.send(msj);
				mc.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isTerminado() {
		return false;
	}

	public void setDatos() {

	}

}
