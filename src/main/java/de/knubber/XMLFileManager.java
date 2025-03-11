package de.knubber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.*;

public class XMLFileManager {
    private DefaultListModel<String> listModel;
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/xmldb";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "start123";

    public XMLFileManager() {
        JFrame frame = new JFrame("XML File Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // List to display uploaded files
        listModel = new DefaultListModel<>();
        JList<String> fileList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(fileList);

        // Upload button
        JButton uploadButton = new JButton("Upload XML File");
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setMultiSelectionEnabled(true);
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("XML Files", "xml"));

                int returnValue = fileChooser.showOpenDialog(frame);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File[] selectedFiles = fileChooser.getSelectedFiles();
                    for (File file : selectedFiles) {
                        listModel.addElement(file.getName());
                        saveFilePathToDatabase(file.getAbsolutePath());
                    }
                }
            }
        });

        frame.add(uploadButton, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        loadFilePathsFromDatabase();
        frame.setVisible(true);
    }

    private void saveFilePathToDatabase(String filePath) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO uploaded_files (file_path) VALUES (?)")) {
            stmt.setString(1, filePath);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadFilePathsFromDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT file_path FROM uploaded_files")) {
            while (rs.next()) {
                String filePath = rs.getString("file_path");
                String fileName = new File(filePath).getName();
                listModel.addElement(fileName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new XMLFileManager());
    }
}
