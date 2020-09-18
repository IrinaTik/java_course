
public class Loader
{
    public static void main(String[] args)
    {
        Cat cat1 = new Cat();
        Cat cat2 = new Cat();
        Cat cat3 = new Cat();
        Cat cat4 = new Cat();
        Cat cat5 = new Cat();

        System.out.println("Вес первой кошки: " + cat1.getWeight());
        System.out.println("Вес второй кошки: " + cat2.getWeight());

        System.out.println("Покормим первую кошку (10).");
        cat1.feed(10.0);
        System.out.println("Вторая кошка прожорливей, но покормим и ее (20).");
        cat2.feed(20.0);

        System.out.println("Вес первой кошки после кормления: " + cat1.getWeight());
        System.out.println("Вес второй кошки после кормления: " + cat2.getWeight());

        System.out.println("Вес третьей кошки: " + cat3.getWeight() + ". Закормим же ее до умопомрачения!");
        while (!cat3.getStatus().equals("Exploded")) {
            cat3.feed(10.0);
            cat3.drink(5.5); // чтобы бедная кошка не давилась всухомятку =)
        }
        System.out.println("Третья кошка благополучно взорвалась. Вес в момент взрыва: " + cat3.getWeight());

        System.out.println("Вес четвертой кошки: " + cat4.getWeight() + ". Замяукаем же ее до смерти! МУА-ХА-ХА!");
        int i = 0; // ну интересно же, сколько надо "мяу", чтобы кошка сдохла
        while (!cat4.getStatus().equals("Dead")) {
            i++;
            cat4.meow();
        }
        System.out.println("Четвертая кошка благополучно взорвалась за " + i + " мяу. Вес в момент взрыва: " + cat4.getWeight());
    }
}