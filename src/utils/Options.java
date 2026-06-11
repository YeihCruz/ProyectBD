package utils;

import java.awt.*;

public class Options {
    private Dimension screenSize;
    private int currentSize;
    private static Options options;


    public Options (){
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        currentSize= 0;
    }
    public static Options getOptions(){
        if( options == null){
            options = new Options();
        }
        return options;
    }
    public void changeAplicationSize(int i) {
        switch (i){
            case  0:
                screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                break;
            case 1:
                Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
                screenSize = new Dimension((int) (dm.width*0.9), (int) (dm.height*0.9));
                break;
            case 2:
                Dimension dm1 = Toolkit.getDefaultToolkit().getScreenSize();
                screenSize = new Dimension((int) (dm1.width*0.75), (int) (dm1.height*0.75));
                break;
            case 3:
                Dimension dm2 = Toolkit.getDefaultToolkit().getScreenSize();
                screenSize = new Dimension((int) (dm2.width*0.5), (int) (dm2.height*0.5));
                break;
            case 4:
                Dimension dm3 = Toolkit.getDefaultToolkit().getScreenSize();
                screenSize = new Dimension((int) (dm3.width*0.25), (int) (dm3.height*0.25));
                break;
            default:
                break;
        }
        currentSize= i;

    }

    public Dimension getScreenSize(){
        return screenSize;
    }
    public int getCurrentSize(){
        return currentSize;
    }
    }

