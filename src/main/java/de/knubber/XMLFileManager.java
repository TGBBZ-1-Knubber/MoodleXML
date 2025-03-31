package de.knubber;

import de.knubber.utils.XMLHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class XMLFileManager {
    private DefaultListModel<String> listModel;
    private Map<String, String> filePathMap;
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/xmldb";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "start123";

    public XMLFileManager() {
        JFrame frame = new JFrame("XML File Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());
        listModel = new DefaultListModel<>();
        filePathMap = new HashMap<>();
        JList<String> fileList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(fileList);

        fileList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = fileList.locationToIndex(e.getPoint());
                    if (index != -1) {
                        String fileName = listModel.getElementAt(index);
                        String filePath = filePathMap.get(fileName);
                        onFileDoubleClick(filePath);
                    }
                }
            }
        });

        JButton uploadButton = new JButton("Upload XML File");
        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setMultiSelectionEnabled(true);
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("XML Files", "xml"));

            int returnValue = fileChooser.showOpenDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File[] selectedFiles = fileChooser.getSelectedFiles();
                for (File file : selectedFiles) {
                    listModel.addElement(file.getName());
                    filePathMap.put(file.getName(), file.getAbsolutePath());
                    saveFilePathToDatabase(file.getAbsolutePath());
                }
            }
        });
         var searchField = new JTextField("Suchen", 15);
        searchField.setForeground(Color.GRAY);
        searchField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Suchen") && searchField.getForeground().equals(Color.GRAY)) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Suchen");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchList(searchField.getText());
            }
        });
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(uploadButton);
        topPanel.add(searchField);
        frame.add(topPanel, BorderLayout.NORTH);
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
                filePathMap.put(fileName, filePath);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void onFileDoubleClick(String filePath) {
        var file = new File(filePath);
        try {
            XMLHandler.handleXML(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void searchList(String text) {
        listModel.clear();
        if (text.isEmpty() || text.equals("Suchen")) {
            loadFilePathsFromDatabase();
            return;
        }
        for (Map.Entry<String, String> entry : filePathMap.entrySet()) {
            String fileName = entry.getKey();
            if (fileName.toLowerCase().contains(text.toLowerCase())) {
                listModel.addElement(fileName);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(XMLFileManager::new);
    }
}
