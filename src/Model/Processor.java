
package Model;

import java.util.Map;
import java.util.TreeMap;

public class Processor
{
    private InternalRegisters irs;
    private Database db;
    public Map<Integer, Integer> pipeline = new TreeMap();
    
    public Processor()
    {
        irs = new InternalRegisters();
        irs.setPC(0x1000);
        db = Database.getInstance();
    }
    
    public Map singleStep()
    {
        Map<Integer, Opcode> instructions = db.getInstructions();
        
        int value;
        boolean stall = false;
        for(Map.Entry<Integer,Integer> entry : pipeline.entrySet())
        {   
            value = entry.getValue();
            switch (value) {
                case 4: //WB
                    irs.setRegAff((int)irs.getMemwb_LMD());
                    value++;
                    entry.setValue(value);
                    break;
                case 3: //MEM
                    irs.setMemwb_IR(irs.getExmem_IR());
                    //if ALU
                    irs.setMemwb_LMD(irs.getExmem_ALU());
                    irs.setMemwb_ALU(irs.getExmem_ALU());
                    irs.setMem_alu(irs.getExmem_ALU());
                    value++;
                    entry.setValue(value);
                    break;
                case 2: //EX
                    break;
                case 1: //ID
                    break;
                default:
                    break;
            }
        }
               
        //if walang stall and may next instruction pa, ipasok sa IF
        //if stall same values pa rin ung IF
        if (!stall && instructions.containsKey((int)irs.getPC()) && instructions.get((int)irs.getPC()) != null)
        {
            pipeline.put((int)irs.getPC(), 1);
            irs.setIfid_IR(instructions.get((int)irs.getPC()));
            irs.setPC(irs.getPC() + 0x0004);
            irs.setIfid_NPC(irs.getPC());
        }
        
        return pipeline;
    }
    
    public InternalRegisters getInternalRegisters()
    {
        return irs;
    }
}