package unir_tfm_rfam.persistence;

import java.io.*;
import java.sql.*;
import java.util.*;
//import java.util.logging.*;

//import org.apache.logging.log4j.core.Logger;

import unir_tfm_rfam.dto.*;
import unir_tfm_rfam.model.common.*;
import unir_tfm_rfam.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Query {

	private static final Logger LOG = LogManager.getLogger(Query.class);

	private DBPostgres db;
	private Statement stActual;

	private Boolean verifyAccess(ServiceRequest request) {
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

	public UserResponse findUser(String username, String password) {
		db = new DBPostgres();
		UserResponse ur = new UserResponse();
		try {
			db.connectPostgres();
			this.stActual = db.getConexion().createStatement(1005, 1007);

			String sql = "select us.id from _user us where us.username = '" + username + "' and us.password = '"
					+ password + "' ";
			ResultSet rs = this.stActual.executeQuery(sql);

			while (rs.next()) {
				ur.setUserId(rs.getLong("id"));
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (this.stActual != null) {
				this.stActual.close();
				this.stActual = null;
			}

			if (ur.getUserId() != null) {
				ur.setMessage("existing user");
			} else {
				ur.setMessage("user does not exist");
			}
			ur.setStatus(ResponseStatus.OK_200.toString());

		} catch (IOException | SQLException e) {
			ur.setMessage(e.toString());
			ur.setStatus(ResponseStatus.Internal_Server_Error_500.toString());
			e.printStackTrace();
		} finally {
			db.closePostgres();
		}
		return ur;
	}

	/**
	 * Listado de usuario paginado
	 * 
	 * @param request
	 * @param criteria
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public UserResponse listUsers(ServiceRequest request, String criteria, int pageNumber, int pageSize) {
		UserResponse ur = new UserResponse();

		if (!verifyAccess(request)) {
			ur.setMessage("Credenciales incorrectas");
			ur.setStatus(ResponseStatus.Bad_Request_400.toString());
		} else {
			db = new DBPostgres();
			List<User> users = new ArrayList<User>();

			ur.setQuantity(countUsers());
			ur.setPageSize(pageSize);

			try {
				db.connectPostgres();

				this.stActual = db.getConexion().createStatement(1005, 1007);
				String sql = "select us.* from _user us where us.surname ilike '%" + criteria + "%' or us.name like '%"
						+ criteria + "%' LIMIT " + pageSize + " offset " + (pageNumber - 1) * pageSize;
				ResultSet rs = this.stActual.executeQuery(sql);
				User us;

				while (rs.next()) {
					us = new User(rs.getLong("id"), rs.getString("name"), rs.getString("surname"), rs.getString("dni"),
							rs.getString("email"), rs.getString("username"), rs.getString("password"),
							rs.getBoolean("isActive"));
					users.add(us);
				}

				ur.setUsers(users);

				if (rs != null) {
					rs.close();
				}

				if (this.stActual != null) {
					this.stActual.close();
				}
				ur.setMessage("");
				ur.setStatus(ResponseStatus.OK_200.toString());
			} catch (Exception e) {
				ur.setMessage(e.toString());
				ur.setStatus(ResponseStatus.Internal_Server_Error_500.toString());
				System.out.print(e.toString());
			} finally {
				db.closePostgres();
			}

		}
		return ur;
	}

	public UserResponse createUser(ServiceRequest request, User user) {
		UserResponse ur = new UserResponse();

		if (!verifyAccess(request)) {
			ur.setMessage("Credenciales incorrectas");
			ur.setStatus(ResponseStatus.Bad_Request_400.toString());
		} else {
			db = new DBPostgres();
			try {
				db.connectPostgres();

				String sql = "INSERT INTO public._user (name, surname, dni, email, username, password, isactive) VALUES( ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement st = db.getConexion().prepareStatement(sql);
				st.setString(1, user.getName());
				st.setString(2, user.getSurname());
				st.setString(3, user.getDni());
				st.setString(4, user.getEmail());
				st.setString(5, user.getUsername());
				st.setString(6, user.getPassword());
				st.setBoolean(7, Boolean.TRUE);

				st.executeUpdate();
				if (st != null) {
					st.close();
				}

				user.setId(finUserId(user.getDni()));
				ur.setUser(user);
				ur.setMessage("Usuario creado con exito");
				ur.setStatus(ResponseStatus.OK_200.toString());
			} catch (Exception e) {
				ur.setMessage(e.toString());
				ur.setStatus(ResponseStatus.Internal_Server_Error_500.toString());
				System.out.print(e.toString());
			} finally {
				db.closePostgres();
			}

		}
		return ur;
	}

	private Long finUserId(String dni) {
		db = new DBPostgres();
		Long idUser = (long) 0;

		try {
			db.connectPostgres();
			String sql = "select us.id as userId from _user us where us.dni = ?";
			PreparedStatement pst = db.getConexion().prepareStatement(sql);
			pst.setString(1, dni);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				idUser = rs.getLong("userId");
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
		return idUser;
	}

	private Integer countUsers() {
		db = new DBPostgres();
		Integer quantity = 0;

		try {
			db.connectPostgres();
			this.stActual = db.getConexion().createStatement(1005, 1007);

			String sql = "select count(us) quantity from _user us";
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
