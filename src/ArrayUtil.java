public class ArrayUtil {
    private ArrayUtil(){}

    public static SuperInteractable[] reorderArr(SuperInteractable[] arr) {
        SuperInteractable[] newArr = new SuperInteractable[arr.length];
        int nextAdded = 0;
        for (SuperInteractable element : arr) {
            if (element != null) {
                newArr[nextAdded] = element;
                System.out.println("ARRAY NUM :" + nextAdded + ", whats been added: " + element);
                nextAdded++;
            }
        }
//        for (SuperInteractable element : newArr) {
//            if (element != null) {
//                System.out.println(element);
//            }
//        }
        return newArr;
    }
}
