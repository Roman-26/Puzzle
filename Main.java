package com.company;



import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {

            PuzzleEx puzzle = new PuzzleEx();
            puzzle.setVisible(true);
        });
    }

    static class  MyButton extends JButton {

        private boolean isLastButton;

        public MyButton() {

            super();

            initUI();
        }

        public MyButton(Image image) {

            super(new ImageIcon(image));

            initUI();
        }

        private void initUI() {

            isLastButton = false;
            BorderFactory.createLineBorder(Color.gray);

            addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent e) {
                    setBorder(BorderFactory.createLineBorder(Color.yellow));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBorder(BorderFactory.createLineBorder(Color.gray));
                }
            });
        }

        public void setLastButton() {

            isLastButton = true;
        }

        public boolean isLastButton() {

            return isLastButton;
        }
    }

    public static class PuzzleEx extends JFrame {

        private JPanel panel;
        private BufferedImage source;
        private BufferedImage resized;
        private Image image;
        private MyButton lastButton;
        private int width, height;

        private java.util.List<MyButton> buttons;
        private java.util.List<Point> solution;

        private final int NUMBER_OF_BUTTONS = 16;
        private final int DESIRED_WIDTH = 600;


        public PuzzleEx() {

            initUI();
        }

        JButton but = new JButton("Picture_START");



        private void initUI() {

            solution = new ArrayList<>();

            solution.add(new Point(0, 0));
            solution.add(new Point(0, 1));
            solution.add(new Point(0, 2));
            solution.add(new Point(1, 0));
            solution.add(new Point(1, 1));
            solution.add(new Point(1, 2));
            solution.add(new Point(2, 0));
            solution.add(new Point(2, 1));
            solution.add(new Point(2, 2));
            solution.add(new Point(3, 0));
            solution.add(new Point(3, 1));
            solution.add(new Point(3, 2));
            solution.add(new Point(3, 3));

            buttons = new ArrayList<>();

            panel = new JPanel();
            panel.setBorder(BorderFactory.createLineBorder(Color.gray));
            panel.setLayout(new GridLayout(4, 4, 0, 0));

            add(but,BorderLayout.PAGE_START);

            but.addActionListener(new SortPicture());


            try {
                source = loadImage();
                int h = getNewHeight(source.getWidth(), source.getHeight());
                resized = resizeImage(source, DESIRED_WIDTH, h,
                        BufferedImage.TYPE_INT_ARGB);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Could not load image", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            width = resized.getWidth(null);
            height = resized.getHeight(null);

            add(panel, BorderLayout.CENTER);

            for (int i = 0; i < 4; i++) {

                for (int j = 0; j < 4; j++) {

                    image = createImage(new FilteredImageSource(resized.getSource(),
                            new CropImageFilter(j * width / 4, i * height / 4,
                                    (width / 4), height / 4)));

                    MyButton button = new MyButton(image);
                    button.putClientProperty("position", new Point(i, j));

                    if (i == 3 && j == 3) {

                        lastButton = new MyButton();
                        lastButton.setBorderPainted(false);
                        lastButton.setContentAreaFilled(false);
                        lastButton.setLastButton();
                        lastButton.putClientProperty("position", new Point(i, j));
                    } else {

                        buttons.add(button);
                    }
                }
            }

            Collections.shuffle(buttons);
            buttons.add(lastButton);

            for (int i = 0; i < NUMBER_OF_BUTTONS; i++) {

                MyButton btn = buttons.get(i);
                panel.add(btn);
                btn.setBorder(BorderFactory.createLineBorder(Color.gray));
                btn.addActionListener(new ClickAction());
            }

            pack();

            setTitle("Puzzle Came");
            setResizable(false);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
        }




        private int getNewHeight(int w, int h) {

            double r = DESIRED_WIDTH / (double) w;
            int newHeight = (int) (h * r);
            return newHeight;
        }




        private BufferedImage loadImage() throws IOException {

            BufferedImage bimg = ImageIO.read(new File("porshe.jpg"));

            return bimg;
        }




        private BufferedImage resizeImage(BufferedImage originalImage, int width,
                                          int height, int type) {

            BufferedImage resizedImage = new BufferedImage(width, height, type);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(originalImage, 0, 0, width, height, null);
            g.dispose();

            return resizedImage;
        }




        private class SortPicture implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                solution = new ArrayList<>();

                solution.add(new Point(0, 0));
                solution.add(new Point(0, 1));
                solution.add(new Point(0, 2));
                solution.add(new Point(1, 0));
                solution.add(new Point(1, 1));
                solution.add(new Point(1, 2));
                solution.add(new Point(2, 0));
                solution.add(new Point(2, 1));
                solution.add(new Point(2, 2));
                solution.add(new Point(3, 0));
                solution.add(new Point(3, 1));
                solution.add(new Point(3, 2));
                solution.add(new Point(3, 3));

                buttons = new ArrayList<>();

                panel = new JPanel();
                panel.setBorder(BorderFactory.createLineBorder(Color.gray));
                panel.setLayout(new GridLayout(4, 4, 0, 0));



                try {
                    source = loadImage();
                    int h = getNewHeight(source.getWidth(), source.getHeight());
                    resized = resizeImage(source, DESIRED_WIDTH, h,
                            BufferedImage.TYPE_INT_ARGB);

                } catch (IOException ex) {
                    System.out.println("Error");
                }

                width = resized.getWidth(null);
                height = resized.getHeight(null);

                add(panel, BorderLayout.CENTER);

                for (int i = 0; i < 4; i++) {

                    for (int j = 0; j < 4; j++) {

                        image = createImage(new FilteredImageSource(resized.getSource(),
                                new CropImageFilter(j * width / 4, i * height / 4,
                                        (width / 4), height / 4)));

                        MyButton button = new MyButton(image);
                        button.putClientProperty("position", new Point(i, j));

                        if (i == 3 && j == 3) {

                            lastButton = new MyButton();
                            lastButton.setBorderPainted(false);
                            lastButton.setContentAreaFilled(false);
                            lastButton.setLastButton();
                            lastButton.putClientProperty("position", new Point(i, j));
                        } else {

                            buttons.add(button);
                        }
                    }
                }

                buttons.add(lastButton);

                for (int i = 0; i < NUMBER_OF_BUTTONS; i++) {

                    MyButton btn = buttons.get(i);
                    panel.add(btn);
                    btn.setBorder(BorderFactory.createLineBorder(Color.gray));
                    btn.addActionListener(new ClickAction());
                }




                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLocationRelativeTo(null);
            }
        }





        private class ClickAction extends AbstractAction {

            @Override
            public void actionPerformed(ActionEvent e) {

                checkButton(e);
                checkSolution();
            }



            private void checkButton(ActionEvent e) {

                int lidx = 0;

                for (MyButton button : buttons) {
                    if (button.isLastButton()) {
                        lidx = buttons.indexOf(button);
                    }
                }

                JButton button = (JButton) e.getSource();
                int bidx = buttons.indexOf(button);

                if ((bidx - 1 == lidx) || (bidx + 1 == lidx)
                        || (bidx - 4 == lidx)
                        || (bidx + 4 == lidx)
                )
                {
                    Collections.swap(buttons, bidx, lidx);
                    updateButtons();
                }
            }

            private void updateButtons() {

                panel.removeAll();

                for (JComponent btn : buttons) {

                    panel.add(btn);
                }

                panel.validate();
            }
        }

        private void checkSolution() {

            ArrayList current = new ArrayList<Point>();

            for (JComponent btn : buttons) {
                current.add((Point) btn.getClientProperty("position"));
            }

            if (compareList(solution, current)) {
                JOptionPane.showMessageDialog(panel, "Finished",
                        "Congratulation", JOptionPane.INFORMATION_MESSAGE);
            }
        }



        public  boolean compareList(List ls1, List ls2) {

            return ls1.toString().contentEquals(ls2.toString());
        }

    }}

