package com.libcentro.demo.utils.command;

import java.util.Stack;

public class CommandInvoker {
    public Stack<Command> commands = new Stack<>();

    public void executeCommand(Command command){
        command.execute();
        commands.push(command);
    }

    public void undoCommand(){
        if (!commands.isEmpty()) {
            Command command = commands.pop();
            command.undo();
        }
    }

    public void undoAll(){
        while (!commands.isEmpty()) {
            Command command = commands.pop();
            command.undo();
        }
    }

    public void save(){
        while (!commands.isEmpty()) {
            Command command = commands.pop();
        }
    }

}
