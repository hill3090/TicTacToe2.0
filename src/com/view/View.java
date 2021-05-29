package com.view;

import com.adapter.Adapter;
import com.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/*
    Displays the actual GUI through a JFrame with a JPanel and JButtons
 */

public class View {

    private Controller controller;
    private JFrame gui;
    private JButton[][] boxes;
    private JButton reset;
    private JLabel status;
    private String playerTurn;
    private Adapter adapter;


    public View()  {
        gui = new JFrame("Tic Tac Toe");
        boxes = new JButton[3][3];
        reset = new JButton("Reset");
        initialize();
    }

    public void setActionListener(Controller c) {
        this.adapter =  new Adapter(c, this);
        for (int row = 0; row < 3; row++)   {
            for (int col = 0; col < 3; col++)   {
                boxes[row][col].addActionListener(adapter);
            }
        }
        reset.addActionListener(adapter)    ;
    }

    public void computerClick(int row, int col) {
        boxes[row][col].doClick();
    }

    public void initialize()  {
        gui.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        gui.setTitle("Tic Tac Toe");
        gui.setResizable(false);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel gp = new JPanel(new GridLayout(3,3));
        JPanel clear = new JPanel();
        status = new JLabel("Tic Tac Toe!");
        status.setMaximumSize(new Dimension(800,200));

        clear.add(reset);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++)   {
                boxes[row][col] = new JButton();
                boxes[row][col].setText("");
                gp.add(boxes[row][col]);
            }
        }
        gui.add(clear, BorderLayout.SOUTH);
        gui.add(status, BorderLayout.NORTH);
        gui.add(gp, BorderLayout.CENTER);
        gui.setVisible(true);
    }

    public void updateView(int x, int y, char symbol, String message) {
        status.setText(message);
        boxes[x][y].setText(Character.toString(symbol));
        status.paintImmediately(status.getVisibleRect());
        boxes[x][y].setEnabled(false);
    }

    public int getX(ActionEvent e) {
        int x = 0;
        for (int row = 0; row < 3; row++) {
            for(int col = 0; col < 3; col++)    {
                if (e.getSource() == boxes[row][col]) {
                    x = row;
                }
            }
        }
        return x;
    }

    public int getY(ActionEvent e) {
        int y = 0;
        for (int row = 0; row < 3; row++) {
            for(int col = 0; col < 3; col++)    {
                if (e.getSource() == boxes[row][col]) {
                    y = col;
                }
            }
        }
        return y;
    }

    public boolean isReset(ActionEvent e)   {
        if (e.getSource() == reset) {
            return true;
        } else  {
            return false;
        }
    }

    public void reset(int x, int y, char symbol, String message)  {
        status.setText(message);
        boxes[x][y].setText(Character.toString(symbol));
        boxes[x][y].setEnabled(true);
    }

    public String getStatus()   {
        return status.getText();
    }

    public void gameWon(String message)   {
        status.setText(message);
        status.paintImmediately(status.getVisibleRect());
    }
}
