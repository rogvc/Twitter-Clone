package rogvc.cs340.twitter.dao.aws;

import rogvc.cs340.twitter.dao.I_DAO_Follow;
import rogvc.cs340.twitter.service.response.FollowResponse;

public class AWS_FollowDAO implements I_DAO_Follow {

    @Override
    public FollowResponse follow(String userFollowing, String userBeingFollowed) {
        FollowResponse response = null;
        //TODO: Follow lambda stuff here.
            //Request follow from Lambda
            //Receive Response with boolean status.
        //return the response.
        return response;
    }
}
