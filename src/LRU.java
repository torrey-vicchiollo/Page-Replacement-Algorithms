
import java.util.Arrays;

public class LRU extends PageReplacementAlgorithm {

    //the array of allocated frames which values store the mapping page numbers
    private int arrFrames[];
    //the next frame index to load the new page
    private int nextLoadLocation;

    //constructor
    public LRU(int numFrames) {
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
        for (int pageIndex = 0; pageIndex < referenceString.length; pageIndex++) {
            int pageNumber = referenceString[pageIndex];
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
            //PAGE FAULT -> handle it and update the page table
            //increase the #page faults
            numPageFaults++;
            //free frames
            if (nextLoadLocation < numFrames) {
                arrFrames[nextLoadLocation] = pageNumber;
                frameNumber = nextLoadLocation;
                nextLoadLocation++;

            //no free frames
            } else {
                //initialize victim frame index, least recently used index
                int victimFrame = -1, lru = Integer.MAX_VALUE;
                //increment through the current frames
                for (int f = 0; f < arrFrames.length; f++) {
                    //intialize least used variable
                    int leastUsed = -1;
                    //decrement through input string starting at index before current one
                    for (int j = pageIndex - 1; j >= 0; j--) {
                        //if the reference string at k equals current frame at f
                        if (referenceString[j] == arrFrames[f]) {
                            leastUsed = j;
                            break;
                        }
                    }
                    //
                    if (leastUsed == -1) {
                        victimFrame = f;
                        break;
                    }
                    //if least used index is less than lru, set lru to last used index and update victim frame index
                    if (leastUsed < lru) {
                        lru = leastUsed;
                        victimFrame = f;
                    }
                }

                //update the page table and return
                arrFrames[victimFrame] = pageNumber;
                frameNumber = nextLoadLocation;
            }
            System.out.printf("PAGE FAULT >> PAGE %d LOADED INTO FRAME %d\n", pageNumber, frameNumber);
        }
    }
}
