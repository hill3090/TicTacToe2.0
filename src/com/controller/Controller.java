package com.controller;

import com.model.*;

public class Controller {

    private Model model;

    public void setModel(Model model)   {
        this.model = model;
    }

    public void updateModel(int x, int y)   {
        model.updateBoard(x, y);
    }

    public void updateModelComputer(int x, int y)   {
        model.updateBoardComputer(x, y);
    }

    public void updateModel()   { model.reset(); }
}
