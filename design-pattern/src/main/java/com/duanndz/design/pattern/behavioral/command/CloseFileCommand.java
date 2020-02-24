package com.duanndz.design.pattern.behavioral.command;

public class CloseFileCommand implements Command {

    private FileSystemReceiver receiver;

    public CloseFileCommand(FileSystemReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.closeFile();
    }
}
