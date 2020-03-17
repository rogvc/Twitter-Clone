package rogvc.cs340.twitter.dao;

import rogvc.cs340.twitter.service.request.RegisterRequest;
import rogvc.cs340.twitter.service.response.LoginResponse;

public interface I_DAO_Register {
    LoginResponse register(RegisterRequest request);
}
