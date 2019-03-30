package main;
import java.util.*;

public class Sender<P> extends Thread {
    private Channel<Pair<P,Boolean>> channel1;
    private Channel<Boolean> channel2;
    private Collection<P> packetsToBeSent;
    private Cell<Boolean> finish;
    private Boolean flag1 = true;
    private int index = 0;

    public Sender(Channel<Pair<P,Boolean>> ch1,
                  Channel<Boolean> ch2,
                  Collection<P> c,
                  Cell<Boolean> f) {
        this.channel1 = ch1;
        this.channel2 = ch2;
        this.packetsToBeSent = c;
        this.finish = f;
    }

    public void run() {
    	for (int i = 0; i < packetsToBeSent.size(); i ++) {
    		synchronized(this) {
            	index = i;
            }
    		Pair<P,Boolean> pr = new Pair<P,Boolean>(((List<P>)packetsToBeSent).get(i),flag1);
            while (true) {
                channel1.put(pr);
                /*
                if (p != null)
                    System.out.println("Snd-Sending " + p);
                */
                Boolean b = channel2.get();
                if (b != null) {
                    if (b == !flag1) {
                        /*
                        System.out.println("Snd-Received " + b);
                        */
                        flag1 = !flag1;
                        break;
                    }
                }
            }
    	}
        finish.set(true);
        /*
        System.out.println("Sender has sent all packets!");
        */
    }
}