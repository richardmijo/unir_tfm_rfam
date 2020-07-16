package unir_tfm_rfam.controller;

import unir_tfm_rfam.dto.ServiceRequest;
import unir_tfm_rfam.dto.UserResponse;
import unir_tfm_rfam.model.common.User;
import unir_tfm_rfam.persistence.UserDAO;
import unir_tfm_rfam.util.UploadedFile;

public class UserController {

	private UserDAO ud = new UserDAO();

	public UserResponse listUsers(ServiceRequest request, String criteria, int pageNumber, int pageSize) {
		return ud.listUsers(request, criteria, pageNumber, pageSize);				
	}
	
	public UserResponse createUser(ServiceRequest request, User user) {
		return ud.createUser(request, user);
	}
	
	public UserResponse uploadProfilePicture(ServiceRequest request, Long userId, UploadedFile uploadedFile) {
		return ud.uploadProfilePicture(request, userId, uploadedFile);				
	}
	
	public UserResponse zipBackupFiles(ServiceRequest request, String filename) {
		return ud.zipBackupFiles(request,filename);
	}
	
	//@WebParam(name="Dfile")
	/*public void uploadFile(UploadedFile uploadedFile) {	
        DataHandler dataHandler = uploadedFile.getDataHandler();
        
        String path = System.getProperty("user.dir");
        
        System.out.println("Working Directory = " + path);
        
        System.out.println(".............. llega"+dataHandler.getContentType());
        

        try {
            InputStream inputStream = dataHandler.getInputStream();

            OutputStream fileOutputStream = new FileOutputStream(new File(uploadedFile.getName() + "." + uploadedFile.getFileType()));

            byte[] bytesBuffer = new byte[100000];

            int bytesRead;

            while ((bytesRead = inputStream.read(bytesBuffer)) != -1) {
                fileOutputStream.write(bytesBuffer, 0, bytesRead);
            }

            fileOutputStream.flush();

            fileOutputStream.close();
            inputStream.close();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }*/

}
