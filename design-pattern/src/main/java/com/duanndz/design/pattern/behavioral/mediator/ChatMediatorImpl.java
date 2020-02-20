package com.duanndz.design.pattern.behavioral.mediator;

import java.util.ArrayList;
import java.util.List;

public class ChatMediatorImpl implements ChatMediator {

    private List<User> users;

    public ChatMediatorImpl() {
        this.users = new ArrayList<>();
    }


    @Override
    public void sendMessage(String msg, User user) {
        // message should not be received by the user sending it
        users.stream()
                .filter(user1 -> user1 != user)
                .forEach(user1 -> user1.receive(msg));
    }

    @Override
    public void addUser(User user) {
        this.users.add(user);
    }
}
