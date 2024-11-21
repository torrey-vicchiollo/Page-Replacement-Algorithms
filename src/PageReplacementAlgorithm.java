
public abstract class PageReplacementAlgorithm {
    // the representation of each memory partition

    protected int numFrames;         // store how many allocated frames
    protected int numPageFaults;     // store how many page faults so far

    //constructor method
    public PageReplacementAlgorithm(int numFrames) {
        this.numFrames = numFrames;
        this.numPageFaults = 0;
    }

    public int getNumFrames() {
        return this.numFrames;
    }

    public int getNumPageFaults() {
        return this.numPageFaults;
    }

    //process a reference string
    public abstract void process(int[] referenceString);
}
