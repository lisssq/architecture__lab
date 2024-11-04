package org.example;

public class App
{
    public static void main(String[] args)
    {
        Commands prog[] =
                {
                        (new Commands(Tasks.init, 2, 20)),
                        (new Commands(Tasks.init, 3, 30)),
                        (new Commands(Tasks.ld, "r1", 3)),
                        (new Commands(Tasks.ld, "r2", 2)),
                        (new Commands(Tasks.mul)),
                        (new Commands(Tasks.st, "r4", 4)),
                        (new Commands(Tasks.ld, "r3", 4)),
                        (new Commands(Tasks.mv, "r1", "r2")),
                        (new Commands(Tasks.print))


                };

        ICpu cpu = BCpu.build();        // создать новый процессор
        //ICpu cpu = new CPU();

        for (Commands commands : prog) {
            cpu.doCommand(commands);
        }
    }
}