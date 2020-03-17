package rogvc.cs340.twitter.presenter.cache;

import rogvc.cs340.twitter.model.domain.User;
import rogvc.cs340.twitter.presenter.IPresenter;

public class Cache {

    private static Cache instance;

    public static Cache getInstance() {
        if (instance == null) {
            instance = new Cache();
        }

        return instance;
    }

    private Cache() {
        super();
    }

    private IPresenter presenter;

    public IPresenter getPresenter() {
        return presenter;
    }

    public void setPresenter(IPresenter presenter) {
        this.presenter = presenter;
    }

    private User activeUser;
    private User displayedUser;

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    public User getDisplayedUser() {
        return displayedUser;
    }

    public void setDisplayedUser(User displayedUser) {
        this.displayedUser = displayedUser;
    }

}
