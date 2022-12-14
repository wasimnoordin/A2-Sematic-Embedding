import java.util.List;

public class HeapSort {
    public static int[] doHeapSort(int[] _arr) {
        heapSort(_arr);
        return _arr;
    }

    public static List<CosSimilarityPair> doHeapSort(List<CosSimilarityPair> _list) {
        //TODO Task 4.1
        heapSort(_list);
        return _list;
    }

    private static void heapSort(int[] _arr) {
        int n = _arr.length;

        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(_arr, i, n);

        for (int j = n - 1; j > 0; j--) {
            int temp = _arr[0];
            _arr[0] = _arr[j];
            _arr[j] = temp;

            heapify(_arr, 0, j);
        }
    }

    private static void heapSort(List<CosSimilarityPair> _list) {
        //TODO Task 4.2
        int hsCosSim = _list.size();
        int i = (hsCosSim / 2) - 1;
        while (i >= 0)
        {
            heapify(_list, i, hsCosSim);
            i--;
        }

        int index = hsCosSim - 1;
        while (index > 0)
        {
            CosSimilarityPair hsList = _list.get(0);
            _list.set(0, _list.get(index));
            _list.set(index, hsList);
            heapify(_list, 0, index);
            index--;
        }
    }

    private static void heapify(int[] _tree, int _rootindex, int _n) {
        while (2 * _rootindex + 2 <= _n) {
            int crIndex = 2 * _rootindex + 2;  //Index of the right child
            int clIndex = 2 * _rootindex + 1;  //Index of the left child
            int smallest = clIndex;

            if (crIndex < _n && _tree[clIndex] > _tree[crIndex])
                smallest = crIndex;
            if (_tree[_rootindex] <= _tree[smallest])
                break;

            //Swap the root with the largest node.
            int temp = _tree[_rootindex];
            _tree[_rootindex] = _tree[smallest];
            _tree[smallest] = temp;
            _rootindex = smallest;
        }
    }

    private static void heapify(List<CosSimilarityPair> _tree, int _rootindex, int _n) {
        //TODO Task 4.3
        while (2 * _rootindex + 2 <= _n) {
            int rhsIndex = (2 * _rootindex) + 2;
            int lhsIndex = (2 * _rootindex) + 1;
            int heapMin = lhsIndex;

            heapMin = (rhsIndex < _n && _tree.get(lhsIndex).getCosineSimilarity()
                    > _tree.get(rhsIndex).getCosineSimilarity()) ? rhsIndex : heapMin;

            if (_tree.get(_rootindex).getCosineSimilarity() <= _tree.get(heapMin).getCosineSimilarity()) {
                break;
            }
            CosSimilarityPair cosSim = _tree.get(_rootindex);
            _tree.set(_rootindex, _tree.get(heapMin));
            _tree.set(heapMin, cosSim);
            _rootindex = heapMin;
        }
    }
}
