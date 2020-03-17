package rogvc.cs340.twitter.presenter;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import rogvc.cs340.twitter.model.domain.Post;
import rogvc.cs340.twitter.model.domain.User;
import rogvc.cs340.twitter.presenter.exception.EmptyFieldException;
import rogvc.cs340.twitter.presenter.exception.IncorrectCredentialsException;
import rogvc.cs340.twitter.presenter.exception.ServerErrorException;
import rogvc.cs340.twitter.presenter.exception.UserNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class PresenterTests {

    Presenter presenter;

    User testUser1 = new User("@yolo", "yolo123",
            "Yolo", "pants", "none.png");
    User testUser2 = new User("@wololo", "yolo123",
            "Yahoo", "Duder", "none.png");
    User testUser3 = new User("", "", "", "", "");

    Post testPost1 = new Post(testUser1, "Hello there!",
            new Date().toString(), 0, "");


    @Before
    public void setUp() {
        presenter = new Presenter();
    }

    @Test
    public void testCanAddUser() {
        boolean test = false;
        try {

            test = presenter.onRegisterSubmit(testUser1.getHandle(), testUser1.getPassword(),
                    testUser1.getFirstName(), testUser1.getLastName(),
                    testUser1.getImageUrl(), null);

        } catch (EmptyFieldException e) {
            fail("Fields were empty.");
        } catch (IncorrectCredentialsException e) {
            fail("User Already in Database.");
        }

        assertTrue(test);
    }

    @Test
    public void testAddsCorrectActiveUser() {
        boolean test = false;
        try {

            test = presenter.onRegisterSubmit(testUser1.getHandle(), testUser1.getPassword(),
                    testUser1.getFirstName(), testUser1.getLastName(),
                    testUser1.getImageUrl(), null);

        } catch (EmptyFieldException e) {
            fail("Fields were empty.");
        } catch (IncorrectCredentialsException e) {
            fail("User Already in Database.");
        }

        assertTrue(test);
        assertEquals(testUser1, presenter.getActiveUser());
    }

    @Test
    public void testCannotAddSameUserTwice() {
        boolean test = false;
        try {

            test = presenter.onRegisterSubmit(testUser1.getHandle(), testUser1.getPassword(),
                    testUser1.getFirstName(), testUser1.getLastName(),
                    testUser1.getImageUrl(), null);

            test = presenter.onRegisterSubmit(testUser1.getHandle(), testUser1.getPassword(),
                    testUser1.getFirstName(), testUser1.getLastName(),
                    testUser1.getImageUrl(), null);

        } catch (EmptyFieldException e) {
            fail("Fields were empty.");
            test = false;
        } catch (IncorrectCredentialsException e) {
            test = false;
        }

        assertFalse(test);
        assertEquals(testUser1, presenter.getActiveUser());
    }

    @Test
    public void testCannotAddBlankUser() {
        boolean test = false;
        try {

            test = presenter.onRegisterSubmit(testUser3.getHandle(), testUser3.getPassword(),
                    testUser3.getFirstName(), testUser3.getLastName(),
                    testUser3.getImageUrl(), null);

        } catch (EmptyFieldException e) {
            test = false;
        } catch (IncorrectCredentialsException e) {
            fail("Should throw EmptyFieldException instead.");
            test = false;
        }

        assertFalse(test);
    }

    @Test
    public void testCanSearchForUser() {
        boolean test = false;
        try {

            presenter.onRegisterSubmit(testUser2.getHandle(), testUser2.getPassword(),
                    testUser2.getFirstName(), testUser2.getLastName(),
                    testUser2.getImageUrl(), null);

            test = presenter.onSearch(testUser2.getHandle());

        } catch (EmptyFieldException e) {
            fail("Should not be empty.");
            test = false;
        } catch (IncorrectCredentialsException e) {
            fail("Should throw EmptyFieldException instead.");
            test = false;
        } catch (UserNotFoundException e) {
            fail("Should find user.");
            test = false;
        }

        assertTrue(test);
    }

    @Test
    public void testSearchesForCorrectUser() {
        boolean test = false;
        User testUser = null;
        try {

            presenter.onRegisterSubmit(testUser2.getHandle(), testUser2.getPassword(),
                    testUser2.getFirstName(), testUser2.getLastName(),
                    testUser2.getImageUrl(), null);

            test = presenter.onSearch(testUser2.getHandle());

            testUser = presenter.getDisplayedUser();

        } catch (EmptyFieldException e) {
            fail("Should not be empty.");
            test = false;
        } catch (IncorrectCredentialsException e) {
            fail("Should throw EmptyFieldException instead.");
            test = false;
        } catch (UserNotFoundException e) {
            fail("Should find user.");
            test = false;
        }

        assertTrue(test);
        assertEquals(testUser2, testUser);
    }

    @Test
    public void testCanPost() {
        boolean test = false;
        try {

            presenter.onRegisterSubmit(testUser1.getHandle(), testUser1.getPassword(),
                    testUser1.getFirstName(), testUser1.getLastName(),
                    testUser1.getImageUrl(), null);

            test = presenter.onPost(testPost1.getUser().getHandle(), testPost1.getBody(),
                    testPost1.getMediaURL(), "", null);

        } catch (EmptyFieldException e) {
            fail("Should not be empty.");
            test = false;
        } catch (IncorrectCredentialsException e) {
            fail("Should throw EmptyFieldException instead.");
            test = false;
        } catch (ServerErrorException e) {
            fail("Should not throw Server Exception.");
            test = false;
        }

        assertTrue(test);
    }

    @Test
    public void testCorrectPostIsAdded() {
        boolean test = false;
        Post temp = null;
        try {

            presenter.onRegisterSubmit(testUser1.getHandle(), testUser1.getPassword(),
                    testUser1.getFirstName(), testUser1.getLastName(),
                    testUser1.getImageUrl(), null);

            test = presenter.onPost(testPost1.getUser().getHandle(), testPost1.getBody(),
                    testPost1.getMediaURL(), "", null);

            temp = presenter.getActiveUser().getStory().get(0);

        } catch (EmptyFieldException e) {
            fail("Should not be empty.");
            test = false;
        } catch (IncorrectCredentialsException e) {
            fail("Should throw EmptyFieldException instead.");
            test = false;
        } catch (ServerErrorException e) {
            fail("Should not throw Server Exception.");
            test = false;
        }

        assertTrue(test);
        assertTrue(temp.equals(testPost1));
    }

    @Test
    public void testCanPostMoreThanOnce() {
        boolean test = false;
        int testSize = 0;
        try {

            presenter.onRegisterSubmit(testUser1.getHandle(), testUser1.getPassword(),
                    testUser1.getFirstName(), testUser1.getLastName(),
                    testUser1.getImageUrl(), null);

            test = presenter.onPost(testPost1.getUser().getHandle(), testPost1.getBody(),
                    testPost1.getMediaURL(), "", null);

            test = presenter.onPost(testPost1.getUser().getHandle(), "Random!",
                    testPost1.getMediaURL(), "", null);

            test = presenter.onPost(testPost1.getUser().getHandle(), "Random Video!",
                    testPost1.getMediaURL(), "Should/have/video", null);

            testSize = presenter.getActiveUser().getStory().size();

        } catch (EmptyFieldException e) {
            fail("Should not be empty.");
            test = false;
        } catch (IncorrectCredentialsException e) {
            fail("Should throw EmptyFieldException instead.");
            test = false;
        } catch (ServerErrorException e) {
            fail("Should not throw Server Exception.");
            test = false;
        }

        assertTrue(test);
        assertEquals(testSize, 3);
    }

    @Test
    public void testMentionIsAdded() {
        boolean test = false;
        int testSize = 0;
        try {

            presenter.onRegisterSubmit(testUser1.getHandle(), testUser1.getPassword(),
                    testUser1.getFirstName(), testUser1.getLastName(),
                    testUser1.getImageUrl(), null);

            test = presenter.onPost(testPost1.getUser().getHandle(), "this post should have 1 mention - @admin",
                    testPost1.getMediaURL(), "", null);

            testSize = presenter.getActiveUser().getStory().get(0).getMentions().size();

        } catch (EmptyFieldException e) {
            fail("Should not be empty.");
            test = false;
        } catch (IncorrectCredentialsException e) {
            fail("Should throw EmptyFieldException instead.");
            test = false;
        } catch (ServerErrorException e) {
            fail("Should not throw Server Exception.");
            test = false;
        }

        assertTrue(test);
        assertEquals(testSize, 1);
    }

    @Test
    public void testHyperlinkIsAdded() {
        boolean test = false;
        int testSize = 0;
        try {

            presenter.onRegisterSubmit(testUser1.getHandle(), testUser1.getPassword(),
                    testUser1.getFirstName(), testUser1.getLastName(),
                    testUser1.getImageUrl(), null);

            test = presenter.onPost(testPost1.getUser().getHandle(),
                    "this post should have 1 Hyperlink - http://google.com",
                    testPost1.getMediaURL(), "", null);

            testSize = presenter.getActiveUser().getStory().get(0).getHyperlinks().size();

        } catch (EmptyFieldException e) {
            fail("Should not be empty.");
            test = false;
        } catch (IncorrectCredentialsException e) {
            fail("Should throw EmptyFieldException instead.");
            test = false;
        } catch (ServerErrorException e) {
            fail("Should not throw Server Exception.");
            test = false;
        }

        assertTrue(test);
        assertEquals(testSize, 1);
    }

}