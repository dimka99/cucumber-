Feature: Check correct errror message for invalid user name and password
Description: The purpose of the test to execute some scenarios

Scenario: User vist the site and enter invalid user name and correct password
Given user open login page
When enter invalid user name and valid password and cick login

Scenario: User vist the site and enter correct user name and invalid password
Given user open login page 
When enter valid user name and invalid password and cick login

Scenario: User vist the site and enter invalid user name and invalid password
Given user open login page 
When enter invalid user name and invalid password and cick login

Scenario: User vist the site and enter valid user name and valid password
Given user open login page 
When enter valid user name and valid password and cick login

Scenario: User vist the site and press login without entering username and password
Given user open login page
When and press login without entering username and password

Scenario: User vist the site and press login without entering username and with password
Given user open login page
When and press login without entering username and with password

Scenario: User vist the site and press login without entering password and with username
Given user open login page
When and press login without entering password and with username
