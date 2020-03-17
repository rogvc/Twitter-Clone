package rogvc.cs340.twitter.dao;

import rogvc.cs340.twitter.service.response.FollowResponse;

public interface I_DAO_Follow {
    FollowResponse follow(String userFollowing, String userBeingFollowed);
}
