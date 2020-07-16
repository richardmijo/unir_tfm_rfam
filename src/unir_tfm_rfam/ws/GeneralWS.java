package unir_tfm_rfam.ws;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import unir_tfm_rfam.controller.HousingProjectController;
import unir_tfm_rfam.controller.PatrimonialInformationController;
import unir_tfm_rfam.controller.UserController;
import unir_tfm_rfam.dto.BankInformationResponse;
import unir_tfm_rfam.dto.HousingProjectResponse;
import unir_tfm_rfam.dto.ServiceRequest;
import unir_tfm_rfam.dto.UserResponse;
import unir_tfm_rfam.model.common.User;
import unir_tfm_rfam.util.ResponseStatus;
import unir_tfm_rfam.util.UploadedFile;

@WebService
public class GeneralWS {

	private UserController userController;
	private HousingProjectController housingProjectController;
	private PatrimonialInformationController patrimonialInformationController;
	// private static final Logger LOG = LogManager.getLogger(GeneralWS.class);

	@Resource
	WebServiceContext wsContext;

	@WebMethod
	public UserResponse findUsers(@WebParam(name = "request") ServiceRequest request,
			@WebParam(name = "criteria") String criteria, @WebParam(name = "pageNumber") int pageNumber,
			@WebParam(name = "pageSize") int pageSize) {

		userController = new UserController();
		UserResponse ur = null;
		// System.out.println(ClassLoader.getSystemResource("log4j2.xml"));
		// LOG.info("consultando");
		try {
			ur = userController.listUsers(request, criteria, pageNumber, pageSize);
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
		userController = new UserController();
		UserResponse ur = null;
		try {
			ur = userController.createUser(request, user);
		} catch (Exception e) {
			ur = new UserResponse();
			ur.setMessage(e.toString());
			ur.setStatus(ResponseStatus.Internal_Server_Error_500.toString());
			e.printStackTrace();
		}
		return ur;
	}

	@WebMethod
	public UserResponse uploadProfilePicture(@WebParam(name = "request") ServiceRequest request,
			@WebParam(name = "userId") Long userId, @WebParam(name = "Dfile") UploadedFile uploadedFile) {
		userController = new UserController();
		UserResponse ur = null;
		try {
			ur = userController.uploadProfilePicture(request, userId, uploadedFile);
		} catch (Exception e) {
			ur = new UserResponse();
			ur.setMessage(e.toString());
			ur.setStatus(ResponseStatus.Internal_Server_Error_500.toString());
			e.printStackTrace();
		}
		return ur;
	}

	@WebMethod
	public UserResponse zipBackupFiles(@WebParam(name = "request") ServiceRequest request, String filename) {
		userController = new UserController();
		UserResponse ur = null;
		try {
			ur = userController.zipBackupFiles(request, filename);
		} catch (Exception e) {
			ur = new UserResponse();
			ur.setMessage(e.toString());
			ur.setStatus(ResponseStatus.Internal_Server_Error_500.toString());
			e.printStackTrace();
		}
		return ur;
	}

	@WebMethod
	public HousingProjectResponse reportHousingProjects(@WebParam(name = "request") ServiceRequest request,
			@WebParam(name = "criteria") String criteria, @WebParam(name = "pageNumber") int pageNumber,
			@WebParam(name = "pageSize") int pageSize) {

		housingProjectController = new HousingProjectController();
		HousingProjectResponse hpr;
		// System.out.println(ClassLoader.getSystemResource("log4j2.xml"));
		try {
			hpr = housingProjectController.listHousingProjects(request, criteria, pageNumber, pageSize);
		} catch (Exception e) {
			hpr = new HousingProjectResponse();
			hpr.setMessage(e.toString());
			hpr.setStatus(ResponseStatus.Internal_Server_Error_500.toString());
			e.printStackTrace();
		}

		return hpr;
	}

	@WebMethod
	public BankInformationResponse findPatrimonialInformation(@WebParam(name = "request") ServiceRequest request) {

		patrimonialInformationController = new PatrimonialInformationController();
		BankInformationResponse bir;
		// System.out.println(ClassLoader.getSystemResource("log4j2.xml"));
		try {
			bir = patrimonialInformationController.listDebtInformationByDNI(request);
		} catch (Exception e) {
			bir = new BankInformationResponse();
			bir.setMessage(e.toString());
			bir.setStatus(ResponseStatus.Internal_Server_Error_500.toString());
			e.printStackTrace();
		}

		return bir;
	}

	private void InvalidateSession() {
		final MessageContext mc = this.wsContext.getMessageContext();
		mc.get(MessageContext.SERVLET_REQUEST);
	}

}
