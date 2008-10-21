package vista;

import java.io.IOException;

import javax.microedition.lcdui.Canvas;

import logica.CentralDatos;
import logica.Constantes;
import logica.StringTokenizer;

import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.Graphics;
import com.sun.lwuit.StaticAnimation;
import com.sun.lwuit.util.Resources;

import conexion.ICliente;

public class FormNavegador extends Form implements Runnable, IPaginable,
		ICliente {

	final int altoImagen = (getHeight() / 6) * 10;
	final int anchoImagen = (getWidth() / 6) * 10;

	// Unidad de desplazamiento,
	double UDFActor;
	double UDLongitud;
	double UDLatitud;

	int x = getWidth() / 2 - 5;
	int y = getHeight() / 2 - 5;

	Thread hilo;
	boolean isCorriendo = false;
	boolean desplazar;
	boolean cargando;

	int cont = 0;

	double radioBusqueda = 1;
	boolean buscando = false;

	/** variables para la exploracion libre */
	private boolean vistaLibre = false;
	private int nRes = 0;
	private double reslat[];
	private double reslong[];
	private int resnid[];
	/***/

	private static FormNavegador miFormNavegador;
	private StaticAnimation cursor;

	byte consulta = 0;

	public static FormNavegador getFormNavegador() {
		if (miFormNavegador == null) {
			miFormNavegador = new FormNavegador();
			CentralDatos.altoImagen = miFormNavegador.altoImagen;
			CentralDatos.anchoImagen = miFormNavegador.anchoImagen;
		}
		return miFormNavegador;
	}

	public void show() {
		super.show();

		if (consulta == DialogCargando.CONEXION_GPS) {
			DialogCargando.getDialogCargando().showModeless();
		} else if (consulta == DialogCargando.CONEXION_GOOGLE_MAP) {

		}
	}

	private FormNavegador() {
		try {
			vistaLibre = false;
			isCorriendo = true;
			Thread hilo = new Thread(this);
			hilo.start();
			iniciarVariables();
			// iniciarGrilla(true);
			try {
				Resources r = Resources.open("/businessTheme.res");
				cursor = r.getAnimation("beeCursor");
			} catch (IOException ioe) {
				System.out.println("Couldn't load theme.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void iniciarVariables() {
		CentralDatos.zoom = 13;
		if (CentralDatos.latitud == 0 && CentralDatos.longitud == 0) {
			CentralDatos.longitud = -74.1412451810;
			CentralDatos.latitud = 4.63;
		}

		UDFActor = 256 * cuadrado(2, CentralDatos.zoom);
		UDLongitud = 360 / UDFActor;
		UDLatitud = 360 / UDFActor;
		System.out.println(UDLatitud);

	}

	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(0XFFD807);
		g.fillRect(16, 26, getWidth() - 32, getHeight() - 52);
		g.clipRect(20, 30, getWidth() - 40, getHeight() - 60);
		if (CentralDatos.imagenMapa != null) {
			g.drawImage(CentralDatos.imagenMapa, CentralDatos.viewPortX,
					CentralDatos.viewPortY);

			int t = cont;

			if (buscando) {
				g.drawArc((int) (x - radioBusqueda), (int) (y - radioBusqueda),
						(int) (radioBusqueda * 2), (int) (radioBusqueda * 2),
						0, 360);
				g.setColor(0X127699);

			}
			g.drawArc(x - t, y - t, t * 2, t * 2, 0, 360);
			g.setColor(0X00000);
			g.drawArc(x - t - 1, y - t - 1, t * 2 + 2, t * 2 + 2, 0, 360);

			t = cont / 2;
			g.drawArc(x - t, y - t, t * 2, t * 2, 0, 360);
			g.setColor(0X00000);
			g.drawArc(x - t - 1, y - t - 1, t * 2 + 2, t * 2 + 2, 0, 360);

			if (vistaLibre) {
				// int xres;
				// int yres;
				int color = 0X000000;
				for (int i = 0; i < nRes; i++) {
					// xres=(int)
					// ((Math.abs(reslong[i])-Math.abs(CentralDatos.longitud))/UDLongitud);
					// yres=(int)
					// ((Math.abs(reslat[i])-Math.abs(CentralDatos.latitud))/UDLongitud);
					g.setColor(color + (0XFFFFF / nRes) * i);
					g
							.fillArc(
									(int) (CentralDatos.viewPortX + reslong[i] - CentralDatos.zoom / 2),
									(int) (CentralDatos.viewPortY + reslat[i] - CentralDatos.zoom / 2),
									CentralDatos.zoom, CentralDatos.zoom, 0,
									360);
				}
			}

			if (cursor != null) {
				g.drawImage(cursor, (x - cursor.getWidth() / 2), (y - cursor
						.getHeight()));
			}
		} else {

			g.setColor(0XFFFFFF);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(0X000000);
			g.drawString("Cargando mapa", x - 50, y);

		}

	}

	public void keyPressed(int keyCode) {
		super.keyPressed(keyCode);
		if (!desplazar && CentralDatos.imagenMapa != null) {
			if (keyCode == -3) {
				CentralDatos.longitud = CentralDatos.longitud - UDLongitud * 5;
				if (x > getWidth() / 2) {
					x -= 5;
				} else {
					CentralDatos.viewPortX += 5;
				}
				if (CentralDatos.viewPortX >= 0) {
					CentralDatos.viewPortX -= 5;
					x -= 5;
				}
				if (x <= getWidth() / 10) {
					iniciarGrilla(false);
				}
			} else if (keyCode == -4) {
				CentralDatos.longitud = CentralDatos.longitud + UDLongitud * 5;
				if (x < getWidth() / 2) {
					x += 5;
				} else {
					CentralDatos.viewPortX -= 5;
				}
				if (CentralDatos.viewPortX < -(anchoImagen - getWidth())) {
					CentralDatos.viewPortX += 5;
					x += 5;
				}
				if (x >= getWidth() * 0.9) {
					iniciarGrilla(false);
				}
			} else if (keyCode == -1) {
				CentralDatos.latitud = CentralDatos.latitud + UDLatitud * 5;
				if (y > getHeight() / 2) {
					y -= 5;
				} else {
					CentralDatos.viewPortY += 5;
				}
				if (CentralDatos.viewPortY >= 0) {
					CentralDatos.viewPortY -= 5;
					y -= 5;
				}
				if (y <= getHeight() * 0.2) {
					iniciarGrilla(false);
				}
			} else if (keyCode == -2) {
				CentralDatos.latitud = CentralDatos.latitud - UDLatitud * 5;
				if (y < getHeight() / 2) {
					y += 5;
				} else {
					CentralDatos.viewPortY -= 5;
				}
				if (CentralDatos.viewPortY < -(altoImagen - getHeight())) {
					CentralDatos.viewPortY += 5;
					y += 5;
				}
				if (y >= getHeight() * 0.9) {
					iniciarGrilla(false);
				}
			} else if (keyCode == Canvas.KEY_NUM1) {
				CentralDatos.zoom--;
				iniciarGrilla(true);
			} else if (keyCode == Canvas.KEY_NUM3) {
				CentralDatos.zoom++;
				iniciarGrilla(true);
			} else if (buscando) {
				if (keyCode == Canvas.KEY_NUM7) {
					System.out.println("radio");
					radioBusqueda--;
					if (radioBusqueda < 1) {
						radioBusqueda = 1;
					}
				} else if (keyCode == Canvas.KEY_NUM9) {
					radioBusqueda++;
				}
			}
			System.gc();
			// repaint();
		}
	}

	private void iniciarGrilla(boolean isZoom) {
		if (isZoom) {
			UDFActor = 256 * cuadrado(2, CentralDatos.zoom);
			UDLongitud = 360 / UDFActor;
			UDLatitud = 360 / UDFActor;
			x = getWidth() / 2 - 5;
			y = getHeight() / 2 - 5;
			CentralDatos.viewPortX = -(anchoImagen - getWidth()) / 2;
			CentralDatos.viewPortY = -(altoImagen - getHeight()) / 2;
		} else {
			if (x < getWidth() / 2) {
				CentralDatos.viewPortX = -anchoImagen / 2 + x;
			} else {
				CentralDatos.viewPortX = -anchoImagen / 2 + x;
			}
			if (y < getHeight() / 2) {
				CentralDatos.viewPortY = -altoImagen / 2 + y;
			} else {
				CentralDatos.viewPortY = -altoImagen / 2 + y;
			}

			desplazar = true;
		}
		System.gc();
		cargando = true;

		consulta = DialogCargando.CONEXION_GOOGLE_MAP;
		DialogCargando.getDialogCargando().iniciarCarga(this, consulta, null,
				null);

		System.gc();

		repaint();

	}

	/**
	 * Vista de navegacion donde se elige un punto y un radio de busqueda.
	 */
	public void setBuscarCoordenadas() {
		consulta = DialogCargando.CONEXION_GPS;
		DialogCargando.getDialogCargando().iniciarCarga(this, consulta, null,
				null);
		buscando = true;

		// conectorGPS gps = new conectorGPS();
		// gps.obtenerPosicion();
		// if (CentralDatos.haveGPS) {
		// consultaLongitud = CentralDatos.longitud;
		// consultaLatitud = CentralDatos.latitud;
		// }

		addCommand(new Command(Constantes.ATRAS_COM));
		addCommand(new Command(Constantes.ACEPTAR_COM));
	}

	/**
	 * Vista del mapa donde aparece listada la foto donde se bajo.
	 */
	public void setMostrarUbicacionFoto() {

	}

	public void setFormNavegar() {
		vistaLibre = true;
		consulta = DialogCargando.CONEXION_GPS;
		DialogCargando.getDialogCargando().iniciarCarga(this, consulta, null,
				null);

		// conectorGPS gps = new conectorGPS();
		// gps.obtenerPosicion();
		//
		// if (CentralDatos.haveGPS) {
		// consultaLongitud = CentralDatos.longitud;
		// consultaLatitud = CentralDatos.latitud;
		// }

		addCommand(new Command(Constantes.ATRAS_COM));
		addCommand(new Command(Constantes.DETALLES_COM));
	}

	public void setFormNavegarManual() {
		System.out.println("1");
		if (CentralDatos.imagenMapa == null)
			iniciarGrilla(true);
		addCommand(new Command(Constantes.ATRAS_COM));
		addCommand(new Command(Constantes.ACEPTAR_COM));
	}

	public double cuadrado(int n, int zoom) {
		int potencia = 1;
		for (int i = 0; i < zoom; i++) {
			potencia = potencia * n;
		}

		return potencia;
	}

	public void run() {
		boolean terminado;
		while (isCorriendo) {
			if (!cargando) {
				if (desplazar) {
					terminado = true;
					if (x < getWidth() / 2) {
						x += 5;
						CentralDatos.viewPortX += 5;
						terminado = false;
					} else if (x > getWidth() / 2) {
						x -= 5;
						CentralDatos.viewPortX -= 5;
						terminado = false;
					}

					if (y < getHeight() / 2) {
						y += 5;
						CentralDatos.viewPortY += 5;
						terminado = false;
					} else if (y > getHeight() / 2) {
						y -= 5;
						CentralDatos.viewPortY -= 5;
						terminado = false;
					}
					if (!terminado) {
						repaint();
					} else {
						desplazar = false;
					}

				}
			}
			try {
				if (cursor != null && cursor.isAnimation()) {
					// cursor.animate();
				}
				cont++;
				if (cont > 30) {
					cont = 0;
					System.gc();
				}
				repaint();
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void destruir() {
		isCorriendo = false;
		while (hilo != null && hilo.isAlive()) {
			System.out.println("estoy vivo");
		}
		System.out.println("ups " + CentralDatos.latitud);
		miFormNavegador = null;
	}

	public void actualizar() {
		if (consulta == DialogCargando.CONEXION_GPS) {
			iniciarGrilla(true);
		} else if (consulta == DialogCargando.CONEXION_GOOGLE_MAP) {
			cargando = false;

			if (vistaLibre) {
				consulta = DialogCargando.CONEXION_HTTP_POST;
				DialogCargando.getDialogCargando().iniciarCarga(
						this,
						consulta,
						Constantes.HTTP + Constantes.HTTP_LOCATION_SEARCH2,
						"lat=" + CentralDatos.latitud + "&lon="
								+ CentralDatos.longitud);
			} else {
				show();
			}
		} else if (consulta == DialogCargando.CONEXION_HTTP_POST) {
			System.out.println("to logn: " + CentralDatos.respuesta);
			if (CentralDatos.respuesta != null
					&& CentralDatos.respuesta.compareTo("") != 0) {
				StringTokenizer tok = new StringTokenizer(
						CentralDatos.respuesta, ";");
				nRes = tok.tokens - 1;
				reslat = new double[nRes];
				reslong = new double[nRes];
				resnid = new int[nRes];

				int i = 0;
				while (i < tok.tokens - 1) {
					StringTokenizer res = new StringTokenizer(
							tok.nextElement(), "::");
					resnid[i] = Integer.parseInt(res.nextElement());

					reslat[i] = Double.parseDouble(res.nextElement());
					reslat[i] = CentralDatos.altoImagen
							/ 2
							- ((Math.abs(reslat[i]) - Math
									.abs(CentralDatos.latitud)) / UDLongitud);

					reslong[i] = Double.parseDouble(res.nextElement());
					reslong[i] = CentralDatos.anchoImagen
							/ 2
							- ((Math.abs(reslong[i]) - Math
									.abs(CentralDatos.longitud)) / UDLongitud);

					// System.out.println(reslat[i]+":"+reslong[i]);
					i++;

				}
				System.out.println("i: " + i);
			} else {
				nRes = 0;
				reslat = new double[nRes];
				reslong = new double[nRes];
				resnid = new int[nRes];
			}
			consulta = -1;
			show();
		}
		// if (consulta == DialogCargando.CONEXION_GPS) {
		// System.out.println("GPS consulta");
		// if (CentralDatos.haveGPS) {
		// // CentralDatos.longitud;
		// // CentralDatos.latitud;
		// }
		// iniciarGrilla(true);
		// } else {
		// System.out.println("image consulta");
		// consulta=0;
		// }
		// cargando = false;
		// repaint();
		// show();
	}

	public void realizarPeticion() {

	}

}
