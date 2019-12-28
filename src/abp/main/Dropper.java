package abp.main;

import gov.nasa.jpf.vm.Verify;

public class Dropper<P> extends Thread {
    private Channel<P> channel;
    private Cell<Boolean> finish;

    public Dropper(Channel<P> ch,Cell<Boolean> f) {
        this.channel = ch;
        this.finish = f;
    }

    public void run() {
        while (true) {
            try { Thread.sleep(100); }
            catch (InterruptedException e) { }
            if (finish.get()) break;
            Verify.beginAtomic();
            P p = channel.get();
            Verify.endAtomic();
            /*
            if (p != null)
                System.out.println("dropped: " + p);
            */
        }
    }
}