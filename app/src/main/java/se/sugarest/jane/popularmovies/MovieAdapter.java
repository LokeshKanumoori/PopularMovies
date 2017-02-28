package se.sugarest.jane.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by jane on 2/26/17.
 */

/**
 * {@link MovieAdapter} exposes a list of movie posters to a
 * {@link android.support.v7.widget.RecyclerView}
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();

    /**
     * An On-click handler that we've defined to make it easy for an Activity to interface with
     * the RecyclerView
     */
    private final MovieAdapterOnClickHandler mClickHandler;

    private String[] mMoviePostersUrlStrings;

    private Context mContext;

    /**
     * Creates a MovieAdapter.
     *
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public MovieAdapter(MovieAdapterOnClickHandler clickHandler, Context context) {
        mClickHandler = clickHandler;
        mContext = context;

    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If RecyclerView has more than one type of item (which this one don't)
     *                  this viewType can be used to provide a different layout.
     * @return A new MovieAdapterViewHolder that holds the View for each list item
     */
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, update the contents of the ViewHolder to display the movie
     * posters for each particular position, using the "position" argument that is conveniently
     * passed in.
     *
     * @param movieAdapterViewHolder The ViewHolder which should be updated to represent the
     *                               contents of the item at the given position in the data set.
     * @param position               The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        String moviePosterForOneMovie = mMoviePostersUrlStrings[position];
        if (moviePosterForOneMovie != null && !moviePosterForOneMovie.isEmpty()) {
            Log.i(TAG, "Loading ".concat(moviePosterForOneMovie));
            Picasso.with(mContext).load(moviePosterForOneMovie).into(movieAdapterViewHolder.mMoviePosterImageView);
        } else {
            Log.w(TAG, "Picture is missing. Load empty pic instead.");
            String nullStr = null;
            Picasso.with(mContext).load(nullStr).into(movieAdapterViewHolder.mMoviePosterImageView);
        }
    }

    /**
     * This method simply returns the number of items to display.
     *
     * @return The number of items available on the screen
     */
    @Override
    public int getItemCount() {
        if (mMoviePostersUrlStrings == null) return 0;
        return mMoviePostersUrlStrings.length;
    }

    /**
     * This method is used to set the movie posters on a MovieAdapter if we've already
     * created one. This is handy when getting new data from the web but don't want to create a
     * new MovieAdapter to display it.
     *
     * @param moviePosterData The new movie poster data to be displayed.
     */
    public void setMoviePosterData(String[] moviePosterData) {
        mMoviePostersUrlStrings = moviePosterData;
        notifyDataSetChanged();
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface MovieAdapterOnClickHandler {
        void onClick(String moviePosterIdThatWasClicked);
    }

    /**
     * Cache of the children views for a movie poster image.
     */
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        public final ImageView mMoviePosterImageView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mMoviePosterImageView = (ImageView) view.findViewById(R.id.iv_movie_posters);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String moviePosterIdThatWasClicked = mMoviePostersUrlStrings[adapterPosition];
            mClickHandler.onClick(moviePosterIdThatWasClicked);
        }
    }
}




