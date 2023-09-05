package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConverterTest {
    @Test
    void stringToInteger() {
        var converter = new StringToIntegerConverter();
        var result = converter.convert("10");
        assertThat(result).isEqualTo(10);
    }

    @Test
    void integerToString() {
        var converter = new IntegerToStringConverter();
        var result = converter.convert(10);
        assertThat(result).isEqualTo("10");
    }

    @Test
    void stringToIpPort() {
        var converter = new StringToIpPortConverter();
        var source = "127.0.0.1:8080";
        var result = converter.convert(source);
        assertThat(result).isEqualTo(new IpPort("127.0.0.1", 8080));
    }

    @Test
    void ipPortToString() {
        var converter = new IpPortToStringConverter();
        var source = new IpPort("127.0.0.1", 8080);
        var result = converter.convert(source);
        assertThat(result).isEqualTo("127.0.0.1:8080");
    }
}
