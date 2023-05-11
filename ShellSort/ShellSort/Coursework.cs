using System;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;

namespace ShellSort
{
    class Coursework
    {
        static void Main(string[] args)
        {
            // Змінна, що зберігає розмір масиву
            int size = 1000000;
            // Змінна, на яку буде ділитись значення кроку d
            int step_divider = 5;

            // Генеруємо масив випадкових чисел розміром size
            Console.WriteLine("Array size: " + size);
            int[] array = GenerateArray(size);

            // Створюємо клас, що містить алгоритми сортуваня масиву змінних типу int
            Console.WriteLine("Step divider: " + step_divider);
            ShellSort<int> algorithms = new ShellSort<int>(step_divider);

            // Створюємо копії згенерованого масиву, щоб посортувати їх різними методами алгоритму Шелла
            int[] array_s = new int[array.Length];
            Array.Copy(array, array_s, array.Length);

            int[] array_p = new int[array.Length];
            Array.Copy(array, array_p, array.Length);

            /*// Виводимо несортований масив
            Console.WriteLine("Unsorted array:");
            PrintArray(array);*/

            // Вимірюємо час виконання послідовного алгоритму Шелла
            Stopwatch stopwatch_s = new Stopwatch();
            stopwatch_s.Start();
            // Сортуємо масив послідовним алгоритмом Шелла
            algorithms.SequentialShellSort(array_s);
            stopwatch_s.Stop();

            /*// Виводимо відсортований послідовним алгоритмом Шелла масив
            Console.WriteLine("Sorted array (Sequential):");
            PrintArray(array_s);*/

            // Вимірюємо час виконання паралельного алгоритму Шелла
            Stopwatch stopwatch_p = new Stopwatch();
            stopwatch_p.Start();
            // Сортуємо масив паралельним алгоритмом Шелла
            algorithms.ParallelShellSort(array_p);
            stopwatch_p.Stop();

            /*// Виводимо відсортований паралельним алгоритмом Шелла масив
            Console.WriteLine("Sorted array (Parallel):");
            PrintArray(array_p);*/

            // Перевіряємо, чи ідентичні масиви, посортовані різними методами алгоритму Шелла
            if (array_s.SequenceEqual(array_p)) PrintColorMessage("Sorted Arrays are identical.", ConsoleColor.Green);
            else PrintColorMessage("Sorted Arrays are not identical.", ConsoleColor.Red);

            // Сортуємо вхідний масив вбудованими засобами, щоб перевірити коректність сортування
            Array.Sort(array);
            // Перевіряємо, чи правильно відсортовані масиви
            if (array.SequenceEqual(array_p)) PrintColorMessage("Arrays are sorted correctly.", ConsoleColor.Green);
            else PrintColorMessage("Arrays are sorted correctly.", ConsoleColor.Red);

            // Виводимо час виконання послідовного алгоритму Шелла
            Console.WriteLine("Sequential shell sort time: " + stopwatch_s.Elapsed.TotalSeconds);

            // Виводимо час виконання паралельного алгоритму Шелла
            Console.WriteLine("Parallel shell sort time: " + stopwatch_p.Elapsed.TotalSeconds);

            // Виводимо прискорення паралельного алгоритму, порівняно з послідовним
            Console.WriteLine("Speed up: " + stopwatch_s.Elapsed.TotalSeconds / stopwatch_p.Elapsed.TotalSeconds);
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

        // Виведення повідомлення в консоль із заданим кольором
        static void PrintColorMessage(string message, ConsoleColor color)
        {
            Console.ForegroundColor = color;
            Console.WriteLine(message);
            Console.ResetColor();
        }
    }
}
