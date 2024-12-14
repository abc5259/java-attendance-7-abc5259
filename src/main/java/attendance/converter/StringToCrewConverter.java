package attendance.converter;

import attendance.domain.Crew;

public class StringToCrewConverter implements Converter<String, Crew> {
    @Override
    public Crew convert(String source) {
        return new Crew(source);
    }
}
