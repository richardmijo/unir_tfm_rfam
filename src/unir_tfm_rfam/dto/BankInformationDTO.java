package unir_tfm_rfam.dto;

import java.math.BigDecimal;
import java.util.Date;

public class BankInformationDTO {

	private Date presentation_date;
	private String observation;

	private String institution_name;
	private Date start_date;
	private Long debt_term;
	private BigDecimal amount;
	private String obligation_type;
	private String credit_card_number;

	public BankInformationDTO() {

	}

	public BankInformationDTO(Date presentation_date, String observation, String institution_name, Date start_date,
			Long debt_term, BigDecimal amount, String obligation_type, String credit_card_number) {
		super();
		this.presentation_date = presentation_date;
		this.observation = observation;
		this.institution_name = institution_name;
		this.start_date = start_date;
		this.debt_term = debt_term;
		this.amount = amount;
		this.obligation_type = obligation_type;
		this.credit_card_number = credit_card_number;
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

	public String getInstitution_name() {
		return institution_name;
	}

	public void setInstitution_name(String institution_name) {
		this.institution_name = institution_name;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Long getDebt_term() {
		return debt_term;
	}

	public void setDebt_term(Long debt_term) {
		this.debt_term = debt_term;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getObligation_type() {
		return obligation_type;
	}

	public void setObligation_type(String obligation_type) {
		this.obligation_type = obligation_type;
	}

	public String getCredit_card_number() {
		return credit_card_number;
	}

	public void setCredit_card_number(String credit_card_number) {
		this.credit_card_number = credit_card_number;
	}

}
