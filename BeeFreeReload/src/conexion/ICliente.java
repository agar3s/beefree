package conexion;

public interface ICliente {

	// invoca al DialogCargando. ke ejecuta la conexion.
	public void realizarPeticion();

	// se encarga de leer los datos listo de la conexion
	public void actualizar();

}
