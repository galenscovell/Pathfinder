package util;

public class Constants {
    private Constants() {}

    public static int SCREEN_X = 800;
    public static int SCREEN_Y = 600;
    public static int TILESIZE = 14;
    public static int MARGIN = 1;
    public static int TILES_X = SCREEN_X / (TILESIZE + MARGIN);
    public static int TILES_Y = SCREEN_Y / (TILESIZE + MARGIN);
}
