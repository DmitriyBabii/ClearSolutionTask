package ua.clearsolutions.test.services;

import ua.clearsolutions.test.models.entities.User;

import java.util.Comparator;

public class UserComparator implements Comparator<User> {
    @Override
    public int compare(User u1, User u2) {
        return u1.getEmail().compareTo(u2.getEmail());
    }
}
