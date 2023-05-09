using System;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;

namespace ShellSort
{
    class ShellSort
    {
        static void Main(string[] args)
        {
            // Генеруємо масив випадкових чисел розміром size
            int size = 100;
            Console.WriteLine("Array size:" + size);
            int[] array = GenerateArray(size);

            // Створюємо копії згенерованого масиву, щоб посортувати їх різними методами алгоритму Шелла
            int[] array_s = new int[array.Length];
            Array.Copy(array, array_s, array.Length);

            int[] array_p = new int[array.Length];
            Array.Copy(array, array_p, array.Length);

            // Виводимо несортований масив
            Console.WriteLine("Unsorted array:");
            PrintArray(array);

            // Вимірюємо час виконання послідовного алгоритму Шелла
            Stopwatch stopwatch_s = new Stopwatch();
            stopwatch_s.Start();
            // Сортуємо масив послідовним алгоритмом Шелла
            SequentialShellSort(array_s);
            stopwatch_s.Stop();

            // Виводимо відсортований послідовним алгоритмом Шелла масив
            Console.WriteLine("Sorted array (Sequential):");
            PrintArray(array_s);

            // Вимірюємо час виконання паралельного алгоритму Шелла
            Stopwatch stopwatch_p = new Stopwatch();
            stopwatch_p.Start();
            // Сортуємо масив паралельним алгоритмом Шелла
            ParallelShellSort(array_p);
            stopwatch_p.Stop();

            // Виводимо відсортований паралельним алгоритмом Шелла масив
            Console.WriteLine("Sorted array (Parallel):");
            PrintArray(array_p);

            // Перевіряємо, чи ідентичні масиви, посортовані різними методами алгоритму Шелла
            Console.WriteLine("Identical sorted arrays: " + array_s.SequenceEqual(array_p));

            // Сортуємо вхідний масив вбудованими засобами, щоб перевірити коректність сортування
            Array.Sort(array);
            // Перевіряємо, чи правильно відсортовані масиви
            Console.WriteLine("Correctly sorted: " + array.SequenceEqual(array_p));

            // Виводимо час виконання послідовного алгоритму Шелла
            Console.WriteLine("Sequential shell sort time: " + stopwatch_s.Elapsed.TotalSeconds);

            // Виводимо час виконання паралельного алгоритму Шелла
            Console.WriteLine("Parallel shell sort time: " + stopwatch_p.Elapsed.TotalSeconds);

            // Виводимо прискорення паралельного алгоритму, порівняно з послідовним
            Console.WriteLine("Speed up: " + stopwatch_s.Elapsed.TotalSeconds / stopwatch_p.Elapsed.TotalSeconds);
        }

        static void SequentialShellSort(int[] array)
        {
            // Зберігаємо довжину масиву в змінній
            int length = array.Length;
            // Визначаємо величину кроку (відстань між елементами, що потівнюються)
            var d = length / 2;

            // Починаємо сортування з кроку d і зменшуємо його до 1
            while (d >= 1)
            {
                // Проходження масиву з кроком d
                for(int i = 0; i < d; i++ ){
                    // Змінна для зберігання елемента при перестановці елементів
                    int key;
                    // Змінна, що зберігатиме індекс елемента, що порівнюється
                    int k;

                    // Сортування підмасиву з кроком d
                    for (int j = i + d; j < length; j += d)
                    {
                        key = array[j];
                        k = j - d;

                        // Перестановка елемента на його місце в підмасиві
                        while (k >= 0 && array[k] > key)
                        {
                            // Перестановка елементів
                            array[k + d] = array[k];
                            k -= d;
                        }
                        array[k + d] = key;
                    }
                };

                // Зменшуємо крок для наступної ітерації
                d /= 2;
            }
        }

        static void ParallelShellSort(int[] array)
        {
            // Зберігаємо довжину масиву в змінній
            int length = array.Length;
            // Визначаємо величину кроку (відстань між елементами, що потівнюються)
            var d = length / 2;

            // Починаємо сортування з кроку d і зменшуємо його до 1
            while (d >= 1)
            {
                // Проходження масиву з кроком d (Кожен підмасив сортується ПАРАЛЕЛЬНО іншим)
                Parallel.For(0, d, i =>
                {
                    // Змінна для зберігання елемента при перестановці елементів
                    int key;
                    // Змінна, що зберігатиме індекс елемента, що порівнюється
                    int k;

                    // Сортування підмасиву з кроком d
                    for (int j = i + d; j < length; j += d)
                    {
                        key = array[j];
                        k = j - d;

                        // Перестановка елемента на його місце в підмасиві
                        while (k >= 0 && array[k] > key)
                        {
                            // Перестановка елементів
                            array[k + d] = array[k];
                            k -= d;
                        }
                        array[k + d] = key;
                    }
                });

                // Зменшуємо крок для наступної ітерації
                d /= 2;
            }
        }

        // Створення масиву цілих чисел та заповнення його випадковими значеннями
        static int[] GenerateArray(int size)
        {
            Random random = new Random();
            int[] array = new int[size];

            for (int i = 0; i < size; i++)
            {
                array[i] = random.Next(100000);
            }

            return array;
        }

        // Виведення масиву в консоль
        static void PrintArray(int[] array)
        {
            for (int i = 0; i < array.Length; i++)
            {
                Console.Write(array[i] + " ");
            }
            Console.WriteLine();
        }
    }
}
