package unir_tfm_rfam.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;

import unir_tfm_rfam.dto.ServiceRequest;
import unir_tfm_rfam.dto.UserResponse;
import unir_tfm_rfam.model.common.SystemParameter;
import unir_tfm_rfam.model.common.User;
import unir_tfm_rfam.util.ResponseStatus;
import unir_tfm_rfam.util.UploadedFile;

public class UserDAO {

	private DBPostgres db;
	private Statement stActual;
	private SystemParameterDAO spd;

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
				System.out.println("------ list users "+sql);
				ResultSet rs = this.stActual.executeQuery(sql);
				User us;

				while (rs.next()) {
					us = new User(rs.getLong("id"), rs.getString("name"), rs.getString("surname"), rs.getString("dni"),
							rs.getString("email"), rs.getString("username"), rs.getString("password"),
							rs.getBoolean("isActive"), rs.getString("profile_picture_url"),
							rs.getString("profile_picture_name"));
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

	/**
	 * Permite la creación de usuarios con los datos mínimos requeridos
	 * 
	 * @param request
	 * @param user
	 * @return
	 */
	public UserResponse createUser(ServiceRequest request, User user) {
		UserResponse ur = new UserResponse();

		if (!verifyAccess(request)) {
			ur.setMessage("Credenciales incorrectas");
			ur.setStatus(ResponseStatus.Bad_Request_400.toString());
		} else {
			db = new DBPostgres();
			try {
				db.connectPostgres();

				String sql = "INSERT INTO public._user (name, surname, dni, email, username, password, isactive, id) VALUES( ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement st = db.getConexion().prepareStatement(sql);
				st.setString(1, user.getName());
				st.setString(2, user.getSurname());
				st.setString(3, user.getDni());
				st.setString(4, user.getEmail());
				st.setString(5, user.getUsername());
				st.setString(6, user.getPassword());
				st.setBoolean(7, Boolean.TRUE);
				st.setLong(8, findMaxUserID());

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

	public UserResponse uploadProfilePicture(ServiceRequest request, Long userId, UploadedFile uploadedFile) {
		UserResponse ur = new UserResponse();
		if (!verifyAccess(request)) {
			ur.setMessage("Credenciales incorrectas");
			ur.setStatus(ResponseStatus.Bad_Request_400.toString());
		} else {
			
			spd = new SystemParameterDAO(); 
			SystemParameter FILE_LOCATION = spd.findParameterByName("FILE_LOCATION");
			
			DataHandler dataHandler = uploadedFile.getDataHandler();
			
			String path = FILE_LOCATION.getValue();
			String fileName = path + "/" + uploadedFile.getName() + "." + uploadedFile.getFileType();

			try {
				InputStream inputStream = dataHandler.getInputStream();
				OutputStream fileOutputStream = new FileOutputStream(new File(fileName));

				byte[] bytesBuffer = new byte[100000];
				int bytesRead;

				while ((bytesRead = inputStream.read(bytesBuffer)) != -1) {
					fileOutputStream.write(bytesBuffer, 0, bytesRead);
				}

				fileOutputStream.flush();
				fileOutputStream.close();
				inputStream.close();

				db = new DBPostgres();
				db.connectPostgres();

				String sql = "update public._user set profile_picture_url = ?, profile_picture_name = ? where id = ?";
				PreparedStatement st = db.getConexion().prepareStatement(sql);
				st.setString(1, path);
				st.setString(2, fileName);
				st.setLong(3, userId);

				st.executeUpdate();
				if (st != null) {
					st.close();
				}

				ur.setUser(findUserById(userId));
				ur.setMessage("Foto de perfil asignada correctamente");
				ur.setStatus(ResponseStatus.OK_200.toString());

			} catch (Exception e) {
				e.printStackTrace();
				ur.setMessage(e.toString());
				ur.setStatus(ResponseStatus.Internal_Server_Error_500.toString());
			} finally {
				db.closePostgres();
			}
		}
		return ur;
	}

	private Long findMaxUserID() {
		db = new DBPostgres();
		Long idUser = (long) 0;

		try {
			db.connectPostgres();
			String sql = "select max(us.id) as userId from _user us ";
			PreparedStatement pst = db.getConexion().prepareStatement(sql);

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

		return Long.sum(idUser, 1);
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

	/**
	 * Buscar un usuario por su ID
	 * 
	 * @param id
	 * @return
	 */
	public User findUserById(Long id) {
		db = new DBPostgres();
		User us = null;

		try {
			db.connectPostgres();
			String sql = "select us.* from _user us where us.id = ?";
			PreparedStatement pst = db.getConexion().prepareStatement(sql);
			pst.setLong(1, id);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				us = new User(rs.getLong("id"), rs.getString("name"), rs.getString("surname"), rs.getString("dni"),
						rs.getString("email"), rs.getString("username"), rs.getString("password"),
						rs.getBoolean("isActive"), rs.getString("profile_picture_url"),
						rs.getString("profile_picture_name"));
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
		return us;
	}

	public UserResponse zipBackupFiles(ServiceRequest request, String filename) {
		UserResponse ur = new UserResponse();

		if (!verifyAccess(request)) {
			ur.setMessage("Credenciales incorrectas");
			ur.setStatus(ResponseStatus.Bad_Request_400.toString());
		} else {

			spd = new SystemParameterDAO();
			SystemParameter FILE_LOCATION = spd.findParameterByName("FILE_LOCATION");

			ProcessBuilder processBuilder = new ProcessBuilder();
			
			// processBuilder.command("cmd.exe", "/c", "cd " + FILE_LOCATION + " && tar cvf c:/opt/" + filename + ".zip *.*");
			processBuilder.command("bash", "-c", "cd "+FILE_LOCATION.getValue()+" && tar -zcvf "+filename+".tar.gz .");
			
			try {
				StringBuffer sb = new StringBuffer();
				Process process = processBuilder.start();
				BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

				String line;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
					System.out.println(line);
				}
				int exitCode = process.waitFor();
				ur.setMessage("exitCode: " + exitCode + "\n" + sb.toString());
				ur.setStatus(ResponseStatus.OK_200.toString());

			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return ur;
	}

}
