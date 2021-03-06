package abp.main;
public class DDropper<P,B> extends Thread {
    private Channel<P> channel1;
    private Channel<B> channel2;
    private Cell<Boolean> finish;

    public DDropper(Channel<P> ch1,Channel<B> ch2,Cell<Boolean> f) {
        this.channel1 = ch1;
        this.channel2 = ch2;
        this.finish = f;
    }

    public void run() {
        while (true) {
            try { Thread.sleep(100); }
            catch (InterruptedException e) { }
            if (finish.get()) break;
            
            channel1.getLock().requestCS();
            P p = channel1.get();
            channel1.getLock().releaseCS();
            
            channel2.getLock().requestCS();
            B b = channel2.get();
            channel2.getLock().releaseCS();
            
//            if (p != null)
//                System.out.println("dropped1: " + p);
//            if (b != null)
//                System.out.println("dropped2: " + b);
            
        }
    }
}