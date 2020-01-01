package fpt.com.virtualoutfitroom.utils;

public class RegexHelper {
    // allow unicode with space between word
    public static final String REGEXUNICODE ="^\\p{L}+( \\p{L}+)*$";
    public static final String REGEXUSERNAME ="^[a-zA-Z0-9]*$";
    public static final String REGEXEMAIL = "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    public static final String REGEXPHONENUMBER = "^[0-9]{10}";
    public static boolean checkSpecChar(String param){
        if(param.matches(REGEXUNICODE))
            return true;
        return false;
    }
    public static boolean checkUserName(String param){
        if(param.matches(REGEXUSERNAME))
            return true;
        return false;
    }
    public static boolean checkEmail(String param){
        if(param.matches(REGEXEMAIL))
            return true;
        return false;
    }
    public static boolean checkPhoneNumber(String param){
        if(param.matches(REGEXPHONENUMBER))
            return true;
        return false;
    }
}
