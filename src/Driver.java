import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("NUMBER OF FRAMES >> ");
        int numFrames = sc.nextInt();
        sc.close();
        ArrayList<String> results = new ArrayList<String>();
        for (int refLen = 100; refLen <= 1000; refLen+=100){
            int[] refString = new int[refLen];
            for(int i = 0; i < refLen; i++){
                refString[i] = (int)(Math.random()*10);
            }
            System.out.println("REFERENCE STRING >> " + Arrays.toString(refString));
            System.out.println("-------------------------------------------------");
            System.out.println("FIFO [" + refLen + "]");
            //simulate the FIFO page replacement
            FIFO fifo = new FIFO(numFrames);
            fifo.process(refString);
            String fifoResult = "FIFO [" + refLen + "] PAGE FAULTS >> " + fifo.getNumPageFaults() + "/" + refLen;
            System.out.println(fifoResult);
            results.add(fifoResult);
            //simulate the OPT page replacement
            System.out.println("-------------------------------------------------");
            System.out.println("OPT [" + refLen + "]");
            OPT opt = new OPT(numFrames);
            opt.process(refString);
            String optResult = "OPT  [" + refLen + "] PAGE FAULTS >> " + opt.getNumPageFaults() + "/" + refLen;
            System.out.println(optResult);
            results.add(optResult);
            //simulate the LRU page replacement
            System.out.println("-------------------------------------------------");
            System.out.println("LRU [" + refLen + "]");
            LRU lru = new LRU(numFrames);
            lru.process(refString);
            String lruResult = "LRU  [" + refLen + "] PAGE FAULTS >> " + lru.getNumPageFaults() + "/" + refLen;
            System.out.println(lruResult);
            results.add(lruResult);
        }
        System.out.println("-------------------------------------------------");
        System.out.println("RESULTS [" + numFrames + " FRAMES] >> ");
        for (String result : results) {
            System.out.println(result);
        }
    }
}
