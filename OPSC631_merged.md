IIE Module Outline                 
PROG7314 
PROGRAMMING 3D 
PROG7314 
MODULE OUTLINE 2026 
This document enjoys copyright under the Berne Convention. In terms of the Copyright Act, no 
98 of 1978, no part of this document may be reproduced or transmitted in any form or by any 
means, electronic or mechanical, including photocopying, recording or by any other 
information storage and retrieval system without permission in writing from the proprietor. 
The Independent Institute of Education (Pty) Ltd is registered with the 
Department of Higher Education and Training as a private higher education 
institution under the Higher Education Act, 1997 (reg. no. 2007/HE07/002). 
Company registration number: 1987/004754/07. 
© The Independent Institute of Education (Pty) Ltd 2026  
Page 1 of 15 
IIE Module Outline                 PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026  Page 2 of 15 
Table of Contents 
 
Introduction ............................................................................................................................... 3 
Using this Module Outline .......................................................................................................... 4 
Module Resources ...................................................................................................................... 5 
This Module on Arc .................................................................................................................... 7 
Module Purpose ......................................................................................................................... 8 
Module Outcomes ...................................................................................................................... 8 
Assessments ............................................................................................................................... 9 
Module Pacer ........................................................................................................................... 11 
Glossary of Key Terms for this Module .................................................................................... 15 
 
 
  
IIE Module Outline                 
PROG7314 
Introduction 
Welcome to Programming 3D. This module follows the Programming 3C module that you have 
already completed. We will still be making use of Kotlin to build native Android apps, but here 
we will be introducing some more advanced concepts such as interacting with web services 
and making use of libraries and software development kits. 
Throughout this module, you will create several apps to master the intermediate skills needed 
to build an Android app. It is important to get hands-on experience in any programming 
module, so it is essential that you complete all the activities provided on Learn. 
We hope you will enjoy the module and take the opportunity to use the knowledge and 
experience gained in both future modules and in your career. 
Figure 1. Module Structure 
© The Independent Institute of Education (Pty) Ltd 2026  
Page 3 of 15 
IIE Module Outline                 
PROG7314 
Using this Module Outline 
A Module Outline is a brief summary of the module and is given to you to support your 
learning. 
The content of this module is on Arc as well as in the prescribed material. You will not 
succeed in this module if you focus on this document alone. 
To succeed in this module, you will need to: - - - 
Attend lectures/ online sessions;  
Go through the material and activities on Arc;  
Read the prescribed material. 
Your lecturer will decide when activities are available/open for submission and when these 
submissions or contributions are due. Ensure that you take note of announcements made 
during lectures and/or posted in the Student Portal and within Arc in this regard. - 
Your campus will provide you with details about when your assessments are due. 
© The Independent Institute of Education (Pty) Ltd 2026  
Page 4 of 15 
IIE Module Outline                 PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026  Page 5 of 15 
Module Resources 
Prescribed Material (PM) for 
this Module 
Programming 3D Module Manual [PM1] 
 
GitHub source code repository for the module [PM2] 
https://github.com/iie-opsc/opsc7312kotlin 
Recommended Readings, 
Digital, and Web Resources 
(RM) 
Please note that a number of additional resources and links 
to resources are provided throughout this module on the 
Arc platform. You are encouraged to engage with these as 
they will assist you in mastering the various objectives of 
this module. They may also be useful resources for 
completing assignments. You will not, however, be assessed 
under examination conditions on any additional or 
recommended reading material. 
 
RM1: Karanpuria, R. and Roy, A.S. (2018) Kotlin 
Programming Cookbook: Explore More Than 100 Recipes 
That Show How to Build Robust Mobile and Web 
Applications with Kotlin, Spring Boot, and Android. 
Birmingham, UK: Packt Publishing. Available at: 
https://search-ebscohost
com.ezproxy.iielearn.ac.za/login.aspx?direct=true& 
db=e000xww&AN=1699229&site=ehost-live&scope=site 
(Accessed: 29 December 2022). 
 
RM2: Smyth, N. (2020) Android Studio 3.6 Development 
Essentials - Kotlin Edition: Developing Android 10 (Q) Apps 
Using Android Studio 3.6, Kotlin and Android Jetpack. 
Payload Media, Inc. Available at: https://search-ebscohost
com.ezproxy.iielearn.ac.za/login.aspx?direct=true& 
db=e000xww&AN=2643704&site=ehost-live&scope=site  
(Accessed: 29 December 2022). 
Software required  Latest Version of Android Studio (with the Android 8.0 
Oreo SDK installed) 
 
Java SE 7 or later Software Development Kit (SDK) 
Software Licence 
requirements 
Open Source – Download the latest version of Android 
Studio from: https://developer.android.com/studio  
and the Java SDK from: 
http://www.oracle.com/technetwork/java/javase/ 
downloads/index.html 
IIE Module Outline                 
PROG7314 
System Requirements 
 
 
Microsoft Windows 8/10 (64-bit) 
8 GB RAM recommended (plus 1 GB for the Android 
Emulator) 
 
 
Lab minimum requirements 
8 GB of available disk space minimum 
1280 x 800 minimum screen resolution 
Same as above. 
Lab configuration settings 
 
 
Microsoft Windows 8/10 (64-bit) 
8 GB RAM recommended (plus 1 GB for the Android 
Emulator) 
 
8 GB of available disk space minimum 
 
Module Overview 
1280 x 800 minimum screen resolution 
You will find an overview of this module on Arc under the 
Module Information link. 
Assessments  
Find more information on this module’s assessments in 
this document and on the Student Portal. 
© The Independent Institute of Education (Pty) Ltd 2026  
Page 6 of 15 
IIE Module Outline                 
PROG7314 
This Module on Arc 
Arc is an online space, designed to support and maximise your learning in an active manner. 
Its main purpose is to guide and pace you through the module. In addition to the information 
provided in this document, you will find the following when you access Arc: 
 
 
 
 
 
A module overview; 
A list of prescribed material; 
Critical questions to guide you through the module’s objectives; 
A variety of additional online resources (articles, videos, audio, interactive graphics, etc.) 
in each learning unit that will further help to explain theoretical concepts; 
Collaborative and individual activities with time-on-task estimates to assist you in 
managing your time around these. 
Kindly note: 
 
 
 
Unless you are completing this as a distance module, Arc does not replace your 
contact time with your lecturers and/or tutors. 
This module is an Arc module, and as such, you are required to engage extensively 
with the content on the Arc platform. Effective use of this tool will provide you with 
opportunities to discuss, debate, and consolidate your understanding of the content 
presented in this module. 
You are expected to work through the learning units on Arc in your own time – 
especially before class. Any contact sessions will therefore be used to raise and 
address any questions or interesting points with your lecturer, and not to cover 
every aspect of this module. 
 
Your lecturer will communicate submission dates for specific activities in class and/or 
on Arc. 
REMEMBER: 
You need to log onto Arc to: 
 
 
Access the learning material and online resources such as articles, interactive 
graphics, explanations, video clips, etc. which will assist you in mastering the content; 
View instructions and submit or post your contributions to individual or group 
activities which are managed and tracked on Arc; and 
 
Submit assessment documents. 
© The Independent Institute of Education (Pty) Ltd 2026  
Page 7 of 15 
IIE Module Outline                 
PROG7314 
Module Purpose 
The purpose of this module is to provide students with further knowledge in well-designed; 
well coded; and business oriented open source software development for mobile devices. 
Module Outcomes 
MO1  Demonstrate knowledge and understanding of designing open source mobile 
device software for business. 
MO2  Apply various open source development techniques to develop stable well 
tested open source software for mobile devices. 
MO3  Develop mobile open source software that meets business requirements. 
MO4  Demonstrate how the developed mobile application meets business 
requirements. 
© The Independent Institute of Education (Pty) Ltd 2026  
Page 8 of 15 
IIE Module Outline                 
PROG7314 
Assessments 
Integrated Curriculum Engagement (ICE) 
Minimum number of ICE activities to complete 
4 
Weighting towards the final module mark 
10% 
Formatives 
Part 1  
Part 2 
Weighting 
Duration 
25% 
30% 
10 hours  
Write/Submit after 
10 hours  
LU2 
Learning Units covered 
LU3 
LU1  
Resources required 
LU1 to 2 
Prescribed Material 
Prescribed Material 
Summative 
POE 
Weighting 
35% 
Duration 
15 Hours 
Total marks 
100 
Open/Closed book 
Open  
Resources required 
Prescribed Material 
Learning Units covered 
All  
© The Independent Institute of Education (Pty) Ltd 2026  
Page 9 of 15 
IIE Module Outline                 PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026  Page 10 of 15 
Assessment Preparation Guidelines 
Format of the Assessment Preparation Hints 
Part 1 
This assessment will assess 
your understanding of 
Learning Unit 1 of this 
module and will consist of 
one application for a simple 
mobile application. You will 
be working in a team and 
are expected to create an 
application as per your 
objectives for these 
learning units. 
 Ensure that you work through all the relevant activities, 
exercises and revision questions on Arc and in your 
textbook. 
 Brainstorm possible programs based on the learning 
outcomes and objectives provided. 
 Pay attention to the instructions and to the mark 
allocations of each question to ensure that you are able 
to meet the requirements. 
 Make sure that you have mastered the objectives in 
Learning Unit 1. 
Part 2 
The task will assess you and 
your team’s ability to 
integrate and apply the 
content in Learning Units 1 
to 2 of this module to build 
on the app you created in 
Task 1. 
 Read through the prescribed chapters and content for 
Learning Units 1 to 2 and ensure that you have engaged 
before you proceed with your coding. 
 Remember to analyse all elements required and ensure 
that your task meets the requirements. 
 Improve the quality of your task by using the provided 
rubric and addressing any areas of concern prior to 
submitting it for marking. 
Portfolio of Evidence (PoE) 
The PoE will consist of Part 
1, Part 2, and further 
activities to complete the 
PoE. All learning units will 
be assessed in the PoE, and 
reflection on your learning 
will be included. 
 Ensure that you work through all the activities, exercises 
and revision questions on Learn and consult your 
textbook. 
 Include the tasks as submitted, together with your 
lecturer’s feedback and your corrected tasks based on the 
feedback received. 
 Include the reflection of your learning (each member to 
submit this). 
 Complete other activities included in the PoE. 
  
IIE Module Outline                 PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026  Page 11 of 15 
Module Pacer 
Module Code Programme Contact 
Sessions 
Credits and 
notional time 
PROG7314 BCA3, BCGD3, DISD3 72 15 
(150 notional 
hours) 
Learning Unit 1 Web Services 
 
Overview: 
 
Web services are available online that provide access to a variety of different types of data. 
In this learning unit, you will learn how to consume web services in an Android app. We will 
make use of RESTful web services presenting data in JavaScript Object Notation (JSON) 
format. And finally, we will look at reusable user interface components called fragments. 
 
The challenge you may experience in this learning unit relates to parsing information 
returned by a web service. Refer to the module manual for assistance. 
 
Learning Unit 1: Theme Breakdown 
Sessions:  
1 – 24 
Theme 1: Connecting to a Web Service Prescribed Material (PM)  
Related Module 
Outcomes: 
MO1 
MO2 
MO3 
LO1: Explain how to code a Hypertext 
Transfer Protocol (HTTP) connection 
from scratch. 
LO2:  Explain how to connect to a RESTful 
Application Programming Interface 
(API). 
LO3:  Use a RESTful API in an Android app. 
PM1: Learning Unit 1 
PM2: LearningUnit1 
folder 
Theme 2: JSON Files and JSON Objects 
LO4:  Explain the purpose of JSON files. 
LO5:  Use JSON objects to read data. 
LO6:  Use JSON objects to write data. 
LO7:  Use a library to work with JSON data. 
Theme 3: Fragments 
LO8:  Explain the purpose of using 
fragments in an Android app. 
LO9:  Apply fragments in an Android app. 
  
IIE Module Outline                 PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026  Page 12 of 15 
Learning Unit 2 External Libraries 
 
Overview: 
 
In this learning unit, we will work through the process of developing a mobile application 
that uses external libraries, geolocation services, and social media services. Additionally, you 
will explore how to select and incorporate Software Development Kits (SDKs) in your 
application. You will learn to make use of tabs and fragments as well. 
 
The challenge you may experience in this learning unit relates to incorporating external 
libraries into an app. Refer to the module manual for tips and tricks. 
 
Learning Unit 2: Theme Breakdown 
Sessions: 
25 – 42 
Theme 1: Connect to External Libraries Prescribed Material (PM)  
Related Module 
Outcomes: 
MO1 
MO2  
MO3 
LO1:  Determine which external libraries 
to use. 
LO2:  Use an external library to solve a 
programming problem. 
PM1: Learning Unit 2 
PM2: LearningUnit2 folder 
Theme 2: Using Geolocation Services 
LO3:  Connect to a geolocation service. 
LO4:  Display information from a 
geolocation service. 
Theme 3: Adding Social Media Services 
LO5:  Connect to a social media service. 
LO6:  Use a social media service. 
Theme 4: Working with SDKs 
LO7:  Determine which SDKs are available 
to connect to an app to. 
LO8:  Connect an app to an appropriate 
SDK. 
  
IIE Module Outline                 PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026  Page 13 of 15 
Learning Unit 3 Using Databases 
 
Overview: 
 
In this learning unit, you will create apps that store data in different databases. We will start 
by looking at SQLite – an embedded database that makes use of Structured Query Language 
(SQL). Then we will work through the process of developing an app that uses online 
authentication services as well as connecting to a NoSQL database. 
 
The challenge you may experience in this learning unit relates to using SQLite databases. 
Refer to the module manual for guidance. 
 
Learning Unit 3: Theme Breakdown 
Sessions: 
43 – 60 
Theme 1: SQ Lite Database Prescribed Material (PM)  
Related 
Module 
Outcomes: 
MO1  
MO2  
MO3 
LO1:  Create an SQLite Database. 
LO2:  Use an SQLite Database to store data. 
LO3:  Use an SQLite database to read data. 
PM1: Learning Unit 3 
PM2: LearningUnit3 
folder 
Theme 2: NoSQL Database 
LO3:  Use an authentication service. 
LO4:  Use blob storage. 
LO5:  Use a NoSQL database to store data. 
LO6:  Use a NoSQL database to read data. 
  
IIE Module Outline                 PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026  Page 14 of 15 
Learning Unit 4 App Publication 
 
Overview: 
 
This learning unit works through the process of publishing and monetizing your mobile 
application via the Google Play Store. It starts with the basics of building an Android Package 
(APK) for deployment to a mobile device and then looks further at the process of creating 
signed and unsigned bundles. Finally, it looks at how to deploy your app to the Play Store 
and how to keep track of the performance of your app. 
 
The challenge you may experience in this learning unit relates to preparing an app for 
publication in the Google Play Store. Read the module manual for tips and tricks to make the 
process as easy as possible. 
 
Learning Unit 4: Theme Breakdown 
Sessions: 
61 – 72 
Theme 1: APKs and Bundles Prescribed Material (PM)  
Related 
Outcomes: 
MO2 
MO4  
LO1:  Explain the purpose of generating an 
APK. 
LO2:  Create an APK from an existing 
project. 
LO3:  Explain the difference between an 
APK and a bundle. 
LO4:  Generate signed and unsigned 
bundles. 
PM1: Learning Unit 4 
Theme 2: Deploying to the Play Store 
LO5:  Describe how to deploy an app to 
the Play Store. 
L06:  Prepare an app for publication in the 
Play Store. 
Theme 3: Track Analytics and Usage 
LO7:  Explain the purpose of analytics 
tracking. 
LO8:  Discuss how to track analytics on the 
Play Store. 
  
IIE Module Outline                 
PROG7314 
Glossary of Key Terms for this Module 
Term 
Definition 
Application Programming Interface. 
API 
APK 
Android Package. 
Converting a string into an object. 
Deserialization  
Fragment  
GSON  
A small, reusable piece of code in Android development.  
A library used to manipulate JSON. 
HTTP 
Hypertext Transfer Protocol. 
Integrated Development Environment.  
IDE 
JSON  
JavaScript Object Notation. 
Software Development Kit. 
SDK 
Serialisation  
Converting an object into a saveable string.  
Structured Query Language. 
SQL 
Web Service  
An API that is available via an online connection.  
© The Independent Institute of Education (Pty) Ltd 2026  
Page 15 of 15 
25; 26                                
2026 
MODULE NAME: 
MODULE CODE: 
OPEN SOURCE CODING (INTERMEDIATE) 
OPSC6312/p/w 
ASSESSMENT TYPE: POE (PAPER AND MARKING RUBRIC) 
TOTAL MARK ALLOCATION: 300 MARKS 
TOTAL HOURS: A MINIMUM OF 35 HOURS IS SUGGESTED TO COMPLETE THIS ASSESSMENT 
By submitting this PoE, you acknowledge that you have read and understood all the rules as per the 
terms in the registration contract, in particular the assignment and assessment rules in The IIE 
Assessment Strategy and Policy (IIE009), the intellectual integrity and plagiarism rules in the 
Intellectual Integrity Policy (IIE023), as well as any rules and regulations published in the student 
portal. 
INSTRUCTIONS: 
1. 
2. 
3. 
4. 
No material may be copied from original sources, even if referenced correctly, unless it is 
a direct quote indicated with quotation marks. No more than 10% of the assignment may 
consist of direct quotes. 
Please ensure that you submit your assignment through Turnitin. Please make sure you 
attach a similarity report to your POE if you are required to submit a hard copy of your 
PoE. 
Make a backup of your assignment before handing it in. 
Follow all instructions on the PoE cover sheet. 
5. 
This is a group assignment. 
© The Independent Institute of Education (Pty) Ltd 2026 
Page 1 of 25 
25; 26                                2026 
© The Independent Institute of Education (Pty) Ltd 2026 
Page 2 of 25 
Referencing Rubric                                                                                                                                                      
Providing evidence based on valid and referenced academic sources is a 
fundamental educational principle and the cornerstone of high-quality 
academic work. Part of achieving this quality is referencing in a way that is 
consistent and congruent with the requirements of the referencing style being 
used.  
 
Therefore, inconsistent and/or incongruent referencing will result in a penalty 
of a maximum of ten percent being deducted from the overall percentage 
awarded to your assessment submission. 
  
Please note that evidence of plagiarism in the form of copied or unreferenced 
work, absent reference lists, or exceptionally poor referencing may result in 
action being taken in accordance with The IIE’s Intellectual Integrity and 
Property Rights Policy (IIE023). Similarly, evidence of excessive AI usage may 
result in action being taken in accordance with The IIE’s Student Conduct, 
Discipline and Safety Policy (IIE015). 
Markers are required to provide feedback to students by 
circling/underlining the information in the table below that best describes 
the student’s work and by adding constructive commentary where 
appropriate. The examples provided are not exhaustive but illustrate the 
errors. 
 
Deductions 
 Where the student’s work contains five or more errors aligned to the 
minor errors column below, deduct 5% from the overall percentage.  
 
 Where the student’s work contains five or more errors aligned to the 
major errors column below, deduct 10% from the overall percentage.  
 
 Where both minor and major errors (e.g. two minor and three major, 
etc.) are present, deduct 10% only (and not 5% or 15%) from the overall 
percentage.  
Required:  
Consistent and congruent 
referencing  
Minor errors  
Deduct 5% from overall percentage. 
Example: if the response receives 70%, deduct 5%. The 
final mark is 65%. 
Major errors  
Deduct 10% from the overall percentage. 
Example: if the response receives 70%, deduct 10%. 
The final mark is 60%. 
Consistency  
 The correct referencing style 
for the discipline – i.e., either 
Harvard, OR APA (for 
Psychology), OR Law, OR IEEE 
(for ICT/Engineering) – has 
been used consistently for all 
in-text references and in the 
bibliography/reference list. 
 
 Concepts and ideas that are 
quoted and/or paraphrased 
are referenced consistently 
throughout. 
 
 Position of the in-text 
reference: an in-text 
reference is positioned 
consistently where 
appropriate for every quote 
and paraphrase.  
 
Minor inconsistencies: 
 The referencing style used is generally consistent with 
what is required, but there are one or two 
changes/errors in the format of in-text referencing 
and/or in the bibliography/reference list.  
 
 For example, page numbers for direct quotes in-text 
have been provided for one source, but not in another. 
Or, two book chapters in the bibliography/reference 
list have been referenced in two different formats. Or, 
the publication year has been placed after the author 
name in one bibliography/reference list entry, and 
after the source title in another, etc.  
 
 Concepts and ideas in quotes and/or paraphrases are 
typically referenced, but a full in-text reference is 
missing or incomplete from one or two small sections 
of the work.  
 
 Position of the references: in-text references are only 
given at the beginning and/or end of every paragraph. 
Major inconsistencies: 
 Poor and wholly inconsistent referencing style used 
in-text and/or in the bibliography/reference list. 
 
 Multiple referencing styles for the same source 
types have been used. 
 
 For example, the format for direct quotes in-text 
and/or book chapters in the bibliography/reference 
list and/or year of publication in the 
bibliography/reference list is different across 
multiple instances.  
 
 Concepts and ideas in quotes and/or paraphrases 
are haphazardly referenced in-text.  
 
 Position of the references: in-text references are 
only given at the beginning or end of large sections 
of work.  
Feedback on referencing consistency: 
 
 
 
Congruency  
 Each source reflected within 
in-text references is included 
accurately in the 
bibliography/reference list. 
 
 All bibliography/reference list 
entries are in the required 
order for the referencing style 
used (e.g. alphabetical, 
alphabetical under 
subheadings, numerical). 
 
 All direct quotes and 
paraphrases have been 
integrated appropriately into 
the text using introductory 
phrases, accurate grammar, 
etc.  
 
Minor incongruences:  
 There is largely a match between the sources 
presented in-text and those in the 
bibliography/reference list, but one or two sources 
that appear in-text do not appear in the 
bibliography/reference list, or vice versa. Or key source 
information is missing from one or two in-text 
references or bibliography/reference list entries only 
(e.g. publication year, city of publication, URL date 
accessed, etc.). 
 
 There is a clear and largely accurate ordering of 
sources in the bibliography/reference list as required 
by the referencing style used, but with one or two 
references out of order.  
 
 An attempt has been made for source integration into 
the text using appropriate introductory phrases and 
grammar, but one or two quotes or paraphrases do 
not flow as clearly or logically within the sentence 
structure as they could. 
Major incongruences: 
 No relationship/several incongruencies between 
the in-text referencing and the 
bibliography/reference list.  
 
 For example, multiple sources are included in-text, 
but not in the bibliography, and/or vice versa. Key 
source information is missing from multiple in-text 
references and/or reference list entries. A URL link, 
rather than the actual reference, is provided in the 
bibliography. Sources are repeated in the reference 
list, etc.  
 
 Most sources are listed in a haphazard order 
throughout the bibliography/reference list. 
 
 Few to no appropriate introductory phrases or 
rules of grammar have been applied, and many 
direct quotes and/or paraphrases feel disconnected 
from the flow of the text.  
  
Feedback on referencing congruency: 
 
 
 
Overall feedback on referencing, with suggested improvements: 
25; 26                                2026 
© The Independent Institute of Education (Pty) Ltd 2026 
Page 3 of 25 
Background 
This portfolio of evidence comprises two parts submitted during the semester, and the 
finalisation of your PoE, each of which takes you through conceptualising, designing, and 
building a mobile application. 
 
You are going to create an app of your choice for a change. The app must make use of an API 
that you find online, or you are welcome to create it yourself, utilising the knowledge learned in 
PROG6212 and APPR6312. The app must include the stated features described in the 
introduction section below. 
 
Finally, the app will need to be prepared so that it can be published on the Google Play Store, as 
well as making use of unit testing and GitHub Actions for running the necessary tests. 
 
To be able to create the app, you will need to use all the techniques you learned during this 
course, as well as do your research on how to complete some of the tasks required of you. It is 
also critical that you approach the project in a methodical and planned manner. For this reason, 
the project will follow a Research – Plan – Design – Build – Evaluate cycle: 
 
 
This PoE will run for one full cycle. After one full cycle, you can always restart the process to further 
refine your app. 
 
The different phases of the cycle will be related to the different tasks as follows: 
Part 1: Research, Planning and Design 
Part 2: Build 
Final PoE: Build and Evaluate 
 
Research
Plan
Design Build
Evaluate
25; 26                                2026 
© The Independent Institute of Education (Pty) Ltd 2026 
Page 4 of 25 
Instructions  
 
Each of the following parts is built on top of the other. Be sure to complete or update one part 
before moving on to the next. Each part will be individually assessed by your lecturer, and 
feedback for each part will be provided. 
 
The exact features, design, and layout of your app are up to you, but each app must be able to 
complete at least the following features: 
 
 The user must be able to register and log in to the app. Make sure to encrypt the password 
 Make use of single sign-on (SSO) (PoE only) 
 The user must be able to change their settings in the app. 
 Connect to a REST API you create that is connected to a database or use any storage 
mechanism that fits the idea you have, that exists on the internet. 
 Offline mode with Sync: Create a feature that allows users to perform offline actions with 
synchronisation capabilities once they reconnect. This does not need to be the whole app, 
but at least some features. Can make use of RoomDB or SQL Lite (PoE only) 
 Implementation of Real-time Notification: Implement a push notification system for real
time updates and alerts (PoE only) 
 Multi-language support that includes at least two South African Languages (PoE only) 
 
These features are the minimum that is required. Your app also needs to work logically and be 
easy to use. Speak to your lecturer about how your app can best implement all these features. 
 
  
25; 26                                2026 
© The Independent Institute of Education (Pty) Ltd 2026 
Page 5 of 25 
Part 1 — Research, Planning and Design                                                                                (Marks: 100) 
 
Learning Unit 1 
At the end of this specific part, students should be able to: 
 Explain how to connect to a RESTful Application Programming Interface (API). 
 Define the features that the app will include. 
 Define the use case of their app. 
 
This part is composed of two sections, weighted equally: 
 Research 
 Planning and design 
 
Research                                                                                                                                          (Marks: 50) 
 
For this part, you are required to research three existing apps that are like the one you want to 
create and are available on the Android operating system. You will need to present your findings 
in the form of a report (1000 to 1500 words) with the following sections: 
 
 Introduction 
 Research on each app, including: 
o Overview of the app 
o Strengths and weaknesses of the app 
o How you think the app was implemented based on your knowledge of Android Studio 
app development 
o Screenshots of the app 
 A comparison of all three apps 
 A list of the best features of all the apps that you want to use in your final app 
 Conclusion 
 References 
 
The information must be submitted in the form of a report. Make sure you reference this part 
correctly. 
 Make sure that the size of the PDF file does not exceed 10 MB. 
25; 26                                2026 
© The Independent Institute of Education (Pty) Ltd 2026 
Page 6 of 25 
 Please note that if you don’t find an app that matches exactly, then look for one that 
matches at least some of the features. 
 
Planning and Design                                                                                                                     (Marks: 50) 
 
For this part, you will need to design your app fully. The purpose of this part is to ensure you 
know exactly what you need to build and how you will build it before you start with Part 2. You 
will need to present your design as a typed PDF document (1000 to 1500 words) with the 
following sections: 
 Introduction. 
 A brief overview of the app, including a name for the app, an initial icon design, and a 
description of the innovative features that you are planning to include. 
 A detailed list of the requirements for the app. 
The requirements must include all the minimum requirements from this document, but 
you must elaborate on each of those requirements rather than just copying them. 
Remember to add the features that you want to include, for example, gamification. The 
requirements must be detailed enough for someone else to be able to build your app by 
just reading this document! You need to make these clear since you will be marked on 
these for Part 2 and the PoE. 
 User interface design, including a mockup for each screen, a description of the purpose of 
the screen, and a diagram showing how the user navigates between the various screens. 
 Design what your API will do for your application and what data it will send and receive. 
Also, state how you are going to create your API and what hosting you will be using. If you 
have found an existing API you would use, please state this here and how best to use it. You 
will also need to state what database you would use with it, for example, Firebase. 
 UML diagrams that include how the API is connected to other systems in the application. 
 Detailed listing of the data that the app needs to capture from the user and store, including 
data types. 
 Project plan detailing deadlines and milestones for the project in the format of a Gantt 
chart. Break down the tasks into smaller tasks than just “implement the prototype”. Include 
some time for testing and bug fixing. 
 Conclusion. 
 References. 
 
25; 26                                
2026 
Submit the following for Part 1: 

 
Research Report and Planning and Design Document in PDF format. 
If applicable, a short write-up (maximum 500 words) on the manner in which AI tools were 
used and cited during the completion of this assessment. Examples could include code 
snippets, debugging errors, image generation, etc. 
© The Independent Institute of Education (Pty) Ltd 2026 
Page 7 of 25 
25; 26                                2026 
© The Independent Institute of Education (Pty) Ltd 2026 
Page 8 of 25 
Part 2 — App Prototype Development                                                                                    (Marks: 100) 
 
Learning Units 1 and 2 
At the end of this specific part, students should be able to: 
 Use a RESTful API in an Android app. 
 Use an external library to solve a programming problem. 
 Connect an app to an appropriate SDK. 
 Conduct detailed unit testing. 
 
For this part, you will need to build a fully working prototype. This prototype needs to include all 
the features listed in the instructions section of this document (except for the requirements 
marked for the PoE), based on your own design and user interface layout. The app must compile 
and run for marks to be awarded for the app’s features. 
Some of the features specified in your design document can be deferred until the final PoE 
submission, but you need to include at least a few of them for Part 2. And final assets (for 
example, images) do not need to be included in the prototype. 
 
You must create a demonstration video showing the prototype running on your mobile phone. For 
the video, make sure that you show the app performing the following functions: 
• The user must be able to register and log in to the app, and encrypt the password of the 
user. 
• The user must be able to change their settings in the app. 
• Connect to a REST API you create that is connected to a database, or use any that fits your 
idea that exists on the internet. Remember, this will need to be hosted to connect to your 
application. You may use anything for this. 
• Your features that were specified in Part 1. Look at the rubric to see the weighting. 
 
Include in the demonstration video that will effectively demonstrate all the features of the app 
and be fully professional. 
The video must include a voice-over explaining what you are showing. The video must also show 
what data is stored in the online-hosted authentication service, API, and database. 
The app must have a user-friendly and appropriate user interface that can handle invalid inputs 
made by the user without crashing. The app must work with only minor bugs and errors. 
 
25; 26                                2026 
© The Independent Institute of Education (Pty) Ltd 2026 
Page 9 of 25 
Version Control with GitHub: Initialise the repository with a README file. Commit and push your 
project files to the GitHub repository. Regularly commit and push your code as you make 
progress. 
 
Conduct automated testing on the main functionality of your app. Make use of GitHub Actions to 
run tests and build your code to make sure it will work on not just your computer. Use this guide, 
which will assist in creating this: 
 https://github.com/marketplace/actions/automated-build-android-app-with-github-action 
[Accessed on 03 November 2025]. 
 https://github.com/IMAD5112/Github-actions/blob/main/.github/workflows/build.yml  
[Accessed on 03 November 2025]. 
 
Documentation: 
Prepare a comprehensive report detailing the purpose of the app, its design considerations, and 
the utilisation of GitHub and GitHub Actions. Include all of this inside your README file on 
GitHub. Add images and make the readme file your own. 
 
Submission: 
For your submission, present the following: 
 The complete Kotlin source code for the Android app must be submitted on GitHub. 
No zip files are allowed. 
 Add comments to your code, and don’t forget to reference them. 
 Make use of logging to show your understanding of your code. 
 Your README file for your app that is also on GitHub. 
 A video presentation showcasing all the app’s features. Include a link to this video in the 
README file. 
 If applicable, a short write-up (maximum 500 words) on the manner in which AI tools were 
used and cited during the completion of this assessment. Examples could include code 
snippets, debugging errors, image generation, etc. 
 
You can upload your video to YouTube, make it unlisted, and add that link to your README file. 
You can also use any video recording software you like. Just make sure to include a voice-over to 
show your understanding. 
 
  
25; 26                                2026 
© The Independent Institute of Education (Pty) Ltd 2026 
Page 10 of 25 
Part 3 — Final Asset Development, Additions, Tweaking and Final Submission            (Marks: 100) 
 
Learning Units 1 to 4 
At the end of this specific part, students should be able to: 
• Use blob storage. 
• Use a NoSQL database to store data. 
• Use a NoSQL database to read data. 
• Prepare an app for publication in the Play Store. 
 
The final app needs to run on a mobile phone and not an emulator. This means that you need to 
plan well in advance to record the required video on a mobile phone if you don’t own an Android 
phone yourself. 
 
For this submission, the app should be a fully working app without errors that has been updated 
based on the feedback your lecturer has provided you throughout the course. The app must 
compile and run for marks to be awarded for the app’s features. 
 
You must create a demonstration video showing that the application is working. For the video, 
make sure that you show the app performing the following functions: 
• The user must be able to register and log in to the app. Make sure to encrypt the password 
• Make use of single sign-on (SSO) (PoE only). 
• The user must be able to change their settings in the app. 
• Connect to a REST API you create that is connected to a database, or use any that fits your 
idea that exists on the internet. 
• Offline mode with Sync: Create a feature that allows users to perform offline actions with 
synchronisation capabilities once they reconnect. This does not need to be the whole app, 
but at least some features. Can make use of RoomDB or SQLite (PoE only). 
• Implementation of Real-time Notification: Implement a push notification system for real
time updates and alerts (PoE only). 
• Multi-language support that includes at least two South African languages (PoE only). 
• Your features that were specified in Part 1. Look at the rubric to see the weighting. 
• An app icon and final image assets. 
 
 
25; 26                                2026 
© The Independent Institute of Education (Pty) Ltd 2026 
Page 11 of 25 
You may consult any resources of your choice to implement push notifications. These resources 
may assist you: 
• Push Notifications: https://firebase.google.com/docs/cloud-messaging 
[Accessed on 03 November 2025]. 
 
Include in the demonstration video that will effectively demonstrate all the features of the app 
and be fully professional. The video must include a voice-over explaining what you are showing. 
The video must also show what data is stored in the online-hosted authentication service, API, 
and database. 
 
The app must have a user-friendly and appropriate user interface that can handle invalid inputs 
made by the user without crashing. The app must be working. 
 
Version Control with GitHub: Initialise the repository with a README file. Commit and push your 
project files to the GitHub repository. Regularly commit and push your code as you make 
progress. Since this is the final PoE, make sure to tag your final commit as the final PoE. 
 
Conduct automated testing on the main functionality of your app. Make use of GitHub Actions to 
run tests and build your code to make sure it will work not on just your computer. Use this guide, 
which will assist in creating this: 
• https://github.com/marketplace/actions/automated-build-android-app-with-github-action 
[Accessed on 03 November 2025]. 
• https://github.com/IMAD5112/Github-actions/blob/main/.github/workflows/build.yml  
[Accessed on 03 November 2025]. 
 
Documentation: 
Prepare a comprehensive report detailing the purpose of the app, its design considerations, and 
the utilisation of GitHub and GitHub Actions. Include all of this inside your README file on 
GitHub. Add images and make the README file your own. Release notes need to be added as 
well, which list all the updates that have been made to the app since the prototype. The release 
notes must clearly explain which of your innovative features were added to this version. 
 
Publishing: 
The app will also need to be ready to be published on the Google Play Store. 
25; 26                                2026 
© The Independent Institute of Education (Pty) Ltd 2026 
Page 12 of 25 
Submission: 
For your submission, present the following: 
• The complete Kotlin source code for the Android app must be submitted on GitHub. 
No zip files are allowed. 
• Add comments to your code, and don’t forget to reference them. 
• Make use of logging to show your understanding of your code. 
• Your README file for your app that is also on GitHub. 
• A video presentation showcasing all the app’s features. Include a link to this video in the 
README file. 
• Release notes that list all the updates that have been made to the app since the prototype. 
The release notes must clearly explain which of your innovative features were added to this 
version. 
• Evidence of preparation for publication: signed APK, screenshots, and a screenshot of the 
app uploaded on the Google Play Console and/or published on the Google Play Store, if 
applicable. 
• If applicable, a short write-up (maximum 500 words) on the manner in which AI tools were 
used and cited during the completion of this assessment. Examples could include code 
snippets, debugging errors, image generation, etc. 
 
You can upload your video to YouTube, make it unlisted, and add that link to your README file. 
You can also use any video recording software you like. Just make sure to include a voice-over to 
show your understanding. 
 
 
25;26;27                                    
2026 
Appendix A - PoE Marking Rubrics                
Markers – Please note that the rubrics below must be used to evaluate the students’ responses to the relevant assignment questions. Please clearly 
indicate the specific mark you allocate for each rubric criterion to show how you reached the question total. Also, please provide constructive feedback 
to ensure students and moderators can follow your marking logic based on the rubric criteria. The most important point is that many markers across 
different campuses will be marking. The rubric needs to promote the validity and reliability of their marking practices. In addition, there is a separate 
memorandum that provides additional marking guidance – please ensure you get this from your relevant campus administrator.  
While reading the student's submission, the marker should consider the goals of the task and the description of each qualitative level. The marker 
should match the student's work to the appropriate qualitative level, highlighting areas of achievement or areas that were not adequately addressed. 
Once the marker has considered all of this, then only should the student be assigned a mark. 
© The Independent Institute of Education (Pty) Ltd 2026 
Page 13 of 25 
25;26;27                                    2026 
© The Independent Institute of Education (Pty) Ltd 2026 
Page 14 of 25 
Assessment Sheet                  (Marking Rubric) 
Please note: Tear off this section and attach it to your work when you submit it/ If this is an online submission, then this information needs to be included 
in the online submission. 
 
MODULE NAME: MODULE CODE: 
OPEN SOURCE CODING (INTERMEDIATE) OPSC6312 
 
STUDENT NAME: 
STUDENT NUMBER: 
 
PART 1 – Research 
Marking 
Criteria 
Does not meet the required 
standard 
Meets the required 
standard 
Partially exceeds the 
required standard 
Greatly exceeds the required 
standard. 
Feedback 
Research: 
Introduction 
 
[5 Marks] 
 No introduction 
included. 
 The introduction does 
not link to the rest of 
the document. 
 Acceptable and links 
to the rest of the 
document, but does 
not explain the 
purpose of the 
research. 
 Acceptable and 
links to the rest 
of the document 
with some 
details about 
the purpose of 
the research. 
 An excellent introduction 
that explains the purpose 
of the research and the 
sections included in the 
document. 
 
0 – 1 Marks 2 – 3 Marks 4 Marks 5 Marks  
Research: 
App 1 
research 
 
[8 Marks] 
 Not included or 
sections missing. 
 All sections are 
included, but limited 
details are provided. 
 All sections are 
included, but more 
details could be 
added to most of 
the sections. 
 All sections are 
included, but 
more details 
could be added 
to some of the 
sections. 
 Comprehensive discussion 
of the strengths and 
weaknesses, and 
motivating why features 
are considered innovative. 
 
0 – 1 Marks 4 – 5 Marks 6 Marks 7 – 8 Marks  
 
  
25;26;27                                    2026 
© The Independent Institute of Education (Pty) Ltd 2026 
Page 15 of 25 
PART 1 – Research 
Marking 
Criteria 
Does not meet the required 
standard 
Meets the required 
standard 
Partially exceeds the 
required standard 
Greatly exceeds the required 
standard. 
Feedback 
Research: 
App 2 
research 
 
[8 Marks] 
 Not included or 
sections missing. 
 All sections are 
included, but limited 
details are provided. 
 All sections are 
included, but more 
details could be 
added to most of 
the sections. 
 All sections are 
included, but 
more details 
could be added 
to some of the 
sections. 
 Comprehensive discussion 
differentiating between the 
strengths and weaknesses 
and motivating why features 
are considered innovative. 
 
0 – 1 Marks 4 – 5 Marks 6 Marks 7 – 8 Marks  
Research: 
App 3 
research 
 
[8 Marks] 
 Not included or 
sections are missing. 
 All sections are 
included, but limited 
details are provided. 
 All sections are 
included, but more 
details could be 
added to most of 
the sections. 
 All sections are 
included, but 
more details 
could be added 
to some of the 
sections. 
 Comprehensive discussion 
differentiating between the 
strengths and weaknesses 
and motivating why features 
are considered innovative. 
 
0 – 1 Marks 4– 5 Marks 6 Marks 7-8 Marks  
Research: 
Comparison 
 
[10 Marks] 
 No comparison is 
included, nor is the 
comparison in a visual 
format. 
 Comparison in a visual 
format, but only 
includes either 
differences or 
similarities. 
 Comparison in a 
visual format with a 
good number of 
similarities and 
differences, but the 
presentation could 
be improved. 
 Comparison in a 
visual format 
with a good 
number of 
similarities and 
differences. 
 Comprehensive comparison 
that shows all the 
differences and similarities 
at a glance. 
 
0 – 4 Marks 5 Marks 6 – 7 Marks 8 – 10 Marks  
  
25;26;27                                    2026 
© The Independent Institute of Education (Pty) Ltd 2026 
Page 16 of 25 
PART 1 – Research 
Marking 
Criteria 
Does not meet the required 
standard 
Meets the required 
standard 
Partially exceeds the 
required standard 
Greatly exceeds the required 
standard. 
Feedback 
Research: List 
of features to 
include 
 
[6 Marks] 
 No list of features 
included. 
 A very short list 
included with no 
motivations. 
 A good list of 
features is included, 
but with little or no 
motivation. 
 A good list of 
features is 
included with 
some 
motivation. 
 An excellent list of features 
with motivations included 
for why these features are 
desirable. 
 
0 – 2 Marks 3 – 4 Marks 5 Marks 6 Marks  
Research: 
Conclusion 
 
[5 Marks] 
 
 No conclusion 
included. 
Conclusion does not 
link to the rest of the 
document. 
 Acceptable and links 
to the rest of the 
document. 
 Acceptable and 
links to the rest 
of the 
document. The 
link to the 
design is 
mentioned but 
not elaborated 
on. 
 An excellent conclusion that 
links to the document and 
the design. 
 
0 – 1 Marks 2 – 3 Marks 4 Marks 5 Marks  
Planning and 
Design: 
Introduction 
and 
Conclusion 
 
[5 Marks] 
 No introduction or 
conclusion included. 
 Either the introduction 
or the conclusion is 
missing. 
 A good introduction 
and conclusion are 
included, as well as 
links to the rest of 
the document. 
 A good 
introduction and 
conclusion are 
included, as well 
as links to the 
rest of the 
document. 
 Mention is 
made of the 
research 
document. 
 An excellent introduction 
and conclusion that links to 
the research document as 
well as the content of the 
design document. 
 
0 – 1 Marks 2 – 3 Marks 4 Marks 5 Marks  
25;26;27                                    2026 
© The Independent Institute of Education (Pty) Ltd 2026 
Page 17 of 25 
PART 1 – Planning and Design 
Marking 
Criteria 
Does not meet the required 
standard 
Meets the required 
standard 
Partially exceeds the 
required standard 
Greatly exceeds the 
required standard. 
Feedback 
Planning and 
Design: 
Overview of 
the app 
 
[10 Marks] 
 No overview is 
included, and no 
innovative features are 
mentioned. 
 The overview needs 
more details. 
 A good overview is 
included with some 
innovative features. 
 A good overview is 
included with some 
innovative features 
and an attempt to 
link to the rest of 
the document. 
 An excellent overview 
that sets the stage for 
the rest of the 
document. 
 
0 – 4 Marks 5 Marks 6 – 7 Marks 8 – 10 Marks  
Planning and 
Design: 
Detailed list of 
requirements 
 
[10 Marks] 
 No requirements are 
included, and the 
required features are 
missing. 
 Only required features 
are included with some 
details, but no 
additional features 
from the research are 
mentioned. 
 Required features 
and the students’ 
requirements are 
included, but more 
details are needed in 
places. 
 Required features 
and the students’ 
requirements are 
included, with more 
detail needed only 
in a few places. 
 An excellently detailed 
list of features that 
describes all the 
features, including the 
student’s own 
research in detail. 
 
0 – 4 Marks 5 Marks 6 – 7 Marks 8 – 10 Marks  
Planning and 
Design: 
Creation of 
the REST API 
 
[10 Marks] 
 Little to no 
understanding of REST 
API. 
 
 Some understanding 
of the 
implementation of 
making use of the 
API with the flow on 
the UML is 
somewhat correct. 
 Only UML created 
or detailed 
understanding of 
making use of a 
REST API to 
complete the 
application. 
 Good, detailed 
understanding of how 
to make use of the 
REST API to complete 
the application. 
 UML Diagram created. 
 
0 – 4 Marks 5 Marks 6 – 7 Marks 8 – 10 Marks  
  
25;26;27                                    2026 
© The Independent Institute of Education (Pty) Ltd 2026 
Page 18 of 25 
PART 1 – Planning and Design 
Marking 
Criteria 
Does not meet the required 
standard 
Meets the required 
standard 
Partially exceeds the 
required standard 
Greatly exceeds the 
required standard. 
Feedback 
Planning and 
Design: User 
interface 
design 
 
[10 Marks] 
 No design is included, 
and only a very brief 
description with no 
mockups is provided.  
 Only mockups or 
reasonably detailed 
descriptions included, 
not both. 
 Mockups and 
descriptions 
included, but no 
diagrams showing 
navigation. 
 Mockups and 
descriptions 
included, and 
navigation diagrams 
included with some 
missing details. 
 Excellent mockups, 
together with 
descriptions and a 
diagram explaining the 
navigation. 
 
0 – 4 Marks 5 Marks 6 – 7 Marks 8 – 10 Marks  
Planning and 
Design: 
Project Plan  
 
[5 Marks] 
 No project plan 
included. 
 Project plan is 
included, but it is not 
logical. 
 Logical with some 
errors. 
 Logical with only 
minor errors. 
 Logical and fully 
detailed with no 
errors. 
 
0 – 1 Marks 2 – 3 Marks 4 Marks 5 Marks Part 1 Total             /100 
 
  
25;26;27                                    2026 
© The Independent Institute of Education (Pty) Ltd 2026 
Page 19 of 25 
PART 2 – App Prototype Development  
Marking Criteria Does not meet the required 
standard 
Meets the required 
standard 
Partially exceeds the 
required standard 
Greatly exceeds the 
required standard  
Feedback 
The app runs on a 
mobile device. 
 
[5 Marks] 
 The app doesn’t run 
at all. 
 The app runs, but lots 
of crashes happen. 
 The app runs with 
one or two errors 
or crashes. 
 Runs on the 
emulator 
 The app runs with 
very infrequent 
errors or crashes. 
 The app runs 
without errors. 
 
0 – 1 Marks 2 – 3 Marks 4 Marks 5 Marks  
Feature:   
sign in 
[10 Marks] 
 Feature not included. 
 Feature implemented 
but very buggy. 
 Feature is working 
mostly with some 
bugs. 
 Feature is working 
with only minor 
bugs. 
 Feature excellently 
implemented. 
 
0 – 4 Marks 5 Marks 6 – 7 Marks 8 – 10 Marks  
Feature: Settings 
menu that makes 
sense for the 
application 
 
[10 Marks] 
 Feature not included. 
 Feature implemented 
but very buggy. 
 Feature is working 
mostly with some 
bugs. 
 Feature is working 
with only minor 
bugs. 
 Feature excellently 
implemented. 
 
0 – 4 Marks 5 Marks 6 – 7 Marks 8 – 10 Marks  
Creation/use of 
the REST API 
 
[10 Marks] 
 API not included. 
 API was created, but 
very buggy. 
 API is working 
mostly with some 
bugs. 
 API is working with 
only minor bugs. 
 API excellently 
created/used 
 
0 – 4 Marks 5 Marks 6 – 7 Marks 8 – 10 Marks  
  
25;26;27                                    2026 
© The Independent Institute of Education (Pty) Ltd 2026 
Page 20 of 25 
PART 2 – App Prototype Development 
Marking Criteria Does not meet the required 
standard 
Meets the required 
standard 
Partially exceeds the 
required standard 
Greatly exceeds the 
required standard  
Feedback 
Integration of 
your REST API in 
your app 
[10 Marks] 
 Feature not included. 
 Feature implemented 
but very buggy. 
 Feature is working 
mostly with some 
bugs. 
 Feature is working 
with only minor 
bugs. 
 Feature is 
excellently 
integrated. 
 
0 – 4 Marks 5 Marks 6 – 7 Marks 8 – 10 Marks  
Feature:   
User Defined 1 
 
[10 Marks] 
 Feature not included. 
 Feature implemented 
but very buggy. 
 Feature is working 
mostly with some 
bugs. 
 Feature is working 
with only minor 
bugs. 
 Feature excellently 
implemented. 
 
0 – 4 Marks 5 Marks 6 – 7 Marks 8 – 10 Marks  
Feature:  
User Defined 2 
 
[10 Marks] 
 Feature not included. 
 Feature implemented 
but very buggy. 
 Feature is working 
mostly with some 
bugs. 
 Feature is working 
with only minor 
bugs. 
 Feature excellently 
implemented. 
 
0 – 4 Marks 5 Marks 6 – 7 Marks 8 – 10 Marks  
Feature:  
User Defined 3 
 
[10 Marks] 
 Feature not included. 
 Feature implemented 
but very buggy. 
 Feature is working 
mostly with some 
bugs. 
 Feature is working 
with only minor 
bugs. 
 Feature excellently 
implemented. 
 
0 – 4 Marks 5 Marks 6 – 7 Marks 8 – 10 Marks  
 
  
25;26;27                                    2026 
© The Independent Institute of Education (Pty) Ltd 2026 
Page 21 of 25 
PART 2 – App Prototype Development 
Marking Criteria Does not meet the required 
standard 
Meets the required 
standard 
Partially exceeds the 
required standard 
Greatly exceeds the 
required standard  
Feedback 
User interface  
 
[10 Marks] 
 Not user-friendly, 
confusing, and illogical. 
 Somewhat user
friendly with lots of 
mistakes in the design. 
 Mostly user
friendly, with 
some mistakes in 
the design. 
 Somewhat 
consistent use of 
layout, fonts, and 
colours. 
 Mostly user
friendly, with some 
attempt made at 
making the capture 
process easier for 
the user. 
 Mostly consistent 
use of layout, fonts, 
and colours. 
 Excellent and user
friendly design. 
 Completely 
consistent use of 
layout, fonts, and 
colours. 
 
0 – 4 Marks 5 Marks 6 – 7 Marks   8 – 10 Marks  
Github, Readme 
and automated 
testing 
 
[10 Marks] 
 Zip file added or file 
upload used for 
GitHub. 
 Multiple commits 
and unit testing 
are not working. 
 No readme file. 
 Automated testing 
includes, but is not 
working correctly. 
 Somewhat readme 
file created. 
 Readme file 
created and 
implemented 
correctly. 
 Automated testing 
is in place. 
 Multiple commits. 
 
0 – 4 Marks 5 Marks 6 – 7 Marks 8 – 10 Marks  
Demonstration 
video 
 
[5 Marks] 
 No demonstration 
video included. 
 Informal and 
unprepared, or not 
showing all the 
features or no voice
over included. 
 It is not completely 
professional, but 
all features are 
demonstrated. 
 Mostly professional 
with some issues, 
but all features 
were 
demonstrated. 
 Fully professional 
video showing all 
the required 
features in detail. 
 
0 – 1 Marks 2 – 3 Marks 4 Marks 5 Marks Part 2 Total              /100 
  
25;26;27                                    2026 
© The Independent Institute of Education (Pty) Ltd 2026 
Page 22 of 25 
PORTFOLIO OF EVIDENCE (POE) 
Marking Criteria Does not meet the required 
standard 
Meets the required 
standard 
Partially exceeds the 
required standard 
Greatly exceeds the 
required standard  
Feedback 
The app runs on 
a mobile device. 
 
[5 Marks] 
 The app doesn’t run 
at all. 
 The app runs, but 
lots of crashes 
happen. 
 The app runs 
with one or two 
errors or crashes. 
 Runs on the 
emulator 
 The app runs with 
very infrequent 
errors or crashes. 
 The app runs 
without errors. 
 
0 – 1 Marks 2 – 3 Marks 4 Marks 5 Marks  
Feature:   
SSO login 
 
[10 Marks] 
 Feature not included. 
 Feature 
implemented but 
very buggy. 
 Feature is 
working mostly 
with some bugs. 
 Feature is working 
with only minor 
bugs. 
 Feature 
excellently 
implemented. 
 
0 – 4 Marks 5 Marks 6 – 7 Marks 8 – 10 Marks  
Feature: Offline 
mode 
 
[10 Marks] 
 Feature not included. 
 Feature 
implemented but 
very buggy. 
 Feature is 
working mostly 
with some bugs. 
 Feature is working 
with only minor 
bugs. 
 Feature 
excellently 
implemented. 
 
0 – 4 Marks 5 Marks 6 – 7 Marks 8 – 10 Marks  
Feature: Real
time 
Notification 
 
[10 Marks] 
 Feature not included. 
 Feature 
implemented but 
very buggy. 
 Feature is 
working mostly 
with some bugs. 
 Feature is working 
with only minor 
bugs. 
 Feature 
excellently 
implemented. 
 
0 – 4 Marks 5 Marks 6 – 7 Marks 8 – 10 Marks  
  
25;26;27                                    2026 
© The Independent Institute of Education (Pty) Ltd 2026 
Page 23 of 25 
PORTFOLIO OF EVIDENCE (POE) 
Marking Criteria Does not meet the required 
standard 
 
Meets the required 
standard 
 
Partially exceeds the 
required standard 
 
Greatly exceeds the 
required standard  
 
Feedback 
Feature:  Multi
language 
 
[10 Marks] 
 Feature not included. 
 Feature implemented 
but very buggy. 
 Feature is 
working mostly 
with some bugs. 
 Feature is working 
with only minor 
bugs. 
 Feature excellently 
implemented. 
 
0 – 4 Marks 5 Marks 6 – 7 Marks 8 – 10 Marks  
Feature:   
User Defined 4 
 
[10 Marks] 
 Feature not included. 
 Feature implemented 
but very buggy. 
 Feature is 
working mostly 
with some bugs. 
 Feature is working 
with only minor 
bugs. 
 Feature excellently 
implemented. 
 
0 – 4 Marks 5 Marks 6 – 7 Marks 8 – 10 Marks  
Feature: 
User Defined 5 
 
[10 Marks] 
 Feature not included. 
 Feature implemented 
but very buggy. 
 Feature is 
working mostly 
with some bugs. 
 Feature is working 
with only minor 
bugs. 
 Feature excellently 
implemented. 
 
0 – 4 Marks 5 Marks 6 – 7 Marks 8 – 10 Marks  
The app has 
been prepared 
for publication 
in the Play 
Store. 
 
[5 Marks] 
 No preparation has 
been done. 
 No evidence of 
preparation has been 
submitted. 
 The app is not ready 
for publication. 
 The minimum 
requirements for 
Play Store 
publication are 
met. 
 The minimum 
requirements for 
Play Store 
publication are met. 
 Some additional 
material is included. 
 The app is 
completely ready 
to be published to 
the Play Store. 
 All material, 
including 
screenshots for the 
Play Store, is 
included. 
 
0 – 1 Marks 2 – 3 Marks 4 Marks 5 Marks  
 
  
25;26;27                                    2026 
© The Independent Institute of Education (Pty) Ltd 2026 
Page 24 of 25 
AI-use writeup 
 
[5 Marks] 
 Writeup not included.  Writeup included, 
but the details 
provided are 
vague. 
 Writeup does not 
include details of 
how AI was used and 
cited. 
 A detailed write-up 
which includes 
information on 
how it was used 
and cited. 
 Mark may be 
awarded if AI is not 
used at all. 
 
0 – 1 Marks 2 – 3 Marks 4 Marks 5 Marks  
User interface  
 
[10 Marks] 
 Not user-friendly, 
confusing, and illogical. 
 Somewhat user
friendly with lots of 
mistakes in the design. 
 Mostly user
friendly, with 
some mistakes in 
the design. 
 Somewhat 
consistent use of 
layout, fonts, and 
colours. 
 Mostly user-friendly, 
with some attempt 
made at making the 
capture process 
easier for the user. 
 Mostly consistent 
use of layout, fonts, 
and colours. 
 Excellent and user
friendly design. 
 Completely 
consistent use of 
layout, fonts, and 
colours. 
 
0 – 4 Marks 5 Marks 6 – 7 Marks 8 – 10 Marks  
GitHub, Readme 
and automated 
testing 
 
[10 Marks] 
 Zip file added or file 
upload used for 
GitHub. 
 Multiple commits 
and unit testing 
are not working. 
 No readme file. 
 Automated testing 
includes, but is not 
working correctly 
 Somewhat of a 
readme file created. 
 Readme file 
created and 
implemented 
correctly. 
 Automated testing 
is in place. 
 Multiple commits. 
 
0 – 4 Marks 5 Marks 6 – 7 Marks 8 – 10 Marks  
 
  
25;26;27                                    
2026 
Demonstration 
video 
[5 Marks] 
 
 
No demonstration 
video included. 
Informal and 
unprepared, or not 
showing all the 
features or no voice
over included. 
 
 It is not completely 
professional, but all 
features are 
demonstrated. 
 
Mostly professional 
with some issues, 
but all features 
were 
demonstrated. 
Fully professional 
video showing all 
the required 
features in detail. 
0 – 1 Marks 
2 – 3 Marks 
4 Marks 
5 Marks 
PoE Total          
/100 
[TOTAL MARKS: 300] 
© The Independent Institute of Education (Pty) Ltd 2026 
Page 25 of 25 
IIE Module Manual         
PROG7314 
PROGRAMMING 3D 
MODULE MANUAL 2026 
This manual enjoys copyright under the Berne Convention. In terms of the Copyright Act, no 
98 of 1978, no part of this manual may be reproduced or transmitted in any form or by any 
means, electronic or mechanical, including photocopying, recording or by any other 
information storage and retrieval system without permission in writing from the proprietor. 
The Independent Institute of Education (Pty) Ltd is registered with the 
Department of Higher Education and Training as a private higher 
education institution under the Higher Education Act, 1997 (reg. no. 
2007/HE07/002). Company registration number:  1987/004754/07. 
© The Independent Institute of Education (Pty) Ltd 2026     
Page 1 of 116 
IIE Module Manual         
PROG7314 
DID YOU KNOW? 
Student Portal 
The full-service Student Portal provides you with access to your academic and administrative 
information, including: 
 
 
 
 
 
an online calendar, 
timetable, 
academic results, 
module content, 
financial account, and so much more! 
Module Guides or Module Manuals 
When you log into the Student Portal, the ‘Module Information’ page displays the ‘Module 
Purpose’ and ‘Textbook Information’, including the online ‘Module Guides or ‘Module Manuals’ 
and assignments for each module for which you are registered. 
Supplementary Materials 
For certain modules, electronic supplementary material is available to you via the 
‘Supplementary Module Material’ link. 
Module Discussion Forum 
The ‘Module Discussion Forum’ may be used by your lecturer to discuss any topics related to 
any supplementary materials and activities, such as ICE, etc. 
To view, print and annotate these related PDF documents, download Adobe Reader at 
the following link below: 
https://www.adobe.com/acrobat/pdf-reader.html  
© The Independent Institute of Education (Pty) Ltd 2026     
Page 2 of 116 
IIE Module Manual         PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026     Page 3 of 116 
IIE Library Online Databases  
 
The following Library Online Databases are available. These links will prompt you for a 
username and password. Use the same username and password as for the student portal. 
Please contact your librarian if you are unable to access any of these. Here are links to 
some of the databases: 
 
Library Website This library website gives access to various online 
resources and study support guides  
[Link] 
 
LibraryConnect 
(OPAC) 
 
 
The Online Public Access Catalogue. Here you will be 
able to search for books that are available in all the IIE 
campus libraries.  
[Link] 
 
EBSCOhost  
 
This database contains full-text online articles.  
[Link] 
 
EBSCO eBook 
Collection 
 
This database contains full-text online eBooks. 
[Link] 
SABINET This database will provide you with books available in 
other libraries across South Africa.  
[Link] 
 
DOAJ DOAJ is an online directory that indexes and provides 
access to high-quality, open-access, peer-reviewed 
journals.  
[Link] 
 
DOAB Directory of open-access books. 
[Link] 
 
IIESPACE The IIE open access research repository 
[Link] 
 
Emerald 
 
Emerald Insight 
[Link] 
 
HeinOnline Law database  
[Link] 
 
JutaStat Law database 
[Link] 
 
 
  
IIE Module Manual         PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026     Page 4 of 116 
Table of Contents 
 
Using this Manual ................................................................................................................. 5 
Introduction ........................................................................................................................... 6 
Learning Unit 1: Web Services .............................................................................................. 7 
Introduction ........................................................................................................................... 7 
Connecting to a Webservice ................................................................................................. 8 
JSON Files and JSON Objects ........................................................................................... 23 
Fragments ........................................................................................................................... 31 
Recommended Additional Reading ..................................................................................... 39 
Recommended Digital Engagement .................................................................................... 40 
Activities.............................................................................................................................. 40 
Learning Unit 2: External Libraries ...................................................................................... 41 
Introduction ......................................................................................................................... 41 
Connecting to External Libraries ......................................................................................... 42 
Using Geolocation Services in an App ................................................................................ 52 
Adding Social Media Services ............................................................................................. 64 
Working with SDKs ............................................................................................................. 67 
Recommended Additional Reading ..................................................................................... 70 
Activities.............................................................................................................................. 70 
Learning Unit 3: Using Databases ....................................................................................... 71 
Introduction ......................................................................................................................... 71 
Creating and Accessing an SQLite Database ..................................................................... 71 
Access a NoSQL Database................................................................................................. 80 
Recommended Additional Reading ..................................................................................... 94 
Activities.............................................................................................................................. 94 
Learning Unit 4: App Publication ......................................................................................... 95 
Introduction ......................................................................................................................... 95 
APKs and Bundles .............................................................................................................. 95 
Deploying to the App Store ............................................................................................... 101 
Track Analytics and Usage................................................................................................ 106 
Recommended Digital Engagement .................................................................................. 107 
Recommended Additional Reading ................................................................................... 107 
Activities............................................................................................................................ 107 
Bibliography ...................................................................................................................... 108 
Intellectual Property .......................................................................................................... 111 
 
  
IIE Module Manual         
PROG7314 
Using this Manual 
This manual has been developed to meet the specific objectives of the module and uses 
several different sources. It functions as a stand-alone resource for this module, and no 
prescribed textbook or material is therefore required. There may, however, be occasions when 
additional readings are also recommended to supplement the information provided. Where 
these are specified, please ensure that you engage with the reading as indicated.  
Various activities and revision questions are included in the learning units of this manual. 
These are designed to help you engage with the subject matter as well as to help you prepare 
for your assessments. 
© The Independent Institute of Education (Pty) Ltd 2026     
Page 5 of 116 
IIE Module Manual         
PROG7314 
Introduction 
Welcome to Programming 3D. This module follows on from the Programming 3C module that 
you have already completed. We will still be making use of Kotlin to build native Android apps, 
but here we will be introducing some more advanced concepts, such as interacting with web 
services and making use of libraries and software development kits. 
Throughout this module, you will create several apps to master the intermediate skills needed 
to build an Android app. It is important to get hands-on experience in any programming 
module, so it is essential that you complete all the activities provided on ARC. 
We hope you will enjoy the module and take the opportunity to use the knowledge and 
experience gained in both future modules and in your career. 
Figure 1. Module Structure 
© The Independent Institute of Education (Pty) Ltd 2026     
Page 6 of 116 
IIE Module Manual         PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026      Page 7 of 116 
Learning Unit 1: Web Services 
Learning Objectives: 
 
 Explain how to code a Hypertext Transfer Protocol 
(HTTP) connection from scratch. 
 Explain how to connect to a RESTful Application 
Programming Interface (API). 
 Use a RESTful API in an Android app. 
 Explain the purpose of JSON files. 
 Use JSON objects to read data. 
 Use JSON objects to write data. 
 Use a library to work with JSON data. 
 Explain the purpose of using fragments in an 
Android app. 
 Apply fragments in an Android app. 
My notes 
Material used for this learning unit: 
 
 GitHub repository: LearningUnit1 
How to prepare for this learning unit: 
 
 Make sure you have Android Studio installed and 
fully updated 
 
Introduction 
 
In Programming 3C (PROG7313), we have learned how to create basic Android apps with 
multiple activities. We have made use of the a NoSQL database to store data in the cloud. 
And we have made use of phone features such as taking a photograph. 
 
In this module, we are going to build on what we have learned already to create more feature
rich applications.  
 
In this first learning unit, we are going to use existing web services to provide data to our 
application. Although creating and hosting such web services is beyond the scope of this 
module, you could consume data from your own web services in the same way. 
 
  
IIE Module Manual         
PROG7314 
Connecting to a Webservice 
Module Theme: Building a Weather App 
If you think back to PROG7313 you will remember the Starsucks 
app example that was used throughout the module. This 
semester, our theme is a weather app. 
Have you ever wondered where the weather apps get their data 
from? All the apps out there have data available for even the most 
remote locations on Earth. Each country has its own weather 
service, for example, the South African Weather Service, which 
has its own website: https://www.weathersa.co.za/  
But if you created an app, and you wanted worldwide weather data, how would you collect 
data from all the countries in the world? The answer is that you would not. Instead, find a 
company that does that already and make use of their services! 
One such company is AccuWeather. You might be familiar with the AccuWeather apps that 
are available for both Android and iOS. But an important part of their business is their 
Application Programming Interfaces (APIs) that provide data to third parties. They even have 
services tailored to the needs of the retail and manufacturing industries. (AccuWeather, Inc., 
2020)  
We will make use of the AccuWeather RESTful APIs in this module. 
What is a RESTful API? 
If we want to request data from AccuWeather, we need to know how to communicate with their 
servers. It is quite easy to do since they expose RESTful APIs. 
“A RESTful API is an application program interface (API) that uses HTTP requests to GET, 
PUT, POST and DELETE data.” (Rouse, 2020) 
The first thing to note here is that the protocol that is used to exchange information is the 
Hypertext Transfer Protocol (HTTP). This is the same protocol that is used when we access a 
website using a web browser. This means that a web browser is an HTTP client – it can make 
HTTP requests and handle the responses. (Rouse, 2019) 
How are RESTful APIs accessed? The definition says by means of HTTP requests. So, the 
same kinds of requests that your browser makes every time you visit a website will also be 
made by our app. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 8 of 116 
IIE Module Manual         
PROG7314 
When a browser makes a request, the information about what is being requested is sent in 
the header of the request message. And when the server responds, the response again starts 
with a header that specifies, amongst other things, the type of content to follow. For example, 
in Figure 2, the browser requests a Hypertext Markup Language (HTML) file from the server. 
And in the header of the response, the server indicates that the data to follow has a text/html 
content type. (Seobility, n.d.) 
Figure 2. HTTP Headers (Seobility, n.d.) 
Many kinds of things can be transported using this HTTP protocol. For example, you might 
have heard of Simple Object Access Protocol (SOAP) messages before, and those can also 
be transported using HTTP. SOAP messages are based on Extensible Markup Language 
(XML), and it is a format for sending data between servers. SOAP is still used in some 
enterprise software systems where very complex transactions are processed. (Monus, 2020) 
RESTful APIs were created later in response to SOAP, to fix some of the issues, such as very 
strict rules that had to be complied with. RESTful APIs can use other messaging formats such 
as JavaScript Object Notation (JSON). (Monus, 2020) 
RESTful APIs are based on the architectural style called REpresentational State Transfer 
(REST). (Rouse, 2020) There are a couple of guiding principles that RESTful services must 
follow, such as the interface being a client-server model and the services being stateless. 
(RESTfulAPI.net, n.d.) 
For the purposes of what we are going to do, we are only reading data from an API. This 
means that we will make GET requests. We do not have any data to contribute, and there is 
no reason for us to have access to delete data from the AccuWeather servers. 
Using the developer tools built into the Chrome web browser, we can inspect the headers of 
the request and response messages when we call the AccuWeather service from our browser. 
Remember, a browser is an HTTP client, so it can interact with these services if we just browse 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 9 of 116 
IIE Module Manual         
PROG7314 
to the Universal Resource Locator (URL) of the service. Do not worry about the details of the 
URL yet – we will get to that later. 
Figure 3. Request Header for an AccuWeather Call 
Figure 3 Shows the details of the request that was made. And we see that the browser made 
a GET request on our behalf just as we would expect. 
Figure 4. Values from the Response Header 
If we inspect the response header, we see a huge number of different fields that are set. For 
our purposes, only the Content-Encoding and Content-Type fields are of interest right now, as 
shown in Figure 4. We can see that the content type here is application/json, so we are 
expecting to see JSON data. More on that later. 
The content encoding is set to gzip, which means that the data is compressed. The size of the 
data that is sent from the server to your device is reduced by running it through an algorithm 
first. Using GZIP is one of the best practices recommended by AccuWeather. (AccuWeather, 
Inc., n.d.) 
You might have noticed that the protocol used in the URL is https and not http. What is the 
difference? Hypertext Transfer Protocol Secure (HTTPS) is, as the name suggests, a secure 
way to send data. Transport Layer Security (TLS) is applied to the connection, which means 
that the data that is sent from the client to the server is encrypted. (Google, 2020) 
If you browse to a site protected by this type of security, the browser will display a lock icon. 
Figure 5 Shows what this looks like in Chrome. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 10 of 116 
IIE Module Manual         
PROG7314 
Figure 5. Secure Site Indicator in Chrome 
Introducing the Source Code 
There is a GitHub repository that contains all the source code for the examples that are 
described in this module manual. The repository can be found here: 
https://github.com/iie-opsc/opsc7312kotlin  
© The Independent Institute of Education (Pty) Ltd 2026      
Page 11 of 116 
IIE Module Manual         
PROG7314 
There is a folder for each learning unit, so the folder that is applicable here is LearningUnit1. 
Note: All the example code and instructions in this module manual are written in Java. But 
you are free to use Kotlin if you want to. 
Registering with AccuWeather 
AccuWeather is a commercial company that provides its weather data 
to third parties to make a profit from it. That means that they do not 
allow everybody to just make unlimited calls to their servers for free. If 
you look carefully at the URL in Figure 3 you will see that there is an 
API key that is included in the URL. And that is how AccuWeather 
knows that the caller is authorised to make the calls. 
You need a 
virtual device 
with Android Q 
(API level 29) or 
later installed to 
run the example 
source code. 
Luckily, there is a free version that we can sign up for. Figure 6 Shows what is included in the 
free version. 
Note that there is a limitation of only 50 calls per day. So, if you make a lot of calls on the same 
day, you will eventually stop getting data back. 
Signing Up for an AccuWeather Account 
Browse to https://developer.accuweather.com/packages to see the details of all the packages. 
Also, note at the bottom of the page that there are branding requirements when using this 
data. This means that each activity where you display weather data should also include the 
AccuWeather logo. 
Click the Get started now! Link under the Free version to sign up.  
Once you have created the account, look for the email with the link to complete the sign-up 
process. Remember to set a strong password! 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 12 of 116 
IIE Module Manual         
PROG7314 
Figure 6. Comparison of the Free and Standard accounts of AccuWeather as of  
10 February 2023 (AccuWeather, Inc., 2023a) 
Watch the videos in the recommended digital engagement section of this learning unit on 
page 40. The videos show step-by-step how to build this weather app. 
Adding an App to AccuWeather 
With a trial account, you only get one API key. So, the next step is to register an app to your 
account.  
To add an app to your AccuWeather account: 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 13 of 116 
IIE Module Manual         
PROG7314 
1. 
Click on your email address at the top of the page to access the menu shown in Figure 
7.  
Figure 7. Accessing My Apps 
2. 
3. 
4. 
5. 
Click My Apps. 
Click Add a new App. 
Specify the following values on the Add App page: 
 
 
 
 
 
 
 
App Name: Basic Weather 
Product: Core Weather Limited Trial 
Where will the app be used? Mobile App. 
What will you be creating with this API? Weather App. 
What programming language is your app written in? Other. 
Is this for Business-to-Business or Business-to-Consumer use? Business to 
consumer. 
Is this Worldwide or country-specific use? Worldwide. 
Click Create App. 
Once the app has been created, you can click on it to access your very own API key. See 
Figure 8. 
Figure 8. Viewing the API key 
Note: No API key is included in the source code in the Git repository – you need to insert 
your own API key to run the apps. Otherwise, the 50 calls per day limit would be reached 
quite quickly if all the students tried to run the app on the same day! 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 14 of 116 
IIE Module Manual         
PROG7314 
Requesting Data from AccuWeather 
Now that we have signed up for an API key, we are ready to make our first request for data 
from AccuWeather. We have already seen that if we have the correct URL, we can access the 
services from a browser. Let us see how to find the URL to retrieve a five-day forecast. 
Requesting Data using a Web Browser  
On the AccuWeather APIs website, there is a good API reference that explains how to call the 
services. Start by looking at the API Flow Diagram page (AccuWeather, Inc., 2020c), which 
contains a diagram explaining the process is to getting information. There are basically two 
steps in the process: 
1. 
2. 
Use the Locations API to find the locationKey for the place that we want to look up. 
Then use that locationKey to request other data, such as a forecast. 
There are different ways to find a locationKey. But maybe the easiest one is to search by city 
name. Figure 9 Shows the block in the flow diagram that searches using a city name. What is 
particularly useful to note here the API key is now included in the URL. 
Figure 9. Extract from the API Flow Diagram showing location search by City Name 
(AccuWeather, Inc., 2020c) 
Say we want to get the location details for Durban. Here is the URL. Copy it and add your own 
API key to the end of it: 
https://dataservice.accuweather.com/locations/v1/search?q=Durban&apikey=  
Figure 10 Shows what the response looks like.   
© The Independent Institute of Education (Pty) Ltd 2026      
Page 15 of 116 
IIE Module Manual         
PROG7314 
Figure 10. Response from the location API 
Note that we use https instead of http. If you access the HTTP address, your browser will 
automatically be redirected to the more secure HTTPS URL. But if we did that from our app, 
the calls from our app would fail. So, let us just use HTTPS to start with. 
In the next section, we will look at how the JSON syntax works. For now, it is enough to see 
that it is human readable, and that it is the right city since it indicates that it is in South Africa. 
Remember, this database is worldwide. So, you might get cities with the same names in other 
countries, too. And we can spot the key value that we were looking for (outlined in green). 
The locationKey for Durban is 305605. 
Now we can use that piece of information to request the five-day forecast for Durban. Figure 
11 Shows the information for the call used to get the 5-day forecast for a location. 
Figure 11. Forecast API (AccuWeather, Inc., 2020b) 
Here is the URL for getting the five-day forecast for Durban. Remember to add your API key 
at the end of the URL again. 
http://dataservice.accuweather.com/forecasts/v1/daily/5day/305605?apikey=  
Figure 12 Shows an example of what the response looks like.  
© The Independent Institute of Education (Pty) Ltd 2026      
Page 16 of 116 
IIE Module Manual         
PROG7314 
Figure 12. 5-Day Forecast Example 
If you see this kind of data, then you know you have the URL ready to start building the app. 
Using the Source Code 
It is recommended that you follow along with the module manual by building your own app 
step-by-step as described here. However, the sample source code is available from the 
GitHub repository, so if you get stuck, have a look at that. 
After you have cloned the repository, open it in Android Studio in the folder 
LearningUnit1\BasicWeatherApp. Using the command line git tools, you can check out a 
specific tag, for example: git checkout LU1-1.6.3. 
Another way to get the source code for a specific tag is to download the zip version of it from 
the GitHub web interface. Click on Tags, and then download the zip file for the tag. 
There are three steps to working with the weather data in our app, as shown in Figure 13. 
1. Build the URL
2. Request the Data
3. Use the Data
Figure 13. Working with Weather Data in Our App 
Building the URL 
To get started, create a new Android Studio project, starting with an Empty Activity. Remember 
to choose your language (Kotlin to follow along here). And use a minimum SDK version of at 
least API 23: Android 6.0 (Marshmallow). 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 17 of 116 
IIE Module Manual         
PROG7314 
Tip: If you ever want to publish your app on the Google Play Store, the package name 
that you choose when creating the app needs to be unique. The default com.example 
suggested by Android Studio will NOT be allowed by the Play Store. So, you must choose 
something that is going to be unique. 
The Java (and Kotlin) naming convention for packages is to use your organisation’s 
website since that is already guaranteed to be unique. You could use, for example: 
com.vegaschool.st21987654.weatherapp 
or 
za.co.varsitycollege.st21987654.weatherapp 
if ST21987654 is your student number. Note how the top-level domain is first in the 
package name. It starts with com or za – the opposite of the website address. 
Let us create a utility class that handles the network requests – call it NetworkUtil and put it 
in the same package as the MainActivity. 
We want to build up the URL to get the five-day Durban weather forecast. So, let us define 
some constants in our NetworkUtil file that we can use to do that. 
private val WEATHERBASE_URL = 
"https://dataservice.accuweather.com/forecasts/v1/daily/5day/305605" 
private val PARAM_METRIC = "metric" 
private val METRIC_VALUE = "true" 
private val PARAM_API_KEY = "apikey" 
private val LOGGING_TAG = "URLWECREATED" 
We have the base URL and some parameters that we want to add. By default, the weather 
data is returned in imperial units of measurement. Have a close look at Figure 12 – You will 
notice that the temperatures are in degrees Fahrenheit. So, we want to pass metric=true to 
get our temperatures in degrees Celsius instead. And we need to pass the API key. 
At the end of the section 0, it was mentioned that there is no API key in the GitHub repository 
source code. Beyond the logistics of a lot of students trying to use the same key on the same 
day, it is generally good practice to safeguard sensitive information like this. We will store the 
API key in a build config that will not be checked into the repository. The process followed 
here is from (CodePath Android Cliffnotes, n.d.) 
To store the API key in a build config: 
1. Switch to Project Files view. 
2. IMPORTANT! If you are committing the code to a Git repository, open up the .gitignore file 
now and add a line containing apikey.properties 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 18 of 116 
IIE Module Manual         
PROG7314 
Figure 14. Creating a New File in the Project Root 
3. 
Right-click the root folder of the project and click New, then File. 
4. 
Call the new file apikey.properties. 
Figure 15. Add the New Property 
5. 
6. 
Specify a single property, ACCUWEATHER_API_KEY, that stores your API key. Note 
that it should be enclosed in double quotes. 
Open the app/build. gradle file, and add the following lines just below plugins:  
def apikeyPropertiesFile = rootProject.file("apikey.properties") 
def apikeyProperties = new Properties() 
apikeyProperties.load(new FileInputStream(apikeyPropertiesFile)) 
7. Inside the default config brackets, add these lines: 
buildConfigField("String", "ACCUWEATHER_API_KEY",      
apikeyProperties['ACCUWEATHER_API_KEY']) 
8. 
The app/build.gradle file should now look like shown in Figure 16. The new parts are 
highlighted in green. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 19 of 116 
IIE Module Manual         
PROG7314 
Figure 16. Completed app/build.gradle File 
9. 
Switch back to Android view. 
10. On the main menu, click Build, then choose Make Project. 
Now we have all the information available that we need to build the URL that we are going to 
call. There is a very useful class in the android.net package called Uri that we can use here. 
The 
documentation 
for 
this 
class 
https://developer.android.com/reference/android/net/Uri 
is 
available 
from 
The advantage of using the Uri class to build up the full URL is that we do not need to know 
about the implementation details of what the URL must look like. The Uri class will add the ? 
and & into the URL as necessary. 
Add this function to the NetworkUtil file: 
fun buildURLForWeather(): URL? { 
val buildUri: Uri = Uri.parse(WEATHERBASE_URL).buildUpon() 
.appendQueryParameter( 
PARAM_API_KEY, 
BuildConfig.ACCUWEATHER_API_KEY 
) // passing in api key 
.appendQueryParameter( 
PARAM_METRIC, 
METRIC_VALUE 
) // passing in metric as measurement unit 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 20 of 116 
IIE Module Manual         PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026      Page 21 of 116 
        .build() 
    var url: URL? = null 
    try { 
        url = URL(buildUri.toString()) 
    } catch (e: MalformedURLException) { 
        e.printStackTrace() 
    } 
    Log.i(LOGGING_TAG, "buildURLForWeather: $url") 
    return url 
} 
 
Requesting Data from the AccuWeather Service 
 
Add a TextView with an ID of tv_weather to the main activity of your app, as a temporary 
place where we can just display the text that we get from the web service. 
 
The call we want to make is going to require access to the Internet. And that is a permission 
that we must request. So, add the below line to AndroidManifest.xml: 
 
<uses-permission android:name="android.permission.INTERNET"/> 
 
Now we can add the code to the MainActivity class to call the web service and display the raw 
data: 
 
class MainActivity : AppCompatActivity() { 
 
    lateinit var binding: ActivityMainBinding 
 
    override fun onCreate(savedInstanceState: Bundle?) { 
        super.onCreate(savedInstanceState) 
        binding = ActivityMainBinding.inflate(layoutInflater) 
        setContentView(binding.root) 
 
        // Thanks to https://stackoverflow.com/questions/46177133/ 
 //      http-request-in-android-with-kotlin 
        thread { 
            val weather = try { 
                buildURLForWeather()?.readText() 
            } catch (e: Exception) { 
                return@thread 
            } 
            runOnUiThread { binding.tvWeather.text = weather } 
        } 
    } 
} 
 
There are very specific rules about what should happen on which thread in an Android app. 
All User Interface (UI) updates must happen on the UI thread because the Android UI toolkit 
is not thread-safe. But other tasks, like web service calls that can take a long time, should not 
block the UI thread. (Android Open Source Project, 2020b) 
 
IIE Module Manual         
PROG7314 
By using threads, calling the methods on the right thread becomes easy. Everything in the 
brackets after the thread is called on a separate thread. But the update needs to happen on 
the UI thread, so we use runOnUiThread to do that.  
Figure 17. The Most Boring Weather App… Ever… 
We are now successfully requesting the data and just displaying it in the app. But this is a very 
long way from being user-friendly. 
Before we jump into how to parse the data and display it in a more user-friendly way, let's 
have a look at JSON. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 22 of 116 
IIE Module Manual         
PROG7314 
JSON Files and JSON Objects 
Why JSON? 
We have already seen that JSON is not the only option for transferring information between a 
server and a client. XML is another option that is also human-readable. The main benefit of 
JSON, applicable to all systems that use it, is that it is a much more compact format. If the 
same data is represented in JSON and XML, the XML version will use as many as double the 
number of characters. (Freeman, 2019) 
JSON also have some more situational advantages. For example, if the data is parsed by a 
JavaScript web UI, it takes only a single line of code to do so. (Freeman, 2019) And since 
JavaScript is such a widely used language on the web, this has contributed quite a bit to the 
widespread use of JSON today. 
Quick Recap of JSON Syntax 
In JSON, we can represent objects with properties, as well as arrays. Let us look at an example 
of a person with their name and age. This example was created in the online editor JSON 
Editor Online: https://jsoneditoronline.org/  
Figure 18. JSON Object 
The curly brackets { } indicate the start and end of the object. And the properties are 
represented as key-value pairs. This is a very simple object, but objects could have many 
properties. 
What if we wanted to represent another object inside of that, maybe containing the information 
about the degree that Bob is studying? Well, objects can be nested as shown in Figure 19. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 23 of 116 
IIE Module Manual         
PROG7314 
Figure 19. Nested Degree Object 
One last thing that we want to be able to represent would be an array of objects. This is done 
using square brackets [ ], as shown in Figure 20. 
Figure 20. Array of Objects 
Looking at this example, we see some of the flexibility of JSON. For the second person, no 
degree information is available. So, it is simply left out of the data representation. 
A very useful feature of this online editor is that you can use it to format JSON data in an easy
to-read way. If we copy the data from the AccuWeather service into this editor, and then click 
the format button (indicated by the arrow in Figure 21. It will add indentation and newlines. 
And if you click the Copy > button, it will also appear in the tree structure on the right. Go 
ahead and do this now with your latest data (from a browser), so you can explore the structure 
of what the service provides. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 24 of 116 
IIE Module Manual         
PROG7314 
Figure 21. Formatting AccuWeather Data 
Read more about JSON in (Tagliaferri, 2016). 
Reading JSON Objects 
Before we start parsing the data, let us quickly deal with the branding requirements specified 
by AccuWeather. At the time of writing this Module Manual, the official logo did not exist. So, 
the logo included in the code might not be accurate.  
Nevertheless, add an image view to your app that displays the AccuWeather logo, with ID 
iv_accuweather. The logo should be available on this page: 
https://developer.accuweather.com/packages 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 25 of 116 
IIE Module Manual         
PROG7314 
And then add the following code to the onCreate method of the activity: 
// add an event handler to open the Accu Weather 
// website on click 
binding.ivAccuweather.setOnClickListener { 
val intent = Intent( 
Intent.ACTION_VIEW, 
Uri.parse("http://www.accuweather.com/") 
) 
startActivity(intent) 
} 
Read more about opening a URL in a browser in (RIP Tutorial, n.d.) 
As with all things in programming, there are different options for reading JSON data in a Java 
Android app. The first way of doing this is by using JSONObject and JSONArray. 
When creating a JSONObject, there is a constructor that takes a string as a parameter. That 
is the input string that will be parsed to extract all the data inside it. So, if we have a String 
variable called weatherJSON, we can do: 
Figure 22. Autocomplete Showing JSONObject Methods 
Autocomplete in Android Studio reveals that there are a few different methods that can be 
called on a JSONObject to get data, depending on the data type of the data that we are looking 
for. For example, to get a single string value, we could call getString. For a nested object, 
there is getJSONObject. And if you are reading an array, use getJSONArray. 
Let us look at the forecast data that we get (see Figure 23). 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 26 of 116 
IIE Module Manual         
PROG7314 
Figure 23. Parsed Daily Forecast Example 
Every day, we have the date, minimum temperature, and maximum temperature. There are 
also indications of precipitation, and which icon would be relevant to display. But let us leave 
that out for now. 
Create a new class called Forecast to store the data and add fields for the date and 
temperatures. 
class Forecast { 
var date: String = "" 
var minimumTemperature: String = "" 
var maximumTemperature: String = "" 
} 
Let us add a function to our MainActivity to read the data from the JSON string: 
fun consumeJson(weatherJSON: String?) { 
if (fiveDaylList != null) { 
fiveDaylList.clear() 
} 
if (weatherJSON != null) { 
try { 
// get the root JSON object 
val rootWeatherData = JSONObject(weatherJSON) 
// find the daily forecasts array 
val fiveDayForecast =  
© The Independent Institute of Education (Pty) Ltd 2026      
Page 27 of 116 
IIE Module Manual         PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026      Page 28 of 116 
                   rootWeatherData.getJSONArray("DailyForecasts") 
 
            // get data from each entry in the array 
            for (i in 0 until fiveDayForecast.length()) { 
                val forecastObject = Forecast() 
                val dailyWeather = fiveDayForecast.getJSONObject(i) 
 
                // get date 
                val date = dailyWeather.getString("Date") 
                Log.i(LOGGING_TAG, "consumeJson: Date$date") 
                forecastObject.date = date 
 
                // get minimum temperature 
                val temperatureObject =  
                       dailyWeather.getJSONObject("Temperature") 
                val minTempObject = temperatureObject.getJSONObject("Minimum") 
                val minTemp = minTempObject.getString("Value") 
                Log.i(LOGGING_TAG, "consumeJson: minTemp$minTemp") 
                forecastObject.minimumTemperature = minTemp 
 
                // get maximum temperature 
                val maxTempObject = temperatureObject.getJSONObject("Maximum") 
                val maxTemp = maxTempObject.getString("Value") 
                Log.i(LOGGING_TAG, "consumeJson: maxTemp$maxTemp") 
                forecastObject.maximumTemperature = maxTemp 
                fiveDaylList.add(forecastObject) 
                binding.tvWeather.append( 
                    "Date: $date Min: $minTemp Max: $maxTemp\n" 
                ) 
            } 
        } catch (e: JSONException) { 
            e.printStackTrace() 
        } 
    } 
} 
 
This method makes use of two fields that need to be added to the class, too: 
 
var fiveDaylList =  mutableListOf<Forecast>() 
val LOGGING_TAG = "weatherDATA" 
 
And then we can call the consumeJson method from the thread: 
 
runOnUiThread { consumeJson(weather) } 
 
Figure 24 Shows what the program now looks like. Only the relevant information has been 
extracted and is now displayed in a somewhat usable format. 
 
 
IIE Module Manual         
PROG7314 
Figure 24. Slightly Better Text Display 
This method of reading JSON does work, but it requires a lot of steps that must be taken for 
every single piece of information. We do end up with an ArrayList of objects that contain the 
data we need, but the process is a little error-prone. 
As we said at the beginning of this section, there is always another way. So, let us look at 
Gson next. 
Using the Gson Library 
Gson is a library that can “be used to convert a JSON string to an equivalent Java object.” 
(Gson, 2020) It has functionality that is a lot like Newtonsoft’s Json.NET library for C#, if you 
are familiar with that. 
We need to add a dependency on the external module Gson. Open the Module: app 
build.gradle file – the same one that we added the buildConfigField to before. Add the following 
dependency: 
implementation "com.google.code.gson:gson:2.8.6" 
For us to use Gson, we need to have Kotlin objects that we can use when reading the data. 
Luckily, some tools can make the process easier. Using your favourite browser, go to 
https://www.json2kt.com/  
This is a website that can generate the code for us that we need, from sample data. So, copy 
the AccuWeather data again, and paste it into the Copy & Paste Json Here text area (see 
Figure 25). Then click Download Kotlin Files. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 29 of 116 
IIE Module Manual         
PROG7314 
Figure 25. Converting JSON to Kotlin code with https://www.json2kt.com/  
You will see that several classes are going to get generated and downloaded as a ZIP file. 
Create a new package called model and copy the downloaded classes into that folder. Make 
sure that you fix any missing imports (although there shouldn’t be any if the dependency was 
added), and that you change the package name for each of the files to match your package. 
Also, do double-check the Minimum and Maximum classes. The value field should be float, 
not int. The tool that we used may misidentify that, depending on the exact sample data that 
we give it. 
Now we can make use of Gson to convert the string into plain old Java objects (POJOs). This 
greatly simplifies the consumeJson method: 
fun consumeJson(weatherJSON: String?) { 
if (weatherJSON != null) { 
val gson = Gson() 
val weatherData =  
gson.fromJson<ExampleJson2KtKotlin>(weatherJSON, 
ExampleJson2KtKotlin::class.java) 
for(forecast in weatherData.DailyForecasts) { 
binding.tvWeather.append("Date: " + 
forecast.Date?.substring(0, 10) + 
" Min: " + 
forecast.Temperature?.Minimum?.Value + 
" Max: " + 
forecast.Temperature?.Maximum?.Value + 
"\n") 
} 
} 
} 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 30 of 116 
IIE Module Manual         
PROG7314 
We no longer need the list of Forecast objects, and once you delete that, you can also delete 
the Forecast class that we do not need anymore. 
The only thing that is different in the display of the app is that only the date portion of the date 
is displayed now, with none of the time information.  
But the rest all look the same. (See Figure 26.) So, our replacement of JSONObjects with 
Gson was successful. And our code is much more concise now.  
The app is still not user-friendly, though. So, let us improve the user interface next. 
Figure 26. New Screenshot after Gson 
Fragments 
What is a Fragment? 
“You can think of a fragment as a modular section of an activity, which has its own 
lifecycle, receives its own input events, and which you can add or remove while the activity 
is running (sort of like a ‘sub activity’ that you can reuse in different activities).” (Android 
Open Source Project, 2020c) 
Just like we use classes and methods to modularise our apps, we can make use of fragments 
to modularise our Android activities. 
In this section, we are going to split the main activity into two fragments: one for the five-day 
forecast and another for the AccuWeather logo. If we added more activities that display 
different data, we could then reuse the AccuWeather logo fragment on all of those. 
Creating a Blank Fragment 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 31 of 116 
IIE Module Manual         
PROG7314 
Let us start by creating a fragment for the AccuWeather logo.  
To create a new fragment: 
1. 
2. 
3. 
Right click on the app in the Project view, click New, then Fragment and finally 
Fragment (blank). 
Specify a name for the fragment (see Figure 27): AccuWeatherLogoFragment 
Click Finish. 
If your project is in a Git repository, you will be prompted to add the files to the repository. 
Figure 27. Creating a Fragment 
When the fragment is created, two files get opened automatically in Android Studio: a layout 
file and a Kotlin class. If you look at the code, you will recognise some similarities with 
activities. For example, there is an onCreate method. 
Copy the image view containing the AccuWeather logo from the main activity. You will notice 
that the image view will now have an ID of iv_accuweather2, to ensure that it is unique. 
Now we need to add the code for the OnClickListener that we had on the image view before. 
This works slightly differently for the fragment. Let us have a look at the generated code in the 
onCreateView method: 
override fun onCreateView( 
inflater: LayoutInflater, container: ViewGroup?, 
savedInstanceState: Bundle? 
): View? { 
// Inflate the layout for this fragment 
return inflater.inflate(R.layout.fragment_accu_weather_logo,  
container, false) 
} 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 32 of 116 
IIE Module Manual         
PROG7314 
This is the method that is responsible for creating the UI for the fragment. So, if we want to 
access components, we should do that here. The view that is created by 
inflater.inflate is the view that will contain the components in our layout. 
override fun onCreateView( 
inflater: LayoutInflater, container: ViewGroup?, 
savedInstanceState: Bundle? 
): View? { 
// Inflate the layout for this fragment 
val view = inflater.inflate(R.layout.fragment_accu_weather_logo,  
container, false) 
val imageView =            
view.findViewById<ImageView>(R.id.iv_accuweather2) 
// add an event handler to open the AccuWeather website 
imageView.setOnClickListener { 
val intent = Intent( 
Intent.ACTION_VIEW, 
Uri.parse("http://www.accuweather.com/") 
) 
startActivity(intent) 
} 
return view 
} 
Note that the findViewById method is now called on the view object, not on the fragment 
itself. And remember to change the id to iv_accuweather2 if you are copying the code! 
For this very simple fragment, we do not need to worry about anything else. So, let us see 
how to make use of it. 
Using the AccuWeather Logo Fragment 
In the layout of the main activity, delete the AccuWeather logo and the code to add the on
click listener.  
Open the main activity’s design. In design view, drag the fragment container view from the 
palette onto the layout, and choose our new fragment called AccuWeatherLogoFragment. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 33 of 116 
IIE Module Manual         
PROG7314 
Figure 28. Adding the Fragment 
Position the fragment in the same place where the logo was before. Double-check that all the 
constraints still make sense, including those of the text view.  
This fragment is very simple and does not require any data from its parent activity. So, we do 
not need to do anything more to get the image view to work as we want it to. 
If we run the app again, it should still look the same as it did before. But now the AccuWeather 
logo is a reusable component. 
Using a List Adapter 
We have not made any improvements to the user interface of the app yet. So, how can we 
improve the display? Let us make a fragment for the five-day weather, that displays a list of 
items. 
Create a new fragment, but this time choose Fragment (List). 
In the New Android Component dialog, specify DailyForecasts as the object kind, and 
DailyForecastsRecyclerViewAdapter as the adapter class name (see Figure 29). 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 34 of 116 
IIE Module Manual         
PROG7314 
Figure 29. Creating the List Fragment 
Several things get created for us during this process: 
 
 
 
 
 
fragment_item_list.xml – layout for the whole list of items that we are going to 
display. 
fragment_item.xml – layout for a single item in the list. 
DailyForecastsFragment – the main class of the fragment. 
DailyForecastsRecyclerViewAdapter – an adapter that is used when displaying a 
list item. 
dummy\DummyContent – a dummy model that we will be replacing completely. 
If we look at the data that we are parsing with Gson, the entries that we are looking for are of 
class DailyForecasts. So, each item in the list will represent a single DailyForecasts 
object. 
Creating the layout for an item 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 35 of 116 
IIE Module Manual         
PROG7314 
Open the layout fragment_item.xml. This is where we are going to put the components to 
display the data. We will have available to display the date, the minimum temperature, and 
the maximum temperature. 
Draw a layout that looks like the one in Figure 30. 
Figure 30. List Item Layout 
Updating the View Adapter 
Now that we have deleted the components that were in the list item layout, the 
DailyForecastsRecyclerViewAdapter will not compile. But that is okay, since it is the next 
thing that needs to be changed anyway. 
Change all references to PlaceholderItem to DailyForecasts. Once all of those have been 
changed, also delete the import for PlaceholderItem. 
This generated class has an inner class that manages a single item. You will see that it has a 
couple of generated TextView fields that are now no longer sensible. Replace those with fields 
for our three fields. 
inner class ViewHolder(binding: FragmentItemBinding) :  
RecyclerView.ViewHolder(binding.root) { 
val date: TextView = binding.tvDate 
val minimum: TextView = binding.tvMinimum 
val maximum: TextView = binding.tvMaximum 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 36 of 116 
IIE Module Manual         PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026      Page 37 of 116 
    override fun toString(): String { 
        return super.toString() + " '" + date.text + "'" 
    } 
} 
 
The last thing that remains to be changed in this class is the onBindViewHolder method, which 
sets the values onto the view: 
 
override fun onBindViewHolder(holder: ViewHolder, position: Int) { 
    val item = values[position] 
    holder.date.text = item.Date?.substring(0, 10) 
    holder.minimum.text = item.Temperature?.Minimum?.Value.toString() 
    holder.maximum.text = item.Temperature?.Maximum?.Value.toString() 
} 
 
Updating the Fragment 
 
The one thing that remains to be changed is the DailyForecastsFragment. You will notice 
that the line where the adapter is set no longer compiles. That is the line that sets the collection 
of data objects onto the view. We want to get our data from the AccuWeather web service, so 
copy the code to read that from the main activity. 
 
override fun onCreateView( 
    inflater: LayoutInflater, container: ViewGroup?, 
    savedInstanceState: Bundle? 
): View? { 
    val view = inflater.inflate(R.layout.fragment_item_list,  
                                container, false) 
 
    // Set the adapter 
    if (view is RecyclerView) { 
        with(view) { 
            layoutManager = when { 
                columnCount <= 1 -> LinearLayoutManager(context) 
                else -> GridLayoutManager(context, columnCount) 
            } 
            thread { 
                val weatherJSON = try { 
                    buildURLForWeather()?.readText() 
                } catch (e: Exception) { 
                    return@thread 
                } 
                if (weatherJSON != null) { 
                    val gson = Gson() 
                    val weatherData =  
                          gson.fromJson<ExampleJson2KtKotlin>( 
                                weatherJSON, 
                                ExampleJson2KtKotlin::class.java) 
                    activity?.runOnUiThread { 
                        adapter = DailyForecastsRecyclerViewAdapter( 
                                      weatherData.DailyForecasts) 
                    } 
                } 
            } 
        } 
    } 
IIE Module Manual         
PROG7314 
return view 
} 
One last bit of clean-up remains. Delete the DummyContent class and dummy package. 
Build the project now. Everything should compile at this point. If there are still any imports 
using DummyContent, remove those and then the build should succeed. 
Updating the Main Activity 
So far, so good. But the fragment is not used by the app yet. Open the main activity’s layout 
and replace the text view with the DailyForecastsFragment. 
In the code for the main activity, delete the tvWeather field and the line in the onCreate 
method that assigns it a value. Also, delete the async task and the call to it. The main activity 
should now look like this: 
class MainActivity : AppCompatActivity() { 
lateinit var binding: ActivityMainBinding 
override fun onCreate(savedInstanceState: Bundle?) { 
super.onCreate(savedInstanceState) 
binding = ActivityMainBinding.inflate(layoutInflater) 
setContentView(binding.root) 
} 
} 
If we run the app, the output now looks like this: 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 38 of 116 
IIE Module Manual         
PROG7314 
Figure 31. App with the List Display 
Fragments can communicate with each other, and there can be call backs to the activity that 
the fragment is used in. For more information about fragments, read the first item on the 
recommended additional reading list below. 
Recommended Additional Reading 
Android Open Source Project, 2020c. Fragments. [Online] Available at: 
https://developer.android.com/guide/components/fragments [Accessed 31 July 2023]. 
RIP Tutorial, n.d. Android: Open a URL in a browser. [Online] Available at: 
https://riptutorial.com/android/example/549/open-a-url-in-a-browser [Accessed 31 July 2023]. 
Tagliaferri, L., 2016. An Introduction to JSON. [Online] Available at: 
https://www.digitalocean.com/community/tutorials/an-introduction-to-json [Accessed 31 July 
2023]. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 39 of 116 
IIE Module Manual         
PROG7314 
Recommended Digital Engagement 
Work through this code lab: 
Get data from the internet 
https://developer.android.com/codelabs/basic-android-kotlin-training-getting-data-internet#0  
Activities 
Complete the activities on Arc. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 40 of 116 
IIE Module Manual         
PROG7314 
Learning Unit 2: External Libraries 
Learning Objectives: 
 
 
 
 
 
 
 
Determine which external libraries to use. 
Use an external library to solve a programming 
problem. 
Connect to a geolocation service. 
Display information from a geolocation service. 
Connect to a social media service. 
Use a social media service. 
My notes 
Determine which SDKs are available to connect to an 
app to. 
 
Connect an app to an appropriate SDK. 
Material used for this learning unit: 
 
GitHub repository: LearningUnit2 
How to prepare for this learning unit: 
 
Make sure that you have the GitHub source code 
available and that your Android Studio is up to date. 
Introduction 
By the end of Learning Unit 1, we had built a working weather app. But it is not a very exciting 
one since it only displays the weather for Durban for the next five days. What if we live in 
Johannesburg or Cape Town? And why can we not search for the weather of another city? In 
short, the app still needs some features that users have come to expect from weather apps. 
In this learning unit, we will create a new app so we can start with a tabbed view. We will make 
use of the relevant template when we create the app. Once we have the tabbed user interface 
in place, we can copy the fragments from the previous learning unit’s app. We will then be 
ready to make use of a different library to read the data from the AccuWeather web services. 
Later in the learning unit, we will add geolocation services and sharing to social media. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 41 of 116 
IIE Module Manual         
PROG7314 
Connecting to External Libraries 
What is a Library? 
“A software library refers to a collection of files, programs, routines, scripts, or functions 
that can be referenced in the programming code.” (Computer Hope, 2017) 
A library is code that is written by somebody else, that you can make use of in your apps. 
Libraries can be free and open source, but there are also commercial libraries. 
In the Android ecosystem, there are too many libraries to name. But a few interesting ones 
include CalendarView, Bubble Navigation and SmoothBottomBar. Read (Bialas, 2020) for a 
list of 30 awesome libraries. 
We have made use of a library already, without much fanfare – Gson. Remember the 
dependency that we added to the app’s build.gradle file? That is a dependency on an external 
library. Gradle is responsible for finding online and downloading this open-source library, so it 
can be included in the build. 
Creating the Tabbed User Interface 
Create a new project, this time using the Tabbed Activity project template. The wizard creates 
an activity that uses the CoordinatorLayout to switch between different tabs (see Figure 32). 
Figure 32. Components for the Tabbed UI 
Change the background of the CoordinatorLayout to an image and make the AppBarLayout 
and tabs transparent. If you run the app, it should look like the screenshot shown in Figure 
33. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 42 of 116 
IIE Module Manual         
PROG7314 
Figure 33. Tabs with Background 
We can reuse some of the code that we created in Learning Unit 1, so copy across everything 
except the main activity. Remember to also copy any related resources, and to add the API 
key to the project, and to update the package names where applicable. 
At this point, your code should look like the code for the tag LU2-CopiedFiles in the 
repository. 
Remember to add a permission to access the Internet to the manifest file. 
Include the AccuWeatherLogoFragment in the AppBarLayout between the title and the tabs. 
This app is going to have three tabs: TODAY, 5-DAY FORECAST and CITY WEATHER. We 
already have an implementation for the five-day forecast that we can include. The generated 
class SectionsPagerAdapter (in the package ui.main) is where we need to go to manage 
the fragments that are displayed. 
In the strings.xml file, make sure that you have the following three entries: 
<string name="tab_text_1">TODAY</string> 
<string name="tab_text_2">5-DAY FORECAST</string> 
<string name="tab_text_3">CITY WEATHER</string> 
The first two should already be there, just with default values. The third one you will need to 
add. Then we have everything ready to update the SectionsPagerAdapter: 
1. 
2. 
3. 
Making use of the string resources, add all three tab titles to the TAB_TITLES array. 
Update the implementation of getCount to return 3. 
Add a switch statement to getItem to create the forecasts fragment. 
For now, we only have an actual fragment for tab position 1 – the DailyForecastsFragment. 
So, use the generated PlaceholderFragment for both the other positions for now. 
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : 
FragmentPagerAdapter(fm) { 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 43 of 116 
IIE Module Manual         
PROG7314 
override fun getItem(position: Int): Fragment { 
// getItem is called to instantiate the fragment for the given page. 
when (position) { 
1 -> return DailyForecastsFragment() 
} 
// Return a PlaceholderFragment. 
return PlaceholderFragment.newInstance(position + 1) 
} 
override fun getPageTitle(position: Int): CharSequence? { 
return context.resources.getString(TAB_TITLES[position]) 
} 
override fun getCount(): Int { 
// Show 3 total pages. 
return 3 
} 
} 
The superclass FragmentPagerAdapter keeps all the fragments in memory. This means that 
switching between them will be fast. 
Running the app reveals that the text is not readable on the background image. So, make the 
background of the list semi-transparent and change the font to be more visible. The app now 
looks like Figure 34. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 44 of 116 
IIE Module Manual         
PROG7314 
Figure 34. 5-Day Forecast Tab Included 
Now we want to make use of libraries to improve the way in which we read the data. 
Retrofit 
We will be making use of a library when communicating with an HTTP API: Retrofit. Let us 
see how the creators of this library describe it. 
“Retrofit turns your HTTP API into a Java interface.” (Square, Inc., n.d.) 
We use Retrofit to implement an interface that makes calling the AccuWeather APIs easier. 
We are going to call multiple services eventually, for example, to get the current weather.  
So, in anticipation of that, rename the ExampleJson2KtKotlin class in the model package to 
FiveDayForecast. Right-click the file, choose Refactor and then Rename. That will 
automatically rename usages of the class too. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 45 of 116 
IIE Module Manual         
PROG7314 
Include the following dependencies in the app module’s build.gradle file: 
implementation "com.squareup.retrofit2:retrofit:2.9.0" 
implementation "com.squareup.retrofit2:converter-gson:2.9.0"     
Remember to build the project to ensure that Gradle fetches these libraries. 
Using Retrofit 
Let us start using Retrofit. The documentation explaining how to use it is available at 
https://square.github.io/retrofit/ 
If you read that page, you will see that we need to define an interface that describes how to 
access the service that we are going to call. Create a package called retrofit, and inside that, 
create an interface called IAccuWeather. 
How do we know what to create in that interface? Well, we look at the API that we want to call, 
as shown in Figure 35. 
Figure 35. Parts of the AccuWeather URL 
The example on the Retrofit website shows that the base URL is passed to the Retrofit class. 
And the interface then specifies everything that goes beyond that, using annotations. 
Let us look at the annotations for the five-day forecast service as another example. 
package za.ac.iie.opsc.geoweather.retrofit 
import retrofit2.http.GET 
import retrofit2.http.Path 
import retrofit2.http.Query 
import za.ac.iie.opsc.geoweather.model.FiveDayForecast 
interface IAccuWeather { 
/** 
* Get the five-day forecast for a specific location key 
* @param locationKey The key for the location 
* @param apiKey The api key to use 
* @param metric Whether to get the data in metric units of  
*               
measurement 
* @return The five-day forecast 
*/ 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 46 of 116 
IIE Module Manual         PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026      Page 47 of 116 
    @GET("forecasts/v1/daily/5day/{locationKey}") 
    suspend fun getFiveDayForecast( 
        @Path("locationKey") locationKey: String?, 
        @Query("apikey") apiKey: String?, 
        @Query("metric") metric: Boolean 
    ): FiveDayForecast? 
 
} 
 
The @GET annotation specifies how to build up the URL for the specific service that we want 
to call. The forecasts/v1/daily/5day/ part will always be the same, regardless of which 
location’s weather we are getting. But the {locationKey} will change, so that is indicated 
using {}. 
 
Where does the value for {locationKey} come from? From the method parameter with the 
corresponding annotation. Since it forms part of the path, the annotation is 
@Path("locationKey"). 
 
The API key, as well as the parameter indicating whether we want the answer in metric units 
of measurement, are both query parameters. And that is why the annotation is then 
@Query("apikey"). The string that is provided here must be exactly what the service expects 
for the key of the query parameter. 
 
The return type of the method specifies what Retrofit should expect from the service. Here we 
are using our newly renamed FiveDayForecast class. 
 
So, the method header shown above has all the information that Retrofit needs at runtime to 
call the service. 
 
Side Note: Annotations in Java and Kotlin can have different retention policies. These can 
be: 
 
 SOURCE: processed when the program is compiled and then discarded. 
 CLASS: also processed at compile time and stored in the class files but not available 
at runtime. 
 RUNTIME: available for use at runtime. (java2s.com, n.d.) 
 
Java annotations can be used in Kotlin. (Kotlin Foundation, 2023) 
 
If we look at the definition of the annotations used by Retrofit (defined in Java), we see that 
these are defined as RUNTIME. For example, here is the definition of the @Path annotation: 
 
@Documented 
@Retention(RUNTIME) 
@Target(PARAMETER) 
public @interface Path { 
  String value(); 
 
This means that Retrofit inspects the interface at runtime using reflection. Based on what is 
found in the methods, the correct code is then called in the background by Retrofit. 
IIE Module Manual         
PROG7314 
Now that we have specified everything that is needed to find the service, let us see how to call 
it. Create a new singleton class called RetrofitClient, that creates and configures the client 
instance and service. 
package za.ac.iie.opsc.geoweather.retrofit 
import retrofit2.Retrofit 
import retrofit2.converter.gson.GsonConverterFactory 
object RetrofitClient { 
var retrofit: Retrofit? = null 
get() { 
if (field == null) field = Retrofit.Builder() 
.baseUrl("https://dataservice.accuweather.com/") 
.addConverterFactory(GsonConverterFactory.create()) 
.build() 
return field 
} 
var weatherService: IAccuWeather? = null 
get() { 
if (field == null) field =  
retrofit?.create(IAccuWeather::class.java) 
return field 
} 
} 
Here we see that we specify the baseUrl, which we know already from the URLs that we called 
earlier. Then, we specify something that may sound familiar: the converter factory uses Gson. 
This tells Retrofit that we are expecting to receive the answer in JSON format, and that it 
should use Gson to parse it into the right kind of object.  
© The Independent Institute of Education (Pty) Ltd 2026      
Page 48 of 116 
IIE Module Manual         PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026      Page 49 of 116 
Using RxJava 
 
Now we are ready to make the call to the AccuWeather servers. To make the asynchronous 
service call, we create a class inheriting from ViewModel called DailyForecastsViewModel. 
 
package za.ac.iie.opsc.geoweather 
 
import androidx.lifecycle.LiveData 
import androidx.lifecycle.MutableLiveData 
import androidx.lifecycle.ViewModel 
import androidx.lifecycle.viewModelScope 
import kotlinx.coroutines.launch 
import za.ac.iie.opsc.geoweather.model.DailyForecasts 
import za.ac.iie.opsc.geoweather.retrofit.RetrofitClient 
 
class DailyForecastsViewModel : ViewModel() { 
 
    private var _fiveDayForecast =  
                     MutableLiveData<List<DailyForecasts>>() 
    var fiveDayForecast: LiveData<List<DailyForecasts>> =  
                     _fiveDayForecast 
 
    fun getFiveDayForecast(locationKey: String) { 
        viewModelScope.launch { 
            val weatherData = RetrofitClient.weatherService?. 
                    getFiveDayForecast(locationKey, 
                        BuildConfig.ACCUWEATHER_API_KEY, false) 
            _fiveDayForecast.value = weatherData?.DailyForecasts 
        } 
    } 
} 
 
The ViewModel has a coroutine called viewModelScope that can be used to make the 
potentially long call to the webservice. 
 
The call that we want to replace is in the DailyForecastsFragment class, so let's go there. 
The onCreateView method becomes much simpler now. 
 
override fun onCreateView( 
    inflater: LayoutInflater, container: ViewGroup?, 
    savedInstanceState: Bundle? 
): View? { 
    val view = inflater.inflate(R.layout.fragment_item_list,  
                                container, false) 
 
    // Set the adapter 
    if (view is RecyclerView) { 
        with(view) { 
            layoutManager = when { 
                columnCount <= 1 -> LinearLayoutManager(context) 
                else -> GridLayoutManager(context, columnCount) 
            } 
 
            // call the webservice 
            viewModel.getFiveDayForecast("305605") 
 
IIE Module Manual         
PROG7314 
// observe the list in the model for changes 
val weatherObserver = Observer<List<DailyForecasts>> {  
newWeather -> 
adapter =  
DailyForecastsRecyclerViewAdapter(newWeather) 
} 
viewModel.fiveDayForecast.observe(viewLifecycleOwner,  
weatherObserver) 
} 
} 
return view 
} 
We call the method getFiveDayForecast on the model to read the data, with the location key 
for Durban for now. And then we observe the list in the model to know when the values change. 
Now the call is made using the libraries!  
Getting Today’s Weather 
Now we want to get the current conditions for Durban, to display on the TODAY tab. Let us 
look at the AccuWeather API documentation. 
Figure 36. Current Conditions URL from (AccuWeather, Inc., 2020d) 
Let us see what the data looks like when it is formatted (see Figure 37). 
Here are some quick tips to do the implementation. You have all the information already about 
how to do these steps in general, but there are a few things you need to know about. 
The data that we get back is an array containing one entry. When we make the call, we need 
to remember that. 
Make a call to the URL, copy the JSON response and generate more entities using the code 
generator we used before: https://www.json2kt.com/ 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 50 of 116 
IIE Module Manual         
PROG7314 
Figure 37. Current Conditions Example Data 
In the model package, create a new package called currentweather. Create the classes in the 
new package. If you see a class called ExampleJson2KtKotlin (zero), make that 
CurrentWeather instead. And ignore the Root class for this one. 
Then add another method to the IAccuWeather interface: 
/** 
* Get the current conditions at a location. 
* @param locationKey The key for the location 
* @param apiKey The api key to use 
* @return The current conditions at the location 
*/ 
@GET("currentconditions/v1/{locationKey}") 
suspend fun getCurrentConditions( 
@Path("locationKey") locationKey: String?, 
@Query("apikey") apiKey: String? 
): List<CurrentWeather?>? 
Note that the method will return a List of CurrentWeather objects. 
Add another fragment called CurrentWeatherFragment and display the weather text and 
current temperature.  
© The Independent Institute of Education (Pty) Ltd 2026      
Page 51 of 116 
IIE Module Manual         
PROG7314 
Figure 38. Displaying the Current Weather 
Using Geolocation Services in an App 
Now this is all working very well, but we are still stuck with getting Durban’s weather data. 
What if we wanted to get the weather for wherever the user is located? We could ask the user 
to configure that when the app first starts up. But that is not a great user experience. 
So, we need to ask the phone where the user is! And we can do that using the class 
FusedLocationProviderClient. (Droid By Me, 2018) 
There are a few steps in the process for make this work, as shown in Figure 39. 
Get location access permissions
Get the location
Search for locationKey for the 
location
Pass the locationKey to the 
fragments when created
Figure 39. Steps to Working with the User’s Location 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 52 of 116 
IIE Module Manual         
PROG7314 
Get Permissions 
Add the following dependency to the app module’s build.gradle file, and remember to build the 
project after that. 
implementation 'com.google.android.gms:play-services-location:17.0.0' 
Add these two permissions to the AndroidManifest.xml file: 
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/> 
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/> 
We need to get the location just once – it will be the same for all the tabs. So, let us add the 
code to the MainActivity to request the location. 
Add the following field to MainActivity: 
private var fusedLocationProviderClient:  
FusedLocationProviderClient? = null 
In the onCreate method, get the fused location provider and store that in the 
fusedLocationProviderClient field. And then call a method we will define in a second to 
request the location. 
We will only set up the fragments after we have done everything that is required. So, that will 
get moved to another method, leaving us with an onCreate method that looks like the one 
below. 
override fun onCreate(savedInstanceState: Bundle?) { 
super.onCreate(savedInstanceState) 
binding = ActivityMainBinding.inflate(layoutInflater) 
setContentView(binding.root) 
fusedLocationProviderClient = 
LocationServices.getFusedLocationProviderClient(this) 
checkPermissionsAndRequestLocation(); 
val fab: FloatingActionButton = binding.fab 
fab.setOnClickListener { view -> 
Snackbar.make(view, "Replace with your own action", 
Snackbar.LENGTH_LONG) 
.setAction("Action", null).show() 
} 
} 
Accessing the user’s location is a permission that has security implications. So, this is one of 
the permissions where one must explicitly get the user to agree for Android 6 and above. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 53 of 116 
IIE Module Manual         PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026      Page 54 of 116 
If we have either course or fine location permissions already, then just continue to ask for the 
location. Otherwise, request the permissions and wait for the call back before proceeding. 
 
private fun checkPermissionsAndRequestLocation() { 
    val hasFineLocationPermission =  
        ActivityCompat.checkSelfPermission( 
        this, "android.permission.ACCESS_FINE_LOCATION" 
    ) 
    val hasCourseLocationPermission =  
        ActivityCompat.checkSelfPermission( 
        this, "android.permission.ACCESS_COARSE_LOCATION" 
    ) 
    if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED  
        && hasCourseLocationPermission !=  
           PackageManager.PERMISSION_GRANTED 
    ) { 
        val permissions = arrayOf<String>( 
            "android.permission.ACCESS_FINE_LOCATION", 
            "android.permission.ACCESS_COARSE_LOCATION" 
        ) 
        // Request permission - this is asynchronous 
        ActivityCompat.requestPermissions(this, permissions, 0) 
    } else { 
        // We have permission, so now ask for the location 
        getLocationAndCreateUI() 
    } 
} 
 
The answer whether the user allowed the location access will be communicated back to us 
via the onRequestPermissionsResult method. Let us look at how we handle that. 
 
override fun onRequestPermissionsResult( 
    requestCode: Int, 
    permissions: Array<String?>, 
    grantResults: IntArray 
) { 
    super.onRequestPermissionsResult( 
        requestCode, permissions, 
        grantResults 
    ) 
    // is this for our request? 
    if (requestCode == 0) { 
        if (grantResults.size > 0 && 
            (grantResults[0] ==  
                    PackageManager.PERMISSION_GRANTED || 
                    grantResults[1] ==  
                    PackageManager.PERMISSION_GRANTED) 
        ) { 
            getLocationAndCreateUI() 
        } else { 
            Toast.makeText( 
                this@MainActivity, 
                "Location permission denied", 
                Toast.LENGTH_SHORT 
            ).show() 
        } 
    } 
} 
 
IIE Module Manual         PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026      Page 55 of 116 
Requesting the Location 
 
To request the location, we need to create a request as well as a callback. So, there is yet 
another asynchronous process happening here. In our getLocationAndCreateUI method, 
we just make the call and then we wait for the callback to get called. 
 
@SuppressLint("MissingPermission") 
private fun getLocationAndCreateUI() { 
    val locationRequest: LocationRequest = buildLocationRequest() 
    val locationCallback: LocationCallback = buildLocationCallBack() 
    fusedLocationProviderClient!!.requestLocationUpdates( 
        locationRequest, 
        locationCallback, Looper.myLooper() 
    ) 
} 
 
When creating the request, we can set a couple of different fields, which will change the 
behaviour of the fused location provider. 
 
private fun buildLocationRequest(): LocationRequest { 
    val locationRequest = LocationRequest() 
    
locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY) 
    locationRequest.setInterval(5000) 
    locationRequest.setFastestInterval(3000) 
    locationRequest.setSmallestDisplacement(10.0f) 
    return locationRequest 
} 
 
The other parameter is the callback, which will be called with the location when the process is 
complete. Let us just create a basic implementation for now, so we can see what the logged 
result is. 
 
private fun buildLocationCallBack(): LocationCallback { 
    return object : LocationCallback() { 
        override fun onLocationResult(locationResult: LocationResult) { 
            super.onLocationResult(locationResult) 
            val location: Location = locationResult.lastLocation 
            Log.i("LocationResult", "onLocationResult: $location") 
 
            // TODO: Get the locationKey from AccuWeater 
            val sectionsPagerAdapter = SectionsPagerAdapter( 
                this@MainActivity, 
                supportFragmentManager 
            ) 
            val viewPager: ViewPager = binding.viewPager 
            viewPager.adapter = sectionsPagerAdapter 
            val tabs: TabLayout = binding.tabs 
            tabs.setupWithViewPager(viewPager) 
        } 
    } 
} 
 
2020-06-28 21:39:26.212 18340-18340/za.ac.iie.opsc.geoweather 
I/LocationResult: onLocationResult: Location[fused 37.421998, 
IIE Module Manual         
PROG7314 -122.084000 hAcc=20 et=+12h21m55s628ms alt=5.0 vel=0.0 bear=90.0 
vAcc=40 sAcc=??? bAcc=??? {Bundle[mParcelledData.dataSize=52]}] 
We are successfully reading the location set by the emulator – Mountain View in California by 
default. You can change the location reported by the emulator in its settings. 
Getting the Location Key from AccuWeather 
Remember in Learning Unit 1, section 0 (page 15) when we requested the locationKey for 
Durban using a web browser? Well, now we need to make a similar request from our app, but 
based on the longitude and latitude of the user this time. Let us look again at an extract from 
the API flow diagram to see what the call should look like. 
Figure 40. Geo-Position Search (AccuWeather, Inc., 2020c) 
If we wanted to get the location information for the position that was logged before, the URL 
would be as follows. Call it with your own API key, so you can generate the classes for the 
call. 
http://dataservice.accuweather.com/locations/v1/cities/geoposition/search?q=37.421998,
122.084000&apikey=  
Figure 41. Location Data for Mountain View 
Looking at the data in a parsed format, this looks good, right? We were expecting Mountain 
View, and we got Mountain View. So, the call worked. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 56 of 116 
IIE Module Manual         PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026      Page 57 of 116 
Using https://www.json2kt.com/ to generate entities again, and this time create those in the 
package model.location. Call the root class AccuWeatherLocation this time. 
 
Add another method to the IAccuWeather interface: 
 
/** 
 * Gets the location data based on the geoposition. 
 * @param geoposition The geoposition as latitude,longitude 
 * @param apiKey The api key to use 
 * @return The location data for the geoposition 
 */ 
@GET("locations/v1/cities/geoposition/search") 
suspend fun getLocationByPosition( 
    @Query("q") geoposition: String?, 
    @Query("apikey") apiKey: String? 
): AccuWeatherLocation? 
 
Now we can change the location call back to call the AccuWeather Service. 
 
private fun buildLocationCallBack(): LocationCallback { 
    return object : LocationCallback() { 
        override fun onLocationResult(locationResult: LocationResult) { 
            super.onLocationResult(locationResult) 
            val location: Location = locationResult.lastLocation 
            Log.i("LocationResult", "onLocationResult: $location") 
            model.getLocation("${location.latitude}," + 
                    "${location.longitude}") 
 
            // observe the list in the model for changes 
            val weatherObserver = Observer<AccuWeatherLocation> { 
                location-> displayData(location) 
            } 
            model.location.observe(this@MainActivity,  
                                   weatherObserver) 
        } 
    } 
} 
 
Passing the Location Key to Fragments 
 
The last step that remains is to pass the location key and the location name to the fragments 
when they get created. 
 
The SectionsPagerAdapter is responsible for creating the fragments, so let us pass it the 
information in its constructor. 
 
/** 
 * A [FragmentPagerAdapter] that returns a fragment corresponding to 
 * one of the sections/tabs/pages. 
 */ 
class SectionsPagerAdapter(private val context: Context, 
                           fm: FragmentManager, 
                           private val locationName: String, 
                           private val locationKey: String) : 
IIE Module Manual         
PROG7314 
Then we can do the last part of the work in the MainActivity – now, finally, displaying the 
data. 
fun displayData(location: AccuWeatherLocation) { 
val sectionsPagerAdapter = SectionsPagerAdapter( 
this@MainActivity, 
supportFragmentManager, 
location.LocalizedName.toString(), 
location.Key.toString() 
) 
val viewPager: ViewPager = binding.viewPager 
viewPager.adapter = sectionsPagerAdapter 
val tabs: TabLayout = binding.tabs 
tabs.setupWithViewPager(viewPager) 
} 
Today’s Weather 
The SectionPagerAdapter does not do anything with the two values yet though. There is a 
bit of a trick to this. We cannot just pass the two parameters to the constructors of the fragment 
since the fragment has to have a no-parameters constructor. But the code generated in the 
CurrentWeatherFragment (which was a blank fragment) has a clue. 
The newInstance method in the companion object takes two parameters, which it adds to a 
bundle and passes to the fragment that it created. Let us make use of those parameters. 
Rename param1 everywhere to locationName, and param2 to locationKey. 
If you renamed everything correctly, then the onCreate method should now look like this: 
override fun onCreate(savedInstanceState: Bundle?) { 
super.onCreate(savedInstanceState) 
arguments?.let { 
locationName = it.getString(ARG_PARAM1) 
locationKey = it.getString(ARG_PARAM2) 
} 
} 
Now you can use the locationKey field’s value (add .toString()) to get the data instead of 
the hard coded 305605. 
And add a new text view to display the name too. 
Lastly, in SectionPagerAdapter call newInstance instead of the constructor, and pass the 
two values: 
when (position) { 
0 -> return CurrentWeatherFragment.newInstance(locationName,  
locationKey) 
If we run the app again, we see the weather for Mountain View right now. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 58 of 116 
IIE Module Manual         
PROG7314 
Figure 42. Today’s Weather Using Geo Position 
5-Day Forecast 
Do a similar implementation for the DailyForecastsFragment too. If you need help with the 
implementation, have a look at the GitHub repository to see how it was done there. 
Searching for a City’s Weather 
The last tab that we have not implemented yet, is the City Weather one. On this tab, we want 
to allow the user to enter the name of a city, and then display the current weather for that city. 
Start by creating a new fragment and adding it to SectionPagerAdapter. 
For the user interface, we want to use the Material SearchBar library. “This beautiful and easy 
to use library will help to add Lollipop Material Design SearchView in your project.” (mancj, 
2020) 
To get started with that, include the following dependency in the app module’s build.gradle 
file: 
implementation 'com.github.mancj:MaterialSearchBar:0.8.5' 
This dependency will not work yet though – we need to add a Maven repository to the project’s 
settings.gradle file. The new line is highlighted in green below. 
dependencyResolutionManagement { 
repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS) 
repositories { 
google() 
mavenCentral() 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 59 of 116 
IIE Module Manual         
PROG7314 
maven{url 'https://jitpack.io'} 
} 
} 
Switch to the code view in the layout of the city weather fragment, and update the XML as 
follows: 
<?xml version="1.0" encoding="utf-8"?> 
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android" 
xmlns:tools="http://schemas.android.com/tools" 
xmlns:app="http://schemas.android.com/apk/res-auto" 
android:layout_width="match_parent" 
android:layout_height="match_parent" 
tools:context=".CityWeatherFragment"> 
<com.mancj.materialsearchbar.MaterialSearchBar 
android:id="@+id/sb_city_name" 
app:mt_searchBarColor="@android:color/transparent" 
app:mt_textColor="@android:color/white" 
style="@style/MaterialSearchBarLight" 
app:mt_hint="City Name" 
android:layout_width="match_parent" 
android:layout_height="wrap_content"/> 
</FrameLayout> 
Now change the layout to a vertical LinearLayout. Set the background of the search bar to 
transparent white, and the text colour to black. If you run the app now, it should look like shown 
in Figure 43. 
Figure 43. App with the Search Bar 
Add some TextViews to display the name of the city, the weather text, and the current 
temperature. 
To populate the search field with values, we can call the Top Cities List API from AccuWeather 
to get the 150 top cities in the world. Try to do that on your own – you know everything that is 
needed already. Then compare your code with what is in the GitHub repository. Here are some 
hints: 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 60 of 116 
IIE Module Manual         
PROG7314 
 
 
Cities are locations. 
The GmtOffset field in TimeZone should be float, not int. 
Making a call to a webservice is a (relatively) slow process. So, when we get the data for the 
top 150 cities, we are going to store the city names and their corresponding locationKey values 
in a hash map. Then, we do not need to make yet another call to a service to get the 
locationKey for the city that the user selects. 
Create a new view model class to read the cities data from the webservice and contain a hash 
map to store the data. The model will also read the weather data for the city once we have it 
selected. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 61 of 116 
IIE Module Manual         PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026      Page 62 of 116 
package za.ac.iie.opsc.geoweather 
 
import androidx.lifecycle.LiveData 
import androidx.lifecycle.MutableLiveData 
import androidx.lifecycle.ViewModel 
import androidx.lifecycle.viewModelScope 
import kotlinx.coroutines.launch 
import za.ac.iie.opsc.geoweather.model.currentweather.CurrentWeather 
import za.ac.iie.opsc.geoweather.model.location.AccuWeatherLocation 
import za.ac.iie.opsc.geoweather.retrofit.RetrofitClient 
 
class CityWeatherModel : ViewModel() { 
 
    // cities list 
    private var _citiesList = MutableLiveData<List<AccuWeatherLocation?>>() 
    var citiesList: LiveData<List<AccuWeatherLocation?>> = _citiesList 
    var citiesHashMap = HashMap<String, AccuWeatherLocation>() 
 
    // current weather 
    private var _currentWeather = MutableLiveData<CurrentWeather>() 
    var currentWeather: LiveData<CurrentWeather> = _currentWeather 
 
    fun getCityList() { 
        viewModelScope.launch { 
            val citiesListFromApi = RetrofitClient.weatherService?. 
                getTop150Cities(BuildConfig.ACCUWEATHER_API_KEY) 
            if (citiesListFromApi != null) { 
                populateHashmap(citiesListFromApi) 
                _citiesList.value = citiesListFromApi!! 
            } 
        } 
    } 
 
    private fun populateHashmap(citiesListFromApi:  
                                     List<AccuWeatherLocation?>?) { 
        citiesHashMap.clear() 
        if (citiesListFromApi != null) { 
            for (city in citiesListFromApi) { 
                citiesHashMap[city?.LocalizedName!!] = city 
            } 
        } 
    } 
 
    fun getCurrentWeather(locationKey: String) { 
        viewModelScope.launch { 
            val weatherData = RetrofitClient.weatherService?. 
            getCurrentConditions(locationKey, 
                BuildConfig.ACCUWEATHER_API_KEY) 
            _currentWeather.value = weatherData?.get(0) 
        } 
    } 
} 
 
And then we can add the code in the fragment to get the data for the city that we search for. 
 
class CityWeatherFragment : Fragment() { 
    private var viewModel = CityWeatherModel() 
    private lateinit var searchBar: MaterialSearchBar 
    private lateinit var cityName: TextView 
    private lateinit var weatherText: TextView 
    private lateinit var temperature: TextView 
 
    override fun onCreate(savedInstanceState: Bundle?) { 
        super.onCreate(savedInstanceState) 
    } 
IIE Module Manual         PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026      Page 63 of 116 
 
    override fun onCreateView( 
        inflater: LayoutInflater, container: ViewGroup?, 
        savedInstanceState: Bundle? 
    ): View? { 
        // Inflate the layout for this fragment 
        val view = inflater.inflate(R.layout.fragment_city_weather, container, 
false) 
        viewModel.getCityList() 
        searchBar = view.findViewById<MaterialSearchBar>(R.id.sb_city_name) 
        cityName = view.findViewById<TextView>(R.id.tvCityName) 
        weatherText = view.findViewById<TextView>(R.id.tvWeatherText) 
        temperature = view.findViewById<TextView>(R.id.tvCurrentTemperature) 
 
        setupViewModel() 
        setupSearchBar() 
 
        return view 
    } 
 
    private fun setupViewModel() { 
        // observe the list in the model for changes 
        val citiesObserver = Observer<List<AccuWeatherLocation?>> {  
            newCities -> 
            searchBar.lastSuggestions =  
                viewModel.citiesHashMap.keys.toList().sorted() 
        } 
        viewModel.citiesList.observe(viewLifecycleOwner, citiesObserver) 
 
        // observe the list in the model for changes 
        val weatherObserver = Observer<CurrentWeather> { weather -> 
            run { 
                weatherText.text = weather.WeatherText 
                temperature.text = "${weather.Temperature?.Metric?.Value} " + 
                        "${weather.Temperature?.Metric?.Unit}" 
            } 
        } 
        viewModel.currentWeather.observe(viewLifecycleOwner, weatherObserver) 
    } 
 
    private fun setupSearchBar() { 
        searchBar.isEnabled = true 
        searchBar.addTextChangeListener(object : TextWatcher { 
            override fun beforeTextChanged( 
                s: CharSequence, start: Int, 
                count: Int, after: Int 
            ) { 
            } 
 
            override fun onTextChanged( 
                s: CharSequence, start: Int, 
                before: Int, count: Int 
            ) { 
                val suggest: MutableList<String> = ArrayList() 
                for (city in viewModel.citiesHashMap.keys) { 
                    if (city.lowercase(Locale.getDefault()).contains( 
                            searchBar.getText().toLowerCase() 
                        ) 
                    ) suggest.add(city) 
                } 
                Collections.sort(suggest) 
                searchBar.setLastSuggestions(suggest) 
            } 
 
            override fun afterTextChanged(s: Editable) {} 
        }) 
IIE Module Manual         PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026      Page 64 of 116 
 
        searchBar.setOnSearchActionListener( 
            object : OnSearchActionListener { 
                override fun onSearchStateChanged(enabled: Boolean) {} 
                override fun onSearchConfirmed(text: CharSequence) { 
                    Log.d("Search:", text.toString() + "") 
                    cityName.text = text.toString() 
                    viewModel.getCurrentWeather( 
                        viewModel.citiesHashMap[text.toString()]?.Key!!) 
                } 
                override fun onButtonClicked(buttonCode: Int) {} 
            }) 
    } 
} 
 
Adding Social Media Services 
 
Users enjoy sharing their experiences on social media. If the weather is particularly extreme, 
they might very well want to share that on social media. And it would be great if they could 
share it using our app, since that will create awareness of this amazing weather app that is 
out there! 
 
You might have noticed the floating action button that was created by the project wizard, 
together with the tabs. Well, now it will finally have a purpose: sharing a screenshot on social 
media. Change the image of the button to the sharing icon and change the button’s ID to 
fab_share. 
 
Create a new utility class called ProcessImageUtil. The class should have the following 
methods: to create a screenshot, save it to a file, and then share it. 
 
object ProcessImageUtil { 
    /** 
     * Convert a view to a Bitmap. 
     * @param view The view to convert. 
     * @return The converted Bitmap. 
     */ 
    fun takeScreenshot(view: View): Bitmap { 
        val screenView: View = view.rootView 
        val bitmap: Bitmap =  
            screenView.drawToBitmap(Bitmap.Config.ARGB_8888) 
        return bitmap 
    } 
 
    /** 
     * Write a screenshot bitmap to a folder. 
     * @param context The activity that this is called from. 
     * @param bitmap The bitmap to write. 
     * @param fileName The name of the file to create. 
     */ 
    fun storeScreenshot( 
        context: Context, bitmap: Bitmap, 
        fileName: String 
    ) { 
        // Get the application's folder - no permissions to write 
IIE Module Manual         PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026      Page 65 of 116 
        val directory: File? = context.getExternalFilesDir(null) 
        if (directory != null && !directory.exists()) { 
            val isCreated: Boolean = directory.mkdirs() 
            Log.d("MakingDir", "Created: $isCreated") 
        } 
        val captureImage = File(directory, "$fileName.PNG") 
        try { 
            val writeImage = FileOutputStream(captureImage) 
            bitmap.compress(Bitmap.CompressFormat.PNG, 85,  
                            writeImage) 
            writeImage.flush() 
            writeImage.close() 
        } catch (e: Exception) { 
            e.printStackTrace() 
        } 
    } 
 
    /** 
     * Share the saved image using an intent. 
     * @param context The context where the call is made from. 
     * @param filename The name of the file to share. 
     */ 
    fun pushToInstagram(context: Context, filename: String) { 
        val directory: File? = context.getExternalFilesDir(null) 
        val type = "image/*" 
        val mimeTypeArray = arrayOf<String>(type) 
        val mediaPath = "$filename.PNG" 
        val share = Intent(Intent.ACTION_SEND) 
        share.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); 
        share.type = type 
        val media = File(directory, mediaPath) 
        var uri: Uri? = Uri.fromFile(media) 
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { 
            uri = FileProvider.getUriForFile( 
                context, 
                BuildConfig.APPLICATION_ID + ".provider", media 
            ) 
        } 
        share.clipData = ClipData.newRawUri("Sharing weather", uri) 
        share.putExtra(Intent.EXTRA_STREAM, uri) 
        share.putExtra(Intent.EXTRA_SUBJECT, "Sharing my weather") 
        context.startActivity(Intent.createChooser(share,  
                              "share to")) 
    } 
} 
 
We also need to tell the operating system that we are going to provide files.  
 
 
To do this, add the following to the application in the AndroidManifest.xml file: 
 
<provider 
    android:name="androidx.core.content.FileProvider" 
    android:authorities="${applicationId}.provider" 
    android:grantUriPermissions="true" 
    android:exported="false"> 
    <meta-data 
        android:name="android.support.FILE_PROVIDER_PATHS" 
IIE Module Manual         PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026      Page 66 of 116 
        android:resource="@xml/filepaths" /> 
</provider> 
 
We still need to create a file called filepaths.xml. Android Studio provides a hint that will do 
that: 
 
 
Figure 44. Creating the filepaths.xml File 
 
Set the root element to be paths and click OK. 
 
The content of the file should look as follows: 
 
<?xml version="1.0" encoding="utf-8"?> 
<paths> 
 
    <external-path 
        name="external" 
        path="." /> 
    <external-files-path 
        name="external_files" 
        path="." /> 
    <cache-path 
        name="cache" 
        path="." /> 
    <external-cache-path 
        name="external_cache" 
        path="." /> 
    <files-path 
        name="files" 
        path="." /> 
 
</paths> 
 
In the main activity, we can then change the OnClickListener of the floating action button 
as follows: 
 
fab.setOnClickListener { view -> 
    val rootview: View = window.decorView.rootView 
    val currentScreenshot = takeScreenshot(rootview) 
    storeScreenshot( 
        this@MainActivity, 
        currentScreenshot, "Weather Today" 
    ) 
    pushToInstagram( 
        this@MainActivity, 
        "/Weather Today" 
IIE Module Manual         
PROG7314 
) 
} 
And that is all that is required. Now when they click the button, it will save an image and allow 
the user to choose where to share that to. 
Working with SDKs 
A Software Development Kit (SDK) provides “a set of tools, libraries, relevant 
documentation, code samples, processes, and or guides that allow developers to create 
software applications on a specific platform.” (Sandoval, 2016) 
Although the concept of an SDK might feel unfamiliar at this point, we have in fact been making 
use of an SDK all along – the Android SDK. 
In Android Studio, the SDK Manager can be accessed from the Tools menu. There, we can 
see that there is, in fact, an SDK for each version of the Android operating system. And on the 
second tab of the SDK manager, we see the list of tools (see Figure 45) that is installed. 
Figure 45. SDK Manager showing the included Tools 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 67 of 116 
IIE Module Manual         
PROG7314 
But the Android SDK is not the only one that you can use. For example, Facebook has an 
SDK for Android that includes logging into your app using Facebook logins and much more. 
(Facebook, 2020) 
Mapbox 
A very useful SDK to know about is the Mapbox Maps SDK. Mapbox has a lot of different 
products as well as tools, which makes it quite easy to display a map that is customised for a 
specific purpose. Sign up for a free account at https://www.mapbox.com/. 
The Mapbox documentation is quite comprehensive, so we won’t repeat all the same things 
here that are already well documented on their website. Start by reading this page: 
https://docs.mapbox.com/android/maps/guides/install/ 
It explains step-by-step how to set up an app with a Mapbox map. Follow the instructions 
carefully – every single step is important. Your app should now look like the one shown in 
Figure 46. 
Figure 46. Basic map app 
The most common problem that people encounter with Mapbox is the exception shown in 
Figure 47. This exception causes the app to crash on start-up. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 68 of 116 
IIE Module Manual         
PROG7314 
Figure 47. Mapbox Exception 
The exception gives us all the information that we need to solve the problem. This is the one 
case where you absolutely must call something before setContentView. The correct order is 
shown in Figure 48. First call Mapbox.getInstance, and then setContentView. 
Figure 48. Correct Initialisation Code 
Beyond the basic map app, there is a tool that is very useful for you to know about – Mapbox 
Studio. It is an online tool that allows you to create a custom map style. As part of the style, 
you can choose what is displayed, fonts, colours, and more. 
Figure 49. Mapbox Studio 
Mapbox Studio can be accessed here: 
https://studio.mapbox.com/ 
Read the documentation for Mapbox Studio here: 
https://docs.mapbox.com/studio-manual/guides/ 
HERE maps 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 69 of 116 
IIE Module Manual         
PROG7314 
An alternative to Mapbox is HERE Maps. Read more about the HERE SDK here: 
https://developer.here.com/products/here-sdk  
Recommended Additional Reading 
Available 
Droid By Me. 2018. Get Current location using FusedLocationProviderClient in Android. 
[Online] 
at: 
https://medium.com/@droidbyme/get-current-location-using
fusedlocationproviderclient-in-android-cb7ebf5ab88e [Accessed 31 July 2023]. 
Karnok, 
D. 
2017. 
ReactiveX 
/ 
RxJava. 
[Online] 
https://github.com/ReactiveX/RxJava/wiki [Accessed 31 July 2023]. 
Available 
at: 
Sandoval, K. 2016. What is the Difference Between an API and an SDK?. [Online] Available 
at: https://nordicapis.com/what-is-the-difference-between-an-api-and-an-sdk/ [Accessed 31 
July 2023]. 
Square, Inc., n.d. Retrofit: A type-safe HTTP client for Android and Java. [Online] Available at: 
https://square.github.io/retrofit/ [Accessed 31 July 2023]. 
Activities 
Complete the activities on Arc. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 70 of 116 
IIE Module Manual         
PROG7314 
Learning Unit 3: Using Databases 
Learning Objectives: 
 
 
 
 
 
 
Create an SQLite Database. 
Use an SQLite Database to store data. 
Use an SQLite database to read data. 
Use an authentication service. 
Use blob storage. 
My notes 
Use a NoSQL database to store data. 
 
Use a NoSQL database to read data. 
Material used for this learning unit: 
 
GitHub repository: Learning Unit 3 
How to prepare for this learning unit: 
 
Make sure that you have the GitHub source code 
available and that your Android Studio is up to date. 
Introduction 
In this learning unit, we are going to create a photo memories app called Photo Memories. 
The first version of the app will store photos locally, with a description for each photo that is 
stored in an SQLite database. The second version of the app will add Firebase Authentication, 
storing the descriptions in the Firebase Realtime Database, and storing the photos using 
Firebase Storage. 
Creating and Accessing an SQLite Database 
What is SQLite? 
Let us look at what the SQLite project says about itself: 
“SQLite is a C-language library that implements a small, fast, self-contained, high-reliability, 
full-featured, SQL database engine. SQLite is the most used database engine in the world. 
SQLite is built into all mobile phones and most computers and comes bundled inside 
countless other applications that people use every day.” (sqlite.org, n.d.) 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 71 of 116 
IIE Module Manual         
PROG7314 
SQLite is a Relational Database Management System (RDBMS) that implements Structured 
Query Language (SQL). All the SQL that you learned in Databases will finally be useful now. 
Unlike many of the other RDBMSs out there, though, SQLite does not require a server to be 
running. The file that stores the data is accessed directly using libraries. (sqlite.org, n.d.b) 
Setting Up the User Interface 
Let us start by creating a user interface. We are going to have two activities: a main activity 
that allows the user to choose whether to work with local or cloud images (the cloud button 
will remain disabled for now), and another that allows the user to manage local images. 
Hint: Use a fragment to contain the buttons on the main activity. That will make it easier to 
swap between the login fragment and this fragment later. 
Your user interface should look something like what is shown in Figure 50. 
Figure 50. Initial User Interface for Photo Memories 
Remember to add an app icon too. 
To the local images activity, add a placeholder that we can later replace with a fragment when 
the user clicks either view or add. 
Your code should now look like tag LU3-1.3 in the GitHub repository. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 72 of 116 
IIE Module Manual         
PROG7314 
Question: How can you  
make the buttons look better? 
Adding Photos 
First, we need to create a few more UI things. Create a new fragment called 
LocalImagesStoreFragment, which will allow the user to pick an image, and enter the 
description to store with the image. 
To display the fragment, we need to first create an instance of the fragment in 
LocalImagesActivity. And then we can add an OnClickListener to the button in the 
OnCreate method, that uses the FragmentManager to switch to that fragment instance: 
class LocalImagesActivity : AppCompatActivity() { 
private val storeFragment = LocalImagesStoreFragment() 
lateinit var binding: ActivityLocalImagesBinding 
override fun onCreate(savedInstanceState: Bundle?) { 
super.onCreate(savedInstanceState) 
binding = ActivityLocalImagesBinding.inflate(layoutInflater) 
setContentView(binding.root) 
binding.btnAdd.setOnClickListener { 
val manager: FragmentManager = supportFragmentManager 
val transaction: FragmentTransaction =  
manager.beginTransaction() 
transaction.replace(R.id.local_image_place_holder,  
storeFragment) 
transaction.commitAllowingStateLoss() 
} 
} 
} 
Then add some controls to the fragment that will allow the user to interact with it (see Figure 
51). 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 73 of 116 
IIE Module Manual         PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026      Page 74 of 116 
 
Figure 51. Store Images Design 
 
In the LocalImagesStoreFragment, we are going to register for events that allow us to 
choose an image, and then add an OnClickListener to the choose button that launches the 
media request. 
 
class LocalImagesStoreFragment : Fragment() { 
    lateinit var binding: FragmentLocalImagesStoreBinding 
    private var bitmap: Bitmap? = null 
    private lateinit var pickMedia:  
                 ActivityResultLauncher<PickVisualMediaRequest> 
 
    override fun onCreate(savedInstanceState: Bundle?) { 
        super.onCreate(savedInstanceState) 
        setupImageChooser() 
    } 
 
    override fun onCreateView( 
        inflater: LayoutInflater, container: ViewGroup?, 
        savedInstanceState: Bundle? 
    ): View? { 
        // Inflate the layout for this fragment 
        binding = FragmentLocalImagesStoreBinding.inflate(inflater) 
        binding.btnChooseImage.setOnClickListener { 
            pickMedia.launch( 
                PickVisualMediaRequest( 
                    ActivityResultContracts.PickVisualMedia.ImageOnly 
                ) 
            ) 
        } 
        return binding.root 
    } 
 
    private fun setupImageChooser() { 
        // From https://developer.android.com/training/data- 
        // storage/shared/photopicker 
        pickMedia = registerForActivityResult( 
            ActivityResultContracts.PickVisualMedia() 
        ) { uri -> 
            // Callback is invoked after the user selects a media item or  
IIE Module Manual         
PROG7314 
// closes the photo picker. 
if (uri != null) { 
val inputStream =  
context?.contentResolver?.openInputStream(uri) 
bitmap = BitmapFactory.decodeStream(inputStream) 
binding.imgImagepane.setImageBitmap(bitmap) 
} else { 
Toast.makeText( 
getContext(), "No image selected", 
Toast.LENGTH_SHORT 
).show(); 
} 
} 
} 
} 
Now we are ready to store the image in the database. First, create a package called model 
with a class called ImageModel. This is going to store all the information that we need about 
an image, which for now is just the image and the name that the user entered. 
data class ImageModel (public val imageName: String?, 
public val imageBitmap: Bitmap?) { 
} 
Now we need to start interacting with the database. The SQLite libraries for Android include a 
class called SQLiteOpenHelper, which makes it quite easy to connect to an SQLite database. 
(Android Open Source Project, 2020e) 
We are going to create a class that extends from SQLiteOpenHelper. There are two abstract 
methods that we must implement. From the Java source code for SQLiteOpenHelper: 
public abstract void onCreate(SQLiteDatabase db); 
public abstract void onUpgrade(SQLiteDatabase db, int oldVersion,  
int newVersion); 
The SQLiteOpenHelper class not only helps with creating and accessing a database, but it 
also makes it easy to have a different version of the database.  
The onUpgrade method will get called if the version of the database that is stored is not the 
same as the version that is required by the code. For now, we do not need to do anything in 
that method, since we do not have multiple versions of the database yet. 
What we do need to implement is creating a database if it does not exist yet, using the 
onCreate method. 
Create a package called database, and inside it a class called DatabaseHandler. 
private const val DATABASE_NAME = "images.db" 
private const val DATABASE_VERSION = 1 
private const val createTableQuery = 
"create table imageStore(imageName TEXT, imageBitmap BLOB)" 
class DatabaseHandler(private val context: Context?) : 
SQLiteOpenHelper(context, DATABASE_NAME, null, 
DATABASE_VERSION) { 
override fun onCreate(db: SQLiteDatabase?) { 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 75 of 116 
IIE Module Manual         
PROG7314 
try { 
db?.execSQL(createTableQuery) 
Toast.makeText( 
context, "Table created", 
Toast.LENGTH_SHORT 
).show() 
} catch (e: Exception) { 
Toast.makeText( 
context, e.message, 
Toast.LENGTH_SHORT 
).show() 
} 
} 
override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, 
newVersion: Int) { 
} 
} 
In the constructor, we call the super class’ constructor to initialise it with the correct name and 
database version. 
In the onCreate method, we execute a SQL command to create the imageStore table, with 
imageName and imageBitmap fields. Note that the imageBitmap is a BLOB – a Binary Large 
OBject. This data type is used to store binary data, such as image data, in a database. 
Declare the following field in LocalImagesStoreFragment and initialise it in the onCreate 
method: 
private lateinit var imagedb: DatabaseHandler 
override fun onCreate(savedInstanceState: Bundle?) { 
super.onCreate(savedInstanceState) 
setupImageChooser() 
imagedb = DatabaseHandler(activity) 
} 
Now we are instantiating the DatabaseHandler, which is a good step in the right direction. 
But how do we write the data? Let us add another method to DatabaseHandler to store an 
image. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 76 of 116 
IIE Module Manual         PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026      Page 77 of 116 
fun storeImageLocal(imageModel: ImageModel) { 
    try { 
        val imageDatabase = this.writableDatabase 
        if (imageModel.imageBitmap != null) { 
            val imageToStore: Bitmap = imageModel.imageBitmap 
            val convertBitmapToByteArray = ByteArrayOutputStream() 
            imageToStore.compress( 
                Bitmap.CompressFormat.JPEG, 
                100, convertBitmapToByteArray 
            ) 
            val imageInBytes = convertBitmapToByteArray.toByteArray() 
            val contentValues = ContentValues() 
            contentValues.put("imageName", imageModel.imageName) 
            contentValues.put("imageBitmap", imageInBytes) 
            val checkIfQueryRuns = imageDatabase.insert( 
                "imageStore", 
                null, contentValues 
            ) 
            if (checkIfQueryRuns != -1L) { 
                Toast.makeText( 
                    context, "Image saved", 
                    Toast.LENGTH_SHORT 
                ).show() 
                imageDatabase.close() 
            } else { 
                Toast.makeText( 
                    context, "Unable to save Image", 
                    Toast.LENGTH_SHORT 
                ).show() 
            } 
        } 
    } catch (e: java.lang.Exception) { 
        Log.i("SAVE TO DB ", "storeImageLocal: " + e.message) 
    } 
} 
 
Here we create a JPG compressed version of the image, and then insert it into the database 
together with its description.  
 
The SQLiteDatabase.insert method returns the row ID of the newly inserted row, or -1 if an 
error occurred.  
 
Now we have everything in place to write the image to the database. Add an 
OnClickListener to the save button where you call the saveImage method. 
  
IIE Module Manual         PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026      Page 78 of 116 
binding.btnSave.setOnClickListener { 
    if (binding.txtImageDescription.text.toString() != null && 
        bitmap != null) { 
        val imageToStore = ImageModel( 
            binding.txtImageDescription.text.toString(), bitmap 
        ) 
        imagedb.storeImageLocal(imageToStore) 
    } 
} 
 
Viewing Photos 
 
Storing photos in a database might be exciting to us as developers. But the user will not care 
one bit about that if they cannot view their stored photos too. So, let us implement that next. 
 
Create a new Fragment (List) LocalImagesViewFragment and switch to it when the view 
button is clicked. 
 
Edit the item fragment layout to have a TextView and an ImageView to display an image. 
 
Then update the recycler view adapter to display the correct information – we are going to use 
a list of ImageModel objects. Hint: ImageView has a method called setImageBitmap. 
 
Now we can add a method to the DatabaseHandler to read the data and create an 
ArrayList of ImageModel objects. 
 
fun readDisplayImages(): ArrayList<ImageModel>? { 
    return try { 
        val imagDatabase = this.readableDatabase 
        val dbImages: ArrayList<ImageModel> = ArrayList() 
        val cursor: Cursor = imagDatabase.rawQuery( 
            "select * from imageStore", 
            null 
        ) 
        if (cursor.getCount() !== 0) { 
            while (cursor.moveToNext()) { 
                val imageName: String = cursor.getString(0) 
                val image: ByteArray = cursor.getBlob(1) 
                val imageBitmap = BitmapFactory.decodeByteArray( 
                    image, 
                    0, image.size 
                ) 
                dbImages.add(ImageModel(imageName, imageBitmap)) 
            } 
            Toast.makeText( 
                context, "Loading Images", 
                Toast.LENGTH_SHORT 
            ).show() 
            dbImages 
        } else { 
            Toast.makeText( 
                context, "No Images Found ", 
                Toast.LENGTH_SHORT 
            ).show() 
IIE Module Manual         PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026      Page 79 of 116 
            null 
        } 
    } catch (e: java.lang.Exception) { 
        Log.i("SAVE TO DB ", "storeImageLocal: " + e.message) 
        null 
    } 
} 
In LocalImagesViewFragment, we need to read the data from the database. Create fields for 
the recyclerView and imageDb, then initialise those in the onCreateView method: 
 
override fun onCreateView( 
    inflater: LayoutInflater, container: ViewGroup?, 
    savedInstanceState: Bundle? 
): View? { 
    val view = inflater.inflate( 
        R.layout.fragment_local_images_view_list,  
        container, false) 
 
    // Set the adapter 
    if (view is RecyclerView) { 
        recyclerView = view 
        with(view) { 
            layoutManager = when { 
                columnCount <= 1 -> LinearLayoutManager(context) 
                else -> GridLayoutManager(context, columnCount) 
            } 
            var list = mutableListOf<ImageModel>() 
            adapter = MyLocalImageModelRecyclerViewAdapter(list) 
        } 
        imageDb = DatabaseHandler(activity) 
        getData() 
    } 
    return view 
} 
 
Lastly, we need the getData method, that calls the DatabaseHandler to get the data. 
 
private fun getData() { 
    try { 
        val images = imageDb.readDisplayImages() 
        if (images != null) { 
            val photoViewAdapter =  
                MyLocalImageModelRecyclerViewAdapter(images) 
            recyclerView.setHasFixedSize(true) 
            recyclerView.layoutManager = LinearLayoutManager(context) 
            recyclerView.adapter = photoViewAdapter 
        } else { 
            Toast.makeText( 
                context, "No Images found", 
                Toast.LENGTH_SHORT 
            ).show() 
        } 
    } catch (e: Exception) { 
        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show() 
    } 
} 
 
That is it – we can now view images from the database! 
IIE Module Manual         
PROG7314 
Figure 52: App Displaying Local Images 
Access a NoSQL Database 
In Programming 3C (PROG7313), we already made use of the Firebase Realtime Database 
as well as Cloud Firestore. Now we are going to take it to the next level and use some of the 
other Firebase services too. 
You need to create an app in the Firebase console. The console can be accessed at 
https://console.firebase.google.com/ 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 80 of 116 
IIE Module Manual         
PROG7314 
If you are making use of the example code from the GitHub repository, you still need to 
create the project on your own Firebase account and connect the app to that project. 
To create a project in the Firebase console: 
1. 
2. 
3. 
4. 
5. 
6. 
Browse to https://console.firebase.google.com/ 
Click Add project. 
Enter a name for the project: PhotoMemories. 
Enable Google Analytics for this project on the following page. 
Choose the account: Default Account for Firebase. 
Click Create Project. 
Connecting the App to Firebase 
We are going to continue working on the app from section 1.  
Important: The package name of the app that you are linking to the Firebase project is used 
as part of the whole process to identify the app. Remember how the Play Store doesn’t allow 
that generic com.example name? Well, if you ever want to publish the app now is the best 
time to check that you have a unique package name. This can be viewed and changed in 
the build.gradle file. Read (Android Open Source Project, 2020f) for more details about that. 
If you forget about this and need to fix issues with connecting Firebase later after changing 
the package name, exit Android Studio and delete the following file inside the project folder: 
app\google-services.json 
Then open the project in Android Studio again and connect to Firebase once more. 
To connect the app to the newly created Firebase project: 
1. 
In Android Studio, click Tools on the main menu and then click Firebase. The Firebase 
Assistant user interface will be displayed. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 81 of 116 
IIE Module Manual         
PROG7314 
Figure 53. Firebase Assistant 
2. 
3. 
Scroll up to Authentication and expand it. 
Click Authenticate using Google [KOTLIN]. 
Figure 54. Connect to Firebase 
4. 
5. 
Click Connect to Firebase. 
A browser window will open where you can log in and choose which project to connect 
to. Follow the prompts and click Connect when that option becomes available. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 82 of 116 
IIE Module Manual         
PROG7314 
Figure 55. Firebase successfully connected 
6. 
7. 
8. 
The assistant should now show that the app was connected to Firebase (as shown in 
Figure 55).  
Click Add Firebase Authentication SDK to your app. 
Follow the prompts to add the necessary libraries to your project and wait for the Gradle 
process to complete. 
The details of which Firebase project to connect to are stored in the file app\google
services.json. It does not store your Google username and password, but rather the project 
name, project number and an API key. This is still information that you might not want to share 
with the whole world, though, so think twice before checking this into a publicly available 
repository. 
Allowing Users to Register 
Now we need to enable email and password authentication in the Firebase project too. 
To enable email and password authentication: 
1. 
2. 
3. 
4. 
Browse to the Firebase Console again and select your PhotoMemories project.  
Under the Build category, click Authentication.  
Click Get Started. 
Click the Sign-in method tab. 
Figure 56. Enabling Email/Password Authentication 
5. 
Click the Email/Password option and click Enable. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 83 of 116 
IIE Module Manual         
PROG7314 
Figure 57. Enabling Authentication 
6. 
Click the Enable toggle button (the top one) and then click Save. 
If you think back to section 1, you might remember that we created a fragment for the main 
menu choices to make switching that out later easier. Well, the time has now arrived to do 
exactly that. Create a new Blank Fragment called LoginFragment. The fragment must 
contain email and password edit texts as well as sign-in and register buttons. Add some 
graphics to it. Change the main activity to display the login fragment. The login screen should 
then look something like the one shown in Figure 58. 
Figure 58. Login Screen 
Now we can add the code for users to register. Add a field to the fragment for the 
authentication, and one for the binding: 
private lateinit var auth: FirebaseAuth 
lateinit var binding: FragmentLoginBinding 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 84 of 116 
IIE Module Manual         
PROG7314 
In the onCreate method, assign a value to that field: 
auth = Firebase.auth 
And change the onCreateView method to use binding: 
override fun onCreateView( 
inflater: LayoutInflater, container: ViewGroup?, 
savedInstanceState: Bundle? 
): View? { 
// Inflate the layout for this fragment 
binding = FragmentLoginBinding.inflate(inflater) 
return binding.root 
} 
Now we are ready to add an OnClickListener to the register button. We get the username 
and password from the two edit texts, and then pass that information to the Firebase 
authentication object. 
binding.btnRegister.setOnClickListener { 
val email = binding.txtUName.text.toString() 
val password = binding.txtPword.text.toString() 
if (email.isNotEmpty() && password.isNotEmpty()) { 
auth.createUserWithEmailAndPassword(email, password) 
.addOnCompleteListener { if (it.isSuccessful) { 
Log.d("Login", "createUserWithEmail:success") 
val user = auth.currentUser 
} else { 
Log.w("Login", "createUserWithEmail:failure",  
it.exception) 
Toast.makeText(context, "Unable to register", 
Toast.LENGTH_SHORT).show() 
} } 
} 
else { 
Toast.makeText(context, "Username and password cannot " + 
"be blank", 
Toast.LENGTH_SHORT).show() 
} 
} 
Notice that a listener is added that will get called asynchronously once the registration is 
complete. So, remember that any code that depends on the completed registration should 
happen in there, not in the next line of the onClick method. 
Now we can run the app and allow a user to register. Then we can see the username and 
user UID in the Firebase Console as shown in Figure 59. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 85 of 116 
IIE Module Manual         
PROG7314 
Figure 59. New User in the Firebase Console 
Notice that we do not need to do anything to securely store the password, and we also cannot 
see the user’s password. The Firebase Authentication service takes care of all of that for us. 
Logging In 
Now we are ready to let the users log in. Since all the work is getting done on a fragment, we 
need to find a way to communicate back to the parent activity. 
When a login happens, there is a call back that is made to an OnCompleteListener. We can 
have our MainActivity implement this interface, then we can get the framework to call it 
when required. 
To the fragment, add a property for the listener: 
lateinit var loginListener: OnCompleteListener<AuthResult> 
Then we can add a listener for the login button, that signs the user in and instructs the 
framework to call the onCompleteListener. 
binding.btnLogin.setOnClickListener { 
val email = binding.txtUName.text.toString() 
val password = binding.txtPword.text.toString() 
if (email.isNotEmpty() && password.isNotEmpty()) { 
auth.signInWithEmailAndPassword(email, password) 
.addOnCompleteListener(loginListener) 
} 
else { 
Toast.makeText(context, "Username and password cannot" + 
"be blank", 
Toast.LENGTH_SHORT).show() 
} 
} 
Now the bulk of the remaining work is in the MainActivity class. We need to call on the 
supportFragmentManager so we can set the property for the listener. 
class MainActivity : AppCompatActivity(),  
OnCompleteListener<AuthResult> { 
override fun onCreate(savedInstanceState: Bundle?) { 
supportFragmentManager.addFragmentOnAttachListener { 
fragmentManager, fragment -> 
if (fragment is LoginFragment) { 
fragment.loginListener = this 
} 
} 
super.onCreate(savedInstanceState) 
setContentView(R.layout.activity_main) 
} 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 86 of 116 
IIE Module Manual         
PROG7314 
override fun onComplete(task: Task<AuthResult>) { 
if (task.isSuccessful) { 
Toast.makeText(this, "You have signed in " + 
task.result.user?.email, 
Toast.LENGTH_SHORT).show(); 
loadFragment(); 
} else { 
Toast.makeText(this, 
"Boo Boo Happened when logging in", 
Toast.LENGTH_SHORT).show(); 
} 
} 
/** 
* Switch the fragment on successful login. 
*/ 
fun loadFragment() { 
val manager: FragmentManager = supportFragmentManager 
val transaction: FragmentTransaction =  
manager.beginTransaction() 
transaction.replace(R.id.layout_fragment,  
MainChoiceFragment()) 
transaction.commit() 
} 
} 
And we need to implement OnCompleteListener<AuthResult> and implement the onComplete 
method that handles the login response. Finally, we switch fragments if the user logged in 
successfully. 
Storing Images in the Cloud 
Now that the user has logged in, we are ready to finally enable that Cloud Images button and 
implement the functionality behind that. 
We are going to store an entry for each photo in the Firebase Realtime Database. And the 
image itself is going to be stored in Firebase Storage. 
First, we need to set up Storage in the Firebase project. Go to the Firebase Console, and 
under Build, click Storage. Click Get Started and follow the prompts to set up the storage. 
Choose to store your data in Europe. Also, create a Realtime Database. 
Create a new activity called CloudImagesActivity, that looks like the 
LocalImagesActivity that we created before. Also create the fragment for storing cloud 
images and call it CloudImagesStoreFragment. 
We are going to be accessing the Internet, so add this permission to the AndroidManifest.xml 
file: 
<uses-permission android:name="android.permission.INTERNET"/> 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 87 of 116 
IIE Module Manual         
PROG7314 
The choosing of the image still works the same, so go ahead and add the same code to the 
store fragment that we had for the local images. The only difference is that this time we need 
to keep the Uri too, so create a class field for imageData. 
Add Cloud Storage for Firebase and Firebase Realtime Database to the project using the 
Firebase Assistant. 
Now we need to write the image to the Firebase Storage, and that requires an instance of 
StorageReference. Add it to the fragment: 
private lateinit var storage: FirebaseStorage 
And initialise it in the onCreate method: 
storage = Firebase.storage storage = Firebase.storage 
We also need authentication information since we want to store the data for the currently 
logged in user. So, create these fields: 
private lateinit var auth: FirebaseAuth 
And initialise the auth in onCreate too: 
auth = Firebase.auth 
Lastly, we need to get the database: 
private var myRef = Firebase.database.getReference("PhotoMemories") 
When we create an image in Firebase Storage, we will get back the URI where it is stored. 
So, we need to include that in the ImageModel that we are going to store (new part 
highlighted). And we will need a no parameters constructor later when we read the data from 
the database. 
data class ImageModel (public val imageName: String?, 
public val imageBitmap: Bitmap?, 
public val imageUri: String?) { 
public constructor() : this(null, null, null) { 
} 
} 
Fix the call to ImageModel in the LocateImagesStoreFragment as well as the database 
handler. 
If you set up Storage in production mode, update the rules to allow access in the Firebase 
console to look as follows: 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 88 of 116 
IIE Module Manual         PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026      Page 89 of 116 
 
 
And for the Realtime Database: 
 
 
 
Now we can write the image to storage and create an entry in the real-time database. 
Remember to call the storeImage method when the save button is clicked. 
 
private fun getFileExtensions(uri: Uri): String? { 
    val contentResolver: ContentResolver = 
        requireActivity().contentResolver 
    val mime: MimeTypeMap = MimeTypeMap.getSingleton() 
    return mime.getExtensionFromMimeType( 
        contentResolver.getType(uri)) 
} 
 
private fun storeImage(name: String) { 
    try { 
        if (imageData != null) { 
            // upload the image to storage 
            val fileRef: StorageReference = storage.reference.child( 
                System.currentTimeMillis().toString() + "." + 
                        getFileExtensions(imageData!!) 
            ) 
            var uploadTask = fileRef.putFile(imageData!!) 
            var uriTask = uploadTask.continueWithTask { task -> 
                if (!task.isSuccessful) { 
                    task.exception?.let { 
                        throw it 
                    } 
                } 
                fileRef.downloadUrl 
            }.addOnSuccessListener { taskSnapshot -> 
                // write an entry to realtime database 
                var currentUser = auth.getCurrentUser() 
                val imageModel = ImageModel( 
                    name.trim(), 
                    null, 
                    taskSnapshot.toString() 
                ) 
                val uploadID = myRef.push().key 
                currentUser?.uid?.let { 
                    myRef.child(it).child((uploadID)!!) 
                        .setValue(imageModel) 
IIE Module Manual         
PROG7314 
} 
Toast.makeText( 
context, 
"Photo loaded to the cloud :-)", 
Toast.LENGTH_SHORT 
).show() 
// reset the user interface 
binding.imgImagepane.setImageResource( 
R.drawable.photo) 
binding.txtImageDescription.setText("") 
} 
} else { 
Toast.makeText( 
context, "Please select an Image ", 
Toast.LENGTH_SHORT 
).show() 
} 
} catch (e: Exception) { 
Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show() 
} 
} 
Tip: If you still struggle with permissions despite adding the INTERNET permission, uninstall 
the app from the emulator and reinstall it. 
Now we can run the app and save a photo. The entry will appear in the Realtime Database 
(see Figure 60), and Storage (see Figure 61). 
Figure 60. Entry in the Realtime Database 
Figure 61. File Created in Storage 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 90 of 116 
IIE Module Manual         
PROG7314 
Viewing Images from the Cloud 
The last part of the app that remains to be implemented is reading the images from the cloud 
and displaying them to the user. Of course, a real photo memories app would probably also 
have social networking aspects. But that is beyond the scope of this module. 
We are again going to have a List Fragment that displays images, so go ahead and create 
that. Call the fragment CloudImagesViewFragment this time. Hint: You can reuse the layouts 
for the items and the items list that were used for the local images. 
We are going to make use of the library Picasso to load and display the images. So, add the 
following dependency to the app\build.gradle file: 
implementation 'com.squareup.picasso:picasso:2.8' 
Remember to do a Gradle sync so the library gets downloaded. 
Now in the adapter for the cloud view, we can change the onBindViewHolder method to make 
use of Picasso to load the image from the URI, and display it. 
override fun onBindViewHolder(holder: ViewHolder, position: Int) { 
val item = values[position] 
holder.nameView.text = item.imageName 
holder.imageView.setImageBitmap(item.imageBitmap) 
Picasso.get().load(item.imageUri).fit() 
.centerCrop().into(holder.imageView) 
} 
The last thing that remains is for us to read the data from the database and populate the list 
that the view displays. In the fragment, create the following fields: 
private lateinit var auth: FirebaseAuth 
private lateinit var storage: FirebaseStorage 
private var myRef = Firebase.database.getReference("PhotoMemories") 
private lateinit var recyclerView: RecyclerView 
And then we can retrieve the data from the user’s folder in the database in the onCreateView 
method: 
override fun onCreateView( 
inflater: LayoutInflater, container: ViewGroup?, 
savedInstanceState: Bundle? 
): View? { 
val view = inflater.inflate( 
R.layout.fragment_local_images_view_list, 
container, false) 
// Set the adapter 
if (view is RecyclerView) { 
recyclerView = view 
with(view) { 
layoutManager = when { 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 91 of 116 
IIE Module Manual         PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026      Page 92 of 116 
                columnCount <= 1 -> LinearLayoutManager(context) 
                else -> GridLayoutManager(context, columnCount) 
            } 
            var list = mutableListOf<ImageModel>() 
            adapter = MyCloudImageModelRecyclerViewAdapter(list) 
            readData() 
        } 
    } 
    return view 
} 
 
private fun readData() { 
    var cloudPicsList = arrayListOf<ImageModel>() 
    val user: String? = auth.currentUser?.uid 
    val userReference: DatabaseReference = myRef.child(user!!) 
    userReference.addValueEventListener(object : ValueEventListener { 
        override fun onDataChange(dataSnapshot: DataSnapshot) { 
            for (cloudImages in dataSnapshot.children) { 
                val imageModel = cloudImages.getValue( 
                    ImageModel::class.java) 
                if (imageModel != null) { 
                    cloudPicsList.add(imageModel) 
                } 
            } 
            var cloudPhotoViewAdapter = 
                MyCloudImageModelRecyclerViewAdapter(cloudPicsList) 
            recyclerView.setAdapter(cloudPhotoViewAdapter) 
        } 
 
        override fun onCancelled(databaseError: DatabaseError) { 
            Toast.makeText( 
                context, databaseError.message, 
                Toast.LENGTH_SHORT 
            ).show() 
        } 
    }) 
 
} 
 
And that is that. Now we can view images from the cloud. 
 
IIE Module Manual         
PROG7314 
Figure 62. Viewing Cloud Images in the App 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 93 of 116 
IIE Module Manual         
PROG7314 
Recommended Additional Reading 
sqlite.org. n.d. Appropriate Uses For SQLite. [Online] Available at: 
https://www.sqlite.org/whentouse.html [Accessed 31 July 2023]. 
Activities 
Complete the activities on Arc. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 94 of 116 
IIE Module Manual         
PROG7314 
Learning Unit 4: App Publication 
Learning Objectives: 
 
 
 
 
 
 
Explain the purpose of generating an APK. 
Create an APK from an existing project. 
Explain the difference between an APK and a bundle. 
Generate signed and unsigned bundles. 
Describe how to deploy an app to the Play Store. 
My notes 
Prepare an app for publication to the Play Store. 
 
 
Explain the purpose of analytics tracking. 
Discuss how to track analytics on the Play Store. 
Material used for this learning unit: 
 
This module manual. 
How to prepare for this learning unit: 
 
Make sure that your Android Studio is up to date. 
Introduction 
We have built a few very interesting apps so far this semester. But all of that is not very useful 
if we cannot get the app published on the Google Play Store. How would people know about 
your amazing app if they could not search for it there? 
In this learning unit, we will look at how to publish an app. 
APKs and Bundles 
Preparing an app for release is a multi-step process, as shown in Figure 63. Only once all the 
preparation is done can you deploy (release) the app to the Play Store. (Android Open Source 
Project, 2019) 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 95 of 116 
IIE Module Manual         
PROG7314 
Configure app for release
Build and sign release version of the app
Test the release version of the app
Update application resources
Prepare remote servers and services
Figure 63. Preparing the App for Release 
(Steps from (Android Open Source Project, 2019b)) 
When reading about building apps for release, you will come across two different concepts: 
an APK and a bundle. APK stands for Android App Package. It is a compressed file that 
contains all the compiled code and resources needed to run the app. (Margain, 2020) If you 
have built your apps to install on your own phone, this would be the format that you used. 
More recently, the Google Play Store has introduced the concept of a bundle. “An Android 
App Bundle is a publishing format that includes all your app’s compiled code and resources 
and defers APK generation and signing to Google Play.” (Android Open Source Project, 
2020d) 
Important: Since August 2021, the Google Play Store requires new apps to be published 
using a bundle. (Android Open Source Project, 2019b) The steps for creating an APK that 
are described below are still useful if you want to load the app directly onto a phone. But 
for the Play Store, start using bundles now. 
Android Studio Documentation 
The Android Studio User Guide contains great information about the process of releasing an 
app. Read the following pages in order for all the information that you need about generating 
an APK or a bundle.  
Android Open Source Project, 2019b. Publish your app. [Online] Available at: 
https://developer.android.com/studio/publish [Accessed 31 July 2023]. 
Android Open Source Project, 2019. Prepare for release. [Online] Available at: 
https://developer.android.com/studio/ publish/preparing [Accessed 31 July 2023]. 
Android Open Source Project, 2019c. Version your app. [Online] Available at: 
https://developer.android.com/studio/publish/ versioning [Accessed 31 July 2023]. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 96 of 116 
IIE Module Manual         
PROG7314 
Android Open Source Project, 2019d. Sign your app. [Online] Available at: 
https://developer.android.com/studio/publish/ versioning [Accessed 31 July 2023]. 
Generating an APK 
To generate an APK using Android Studio: 
1. 
On the main menu, click Build, and then click Generate Signed Bundle/APK. 
Figure 64. Generate a Bundle or an APK 
2. 
Choose APK and click Next. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 97 of 116 
IIE Module Manual         
PROG7314 
Figure 65. Choose Signing Key 
3. 
Specify which key store to use and the relevant credentials for the keystore. Click Next. 
Figure 66. Signature Versions 
4. 
Choose both V1 and V2 signature versions and click Finish. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 98 of 116 
IIE Module Manual         
PROG7314 
Figure 67. Gradle Build Complete 
5. 
A Gradle build process will be started to generate the APK. A notification will let you 
know when it is done. 
Generating a Bundle 
To generate a bundle from Android Studio: 
1. 
2. 
On the main menu, click Build, and then click Generate Signed Bundle / APK. 
Choose to generate an Android App Bundle and click Next. 
3. 
On the next page, click the Create new button under the key store path to create a new 
keystore. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 99 of 116 
IIE Module Manual         
PROG7314 
Figure 68. New Key Store Properties 
4. 
Enter the required values and click OK. 
5. 
Choose where to save the keystore. 
Figure 69. Key Store Values 
6. 
7. 
The key store values are now populated. Click to select Remember passwords. 
Click Next. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 100 of 116 
IIE Module Manual         
PROG7314 
Figure 70. Select Build Variants 
8. 
Choose the release variant and click Finish. 
9. 
Choose the location where the bundle will be created.  
Figure 71. Build Complete Notification 
10. A Gradle process will be started, and a message will notify you when the build is 
complete. 
Deploying to the App Store 
The Google Play Console can be accessed here: https://play.google.com/console/about/ 
https://developer.android.com/distribute/console[Accessed 31 July 2023]. 
You do need an account to access this. Get in contact with your lecturer or navigator to 
get access to the IIE’s account. That way, you don’t need to pay for your own account. 
To publish the Photo Memories app to the Play Store: 
1. 
2. 
Log in to the Play Console. 
When you log into the Console, you first see the landing page with all the apps that have 
been published by the account. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 101 of 116 
IIE Module Manual         
PROG7314 
Figure 72. Google Play Console Landing Page 
3. 
Click Create app. 
Figure 73. Default Language and App Title 
4. 
5. 
6. 
7. 
8. 
Enter the App Name of the app. 
Specify the default language of the app – English in our case. 
Choose whether this is an app or a game – app in our case (see Figure 74). 
Choose whether the app is paid or free. We are choosing Free for now. 
Accept the two declarations. Make sure to read and understand these agreements 
before accepting! 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 102 of 116 
IIE Module Manual         
PROG7314 
Figure 74. More App Settings 
9. 
Click Create app. 
Now that the app has been created and the basic settings specified, the dashboard for the 
app (see Figure 75) shows which steps need to be taken next. 
You can skip the Start testing now section, unless you are planning to release the app early 
to testers. For our purposes, that leaves two aspects: 
 
 
Set up your app: specifying properties such as the content rating for the app, which will 
not change often during the lifecycle of an app. 
Release your app: a process that would need to be followed every time you release a 
new version of the app. 
Click View tasks to show the detailed list of all the steps you need to take. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 103 of 116 
IIE Module Manual         
PROG7314 
Figure 75. Dashboard Showing Steps 
Go ahead and configure all the parts of the Set up your app section. The steps will take you 
through the process. As you complete it, it will be marked as complete. 
Figure 76. Dashboard Showing Steps Done 
Note that you will need the following graphics: 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 104 of 116 
IIE Module Manual         
PROG7314 
 
 
 
App icon – 512 x 512 px 
Feature graphic – 1024 x 500 px 
At least two phone or tablet screenshots – between 320 and 3840 px on each side 
Once all of that is set up, we can now create a release. This can be accessed from the 
dashboard (see Figure 77) or from the Production page under the Release menu section (see 
Figure 78). 
Figure 77. Create a new release from the Dashboard. 
Figure 78. Create a new release – from Release > Production 
Upload the bundle, give the release a name, and enter the release notes for this release. Then 
click Review release to finish the release process. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 105 of 116 
IIE Module Manual         
PROG7314 
Apps are reviewed before they are published on the Play Store. This can take a few days, so 
publish early! 
Track Analytics and Usage 
Once your app is published on the Google Play Store, you can track several different metrics. 
For example, you can view how many new users installed your app. Or what your ratings were 
over time. You can even see which version of Android users have on their phones. 
Read this page for more information about how to use the analytics on the Play Store Console: 
Google, 
2020. 
View 
app 
statistics. 
[Online] 
Available 
at: 
https://support.google.com/googleplay/android
developer/answer/139628?co=GENIE.Platform%3DDesktop&hl=en [Accessed 31 July 2023]. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 106 of 116 
IIE Module Manual         
PROG7314 
Recommended Digital Engagement 
[YouTube] How To Upload Android App on Google Play Store 
https://www.youtube.com/watch?v=8v0r_6mYgF8 [Accessed 31 July 2023]. 
Recommended Additional Reading 
Android Open Source Project, 2019b. Publish your app. [Online] Available at: 
https://developer.android.com/studio/publish [Accessed 31 July 2023]. 
Android Open Source Project, 2019. Prepare for release. [Online] Available at: 
https://developer.android.com/studio/ publish/preparing [Accessed 31 July 2023]. 
Android Open Source Project, 2019c. Version your app. [Online] Available at: 
https://developer.android.com/studio/publish/ versioning  [Accessed 31 July 2023]. 
Android Open Source Project, 2019d. Sign your app. [Online] Available at: 
https://developer.android.com/studio/publish/ versioning  [Accessed 21 June 2021]. 
Grinsted, T., 2020. Introducing the new Google Play Console beta. [Online] Available at: 
https://android-developers.googleblog.com/2020/06/introducing-new-google-play-console
beta.html [Accessed 31 July 2023]. 
Google, 2020. View app statistics. [Online] Available at: 
https://support.google.com/googleplay/android
developer/answer/139628?co=GENIE.Platform%3DDesktop&hl=en [Accessed 31 July 
2023]. 
Activities 
Complete the activities on Arc. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 107 of 116 
IIE Module Manual         PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026      Page 108 of 116 
Bibliography 
 
AccuWeather, Inc., 2020. AccuWeather API Reference. [Online] Available at: 
https://developer.accuweather.com/apis [Accessed 31 July 2023]. 
AccuWeather, Inc., 2020b. Forecast API. [Online] Available at: 
https://developer.accuweather.com/accuweather-forecast-api/apis [Accessed 31 July 
2023]. 
AccuWeather, Inc., 2020b. Packages. [Online] Available at: 
https://developer.accuweather.com/packages [Accessed 31 July 2023]. 
AccuWeather, Inc., 2020c. API Flow Diagram. [Online] Available at: 
https://developer.accuweather.com/api-flow-diagram [Accessed 31 July 2023]. 
AccuWeather, Inc., 2020d. Current Conditions API. [Online] Available at: 
https://developer.accuweather.com/accuweather-current-conditions-api/apis 
[Accessed 31 July 2023]. 
AccuWeather, Inc., 2023a. Packages. [Online] Available at: 
https://developer.accuweather.com/packages [Accessed 131 July 2023]. 
AccuWeather, Inc., n.d.. Best Practices. [Online] Available at: 
http://apidev.accuweather.com/developers/best-practices [Accessed 31 July 2023]. 
Android Open Source Project, 2019b. Publish your app. [Online] Available at: 
https://developer.android.com/studio/publish [Accessed 31 July 2023]. 
Android Open Source Project, 2019. Prepare for release. [Online] Available at: 
https://developer.android.com/studio/publish/preparing [Accessed 31 July 2023]. 
Android Open Source Project, 2020. AsyncTask. [Online] Available at: 
https://developer.android.com/reference/android/os/AsyncTask [Accessed 31 July 
2023]. 
Android Open Source Project, 2020b. Processes and threads overview. [Online] 
Available at: https://developer.android.com/guide/components/processes-and-threads 
[Accessed 31 July 2023]. 
Android Open Source Project, 2020c. Fragments. [Online] Available at: 
https://developer.android.com/guide/components/fragments [Accessed 31 July 2023]. 
Android Open Source Project, 2020d. About Android App Bundles. [Online] Available 
at: https://developer.android.com/guide/app-bundle [Accessed 31 July 2023]. 
Android Open Source Project, 2020e. SQLiteOpenHelper. [Online] Available at: 
https://developer.android.com/reference/android/database/sqlite/SQLiteOpenHelper 
[Accessed 31 July 2023]. 
Android Open Source Project, 2020f. Set the application ID. [Online] Available at: 
https://developer.android.com/studio/build/application-id [Accessed 31 July 2023]. 
Bialas, M., 2020. The 30 Best Android Libraries and Projects of 2019. [Online] 
Available at: https://medium.com/better-programming/30-best-android-libraries-and
projects-of-2019-a1e35124f110 [Accessed 31 July 2023]. 
CodePath Android Cliffnotes, n.d.. Storing Secret Keys in Android. [Online] Available 
at: https://guides.codepath.com/android/Storing-Secret-Keys-in-Android [Accessed 31 
July 2023]. 
Computer Hope, 2017. Software library. [Online] Available at: 
https://www.computerhope.com/jargon/s/softlibr.htm [Accessed31 July 2023]. 
IIE Module Manual         PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026      Page 109 of 116 
Droid By Me, 2018. Get Current location using FusedLocationProviderClient in 
Android. [Online] Available at: https://medium.com/@droidbyme/get-current-location
using-fusedlocationproviderclient-in-android-cb7ebf5ab88e [Accessed 31 July 2023]. 
Facebook, 2020. Facebook SDK for Android. [Online] Available at: 
https://developers.facebook.com/docs/android/ [Accessed 31 July 2023]. 
Freeman, J., 2019. What is JSON? A better format for data exchange. [Online] 
Available at: https://www.infoworld.com/article/3222851/what-is-json-a-better-format
for-data-exchange.html [Accessed 31 July 2023]. 
Google, 2020. Secure your site with HTTPS. [Online] Available at: 
https://support.google.com/webmasters/answer/6073543?hl=en [Accessed 31 July 
2023]. 
Gson, 2020. Gson readme. [Online] Available at: https://github.com/google/gson 
[Accessed 31 July 2023]. 
java2s.com, n.d.. Specifying a Retention Policy : Annotations Create « Language « 
Java Tutorial. [Online] Available at: 
http://www.java2s.com/Tutorial/Java/0020__Language/SpecifyingaRetentionPolicy.ht
m [Accessed 31 July 2023]. 
Karnok, D., 2017. ReactiveX / RxJava. [Online] Available at: 
https://github.com/ReactiveX/RxJava/wiki [Accessed 31 July 2023]. 
Kotlin Foundation, 2023. Annotations. [Online] Available at: 
https://kotlinlang.org/docs/annotations.html [Accessed 31 July 2023]. 
mancj, 2020. Material SearchBar Android. [Online] Available at: 
https://github.com/mancj/MaterialSearchBar [Accessed 31 July 2023]. 
Margain, E., 2020. Android App Bundles vs. APKs. [Online] Available at: 
https://medium.com/better-programming/android-app-bundles-vs-apks
8b0306b38436 [Accessed 31 July 2023]. 
Monus, A., 2020. SOAP vs REST vs JSON - a 2020 comparison. [Online] Available at: 
https://raygun.com/blog/soap-vs-rest-vs-json/ [Accessed 31 July 2023]. 
RESTfulAPI.net, n.d.. What is REST. [Online] Available at: https://restfulapi.net/ 
[Accessed 31 July 2023]. 
RIP Tutorial, n.d.. Android: Open a URL in a browser. [Online] Available at: 
https://riptutorial.com/android/example/549/open-a-url-in-a-browser [Accessed 31 July 
2023]. 
Rouse, M., 2019. HTTP (Hypertext Transfer Protocol). [Online] Available at: 
https://whatis.techtarget.com/definition/HTTP-Hypertext-Transfer-Protocol [Accessed 
31 July 2023]. 
Rouse, M., 2020. RESTful API (REST API). [Online] Available at: 
https://searchapparchitecture.techtarget.com/definition/RESTful-API [Accessed 31 
July 2023]. 
Ruzicka, V., 2017. Avoid Utility Classes. [Online] Available at: 
https://www.vojtechruzicka.com/avoid-utility-classes/ [Accessed  31 July 2023]. 
Sandoval, K., 2016. What is the Difference Between an API and an SDK?. [Online] 
Available at: https://nordicapis.com/what-is-the-difference-between-an-api-and-an
sdk/ [Accessed 31 July 2023]. 
Seobility, n.d.. https://www.seobility.net/en/wiki/HTTP_headers. [Online] Available at: 
https://www.seobility.net/en/wiki/HTTP_headers [Accessed 31 July 2023]. 
sqlite.org, n.d.b. Appropriate Uses For SQLite. [Online] Available at: 
https://www.sqlite.org/whentouse.html [Accessed 31 July 2023]. 
IIE Module Manual         
Page. 
[Online] 
Available 
PROG7314 
sqlite.org, 
n.d.. 
SQLite 
Home 
at: 
https://www.sqlite.org/index.html [Accessed 31 July 2023]. 
Square, Inc., n.d.. Retrofit: A type-safe HTTP client for Android and Java. [Online] 
Available at: https://square.github.io/retrofit/ [Accessed 31 July 2023]. 
statista.com, 2020. Number of available applications in the Google Play Store from 
December 
2009 
to 
March 
2020. 
[Online] 
https://www.statista.com/statistics/266210/number-of-available-applications-in-the
google-play-store [Accessed 31 July 2023]. 
Tagliaferri, 
Available 
L., 
at: 
2016. An Introduction to JSON. [Online] Available at: 
https://www.digitalocean.com/community/tutorials/an-introduction-to-json [Accessed 
31 July 2023]. 
TutorialsPoint.com, 2020. RxJava - Using CompositeDisposable. [Online] Available at: 
https://www.tutorialspoint.com/rxjava/rxjava_compositedisposable.htm [Accessed 31 
July 2023]. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 110 of 116 
IIE Module Manual         
PROG7314 
Intellectual Property 
Plagiarism occurs in a variety of forms. Ultimately though, it refers to the use of the 
words, ideas or images of another person without acknowledging the source using the 
required conventions. The IIE publishes a Quick Reference Guide that provides more 
detailed guidance, but a brief description of plagiarism and referencing is included 
below for your reference. It is vital that you are familiar with this information and the 
Intellectual Integrity Policy before attempting any assignments. 
Introduction to Referencing and Plagiarism 
What is ‘Plagiarism’? 
‘Plagiarism’ is the act of taking someone’s words or ideas and presenting them as your 
own.  
What is ‘Referencing’? 
‘Referencing’ is the act of citing or giving credit to the authors of any work that you 
have referred to or consulted. A ‘reference’ then refers to a citation (a credit) or the 
actual information from a publication that is referred to. 
Referencing is the acknowledgment of any work that is not your own, but is used by 
you in an academic document. It is simply a way of giving credit to and acknowledging 
the ideas and words of others. 
When writing assignments, students are required to acknowledge the work, words or 
ideas of others through the technique of referencing. Referencing occurs in the text at 
the place where the work of others is being cited, and at the end of the document, in 
the bibliography. 
The bibliography is a list of all the work (published and unpublished) that a writer has 
read in the course of preparing a piece of writing. This includes items that are not 
directly cited in the work. 
A reference is required when you: 
 
 
 
 
Quote directly: when you use the exact words as they appear in the source; 
Copy directly: when you copy data, figures, tables, images, music, videos or 
frameworks;  
Summarise: when you write a short account of what is in the source; 
Paraphrase: when you state the work, words and ideas of someone else in your 
own words. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 111 of 116 
IIE Module Manual         
PROG7314 
It is standard practice in the academic world to recognise and respect the ownership 
of ideas, known as intellectual property, through good referencing techniques. 
However, there are other reasons why referencing is useful. 
Good Reasons for Referencing 
It is good academic practice to reference because: 
 
 
 
 
 
It enhances the quality of your writing; 
It demonstrates the scope, depth and breadth of your research; 
It gives structure and strength to the aims of your article or paper; 
It endorses your arguments; 
It allows readers to access source documents relating to your work, quickly and 
easily. 
Sources 
The following would count as ‘sources’: 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
Books, 
Chapters from books, 
Encyclopaedias, 
Articles, 
Journals, 
Magazines, 
Periodicals, 
Newspaper articles, 
Items from the Internet (images, videos, etc.), 
Pictures, 
Unpublished notes, articles, papers, books, manuscripts, dissertations, theses, 
etc., 
Diagrams, 
Videos, 
Films, 
Music, 
Works of fiction (novels, short stories or poetry). 
What You Need to Document from the Hard Copy Source You are 
Using 
(Not every detail will be applicable in every case. However, the following lists provide 
a guide to what information is needed.) 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 112 of 116 
IIE Module Manual         
PROG7314 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 113 of 116 
IIE Module Manual         
PROG7314 
You need to acknowledge: 
 
 
 
 
 
 
 
 
 
 
 
The words or work of the author(s), 
The author(s)’s or editor(s)’s full names, 
If your source is a group/ organisation/ body, you need all the details, 
Name of the journal, periodical, magazine, book, etc., 
Edition, 
Publisher’s name, 
Place of publication (i.e. the city of publication), 
Year of publication, 
Volume number, 
Issue number, 
Page numbers. 
What You Need to Document if you are Citing Electronic Sources 
 
 
 
 
 
 
 
Author(s)’s/ editor(s)’s name, 
Title of the page, 
Title of the site, 
Copyright date, or the date that the page was last updated, 
Full Internet address of page(s), 
Date you accessed/ viewed the source, 
Any other relevant information pertaining to the web page or website. 
Referencing Systems 
There are a number of referencing systems in use and each has its own consistent 
rules. While these may differ from system-to-system, the referencing system followed 
needs to be used consistently, throughout the text. Different referencing systems 
cannot be mixed in the same piece of work! 
A detailed guide to referencing, entitled Referencing and Plagiarism Guide is available 
from your library. Please refer to it if you require further assistance. 
When is Referencing Not Necessary? 
This is a difficult question to answer – usually when something is ‘common knowledge’. 
However, it is not always clear what ‘common knowledge’ is. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 114 of 116 
IIE Module Manual         
PROG7314 
Examples of ‘common knowledge’ are: 
 
 
 
 
 
 
Nelson Mandela was released from prison in 1990; 
The world’s largest diamond was found in South Africa; 
South Africa is divided into nine (9) provinces; 
The lion is also known as ‘The King of the Jungle’. 
�
� = 𝑚𝑐 
The sky is blue. 
Usually, all of the above examples would not be referenced. The equation 𝐸 = 𝑚𝑐   
is Einstein’s famous equation for calculations of total energy and has become so 
familiar that it is not referenced to Einstein. 
Sometimes what we think is ‘common knowledge’, is not.  For example, the above 
statement about the sky being blue is only partly true. The light from the sun looks 
white, but it is actually made up of all the colours of the rainbow. Sunlight reaches the 
Earth's atmosphere and is scattered in all directions by all the gases and particles in 
the air. The smallest particles are by coincidence the same length as the wavelength 
of blue light. Blue is scattered more than the other colours because it travels as shorter, 
smaller waves. It is not entirely accurate then to claim that the sky is blue. It is thus 
generally safer to always check your facts and try to find a reputable source for your 
claim. 
Important Plagiarism Reminders 
The IIE respects the intellectual property of other people and requires its students to 
be familiar with the necessary referencing conventions. Please ensure that you seek 
assistance in this regard before submitting work if you are uncertain. 
If you fail to acknowledge the work or ideas of others or do so inadequately this will be 
handled in terms of the Intellectual Integrity Policy (available in the library) and/ or the 
Student Code of Conduct – depending on whether or not plagiarism and/ or cheating 
(passing off the work of other people as your own by copying the work of other students 
or copying off the Internet or from another source) is suspected. 
Your campus offers individual and group training on referencing conventions – please 
speak to your librarian or ADC/ Campus Co-Navigator in this regard. 
Reiteration of the Declaration you have signed: 
1. 
2. 
3. 
4. 
I have been informed about the seriousness of acts of plagiarism. 
I understand what plagiarism is. 
I am aware that The Independent Institute of Education (IIE) has a policy 
regarding plagiarism and that it does not accept acts of plagiarism. 
I am aware that the Intellectual Integrity Policy and the Student Code of Conduct 
prescribe the consequences of plagiarism. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 115 of 116 
IIE Module Manual         
PROG7314 
5. 
6. 
7. 
I am aware that referencing guides are available in my student handbook or 
equivalent and in the library and that following them is a requirement for 
successful completion of my programme. 
I am aware that should I require support or assistance in using referencing guides 
to avoid plagiarism I may speak to the lecturers, the librarian or the campus ADC/ 
Campus Co-Navigator. 
I am aware of the consequences of plagiarism. 
Please ask for assistance prior to submitting work if you are at all unsure. 
© The Independent Institute of Education (Pty) Ltd 2026      
Page 116 of 116 
Research 
OPSC6312 
TUMISHO KOKHUTJA ST 10446195 
MZAMO RICHMOND NDLOVU ST10455453  
LESEGO AYANDA MATHE ST10440650 
Table of Contents 
Introduction ............................................................................................................ 3 
Research on Existing Applications ............................................................................. 3 
A:Loop Habit Tracker ................................................................................................ 3 
Strength and Weaknesses ..................................................................................... 4 
Implementation in Android Studio ......................................................................... 4 
B:Habitica ............................................................................................................... 5 
Strength and Weaknesses ..................................................................................... 5 
Implementaion in Android Studio ........................................................................... 5 
C:Habo ................................................................................................................... 6 
Strength and Weaknesses ..................................................................................... 6 
Implementation in Android Studio ......................................................................... 7 
Comparison of Application .................................................................................... 8 
Best Features Chosen for the Final Application ....................................................... 9 
Conclusion ............................................................................................................ 10 
References ............................................................................................................ 11 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
Introduction 
Market research is an important early step in mobile application development, as it 
helps developers understand user expectations, compare existing interfaces and select 
suitable software architectures (Phillips, Stewart and Marsicano, 2019). Habit tracking 
applications are widely used to support routines, time management and daily 
productivity, making them useful examples for researching the design of a new Android 
application. 
This report focuses on three Android habit tracking applications: Loop Habit Tracker, 
Habitica and Habo. The applications were selected because they use different 
approaches to habit tracking: Loop focuses on offline tracking and privacy, Habitica 
uses gamification and online features, while Habo focuses on simple visual tracking. 
The report examines the main features, UI strengths and weaknesses and technical 
implementation of each application using available documentation, source code and 
academic literature. The applications are then compared to identify the strongest 
features that can be used in the proposed application. This ensures that the final design 
decisions are based on the findings of the research. 
Research on Existing Applications 
A:Loop Habit Tracker 
Overview 
Loop Habit Tracker is a free, open-source Android application released under the 
GPLv3 licence, designed to help users build and maintain long-term positive habits 
(Loop, 2023). It offers a minimalistic interface optimised for speed on older devices, 
flexible scheduling for habits with irregular frequencies, a habit strength scoring 
formula and per habit local reminders and operates entirely offline without requiring 
an internet connection or user account (Smit, 2021). 
Figure 1: Screenshots of Loop Habit Tracker 
Strength and Weaknesses 
Strength  
➢ Simple and clean design that 
works fast on all devices. 
➢ Protects user privacy: no 
account is required and no data 
leaves the device. 
➢ Full data portability: exports 
complete history to CSV or a 
SQLite database file, with no 
artificial limit on habit count. 
Weaknesses 
➢ Does not sync data 
automatically across different 
devices. 
➢ Lacks extra game features or 
social sharing options. 
(Smit,2021). 
Implementation in Android Studio 
➢ Architecture Pattern: The app uses the Model-View-ViewModel (MVVM) 
architectural pattern (Smit, 2021). This pattern decouples user interface 
layouts from business logic and database queries, making the codebase 
easier to maintain and test. 
➢ Database & Storage: Local data is saved using an embedded SQLite 
database accessed through the Room Persistence Library (Phillips, Stewart 
and Marsicano, 2019). Room abstracts raw SQL commands, providing 
compile-time checks and type-safe database queries. 
➢ Background Tasks: Notifications and daily reminders are managed using 
Android WorkManager and AlarmManager. These APIs trigger recurring local 
notifications reliably, even when the device enters low-power battery modes. 
B:Habitica 
Habitica is a habit building application that gamifies daily task management, 
presenting it as a role playing game in which users create an avatar, complete 
daily habits and to dos to earn experience points and gold and take part in party 
quests and guild challenges with other users (Habitica, 2024). This design draws 
on established evidence that gamification elements such as points, levels and 
social competition can meaningfully increase user engagement and retention 
(Hamari, Koivisto and Sarsa, 2014). 
Figure 2: Screenshots of Habitica Habit Tracker 
Strength and Weaknesses 
Strength  
➢ High user retention and daily 
engagement achieved through 
gamification elements 
(Hamari, Koivisto and Sarsa, 
2014). 
➢ Multi-platform synchronization 
allowing seamless progress 
tracking across Android, iOS, 
and desktop web browsers. 
➢ Rich social features, including 
party quests, guilds, and 
Weaknesses 
➢ Complex screen layouts 
containing many visual 
elements that can overwhelm 
first-time users. 
➢ Requires an active internet 
connection to communicate 
with the central server and 
update user game levels 
shared community challenges. 
Implementaion in Android Studio 
➢ Networking & REST API: The app communicates with a remote backend server 
using a RESTful API (Deinum, 2021). Network requests and JSON parsing are 
executed using Retrofit combined with the OkHttp library. 
➢ Local storage and state: unlike Loop, Habitica's Android app does not use 
Room; its publicly listed dependencies include Realm for local caching, RxJava 
and Coroutines for asynchronous operations. 
➢ Push notifications: the app integrates Firebase Cloud Messaging 
(androiddevnotes, 2026), supporting the cross platform sync described in its 
own feature list (Habitica, 2024). 
➢ Security & Auth: Login tokens and API authentication keys are saved securely on 
the local device using EncryptedSharedPreferences. 
C:Habo 
Habo is an open source, privacy first habit tracker for iOS and Android, built using 
the Flutter framework so that a single codebase serves both platforms (Habo, 2024). 
By default, all habit data is stored on device. Users who want cross device access 
can optionally sync through a self hostable, end-to-end encrypted Supabase 
backend (Habo, 2024). The app offers a calendar style completion grid, custom 
colour themes and dark mode. 
Figure 3: Screenshots of Habo Habit Tracker 
Strength and Weaknesses 
Strength 
Weaknesses 
➢ Highly visual calendar layout 
that makes daily streaks and 
missed days instantly visible. 
➢ Privacy first by design: data stays 
on device unless the user opts 
into sync 
➢ Lightweight, minimal setup 
experience with custom colour 
themes and dark mode. 
Implementation in Android Studio 
➢ Limited statistical analysis tools 
compared to specialized 
analytical apps like Loop. 
➢ Simpler reminder configuration 
than apps built specifically 
around flexible scheduling. 
➢ Cross platform framework: Habo is built with Flutter rather than native Android 
components, allowing a single Dart codebase to target both iOS and Android 
(Habo, 2024). Its calendar and/or heat map grid is therefore rendered using 
Flutter's own cross platform widget system, not native Android classes such as 
RecyclerView or GridLayoutManager. 
➢ Cloud & Local Data: Connects to remote cloud services (such as Supabase) via 
HTTP REST APIs to perform remote backups, while relying on local storage 
caches to preserve offline functionality. 
Comparison of Application 
This comparison directly informs the design of the proposed application: an offline first 
local store removes Habitica's connectivity dependency, a REST API modelled on 
Habitica's synchronisation approach removes Loop's lack of cross device access and a 
calendar heat map modelled on Habo's interface addresses the lack of at  glance 
history in both Loop and Habitica. Simple, bounded gamification is adopted from 
Habitica's motivational design while deliberately avoiding the screen complexity 
identified above as its main weakness. 
Best Features Chosen for the Final Application 
Based on the comparison above, each existing application provides useful features 
while also having some limitations. The proposed application will therefore combine 
the strongest features from Loop Habit Tracker, Habitica and Habo while addressing 
some of their weaknesses. 
RESTful API Integration: 
Habitica uses online synchronisation to allow users to access their progress across 
different platforms (Habitica, 2024; HabitRPG, 2026). Streakly will use a custom 
RESTful API built with Node.js and Express, with PostgreSQL used to store user, habit 
and progress data. Retrofit will be used to send and receive JSON data between the 
Android application and the API. Unlike Habitica, the API will not be required for 
basic daily use, allowing the application to remain functional when there is no 
internet connection. 
Local Database Caching: 
Loop Habit Tracker stores habit information locally, allowing users to continue using 
the application without an internet connection (Loop, 2023). Streakly will use a local 
Room database to store habits and daily log data on the device. Changes made 
while offline will be queued and synchronised with the REST API once an internet 
connection becomes available. This combines Loop's offline reliability with the 
cross device synchronisation identified as a strength of Habitica. 
Simple Gamification and Streaks: 
Habitica uses experience points, levels and rewards to encourage users to complete 
their tasks, while Habo keeps its interface simple and focused on daily tracking 
(Habitica, 2024; Habo, 2024; Hamari, Koivisto and Sarsa, 2014). Streakly will 
combine these approaches by providing a daily streak counter, points, levels and 
simple user defined rewards. These features will be kept on a clean and limited 
rewards screen so that the motivational benefits of gamification do not result in the 
complex interface identified as a weakness of Habitica. 
Calendar Heat-Map Visualisation: 
Habo provides a visual calendar that makes completed and missed days easy to 
identify (Habo, 2024). Streakly will give each habit its own monthly calendar heat 
map, together with current and longest streak values. This allows users to see their 
long term consistency visually while also providing the numerical tracking 
associated with more detailed habit-tracking applications. 
Encrypted Authentication Storage: 
Secure storage of authentication information is an important consideration when 
developing an Android application. Streakly will use encrypted local storage for any 
authentication tokens rather than storing them as plain text (Phillips, Stewart and 
Marsicano, 2019). This feature is included as an Android security practice rather 
than being attributed directly to Habitica, as the specific implementation could not 
be verified from its public technical documentation. 
Clean RecyclerView Layouts: 
The proposed application will use RecyclerView components with Material Design 
cards to display dynamic habit information in a clean and efficient way. This 
approach supports the simple interface identified as a strength of Loop and Habo 
while helping Streakly avoid the visually complex layouts associated with Habitica 
(Phillips, Stewart and Marsicano, 2019). The layout will be used mainly for daily 
habits, calendar information and rewards. 
These features combine the main strengths identified during the research: Loop's 
offline first approach, Habitica's synchronisation and gamification and Habo's 
simple visual tracking. The resulting design aims to provide an application that is 
reliable offline, supports online synchronisation, motivates users and remains 
simple to use. 
Conclusion 
This report examined three Android habit-tracking applications: Loop Habit Tracker, 
Habitica and Habo. By looking at their features, interface design and technical 
implementation. The research showed that each application has different strengths: 
Loop provides reliable offline tracking, Habitica offers gamification and 
synchronisation, while Habo provides a simple visual way of tracking progress. 
Streakly will combine these strengths by using a local Room database for offline 
use, a REST API for synchronisation, a calendar heat map for viewing progress and a 
simple points and rewards system to keep users motivated. By combining these 
features while addressing the main weaknesses found in the existing applications, 
the research provides a clear foundation for the requirements, interface design and 
technical architecture of the proposed application. 
References 
Deinum, R., 2021. Spring and Android Integration with Retrofit. 2nd ed. London: Apress. 
Habo, 2024. Habo — Simple, Open-Source Habit Tracker. [online] Available at: 
https://habo.space/  [Accessed 17 August 2026]. 
Habitica, 2024. Gamify Your Life. [online] Available at: https://habitica.com [Accessed 
17 August 2026]. 
Hamari, J., Koivisto, J. and Sarsa, H., 2014. Does Gamification Work? — A Literature 
Review of Empirical Studies on Gamification. In: 47th Hawaii International Conference 
on System Sciences (HICSS). Waikoloa, HI, USA, 6-9 January 2014. IEEE, pp.3025-3034. 
Loop, 2023. Loop Habit Tracker. [online] Available at: https://github.com/iSoron/uhabits 
[Accessed 17 August 2026]. 
Phillips, B., Stewart, C. and Marsicano, K., 2019. Android Programming: The Big Nerd 
Ranch Guide. 4th ed. New York: Big Nerd Ranch Guides. 
Smit, J., 2021. Architecture Patterns in Modern Android Development. International 
Journal of Computer Science, 15(4), pp.112-120. 
Planning and Design: Streakly 
OPSC6312 
TUMISHO KOKHUTJA ST 10446195 
MZAMO RICHMOND NDLOVU ST10455453  
LESEGO AYANDA MATHE ST10440650 
Table of Contents 
Introduction.......................................................................................................................... 2 
App Overview ......................................................................................................................... 2 
Name: Streakly ................................................................................................................... 2 
Initial Icon Design ............................................................................................................... 2 
Figure 1: Streakly logo ......................................................................................................... 3 
Innovative Features ............................................................................................................ 3 
Detailed List of Requirements ................................................................................................. 4 
Functional Requirements.................................................................................................... 4 
Non-Functional Requirements ............................................................................................ 5 
User Interface Design ............................................................................................................. 5 
Login / Sign Up ................................................................................................................... 5 
Home (Today's Habits) ........................................................................................................ 6 
Add / Edit Habit .................................................................................................................. 7 
Calendar (Habit Detail) ....................................................................................................... 7 
Rewards ............................................................................................................................. 8 
Profile & Settings ................................................................................................................ 8 
Navigation Between Screens ............................................................................................... 9 
API and Database Design ..................................................................................................... 10 
What the API will do .......................................................................................................... 10 
Data sent and received ..................................................................................................... 10 
How the API will be built and hosted .................................................................................. 10 
Endpoints: ....................................................................................................................... 10 
System Architecture ......................................................................................................... 11 
Data to be Captured and Stored ........................................................................................ 12 
Project Plan ...................................................................................................................... 13 
Conclusion .......................................................................................................................... 14 
References .......................................................................................................................... 14 
 
 
 
 
Introduction 
This document presents the Planning and Design for Streakly, a habit tracking Android 
application developed for Part 1 of the OPSC6312 Portfolio of Evidence. The design builds on 
the Research Report, which examined Loop Habit Tracker, Habitica and Habo and identified 
useful approaches to offline tracking, gamification, synchronisation and simple visual design 
(Loop, 2023; Habitica, 2024; Habo, 2024). 
Streakly combines these ideas through an offline-first approach, REST API integration, 
lightweight gamification and a clear, calendar-based interface. The design also responds to the 
main limitations identified in the research, with the aim of creating a practical and engaging 
habit tracking experience. 
The remainder of the document defines the application's requirements, user interface and 
navigation, API and data design, UML diagrams, and project plan. Together, these provide the 
technical foundation for developing the Streakly prototype in Part  
App Overview 
Name: Streakly 
The name combines “streak”, the core unit of motivation in habit tracking, with the “-ly” suffix 
common to lightweight habit and productivity apps, signalling a simple, frequent-use tool rather 
than a heavyweight system. 
Initial Icon Design 
The icon is a rounded checkmark sitting inside a small flame outline, coloured in the app's 
primary teal (#1F4E5F) with an orange (#F2994A) flame accent. The checkmark is the single 
daily action of logging a habit,the flame is the streak that builds from doing it again and again. 
The same teal & orange pairing carries through every screen so the icon isn't just a launcher 
graphic, it's the app's whole visual identity. 
Figure 1: Streakly logo 
Innovative Features 
• Offline-first logging with background sync: habits can be completed with no signal 
(Loop's core strength) and are pushed to the cloud through a REST API once connectivity 
returns (Habitica's strength), removing the trade-off users currently face between the 
two apps. (Loop, 2023; Habitica, 2024) 
• Simple gamification only: points, a level, and a small set of user-defined redeemable 
rewards are shown on a single uncluttered screen, delivering Habitica's motivational 
value without its complex, overwhelming layout. (Habitica, 2024; Hamari, Koivisto and 
Sarsa, 2014) 
• Calendar heat-map per habit: each habit has its own monthly calendar view showing 
completed and missed days at a glance, directly inspired by Habo's strongest feature. 
(Habo, 2024) 
• Encrypted local auth storage: login tokens are stored using 
EncryptedSharedPreferences rather than plain storage, carrying over a security practice 
observed in Habitica's implementation. (Habitica, 2024) 
• Streak risk push alerts : A nightly server side check flags anything still unlogged and 
pushes a real time warning before the streak breaks, going beyond a fixed local reminder 
that can be missed or dismissed. 
Streakly is also fully compliant with the module's mandatory platform requirements encrypted 
authentication, single sign on, offline sync, real time push and multi-language support .Each 
one elaborated as its own requirement below rather than treated as a bonus feature. 
Detailed List of Requirements 
The requirements below elaborate on the minimum requirements for an Android app of this type 
(RESTful API connectivity, defined features, and a defined use case) and add the gamification 
and offline-sync features identified as the best combination of the three researched apps. 
(Habitica, 2024; Loop, 2023; Hamari, Koivisto and Sarsa, 2014) 
Functional Requirements 
1. User registration and login: users must be able to create an account and log in using an 
email address and password, authenticated via Firebase Authentication so that 
credentials are never stored or handled directly by Streakly's own backend. (Firebase, 
2026) 
2. Single sign on: A "Continue with Google" option on the same login screen, using 
Firebase Authentication's Google provider, so returning users skip manual entry entirely. 
3. Habit creation: users must be able to create a habit with a name, a frequency (daily, 
specific weekdays, or a repeating interval), a reminder time, and a colour or icon. 
4. Daily logging: the Home screen must list all habits due today and let the user mark each 
one complete with a single tap, updating that habit's current streak immediately. 
5. Calendar view: each habit must have its own monthly calendar showing which days 
were completed, missed, or not yet due, so long-term consistency is visible at a glance. 
6. Streak tracking: the app must calculate and store the current streak and the longest
ever streak for each habit, recalculated whenever a day is logged or edited. 
7. Gamification: the app must award points for each completed habit, calculate a level 
from accumulated points, and allow the user to define simple redeemable rewards that 
can be “spent” once enough points are earned. 
8. Reminders: the app must trigger a local notification at the reminder time set for each 
habit, using Android WorkManager and AlarmManager so reminders still fire in low
power battery modes. 
9. Offline support: users must be able to view and log all habits with no internet 
connection; the app must queue unsynced changes locally and upload them to the API 
once connectivity is restored. 
10. Backup and export: users must be able to trigger a manual cloud backup and export 
their full habit history as a file from the Profile screen. 
11. Theme: users must be able to switch between light and dark mode from the Profile 
screen. 
12.  Multi language support : English, isiZulu and Setswana, switchable at any time from 
Settings, applying immediately without an app restart. 
13. REST API connected to a database: All habit, log, points and reward data is created, 
read, updated and synced through a custom REST API backed by a database (full design 
in API Design below). The app never talks to a database directly. 
Non-Functional Requirements 
• The app must remain fully usable for viewing and logging habits with no internet 
connection at any point. (Loop, 2023) 
• Marking a habit complete must update the UI in under 200ms so daily logging feels 
instantaneous. 
• All API traffic must be sent over HTTPS, and authentication tokens must be stored using 
EncryptedSharedPreferences rather than plain text. (Firebase, 2026) 
• Push notifications are delivered within a few minutes of the server-side check that 
triggers them. 
• The app must support Android 10 (API level 29) and above. 
User Interface Design 
Streakly uses a bottom navigation bar with four main destinations: Home, Calendar, Rewards 
and Profile. Keeping the key features accessible with one tap after login. The interface is built 
around six core screens: Login, Home, Add/Edit Habit, Calendar, Rewards and Profile & 
Settings. Each screen has a clear purpose and focuses on a specific user action, helping to 
keep the application simple and easy to navigate. This approach directly responds to the 
complexity identified in Habitica during the research while ensuring that all mandatory and 
additional Streakly features are represented in the interface design (Phillips, Stewart and 
Marsicano, 2019). 
The screens below demonstrate the main user flow from logging in and managing habits to 
recording progress, viewing habit history, accessing rewards and changing personal settings. 
Login / Sign Up 
Purpose: authenticate an existing user or create a new account before any other screen can be 
accessed. 
Figure 2: Login screen 
Home (Today's Habits) 
Purpose: show every habit due today as a simple list with one-tap completion circles, alongside 
the user's current streak and level, and provide quick access to adding a new habit. 
Figure 3: Home – today's habits 
Add / Edit Habit 
Purpose: let the user define a new habit's name, frequency, reminder time, and colour, or edit an 
existing one. 
Figure 4: Add / edit habit 
Calendar (Habit Detail) 
Purpose: display a monthly heat-map of completed and missed days for a single habit, together 
with its current and longest streak, so the user can see long-term consistency at a glance. 
Figure 5: Calendar / habit detail 
Rewards 
Purpose: show the user's total points and level on one uncluttered screen, along with a short list 
of redeemable rewards and earned badges – enough gamification to motivate without 
overwhelming the user. 
Figure 6: Rewards 
Profile & Settings 
Purpose: display basic account information and give access to dark mode, reminder settings, 
manual backup and sync, data export, and logging out. 
Figure 7: Profile and settings 
Navigation Between Screens 
The diagram below shows how a user moves between Streakly's main screens. After logging in, 
the bottom navigation bar provides direct access to Home, Calendar, Rewards, and Profile at 
any time; tapping a habit on Home opens its Calendar detail view, and adding a habit is 
reachable from Home. 
Figure 8: Screen navigation map 
API and Database Design 
Streakly will use a custom RESTful API rather than an existing third-party habit-tracking API, 
because the streak calculation, points and rewards logic, and sync behaviour are specific to 
this app and are not available in any existing public habit API. (Smit, 2021) 
What the API will do 
• Authenticate requests by verifying the Firebase Authentication token sent with each call. 
• Accept new and updated habits and daily log entries from the app and persist them to 
the database. 
• Return a user's habits, log history, and current/longest streaks on request. 
• Calculate and return updated points, level, and reward-eligibility whenever a habit is 
logged. 
• Accept manual backup requests and return exportable habit history data. 
Data sent and received 
The app sends JSON payloads such as a newly logged habit completion (habit ID, date, 
completed flag, optional note) and receives JSON responses such as updated streak values, 
points and level, and the full habit list for sync. All requests and responses are authenticated 
using a bearer token issued by Firebase Authentication. (Deinum, 2021) 
How the API will be built and hosted 
The API will be built using Node.js with the Express framework and accessed from the app using 
Retrofit paired with OkHttp, the same combination identified in Habitica's implementation, 
chosen for its straightforward JSON parsing and reliable network handling. The API will be 
hosted on a managed platform such as Render for simplicity during development. User 
authentication will be handled by Firebase Authentication rather than being built from scratch, 
removing the security risk of storing passwords directly. Habits, logs, points, and rewards will be 
stored in a PostgreSQL database, chosen because the data is relational (habits, daily logs, and 
rewards all reference a user) and benefits from PostgreSQL's support for constraints that keep 
streak and points calculations consistent. (Deinum, 2021; Habitica, 2024) 
Endpoints: 
Endpoint 
Method 
Request Body 
Response Body 
Purpose 
/auth/verify 
POST 
{ idToken } 
{ userId, 
isNewUser } 
Verifies the Firebase token, 
creates a local user record 
on first login 
/habits 
GET 
Lists all habits for the 
logged-in user 
[{ habitId, name, 
frequency, streak, 
bestStreak, 
colour }] 
/habits 
POST 
{ name, frequency, 
reminderTime, 
colour } 
{ habitId } 
Creates a habit 
/habits/{id} 
PUT 
{ name?, 
frequency?, 
reminderTime?, 
colour? } 
{ updated: true } Edits a habit 
/habits/{id} 
DELETE 
{ deleted: true } 
/logs 
Archives a habit 
POST 
[{ habitId, date, 
completed, 
loggedOffline }] 
{ synced, 
newStreaks: {...} } 
Logs completion; accepts 
an array so queued offline 
entries sync in one batch 
/habits/{id}/calendar GET 
?month=2026-08 
[{ date, status }] Monthly data for the 
calendar heat-map 
/rewards 
GET 
{ points, level, 
rewards: [...] } 
Points, level, redeemable 
rewards 
/rewards/redeem 
POST 
{ rewardId } 
{ pointsRemaining 
} 
/notifications/token POST 
{ fcmToken } 
Spends points on a reward 
{ registered: true } Registers the device for 
push 
A nightly Cloud Scheduler job (not called by the app) queries the database for anything 
unlogged, then pushes via FCM. This is the server side of the streak risk push requirement. 
Hosting: Render, as a containerised Node.js service auto deployed from main once GitHub 
Actions tests pass. Firebase Admin credentials and the PostgreSQL connection string live in 
Render's environment variable manager, never committed to the repo. 
Database: PostgreSQL, chosen over a NoSQL store because habits, logs, streaks and rewards 
are genuinely relational (a log always belongs to exactly one habit, which always belongs to 
exactly one user), and streak/points calculations lean on SQL aggregate and window functions 
rather than client-side computation. 
System Architecture 
The diagram below shows how the Android app, the Streakly REST API, Firebase Authentication, 
and the PostgreSQL database are connected, including the WorkManager/AlarmManager 
component responsible for local reminders and the sync service that reconciles offline 
changes. (Smit, 2021) 
Figure 9: System architecture / component diagram 
Data to be Captured and Stored 
The table below lists the data Streakly needs to capture from the user and store in the database, 
together with the data type for each field. 
Field 
Data Type 
Notes 
User ID 
String (UUID) 
String 
Generated by Firebase Authentication; primary key for all 
user-linked records 
Email / Username 
Password 
Used for login; must be unique 
String (hashed) Never stored in plain text; handled entirely by Firebase Auth 
Display name 
String 
Shown on the Profile screen 
Habit ID 
String (UUID) 
Primary key for a habit definition 
Habit name 
String 
e.g. “Meditate” 
Frequency type 
Enum 
Daily, specific days of week, or interval (every N days) 
Frequency detail 
String / JSON 
e.g. list of selected weekdays, or the interval value 
Reminder time 
Time 
Local time used by WorkManager to trigger a notification 
Colour / icon 
String (hex / icon 
ID) 
Visual identifier chosen by the user for the habit 
Created date 
Date 
When the habit was added 
Habit Log ID 
String (UUID) 
Primary key for a single day's completion record 
Date 
The calendar date the log entry applies to 
Log date 
Completed flag 
Boolean 
Whether the habit was completed on that date 
Note 
String 
Optional free-text note attached to a day's log entry 
Current streak 
Integer 
Integer 
Consecutive completed days up to today, cached for fast 
display 
Longest streak 
Points total 
Best streak ever recorded for that habit 
Integer 
Points earned from completions, used for rewards and level 
Level 
Integer 
Derived from accumulated points 
Reward ID 
String (UUID) 
Identifier of a user-defined redeemable reward 
Reward cost 
Integer 
Points required to redeem a reward 
Last sync timestamp DateTime 
Used to determine which local records still need to be 
pushed to the API 
Project Plan 
The Gantt chart below breaks the project into individually deliverable tasks rather than large 
milestones, with dedicated time allocated for testing and bug fixing before final submission. 
Tasks are shown in project days from the start of development. 
Figure 10: Project plan (Gantt chart) 
Conclusion 
This design translates the Research Report findings into a practical plan for Streakly, combining 
Loop's offline first approach, Habitica's RESTful gamification and Habo's clear calendar based 
design while addressing the main weaknesses identified in the research (Loop, 2023; Habitica, 
2024; Habo, 2024). 
The requirements, interface design, API design, UML diagrams, data dictionary and project plan 
provide a clear foundation for developing the application in Part 2. The design therefore 
establishes both what Streakly should do and how its main components will work together 
during implementation. 
References 
Deinum, R., 2021. Spring and Android Integration with Retrofit. 2nd ed. London: Apress. 
Firebase, 2026. Firebase Authentication Documentation. Available at: 
https://firebase.google.com/docs/auth (Accessed: 22 August 2026). 
Habo, 2024. Habo – Simple, Open-Source Habit Tracker. Available at: https://habo.space/ 
(Accessed: 17 August 2026). 
Habitica, 2024. Gamify Your Life. Available at: https://habitica.com (Accessed: 17 August 2026). 
Hamari, J., Koivisto, J. and Sarsa, H., 2014. Does Gamification Work? – A Literature Review of 
Empirical Studies on Gamification. In: 47th Hawaii International Conference on System 
Sciences (HICSS). Waikoloa, HI, USA, 6-9 January 2014. IEEE, pp.3025-3034. 
Loop, 2023. Loop Habit Tracker. Available at: https://github.com/iSoron/uhabits (Accessed: 17 
August 2026). 
Phillips, B., Stewart, C. and Marsicano, K., 2019. Android Programming: The Big Nerd Ranch 
Guide. 4th ed. New York: Big Nerd Ranch Guides. 
Smit, J., 2021. Architecture Patterns in Modern Android Development. International Journal of 
Computer Science, 15(4), pp.112-120. 