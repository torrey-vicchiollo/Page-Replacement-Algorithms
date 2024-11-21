
import java.util.Arrays;

public class OPT extends PageReplacementAlgorithm {

    //the array of allocated frames which values store the mapping page numbers
    private int arrFrames[];
    //the next frame index to load the new page
    private int nextLoadLocation;

    //constructor
    public OPT(int numFrames) {
        super(numFrames);
        //initialize the page table and set default location to load a page
        //page table
        arrFrames = new int[numFrames];
        //index of arrFrames = frame #; value of arrFrames = mapping page #
        //all frames store invalid pages at the beginning
        Arrays.fill(arrFrames, -1);
        nextLoadLocation = 0;
    }

    //process a reference string
    //which is a list of page# to be referenced when the instructions are excuted
    @Override
    public void process(int[] referenceString) {
        for (int pIdx = 0; pIdx < referenceString.length; pIdx++) {
            int pageNumber = referenceString[pIdx];
            //search the page table to see if such page has been loaded into physical memory yet
            int frameNumber = -1;
            for (int f = 0; f < arrFrames.length; f++) {
                if (arrFrames[f] == pageNumber) {
                    //page hit
                    frameNumber = f;
                    break;
                }
            }
            //if page hit -> print a text and continue
            if (frameNumber >= 0) {
                System.out.printf("PAGE HIT   >> PAGE %d LOADED INTO FRAME %d\n", pageNumber, frameNumber);
                continue;
            }
            //PAGE FAULT -> handle it and udpate the page table
            //increase the #page faults
            numPageFaults++;
            //there are still free frames
            if (nextLoadLocation < numFrames) {
                arrFrames[nextLoadLocation] = pageNumber;
                frameNumber = nextLoadLocation;
                nextLoadLocation++;
            //choose a victim frame containing a page that will not be used or farthest use in the future
            } else {
                int victimFrame = -1, farthestUsed = -1;
                for (int f = 0; f < arrFrames.length; f++) {
                    //try to find the next time the page arrFrames[f] will be used in the remaining ref string
                    int nextUsed = -1;
                    for (int k = pIdx + 1; k < referenceString.length; k++) {
                        if (referenceString[k] == arrFrames[f]) {
                            nextUsed = k;
                            break;
                        }
                    }
                    //a best candidate to be the victim
                    if (nextUsed == -1) {
                        victimFrame = f;
                        break;
                        //to find the f containing a page with farthest index in the ref string
                    } else {
                        if (nextUsed > farthestUsed) {
                            victimFrame = f;
                            farthestUsed = nextUsed;
                        }
                    }
                }
                //update the page table and return
                arrFrames[victimFrame] = pageNumber;
                frameNumber = victimFrame;
            }
            System.out.printf("PAGE FAULT >> PAGE %d LOADED INTO FRAME %d\n", pageNumber, frameNumber);
        }
    }
}
