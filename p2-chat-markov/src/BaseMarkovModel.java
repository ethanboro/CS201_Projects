import java.util.*;

/**
 * Implements the MarkovInterface to generate random text based
 * on a training text. Searches over training text to generate
 * each random word of generated text.
 * For use in Compsci 201, Fall 2022, Duke University
 * Updated in Fall 2023
 * Updated in Fall 2024 with AbstractMarkovModel
 * @author ola
 * @author Brandon Fain
 */

public class BaseMarkovModel extends AbstractMarkovModel {

	/**
	 * Default constructor creates order 3 model
	 */
	public BaseMarkovModel() {
		this(3);
	}

	/**
	 * Initializes a model of given order and random number generator.
	 * @param order Number of words used to generate next 
	 * random word / size of WordGrams used.
	 */
	public BaseMarkovModel(int order){
		super(order);
	}

	/**
	 * Initializes training text. Should always be called prior to
	 * random text generation.
	 */
	@Override
	public void setTraining(String text){
		myWords = text.split("\\s+");
	}

	/**
	 * Get a list of Strings containing all words that follow
	 * from wgram in the training text. Result may be an empty list.
	 * Implemented by looping over training text.
	 * @param wgram is a WordGram to search for in the text
	 * @return List of words following wgram in training text.
	 * May be empty.
	 */
	@Override
	public List<String> getFollows(WordGram wgram) {
		List<String> follows = new ArrayList<>();

		// first WordGram is at the beginning of myWords
		WordGram currentWG = new WordGram(myWords,0,wgram.length());
		for (int k = wgram.length(); k < myWords.length; k += 1) {
			String currentWord = myWords[k];
			if (currentWG.equals(wgram)) {
				follows.add(currentWord);
			}
			// shift word gram by one, add currentWord
			currentWG = currentWG.shiftAdd(currentWord);
		}
		return follows;
	}
	
}
