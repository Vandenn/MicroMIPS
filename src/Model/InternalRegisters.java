
package Model;

public class InternalRegisters
{
    private String PC;
    private String ifid_IR;
    private String ifid_NPC;
    private String idex_IR;
    private String idex_A;
    private String idex_B;
    private String idex_Imm;
    private String exmem_IR;
    private String exmem_ALU;
    private String exmem_B;
    private String exmem_Cond;
    private String memwb_IR;
    private String memwb_ALU;
    private String memwb_LMD;
    private String mem_alu;
    private String regAff;

    public InternalRegisters() {
        this.PC = "";
        this.ifid_IR = "";
        this.ifid_NPC = "";
        this.idex_IR = "";
        this.idex_A = "";
        this.idex_B = "";
        this.idex_Imm = "";
        this.exmem_IR = "";
        this.exmem_ALU = "";
        this.exmem_B = "";
        this.exmem_Cond = "";
        this.memwb_IR = "";
        this.memwb_ALU = "";
        this.memwb_LMD = "";
        this.mem_alu = "";
        this.regAff = "";
    }
    
    public InternalRegisters(String PC, String ifid_IR, String ifid_NPC, String idex_IR, String idex_A, String idex_B, String idex_Imm, String exmem_IR, String exmem_ALU, String exmem_B, String exmem_Cond, String memwb_IR, String memwb_ALU, String memwb_LMD, String mem_alu, String regAff) {
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

    public String getPC() {
        return PC;
    }

    public void setPC(String PC) {
        this.PC = PC;
    }

    public String getIfid_IR() {
        return ifid_IR;
    }

    public void setIfid_IR(String ifid_IR) {
        this.ifid_IR = ifid_IR;
    }

    public String getIfid_NPC() {
        return ifid_NPC;
    }

    public void setIfid_NPC(String ifid_NPC) {
        this.ifid_NPC = ifid_NPC;
    }

    public String getIdex_IR() {
        return idex_IR;
    }

    public void setIdex_IR(String idex_IR) {
        this.idex_IR = idex_IR;
    }

    public String getIdex_A() {
        return idex_A;
    }

    public void setIdex_A(String idex_A) {
        this.idex_A = idex_A;
    }

    public String getIdex_B() {
        return idex_B;
    }

    public void setIdex_B(String idex_B) {
        this.idex_B = idex_B;
    }

    public String getIdex_Imm() {
        return idex_Imm;
    }

    public void setIdex_Imm(String idex_Imm) {
        this.idex_Imm = idex_Imm;
    }

    public String getExmem_IR() {
        return exmem_IR;
    }

    public void setExmem_IR(String exmem_IR) {
        this.exmem_IR = exmem_IR;
    }

    public String getExmem_ALU() {
        return exmem_ALU;
    }

    public void setExmem_ALU(String exmem_ALU) {
        this.exmem_ALU = exmem_ALU;
    }

    public String getExmem_B() {
        return exmem_B;
    }

    public void setExmem_B(String exmem_B) {
        this.exmem_B = exmem_B;
    }

    public String getExmem_Cond() {
        return exmem_Cond;
    }

    public void setExmem_Cond(String exmem_Cond) {
        this.exmem_Cond = exmem_Cond;
    }

    public String getMemwb_IR() {
        return memwb_IR;
    }

    public void setMemwb_IR(String memwb_IR) {
        this.memwb_IR = memwb_IR;
    }

    public String getMemwb_ALU() {
        return memwb_ALU;
    }

    public void setMemwb_ALU(String memwb_ALU) {
        this.memwb_ALU = memwb_ALU;
    }

    public String getMemwb_LMD() {
        return memwb_LMD;
    }

    public void setMemwb_LMD(String memwb_LMD) {
        this.memwb_LMD = memwb_LMD;
    }

    public String getMem_alu() {
        return mem_alu;
    }

    public void setMem_alu(String mem_alu) {
        this.mem_alu = mem_alu;
    }

    public String getRegAff() {
        return regAff;
    }

    public void setRegAff(String regAff) {
        this.regAff = regAff;
    }
    
    public String printRegisters()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("PC: " + PC + "\n");
        sb.append("IF/ID.IR: " + ifid_IR + "\n");
        sb.append("IF/ID.NPC: " + ifid_NPC + "\n");
        sb.append("ID/EX.IR: " + idex_IR + "\n");
        sb.append("ID/EX.A: " + idex_A + "\n");
        sb.append("ID/EX.B: " + idex_B + "\n");
        sb.append("ID/EX.Imm: " + idex_Imm + "\n");
        sb.append("EX/MEM.IR: " + exmem_IR + "\n");
        sb.append("EX/MEM.ALUOutput: " + exmem_ALU + "\n");
        sb.append("EX/MEM.B: " + exmem_B + "\n");
        sb.append("EX/MEM.Cond: " + exmem_Cond + "\n");
        sb.append("MEM/WB.IR: " + memwb_IR + "\n");
        sb.append("MEM/WB.ALUOutput: " + memwb_ALU + "\n");
        sb.append("MEM/WB.LMD: " + memwb_LMD + "\n");
        sb.append("MEM[ALUOutput]: " + mem_alu + "\n");
        sb.append("Registers Affected: " + regAff + "\n");
        
        return sb.toString();
    }
}