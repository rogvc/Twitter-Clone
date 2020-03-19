package rogvc.cs340.twitter.dao.aws;

import rogvc.cs340.twitter.dao.I_DAO_Unfollow;
import rogvc.cs340.twitter.service.response.UnfollowResponse;

public class AWS_UnfollowDAO implements I_DAO_Unfollow {
    @Override
    public UnfollowResponse unfollow(String userUnfollowing, String userBeingUnfollowed) {
        UnfollowResponse response = null;
        //TODO: Unfollow lambda stuff here.
            //Request follow from Lambda
            //Receive Response with boolean status.
        //return the response.
        return response;
    }
}
