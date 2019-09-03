package main.abp;

import gov.nasa.jpf.vm.Verify;

public class Duplicator<P> extends Thread {
    private Channel<P> channel;
    private Cell<Boolean> finish;

    public Duplicator(Channel<P> ch,Cell<Boolean> f) {
        this.channel = ch;
        this.finish = f;
    }

    public void run() {
        while (true) {
            try { Thread.sleep(100); }
            catch (InterruptedException e) { }
            if (finish.get()) break;
            Verify.beginAtomic();
            P p = channel.duptop();
            Verify.endAtomic();
            /*
            if (p != null)
                System.out.println("duplicated: " + p);
            */
        }
    }
}