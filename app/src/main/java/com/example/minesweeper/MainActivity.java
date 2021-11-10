package com.example.minesweeper;

import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //테이블 만들고 행 9개 만듬
        TableLayout table;
        table = findViewById(R.id.tableLayout);
        List<TableRow> tableRows = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            tableRows.add(new TableRow(this));
            table.addView(tableRows.get(i));
        }


        //행 안에 넣을 수 있게 layout 만듬
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1.0f);


        //버튼 81개 만들고 행 안에 넣음
        BlockButton[][] buttons = new BlockButton[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                buttons[i][j] = new BlockButton(this, i, j);
                buttons[i][j].setLayoutParams(layoutParams);
                tableRows.get(i).addView(buttons[i][j]);
            }
        }


        // 랜덤으로 폭탄 10개 생성
        ArrayList<String> bombs = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            Random r = new Random();
            int x = r.nextInt(9);
            int y = r.nextInt(9);
            String stringPair = String.valueOf(x) + String.valueOf(y);
            if (bombs.contains(stringPair)) {
                i--;
                continue;
            }
            bombs.add(stringPair);
            buttons[x][y].setMine(true);
        }

        // 편리하게 주변 폭탄의 갯수를 계산을 위해 11*11 버튼 배열을 만든다.
        BlockButton[][] fakeButtons = new BlockButton[11][11];
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                fakeButtons[i][j] = new BlockButton(this, i, j);
            }
        }

        // fakeButtons 에서 폭탄이 있는 부분에 mine을 true로 설정한다.
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (buttons[i][j].isMine()) fakeButtons[i + 1][j + 1].setMine(true);
            }
        }

        // 주변에 있는 폭탄의 수를 계산한다.
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                int count = 0;
                if (fakeButtons[i][j].mine) continue;
                if (fakeButtons[i-1][j-1].mine) count++;
                if (fakeButtons[i][j-1].mine) count++;
                if (fakeButtons[i+1][j-1].mine) count++;
                if (fakeButtons[i-1][j].mine) count++;
                if (fakeButtons[i+1][j].mine) count++;
                if (fakeButtons[i-1][j+1].mine) count++;
                if (fakeButtons[i][j+1].mine) count++;
                if (fakeButtons[i+1][j+1].mine) count++;
                buttons[i-1][j-1].setNeighborMines(count);
            }
        }

        ToggleButton tButton = (ToggleButton) findViewById(R.id.toggleButton);
        tButton.isChecked();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                    // break
                    buttons[i][j].setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            //isChecked() 가 참이라면 flag
                            if (tButton.isChecked()){
                                ((BlockButton) view).toggleFlag();
                                System.out.println(BlockButton.answer);
                                if(BlockButton.answer == 10){
                                    for (int i = 0; i < 9; i++) {
                                        for (int j = 0; j < 9; j++) {
                                            buttons[i][j].gameOver();
                                        }
                                    }
                                    Toast toast = Toast.makeText(getApplicationContext(), "승리하였습니다!", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            }

                            //isChecked() 가 거짓이라면 break
                            else{
                                chainBlock(((BlockButton) view));

                                if(((BlockButton) view).breakBlock()){
                                    for (int i = 0; i < 9; i++) {
                                        for (int j = 0; j < 9; j++) {
                                            buttons[i][j].gameOver();
                                        }
                                    }
                                    Toast toast = Toast.makeText(getApplicationContext(), "패배하였습니다.!", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            }
                        }

                        private void chainBlock(BlockButton b) {
                            System.out.println("x는"+b.x+" "+"y는"+b.y);
                            b.breakBlock();
                            if (b.neighborMines != 0  || b.flag){
                                return;
                            }
                            else {
                                if (b.x == 0 && b.y == 0){
                                    if (!buttons[b.x+1][b.y].breakState) chainBlock(buttons[b.x+1][b.y]);
                                    if (!buttons[b.x][b.y+1].breakState) chainBlock(buttons[b.x][b.y+1]);
                                }
                                else if (b.x == 0 && b.y == 8){
                                    if (!buttons[b.x+1][b.y].breakState) chainBlock(buttons[b.x+1][b.y]);
                                    if (!buttons[b.x][b.y-1].breakState) chainBlock(buttons[b.x][b.y-1]);
                                    }
                                else if(b.x == 8 && b.y == 0) {
                                    if (!buttons[b.x-1][b.y].breakState) chainBlock(buttons[b.x-1][b.y]);
                                    if (!buttons[b.x][b.y+1].breakState) chainBlock(buttons[b.x][b.y+1]);
                                }
                                else if(b.x == 8 && b.y == 8) {
                                    if (!buttons[b.x-1][b.y].breakState) chainBlock(buttons[b.x-1][b.y]);
                                    if (!buttons[b.x][b.y-1].breakState) chainBlock(buttons[b.x][b.y-1]);
                                }
                                else if(b.x == 0 && (0< b.y && b.y< 8)) {
                                    if (!buttons[b.x+1][b.y].breakState) chainBlock(buttons[b.x+1][b.y]);
                                    if (!buttons[b.x][b.y+1].breakState) chainBlock(buttons[b.x][b.y+1]);
                                    if (!buttons[b.x][b.y-1].breakState) chainBlock(buttons[b.x][b.y-1]);
                                }
                                else if(b.x == 8 && (0< b.y && b.y< 8)) {
                                    if (!buttons[b.x-1][b.y].breakState) chainBlock(buttons[b.x-1][b.y]);
                                    if (!buttons[b.x][b.y+1].breakState) chainBlock(buttons[b.x][b.y+1]);
                                    if (!buttons[b.x][b.y-1].breakState) chainBlock(buttons[b.x][b.y-1]);
                                }
                                else if(b.y == 0 && (0< b.x && b.x< 8)) {
                                    if (!buttons[b.x][b.y+1].breakState) chainBlock(buttons[b.x][b.y+1]);
                                    if (!buttons[b.x+1][b.y].breakState) chainBlock(buttons[b.x+1][b.y]);
                                    if (!buttons[b.x-1][b.y].breakState) chainBlock(buttons[b.x-1][b.y]);
                                }
                                else if(b.y == 8 && (0< b.x && b.x< 8)) {
                                    if (!buttons[b.x][b.y-1].breakState) chainBlock(buttons[b.x][b.y-1]);
                                    if (!buttons[b.x+1][b.y].breakState) chainBlock(buttons[b.x+1][b.y]);
                                    if (!buttons[b.x-1][b.y].breakState) chainBlock(buttons[b.x-1][b.y]);
                                }
                                else {
                                    if (!buttons[b.x-1][b.y].breakState) chainBlock(buttons[b.x-1][b.y]);
                                    if (!buttons[b.x+1][b.y].breakState) chainBlock(buttons[b.x+1][b.y]);
                                    if (!buttons[b.x][b.y-1].breakState) chainBlock(buttons[b.x][b.y-1]);
                                    if (!buttons[b.x][b.y+1].breakState) chainBlock(buttons[b.x][b.y+1]);
                                }
                            }
                        }
                    });
            }
        }
    }
}


