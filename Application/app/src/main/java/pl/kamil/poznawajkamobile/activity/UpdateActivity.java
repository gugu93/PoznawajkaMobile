//package pl.kamil.poznawajkamobile.activity;
//
//import android.app.AlertDialog;
//import android.content.ComponentName;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.ServiceConnection;
//import android.os.Bundle;
//import android.os.IBinder;
//import android.view.Menu;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CheckedTextView;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import de.greenrobot.event.EventBus;
//import pl.kamil.poznawajkamobile.utils.UpdateService;
//
//public class UpdateActivity extends AbstractActivity
//    implements QuestionDialogFragment.QuestionDialogListener {
//
//    private LinearLayout mProgressLayout;
//    private LinearLayout mConfigurationLayout;
//    private ProgressBar mProgress;
//    private TextView mProgressTitle;
//    private TextView description;
//    private CheckedTextView mIsNoMedia;
//    private UpdateService mService;
//    private Intent mServiceIntent;
//
//    static View.OnClickListener listener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            CheckedTextView checkedTextView = (CheckedTextView) v;
//            checkedTextView.setChecked(!checkedTextView.isChecked());
//        }
//    };
//    private ServiceConnection serviceConnection = new ServiceConnection() {
//
//        public void onServiceConnected(ComponentName className, IBinder binder) {
//            mService = ((UpdateService.UpdateBinder) binder).getService();
//        }
//
//        public void onServiceDisconnected(ComponentName className) {
//            mService = null;
//        }
//    };
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        return false;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.update);
//        setTitle(R.string.update_title);
//        mProgressLayout = (LinearLayout) findViewById(R.id.update_progress_layout);
//        mConfigurationLayout = (LinearLayout) findViewById(R.id.update_configuration_layout);
//        mProgress = (ProgressBar) findViewById(R.id.update_progress);
//        mProgressTitle = (TextView) findViewById(R.id.update_title);
//        mIsNoMedia = (CheckedTextView) findViewById(R.id.update_no_media);
//        mIsNoMedia.setOnClickListener(listener);
//        description = (TextView) findViewById(R.id.update_description);
//        description.setText(String.format(getString(R.string.update_info), "31 MB"));
//        Button start = (Button) findViewById(R.id.update_start);
//        Button noUpdate = (Button) findViewById(R.id.update_no_update);
//        start.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showConnectionQuestion();
//            }
//        });
//        noUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mService != null) {
//                    mProgressTitle.setText(R.string.update_progress_unpack_title);
//                    mProgress.setProgress(1);
//                    mService.startOffline();
//                    setMode(false);
//                }
//            }
//        });
//        mServiceIntent = new Intent(this, UpdateService.class);
//        startService(mServiceIntent);
//        bindService(mServiceIntent, serviceConnection, BIND_AUTO_CREATE);
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        EventBus.getDefault().unregister(this);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        unbindService(serviceConnection);
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (mService == null || !mService.isWorking()) {
//            super.onBackPressed();
//        } else {
//            DialogUtil.getTextDialog(
//                this, R.string.update_back_dialog_title, R.string.update_back_dialog_message,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
//                    }
//                });
//        }
//    }
//
//    private void setMode(boolean configuration) {
//        if (mProgressLayout != null) {
//            mProgressLayout.setVisibility(configuration ? View.GONE : View.VISIBLE);
//        }
//        if (mConfigurationLayout != null) {
//            mConfigurationLayout.setVisibility(configuration ? View.VISIBLE : View.GONE);
//        }
//    }
//
//    public void onEventMainThread(ProgressUpdateEvent event) {
//        setMode(false);
//        mProgress.setProgress(event.getProgress());
//    }
//
//    public void onEventMainThread(CheckFinishEvent event) {
//        UpdateErrorEvent errorEvent = new UpdateErrorEvent(R.string.update_check_no_update);
//        mInfo = event.getInfo();
//        if (mInfo != null) {
//            UpdateInfo currentInfo = getPreferences().getUpdateInfo();
//            if (currentInfo == null || currentInfo.getDate() < mInfo.getDate()) {
//                showPackageDialog(mInfo);
//            } else {
//
//                onEventMainThread(errorEvent);
//            }
//        } else {
//            onEventMainThread(errorEvent);
//        }
//    }
//
//    public void onEventMainThread(DownloadFinishEvent event) {
//        if (event.getResult()) {
//            setMode(false);
//            mProgressTitle.setText(R.string.update_progress_unpack_title);
//            mProgress.setProgress(1);
//        }
//    }
//
//    public void onEventMainThread(RssFinishEvent event) {
//        if (event.getResult()) {
//            setMode(false);
//            mProgressTitle.setText(R.string.update_progress_calendar_title);
//            mProgress.setProgress(1);
//        }
//    }
//
//    public void onEventMainThread(CalendarFinishEvent event) {
//        if (event.getResult()) {
//            setMode(false);
//            mProgressTitle.setText(R.string.update_progress_routes_title);
//            mProgress.setProgress(1);
//        }
//    }
//
//    public void onEventMainThread(UnpackFinishEvent event) {
//        if (event.getResult()) {
//            setMode(false);
//            mProgressTitle.setText(R.string.update_progress_news_title);
//            mProgress.setProgress(1);
//        }
//    }
//
//    public void onEventMainThread(UpdateErrorEvent event) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(R.string.update_error_title);
//        builder.setMessage(event.getMessage());
//        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        builder.create().show();
//        setMode(true);
//    }
//
//    public void onEventMainThread(UpdateFinishEvent event) {
//        setMode(true);
//        if (mInfo != null) {
//            getPreferences().setUpdateInfo(mInfo);
//        }
//        Toast.makeText(this, R.string.update_finished, Toast.LENGTH_LONG).show();
//        goTo(MainActivity.class);
//        finish();
//        stopService(mServiceIntent);
//        EventBus.getDefault().removeStickyEvent(event);
//    }
//
//    public void showConnectionQuestion() {
//        if (Utils.isConnectionAvailable(this)) {
//            if (getPreferences().isAlwaysConnected()) {
//                if (mService != null) {
//                    mService.startOnline(mIsNoMedia.isChecked());
//                    setMode(false);
//                }
//            } else {
//                QuestionDialogFragment dialog = QuestionDialogFragment.newInstance(
//                    QuestionDialogFragment.DialogType.INTERNET_CONNECTION, null);
//                dialog.setListener(this);
//                dialog.show(getSupportFragmentManager(),
//                    QuestionDialogFragment.DialogType.INTERNET_CONNECTION.getTag());
//            }
//        } else {
//            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_LONG).show();
//        }
//    }
//
//    public void showPackageDialog(UpdateInfo info) {
//        Bundle bundle = new Bundle();
//        String size = Utils.humanReadableByteCount(info.getSize(), true);
//        description.setText(String.format(getString(R.string.update_info), size));
//        bundle.putString(Constant.EXTRA_DIALOG_PACKAGE_SIZE, size);
//        if (getPreferences().isAlwaysConnected()) {
//            EventBus.getDefault().post(new DownloadStartEvent());
//            mProgressTitle.setText(R.string.update_progress_main_title);
//            mProgress.setProgress(1);
//        } else {
//            QuestionDialogFragment dialog = QuestionDialogFragment.newInstance(
//                QuestionDialogFragment.DialogType.DATA_PACKAGE, bundle);
//            dialog.setListener(this);
//            dialog.show(getSupportFragmentManager(), QuestionDialogFragment.DialogType.DATA_PACKAGE.getTag());
//        }
//    }
//
//    @Override
//    public void onPositiveButton(DialogInterface dialog, QuestionDialogFragment.DialogType type) {
//        switch (type) {
//            case INTERNET_CONNECTION:
//                if (mService != null) {
//                    mService.startOnline(mIsNoMedia.isChecked());
//                    setMode(false);
//                }
//                break;
//            case DATA_PACKAGE:
//                if (mService != null) {
//                    EventBus.getDefault().post(new DownloadStartEvent());
//                    mProgressTitle.setText(R.string.update_progress_main_title);
//                    mProgress.setProgress(1);
//                }
//                break;
//        }
//    }
//
//    @Override
//    public void onNegativeButton(DialogInterface dialog, QuestionDialogFragment.DialogType type) {
//        setMode(true);
//        dialog.dismiss();
//    }
//}
