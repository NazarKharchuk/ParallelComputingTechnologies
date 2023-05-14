using System;
using System.Diagnostics;
using System.Linq;

namespace ShellSort
{
    class Coursework
    {
        static void Main(string[] args)
        {
            // Змінна, що зберігає розмір масиву
            int size = 10;
            // Змінна, на яку буде ділитись значення кроку d
            int step_divider = 2;

            // Змінна, залежно від якої сортуються масив int чи об'єктів (ComparableType)
            bool is_int = false;

            if (is_int)
            {
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

                // Виводимо несортований масив
                Console.WriteLine("\tUnsorted array:");
                PrintArray(array);

                // Вимірюємо час виконання послідовного алгоритму Шелла
                Stopwatch stopwatch_s = new Stopwatch();
                stopwatch_s.Start();
                // Сортуємо масив послідовним алгоритмом Шелла
                algorithms.SequentialShellSort(array_s);
                stopwatch_s.Stop();

                // Виводимо відсортований послідовним алгоритмом Шелла масив
                Console.WriteLine("\tSorted array (Sequential):");
                PrintArray(array_s);

                // Вимірюємо час виконання паралельного алгоритму Шелла
                Stopwatch stopwatch_p = new Stopwatch();
                stopwatch_p.Start();
                // Сортуємо масив паралельним алгоритмом Шелла
                algorithms.ParallelShellSort(array_p);
                stopwatch_p.Stop();

                // Виводимо відсортований паралельним алгоритмом Шелла масив
                Console.WriteLine("\tSorted array (Parallel):");
                PrintArray(array_p);

                // Перевіряємо, чи ідентичні масиви, посортовані різними методами алгоритму Шелла
                if (array_s.SequenceEqual(array_p)) PrintColorMessage("Sorted Arrays are identical.", ConsoleColor.Green);
                else PrintColorMessage("Sorted Arrays are not identical.", ConsoleColor.Red);

                // Сортуємо вхідний масив вбудованими засобами, щоб перевірити коректність сортування
                Array.Sort(array);

                // Виводимо відсортований вбудованим засобом масив
                Console.WriteLine("\tSorted array (Build-in method):");
                PrintArray(array);

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
            else
            {
                // Генеруємо масив ComparableType розміром size
                Console.WriteLine("Array size: " + size);
                ComparableType[] array = GenerateArrayComparableType(size);

                // Створюємо клас, що містить алгоритми сортуваня масиву змінних типу ComparableType
                Console.WriteLine("Step divider: " + step_divider);
                ShellSort<ComparableType> algorithms = new ShellSort<ComparableType>(step_divider);

                // Створюємо копії згенерованого масиву, щоб посортувати їх різними методами алгоритму Шелла
                ComparableType[] array_s = new ComparableType[array.Length];
                Array.Copy(array, array_s, array.Length);

                ComparableType[] array_p = new ComparableType[array.Length];
                Array.Copy(array, array_p, array.Length);

                // Виводимо несортований масив
                Console.WriteLine("\tUnsorted array:");
                PrintArrayComparableType(array);

                // Вимірюємо час виконання послідовного алгоритму Шелла
                Stopwatch stopwatch_s = new Stopwatch();
                stopwatch_s.Start();
                // Сортуємо масив послідовним алгоритмом Шелла
                algorithms.SequentialShellSort(array_s);
                stopwatch_s.Stop();

                // Виводимо відсортований послідовним алгоритмом Шелла масив
                Console.WriteLine("\tSorted array (Sequential):");
                PrintArrayComparableType(array_s);

                // Вимірюємо час виконання паралельного алгоритму Шелла
                Stopwatch stopwatch_p = new Stopwatch();
                stopwatch_p.Start();
                // Сортуємо масив паралельним алгоритмом Шелла
                algorithms.ParallelShellSort(array_p);
                stopwatch_p.Stop();

                // Виводимо відсортований паралельним алгоритмом Шелла масив
                Console.WriteLine("\tSorted array (Parallel):");
                PrintArrayComparableType(array_p);

                // Перевіряємо, чи ідентичні масиви, посортовані різними методами алгоритму Шелла
                if (array_s.SequenceEqual(array_p)) PrintColorMessage("Sorted Arrays are identical.", ConsoleColor.Green);
                else PrintColorMessage("Sorted Arrays are not identical.", ConsoleColor.Red);

                // Сортуємо вхідний масив вбудованими засобами, щоб перевірити коректність сортування
                Array.Sort(array);

                // Виводимо відсортований вбудованим засобом масив
                Console.WriteLine("\tSorted array (Build-in method):");
                PrintArrayComparableType(array);

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

        // Створення масиву ComparableType та заповнення його випадковими значеннями
        static ComparableType[] GenerateArrayComparableType(int size)
        {
            Random random = new Random();
            ComparableType[] array = new ComparableType[size];

            int Id;
            string Name;
            double Value;

            for (int i = 0; i < size; i++)
            {
                Id = random.Next(1, 10); // Встановлюємо випадкове значення для поля Id
                Name = "Object " + i.ToString(); // Встановлюємо назву Name об'єкта
                Value = random.NextDouble() * 10; // Встановлюємо випадкове значення для поля Value
                ComparableType obj = new ComparableType(Id, Name, Value);
                array[i] = obj;
            }

            return array;
        }

        // Виведення масиву ComparableType в консоль
        static void PrintArrayComparableType(ComparableType[] array)
        {
            for (int i = 0; i < array.Length; i++)
            {
                array[i].Print();
            }
        }
    }
}
