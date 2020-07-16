package unir_tfm_rfam.model.common;

import java.math.BigDecimal;
import java.util.Date;

public class DebtInformation {

	private Long id;

	private String institution_name;
	private Date start_date;
	private Long debt_term;
	private BigDecimal amount;
	private String obligation_type;
	private String credit_card_number;

	public DebtInformation() {

	}

	public DebtInformation(Long id, String institution_name, Date start_date, Long debt_term, BigDecimal amount,
			String obligation_type, String credit_card_number) {
		super();
		this.id = id;
		this.institution_name = institution_name;
		this.start_date = start_date;
		this.debt_term = debt_term;
		this.amount = amount;
		this.obligation_type = obligation_type;
		this.credit_card_number = credit_card_number;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
