public class ColorFade {

    public static void main(String[] args) {
        new ColorFade();
    }

    public ColorFade() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                } catch (InstantiationException ex) {
                } catch (IllegalAccessException ex) {
                } catch (UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
//                frame.add(new FadePane());
                frame.add(new ColorFadePane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class FadePane extends JPanel {

        private float[] fractions = new float[]{0f, 0.25f, 0.5f, 1f};
        private Color[] colors = new Color[]{Color.GREEN, Color.BLUE, Color.YELLOW, Color.RED};
        private float direction = 0.05f;
        private float progress = 0f;

        public FadePane() {
            Timer timer = new Timer(125, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (progress + direction > 1f) {
                        direction = -0.05f;
                    } else if (progress + direction < 0f) {
                        direction = 0.05f;
                    }
                    progress += direction;
                    repaint();
                }
            });
            timer.setCoalesce(true);
            timer.setRepeats(true);
            timer.start();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(100, 100);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            int width = getWidth();
            int height = getHeight();
            Color startColor = blendColors(fractions, colors, progress);
            g2d.setColor(startColor);
            g2d.fillRect(0, 0, width, height);
            g2d.dispose();
        }
    }

    public class ColorFadePane extends JPanel {

        private float[] fractions = new float[]{0f, 0.25f, 0.5f, 1f};
        private Color[] colors = new Color[]{Color.GREEN, Color.BLUE, Color.YELLOW, Color.RED};

        public ColorFadePane() {
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 100);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g.create();
            int width = getWidth();
            int height = getHeight();
            int bandWidth = width / 100;
            for (int index = 0; index < 100; index++) {
                float progress = (float)index / (float)100;
                Color color = blendColors(fractions, colors, progress);

                int x = bandWidth * index;
                int y = 0;
                g2d.setColor(color);
                g2d.fillRect(x, y, bandWidth, height);
            }
            g2d.dispose();
        }
    }

    public static Color blendColors(float[] fractions, Color[] colors, float progress) {
        Color color = null;
        if (fractions != null) {
            if (colors != null) {
                if (fractions.length == colors.length) {
                    int[] indicies = getFractionIndicies(fractions, progress);

                    float[] range = new float[]{fractions[indicies[0]], fractions[indicies[1]]};
                    Color[] colorRange = new Color[]{colors[indicies[0]], colors[indicies[1]]};

                    float max = range[1] - range[0];
                    float value = progress - range[0];
                    float weight = value / max;

                    color = blend(colorRange[0], colorRange[1], 1f - weight);
                } else {
                    throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
                }
            } else {
                throw new IllegalArgumentException("Colours can't be null");
            }
        } else {
            throw new IllegalArgumentException("Fractions can't be null");
        }
        return color;
    }

    public static int[] getFractionIndicies(float[] fractions, float progress) {
        int[] range = new int[2];

        int startPoint = 0;
        while (startPoint < fractions.length && fractions[startPoint] <= progress) {
            startPoint++;
        }

        if (startPoint >= fractions.length) {
            startPoint = fractions.length - 1;
        }

        range[0] = startPoint - 1;
        range[1] = startPoint;

        return range;
    }

    public static Color blend(Color color1, Color color2, double ratio) {
        float r = (float) ratio;
        float ir = (float) 1.0 - r;

        float rgb1[] = new float[3];
        float rgb2[] = new float[3];

        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);

        float red = rgb1[0] * r + rgb2[0] * ir;
        float green = rgb1[1] * r + rgb2[1] * ir;
        float blue = rgb1[2] * r + rgb2[2] * ir;

        if (red < 0) {
            red = 0;
        } else if (red > 255) {
            red = 255;
        }
        if (green < 0) {
            green = 0;
        } else if (green > 255) {
            green = 255;
        }
        if (blue < 0) {
            blue = 0;
        } else if (blue > 255) {
            blue = 255;
        }

        Color color = null;
        try {
            color = new Color(red, green, blue);
        } catch (IllegalArgumentException exp) {
            NumberFormat nf = NumberFormat.getNumberInstance();
            System.out.println(nf.format(red) + "; " + nf.format(green) + "; " + nf.format(blue));
            exp.printStackTrace();
        }
        return color;
    }
}
