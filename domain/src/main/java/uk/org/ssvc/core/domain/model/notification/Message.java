package uk.org.ssvc.core.domain.model.notification;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Builder
@Getter
@ToString(of = { "type" })
public class Message {

    private final MessageType type;
    private final Map<String, String> variables = new HashMap<>();

    public Message(MessageType type) {
        this.type = type;
    }

    public Message(MessageType type, Map<String, String> variables) {
        this.type = type;
        this.variables.putAll(variables);
    }

    public Message withVariable(String name, String value) {
        Map<String, String> variables = new HashMap<>(this.variables);
        variables.put(name, value);

        return new Message(type, variables);
    }

}
