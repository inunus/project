package video.rental.demo.domain.prices;

import video.rental.demo.domain.Video;

public class ChildrenPrice extends Price {
    @Override
    public int getPriceCode() {
        return Video.CHILDREN;
    }

    @Override
    public double getCharge(int daysRented) {
        double amount = 1.5;
        if (daysRented > 3)
            amount += (daysRented - 3) * 1.5;

        return amount;
    }
}
