package Game;

public class PrintThread extends Thread
{
    private NKeyList keys;

    public PrintThread(NKeyList li)
    { keys = li;
    }

    public void run()
    {
        while(true)
        {
            keys.print();
            try { Thread.sleep(10); } catch(Exception ex) {}

        }
    }
}
