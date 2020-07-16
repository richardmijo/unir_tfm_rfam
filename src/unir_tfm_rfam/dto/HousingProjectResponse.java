package unir_tfm_rfam.dto;

import java.util.List;

import unir_tfm_rfam.model.common.HousingProject;

public class HousingProjectResponse {

	private Long projectId;
	private String message;
	private String status;

	private List<HousingProject> housingProjects;

	private Integer quantity;
	private Integer pageSize;

	private String reportURL;

	private HousingProject housingProject;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<HousingProject> getHousingProjects() {
		return housingProjects;
	}

	public void setHousingProjects(List<HousingProject> housingProjects) {
		this.housingProjects = housingProjects;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public HousingProject getHousingProject() {
		return housingProject;
	}

	public void setHousingProject(HousingProject housingProject) {
		this.housingProject = housingProject;
	}

	public String getReportURL() {
		return reportURL;
	}

	public void setReportURL(String reportURL) {
		this.reportURL = reportURL;
	}

}
