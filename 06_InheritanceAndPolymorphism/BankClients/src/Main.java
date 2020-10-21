public class Main {

    public static void main(String[] args) {
        Client natural = new NaturalPerson(1500);
        clientOperations(natural);

        Client legal = new LegalPerson(1500);
        clientOperations(legal);

        Client selfEmployed = new SelfEmployedPerson(1500);
        clientOperations(selfEmployed);
    }

    private static void clientOperations(Client client) {
        System.out.println("=================================");
        client.deposit(1500);
        client.withdraw(250);
        client.info();
        client.deposit(100);
        client.withdraw(100);
        client.info();
    }

}
