package edu.fpt.swp391.g2.imageexp;

/**
 * The main class, or the boostrap class, which runs first to create the main instance and services
 */
public class ImageExpBoostrap {
    /**
     * The object of the main instance, in Singleton Pattern.
     * Use this to get all services.
     */
    public static final ImageExpMain INSTANCE = new ImageExpMain();

    /**
     * The main method
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        INSTANCE.enable(args);
    }
}
