package com.libcentro.demo.utils.command;

import com.libcentro.demo.services.ProgressService;
import com.libcentro.demo.services.interfaces.IprogressService;
import lombok.Getter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Stack;

@Getter
public class CommandInvoker {
    private Stack<Command> commands = new Stack<>();
    private int count = 0;

    private IprogressService<Command> progressService;

    public CommandInvoker() {
    }

    public void executeCommand(Command command) {
        command.execute();
        commands.push(command);
        this.count++;
    }

    public void undoCommand() {
        if (!commands.isEmpty()) {
            Command command = commands.pop();
            command.undo();
            this.count--;
        }
    }

    public void undoAll() {
        if (commands.isEmpty()) {
            throw new RuntimeException ("No hay cambios para deshacer");
        }

        progressService = new ProgressService<Command>(null,count);
        progressService.ejecutarProceso(
                new ArrayList<> (commands),
                command -> {
                    command.undo();
                    count--;
                }
        );
        commands.clear();
    }

    public boolean save() {
        if (count == 0) {
            throw new RuntimeException ("No hay cambios que guardar");
        }
        progressService = new ProgressService<Command>(null,count);
        progressService.ejecutarProceso(
                new ArrayList<> (commands),
                command -> {
                    commands.pop();
                    count = 0;
                }
        );

        return true;
    }
}