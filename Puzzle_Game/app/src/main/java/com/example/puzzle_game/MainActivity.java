package com.example.puzzle_game;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;  // If you are calling getActivity() from a Fragment


import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Model {
    public int[][] board = new int[4][4];
    public int moves; //to keep track of how many moves the player did
    private int zero_i; //row index for the empty cell
    private int zero_j; //column index for the empty cell

    public void start_board() {
        moves = 0; //at the beginning the moves has to be equal zero
        Random random = new Random(); //to create a board of random numbers
        int target;
        Map<Integer, Integer> map = new HashMap<Integer, Integer>(); //to check if the number is already in the board or not
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                target = random.nextInt(16);
                if(map.get(target) != null){
                    j--;
                    continue;
                }
                else{
                    board[i][j] = target;
                    map.put(target, 1);
                    if(board[i][j] == 0){
                        zero_i = i;
                        zero_j = j;
                    }
                }
            }
        }
    }

    //for an Easy Game
     public void test_start_board() {
         moves = 0; //at the beginning the moves has to be equal zero
         board[0][0] = 1;  board[0][1] = 2; board[0][2] = 3; board[0][3] = 4;
         board[1][0] = 0;  board[1][1] = 6; board[1][2] = 7; board[1][3] = 8;
         board[2][0] = 5;  board[2][1] = 9; board[2][2] = 10; board[2][3] = 11;
         board[3][0] = 13;  board[3][1] = 14; board[3][2] = 15; board[3][3] = 12;

         zero_i = 1;
         zero_j = 0;
     }

    public boolean validate_move(int i, int j){
        if( (i != 0 ) && (j != 0) && (i != 3) && (j != 3) ){
            if( (board[i][j-1] != 0) && (board[i][j+1] != 0) && (board[i-1][j] != 0) && (board[i+1][j] != 0) ){
                return false;
            }
        }

        else if( i == 0 ){
            if( j == 0 ){
                if( (board[i][j+1] != 0) && (board[i+1][j] != 0) ){
                    return false;
                }
            }
            else if(j == 3){
                if( (board[i][j-1] != 0) && (board[i+1][j] != 0) ){
                    return false;
                }
            }
            else{
                if( (board[i][j-1] != 0) && (board[i+1][j] != 0) && (board[i][j+1] != 0)){
                    return false;
                }
            }
        }

        else if( i == 3 ){
            if( j == 0 ){
                if( (board[i][j+1] != 0) && (board[i-1][j] != 0) ){
                    return false;
                }
            }
            else if(j == 3){
                if( (board[i][j-1] != 0) && (board[i-1][j] != 0) ){
                    return false;
                }
            }
            else{
                if( (board[i][j-1] != 0) && (board[i-1][j] != 0) && (board[i][j+1] != 0)){
                    return false;
                }
            }
        }

        else if(j == 0){
            if( (board[i][j+1] != 0) && (board[i-1][j] != 0) && (board[i+1][j] != 0) ){
                return false;
            }
        }

        else if(j == 3){
            if( (board[i][j-1] != 0) && (board[i-1][j] != 0) && (board[i+1][j] != 0) ){
                return false;
            }
        }

        return true;
    }

    public void update_board(int i, int j){
        int temp = board[i][j];
        board[i][j] = board[zero_i][zero_j];
        board[zero_i][zero_j] = temp;
        zero_i = i;
        zero_j = j;
        ++moves;
    }

    public boolean is_sorted(){
        int number = 1;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                if(i == 3 && j == 3){
                    return true;
                }
                if(board[i][j] != number){
                    return false;
                }
                number++;
            }
        }
        return true;
    }
}

public class MainActivity extends AppCompatActivity {
    Model model = new Model();
    boolean test_flag = false;

    public void SetButtonTexts(Button[][] buttons) {
        // Set the text of the buttons based on the board array
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (model.board[i][j] != 0) {
                    buttons[i][j].setText(String.valueOf(model.board[i][j]));
                } else {
                    buttons[i][j].setText("");
                }
            }
        }
    }

    public void message_box(int moves){
        // 1. Instantiate an AlertDialog.Builder with its constructor.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics.
        builder.setMessage("Congratulations! You finished it in" + String.valueOf(moves) + " moves");

        // 3. Get the AlertDialog.
        AlertDialog dialog = builder.create();

        // 4. Show the dialog.
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_table_layout);

        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox)v).isChecked())
                    test_flag = true;
                else
                    test_flag = false;
            }
        });


        Button start = (Button)findViewById(R.id.button24);
        Button button1 = (Button)findViewById(R.id.button1);
        Button button2 = (Button)findViewById(R.id.button2);
        Button button3 = (Button)findViewById(R.id.button3);
        Button button4 = (Button)findViewById(R.id.button4);
        Button button5 = (Button)findViewById(R.id.button5);
        Button button6 = (Button)findViewById(R.id.button6);
        Button button7 = (Button)findViewById(R.id.button7);
        Button button8 = (Button)findViewById(R.id.button8);
        Button button9 = (Button)findViewById(R.id.button9);
        Button button10 = (Button)findViewById(R.id.button10);
        Button button11 = (Button)findViewById(R.id.button11);
        Button button12 = (Button)findViewById(R.id.button12);
        Button button13 = (Button)findViewById(R.id.button13);
        Button button14 = (Button)findViewById(R.id.button14);
        Button button15 = (Button)findViewById(R.id.button15);
        Button button16 = (Button)findViewById(R.id.button16);

        Button[][] buttons = new Button[4][4];

        // Initialize your buttons
        buttons[0][0] = button1;
        buttons[0][1] = button2;
        buttons[0][2] = button3;
        buttons[0][3] = button4;
        buttons[1][0] = button5;
        buttons[1][1] = button6;
        buttons[1][2] = button7;
        buttons[1][3] = button8;
        buttons[2][0] = button9;
        buttons[2][1] = button10;
        buttons[2][2] = button11;
        buttons[2][3] = button12;
        buttons[3][0] = button13;
        buttons[3][1] = button14;
        buttons[3][2] = button15;
        buttons[3][3] = button16;

        model.start_board();
        SetButtonTexts(buttons);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(test_flag){
                    model.test_start_board();
                }
                else {
                    model.start_board();
                }
                SetButtonTexts(buttons);
            }
        });


        // Set click listeners for each button
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.validate_move(0, 0)){
                    model.update_board(0, 0);
                    button1.setText("");
                    button2.setText(String.valueOf(model.board[0][1]));
                    button5.setText(String.valueOf(model.board[1][0]));

                    if(model.is_sorted()) {
                        message_box(model.moves);
                    }
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.validate_move(0, 1)){
                    model.update_board(0, 1);
                    button2.setText("");
                    button1.setText(String.valueOf(model.board[0][0]));
                    button3.setText(String.valueOf(model.board[0][2]));
                    button6.setText(String.valueOf(model.board[1][1]));

                    if(model.is_sorted()) {
                        message_box(model.moves);
                    }
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.validate_move(0, 2)){
                    model.update_board(0, 2);
                    button3.setText("");
                    button2.setText(String.valueOf(model.board[0][1]));
                    button4.setText(String.valueOf(model.board[0][3]));
                    button7.setText(String.valueOf(model.board[1][2]));

                    if(model.is_sorted()) {
                        message_box(model.moves);
                    }
                }
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.validate_move(0, 3)){
                    model.update_board(0, 3);
                    button4.setText("");
                    button3.setText(String.valueOf(model.board[0][2]));
                    button8.setText(String.valueOf(model.board[1][3]));

                    if(model.is_sorted()) {
                        message_box(model.moves);
                    }
                }
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.validate_move(1, 0)){
                    model.update_board(1, 0);
                    button5.setText("");
                    button1.setText(String.valueOf(model.board[0][0]));
                    button6.setText(String.valueOf(model.board[1][1]));
                    button9.setText(String.valueOf(model.board[2][0]));

                    if(model.is_sorted()) {
                        message_box(model.moves);
                    }
                }
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.validate_move(1, 1)){
                    model.update_board(1, 1);
                    button6.setText("");
                    button7.setText(String.valueOf(model.board[1][2]));
                    button2.setText(String.valueOf(model.board[0][1]));
                    button5.setText(String.valueOf(model.board[1][0]));
                    button10.setText(String.valueOf(model.board[2][1]));

                    if(model.is_sorted()) {
                        message_box(model.moves);
                    }
                }
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.validate_move(1, 2)){
                    model.update_board(1, 2);
                    button7.setText("");
                    button3.setText(String.valueOf(model.board[0][2]));
                    button8.setText(String.valueOf(model.board[1][3]));
                    button11.setText(String.valueOf(model.board[2][2]));
                    button6.setText(String.valueOf(model.board[1][1]));

                    if(model.is_sorted()) {
                        message_box(model.moves);
                    }
                }
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.validate_move(1, 3)){
                    model.update_board(1, 3);
                    button8.setText("");
                    button4.setText(String.valueOf(model.board[0][3]));
                    button7.setText(String.valueOf(model.board[1][2]));
                    button12.setText(String.valueOf(model.board[2][3]));

                    if(model.is_sorted()) {
                        message_box(model.moves);
                    }
                }
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.validate_move(2, 0)){
                    model.update_board(2, 0);
                    button9.setText("");
                    button5.setText(String.valueOf(model.board[1][0]));
                    button10.setText(String.valueOf(model.board[2][1]));
                    button13.setText(String.valueOf(model.board[3][0]));

                    if(model.is_sorted()) {
                        message_box(model.moves);
                    }
                }
            }
        });

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.validate_move(2, 1)){
                    model.update_board(2, 1);
                    button10.setText("");
                    button6.setText(String.valueOf(model.board[1][1]));
                    button11.setText(String.valueOf(model.board[2][2]));
                    button14.setText(String.valueOf(model.board[3][1]));
                    button9.setText(String.valueOf(model.board[2][0]));

                    if(model.is_sorted()) {
                        message_box(model.moves);
                    }
                }
            }
        });

        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.validate_move(2, 2)){
                    model.update_board(2, 2);
                    button11.setText("");
                    button7.setText(String.valueOf(model.board[1][2]));
                    button12.setText(String.valueOf(model.board[2][3]));
                    button15.setText(String.valueOf(model.board[3][2]));
                    button10.setText(String.valueOf(model.board[2][1]));

                    if(model.is_sorted()) {
                        message_box(model.moves);
                    }
                }
            }
        });

        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.validate_move(2, 3)){
                    model.update_board(2, 3);
                    button12.setText("");
                    button8.setText(String.valueOf(model.board[1][3]));
                    button11.setText(String.valueOf(model.board[2][2]));
                    button16.setText(String.valueOf(model.board[3][3]));

                    if(model.is_sorted()) {
                        message_box(model.moves);
                    }
                }
            }
        });

        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.validate_move(3, 0)){
                    model.update_board(3, 0);
                    button13.setText("");
                    button9.setText(String.valueOf(model.board[2][0]));
                    button14.setText(String.valueOf(model.board[3][1]));

                    if(model.is_sorted()) {
                        message_box(model.moves);
                    }
                }
            }
        });

        button14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.validate_move(3, 1)){
                    model.update_board(3, 1);
                    button14.setText("");
                    button10.setText(String.valueOf(model.board[2][1]));
                    button13.setText(String.valueOf(model.board[3][0]));
                    button15.setText(String.valueOf(model.board[3][2]));

                    if(model.is_sorted()) {
                        message_box(model.moves);
                    }
                }
            }
        });

        button15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.validate_move(3, 2)){
                    model.update_board(3, 2);
                    button15.setText("");
                    button11.setText(String.valueOf(model.board[2][2]));
                    button14.setText(String.valueOf(model.board[3][1]));
                    button16.setText(String.valueOf(model.board[3][3]));

                    if(model.is_sorted()) {
                        message_box(model.moves);
                    }
                }
            }
        });

        button16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.validate_move(3, 3)){
                    model.update_board(3, 3);
                    button16.setText("");
                    button12.setText(String.valueOf(model.board[2][3]));
                    button15.setText(String.valueOf(model.board[3][2]));

                    if(model.is_sorted()) {
                        message_box(model.moves);
                    }
                }
            }
        });

    }
}

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });