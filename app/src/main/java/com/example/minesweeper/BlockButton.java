package com.example.minesweeper;

import android.content.Context;
import android.widget.Button;

import androidx.annotation.ColorInt;

public class BlockButton extends Button {
    boolean mine = false;
    boolean flag = false;
    int neighborMines = 0;
    static int flags = 0;

    public BlockButton(Context context, int x, int y) {
        super(context);
    }

    public void toggleFlag(){
        if (this.flag){
            this.flag = false;
            flags--;
            this.setText("");
        }

        else{
            this.flag = true;
            flags++;
            this.setText("f");
        }
    }

    public boolean breakBlock(){
        if (this.mine){
            this.setText("@");
//        this.setBackgroundColor(4);
            return true;
        }
        else{
            this.setText("!");
            return false;
        }
    }


    public boolean isMine() {
        return mine;
    }
    public void setMine(boolean mine) {
        this.mine = mine;
    }
    public boolean isFlag() {
        return flag;
    }
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    public int getNeighborMines() {
        return neighborMines;
    }
    public void setNeighborMines(int neighborMines) {
        this.neighborMines = neighborMines;
    }
}
