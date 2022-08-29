# Lesson 04 | Material Design - Components and Material Theme

Created: August 24, 2022 9:30 AM

# Material Design

Comprehensive guide for visual, motion and interaction made by Google.

Resources page: [https://www.material.io](https://material.io/)

The idea came up in 2014. Material design includes some certain colors, shapes, shadows etc. - basic ideas that will help your android application look good.

It is inspired on a **paper** containing layers

## Components

Material design comes up with **components** - ready buttons/navbars etc. that we can include in our application. 

![Zrzut ekranu 2022-08-24 o 12.31.35.png](Lesson%2004%20Material%20Design%20-%20Components%20and%20Materia%20e3751b77c36c4fbdb8ca63fc7222b7e6/Zrzut_ekranu_2022-08-24_o_12.31.35.png)

## Jetpack compose implementation

Jetpack Compose offers an implementation of Material Design.  You can implement the components in your code. 

Code samples resource: [https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary#ModalBottomSheetLayout(kotlin.Function1,androidx.compose.ui.Modifier,androidx.compose.material.ModalBottomSheetState,androidx.compose.ui.graphics.Shape,androidx.compose.ui.unit.Dp,androidx.compose.ui.graphics.Color,androidx.compose.ui.graphics.Color,androidx.compose.ui.graphics.Color,kotlin.Function0)](https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary#ModalBottomSheetLayout(kotlin.Function1,androidx.compose.ui.Modifier,androidx.compose.material.ModalBottomSheetState,androidx.compose.ui.graphics.Shape,androidx.compose.ui.unit.Dp,androidx.compose.ui.graphics.Color,androidx.compose.ui.graphics.Color,androidx.compose.ui.graphics.Color,kotlin.Function0))

## Material Theme

In your project structure in ui.folder you can find 4 files connected with Material Theme. It cointains:

- Color
- Theme
- Shape
- Typography

## Color

![Zrzut ekranu 2022-08-24 o 12.40.44.png](Lesson%2004%20Material%20Design%20-%20Components%20and%20Materia%20e3751b77c36c4fbdb8ca63fc7222b7e6/Zrzut_ekranu_2022-08-24_o_12.40.44.png)

In the **color** file, you can see the colors used in a project. You can define your own colors with specific names that can be then used.

## Theme

![Zrzut ekranu 2022-08-24 o 12.42.19.png](Lesson%2004%20Material%20Design%20-%20Components%20and%20Materia%20e3751b77c36c4fbdb8ca63fc7222b7e6/Zrzut_ekranu_2022-08-24_o_12.42.19.png)

In the **theme** file you can see the list of colors used in your application. You can assign the colors to various labels like: primary, secondary etc.

## Shape

![Zrzut ekranu 2022-08-24 o 12.44.12.png](Lesson%2004%20Material%20Design%20-%20Components%20and%20Materia%20e3751b77c36c4fbdb8ca63fc7222b7e6/Zrzut_ekranu_2022-08-24_o_12.44.12.png)

In the **shape** file you can see the shape types with assigned properties. You can write your own shapes. Then, you can call it from the source code by ferering to the names or create the copy of existing property and change it: 

```
shape = MaterialTheme.shapes.small.copy(
    bottomStart =ZeroCornerSize,
    bottomEnd =ZeroCornerSize,
    topStart =CornerSize(16.dp),
    topEnd =ZeroCornerSize,
```

## Typography

![Zrzut ekranu 2022-08-24 o 12.49.08.png](Lesson%2004%20Material%20Design%20-%20Components%20and%20Materia%20e3751b77c36c4fbdb8ca63fc7222b7e6/Zrzut_ekranu_2022-08-24_o_12.49.08.png)

In **typography** file, you can change the properties of your Texts in source code. Similar to the shape file, you can create your own styles and then refer to them using **style** argument or use predefined typography styles like:

`style = MaterialTheme.typography.h5`

## Final lesson project

Concluding all the subjects learned during the lesson, I created following app design:

![Zrzut ekranu 2022-08-24 o 12.52.14.png](Lesson%2004%20Material%20Design%20-%20Components%20and%20Materia%20e3751b77c36c4fbdb8ca63fc7222b7e6/Zrzut_ekranu_2022-08-24_o_12.52.14.png)