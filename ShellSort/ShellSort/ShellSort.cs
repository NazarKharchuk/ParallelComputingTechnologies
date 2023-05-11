using System;
using System.Linq;
using System.Threading.Tasks;

namespace ShellSort
{
    public class ShellSort<T> where T : IComparable<T>
    {
        // Змінна, на яку буде ділитись значення кроку d
        readonly int step_divider;

        // Конструктор
        public ShellSort(int step_divider)
        {
            this.step_divider = step_divider;
        }

        // Послідовний алгоритм сортування Шелла
        public void SequentialShellSort(T[] array)
        {
            // Зберігаємо довжину масиву в змінній
            int length = array.Length;
            // Визначаємо величину кроку (відстань між елементами, що потівнюються)
            int d = 1;
            while (d < length / step_divider)
            {
                d = step_divider * d + 1;
            }

            // Починаємо сортування з кроку d і зменшуємо його до 1
            while (d >= 1)
            {
                // Проходження масиву з кроком d
                for (int i = 0; i < d; i++)
                {
                    // Змінна для зберігання елемента при перестановці елементів
                    T key;
                    // Змінна, що зберігатиме індекс елемента, що порівнюється
                    int k;

                    // Сортування підмасиву з кроком d
                    for (int j = i + d; j < length; j += d)
                    {
                        key = array[j];
                        k = j - d;

                        // Перестановка елемента на його місце в підмасиві
                        while (k >= 0 && array[k].CompareTo(key) > 0)
                        {
                            // Перестановка елементів
                            array[k + d] = array[k];
                            k -= d;
                        }
                        array[k + d] = key;
                    }
                };

                // Зменшуємо крок для наступної ітерації
                d /= step_divider;
            }
        }

        // Паралельний алгоритм сортування Шелла
        public void ParallelShellSort(T[] array)
        {
            // Зберігаємо довжину масиву в змінній
            int length = array.Length;
            // Визначаємо величину кроку (відстань між елементами, що потівнюються)
            int d = 1;
            while (d < length / step_divider)
            {
                d = step_divider * d + 1;
            }

            // Починаємо сортування з кроку d і зменшуємо його до 1
            while (d >= 1)
            {
                // Проходження масиву з кроком d (Кожен підмасив сортується ПАРАЛЕЛЬНО іншим)
                Parallel.For(0, d, i =>
                {
                    // Змінна для зберігання елемента при перестановці елементів
                    T key;
                    // Змінна, що зберігатиме індекс елемента, що порівнюється
                    int k;

                    // Сортування підмасиву з кроком d
                    for (int j = i + d; j < length; j += d)
                    {
                        key = array[j];
                        k = j - d;

                        // Перестановка елемента на його місце в підмасиві
                        while (k >= 0 && array[k].CompareTo(key) > 0)
                        {
                            // Перестановка елементів
                            array[k + d] = array[k];
                            k -= d;
                        }
                        array[k + d] = key;
                    }
                });

                // Зменшуємо крок для наступної ітерації
                d /= step_divider;
            }
        }
    }
}
