package Übung4;

import java.util.ArrayList;
import java.util.List;

public class UserStore {
    private List<User> users = new ArrayList<>();

    public synchronized void addUser(String username) {
        users.add(new User(username));
    }

    public synchronized User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
