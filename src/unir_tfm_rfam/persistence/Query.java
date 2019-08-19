package unir_tfm_rfam.persistence;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import unir_tfm_rfam.model.Employee;

public class Query {
	
	private DBPostgres db;
	private Statement stActual;
	
	public List<Employee> getAllEmployees(String criteria){
		db = new DBPostgres();
		System.out.println("------------------ ");
		try {
			db.conectarPostgres();
			
			this.stActual = db.getConexion().createStatement(1005, 1007);
			
			String sql = "select * " + 
					"from employee em " + 
					"where em.name like '%"+criteria+"%' ";
			System.out.println("la sql------------- "+sql);
			ResultSet rs = this.stActual.executeQuery(sql);
			//NativeQueryResultsMapper.map(rs., Employee.class);
			
			while (rs.next()) {
				Long CONT = rs.getLong("id");
				System.out.println(CONT);
				/*String DIS_IDENTIFICADOR = rs.getString("identificator");
				String DIS_IP = rs.getString("IP");
				String DIS_UBICACION = rs.getString("NAME");
				// Todo controlar nulos
				DatosValidarConsolaTerminalesDTO dc = new DatosValidarConsolaTerminalesDTO();
				dc.setContadorRegistros(CONT);
				dc.setIdentificadorDispostitivo(DIS_IDENTIFICADOR);
				dc.setIpDispositivo(DIS_IP);
				dc.setUbicacionDispostitivo(DIS_UBICACION);
				listaregistros.add(dc);*/
			}
			
			rs.close();

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (this.stActual != null) {
				this.stActual.close();
				this.stActual = null;
			}
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//db.cerrarPostgres();
		}
		return null;
	}

}
