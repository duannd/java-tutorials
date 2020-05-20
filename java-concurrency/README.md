# Java Concurrency examples

    https://www.baeldung.com/java-asynchronous-programming

## 1. Overview
   
With the growing demand for writing non-blocking code, we need ways to execute the code asynchronously.
  
## 2. Asynchronous Programing with Java

### 2.1 Thread 

We can create a new thread to perform any operation asynchronously. 
    
With the release of lambda expressions in Java 8, it's cleaner and more readable.
    
### 2.2 FutureTask 
    
Since Java 5, the Future interface provides a way to perform asynchronous operations using the FutureTask.
    
We can use the submit method of the ExecutorService to perform the task asynchronously and return the instance of the FutureTask.
    
```$xslt
    ExecutorService threadpool = Executors.newCachedThreadPool();
    Future<Long> futureTask = threadpool.submit(() -> factorial(number));
     
    while (!futureTask.isDone()) {
        System.out.println("FutureTask is not finished yet..."); 
    } 
    long result = futureTask.get(); 
     
    threadpool.shutdown();
```
   
Here, we've used the isDone method provided by the Future interface to check if the task is complete. 

Once finished, we can retrieve the result using the get method.

### 2.3. CompletableFuture

Java 8 introduced CompletableFuture with a combination of a Future and CompletionStage.

It provides various methods like **supplyAsync**, **runAsync**, and **thenApplyAsync** for asynchronous programming.

```$xslt
    CompletableFuture<Long> completableFuture = CompletableFuture.supplyAsync(() -> factorial(number));
    while (!completableFuture.isDone()) {
        System.out.println("CompletableFuture is not finished yet...");
    }
    long result = completableFuture.get();
```

We don't need to use the ExecutorService explicitly. 

The CompletableFuture internally uses ForkJoinPool to handle the task asynchronously.




    