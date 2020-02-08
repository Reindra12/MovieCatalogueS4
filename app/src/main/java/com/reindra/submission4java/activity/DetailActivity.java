    public static String FLAG_EXTRA = "flag_extra";
    ProgressBar progressBar;
    Movie movie = new Movie();
    private MovieHelper movieHelper;
    TextView title, overview, date, rate;
    ImageView poster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        progressBar = findViewById(R.id.progressBar);
        title = findViewById(R.id.tv_tittle_detail);
        overview = findViewById(R.id.tv_synopsis);
        date = findViewById(R.id.tv_year);
        rate = findViewById(R.id.tv_score_detail);
        poster = findViewById(R.id.iv_poster_detail);
        movie = getIntent().getParcelableExtra(FLAG_EXTRA);
        movieHelper = MovieHelper.getInstance(getApplicationContext());
        movieHelper.open();
        showloading(true);
        if (movie != null) {
            title.setText(movie.getTitle());
            date.setText(movie.getDate());
            rate.setText(movie.getRating());
            overview.setText(movie.getOverview());
            Glide.with(this)
                    .load(movie.getPhoto())
                    .into(poster);
            showloading(false);
        }
    }
