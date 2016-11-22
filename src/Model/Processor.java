
package Model;

import java.util.Map;

public class Processor
{
    private InternalRegisters irs;
    private Database db;
    
    public Processor()
    {
        irs = new InternalRegisters();
        irs.setPC(0x1000);
        db = Database.getInstance();
    }
    
    public Boolean singleStep()
    {
        Map<Integer, Opcode> instructions = db.getInstructions();
        if (instructions.containsKey((int)irs.getPC()) && instructions.get((int)irs.getPC()) != null)
        {
            irs.setIfid_IR(Converter.hexToInt(instructions.get((int)irs.getPC()).getHex()));
            irs.setPC(irs.getPC() + 0x0004);
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public InternalRegisters getInternalRegisters()
    {
        return irs;
    }
}