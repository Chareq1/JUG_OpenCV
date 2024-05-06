package pl.jug.opencv.processing;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Saturation {
    double alpha = 0;
    int beta = 0;

    Mat out = new Mat();

    public void saturation(Mat img, Scanner scanner) throws IOException {
        Imgcodecs imgCodecs = new Imgcodecs();
        out = Mat.zeros(img.size(), img.type());

        System.out.print("\n--- KONTRAST I JASNOŚĆ---");
        System.out.print("\n* Podaj wartość wskaźnika alfa [0.0-3.0]: ");
        alpha = scanner.nextDouble();
        System.out.print("* Podaj wartość wskaźnika beta [0-100]: ");
        beta = scanner.nextInt();

        img.convertTo(out, -1, alpha, beta);

        MatOfByte matOfByteOut = new MatOfByte();

        imgCodecs.imencode(".jpg", out, matOfByteOut);
        byte[] byteArrayOut = matOfByteOut.toArray();
        InputStream inOut = new ByteArrayInputStream(byteArrayOut);
        BufferedImage bufImageOut= ImageIO.read(inOut);

        JFrame frameS = new JFrame();
        frameS.getContentPane().add(new JLabel(new ImageIcon(bufImageOut)));
        frameS.pack();
        frameS.setTitle("Korekcja jasności i kontrastu");
        frameS.setVisible(true);
    }
}
