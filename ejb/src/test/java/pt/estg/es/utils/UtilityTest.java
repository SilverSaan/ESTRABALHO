package pt.estg.es.utils;

import org.junit.Test;
import pt.estg.es.util.StringsUtils;
import static org.junit.Assert.*;

public class UtilityTest {

    @Test
    public void testFirstLastName(){

        String firstLastNameView = StringsUtils.getFirstLastNameView("jonh test doe");
        assertEquals(firstLastNameView,"jonh doe");

        firstLastNameView = StringsUtils.getFirstLastNameView("jonh test sdasdasd doe");
        assertEquals(firstLastNameView,"jonh doe");

        firstLastNameView = StringsUtils.getFirstLastNameView("jonh     ");
        assertEquals(firstLastNameView,"jonh");

        firstLastNameView = StringsUtils.getFirstLastNameView("     ");
        assertEquals(firstLastNameView,StringsUtils.N_A);

        firstLastNameView = StringsUtils.getFirstLastNameView(null);
        assertEquals(firstLastNameView,StringsUtils.N_A);
    }
}
