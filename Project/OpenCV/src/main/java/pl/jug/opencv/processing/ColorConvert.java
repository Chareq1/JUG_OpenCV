package pl.jug.opencv.processing;

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

public class ColorConvert {
    public void convert(Mat img) throws IOException {
        Imgproc imgProc = new Imgproc();
        Imgcodecs imgCodecs = new Imgcodecs();

        Mat tmp = new Mat();
        imgProc.cvtColor(img, tmp, Imgproc.COLOR_BGR2GRAY);

        System.out.println("\n--- KONWERSJA PRZESTRZENI BARW ---");

        MatOfByte matOfByteOut = new MatOfByte();

        imgCodecs.imencode(".jpg", tmp, matOfByteOut);
        byte[] byteArrayOut = matOfByteOut.toArray();
        InputStream inOut = new ByteArrayInputStream(byteArrayOut);
        BufferedImage bufImageOut= ImageIO.read(inOut);

        JFrame frameC = new JFrame();
        frameC.getContentPane().add(new JLabel(new ImageIcon(bufImageOut)));
        frameC.pack();
        frameC.setTitle("Konwersja -> Gray");
        frameC.setVisible(true);
    }
}
