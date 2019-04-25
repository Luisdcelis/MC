
import java.math.BigInteger;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Luis
 */
public class ca2DSimulator 
{
    public static int[][] m1, m2;
    static int tam, nHilos;
    static CyclicBarrier barrier;
    
    
    public ca2DSimulator(int tam, int nHilos) //rellenar matriz m1
    {
        this.tam = tam;
        this.nHilos = nHilos;
        m1 = new int[tam][tam];
        m2 = new int[tam][tam];

        randomGenerator r = new randomGenerator(new BigInteger("23"));
        
        for(int i = 0; i < tam; i++)
            for(int j = 0; j < tam; j++)
                m1[i][j] = (int)Math.round(r.nextRandom26_42Norm());
    }
    
    public void caComputation()
    {
        nextGen();
    }
    
    public void nextGen()
    {
        this.barrier = new CyclicBarrier(nHilos);
        ExecutorService pool = Executors.newFixedThreadPool(nHilos);
        
        int tam_v = tam/nHilos;
        for(int i = 0; i < nHilos; i++)
        {
            if(i != nHilos - 1)
                pool.execute(new Paralelo(i*tam_v, i*tam_v+tam_v));
            else
                pool.execute(new Paralelo(i*tam_v, tam));
        }
        pool.shutdown();
        
        while(!pool.isTerminated());
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
            for(int i = 0; i < m1.length; i++)
            {
                for(int j = ini; j < fin; j++)
                {
                    m2[i][j] = nextState(i, j);
                }
            }
            try {
                barrier.await();
            } catch (Exception ex) {
                Logger.getLogger(ca2DSimulator.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for(int i = 0; i < m1.length; i++)
                for(int j = ini; j < fin; j++)
                    m1[i][j] = m2[i][j];
        }
        
        int nextState(int a, int b)
        {
            int numVec = 0;
            int state = m1[a][b];
            
            for(int i = a-1; i <= a+1; i++)
            {
                for(int j = b-1; j <= b+1; j++)
                {
                    if(i != a || j != b)
                    {
                        try{
                            if(m1[i][j] == 1)
                                numVec++;
                        }catch(Exception e){}
                    }
                }
            }
            if(state == 0 && numVec == 3) //si la celula esta muerta y tiene 3 vecinos, nace
                state = 1;
            if(state == 1 && (numVec > 3 || numVec < 2)) //si la celula esta viva pero tiene mas de 3 o menos de 2 vecinas muere
                state = 0;
            
            return state;
        }
    }
    
    
    
}
