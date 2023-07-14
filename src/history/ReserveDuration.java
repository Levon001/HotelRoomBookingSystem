package history;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;

public class ReserveDuration implements Serializable {
    private LocalDate startDate;
    private LocalDate endDate;

    public ReserveDuration(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private static boolean checkDate(LocalDate date) {
        LocalDate currentDate = LocalDate.now();
        return !date.isBefore(currentDate);
    }

    public static ReserveDuration checkingDate(LocalDate startDate, LocalDate endDate) throws DateTimeException {
        if (checkDate(startDate) && checkDate(endDate)) {
            return new ReserveDuration(startDate, endDate);
        }
        throw new DateTimeException("Date is before than current date");
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "ReserveDuration{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
