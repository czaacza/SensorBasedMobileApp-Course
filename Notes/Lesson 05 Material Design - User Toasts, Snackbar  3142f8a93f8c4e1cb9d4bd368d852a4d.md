# Lesson 05 | Material Design - User Toasts, Snackbar & Notifications

Created: August 24, 2022 2:09 PM

## Sources

- https://material.io/design/
- https://developer.android.com/jetpack/compose/themes/material
- https://materialdesignicons.com/
- https://developer.android.com/
reference/kotlin/androidx/
compose/material/icons/
package-summary

## Lab Project

Creating your own theme (both Light and Dark). And customize the status bar by changing primary colors. 

[https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary](https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary) and [https://material.io/design/color/](https://material.io/design/color/)

Add elevation and shadow to your elements

[https://material.io/design/environment/elevation.html](https://material.io/design/environment/elevation.html)

## Toast

Toast shows a popup message at the bottom of the screen.

- It will show even if the application is in the background
- Fills the amount of space required for the message
- current activity remains visible and interactive when message
- shows up automatically disappear after a timeout
- can be launched by any component

![Untitled](Untitled.png)

## Snackbar

- **Snackbar** show a small message at bottom of the application
- will only show when the application is **active**
- if many snackbars at the same time, will be **queued** to show
one by one
- **automatically** **disappear** after a timeout or after user
interaction (swipe)
- can have user interaction
- as the Snackbar will disappear after timeout/swipe, your app
should provide an alternative way to perform the action
- must be attached to a view (e.g. launched from a @Composable, or in traditional XML layout app from an Activity or Fragment)

There are a few ways to manage the snackbar. 

…

## Notification

It is the display message which can be shown to the user outside of your application’s normal UI. 

It consists of **notification area** (icon at the top bar) and **notification drawer** (notification details)

The notification **must** contain:

- a small icon
- a title
- detail text
- priority

You can add an action to perform when the notification is clicked.

Details: 

[https://material.io/design/platform-guidance/android-notifications.html](https://material.io/design/platform-guidance/android-notifications.html)