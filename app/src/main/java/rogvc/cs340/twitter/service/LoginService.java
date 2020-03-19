package rogvc.cs340.twitter.service;

import rogvc.cs340.twitter.dao.I_DAO_Login;
import rogvc.cs340.twitter.dao.aws.AWS_LoginDAO;
import rogvc.cs340.twitter.service.request.LoginRequest;
import rogvc.cs340.twitter.service.response.LoginResponse;

public class LoginService {
    public static LoginResponse login(LoginRequest request){
        LoginResponse response;
        I_DAO_Login loginDAO = new AWS_LoginDAO();

        response = loginDAO.login(request);

        return response;
    }
}
