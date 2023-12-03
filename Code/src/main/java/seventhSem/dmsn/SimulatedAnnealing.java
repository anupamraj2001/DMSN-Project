// Random Selection: The function randomly selects a depot and two distinct salesmen from that depot.
// This random selection introduces exploration into the search space, allowing the algorithm to escape
// local optima and potentially find better solutions.

// Swapping Cities: Swapping cities between two salesmen allows the algorithm to explore different
// permutations of routes within the selected depot.
// This operation can lead to the discovery of shorter routes and reduced overall cost.

// Avoiding Repeated Neighbors: By ensuring that the two selected salesmen are distinct,
// the algorithm avoids generating the same neighbor repeatedly,
// preventing cycling in the search process.

// Flexibility: The approach is flexible and can be adapted based on the problem requirements.
// For more complex scenarios, additional neighborhood generation techniques like 2-opt moves or
// insertion/deletion operations can be incorporated.

package seventhSem.dmsn;

import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.lexicalscope.jewel.cli.CliFactory;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Random;



public class SimulatedAnnealing implements Cloneable {
    // Define your state representation and distance matrix
    // ...
    private mTSP mTSP;
    private Invoker invoker;
    int minCost;
    Random r;
    int op0, op1, op2, op3, op4;


    public SimulatedAnnealing(mTSP mTSP) {
        this.mTSP = mTSP;
        RandomSolutionCommand rsC = new RandomSolutionCommand(mTSP);
        SwapNodesInRouteCommand snirC = new SwapNodesInRouteCommand(mTSP);
        SwapHubWithNodeInRouteCommand shwnirC = new SwapHubWithNodeInRouteCommand(mTSP);
        SwapNodesBetweenRoutesCommand snbrC = new SwapNodesBetweenRoutesCommand(mTSP);
        InsertNodeInRouteCommand inirC = new InsertNodeInRouteCommand(mTSP);
        InsertNodeBetweenRoutesCommand inbrC = new InsertNodeBetweenRoutesCommand(mTSP);

        //Invoker
        invoker = new Invoker(rsC, snirC, shwnirC, snbrC, inirC, inbrC);
        minCost = mTSP.cost();
        op0 = 0;
        op1 = 0;
        op2 = 0;
        op3 = 0;
        op4 = 0;
        r = new Random();
    }


    // Simulated Annealing Algorithm
    public void simulatedAnnealing(String[] args, int SecondCost, int[][] distanceMatrix, double initialTemperature, double coolingRate) {
        double currentCost = mTSP.cost();
        double temperature = initialTemperature;

        while (temperature > 1e-10) {
            for (int i = 0; i < 5_000; i++) {
                int rOpe = r.nextInt(5);
                if (rOpe == 0) {

                    int curCost = mTSP.cost();
                    invoker.executeSwapNodesInRoute();
                    int newCost = mTSP.cost();

                    double deltaCost = newCost - curCost;

                    // If the neighbor solution is better or accepted based on probability, move to the neighbor state
                    if (deltaCost < 0 || Math.random() < Math.exp(-deltaCost / temperature))
                        op0++;
                    else
                        invoker.unexecuteSwapNodesInRoute();
                } else if (rOpe == 1) {
                    int curCost = mTSP.cost();
                    invoker.executeSwapHubWithNodeInRoute();
                    int newCost = mTSP.cost();

                    double deltaCost = newCost - curCost;

                    // If the neighbor solution is better or accepted based on probability, move to the neighbor state
                    if (deltaCost < 0 || Math.random() < Math.exp(-deltaCost / temperature))
                        op1++;
                    else
                        invoker.unexecuteSwapHubWithNodeInRoute();
                } else if (rOpe == 2) {
                    int curCost = mTSP.cost();
                    invoker.executeSwapNodesBetweenRoutes();
                    int newCost = mTSP.cost();

                    double deltaCost = newCost - curCost;

                    // If the neighbor solution is better or accepted based on probability, move to the neighbor state
                    if (deltaCost < 0 || Math.random() < Math.exp(-deltaCost / temperature))
                        op2++;
                    else
                        invoker.unexecuteSwapNodesBetweenRoutes();
                } else if (rOpe == 3) {
                    int curCost = mTSP.cost();
                    invoker.executeInsertNodeInRoute();
                    int newCost = mTSP.cost();

                    double deltaCost = newCost - curCost;

                    // If the neighbor solution is better or accepted based on probability, move to the neighbor state
                    if (deltaCost < 0 || Math.random() < Math.exp(-deltaCost / temperature))
                        op3++;
                    else
                        invoker.unexecuteInsertNodeInRoute();
                } else if (rOpe == 4) {
                    int curCost = mTSP.cost();
                    invoker.executeInsertNodeBetweenRoutes();
                    int newCost = mTSP.cost();

                    double deltaCost = newCost - curCost;

                    // If the neighbor solution is better or accepted based on probability, move to the neighbor state
                    if (deltaCost < 0 || Math.random() < Math.exp(-deltaCost / temperature))
                        op4++;
                    else
                        invoker.unexecuteInsertNodeBetweenRoutes();
                }
            }

            // Cool down the temperature
            temperature *= coolingRate;
        }

        JSONObject opJson = new JSONObject();
        opJson.put("swapNodesInRoute", op0);
        opJson.put("swapHubWithNodeInRoute", op1);
        opJson.put("swapNodesBetweenRoutes", op2);
        opJson.put("insertNodeInRoute", op3);
        opJson.put("insertNodeBetweenRoutes", op4);

        try {
            mTSP.writeMoveOpeJSON(opJson);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int cost = mTSP.cost();
        Params params;
        try {
            params = CliFactory.parseArguments(Params.class, args);
        } catch (ArgumentValidationException e) {
            System.out.println(e.getMessage());
            return;
        }
      mTSP.print(params.getVerbose());
        //     public static void main(String[] args) {
//         // Initialize your state and distance matrix
//         // ...
//
//         // Set initial temperature and cooling rate
//         double initialTemperature = 1000;
//         double coolingRate = 0.995;
//         // Run simulated annealing
//         MultipleDepotMultipleSalesmanSimulatedAnnealing saSolver = new MultipleDepotMultipleSalesmanSimulatedAnnealing();
//         State optimizedState = saSolver.simulatedAnnealing(distanceMatrix, initialTemperature, coolingRate);
//
//         // Output optimized solution
//         System.out.println("Optimized Routes: " + optimizedState);
//         System.out.println("Optimized Cost: " + saSolver.calculateCost(optimizedState, distanceMatrix));
//     }
        //   }


        System.out.println("**Total cost (After applying simulated annelaing) is :" + cost);
    }

//        mTSP.print(params.getVerbose());
    //     public static void main(String[] args) {
//         // Initialize your state and distance matrix
//         // ...
//
//         // Set initial temperature and cooling rate
//         double initialTemperature = 1000;
//         double coolingRate = 0.995;
//         // Run simulated annealing
//         MultipleDepotMultipleSalesmanSimulatedAnnealing saSolver = new MultipleDepotMultipleSalesmanSimulatedAnnealing();
//         State optimizedState = saSolver.simulatedAnnealing(distanceMatrix, initialTemperature, coolingRate);
//
//         // Output optimized solution
//         System.out.println("Optimized Routes: " + optimizedState);
//         System.out.println("Optimized Cost: " + saSolver.calculateCost(optimizedState, distanceMatrix));
//     }
//   }
}
