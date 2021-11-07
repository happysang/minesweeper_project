package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

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
        for (int i = 0; i < 9; i++){
            tableRows.add(new TableRow(this));
            table.addView(tableRows.get(i));
        }


        //행 안에 넣을 수 있게 layout 만듬
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1.0f);


        // 랜덤으로 폭탄 10개 생성
        for (int i = 0; i < 10; i++) {
            Random r = new Random();
            ArrayList<int[]> bombs = new ArrayList<>();
            int[] pair = new int[2];
            pair[0] = r.nextInt(9);
            pair[1] = r.nextInt(9);
            if (bombs.contains(pair)) {
                i--;
                continue;
            }
            bombs.add(pair);
        }

        //버튼 81개 만들고 행 안에 넣음
        Button[][] buttons = new Button[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                buttons[i][j] = new BlockButton (this,i,j);
                buttons[i][j].setLayoutParams(layoutParams);
                tableRows.get(i).addView(buttons[i][j]);

                // 클릭 했을 때 flag가 되는지 확인
//                buttons[i][j].setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view){
//                        ((BlockButton)view).toggleFlag();}});
            }
        }
        


            BlockButton b = (BlockButton)buttons[pair[0]][pair[1]];
            b.setMine(true);

            // 클릭 했을 때 breakBlock이 되는지 확인
//            b.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view){
//                    ((BlockButton)view).breakBlock();}});
        }
    }
}

