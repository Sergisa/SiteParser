package com.company;

import com.company.ui.Bage;
import com.company.ui.CustomInput;
import com.company.ui.TagListCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class MainForm extends JFrame {
    private final List<Tag.Type> scanningTagsList;
    private JPanel root;
    private JTextField urlTextField;
    private JButton mainActionButton;
    private JPanel panelForTagLabels;
    private JButton addTag;
    private JList<Tag> retrievedTagsListView;
    private JButton downloadAllButton;
    private JButton singleDownloadButton;
    private JScrollPane tagsListScrollPane;
    private DefaultListModel<Tag> listViewModel;
    private final List<Tag> tagsRetrievedFromPage = new ArrayList<>();
    private final String[] defaultScanningTags = new String[]{"meta", "script"};
    //Parser parser;
    Retriever retriever;

    public MainForm() {
        scanningTagsList = new ArrayList<>();
        scanningTagsList.addAll(
                Stream.of(defaultScanningTags)
                        .map(s -> Tag.Type.valueOf(s.toUpperCase()))
                        .toList()
        );

        setContentPane(root);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);

        retriever = new Retriever(scanningTagsList);
        retriever.setListener(this::addRetrievedTags);

        downloadAllButton.addActionListener(this::downloadAll);

        singleDownloadButton.addActionListener(this::downloadSelected);

        retrievedTagsListView.setCellRenderer(new TagListCellRenderer());
        retrievedTagsListView.setModel(listViewModel);

        panelForTagLabels.setLayout(new FlowLayout());
        addTag.addActionListener(this::addScaningTag);

        mainActionButton.addActionListener(this::retrieveHTMLData);

        for (Tag.Type tagType : scanningTagsList) {
            JLabel currentBadge = generateBadge(tagType.tagName);
            currentBadge.updateUI();
            currentBadge.setAlignmentX(Component.LEFT_ALIGNMENT);
            panelForTagLabels.add(currentBadge, FlowLayout.LEFT);
        }
        panelForTagLabels.updateUI();
        repaint();
    }

    private void downloadSelected(ActionEvent actionEvent) {
        List<URL> urlsToDownload = retrievedTagsListView.getSelectedValuesList().stream().map(Tag::getUrl).toList();
        makeDownload(urlsToDownload);
    }

    private void downloadAll(ActionEvent actionEvent) {
        List<URL> urlsToDownload = tagsRetrievedFromPage.stream().map(Tag::getUrl).toList();
        makeDownload(urlsToDownload);
    }

    private void makeDownload(List<URL> urlsToDownload) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выбор директории");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            DownloadManager manager = DownloadManager.newBuilder()
                    .setAllowedFormats(new String[]{"css", "js", "png"})
                    .setSaveFolder(fileChooser.getSelectedFile())
                    .build();

            if (retriever.isInterrupted() && !urlsToDownload.isEmpty()) {
                manager.download(urlsToDownload);
            }
        }
    }


    private void retrieveHTMLData(ActionEvent actionEvent) {
        retriever.setUrl(urlTextField.getText());
        retriever.updateAllowedTagsList(scanningTagsList);
        System.out.printf("Состояние потока ресивера HTML: %s \n", retriever.getState());
        if (retriever.isInterrupted()) {
            retriever.getData();
        } else {
            retriever.start();
        }
        try {
            retriever.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addRetrievedTags(List<Tag> tags) {
        tagsRetrievedFromPage.clear();
        tagsRetrievedFromPage.addAll(tags);
        listViewModel.clear();
        for (Tag tag : tags) {
            listViewModel.addElement(tag);
        }
        retriever.interrupt();
    }

    private void addScaningTag(ActionEvent actionEvent) {
        Tag.Type s = (Tag.Type) JOptionPane.showInputDialog(
                this,
                "Выберите тэг для сканирования",
                "Добавление тэга",
                JOptionPane.PLAIN_MESSAGE,
                null,
                Arrays.stream(Tag.Type.values()).filter(type -> !scanningTagsList.contains(type)).toArray(),
                null);
        if (s != null && !s.tagName.isEmpty()) {
            try {
                scanningTagsList.add(s);
                panelForTagLabels.add(generateBadge(s), panelForTagLabels.getComponents().length - 2);
                panelForTagLabels.repaint();
                panelForTagLabels.updateUI();
            } catch (IllegalArgumentException e) {
                //e.printStackTrace();
                JOptionPane optionPane = new JOptionPane("Такие тэги не сканируются", JOptionPane.ERROR_MESSAGE);
                JDialog dialog = optionPane.createDialog(this, "Ошибка");
                dialog.setAlwaysOnTop(true);
                dialog.setVisible(true);
            }
        }
    }

    private Bage generateBadge(Tag.Type tagType) {
        return generateBadge(tagType.tagName);
    }

    private Bage generateBadge(String text) {
        Bage currentBage = new Bage(text);
        currentBage.putClientProperty("tagName", text);
        currentBage.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger())
                    doPop(e);
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger())
                    doPop(e);
            }

            private void doPop(MouseEvent e) {
                PopUpMenu menu = new PopUpMenu(e.getComponent());
                menu.show(e.getComponent(), e.getX(), e.getY());
                menu.setActionListener(() -> {
                    Container parentEl = e.getComponent().getParent();
                    parentEl.remove(e.getComponent());
                    parentEl.repaint();
                    scanningTagsList.removeIf(type -> type.tagName.equalsIgnoreCase(text));
                    ((JPanel) parentEl).updateUI();
                });
            }
        });
        return currentBage;
    }

    private void createUIComponents() {
        listViewModel = new DefaultListModel<>();
        retrievedTagsListView = new JList<>(listViewModel);
        urlTextField = new CustomInput();
        tagsListScrollPane = new JScrollPane();
    }
}
