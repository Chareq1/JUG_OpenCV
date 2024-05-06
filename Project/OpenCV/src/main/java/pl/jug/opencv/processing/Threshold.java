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

public class Threshold {
    int thresh_type = 0; //Typ progowania
    int thresh_R = 0;    //Próg dla kanału R
    int thresh_G = 0;    //Próg dla kanału G
    int thresh_B = 0;    //Próg dla kanału B
    Imgproc imgProc = new Imgproc();
    Mat out = new Mat();
    Imgcodecs imgCodecs = new Imgcodecs();

    public void thresh(Mat img, Scanner scanner) throws IOException {
        ArrayList<Mat> img_channels = new ArrayList<>(3); //Kolekcja pomocnicza do przechowywania kanałów RGB obrazu wejściowego
        ArrayList<Mat> out_channels = new ArrayList<>(3); //Kolekcja pomocnicza do przechowywania kanałów RGB obrazu wyjściowego

        //Dla całego obrazu
        //threshold(Mat src, Mat dst, int thresh, int maxval, int type)
        //imgProc.threshold(img, out, 100, 255, Imgproc.THRESH_BINARY);
        Core.split(img, img_channels);  //Podzielenie obrazu wejściowego na kanały RGB

        System.out.print("\n--- PROGOWANIE ---");
        System.out.print("\n* Podaj typ progowania [0-7]: ");
        thresh_type = scanner.nextInt();
        System.out.print("* Podaj wartość progu dla R [0-255]: ");
        thresh_R = scanner.nextInt();
        System.out.print("* Podaj wartość progu dla G [0-255]: ");
        thresh_G = scanner.nextInt();
        System.out.print("* Podaj wartość progu dla B [0-255]: ");
        thresh_B = scanner.nextInt();

        out_channels.add(Mat.zeros(img_channels.get(0).size(), img_channels.get(0).type()));
        out_channels.add(Mat.zeros(img_channels.get(1).size(), img_channels.get(1).type()));
        out_channels.add(Mat.zeros(img_channels.get(2).size(), img_channels.get(2).type()));

        //Ustawienie progowania dla poszczególnych kanałów
        imgProc.threshold(img_channels.get(0), out_channels.get(0), thresh_B, 255, thresh_type);
        imgProc.threshold(img_channels.get(1), out_channels.get(1), thresh_G, 255, thresh_type);
        imgProc.threshold(img_channels.get(2), out_channels.get(2), thresh_R, 255, thresh_type);

        //Połączenie zmienionych składowych RGB do jednego obiektu (obrazu wyjściowego)
        Core.merge(out_channels, out);

        MatOfByte matOfByteOut = new MatOfByte();

        imgCodecs.imencode(".jpg", out, matOfByteOut);
        byte[] byteArrayOut = matOfByteOut.toArray();
        InputStream inOut = new ByteArrayInputStream(byteArrayOut);
        BufferedImage bufImageOut= ImageIO.read(inOut);

        JFrame frameT = new JFrame();
        frameT.getContentPane().add(new JLabel(new ImageIcon(bufImageOut)));
        frameT.pack();
        frameT.setTitle("Progowanie");
        frameT.setVisible(true);
    }
}
