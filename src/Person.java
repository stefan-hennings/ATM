import java.util.UUID;

public class Person {
    private String name;
    private final String personalId;
    private final UUID customerId;
    
    public Person(String name, String personalId) {
        this.name = name;
        this.personalId = personalId;
        this.customerId = UUID.randomUUID();
    }
}
