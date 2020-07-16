package unir_tfm_rfam.persistence;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import unir_tfm_rfam.model.common.SystemParameter;

public class SystemParameterDAO {
	
	private DBPostgres db;
	
	public SystemParameter findParameterByName(String name) {
		db = new DBPostgres();
		SystemParameter sp = null;

		try {
			db.connectPostgres();
			String sql = "select sp.* from system_parameter sp where sp.name = ?";
			PreparedStatement pst = db.getConexion().prepareStatement(sql);
			pst.setString(1, name);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				sp = new SystemParameter(rs.getLong("id"), rs.getString("name"), rs.getString("value"));
			}

			if (rs != null) {
				rs.close();
			}
			if (pst != null) {
				pst.close();
			}

		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} finally {
			db.closePostgres();
		}
		return sp;
	}

}
