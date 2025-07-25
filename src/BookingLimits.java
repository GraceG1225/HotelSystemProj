import java.time.LocalDate;

public class BookingLimits {
    
    private static final int MAX_ADV_MONTHS = 18;
    private static final int MAX_ADULTS = 15;
    private static final int MAX_CHILDREN = 10;
    private static final int MAX_ROOMS = 10;

    public String validateDates(LocalDate checkIn, LocalDate checkOut) {
        LocalDate today = LocalDate.now();

        // user cannot proceed without both preferred dates selected
        if (checkIn == null || checkOut == null) {
            return "Please select both a check-in and check-out date.";
        }

        if (checkIn.isBefore(today)) {
            return "Check-in date cannot be before today.";
        }

        // enforcing that check-out date MUST be after check-in date
        if (!checkOut.isAfter(checkIn)) {
            return "Check-out date must be after check-in date.";
        }

        if (checkIn.isAfter(today.plusMonths(MAX_ADV_MONTHS)) ||
            checkOut.isAfter(today.plusMonths(MAX_ADV_MONTHS))) {
            return "Room reservations can only be made up to 18 months in advance.";
        }

        return "";
    }

    public boolean validateGuests(int adults, int children, int rooms) {
        if (adults < 1 || adults > MAX_ADULTS) {
            return false;
        }
        
        if (children < 0 || children > MAX_CHILDREN) {
            return false;
        }

        if (rooms < 1 || rooms > MAX_ROOMS) {
            return false;
        }

        return true;
    }

    public String getRoomsWarning(int rooms) {
        if (rooms > MAX_ROOMS) {
            return "For reservations of more than 10 rooms, we ask "
                + "that you please dial our front desk to make your reservation. "
                + "The number is 1-800-343-2938.";
        }
        return "";
    }
}
