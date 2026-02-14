package com.salat.kanbash.output.gui;

import com.salat.kanbash.content.ContentAdapter;
import com.salat.kanbash.content.WindowState;

import javax.swing.*;
import java.awt.*;

public class WindowStateManager {
    private final ContentAdapter contentAdapter;

    private Rectangle lastNormalLocation = null;

    public WindowStateManager(ContentAdapter contentAdapter) {
        this.contentAdapter = contentAdapter;
    }

    public void onWindowMoved(JFrame mainFrame) {
        rememberWindowState(mainFrame);
    }

    public void onWindowResized(JFrame mainFrame) {
        rememberWindowState(mainFrame);
    }

    private void rememberWindowState(JFrame mainFrame) {
        if (!mainFrame.isVisible()) {
            return;
        }

        if (mainFrame.getExtendedState() == Frame.NORMAL) {
            lastNormalLocation = mainFrame.getBounds();
        }

        var windowState = new WindowState();
        windowState.extendedState = mainFrame.getExtendedState();
        if (windowState.extendedState == Frame.NORMAL) {
            windowState.location = mainFrame.getBounds();
        } else {
            windowState.location = lastNormalLocation;
        }

        contentAdapter.setLastWindowState(windowState);
    }

    public void resetWindowState(JFrame mainFrame) {
        var lastWindowState = contentAdapter.getLastWindowState();
        if (lastWindowState == null || lastWindowState.location == null) {
            return;
        }
        mainFrame.setBounds(lastWindowState.location);
        mainFrame.setExtendedState(lastWindowState.extendedState);
    }
}
