package unir_tfm_rfam.persistence;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import unir_tfm_rfam.dto.HousingProjectResponse;
import unir_tfm_rfam.dto.ServiceRequest;
import unir_tfm_rfam.model.common.HousingProject;
import unir_tfm_rfam.util.ResponseStatus;

public class HousingProjectDAO {

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

	public HousingProjectResponse listHousingProjects(ServiceRequest request, String criteria, int pageNumber,
			int pageSize) {

		HousingProjectResponse hpr = new HousingProjectResponse();

		if (!verifyAccess(request)) {
			hpr.setMessage("Credenciales incorrectas");
			hpr.setStatus(ResponseStatus.Bad_Request_400.toString());
		} else {
			db = new DBPostgres();
			List<HousingProject> projects = new ArrayList<HousingProject>();

			hpr.setQuantity(countHousinProjects());
			hpr.setPageSize(pageSize);

			try {
				db.connectPostgres();

				this.stActual = db.getConexion().createStatement(1005, 1007);
				String sql = "select hp.* from housing_project hp where hp.name ilike '%" + criteria
						+ "%' or hp.number like '%" + criteria + "%' LIMIT " + pageSize + " offset "
						+ (pageNumber - 1) * pageSize;
				ResultSet rs = this.stActual.executeQuery(sql);
				HousingProject hp;

				while (rs.next()) {
					hp = new HousingProject(rs.getLong("id"), rs.getString("name"), rs.getString("number"),
							rs.getBoolean("is_enabled"));
					projects.add(hp);
				}

				hpr.setHousingProjects(projects);

				if (rs != null) {
					rs.close();
				}

				if (this.stActual != null) {
					this.stActual.close();
				}

				hpr.setReportURL(generateTXTReport(projects));
				hpr.setMessage("");
				hpr.setStatus(ResponseStatus.OK_200.toString());
			} catch (Exception e) {
				hpr.setMessage(e.toString());
				hpr.setStatus(ResponseStatus.Internal_Server_Error_500.toString());
				System.out.print(e.toString());
			} finally {
				db.closePostgres();
			}

		}
		return hpr;
	}

	public String generateTXTReport(List<HousingProject> projects) {
		String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String path = "http://localhost:8080/";

		try (Writer writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(path + fileName + ".txt"), "utf-8"))) {
			for (HousingProject hp : projects) {
				writer.write(hp.getId() + "\t" + hp.getNumber() + "\t" + hp.getName() + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path + fileName + ".txt";
	}

	private Integer countHousinProjects() {
		db = new DBPostgres();
		Integer quantity = 0;

		try {
			db.connectPostgres();
			this.stActual = db.getConexion().createStatement(1005, 1007);

			String sql = "select count(hpro) quantity from housing_project hpro";
			ResultSet rs = this.stActual.executeQuery(sql);

			while (rs.next()) {
				quantity = rs.getInt("quantity");
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (this.stActual != null) {
				this.stActual.close();
				this.stActual = null;
			}

		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} finally {
			db.closePostgres();
		}
		return quantity;
	}
}
