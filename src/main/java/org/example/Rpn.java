package org.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;


public class Rpn {

    private Character [] operatorsChar=new Character[]{'(', ')', '+', '-', '*', '/', '^'};
    private List<String> outputSeparated = new LinkedList<String>();//выходная строка

//Пока есть ещё символы для чтения:
//Читаем очередной символ.
//Если символ является числом или постфиксной функцией (например, ! — факториал), добавляем его к выходной строке.
//Если символ является префиксной функцией (например, sin — синус), помещаем его в стек.
//Если символ является открывающей скобкой, помещаем его в стек.
//Если символ является закрывающей скобкой:
//До тех пор, пока верхним элементом стека не станет открывающая скобка, выталкиваем элементы из стека в выходную строку. При этом открывающая скобка удаляется из стека, но в выходную строку не добавляется. Если стек закончился раньше, чем мы встретили открывающую скобку, это означает, что в выражении либо неверно поставлен разделитель, либо не согласованы скобки.
//Если существуют разные виды скобок, появление непарной скобки также свидетельствует об ошибке. Если какие-то скобки одновременно являются функциями (например, [x] — целая часть), добавляем к выходной строке символ этой функции.
//Когда входная строка закончилась, выталкиваем все символы из стека в выходную строку. В стеке должны были остаться только символы операций; если это не так, значит в выражении не согласованы скобки.
    private String [] toArray(String input){
        ArrayList<String>t=new ArrayList<>();

        for (int i = 0; i < input.length(); i++) {
            int j=i;
            if(checkOperator(input.charAt(i))) {
                t.add(String.valueOf(input.charAt(i)));
            }
            else{
                String temp="";
                while (i<input.length()&&!checkOperator(input.charAt(i))){
                    temp+=String.valueOf(input.charAt(i));
                    i++;
                }

                t.add(temp);
                i--;
            }

        }

        return t.toArray(new String[t.size()]);
    }
    private boolean checkOperator(Character g){
        for (Character t:operatorsChar
             ) {
            if(t.equals(g)) return true;
        }
        return false;
    }

    private int getPriority(Character g)
    {
        switch (g)
        {
            case '(':
            case ')':
                return 0;
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return 4;
        }
    }

//    public void checkInput(String input){
//        int operators=0;
//        int operand=0;
//        String chars[]= toArray(input);
//        for (int i = 0; i < chars.length; i++) {
//            if(checkOperator(chars[i].charAt(0))) operators++;
//            else operand++;
//        }
//
//        if(operators>=operand) {
//            System.out.println("Невозможно выполнить POP для пустого стека"); //Вывести
//            throw new ArrayIndexOutOfBoundsException();
//        }
//    }

    public int rpnRes(String input){//5+6*3
        outputSeparated.clear();
        Stack<Character> stack = new Stack<Character>(); //рабочий стек
        String[] chars= toArray(input);//в массив объектов character
        for (String g:chars
             ) {
            System.out.println(outputSeparated.toString());
            Character gOper=g.charAt(0);
            if(checkOperator(gOper)){ //проверка на наличие оператора
                if(gOper.equals('(')) stack.push(gOper); //если скобка, то просто её добавляем в стек
                else
                    if(gOper.equals(')')) { //если закрывающаяся скобка, то выталкиваем все элементы до открывающейся скобки
                        while (!stack.peek().equals('(')){
                            outputSeparated.add(String.valueOf(stack.pop()));
                            if(stack.size()==1 && !stack.peek().equals('(')){ //лучше написать отдельную функцию для проверки строки
                                System.out.println("Не хватает скобок, эй");
                                throw new ArrayIndexOutOfBoundsException();
                            }
                        }
                        stack.pop(); //удаляем откр. скобку
                    }
                    else{
                        while(stack.size()>0 && getPriority(gOper)<= getPriority(stack.peek())) //чем больше приоритетность - тем важнее. выталкиваем более приоритеттные
                            outputSeparated.add(String.valueOf(stack.pop())); //выталкиваем более приоритеттные
                        stack.push(g.charAt(0));
                    }
                }
                else
                    outputSeparated.add(g);
        }
           while(stack.size()>0)
               outputSeparated.add(String.valueOf(stack.pop()));
        System.out.println(outputSeparated.toString());
            return result();
        }

        private int result(){
            Stack<Integer> stack = new Stack<Integer>(); //рабочий стек
            for (String g: outputSeparated
                 ) {
                if(checkOperator(g.charAt(0))){
                    int res=0;
                    int n2=stack.pop();
                    int n1=stack.pop();
                    switch (g.charAt(0)){
                        case '+':
                            res=n1+n2;
                            break;
                        case '-':
                            res=n1-n2;
                            break;
                        case '*':
                            res=n1*n2;
                            break;
                        case '/':
                            res=n1/n2;
                            break;
                        case '^':
                            res= (int) Math.pow(n1,n2);
                            break;
                    }
                    stack.add(res);
                }
                else
                    stack.add(Integer.parseInt(g));

            }
            return stack.pop();
        }

    }


