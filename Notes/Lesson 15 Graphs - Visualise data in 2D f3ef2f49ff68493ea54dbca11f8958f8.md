# Lesson 15 | Graphs - Visualise data in 2D

Created: September 6, 2022 9:05 AM

## Drawing graphs

### Old way - Views

It is possible to draw graphs directly to the screen using 2D drawing.

- If you need animation, you create a Canvas object and draw directly to it
- If slow animation is enough, we can draw directly to the View
    - We can extend the View class and draw to the canvas given in the **onDraw()** method.
        
        ![Untitled](Lesson%2015%20Graphs%20-%20Visualise%20data%20in%202D%20f3ef2f49ff68493ea54dbca11f8958f8/Untitled.png)
        
    

### New way - Jetpack Compose

We simply use the **Canvas()** composable function. 

![Untitled](Lesson%2015%20Graphs%20-%20Visualise%20data%20in%202D%20f3ef2f49ff68493ea54dbca11f8958f8/Untitled%201.png)

## Drawing graphs

In most cases it is easier to use a special library to create the graphs from your data.

They are many grap libraries available for the Android system, e.g:

- AAChartCore-Kotlin, https://github.com/AAChartModel/AAChartCore-Kotlin
- AndroidPlot, https://github.com/halfhp/androidplot
- Graph-View, http://www.android-graphview.org/
- MP Android Chart, https://github.com/PhilJay/MPAndroidChart
- AnyChart, https://github.com/AnyChart/AnyChart-Android

For Jetpack Canvas there are currently only simple libraries available:

- Decent, https://github.com/tehras/charts
- Very simple, https://github.com/Madrapps/plot

![Untitled](Lesson%2015%20Graphs%20-%20Visualise%20data%20in%202D%20f3ef2f49ff68493ea54dbca11f8958f8/Untitled%202.png)

## Using libraries in Android Studio

To include any library in our application:

![Untitled](Lesson%2015%20Graphs%20-%20Visualise%20data%20in%202D%20f3ef2f49ff68493ea54dbca11f8958f8/Untitled%203.png)

## How to use both Layout Views and Composable?

If we have an xml-layout file, e.g. graph.xml and its widget is:

![Untitled](Lesson%2015%20Graphs%20-%20Visualise%20data%20in%202D%20f3ef2f49ff68493ea54dbca11f8958f8/Untitled%204.png)

Then we use **AndroidView** composable function to wrap layout view to Composable.

![Untitled](Lesson%2015%20Graphs%20-%20Visualise%20data%20in%202D%20f3ef2f49ff68493ea54dbca11f8958f8/Untitled%205.png)

If we want to create only one graph, we can use the following code:

![Untitled](Lesson%2015%20Graphs%20-%20Visualise%20data%20in%202D%20f3ef2f49ff68493ea54dbca11f8958f8/Untitled%206.png)