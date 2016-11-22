
package Model;

public class Processor
{
    private InternalRegisters irs;
    private Database db;
    
    public Processor()
    {
        irs = new InternalRegisters();
        irs.setPC("0000 0000 0000 1000");
        db = Database.getInstance();
    }
    
    public Boolean singleStep()
    {
        return false;
    }
}