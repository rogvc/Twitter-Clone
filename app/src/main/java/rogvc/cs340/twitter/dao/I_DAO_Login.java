package rogvc.cs340.twitter.dao;

import rogvc.cs340.twitter.service.request.LoginRequest;
import rogvc.cs340.twitter.service.response.LoginResponse;

public interface I_DAO_Login {
    LoginResponse login(LoginRequest request);
}
