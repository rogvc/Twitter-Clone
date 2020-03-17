package rogvc.cs340.twitter.dao;

import rogvc.cs340.twitter.service.request.RegisterRequest;
import rogvc.cs340.twitter.service.response.LoginResponse;

public class RegisterDAO implements I_DAO_Register{

    @Override
    public LoginResponse register(RegisterRequest request) {
        LoginResponse response = null;
        //TODO: Register lambda stuff here.
            //Request register from Lambda
            //Receive Response with User and Active Token.
        //return the response.
        return response;
    }
}
