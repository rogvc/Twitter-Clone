package rogvc.cs340.twitter.dao;

import rogvc.cs340.twitter.service.response.RetrieveUserResponse;

public interface I_DAO_RetrieveUser {
    RetrieveUserResponse retrieveUser(String handle);
}
