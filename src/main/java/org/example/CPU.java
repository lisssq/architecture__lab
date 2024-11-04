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
    int GetR1() {return r1;}
    void SetR1(int r1) {this.r1=r1;}

    int GetR2() {return r2;}
    void SetR2(int r2) {this.r2=r2;}

    int GetR3() {return r3;}
    void SetR3(int r3) {this.r3=r3;}

    int GetR4() {return r4;}
    void SetR4(int r4) {this.r4=r4;}


    @Override
    public void doCommand(Commands c)
    {
        Map<String, Consumer<Integer>> registerSetMap = new HashMap<>();
        registerSetMap.put("r1", this::SetR1);       // хранит функции для записи значений в регистры
        registerSetMap.put("r2", this::SetR2);
        registerSetMap.put("r3", this::SetR3);
        registerSetMap.put("r4", this::SetR4);

        Map<String, Supplier<Integer>> registerGetMap = new HashMap<>();
        registerGetMap.put("r1", this::GetR1);       // хранит функции для получения значений из регистров
        registerGetMap.put("r2", this::GetR2);
        registerGetMap.put("r3", this::GetR3);
        registerGetMap.put("r4", this::GetR4);


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
                System.out.println("Reg1: " + GetR1());
                System.out.println("Reg2: " + GetR2());
                System.out.println("Reg3: " + GetR3());
                System.out.println("Reg4: " + GetR4());
                break;

            case add:
                SetR4(GetR1() + GetR2());
                break;

            case sub:
                SetR4(GetR1() - GetR2());
                break;

            case mul:
                SetR4(GetR1() * GetR2());
                break;

            case div:
                SetR4(GetR1() / GetR2());
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
