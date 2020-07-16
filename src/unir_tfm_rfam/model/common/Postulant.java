package unir_tfm_rfam.model.common;

import java.util.ArrayList;
import java.util.List;

public class Postulant {

	private Long id;

	private String name;
	private String dni;
	private String address;
	private String postalCode;
	private String phoneNumber;
	private String email;

	private List<PatrimonialInformation> patrimonialInformations = new ArrayList<PatrimonialInformation>();

	public Postulant() {

	}

	public Postulant(Long id, String name, String dni, String address, String postalCode, String phoneNumber,
			String email) {
		this.id = id;
		this.name = name;
		this.dni = dni;
		this.address = address;
		this.postalCode = postalCode;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<PatrimonialInformation> getPatrimonialInformations() {
		return patrimonialInformations;
	}

	public void setPatrimonialInformations(List<PatrimonialInformation> patrimonialInformations) {
		this.patrimonialInformations = patrimonialInformations;
	}

}
