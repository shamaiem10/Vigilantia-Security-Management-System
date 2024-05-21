import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCode {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private String visitorName;
    private String houseNumber;
    private String contact;

    public QRCode(String visitorName, String houseNumber, String contact) {
        this.visitorName = visitorName;
        this.houseNumber = houseNumber;
        this.contact = contact;

        captureAndVerifyQRCode();
    }

    public static String readQR(String path, String charset, Map<EncodeHintType, ErrorCorrectionLevel> hints)
            throws IOException, NotFoundException {
        try (FileInputStream fis = new FileInputStream(path)) {
            BinaryBitmap binaryBitmap = new BinaryBitmap(
                    new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(fis))));
            Result result = new MultiFormatReader().decode(binaryBitmap);
            return result.getText();
        }
    }

    public static void displayImage(String imagePath) {
        JFrame frame = new JFrame();
        JLabel label = new JLabel(new ImageIcon(imagePath));
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void captureAndVerifyQRCode() {
        // Create a VideoCapture object to access the webcam
        VideoCapture capture = new VideoCapture(0);
        if (!capture.isOpened()) {
            System.out.println("Failed to open webcam.");
            return;
        }

        // Create a Mat object to store the captured frame
        Mat frame = new Mat();

        // Capture a frame from the webcam
        boolean isFrameCaptured = capture.read(frame);

        // Release the VideoCapture object
        capture.release();

        // Check if the frame is empty
        if (isFrameCaptured && !frame.empty()) {
            // Define the file path to save the image
            String filePath = System.getProperty("user.home") + "\\Desktop\\webcam_capture.jpg";
            // Save the frame as an image file
            boolean success = Imgcodecs.imwrite(filePath, frame);
            if (success) {
                System.out.println("Image saved successfully: " + filePath);

                // Encoding charset for reading QR code
                String charset = "UTF-8";

                // Configure hints for reading QR code
                Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

                // Attempt to read QR code from the saved image
                try {
                    String qrContent = readQR(filePath, charset, hints);
                    if (qrContent != null) {
                        System.out.println("Decoded QR code from webcam capture: " + qrContent);

                        // Decode the QR code from the specified path
                        String qrContentFromFile = readQR(System.getProperty("user.home") + "\\Desktop\\visitor_qr_code.jpg", charset, hints);
                        if (qrContentFromFile != null) {
                            System.out.println("Decoded QR code from file: " + qrContentFromFile);
                            if (qrContent.equals(qrContentFromFile)) {
                                System.out.println("QR Codes Match. Verification Successful.");
                                displayImage(System.getProperty("user.home") + "\\Downloads\\Untitled design.jpg");
                            } else {
                                System.out.println("QR Codes Mismatch. Verification Failed.");
                                displayImage(System.getProperty("user.home") + "\\Downloads\\Untitled design.png");
                            }
                        } else {
                            System.out.println("Failed to decode QR code from file.");
                        }
                    } else {
                        System.out.println("No QR code found in the captured image.");
                    }
                } catch (NotFoundException e) {
                    System.out.println("No QR code found. Error: " + e.getMessage());
                } catch (IOException e) {
                    System.out.println("Failed to read QR code due to IO error: " + e.getMessage());
                }
            } else {
                System.out.println("Failed to save image.");
            }
        } else {
            System.out.println("Failed to capture frame or frame is empty.");
        }
    }

    public static void main(String[] args) {
        new QRCode("John Doe", "123", "555-1234");
    }
}
