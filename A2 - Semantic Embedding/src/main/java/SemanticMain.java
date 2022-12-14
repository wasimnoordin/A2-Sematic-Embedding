import org.apache.commons.lang3.time.StopWatch;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SemanticMain {
    public List<String> listVocabulary = new ArrayList<>();  //List that contains all the vocabularies loaded from the csv file.
    public List<double[]> listVectors = new ArrayList<>(); //Associated vectors from the csv file.
    public List<Glove> listGlove = new ArrayList<>();
    public final List<String> STOPWORDS;

    public SemanticMain() throws IOException {
        STOPWORDS = Toolkit.loadStopWords();
        Toolkit.loadGLOVE();
    }


    public static void main(String[] args) throws IOException {
        StopWatch mySW = new StopWatch();
        mySW.start();
        SemanticMain mySM = new SemanticMain();
        mySM.listVocabulary = Toolkit.getListVocabulary();
        mySM.listVectors = Toolkit.getlistVectors();
        mySM.listGlove = mySM.CreateGloveList();

        List<CosSimilarityPair> listWN = mySM.WordsNearest("computer");
        Toolkit.PrintSemantic(listWN, 5);

        listWN = mySM.WordsNearest("phd");
        Toolkit.PrintSemantic(listWN, 5);

        List<CosSimilarityPair> listLA = mySM.LogicalAnalogies("china", "uk", "london", 5);
        Toolkit.PrintSemantic("china", "uk", "london", listLA);

        listLA = mySM.LogicalAnalogies("woman", "man", "king", 5);
        Toolkit.PrintSemantic("woman", "man", "king", listLA);

        listLA = mySM.LogicalAnalogies("banana", "apple", "red", 3);
        Toolkit.PrintSemantic("banana", "apple", "red", listLA);
        mySW.stop();

        if (mySW.getTime() > 2000)
            System.out.println("It takes too long to execute your code!\nIt should take less than 2 second to run.");
        else
            System.out.println("Well done!\nElapsed time in milliseconds: " + mySW.getTime());
    }

    public List<Glove> CreateGloveList() {
        List<Glove> listResult = new ArrayList<>();
        //TODO Task 6.1
        int i = 0;
        while (i < listVocabulary.size()) {
            if (!(STOPWORDS.contains(listVocabulary.get(i)))) {
                Glove freshGlove = new Glove(listVocabulary.get(i), new Vector(listVectors.get(i)));
                listResult.add(freshGlove);
            }
            i++;
        }
        return listResult;
    }

    public List<CosSimilarityPair> WordsNearest(String _word) {
        List<CosSimilarityPair> listCosineSimilarity = new ArrayList<>();
        //TODO Task 6.2
        Vector wordsNearVec = null;

        if (!listVocabulary.contains(_word) || STOPWORDS.contains(_word)) {
            _word = "error";
        }
        for (Glove wnGlove : listGlove) {
            if (wnGlove.getVocabulary().equals(_word)) {
                wordsNearVec = wnGlove.getVector();
                break;
            }
        }
        for (Glove csGlove : listGlove) {
            if (!csGlove.getVocabulary().equals(_word)) {
                listCosineSimilarity.add(new CosSimilarityPair
                        (_word, csGlove.getVocabulary(), csGlove.getVector().cosineSimilarity(wordsNearVec)));
            }
        }
        return HeapSort.doHeapSort(listCosineSimilarity);
    }

    public List<CosSimilarityPair> WordsNearest(Vector _vector) {
        List<CosSimilarityPair> listCosineSimilarity = new ArrayList<>();
        //TODO Task 6.3
        for (Glove wnGloveVec : listGlove) {
            CosSimilarityPair gloveAnalogue = new CosSimilarityPair
                    (_vector, wnGloveVec.getVocabulary(), wnGloveVec.getVector().cosineSimilarity(_vector));
            if(gloveAnalogue.getCosineSimilarity() < 1) {
                listCosineSimilarity.add(gloveAnalogue);
            }
        }
        return HeapSort.doHeapSort(listCosineSimilarity);
    }

    /**
     * Method to calculate the logical analogies by using references.
     * <p>
     * Example: uk is to london as china is to XXXX.
     *       _firISRef  _firTORef _secISRef
     * In the above example, "uk" is the first IS reference; "london" is the first TO reference
     * and "china" is the second IS reference. Moreover, "XXXX" is the vocabulary(ies) we'd like
     * to get from this method.
     * <p>
     * If _top <= 0, then returns an empty listResult.
     * If the vocabulary list does not include _secISRef or _firISRef or _firTORef, then returns an empty listResult.
     *
     * @param _secISRef The second IS reference
     * @param _firISRef The first IS reference
     * @param _firTORef The first TO reference
     * @param _top      How many vocabularies to include.
     */
    public List<CosSimilarityPair> LogicalAnalogies(String _secISRef, String _firISRef, String _firTORef, int _top) {
        List<CosSimilarityPair> listResult = new ArrayList<>();
        //TODO Task 6.4

        Vector vectorOneTo = null;
        Vector vectorOneIs = null;
        Vector vectorTwoIs = null;

        for (Glove logicAnalogy : listGlove) {
            if (logicAnalogy.getVocabulary().equals(_firTORef)) {
                vectorOneTo = logicAnalogy.getVector();
            }
            else if(logicAnalogy.getVocabulary().equals(_firISRef)) {
                vectorOneIs = logicAnalogy.getVector();
            }
            else if (logicAnalogy.getVocabulary().equals(_secISRef)) {
                vectorTwoIs = logicAnalogy.getVector();
            }
            else if(vectorOneTo != null && vectorOneIs != null && vectorTwoIs != null)
                break;
        }

        if(_top <= 0 || vectorOneTo == null || vectorOneIs == null || vectorTwoIs == null) {
            return listResult;
        }

        listResult = WordsNearest(vectorOneTo.add(vectorTwoIs).subtraction(vectorOneIs));
        List<CosSimilarityPair> refittedArrList = new ArrayList<>();

        int index = 0;
        for (CosSimilarityPair cosSimFix : HeapSort.doHeapSort(listResult)) {
            if(!cosSimFix.getWord2().equals(_firTORef) && !cosSimFix.getWord2().equals(_secISRef)) {
                refittedArrList.add(cosSimFix);
                index++;
                if(index == _top) break;
            }
        }
        return refittedArrList;
    }
}

