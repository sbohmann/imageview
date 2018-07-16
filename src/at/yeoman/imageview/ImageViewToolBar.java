package at.yeoman.imageview;

import javax.swing.*;

class ImageViewToolBar {
    private final JToolBar component;
    private Runnable pasteHandler;

    ImageViewToolBar() {
        component = new JToolBar();
        addButtons();
    }

    JToolBar getComponent() {
        return component;
    }

    void setPasteHandler(Runnable pasteHandler) {
        this.pasteHandler = pasteHandler;
    }

    private void addButtons() {
        createPasteButton();
    }

    private void createPasteButton() {
        JButton pasteButton = new JButton("Paste");
        component.add(pasteButton);
        pasteButton.addActionListener(event -> pasteButtonPressed());
    }

    private void pasteButtonPressed() {
        if (pasteHandler != null) {
            pasteHandler.run();
        }
    }
}
