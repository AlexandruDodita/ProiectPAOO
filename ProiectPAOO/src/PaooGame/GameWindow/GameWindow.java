package PaooGame.GameWindow;

import PaooGame.Graphics.Assets;

import javax.swing.*;
import java.awt.*;

public class GameWindow {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    private JFrame wndFrame;
    private String wndTitle;
    private int wndWidth;
    private int wndHeight;

    private Canvas canvas;
    private JPanel contentPane;  // New container to hold layouts

    public GameWindow(String title, int width, int height) {
        wndTitle = title;
        wndWidth = width;
        wndHeight = height;
        wndFrame = null;
    }

    public void BuildGameWindow() {
        if (wndFrame != null) {
            return;
        }

        wndFrame = new JFrame(wndTitle);
        wndFrame.setLayout(new BorderLayout());
        wndFrame.setSize(wndWidth, wndHeight);
        wndFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wndFrame.setResizable(true);
        wndFrame.setLocationRelativeTo(null);

        // Create a JPanel as the new main container (content pane)
        contentPane = new JPanel(new BorderLayout());
        wndFrame.setContentPane(contentPane);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(wndWidth, wndHeight));
        canvas.setMaximumSize(new Dimension(wndWidth, wndHeight));
        canvas.setMinimumSize(new Dimension(wndWidth, wndHeight));

        // Add canvas to CENTER of contentPane (but this can be removed later if needed)
        wndFrame.add(canvas, BorderLayout.CENTER);
        wndFrame.pack();
        wndFrame.setVisible(true);
    }

    public void setCustomLayout(LayoutManager layout) {
        if(canvas!=null && contentPane!=null) {
            //contentPane.removeAll();

            wndFrame.setLayout(layout);
            //   contentPane.add(canvas, layout);
            wndFrame.revalidate();
            wndFrame.repaint();
        }
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    public void resetToDefaultLayout() {
        contentPane.removeAll();
        contentPane.add(canvas, BorderLayout.CENTER);
        contentPane.revalidate();
        contentPane.repaint();
    }

    public int GetWndWidth() {
        return wndWidth;
    }

    public int GetWndHeight() {
        return wndHeight;
    }
    public static int getWidth(){
        return WIDTH;
    }
    public static int getHeight(){
        return HEIGHT;
    }

    public Canvas GetCanvas() {
        return canvas;
    }

    public JFrame getWndFrame() {
        return wndFrame;
    }
}
