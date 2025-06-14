# JSONPlaceholder User‑API Automation Framework

This project automates API tests for the public service  
[`https://jsonplaceholder.typicode.com`](https://jsonplaceholder.typicode.com).

It focuses on two user‑related endpoints:

| Method | Endpoint                          | Purpose                     |
|--------|-----------------------------------|-----------------------------|
| **POST** | `/users`                          | Create a user (dummy create)|
| **GET**  | `/users/{id}` (`/users/1`, …)     | Retrieve a user by ID       |

The framework is built with:

- **Java 11**
- **JUnit 5**
- **REST Assured**
- **Apache Commons CSV** (data‑driven tests)
- **Extent Reports** (custom HTML reporting)

> **Note:** JSONPlaceholder is a free fake API. No authentication or tokens are required.

---

## Project Structure
src
└── test
├── java
│ └── nz.co.sundar.testautomation.jsonplaceholder
│ ├── base # TestBase
│ ├── pojo # Address, Company, CreateUserData, CreateUserReponse, Geo, GetUserData, GetUserResponse, User
│ ├── tests # CreateUserTests, GetUserTests
│ └── utils # AssertionsUtils, ConfigReader, GenericUtils, PojoUtils, ReportManager
└── resources
└── csv
├── create-user.csv # data for POST /users
└── get-user.csv # ids for GET /users/{id}


---

## How to Run the Tests

### Prerequisites
* Java 17(or 11+)
* Maven 3.8+
* IDE (IntelliJ IDEA recommended)
* Internet connection (tests hit the live JSONPlaceholder API)

### Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/sundar7733/JsonPlaceholder-API-Test-Framework.git
   cd  JsonPlaceholder-API-Test-Framework
2. **Install  dependencies** 
   Ensure you have Maven installed. Run the following command to install dependencies:
   ```bash
   mvn clean install
   ```
3. Build / run all tests
   ```bash 
   mvn clean test

### Run the tests Using IntelliJ IDEA

**For All Tests: (Open Maven tool→window→Lifecycle→test)**
1. Open the project in IntelliJ IDEA.
2. Click maven signature on the right side of the IDE.
3. Expand the `Lifecycle` section.
4. Double-click on `test` to run all tests.
5. Alternatively, you can run all tests by right-clicking on the `tests` package and selecting `Run 'Tests in 'tests''`.
   (right‑click the tests package / individual class →Run).

## Reporting
    After a test run, an HTML report is created at target/test-results/JsonPlaceholderAPITestResults.html.
    Open the file in a browser to view:

### Types of Tests
| **Test Class**               | **Description**                                                                                |
|------------------------------|------------------------------------------------------------------------------------------------|
| `CreateUserTests`            | Sends POST /users requests from create-user.csv. Validates https status and response fields    |
| `GetUserTests`               | Sends GET /users/{id} using ids from get-user.csv. Validates https status and response fields  |


### Data‑Driven Tests

| **CSV File**        | **Consumed By**     | **Columns (header)**              |
|---------------------|---------------------|-----------------------------------|
| `create-user.csv`   | `CreateUserTests`   | `testcase, title, body, userId`   |
| `get-user.csv`      | `GetUserTests`      | `testcase, id`                    |

> 📝 *Edit or extend these CSVs to add test coverage without modifying any code.*

## Branch/ Merge Strategy

Follow these steps to have your updates added to the project.

1. Create a new branch based off the main branch. A good name includes a Jira reference and brief description, e.g. `ITT-1234-get-all-users-tests` or `ITT-5678-update-user-tests`.
    ```bash
    - This will create a new branch in your local repository.
    - Make sure to pull the latest changes from the main branch before creating your new branch.
2. Commit your work. Ensure you follow appropriate conventions and the tests pass.
3. Create a pull request from your branch back to main. Add appropriate reviewers and request a code-review. Your code will be reviewed, and you may be asked to make changes for test quality.
4. Once the pull-request is approved, merge your branch into main. Ensure you delete your branch as you merge to ensure stale branches 