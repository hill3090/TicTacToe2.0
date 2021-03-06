package com.adapter;

import com.controller.Controller;
import com.view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
    Decouples the view from the controller. When action happens, it moves from view to adapter to controller to model.
    Model then updates view.
 */

public class Adapter implements ActionListener {

    private Controller c;
    private View v;
    private int x;
    private int y;

    public Adapter(Controller c, View v)    {
        this.c = c;
        this.v = v;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(v.isReset(e))  {
            c.updateModel();
        } else if (v.getStatus() == "Computer's Turn") {
            x = v.getX(e);
            y = v.getY(e);
            c.updateModelComputer(x, y);
        } else  {
            x = v.getX(e);
            y = v.getY(e);
            c.updateModel(x, y);
        }
    }
}
