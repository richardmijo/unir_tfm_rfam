package unir_tfm_rfam.model.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatrimonialInformation {
	private Long id;

	private Date presentation_date;
	private String observation;

	private List<BankInformation> bankInformations = new ArrayList<BankInformation>();
	private List<DebtInformation> debtInformations = new ArrayList<DebtInformation>();

	public PatrimonialInformation(Long id, Date presentation_date, String observation) {
		super();
		this.id = id;
		this.presentation_date = presentation_date;
		this.observation = observation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getPresentation_date() {
		return presentation_date;
	}

	public void setPresentation_date(Date presentation_date) {
		this.presentation_date = presentation_date;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public List<BankInformation> getBankInformations() {
		return bankInformations;
	}

	public void setBankInformations(List<BankInformation> bankInformations) {
		this.bankInformations = bankInformations;
	}

	public List<DebtInformation> getDebtInformations() {
		return debtInformations;
	}

	public void setDebtInformations(List<DebtInformation> debtInformations) {
		this.debtInformations = debtInformations;
	}

}
