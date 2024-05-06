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

public class Binarization {
    int thresh_type = 0;        //Typ progowania
    int bin_Y = 0;              //Wartośc progu binaryzacji

    Imgproc imgProc = new Imgproc();
    Mat out = new Mat();

    Imgcodecs imgCodecs = new Imgcodecs();

    public void bin(Mat in, Scanner scanner) throws IOException {
        Mat img = new Mat();

        //Konwersja na YCrCb
        imgProc.cvtColor(in, img, Imgproc.COLOR_BGR2YCrCb);

        System.out.print("\n--- BINARYZACJA ---");
        System.out.print("\n* Podaj typ binaryzacji [0-1]: ");
        thresh_type = scanner. nextInt();
        System.out.print("* Podaj wartość progu binaryzacji [0-255]: ");
        bin_Y = scanner.nextInt();

        //Kolekcja pomocnicza do przechowywania kanałów YCrCb
        ArrayList<Mat> imgChannels = new ArrayList<Mat>(3);
        Core.split(img, imgChannels);

        //Dokonanie operacji binaryzacji na kanale Y
        imgProc.threshold(imgChannels.get(0), out, bin_Y, 255, thresh_type);

        MatOfByte matOfByteOut = new MatOfByte();

        imgCodecs.imencode(".jpg", out, matOfByteOut);
        byte[] byteArrayOut = matOfByteOut.toArray();
        InputStream inOut = new ByteArrayInputStream(byteArrayOut);
        BufferedImage bufImageOut= ImageIO.read(inOut);

        JFrame frameB = new JFrame();
        frameB.getContentPane().add(new JLabel(new ImageIcon(bufImageOut)));
        frameB.pack();
        frameB.setTitle("Binaryzacja");
        frameB.setVisible(true);
    }
}
