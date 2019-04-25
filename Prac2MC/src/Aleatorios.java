
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Luis
 */
public class Aleatorios 
{
    
    int seed, seed1, seed2, seed3;
    static double Xn, x1, x2, x3;
    
    public Aleatorios(){} //ctor vacio
    
    public Aleatorios(int seed)
    {
        this.seed = seed;
        this.Xn = seed;
    }
    
    public Aleatorios(int seed1, int seed2, int seed3)
    {
        this.seed1 = seed1;
        this.seed2 = seed2;
        this.seed3 = seed3;
        this.x1 = seed1;
        this.x2 = seed2;
        this.x3 = seed3;
    }
    
    public double nextRandom26_1a()
    {
        double rndm;
        rndm = (5*Xn)%(Math.pow(2,5));
        this.Xn = rndm;
        
        return rndm;
    }
    
    public double nextRandom26_1aNorm()
    {
        return (nextRandom26_1a())/Math.pow(2,5);
    }

    public double nextRandom26_1b()
    {
        double rndm;
        rndm = (7*Xn)%(Math.pow(2,5));
        this.Xn = rndm;
        
        return rndm;
    }
    
    public double nextRandom26_1bNorm()
    {
        return (nextRandom26_1b())/Math.pow(2,5);
    }
    
    public double nextRandom26_2()
    {
        double rndm;
        rndm = (3*Xn)%31;
        this.Xn = rndm;
        
        return rndm;
    }
    
    public double nextRandom26_2Norm()
    {
        return (nextRandom26_2())/31;
    }
    
    public double nextRandom26_3()
    {
        double rndm;
        rndm = (Math.pow(7,5)*Xn)%(Math.pow(2,31)-1);
        this.Xn = rndm;
        
        return rndm;
    }
    
    public double nextRandom26_3Norm()
    {
        return (nextRandom26_3())/(Math.pow(2,31)-1);
    }
    
    public double nextRandom26_42()
    {
        double rndm, rndm1, rndm2, rndm3;
        rndm1 = (157*x1)%32363;
        rndm2 = (146*x2)%31727;
        rndm3 = (142*x3)%31657;
        
        rndm = (rndm1 - rndm2 + rndm3)%32365;
        this.x1=rndm1;
        this.x2=rndm2;
        this.x3=rndm3;
        
        return rndm;
    }
    
    public double nextRandom26_42Norm()
    {
        return (nextRandom26_42())/32365;
    }
    
    public double nextRandomFishmanMoore()
    {
        return 0;
    }
    
    public double nextRandomFishmanMooreNorm()
    {
        return 0;
    }
    
    public double nextRandomRandu()
    {
        double rndm;
        rndm = ((Math.pow(2,8)+3)*Xn)%(Math.pow(2,15));
        this.Xn = rndm;
        
        return rndm;
    }
    
    public double nextRandomRanduNorm()
    {
        return (nextRandomRandu())/(Math.pow(2,15));
    }
    
    public void menu()
    {
        
        Scanner scan = new Scanner(System.in);

        System.out.println("Introduzca la cantidad de numeros aleatorios que desea generar");
        int num = scan.nextInt();
        
        System.out.println("Introduzca el generador que desee utilizar");
        System.out.println("1)  26.1a");
        System.out.println("2)  26.1b");
        System.out.println("3)  26.2");
        System.out.println("4)  26.3");
        System.out.println("5)  Generador combinado 26_42");
        System.out.println("6)  Generador Fishman and Moore");
        System.out.println("7)  Generador Randu");
        int op = scan.nextInt();
        
        Aleatorios al = new Aleatorios(5);
        Aleatorios al_comb = new Aleatorios(5, 8, 2);
        
        switch(op)
        {
            case 1:
                for (int i = 0; i < num; i++) {
                    System.out.println(al.nextRandom26_1aNorm());
                }
                break;
                
            case 2: 
                for (int i = 0; i < num; i++) {
                    System.out.println(al.nextRandom26_1bNorm());
                }
                break;
                
            case 3:
                for (int i = 0; i < num; i++) {
                    System.out.println(al.nextRandom26_2Norm());
                }
                break;
                
            case 4: 
                for (int i = 0; i < num; i++) {
                    System.out.println(al.nextRandom26_3Norm());
                }
                break;
                
            case 5:
                for (int i = 0; i < num; i++) {
                    System.out.println(al_comb.nextRandom26_42Norm());
                }
                break;
                
            case 6:
                for (int i = 0; i < num; i++) {
                    System.out.println(al.nextRandomFishmanMooreNorm());
                }
                break;
                
            case 7:
                for (int i = 0; i < num; i++) {
                    System.out.println(al.nextRandomRanduNorm());
                }
                break;
                
        }
    }
   
    public static void main(String[] args)
    {
        Aleatorios al = new Aleatorios();
        al.menu();
    }

}