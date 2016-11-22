
package Model;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Database 
{
    public static final String DEFAULT_REGISTER_VALUE = "0000 0000 0000 0000";
    public static final String DEFAULT_MEMORY_VALUE = "00";
    
    private static Database _instance;
    
    private Map<Integer, String> registerDB;
    private Map<Integer, String> instructionDB;
    private Map<Integer, String> memoryDB;
    
    private String registerValuePattern;
    private String memoryValuePattern;
    
    private int instructionAddPointer;
    
    private Database() 
    {
        registerDB = new TreeMap<>();
        instructionDB = new TreeMap<>();
        memoryDB = new TreeMap<>();
        initializeRegisters();
        initializeInstructions();
        initializeMemory();
        
        registerValuePattern = "^([0-9A-F]{4} {1}){3}[0-9A-F]{4}$";
        memoryValuePattern = "^[0-9A-F]{2}$";
        instructionAddPointer = 0x1000;
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
            instructionDB.put(i, "");
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
    
    public Boolean editRegister(int key, String value)
    {
        if (registerDB.containsKey(key) && value.matches(registerValuePattern))
        {
            registerDB.replace(key, value);
            return true;
        }
        return false;
    }
    
    public Boolean editMemory(int key, String value)
    {
        if (memoryDB.containsKey(key) && value.matches(memoryValuePattern))
        {
            memoryDB.replace(key, value);
            return true;
        }
        return false;
    }
    
    public Boolean addInstruction(String instruction)
    {
        if (instructionAddPointer >= 0x2FFF) return false;
        instructionDB.put(instructionAddPointer, instruction);
        instructionAddPointer += 0x0004;
        return true;
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
