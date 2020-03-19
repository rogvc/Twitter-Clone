package rogvc.cs340.twitter.dao.aws;

import java.util.Date;

import rogvc.cs340.twitter.dao.I_DAO_Login;
import rogvc.cs340.twitter.net.Proxy;
import rogvc.cs340.twitter.service.request.LoginRequest;
import rogvc.cs340.twitter.service.response.LoginResponse;

public class AWS_LoginDAO implements I_DAO_Login {

    @Override
    public LoginResponse login(LoginRequest request) {
        LoginResponse response = new LoginResponse();

        Proxy proxy = new Proxy();

        response.setUser(proxy.login(request.getHandle(), request.getPassword()));
        response.setAuthToken("HARD_CODED_BOY");
        response.setMessage("Logged in Successfully");
        response.setTimeStamp(new Date().toString());

        return response;
    }

}
