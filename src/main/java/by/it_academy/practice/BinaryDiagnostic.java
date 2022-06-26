package by.it_academy.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim Tereshchenko
 */
class BinaryDiagnostic {

    public static void main(String[] args) throws IOException {
        System.out.println(powerConsumption(diagnosticReport1())); //198
        System.out.println(powerConsumption(diagnosticReport2())); //3242606
    }

    private static List<String> diagnosticReport1() {
        List<String> diagnosticReport = new ArrayList<>();
        diagnosticReport.add("00100");
        diagnosticReport.add("11110");
        diagnosticReport.add("10110");
        diagnosticReport.add("10111");
        diagnosticReport.add("10101");
        diagnosticReport.add("01111");
        diagnosticReport.add("00111");
        diagnosticReport.add("11100");
        diagnosticReport.add("10000");
        diagnosticReport.add("11001");
        diagnosticReport.add("00010");
        diagnosticReport.add("01010");
        return diagnosticReport;
    }

    private static List<String> diagnosticReport2() throws IOException {
        return Files.readAllLines(Paths.get("src/main/resources/BinaryDiagnostic.txt"));
    }

    /**
     * Подлодка издавала какие-то странные скрипящие звуки, поэтому на всякий случай
     * вы попросили предоставить диагностический отчет.
     * Диагностический отчет (входные данные) состоит из списка двоичных чисел, которые при правильной расшифровке
     * могут рассказать вам много полезного о потребляемой мощности.
     * Вам необходимо использовать двоичные числа в диагностическом отчете для создания двух новых двоичных чисел
     * (называемых коэффициентом гаммы и коэффициентом эпсилон).
     * Потребляемая мощность может быть найдена путем умножения коэффициента гаммы на коэффициент эпсилон.
     * Каждый бит в частоте гаммы можно определить, найдя наиболее распространенный бит в соответствующей позиции
     * всех чисел в диагностическом отчете. Например, учитывая следующий диагностический отчет:
     * 00100
     * 11110
     * 10110
     * 10111
     * 10101
     * 01111
     * 00111
     * 11100
     * 10000
     * 11001
     * 00010
     * 01010
     * Учитывая только первый бит каждого числа, имеется пять битов 0 и семь битов 1.
     * Поскольку наиболее распространенный бит равен 1, первый бит коэффициента гаммы равен 1.
     * Чаще всего второй бит числа в диагностическом отчете равен 0, поэтому второй бит коэффициента гаммы равен 0.
     * Наиболее распространенными значениями третьего, четвертого и пятого битов являются 1, 1 и 0 соответственно,
     * поэтому последние три бита коэффициента гаммы равны 110.
     * Итак, гамма-коэффициент — это двоичное число 10110, или 22 в десятичной системе.
     * Коэффициент эпсилон рассчитывается аналогичным образом; вместо использования самого общего бита используется
     * наименее общий бит из каждой позиции. Таким образом, скорость эпсилон равна 01001, или 9 в десятичной системе.
     * Умножение коэффициента гаммы (22) на коэффициент эпсилон (9) дает потребляемую мощность 198.
     * Используйте двоичные числа в своем диагностическом отчете, чтобы вычислить скорость гаммы и скорость эпсилон,
     * а затем перемножьте их.
     * Какова потребляемая мощность подводной лодки?
     */
    private static int powerConsumption(List<String> diagnosticReport) {
        return 0;
    }
}
