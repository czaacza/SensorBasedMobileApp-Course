# Lesson 06 | Coroutines

Created: August 25, 2022 8:51 AM

## Introduction

Multitasking can be modelled using:

- **processes** (used in operating systems)
- **threads**
- **coroutines**

![Zrzut ekranu 2022-08-25 o 09.09.35.png](Zrzut_ekranu_2022-08-25_o_09.09.35.png)

With only one processing unit the “multitasking” can be done by task swapping.

![Zrzut ekranu 2022-08-25 o 09.15.25.png](Zrzut_ekranu_2022-08-25_o_09.15.25.png)

Task swap is time consuming if the task context is large (task time of processes becomes longer).

The process uses **threads** - they share the same memory, so it is easier to quickly swap between them. However, they are not really protected from each other.

One process can be separated into around 50 threads.

## Coroutine

(co-routines, Fibers, lightweight threads, green threads)

![Zrzut ekranu 2022-08-25 o 09.24.02.png](Zrzut_ekranu_2022-08-25_o_09.24.02.png)

### Function vs Coroutine

When a **function** is being called, the flow of the caller function stops. This is not a behaviour we are looking for while using multitasking.

When the **coroutine** is called, it quickly **suspends** the function and gets back to the caller function. It is extremely fast, however, using coroutines we have no protection at all. We need to implement them smart so the caller function will not stop its execution. The compiler helps with many of these kind of problems.

## Kotlin coroutine

Kotlin implementation of coroutine is build around the suspend function

![Untitled](Untitled%201.png)

The suspension points are automatically set by kotlin compiler. It sets the suspension points if it sees f.ex: delay() function.

Sometimes it is our responsibility to suspend a function in the right time, f.ex if we have a time-consuming mathematical operation in a loop.

We can  suspend a coroutine by using **yield()** function.

![Untitled](Untitled%202.png)

## Coroutine concepts

**Coroutine Builders** - These take a suspending lambda as an argument to create a coroutine

**Coroutine Scope** - Helps to define the lifecycle of Kotlin Coroutines 

**Coroutine Job** - Represents a piece of work that needs to be done

**Coroutine Dispatcher** - Defines thread pools to launch your Kotlin Coroutines in

## Coroutine Dispatcher

- Dispatchers.Default: CPU-intensive work, such as sorting large lists, doing
complex calculations and similar
A shared pool of threads on the JVM backs it
- [Dispatchers.IO](http://dispatchers.io/): networking or reading and writing from files
In short – any input and output, as the name states
- Dispatchers.Main: recommended dispatcher for performing UI-related events
For example, showing lists in a RecyclerView, updating Views and so on

## Coroutine Builders

- Launch()
- Async()

## Reading List

- [https://kotlinlang.org/docs/coroutines-guide.html](https://kotlinlang.org/docs/coroutines-guide.html)
- [https://www.baeldung.com/kotlin/kotlin-threads-coroutines](https://www.baeldung.com/kotlin/kotlin-threads-coroutines)
- [https://dmitrykandalov.com/coroutines-as-threads](https://dmitrykandalov.com/coroutines-as-threads)
- [https://stackoverflow.com/questions/47871868/what-does-the-suspend-function-mean-in-a-kotlin-coroutine](https://stackoverflow.com/questions/47871868/what-does-the-suspend-function-mean-in-a-kotlin-coroutine)
- [https://medium.com/l-r-engineering/launching-kotlin-coroutines-in-android-coroutine-scope-context-800d280ebd80](https://medium.com/l-r-engineering/launching-kotlin-coroutines-in-android-coroutine-scope-context-800d280ebd80)