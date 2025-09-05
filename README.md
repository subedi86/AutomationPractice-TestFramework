# AutomationPractice Test Framework

This is a **Selenium Java Test Automation Framework** built using **Page Object Model (POM)**, **JUnit**, **Maven**, and **Allure Reports**.  
It is designed for automation practice websites and can be easily extended to other web applications.

---

## 🚀 Features
- Page Object Model (POM) for clean test structure
- JUnit for test execution and assertions
- Maven for dependency management
- Data-driven testing using `.properties` files
- Allure Reports for rich test reporting
- Configurable browser setup with `DriverFactory`
- Utilities for config and test data handling

---

## 📂 Project Structure
AllInOne
┣ src/main/java
┃ ┣ base (BasePage, DriverFactory)
┃ ┣ pages (HomePage, AutomationPracticePage)
┣ src/test/java
┃ ┣ tests (AutomationPracticeTest)
┃ ┣ utils (ConfigReader, TestDataReader)
┣ src/test/resources
┃ ┣ config.properties
┃ ┣ testdata.properties
┣ pom.xml

---

## ⚙️ Tools & Tech Stack
- Java 17  
- Selenium WebDriver  
- JUnit  
- Maven  
- Allure Reports  

---

## ▶️ How to Run Tests
1. Clone this repo:
   ```bash
   git clone https://github.com/subedi86/AutomationPractice-TestFramework.git
Navigate into the project:

cd AutomationPractice-TestFramework


Run tests with Maven:

mvn clean test


Generate Allure report:

mvn allure:serve
📊 Reports

JUnit default reports available under target/surefire-reports/

Allure HTML reports generated via Maven

🤝 Contribution

Feel free to fork this project and add more test cases or improvements. PRs are welcome!

👤 Author

Lok Subedi
📧 lifeintestcases.com

💼 LinkedIn
