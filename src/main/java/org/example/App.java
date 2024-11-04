package org.example;

public class App
{
    public static void main(String[] args)
    {
        Collect prog = new Collect();
        prog.add(new Commands(Tasks.init,5,15));
        prog.add(new Commands(Tasks.init, 10, 20));
        prog.add(new Commands(Tasks.init, 11, 25));
        prog.add(new Commands(Tasks.init, 12, 5));
        prog.add(new Commands(Tasks.ld, "r1", 10));
        prog.add(new Commands(Tasks.ld, "r2", 11));
        prog.add(new Commands(Tasks.ld, "r3", 12));
        prog.add(new Commands(Tasks.add));
        prog.add(new Commands(Tasks.print));
        prog.add(new Commands(Tasks.mv, "r1", "r2"));
        prog.add(new Commands(Tasks.div));
        prog.add(new Commands(Tasks.ld, "r3", 5));
        prog.add(new Commands(Tasks.print));

        ICpu cpu = BCpu.build();

        for (Commands command : prog)       // выполнение всех команд из Collect
        {
            cpu.doCommand(command);

            if (command.getTasks() == Tasks.print)
            {
                System.out.println("------");
            }
        }

        // статистика по программе
        System.out.println("Наиболее часто встречающаяся инструкция: " + prog.mostPopularInstruction());
        System.out.println("Диапазон используемых адресов памяти: " + prog.getMemoryAddressRange());
        System.out.println("Инструкции, отсортированные по частоте: " + prog.getInstructionsByCount());
    }
}
