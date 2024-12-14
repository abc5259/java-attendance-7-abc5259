package attendance.domain;

import java.util.Objects;

public class Crew {

    private final String name;

    public Crew(String name) {
        validateName(name);
        this.name = name.trim();
    }

    public void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름을 작성하세요.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Crew crew = (Crew) o;
        return Objects.equals(name, crew.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    public String getName() {
        return name;
    }
}
