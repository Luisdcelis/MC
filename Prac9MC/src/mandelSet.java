import java.awt.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.concurrent.Executors;
import java.util.logging.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class mandelSet extends JFrame{
    static JFrame xdd = new JFrame();

    private BufferedImage Imagen;
    static int nNucAct;
    static boolean dibujito;
    private static boolean dibujar = false;
    private static int MAX_ITER = 10000;
    private static double ZOOM = 250;
    private static double desplX = 550; // 0 → se desplaza a la derecha del todo 
                                       // 1200 → se desplaza a la izquierda del todo
    private static double desplY = 310; // 0 → se desplaza a abajo del todo
                                       //600 → 0 → se desplaza a arriba del todo
    
    //para que quede centrado: ZOOM = 250, desplX = 550, desplY = 310

    public void paint(Graphics g)
    {
        g.drawImage(Imagen, 0, 0, this);
    }
    
    public static void inicia(int MaxIter, double Zoom, double desX, double desY, int nn, boolean dibujito)
    {
        mandelSet.MAX_ITER = MaxIter;
        mandelSet.ZOOM = Zoom;
        mandelSet.desplX = desX;
        mandelSet.desplY = desY;
        mandelSet.nNucAct = nn;
        mandelSet.dibujito = dibujito;
        if(dibujito == true)
            new mandelSet().setVisible(true);
        else
            new mandelSet().setVisible(false);
    }
    
    public mandelSet()
    {
        super("Conjunto de Mandelbrot");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Imagen = new BufferedImage(getWidth(), getHeight(),
                    BufferedImage.TYPE_INT_RGB);
        
        int nNuc = nNucAct;
        Thread[] hilos = new Thread[nNuc];
        int tamH = getWidth()/nNuc;
        for(int i = 0; i < nNuc; i++)
        {
            if(i != nNuc-1)
            {
                hilos[i] = new Thread(new HiloCa(i*tamH, i*tamH+tamH));
            }
            else
            {
                hilos[i] = new Thread(new HiloCa(i*tamH, getWidth()));
            }
            hilos[i].start();
        }
        for(int i = 0; i < nNuc; i++)
        {
            try {
                hilos[i].join();
            } catch (InterruptedException ex) {
                Logger.getLogger(mandelSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(dibujar == true)
        {
            System.out.println("Saved as: \"image.jpg\"");
            File outputfile = new File("image.jpg");
            try {
                ImageIO.write(Imagen, "jpg", outputfile);
            } catch (IOException ex) {
                Logger.getLogger(mandelSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    class HiloCa implements Runnable
    {
        int ini, fin;
        private double zx, zy, cX, cY, tmp;

  
        public HiloCa(int ini, int fin)
        {
            this.ini = ini;
            this.fin = fin;
        }
        
        public void run()
        {
            for(int y = 0; y < getHeight(); y++)
            {
                for(int x = ini; x < fin; x++)
                {
                    zx = zy = 0;
                    cX = (x - desplX) / ZOOM;
                    cY = (y - desplY) / ZOOM;
                    int iter = MAX_ITER;
                    while(zx*zx+zy*zy < 4 && iter > 0)
                    {
                        tmp = zx * zx - zy * zy + cX;
                        zy = 2.0 * zx * zy + cY;
                        zx = tmp;
                        iter--; 
                    }
                    if(dibujito == true)
                    {
                        Imagen.setRGB(x, y, iter | (iter << 8));
                    }
                }
            }
        }
    }
    
    public static void main(String[] args)
    {


        ImageIcon loading = new ImageIcon("ajax-loader.gif");
        xdd.add(new JLabel("cargando... ", loading, JLabel.CENTER));
        xdd.setLocation(760,400);
        xdd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        xdd.setSize(400, 300);
        
        JFrame frame = new JFrame("Opciones");
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ////////////////////////*PANEL*\\\\\\\\\\\\\\\\\\\\\\\\
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JLabel JLMaxIter = new JLabel("Máximo de iteraciones:    ");
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.insets = new Insets(10, 0, 0, 0);
        panel.add(JLMaxIter, c);
        JTextField JTMaxIter = new JTextField("10000", 7);
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        panel.add(JTMaxIter, c);
        
        JLabel JLZoom = new JLabel("Zoom: ");
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        panel.add(JLZoom, c);
        JTextField JTZoom = new JTextField("250", 7);
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        panel.add(JTZoom,c);
        
        JLabel JLdesX = new JLabel("Desplazamiento eje x: ");
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        panel.add(JLdesX, c);
        JTextField JTdesX = new JTextField("0", 7);
        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        panel.add(JTdesX,c);
        
        JLabel JLdesY = new JLabel("Desplazamiento eje y: ");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 1;
        panel.add(JLdesY, c);
        JTextField JTdesY = new JTextField("0", 7);
        c.gridx = 2;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 1;
        panel.add(JTdesY,c);
        
        JButton dibu = new JButton("Dibujar");
        dibu.addActionListener((ActionEvent e) -> {
            Executors.newSingleThreadExecutor().execute(new Runnable(){
                public void run()
                {
                    int MaxIter = Integer.parseInt(JTMaxIter.getText());
                    double Zoom = Double.parseDouble(JTZoom.getText());
                    double desX = Double.parseDouble(JTdesX.getText());
                    double desY = Double.parseDouble(JTdesY.getText());
                    int nNuc = Runtime.getRuntime().availableProcessors();
                    mandelSet.dibujar = false;

                    cargando();
                    //frame.dispose();
                    inicia(MaxIter, Zoom, desX+550, desY+310, nNuc, true);
                    xdd.dispose();
                    
                }
            });
            
        });
        
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 1;
        c.gridheight = 1;
        panel.add(dibu,c);
        
        JButton dibugu = new JButton("Dibujar y guardar");
        dibugu.addActionListener((ActionEvent e) -> {
            Executors.newSingleThreadExecutor().execute(new Runnable(){
                public void run()
                {
                    int MaxIter = Integer.parseInt(JTMaxIter.getText());
                    double Zoom = Double.parseDouble(JTZoom.getText());
                    double desX = Double.parseDouble(JTdesX.getText());
                    double desY = Double.parseDouble(JTdesY.getText());
                    int nNuc = Runtime.getRuntime().availableProcessors();
                    mandelSet.dibujar = true;
                    cargando();
                    //frame.dispose();
                    inicia(MaxIter, Zoom, desX+550, desY+310, nNuc, true);
                    xdd.dispose();
                    
                }
            });
            
        });
        
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 1;
        c.gridheight = 1;
        panel.add(dibugu,c);
        
        JButton SpeedUp = new JButton("Calcular SpeedUp");
        SpeedUp.addActionListener((ActionEvent e) -> {
            Executors.newSingleThreadExecutor().execute(new Runnable(){
                public void run()
                {

                int nNuc = Runtime.getRuntime().availableProcessors();

                int MaxIter = Integer.parseInt(JTMaxIter.getText());
                double Zoom = Double.parseDouble(JTZoom.getText());
                double desX = Double.parseDouble(JTdesX.getText());
                double desY = Double.parseDouble(JTdesY.getText());
                mandelSet.dibujar = false;

                cargando();
                long ini = System.currentTimeMillis();
                inicia(MaxIter, Zoom, desX+550, desY+310, nNuc, false);
                long tn = System.currentTimeMillis() - ini;

                System.out.println("Tiempo n nucleos: " + tn);

                ini = System.currentTimeMillis();
                inicia(MaxIter, Zoom, desX+550, desY+310, 1, false);
                long t1 = System.currentTimeMillis() - ini;

                System.out.println("Tiempo un nucleo: " + t1);
                
                xdd.dispose();
                
                float speedup = (float)t1/(float)tn;
                    System.out.println("speedUp: " +speedup);
                JOptionPane.showMessageDialog(null, "El SpeedUp es: " + speedup);

                }
                });
            
            
            
        });
        
        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 1;
        c.gridheight = 1;
        panel.add(SpeedUp,c);


        //Set the menu bar and add the label to the content pane.
        frame.getContentPane().add(panel);
        frame.setVisible(true);

        
        
    }
    
    public static void cargando()
    {

        xdd.setVisible(true);
    }
}
