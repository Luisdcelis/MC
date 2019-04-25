import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
public class Mandelbrot extends JFrame {
  private final int MAX_ITER = 100000;
  private final double ZOOM = 150;
  private BufferedImage Imagen;

  private double zx, zy, cX, cY, tmp;
  public Mandelbrot() {
    super("Conjunto de Mandelbrot");
    setBounds(100, 100, 800, 600);
    setResizable(false);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    Imagen = new BufferedImage(getWidth(), getHeight(),
   	   BufferedImage.TYPE_INT_RGB);
   //aqui comienza la rutina a paralelizar
   for (int y = 0; y < getHeight(); y++) {
     for (int x = 0; x < getWidth(); x++) {
     	     zx = zy = 0;
     	     cX = (x - 400) / ZOOM;
     	     cY = (y - 300) / ZOOM;
     	     int iter = MAX_ITER;
     	     while (zx * zx + zy * zy < 4 && iter > 0) {
     	     	     tmp = zx * zx - zy * zy + cX;
     	     	     zy = 2.0 * zx * zy + cY;
     	     	     zx = tmp;
     	     	     iter--;
     	     }
     	     Imagen.setRGB(x, y, iter | (iter << 8));
     }
   }//aqui finaliza la rutina a paralelizar
  }
  public void paint(Graphics g) {
    g.drawImage(Imagen, 0, 0, this);
  }
  public static void main(String[] args) {
  	  new Mandelbrot().setVisible(true);
  }
}
