import java.util.*;
import java.util.concurrent.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class temp 
{
	public static void main(String[] args)
	{
		int[] sharedMem = new int[24];
		Random rand = new Random();
		long startTime, endTime, executionTime;

		System.out.println("~~~~~~~~~24 hours of sensor readings~~~~~~~~");
		startTime = System.nanoTime();
        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        for(int i = 0; i < 24; i++)
        {
        	int temperature = rand.nextInt((70-(-100))+1)+(-100);
        	sharedMem[i] = temperature;
        	System.out.println("Hour " + i +": "+ temperature + "F");
        }
        threadPool.shutdown();
        endTime = System.nanoTime();
        executionTime = (endTime - startTime)/1000000;
        System.out.println("\nRuntime: "+executionTime+" ms");
	}
}