import java.util.*;

/**
 * Refactored from interface in previous 201 semesters
 * into an abstract base class to avoid copy/paste with code
 * when implementing HashMarkovModel
 * 
 * September, 2024
 * 
 * Implementing classes must supply implementations of the
 * two abstract methods. Other methods in this class are defined
 * in terms of those.
 * 
 * @author Owen Astrachan
 */


public abstract class AbstractMarkovModel {

    public static String END_OF_TEXT = "*** ERROR ***";  	
	protected String[] myWords;		// Training text split into array of words 
	protected Random myRandom;		// Random number generator
	protected int myOrder;			// Length of WordGrams used
	
    public AbstractMarkovModel(int order){
        myOrder = order;
        myRandom = new Random();
    }

    /**
	 * Set the training text for subsequent random text generation.
	 * Should always be called prior to calling getRandomText.
	 * @param text is the training text
	 */
	public abstract void setTraining(String text);
	

	/**
	 * Get a list of Strings containing all words that follow
	 * from wgram in the training text. Result may be an empty list.
	 * @param wgram is a WordGram to search for in the text
	 * @return List of words following wgram in training text.
	 * May be empty.
	 */
	public abstract List<String> getFollows(WordGram wgram);

	/**
	 * Returns a random word that follows kGram in the training text.
	 * In case no word follows kGram, returns END_OF_TEXT
	 * @param wgram is being searched for in training text. Typically
	 * the previous words of the randomly generated text, but could be
	 * an arbitrary WordGram.
	 * @return a random word among those that follow after kGram in 
	 * the training text, or END_OF_TEXT if none
	 */
	public String getRandomNextWord(WordGram wgram) {
		List<String> follows = getFollows(wgram);
		if (follows.size() == 0) {
			return END_OF_TEXT;
		}
		else {
			int randomIndex = myRandom.nextInt(follows.size());
			return follows.get(randomIndex);
		}
	}

	public WordGram getRandomGram(){
		int index = myRandom.nextInt(myWords.length - myOrder + 1);
		WordGram current = new WordGram(myWords,index,myOrder);
		return current;
	}
	/**
	 * Returns the order of the Markov Model, typically set at construction 
	 * @return the order of this model
	 */
	public int getOrder() {
        return myOrder;
    }	
	
	/**
	 * Sets the random seed and initializes the random 
	 * number generator. A given implementing class should
	 * always produce the same randomText given the same 
	 * training text and random seed.
	 * @param seed initial seed for java.util.Random
	 */
	public void setSeed(long seed) {
        myRandom.setSeed(seed);
    }
}
