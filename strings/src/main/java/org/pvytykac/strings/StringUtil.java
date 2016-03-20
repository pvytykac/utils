package org.pvytykac.strings;

/**
 * @author paly
 * @since 20/03/2016 04:03
 */
public class StringUtil {

    private StringUtil(){}

    /**
     * @param string to be tested
     * @return true if the tested string is null or empty after getting trimmed
     */
    public static boolean isEmpty(String string) {
        return string == null || string.trim().isEmpty();
    }

    /**
     * @param string to be tested
     * @return false if the tested string is null or empty after getting trimmed
     */
    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    /**
     * @see #isEmpty(String) for more info on which strings get ignored
     * @param strings vararg array of strings to be concatenated
     * @return concatenated string consisting of non-empty string arguments
     */
    public static String concat(String... strings) {
        return concat(null, strings);
    }

    /**
     * @see #isEmpty(String) for more info on which strings get ignored
     * @param delimiter to be appended between string parts
     * @param strings vararg array of strings to be concatenated
     * @return concatenated string consisting of non-empty string arguments delimited by provided delimiter
     */
    public static String concat(String delimiter, String... strings) {
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < strings.length; i++) {
            String part = strings[i];

            if(isNotEmpty(part)) {
                builder.append(part);

                if (isNotEmpty(delimiter) && i < strings.length - 1)
                    builder.append(delimiter);
            }
        }

        return builder.toString();
    }

    /**
     * @param string to be trimmed
     * @return trimmed string or null if the argument was null
     */
    public static String trim(String string) {
        return trim(string, false);
    }

    /**
     * @param string to be trimmed
     * @param nullToEmptyString if true, null string argument is converted to empty string
     * @return trimmed string or empty string if the argument was null
     */
    public static String trim(String string, boolean nullToEmptyString) {
        String trimmed;

        if(string != null)
            trimmed = string.trim();
        else
            trimmed = (nullToEmptyString)
                    ? ""
                    : null;

        return trimmed;
    }
}
