
import java.math.BigInteger;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;


// Paso 1: si rr < Ps Paso 2 (vive), si no, muere
// Paso 2: si rrp < Pp +1PH y Paso 3, si no, Paso 4
// Paso 3: si PH >= NP Paso 5 (Calculamos Pp), si no, Paso 4
// Paso 4: si rrm < Pm Paso 6 (Calculamos Pm), si no, Paso 7
// Paso 5: si Prolifera Paso 8 (Actualizamos su posicion), si no, Paso 4
// Paso 6: si Migra Paso 8 (Actualizamos su posicion), si no, Paso 1
// Paso 7: Inactivo → Paso 1
// Paso 8: Actualizamos → Paso 1

public class parallelTumoralGrowth 
{
//           Ps, Pd, Pm, Pp, Pq; //Probabilities: survive, death, migration
//                               //proliferation and quiescence
//                               //Ps + Pd = 1;
//    
//           rr, rrm, rrp;       //Randoms: survival, migration and
//                               //proliferation
//                               //0 <= rr <= 1
//    
//           PH, NP;             //PH: number of proliferation signals
//                               //NP: total PH needed to proliferate
    
    public Cell[][][] celulas;
    double Ps, Pm, Pp;
    int NP;
    public static int size, p, q;
    static randomGenerator r;
    
    static CyclicBarrier cb;
    
    class Cell
    {
        public int PH;
        public int state;
        
        public Cell()
        {
            this.PH = 0;
            this.state = 0;
        }
    }
    
    public parallelTumoralGrowth(int size, double Ps, double Pm, double Pp, int NP, boolean positions)
    {
        this.NP = NP;
        this.Pm = Pm;
        this.Pp = Pp;
        this.Ps = Ps;
        this.size = size;
        p = 0; q = 1;
        
        celulas = new Cell[size][size][2];
        r = new randomGenerator(new BigInteger("23"));
        
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                celulas[i][j][p] = new Cell();
                celulas[i][j][q] = new Cell();

            }
        }
        
        if(positions == true)                       // Positions = 1 → una celula viva en el centro
        {
            celulas[Math.round(size/2)][Math.round(size/2)][p].state = 1;
            celulas[Math.round(size/2)][Math.round(size/2)][q].state = 1;
        }
        else
        {
            for(int i = 1; i <= 3; i++)
            {
                int randX = (int) Math.round(r.nextRandom26_42Norm()*size);
                int randY = (int) Math.round(r.nextRandom26_42Norm()*size);
                
                celulas[randX][randY][p].state = 1;
            }
        }
        
    }   
    
    public void caComputation()
    {
        nextGen();
    }
    
    public void nextGen()
    {
        int nNuc = Runtime.getRuntime().availableProcessors();
        this.cb = new CyclicBarrier(nNuc);
        Thread[] hilos = new Thread[nNuc];
        int tamH = size/nNuc;
        
        for(int i = 0; i < nNuc; i++)
        {
            if(i != nNuc-1)
                hilos[i] = new Thread(new Hilos(i*tamH, i*tamH+tamH));
            else
                hilos[i] = new Thread(new Hilos(i*tamH, size));
            hilos[i].start();
        }
        
        for(int i = 0; i < nNuc; i++)
        {
            try{
                hilos[i].join();
            }catch(Exception ex){}
        }
//        System.out.println(size);
//        for(int i = 0; i < size; i++)
//        {
//            for(int j = 0; j < size; j++)
//            {
//                System.out.print(celulas[i][j][p].state + " ");
//            }
//            System.out.println("");
//        }
        
        
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
    
    class Hilos implements Runnable
    {
        int ini,fin;
        
        public Hilos(int ini, int fin)
        {
            this.ini = ini;
            this.fin = fin;
        }
        
        public void run()
        {
            for(int i = ini; i < fin; i++)
            {
                for(int j = 0; j < size; j++)
                {
                    if(celulas[i][j][p].state == 1) //si esta viva
                    {
                        if(r.nextRandom26_42Norm() >= Ps) //si muere
                        {
                            celulas[i][j][q].state = 0;
                        }
                        else                             // comprobar estados
                        {
                            if(r.nextRandom26_42Norm() < Pp)    //mandamos señal
                            {
                                celulas[i][j][q].PH++;  
                                if(celulas[i][j][q].PH >= NP)   //si supera la señal, prolifera
                                {
                                    proliferation(i,j);
                                }
                                else                            //si no la supera miramos si migra
                                {
                                    if(r.nextRandom26_42Norm() < Pm)
                                    {

                                        migration(i,j);
                                    }
                                    else                        //si ni migra ni prolifera, permanece
                                    {
                                        celulas[i][j][q] = celulas[i][j][p];
                                    }
                                }
                            }
                            else
                            {
                                    if(r.nextRandom26_42Norm() < Pm)
                                    {
                                        migration(i,j);
                                    }
                                    else                        //si ni migra ni prolifera, permanece
                                    {
                                        celulas[i][j][q] = celulas[i][j][p];
                                    }
                            }
                        }
                    }
                }
            }
            try {
                cb.await();
            } catch (Exception ex) {
                Logger.getLogger(parallelTumoralGrowth.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        void proliferation(int x, int y)
        {
            int i, j;
            double c1 = celulas[(x+1+size)%size][y][p].state, c2 = celulas[(x-1+size)%size][y][p].state,
                c3 = celulas[x][(y+1+size)%size][p].state, c4 = celulas[x][(y-1+size)%size][p].state;
            
            if((c1+c2+c3+c4) != 4) // Si alguna celula esta muerta de alrededor
            {
                double p1 = (1-c2)/(4-(c1+c2+c3+c4)), p2 = (1-c1)/(4-(c1+c2+c3+c4)),
                       p3 = (1-c4)/(4-(c1+c2+c3+c4));
                
                double random = r.nextRandom26_42Norm();
                
                if(random < p1)
                {
                    i = (x-1+size)%size;
                    j = y;
                } 
                else
                {
                    if(random < p1+p2)
                    {
                        i = (x+1+size)%size;
                        j = y;
                    }
                    else
                    {
                        if(random < p1+p2+p3)
                        {
                            i = x;
                            j = (y-1+size)%size;
                        }
                        else
                        {
                            i = x;
                            j = (y+1+size)%size;
                        }
                    }
                }
                celulas[x][y][q].PH = 0;
                celulas[i][j][q].state = 1;
            }//si no hay no hacemos nada
        }
        
        void migration(int x, int y)
        {
            int i, j;
            double c1 = celulas[(x+1+size)%size][y][p].state, c2 = celulas[(x-1+size)%size][y][p].state,
                c3 = celulas[x][(y+1+size)%size][p].state, c4 = celulas[x][(y-1+size)%size][p].state;
            if((c1+c2+c3+c4) != 4)
            {
                double p1 = (1-c2)/(4-(c1+c2+c3+c4)), p2 = (1-c1)/(4-(c1+c2+c3+c4)),
                       p3 = (1-c4)/(4-(c1+c2+c3+c4));
                
                double random = r.nextRandom26_42Norm();
                
                if(random < p1)
                {
                    i = (x-1+size)%size;
                    j = y;
                }
                else
                {
                    if(random < p1 + p2)
                    {
                        i = (x+1+size)%size;
                        j = y;

                    }
                    else
                    {
                        if(random < p1+p2+p3)
                        {
                            i = x;
                            j = (y-1+size)%size;
                        }
                        else
                        {
                            i = x;
                            j = (y+1+size)%size;
                        }
                    }
                }
                celulas[i][j][q] = celulas[x][y][p];
                celulas[x][y][q] = new Cell();
                celulas[x][y][q].state = 0;
            }
            else    //quiescent
            {
                celulas[x][y][q] = celulas[x][y][p];
            }
        }
        
        
    }

    
}


