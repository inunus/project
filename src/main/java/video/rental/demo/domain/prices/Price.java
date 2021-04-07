package video.rental.demo.domain.prices;

public abstract class Price {
    public abstract int getPriceCode();

    public abstract double getCharge(int daysRented);

    public int getSavingPoint() {
        return 1;
    }
}
