package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TripServiceTest {

    private TripServiceMock tripServiceMock;

    @Test
    void should_throw_exception_when_no_user_logged() {
        tripServiceMock = new TripServiceMock(null);
        assertThrows(UserNotLoggedInException.class, () -> tripServiceMock.getTripsByUser(new User()));
    }

    @Test
    void should_return_empty_tripList_when_logged_user_has_no_friend() {
        tripServiceMock = new TripServiceMock(new User());
        List<Trip> tripList = tripServiceMock.getTripsByUser(new User());
        assertEquals(0, tripList.size());
    }

    @Test
    void should_return_empty_tripList_when_logged_user_has_friend_but_not_my_friend() {
        tripServiceMock = new TripServiceMock(new User());

        User user = new User();
        user.addFriend(new User());

        List<Trip> tripList = tripServiceMock.getTripsByUser(user);
        assertEquals(0, tripList.size());
    }

    @Test
    void should_return_empty_tripList_when_logged_user_has_friend_and_my_friend() {
        User me = new User();
        tripServiceMock = new TripServiceMock(me);

        User user = new User();
        user.addFriend(me);

        List<Trip> tripList = tripServiceMock.getTripsByUser(user);
        assertEquals(2, tripList.size());
    }

    class TripServiceMock extends TripService {
        private User user;

        public TripServiceMock(User user) {
            this.user = user;
        }

        @Override
        protected User getLoggedUser() {
            return this.user;
        }

        @Override
        protected List<Trip> getTripListByUser(User user) {
            return Arrays.asList(new Trip(), new Trip());
        }
    }

}


