package unir_tfm_rfam.dto;

/**
 * Solicitud de un servicio para un contribuyente especifico
 */
public class ServiceRequest {

	/**
	 * username Nombre de usuario en el sistema
	 */
	private String username;

	/**
	 * Contraseña de la entidad que solicita un servicio
	 */
	private String password;

	/**
	 * Identificacion unica en el sistema
	 */
	private String dni;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

}
