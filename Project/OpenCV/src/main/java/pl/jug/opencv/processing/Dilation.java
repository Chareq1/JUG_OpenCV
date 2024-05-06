package pl.jug.opencv.processing;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Dilation {
    Imgproc imgProc = new Imgproc();
    Imgcodecs imgCodecs = new Imgcodecs();
    Mat out = new Mat();
    int elementType = 0;    //Typ elementu
    int baseSize = 0;

    public void dilation(Mat img, Scanner scanner) throws IOException {
        Mat kernel = new Mat();

        System.out.print("\n--- DYLATACJA ---");
        System.out.print("\n* Podaj typ elementu [0-2]: ");
        elementType = scanner.nextInt();
        System.out.print("* Podaj wielkość bazową elementu [0-11]: ");
        baseSize = scanner.nextInt();

        if(elementType== 0)
            kernel = imgProc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2*baseSize + 1, 2*baseSize+1), new Point(baseSize, baseSize));
        else if(elementType == 1)
            kernel = imgProc.getStructuringElement(Imgproc.MORPH_CROSS, new Size(2*baseSize + 1, 2*baseSize+1), new Point(baseSize, baseSize));
        else if(elementType == 2)
            kernel = imgProc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(2*baseSize + 1, 2*baseSize+1), new Point(baseSize, baseSize));

        imgProc.dilate(img, out, kernel, new Point(-1,-1), 1, Core.BORDER_CONSTANT);

        MatOfByte matOfByteOut = new MatOfByte();

        imgCodecs.imencode(".jpg", out, matOfByteOut);
        byte[] byteArrayOut = matOfByteOut.toArray();
        InputStream inOut = new ByteArrayInputStream(byteArrayOut);
        BufferedImage bufImageOut= ImageIO.read(inOut);

        JFrame frameR = new JFrame();
        frameR.getContentPane().add(new JLabel(new ImageIcon(bufImageOut)));
        frameR.pack();
        frameR.setTitle("Dylatacja");
        frameR.setVisible(true);
    }
}