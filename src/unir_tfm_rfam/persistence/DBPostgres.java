package unir_tfm_rfam.persistence;

import java.io.*;
import java.sql.*;

import org.postgresql.ds.*;

public class DBPostgres {

	private Connection conexion;

	String confPortNumber = "5432";
	String confDatabaseName = "unir_tfm_rfam_db";
	String confUser = "postgres";
	String confServerName = "localhost";
	String confPassword = "server";

	public boolean connectPostgres() throws IOException, SQLException {
		boolean estadoConexion = false;
		// Connection conn = null;
		try {
			// this.conexion =
			// DriverManager.getConnection("jdbc:postgresql://localhost:5432/unir_tfm_rfam_db",
			// "postgres", "server");
			PGSimpleDataSource ods = new PGSimpleDataSource();
			ods.setServerName("localhost");
			ods.setDatabaseName("unir_tfm_rfam_db");
			ods.setPortNumber(Integer.parseInt("5432"));
			ods.setUser("postgres");
			ods.setPassword("server");

			this.conexion = ods.getConnection();
			this.conexion.setTransactionIsolation(2);
			this.conexion.setAutoCommit(true);

			System.out.println("Connected to the PostgreSQL server successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return estadoConexion;
	}

	public void closePostgres() {
		/*try {
			this.conexion.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}*/
	}

	public void reOpenPostgres() {
		try {
			this.conexion.close();
			System.out.println("desconeccion de oracle");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public Connection getConexion() {
		return conexion;
	}

	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}

	/**
	 * obtiene el id del resident por codigo de picada
	 * 
	 * @param code
	 * @return
	 */
	/*
	 * public Long getResidentIdByCode(String code) { Long id = null; try { String
	 * sql = "select re.id " + "from employee emp " +
	 * "inner join resident re on emp.resident_id = re.id " +
	 * "where emp.budgetheadingnumber = ?"; pst =
	 * this.conexion.prepareStatement(sql); pst.setString(1, code); ResultSet rs =
	 * this.pst.executeQuery(); if (rs.next()) { id = rs.getLong("id"); } if (rs !=
	 * null) { rs.close(); rs = null; } } catch (SQLException exe) {
	 * exe.printStackTrace(); exe.printStackTrace(); try { this.conexion.rollback();
	 * } catch (SQLException exe1) { exe1.printStackTrace(); } } return id; }
	 * 
	 * 
	 * public boolean consultarExistente(Long idResident, Date fechaHora, Long
	 * idDispositivo) { Timestamp fechaSql = new Timestamp(fechaHora.getTime()); try
	 * { PreparedStatement p =
	 * this.conexion.prepareStatement("SELECT count(*) as contador " +
	 * "from terminalrecord " + "WHERE resident_id = ? " +
	 * "and registrationdate = ?" + "and biometric_id = ?");
	 * 
	 * p.setLong(1, idResident); p.setTimestamp(2, fechaSql); p.setLong(3,
	 * idDispositivo); ResultSet rs = p.executeQuery(); Integer contador = 0; if
	 * (rs.next()) { contador = rs.getInt("contador"); } if (rs != null) {
	 * rs.close(); rs = null; } if (contador == 0) { return false; } else if
	 * (contador > 0) { return true; } } catch (SQLException ex) {
	 * ex.printStackTrace();
	 * Logger.getLogger(DBPostgres.class.getName()).log(Level.SEVERE, null, ex); }
	 * return false; }
	 * 
	 * 
	 * Timestamp currentDate;
	 * 
	 * public Boolean saveRecord(Long id, Long idResident, Date fechaHora, Long
	 * idDispositivo) { Timestamp fechaSql = new Timestamp(fechaHora.getTime());
	 * currentDate = new Timestamp(new Date().getTime());
	 * 
	 * String sql = "INSERT INTO public.terminalrecord(" +
	 * "id, registrationdate, transaciondate, biometric_id, resident_id) VALUES (?, ?, ?, ?, ?)"
	 * ; try { PreparedStatement p = this.conexion.prepareStatement(sql);
	 * p.setLong(1, id); p.setTimestamp(2, fechaSql); p.setTimestamp(3,
	 * currentDate); p.setLong(4, idDispositivo); p.setLong(5, idResident);
	 * p.executeUpdate();
	 * 
	 * if (p != null) { p.close(); p = null; } return true;
	 * 
	 * } catch (SQLException ex) { rl.logger.info("oracle Excepcion: " + ex);
	 * Logger.getLogger(DBPostgres.class.getName()).log(Level.SEVERE, null, ex);
	 * return false; } // Todo ver si va dentro del catch el return }
	 * 
	 * 
	 * 
	 * public Long getBiometricId(Integer identificator, Boolean isActive) { Long id
	 * = null; try { String sql =
	 * "select id from biometric where isactive= ? AND identificator= ?"; pst =
	 * this.conexion.prepareStatement(sql); pst.setBoolean(1, isActive);
	 * pst.setInt(2, identificator); ResultSet rs = this.pst.executeQuery(); if
	 * (rs.next()) { id = rs.getLong("id"); } if (rs != null) { rs.close(); rs =
	 * null; } } catch (SQLException exe) { rl.logger.info("oracle Excepcion: " +
	 * exe); exe.printStackTrace(); try { this.conexion.rollback(); } catch
	 * (SQLException exe1) { rl.logger.info("oracle Excepcion: " + exe1); } } return
	 * id; }
	 * 
	 * public Boolean updateBiometricId() {
	 * 
	 * String sql =
	 * "UPDATE identitygenerator SET value = (select (max(id) + 1 ) from terminalrecord) "
	 * + "WHERE name = ?"; try { PreparedStatement p =
	 * this.conexion.prepareStatement(sql); p.setString(1, "TerminalRecord");
	 * 
	 * ResultSet rs = p.executeQuery(); if (rs.next()) { if (p != null) { p.close();
	 * p = null; } return true; } if (p != null) { p.close(); p = null; } return
	 * false; } catch (SQLException ex) { rl.logger.info("oracle Excepcion: " + ex);
	 * Logger.getLogger(DBPostgres.class.getName()).log(Level.SEVERE, null, ex);
	 * 
	 * } // Todo ver si va dentro del catch el return return false; }
	 * 
	 * public Long getNextTerminalRecordId() { Long id = null; try { String sql =
	 * "select value from identitygenerator where name = ?"; pst =
	 * this.conexion.prepareStatement(sql); pst.setString(1, "TerminalRecord");
	 * ResultSet rs = this.pst.executeQuery(); if (rs.next()) { id =
	 * rs.getLong("value"); } if (rs != null) { rs.close(); rs = null; } } catch
	 * (SQLException exe) { rl.logger.info("oracle Excepcion: " + exe);
	 * exe.printStackTrace(); try { this.conexion.rollback(); } catch (SQLException
	 * exe1) { rl.logger.info("oracle Excepcion: " + exe1); } } return id; }
	 * 
	 * public List<DatosValidarConsolaTerminalesDTO> consultarEjecucionConsolas() {
	 * List<DatosValidarConsolaTerminalesDTO> listaregistros = new
	 * ArrayList<DatosValidarConsolaTerminalesDTO>();
	 * 
	 * try { System.out.println(
	 * "Creating statement...<Cuenta los registros de marcadas de todas la consolas del dia de hoy>"
	 * );
	 * 
	 * this.stActual = this.conexion.createStatement(1005, 1007);
	 * 
	 * String sql = "SELECT COUNT(*) AS CONT, DB.identificator, DB.IP, DB.NAME " +
	 * "FROM BIOMETRIC DB, terminalrecord RP " +
	 * "WHERE DB.ID = RP.biometric_id AND DB.isactive = true " +
	 * "AND RP.transaciondate::date = CURRENT_DATE " +
	 * "GROUP BY DB.identificator, DB.IP, DB.NAME "; ResultSet rs =
	 * this.stActual.executeQuery(sql);
	 * 
	 * while (rs.next()) { String CONT = rs.getString("CONT"); String
	 * DIS_IDENTIFICADOR = rs.getString("identificator"); String DIS_IP =
	 * rs.getString("IP"); String DIS_UBICACION = rs.getString("NAME"); // Todo
	 * controlar nulos DatosValidarConsolaTerminalesDTO dc = new
	 * DatosValidarConsolaTerminalesDTO(); dc.setContadorRegistros(CONT);
	 * dc.setIdentificadorDispostitivo(DIS_IDENTIFICADOR);
	 * dc.setIpDispositivo(DIS_IP); dc.setUbicacionDispostitivo(DIS_UBICACION);
	 * listaregistros.add(dc); }
	 * rl.logger.info("Se obtuvo todos los registros de las consolas"); rs.close();
	 * 
	 * if (rs != null) { rs.close(); rs = null; } if (this.stActual != null) {
	 * this.stActual.close(); this.stActual = null; } return listaregistros; } catch
	 * (SQLException exe) { rl.logger.info("oracle Excepcion: " + exe);
	 * exe.printStackTrace(); try { this.conexion.rollback(); } catch (SQLException
	 * exe1) { rl.logger.info("oracle Excepcion: " + exe1); exe.printStackTrace(); }
	 * } return listaregistros; }
	 * 
	 * public List<DatosRegistradosDataB_DTO> consultarRegistros(String idDispos) {
	 * List<DatosRegistradosDataB_DTO> listaregistros = new
	 * ArrayList<DatosRegistradosDataB_DTO>(); int countRegistros = 0; String
	 * dataString = ""; try { rl.logger.
	 * info("Ejecutando Consulta <Traer Registros Ingresados de la bd>.....");
	 * this.stActual = this.conexion.createStatement(1005, 1007);
	 * 
	 * String sql = "SELECT RP.resident_id reId, RP.registrationdate, DB.id bioId "
	 * + "FROM BIOMETRIC DB, terminalrecord RP " + "WHERE DB.ID = RP.biometric_id "
	 * + "and DB.id = ( " +
	 * "	SELECT distinct id FROM BIOMETRIC WHERE identificator = " +
	 * Integer.parseInt(idDispos) +
	 * "	AND RP.transaciondate::date = CURRENT_DATE )"; ResultSet rs =
	 * this.stActual.executeQuery(sql);
	 * 
	 * while (rs.next()) { Long IDENTIFICADOR_DISPOSITIVO = rs.getLong("bioId"); //
	 * dataString = rs.getString("registrationdate"); Date fecha =
	 * rs.getTimestamp("registrationdate"); Long IDENTIFICADOR_USUARIO =
	 * rs.getLong("reId"); // Todo controlar nulos DatosRegistradosDataB_DTO dc =
	 * new DatosRegistradosDataB_DTO(); dc.setCodEmpleado("" +
	 * IDENTIFICADOR_USUARIO); dc.setFechaMarcada(fecha); dc.setIdDispositivo("" +
	 * IDENTIFICADOR_DISPOSITIVO); listaregistros.add(dc); countRegistros++;
	 * 
	 * rl.logger.info("Registros en la bd-> Cedula empleado: " +
	 * IDENTIFICADOR_USUARIO + " FechaMarcada: " + dataString + " Idispositivo: " +
	 * IDENTIFICADOR_DISPOSITIVO); }
	 * 
	 * rs.close();
	 * 
	 * if (rs != null) { rs.close(); rs = null; } if (this.stActual != null) {
	 * this.stActual.close(); this.stActual = null; } return listaregistros; } catch
	 * (SQLException exe) { rl.logger.info("oracle Excepcion: " + exe);
	 * exe.printStackTrace(); try { this.conexion.rollback(); } catch (SQLException
	 * exe1) { rl.logger.info("oracle Excepcion: " + exe1); exe.printStackTrace(); }
	 * } catch (IllegalArgumentException ex2) { rl.logger.info("oracle Excepcion: "
	 * + ex2); ex2.printStackTrace(); } return listaregistros; }
	 * 
	 * public String consultarRegistrosPorCodigoDis(String IdDisp) {
	 * 
	 * String dataString = ""; try { rl.logger.
	 * info("Ejecutando Consulta <Traer dispositivos que registraron en la bd>....."
	 * ); this.stActual = this.conexion.createStatement(1005, 1007);
	 * 
	 * String sql = "SELECT DISTINCT DB.IP " +
	 * "FROM BIOMETRIC DB, terminalrecord RP " +
	 * "WHERE DB.ID = RP.biometric_id AND DB.isactive = true " +
	 * "AND DB.identificator = ( " + "	SELECT DISTINCT identificator " +
	 * " FROM BIOMETRIC " + "	WHERE identificator = "+Integer.parseInt(IdDisp) +
	 * ") AND RP.transaciondate::date = CURRENT_DATE"; ResultSet rs =
	 * this.stActual.executeQuery(sql);
	 * 
	 * List l = new ArrayList<String>(); while (rs.next()) { String Trimed =
	 * rs.getString("IP"); Trimed = Trimed.trim(); l.add(Trimed); }
	 * 
	 * dataString = String.valueOf(l);
	 * System.out.println("Ips de los dispositivos que ingresaron data en la bd: " +
	 * dataString); if (dataString == "" || dataString == "[]") { rs.close(); return
	 * null; }
	 * 
	 * rs.close();
	 * 
	 * if (rs != null) { rs.close(); rs = null; } if (this.stActual != null) {
	 * this.stActual.close(); this.stActual = null; return dataString; }
	 * 
	 * } catch (SQLException exe) { exe.printStackTrace();
	 * rl.logger.info("oracle Excepcion: " + exe);
	 * Logger.getLogger(DBPostgres.class.getName()).log(Level.SEVERE, null, exe);
	 * try { this.conexion.rollback(); } catch (SQLException exe1) {
	 * rl.logger.info("oracle Excepcion: " + exe1); exe.printStackTrace(); } } catch
	 * (IllegalArgumentException ex2) { ex2.printStackTrace();
	 * rl.logger.info("oracle Excepcion: " + ex2); } return dataString; }
	 */
}
