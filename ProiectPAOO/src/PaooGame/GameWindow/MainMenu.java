package PaooGame.GameWindow;

import PaooGame.Game;
import PaooGame.Graphics.Assets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu {
    private Graphics g;
    private static JFrame wnd;
    private static final MainMenu instance = new MainMenu();
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 50;
    private static final int BUTTON_SPACING = 20;

    private MainMenu() {
        g = Game.getGraphicalContext();
        GameWindow gameWindow = Game.getGameWnd();
        wnd = gameWindow.getWndFrame();
    }


    public void render() {
        // Create background panel with custom painting
        // Add a semi-transparent overlay for better text readability
        // Draw game title
        JPanel backgroundPanel = backgroundInit();

        wnd.setContentPane(backgroundPanel);
        backgroundPanel.setLayout(new GridBagLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        JButton[] buttons = {
                createMenuButton("Play"),
                createMenuButton("Load Game"),
                createMenuButton("Options"),
                createMenuButton("Credits"),
                createMenuButton("Exit")
        };

        for (JButton button : buttons) {
            buttonPanel.add(button);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, BUTTON_SPACING)));
        }


        backgroundPanel.add(buttonPanel);

        JLabel versionLabel = new JLabel("Version 0.0.0");
        versionLabel.setForeground(Color.WHITE);
        versionLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);
        bottomPanel.add(versionLabel);

        backgroundPanel.add(bottomPanel, new GridBagConstraints() {{
            gridy = 1;
            anchor = GridBagConstraints.SOUTHEAST;
            insets = new Insets(0, 0, 10, 10);
        }});

        wnd.revalidate();
        wnd.repaint();
        wnd.setVisible(true);
    }


    public static MainMenu getInstance() {
        return instance;
    }

    public static JPanel backgroundInit(){
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(Assets.battleBackground, 0, 0, getWidth(), getHeight(), this);

                g.setColor(new Color(0, 0, 0, 100));
                g.fillRect(0, 0, getWidth(), getHeight());

                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 48));
                String title = "Knightly Order";
                FontMetrics fm = g.getFontMetrics();
                int titleX = (getWidth() - fm.stringWidth(title)) / 2;
                g.drawString(title, titleX, 100);
            }
        };
        return backgroundPanel;
    }

    private static JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setFont(new Font("Arial", Font.BOLD, 20));

        // Enhanced button styling
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(45, 45, 65));
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 120), 2),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        button.setOpaque(true);

        // Enhanced hover effect with gradient
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(65, 65, 95));
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(120, 120, 180), 2),
                        BorderFactory.createEmptyBorder(5, 15, 5, 15)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(45, 45, 65));
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(80, 80, 120), 2),
                        BorderFactory.createEmptyBorder(5, 15, 5, 15)
                ));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(new Color(35, 35, 55));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(new Color(45, 45, 65));
            }
        });

        switch (text) {
            case "Play":
                button.addActionListener(e -> {
                    Game.state = Game.GAME_STATE.LEVEL_SELECTION;
                    //Game.currentLevel = Game.GAME_STATE.LEVEL_ONE;
                    Game.isInMenu = false;
                    wnd.setContentPane(Game.getGameWnd().getContentPane());
                });
                break;
            case "Exit":
                button.addActionListener(e -> System.exit(0));
                break;
            case "Options": //TODO add a separate options screen which temporarily houses a button for going into CHAR_CREATION
                button.addActionListener(e -> {
                    Game.state = Game.GAME_STATE.CHAR_CREATION;
                    //Game.currentLevel = Game.GAME_STATE.CHAR_CREATION;
                    Game.isInMenu = false;
                    wnd.setContentPane(Game.getGameWnd().getContentPane());
                });
                break;
            case "Level One": //TODO add a separate options screen which temporarily houses a button for going into CHAR_CREATION
                button.addActionListener(e -> {
                    Game.setPlayerCoords(40,900);
                    Game.state = Game.GAME_STATE.LEVEL_ONE;
                    Game.currentLevel = Game.GAME_STATE.LEVEL_ONE;
                    Game.isInLevelSelection = false;
                    wnd.setContentPane(Game.getGameWnd().getContentPane());
                });
                break;
            case "Level Two":
                button.addActionListener(e -> {
                    Game.setPlayerCoords(40,900);
                    Game.state = Game.GAME_STATE.LEVEL_TWO;
                    Game.currentLevel = Game.GAME_STATE.LEVEL_TWO;
                    Game.isInLevelSelection = false;
                    wnd.setContentPane(Game.getGameWnd().getContentPane());
                });
                break;
            case "Level Three":
                button.addActionListener(e -> {
                    Game.setPlayerCoords(40,900);
                    Game.state = Game.GAME_STATE.LEVEL_THREE;
                    Game.currentLevel = Game.GAME_STATE.LEVEL_THREE;
                    Game.isInLevelSelection = false;
                    wnd.setContentPane(Game.getGameWnd().getContentPane());
                });
                break;
            default:
                //placeholder action listeners
                button.addActionListener(e -> System.out.println(text + " clicked"));
        }
        return button;
    }

    public static class LevelSelection {
        private static final String LEVEL_TITLE = "Select Level";
        private static JLabel levelDescription;

        public static void render() {
            if (wnd == null) {
                wnd = Game.getGameWnd().getWndFrame();
            }

            JPanel backgroundPanel = backgroundInit();
            wnd.setContentPane(backgroundPanel);

            // Main layout
            backgroundPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            // Create title panel
            JPanel titlePanel = new JPanel();
            titlePanel.setOpaque(false);
            JLabel titleLabel = new JLabel(LEVEL_TITLE);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
            titleLabel.setForeground(Color.WHITE);
            titlePanel.add(titleLabel);

            // Create description panel
            JPanel descriptionPanel = new JPanel();
            descriptionPanel.setOpaque(false);
            levelDescription = new JLabel("Hover over a level to see its description");
            levelDescription.setFont(new Font("Arial", Font.ITALIC, 16));
            levelDescription.setForeground(Color.WHITE);
            descriptionPanel.add(levelDescription);

            // Create button panel
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
            buttonPanel.setOpaque(false);

            // Create level buttons with descriptions
            JButton[] buttons = {
                    createLevelButton("Level One", "Begin your journey as a knight"),
                    createLevelButton("Level Two", "Face stronger enemies and prove your worth"),
                    createLevelButton("Level Three", "The final challenge awaits"),
                    createBackButton()
            };

            for (JButton button : buttons) {
                buttonPanel.add(button);
                buttonPanel.add(Box.createRigidArea(new Dimension(0, BUTTON_SPACING)));
            }

            // Add components to the background panel
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(20, 0, 20, 0);
            backgroundPanel.add(titlePanel, gbc);

            gbc.gridy = 1;
            gbc.insets = new Insets(0, 0, 20, 0);
            backgroundPanel.add(descriptionPanel, gbc);

            gbc.gridy = 2;
            gbc.insets = new Insets(0, 0, 20, 0);
            backgroundPanel.add(buttonPanel, gbc);

            wnd.revalidate();
            wnd.repaint();
            wnd.setVisible(true);
        }

        private static JButton createLevelButton(String text, String description) {
            JButton button = createMenuButton(text);

            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    levelDescription.setText(description);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    levelDescription.setText("Hover over a level to see its description");
                }
            });

            return button;
        }

        private static JButton createBackButton() {
            JButton backButton = createMenuButton("Back to Main Menu");
            backButton.addActionListener(e -> {
                Game.state = Game.GAME_STATE.MENU;
                Game.isInMenu = true;
                MainMenu.getInstance().render();
            });
            return backButton;
        }
    }
}