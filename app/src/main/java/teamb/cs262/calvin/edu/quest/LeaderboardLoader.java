package teamb.cs262.calvin.edu.quest;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.AsyncTaskLoader;

public class LeaderboardLoader extends AsyncTaskLoader<String> {

    private String mQueryString;

    public LeaderboardLoader(@NonNull Context context, String string) {
        super(context);
        mQueryString = string;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public String loadInBackground() {
        return NetworkUtils.getDatabaseJSON(mQueryString);
    }
}
