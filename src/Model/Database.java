
package Model;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Database 
{
    public static final Long DEFAULT_REGISTER_VALUE = 0L;
    public static final Long MINIMUM_REGISTER_VALUE = 0x8000000000000000L;
    public static final Long MAXIMUM_REGISTER_VALUE = 0x7FFFFFFFFFFFFFFFL;
    public static final Byte DEFAULT_MEMORY_VALUE = 0;
    public static final Byte MINIMUM_MEMORY_VALUE = (byte) 0x80;
    public static final Byte MAXIMUM_MEMORY_VALUE = 0x7F;
    
    private static Database _instance;
    
    private Map<Integer, Long> registerDB;
    private Map<Integer, Opcode> instructionDB;
    private Map<Integer, Byte> memoryDB;
    
    private String registerValuePattern;
    private String memoryValuePattern;
    private String instructionValuePattern;
    
    private Database() 
    {
        registerDB = new TreeMap<>();
        instructionDB = new TreeMap<>();
        memoryDB = new TreeMap<>();
        initializeRegisters();
        initializeInstructions();
        initializeMemory();

        instructionValuePattern = "^[01]{32}$";
    }
    
    private void initializeRegisters()
    {
        for (int i = 1; i <= 31; i++)
        {
            registerDB.put(i, DEFAULT_REGISTER_VALUE);
        }
    }
    
    private void initializeInstructions()
    {
        for (int i = 0x1000; i <= 0x2FFF; i += 0x0004)
        {
            instructionDB.put(i, null);
        }
    }
    
    private void initializeMemory()
    {
        for (int i = 0x3000; i <= 0x4FFF; i += 0x0001)
        {
            memoryDB.put(i, DEFAULT_MEMORY_VALUE);
        }
    }
    
    public Map getRegisters()
    {
        return registerDB;
    }
    
    public Map getMemory()
    {
        return memoryDB;
    }
    
    public Map getInstructions()
    {
        return instructionDB;
    }
    
    public Boolean editRegister(int key, long value)
    {
        if (registerDB.containsKey(key) && value >= MINIMUM_REGISTER_VALUE && value <= MAXIMUM_REGISTER_VALUE)
        {
            registerDB.replace(key, value);
            return true;
        }
        return false;
    }
    
    public Boolean editMemory(int key, byte value)
    {
        if (memoryDB.containsKey(key) && value >= MINIMUM_REGISTER_VALUE && value <= MAXIMUM_MEMORY_VALUE)
        {
            memoryDB.replace(key, value);
            return true;
        }
        return false;
    }
    
    public Boolean addInstruction(int key, String value)
    {
        if (instructionDB.containsKey(key) && value.matches(instructionValuePattern))
        {
            instructionDB.replace(key, new Opcode(value));
            return true;
        }
        return false;
    }
    
    public void clearInstructions()
    {
        initializeInstructions();
    }
    
    public static Database getInstance()
    {
        if(_instance == null)
        {
            _instance = new Database();
        }
        return _instance;
    }
}
