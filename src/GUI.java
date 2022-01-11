import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GUI extends JPanel implements ActionListener {

    JFrame frame;
    Timer time;

    //The array to be sorted
    private int[] arr = new int[25];

    //Counters used in the sorting
    private int cur1, cur2;
    private int cur3; //only used for selection sort
    private int key; //only used for insertion sort

    private boolean sorted = false;
    private Map<String, Boolean> algos = new HashMap<>();


    public GUI(String name) {
        frame = new JFrame(name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 550);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.add(this);
        this.setBackground(Color.BLACK);

        time = new Timer(30, this);
        time.start();

        algos.put("Bubble Sort", false);
        algos.put("Selection Sort", false);
        algos.put("Insertion Sort", false);

        setMenuBar();
    }

    /*
     * Creating the menu bar and its functions to pick which
     * sorting algorithm you would like to show
     */
    private void setMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu algorithms = new JMenu("Sorting Algorithms");
        menuBar.add(algorithms);

        JMenuItem bubbleSort = new JMenuItem("Bubble Sort");
        algorithms.add(bubbleSort);
        class BubbleSortAction implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                newArray();
                sorted = false;
                cur1 = 0;
                cur2 = 0;
                for(String algo : algos.keySet()) {
                    if(algo.equals("Bubble Sort"))
                        algos.put(algo, true);
                    else
                        algos.put(algo, false);
                }
            }
        }
        bubbleSort.addActionListener(new BubbleSortAction());

        JMenuItem selectionSort = new JMenuItem("Selection Sort");
        algorithms.add(selectionSort);
        class SelectionSortAction implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                newArray();
                sorted = false;
                cur1 = 0;
                cur2 = 1;
                cur3 = 0;
                for(String algo : algos.keySet()) {
                    if(algo.equals("Selection Sort"))
                        algos.put(algo, true);
                    else
                        algos.put(algo, false);
                }
            }
        }
        selectionSort.addActionListener(new SelectionSortAction());

        JMenuItem insertionSort = new JMenuItem("Insertion Sort");
        algorithms.add(insertionSort);
        class InsertionSortAction implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                newArray();
                sorted = false;
                cur1 = 1;
                cur2 = 0;
                key = 0;
                for(String algo : algos.keySet()) {
                    if(algo.equals("Insertion Sort"))
                        algos.put(algo, true);
                    else
                        algos.put(algo, false);
                }
            }
        }
        insertionSort.addActionListener(new InsertionSortAction());
    }

    public void actionPerformed(ActionEvent action) {
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Deciding which algorithm to run
        for(String algo : algos.keySet()) {
            if(algo.equals("Bubble Sort") && algos.get(algo)) {
                drawBubbleSort(g);
            } else if(algo.equals("Selection Sort") && algos.get(algo)) {
                drawSelectionSort(g);
            } else if(algo.equals("Insertion Sort") && algos.get(algo)) {
                drawInsertionSort(g);
            }
        }
    }

    /*
     * Filling the array with random values from 50 to 450
     * so that they fit on the screen
     */
    private void newArray() {
        Random rand = new Random();
        for(int i = 0; i < 25; i++)
            arr[i] = rand.nextInt(401) + 50;
    }

    /*
     * Every time the counters iterate through the array during
     * bubble sort, this method draws the current state of the
     * sort to the JPanel
     */
    private void drawBubbleSort(Graphics g) {
        if(!sorted) {
            if(bubbleSort()) {
                sorted = true;
            } else {
                for(int i = 0; i < arr.length; i++) {
                    if(i == arr.length - cur1)
                        g.setColor(Color.RED);
                    else if(i == cur2)
                        g.setColor(Color.GREEN);
                    else
                        g.setColor(Color.WHITE);

                    g.fillRect(25 * i + 4, arr[i], 20 - 2, 500 - arr[i]);
                }
            }
        } else {
            for(int i = 0; i < arr.length; i++) {
                g.setColor(Color.GREEN);
                g.fillRect(25 * i + 4, arr[i], 20 - 2, 500 - arr[i]);
            }
        }
    }

    /*
     * Does one iteration of the bubble sort algorithm
     */
    private boolean bubbleSort() {
        if(cur1 < arr.length) {
            if(cur2 < arr.length - cur1 - 1) {
                if(arr[cur2] < arr[cur2+1]) {
                    int temp = arr[cur2];
                    arr[cur2] = arr[cur2+1];
                    arr[cur2+1] = temp;
                }
                cur2++;
            } else {
                cur1++;
                cur2 = 0;
            }
            return false;
        } else {
            return true;
        }
    }

    /*
     * Every time the counters iterate through the array during
     * selection sort, this method draws the current state of the
     * sort to the JPanel
     */
    private void drawSelectionSort(Graphics g) {
        if(!sorted) {
            if(selectionSort()) {
                sorted = true;
            } else {
                for(int i = 0; i < arr.length; i++) {
                    if(i == cur1)
                        g.setColor(Color.RED);
                    else if(i == cur2)
                        g.setColor(Color.GREEN);
                    else if(i == cur3)
                        g.setColor(Color.YELLOW);
                    else
                        g.setColor(Color.WHITE);

                    g.fillRect(25 * i + 4, arr[i], 20 - 2, 500 - arr[i]);
                }
            }
        } else {
            for(int i = 0; i < arr.length; i++) {
                g.setColor(Color.GREEN);
                g.fillRect(25 * i + 4, arr[i], 20 - 2, 500 - arr[i]);
            }
        }
    }

    /*
     * Does one iteration of the selection sort algorithm
     */
    private boolean selectionSort() {
        if(cur1 < arr.length - 1) {
            if(cur2 < arr.length) {
                if(arr[cur2] > arr[cur3])
                    cur3 = cur2;
                cur2++;
            } else {
                int temp = arr[cur3];
                arr[cur3] = arr[cur1];
                arr[cur1] = temp;

                cur1++;
                cur3 = cur1;
                cur2 = cur1 + 1;
            }
            return false;
        } else {
            return true;
        }
    }

    /*
     * Every time the counters iterate through the array during
     * insertion sort, this method draws the current state of the
     * sort to the JPanel
     */
    private void drawInsertionSort(Graphics g) {
        if(!sorted) {
            if(insertionSort()) {
                sorted = true;
            } else {
                for(int i = 0; i < arr.length; i++) {
                    if(i == cur1)
                        g.setColor(Color.RED);
                    else if(i == cur2)
                        g.setColor(Color.GREEN);
                    else
                        g.setColor(Color.WHITE);

                    g.fillRect(25 * i + 4, arr[i], 20 - 2, 500 - arr[i]);
                }
            }
        } else {
            for(int i = 0; i < arr.length; i++) {
                g.setColor(Color.GREEN);
                g.fillRect(25 * i + 4, arr[i], 20 - 2, 500 - arr[i]);
            }
        }
    }

    /*
     * Does one iteration of the insertion sort algorithm
     */
    private boolean insertionSort() {
        if(cur1 < arr.length) {
            if(cur2 >= 0 && arr[cur2] < key) {
                arr[cur2 + 1] = arr[cur2];
                cur2--;
            } else {
                arr[cur2 + 1] = key;

                cur1++;
                if(cur1 < arr.length) {
                    key = arr[cur1];
                    cur2 = cur1 - 1;
                }
            }
            return false;
        } else {
            return true;
        }
    }
}