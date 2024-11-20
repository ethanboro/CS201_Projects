import java.util.*;

public class HashMarkovModel extends AbstractMarkovModel {
    private HashMap<WordGram, List<String>> myMap;
    public HashMarkovModel(){
        super(3);
        myMap = new HashMap <WordGram, List<String>>();
    }

    public HashMarkovModel(int order){
        super(order);
        myMap = new HashMap <WordGram, List<String>>();
    }


    @Override
    public void setTraining(String txt) {
        // TODO implement this method
        myWords = txt.split("\\s+");
        myMap.clear();
        WordGram wg = new WordGram(myWords,0,myOrder);
        for (int k=0; k< myWords.length - myOrder; k++) {
            String s = myWords[k + myOrder];
            if (myMap.keySet().contains(wg)) {
                myMap.get(wg).add(s);
            }
            else {
                ArrayList<String> array = new ArrayList<String>();
                array.add(s);
                myMap.put(wg, array);
            }
            wg = wg.shiftAdd(s);
        }
    }




    @Override
    public List<String> getFollows(WordGram wgram) {
        // TODO implement this method
        if(!myMap.containsKey(wgram)) {
            return new ArrayList<String>();
        }
        return myMap.get(wgram);
    }
}