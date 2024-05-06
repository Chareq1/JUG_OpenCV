package pl.jug.opencv.processing;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static org.opencv.core.CvType.CV_8UC1;

public class ChannelSplit {
    public void splitChannels(Mat img) throws IOException {
        Imgcodecs imgCodecs = new Imgcodecs();

        System.out.println("\n--- PODZIAŁ PRZESTRZENI BARW NA KANAŁY ---");

        //Utworzenie macierzy dla poszczególnych składowych
        Mat b = new Mat();
        Mat g = new Mat();
        Mat r = new Mat();

        //Podział obrazu wejściowego na poszczególne kanały
        //(wraz z zerowaniem wartości innych kanałów)
        //NIEBIESKI
        ArrayList<Mat> bChannel = new ArrayList<>(3);
        Core.split(img, bChannel);  //Core.split(Mat src, ArrayList<Mat> dst)
        bChannel.set(1, Mat.zeros(bChannel.get(1).size(), CV_8UC1));
        bChannel.set(2, Mat.zeros(bChannel.get(2).size(), CV_8UC1));
        Core.merge(bChannel, b);  //Core.merge(ArrayList<Mat> src, Mat dst)

        MatOfByte matOfByteB = new MatOfByte();
        imgCodecs.imencode(".jpg", b, matOfByteB);
        byte[] byteArrayB = matOfByteB.toArray();
        InputStream inB = new ByteArrayInputStream(byteArrayB);
        BufferedImage bufImageB = ImageIO.read(inB);

        JFrame frameB = new JFrame();
        frameB.getContentPane().add(new JLabel(new ImageIcon(bufImageB)));
        frameB.pack();
        frameB.setTitle("Niebieski");
        frameB.setVisible(true);


        //ZIELONY
        ArrayList<Mat> gChannel = new ArrayList<>(3);
        Core.split(img, gChannel);  //Core.split(Mat src, ArrayList<Mat> dst)
        gChannel.set(0, Mat.zeros(gChannel.get(0).size(), CV_8UC1));
        gChannel.set(2, Mat.zeros(gChannel.get(2).size(), CV_8UC1));
        Core.merge(gChannel, g);  //Core.merge(ArrayList<Mat> src, Mat dst)

        MatOfByte matOfByteG = new MatOfByte();
        imgCodecs.imencode(".jpg", g, matOfByteG);
        byte[] byteArrayG = matOfByteG.toArray();
        InputStream inG = new ByteArrayInputStream(byteArrayG);
        BufferedImage bufImageG = ImageIO.read(inG);

        JFrame frameG = new JFrame();
        frameG.getContentPane().add(new JLabel(new ImageIcon(bufImageG)));
        frameG.pack();
        frameG.setTitle("Zielony");
        frameG.setVisible(true);


        //CZERWONY
        ArrayList<Mat> rChannel = new ArrayList<>(3);
        Core.split(img, rChannel);  //Core.split(Mat src, ArrayList<Mat> dst)
        rChannel.set(0, Mat.zeros(rChannel.get(0).size(), CV_8UC1));
        rChannel.set(1, Mat.zeros(rChannel.get(1).size(), CV_8UC1));
        Core.merge(rChannel, r);  //Core.merge(ArrayList<Mat> src, Mat dst);

        MatOfByte matOfByteR = new MatOfByte();
        imgCodecs.imencode(".jpg", r, matOfByteR);
        byte[] byteArrayR = matOfByteR.toArray();
        InputStream inR = new ByteArrayInputStream(byteArrayR);
        BufferedImage bufImageR = ImageIO.read(inR);

        JFrame frameR = new JFrame();
        frameR.getContentPane().add(new JLabel(new ImageIcon(bufImageR)));
        frameR.pack();
        frameR.setTitle("Czerwony");
        frameR.setVisible(true);
    }
}
