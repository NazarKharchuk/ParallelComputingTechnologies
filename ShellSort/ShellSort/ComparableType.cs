using System;

namespace ShellSort
{
    // Клас ComparableType, що містить метод CompareTo
    public class ComparableType : IComparable<ComparableType>
    {
        // Чіле число - Id
        public int Id { get; set; }
        // Рядкове значення - Name
        public string Name { get; set; }
        // Дійсне число - Value
        public double Value { get; set; }

        // Конструктор класу ComparableType
        public ComparableType(int Id, string Name, double Value)
        {
            this.Id = Id;
            this.Name = Name;
            this.Value = Value;
        }

        // Реалізація метода CompareTo
        public int CompareTo(ComparableType other)
        {
            // Якщо порівнюваний об'єкт є null, тому повертаємо 1, що означає, що поточний об'єкт більший.
            if (other == null)
                return 1;

            // Порівнюємо Id поточного об'єкта з Id іншого об'єкта.
            int result = Id.CompareTo(other.Id);

            // Якщо Id однакові, переходимо до порівняння Name.
            if (result == 0)
            {
                // Порівнюємо Name поточного об'єкта з Name іншого об'єкта.
                result = Name.CompareTo(other.Name);

                // Якщо Name також однакові, переходимо до порівняння Value.
                if (result == 0)
                {
                    // Порівнюємо Value поточного об'єкта з Value іншого об'єкта.
                    result = Value.CompareTo(other.Value);
                }
            }

            // Повертаємо результат порівняння, який вказує, чи є поточний об'єкт меншим, більшим або рівним іншому об'єкту.
            return result;
        }

        // Метод, що виводить дані об'єкта ComparableType
        public void Print()
        {
            Console.WriteLine("Id: " + Id + ", Name: " + Name + ", Value: " + Value);
        }
    }
}
