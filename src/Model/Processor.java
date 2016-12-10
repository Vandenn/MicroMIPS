
package Model;

import java.math.BigInteger;
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
            irs.setIfid_IR(instructions.get((int)irs.getPC()));
            irs.setPC(irs.getPC() + 0x0004);
            irs.setIfid_NPC(irs.getPC());
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
    
    private long exOR(long rsContent, long rtContent){ //return ALUOutput
        return (rsContent | rtContent);
    }
    
    private long exDSUBU(long rsContent, long rtContent){ //return ALUOutput
        return (rsContent - rtContent);
    }
    
    private long exSLT(long rsContent, long rtContent){ //return ALUOutput
        if(rsContent < rtContent){
            return 1;
        }else{
            return 0;
        }
    }
    
    private int exLDSD(Opcode op){ //for Load or Store: return starting memory location (base + offset)
        BigInteger memLocation = new BigInteger(op.getIR6_10()); //base
        memLocation.add(new BigInteger(op.getIR16_31())); //offset
        return memLocation.intValue();
    }
    
    private long memLD(int memLocation){ //return LMD
        String result = "";
        for(int i = 0; i<8; i++){
            result+=Converter.byteToHex((byte)db.getMemory().get(memLocation+i), 2);   
        }
        return (Converter.hexToLong(result));
    }
     
    //not sure if this is right??
    private void memSD(long rtContent, int memLocation){ //store rt to memory
        String content = Converter.longToHex(rtContent, 16);
        db.editMemory(memLocation, Converter.hexToByte(content));
    }
    
    private long exDADDIU(long rtContent, Opcode op){ //return ALUOutput
        BigInteger result = new BigInteger(op.getIR16_31());
        result.add(BigInteger.valueOf(rtContent));
        return result.longValue();
    }

    private long getNPCBranch(long npc, Opcode op){ //return NPC for ALUOutput if cond = 1
        BigInteger newNPC = new BigInteger(op.getIR16_31());
        newNPC.shiftLeft(2);
        newNPC.add(BigInteger.valueOf(npc));
        return newNPC.longValue();
    }
    
    private long getNPCJump(long npc, Opcode op){ //return new NPC for ALUOutput
        BigInteger newNPC = new BigInteger(op.getIR16_31());
        newNPC.shiftLeft(2);
        return newNPC.longValue();
    }
}