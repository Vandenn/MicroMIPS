
package Model;

import org.apache.commons.lang.StringUtils;

public class InternalRegisters
{
    private long PC;
    private int ifid_IR;
    private long ifid_NPC;
    private int idex_IR;
    private long idex_A;
    private long idex_B;
    private long idex_Imm;
    private int exmem_IR;
    private long exmem_ALU;
    private long exmem_B;
    private int exmem_Cond;
    private int memwb_IR;
    private long memwb_ALU;
    private long memwb_LMD;
    private long mem_alu;
    private int regAff;

    public InternalRegisters()
    {
        this.PC = 0;
        this.ifid_IR = 0;
        this.ifid_NPC = 0;
        this.idex_IR = 0;
        this.idex_A = 0;
        this.idex_B = 0;
        this.idex_Imm = 0;
        this.exmem_IR = 0;
        this.exmem_ALU = 0;
        this.exmem_B = 0;
        this.exmem_Cond = 0;
        this.memwb_IR = 0;
        this.memwb_ALU = 0;
        this.memwb_LMD = 0;
        this.mem_alu = 0;
        this.regAff = 0;
    }

    public InternalRegisters(long PC, int ifid_IR, long ifid_NPC, int idex_IR, long idex_A, long idex_B, long idex_Imm, int exmem_IR, long exmem_ALU, long exmem_B, int exmem_Cond, int memwb_IR, long memwb_ALU, long memwb_LMD, long mem_alu, int regAff) {
        this.PC = PC;
        this.ifid_IR = ifid_IR;
        this.ifid_NPC = ifid_NPC;
        this.idex_IR = idex_IR;
        this.idex_A = idex_A;
        this.idex_B = idex_B;
        this.idex_Imm = idex_Imm;
        this.exmem_IR = exmem_IR;
        this.exmem_ALU = exmem_ALU;
        this.exmem_B = exmem_B;
        this.exmem_Cond = exmem_Cond;
        this.memwb_IR = memwb_IR;
        this.memwb_ALU = memwb_ALU;
        this.memwb_LMD = memwb_LMD;
        this.mem_alu = mem_alu;
        this.regAff = regAff;
    }

    public long getPC() {
        return PC;
    }

    public void setPC(long PC) {
        this.PC = PC;
    }

    public int getIfid_IR() {
        return ifid_IR;
    }

    public void setIfid_IR(int ifid_IR) {
        this.ifid_IR = ifid_IR;
    }

    public long getIfid_NPC() {
        return ifid_NPC;
    }

    public void setIfid_NPC(long ifid_NPC) {
        this.ifid_NPC = ifid_NPC;
    }

    public int getIdex_IR() {
        return idex_IR;
    }

    public void setIdex_IR(int idex_IR) {
        this.idex_IR = idex_IR;
    }

    public long getIdex_A() {
        return idex_A;
    }

    public void setIdex_A(long idex_A) {
        this.idex_A = idex_A;
    }

    public long getIdex_B() {
        return idex_B;
    }

    public void setIdex_B(long idex_B) {
        this.idex_B = idex_B;
    }

    public long getIdex_Imm() {
        return idex_Imm;
    }

    public void setIdex_Imm(long idex_Imm) {
        this.idex_Imm = idex_Imm;
    }

    public int getExmem_IR() {
        return exmem_IR;
    }

    public void setExmem_IR(int exmem_IR) {
        this.exmem_IR = exmem_IR;
    }

    public long getExmem_ALU() {
        return exmem_ALU;
    }

    public void setExmem_ALU(long exmem_ALU) {
        this.exmem_ALU = exmem_ALU;
    }

    public long getExmem_B() {
        return exmem_B;
    }

    public void setExmem_B(long exmem_B) {
        this.exmem_B = exmem_B;
    }

    public int getExmem_Cond() {
        return exmem_Cond;
    }

    public void setExmem_Cond(int exmem_Cond) {
        this.exmem_Cond = exmem_Cond;
    }

    public int getMemwb_IR() {
        return memwb_IR;
    }

    public void setMemwb_IR(int memwb_IR) {
        this.memwb_IR = memwb_IR;
    }

    public long getMemwb_ALU() {
        return memwb_ALU;
    }

    public void setMemwb_ALU(long memwb_ALU) {
        this.memwb_ALU = memwb_ALU;
    }

    public long getMemwb_LMD() {
        return memwb_LMD;
    }

    public void setMemwb_LMD(long memwb_LMD) {
        this.memwb_LMD = memwb_LMD;
    }

    public long getMem_alu() {
        return mem_alu;
    }

    public void setMem_alu(long mem_alu) {
        this.mem_alu = mem_alu;
    }

    public int getRegAff() {
        return regAff;
    }

    public void setRegAff(int regAff) {
        this.regAff = regAff;
    }
    
    public String printRegisters()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("PC: " + Converter.longToHex(PC, 16) + "\n");
        sb.append("IF/ID.IR: " + Converter.intToHex(ifid_IR, 8) + "\n");
        sb.append("IF/ID.NPC: " + Converter.longToHex(ifid_NPC, 16) + "\n");
        sb.append("ID/EX.IR: " + Converter.intToHex(idex_IR, 8) + "\n");
        sb.append("ID/EX.A: " + Converter.longToHex(idex_A, 16) + "\n");
        sb.append("ID/EX.B: " + Converter.longToHex(idex_B, 16) + "\n");
        sb.append("ID/EX.Imm: " + Converter.longToHex(idex_Imm, 16) + "\n");
        sb.append("EX/MEM.IR: " + Converter.intToHex(exmem_IR, 8) + "\n");
        sb.append("EX/MEM.ALUOutput: " + Converter.longToHex(exmem_ALU, 16) + "\n");
        sb.append("EX/MEM.B: " + Converter.longToHex(exmem_B, 16) + "\n");
        sb.append("EX/MEM.Cond: " + exmem_Cond + "\n");
        sb.append("MEM/WB.IR: " + Converter.intToHex(memwb_IR, 8) + "\n");
        sb.append("MEM/WB.ALUOutput: " + Converter.longToHex(memwb_ALU, 16) + "\n");
        sb.append("MEM/WB.LMD: " + Converter.longToHex(memwb_LMD, 16) + "\n");
        sb.append("MEM[ALUOutput]: " + Converter.longToHex(mem_alu, 16) + "\n");
        sb.append("Registers Affected: R" + regAff + "\n");
        
        return sb.toString();
    }
}