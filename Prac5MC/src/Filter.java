import java.nio.file.*;
import java.io.*;
import static java.nio.file.StandardOpenOption.*;
/**
 *
 * @author Luis
 */
public class Filter {
    
    static int k = 2, r = 1, sizex = 1000, sizey = 4000;
    static int[] h;
    static int[][] M;
    static int[] t;
    static Converter c = new Converter();
    
    static void simular(int rule)
    {
        ca1DSimulator ca1d = new ca1DSimulator(k, r, rule, sizex, 1, "5) Generador combinado 26_42");
        String s;
        h = new int[sizey];
        M = new int[k][sizey];
        t = new int[k];

        for(int i = 0; i < sizey; i++)
        { 
            for(int j = 0; j < sizex; j++)
            {
                s = ca1d.vec1[j];
                M[c.desconvert(s, k)][i]++;
                if(j == 350) //la temporal la calculamos para la celula 350
                    t[c.desconvert(s, k)]++;
            }
            ca1d.caComputation(i);
            h[i] = ca1d.hamming;
        }
    }
    
    static double mediaHamming()
    {
        double sum = 0.0;
        for(int i = 0; i < sizey; i++)
            sum += h[i];
        
        System.out.println("hamming: " + sum/(double)sizey);
        
        return sum/(double)sizey;
    }
    
    static double logk(double num, int k)
    {
    return (Math.log(num)/Math.log(k));
    }
    
    static double entropiaEsp()
    {
        double e = 0.0, p;
        for(int i = 0; i<sizey; i++)
        {
            double ep = 0.0;
            for(int j = 0; j < k; j++)
            {
                if(M[j][i] != 0)
                {
                    p = (double)M[j][i] /(double)sizex; //prob = el numero de veces q ha salido/el num d veces q podria
                    ep += p*logk(p,k);
                }
            }
            if(ep < 0)
                ep = -ep;
            e += ep;
        }
        
        System.out.println("Entropia espacial: " + e/(double)sizey);
        
        return e/(double)sizey; 
    }
    
    static double entropiaTemp()
    {
        double H = 0;
        for(int i = 0; i < k; i++)
        {
            if(t[i] != 0)
                H += ((double)t[i]/sizey)*logk((double)t[i]/sizey,k);
        }
        
        if(H != 0)
            H = -H;
        
        System.out.println("Entropia temporal: " + H + "\n");
        
        return H;
    }
    
    public static void main(String[] args)
    {
        Path p = Paths.get("C:\\Users\\Luis\\Documents\\NetBeansProjects\\Prac5MC\\src\\rules.txt");
        double suma, best = 0;
        int bestr = 0;
        
        for(int rule = 0; rule < 256; rule++)
        {
            System.out.println("Probando regla: " + rule + "...");
            simular(rule);
            double ham = mediaHamming(), esp = entropiaEsp(), temp = entropiaTemp();
            
            
            if(ham > (double)sizex/7 && esp > 0.8 && temp > 0.8)
            {
                suma = (ham/sizex) + esp + temp;
                if(suma > best)
                {
                    best = suma;
                    bestr = rule;
                }
                
                System.out.println("La regla " + rule + " ha sido aceptada");
                String str = Integer.toString(rule) + " ";
                byte[] data = str.getBytes();
                
                try (OutputStream out = new BufferedOutputStream(
                Files.newOutputStream(p, CREATE, APPEND))) {
                    out.write(data, 0, data.length);
                } catch (IOException x) {
                    System.err.println(x);
                }
            }
        }
        String str = " best: " + Integer.toString(bestr) + " ";
                byte[] data = str.getBytes();
                
                try (OutputStream out = new BufferedOutputStream(
                Files.newOutputStream(p, CREATE, APPEND))) {
                    out.write(data, 0, data.length);
                } catch (IOException x) {
                    System.err.println(x);
                }
    }
}
