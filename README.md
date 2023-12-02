# DMSN-Implementation
This repository contains implementation and presentation part of Homogenous Multiple depot Multiple Salesman Travelling problem. 

# To run the code:
1. Install Intellij idea and open the code folder as a project in it.
2. Then build the code.
3. Then run App.java or simple click on the run button.
4. To see the result, visit Terminal.

# The code Architecture/Control flow:
1. The App.java file is just caller for every function that are specified in mTSP.java and simulateAnnealing.java
2. The simulated Annealing.java contains the logic of simulate Annealing solution.
3. The mTSP.java contains logic of random solution and Hill-climbing method both.
4. createImageSol file on invokation, create a image solution the text dump we do in the solution.json.
5. The other files are move operations which override the mTSP method to do the move operations accordingly.
6. The json files in the parent folder is the solution dump of various methods.


   
