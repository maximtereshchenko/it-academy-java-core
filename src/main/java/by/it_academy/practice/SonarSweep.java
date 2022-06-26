package by.it_academy.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class SonarSweep {

    public static void main(String[] args) throws IOException {
        System.out.println(sumsLargerThanPreviousSum(measurements1())); //7
        System.out.println(sumsLargerThanPreviousSum(measurements2())); //1390

        System.out.println(sumsOfThreeLargerThanPreviousSum(measurements1())); //5
        System.out.println(sumsOfThreeLargerThanPreviousSum(measurements2())); //1457
    }

    private static List<Integer> measurements1() {
        List<Integer> measurements = new ArrayList<>();
        measurements.add(199);
        measurements.add(200);
        measurements.add(208);
        measurements.add(210);
        measurements.add(200);
        measurements.add(207);
        measurements.add(240);
        measurements.add(269);
        measurements.add(260);
        measurements.add(263);
        return measurements;
    }

    private static List<Integer> measurements2() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/main/resources/SonarSweep.txt"));
        List<Integer> measurements = new ArrayList<>();

        for (String line : lines) {
            measurements.add(Integer.valueOf(line));
        }

        return measurements;
    }

    /**
     * Вы управляете подводной лодкой и спускаетесь в грубины океана.
     * Когда подводная лодка опускается, она автоматически выполняет гидролокационное сканирование морского дна.
     * На маленьком экране появляется отчет о сканировании гидролокатором (входные данные):
     * каждая строка представляет собой измерение глубины морского дна.
     * Например, предположим, что у вас есть следующий отчет:
     * 199
     * 200
     * 208
     * 210
     * 200
     * 207
     * 240
     * 269
     * 260
     * 263
     * В этом отчете написано, что при сканировании в направлении от подводной лодки гидролокатор обнаружил глубины
     * 199, 200, 208, 210 и так далее.
     * Нужно выяснить, как быстро увеличивается глубина. Для этого подсчитайте, сколько раз измерение глубины увеличивается
     * по сравнению с предыдущим измерением. В приведенном выше примере изменения следующие:
     * 199 (нет предыдущих измерений)
     * 200 (увеличилась)
     * 208 (увеличилась)
     * 210 (увеличилась)
     * 200 (уменьшилась)
     * 207 (увеличилась)
     * 240 (увеличилась)
     * 269 (увеличилась)
     * 260 (уменьшилась)
     * 263 (увеличилась)
     * В этом примере есть 7 измерений, которые больше, чем предыдущее измерение.
     */
    private static int sumsLargerThanPreviousSum(List<Integer> measurements) {
        return 0;
    }

    /**
     * Учет каждого отдельного измерения не так полезен, как вы ожидали: слишком много шума в данных.
     * Вместо этого рассмотрим суммы скользящего окна из трех измерений. Применяя к примеру выше:
     * 199  A
     * 200  A B
     * 208  A B C
     * 210    B C D
     * 200  E   C D
     * 207  E F   D
     * 240  E F G
     * 269    F G H
     * 260      G H
     * 263        H
     * Начнем со сравнения первого и второго окон. Измерения в первом окне отмечены буквой А (199, 200, 208);
     * их сумма 199 + 200 + 208 = 607. Второе окно отмечено буквой B (200, 208, 210); его сумма равна 618.
     * Сумма измерений во втором окне больше, чем сумма в первом, поэтому это первое сравнение увеличилось.
     * Теперь ваша цель состоит в том, чтобы подсчитать, сколько раз сумма измерений в окне увеличивается по сравнению
     * с предыдущей суммой. То есть, сравните A с B, затем сравните B с C, затем C с D и так далее.
     * В приведенном выше примере сумма каждого окна с тремя измерениями выглядит следующим образом:
     * A: 607 (нет предыдущей суммы)
     * В: 618 (увеличилась)
     * С: 618 (без изменений)
     * Д: 617 (уменьшилась)
     * Е: 647 (увеличилась)
     * F: 716 (увеличилась)
     * Г: 769 (увеличилась)
     * H: 792 (увеличилась)
     * В этом примере есть 5 сумм, которые больше, чем предыдущая сумма.
     *
     * @author Maxim Tereshchenko
     */
    private static int sumsOfThreeLargerThanPreviousSum(List<Integer> measurements) {
        return 0;
    }
}
