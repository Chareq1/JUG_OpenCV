package pl.jug.opencv;

import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import pl.jug.opencv.processing.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        OpenCV.loadLocally();
        Imgcodecs imgCodecs = new Imgcodecs();
        Imgproc imgProc = new Imgproc();
        String path = Main.class.getResource("/pingu.jpg").getPath();
        Mat img = imgCodecs.imread(path, Imgcodecs.IMREAD_COLOR);
        Mat out = new Mat();
        Scanner scanner = new Scanner(System.in);

        //KONWERSJA PRZESTRZENI BARW
        ColorConvert konwersjaBarw = new ColorConvert();
        konwersjaBarw.convert(img);

        //PODZIAŁ NA POSZCZEGÓLNE KANAŁY
        ChannelSplit kanaly = new ChannelSplit();
        kanaly.splitChannels(img);

        //KONTRAST I JASNOŚĆ
        Saturation saturacja = new Saturation();
        saturacja.saturation(img, scanner);

        //PROGOWANIE
        Threshold progowanie = new Threshold();
        progowanie.thresh(img, scanner);

        //PROGOWANIE ADAPTACYJNE
        AdaptiveThreshold progAdaptacyjne = new AdaptiveThreshold();
        progAdaptacyjne.thresh(img, scanner);

        //BINARYZACJA
        Binarization binaryzacja = new Binarization();
        binaryzacja.bin(img, scanner);

        //TRANSFORMACJE
        Transformation transformacja = new Transformation();
        transformacja.rotation(img, scanner);
        transformacja.scale(img, scanner);

        //FILTRACJA
        AvgFilter filtracja = new AvgFilter();
        filtracja.filter(img, scanner);

        //MAPA KOLORÓW
        ColorMap mapa = new ColorMap();
        mapa.colorMap(img);

        //DETEKCJA KRAWĘDZI
        Canny detekcja = new Canny();
        detekcja.cannyDetect(img, scanner);

        //EROZJA
        Erosion erozja = new Erosion();
        erozja.erosion(img, scanner);

        //DYLATACJA
        Dilation dylatacja = new Dilation();
        dylatacja.dilation(img, scanner);

        //Utworzenie macierzy bajtów
        MatOfByte matOfByteInput = new MatOfByte();
        imgCodecs.imencode(".jpg", img, matOfByteInput);
        byte[] byteArrayInput = matOfByteInput.toArray();
        InputStream inInput = new ByteArrayInputStream(byteArrayInput);
        BufferedImage bufImageInput = ImageIO.read(inInput);

        //Wyświetlenie oryginalnego obrazu
        JFrame frameInput = new JFrame();
        frameInput.getContentPane().add(new JLabel(new ImageIcon(bufImageInput)));
        frameInput.pack();
        frameInput.setTitle("Oryginał");
        frameInput.setVisible(true);
        frameInput.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}