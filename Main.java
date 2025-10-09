import java.util.Random;

class SumProductFromStart extends Thread {
    private int[] mas;

    public SumProductFromStart(int[] mas) {
        this.mas = mas;
    }

    @Override
    public void run() {
        int sum = 0;
        for (int i = 0; i < mas.length - 1; i += 2) {
            int prod = mas[i] * mas[i + 1];
            sum += prod;
            System.out.println(getName() + " -> Позиции: " + i + "," + (i + 1) +
                    " Произв: " + prod + " Текущая сумма: " + sum);
        }
        System.out.println(getName() + " итоговая сумма произведений: " + sum);
    }
}

class SumProductEvenFromEnd extends Thread {
    private int[] mas;

    public SumProductEvenFromEnd(int[] mas) {
        this.mas = mas;
    }

    @Override
    public void run() {
        int sum = 0;
        for (int i = mas.length - 2; i >= 0; i -= 2) {
            if ((i % 2 == 0) && (i + 1 < mas.length)) {
                int prod = mas[i] * mas[i + 1];
                sum += prod;
                System.out.println(getName() + " -> Позиции: " + i + "," + (i + 1) +
                        " Произв: " + prod + " Текущая сумма: " + sum);
            }
        }
        System.out.println(getName() + " итоговая сумма произведений четных позиций: " + sum);
    }
}

 public class Main {
    public static void main(String[] args) {
        int[] mas = new int[100];
        Random rand = new Random();

        // Генерация случайных чисел от 1 до 100
        for (int i = 0; i < mas.length; i++) {
            mas[i] = rand.nextInt(100) + 1;
            System.out.print(mas[i] + " ");
        }
        System.out.println("\n");

        // Создаем и запускаем потоки
        SumProductFromStart th1 = new SumProductFromStart(mas);
        SumProductEvenFromEnd th2 = new SumProductEvenFromEnd(mas);

        th1.setName("Th1 (с первого элемента)");
        th2.setName("Th2 (с последнего элемента)");

        th1.start();
        th2.start();

        // Ждем окончания обоих потоков
        try {
            th1.join();
            th2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // После завершения — информация о студенте (буквы появляются по 100 мс)
        String info = "Лабораторная работа 1 выполнена студентом: Влад Зубак, группа CR-233";
        for (char c : info.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\nРабота завершена успешно!");
    }
}
