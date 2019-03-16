package main;
import java.util.*;

public class ABP<P> {
    public void begin(Collection<P> sentPackets, Collection<P> recPackets, int bound) throws InterruptedException {
        Channel<Pair<P,Boolean>> ch1 = new Channel<Pair<P,Boolean>>(bound);
        Channel<Boolean> ch2 = new Channel<Boolean>(bound);
        
//        ch2.put(false);
//        ch2.put(true);
//        Iterator<P> iter = sentPackets.iterator();
//        ch1.put(new Pair<P, Boolean>(iter.next(), true));
//        ch1.put(new Pair<P, Boolean>(iter.next(), false));
        
        Cell<Boolean> f = new Cell<Boolean>(false);
        Sender<P> sender = new Sender<P>(ch1,ch2,sentPackets,f);
        Receiver<P> receiver = new Receiver<P>(ch1,ch2,recPackets,f);
        DDropper<Pair<P,Boolean>,Boolean> ddropper = new DDropper<Pair<P,Boolean>,Boolean>(ch1,ch2,f);
        DDuplicator<Pair<P,Boolean>,Boolean> dduplicator = new DDuplicator<Pair<P,Boolean>,Boolean>(ch1,ch2,f);
        
        /*
        Dropper<Pair<P,Boolean>> dropper1
            = new Dropper<Pair<P,Boolean>>(ch1,f);
        Duplicator<Pair<P,Boolean>> duplicator1
            = new Duplicator<Pair<P,Boolean>>(ch1,f);
        Dropper<Boolean> dropper2
            = new Dropper<Boolean>(ch2,f);
        Duplicator<Boolean> duplicator2
            = new Duplicator<Boolean>(ch2,f);
        */
        
        sender.start();
        receiver.start();
        ddropper.start();
        dduplicator.start();
        
        /*
        dropper1.start();
        duplicator1.start();
        dropper2.start();
        duplicator2.start();
        */
        
        sender.join();
        receiver.join();
        ddropper.join();
        dduplicator.join();
        
        /*
        dropper1.join();
        duplicator1.join();
        dropper2.join();
        duplicator2.join();
        */
    }
}