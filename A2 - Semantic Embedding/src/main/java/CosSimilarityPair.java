public class CosSimilarityPair {
    private String strWord1;
    private String strWord2;
    private double doubCS;
    private Vector vecVector;

    public CosSimilarityPair(String _word1, String _word2, double _cosinesimilarity) {
        //TODO Task 3.1
        this.strWord1 = _word1;
        this.strWord2 = _word2;
        this.doubCS = _cosinesimilarity;
    }

    public CosSimilarityPair(Vector _vector, String _word2, double _cosinesimilarity) {
        //TODO Task 3.2
        this.vecVector = _vector;
        this.strWord2 = _word2;
        this.doubCS = _cosinesimilarity;
    }

    public String getWord1() {
        //TODO Task 3.3
        return strWord1;
    }

    public String getWord2() {
        //TODO Task 3.4
        return strWord2;
    }

    public double getCosineSimilarity() {
        //TODO Task 3.5
        return doubCS;
    }

    public void setCosineSimilarity(double _cs) {
        //TODO Task 3.6
        this.doubCS = _cs;
    }

    public void setWord1(String _word) {
        //TODO Task 3.7
        this.strWord1 = _word;
    }

    public void setWord2(String _word) {
        //TODO Task 3.8
        this.strWord2 = _word;
    }

    public Vector getVector() {
        //TODO Task 3.9
        return vecVector;
    }

    public void setVector(Vector _vector) {
        //TODO Task 3.10
        this.vecVector = _vector;
    }

}
