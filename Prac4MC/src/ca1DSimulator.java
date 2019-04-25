
import java.math.BigInteger;
import java.util.HashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Luis
 */
public class ca1DSimulator implements ca1DSim{
    
    static final Runtime rt = Runtime.getRuntime();
    static final int nNuc = rt.availableProcessors();
    static CyclicBarrier cb = new CyclicBarrier(nNuc);
    static int nGen;
    static int k;
    public static int hamming;
    
    Converter c = new Converter();
    int r, rule, size, front;
    String gen;
    static HashMap<String,String> icons;
    String[] vec1, vec2;
    
    public ca1DSimulator(){}
    
    public ca1DSimulator(int k, int r, int rule, int size, int front, String gen)
    {
        
        this.k = k;
        this.r = r;
        this.rule = rule;
        this.size = size;
        this.front = front;
        this.gen = gen;
        
        vec1 = new String[size];
        vec2 = new String[size];
        
        
        randomGenerator randomGen;
        randomGen = new randomGenerator(new BigInteger("23"));
        for(int i = 0; i < size; i++)
        {
            int ran = (int) Math.round(randomGen.nextRandomGen(gen)*(k-1));
            vec1[i] = c.convertTo(k, ran);
        }
       this.hamming = 0;
       this.nGen = 1;
       
       getIcons();

    }
    
    class Paralelo implements Runnable
    {
        int ini, fin;
        
        
        public Paralelo(int ini, int fin)
        {
            this.ini = ini;
            this.fin = fin;
        }
            
        public void run()
        {
            hamming = 0;
            String s = "";
            if(front == 0)
            {
                for(int i = ini; i < fin; i++)
                {
                    s = "";
                    for(int j = i-r; j <= i+r; j++)
                    {
                        try
                        {
                            s += vec1[j];
                        }catch(Exception ex){
                            s += "0";
                        }
                    }
                    vec2[i] = icons.get(s);
                }
            }
            else
            {
                for(int i = ini; i < fin; i++)
                {
                    s = "";
                    for(int j = i-r; j <= i+r; j++)
                    {
                        try
                        {
                            s += vec1[j];
                        }catch(Exception ex){
                            if(j < 0)
                                s += vec1[fin + j];
                            else
                                s += vec1[j % fin];
                        }
                    }
                    vec2[i] = icons.get(s);
                }
            }

            
            synchronized(this)
            {
                for(int i = ini; i < fin; i++)
                {
                    if(!vec1[i].equals(vec2[i]))
                        hamming++;
                }
            }
                
            

            try
            {
            cb.await();
            } catch (Exception ex) {
                Logger.getLogger(ca1DSimulator.class.getName()).log(Level.SEVERE, null, ex);
            }
            for(int i = ini; i < fin; i++)
                vec1[i] = vec2[i];
            try
            {
            cb.await();
            } catch (Exception ex) {
                Logger.getLogger(ca1DSimulator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    

    
    void getIcons()
    {
        int numIcons = (int) Math.pow(k, (2*r+1));
        icons = new HashMap<>(numIcons);
        String key;
        
        String ruleNorm = c.convertTo(k, rule);
        for(int i = 0; i < numIcons; i++)
        {
            key = c.convertTo(k, i);
            while(key.length() < (2*r)+1)
                key = "0" + key;
            
            if(i < ruleNorm.length())
                icons.put(key, Character.toString(ruleNorm.charAt(ruleNorm.length()-i-1)));
            else
                icons.put(key, "0");
        }
    }

    
    public void nextGen()
    {
        ExecutorService pool = Executors.newFixedThreadPool(nNuc);
        
        int tam = size/nNuc;
        
        for(int i = 0; i < nNuc; i++)
        {
            if(i != nNuc - 1)
                pool.execute(new Paralelo(i*tam, i*tam + tam));
            else
                pool.execute(new Paralelo(i*tam, size));
        }
        pool.shutdown();
        
        while(!pool.isTerminated());
        
    }
    
    public void caComputation(int nGen)
    {
        nextGen();
        ca1DSimulator.nGen = nGen;
    }
    
}
