package unir_tfm_rfam.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Query {

	private static final Logger LOG = LogManager.getLogger(Query.class);

		

	

	/*
	 * public List<Employee> getAllEmployees(String criteria) { db = new
	 * DBPostgres(); System.out.println("------------------ "); try {
	 * db.connectPostgres();
	 * 
	 * this.stActual = db.getConexion().createStatement(1005, 1007);
	 * 
	 * String sql = "select * " + "from employee em " + "where em.name like '%" +
	 * criteria + "%' "; System.out.println("la sql------------- " + sql); ResultSet
	 * rs = this.stActual.executeQuery(sql); // NativeQueryResultsMapper.map(rs.,
	 * Employee.class);
	 * 
	 * while (rs.next()) { Long CONT = rs.getLong("id"); System.out.println(CONT); }
	 * 
	 * rs.close();
	 * 
	 * if (rs != null) { rs.close(); rs = null; } if (this.stActual != null) {
	 * this.stActual.close(); this.stActual = null; } return null; } catch
	 * (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 * finally { db.closePostgres(); } return null; }
	 */

}
