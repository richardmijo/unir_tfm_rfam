package unir_tfm_rfam.ws;

import javax.jws.*;

import unir_tfm_rfam.persistence.*;

@WebService
public class EmployeeWS {
	private final double EXCHANGE_RATE_USD_EUR = 0.89f;
	private Query query;

	@WebMethod
	public double usdToEur(double usd) {
		return usd * EXCHANGE_RATE_USD_EUR;
	}

	/*@WebMethod
	public boolean login(String name, String password) {
		query = new Query();
		try {
			query.getAllEmployees(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}*/

}
