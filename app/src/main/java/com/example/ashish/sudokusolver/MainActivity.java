package com.example.ashish.sudokusolver;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button b_1,b_2,b_3,b_4,b_5,b_6,b_7,b_8,b_9,b_c;
    int matrix[][]=new int[9][9];
    int x=0,y=0;
    final EditText editText[][] = new EditText[9][9];

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Exit ?");
        builder.setMessage("Are you sure you want to exit ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
        //super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.men_solve)
        {
            for(int i=0;i<9;i++)
            {
                for(int j=0;j<9;j++)
                {
                    String temp=editText[i][j].getText().toString();
                    if(temp.equals(""))
                    {
                        matrix[i][j]=0;
                        editText[i][j].setTextColor(getResources().getColor(R.color.Green));
                    }
                    else
                    {
                        matrix[i][j]=Integer.parseInt(temp);
                    }
                }
            }

            char[][] board=new char[9][9];

            for(int i=0;i<9;i++)
            {
                for(int j=0;j<9;j++)
                {
                    if(matrix[i][j]==0)
                    {
                        board[i][j]='.';
                    }
                    else
                    {
                        board[i][j]= (char) ('0'+matrix[i][j]);
                    }
                }
            }

            if(isValidSudoku(board)==true)
            {
                boolean val=solveSudoku(matrix);
                if(val==true)
                {
                    for(int i=0;i<9;i++)
                    {
                        for(int j=0;j<9;j++)
                        {
                            String temp=Integer.toString(matrix[i][j]);
                            editText[i][j].setText(temp);
                        }
                    }
                    Toast.makeText(MainActivity.this,"Sudoku Solved !",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Not a valid Sudoku Grid !\nPlease Try again !",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(MainActivity.this,"Not a valid Sudoku Grid !\nPlease Try again !",Toast.LENGTH_SHORT).show();
                for(int i=0;i<9;i++)
                {
                    for(int j=0;j<9;j++)
                    {

                        editText[i][j].setTextColor(getResources().getColor(R.color.Blue));
                    }
                }
            }
        }
        else
        if(id==R.id.men_clear)
        {
            for(int i=0;i<9;i++)
            {
                for(int j=0;j<9;j++)
                {
                    String temp="";
                    editText[i][j].setText(temp);
                    editText[i][j].setTextColor(getResources().getColor(R.color.Blue));
                }
            }
        }
        else
        if(id==R.id.men_set)
        {
            Intent intent=new Intent(MainActivity.this,Settings.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout ll= (LinearLayout) findViewById(R.id.ll);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width=metrics.widthPixels;
        int cell_width=width/9;
        int cell_height=cell_width;

        b_1= (Button) findViewById(R.id.b_1);
        b_2= (Button) findViewById(R.id.b_2);
        b_3= (Button) findViewById(R.id.b_3);
        b_4= (Button) findViewById(R.id.b_4);
        b_5= (Button) findViewById(R.id.b_5);
        b_6= (Button) findViewById(R.id.b_6);
        b_7= (Button) findViewById(R.id.b_7);
        b_8= (Button) findViewById(R.id.b_8);
        b_9= (Button) findViewById(R.id.b_9);
        b_c= (Button) findViewById(R.id.b_c);



        for(int i=0;i<9;i++)
        {
            LinearLayout l=new LinearLayout(this);
            l.setOrientation(LinearLayout.HORIZONTAL);
            for(int j=0;j<9;j++)
            {
                editText[i][j]=new EditText(this);
                editText[i][j].setWidth(cell_width);
                editText[i][j].setHeight(cell_height);
                editText[i][j].setInputType(InputType.TYPE_CLASS_NUMBER);
                editText[i][j].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                editText[i][j].setCursorVisible(false);
                editText[i][j].setTextColor(getResources().getColor(R.color.Blue));
                editText[i][j].setTypeface(null, Typeface.BOLD);
                editText[i][j].setShowSoftInputOnFocus(false);
                if(((i==0||i==1||i==2)&&(j==3||j==4||j==5))||
                    ((i==3||i==4||i==5)&&(j==0||j==1||j==2))||((i==6||i==7||i==8)&&(j==3||j==4||j==5))||((i==3||i==4||i==5)&&(j==6||j==7||j==8)))
                {
                    editText[i][j].setBackground(getResources().getDrawable(R.drawable.shape));
                }
                else
                {
                    editText[i][j].setBackground(getResources().getDrawable(R.drawable.shape1));
                }
                editText[i][j].setFilters(new InputFilter[] {
                        new InputFilter.LengthFilter(1)});
                l.addView(editText[i][j]);
            }
            ll.addView(l);
        }
        for(int a=0;a<9;a++)
        {
            for(int b=0;b<9;b++)
            {
                final int finalB = b;
                final int finalA = a;
                editText[a][b].setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {

                        if(((x==0||x==1||x==2)&&(y==3||y==4||y==5))||
                                ((x==3||x==4||x==5)&&(y==0||y==1||y==2))||((x==6||x==7||x==8)&&(y==3||y==4||y==5))||((x==3||x==4||x==5)&&(y==6||y==7||y==8)))
                        {
                            editText[x][y].setBackground(getResources().getDrawable(R.drawable.shape));
                        }
                        else
                        {
                            editText[x][y].setBackground(getResources().getDrawable(R.drawable.shape1));
                        }

                        x= finalA;
                        y= finalB;

                        editText[x][y].setBackground(getResources().getDrawable(R.drawable.shape2));
                        return false;
                    }
                });
            }
        }


        b_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText[x][y].setText("1");
            }
        });

        b_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText[x][y].setText("2");
            }
        });

        b_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText[x][y].setText("3");
            }
        });

        b_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText[x][y].setText("4");
            }
        });

        b_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText[x][y].setText("5");
            }
        });

        b_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText[x][y].setText("6");
            }
        });

        b_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText[x][y].setText("7");
            }
        });

        b_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText[x][y].setText("8");
            }
        });

        b_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText[x][y].setText("9");
            }
        });

        b_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText[x][y].setText("");
            }
        });

    }


    public boolean isValidSudoku(char[][] board) {
        boolean[][] row = new boolean[9][9];
        boolean[][] col = new boolean[9][9];
        boolean[][] box = new boolean[9][9];
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if (board[i][j]!='.'){
                    int num = board[i][j]-'0'-1;
                    int k = i/3*3+j/3;
                    if (row[i][num] || col[j][num] || box[k][num]){
                        return false;
                    }
                    row[i][num]=col[j][num]=box[k][num] = true;
                }
            }
        }
        return true;

    }


    public static boolean solveSudoku(int[][] matrix){

        for (int i=0; i<9; i++) {
            for (int j=0; j<9; j++) {
                if (matrix[i][j]==0) {
                    boolean result = false;
                    int k = 1;
                    while(!result){
                        if (k==10){
                            matrix[i][j] = 0;
                            return false;
                        }
                        matrix[i][j] = k;
                        k++;
                        result = testRow(matrix,i,j)&&testColumn(matrix,i,j)&&testBlock(matrix,i,j);
                        if (!result)
                            continue;
                        result = solveSudoku(matrix);
                    }
                }
            }
        }
        return true;
    }

    public static boolean testRow(int[][] matrix, int i,int j){
        int key = matrix[i][j];

        for (int k=0; k<9; k++) {
            if (k==j)
                continue;
            if (key==matrix[i][k])
                return false;
        }

        return true;
    }

    public static boolean testColumn(int[][] matrix, int i, int j){

        int key = matrix[i][j];

        for (int k=0; k<9; k++) {
            if (k==i)
                continue;
            if (key==matrix[k][j])
                return false;
        }

        return true;
    }

    public static boolean testBlock(int[][] matrix, int i, int j){

        int key = matrix[i][j];

        int k_gap = 3*(i/3);
        int l_gap = 3*(j/3);

        for (int k=0; k<3; k++) {
            for (int l=0; l<3;l++) {
                if (i==k_gap+k&&j==l_gap+l)
                    continue;
                if (matrix[k_gap+k][l_gap+l]==key)
                    return false;
            }
        }
        return true;
    }
}
