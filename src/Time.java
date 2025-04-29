public class Time {
    public static double TimeStarted = System.nanoTime();

    public static double getTime(){
        return System.nanoTime() - TimeStarted * 1e-9;
    }

}
