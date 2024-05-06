package pl.jug.opencv.processing;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static org.opencv.imgproc.Imgproc.INTER_LINEAR;

public class Transformation {
    double angle=0;
    double scale=0;
    Imgproc imgProc = new Imgproc();
    Imgcodecs imgCodecs = new Imgcodecs();

    Mat out = new Mat();

    public void rotation(Mat img, Scanner scanner) throws IOException {
        Size size = img.size();   //Rozmiar

        System.out.print("\n--- ROTACJA ---");
        System.out.print("\n* Podaj kąt obrotu [0-360]: ");
        angle = scanner.nextDouble();

        size.width = img.cols();
        size.height = img.rows();

        Point rotationPoint = new Point(size.width/2., size.height/2.);
        Mat rotationMatrix = imgProc.getRotationMatrix2D(rotationPoint, angle, 1.0);

        imgProc.warpAffine(img, out, rotationMatrix, size);

        MatOfByte matOfByteOut = new MatOfByte();

        imgCodecs.imencode(".jpg", out, matOfByteOut);
        byte[] byteArrayOut = matOfByteOut.toArray();
        InputStream inOut = new ByteArrayInputStream(byteArrayOut);
        BufferedImage bufImageOut= ImageIO.read(inOut);

        JFrame frameR = new JFrame();
        frameR.getContentPane().add(new JLabel(new ImageIcon(bufImageOut)));
        frameR.pack();
        frameR.setTitle("Rotacja");
        frameR.setVisible(true);
    }

    public void scale(Mat img, Scanner scanner) throws IOException {
        System.out.print("\n--- SKALOWANIE ---");
        System.out.print("\n* Podaj procent skalowania [1-*]: ");
        scale = scanner.nextDouble();

        scale = (scale+1)/100;

        Size size=new Size(img.cols()*scale, img.rows()*scale);

        imgProc.resize(img, out, size, 0, 0, INTER_LINEAR);

        MatOfByte matOfByteOut = new MatOfByte();

        imgCodecs.imencode(".jpg", out, matOfByteOut);
        byte[] byteArrayOut = matOfByteOut.toArray();
        InputStream inOut = new ByteArrayInputStream(byteArrayOut);
        BufferedImage bufImageOut= ImageIO.read(inOut);

        JFrame frameR = new JFrame();
        frameR.getContentPane().add(new JLabel(new ImageIcon(bufImageOut)));
        frameR.pack();
        frameR.setTitle("Skalowanie");
        frameR.setVisible(true);
    }
}
