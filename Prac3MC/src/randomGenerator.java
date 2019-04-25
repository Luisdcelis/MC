import java.math.BigInteger;
import java.util.Scanner;

public class randomGenerator {
        
    static double Xn;
    static BigInteger int1 = new BigInteger("0");
    static BigInteger int2 = new BigInteger("0");
    static BigInteger int3 = new BigInteger("0");
    
    public randomGenerator(){} //ctor vacio
    
    public randomGenerator(BigInteger seed)
    {
        this.Xn = seed.doubleValue();
        this.int1 = new BigInteger(seed.toString());
        this.int2 = new BigInteger(seed.toString());
        this.int3 = new BigInteger(seed.toString());
    }
    

    
    public double nextRandom26_1a()
    {
        Xn = (5*Xn)%(Math.pow(2,5));
        return Xn;
    }
    
    public double nextRandom26_1aNorm()
    {
        return (nextRandom26_1a())/Math.pow(2,5);
    }

    public double nextRandom26_1b()
    {
        Xn = (7*Xn)%(Math.pow(2,5));
        
        return Xn;
    }
    
    public double nextRandom26_1bNorm()
    {
        return (nextRandom26_1b())/Math.pow(2,5);
    }
    
    public double nextRandom26_2()
    {
        Xn = (3*Xn)%31;
        
        return Xn;
    }
    
    public double nextRandom26_2Norm()
    {
        return (nextRandom26_2())/31;
    }
    
    public double nextRandom26_3()
    {
        int1 = (int1.multiply(new BigInteger("16807"))).mod(new BigInteger("2147483647"));
        return int1.doubleValue();
    }
    
    public double nextRandom26_3Norm()
    {
        return (nextRandom26_3())/(Math.pow(2,31)-1);
    }
    
    public double nextRandom26_42()
    {
        double rndm;
        int1 = (int1.multiply(new BigInteger("157"))).mod(new BigInteger("32363"));
        int2 = (int2.multiply(new BigInteger("146"))).mod(new BigInteger("31727"));
        int3 = (int3.multiply(new BigInteger("142"))).mod(new BigInteger("31657"));
        
        rndm = (int1.subtract(int2).add(int3)).mod(new BigInteger("32362")).doubleValue();
        
        return rndm;
    }
    
    public double nextRandom26_42Norm()
    {
        return (nextRandom26_42())/32365;
    }
    
    public double nextRandomFishmanMoore1()
    {
        int1 = (int1.multiply(new BigInteger("48271"))).mod(new BigInteger("2147483647"));
        return int1.doubleValue();
    }
    
    public double nextRandomFishmanMoore1Norm()
    {
        return nextRandomFishmanMoore1()/(Math.pow(2,31)-1);
    }
    
    public double nextRandomFishmanMoore2()
    {
        int1 = (int1.multiply(new BigInteger("69621"))).mod(new BigInteger("2147483647"));
        return int1.doubleValue();
    }
    
    public double nextRandomFishmanMoore2Norm()
    {
        return nextRandomFishmanMoore2()/(Math.pow(2,31)-1);
    }
    
    public double nextRandomRandu()
    {
        int1 = (int1.multiply(new BigInteger("65539"))).mod(new BigInteger("2147483648"));
        return int1.doubleValue();

    }
    
    public double nextRandomRanduNorm()
    {
        return (nextRandomRandu())/(Math.pow(2,31));
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
        
        
        switch(op)
        {
            case 1:
                for (int i = 0; i < num; i++) {
                    System.out.println(nextRandom26_1aNorm());
                }
                break;
                
            case 2: 
                for (int i = 0; i < num; i++) {
                    System.out.println(nextRandom26_1bNorm());
                }
                break;
                
            case 3:
                for (int i = 0; i < num; i++) {
                    System.out.println(nextRandom26_2Norm());
                }
                break;
                
            case 4: 
                for (int i = 0; i < num; i++) {
                    System.out.println(nextRandom26_3Norm());
                }
                break;
                
            case 5:
                for (int i = 0; i < num; i++) {
                    System.out.println(nextRandom26_42Norm());
                }
                break;
                
            case 6:
                for (int i = 0; i < num; i++) {
                    System.out.println(nextRandomFishmanMoore1Norm());
                }
                break;
                
            case 7:
                for (int i = 0; i < num; i++) {
                    System.out.println( nextRandomRanduNorm());
                }
                break;
                
        }
    }
    
    public double nextRandomGen(String gen)
    {
        double random = 0.0;
        switch(gen)
        {
            case "1) 26 1a":
                random = nextRandom26_1aNorm(); break;
                
            case "2) 26 1b":
                random = nextRandom26_1bNorm(); break;
                
            case "3) 26 2":
                random = nextRandom26_2Norm(); break;
                
            case "4) 26 3":
                random = nextRandom26_3Norm(); break;
                
            case "5) Generador combinado 26_42":
                random = nextRandom26_42Norm(); break;
                
            case "6)  Generador Fishman and Moore1":
                random = nextRandomFishmanMoore1Norm(); break;
                
            case "7)  Generador Fishman and Moore2":
                random = nextRandomFishmanMoore2Norm(); break;
                
            case "8)  Generador Randu":
                random = nextRandomRanduNorm(); break;
        }
        
        return random;
    }
}
