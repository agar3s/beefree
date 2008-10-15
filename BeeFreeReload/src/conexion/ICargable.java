package conexion;

public interface ICargable {

	// devuelve el estado de la conexion
	public boolean isTerminado();

	// obliga a la conexion a setear los datos de la conexion.
	public void setDatos();
}
