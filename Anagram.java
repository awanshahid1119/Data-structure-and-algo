import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

class Hashtable{
    static int max = 100005;
    public int newSize;
    public LinkedList<String>[] hashtable;

    static Vector<Integer> primeNumber = new Vector<>();

    static void sieveOfEratosthenes() {

        boolean[] prime = new boolean[max + 1];
        for (int i = 0; i <= max; i++)
            prime[i] = true;

        for (int p = 2; p * p <= max; p++) {

            if (prime[p]) {

                // Update all multiples of p
                for (int i = p * p; i <= max; i += p) {
                    prime[i] = false;
                }
            }
        }

        for (int i = 2; i <= max; i++) {

            if (prime[i])
                primeNumber.add(i);
        }
    }

    static int upper_bound(Integer[] arr, int low, int high, int X) {
        if (low > high)
            return low;
        int mid = low + (high - low) / 2;
        if (arr[mid] <= X) {
            return upper_bound(arr, mid + 1, high, X);
        }
        return upper_bound(arr, low, mid - 1, X);
    }

    public static int closetPrime(int number) {

        if (number == 1)
            return 2;
        else {
            sieveOfEratosthenes();

            Integer[] arr = primeNumber.toArray(new Integer[primeNumber.size()]);
            int index = upper_bound(arr, 0, arr.length, number);
            if (arr[index] == number || arr[index - 1] == number)
                return number;
            else if (Math.abs(arr[index] - number)
                    < Math.abs(arr[index - 1] - number))
                return arr[index];
            else
                return arr[index - 1];
        }
    }

    public Hashtable(int size) {
        newSize=closetPrime(2*size);
        hashtable = new LinkedList[newSize];
    }

}

public class Anagram {
    public static int Hashing(String a,int mod){
        char[] tempArray = a.toCharArray();
        Arrays.sort(tempArray);
        String b=new String(tempArray);
        int hassheda=0;
        for(int i=0;i<b.length();i++){
            hassheda+=(Math.pow(256,i)*(int)b.charAt(i))%mod;
        }
        return hassheda%mod;
    }

    public static LinkedList<String> Search(String word,Hashtable HT){
        char[] tempArray2 = word.toCharArray();
        Arrays.sort(tempArray2);
        String sortedword = new String(tempArray2);
        int a=Hashing(word, HT.newSize);
        int temp=a;
        int i=1;
        while(true){
            if(HT.hashtable[a]==null){
                break;
            }
            LinkedList<String> templ = HT.hashtable[a];
            String wordalready = templ.get(0);
            char[] tempArray = wordalready.toCharArray();
            Arrays.sort(tempArray);
            String sortedwordalready = new String(tempArray);
            if(sortedword.equals(sortedwordalready)){
                return templ;
            }else{
                a=(a+i*i)% HT.newSize;
                i+=1;
            }
            if(a==temp){
                break;
            }
        }
        return null;
    }


    static class Permutation {
        public Vector<Vector<String>> permute(String s) {
//            int count = 1;
            Vector<Vector<String>> vectorofpurmuts = new Vector<>();
            Vector<String> temp = new Vector<>();
            temp.add("" + s.charAt(0));
            temp.add("");
            temp.add("");
            vectorofpurmuts.add(temp);
            for (int i = 1; i < s.length(); i++) {

                String ts = "" + s.charAt(i);
                Vector<Vector<String>> huehue = new Vector<>();
//                int nn = huehue.size();
                for(Vector<String> joe:vectorofpurmuts){
                    huehue.addAll(givemesomestrings(ts,joe));
                }
                vectorofpurmuts.removeAllElements();
                vectorofpurmuts.addAll(huehue);

        }
            return vectorofpurmuts;
    }

        public Vector<Vector<String>> givemesomestrings(String ts,Vector<String> tvs){
            Vector<Vector<String>> america = new Vector<>();
            Vector<String> trump = (Vector<String>) tvs.clone();
            String tempor = trump.elementAt(0) + ts;
            trump.set(0, tempor);
            america.add(trump);
            if (!tvs.elementAt(1).equals("")) {
                trump = (Vector<String>) tvs.clone();
                tempor = trump.elementAt(1) + ts;
                trump.set(1, tempor);
                america.add(trump);
                trump = (Vector<String>) tvs.clone();
                tempor = trump.elementAt(2) + ts;
                trump.set(2, tempor);
                america.add(trump);
            } else {
                trump = (Vector<String>) tvs.clone();
                tempor = trump.elementAt(1) + ts;
                trump.set(1, tempor);
                america.add(trump);
            }
            return america;
        }
    }
    public static void main(String[] args) throws FileNotFoundException {
        Instant start = Instant.now();
        File file = new File("./vocabulary.txt");
        Scanner sc = new Scanner(file);
        int size= sc.nextInt();
        Hashtable HT=new Hashtable(size);
        while(sc.hasNext()){
            String word=sc.next();
            if(word.length()>12){
                continue;
            }else {
                int hasshedword = Hashing(word, HT.newSize);
                boolean stop = false;
                int i = 1;
                while (!stop) {
                    if (HT.hashtable[hasshedword] == null) {
                        LinkedList<String> temp = new LinkedList();
                        temp.addFirst(word);
                        HT.hashtable[hasshedword] = temp;
                        stop = true;
                    } else {
                        LinkedList<String> temp = HT.hashtable[hasshedword];
                        String wordalready = temp.get(0);
                        char[] tempArray = wordalready.toCharArray();
                        Arrays.sort(tempArray);
                        String sortedwordalready = new String(tempArray);
                        char[] tempArray2 = word.toCharArray();
                        Arrays.sort(tempArray2);
                        String sortedword = new String(tempArray2);
                        if (sortedwordalready.equals(sortedword)) {
                            boolean goo = false;
                            while (!goo) {
                                if (temp.contains(word)) {
                                    word += "$";
                                } else {
                                    goo = true;
                                }
                            }
                            temp.add(word);
                            stop = true;
                        } else {
                            hasshedword = (hasshedword + i * i) % HT.newSize;
                            i++;
                        }
                    }
                }
            }
        }
        File file2 = new File("./input.txt");
        Scanner sc2 = new Scanner(file2);
        int a = sc2.nextInt();
        for(int i=0;i<a;i++) {
            Vector<Vector<String>> temp;
            String str = sc2.next();
            Permutation permutation = new Permutation();
            temp=permutation.permute(str);
//            Vector<String> finalanswer=new Vector<>();
            HashSet<String> finalanswer = new HashSet<>();
            for(Vector<String> hue:temp){
                Vector<LinkedList<String>> hola=new Vector<>();
                for(String fir:hue){
                    if(fir.length()>0) {
                        LinkedList<String> tempList = Search(fir, HT);
                        if(tempList==null){
                            hola=null;
                            break;
                        }
                        hola.add(tempList);
                    }
                }
                if(hola!=null){
                    LinkedList<String> lst= hola.elementAt(0);
                    if(hola.size()==1){
                        finalanswer.addAll(lst);
                    }else if(hola.size()==2){
                        LinkedList<String> lst2= hola.elementAt(1);
                        for(String hehe:lst){
                            for(String hehehe:lst2){
//                                if(!finalanswer.contains(hehe+" "+hehehe)) {
                                    finalanswer.add(hehe + " " + hehehe);
//                                }
//                                if(!finalanswer.contains(hehehe+" "+hehe)) {
                                    finalanswer.add(hehehe + " " + hehe);
//                                }
                            }
                        }
                    }else if(hola.size()==3){
                        LinkedList<String> lst2= hola.elementAt(1);
                        LinkedList<String> lst3= hola.elementAt(2);
                        for(String hehe:lst){
                            for(String hehe2:lst2){
                                for(String hehe3:lst3){
//                                    if(!finalanswer.contains(hehe+" "+hehe2+" "+hehe3)) {
                                        finalanswer.add(hehe + " " + hehe2 + " " + hehe3);

//                                    if(!finalanswer.contains(hehe+" "+hehe3+" "+hehe2)) {
                                        finalanswer.add(hehe + " " + hehe3 + " " + hehe2);
//                                    }
//                                    if(!finalanswer.contains(hehe2+" "+hehe+" "+hehe3)) {
                                        finalanswer.add(hehe2 + " " + hehe + " " + hehe3);
//                                    }
//                                    if(!finalanswer.contains(hehe2+" "+hehe3+" "+hehe)) {
                                        finalanswer.add(hehe2 + " " + hehe3 + " " + hehe);
//                                    }
//                                    if(!finalanswer.contains(hehe3+" "+hehe2+" "+hehe)) {
                                        finalanswer.add(hehe3 + " " + hehe2 + " " + hehe);
//                                    }
//                                    if(!finalanswer.contains(hehe3+" "+hehe+" "+hehe2)) {
                                        finalanswer.add(hehe3 + " " + hehe + " " + hehe2);
//                                    }
                                }
                            }
                        }
                    }
                }
            }
            String[] array=new String[finalanswer.size()];
            int iii=0;
            for(String xx:finalanswer){
                array[iii]=xx.replace("$","");
                iii++;
            }
            Arrays.sort(array, String.CASE_INSENSITIVE_ORDER);
            for(String alls:array){
                System.out.println(alls);
            }
            System.out.println(-1);
        }
        Instant end = Instant.now();
        System.out.println("Total Time Taken :"+ Duration.between(start, end));
    }
}
