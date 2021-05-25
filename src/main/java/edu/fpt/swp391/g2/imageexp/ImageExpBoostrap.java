package edu.fpt.swp391.g2.imageexp;

public class ImageExpBoostrap {
    public static final ImageExpMain INSTANCE = new ImageExpMain();

    public static void main(String[] args) {
        INSTANCE.enable();
    }
}
