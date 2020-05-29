package unir_tfm_rfam.ws;

import javax.annotation.*;
import javax.jws.*;
import javax.xml.ws.*;
import javax.xml.ws.handler.*;

import org.apache.logging.log4j.*;

import unir_tfm_rfam.dto.*;
import unir_tfm_rfam.model.common.*;
import unir_tfm_rfam.persistence.*;
import unir_tfm_rfam.util.*;

@WebService
public class GeneralWS {

	private final double EXCHANGE_RATE_USD_EUR = 0.89f;
	private Query query;
	private static final Logger LOG = LogManager.getLogger(GeneralWS.class);

	@Resource
	WebServiceContext wsContext;

	@WebMethod
	public UserResponse login(@WebParam(name = "username") String username,
			@WebParam(name = "password") String password) {
		query = new Query();
		UserResponse ur;
		try {
			ur = query.findUser(username, password);
		} catch (Exception e) {
			ur = new UserResponse();
			ur.setMessage(e.toString());
			ur.setStatus(ResponseStatus.Internal_Server_Error_500.toString());
			e.printStackTrace();
		}

		return ur;
	}

	@WebMethod
	public UserResponse findUsers(@WebParam(name = "request") ServiceRequest request,
			@WebParam(name = "criteria") String criteria, @WebParam(name = "pageNumber") int pageNumber,
			@WebParam(name = "pageSize") int pageSize) {

		query = new Query();
		UserResponse ur;
		// System.out.println(ClassLoader.getSystemResource("log4j2.xml"));
		try {
			ur = query.listUsers(request, criteria, pageNumber, pageSize);
		} catch (Exception e) {
			ur = new UserResponse();
			ur.setMessage(e.toString());
			ur.setStatus(ResponseStatus.Internal_Server_Error_500.toString());
			e.printStackTrace();
		}

		return ur;
	}

	@WebMethod
	public UserResponse createUser(@WebParam(name = "request") ServiceRequest request,
			@WebParam(name = "user") User user) {
		query = new Query();
		UserResponse ur;
		try {
			ur = query.createUser(request, user);
		} catch (Exception e) {
			ur = new UserResponse();
			ur.setMessage(e.toString());
			ur.setStatus(ResponseStatus.Internal_Server_Error_500.toString());
			e.printStackTrace();
		}

		return ur;
	}

	private void InvalidateSession() {
		final MessageContext mc = this.wsContext.getMessageContext();
		mc.get(MessageContext.SERVLET_REQUEST);
	}

}
