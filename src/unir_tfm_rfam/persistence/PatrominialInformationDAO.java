package unir_tfm_rfam.persistence;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import unir_tfm_rfam.dto.BankInformationDTO;
import unir_tfm_rfam.dto.BankInformationResponse;
import unir_tfm_rfam.dto.ServiceRequest;
import unir_tfm_rfam.util.ResponseStatus;

public class PatrominialInformationDAO {
	
	private DBPostgres db;
	private Statement stActual;

	public Boolean verifyAccess(ServiceRequest request) {
		Long userId = null;
		db = new DBPostgres();
		try {
			db.connectPostgres();
			this.stActual = db.getConexion().createStatement(1005, 1007);

			String sql = "select us.id from _user us where us.username = '" + request.getUsername()
					+ "' and us.password = '" + request.getPassword() + "' ";
			ResultSet rs = this.stActual.executeQuery(sql);

			while (rs.next()) {
				userId = rs.getLong("id");
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (this.stActual != null) {
				this.stActual.close();
				this.stActual = null;
			}

			if (userId != null) {
				return true;
			} else {
				return false;
			}

		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} finally {
			db.closePostgres();
		}
		return false;
	}

	public BankInformationResponse listDebtInformationByDNI(ServiceRequest request) {

		BankInformationResponse bir = new BankInformationResponse();

		if (!verifyAccess(request)) {
			bir.setMessage("Credenciales incorrectas");
			bir.setStatus(ResponseStatus.Bad_Request_400.toString());
		} else {
			db = new DBPostgres();
			

			try {
				db.connectPostgres();

				this.stActual = db.getConexion().createStatement(1005, 1007);
				String sql = "select " + 
						"	pi.presentation_date, " + 
						"	pi.observation, " + 
						"	di.institution_name, " + 
						"	di.start_date, " + 
						"	di.debt_term, " + 
						"	di.amount, " + 
						"	di.obligation_type, " + 
						"	di.credit_card_number " + 
						"from patrimonial_information pi " + 
						"join postulant po on pi.postulant_id = po.id " + 
						"join debt_information di on pi.id = di.patrimonial_information_id " + 
						"where po.dni = ?";
				
				
				PreparedStatement pst = db.getConexion().prepareStatement(sql);
				pst.setString(1, request.getDni());

				ResultSet rs = pst.executeQuery();
				
				BankInformationDTO dto;

				while (rs.next()) {
					dto = new BankInformationDTO(
							rs.getDate("presentation_date"), 
							rs.getString("observation"), 
							rs.getString("institution_name"),
							rs.getDate("start_date"),
							rs.getLong("debt_term"),
							rs.getBigDecimal("amount"),
							rs.getString("obligation_type"),
							rs.getString("credit_card_number"));
					bir.getBankInformationDTOs().add(dto);
				}

				
				if (rs != null) {
					rs.close();
				}

				if (this.stActual != null) {
					this.stActual.close();
				}
				
				bir.setMessage("");
				bir.setStatus(ResponseStatus.OK_200.toString());
			} catch (Exception e) {
				bir.setMessage(e.toString());
				bir.setStatus(ResponseStatus.Internal_Server_Error_500.toString());
				System.out.print(e.toString());
			} finally {
				db.closePostgres();
			}

		}
		return bir;
	}

}
