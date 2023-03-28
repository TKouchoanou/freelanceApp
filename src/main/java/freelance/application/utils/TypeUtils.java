package freelance.application.utils;

public class TypeUtils {
    // must be moved in common odule
    public static  boolean hasValue(Long v) {
        return v!=null && v!=0;
    }
    public static boolean hasValue(String v) {
        return v!=null && !v.isBlank();
    }
}
