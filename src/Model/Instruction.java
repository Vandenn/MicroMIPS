
package Model;

import java.math.BigInteger;
import java.util.HashMap;
import org.apache.commons.lang.StringUtils;

public enum Instruction
{
    OR("^or r[0-9]{1,2},[ ]{0,1}r[0-9]{1,2},[ ]{0,1}r[0-9]{1,2}$"), 
    DSUB("^dsub r[0-9]{1,2},[ ]{0,1}r[0-9]{1,2},[ ]{0,1}r[0-9]{1,2}$"), 
    SLT("^slt r[0-9]{1,2},[ ]{0,1}r[0-9]{1,2},[ ]{0,1}r[0-9]{1,2}$"), 
    NOP("^nop$"),
    BNE("^bne r[0-9]{1,2},[ ]{0,1}r[0-9]{1,2},[ ]{0,1}(0x[0-9a-f]{4}|[a-z0-9_]+)$"),
    LD("^ld r[0-9]{1,2},[ ]{0,1}[0-9a-f]{4}\\(r[0-9]{1,2}\\)$"),
    SD("^sd r[0-9]{1,2},[ ]{0,1}[0-9a-f]{4}\\(r[0-9]{1,2}\\)$"),
    DADDIU("^daddiu r[0-9]{1,2},[ ]{0,1}r[0-9]{1,2},[ ]{0,1}0x[0-9a-f]{4}$"),
    J("^j [a-z0-9_]+$")
    ;
    
    private final String stringValue;
    private Instruction(final String s) { stringValue = s; }
    public String getRegex() { return stringValue; }
    public String toString()
    {
        switch(this)
        {
            case OR: return "or";
            case DSUB: return "dsub";
            case SLT: return "slt";
            case NOP: return "nop";
            case BNE: return "bne";
            case LD: return "ld";
            case SD: return "sd";
            case DADDIU: return "daddiu";
            case J: return "j";
            default: return "";
        }
    }
    public String generateOpcode(int codeLine, String code, HashMap<String, Integer> labels)
    {
        String opcode = "";
        
        switch(this)
        {
            case OR:
            case DSUB:
            case SLT:
                opcode = generateROpcode(code);
                break;
            case NOP:
                opcode = "00000000";
                break;
            case BNE:
                opcode = generateBNEOpcode(codeLine, code, labels);
                break;
            case LD:
            case SD:
                opcode = generateLoadStoreOpcode(codeLine, code, labels);
                break;
            case DADDIU:
                opcode = generateDADDIUOpcode(codeLine, code, labels);
                break;
            case J:
                opcode = generateJOpcode(codeLine, code, labels);
                break;
        }
        
        return opcode;
    }
    
    private String generateROpcode(String code)
    {
        String[] registers = getDataPart(code).split(",");
        
        int firstRegister = Integer.parseInt(registers[0].substring(1));
        int secondRegister = Integer.parseInt(registers[1].substring(1));
        int thirdRegister = Integer.parseInt(registers[2].substring(1));
        
        if (firstRegister > 31 || secondRegister > 31 || thirdRegister > 31) return "";
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("000000");
        sb.append(generateBinaryRepresentation(secondRegister, 5));
        sb.append(generateBinaryRepresentation(thirdRegister, 5));
        sb.append(generateBinaryRepresentation(firstRegister, 5));
        sb.append("00000");
        switch (this)
        {
            case OR:
                sb.append("100101");
                break;
            case DSUB:
                sb.append("101111");
                break;
            case SLT:
                sb.append("101010");
                break;
        }
        
        return generateFinalHexRepresentation(sb.toString());
    }
    
    private String generateBNEOpcode(int codeLine, String code, HashMap<String, Integer> labels)
    {
        String hexRegex = "^0x[0-9a-f]{4}$";
        String[] data = getDataPart(code).split(",");
        
        int firstRegister = Integer.parseInt(data[0].substring(1));
        int secondRegister = Integer.parseInt(data[1].substring(1));
        int immediate = 0;
        
        if (data[2].matches(hexRegex))
        {
            immediate = Integer.parseInt(data[2].substring(2), 16);
        }
        else
        {
            if (!labels.containsKey(data[2])) return "";
            immediate = labels.get(data[2]) - (codeLine + 0x0004);
        }
        
        if (firstRegister > 31 || secondRegister > 31) return "";
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("000101");
        sb.append(generateBinaryRepresentation(firstRegister, 5));
        sb.append(generateBinaryRepresentation(secondRegister, 5));
        sb.append(generateBinaryRepresentation(immediate, 16));
        
        return generateFinalHexRepresentation(sb.toString());
    }
    
    private String generateLoadStoreOpcode(int codeLine, String code, HashMap<String, Integer> labels)
    {
        String[] data = getDataPart(code).split(",");
        
        int destinationRegister = Integer.parseInt(data[0].substring(1));
        int baseRegister = Integer.parseInt(data[1].substring(data[1].indexOf("r") + 1, data[1].indexOf(")")));
        int offset = Integer.parseInt(data[1].substring(0, data[1].indexOf("(")), 16);
        
        if (destinationRegister > 31 || baseRegister > 31) return "";
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(this == Instruction.LD ? "110111" : "111111");
        sb.append(generateBinaryRepresentation(baseRegister, 5));
        sb.append(generateBinaryRepresentation(destinationRegister, 5));
        sb.append(generateBinaryRepresentation(offset, 16));
        
        return generateFinalHexRepresentation(sb.toString());
    }
    
    private String generateDADDIUOpcode(int codeLine, String code, HashMap<String, Integer> labels)
    {
        String[] data = getDataPart(code).split(",");
        
        int firstRegister = Integer.parseInt(data[0].substring(1));
        int secondRegister = Integer.parseInt(data[1].substring(1));
        int immediate = Integer.parseInt(data[2].substring(2), 16);
        
        if (firstRegister > 31 || secondRegister > 31) return "";
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("011001");
        sb.append(generateBinaryRepresentation(secondRegister, 5));
        sb.append(generateBinaryRepresentation(firstRegister, 5));
        sb.append(generateBinaryRepresentation(immediate, 16));
        
        return generateFinalHexRepresentation(sb.toString());
    }
    
    private String generateJOpcode(int codeLine, String code, HashMap<String, Integer> labels)
    {
        String label = getDataPart(code);
        
        if (!labels.containsKey(label)) return "";
        
        int index = labels.get(label);
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("000010");
        sb.append(generateBinaryRepresentation(index, 26));
        
        return generateFinalHexRepresentation(sb.toString());
    }
    
    private String getDataPart(String code)
    {
        System.out.println(code);
        String registersString = code.split(" ",2)[1];
        return registersString.replaceAll("\\s+","");
    }
    
    private String generateBinaryRepresentation(int number, int digits)
    {
        String raw = Integer.toBinaryString(number);
        return StringUtils.repeat("0", digits - raw.length()) + raw;
    }
    
    private String generateFinalHexRepresentation(String binaryCode)
    {
        BigInteger decimalOpcode = new BigInteger(binaryCode, 2);
        String hexOpcode = decimalOpcode.toString(16);
        return StringUtils.repeat("0", 8 - hexOpcode.length()) + hexOpcode;
    }
}