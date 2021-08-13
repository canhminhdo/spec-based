package abp.main;
import java.util.Collection;
import java.util.List;

import config.CaseStudy;
import gov.nasa.jpf.vm.Verify;
import lock.LockPool;

public class Receiver<P> extends LockPool {
    private Channel<Pair<P,Boolean>> channel1;
    private Channel<Boolean> channel2;
    private Collection<P> packetsToBeSent;
    private Collection<P> packetsReceived;
    private Cell<Boolean> finish;
    private Boolean flag2;	// initially `true`

    public Receiver(Channel<Pair<P,Boolean>> ch1,
                    Channel<Boolean> ch2,
                    Collection<P> c,
                    Cell<Boolean> f,
                    Boolean flag2,
                    Collection<P> packetsToBeSent) {
        this.channel1 = ch1;
        this.channel2 = ch2;
        this.packetsReceived = c;
        this.finish = f;
        this.flag2 = flag2;
        this.packetsToBeSent = packetsToBeSent;
    }

    public void run() {
        while (true) {
        	synchronized (this.getLock()) {
        		Verify.beginAtomic();
        		if (finish.get()) break;
        		Boolean b = channel2.put(flag2);
        		Verify.endAtomic();
			}
            /*
            if (b != null)
                System.out.println("RecSending " + flag2);
            */
            
        	synchronized (this.getLock()) {
        		Verify.beginAtomic();
        		if (finish.get()) break;
        		Pair<P,Boolean> pr = channel1.get();
        		if (pr != null) {
        			/*
                	System.out.println("RecReceived " + pr);
        			 */
        			if (pr.second() == flag2) {
        				// Add bugs
//	                	if (packetsReceived.size() == 2) {
//	                		packetsReceived.add(((List<P>)packetsToBeSent).get(3));
//	                	} else {
//	                		packetsReceived.add(pr.first());
//	                	}
        				packetsReceived.add(pr.first());
        				flag2 = !flag2;
        				
        			}
        		}
        		Verify.endAtomic();
        	}
        	
//        	if (finish.get()) break;
        }
        /*
        System.out.println("Receiver has received all packets!");
        */
    }
}