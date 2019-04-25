
public class URMinter {
    
    static String[] instr;
    static int[] regs;
    static int actual;
    
    public URMinter(String[] instr, int[] regs)
    {
        URMinter.instr = instr;
        URMinter.regs = regs;
        URMinter.actual = 0;
    }
    int interpreta()
    {
        while(actual >= 0 && actual < instr.length)
        {
            String instrAct = instr[actual];
            switch(instrAct.charAt(0))
            {
                case 'Z':
                    int[] aux1 = entreParentesis(instrAct);
                    regs[aux1[0]-1] = 0;
                    actual++;
                    break;
                    
                case 'S':
                    int[] aux2 = entreParentesis(instrAct);
                    regs[aux2[0]-1]++;
                    actual++;
                    break;
                    
                case 'T':
                    int[] aux3 = entreParentesis(instrAct);
                    regs[aux3[1]-1] = regs[aux3[0]-1];
                    actual++;
                    break;
                    
                case 'J':
                    int[] aux4 = entreParentesis(instrAct);
                    if(regs[aux4[0]-1] == regs[aux4[1]-1])
                    {
                        if(aux4[2] <= instr.length || aux4[2] > 0)
                            actual = aux4[2]-1;
                        else
                            actual = -1;
                    }
                    else
                    {
                        actual++;
                    }
            }
        }
        return regs[0];
    }
    
    int[] entreParentesis(String input)
    {
        int[] res = new int[3];
        String n = "";
        int i = 2;
        boolean ended = false;
        while(!ended)
        {
            n = n + input.charAt(i);
            if(input.charAt(i+1) == ')')
                ended = true;
            else
                i++;
        }
        String[] aux = n.split(",");
        for(int it = 0; it < aux.length; it++)
        {
            res[it] = Integer.parseInt(aux[it]);
        }
        return res;
    }
}
