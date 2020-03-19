package rogvc.cs340.twitter.lambda;

import rogvc.cs340.twitter.service.LoginService;
import rogvc.cs340.twitter.service.request.FollowRequest;
import rogvc.cs340.twitter.service.request.LoginRequest;
import rogvc.cs340.twitter.service.request.RegisterRequest;
import rogvc.cs340.twitter.service.request.RetrieveUserRequest;
import rogvc.cs340.twitter.service.request.UnfollowRequest;
import rogvc.cs340.twitter.service.response.FollowResponse;
import rogvc.cs340.twitter.service.response.LoginResponse;
import rogvc.cs340.twitter.service.response.RetrieveUserResponse;
import rogvc.cs340.twitter.service.response.UnfollowResponse;

public class AWS_Handler implements BackendHandler {
    @Override
    public LoginResponse login(LoginRequest request) {
        return LoginService.login(request);
    }

    @Override
    public LoginResponse register(RegisterRequest request) {
        return null;
    }

    @Override
    public RetrieveUserResponse retrieveUser(RetrieveUserRequest request) {
        return null;
    }

    @Override
    public FollowResponse follow(FollowRequest request) {
        return null;
    }

    @Override
    public UnfollowResponse unfollow(UnfollowRequest request) {
        return null;
    }
}
