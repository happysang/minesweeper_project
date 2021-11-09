package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.ToggleButton;

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
        tButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {

                        if (isChecked){
                            // break
                            buttons[i][j].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ((BlockButton) view).breakBlock();
                                }
                            });
                        }
                        else{
                            // flag
                            buttons[i][j].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ((BlockButton) view).toggleFlag();
                                }
                            });
                        }

                    }
                }
            }
        });
    }
}


