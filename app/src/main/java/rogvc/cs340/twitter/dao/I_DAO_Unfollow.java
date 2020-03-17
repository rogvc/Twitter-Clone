package rogvc.cs340.twitter.dao;

import rogvc.cs340.twitter.service.response.UnfollowResponse;

public interface I_DAO_Unfollow {
    UnfollowResponse unfollow(String userUnfollowing, String userBeingUnfollowed);
}
