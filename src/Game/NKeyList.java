package Game;


public class NKeyList
{
    private static final int KEYNR = 20;
    private char[] keys = new char[KEYNR];
    private int    size = 0;



    /**
     *  clear the whole list
     */
    public void clear() {
        size = 0;
    }


    /** add char to list
     *
     * @param c char to add
     */
    public void add(char c)
    {
        // too long?
        if(size>=KEYNR) return;

        // already contained?
        for(int i=0; i<size; i++)
        { if(keys[i]==c) return;
        }

        keys[size] = c;
        size++;
    }


    /** remove char c from list
     *
     * @param c char to be removed
     */
    public void remove(char c) {
        // find contained c and remove it
        for(int i=0; i<size; i++)
        {
            if(keys[i]==c)
            {
                keys[i] = keys[size-1];
                size--;
                return;
            }
        }
    }

    /** test if char c is contained
     *
     * @param c   char which may be contained
     * @return IF c is contained
     */
    public boolean isIn(char c)
    {
        for(int i=0; i<size; i++)
        {
            if(keys[i]==c) return true;
        }
        return false;
    }


    public void print() {
        System.out.print("<");
        for(int i=0; i<size; i++)
        {
            System.out.print(" "+keys[i]);
        }
        System.out.println(" >");
    }

}
