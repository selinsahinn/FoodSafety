package com.FoodSafetyProject.FoodSafety.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QrCodeGenerator {

    // QR kodu görseli üretme metodu
    public static void generateQrCodeImage(String qrText, String filePath) throws WriterException, IOException {
        int width = 250;
        int height = 250;

        // QR kodu oluşturuluyor
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrText, BarcodeFormat.QR_CODE, width, height);

        // Görsel oluşturuluyor
        BufferedImage qrImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        qrImage.createGraphics();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                qrImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        // QR kod görselini kaydetme
        File qrFile = new File(filePath);
        qrFile.getParentFile().mkdirs(); // Klasör yoksa oluştur
        ImageIO.write(qrImage, "png", qrFile);
    }
}
