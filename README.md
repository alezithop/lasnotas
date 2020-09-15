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

PENDING TO CHECK WITH NESTOR:
Why do I had to add bigger padding in activity_main.xml to avoid few elements be hidden at bottom of the screen?
android:paddingBottom="100dp"