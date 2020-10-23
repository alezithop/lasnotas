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

Get generated id after insert
https://stackoverflow.com/questions/5409751/get-generated-id-after-insert/5409803#5409803

Toast messages
https://www.javatpoint.com/kotlin-android-toast

Issue creating 2 tables:
https://stackoverflow.com/questions/52490706/kotlin-sqlite-create-two-tables/52490809

Issue with foreign key:
https://stackoverflow.com/questions/35801466/android-database-sqlite-sqliteexception-near-foreign-syntax-error-code-1
https://sqlite.org/foreignkeys.html

Recycler View Using kotlin with cell click listener
https://medium.com/@aayushpuranik/recycler-view-using-kotlin-with-click-listener-46e7884eaf59

How to get the first element from the first element of a list using kotlin?
https://stackoverflow.com/questions/57026733/how-to-get-the-first-char-from-the-first-element-of-a-list-using-kotlin
https://kotlinlang.org/docs/reference/collection-elements.html

Kotlin - How do I concatenate a String to an Int value?
https://stackoverflow.com/questions/56390121/kotlin-how-do-i-concatenate-a-string-to-an-int-value

Convert String to Uri
https://stackoverflow.com/questions/3487389/convert-string-to-uri

Border color on Android button
https://stackoverflow.com/questions/19010573/border-color-on-android-button/29071340

Textview show partial text
https://stackoverflow.com/questions/4860020/textview-show-partial-text

SQLLite update doesn't update the table at all (Android Studio with Kotlin)
https://stackoverflow.com/questions/56179532/sqllite-update-doesnt-update-the-table-at-all-android-studio-with-kotlin
https://www.sqlitetutorial.net/sqlite-update/


PENDING TO CHECK WITH NESTOR:
Why do I had to add bigger padding in activity_main.xml to avoid few elements be hidden at bottom of the screen?
android:paddingBottom="100dp"