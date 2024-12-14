package attendance.domain;

public record Time(
        int hour,
        int minute
) {
    public boolean isBefore(Time time) {
        if (this.hour < time.hour) {
            return true;
        }

        if (this.hour == time.hour && this.minute < time.minute) {
            return true;
        }

        return false;
    }

    public boolean isEqualAndAfter(Time time) {
        if (this.hour > time.hour) {
            return true;
        }

        if (this.hour == time.hour && this.minute >= time.minute) {
            return true;
        }

        return false;
    }
}
