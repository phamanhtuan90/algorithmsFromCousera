import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int N = 0;

    public RandomizedQueue(){
        q = (Item[]) new Object[1];
    }
    public boolean isEmpty(){
        return N == 0;
    }

    public int size(){
        return N;
    }

    private void resize(int max){
        Item[] temp = (Item[]) new Object[max];
        for(int i = 0;i <N;i++){
            temp[i] = q[i];
        }
        q = temp;
    }

    public void enqueue(Item item){
        if(item == null) throw new NullPointerException();
        if(N == q.length) resize(2*q.length);
        q[N++] = item;
    }

    public Item dequeue(){
        if(isEmpty()) throw new NoSuchElementException();
        int offset = StdRandom.uniform(N);
        Item item = q[offset];
        if(offset != N-1) q[offset] = q[N-1];
        q[N-1] = null;
        N--;
        if(N > 0 && N == q.length/4) resize(q.length/2);
        return item;
    }
    public Item sample(){
        if(isEmpty()) throw new NoSuchElementException();
        int offset = StdRandom.uniform(N);
        return q[offset];
    }

    public Iterator<Item> iterator(){
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private Item[] copyArray = (Item[]) new Object[q.length];
        private int copyN = N;

        public ArrayIterator(){
            for(int i = 0;i <q.length;i++){
                copyArray[i] = q[i];
            }
        }

        public boolean hasNext(){
            return copyN != 0;
        }

        public void remove(){
            throw new UnsupportedOperationException();
        }

        public Item next(){
            if (!hasNext()) throw new NoSuchElementException();
            int offset = StdRandom.uniform(copyN);
            Item item = copyArray[offset];
            if(offset != copyN-1){
                copyArray[offset] = copyArray[copyN-1];
            }
            copyArray[copyN-1] = null;
            copyN--;
            return item;
        }
    }

    public static void main(String[] args){
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) q.enqueue(item);
            else if (!q.isEmpty()) StdOut.print(q.dequeue() + " ");
        }
        StdOut.println("(" + q.size() + " left on queue)");
    }
}
  