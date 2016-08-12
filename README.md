Android Content Provider Demo App
=================================

Inspired by the Address Book Android App in Paul Deitel's 'Android 6 for Programmers, 3rd edn(http://www.deitel.com/Books/Android/AndroidforProgrammersAnAppDrivenApproach3/tabid/3671/Default.aspx). 
The app allows the user to save contacts to a simple address book.
You can add, edit and delete contact details.

The app is a learning exercise in the implementation of the following:
- SQLite database and Content Providers
- Phone/Tablet layout using activities/fragments
- activity/fragment communication via interfaces
- RecyclerView implementing Single Choice Mode
- EventBus 
- DialogFragment
- Navigation Drawer


Pre-requisites
--------------

- Min Android SDK supported v16

Getting Started
---------------

This sample uses the Gradle build system. To build this project, use the
"gradlew build" command or use "Import Project" in Android Studio.

Screenshots
-----------

![Phone](screenshots/content-provider-app-preview-1.gif "Interacting with the app")
![Phone](screenshots/content-provider-app-preview-2.gif "Interacting with the app")

Credit
------
The project uses the following 3rd party libraries:
- GreenRobot EventBus (https://github.com/greenrobot/EventBus)
- Glide Image downloading and caching library (https://github.com/bumptech/glide)
- Timber Android logging library by Jake Wharton (https://github.com/JakeWharton/timber)
- TextDrawable library by Amulya Khare (https://github.com/amulyakhare/TextDrawable)
- Facebook's Stetho debugging library (https://github.com/facebook/stetho)
- All images by Skeeze (https://pixabay.com/en/users/skeeze-272447/), used with permission.

MIT License
-----------

Copyright (c) [2016] [William Fero]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.