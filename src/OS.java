package OperatingSystem;

import java.util.*;
import java.util.logging.*;
import java.util.concurrent.*;

public class OS {
    static int [][]Matrix;
    static long [][]pagetable;
    static int pageTablesize;
    static int pagesize;
    static Semaphore mutex;
    public static void allocatePageTable(){
        Random rand = new Random();
        pagetable = new long[pageTablesize][1];
        for(int i=0;i<pageTablesize;i++){
            pagetable[i][0]=Math.abs(rand.nextLong()%pageTablesize);
        }
    }
    public static long translate(long add){
        long ret_add;
        int pageno=(int)add/pagesize;
        long offset=add%pagesize;
        long physical = pagetable[pageno][0];
        ret_add = physical * pagesize + offset;
        return ret_add;
    }
    public static void booting(){
        System.out.print("Booting");
        for(int i=0;i<4;i++){
            System.out.print(".");
            try {
                Thread.sleep((long) 1000.0);
            } catch (InterruptedException ex) {
                Logger.getLogger(OS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.print("\nLoading OS");
        Matrix =  new int[20][6];
        pageTablesize = 100;
        pagesize = 1024;
        for(int i=0;i<4;i++){
            System.out.print(".");
            try {
                Thread.sleep((long) 1000.0);
            } catch (InterruptedException ex) {
                Logger.getLogger(OS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.print("\nLoading Kernel\n");
        try {
                Thread.sleep((long) 1000.0);
            } catch (InterruptedException ex) {
                Logger.getLogger(OS.class.getName()).log(Level.SEVERE, null, ex);
            }
        System.out.print("Restoring System state\n");
        try {
                Thread.sleep((long) 1000.0);
            } catch (InterruptedException ex) {
                Logger.getLogger(OS.class.getName()).log(Level.SEVERE, null, ex);
            }
        System.out.print("Booting Done\nOK.\n");
    }
    
    public static synchronized void findCompletion(int par)
    {
        int temp=0, val=0;
        try {
            // acquiring the lock
            mutex.acquire();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        Matrix[0][3] = Matrix[0][1] + Matrix[0][2];
        Matrix[0][5] = Matrix[0][3] - Matrix[0][1];
        Matrix[0][4] = Matrix[0][5] - Matrix[0][2];

        for (int i = 1; i < par; i++){
            temp = Matrix[i - 1][3];
            int low = Matrix[i][2];
            for (int j = i; j < par; j++){
                if (temp >= Matrix[j][1] && low >= Matrix[j][2]){
                        low = Matrix[j][2];
                        val = j;
                }
            }
            Matrix[val][3] = temp + Matrix[val][2];
            Matrix[val][5] = Matrix[val][3] - Matrix[val][1];
            Matrix[val][4] = Matrix[val][5] - Matrix[val][2];
            for (int k = 0; k < 6; k++){
                temp=Matrix[i][k];
                Matrix[i][k]=Matrix[val][k];
                Matrix[val][k]=temp;
            }
        }
        mutex.release();
    }
    public static synchronized void updateArrive(int par)
    {
        int temp=0;
            try {
                // acquiring the lock
                mutex.acquire();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            for (int i = 0; i < par; i++){
                for (int j = 0; j < par - i - 1; j++){
                    if (Matrix[j][1] > Matrix[j + 1][1]){
                        for (int k = 0; k < 5; k++){
                                temp=Matrix[j][k];
                                Matrix[j][k]= Matrix[j + 1][k];
                                Matrix[j + 1][k]=temp;
                        }
                    }
                }
            }
            mutex.release();
    }
    public static  void waitTime(int proc[], int n,
                int bt[], int wt[])
    {
        wt[0] = 0;
        for (int i = 1; i < n; i++)
                wt[i] = bt[i - 1] + wt[i - 1];
    }
    public static void turnTime(int proc[], int n,
            int bt[], int wt[], int tat[])
    {
        for (int i = 0; i < n; i++)
                tat[i] = bt[i] + wt[i];
    }
    public static float averageTime(int proc[], int n, int bt[])
    {
        int[] wt = new int[n];
        int []tat = new int[n];
        int  total_wt = 0, total_tat = 0;
        waitTime(proc, n, bt, wt);

        //Function to find turn around time for all processes 
        turnTime(proc, n, bt, wt, tat);

        System.out.println("Processes Burst time Waiting time Turn around time");
        for (int i = 0; i < n; i++)
        {
                total_wt = total_wt + wt[i];
                total_tat = total_tat + tat[i];
                System.out.println("  "+ i+1+" \t\t"+ bt[i]+" \t"+wt[i]+ "\t\t"+tat[i]);

        }
        float avg = (float)total_wt / (float)n;
        System.out.println("Average waiting time ="+avg);
        System.out.println("Average turn around time = "+ ((float)total_tat/(float)n));

        return avg;
    }
    
    public static class Multiprocessing extends Thread{
        @Override
        public void run(){
            booting();
            Scanner input=new Scanner(System.in);
            System.out.print("\nMemory Management Unit\n");
            System.out.print("\nInitializeing Page Table\n");
            for(int i=0;i<4;i++){
                System.out.print(".");
                try {
                    Thread.sleep((long) 1000.0);
                } catch (InterruptedException ex) {
                    Logger.getLogger(OS.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            allocatePageTable();
            System.out.println("Enter Virtual Address : ");
            long add = input.nextLong();
            long physical = translate(add);
            System.out.println("Physical Virtual Address : " + physical);
            
            System.out.print("\nScheduling Processes\n");
            System.out.println("Enter Total Processes : ");
            int num=input.nextInt();
            int [] round_time = new int[num];
            int [] arrive_time = new int[num];
            int [] proc = new int[num];
            int [] burst_time = new int[num];
            System.out.println("Enter Arrival time: ");
            int rr;
            int bb;
            int n = num;
            for (int i = 0; i < num; i++) {
                    rr=input.nextInt();
                    proc[i] = i + 1;
                    arrive_time[i] = rr;
            }
            System.out.println( "\nEnter Burst time: ");
            for (int i = 0; i < num; i++) {
                    bb=input.nextInt();
                    burst_time[i] = bb;
                    round_time[i] = bb;
            }
            int index;

            int t = 0;
            int sum_bt = 0;
            int []wait_time=new int[num];
            int []turnaround_time=new int[num];
            int avgtt = 0, avgwt = 0;
            int []completed=new int[num];
            for (index = 0; index < num; index++) {
                completed[index]=0;
                wait_time[index]=0;
                turnaround_time[index]=0;
                sum_bt += burst_time[index];
            }

            System.out.println( "\n\nHighest Response Ratio\n");
            System.out.println("ID  Arrival Time  Burst Time  Waiting Time  TurnAround Time  ");
            for (t = arrive_time[0]; t < sum_bt;) {

                float hrr = -9999;
                float temp1=0;
                int loc=0;
                for (index = 0; index < num; index++) {
                    if (arrive_time[index] <= t && completed[index] != 1) {
                        temp1 = (burst_time[0] + (t - arrive_time[index])) / burst_time[index];
                        if (hrr < temp1) {
                            hrr = temp1;
                            loc = index;
                        }
                    }
                } 
                t += burst_time[loc];
                wait_time[loc] = t - arrive_time[loc] - burst_time[loc];
                turnaround_time[loc] = t - arrive_time[loc];
                avgtt += turnaround_time[loc];  
                completed[loc] = 1;  
                avgwt += wait_time[loc];
                System.out.println( proc[loc] + "\t" +arrive_time[loc]+ "\t\t" + burst_time[loc] + "\t" + wait_time[loc]+"\t\t" + turnaround_time[loc]);
            }
            System.out.println( "\nAverage waiting time: " + (float)avgwt / (float)num );
            System.out.println( "Average Turn Around time:" + ((float)avgtt / (float)num));
            
            System.out.println( "\nFirst Come First Serve\n");
            averageTime(proc, num, burst_time);
            try {
                // acquiring the lock
                mutex.acquire();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            for (int i = 0; i < num; i++)
            {
                    Matrix[i][0] = proc[i];
                    Matrix[i][1] = arrive_time[i];
                    Matrix[i][2] = burst_time[i];
            }
            mutex.release();

            updateArrive(num);
            findCompletion(num);
            System.out.println( "\n\n\nShortest Job First\n");
            System.out.println("Process ID\tArrival Time\tBurst Time\tWaiting Time\tTurnaround Time\n");
            for (int i = 0; i < num; i++)
            {
                    System.out.println( Matrix[i][0]+ "\t\t" + Matrix[i][1] + "\t\t" + Matrix[i][2]+ "\t\t" + Matrix[i][4] + "\t\t" + Matrix[i][5]);
            }
            int total_wt = 0;
            int total_tat = 0;
            for (int i = 0; i < num; i++)
            {
                    total_wt = total_wt + Matrix[i][4];
                    total_tat = total_tat + Matrix[i][5];
            }
            System.out.println("Average waiting time = "+ (float)total_wt / (float)num);
            System.out.println( "\nAverage turn around time = "+(float)total_tat / (float)num);

            System.out.println("\n\nShortest Time Remaining First");
            int[] exe2=new int [10];
            int  flag = 1;
            int at = 0, ind=0, wt1, tnt, min, max = burst_time[0];
            float avtnt = 0;
            for ( index = 0; index < n; index++)
            {
                exe2[index] = burst_time[index];
                if (max < burst_time[index])
                        max = burst_time[index];
            }
            at = arrive_time[0];
            min = max + 1;
            int avgst=0;
            System.out.println( "\nProcess ID \tWaiting time \tTurn Around time " );
            while (flag==1)
            {
                for ( index = 0; index < n; index++)
                {
                    if (at >= arrive_time[index] && min > burst_time[index] && proc[index] > 0)
                    {
                            ind = index;
                            min = burst_time[index];
                    }
                }
                at++;
                burst_time[ind]--;
                min = max + 1;
                if (burst_time[ind] == 0)
                {
                    wt1 = at - exe2[ind] - arrive_time[ind];
                    tnt = at - arrive_time[ind];
                    System.out.println(proc[ind] + "\t\t" + wt1 + "\t\t" + tnt );
                    proc[ind] = -1;
                    avgst += wt1;
                    avtnt += tnt;
                }
                flag = 0;
                for (int k = 0; k < n; k++)
                    if (proc[k] != -1)
                            flag = 1;
            }
            System.out.println("\nAverage Waiting time     : " + (float)avgst / (float)n);
            System.out.println( "\nAverage turn Around time : " + (float)avtnt / (float)n );
            
        }
    }
    public static void main(String[] args) {
        Multiprocessing mult;
        mutex = new Semaphore(1);
        mult = new Multiprocessing();
        mult.start();
    }
}
