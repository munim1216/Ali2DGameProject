public class ArrayUtil {
    private ArrayUtil(){}

    public static void reorderArr(SuperInteractable[] arr) {
        //SuperInteractable[] newArr = new SuperInteractable[arr.length];
        for(int i=0; i < arr.length - 1; ++i) {
            if(arr[i] == null) {
                arr[i] = arr[i+1];
                arr[i+1] = null;
            }
        }
//        int nextAdded = 0;
//        for (SuperInteractable element : arr) {
//            if (element != null) {
//                newArr[nextAdded] = element;
//                System.out.println(arr.toString() + " ARRAY NUM :" + nextAdded + ", whats been added: " + element);
//                nextAdded++;
//            }
//        }
//        return newArr;
    }
    public static void reorderArr2(SuperInteractable[] arr) {
        int earliestNull = 1000000;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
                if (i < earliestNull) {
                    earliestNull = i;
                }
            } else if (i > earliestNull) {
                arr[earliestNull] = arr[i];
                arr[i] = null;
                i--;
            }
        }
    }
}
