package org.example;

import java.util.HashMap;
import java.util.*;
import java.util.function.*;
public class CPU implements ICpu
{
    int r1;
    int r2;
    int r3;
    int r4;

    Memory mem = new Memory();      //экземпляр памяти

    // методы для получения и установки значений регистров
    int GetterR1() {return r1;}
    void SetterR1(int r1) {this.r1=r1;}

    int GetterR2() {return r2;}
    void SetterR2(int r2) {this.r2=r2;}

    int GetterR3() {return r3;}
    void SetterR3(int r3) {this.r3=r3;}

    int GetterR4() {return r4;}
    void SetterR4(int r4) {this.r4=r4;}


    @Override
    public void doCommand(Commands c)
    {
        Map<String, Consumer<Integer>> registerSetMap = new HashMap<>();
        registerSetMap.put("r1", this::SetterR1);       // хранит функции для записи значений в регистры
        registerSetMap.put("r2", this::SetterR2);
        registerSetMap.put("r3", this::SetterR3);
        registerSetMap.put("r4", this::SetterR4);

        Map<String, Supplier<Integer>> registerGetMap = new HashMap<>();
        registerGetMap.put("r1", this::GetterR1);       // хранит функции для получения значений из регистров
        registerGetMap.put("r2", this::GetterR2);
        registerGetMap.put("r3", this::GetterR3);
        registerGetMap.put("r4", this::GetterR4);


        switch (c.getTasks())
        {
            case init:
                mem.memory[c.index_memory] = c.figure;    //в ячейку памяти №** загрузится какое-то число/значение
                break;

            case ld:
                Consumer<Integer> registerSetter = registerSetMap.get(c.register);      //выбираем регистр
                // c.register - это строка, представляющая регистр (например, "r1").
                // Если c.register равно "r1", то registerSetter будет указывать на метод SetterR1.
                if (registerSetter != null)         // если нашли функцию для установки значения в регистр
                {
                    registerSetter.accept(mem.memory[c.index_memory]);  //загружаем значение из памяти в регистр
                }
                break;

            case print:
                System.out.println("Reg1: " + GetterR1());
                System.out.println("Reg2: " + GetterR2());
                System.out.println("Reg3: " + GetterR3());
                System.out.println("Reg4: " + GetterR4());
                break;

            case add:
                SetterR4(GetterR1() + GetterR2());
                break;

            case sub:
                SetterR4(GetterR1() - GetterR2());
                break;

            case mul:
                SetterR4(GetterR1() * GetterR2());
                break;

            case div:
                SetterR4(GetterR1() / GetterR2());
                break;

            case st:            // запись значения из регистра в память
                Supplier<Integer> registerGetter = registerGetMap.get(c.register);  //если c.register равно "r1",
                // то registerGetter будет указывать на метод GetterR1
                if (registerGetter != null)
                {
                    (mem.memory[c.index_memory]) = registerGetter.get();    // получаем значение из регистра и сохраняем его в памяти по адресу c.index_memory.
                }
                break;

            case mv:        // перемещение значения из одного регистра в другой
                Supplier<Integer> registerGetter_1 = registerGetMap.get(c.register);    //получаем значение из источника (у нас это р1)
                Consumer<Integer> registerSetter_2 = registerSetMap.get(c.register_dop);    //функция записи в регистр (?)

                if (registerGetter_1 != null && registerSetter_2 != null)     //если оба существуют
                {
                    registerSetter_2.accept(registerGetter_1.get());    //перемещаем значение из р1 в р2
                }
                break;
        }
    }
}
