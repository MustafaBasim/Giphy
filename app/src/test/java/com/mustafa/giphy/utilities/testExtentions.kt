package com.mustafa.giphy.utilities

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: FreshWorks Giphy
 * Package: com.mustafa.giphy.utilities
 * Date: 8/25/2021
 */

@Throws(InterruptedException::class)
fun <T> getValue(liveData: LiveData<T>): T {
    val data = arrayOfNulls<Any>(1)
    val latch = CountDownLatch(1)
    liveData.observeForever { o ->
        data[0] = o
        latch.countDown()
    }
    latch.await(2, TimeUnit.SECONDS)
    @Suppress("UNCHECKED_CAST")
    return data[0] as T
}

fun <T> LiveData<T>.getOrAwaitFirstSetValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    return getOrAwaitSecondSetValue(time = time, timeUnit = timeUnit, latchTime = 1) { afterObserve.invoke() }
}

fun <T> LiveData<T>.getOrAwaitSecondSetValue(
    time: Long = 2,
    latchTime: Int = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(latchTime)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            if (latch.count == 0L) this@getOrAwaitSecondSetValue.removeObserver(this)
        }
    }
    this.observeForever(observer)
    afterObserve.invoke()
    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        this.removeObserver(observer)
        if (latch.count == latchTime.toLong()) {
            throw TimeoutException("LiveData value was never set.")
        } else {
            return data as T
        }
    }
    @Suppress("UNCHECKED_CAST")
    return data as T
}