package main;

import control.ControlClass;

/**
 *
 * @author Christopher
 */
public class EntryPoint_Server {

    public static void main(String[] args) {
        ControlClass controlClass = new ControlClass("server");
        controlClass.run();
    }
}
