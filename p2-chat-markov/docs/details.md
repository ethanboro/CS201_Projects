## Starter Code and Using Git

**_You should have installed all software (Java, Git, VS Code) before completing this project._** You can find the [directions for installation here](https://coursework.cs.duke.edu/201fall24/resources-201/-/blob/main/installingSoftware.md) (including workarounds for submitting without Git if needed).

We'll be using Git and the installation of GitLab at [coursework.cs.duke.edu](https://coursework.cs.duke.edu). All code for classwork will be kept here. Git is software used for version control, and GitLab is an online repository to store code in the cloud using Git.

**[This document details the workflow](https://coursework.cs.duke.edu/201fall24/resources-201/-/blob/main/projectWorkflow.md) for downloading the starter code for the project, updating your code on coursework using Git, and ultimately submitting to Gradescope for autograding.** We recommend that you read and follow the directions carefully when working on a project! While coding, we recommend that you periodically (perhaps when completing a method or small section) push your changes as explained.


## Coding in Project P2: Markov

When you fork and clone the project, **make sure you open the correct project folder in VS Code** following the [directions shown here](https://coursework.cs.duke.edu/201fall24/resources-201/-/blob/main/projectWorkflow.md#step-3-open-the-project-in-vs-code). 

## What is a WordGram?

The number of strings contained in a `WordGram` is sometimes called the *order* of the WordGram, and we sometimes call the `WordGram` an *order-k* WordGram, or a *k-gram* -- the term used in the Markov program you'll implement.  You can see some examples of order-3 `WordGram` objects below.

| | | |
| --- | --- | --- |
| "cat" | "sleeping" | "nearby" |
| | | |

and 
| | | |
| --- | --- | --- |
| "chocolate" | "doughnuts" | "explode" |
| | | |


### What is a Markov Model?

Markov models are random models with the Markov property. In this project you can think of the Markov Model as a _small language model_. You've certainly heard about ChatGPT, Claude, Gemini and other _large language models_. In our case, we want to create a Markov model for generating random text that looks similar to a training text. Your code will generate one random word at a time, and the Markov property in our context means that the probabilities for that next word will be based on the previous words -- more precisely on 3 previous words in an order-3 Markov Model and the `k`previous words in an order-`k` Markov model.

An order-k Markov model uses order-k `WordGram`s to predict text: we sometimes call these *k-grams* where *k* refers to the order. To begin, we select a random k-gram from the *training text* (the data we use to create our model; we want to generate random text based on the training text). Then, we look for instances of that k-gram in the training text to calculate the probabilities corresponding to words that might follow. We then generate a new word according to these probabilities, after which we repeat the process using the last k-1 words from the previous k-gram and the newly generated word. Continue in that fashion to create the desired number of random words. 

Here is a concrete example. Suppose we are using an order 2 Markov model with the following training text (located in `testfile.txt`):

```
this is a test
it is only a test
do you think it is a test
this test it is ok
it is short but it is ok to be short
```

We begin with a random k-gram, suppose we get `[it, is]`. This appears 5 times in total, and is followed by `only`, `a`, `ok`, `short`, and again by `ok` for the five occurences of `[it is]`. So the probability (in the training text) that `it is` is followed by `ok` is 2/5 or 40%, and for the other words is 1/5 or 20%. To generate a random word following the 2-gram `[it, is]`, we would therefore choose `ok` with 2/5 probability, or `only`, `a`, or `short` with 1/5 probability each.

Rather than calculating these probabilities explicitly, your code will use these probabilities implicitly. In particular, the `AbstractMarkovModel.getFollows` method will return a `List` of *all* of the words that follow after a given k-gram in the training text (including duplicates), and then you will choose one of these words uniformly at random. Words that more commonly follow will be selected with higher probability by virtue of these words being duplicated in the `List`. In our example above with `[it is]` the `getFollows` method would return the `List` `["only", "a", "ok", "short", "ok"]`.

Suppose your code chooses `ok` as the next random word. Then the random text generated so far is `it is ok`, and the current `WordGram` of order 2 we are using would be updated to `[is, ok]` --- by dropping the `it` and adding `ok`. We then again find the following words in the training text, and so on and so forth, until we have generated the desired number of random words.

Of course, for a very small training text these probabilities may not be very meaningful, but random generative models like this can be much more powerful when supplied with large quantities of training data, in this case meaning very large training texts.

## The Chat201Driver class

*You will modify the `main` method in this class to answer analysis questions.*

- Some static variables used in the main method are defined at the top of class, namely:
  - `TEXT_SIZE` is the number of words to be randomly generated.
  - `RANDOM_SEED` is the random seed used to initialize the random number generator. You should always get the same random text given a particular random seed and training text.
  - `MODEL_ORDER` is the order of `WordGram`s that will be used.
  - `PRINT_MODE` can be set to true or false based on whether you want the random text generated to be printed.
- The `filename` defined at the beginning of the main method determines the file that will be used for the training text. By default it is set to `data/alice.txt`, meaning the text of *Alice in Wonderland* is being used. Note that data files are located inside the data folder.
- An `AbstractMarkovModel` object named `generator` is created. By default, it uses `BaseMarkovModel` as the implementing class, a complete implementation of which is provided in the starter code. Later on, when you have developed `HashMarkovModel`, you can comment out the line using `BaseMarkovModel` and uncomment the line using `HashMarkovModel` to change which implementation you use.
- The `generator` then sets the specified random seed. You should get the same result on multiple runs with the same random seed. Feel free to change the seed for fun while developing and running, but *the random seed should be set to 1234 as in the default when submitting for grading*.
- The `generator` is timed in how long it takes to run two methods: first `generator.setTraining()` and then the static method `getRandomText()` you will implement in `Chat201Driver`.
- Finally, values are printed: The random text itself if `PRINT_MODE` is set to true and the time it took to train (that is, for `setTraining()` to run) the Markov model and to generate random text using the model (that is, for `getRandomText` to run). 

## Developing the WordGram class: details

The constructors and methods of the `WordGram` class are detailed below. 

### Constructors 

You'll construct a `WordGram` object by passing as constructor arguments: an array, a starting index, and the size (or order) of the `WordGram.` You'll **store the strings in an array instance variable** by copying them from the array passed to the constructor.

There are three instance variables in `WordGram`:
```
private String[] myWords;
private String myToString;
private int myHash;
```

The constructor for WordGram `public WordGram(String[] source, int start, int size)`
should store `size` strings from the array `source`, starting at index `start` (of `source`) into the private `String` array instance variable `myWords` of the `WordGram` class *starting at index zero of `myWords`*. The array `myWords` should contain exactly `size` strings. 

For example, suppose parameter `words` is the array below, with "this" at index 0.

| | | | | | | |
| --- | --- | --- | --- | --- | --- | --- |
| "this" | "is" | "a" | "test" |"of" |"the" |"code" |
| | | | | | |

The call `new WordGram(words,3,4)` should create this array `myWords` since the starting index is the second parameter, 3, and the size is the third parameter, 4.

| | | | |
| --- | --- | --- | --- |
| "test" | "of" | "the" | "code"|
| | | | |

You can initialize the instance variables `myToString` and `myHash` in the constructor stub to whatever default values you choose; these will change when you implement the methods `.toString()` and `.hashCode()`, respectively. It's fine to not assign any values to them in the constructor. This will make the value of `myHash == 0` and the value of `myToString == null` since those are the default values for instance variables in a class.
 

### The length() method 

The `length()` method should return the order of the `WordGram`, that is, the length of `myWords`. 

### The .equals method

The `equals()` method should return `true` when the parameter passed is a `WordGram` object with **the same strings in the same order** as this object. 

The [Java API specification of `.equals()`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#equals(java.lang.Object) takes an `Object` type as input. Thus, the first thing the `WordGram` `equals()` method should do is check if the parameter passed is really a `WordGram` using the `instanceof` operator, and if not return false. Otherwise, the parameter can be *cast* as a `WordGram`. This is done for you in the starter code and you do not need to change it.

Then what you need to do is compare the strings in the array `myWords` of `other` and `this` (the object on which `equals()` is called). Note that `WordGram` objects of different lengths cannot be equal, and *your code should check this.*

### The hashCode() method

The `hashCode()` method should return an `int` value based on all the strings in instance variable `myWords`. See the [Java API documentation](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#hashCode()) for the design constraints to which a `hashCode()` method should conform. 

Note that the Java String class already has a good [`.hashCode()` method](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html#hashCode()) we can leverage. Use the `.hashCode()` of the String returned by `this.toString()` to implement this method.

Since `WordGram` objects *are immutable* (do not change after creation), you do not need to recompute the hash value each time `.hashCode()` is called. Instead, you can compute it the first time `.hashCode()` is called (which you can check against whatever default value you might set in the constructor), and store the result in the `myHash` instance variable. On subsequent calls, simply return `myHash`. You'll do this with code similar to:
```
    if (myHash == 0) {
      // assign something to myHash, this will happen ONCE!
    }
    return myHash;
```

### The shiftAdd() method

If this `WordGram` has `k` entries then the `shiftAdd()` method should create and return a _**new**_ `WordGram` object, also with `k` entries, whose *first* k-1 entries are the same as the *last* k-1 entries of this `WordGram`, and whose last entry is the parameter `last`. Recall that `WordGram` objects are immutable and cannot be modified once created - **this method must create a new WordGram object** and copy values correctly to return in the new `WordGram` created in the `shiftAdd` method.

For example, if `WordGram w` is 
| | | |
| --- | --- | --- |
| "apple" | "pear" | "cherry" |
| | | | 

then the method call `w.shiftAdd("lemon")` should return a new `WordGram` containing {"pear", "cherry", "lemon"} in that order. Note that this new `WordGram` will not equal the `WordGram` object `w` on which `shiftAdd` is called.

Note: To implement `shiftAdd()` you'll need to create a new `WordGram` object. The code you write in `shiftAdd` will be able to *assign values to the private instance variables of that new object directly* since the `shiftAdd()` method is in the `WordGram` class.

### The toString() method

The `toString()` method should return a printable `String` representing all the strings stored in the `WordGram` instance variable `myWords`, each separated by a single blank space (that is, `" "`). You may find the String `join` method useful, see [the documentation](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html#join(java.lang.CharSequence,java.lang.CharSequence...)).

You do not need to recompute this `String` each time `toString()` is called since the `WordGram` class is immutable-- instead, store the String being returned in instance variable `myToString`, similarly to what your code did in `hashCode` with `myHash`. On subsequent calls your code should simply return the value stored in `myToString` (again using the immutability of `WordGram`s to ensure this value will not change). To determine whether a given call to `toString()` is the first, you can compare to the default constructor value of `myToString` which is `null` unless you made an explicit assignment in the constructor.

## JUnit Testing in P2-ChatMarkov

To help test your `WordGram`, `Chat201Markov1`, and `HashMarkovModel` implementations, you are given some *unit tests* in three
classes: `WordGramTest.java`, `Chat201Test`, and `MarkovModelTest.java`, all located in the `src` folder. A unit test specifies a given input and asserts an expected outcome of running a method, then runs your code to confirm that the expected outcome occurs. You can see the exact tests inside of these Java files. the JUnit library used by these testing classes is a very widely-used industry standard for unit testing.

Note that by default (to avoid compiler errors in the starter code), `MarkovModelTest` is testing the `BaseMarkovModel` implementation. When you are ready to test your `HashMarkovModel` implementation, you will want to change which model is created in the `getModel` method of `MarkovModelTest` at the position shown in the screenshow below (if the image does not render for you, you can find them in the `figures` folder). You will also make similar modifications
to the code in `Chat201Test` since that class includes test methods that will test how your `WordGram` class works with both `Base` and `Hash` Models.


<div align="center">
  <img src="figures/getmodel.png">
</div>


In order **to run these tests** inside VS Code, click the [Test Explorer](https://code.visualstudio.com/docs/java/java-testing#_test-explorer) (beaker) icon on the left side of VS Code (it should be the lowest icon on the panel). You can expand the arrow for `p2-chatmarkov` and the default package to see three sets of tests: `Chat201Test`, `MarkovModelTest`, and `WordGramTest`. You can click the run triangle next to each test package to run the tests. See the screenshot example below. *Note that JUnit programs are run by the JUnit library and the beaker-icon, not be running them as Java programs.* You'll run the tests by clicking the triangle in the left panel, and you'll see the results in the _Test Results_ window rather than in the Terminal or Debugger window in VSCode.

For example, you can test all the tests in `WordGramTest` by hovering over that label in the _TestExplorer_ panel which is active when you click the Beaker-Icon, and is shown in the screenshot below. You can also run each individual unit test by hovering and clicking on each test's run triangle. The results of the tests are in the VSCode _TEST RESULTS_ panel, not in the other panels where output is shown. Deciphering error JUnit error messages is not always straightforward -- but when the tests pass? You'll get all green.

<div align="center">
  <img src="figures/markovtest.png">
</div>



The main benefit of JUnit tests lies in their ability to examine isolated "units" of code — that is, to check correctness of a segment with minimal reliance on other relevant code and data. Additionally, the purpose of supplying these *local* (on your own machine) tests is to allow you to catch potential problems quickly without needing to rely on the (somewhat slower) Gradescope autograder until you are reasonably confident in your code. You do not have to use them for a grade.

<details>
<summary>Expand for optional JUnit details</summary>

We use a major Java library called [**JUnit**](https://junit.org/junit5/) (specifically version 5) for creating and running these unit tests. It is not part of the standard Java API, so we have supplied the requisite files `JAR` files (Java ARchive files) along with this project in a folder called `lib` (you don't need to do anything with this).   

</details

## The HashMarkovModel class


This class *must extend* the class `AbstractMarkovModel` just as the class `BaseMarkovModel` does. This means your new class will
inherit the protected instance variables you'll see in `AbstractMarkovModel`. Look at the class `BaseMarkovModel` for details.

However, you will need a `HashMap` instance variable that maps from `WordGram`s (the keys) to `List<String>` (the values). You may find this useful:
```
    private HashMap<WordGram,List<String>> myMap;
```

### Constructor

You must have at least one constructor that takes as input the order of `WordGram`s used in the model. The *first line of the constructor must be `super(order)` just as you'll see* in `BaseMarkovModel` -- this is required when one class inherits from (extends) another class in Java. You'll also need to assign to the `HashMap` private instance variable.

### The setTraining() method

Similar to `BaseMarkovModel`, your `setTraining()` method should store the words of the training text in an Array of Strings. The easiest way is to use the method call `text.split("\\s+")` as seen in `BaseMarkovModel` - the regular expression `\\s+` is used to split on all whitespace, including spaces and newline characters.

In addition, you *must clear the `HashMap` instance variable* (for example, if the name of the variable is `myMap`, you can do this by calling `myMap.clear();`). This ensures that the map does not contain stale data if `setTraining()` is called multiple times on different training texts.

Finally, you should loop through the words in the training text *exactly once*. For each `WordGram` of size `k`, where `k` is the order of the `HashMarkov` model,  you'll use the `WordGram` as a key, and add the String that follow it as an entry in the`ArrayList` object that's the value associated with that `key` -- note that in the explanation above you'll see `List<String>` as the type of the value associated with each `WordGram` key in the map. Your code *must create an `ArrayList` value to assign to each key in the map*. 

You'll create a `WordGram` object from the first `myOrder` words, then loop over all the strings that are after those first strings. You'll find code very similar to this in the `BaseMarkovModel` method `getFollows` since some of that logic now moves into the the `HashMarkovModel` method `setTraining`. When your code looks up a `WordGram` model as a key in the `HashMap` instance variable it will create a new `ArrayList` as the value associated with that key the first time the `WordGram` occurs. Then your code wil call `myMap.get(wg).add(str)` where `str` is the string that occurs after the `WordGram`. Note that the values in the `HashMap` have type `List<String>`, but you'll create new `ArrayList` objects (since `List` is an interface).

As you loop over all strings, you'll change the `WordGram` in the body of the loop by calling `shiftAdd` which creates a new `WordGram` with the next-to-be-looped-over string after it. Think about that!


### The getFollows() method

Just like in `BaseMarkovModel`, the `getFollows` method takes a `WordGram` object `wgram` as a parameter and should return a `List` of all the words (containing `String` objects) that follow the `wgram` in the training text. However, the `HashMarkovModel` implementation *must* be more efficient, as it should *not* loop over the training text, but should instead *simply lookup the `List` in the `HashMap` instance variable intialized during `setTraining()`*, or return an empty `List` if the `wgram` is not a key in the map. 

*This means that the `getFollows` method will be O(1) instead of O(N) where N is the size of the training text.*

