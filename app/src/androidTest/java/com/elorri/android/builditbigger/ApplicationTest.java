package com.elorri.android.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.concurrent.CountDownLatch;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    //This latch will allow the main thread to wait until the background thread return before
    // pursuing.
    private static final int COUNT_LATCH = 1;
    private CountDownLatch latch = null;
    private String result = null;

    public ApplicationTest() {
        super(Application.class);
    }


    public void testIsGCEResultValid() throws InterruptedException {
        latch = new CountDownLatch(COUNT_LATCH);
        GCEndpointsApiService task = new GCEndpointsApiService();


        task.execute(getContext());
        task.setListener(new GCEndpointsApiService.GCEndpointsApiServiceListener() {
            @Override
            public void onCompleted(String joke) {
                result = joke;
                latch.countDown();  // Stop the blocking.
            }
        });

        //start waiting until the latch has counted down to zero, move to next line only then.
        latch.await();


        //This test won't pass if we haven't made sure its done when task completed using latch
        assertNotNull(result);
        assertEquals("This is a joke from a Java Library", result);

    }
}