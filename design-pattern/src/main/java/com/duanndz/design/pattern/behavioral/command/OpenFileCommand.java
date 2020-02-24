package com.duanndz.design.pattern.behavioral.command;

public class OpenFileCommand implements Command {

    private FileSystemReceiver receiver;

    public OpenFileCommand(FileSystemReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        //open command is forwarding request to openFile method
        this.receiver.openFile();
    }


}
