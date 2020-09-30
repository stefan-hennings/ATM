import java.util.UUID;

public class Person {
    private String name;
    private String personalId;
    private UUID customerId;
    
    public Person(String name, String personalId) {
        this.name = name;
        this.personalId = personalId;
        this.customerId = UUID.randomUUID();
    }
}
