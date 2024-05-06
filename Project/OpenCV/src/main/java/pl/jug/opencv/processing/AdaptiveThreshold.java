package pl.jug.opencv.processing;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class AdaptiveThreshold {
    int thresh_type = 0;        //Typ progowania
    int adaptive_method = 0;    //Typ metody adaptacyjnego progowania
    int block_size = 0;         //Rozmiar bloku sąsiedztwa
    double constant = 0;        //Stała

    Imgproc imgProc = new Imgproc();
    Mat out = new Mat();
    Imgcodecs imgCodecs = new Imgcodecs();

    public void thresh(Mat in, Scanner scanner) throws IOException {
        Mat img = new Mat();

        imgProc.cvtColor(in, img, Imgproc.COLOR_BGR2GRAY);

        System.out.print("\n--- PROGOWANIE ADAPTACYJNE ---");
        System.out.print("\n* Podaj typ progowania [0-7]: ");
        thresh_type = scanner.nextInt();
        System.out.print("* Podaj metodę adaptacyjnego progowania [0-1]: ");
        adaptive_method = scanner.nextInt();
        System.out.print("* Podaj rozmiar bloku (nieparzysty): ");
        block_size = scanner.nextInt();
        System.out.print("* Podaj wartość stałej do obliczeń (nieparzysty): ");
        constant = scanner.nextDouble();

        //adaptiveThreshold(Mat src, Mat dst, int maxval, int adaptiveMethod, int type, int blocksize, double c)
        //0-ADAPTIVE_THRESH_MEAN_C, 1-ADAPTIVE_THRESH_GAUSSIAN_C
        imgProc.adaptiveThreshold(img, out, 255, adaptive_method, thresh_type, block_size, constant);

        MatOfByte matOfByteOut = new MatOfByte();

        imgCodecs.imencode(".jpg", out, matOfByteOut);
        byte[] byteArrayOut = matOfByteOut.toArray();
        InputStream inOut = new ByteArrayInputStream(byteArrayOut);
        BufferedImage bufImageOut = ImageIO.read(inOut);

        JFrame frameT = new JFrame();
        frameT.getContentPane().add(new JLabel(new ImageIcon(bufImageOut)));
        frameT.pack();
        frameT.setTitle("Progowanie adaptacyjne");
        frameT.setVisible(true);
    }
}
