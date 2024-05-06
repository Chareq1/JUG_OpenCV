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

public class Canny {
    Imgproc imgProc = new Imgproc();
    Imgcodecs imgCodecs = new Imgcodecs();
    Mat out = new Mat();
    int T_min;
    int T_max;

    public void cannyDetect(Mat in, Scanner scanner) throws IOException {
        Mat img = new Mat();
        Imgproc.cvtColor(in, img, Imgproc.COLOR_BGR2YCrCb);

        System.out.print("\n--- DETEKCJA KRAWĘDZI ---");
        System.out.print("\n* Podaj próg minimalny [1-100]: ");
        T_min = scanner.nextInt();

        ArrayList<Mat> imgChannels =  new ArrayList<>(3);
        Core.split(img, imgChannels);

        T_max = T_min*3;

        imgProc.Canny(imgChannels.getFirst(), out, T_min, T_max, 3);

        MatOfByte matOfByteOut = new MatOfByte();

        imgCodecs.imencode(".jpg", out, matOfByteOut);
        byte[] byteArrayOut = matOfByteOut.toArray();
        InputStream inOut = new ByteArrayInputStream(byteArrayOut);
        BufferedImage bufImageOut= ImageIO.read(inOut);

        JFrame frameR = new JFrame();
        frameR.getContentPane().add(new JLabel(new ImageIcon(bufImageOut)));
        frameR.pack();
        frameR.setTitle("Detekcja krawędzi");
        frameR.setVisible(true);
    }
}
