package pt.estg.es.util;

public class StringsUtils {


    public static final String N_A = "Desconhecido N/A";

    /**
     *
     * @param firstLastName
     * @return true if firstLastName is null or "" or == N_A constant
     */
    public boolean isUnknown(String firstLastName)
    {
        return firstLastName == null || firstLastName.trim().length() == 0 || firstLastName.equals(N_A);
    }

    /**
     * Return first and last name or N_A constant
     * @param name to split
     * @return first and last name or N_A constant
     */
    public static String getFirstLastNameView(String name){
        if(name == null)
            return N_A;
        if(name.length() == 0)
            return N_A;
        if(name.trim().length() == 0)
            return N_A;


        String[] s = name.split(" ");

        if(s.length == 1)
            return s[0];
        else
            return s[0] + " " + s[s.length-1];

    }
}
