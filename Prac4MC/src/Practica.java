import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


class Lienzo extends Canvas
{
    randomGenerator r = new randomGenerator(new BigInteger("23"));
    String generador;
    int k,erre,rule,size,front;
    public int [][] M;
    public int[] vectH;
    public double[] t0;
    
    Converter c = new Converter();
    
    public void dibujar(int k, int r, int rule, int size, int front, String generador)
    {
        this.k = k;
        this.generador = generador;
        this.erre = r;
        this.rule = rule;
        this.size = size;
        this.front = front;
        this.vectH = new int[size];
        this.t0 = new double[k];
        for(int i = 0; i < k; i++)
            t0[i] = 0;
        
        M = new int[k][size];
        for(int i = 0; i < k; i++)
            for(int j = 0; j < size; j++)
                M[i][j] = 0;
        
        repaint();
    }
    
    public void paint(Graphics g)
    {
        ca1DSimulator ca1d = new ca1DSimulator(k, erre, rule, size, front, generador);
        String s;

        for(int i = 0; i < size; i++)
        { 
            for(int j = 0; j < size; j++)
            {
                s = ca1d.vec1[j];
                g.setColor(strToColor(s));
                g.fillOval(j, i, 1, 1);
                M[c.desconvert(s, k)][i]++;
                if(j == 0)
                    t0[c.desconvert(s, k)]++;
            }
            ca1d.caComputation(i+1);
            vectH[i] = ca1d.hamming;
        }
    }
    
    Color strToColor(String s)
    {
        Color c;
        switch(s)
        {
            case "0": c = Color.black; break;
            case "1": c = Color.blue; break;
            case "2": c = Color.green; break;
            case "3": c = Color.magenta; break;
            case "4": c = Color.orange; break;
            case "5": c = Color.pink; break;
            case "6": c = Color.yellow; break;
            case "7": c = new Color(153, 0, 204); break; //morado
            case "8": c = new Color(102, 26, 0); break; //marron
            case "9": c = new Color(102, 204, 255); break; //celeste
            case "A": c = new Color(0, 102, 0); break; //verde oscuro
            case "B": c = new Color(255, 102, 0); break; //naranja oscuro
            default: c = new Color(255*(int)r.nextRandomGen(generador),255*(int)r.nextRandomGen(generador),255*(int)r.nextRandomGen(generador));
        }
        return c;
    }  
}

class LienzoHam extends Canvas
{
    Converter c = new Converter();
    randomGenerator rand = new randomGenerator(new BigInteger("23"));
    int[] vectH;
    
    
    public void dibujar(int[] vectH)
    {
        this.vectH = vectH;
    }
    
    public void paint(Graphics g)
    {
        g.setColor(Color.blue);
        for(int i = 10; i < 700; i+=10)
        {
            try
            {
                g.drawLine(i-10, 198-Math.round((vectH[i-10]*198)/700), i, 198-Math.round((vectH[i]*198)/700));
            }catch(Exception e){}
        }
    }
}

class LienzoEntropiaE extends Canvas
{
    int M[][];
    int k;
    public void dibujar(int M[][], int k)
    {
        this.M = M;
        this.k = k;
    }
    
    double logk(double num, int k)
    {
    return (Math.log(num)/Math.log(k));
    }
    
    public void paint(Graphics g)
    {
        g.setColor(Color.blue);
        double sumant = 0, p;
        for(int j = 0; j < k; j++)
        {
            p = (double)M[j][0]/(double)700;
            sumant += p*logk(p,k);
        }
        for(int i = 13; i < 700; i+=13)
        {
            double sum = 0;
            for(int j = 0; j < k; j++)
            {
                p = (double)M[j][i]/(double)700;
                sum += p*logk(p,k);
            }
            g.drawLine(i-13, 198-(int)Math.round((-sumant)*198), i, 198-(int)Math.round((-sum)*198));
            sumant = sum;
        }
    }
}


class LienzoEntropiaT extends Canvas
{
    double t0[];
    int k;
    public void dibujar(double t0[], int k)
    {
        this.t0 = t0;
        this.k = k;

    }
    
    double logk(double num, int k)
    {
    return (Math.log(num)/Math.log(k));
    }
    
    public void paint(Graphics g)
    {
       
        double H = 0;
        for(int i = 0; i < k; i++)
        {
            if(t0[i] != 0)
                H += (t0[i]/700)*logk(t0[i]/700,k);
        }
        
        if(H != 0)
            H = -H;
        
        String s = "La entropia temporal de la celula 0 es "+Double.toString(H);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
        g.drawString(s, 90, 100);
    }
}

public class Practica{
    
    static Lienzo l = new Lienzo();
    static LienzoHam lH = new LienzoHam();
    static LienzoEntropiaE lE = new LienzoEntropiaE();
    static LienzoEntropiaT lT = new LienzoEntropiaT();
    

    
    
    private static void mostrar(){
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame ventana = new JFrame("Simulaciones");
	ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JDesktopPane dp = new JDesktopPane();
        ventana.setContentPane(dp);
        ventana.setSize(1407, 752);
        ventana.setLocationRelativeTo(null);
        
        
        //////*Internal frame 1: Graficos*\\\\\\

        
        JInternalFrame intF = new JInternalFrame("Graficos");
        intF.setSize(700, 700);
        intF.setResizable(true);
        intF.setClosable(false);
        
        dp.add(intF);
        
        //////*Internal frame 3: Estadisticas*\\\\\\
        
        JInternalFrame intF3 = new JInternalFrame("Estadisticas");
        intF3.setSize(700, 234);
        intF3.setLocation(701, 468);
        intF3.setResizable(true);
        intF3.setClosable(false);
        intF3.setVisible(true);

        
        dp.add(intF3);
        
        
        //////*Internal frame 2: Parametros*\\\\\\
        
        JInternalFrame intF2 = new JInternalFrame("Parametros");

        JPanel panel1 = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        JButton acerca = new JButton("Acerca de");
        acerca.addActionListener((ActionEvent e) -> {
            JFrame d = new JFrame("Acerca de...");
            JPanel p = new JPanel(true);
            JLabel l = new JLabel("Has pulsado el boton de \"Acerca de\"");
            
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.CENTER);
            
            d.add(p);
            d.add(l);
            d.setSize(250,100);
            d.setLocationRelativeTo(intF2);
            d.setVisible(true);
        });
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        //c.insets = new Insets(0, 0, 20, 20);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        panel1.add(acerca, c);
        
        JButton ayuda = new JButton("Ayuda");
        ayuda.addActionListener((ActionEvent e) -> {
            JFrame d = new JFrame("Ayuda");
            JPanel p = new JPanel(true);
            JLabel l = new JLabel("Esta es una ventana de ayuda");
            JButton b = new JButton("OK");
            b.addActionListener((ActionEvent ev) -> {
                d.dispose();
            });
            
            p.add(l);
            p.add(b);
            d.add(p);
            d.setSize(200,100);
            d.setLocationRelativeTo(intF2);
            d.setVisible(true);
        });
        c.gridx = 3;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        //c.insets = new Insets(0, 20, 20, 0);

        panel1.add(ayuda, c);
        JLabel etiq = new JLabel("Generador a usar: ");
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        panel1.add(etiq, c);
        
        String[] gener = {"1) 26 1a", "2) 26 1b", "3) 26 2", "4) 26 3",
                          "5) Generador combinado 26_42",
                          "6)  Generador Fishman and Moore1",
                          "7)  Generador Fishman and Moore2",
                          "8)  Generador Randu"};
        
        JComboBox<String> ListaGen = new JComboBox<String>(gener);
        c.gridx = 3;
        c.gridy = 1;
        c.gridwidth = 2;
        c.gridheight = 1;
        panel1.add(ListaGen, c);
        
        JLabel etiq0 = new JLabel("Funcion a usar: ");
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        panel1.add(etiq0, c);
        
        String[] gener2 = {"Distancia de Hamming",
                           "Entropia espacial",
                           "Entropia temporal"};
        
        JComboBox<String> ListaFun = new JComboBox<String>(gener2);
        ListaFun.setPrototypeDisplayValue("7)  Generador Fishman and Moore2");

        c.gridx = 3;
        c.gridy = 2;
        c.gridwidth = 2;
        c.gridheight = 1;
        panel1.add(ListaFun, c);
        
        JLabel etiq2 = new JLabel("Elija la condicion de frontera: ");
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 1;
        panel1.add(etiq2, c);
        
        String[] condic = {"Nula", "Cilindrica"};
        
        JComboBox<String> condF = new JComboBox<String>(condic);
        condF.setPrototypeDisplayValue("7)  Generador Fishman and Moore2");
        c.gridx = 3;
        c.gridy = 3;
        c.gridwidth = 2;
        c.gridheight = 1;
        panel1.add(condF, c);
        
        etiq = new JLabel("Numero de estados por celula:");
        
        SpinnerNumberModel nm = new SpinnerNumberModel();
        nm.setValue(2);
        nm.setMinimum(2);
        nm.setMaximum(36);
        
        JSpinner spin1 = new JSpinner(nm);
        spin1.setEditor(new JSpinner.NumberEditor(spin1, "       #"));
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        c.gridheight = 1;
        panel1.add(etiq, c);
        
        c.gridx = 2;
        c.gridy = 4;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        panel1.add(spin1, c);
        
        ///
        
        etiq = new JLabel("Rango de vecindad:");
        
        SpinnerNumberModel nm2 = new SpinnerNumberModel();
        nm2.setValue(1);
        nm2.setMinimum(1);
        nm2.setMaximum(999999999);
        
        JSpinner spin2 = new JSpinner(nm2);
        spin2.setEditor(new JSpinner.NumberEditor(spin2, "#"));
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        c.gridheight = 1;
        panel1.add(etiq, c);
        
        c.gridx = 2;
        c.gridy = 5;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        panel1.add(spin2, c);

        etiq = new JLabel("Funcion de transicion:");
        
        SpinnerNumberModel nm3 = new SpinnerNumberModel();
        nm3.setValue(0);
        nm3.setMinimum(0);
        nm3.setMaximum(999999999);
        
        JSpinner spin3 = new JSpinner(nm3);
        spin3.setEditor(new JSpinner.NumberEditor(spin3, "#"));
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        c.gridheight = 1;
        panel1.add(etiq, c);
        
        c.gridx = 2;
        c.gridy = 6;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        panel1.add(spin3, c);
        
        JButton para = new JButton("Dibujar");
        para.addActionListener((ActionEvent e) -> {
            
            intF3.remove(lE);
            intF3.remove(lT);
            intF3.remove(lH);
            
            String genenerador = (String) ListaGen.getSelectedItem();
            int front = condF.getSelectedIndex();
            int rule = (int) spin3.getValue();
            int size = intF.getWidth();
            int k = (int) spin1.getValue();
            int r = (int) spin2.getValue();
            l.dibujar(k, r, rule, size, front, genenerador);
            intF.add(l);
            try {
                intF.setSelected(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Practica.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        
        c.gridx = 1;
        c.gridy = 7;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        //c.anchor = GridBagConstraints.PAGE_END;
        panel1.add(para, c);
        
        JButton calc = new JButton("Calcular");
        calc.addActionListener((ActionEvent e) -> {
            
            int fun = ListaFun.getSelectedIndex();
            intF3.remove(lE);
            intF3.remove(lH);
            intF3.remove(lT);
            switch(fun)
            {
                case 0: 
                    lH.dibujar(l.vectH);
                    intF3.add(lH);
                    break;
                    
                case 1: 
                    lE.dibujar(l.M, (int)spin1.getValue());
                    intF3.add(lE);
                    break;    
                    
                case 2: 
                    lT.dibujar(l.t0, (int)spin1.getValue());
                    intF3.add(lT);
                    break;
            }
            try {
                intF3.setSelected(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Practica.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        c.gridx = 3;
        c.gridy = 7;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        panel1.add(calc, c);

        intF2.add(panel1);
        intF2.setSize(700, 467);
        intF2.setLocation(701, 0);
        intF2.setResizable(true);
        intF2.setClosable(false);
        intF2.setVisible(true);
        
        dp.add(intF2);
        

        
        
        //////*Menu*\\\\\\

	JMenuBar blackmenu = new JMenuBar();
	blackmenu.setOpaque(true);
	blackmenu.setBackground(new Color(255, 255, 255));
        blackmenu.setPreferredSize(new Dimension(200, 20));
        
        ventana.setJMenuBar(blackmenu);

	JMenu menu1 = new JMenu("Menu 1");
	menu1.setOpaque(true);
	menu1.setBackground(new Color(255, 255, 255));
        menu1.setPreferredSize(new Dimension(150, 20));
        blackmenu.add(menu1);

        JMenu menu2 = new JMenu("Menu 2");
	menu2.setOpaque(true);
	menu2.setBackground(new Color(255, 255, 255));
        menu2.setPreferredSize(new Dimension(150, 20));
        blackmenu.add(menu2);
        
        JMenuItem item1 = new JMenuItem("Primer Item del menu", KeyEvent.VK_F1);
        item1.addActionListener((ActionEvent e) -> {
            JFrame d = new JFrame("Item1");
            JPanel p = new JPanel(true);
            JLabel l = new JLabel("Has pulsado el primer item del menu");
            
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.CENTER);
            
            d.add(p);
            d.add(l);
            d.setSize(250,100);
            d.setLocationRelativeTo(ventana);
            d.setVisible(true);
        });
        menu1.add(item1);
        
        JMenuItem item2 = new JMenuItem("Primer item del menu2", KeyEvent.VK_3);
        item2.addActionListener((ActionEvent e) -> {
            JFrame d = new JFrame("Item");
            JPanel p = new JPanel(true);
            JLabel l = new JLabel("Has pulsado el primer item del menu2");
            
            l.setHorizontalAlignment(JLabel.CENTER);
    	    l.setVerticalAlignment(JLabel.CENTER);
            
            d.add(p);
            d.add(l);
            d.setSize(250,100);
            d.setLocationRelativeTo(ventana);
            d.setVisible(true);
        });
        menu2.add(item2);
        
        JMenuItem item3 = new JMenuItem("Segundo Item del menu1", KeyEvent.VK_A);
        item3.addActionListener((ActionEvent e) -> {
            JFrame d = new JFrame("Item2");
            JPanel p = new JPanel(true);
            JLabel l = new JLabel("Has pulsado el segundo item del menu");
            
            l.setHorizontalAlignment(JLabel.CENTER);
    	    l.setVerticalAlignment(JLabel.CENTER);
            
            d.add(p);
            d.add(l);
            d.setSize(250,100);
            d.setLocationRelativeTo(ventana);
            d.setVisible(true);
        });
        menu1.add(item3);
        
        JMenu submenu1 = new JMenu("Submenu");
        menu1.add(submenu1);
        
        JMenuItem subItem1 = new JMenuItem("item del submenu", KeyEvent.VK_4);
        subItem1.addActionListener((ActionEvent e) -> {
            JFrame d = new JFrame("Item del submenu");
            JPanel p = new JPanel(true);
            JLabel l = new JLabel("Has pulsado el item del submenu");
            
            l.setHorizontalAlignment(JLabel.CENTER);
    	    l.setVerticalAlignment(JLabel.CENTER);
            
            d.add(p);
            d.add(l);
            d.setSize(250,100);
            d.setLocationRelativeTo(ventana);
            d.setVisible(true);
        });
        submenu1.add(subItem1);
        
	ventana.setVisible(true);
        intF.setVisible(true);
	}

	public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                mostrar();
            }
        });
    }
}
