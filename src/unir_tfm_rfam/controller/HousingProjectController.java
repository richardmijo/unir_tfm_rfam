package unir_tfm_rfam.controller;

import unir_tfm_rfam.dto.HousingProjectResponse;
import unir_tfm_rfam.dto.ServiceRequest;
import unir_tfm_rfam.persistence.HousingProjectDAO;

public class HousingProjectController {

	private HousingProjectDAO housingProject = new HousingProjectDAO();

	public HousingProjectResponse listHousingProjects(ServiceRequest request, String criteria, int pageNumber,
			int pageSize) {
		return housingProject.listHousingProjects(request, criteria, pageNumber, pageSize);
	}

}
