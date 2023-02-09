package pt.estg.es.model;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.DateSerializer;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateSerializer extends JsonSerializer<Date> {

    private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException {
        String formatedDate = format.format(date);
        jsonGenerator.writeString(formatedDate);
    }
}
