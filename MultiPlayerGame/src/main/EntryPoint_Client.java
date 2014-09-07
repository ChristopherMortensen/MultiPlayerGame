package main;

import control.ControlClass;

public class EntryPoint_Client {
        public static void main(String[] args) {
        ControlClass controlClass = new ControlClass("client", "10.0.0.10");
        controlClass.run();
    }
}
