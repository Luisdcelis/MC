
/**
 *
 * @author Luis
 */
public class Converter 
{
    private static final char syms[] = new char[] { '0','1','2','3','4','5','6',
                                                    '7','8','9','A','B','C','D',
                                                    'E','F','G','H','I','J','K',
                                                    'L','M','N','O','P','Q','R',
                                                    'S','T','U','V','W','X','Y',
                                                    'Z'};
    
    String convertTo(int base, int number)
    {
        String res = "";
        if(number == 0)
            res = "0";
        else
        {
            while(number > 0)
            {
                res = syms[number%base] + res;
                number = number/base;
            }
        }  
        return res;
    }
    
    int desconvert(String s, int base)
    {
        int ret = 0;
        for(int i = 0; i < s.length(); i++)
            ret += (getIndex(s.charAt(i))*Math.pow(base,i));
        return ret;
    }
    
    int getIndex(char c)
    {
        int i = 0;
        while(syms[i] != c)
            i++;
        return i;
    }

}
