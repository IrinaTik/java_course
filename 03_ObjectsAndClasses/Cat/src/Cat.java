
public class Cat
{
    private double originWeight;
    private double weight;

    private double minWeight;
    private double maxWeight;

    private double foodAmount;

    private boolean isAlive; //переключатель для уменьшения catCount
    private static int catCount = 0;

    public Cat()
    {
        weight = 1500.0 + 3000.0 * Math.random();
        originWeight = weight;
        minWeight = 1000.0;
        maxWeight = 9000.0;
        foodAmount = 0;
        isAlive = true;
        catCount++;
    }

    public void pee() {
        if (!declareDead()) {
            weight = weight - 1;
            System.out.println("Мама-мия! Итальяно писсуаро!");
        }
    }

    public void meow()
    {
        if (!declareDead()) {
            weight = weight - 1;
            System.out.println("Meow");
        }
    }

    public void feed(Double amount)
    {
        if (!declareDead()) {
            weight = weight + amount;
            foodAmount = foodAmount + amount;
        }
    }

    public void drink(Double amount)
    {
        if (!declareDead()) {
            weight = weight + amount;
        }
    }

    private boolean isAlive() {
        return (!(weight < minWeight)) && (!(weight > maxWeight));
    }
    
    private boolean declareDead() {
        if (!isAlive()) {
            System.out.println("This cat is dead, Jim!");
            // если еще не проверяли, жив кот или нет
            if (isAlive) {
                isAlive = false;
                catCount--;
            }
            return true;
        } else {
            return false;
        }
    }

    public Double getWeight()
    {
        return weight;
    }

    public double getFoodAmount() {
        return foodAmount;
    }

    public String getStatus()
    {
        if(weight < minWeight) {
            declareDead();
            return "Dead";
        }
        else if(weight > maxWeight) {
            declareDead();
            return "Exploded";
        }
        else if(weight > originWeight) {
            return "Sleeping";
        }
        else {
            return "Playing";
        }
    }

    public static int getCatCount() {
        return catCount;
    }
}