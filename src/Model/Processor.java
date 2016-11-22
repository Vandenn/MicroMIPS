
package Model;

public class Processor
{
    private String opcode;
    
    public Processor(String opcode)
    {
        this.opcode = opcode;
    }
    
    public Boolean singleStep()
    {
        return false;
    }
}