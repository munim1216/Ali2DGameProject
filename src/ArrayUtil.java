public class ArrayUtil {
    private ArrayUtil(){}

    public static void reorderArr(SuperInteractable[] arr) {
        for(int i=0; i < arr.length - 1; ++i) {
            if(arr[i] == null) {
                arr[i] = arr[i+1];
                arr[i+1] = null;
            }
        } // credit https://stackoverflow.com/questions/25787831/how-to-move-a-null-to-the-end-of-array-in-java-using-for-loop
    }
}
