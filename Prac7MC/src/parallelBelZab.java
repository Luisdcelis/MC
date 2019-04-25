
import java.awt.Color;
import java.math.BigInteger;
import java.util.concurrent.CyclicBarrier;
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
public class parallelBelZab 
{   
    double [][][] a,b,c;
    static int p = 0, q = 1;
    double alpha, beta, gamma;
    int dim;
    randomGenerator r;
    public static Color[][] m_color;
    static CyclicBarrier barrier;

    
    public parallelBelZab(int dim, double alpha, double beta, double gamma)
    {
        this.dim = dim;
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
        r = new randomGenerator(new BigInteger("23"));
        setup();
    }
    
    void setup()
    {
        a = new double[dim][dim][2];
        b = new double[dim][dim][2];
        c = new double[dim][dim][2];   
        m_color = new Color[dim][dim];
        for(int x = 0; x < dim; x++)
        {
            for(int y = 0; y < dim; y++)
            {
                a[x][y][p] = r.nextRandom26_42Norm();
                b[x][y][p] = r.nextRandom26_42Norm();
                c[x][y][p] = r.nextRandom26_42Norm();
                m_color[x][y] = new Color((float)a[x][y][p], (float)b[x][y][p], (float)c[x][y][p]);
            }
        }
    }
    
    public void caComputation()
    {
        int nNuc = Runtime.getRuntime().availableProcessors();
        this.barrier = new CyclicBarrier(nNuc);
        Thread[] hilos = new Thread[nNuc];
        int tamH = dim/nNuc;
        
        for(int i = 0; i < nNuc; i++)
        {
            if(i != nNuc-1)
                hilos[i] = new Thread(new Hilos(i*tamH, i*tamH+tamH));
            else
                hilos[i] = new Thread(new Hilos(i*tamH, dim));
            hilos[i].start();
        }
        
        for(int i = 0; i < nNuc; i++)
        {
            try{
                hilos[i].join();
            }catch(Exception ex){}
        }
        
        if (p == 0) 
        {
            p = 1; 
            q = 0;
        }
        else 
        {
            p = 0;
            q = 1;
        }
        
    }
    
    double constrain(double x)
    {
        if(x < 0) return 0;
        if(x > 1) return 1;
        return x;
    }
    
    class Hilos implements Runnable
    {
        int ini,fin;
        
        public Hilos(int ini, int fin)
        {
            this.ini = ini;
            this.fin = fin;
        }
        
        @Override
        public void run()
        {
            dim = a.length;
            
            for(int x = ini; x < fin; x++)
            {
                for(int y = 0; y < dim; y++)
                {
                    double c_a = 0.0;
                    double c_b = 0.0;
                    double c_c = 0.0;
                    
                    for(int i = x-1; i <= x+1; i++)
                    {
                        for(int j = y-1; j <= y+1; j++)
                        {
                            c_a += a[(i+dim)%dim][(j+dim)%dim][p];
                            c_b += b[(i+dim)%dim][(j+dim)%dim][p];
                            c_c += c[(i+dim)%dim][(j+dim)%dim][p];
                        }
                    }
                    
                    c_a /= 9.0;
                    c_b /= 9.0;
                    c_c /= 9.0;
                    
                    a[x][y][q] = constrain(c_a + c_a*(alpha*c_b - gamma*c_c));
                    b[x][y][q] = constrain(c_b + c_b*(beta*c_c - alpha*c_a));
                    c[x][y][q] = constrain(c_c + c_c*(gamma*c_a - beta*c_b));
                    
                    m_color[x][y] = new Color((float)a[x][y][q], (float)b[x][y][q], (float)c[x][y][q]);
                }
            }
            
            try {
                barrier.await();
            } catch (Exception ex) {
                Logger.getLogger(parallelBelZab.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    
}
