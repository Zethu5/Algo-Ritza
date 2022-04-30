import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void initArrays(int[] lengths, int[] widths,int[] heights) {
        Random rnd = new Random();

        for(int i = 0; i < lengths.length; i++) {
            lengths[i] = rnd.nextInt(200)  + 1;
            widths[i] = rnd.nextInt(200)  + 1;
            heights[i] = rnd.nextInt(200)  + 1;
        }
    }

    public static void printArrays(int[] lengths, int[] widths, int[] heights) {
        for(int i = 0; i < lengths.length; i++) {
            int mul = lengths[i] * widths[i];
            System.out.printf("Length: %d, Width: %d, Area: %d, Height: %d\n",lengths[i],widths[i],mul,heights[i]);
        }
    }

    public static void maxHeight(int[] lengths, int[] widths, int[] heights) {
        int numBoxes = lengths.length;
        int []maxHeights = new int[numBoxes];
        int []sitsOn = new int[numBoxes];

        // bubble sort - O(n^2)
        // re-arrange the arrays via area so that the larger area boxes will be at the beginning
        // and smaller area boxes will be at the end
        for (int i = 0; i < numBoxes-1; i++)
            for (int j = 0; j < numBoxes-i-1; j++)
                if (lengths[j] * widths[j] < lengths[j+1] * widths[j+1])
                {
                    int tempLength = lengths[j];
                    int tempWidth = widths[j];
                    int tempHeight = heights[j];

                    lengths[j] = lengths[j+1];
                    widths[j] = widths[j+1];
                    heights[j] = heights[j+1];

                    lengths[j+1] = tempLength;
                    widths[j+1] = tempWidth;
                    heights[j+1] = tempHeight;
                }

        System.out.println(numBoxes + " Boxes arranged from the largest area to the smallest:");
        printArrays(lengths,widths,heights);
        System.out.println("");


        // O(n)
        // initialize the max height for the boxes if only 1 box was allowed to be in the solution
        // initialize the box on which this one sits on (which is no one), so we will init it to itself
        for(int i = 0; i < numBoxes; i++) {
            maxHeights[i] = heights[i];
            sitsOn[i] = i;
        }

        // O(n^2)
        // check for each box if the other boxes that are less in their area, can be on that box
        // if so than check if the max height will increase by stacking them together
        // if so than update maxHeight for each box, and update which box will sit on which one
        for(int i = 1; i < numBoxes; i++) {
            for(int j = 0; j < i; j++) {
                if(lengths[i] < lengths[j] && widths[i] < widths[j]) {
                    if(maxHeights[j] + heights[i] > maxHeights[i]) {
                        maxHeights[i] += maxHeights[j] + heights[i];
                        sitsOn[i] = j;
                    }
                }
            }
        }

        int maxHeight = 0;
        int index = 0;

        // O(n)
        // check for the max height and the starting index of the smallest box at the top of the tower
        for(int i = 0; i < numBoxes; i++) {
            if(maxHeights[i] > maxHeight) {
                maxHeight = maxHeights[i];
                index = i;
            }
        }

        // O(n)
        // print the highest tower box by box from the smallest one to the largest
        System.out.println("Highest tower possible with the restrictions:");

        int sumMaxHeight = 0;
        do {
            sumMaxHeight += heights[index];
            System.out.printf("Length: %d, Width: %d, Height: %d\n",lengths[index],widths[index],heights[index]);
            index = sitsOn[index];

            // last box that points to itself will not be printed so we need to do it manually
            if(sitsOn[index] == index) {
                sumMaxHeight += heights[index];
                System.out.printf("Length: %d, Width: %d, Height: %d\n",lengths[index],widths[index],heights[index]);
            }
        } while (sitsOn[index] != index);

        System.out.println("Max height is: " + sumMaxHeight);
    }

    public static void main(String[] args) {
        int numBoxes = 20;
        int[] lengths = new int[numBoxes];
        int[] widths = new int[numBoxes];
        int[] heights = new int[numBoxes];

        System.out.println("========================== First call: ==========================");
        initArrays(lengths,widths,heights);
        maxHeight(lengths,widths,heights);

        System.out.println("========================== Second call: ==========================");
        numBoxes = 30;
        lengths = new int[numBoxes];
        widths = new int[numBoxes];
        heights = new int[numBoxes];
        initArrays(lengths,widths,heights);
        maxHeight(lengths,widths,heights);
    }
}
