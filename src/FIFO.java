
import java.util.Arrays;

public class FIFO extends PageReplacementAlgorithm {

    //the array of allocated frames which values store the mapping page numbers
    private int arrFrames[];
    //the next frame index to load the new page
    private int nextLoadLocation;

    //constructor
    public FIFO(int numFrames) {
        super(numFrames);
        //page table index: frame#, value: page number
        arrFrames = new int[numFrames];
        //fill -1 for all frames at initial
        Arrays.fill(arrFrames, -1);
        //the default free frame (0)
        nextLoadLocation = 0;

    }

    //process a reference string
    @Override
    public void process(int[] referenceString) {
        for (int pageNumber : referenceString) {
            //search on arrFrames to see if such pages has been loaded to RAM yet
            int frameNum = -1;
            for (int f = 0; f < numFrames; f++) {
                //page hit event
                if (arrFrames[f] == pageNumber) {
                    frameNum = f;
                    break;
                }
            }

            //page hit
            if (frameNum >= 0) {
                System.out.println("PAGE HIT   >> PAGE " + pageNumber + " LOADED INTO FRAME " + frameNum);
                //continue to handle the next request page
                continue;
            }

            //handle page fault
            //increase the #page faults
            numPageFaults++;
            //load the page into the current selected victim
            arrFrames[nextLoadLocation] = pageNumber;
            System.out.println("PAGE FAULT >> PAGE " + pageNumber + " LOADED INTO FRAME " + nextLoadLocation);
            //circular
            nextLoadLocation = (nextLoadLocation + 1) % numFrames;
        }
    }
}
