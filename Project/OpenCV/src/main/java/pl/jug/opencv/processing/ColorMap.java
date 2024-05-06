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

public class ColorMap {
    Imgproc imgProc = new Imgproc();
    Imgcodecs imgCodecs = new Imgcodecs();
    Mat out = new Mat();

    public void colorMap(Mat img) throws IOException {
        Imgproc.applyColorMap(img, out, Imgproc.COLORMAP_WINTER);

        MatOfByte matOfByteOut = new MatOfByte();

        imgCodecs.imencode(".jpg", out, matOfByteOut);
        byte[] byteArrayOut = matOfByteOut.toArray();
        InputStream inOut = new ByteArrayInputStream(byteArrayOut);
        BufferedImage bufImageOut= ImageIO.read(inOut);

        JFrame frameR = new JFrame();
        frameR.getContentPane().add(new JLabel(new ImageIcon(bufImageOut)));
        frameR.pack();
        frameR.setTitle("Mapa kolorów");
        frameR.setVisible(true);
    }
}
