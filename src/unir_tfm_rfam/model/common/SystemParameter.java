package unir_tfm_rfam.model.common;

public class SystemParameter {
	private Long id;

	private String name;
	private String value;

	public SystemParameter() {

	}

	public SystemParameter(Long id, String name, String value) {
		this.id = id;
		this.name = name;
		this.value = value;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
