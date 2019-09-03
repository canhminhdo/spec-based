package main.abp;
import java.util.Collection;

import atomic.HWMutex;
import atomic.Lock;
import atomic.TestAndSet;
import gov.nasa.jpf.vm.Verify;

public class ABP<P> {
	public void begin(
			Collection<P> sentPackets,
			Collection<P> recPackets,
			Channel<Pair<P,Boolean>> ch1,
			Channel<Boolean> ch2,
			Integer index,
			Boolean finish,
			Boolean flag1,
			Boolean flag2) throws InterruptedException {
		Verify.beginAtomic();
		
		// Initial for lock
		TestAndSet lockFlagCh1 = new TestAndSet();
		Lock lockCh1 = new HWMutex(lockFlagCh1);
		TestAndSet lockFlagCh2 = new TestAndSet();
		Lock lockCh2 = new HWMutex(lockFlagCh2);
		ch1.setLock(lockCh1);
		ch2.setLock(lockCh2);
		
		Cell<Boolean> f = new Cell<Boolean>(finish);
        Sender<P> sender = new Sender<P>(ch1,ch2,sentPackets,f,flag1,index);
        Receiver<P> receiver = new Receiver<P>(ch1,ch2,recPackets,f,flag2,sentPackets);
        DDropper<Pair<P,Boolean>,Boolean> ddropper = new DDropper<Pair<P,Boolean>,Boolean>(ch1,ch2,f);
        DDuplicator<Pair<P,Boolean>,Boolean> dduplicator = new DDuplicator<Pair<P,Boolean>,Boolean>(ch1,ch2,f);
        
        Verify.endAtomic();
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