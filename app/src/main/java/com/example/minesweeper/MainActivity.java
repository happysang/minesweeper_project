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

//      버튼 81개 만들고 행 안에 넣음
        Button[][] buttons = new Button[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                buttons[i][j] = new BlockButton (this,i,j);
                buttons[i][j].setLayoutParams(layoutParams);
                tableRows.get(i).addView(buttons[i][j]);

                // 클릭 됐을 때 flag가 되는지 확인
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        ((BlockButton)view).toggleFlag();
                        System.out.println("((BlockButton)view).flag = " + ((BlockButton)view).flag);
                    }
                });
            }
        }
    }
}