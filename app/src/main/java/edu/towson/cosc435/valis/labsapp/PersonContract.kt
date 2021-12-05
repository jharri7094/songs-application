package edu.towson.cosc435.valis.labsapp

import android.content.ContentResolver
import android.net.Uri

class PersonContract {
    companion object {
        val AUTHORITY = "edu.towson.cosc435.valis.contentproviderdemo.provider"
        val CONTENT_URI = Uri.parse("content://$AUTHORITY")

        private val PERSONS_URI = "${AUTHORITY}.persons"

        val PERSON_NAME_EXTRA = "person_name_extra"
        val PERSON_AGE_EXTRA = "person_age_extra"
    }

    class Person {
        companion object {
            const val TABLE_NAME = "person_table"
            const val ID = "_ID"
            const val NAME = "name"
            const val AGE = "age"
            /**
             * The content URI for this table.
             */
            val CONTENT_URI = Uri.withAppendedPath(
                    PersonContract.CONTENT_URI,
                    "persons")
            /**
             * The mime type of a directory of items.
             * This ends up being vnd.android.cursor.dir/edu.towson.cosc435.valis.contentproviderdemo.provider
             */
            val CONTENT_TYPE = "${ContentResolver.CURSOR_DIR_BASE_TYPE}/${PERSONS_URI}"
            /**
             * The mime type of a single item.
             */
            val CONTENT_ITEM_TYPE = "${ContentResolver.CURSOR_ITEM_BASE_TYPE}/${PERSONS_URI}"
            /**
             * A projection of all columns
             * in the items table.
             */
            val PROJECTION_ALL = arrayOf(ID, NAME, AGE)
            /**
             * The default sort order for
             * queries containing NAME fields.
             */
            val SORT_ORDER_DEFAULT = "$NAME DESC"
        }
    }
}