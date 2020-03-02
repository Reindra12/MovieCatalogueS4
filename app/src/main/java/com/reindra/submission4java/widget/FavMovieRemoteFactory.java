    @Override
    public void onDataSetChanged() {
        movieHelper.open();
        tvHelper.open();

        list = tvHelper.getdataTV();
        for (int i = 0; i < list.size(); i++){
            Bitmap bitmap = null;
            try {
                bitmap = Glide.with(context)
                        .asBitmap()
                        .load(list.get(i).getPhoto())
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL ).get();
            } catch (Exception e) {
                e.getMessage();
            }
            Items.add(bitmap);
        }
        list = movieHelper.getAllMovies();
        for (int i = 0; i < list.size(); i++){
            Bitmap bitmap = null;
            try {
                bitmap = Glide.with(context)
                        .asBitmap()
                        .load(list.get(i).getPhoto())
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL ).get();

            } catch (Exception e) {
                e.getMessage();
            }
            Items.add(bitmap);
        }

    }
