# KNearestNeighbor
## Introduction
This is the repository containing the code for the implementation and visualization of the kNN and wNN algorithms. In the event that you don't want to run the code yourself, the results for the test datasets (`autos.arff` , `iris.arff`, and `iconosphere.arff`) are in the files `autos-kNN.txt`, `autos-wNN.txt`, `iris-kNN.txt` and `iconosphere-kNN.txt`.

## Usage
To run the program yourself, either open up the Eclipse project, or run the `.JAR` file that is given. Both methods do the same thing. When you initially run the program, a visualization will open up. This is the interactive version of the program. In the visualization, you can choose the algorithm to run, which observation to run it on (since it uses leave-one-out cross-validation) and how many observations to display. You can also click on any displayed datapoint to see more information on the observation. When you click run in the visualization, the output is also sent to the console. So, if you run the Eclipse project or you run the `.JAR` file in the console, you will see the output printed. This is the same output that is in the files mentioned above. 

## Organization
The code is organized into three packages. The `data` package contains only the datasets used for the program (`autos.arff`, `iris.arff` and `iconosphere.arff`). The `algorithm` package contains the implementations of the kNN and wNN algorithms. This is where all of the core work of the algorithms is done, from reading the `.arff` files to actually running the algorithm. The last package, `visualization`, simply contains the code for the visualization you see when you run the program. Nothing in the `visualization` package pertains to the actual algorithms.

## Implementation Details
The actual implementation of the algorithm is rather simple. First, the `ArffParser` class reads in the specified dataset. It returns a `Dataset` object. Each `Dataset` object contains a few things: a list of the names of the fields, and a list of `DataPoint` objects. The `DataPoint` object stores all the information for a single observation. Each `DataPoint` object has a list of parameter values, the actual value for the observation, and the predicted value for the observation. It also contains a list of `DistanceTuple` objects which associate every other `DataPoint` in the dataset with a number indicating the distance from the main `DataPoint`. This distance is simply the sum-squared difference for each of the parameters. This list of `DistanceTuple` objects is then sorted and the first `k` objects are chosen to be used for the prediction. The `Main` class in the `algorithm` package is where the whole program is started and run from. If you want to look through the code, the `Main` class is where you should start.

## Visualization Information
The Processing library was used to create the visualization. More information on Processing can be found at [https://processing.org/](https://processing.org/)

## Notes
I have also included an extra dataset (`iris.arff`) to serve as a test dataset that wasn't given to me in the project prompt. 
