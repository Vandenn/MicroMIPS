
package Model;

import java.math.BigInteger;
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
        String opcode0_5 = "";
        String opcode16_31 = "";
        String operation = "";
        
        
        boolean stall = false;
        //decoding
        for(Map.Entry<Integer,Opcode> entry : instructions.entrySet())
        {
            opcode0_5 = entry.getValue().getIR0_5();
            switch (opcode0_5)
            {
                case "110111": //LD
                    operation = "ld";
                    break;
                case "111111": //SD
                    operation = "sd";
                    break;
                case "000101": //BNE
                    operation = "bne";
                    break;
                case "011001": //DADDIU
                    operation = "daddiu";
                    break;
                case "000010": //J
                    operation = "j";
                    break;
                case "000000": //NOP, OR, DSUBU, SLT
                    opcode16_31 = entry.getValue().getIR16_31();
                    switch (opcode16_31)
                    {
                        case "000000": //NOP
                            operation = "nop";
                            break;
                        case "100101": //OR
                            operation = "or";
                            break;
                        case "101111": //DSUBU
                            operation = "dsubu";
                            break;
                        case "101010": //SLT
                            operation = "slt";
                            break;
                    }       
            }
            
            
            
        }
        
        for(Map.Entry<Integer,Integer> entry : pipeline.entrySet())
        {   
            int value = entry.getValue();
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
        stall = false;
        return pipeline;
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
