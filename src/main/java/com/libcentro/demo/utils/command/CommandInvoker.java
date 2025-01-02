package com.libcentro.demo.utils.command;

import java.util.Stack;

public class CommandInvoker {
    private Stack<Command> commands = new Stack<>();
    private int count = 0;

    public void executeCommand(Command command){
        command.execute();
        commands.push(command);
        this.count++;
    }

    public void undoCommand(){
        if (!commands.isEmpty()) {
            Command command = commands.pop();
            command.undo();
            this.count--;
        }
    }

    public void undoAll(){
        while (!commands.isEmpty()) {
            Command command = commands.pop();
            command.undo();
            this.count--;
        }
    }

    public boolean save(){
        if(this.count==0) return false;
        while (!commands.isEmpty()) {
            Command command = commands.pop();
            this.count = 0;
        }
        return true;
    }

    public Stack<Command> getCommands() {
        return commands;
    }

    public int getCount() {
        return count;
    }
}
