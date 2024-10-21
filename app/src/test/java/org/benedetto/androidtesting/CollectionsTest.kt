package org.benedetto.androidtesting

import org.benedetto.androidtesting.tests.Collections
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.SortedMap

class CollectionsTest {

    private val collections = Collections()



    @Test
    fun arrayListTest(){
        val userProfiles = collections.userProfiles
        //[John, Alice, Bob]
        val profile: String = userProfiles[1]
        assertTrue(profile == "Alice")
        //ArrayList:inserting at the end
        userProfiles.add("Carol")
        //Amortized O(1) time for adding at the end
        // [John, Alice, Bob, Carol]
        assertTrue(userProfiles[3] == "Carol")
    }

    @Test
    fun synchronizedArrayList(){
        val sharedLog = collections.sharedLog
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

        assertTrue(sharedLog.contains("Log entry from thread 1"))
        assertTrue(sharedLog.contains("Log entry from thread 2"))
        sharedLog.add("Log entry 3")
        assertTrue(sharedLog.contains("Log entry 3"))

    }

    @Test
    fun linkedListTest(){
        val taskQueue = collections.taskQueue
        //init LinkedList: adding tasks at the front of the queue (high priority tasks)
        taskQueue.addFirst("New feature development")
        taskQueue.addFirst("Critical bug fix")

        //Adding tasks at the end of the queue (low priority tasks)
        taskQueue.addLast("Refactor code")

        //LinkedList: removing the most urgent task
        val currentTask: String = taskQueue.removeFirst()//fast removal at the head of the list
        assertTrue(currentTask == "Critical bug fix")
    }


    @Test
    fun hashMapTest(){
        val userName = collections.userCache[2]
        assertTrue(userName.toString() == "Alice")
    }

    @Test
    fun treeMapTest(){
        //keys are automatically sorted
        val studentScores = collections.studentScores
        //{87=Bob, 92=John, 95=Alice}
        val highScorers: SortedMap<Int, String> = studentScores.subMap(90, 100)
        //{92=John, 95=Alice}
        highScorers.forEach{ (score) ->
            assertTrue(score >= 90)
        }
    }

    @Test
    fun linkedHashMapTest(){
        val lruCache = collections.lruCache
        //{1=John, 2=Alice, 3=Bob, 4=Carol, 5=David}
        assertTrue(lruCache[5] == "David")
        //LinkedHashMap: Adding a new entry, this will evict the least recently accessed (key 1)
        lruCache[6] = "Eve"
        //{3=Bob, 4=Carol, 5=David, 2=Alice, 6=Eve}
        assertFalse(lruCache.containsValue("John"))

    }
}