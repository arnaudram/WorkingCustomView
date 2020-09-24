# Working with CustomView
exploring custom view functionalities.Here is a simple app through which i was learning how to draw with canvas and paint.
A paint object describes how to draw whereas the canvas is where to draw, just a piece of paper.
# What does the App features
Here i have implemented some functionalities like:

- Drawing  a serie of custom circles with spacing between them. 
  the has the ability to fit into any screen resolution.Thank to **onMeasure** method where i used the availabe dimension provided by the sytem to draw on.
  
  ![Screenshot_20200922-123742](https://user-images.githubusercontent.com/52896739/94109197-4dd39100-fe49-11ea-974a-886d9a496845.png)
-  Handling touch event. when a user tap on paticular circle shape , it will automatically fill in the shape if this one was previously empty and empty if if was filled in.

   ![Screenshot_20200922-161437](https://user-images.githubusercontent.com/52896739/94109810-3517ab00-fe4a-11ea-8fcf-8fc8cb4d4add.png)
   
- Using style able resource to easily handle some functionalites such as  color and dimensions where i had to deal with the conversion from independant pixel and physical pixel
  because the custom view is more likely to recognise physical pixel which displays differently across different devices.
  
  ![Screenshot_20200922-182509](https://user-images.githubusercontent.com/52896739/94110226-f9c9ac00-fe4a-11ea-99ba-b6a16e946512.png)
  
  
 - Allowing the option of type shape to display: **stars** or **circles**
  
  
   ![Screenshot_20200923-180155](https://user-images.githubusercontent.com/52896739/94111450-bf610e80-fe4c-11ea-924b-8a6480d351f5.png)
