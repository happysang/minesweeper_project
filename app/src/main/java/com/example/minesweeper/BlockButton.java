package com.example.minesweeper;

import android.content.Context;
import android.widget.Button;

public class BlockButton extends Button {
    boolean mine = false;
    boolean flag = false;
    boolean breakState = false;
    static int answer = 0;
    static int flags = 0;
    int neighborMines;
    


    public BlockButton(Context context, int x, int y) {
        super(context);
    }

    public void toggleFlag(){
        // 플래그 10개를 다 꽂은 경우 취소만 가능
        if(flags==10){
            if(this.flag){
                this.flag = false;
                flags--;
                this.setText("");
                if(this.mine) answer++;
                return;
            }
            else return;
        }

        // break가 된 버튼은 플래그를 꽂을 수 없다.
        if(breakState){
            return;
        }

        else if (this.flag){
            this.flag = false;
            flags--;
            this.setText("");
            if(this.mine) answer--;
        }

        else{
            this.flag = true;
            flags++;
            this.setText("\uD83D\uDEA9");
            if(this.mine) answer++;
        }
    }


    public boolean breakBlock(){
        this.breakState = true;
        if (this.mine){
            this.setText("\uD83D\uDCA3");
            return true;
        }
        else if(this.neighborMines == 0){
            this.setBackgroundColor(4);
            return false;
        }
        else{
            this.setText( Integer.toString(neighborMines));
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
