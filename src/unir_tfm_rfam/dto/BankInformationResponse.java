package unir_tfm_rfam.dto;

import java.util.ArrayList;
import java.util.List;

public class BankInformationResponse {

	private String message;
	private String status;

	private List<BankInformationDTO> bankInformationDTOs = new ArrayList<BankInformationDTO>();

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

	public List<BankInformationDTO> getBankInformationDTOs() {
		return bankInformationDTOs;
	}

	public void setBankInformationDTOs(List<BankInformationDTO> bankInformationDTOs) {
		this.bankInformationDTOs = bankInformationDTOs;
	}

}
