package unir_tfm_rfam.model.common;

public class HousingProject {

	private Long id;

	private String name;
	private String number;

	private Boolean isEnabled;

	public HousingProject() {
	}

	public HousingProject(Long id, String name, String number, Boolean isEnabled) {
		super();
		this.id = id;
		this.name = name;
		this.number = number;
		this.isEnabled = isEnabled;
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

}
