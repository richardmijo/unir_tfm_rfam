package unir_tfm_rfam.model.common;

public class User {

	private Long id;

	private String name;
	private String surname;
	private String dni;
	private String email;

	private String username;
	private String password;
	private Boolean isActive;

	private String profilePictureURL;
	private String profilePictureName;

	public User() {
	}

	public User(Long id, String name, String surname, String dni, String email, String username, String password,
			Boolean isActive) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.dni = dni;
		this.email = email;
		this.username = username;
		this.password = password;
		this.isActive = isActive;
	}

	public User(Long id, String name, String surname, String dni, String email, String username, String password,
			Boolean isActive, String profilePictureURL, String profilePictureName) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.dni = dni;
		this.email = email;
		this.username = username;
		this.password = password;
		this.isActive = isActive;
		this.profilePictureURL = profilePictureURL;
		this.profilePictureName = profilePictureName;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getProfilePictureURL() {
		return profilePictureURL;
	}

	public void setProfilePictureURL(String profilePictureURL) {
		this.profilePictureURL = profilePictureURL;
	}

	public String getProfilePictureName() {
		return profilePictureName;
	}

	public void setProfilePictureName(String profilePictureName) {
		this.profilePictureName = profilePictureName;
	}

}
