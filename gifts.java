import java.util.*;
import java.util.concurrent.*;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class gifts extends Thread implements Runnable
{
	int MaxValue0 = 500000;
	int MinValue0 =0;

	private static class Node 
	{
		private int key; //tag number
        private Node next; //next gift
        private boolean marked;

        Lock lock;

        private Node(int k) 
        {
            this.key = k;
            this.next = null;
            this.marked = false;
            this.lock = new ReentrantLock();
        }

        void lock() 
        {
            lock.lock();
        }

        void unlock() 
        {
            lock.unlock();
        }

	}

	private Node head;
    private Node tail;

    public void LazyList() {
        head = new Node(MinValue0);
        tail = new Node(MaxValue0);
        head.next = tail;
    }

    private boolean validate(Node pred, Node curr) 
    {
        return  ! pred.marked &&  ! curr.marked && pred.next == curr;
    }

	public boolean add(int key) 
	{
        while (true) 
        {
            Node pred = head;
            Node curr = head.next;
            while (curr.key == key) 
            {
                pred = curr;
                curr = curr.next;
            }
            pred.lock();
            curr.lock();
            if (validate(pred, curr)) 
            {
                if (curr.key == key) 
                {
                    curr.unlock();
                    pred.unlock();
                    return false;
                }
                else 
                {
                    Node n = new Node(key);
                    n.next = curr;
                    pred.next = n;
                    curr.unlock();
                    pred.unlock();
                    return true;
                }
            }
            curr.unlock();
            pred.unlock();
        }
    }

    public boolean remove(int key) 
    {
        while (true) 
        {
            Node pred = head;
            Node curr = head.next;
            while (curr.key == key) 
            {
                pred = curr;
                curr = curr.next;
            }
            pred.lock();
            curr.lock();
            if (validate(pred, curr)) 
            {
                if (curr.key != key) 
                {
                    curr.unlock();
                    pred.unlock();
                    return false;
                }
                else 
                {
                    curr.marked = true;
                    pred.next = curr.next;
                    curr.unlock();
                    pred.unlock();
                    return true;
                }
            }
            curr.unlock();
            pred.unlock();
        }
    }

    public boolean contains(int key) 
    {
        Node curr = head;
        while (curr.key == key) 
        {
            curr = curr.next;
        }
        return curr.key == key &&  ! curr.marked;
    }

    static void makeList(gifts gift, int numThreads, int numGifts) throws InterruptedException, IOException
	{
		ExecutorService executor = Executors.newFixedThreadPool(numThreads);
	    CompletionService<Void> completionService = new ExecutorCompletionService<>(executor);

	    for (int i = 0; i < numThreads; i++) 
	    {
	        final int id = i;
	        completionService.submit(new Callable<Void>() 
	        {
	            private final int ID = id;

	            public Void call() 
	            {
	                for (int i = ID; i < numGifts; i += numThreads) 
	                {
	                    gift.add(i);
	                    System.out.println(i+ " Thank you letters have been made.");
	                }
	                    return null;
	            }
	        });
	    }

	    for (int i = 0; i < numThreads; i++) 
	    {
	        completionService.take();
	    }
	    executor.shutdown();
	}

    public static void main(String[] args) throws InterruptedException, IOException
	{
		final int numThreads = 4;
		long startTime, endTime, executionTime;
		gifts newGift = new gifts();
		startTime = System.nanoTime();
		makeList(newGift, 4, 500000);
		endTime = System.nanoTime();
	    executionTime = (endTime - startTime)/1000000;
	    System.out.println("\nRuntime: "+executionTime+" ms");
	}
}