package unir_tfm_rfam.controller;

import unir_tfm_rfam.dto.BankInformationResponse;
import unir_tfm_rfam.dto.ServiceRequest;
import unir_tfm_rfam.persistence.PatrominialInformationDAO;

public class PatrimonialInformationController {

	PatrominialInformationDAO pid = new PatrominialInformationDAO();

	public BankInformationResponse listDebtInformationByDNI(ServiceRequest request) {
		return pid.listDebtInformationByDNI(request);
	}

}
