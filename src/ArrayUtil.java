public class ArrayUtil {
    private ArrayUtil(){}

    public static SuperInteractable[] reorderArr(SuperInteractable[] arr) {
        SuperInteractable[] newArr = new SuperInteractable[arr.length];
        int nextAdded = 0;
        for (SuperInteractable element : arr) {
            if (element != null) {
                newArr[nextAdded] = element;
                System.out.println(arr.toString() + " ARRAY NUM :" + nextAdded + ", whats been added: " + element);
                nextAdded++;
            }
        }
        return newArr;
    }
}
