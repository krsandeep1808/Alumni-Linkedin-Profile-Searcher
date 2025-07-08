@echo off
echo Building Alumni LinkedIn Searcher Application...
echo.

if not exist "target" mkdir target

echo Step 1: Prerequisites Check
echo - Java 17 or higher required
echo - PostgreSQL database required
echo - Maven 3.6 or higher required
echo - PhantomBuster API key required
echo.

echo Step 2: To build the project:
echo mvn clean install
echo.

echo Step 3: To run the application:
echo mvn spring-boot:run
echo.

echo Step 4: To run tests:
echo mvn test
echo.

echo Note: Please ensure Maven is installed and configured before running these commands.
pause
