

# Project 1: NBody

This is the directions document for Project P1 NBody in CompSci 201 at Duke University, Fall 2024. You'll be directed in several
places to the [details](docs/details.md) document since this explanation is more high-level.


## Background for context

This assignment heavily borrows from Princeton and Berkeley Computer Science and the work of Robert Sedgewick, Kevin Wayne and Josh Hug.

**Context:** In 1687, Isaac Newton formulated the principles governing the motion of two particles under the influence of their mutual gravitational attraction in his famous _Principia Mathematica_. However, Newton was unable to solve the problem for three particles. Indeed, in general, solutions to systems of three or more particles must be approximated via numerical simulations.
For a more complete understanding of the Physics you can reference [this document][Physics].

In this assignment, you will write a program to simulate the motion of _N_ objects in a plane, mutually affected by gravitational forces, and animate the results. Such methods are widely used in cosmology, semiconductors, and fluid dynamics to study complex physical systems. Ultimately, you will be creating a driver program `NBody.java` that runs the simulation and creates an animation of bodies moving in space interacting with each other subject to mutual gravitational forces. These bodies are modeled by the class `CelestialBody.java` that you'll implement and test independently of the simulation.

Below you can see an animation of a completed project running with some planets in our solar system. The animation repeats after one earth year, your program continues until the simulation completes.

### Example Simulation of Complete Project

<div align="center">
  <img width="500" height="500" src="p1-figures/planets.gif">
</div>


## Starter Code and Using Git

See [the details document](docs/details.md) for information on using Git, starting the project, and more details. 

### Zoom Cloud Recording

You should cloud record via your Duke Zoom account the first 20 minutes of you coding at the beginning of this project and submit a link to that recording. Before you proceed, we recommend that you verify that your Duke Zoom account is created (done by logging into Zoom at [duke.zoom.us](duke.zoom.us)) and that you have Cloud Recording permissions enabled by going to Settings -> Recording tab -> enabling Cloud Recording after logging in. Then sign into the Zoom app on your machine via the SSO option with "duke.zoom.us" as the Company Domain. For more details about using your Duke Zoom account, see [this link](https://oit.duke.edu/help/articles/kb0029318/).

To record your screen as you code, start a New Meeting, and share your entire screen. You do not need to record yourself via your camera or audio; the screen is all we need to see. Once you have shared your screen, hit Record to Cloud. After 20 minutes, hit Stop the Recording. After you exit the meeting, Zoom will process the video in the cloud and send you an email once that the recording is available. Upon opening the link in the browser, click "Copy shareable link" on the recording to get a URL that you submit to us. **This is practice for the APT quizzes where you must record your work. Here, you should try, but you will *NOT* submit to course staff.**

## Initial Engagement Points

* Answer the questions on the [pre-project engagement form](https://forms.office.com/r/mFdQnyNvD5). For each question you answer correctly, you'll receive 0.5 engagement points (there are six questions). For completing the form you get 2 points as long as at least one answer is correct.

## Developing, Running, Testing Code

You're given the outline of a class `CelestialBody` with stub or missing methods and a missing constructor. You'll add code so that the class `CelestialBody.java` works as described below. This class represents a celestial body such as a planet or a sun.  

Finally, you will create a class `NBody.java` that drives a simulation between planets, suns, and celestial bodies interacting. This class will read a file of data that specifies the initial positions and masses of the bodies and then simulates their interaction over a set time period. The simulation will also animate the interactions between the bodies.

### Developing and Testing the CelestialBody Class

There are classes provided in the code you get that help you **test whether your constructor, getters, and update/interaction methods are correct.** Running each `TestX` class will print *PASS* or *FAIL* messages to your terminal/console window. You should only proceed to the next step in development and coding when you've passed the current test. When these tests pass, there's a good chance your code is correct, but you may uncover additional errors when you run the `NBody` simulation.

You'll implement code in `CelestialBody`, then run a testing program you're given to see if the code you wrote passes the tests. *Do not go to the next step* until your code passes each testing program.

Details about developing and testing each part of `CelestialBody` are in the [details](docs/details.md) document. Each group of related methods has a corresponding test program: `TestX`, e.g., for the second bullet item below you'll write the method `calcDistance` and test your implementation by 
running the test program `TestCalcDistance.java`.

* Constructor and getter methods
* `calcDistance` method
* `calcForceExertedBy`
* `calcForceExertedByX` (and Y)
* `calcNetForceExertedByX` (and Y)
* `update` (mutator method)

All the method you've written and tested use `CelestialBody` instance variables to calculate the forces acting on each body in a simulation, and then to `update` the state of a `CelestialBody` as part of a simulation. After developing, implementing, testing, and debugging these `CelestialBody` methods you're ready to move to the simulation code.

## The `NBody` Class

The `NBody` class will use `CelestialBody` objects to run the simulation.

This class consists only of `static` methods, including the main method that runs the simulation. Your task will be to implement the three `static` methods that have been outlined for you in the starter code. That code has `// TODO` comments indicating where you need to make edits. Details about
the changes you must make (and test) can be found in the [details](docs/details.md) document.

<div align="center">
  <img src="p1-figures/NBodyMethods.png">
</div>

### Details on Data Format

The data for planets, suns, and celestial bodies in general is in the format shown below. **All files in the folder data are in this format.** For example, this is the file `data/planets.txt`:

<div align="center">
  <img src="p1-figures/format.png">
</div>

The first value is an integer _**n**_, the number of bodies for which data is given in the file. The next value is a `double`, the radius of the universe for the simulation. This value is used to set the scale for the animation.

Next, there are _**n**_ lines, one line for each `CelestialBody`. Each line contains six values as shown above. The first five values are `doubles`: the first two are initial x and y coordinates; the next two are initial x and y velocities; the next is the mass of the `CelestialBody`. The last value on a line is a `String` specifying the file in the images folder used for the animation of the simulation.

You will need to understand this format to write the methods `readRadius` and `readBodies` that create the simulated universe and each `CelestialBody` in a simulation. 


## NBody Methods

This section provides a high-level overview of each of the three `static` methods you need to implement for the `NBody` class. The details of each method are found in the [details](docs/details.md) docuoment.

### The method NBody.readRadius

Given a file name, this method should return a double corresponding to the radius of the universe in that file, e.g. `readRadius("./data/planets.txt")` should return $`2.50 \cdot 10 ^{11}`$ (alternatively, 2.50e+11). As described in the [details](docs/details.md) document, you'll need to create a `Scanner`, bound to the specified `File`, and read and return the radius of the simulated universe. There's a `TestReadRadius.java` program to see if your code passes tests. When you've passed this test, proceed to the next static method below.



### The method NBody.readBodies

This method returns an array of `CelestialBody` objects using the data read from the file. For example, `readBodies("./data/planets.txt")` should return an array of 5 `CelestialBody` objects. See the [details](docs/details.md) details document for more information. Note that you'll need to create an array, read data from a file/scanner, create new `CelestialBody` objects with references in the created array, then return the array.  

### The method NBody.main

You'll see four TODO comments in the static `main` method: one before the loop, and three within the loop. Completing these will make your simulation run correctly and provide an animation of the simulation. The four TODOs are described in the [details](docs/details.md) document.


## Note on Running the Simulation

When the simulation is over your code prints out the final state of the universe in the same format as the input, you can expand below for an example of the output for `data/planets.txt`.


<summary>Example Simulation Output</summary>

|             |             |             |           |           |          |
| :---        |    :----:   |       :---: |  :---:    | :---:     | ---:     |
| 5           |             |             |           |           |          |
| 2.50e+11  |            |             |             |           |           |
| 1.4631e+09 | 1.4943e+11 | -2.9831e+04 | 4.0749e+02 | 5.9740e+24 |earth.gif |
|-1.1174e+11 |-1.9803e+11 |  2.0989e+04 | -1.1953e+04 |  6.4190e+23 |   mars.gif |
| 2.4125e+10 | 5.2103e+10 | -4.3685e+04 | 2.0627e+04 | 3.3020e+23 | mercury.gif |
| 5.6664e+05 | 7.0808e+06 | 1.0861e-01 | 1.0639e-01 | 1.9890e+30  |    sun.gif |
| 1.0555e+11 | 2.3363e+10 |-7.5708e+03 | 3.4204e+04 | 4.8690e+24 |   venus.gif |


The code for printing is given to you in the `NBody.java` you start with. This code isn't all that exciting (which is why we've provided a solution), but we'll need this method to work correctly to autograde your assignment. ***You should NOT print anything other than the final printing shown here***. This printing is done after your simulation completes. *If you use debugging print statements, be sure to remove them before testing in Gradescope.*

When the simulation finishes, you'll need to close/quit the graphics window to be able to run another simulation. Use the red X button in the upper left of the graphics window to dismiss the window.

### Warnings

On a mac, you may see this warning printed in the console/debug window each time you run the program:
```
java[81197:838423] WARNING: Secure coding is not enabled for restorable state! Enable secure coding by implementing NSApplicationDelegate.applicationSupportsSecureRestorableState: and returning YES.
```
It's safe to ignore this warning, it's related to improved security for mac GUI applications released commercially. Not relevant here.


## Analysis Questions

Answer the following questions in your analysis. You'll submit your analysis as a separate PDF as a separate assignment to Gradescope. You can earn +2 extra points on this assignment (or you can use them as engagement points) if you [use this .docx template](https://courses.cs.duke.edu/compsci201/fall24/assign/p1nbody-analysis.docx) for answering the questions, and identifying questions as asked for when uploading to Gradescope. You'll need to upload a PDF, print and save after editing the .docx.


### Question 1 (1 point)

If there are $`n`$ `CelestialBody` objects, how  many _total_ times will the code have to execute the `calcForceExertedByX` method per time step (that is, per iteration of the outer loop of the main method) of the simulation? Your answer should be in terms of $`n`$. Briefly explain your answer. Note that the method `calcForceExertedByX` is called *indirectly* from the time-step loop, as a result of calling `calcNetForceExertedByX` (note the *Net*).

### Question 2 (1 point)

In terms of `totalTime` and `dt`, how many total time steps (that is, iterations of the outer loop of the main method) will there be in the simulation? Briefly explain your answer. Based on this, would increasing the value of `dt` increase, decrease, or have no impact on computational resources necessary to run a complete simulation?

### Question 3 (2 points)

`dt`was initially set to `25000.0`. Change this value to `1000000.0` (one million) and run the simulation again. You should see behavior inconsistent with what is expected for the simulation using `planets.txt`. Briefly explain why increasing the value of `dt` could cause this behavior.

### Question 4 (4 points)

Read [Making data visualization more accessible for blind and low-vision individuals](https://news.mit.edu/2022/data-visualization-accessible-blind-0602), and summarize in one paragraph the main ideas in the paper. In another paragraph relate the work you've done with this visualization to the ideas in that article and how you might make the results of this simulation more effective for blind or low-vision people.

### Question 5 (2 points)

Run the simulation for two data files other than `planets.txt` and describe what you see for each one you choose. Be brief, but thorough.


## Submitting and Grading
You will submit the assignment on Gradescope. You can access Gradescope through the tab on Canvas. The [project workflow writeup](https://coursework.cs.duke.edu/201fall24/resources-201/-/blob/main/projectWorkflow.md) explains the how to submit your project in detail. Be sure to push changes often and be sure your final program is in your Git repository before you submit it for autograding on Gradescope. Please take note that changes/commits on GitLab are NOT automatically synced to Gradescope. You are welcome to submit as many times as you like, only the most recent submission will count for a grade.

Don't forget to upload a PDF for the analysis part of this assignment and mark where you answer each question. This is a separate submission in Gradescope.

| Project Part | Points |
| ------ | ------ |
| CelestialBody | 10 |
| NBody | 10 |
| Analysis | 10 |

[Physics]:https://docs.google.com/document/d/1LRRW970ZwgZQtsif1L1SfRBTlB_VUGJAZKYol-DHGWE/edit?usp=sharing




