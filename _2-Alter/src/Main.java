import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String input = in.next();
        in.close();
        Start(input);
    }
    public static void Start(String input){
        for(int x = 0;x<input.length();x++)
            RecursiveSplit(input,x,x,new boolean[input.length()-1],0);
    }
    public static void RecursiveSplit(String input,int inputSplittersNum,int splittersNum,boolean[] splittersPlace,int left) {
        if (splittersNum!=0){
            for(int x = left;x<input.length()-splittersNum;x++){
            if (!splittersPlace[x]) {splittersPlace[x] = true; --splittersNum;}
            else continue;
            RecursiveSplit(input,inputSplittersNum,splittersNum,splittersPlace,x+1);
            if (splittersNum==0) DivideNumbersAndOpers(input,splittersPlace);
            splittersPlace[x] = false; splittersNum++;
            }
        }
        else if (inputSplittersNum==0) DivideNumbersAndOpers(input,splittersPlace);
    }
    public static void DivideNumbersAndOpers(String input,boolean[] splittersPlace){
        StringBuilder unsplittedNumbers = new StringBuilder(input);
        for(int x = splittersPlace.length-1;x>-1;x--){
            if(splittersPlace[x]) unsplittedNumbers.insert(x+1,'|');
        }
        //System.out.println(wtf.toString());

        List<String> numbers = new ArrayList<String>(Arrays.asList(unsplittedNumbers.toString().split("\\|")));
        List<Integer> opers = new ArrayList<>();
        for (int x=0;x<numbers.size()-1;x++){
            opers.add(0);
        }
        boolean isEnd = false;
        while(!isEnd){
            List<String> tempOpers= new ArrayList<>();
            for (int x=0;x<numbers.size()-1;x++){
                if (opers.get(x)==0) tempOpers.add("+");
                else if (opers.get(x)==1) tempOpers.add("-");
                else if (opers.get(x)==2) tempOpers.add("*");
                else if (opers.get(x)==3) tempOpers.add("/");
            }
            FinalMath(numbers,tempOpers);
            if (opers.size()==0) {isEnd=true; break;}
            int pointer = 0;
            boolean isOk = false;
            while(!isOk){
                if (pointer ==0) opers.set(pointer, opers.get(pointer)+1);
                if (opers.get(pointer)==4){
                    if (pointer==opers.size()-1) {isEnd=true; isOk=true;}
                    else {
                        opers.set(pointer,0);
                        opers.set(pointer+1,opers.get(pointer+1)+1);
                    }
                    ++pointer;
                }
                else isOk=true;
            }
        }
    }
    public static void FinalMath(List<String> numbers,List<String> opers){
        StringBuilder regulation = new StringBuilder();
        for(int x = 0;x<numbers.size();x++) {
            regulation.append(numbers.get(x)); if(x!=opers.size()) regulation.append(opers.get(x));
        }
        regulation.append("=100");
        //System.out.println(regulation);
        List<Double> tempNumbers = new ArrayList<>();
        for (int x=0;x<numbers.size();x++){
            tempNumbers.add(Double.parseDouble(numbers.get(x)));
        }
        while(opers.size()!=0)
        {
            if (opers.contains("*")||opers.contains("/")){
                for(int x = 0;x<opers.size();x++){
                    if (opers.get(x) =="*"){
                        tempNumbers.set(x,tempNumbers.get(x)*tempNumbers.get(x+1));
                        tempNumbers.remove(x+1);
                        opers.remove(x);
                        break;
                    }
                    else if (opers.get(x) =="/"){
                        tempNumbers.set(x,tempNumbers.get(x)/tempNumbers.get(x+1));
                        tempNumbers.remove(x+1);
                        opers.remove(x);
                        break;
                    }

                }
            }
            else for(int x = 0;x<opers.size();x++){
                if (opers.get(x) =="+"){
                    tempNumbers.set(x,tempNumbers.get(x)+tempNumbers.get(x+1));
                    tempNumbers.remove(x+1);
                    opers.remove(x);
                    break;
                }
                else if (opers.get(x) =="-"){
                    tempNumbers.set(x,tempNumbers.get(x)-tempNumbers.get(x+1));
                    tempNumbers.remove(x+1);
                    opers.remove(x);
                    break;
                }

            }
        }
        if (tempNumbers.get(0)==100)
            System.out.println(regulation);
    }
}