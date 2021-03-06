
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

        System.out.println("Покормим первую кошку (100 г).");
        cat1.feed(100.0);
        System.out.println("Вторая кошка прожорливей, но покормим и ее (200 г).");
        cat2.feed(200.0);

        System.out.println("Вес первой кошки после кормления: " + cat1.getWeight());
        System.out.println("Вес второй кошки после кормления: " + cat2.getWeight());

        System.out.println("Вес третьей кошки: " + cat3.getWeight() + ". Закормим же ее до умопомрачения!");
        while (!cat3.getStatus().equals("Exploded")) {
            cat3.feed(100.0);
            cat3.drink(50.5); // чтобы бедная кошка не давилась всухомятку =)
        }
        System.out.println("Третья кошка благополучно взорвалась. Вес в момент взрыва: " + cat3.getWeight());

        System.out.println("Вес четвертой кошки: " + cat4.getWeight() + ". Замяукаем же ее до смерти! МУА-ХА-ХА!");
        int i = 0; // ну интересно же, сколько надо "мяу", чтобы кошка сдохла
        while (!cat4.getStatus().equals("Dead")) {
            i++;
            cat4.meow();
        }
        System.out.println("Четвертая кошка скоропостижно умерла за " + i + " мяу. Вес в момент смерти: " + cat4.getWeight());

        System.out.println("Пятая кошка чувствует себя забытой и заедает горе кормом.");
        cat5.feed(150.0);
        System.out.println("Ну а еще она писает во все хозяйские тапки.");
        for (int j = 0; j < 5; j++) {
            cat5.pee();
        }
        System.out.println("Пятая кошка съела " + cat5.getFoodAmount() + " г корма.");

        System.out.println();
        System.out.println("Кошек осталось на трубе: " + Cat.getCatCount());

        System.out.println();
        System.out.println("Запущена машина клонирования кошек...");
        Cat cat6 = getKitten();
        System.out.println("Материализовалась " + cat6.toString());
        Cat cat7 = getKitten();
        System.out.println("Материализовалась " + cat7.toString());
        Cat cat8 = getKitten();
        System.out.println("Материализовалась " + cat8.toString());
        System.out.println("Кошек осталось на трубе: " + Cat.getCatCount());

        System.out.println(new Cat(999.));
        System.out.println("Кошек осталось на трубе: " + Cat.getCatCount());

        catTest();
    }

    private static Cat getKitten() {
        return new Cat(1100.00);
    }

    private static void catTest() {
        Cat cat = new Cat();
        System.out.println("Статус кота: " + cat.getStatus());
        System.out.println("Количество котов: " + Cat.getCatCount());
        while (!cat.getStatus().equals("Exploded")) {
            cat.feed(1000.);
        }
        System.out.println("Статус кота: " + cat.getStatus());
        System.out.println("Количество котов: " + Cat.getCatCount());
    }
}