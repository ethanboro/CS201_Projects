# Project 2: Markov, Fall 2024

This is the directions document for Project P2-ChatMarkov in CompSci 201 at Duke University, Fall 2024.

See [the details document](docs/details.md) for information on using Git, starting the project, and more details about the project including information about the classes and concepts that are outlined briefly below. You'll absolutely need to read the information in the [details document](docs/details.md) to understand how the classes in this project work independently and together. In particular, information about testing with `JUnit` is in that document and you'll want to test your code with the `JUnit` tests.


## Introduction

Random Markov processes are widely used in Computer Science and in analyzing different forms of data. This project offers an occasionally amusing look at a *generative model* for creating realistic looking text in a data-driven way by training a model. To do so, you will implement two classes: First `WordGram` which represents immutable sequences of words, then `HashMarkovModel` which will be an efficient model for generating random text that uses `WordGram`s and `HashMap`s.

Generative models of the sort you will build are of great interest to researchers in artificial intelligence and machine learning generally, and especially those in the field of *natural language processing* (the use of algorithmic and statistical AI/ML techniques on human language). One recent and powerful example of such text-generation model via statistical machine learning program is the [OpenAI GPT project](https://openai.com/blog/chatgpt). In this
project youll be creating what are reasonably and historically called _small language models_ as opposed to the _large language models_ (LLMs) behind ChatGPT, Claude, Gemini, and more.

### Historical details of this assignment (optional, Engagement points)

The historical and literary references below can be read for optional enagagement points by filling out the
questions accessible via this form:https://bit.ly/p2-preengage-2024. You don't need to read this to do the project, but you can earn these
engagement points *without looking at or writing code*! 

This assignment has its roots in several places. The true mathematical roots are from a 1948 monolog by Claude Shannon, [A Mathematical Theory of Communication](https://docs.google.com/viewer?a=v&pid=sites&srcid=ZGVmYXVsdGRvbWFpbnxtaWNyb3JlYWRpbmcxMmZhbGx8Z3g6MThkYzkwNzcyY2U5M2U5Ng) which discusses in detail the mathematics and intuition behind this assignment. In particular, the `BaseMarkovModel` uses the approach Shannon describes below:

> To construct [a Markov model of order 1], for example, one opens a book at random and selects a letter at random on the page. This letter is recorded. The book is then opened to another page and one reads until this letter is encountered. The succeeding letter is then recorded. Turning to another page this second letter is searched for and the succeeding letter recorded, etc. It would be interesting if further approximations could be constructed, but the labor involved becomes enormous at the next stage.

For *two engagement points*, find something interesting about Claude Shannon and answer questions in the form: https://bit.ly/p2-preengage-2024 

You can see Shannon's ideas expressed in the so-called [_Infinite Monkey Theorem_](https://en.wikipedia.org/wiki/Infinite_monkey_theorem) which, in turn, is the basis for a somewhat amusing or arguably disturbing short story named _Inflexible Logic_ now found in pages 91-98 from [_Fantasia Mathematica (Google Books)_](http://books.google.com/books?id=9Xw8tMEmXncC&printsec=frontcover&pritnsec=frontcover#PPA91,M1) and reprinted from a 
[1940 New Yorker story called by Russell Maloney](https://www.newyorker.com/magazine/1940/02/03/inflexible-logic). For four engagement points, read this short story and the Wikipedia article linked above and answer the questions in the form: https://bit.ly/p2-preengage-2024.


This assignment has its roots in a Nifty Assignment designed by Joe Zachary from U. Utah, assignments from Princeton designed by Kevin Wayne and others, and the work done at Duke starting with Owen Astrachan and continuing with Jeff Forbes, Salman Azhar, Branodn Fain, and the UTAs from Compsci 201.


## General Work for this project

Your goal is to create a more efficient Markov-Generating program/class than the provided `BaseMarkovModel` class. As you'll see below, you'll call this new class `HashMarkovModel` and it will generate the same random text as `BaseMarkovModel`, but it will generate that text _much more efficiently_. You will be able to run `BaseMarkovModel` only after you've implemented the `WordGram` class completely. So that's the first step in creating `HashMarkovModel`.

You're given several classes to test the `WordGram` class you'll develop. It's likely that when you've developed the `WordGram` class and it passes all tests that you'll be able to run `BaseMarkovModel` to see random text generated. Then you'll be able to develop the more efficient `HashMarkovModel` class.


## What is a `WordGram`

You will implement a class called `WordGram` that represents a sequence of words (represented as strings), just like a Java String represents a sequence of characters. Just as the Java String class is an immutable sequence of characters, the `WordGram` class you implement will be an immutable sequence of strings. Immutable means that once a WordGram object has been created, it cannot be modified. You cannot change the contents of a `WordGram` object -- just as you cannot change the contents of a String in Java. However, you can create a new WordGram from an existing `WordGram`.

For details about the `WordGram` class and the concepts in it, see the [details document](docs/details.md) -- the explanation below assumes you have a *very good* understanding of the `WordGram` class.

## Running Driver Code

The primary driver code for this assignment is located in `Chat201Driver.java`. You should be able to run the `public static void main` method of `Chat201Driver` immediately after cloning the starter code, and should see something like the output shown in the section below (noting that your exact runtimes will likely be different / machine dependent). Note that *there is no random text generated* because you must implement `WordGram` before the code in `BaseMarkovModel` works.

Expand for example output of MarkovDriver with starter code

```

Trained on text in data/alice.txt with T=28196 words
Training time = 0.011 s
Generated N=100 random words with order 2 Markov Model
Generating time = 0.002 s
----------------------------------
 
----------------------------------
```

This initial output is blank now because the `WordGram` class is not correctly implemented; that will be your first coding task. Before starting to code however, you are encouraged to inspect `Chat201Driver` a little more closely to understand what it is doing. You will find details about the `Chat201Driver` class in the class and its comments as well  as in the [details document](docs/details.md) that is part of this project.


## JUnit Tests

You'll develop code in three classes: `WordGram`, `HashMarkovModel` and `Chat201Driver`. Each of theses has a corresponding JUnit testing program. See
the [details document](docs/details.md) for information on how to run `JUnit` tests in VSCode and how to read the test results. You should *absolutely* make sure your code passes these tests when writing each program before proceeding to the next class, e.g., after coding `WordGram` you should use JUnit to
run the tests in `WordGramTest` and in `Chat201Test`-- again, see the [details document](docs/details.md) for *how to run these tests.*


## Coding Part 1: Developing the `WordGram` Class

Your first task is to develop the `WordGram` class itself. You're given an outline of `WordGram.java` with appropriate instance variables declared, as well as stub (not fully/correctly implemented) methods.

Your task will be to implement these methods in `WordGram` according to the specifications provided. Javadocs in the starter code detail the expected behavior of all methods. For `hashCode`, `equals`, and `toString`, your implementations should conform to the specifications as given in the [documentation for `Object`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html). 

Note that `WordGram` objects are *immutable*, meaning they should not change after creation (similar to Java Strings). Therefore, none of the methods except the constructor should *mutate* (or change) the words assosciated with a `WordGram` object.

For details on the constructor and methods of the `WordGram` class see the comments in the class you're given when you clone the project and the detailed descriptions in the [details document](docs/details.md).

After implementing the `WordGram` class, you should run the `WordGramTest` [JUnit tests](#junit-tests) to verify that your implementation is correct. You'll want to run the tests in `Chat201Test` as well, since that code also uses `WordGram` objects.

After correctly implementing the `WordGram` class, re-run the `Chat201Driver`. With the default values (`TEXT_SIZE = 100`, `RANDOM_SEED = 1234`, `MODEL_ORDER = 2`, `PRINT_MODE = true`, and `filename = "data/alice.txt"`) you should see different output than when you first ran the starter code, something like what is shown below.

```
Trained on text in data/alice.txt with T=28196 words
Training time = 0.012 s
Generated N=100 random words with order 2 Markov Model
Generating time = 0.060 s
----------------------------------
Alice; `I daresay it's a set of verses.' `Are they in the distance, and she swam 
about, trying to touch her. `Poor little thing!' said Alice, `a great girl like you,' 
(she might well say this), `to go on with the Dutchess, it had made. `He took me 
for a few minutes to see a little worried. `Just about as it turned a corner, `Oh 
my ears and whiskers, how late it's getting!' She was close behind it was growing, 
and very neatly and simply arranged; the only one who had got its head to keep back 
the wandering hair
```

Note in particular how the phrases/sentences seem reasonably connected. As you will see when inspecting `BaseMarkovModel`, if it cannot find a given `WordGram` to calculate possible following words, it stops generating text.

*Caution*: Seeing the output shown above does not necessarily mean that every method of your `WordGram` class is correct. In particular, `BaseMarkovModel` does not use hashing, and so the `hashCode()` method does not impact it, but you should correctly implement *all* methods of `WordGram` before proceeding to the next part.


## Coding Part 2: Developing the HashMarkovModel Class

In this part you will develop a Markov model for generating random text using `WordGram`s and hashing. In particular, you should create a new `HashMarkovModel.java` file with a single public `HashMarkovModel` class that extends  `AbstractMarkovModel`. See the `BaseMarkovModel` class and the [details document](docs/details.md) for more complete information.

Your implementation should have the same behavior as `BaseMarkovModel` in terms of implementing the abstract methods `getFollows` and `setTraining` and generating the same output, but it will have different performance properties due to the use of a `HashMap` in training and finding following strings. See the
[details document](docs/details.md) for information on creating a private `HashMap` instance variable as well as more details on how 
to develop and test your code.

You can and should use `BaseMarkovModel` as an example for how to implement the two abstract methods, noting in particular that you *must* override and implement these methods. *See details in the [details document](docs/details.md). 

### Running and Testing HashMarkov

The `Chat201Driver` sets a `RANDOM_SEED` to initialize the random number generator. You are welcome to change that value to experiment and play around with different random generations of text, but you should be sure to set it to 1234 for testing/submitting. Note that *if you use the same value for `RANDOM_SEED` you should get the same random text for `BaseMarkovModel` and `HashMarkovModel`*, if not, something is likely wrong with your implementation.

You can also test your `HashMarkovModel` class with the `MarkovModelTest` [JUnit tests](#junit-tests). Don't forget to edit the `getModel` method of `MarkovModelTest` to use a `HashMarkovModel` implementation when running your tests.

Note that JUnit tests and Gradescope tests will not check the efficiency of the `HashMarkovModel` implementation. Instead, you will need to rely on your conceptual understanding and empirical timing data which you can aquire by running `Chat201Driver` using a `HashMarkovModel` implementation. You will be asked to reason about both in the Analysis section.

Once you are confident that your `HashMarkovModel` code is correct, you are ready to submit to Gradescope and then move on to the analysis questions.


## Analysis Questions

Answer the following questions in your analysis. You'll submit your analysis as a separate PDF as a separate assignment to Gradescope. Answering these questions will require you to run the driver code to generate timing data and to reason about the algorithms and data structures you have implemented. We will include a template file for submitting your answers.

### Working Together for Analysis

You're *stronlgy encouraged* to work with others in 201 in completing the analysis section for this project. In future projects you'll work on an entire project in pairs, and submit once for the pair. For this project, however, each person should submit independently. If you actively work with one or more people in 201, *please make sure* you list each other in the analysis document you turn in.

### Big-O/O-notation for analysis questions

For the analysis, let $`N`$ denote the length/number of words of the random text being generated. Let $`T`$ denote the length/number of words of the training text. Assume that *all words are of at most a constant length* (say, no more than 35 characters). To help in using the files in the `data` folder, here are is some information about total number of words and number of different words in some of the files:

|file    |# different words| # total words|
|--------|---------------|--------------|
|alice.txt| 5,910 | 28,196 |
|hawthorne.txt| 14,123| 85,753|
|kjv10.txt| 34,027 | 823,135|
|littlebrother.txt|18,304| 119,986|
|melville.txt| 4,256 | 14,353|
|romeo.txt| 6,394 | 25,788 |
| shakespeare.txt | 67,505 | 901,325|

### Question 1 (4 points)

What is the asymptotic (big O) runtime complexity of each of the methods: `setTraining()` `getRandomText()` for the `BaseMarkovModel` implementation in terms of $`N`$ and $`T`$? State your answers, and justify them in *both* of the following ways.

- *Theory*. Explain why you expect `setTraining()` in `BaseMarkovModel` and `getRandomText()` from `Chat201Driver` when `BaseMarkovModel` is used
as the model to have the stated runtime complexity by referencing the algorithms/data structures/code used. Explain the complexity of each operation/method, accounting for any looping, in the code. You may assume that `nextInt` is a constant time operation to generate a random number and `split()` has runtime complexity $`O(T)`$ when called on the training text.

- *Experiment*. Run the main method of `Chat201Driver` with *at least* 3 different data files of varying sizes $`T`$ (it is fine to use `alice.txt` for one of them). For each, run the main method with *at least* 3 different values of `TEXT_SIZE` (which corresponds to $`N`$). So you should have a total of at least 9 data points; use these to fill out a table like the one shown below. *Explain how your empirical data does or does not conform to your expectations for the runtime complexity of `getRandomText()`.*

| Data file    | $`T`$    | $`N`$    | Training Time (s)    | Generating time (s)    |
| ------------ | -------- | -------- | -------------------- | ---------------------- |
| alice.txt    | 28,196   | 100      | 0.012                | 0.060                  |
| ...          | ....     | ...      | ...                  | ...                    |

*Suggestions*: You will likely get the clearest data if you include very different values for $`T`$ and $`N`$, and preferably larger values -- see the table of sizes above to help you choose text files you'll use. You can set $`N`$ directly to much larger values by changing `TEXT_SIZE`, for example to 1,000 or 10,000. When doing so, you can set `PRINT_MODE` to `false` in `Chat201Driver` to avoid having a large amount of text printed to your terminal. Note that `BaseMarkovModel` is not necessarily an efficient implementation, so it may take a long time to run with large $`T`$ and $`N`$. You do not need to run anything for multiple minutes just for data collection for this assignment.  

### Question 2 (4 points)

Same as Question 1, but for `HashMarkovModel` instead of `BaseMarkovModel`: What is the asymptotic (big O) runtime complexity of the methods: `setTraining()` and for the `Chat201Driver` `getRandomText()` when  a `HashMarkovModel` is used in terms of $`N`$ and $`T`$? State your answers, and justify them in *theory and experiment* exactly as you did for Question 1. 

You can use the same training texts and values for $`N`$ as you chose in question 1, with the same suggestions mentioned there. Note that, implemented correctly, `HashMarkovModel` should be noticably more efficient at generating random text than `BaseMarkovModel`, and this should be evident in your analysis.

### Question 3 (4 points)

Markov models like the one you implemented in this project are one example of a larger research area in artificial intelligence (AI) and machine learning (ML) called *generative models* for *natural language processing*. Currently, one of the state-of-the-art models is called *GPT*, created by [OpenAI](https://openai.com/about/). OpenAI states that their "mission is to ensure that artificial general intelligence (AGI)—...highly autonomous systems that outperform humans at most economically valuable work—benefits all of humanity." 

GPT is not, however, open-source, meaning that the underlying source code of the model is not freely available and the model is only accessible via API calls. Read this short article about open source code in artificial intelligence: Can’t Access GPT-3? Here’s GPT-J — Its Open-Source Cousin [accessible via this link](https://towardsdatascience.com/cant-access-gpt-3-here-s-gpt-j-its-open-source-cousin-8af86a638b11). 

Answer both of the following related questions:
- What do you think of OpenAI's stated mission? In particular, do you think that "highly autonomous systems that outperform humans at most economically valuable work" can benefit all of humanity? Why or why not?
- Do you think new research code in AI/ML should be more open source? Why, or why not? 

There is no right or wrong answer to either question; we are looking for one or two paragraphs of thoughtful reflection.

### Question 4 (challenge, optional, four engagement points)

The `AbstractMarkovModel.getRandomNextWord` returns the static string ``AbstractMarkovModel.END_OF_TEXT` if there are no strings that follow the particular `WordGram` parameter. That can happen, for example, if the `WordGram` is the last few words in a file and is unique, so no string follows it. Add code to the program `Chat201Driver` to generate 100 strings a 1,000 times using `data/alice.txt` and count how many times fewer than 100 strings are generated because `END_OF_TEXT` is returned and the the code in `getRandomText` exits early. You can simply split the returned string in the `main` method and see if its length is less than 100. Copy/paste the code into your analysis doc and report on how many times fewer strings are generated than asked fro. 


## Submitting and Grading
You will submit the assignment on Gradescope. You can access Gradescope through the tab on Canvas. Please take note that changes/commits on GitLab are NOT automatically synced to Gradescope. You are welcome to submit as many times as you like, only the most recent submission will count for a grade.

Don't forget to upload a PDF for the analysis part of this assignment and mark where you answer each question. This is a separate submission in Gradescope.


### Grading

| Section.  | points |
|-----------|--------|
| WordGram  | 8   |
| HashMarkovModel| 8   |
| Analysis  | 12  |

