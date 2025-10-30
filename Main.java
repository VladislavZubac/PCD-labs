import java.util.Random;

// Класс потока, который вычисляет сумму произведений пар элементов массива, начиная с начала
class SumProductFromStart extends Thread {
    private int[] mas;
    // Поле для хранения ссылки на массив

    // Конструктор принимает массив чисел
    public SumProductFromStart(int[] mas) {
        this.mas = mas;
    }

    @Override
    public void run() {
        // Метод run() — то, что выполняется при запуске потока
        int sum = 0;
        // Переменная для накопления суммы произведений

        for (int i = 0; i < mas.length - 1; i += 2) {
            // Проходим по массиву с шагом 2, чтобы брать пары (0,1), (2,3) и т.д.
            int prod = mas[i] * mas[i + 1];
            // Перемножаем текущую пару элементов
            sum += prod;
            // Добавляем произведение в общую сумму

            System.out.println(getName() + " -> Позиции: " + i + "," + (i + 1) +
                    " Произв: " + prod + " Текущая сумма: " + sum);
            // Выводим имя потока, позиции, произведение и текущую сумму
        }

        System.out.println(getName() + " итоговая сумма произведений: " + sum);
        // После завершения цикла выводим итоговую сумму произведений
    }
}

// Класс потока, который вычисляет произведения пар элементов массива, начиная с конца,
// но учитывает только чётные позиции
class SumProductEvenFromEnd extends Thread {
    private int[] mas;
    // Массив, который будет обрабатываться

    // Конструктор принимает массив чисел
    public SumProductEvenFromEnd(int[] mas) {
        this.mas = mas;
    }

    @Override
    public void run() {
        int sum = 0;
        // Переменная для накопления суммы

        for (int i = mas.length - 2; i >= 0; i -= 2) {
            // Цикл идёт с конца массива к началу, с шагом 2
            if ((i % 2 == 0) && (i + 1 < mas.length)) {
                // Проверяем, что индекс чётный и что следующий элемент существует
                int prod = mas[i] * mas[i + 1];
                // Вычисляем произведение текущего и следующего элемента
                sum += prod;
                // Добавляем произведение к общей сумме

                System.out.println(getName() + " -> Позиции: " + i + "," + (i + 1) +
                        " Произв: " + prod + " Текущая сумма: " + sum);
                // Выводим текущие позиции, произведение и промежуточную сумму
            }
        }

        System.out.println(getName() + " итоговая сумма произведений четных позиций: " + sum);
        // После завершения цикла выводим итоговую сумму
    }
}

public class Main {
    public static void main(String[] args) {
        int[] mas = new int[100];
        Random rand = new Random();

        // Генерация случайных чисел от 1 до 100
        for (int i = 0; i < mas.length; i++) {
            mas[i] = rand.nextInt(100) + 1;
            // Генерируем случайное число от 1 до 100
            System.out.print(mas[i] + " ");
            // Выводим элемент массива в консоль
        }
        System.out.println("\n");
        // Переход на новую строку после вывода массива

        // Создаём два потока для параллельных вычислений
        SumProductFromStart th1 = new SumProductFromStart(mas);
        // Поток, обрабатывающий массив с начала
        SumProductEvenFromEnd th2 = new SumProductEvenFromEnd(mas);
        // Поток, обрабатывающий массив с конца

        // Устанавливаем имена потокам
        th1.setName("Th1 (с первого элемента)");
        th2.setName("Th2 (с последнего элемента)");

        // Запускаем оба потока
        th1.start();
        th2.start();

        // Ждём завершения обоих потоков, чтобы не продолжить выполнение раньше времени
        try {
            th1.join();
            // Ждём, пока поток th1 завершится
            th2.join();
            // Ждём, пока поток th2 завершится
        } catch (InterruptedException e) {
            e.printStackTrace();
            // В случае прерывания выводим стек ошибки
        }
//test
        String info = "Лабораторная работа 1 выполнена студентом: Влад Зубак, группа CR-233";

        for (char c : info.toCharArray()) {
            // Перебираем каждый символ строки
            System.out.print(c);
            // Печатаем символ без перехода на новую строку
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                // В случае прерывания — вывод ошибки
            }
        }

        System.out.println("\nРабота завершена успешно!");
    }
}
