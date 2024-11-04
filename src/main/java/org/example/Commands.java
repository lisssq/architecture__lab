package org.example;

public class Commands
{
    Tasks t;
    int index_memory;
    int figure; //число которое мы передаем
    String register, register_dop;

    //CPU c = new CPU();      //экземпляр процессора для команд

    public Tasks getTasks()
    {
        return t;
    }

    public Commands(Tasks t, int index_memory, int figure)
    {
        this.t = t;
        this.index_memory = index_memory;
        this.figure = figure;
    }

    public Commands(Tasks t, String register, int index_memory)
    {
        this.t = t;
        this.register = register;
        this.index_memory = index_memory;
    }

    public Commands(Tasks t)
    {
        this.t = t;
    }

    public Commands(Tasks t, String register_1, String register_2)
    {
        this.t = t;
        this.register = register_1;
        this.register_dop = register_2;
    }
}
