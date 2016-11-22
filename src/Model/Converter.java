
package Model;

import java.math.BigInteger;
import org.apache.commons.lang.StringUtils;

public class Converter
{
    public static int hexToInt(String hex)
    {
        return (int) Long.parseLong(hex, 16);
    }
    
    public static long hexToLong(String hex)
    {
        return new BigInteger(hex, 16).longValue();
    }
    
    public static byte hexToByte(String hex)
    {
        return (byte) Integer.parseInt(hex, 16);
    }
    
    public static String intToHex(int i, int digits)
    {
        String hex = Integer.toHexString(i);
        return (StringUtils.repeat("0", digits - hex.length()) + hex).toUpperCase();
    }
    
    public static String longToHex(long l, int digits)
    {
        String hex = Long.toHexString(l);
        return (StringUtils.repeat("0", digits - hex.length()) + hex).toUpperCase();
    }
    
    public static String byteToHex(byte b, int digits)
    {
        return String.format("%02X", b);
    }
    
    public static String intToBinary(int number, int digits)
    {
        String raw = Integer.toBinaryString(number);
        return StringUtils.repeat("0", digits - raw.length()) + raw;
    }
    
    public static String binaryToHex(String binaryCode, int digits)
    {
        BigInteger decimalOpcode = new BigInteger(binaryCode, 2);
        String hexOpcode = decimalOpcode.toString(16);
        return StringUtils.repeat("0", digits - hexOpcode.length()) + hexOpcode;
    }
}