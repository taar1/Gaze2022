package net.gazeapp.ui.search;

import android.app.SearchManager;
import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;

import net.gazeapp.R;

public class SearchSuggestionProvider extends SearchRecentSuggestionsProvider {

    // http://stackoverflow.com/questions/23697385/how-to-change-the-recent-searches-icon-searchmanager-suggest-column-icon-1-colu

    public final static String AUTHORITY = "net.gazeapp.ui.search.SearchSuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    // Replace Default "Search History" Icon with this icon:
    String mIconUri = String.valueOf(R.drawable.ic_time);

    public SearchSuggestionProvider() {
        super();
        setupSuggestions(AUTHORITY, MODE);
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        class Wrapper extends CursorWrapper {
            Wrapper(Cursor c) {
                super(c);
            }

            public String getString(int columnIndex) {
                if (columnIndex != -1 && columnIndex == getColumnIndex(SearchManager.SUGGEST_COLUMN_ICON_1))
                    return mIconUri;

                return super.getString(columnIndex);
            }
        }

        return new Wrapper(super.query(uri, projection, selection, selectionArgs, sortOrder));
    }

}
