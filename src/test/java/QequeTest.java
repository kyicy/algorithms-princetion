import org.junit.Test;

public class QequeTest {
    @Test
    public void testDeque() {
        Deque<Integer> di = new Deque<>();
        di.addFirst(4);
        for (Integer e : di) {
            System.out.println(e);
        }
    }

    @Test
    public void testRandomizedQueue() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(5);
        rq.dequeue();
        rq.enqueue(3);

        for (Integer e : rq) {
            System.out.println(e);
        }
    }
}
