package com.duanndz.design.pattern.behavioral.command;

public class WriteFileCommand implements Command {

    private FileSystemReceiver receiver;

    public WriteFileCommand(FileSystemReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.writeFile();
    }

}
