package unir_tfm_rfam.model.common;

import java.math.BigDecimal;

public class BankInformation {

	private Long id;

	private String name;
	private String account_number;
	private String account_type;
	private BigDecimal balance;

	public BankInformation() {

	}

	public BankInformation(Long id, String name, String account_number, String account_type, BigDecimal balance) {
		this.id = id;
		this.name = name;
		this.account_number = account_number;
		this.account_type = account_type;
		this.balance = balance;
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

	public String getAccount_number() {
		return account_number;
	}

	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}

	public String getAccount_type() {
		return account_type;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
