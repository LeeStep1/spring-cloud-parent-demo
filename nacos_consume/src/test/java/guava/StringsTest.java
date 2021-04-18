package guava;



import com.google.common.base.Strings;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class StringsTest {

    @Test
    public void testStringsMethod(){
        assertThat(Strings.emptyToNull(""),nullValue());
        assertThat(Strings.nullToEmpty(null),equalTo(""));
        assertThat(Strings.nullToEmpty("hello"),equalTo("hello"));
        assertThat(Strings.commonPrefix("hello","hi"),equalTo("h"));
        assertThat(Strings.commonPrefix("hello","word"),equalTo(""));
        assertThat(Strings.commonSuffix("hello","who"),equalTo("o"));
        assertThat(Strings.repeat("hello",3),equalTo("hellohellohello"));
        assertThat(Strings.isNullOrEmpty(null),equalTo(true));
        assertThat(Strings.isNullOrEmpty(""),equalTo(true));

        assertThat(Strings.padStart("hello",6,'H'),equalTo("Hhello"));
        assertThat(Strings.padEnd("hello",6,'H'),equalTo("helloH"));
    }
}
