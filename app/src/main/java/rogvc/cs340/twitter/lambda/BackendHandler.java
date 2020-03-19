package rogvc.cs340.twitter.lambda;

import rogvc.cs340.twitter.service.request.FollowRequest;
import rogvc.cs340.twitter.service.request.LoginRequest;
import rogvc.cs340.twitter.service.request.RegisterRequest;
import rogvc.cs340.twitter.service.request.RetrieveUserRequest;
import rogvc.cs340.twitter.service.request.UnfollowRequest;
import rogvc.cs340.twitter.service.response.FollowResponse;
import rogvc.cs340.twitter.service.response.LoginResponse;
import rogvc.cs340.twitter.service.response.RetrieveUserResponse;
import rogvc.cs340.twitter.service.response.UnfollowResponse;

public interface BackendHandler {
    LoginResponse login(LoginRequest request);

    LoginResponse register(RegisterRequest request);

    RetrieveUserResponse retrieveUser(RetrieveUserRequest request);

    FollowResponse follow(FollowRequest request);

    UnfollowResponse unfollow(UnfollowRequest request);
}
