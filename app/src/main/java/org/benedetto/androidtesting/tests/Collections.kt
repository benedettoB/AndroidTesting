package org.benedetto.androidtesting.tests

import org.benedetto.androidtesting.util.log
import java.util.Collections
import java.util.LinkedList
import java.util.SortedMap
import java.util.TreeMap


class Collections {

    private val TAG = "Collections"

    /*List*/
    //List of user profiles where random access is important
    val userProfiles = ArrayList<String>()

    // Shared log where thread-safe operations are required
    val sharedLog = Collections.synchronizedList(ArrayList<String>())

    //Task management system where tasks are frequently inserted and removed
    val taskQueue: LinkedList<String> = LinkedList()


    /*Map*/
    //Caching user data where order doesn't matter
    val userCache: Map<Int, String> = hashMapOf(1 to "John", 2 to "Alice", 3 to "Bob")

    //Storing student scores where sorted access is important
    val studentScores: SortedMap<Int, String> = TreeMap()

    //Implementing an Least Recently Used cache using LinkedHashMap
    val lruCache = object : LinkedHashMap<Int, String>(16, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<Int, String>?): Boolean {
            //Evict if cache size exceeds 5
            return size > 5
        }

    }

    init {
        //init TreeMap
        studentScores[95] = "Alice"
        studentScores[87] = "Bob"
        studentScores[92] = "John"
        //init LinkedHashMap
        lruCache[1] = "John"
        lruCache[2] = "Alice"
        lruCache[3] = "Bob"
        lruCache[4] = "Carol"
        lruCache[5] = "David"
        //init ArrayList
        userProfiles.add("John")
        userProfiles.add("Alice")
        userProfiles.add("Bob")

        //init synchronized ArrayList using Collections.synchronizedList in a thread-safe manner
        sharedLog.add("John")
        sharedLog.add("Alice")
        sharedLog.add("Bob")

        //Multiple threads accessing the shared log concurrently
        val t1 = Thread {
            sharedLog.add("Log entry from thread 1")
        }
        val t2 = Thread {
            sharedLog.add("Log entry from thread 2")
        }
        t1.start()
        t2.start()
        t1.join()
        t2.join()

        //init LinkedList: adding tasks at the front of the queue (high priority tasks)
        taskQueue.addFirst("Critical bug fix")
        taskQueue.addFirst("New feature development")

        //Adding tasks at the end of the queue (low priority tasks)
        taskQueue.addLast("Refactor code")

    }

    fun begin() {
        //HashMap: Fast retrieval
        val userName = userCache[2]
        log(TAG, userName.toString())
        //TreeMap: Keys are automatically sorted
        log(TAG, studentScores.toString())
        //{87=Bob, 92=John, 95=Alice}
        //TreeMap: Get all students with scores between 90 and 100
        val highScorers: SortedMap<Int, String> = studentScores.subMap(90, 100)
        log(TAG, highScorers.toString())
        //{92=John, 95=Alice}:

        //LinkedHashMap:
        log(TAG, lruCache.toString())
        //{1=John, 2=Alice, 3=Bob, 4=Carol, 5=David}
        //LinkedHashMap: Accessing key 2 to make it more recent
        lruCache[2]

        log(TAG, lruCache.toString())
        //{1=John, 3=Bob, 4=Carol, 5=David, 2=Alice}
        //LinkedHashMap: Adding a new entry, this will evict the least recently accessed (key 1)
        lruCache[6] = "Eve"
        log(TAG, lruCache.toString())
        //{3=Bob, 4=Carol, 5=David, 2=Alice, 6=Eve}

        //ArrayList
        log(TAG, userProfiles.toString())
        //[John, Alice, Bob]
        //ArrayList: accessing a specific profile by index
        val profile: String = userProfiles[1]
        //Fast random access, returns "Alice"
        log(TAG, profile)
        //ArrayList:inserting at the end
        userProfiles.add("Carol")
        //Amortized O(1) time for adding at the end
        log(TAG, userProfiles.toString())
        // [John, Alice, Bob, Carol]

        //Synchronized ArrayList: Access and iterate over the list inside a synchronized block in thread-safe manner
        synchronized(sharedLog) {
            for (log in sharedLog) {
                log(TAG, log)
            }
        }
        //LinkedList: removing the most urgent task
        val currentTask: String = taskQueue.removeFirst()//fast removal at the head of the list
        log(TAG, "Current Task : $currentTask")
        // Current Task : New feature development:

    }

}

/*
List
    ArrayList: Best for fast random access and infrequent insertions/deletions
    Example Use Case: Imagine you are building an application that needs to display a list of user profiles, where you often need to access specific profiles by index. In this case, ArrayList is perfect because of its fast random access(O(1) time complexity) and the fact that the list of profiles is relatively stable (infrequent insertions or deletions).
    Why ArrayList?: Random access to specific user profiles is important here, and since the list size doesn't change frequently, the array's fast access outshines the relatively slower insertion/deletion performance.

    Vector (Synchronized ArrayList): Best for thread-safe operations in a multithreaded environment. Use Collections.synchronizedList to wrap an ArrayList and provide a thread-safe list, offering more control over synchronization (instead of synchronizing every method like Vector)
    Example Use Case: You are developing a shared resource in a multithreaded environment, where multiple threads need to add or access elements in a list simultaneously. Since Vector is synchronized, it ensures thread safety, preventing concurrency issues without needing external synchronization
    Synchronized ArrayList iteration: When iterating over a synchronized list, you need to explicitly wrap the iteration inside a synchronized block to prevent concurrency issues. Even though the list is synchronized, methods like iterator() are not synchronized by default. Therefore, to avoid ConcurrentModificationException during iteration, you should always synchronize the block when iterating over the list.

    LinkedList: Best for frequent insertions and deletions, particularly in the middle or beginning of the list
    Example Use Case: You are building a task manager where tasks are frequently added and removed, especially at the beginning or in the middle of the list (e.g., to prioritize urgent tasks). In this case, LinkedList is ideal because of its fast insertion and removal operations (O(1) at the beginning or end), as it doesn't require shifting elements like ArrayList or synchronized list
    Why LinkedList? Here, tasks are frequently inserted and removed, especially at the beginning of the list (highest priority tasks). LinkedLIst handles this effeciently because it only updates the references between nodes, avoiding the costly array shifts that would occur in ArrayList or synchronized array list

Map
    HashMap: Best when you need fast lookups and don't care about the order of elements
    Example Use Case: Imagine you're implementing a caching mechanism for an application where you need to store and retrieve values (e.g., user data or session information) very quickly

    TreeMap: Best when you need sorted keys or range-based operations
    Example Use Case: Consider you are building a system to store students' scores, and you need to frequently retrieve scores in sorted order, or you want to get all students who scored within a certain range

    LinkedHashMap: Best when you need predictable iteration order (insertion or access order)
    Example Use Case: You are building an LRU (Least Recently Used) cache, where you need to evict the least recently accessed item when the cache reaches a certain size. LinkedHashMap helps maintain access order for this purpose.

 */
