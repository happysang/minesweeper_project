package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TableLayout table;
        table = findViewById(R.id.tableLayout);
        List<TableRow> tableRows = new ArrayList<>(9);
        for (int i = 0; i < 9; i++){
            tableRows.add(new TableRow(this));
            table.addView(tableRows.get(i));
        }

        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1.0f);

        Button[][] buttons = new Button[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                buttons[i][j] = new Button(this);
                buttons[i][j].setLayoutParams(layoutParams);
                tableRows.get(i).addView(buttons[i][j]);
            }
        }
    }
}