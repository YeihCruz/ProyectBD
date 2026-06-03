package reports;

public class MonthlyPremiumIncomeReport {

        private int year;
        private int month;
        private double monthlyIncome;

        public MonthlyPremiumIncomeReport() {
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public double getMonthlyIncome() {
            return monthlyIncome;
        }

        public void setMonthlyIncome(double monthlyIncome) {
            this.monthlyIncome = monthlyIncome;
        }
    }

