package in.mcxiv.animation;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.DoubleAdder;

import static org.junit.jupiter.api.Assertions.*;

class AnimatorTest {
    @Test
    void test() throws InterruptedException {
        Animator animator = new Animator();

        AtomicInteger calls = new AtomicInteger();
        DoubleAdder deltas = new DoubleAdder();
        animator.addActor(x -> {
            deltas.add(x);
            calls.incrementAndGet();
        });

        animator.start();
        Thread.sleep(2000);
        animator.stop();
        Thread.sleep(1000);

        System.out.println("calls = " + calls);
        System.out.println("deltas = " + deltas);
        assertEquals(2000f, deltas.floatValue(), 100);
    }
}