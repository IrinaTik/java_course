import Exceptions.NameFormatException;

import java.util.HashMap;

public class CustomerStorage
{
    private HashMap<String, Customer> storage;

    public CustomerStorage()
    {
        storage = new HashMap<>();
    }

    public void addCustomer(String data)
    {
        String[] components = data.split("\\s+");
        String name = components[0] + " " + components[1];
        storage.put(name, new Customer(name, components[3], components[2]));
    }

    public void listCustomers()
    {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name) throws NameFormatException
    {
        String[] fullName = name.split("\\s+");
        if (fullName.length != 2) {
            throw new NameFormatException("Wrong name format! Please provide first name and last name.");
        }
        storage.remove(name);
    }

    public int getCount()
    {
        return storage.size();
    }
}