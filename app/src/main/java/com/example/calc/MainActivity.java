package com.example.calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void numClick(View v){
        Button btn = (Button)findViewById(v.getId());
        EditText input = (EditText) findViewById(R.id.txtInput);

        input.setText(input.getText().toString()+btn.getText().toString());
        input.setSelection(input.getText().length());
    }

    public void pointClick(View v){
        EditText input = (EditText)findViewById(R.id.txtInput);
        input.setText(input.getText().toString()+".");
        input.setSelection(input.getText().length());
    }

    public void zeroClick(View v){
        EditText input = (EditText)findViewById(R.id.txtInput);
        input.setText(input.getText().toString()+"0");
        input.setSelection(input.getText().length());
    }

    public void addClick(View v){
        EditText input = (EditText)findViewById(R.id.txtInput);
        Button btn = (Button)findViewById(v.getId());
        String s = btn.getText().toString();
        input.setText(input.getText().toString()+" "+s+" ");
        input.setSelection(input.getText().length());
    }

    public void mulClick(View v){
        EditText input = (EditText)findViewById(R.id.txtInput);
        Button btn = (Button)findViewById(v.getId());
        String s = btn.getText().toString();
        input.setText(input.getText().toString()+" "+s+" ");
        input.setSelection(input.getText().length());
    }

    public void clrClick(View v){
        EditText input = (EditText)findViewById(R.id.txtInput);
        input.setText("");

    }

    public void parenClick(View v){
        EditText input = (EditText)findViewById(R.id.txtInput);
        Button btn = (Button)findViewById(v.getId());
        String s = btn.getText().toString();
        input.setText(input.getText().toString()+" "+s+" ");
        input.setSelection(input.getText().length());

    }

    public void closeClick(View v){
        EditText input = (EditText)findViewById(R.id.txtInput);
        Button btn = (Button)findViewById(v.getId());
        String s = btn.getText().toString();
        input.setText(input.getText().toString()+" "+s+" ");
        input.setSelection(input.getText().length());
    }

    public void ansClick(View v){
        EditText input = (EditText)findViewById(R.id.txtInput);
        try{
            Double evaluted =eval(input.getText().toString());
            long a = evaluted.longValue();
            if(a==evaluted)
                input.setText(a+"");
            else
            input.setText(evaluted+"");

        }catch(Exception e){
            input.setText("Invalid Input");

        }
        input.setSelection(input.getText().length());

    }

    public void backClick(View v){
        EditText input =(EditText)findViewById(R.id.txtInput);
        String str= input.getText().toString();
        int pos = input.getSelectionEnd();
        if(pos>0){
            input.setText(str.substring(0,pos-1)+str.substring(pos));
            input.setSelection(pos-1);
        }
        else
        /*if(str.length()>0){
            input.setText(str.substring(0,str.length()-1));
        }*/
        input.setSelection(input.getText().length());
    }

    public Double eval(String s) throws Exception{
        StringTokenizer st = new StringTokenizer(s);
        Queue<String> postfix = new LinkedList<>();
        Stack<Character> stack = new Stack<>();
        while(st.hasMoreTokens()){
            String cur = st.nextToken();
            try{
                Double.parseDouble(cur);
                postfix.add(cur);
            }

            catch(Exception e){
                char c = cur.charAt(0);
                if (c == '(')
                    stack.push(c);

                else if (c == ')')
                {
                    while (!stack.isEmpty() && stack.peek() != '(')
                        postfix.add(stack.pop()+"");

                    if (!stack.isEmpty() && stack.peek() != '(')
                    {
                        System.out.println("INvalid");
                        return 0.0;
                    }
                    else
                        stack.pop();
                }
                else // an operator is encountered
                {
                    while (!stack.isEmpty() && Prec(c) <= Prec(stack.peek()))
                        postfix.add(stack.pop()+"");
                    stack.push(c);
                }
            }
        }
        while (!stack.isEmpty())
            postfix.add(stack.pop()+"");
        Stack<Double> stk = new Stack<>();
        double ans = 0.0;
        while(postfix.peek()!=null){

            String cur = postfix.remove();
            System.out.println(cur);
            char c = cur.charAt(0);
            try{
                stk.push(Double.parseDouble(cur));
            }
            catch(Exception e)
            {
                Double val1 =stk.pop();
                Double val2 =stk.pop();
                switch(c)
                {
                    case '+':
                        stk.push(val2+val1);
                        break;

                    case '-':
                        stk.push(val2-val1);
                        break;

                    case '/':
                        stk.push(val2/val1);
                        break;

                    case '*':
                        stk.push(val2*val1);
                        break;
                }
            }
        }
        ans = stk.pop();
        if(!stk.isEmpty()) throw new Exception();
        System.out.println("ans : "+ans);
        return ans;
    }

    public boolean isDigit(char c){
        if(c>=48&&c<=57)return true;
        else return false;
    }

    public int Prec(char ch)
    {
        switch (ch)
        {
            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
                return 2;

            case '^':
                return 3;
        }
        return -1;
    }

    public void changeSize(TextView v){
        int len = v.getText().toString().length();
        if(len>17 && len<=34)
        {
            v.getPaddingTop();
            //Toast.makeText(MainActivity.this, v.getPaddingTop()+"", Toast.LENGTH_SHORT).show();
            v.setPadding(v.getPaddingLeft(),10,v.getPaddingRight(),10);
        }
        if(len>34 && len<=51)
        {
            v.getPaddingTop();
            //Toast.makeText(MainActivity.this, v.getPaddingTop()+"", Toast.LENGTH_SHORT).show();
            v.setPadding(v.getPaddingLeft(),5,v.getPaddingRight(),5);
        }
        if(len>51)
        {
            v.getPaddingTop();
            // Toast.makeText(MainActivity.this, v.getPaddingTop()+"", Toast.LENGTH_SHORT).show();
            v.setPadding(v.getPaddingLeft(),0,v.getPaddingRight(),0);
        }
        if(len<17)
        {
            v.setPadding(v.getPaddingLeft(),20,v.getPaddingRight(),20);
        }
    }
}
