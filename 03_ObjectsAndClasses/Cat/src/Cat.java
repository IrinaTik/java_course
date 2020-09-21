
public class Cat
{
    private static final double MIN_WEIGHT = 1000;
    private static final double MAX_WEIGHT = 9000;
    private static final int EYE_COUNT = 2;

    private double originWeight;
    private double weight;

    private double foodAmount;

    private boolean isAlive; //переключатель для уменьшения catCount
    private static int catCount = 0;

    public Cat()
    {
        weight = 1500.0 + 3000.0 * Math.random();
        originWeight = weight;
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
        return (!(weight < MIN_WEIGHT)) && (!(weight > MAX_WEIGHT));
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
        if(weight < MIN_WEIGHT) {
            declareDead();
            return "Dead";
        }
        else if(weight > MAX_WEIGHT) {
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