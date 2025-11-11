package Übung4;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MessageStore {
    private List<Message> messages = new ArrayList<>();

    public synchronized void addMessage(Message message) {
        messages.add(message);
    }

    public synchronized List<Message> getMessages(User user) {
        return messages.stream()
            .filter(m -> m.getReceiver().getUsername().equals(user.getUsername()))
            .collect(Collectors.toList());
    }
}
