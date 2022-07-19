package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TripServiceTest {

    private TripService tripService;

    @Mock
    private UserSession userSession;

    @BeforeEach
    void setUp() {
        tripService = new TripService(userSession);
    }

    @Test
    void should_throw_exception_when_no_user_logged() {
        when(userSession.getLoggedUser()).thenReturn(null);
        assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(new User()));
    }

    @Test
    void should_return_empty_tripList_when_logged_user_has_no_friend() {
        when(userSession.getLoggedUser()).thenReturn(new User());

        List<Trip> tripList = tripService.getTripsByUser(new User());
        assertEquals(0, tripList.size());
    }

    @Test
    void should_return_empty_tripList_when_logged_user_has_friend_but_not_my_friend() {
        when(userSession.getLoggedUser()).thenReturn(new User());

        User user = new User();
        user.addFriend(new User());

        List<Trip> tripList = tripService.getTripsByUser(user);
        assertEquals(0, tripList.size());
    }

    @Test
    void should_return_empty_tripList_when_logged_user_has_friend_and_my_friend() {
        User me = new User();
        when(userSession.getLoggedUser()).thenReturn(me);

        User user = new User();
        user.addFriend(me);

        List<Trip> tripList = tripService.getTripsByUser(user);
        assertEquals(2, tripList.size());
    }
}
