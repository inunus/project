package video.rental.demo.domain.prices;

import video.rental.demo.domain.Video;

public class RegularPrice extends Price {
    @Override
    public int getPriceCode() {
        return Video.REGULAR;
    }

    @Override
    public double getCharge(int daysRented) {
        double amount = 2;
        if (daysRented > 2)
            amount += (daysRented - 2) * 1.5;

        return amount;
    }
}
