# Automated test POM website Ozone

Creates the following automated test scenarios for the site https://www.ozone.bg/

- Successfully logged into the system - requires some manual clicks for the recaptcha frame and the submit button, also in the User csv file put your credentials!
- Failed to log into the system 
- Adding products (at least two per choice - to be given as parameters) in quantities



1. Page Object Model 
2. The project supports two browsers - Chrome and Firefox (from configuration file) 
3. The url selection is given as a configuration 
4. Every driver closes after completing the test
5. Each test is in a separate class 
6. Parallel performance of tests (at least two parallel)
7. Tests given are observed (and read from) csv file 
8. Implicit wait
9. Explicit wait 
10. Asserts 
