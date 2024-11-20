## Starter Code and Using Git
**_You should have installed all software (Java, Git, VS Code) before completing this project._** You can find the [directions for installation here](https://coursework.cs.duke.edu/201fall24/resources-201/-/blob/main/installingSoftware.md) (including workarounds for submitting without Git if needed).

We'll be using Git and the installation of GitLab at [coursework.cs.duke.edu](https://coursework.cs.duke.edu). All code for classwork will be kept here. Git is software used for version control, and GitLab is an online repository to store code in the cloud using Git.

**[This document details the workflow](https://coursework.cs.duke.edu/201fall24/resources-201/-/blob/main/projectWorkflow.md) for downloading the starter code for the project, updating your code on coursework using Git, and ultimately submitting to Gradescope for autograding.** We recommend that you read and follow the directions carefully when working on a project! While coding, we recommend that you periodically (perhaps when completing a method or small section) push your changes as explained.


## Coding in Project P1: NBody

When you fork and clone the project, **make sure you open the correct project folder in VS Code** following the [directions shown here](https://coursework.cs.duke.edu/201fall24/resources-201/-/blob/main/projectWorkflow.md#step-3-open-the-project-in-vs-code). 


## CelestialBody code

### CB1: `CelestialBody` Variables, Constructor, and Getter Methods

This section introduces the `CelestialBody` class and describes its instance variables, constructor, and getter methods, which you will need to implement. 

**CelestialBody Instance variables**

The outline below shows the constructor, methods, and instance variables (or fields)  of the `CelestialBody` class. All instance variables should be `private`. All methods described in this document should be `public` (if you write helper methods they should be `private`). *When you clone the project, you'll get a `CelestialBody` class with the instance variables and stub methods already implemented*.


There are six instance variables: `myXPos`, `myYPos`, `myXVel`, `myYVel`, `myMass`, `myFileName`. The first five have type `double`, the last is a `String`.

**CelestialBody Constructor**

There is one constructor: it has six parameters, one for each instance variable. The signature is shown below. 

<div align="center">
  <img width="576" height="248" src="p1-figures/celestialBodyConst1.png">
</div>

*You'll write code to assign values to *each of six* instance variables using the six parameters.*


**CelestialBody Getter Methods**

You'll also write six _getter methods_ specified in the class. The body of each method is a single return statement, returning the value of the corresponding instance variable. These getter methods allow the values of `private` instance variables to be accessed outside the class. For example, the method `getXVel()` is shown below. These are getter methods because they *do not allow client programs to set the values, only to get the values*.

<div align="center">
  <img width="310" height="115" src="p1-figures/getXVel.png">
</div>


When you've implemented the constructor and the six getter methods you should be able to run the program in `TestBodyConstructorGetters.java` to see if your code is correct. When it reports that everything works you can proceed to the next step in implementing the `CelestialBody` class. The report from running `TestBodyConstructorGetters` indicates whether each getter method passes.

### CB2: The method CelestialBody.calcDistance

<br>

 <div align="center">
  <img width="420" height="128" src="p1-figures/calcDistance.png">
</div>


This method returns the distance between two `CelestialBody` objects. Use the standard distance formula to determine the distance between `this` body (using `myXPos` and `myYPos` or `this.myXPos` and `this.myYPos`) and the `CelestialBody` object referenced by the parameter `b`. The distance is the value of $`r`$ in the formula below where

```math
r^2=dx^2 + dy^2
```

where $`dx`$ is deltaX -- the $`x`$-coordinate of the given `CelestialBody` object referenced by `b` minus the  $`x`$-coordinate of `this` body, and similarly for $`dy`$. You can use the static method `Math.sqrt` to calculate the square root of a number.

You test your implementation by running the Java program `TestCalcDistanced.java` -- when all tests have passed, you continue.

### CB3 The method CelestialBody.calcForceExertedBy


<div align="center">
  <img width="372" height="58" src="p1-figures/calcForceExertedBy.png">
</div>

<br>

This method calculates and returns the force exerted on `this` body by the body referenced by the parameter `b`. You should calculate the force using the formula below. You can read about the physics of the formula in the [NBody Physics document][Physics].

```math
F = G\frac{m_1m_2}{r^2}
```
 
Here $`m_1`$ and $`m_2`$ are the masses of the two bodies, $`G`$ is the gravitational constant ($`6.67 \cdot 10^{-11}\frac{N-m^2}{kg^2}`$), and $`r`$ is the distance between the two objects. Call `calcDistance` to determine this distance. You can specify $`G`$ as $`6.67 \cdot 10^{-11}`$ (alternatively, 6.67*1e-11) using scientific notation in Java. 

When you've implemented this method, test it by running `TestCalcForceExertedBy.java`.

### CB4: The methods CelestialBody.calcForceExertedByX and calcForceExertedByY


<div align="center">
  <img width="428" height="20" src="p1-figures/calcForceExertedByX.png">
</div>

<br> 

These two methods describe the force exerted in the X and Y directions, respectively. The signature of `calcForceExertedByX` is shown above; `calcForceExertedByY` has a similar signature. 

You can obtain the $`x`$- and $`y`$-components from the total force using the formulas below, where $`F`$ is the value returned by `calcForceExertedBy`, $`r`$ is the distance between two bodies, and $`F_x`$ and $`F_y`$ are the values to be returned by `calcForceExertedByX` and `calcForceExertedByY`, respectively. Note that $`dx`$ and $`dy`$ in the formula are the differences between $`x`$ and $`y`$ coordinates, respectively, between the original body (`this`, the object on which the method is called) and the exerting body (the argument to the method).

```math
F_x = F\frac{dx}{r}\\~\\
F_y = F\frac{dy}{r}
```

Note: Be careful with the signs! In particular, recall that $`dx`$ and $`dy`$ are signed differences (positive or negative). By convention, we define the positive $`x`$-direction as towards the right of the screen, and the positive $`y`$-direction as towards the top. What this means is that the distance between x-coordinates should be calculated as `b.myXPos - this.myXPos` or using methods as `b.getX() - this.getX()`. Recall that you do *not* need to use `this.` in the context shown, e.g., you could just write `b.myXPos - myXPos`.

*Also note:* While mathematically `F/r * dx` is the same as `F*dx/r`, because of roundoff error these may not be the same computationally. You should use `F*dx/r` in your method. *Please be attentive to this in your code!*

You can test these methods using the program in `TestCalcForceExertedByXY.java`.

### CB5: The method CelestialBody.calcNetForceExertedByX and calcNetForceExertedByY

<br>

<div align="center">
  <img width="428" height="20" src="p1-figures/calcNetForceExertedByY.png">
</div>

<br>

This method returns the total/net force exerted on this body by all the bodies in the array parameter. The principle of superposition ([see Physics][Physics]) says that the net force acting on a `CelestialBody` object by many other bodies is the sum of the pairwise forces acting on the `CelestialBody` by each body. So you'll need to sum the forces returned by `calcForceExertedByX` (or `Y`) in calculating the value to return. 

You must make sure _**NOT to include the force exerted by a body on itself!**_ The universe might collapse (Infinite/NaN error) if an object attracted itself. If you loop over each element in array `bodies`, you'll need to check explicitly with code that looks something like the following.

<br> 

<div align="center">
  <img width="289" height="39" src="p1-figures/forLoop.png">
</div>

You can test the code for this method by running the program in `TestCalcNetForceExertedByXY.java`.

### CB6: The method CelestialBody.update

<div align="center">
  <img width="379" height="39" src="p1-figures/update.png">
</div>

This method is a so-called _mutator_. It doesn't return a value, but updates the state/instance variables of the `CelestialBody` object on which it's called. 

This method will be called during the simulation to update the body's position and velocity with small time steps (the value of the first parameter, `deltaT`). The values of parameters `xforce` and `yforce` are the net forces exerted on this body by all other bodies in the simulation. When code calls the update method from `NBody.java`, you will determine the values of the arguments passed as these two parameters by calling `calcNetForceExertedByX` (or `Y`). In the formulas you're writing below the parameter `xforce` is $`F_x`$ and parameter `yforce` is $`F_y`$. These are passed to the `update` method, you're writing `update`!

This update method updates the instance variables `myXPos`, `myYPos`, `myXVel`, and `myYVel` in four steps.

1. First, calculate the acceleration using Newton's second law of motion where $`m`$ is the mass of the `CelestialBody`. This creates two variables for acceleration in the $`x`$ and $`y`$ directions.

```math
a_x = \frac{F_x}{m}\\~\\
a_y = \frac{F_y}{m}
```
 
2. You'll then calculate new values for  `myXVel` and `myYVel`, we'll call these `nvx` and `nvy` where the $`n`$ is for new, using the relationship between acceleration and velocity, e.g., `nvx = myXVel + deltaT*ax`. You use `double` variables here, you cannot assign to `myXVel` for example until the steps below. 

3. You'll use `nvx` (and a corresponding `nvy`) to calculate new values for `myXPos` and `myYPos` using the relationship between position and velocity, e.g., `nx = myXPos + deltaT*nvx`.

4. _**After**_ you've calculated `nx`,`ny`,`nvx`, and `nvy`, you'll assign these to the instance variables `myXPos`, `myYPos`, `myXVel`, and `myYVel`, respectively. The key in *after* is that you do not update instance variable values until you've calculated each of the `nx`, `ny`, `nvx`, and `nvy` values.

These steps will update the position and velocity of the body making the simulation possible. You can test this method using `TestUpdate.java`.

## Static methods in `NBody`

### The method `readRadius`

You'll need to read the `int` value that's the number of bodies (you can ignore that in this method `readRadius`, but you must read it first), then read the `double` value for the radius using the `Scanner` already created in the starter code. Use `s.nextInt()` and `s.nextDouble()` for the `Scanner` variable `s` to read an `int` and `double` value, respectively. Your code in `readRadius` must read both values, but only the radius is returned. The number of bodies (first value in a data file) is ignored in this method.

You can test your method using the provided `TestReadRadius.java` program.

### The static method `readBodies`

You'll need to read the number of `CelestialBody` objects, the first value stored in the data-file. Let's call this value `numBodies`. You'll use `s.nextInt()` to read and store the read value in a local variable, e.g., `numBodies`.  You'll create a `CelestialBody []` array of the correct size to return, calling `new` and storing a pointer/reference to the array in a local variable, let's call it `bodies`. When initially created, each value in the array `bodies` will be `null`, but you will write a `for` loop to read the values on each line and use these as parameters when you call `new` and create a `CelestialBody` object with the parameters on each line of the file.

As you iterate through the information for each of the `CelestialBody` objects in the file (one per line), you will find the `nextDouble()`, and `next()` methods in the Scanner useful in reading `double`, and `String` values, respectively. Note that `next()` returns a `String`. In writing `readBodies`, you'll need to read the radius of the universe to move the file reading/pointer/window to the next thing to be read, but you'll ignore the radius here. As your `for` loop iterates, you'll need to read six values (the values on one line: five `double` values and one `String` value) and then call `new CelestialBody` with the read values as appropriate parameters.

You can test this method using the supplied `TestReadBodies.java` class. 

### The static method `main`

You'll see four TODO comments in the static `main` method: one before the loop, and three within the loop. Completing these will make your simulation run correctly and provide an animation of the simulation. The loop drives the simulation, updating a time-step of the simulation by `dt`, a delta-time value. Within this loop, you'll add code to do the four things described below. Some of these will use loops as part of implementing the tasks specified. 

1. Create an `xForces` array and `yForces` array. Each should have the same size as the number of bodies in the simulation. Both arrays should 
have type `double[]`, these are arrays of double values. You'll create the arrays _before_ the `for` loop, and access the arrays within the loop body.

2. You'll write a loop over the `CelestialBody` objects stored in the arary returned from the call to `readBodies`.  Calculate the *net* x and y forces for each `CelestialBody` object, storing these in the `xForces` and `yForces` arrays respectively. You can use the `CelestialBody` methods you wrote in previous steps above to do this. 

In this loop, you'll have a statement that looks similar to `xforces[k] = bodies[k].calcNetForceExertedByX(bodies)`, so that the index `k` updates the `CelesetialBody` element referenced by each array-slot.

3. You'll write a loop to call `update` on each `CelestialBody` object, using `dt` and the corresponding elements of these arrays as parameters. Notice the parameters in `update`, in particular the same value of `deltaT`, the first parameter, is passed to each `CelestialBody` object's `update` method. 

4. Call `draw` on each body, again you'll need a loop and a call to the `draw` method. The loop is provided, you'll need to call `b.draw()` in the body of the loop so that each object is drawn using its own graphical image.


