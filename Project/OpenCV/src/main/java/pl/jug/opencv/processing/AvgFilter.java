package pl.jug.opencv.processing;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
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

public class AvgFilter {
    int windowSize = 1;
    Imgproc imgProc = new Imgproc();
    Imgcodecs imgCodecs = new Imgcodecs();
    Mat out = new Mat();

    public void filter(Mat img, Scanner scanner) throws IOException {
        System.out.print("\n--- FILTRACJA---");
        System.out.print("\n* Podaj wielkość okna [1-10]: ");
        windowSize = scanner.nextInt();

        int filterMask=(2*windowSize)+1;

        imgProc.blur(img, out, new Size(filterMask, filterMask));

        MatOfByte matOfByteOut = new MatOfByte();

        imgCodecs.imencode(".jpg", out, matOfByteOut);
        byte[] byteArrayOut = matOfByteOut.toArray();
        InputStream inOut = new ByteArrayInputStream(byteArrayOut);
        BufferedImage bufImageOut= ImageIO.read(inOut);

        JFrame frameS = new JFrame();
        frameS.getContentPane().add(new JLabel(new ImageIcon(bufImageOut)));
        frameS.pack();
        frameS.setTitle("Filtracja uśredniająca");
        frameS.setVisible(true);
    }
}
