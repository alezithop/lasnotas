# lasnotas

# Guidelines for text size.
https://learnui.design/blog/android-material-design-font-size-guidelines.html

# Links
SPINNER IMPLEMENTATION:
https://developer.android.com/guide/topics/ui/controls/spinner

RECYCLER VIEWS
https://guides.codepath.com/android/using-the-recyclerview#creating-the-recyclerview-adapter

SORT RECYCLERVIEW
https://android.camposha.info/kotlin-android-recyclerview-sort-ascending-descending/

RANDOM TEXT
https://stackoverflow.com/questions/46943860/idiomatic-way-to-generate-a-random-alphanumeric-string-in-kotlin

GLOBAL VARIABLES
https://gist.github.com/yingshaoxo/426d1b2557f95faa90aededee6697240
https://stackoverflow.com/questions/52844343/kotlin-set-value-of-global-variable/52844621

ADD NEW ACTIVITY AND NAVIGATE TO IT
https://developer.android.com/training/basics/firstapp/starting-activity#kotlin
https://stackoverflow.com/questions/46970565/button-onclick-attribute-is-none-if-activity-written-in-kotlin

PASSING OBJECTS BETWEEN ACTIVITIES
https://www.journaldev.com/37763/android-intent-handling-between-activities-using-kotlin#sending-serializable-data
https://www.journaldev.com/37763/android-intent-handling-between-activities-using-kotlin#activity-code
https://stackoverflow.com/questions/47593205/how-to-pass-custom-object-via-intent-in-kotlin

---------------------------------------------------------------------------------------------
val bundle: Bundle? = intent.extras
        bundle?.let {
            bundle.apply {
                val newNoteAdded = intent.getSerializableExtra("newNoteAdded") as Note
                if(newNoteAdded != null) {
                    Log.d("HELLO", "Content data received is $newNoteAdded.noteContent ")
                }
                notes.add(newNoteAdded)
            }
        }
---------------------------------------------------------------------------------------------

PROGRAMMATICALLY VISIBILITY OF OBJECTS IN LAYOUT
https://stackoverflow.com/questions/45553167/android-kotlin-visibility

SCROLL VIEW for only one children view.
https://stackoverflow.com/questions/52255575/how-to-make-a-scrollview-in-kotlin

Display Image From Gallery in ImageView
https://zaidisoft.com/android-imageview-display-image-from-gallery-or-camera/

KOTLIN ANDROID SQLITE (DATABASE SCHEMA)
https://developer.android.com/training/data-storage/sqlite#kotlin
https://www.tutorialkart.com/kotlin-android/android-sqlite-example-application/
https://sqlite.org/datatype3.html

Pick Image From Gallery in Kotlin – Android
https://handyopinion.com/pick-image-from-gallery-in-kotlin-android/
https://devofandroid.blogspot.com/2018/09/pick-image-from-gallery-android-studio_15.html


SAVE IMAGE TO INTERNAL STORAGE + BITMAP
https://android--code.blogspot.com/2018/04/android-kotlin-save-image-to-internal.html
https://handyopinion.com/pick-image-from-gallery-in-kotlin-android/
https://stackoverflow.com/questions/24644997/save-retrieve-images-from-sqlite-database

Return type is 'Unit?', which is not a subtype of overridden
https://stackoverflow.com/questions/57434712/return-type-is-unit-which-is-not-a-subtype-of-overridden



PENDING TO CHECK WITH NESTOR:
Why do I had to add bigger padding in activity_main.xml to avoid few elements be hidden at bottom of the screen?
android:paddingBottom="100dp"