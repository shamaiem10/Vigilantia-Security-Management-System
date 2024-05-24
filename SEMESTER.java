
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.io.FileWriter;
import java.util.Arrays;
import java.io.IOException;
import java.util.ArrayList;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.opencv.core.*;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.Dimension;
import java.net.URI;
import java.net.URISyntaxException;


public class SEMESTER{

    private static final String RESIDENTS_FILE_PATH = "C:\\Users\\shama\\Desktop\\residents.txt";
    private static final String VISITORS_FILE_PATH = "C:\\Users\\shama\\Desktop\\visitors.txt";
    private static final String FEEDBACK_FILE_PATH = "C:\\Users\\shama\\Desktop\\feedback.txt";
    private static final String ALERTS_FILE_PATH = "C:\\Users\\shama\\Desktop\\alerts.txt";
    private static JFrame frame;



    // ArrayList to store visitor information
    private static ArrayList<Resident> residentInfoList = new ArrayList<>();
    private static ArrayList<String> visitorInfoList = new ArrayList<>();
    private static ArrayList<String> residentFeedbacks = new ArrayList<>();
    private static ArrayList<String> alerts = new ArrayList<>();

    static String visitorName;
    static String contact;
    static String houseNumber;
    public static String getVisitorName() {
        return visitorName;
    }

    public static void setVisitorName(String visitorName) {
        visitorName = visitorName;
    }

    public static String getContact() {
        return contact;
    }

    public static void setContact(String contact) {
        contact = contact;
    }

    public static String getHouseNumber() {
        return houseNumber;
    }

    public static void setHouseNumber(String houseNumber) {
        houseNumber = houseNumber;
    }

    public static void main(String[] args) {
        Management management = new Management();
        // Create files for residents and visitors
        createResidentFile("C:\\Users\\shama\\Desktop\\residents.txt");
        createVisitorFile("C:\\Users\\shama\\Desktop\\visitors.txt");
        createFeedbackFile("C:\\Users\\shama\\Desktop\\feedback.txt");
        createAlertsFile("C:\\Users\\shama\\Desktop\\alerts.txt");


        // Create a JFrame
        JFrame frame = new JFrame("SEMESTER Window");

        // Set the frame to full screen
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Create a JLabel with the image
        ImageIcon icon = new ImageIcon("C:\\Users\\shama\\Desktop\\LOGO1.png");
        JLabel background = new JLabel(icon);

        // Set the layout to BorderLayout to cover the whole window
        frame.setLayout(new BorderLayout());

        // Add the background JLabel to the center of the window
        frame.add(background, BorderLayout.CENTER);

        // Set the default close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add a MouseListener to the JFrame to listen for mouse clicks
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openPurpleWindow(frame.getSize());
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }

    private static void createResidentFile(String path) {
        File file = new File(path);

        try {
            // Create the file if it doesn't exist
            if (file.createNewFile()) {
                System.out.println("Resident file created successfully!");
            } else {
                System.out.println("Resident file already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to create resident file!");
        }
    }

    private static void createVisitorFile(String path) {
        File file = new File(path);

        try {
            // Create the file if it doesn't exist
            if (file.createNewFile()) {
                System.out.println("Visitor file created successfully!");
            } else {
                System.out.println("Visitor file already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to create visitor file!");
        }
    }

    private static void createFeedbackFile(String path) {
        File file = new File(path);

        try {
            // Create the file if it doesn't exist
            if (file.createNewFile()) {
                System.out.println("Resident feedback file created successfully!");
            } else {
                System.out.println("Resident feedback file already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to create feedback file!");
        }
    }

    private static void createAlertsFile(String path) {
        File file = new File(path);

        try {
            // Create the file if it doesn't exist
            if (file.createNewFile()) {
                System.out.println("Alerts notifications file created successfully!");
            } else {
                System.out.println("Alerts notifications file already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to create feedback file!");
        }
    }

    // Method to write visitor information to the visitors file
    private static void writeVisitorInfoToFile() {
        try {
            FileWriter writer = new FileWriter(VISITORS_FILE_PATH, true);
            for (String visitorInfo : visitorInfoList) {
                writer.write(visitorInfo + "\n\n");
            }
            writer.close();
            System.out.println("Visitor information written to file successfully!");

            visitorInfoList.clear();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to write visitor information to file!");
        }
    }

    private static void readVisitorsFromFile() {
        Scanner scanner = null;
        try {
            // Create a Scanner to read from the file
            scanner = new Scanner(new File(VISITORS_FILE_PATH));

            // Read each line and add it to the residentFeedbacks ArrayList
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                visitorInfoList.add(line);
            }

            System.out.println("Visitors details read from file successfully!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found or unable to read visitor details from file!");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    private static void writeFeedbackToFile() {
        try {
            // Create a FileWriter in append mode
            FileWriter writer = new FileWriter(FEEDBACK_FILE_PATH, true);

            // Iterate over the residentFeedbacks
            for (String feedback : residentFeedbacks) {
                // Write each feedback to the file
                writer.write(feedback + "\n\n");
            }

            writer.close();
            System.out.println("Resident feedback appended to file successfully!");

            // Clear the residentFeedbacks list after writing to file
            residentFeedbacks.clear();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to append resident feedback to file!");
        }
    }

    private static void readFeedbackFromFile() {
        Scanner scanner = null;
        try {
            // Create a Scanner to read from the file
            scanner = new Scanner(new File(FEEDBACK_FILE_PATH));

            // Read each line and add it to the residentFeedbacks ArrayList
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                residentFeedbacks.add(line);
            }

            System.out.println("Feedback information read from file successfully!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found or unable to read feedback information from file!");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    private static void writeAlertsToFile() {
        try {
            // Create a FileWriter in append mode
            FileWriter writer = new FileWriter(ALERTS_FILE_PATH, true);

            // Iterate over the residentFeedbacks
            for (String feedback : alerts) {
                // Write each feedback to the file
                writer.write(feedback + "\n\n");
            }

            writer.close();
            System.out.println("Alerts appended to file successfully!");

            // Clear the residentFeedbacks list after writing to file
            alerts.clear();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to append alerts to file!");
        }
    }

    private static void readAlertsFromFile() {
        Scanner scanner = null;
        try {
            // Create a Scanner to read from the file
            scanner = new Scanner(new File(ALERTS_FILE_PATH));

            // Read each line and add it to the residentFeedbacks ArrayList
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                alerts.add(line);
            }

            System.out.println("Alerts notifications read from file successfully!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found or unable to read alert notification from file!");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }


    public static void saveResidentToFile(Resident resident) {
        try {
            // Append the resident's information to the file
            try (FileWriter writer = new FileWriter(RESIDENTS_FILE_PATH, true)) {
                // Write resident information separated by commas
                writer.write(resident.name + "," +
                        resident.contact + "," +
                        resident.residentId + "," +
                        resident.residentPassword + "," +
                        resident.houseNumber + System.lineSeparator()); // Add newline character
                System.out.println("Resident appended to file successfully!");
            }
        } catch (IOException e) {
            System.err.println("Failed to append resident to file: " + e.getMessage());
        }
    }


    public static void readResidentsFromFile(ArrayList<Resident> residentInfoList) {
        Scanner scanner = null;
        try {
            // Create a Scanner to read from the file
            scanner = new Scanner(new File(RESIDENTS_FILE_PATH));

            // Read each line and add it to the residentInfoList
            while (scanner.hasNextLine()) {
                String[] residentInfo = scanner.nextLine().split(",");
                if (residentInfo.length == 5) { // Check if the line has all fields
                    String name = residentInfo[0].trim();
                    String contact = residentInfo[1].trim();
                    String residentId = residentInfo[2].trim();
                    String residentPassword = residentInfo[3].trim();
                    String address = residentInfo[4].trim();
                    Resident resident = new Resident(name, contact, residentId, residentPassword, address);
                    residentInfoList.add(resident);
                } else {
                    System.err.println("Invalid format for resident information: " + Arrays.toString(residentInfo));
                }
            }

            System.out.println("Residents details read from file successfully!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found or unable to read residents' details from file!");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }


    // Method to open the purple window
    private static void openPurpleWindow(Dimension size) {
        // Create a new JFrame with a purple background covering full laptop screen
        JFrame purpleFrame = new JFrame();
        purpleFrame.setSize(size); // Set size to match the main window
        purpleFrame.getContentPane().setBackground(new Color(157, 109, 175)); // Purple color code: #800080
        purpleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only the purple window when closed
        purpleFrame.setUndecorated(true); // Remove window decorations

        // Create three buttons (RESIDENT, MANAGEMENT, SECURITY)
        JButton button1 = new JButton("            RESIDENT             ");
        JButton button2 = new JButton("         MANAGEMENT       ");
        JButton button3 = new JButton("            SECURITY             ");

        // Set button background color
        button1.setBackground(new Color(250, 251, 216)); // Button background color: #FAFBD8
        button2.setBackground(new Color(250, 251, 216)); // Button background color: #FAFBD8
        button3.setBackground(new Color(250, 251, 216)); // Button background color: #FAFBD8

        // Set button size
        Dimension buttonSize = new Dimension(200, 50);
        button1.setPreferredSize(buttonSize);
        button2.setPreferredSize(buttonSize);
        button3.setPreferredSize(buttonSize);

        // Set button font style
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14); // Example: Arial, bold, size 16
        button1.setFont(buttonFont);
        button2.setFont(buttonFont);
        button3.setFont(buttonFont);

        // Add action listeners to the buttons
        button1.addActionListener(e -> openLilacWindow(size, "RESIDENT"));
        button2.addActionListener(e -> openLilacWindow(size, "MANAGEMENT"));
        button3.addActionListener(e -> openLilacWindow(size, "SECURITY"));

        // Create a JPanel to hold the buttons with BoxLayout aligned vertically
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(157, 109, 175)); // Purple color code: #800080
        buttonPanel.add(Box.createVerticalGlue()); // Add vertical glue to center buttons vertically
        buttonPanel.add(button1);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 30))); // Add space between buttons
        buttonPanel.add(button2);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 30))); // Add space between buttons
        buttonPanel.add(button3);
        buttonPanel.add(Box.createVerticalGlue()); // Add vertical glue to center buttons vertically

        // Create a JPanel to hold the buttonPanel with BoxLayout aligned horizontally
        JPanel horizontalPanel = new JPanel();
        horizontalPanel.setLayout(new BoxLayout(horizontalPanel, BoxLayout.X_AXIS));
        horizontalPanel.setBackground(new Color(157, 109, 175)); // Purple color code: #800080
        horizontalPanel.add(Box.createHorizontalGlue()); // Add horizontal glue to center buttonPanel horizontally
        horizontalPanel.add(buttonPanel);
        horizontalPanel.add(Box.createHorizontalGlue()); // Add horizontal glue to center buttonPanel horizontally

        // Add the horizontalPanel to the purpleFrame
        purpleFrame.add(horizontalPanel, BorderLayout.CENTER);

        // Make the purple window visible
        purpleFrame.setVisible(true);
    }

    // Method to open the lilac window
    private static void openLilacWindow(Dimension size, String userType) {
        //Read data from residents file
        readResidentsFromFile(residentInfoList);

        // Create a new JFrame with a lilac background covering full laptop screen
        JFrame lilacFrame = new JFrame();
        lilacFrame.setSize(size); // Set size to match the main window
        lilacFrame.getContentPane().setBackground(new Color(157, 109, 175)); // Lilac color code: #800080
        lilacFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only the lilac window when closed
        lilacFrame.setUndecorated(true); // Remove window decorations

        // Create text fields for user ID and password
        JTextField userIDField = new JTextField(30);
        JPasswordField passwordField = new JPasswordField(30);

        // Create labels for user ID and password
        JLabel userIDLabel = new JLabel("User ID:");
        JLabel passwordLabel = new JLabel("Password:");

        // Create a panel to hold the components
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(157, 109, 175)); // Lilac color code: #800080
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5); // Padding
        panel.add(userIDLabel, gbc);
        gbc.gridy++;
        panel.add(userIDField, gbc);
        gbc.gridy++;
        panel.add(passwordLabel, gbc);
        gbc.gridy++;
        panel.add(passwordField, gbc);

        // Create a button for login
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(250, 251, 216)); // Button background color: #FAFBD8
        loginButton.setPreferredSize(new Dimension(200, 50)); // Set button size
        loginButton.addActionListener(event -> {
            // Retrieve user ID and password
            String userID = userIDField.getText();
            String password = new String(passwordField.getPassword());

            // Perform login validation based on user type
            if (validateLogin(userType, userID, password, residentInfoList)) {
                // Open new lilac window with buttons if login successful and user is a resident
                if (userType.equals("RESIDENT")) {
                    openLilacWindowWithButtons(size, userType);
                } else if (userType.equals("MANAGEMENT")) {
                    openLilacWindowForManagement(size);
                } else if (userType.equals("SECURITY")) {
                    openLilacWindowForSecurity(size);
                }
                // Close the lilac window
                lilacFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(lilacFrame, "Invalid credentials. Please try again.");
            }
        });

        // Add login button to the panel
        gbc.gridy++;
        panel.add(loginButton, gbc);

        // Center the panel on the lilac frame
        lilacFrame.add(panel, BorderLayout.CENTER);

        // Center the lilac frame on the screen
        lilacFrame.setLocationRelativeTo(null);

        // Make the lilac window visible
        lilacFrame.setVisible(true);
    }

    // Method to validate login credentials
    private static boolean validateLogin(String userType, String userID, String password, ArrayList<Resident> residentInfoList) {
        // Perform login validation based on user type
        if (userType.equals("RESIDENT")) {
            // readResidentsFromFile(residentInfoList);
            // Assuming management has a method to retrieve residents
            for (Resident resident : residentInfoList) {
                if (userID.equals(resident.residentId) && password.equals(resident.residentPassword)) {
                    return true; // Resident login successful
                }
            }
        } else if (userType.equals("MANAGEMENT")) {
            // Check if user is part of management team
            return userID.equals("management") && password.equals("management");
        } else if (userType.equals("SECURITY")) {
            // Check if user is part of security team
            return userID.equals("security") && password.equals("security");
        }
        return false; // Invalid credentials or user type
    }

    // Method to open the lilac window with background image and buttons for RESIDENT
    private static void openLilacWindowWithButtons(Dimension size, String userType) {
        frame = new JFrame("Visitor Information");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        Management management1 = new Management();
        // Create a new JFrame with lilac background image covering full laptop screen
        JFrame lilacFrame = new JFrame();
        lilacFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set to full screen
        lilacFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only the lilac window when closed

        // Create a JLabel with the background image
        ImageIcon icon = new ImageIcon("C:\\Users\\shama\\Desktop\\11.jpg");
        JLabel background = new JLabel(icon);
        lilacFrame.add(background);

        // Create buttons for "NOTIFY SECURITY" and "SHARE FEEDBACK"
        JButton notifySecurityButton = createButton("     NOTIFY SECURITY  ");
        JButton shareFeedbackButton = createButton("     SHARE FEEDBACK   ");
        JButton alertNotificationsButton = createButton("ALERT NOTIFICATIONS");

        // Add action listeners to the buttons if user is resident
        if (userType.equals("RESIDENT")) {
            notifySecurityButton.addActionListener(e -> {
                // Open a new lilac screen
                JFrame newLilacScreen = new JFrame("New Lilac Screen");

                // Set lilac color as the background of the JFrame
                Color lilacColor = new Color(200, 162, 200); // Adjust RGB values as needed
                newLilacScreen.getContentPane().setBackground(lilacColor);

                // Create a panel for entering visitor details
                JPanel visitorPanel = new JPanel();
                visitorPanel.setBackground(lilacColor); // Set lilac color as panel background
                visitorPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for centering

                // Set beige color as the background of text fields
                Color beigeColor = new Color(250, 251, 216); // Adjust RGB values as needed

                // Create labels and text fields for visitor details
                JLabel nameLabel = new JLabel("Visitor Name:");
                JTextField nameField = new JTextField(20);
                nameField.setBackground(beigeColor);

                JLabel houseLabel = new JLabel("House Number:");
                JTextField houseField = new JTextField(20);
                houseField.setBackground(beigeColor);

                JLabel contactLabel = new JLabel("Contact:");
                JTextField contactField = new JTextField(20);
                contactField.setBackground(beigeColor);

                // Add components to the visitor panel using GridBagConstraints to center them
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(10, 10, 10, 10); // Padding
                gbc.anchor = GridBagConstraints.CENTER; // Center the components
                visitorPanel.add(nameLabel, gbc);
                gbc.gridy++;
                visitorPanel.add(nameField, gbc);
                gbc.gridy++;
                visitorPanel.add(houseLabel, gbc);
                gbc.gridy++;
                visitorPanel.add(houseField, gbc);
                gbc.gridy++;
                visitorPanel.add(contactLabel, gbc);
                gbc.gridy++;
                visitorPanel.add(contactField, gbc);

                // Create a send button
                JButton sendButton = new JButton("Send");
                sendButton.setBackground(beigeColor);
                // Inside the sendButton action listener in the openLilacWindowWithButtons method
                // Add action listener to the sendButton
                sendButton.addActionListener(sendAction -> {
                    // Retrieve visitor details
                    String visitorName = nameField.getText();
                    String houseNumber = houseField.getText();
                    String contact = contactField.getText();
                    if (!visitorName.matches("[a-zA-Z ]+")) {
                        JOptionPane.showMessageDialog(frame, "Name should contain alphabets only.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!contact.matches("\\d+")) {
                        JOptionPane.showMessageDialog(frame, "Contact should contain numbers only.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // Store visitor information in the ArrayList
                    String visitorInfo = "Visitor Name: " + visitorName +
                            "\nHouse Number: " + houseNumber +
                            "\nContact: " + contact + "\n_______________________________";
                    visitorInfoList.add(visitorInfo);

                    // Write visitor information to file
                    writeVisitorInfoToFile();

                    // Generate QR code
                    generateQRCode(visitorName, houseNumber, contact);

                    // Display confirmation message
                    JOptionPane.showMessageDialog(newLilacScreen, "Visitor details sent and QR code generated!");
                    // Create and display the new lilac screen
// Create a new Lilac screen

                    JFrame new1LilacScreen = new JFrame("New Lilac Screen");
                    new1LilacScreen.setUndecorated(true); // Remove window decorations
                    new1LilacScreen.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set to full screen
                    new1LilacScreen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only the new lilac screen when closed

                    // Create a JPanel with BorderLayout
                    JPanel panel = new JPanel(new BorderLayout());

                    // Set the background color to purple
                    panel.setBackground(new Color(250, 251, 216)); // This creates a purple color using RGB values

                    // Create a JLabel with the image
                    ImageIcon imageIcon = new ImageIcon("C://Users//shama//Desktop//visitor_qr_code.jpg"); // Replace with the path to your image
                    JLabel imageLabel = new JLabel(imageIcon);
                    panel.add(imageLabel, BorderLayout.CENTER);

                    // Create a share button with lilac background color
                    JButton shareButton = new JButton("Share");
                    shareButton.setBackground(new Color(200, 162, 200)); // Lilac color
                    shareButton.setPreferredSize(new Dimension(100, 40)); // Set preferred size
                    shareButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                // Load the image
                                File imageFile = new File("C://Users//shama//Desktop//visitor_qr_code.jpg");

                                // Check if the file exists
                                if (!imageFile.exists()) {
                                    JOptionPane.showMessageDialog(new1LilacScreen, "Image file not found!");
                                    return;
                                }

                                // Open WhatsApp with the image attached
                                Desktop desktop = Desktop.getDesktop();
                                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                                    // Construct the URI
                                    URI uri = new URI("whatsapp://send?text=&source=&data=&app_absent=&attachment=" + imageFile.getAbsolutePath().replace("\\", "/")); // Replace backslashes with forward slashes

                                    // Open the URI with the default application
                                    desktop.browse(uri);
                                } else {
                                    JOptionPane.showMessageDialog(new1LilacScreen, "Desktop browse action not supported!");
                                }
                            } catch (IOException | URISyntaxException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(new1LilacScreen, "Error opening WhatsApp: " + ex.getMessage());
                            }
                        }
                    });

                    // Add the share button to the bottom of the panel
                    panel.add(shareButton, BorderLayout.SOUTH);

                    // Add the panel to the content pane
                    new1LilacScreen.getContentPane().add(panel, BorderLayout.CENTER); // Add the panel to the center of the frame

                    // Make the new Lilac screen visible
                    new1LilacScreen.setVisible(true);


                });



                // Add visitor panel and send button to the new lilac screen
                newLilacScreen.add(visitorPanel, BorderLayout.CENTER);
                newLilacScreen.add(sendButton, BorderLayout.SOUTH);

                // Set the size of the new lilac screen to fit the contents
                newLilacScreen.pack();

                // Center the new lilac screen on the screen
                newLilacScreen.setLocationRelativeTo(null);

                // Make the new lilac screen visible
                newLilacScreen.setVisible(true);
            });

            UIManager.put("OptionPane.background", new Color(229, 204, 255)); // Lilac background
            UIManager.put("Panel.background", new Color(229, 204, 255)); // Lilac background
            UIManager.put("Button.background", new Color(250, 251, 216)); // Beige button

            // Create JFrame

            lilacFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            lilacFrame.setSize(400, 300);
            lilacFrame.setLayout(new FlowLayout());

            // Create JButton

            lilacFrame.add(shareFeedbackButton);

            // Add action listener to the button
            shareFeedbackButton.addActionListener(e -> {
                // Prompt user for resident's name
                String residentName = JOptionPane.showInputDialog(lilacFrame, "Enter Resident's Name:");

                // Check if the resident's name is not null or empty and contains only alphabets and spaces
                if (residentName != null && !residentName.isEmpty() && residentName.matches("[a-zA-Z\\s]+")) {
                    // Prompt user for feedback
                    String feedback = JOptionPane.showInputDialog(lilacFrame, "Enter Feedback:");

                    // Check if the feedback is not null or empty
                    if (feedback != null && !feedback.isEmpty()) {
                        // Concatenate resident's name and feedback into one string
                        String feedbackEntry = "Resident: " + residentName + "\nFeedback: " + feedback;

                        // Check if the feedback entry already exists in the list
                        if (!residentFeedbacks.contains(feedbackEntry)) {
                            // Add the feedback entry to the ArrayList residentFeedbacks
                            residentFeedbacks.add(feedbackEntry);
                            writeFeedbackToFile();

                            // Display confirmation message
                            JOptionPane.showMessageDialog(lilacFrame, "Feedback saved successfully!");
                        } else {
                            // Display message indicating that the feedback is already present
                            JOptionPane.showMessageDialog(lilacFrame, "Feedback already exists for this resident.");
                        }
                    } else {
                        // Display error message if feedback is empty
                        JOptionPane.showMessageDialog(lilacFrame, "Please enter Feedback.");
                    }
                } else {
                    // Display error message if resident name is invalid
                    JOptionPane.showMessageDialog(lilacFrame, "Please enter a valid Resident's Name.");
                }

                // Set the frame visible
                lilacFrame.setVisible(true);
            });

            alertNotificationsButton.addActionListener(e -> {
                // Read feedbacks from the file into residentFeedbacks
                readAlertsFromFile();

                // Construct a formatted message to display feedback
                StringBuilder alertMessage = new StringBuilder("                                                     ALERTS:\n\n");
                for (String alert : alerts) {
                    alertMessage.append("       ").append(alert).append("\n");
                }
                // Create a JTextArea to display the alerts
                JTextArea textArea = new JTextArea(alertMessage.toString());
                textArea.setEditable(false); // Make the text area read-only
                textArea.setFont(textArea.getFont().deriveFont(Font.BOLD)); // Set font to bold
                textArea.setForeground(Color.RED); // Set text color to red
                textArea.setAlignmentX(JComponent.CENTER_ALIGNMENT); // Center horizontally
                textArea.setAlignmentY(JComponent.CENTER_ALIGNMENT); // Center vertically

                // Wrap the text area in a JScrollPane for scrolling
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(400, 300)); // Set preferred size for the scroll pane

                // Display the scrollable feedback message
                JOptionPane.showMessageDialog(lilacFrame, scrollPane, "ALERT NOTIFICATIONS", JOptionPane.PLAIN_MESSAGE);
            });

        }
        // Create a JPanel to hold the buttons with BoxLayout aligned vertically
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false); // Make the panel transparent
        buttonPanel.add(Box.createVerticalGlue()); // Add vertical glue to center buttons vertically
        buttonPanel.add(notifySecurityButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 30))); // Add space between buttons
        buttonPanel.add(shareFeedbackButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 30))); // Add space between buttons
        buttonPanel.add(alertNotificationsButton);
        buttonPanel.add(Box.createVerticalGlue()); // Add vertical glue to center buttons vertically

        // Add the buttonPanel to the content pane with GridBagLayout
        lilacFrame.getContentPane().setLayout(new GridBagLayout());
        lilacFrame.getContentPane().setBackground(new Color(0, 0, 0, 0)); // Make the content pane transparent
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20); // Padding
        lilacFrame.getContentPane().add(buttonPanel, gbc);

        // Center the lilac frame on the screen
        lilacFrame.setLocationRelativeTo(null);

        // Make the lilac window visible
        lilacFrame.setVisible(true);

    }
    // Method to generate a QR code
    private static void generateQRCode(String visitorName, String houseNumber, String contact) {
        String data = "Visitor Name: " + visitorName +
                "\nHouse Number: " + houseNumber +
                "\nContact: " + contact;

        String path = "C://Users//shama//Desktop//visitor_qr_code.jpg"; // Set the desired path for the generated QR code image

        try {
            BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 500, 500);
            Path outputPath = Paths.get(path);
            MatrixToImageWriter.writeToPath(matrix, "jpg", outputPath);
            System.out.println("QR code successfully created at: " + outputPath);
        } catch (Exception e) {
            System.err.println("Failed to generate QR code: " + e.getMessage());
        }
    }

    private static void openLilacWindowForManagement(Dimension size) {
        Management management = new Management();
        // Create a new JFrame with lilac background image covering full laptop screen
        JFrame lilacFrame = new JFrame();
        lilacFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set to full screen
        lilacFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only the lilac window when closed

        // Create a JLabel with the background image
        ImageIcon icon = new ImageIcon("C:\\Users\\shama\\Desktop\\11.jpg");
        JLabel background = new JLabel(icon);
        lilacFrame.add(background);

        // Create text fields for resident details
        JTextField nameField = new JTextField(20);
        JTextField userIDField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JTextField houseNumberField = new JTextField(20);

        // Create labels for resident details
        JLabel nameLabel = new JLabel("Name:");
        JLabel userIDLabel = new JLabel("User ID:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel houseNumberLabel = new JLabel("House Number:");

        // Create a panel to hold the components
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(157, 109, 175)); // Lilac color code: #800080
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20); // Padding
        panel.add(nameLabel, gbc);
        gbc.gridy++;
        panel.add(nameField, gbc);
        gbc.gridy++;
        panel.add(userIDLabel, gbc);
        gbc.gridy++;
        panel.add(userIDField, gbc);
        gbc.gridy++;
        panel.add(passwordLabel, gbc);
        gbc.gridy++;
        panel.add(passwordField, gbc);
        gbc.gridy++;
        panel.add(houseNumberLabel, gbc);
        gbc.gridy++;
        panel.add(houseNumberField, gbc);

        // Create buttons for managing residents, visitor details, and resident details
        JButton addResidentButton = new JButton("       Add Resident    ");
        JButton visitorDetailsButton = new JButton("    Visitor Details       ");
        JButton residentDetailsButton = new JButton("  Resident Details      ");
        JButton FeedbackButton = new JButton("Residents Feedback ");

        // Set button colors and size
        Color beigeColor = new Color(250, 251, 216, 255); // Beige color code: #F5F5DC
        Font buttonFont = new Font("Seogu ue", Font.PLAIN, 18);
        addResidentButton.setBackground(beigeColor);
        visitorDetailsButton.setBackground(beigeColor);
        residentDetailsButton.setBackground(beigeColor);
        FeedbackButton.setBackground(beigeColor);
        addResidentButton.setFont(buttonFont);
        visitorDetailsButton.setFont(buttonFont);
        residentDetailsButton.setFont(buttonFont);
        FeedbackButton.setFont(buttonFont);

        // Add action listener to the "Add Resident" button
        // Add action listener to the "Add Resident" button
        addResidentButton.addActionListener(e -> {

            JFrame residentFrame = new JFrame("Add Resident Screen");

            // Set lilac color as the background of the JFrame
            Color lilacColor = new Color(200, 162, 200); // Adjust RGB values as needed
            residentFrame.getContentPane().setBackground(lilacColor);

            // Create a panel for entering visitor details
            JPanel residentPanel = new JPanel();
            residentPanel.setBackground(lilacColor); // Set lilac color as panel background
            residentPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for centering

            // Set beige color as the background of text fields

            // Create labels and text fields for visitor details
            JLabel nameLabel1 = new JLabel("RESIDENT NAME:");
            JTextField nameField1 = new JTextField(20);
            nameField1.setBackground(beigeColor);

            JLabel contactLabel1 = new JLabel("CONTACT:");
            JTextField contactField1 = new JTextField(20);
            contactField1.setBackground(beigeColor);

            JLabel idLabel1 = new JLabel("RESIDENT ID:");
            JTextField idField1 = new JTextField(20);
            idField1.setBackground(beigeColor);

            JLabel passwordLabel1 = new JLabel("PASSWORD:");
            JTextField passwordField1 = new JTextField(20);
            passwordField1.setBackground(beigeColor);

            JLabel houseNumberLabel1 = new JLabel("HOUSE NUMBER:");
            JTextField houseNumberField1 = new JTextField(20);
            houseNumberField1.setBackground(beigeColor);
            nameField1.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    if (!Character.isLetter(c) && c != ' ' && c != KeyEvent.VK_BACK_SPACE) {
                        e.consume();  // Ignore the event
                        JOptionPane.showMessageDialog(frame, "Invalid input! Only alphabets and spaces are allowed.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            // Add key listener to contactField1 for numbers only
            contactField1.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                        e.consume();  // Ignore the event
                        JOptionPane.showMessageDialog(frame, "Invalid input! Only numbers are allowed.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            // Add components to the visitor panel using GridBagConstraints to center them
            GridBagConstraints gbc1 = new GridBagConstraints();
            gbc1.gridx = 0;
            gbc1.gridy = 0;
            gbc1.insets = new Insets(10, 10, 10, 10); // Padding
            gbc1.anchor = GridBagConstraints.CENTER; // Center the components

            residentPanel.add(nameLabel1, gbc1);
            gbc1.gridy++;
            residentPanel.add(nameField1, gbc1);
            gbc1.gridy++;
            residentPanel.add(contactLabel1, gbc1);
            gbc1.gridy++;
            residentPanel.add(contactField1, gbc1);
            gbc1.gridy++;
            residentPanel.add(idLabel1, gbc1);
            gbc1.gridy++;
            residentPanel.add(idField1, gbc1);
            gbc1.gridy++;
            residentPanel.add(passwordLabel1, gbc1);
            gbc1.gridy++;
            residentPanel.add(passwordField1, gbc1);
            gbc1.gridy++;
            residentPanel.add(houseNumberLabel1, gbc1);
            gbc1.gridy++;
            residentPanel.add(houseNumberField1, gbc1);

            // Create a send button
            // Set UI Manager properties
            UIManager.put("Button.background", new Color(250, 251, 216)); // Beige button

// Create JButton
            JButton submitButton = new JButton("OK");
// Add action listener to the submitButton
            submitButton.addActionListener(sendAction -> {
                // Retrieve resident details
                String name = nameField1.getText();
                String contact = contactField1.getText();
                String residentId = idField1.getText();
                String password = passwordField1.getText();
                String houseNumber = houseNumberField1.getText();

                // Check if the resident with the same ID already exists
                boolean residentExists = residentInfoList.stream()
                        .anyMatch(resident -> resident.residentId.equals(residentId));

                if (residentExists) {
                    // Display a message indicating that the resident already exists
                    JOptionPane.showMessageDialog(residentFrame, "Resident with ID " + residentId + " already exists!");
                } else {
                    // Create a new Resident object with the retrieved details
                    Resident resident = new Resident(name, contact, residentId, password, houseNumber);
                    residentInfoList.add(resident);

                    // Display a confirmation message
                    JOptionPane.showMessageDialog(residentFrame, "Resident details saved successfully!");

                    // After saving the details, dispose the frame
                    residentFrame.dispose();

                    // Save the resident to the file
                    saveResidentToFile(resident); // Assuming you have a method to save the resident to a file
                }
            });


            // Add panel and submit button to frame
            residentFrame.getContentPane().add(residentPanel, BorderLayout.CENTER);
            residentFrame.getContentPane().add(submitButton, BorderLayout.SOUTH);

            residentFrame.pack();

            // Set the location of the frame to the center of the screen
            residentFrame.setLocationRelativeTo(null);

            // Display the frame
            residentFrame.setVisible(true);
        });

        visitorDetailsButton.addActionListener(e -> {
            readVisitorsFromFile();
            // Construct a formatted message to display residents' information
            StringBuilder visitorsMessage = new StringBuilder("                 VISITOR INFORMATION:\n\n");

            for (String visitor : visitorInfoList) {
                // Append the visitor's information
                visitorsMessage.append(visitor).append("\n");
            }

            // Create a JTextArea with a custom font to display the residents' information
            JTextArea textArea = new JTextArea(visitorsMessage.toString());
            Font customFont = new Font("Comic Sans MS", Font.BOLD, 14); // Custom font: Comic Sans MS, bold, size 14
            textArea.setFont(customFont);
            textArea.setEditable(false); // Make the text area read-only
            textArea.setForeground(Color.WHITE); // Set text color to white
            textArea.setBackground(new Color(157, 109, 175)); // Set background color to lilac
            textArea.setOpaque(true); // Ensure the JTextArea is opaque
            textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

            // Create a scrollbar and add the text area to it
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            UIManager.put("OptionPane.okButtonText", "OK");
            UIManager.put("OptionPane.buttonFont", customFont);
            UIManager.put("OptionPane.buttonBackground", new Color(211, 103, 196, 199));
            UIManager.put("OptionPane.buttonForeground", Color.WHITE);
            UIManager.put("Button.background", new Color(211, 103, 196, 199));
            UIManager.put("Button.foreground", Color.WHITE);

            UIManager.put("ScrollBar.thumb", UIManager.get("ScrollBar.thumb"));
            UIManager.put("ScrollBar.thumbHighlight", UIManager.get("ScrollBar.thumbHighlight"));
            UIManager.put("ScrollBar.thumbDarkShadow", UIManager.get("ScrollBar.thumbDarkShadow"));
            UIManager.put("ScrollBar.thumbShadow", UIManager.get("ScrollBar.thumbShadow"));
            UIManager.put("ScrollBar.track", UIManager.get("ScrollBar.track"));
            UIManager.put("ScrollBar.trackHighlight", UIManager.get("ScrollBar.trackHighlight"));

            // Display the panel in a JOptionPane with a custom message type
            JOptionPane.showMessageDialog(null, scrollPane, "Visitors Information", JOptionPane.PLAIN_MESSAGE);

        });

        residentDetailsButton.addActionListener(e -> {
            // Construct a formatted message to display residents' information
            StringBuilder residentsMessage = new StringBuilder("                  RESIDENT INFORMATION:\n\n");

            for (int i = 0; i < residentInfoList.size(); i++) {
                // Append the heading for each resident
                residentsMessage.append("Resident ").append(i + 1).append(":\n");

                // Append the resident's information
                residentsMessage.append(residentInfoList.get(i).toString()).append("\n");

                // Append a separating line
                residentsMessage.append("_______________________________________\n\n");
            }

            // Create a JTextArea with a custom font to display the residents' information
            JTextArea textArea = new JTextArea(residentsMessage.toString());
            Font customFont = new Font("Comic Sans MS", Font.BOLD, 14); // Custom font: Comic Sans MS, bold, size 14
            textArea.setFont(customFont);
            textArea.setEditable(false); // Make the text area read-only
            textArea.setForeground(Color.WHITE); // Set text color to white
            textArea.setBackground(new Color(157, 109, 175)); // Set background color to lilac
            textArea.setOpaque(true); // Ensure the JTextArea is opaque
            textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

            // Create a scrollbar and add the text area to it
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            UIManager.put("OptionPane.okButtonText", "OK");
            UIManager.put("OptionPane.buttonFont", customFont);
            UIManager.put("OptionPane.buttonBackground", new Color(211, 103, 196, 199));
            UIManager.put("OptionPane.buttonForeground", Color.WHITE);
            UIManager.put("Button.background", new Color(211, 103, 196, 199));
            UIManager.put("Button.foreground", Color.WHITE);

            // Display the panel in a JOptionPane with a custom message type
            JOptionPane.showMessageDialog(null, scrollPane, "Residents Information", JOptionPane.PLAIN_MESSAGE);
        });



        FeedbackButton.addActionListener(e -> {
            // Read feedbacks from the file into residentFeedbacks
            readFeedbackFromFile();

            // Construct a formatted message to display feedback
            StringBuilder feedbackMessage = new StringBuilder("                          FEEDBACKS:\n\n");
            for (String feedback : residentFeedbacks) {


                feedbackMessage.append("       ").append(feedback).append("\n\n");


            }
             // Create a JTextArea to display the feedbacks
            JTextArea textArea = new JTextArea(feedbackMessage.toString());
            textArea.setEditable(false); // Make the text area read-only
            textArea.setFont(textArea.getFont().deriveFont(Font.BOLD)); // Set font to bold
            textArea.setAlignmentX(JComponent.CENTER_ALIGNMENT); // Center horizontally
            textArea.setAlignmentY(JComponent.CENTER_ALIGNMENT); // Center vertically

            // Create a JTextArea with a custom font to display the residents' information
            Font customFont = new Font("Comic Sans MS", Font.BOLD, 14); // Custom font: Comic Sans MS, bold, size 14
            textArea.setFont(customFont);
            textArea.setEditable(false); // Make the text area read-only
            textArea.setForeground(Color.WHITE); // Set text color to white
            textArea.setBackground(new Color(157, 109, 175)); // Set background color to lilac
            textArea.setOpaque(true); // Ensure the JTextArea is opaque
            textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

            // Create a scrollbar and add the text area to it
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setPreferredSize(new Dimension(400, 300));

            // Display the panel in a JOptionPane with a custom message type
            JOptionPane.showMessageDialog(null, scrollPane, "FEEDBACKS", JOptionPane.PLAIN_MESSAGE);


        });



        // Create a JPanel to hold the buttons with BoxLayout aligned vertically
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false); // Make the panel transparent
        buttonPanel.add(Box.createVerticalGlue()); // Add vertical glue to center buttons vertically
        buttonPanel.add(addResidentButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 30))); // Add space between buttons
        buttonPanel.add(visitorDetailsButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 30))); // Add space between buttons
        buttonPanel.add(residentDetailsButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 30))); // Add space between buttons
        buttonPanel.add(FeedbackButton);
        buttonPanel.add(Box.createVerticalGlue()); // Add vertical glue to center buttons vertically


        // Add the buttonPanel to the content pane with GridBagLayout
        lilacFrame.getContentPane().setLayout(new GridBagLayout());
        lilacFrame.getContentPane().setBackground(new Color(0, 0, 0, 0)); // Make the content pane transparent
        GridBagConstraints bc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20); // Padding
        lilacFrame.getContentPane().add(buttonPanel, gbc);

        // Center the lilac frame on the screen
        lilacFrame.setLocationRelativeTo(null);

        // Make the lilac window visible
        lilacFrame.setVisible(true);
    }

    private static void openLilacWindowForSecurity(Dimension size) {
        // Create a new JFrame with lilac background covering full laptop screen
        JFrame lilacFrame = new JFrame();
        lilacFrame.setSize(size); // Set size to match the main window
        lilacFrame.getContentPane().setBackground(new Color(157, 109, 175)); // Lilac color code: #800080
        lilacFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only the lilac window when closed
        lilacFrame.setUndecorated(true); // Remove window decorations

        // Create buttons for "Notifications" and "Scan QR"

        JButton scanQRButton = createButton("         Scan QR           ");
        JButton sendAlertButton = createButton("       Send Alerts       ");

        // Add action listeners to the buttons





        scanQRButton.addActionListener(e -> {
            QRCode.captureAndVerifyQRCode();
        });
        UIManager.put("OptionPane.background", new Color(229, 204, 255)); // Lilac background
        UIManager.put("Panel.background", new Color(229, 204, 255)); // Lilac background
        UIManager.put("Button.background", new Color(250, 251, 216)); // Beige button

        // Create JFrame

        lilacFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        lilacFrame.setSize(400, 300);
        lilacFrame.setLayout(new FlowLayout());

        // Create JButton

        lilacFrame.add(sendAlertButton);


        sendAlertButton.addActionListener(e -> {
            // Prompt user for alert message
            String alertMessage = JOptionPane.showInputDialog(lilacFrame, "Enter Alert Message:");

            // Check if the alert message is not null or empty
            if (alertMessage != null && !alertMessage.isEmpty()) {
                // Capture the current time
                LocalDateTime currentTime = LocalDateTime.now();

                // Format the current time
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedTime = currentTime.format(formatter);

                // Construct the alert entry including the current time
                String alertEntry = "Alert at " + formattedTime + ":\n" + alertMessage;

                // Check if the alert entry already exists in the list
                if (!alerts.contains(alertEntry)) {
                    // Add the alert entry to the alerts list
                    alerts.add(alertEntry);
                    writeAlertsToFile();

                    // Display confirmation message
                    JOptionPane.showMessageDialog(lilacFrame, "Alert notification sent!");
                }
            } else {
                // Display error message if the alert message is empty
                JOptionPane.showMessageDialog(lilacFrame, "Please enter an alert message.");
            }
        });

        // Create a JPanel to hold the buttons with BoxLayout aligned vertically
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(157, 109, 175)); // Purple color code: #800080
        buttonPanel.add(Box.createVerticalGlue()); // Add vertical glue to center buttons vertically
        buttonPanel.add(scanQRButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 30))); // Add space between buttons
        buttonPanel.add(sendAlertButton);
        buttonPanel.add(Box.createVerticalGlue()); // Add vertical glue to center buttons vertically

        // Create a JPanel to hold the buttonPanel with BoxLayout aligned horizontally
        JPanel horizontalPanel = new JPanel();
        horizontalPanel.setLayout(new BoxLayout(horizontalPanel, BoxLayout.X_AXIS));
        horizontalPanel.setBackground(new Color(157, 109, 175)); // Purple color code: #800080
        horizontalPanel.add(Box.createHorizontalGlue()); // Add horizontal glue to center buttonPanel horizontally
        horizontalPanel.add(buttonPanel);
        horizontalPanel.add(Box.createHorizontalGlue()); // Add horizontal glue to center buttonPanel horizontally

        // Add the horizontalPanel to the lilacFrame
        lilacFrame.add(horizontalPanel, BorderLayout.CENTER);

        // Center the lilac frame on the screen
        lilacFrame.setLocationRelativeTo(null);

        // Make the lilac window visible
        lilacFrame.setVisible(true);
    }
    private static BufferedImage Mat2BufferedImage(Mat m) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (m.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = m.channels() * m.cols() * m.rows();
        byte[] b = new byte[bufferSize];
        m.get(0, 0, b);

        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);

        if (type == BufferedImage.TYPE_BYTE_GRAY) {
            WritableRaster raster = image.getRaster();
            raster.setDataElements(0, 0, m.cols(), m.rows(), b);
        } else if (type == BufferedImage.TYPE_3BYTE_BGR) {
            // Reorder bytes for BufferedImage
            for (int i = 0; i < m.cols() * m.rows(); i++) {
                byte b1 = b[i * 3];
                byte b2 = b[i * 3 + 1];
                byte b3 = b[i * 3 + 2];
                int rgb = ((b1 & 0xFF) << 16) | ((b2 & 0xFF) << 8) | (b3 & 0xFF);
                image.setRGB(i % m.cols(), i / m.cols(), rgb);
            }
        }

        return image;
    }

    // Method to create a button with specified text and background color
    private static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(250, 251, 216, 255)); // Lilac color code: #800080
        button.setForeground(Color.BLACK); // Set text color to white
        button.setPreferredSize(new Dimension(200, 50)); // Set button size
        button.setFont(new Font("Seoge ui", Font.BOLD, 16)); // Set button font
        return button;
    }


}

