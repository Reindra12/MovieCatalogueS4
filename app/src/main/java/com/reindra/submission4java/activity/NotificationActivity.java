        swDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean dailyIsChecked) {
                checkDaily = dailyIsChecked;
                setPref();
                if (dailyIsChecked) {
                    dailyReceiver.dailyNotificationOn(NotificationActivity.this);
                } else {
                    dailyReceiver.dailyNotifOff(NotificationActivity.this);
                }
            }
        });
    private void setPref() {
        SharedPreferences sharedPreferences = getSharedPreferences(SETTING_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(DAILY, checkDaily);
        editor.putBoolean(RELEASE, checkRelease);
        editor.apply();
    }
