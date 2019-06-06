package com.huiketong.guanjiatong.activity;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huiketong.guanjiatong.MainApplication;
import com.huiketong.guanjiatong.R;
import com.huiketong.guanjiatong.base.BaseActivity;
import com.huiketong.guanjiatong.bean.RealPlaySquareInfo;
import com.huiketong.guanjiatong.myview.LoadingTextView;
import com.huiketong.guanjiatong.myview.ScreenOrientationHelper;
import com.huiketong.guanjiatong.myview.WaitDialog;
import com.huiketong.guanjiatong.presenter.SiteLivePresenter;
import com.huiketong.guanjiatong.utils.AudioPlayUtil;
import com.huiketong.guanjiatong.utils.DataManager;
import com.huiketong.guanjiatong.view.SiteLiveView;
import com.orhanobut.logger.Logger;
import com.videogo.constant.Config;
import com.videogo.constant.Constant;
import com.videogo.constant.IntentConsts;
import com.videogo.errorlayer.ErrorInfo;
import com.videogo.exception.BaseException;
import com.videogo.exception.ErrorCode;
import com.videogo.openapi.EZConstants;
import com.videogo.openapi.EZPlayer;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.openapi.bean.EZDeviceInfo;
import com.videogo.realplay.RealPlayStatus;
import com.videogo.util.ConnectionDetector;
import com.videogo.util.LocalInfo;
import com.videogo.util.LogUtil;
import com.videogo.util.RotateViewUtil;
import com.videogo.util.Utils;
import com.videogo.widget.CheckTextButton;
import com.videogo.widget.CustomRect;
import com.videogo.widget.CustomTouchListener;
import com.videogo.widget.RingView;
import com.videogo.widget.TitleBar;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.videogo.openapi.EZConstants.MSG_VIDEO_SIZE_CHANGED;

/**
 * 工地直播
 */
public class SiteLiveActivity extends BaseActivity<SiteLiveView, SiteLivePresenter> implements SiteLiveView, Handler.Callback, SurfaceHolder.Callback, View.OnTouchListener, View.OnClickListener {

    private static final int ANIMATION_DURING_TIME = 500;

    public static final float BIG_SCREEN_RATIO = 1.60f;

    // UI消息
    public static final int MSG_PLAY_UI_UPDATE = 200;

    public static final int MSG_AUTO_START_PLAY = 202;

    public static final int MSG_CLOSE_PTZ_PROMPT = 203;

    public static final int MSG_HIDE_PTZ_DIRECTION = 204;

    public static final int MSG_HIDE_PAGE_ANIM = 205;

    public static final int MSG_PLAY_UI_REFRESH = 206;

    public static final int MSG_PREVIEW_START_PLAY = 207;

    public static final int MSG_SET_VEDIOMODE_SUCCESS = 105;

    public static final int MSG_SET_VEDIOMODE_FAIL = 106;

    private int[] mStartXy = new int[2];
    private int[] mEndXy = new int[2];
    private int mCaptureDisplaySec = 0;
    private Timer mUpdateTimer = null;
    private TimerTask mUpdateTimerTask = null;
    private float mRealRatio = Constant.LIVE_VIEW_RATIO;
    // 播放比例 Play ratio
    private float mPlayScale = 1;
    private boolean mIsOnStop = false;
    private int mControlDisplaySec = 0;
    private AnimationDrawable mPageAnimDrawable = null;

    @BindView(R.id.realplay_sv)
    SurfaceView mRealPlaySv;
    @BindView(R.id.realplay_play_rl)
    RelativeLayout mRealPlayPlayRl;
    // 横屏对讲 Cross screen intercom
    @BindView(R.id.realplay_full_talk_anim_btn)
    ImageButton mRealPlayFullTalkAnimBtn;

    // 全屏按钮 Full screen button
    @BindView(R.id.fullscreen_full_button)
    CheckTextButton mFullscreenFullButton;
    @BindView(R.id.fullscreen_button)
    CheckTextButton mFullscreenButton;
    @BindView(R.id.realplay_full_anim_btn)
    ImageButton mRealPlayFullAnimBtn;
    @BindView(R.id.realplay_full_ptz_prompt_iv)
    ImageView mRealPlayFullPtzPromptIv;
    @BindView(R.id.realplay_full_ptz_anim_btn)
    ImageButton mRealPlayFullPtzAnimBtn;
    @BindView(R.id.realplay_full_video_start_btn)
    ImageButton mRealPlayFullRecordStartBtn;
    @BindView(R.id.realplay_full_video_btn)
    ImageButton mRealPlayFullRecordBtn;
    @BindView(R.id.realplay_full_video_container)
    FrameLayout mRealPlayFullRecordContainer;
    @BindView(R.id.realplay_full_ptz_btn)
    ImageButton mRealPlayFullPtzBtn;
    @BindView(R.id.realplay_full_previously_btn)
    ImageButton mRealPlayFullCaptureBtn;
    @BindView(R.id.realplay_full_talk_btn)
    ImageButton mRealPlayFullTalkBtn;
    @BindView(R.id.realplay_full_sound_btn)
    ImageButton mRealPlayFullSoundBtn;
    @BindView(R.id.realplay_full_play_btn)
    ImageButton mRealPlayFullPlayBtn;
    @BindView(R.id.realplay_full_operate_bar)
    RelativeLayout mRealPlayFullOperateBar;
    @BindView(R.id.realplay_ratio_tv)
    TextView mRealPlayRatioTv;
    @BindView(R.id.realplay_play_btn)
    ImageButton mRealPlayBtn;
    @BindView(R.id.realplay_prompt_rl)
    RelativeLayout mRealPlayPromptRl;
    @BindView(R.id.realplay_control_rl)
    LinearLayout mRealPlayControlRl;
    @BindView(R.id.realplay_sound_btn)
    ImageButton mRealPlaySoundBtn;
    @BindView(R.id.realplay_flow_tv)
    TextView mRealPlayFlowTv;
    private boolean mIsRecording = false;

    @BindView(R.id.realplay_loading_rl)
    RelativeLayout mRealPlayLoadingRl;
    @BindView(R.id.realplay_tip_tv)
    TextView mRealPlayTipTv;
    @BindView(R.id.realplay_play_iv)
    ImageView mRealPlayPlayIv;
    @BindView(R.id.realplay_loading)
    LoadingTextView mRealPlayPlayLoading;
    @BindView(R.id.realplay_privacy_ly)
    LinearLayout mRealPlayPlayPrivacyLy;
    @BindView(R.id.realplay_page_ly)
    LinearLayout mRealPlayPageLy;


    @BindView(R.id.realplay_capture_rl)
    RelativeLayout mRealPlayCaptureRl;
    @BindView(R.id.realplay_capture_watermark_iv)
    ImageView mRealPlayCaptureWatermarkIv;
    @BindView(R.id.realplay_record_ly)
    LinearLayout mRealPlayRecordLy;
    @BindView(R.id.realplay_record_iv)
    ImageView mRealPlayRecordIv;
    @BindView(R.id.realplay_record_tv)
    TextView mRealPlayRecordTv;

    @BindView(R.id.realplay_ptz_direction_iv)
    ImageView mRealPlayPtzDirectionIv;
    @BindView(R.id.realplay_quality_btn)
    Button mRealPlayQualityBtn;
    @BindView(R.id.realplay_full_flow_ly)
    LinearLayout mRealPlayFullFlowLy;
    @BindView(R.id.realplay_full_rate_tv)
    TextView mRealPlayFullRateTv;
    @BindView(R.id.realplay_full_flow_tv)
    TextView mRealPlayFullFlowTv;
    @BindView(R.id.realplay_capture_iv)
    ImageView mRealPlayCaptureIv;
    @BindView(R.id.title_bar_portrait)
    TitleBar mPortraitTitleBar;
    @BindView(R.id.title_bar_landscape)
    TitleBar mLandscapeTitleBar;
    @BindView(R.id.realplay_page_anim_iv)
    ImageView mPageAnimIv;

    private long mStreamFlow = 0;
    private Rect mRealPlayRect = null;
    // 直播预告 Live announcements
    private TextView mRealPlayPreviewTv = null;

    private AudioPlayUtil mAudioPlayUtil;
    private LocalInfo mLocalInfo;
    private Handler mHandler;
    private RotateViewUtil mRecordRotateViewUtil;
    // 播放器广播
    private RealPlayBroadcastReceiver mBroadcastReceiver;
    // 视频广场播放信息 Video square to play information
    private RealPlaySquareInfo mRealPlaySquareInfo;


    private EZConstants.EZVideoLevel mCurrentQulityMode = EZConstants.EZVideoLevel.VIDEO_LEVEL_HD;
    private EZCameraInfo mCameraInfo = null;
    private EZDeviceInfo mDeviceInfo = null;
    // 视频广场URL  Video Square URL
    private String mRtspUrl = null;
    private String mVerifyCode;
    private SurfaceHolder mRealPlaySh;
    private CustomTouchListener mRealPlayTouchListener = null;
    private int mStatus = RealPlayStatus.STATUS_INIT;
    private EZPlayer mEZPlayer = null;
    private int mOrientation = Configuration.ORIENTATION_PORTRAIT;
    private boolean mIsOnPtz = false;
    private PopupWindow mPtzPopupWindow = null;
    private PopupWindow mTalkPopupWindow = null;
    private RingView mTalkRingView = null;


    private LinearLayout mPtzControlLy = null;
    private int mForceOrientation = 0;
    private ScreenOrientationHelper mScreenOrientationHelper;
    // 对讲模式 Talkback mode
    private boolean mIsOnTalk = false;
    private HorizontalScrollView mRealPlayOperateBar;
    private LinearLayout mRealPlayPtzBtnLy;
    private LinearLayout mRealPlayTalkBtnLy;
    private LinearLayout mRealPlaySslBtnLy;
    private LinearLayout mRealPlayRecordContainerLy;
    private Button mRealPlaySslBtn;
    private FrameLayout mRealPlayRecordContainer;
    private LinearLayout mRealPlayCaptureBtnLy;
    private ImageButton mRealPlayTalkBtn;
    private ImageButton mRealPlayPrivacyBtn;
    private ImageButton mRealPlayCaptureBtn;
    private ImageButton mRealPlayRecordStartBtn;
    private ImageButton mRealPlayRecordBtn;
    private ImageButton mRealPlayPtzBtn;
    private RelativeLayout.LayoutParams mRealPlayCaptureRlLp;
    private CheckTextButton mFullScreenTitleBarBackBtn;
    private WaitDialog mWaitDialog;
    private PopupWindow mQualityPopupWindow;
    // 弱提示预览信息  Weak prompt preview information
    private long mStartTime = 0;
    private long mStopTime = 0;

    // 云台控制状态  PTZ control status
    private float mZoomScale = 0;
    private int mCommand = -1;
    private Button mTiletRightBtn = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_live);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Application application = (Application) getApplication();
        mAudioPlayUtil = AudioPlayUtil.getInstance(application);
        mLocalInfo = LocalInfo.getInstance();
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mLocalInfo.setScreenWidthHeight(metric.widthPixels, metric.heightPixels);
        mLocalInfo.setNavigationBarHeight((int) Math.ceil(25 * getResources().getDisplayMetrics().density));

        mHandler = new Handler(this);
        mRecordRotateViewUtil = new RotateViewUtil();
        // 创建播放器广播并注册
        mBroadcastReceiver = new RealPlayBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_USER_PRESENT);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mBroadcastReceiver, filter);

        mRealPlaySquareInfo = new RealPlaySquareInfo();
        // 获取播放基本数据
        Intent intent = getIntent();
        if (intent != null) {
            mCameraInfo = intent.getParcelableExtra(IntentConsts.EXTRA_CAMERA_INFO);
            mDeviceInfo = intent.getParcelableExtra(IntentConsts.EXTRA_DEVICE_INFO);
            mRtspUrl = intent.getStringExtra(IntentConsts.EXTRA_RTSP_URL);
            if (mCameraInfo != null) {
                mCurrentQulityMode = (mCameraInfo.getVideoLevel());
            }
            Logger.d("respUrl" + mRtspUrl);

            getRealPlaySquareInfo();
        }
        if (mDeviceInfo != null && mDeviceInfo.getIsEncrypt() == 1) {
            mVerifyCode = DataManager.getInstance().getDeviceSerialVerifyCode(mCameraInfo.getDeviceSerial());
        }
    }
    // 等到播放器的信息
    private void getRealPlaySquareInfo() {
        if (TextUtils.isEmpty(mRtspUrl)) {
            return;
        }
        Uri uri = Uri.parse(mRtspUrl.replaceFirst("&", "?"));
        try {
            mRealPlaySquareInfo.mSquareId = Integer.parseInt(uri.getQueryParameter("squareid"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            mRealPlaySquareInfo.mChannelNo = Integer.parseInt(Utils.getUrlValue(mRtspUrl, "channelno=", "&"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        mRealPlaySquareInfo.mCameraName = uri.getQueryParameter("cameraname");
        try {
            mRealPlaySquareInfo.mSoundType = Integer.parseInt(uri.getQueryParameter("soundtype"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        mRealPlaySquareInfo.mCoverUrl = uri.getQueryParameter("md5Serial");
        if (!TextUtils.isEmpty(mRealPlaySquareInfo.mCoverUrl)) {
            mRealPlaySquareInfo.mCoverUrl = mLocalInfo.getServAddr() + mRealPlaySquareInfo.mCoverUrl + "_mobile.jpeg";
        }
    }

    /**
     * 初始化视图
     */
    private void initView() {
        //保持屏幕常亮 Keep the screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initTitleBar();
        initRealPlayPageLy();

        mRealPlayPlayIv.setOnClickListener(this);

        mRealPlaySh = mRealPlaySv.getHolder();
        mRealPlaySh.addCallback(this);
        // 视频被点击
        mRealPlayTouchListener = new CustomTouchListener() {
            @Override
            public boolean canZoom(float scale) {
                if (mStatus == RealPlayStatus.STATUS_PLAY) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public boolean canDrag(int direction) {
                if (mStatus != RealPlayStatus.STATUS_PLAY) {
                    return false;
                }
                if (mEZPlayer != null && mDeviceInfo != null) {
                    // 出界判断 Out of bounds
                    if (DRAG_LEFT == direction || DRAG_RIGHT == direction) {
                        // 左移/右移出界判断 Left / right out of bounds
                        if (mDeviceInfo.isSupportPTZ()) {
                            return true;
                        }
                    } else if (DRAG_UP == direction || DRAG_DOWN == direction) {
                        // 上移/下移出界判断  Move up / down to judge
                        if (mDeviceInfo.isSupportPTZ()) {
                            return true;
                        }
                    }
                }
                return false;
            }

            @Override
            public void onSingleClick() {
                onRealPlaySvClick();
            }

            @Override
            public void onDoubleClick(MotionEvent e) {
            }

            @Override
            public void onZoom(float scale) {
                Logger.d("onZoom:" + scale);
                if (mEZPlayer != null && mDeviceInfo != null && mDeviceInfo.isSupportZoom()) {
                    // 开始缩放
                    startZoom(scale);
                }
            }

            @Override
            public void onDrag(int direction, float distance, float rate) {
                Logger.d("onDrag:" + direction);
                if (mEZPlayer != null) {
                    // 开始拖拽
                    startDrag(direction, distance, rate);
                }
            }

            @Override
            public void onEnd(int mode) {
                Logger.d("onEnd:" + mode);
                if (mEZPlayer != null) {
                    stopDrag(false);
                }
                if (mEZPlayer != null && mDeviceInfo != null && mDeviceInfo.isSupportZoom()) {
                    stopZoom();
                }
            }

            @Override
            public void onZoomChange(float scale, CustomRect oRect, CustomRect curRect) {
                Logger.d("onZoomChange:" + scale);
                if (mEZPlayer != null && mDeviceInfo != null && mDeviceInfo.isSupportZoom()) {
                    //采用云台调焦 Using PTZ focus
                    return;
                }
                if (mStatus == RealPlayStatus.STATUS_PLAY) {
                    if (scale > 1.0f && scale < 1.1f) {
                        scale = 1.1f;
                    }
                    // 设置播放缩放ui
                    setPlayScaleUI(scale, oRect, curRect);
                }
            }
        };

        mRealPlaySv.setOnTouchListener(mRealPlayTouchListener);
        mRealPlayFlowTv.setText("0k/s");
        mRealPlayCaptureRlLp = (RelativeLayout.LayoutParams) mRealPlayCaptureRl.getLayoutParams();
        mRealPlayFullRateTv.setText("0k/s");
        mRealPlayFullFlowTv.setText("0MB");
        if (mRtspUrl == null) {
            initOperateBarUI(false);

            mRealPlayFullOperateBar.setOnTouchListener(this);

            // mj           LinearLayout.LayoutParams realPlayPlayRlLp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
            //                    LayoutParams.WRAP_CONTENT);
            //            realPlayPlayRlLp.gravity = Gravity.CENTER;
            //            mRealPlayPlayRl.setLayoutParams(realPlayPlayRlLp);
            mRealPlayOperateBar.setVisibility(View.VISIBLE);
        } else {
            //mRealPlayPageLy.setBackgroundColor(getResources().getColor(R.color.black_bg));
            LinearLayout.LayoutParams realPlayPlayRlLp = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            realPlayPlayRlLp.gravity = Gravity.CENTER;
            //mj 2015/11/01 realPlayPlayRlLp.weight = 1;
            mRealPlayPlayRl.setLayoutParams(realPlayPlayRlLp);
            mRealPlayPlayRl.setBackgroundColor(getResources().getColor(R.color.common_bg));
        }

        setRealPlaySvLayout();
        initCaptureUI();
        mScreenOrientationHelper = new ScreenOrientationHelper(this, mFullscreenButton, /*mFullscreenFullButton*/mFullScreenTitleBarBackBtn);

        mWaitDialog = new WaitDialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        mWaitDialog.setCancelable(false);
    }
    // 初始化播放器页面
    private void initRealPlayPageLy() {
        /** 测量状态栏高度 Measure the status bar height**/
        ViewTreeObserver viewTreeObserver = mRealPlayPageLy.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (mRealPlayRect == null) {
                    // 获取状况栏高度
                    mRealPlayRect = new Rect();
                    getWindow().getDecorView().getWindowVisibleDisplayFrame(mRealPlayRect);
                }
            }
        });
    }
    // 初始化标题栏
    private void initTitleBar() {
        // 返回事件
        mPortraitTitleBar.addBackButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                closePtzPopupWindow();
                closeTalkPopupWindow(true, false);
                if (mStatus != RealPlayStatus.STATUS_STOP) {
                    stopRealPlay();
                    setRealPlayStopUI();
                }
                finish();
            }
        });
        if (mRtspUrl == null) {
        } else {
            //mPortraitTitleBar.setBackgroundColor(getResources().getColor(R.color.black_bg));
        }
        mLandscapeTitleBar.setStyle(Color.rgb(0xff, 0xff, 0xff), getResources().getDrawable(R.color.dark_bg_70p),
                getResources().getDrawable(R.drawable.selector_ez_message_back));
        mLandscapeTitleBar.setOnTouchListener(this);
        mFullScreenTitleBarBackBtn = new CheckTextButton(this);
        mFullScreenTitleBarBackBtn.setBackground(getResources().getDrawable(R.drawable.selector_ez_message_back));
        mLandscapeTitleBar.addLeftView(mFullScreenTitleBarBackBtn);
    }

    // 初始化截屏界面
    private void initCaptureUI() {
        mCaptureDisplaySec = 0;
        mRealPlayCaptureRl.setVisibility(View.GONE);
        mRealPlayCaptureIv.setImageURI(null);
        mRealPlayCaptureWatermarkIv.setTag(null);
        mRealPlayCaptureWatermarkIv.setVisibility(View.GONE);
    }

    // 开始缩放
    private void startZoom(float scale) {
        if (mEZPlayer == null) {
            return;
        }

        hideControlRlAndFullOperateBar(false);
        boolean preZoomIn = mZoomScale > 1.01 ? true : false;
        boolean zoomIn = scale > 1.01 ? true : false;
        if (mZoomScale != 0 && preZoomIn != zoomIn) {
            Logger.d("startZoom stop:" + mZoomScale);
            //            mEZOpenSDK.controlPTZ(mZoomScale > 1.01 ? RealPlayStatus.PTZ_ZOOMIN
            //                    : RealPlayStatus.PTZ_ZOOMOUT, RealPlayStatus.PTZ_SPEED_DEFAULT, EZPlayer.PTZ_COMMAND_STOP);
            mZoomScale = 0;
        }
        if (scale != 0 && (mZoomScale == 0 || preZoomIn != zoomIn)) {
            mZoomScale = scale;
            Logger.d("startZoom start:" + mZoomScale);
            //            mEZOpenSDK.controlPTZ(mZoomScale > 1.01 ? RealPlayStatus.PTZ_ZOOMIN
            //                    : RealPlayStatus.PTZ_ZOOMOUT, RealPlayStatus.PTZ_SPEED_DEFAULT, EZPlayer.PTZ_COMMAND_START);
        }
    }
    // 停止缩放
    private void stopZoom() {
        if (mEZPlayer == null) {
            return;
        }
        if (mZoomScale != 0) {
            Logger.d("stopZoom stop:" + mZoomScale);
            //            mEZOpenSDK.controlPTZ(mZoomScale > 1.01 ? RealPlayStatus.PTZ_ZOOMIN
            //                    : RealPlayStatus.PTZ_ZOOMOUT, RealPlayStatus.PTZ_SPEED_DEFAULT, EZPlayer.PTZ_COMMAND_STOP);
            mZoomScale = 0;
        }
    }

    public void startDrag(int direction, float distance, float rate) {
    }

    public void stopDrag(boolean control) {
    }
    // 点击播放器sv
    private void onRealPlaySvClick() {
        if (mCameraInfo != null && mEZPlayer != null && mDeviceInfo != null) {
            if (mDeviceInfo.getStatus() != 1) {
                return;
            }
            if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
                setRealPlayControlRlVisibility();
                Logger.w("设置播放器控件RL的可见性");
            } else {
                Logger.w("设置播放器全屏操作可见度");
                setRealPlayFullOperateBarVisibility();
            }
        } else if (mRtspUrl != null) {
            Logger.w("设置播放器控件RL的可见性");
            setRealPlayControlRlVisibility();
        }
    }
    // 设置播放器控件RL的可见性
    private void setRealPlayControlRlVisibility() {
        if (mLandscapeTitleBar.getVisibility() == View.VISIBLE || mRealPlayControlRl.getVisibility() == View.VISIBLE) {
            //            mRealPlayControlRl.setVisibility(View.GONE);
            mLandscapeTitleBar.setVisibility(View.GONE);
            closeQualityPopupWindow();
        } else {
            mRealPlayControlRl.setVisibility(View.VISIBLE);
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (!mIsOnTalk && !mIsOnPtz) {
                    mLandscapeTitleBar.setVisibility(View.VISIBLE);
                }
            } else {
                mLandscapeTitleBar.setVisibility(View.GONE);
            }
            mControlDisplaySec = 0;
        }
    }
    // 设置播放器全屏操作可见度
    private void setRealPlayFullOperateBarVisibility() {
        if (mLandscapeTitleBar.getVisibility() == View.VISIBLE) {
            mRealPlayFullOperateBar.setVisibility(View.GONE);
            if (!mIsOnTalk && !mIsOnPtz) {
                mFullscreenFullButton.setVisibility(View.GONE);
            }
            mLandscapeTitleBar.setVisibility(View.GONE);
        } else {
            if (!mIsOnTalk && !mIsOnPtz) {
                //mj mRealPlayFullOperateBar.setVisibility(View.VISIBLE);
                //                mFullscreenFullButton.setVisibility(View.VISIBLE);
                mLandscapeTitleBar.setVisibility(View.VISIBLE);
            }
            mControlDisplaySec = 0;
        }
    }

    /**
     * 播放器广播
     */
    private class RealPlayBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
                closePtzPopupWindow();
                closeTalkPopupWindow(true, false);
                if (mStatus != RealPlayStatus.STATUS_STOP) {
                    stopRealPlay();
                    mStatus = RealPlayStatus.STATUS_PAUSE;
                    setRealPlayStopUI();
                }
            }
        }
    }
    // 设置播放器停止ui
    private void setRealPlayStopUI() {
        stopUpdateTimer();
        updateOrientation();
        setRealPlaySvLayout();
        setStopLoading();
        hideControlRlAndFullOperateBar(true);
        mRealPlayBtn.setBackgroundResource(R.drawable.selector_ez_play_play);
        if (mCameraInfo != null && mDeviceInfo != null) {
            closePtzPopupWindow();
            setFullPtzStopUI(false);
            mRealPlayCaptureBtn.setEnabled(false);
            mRealPlayRecordBtn.setEnabled(false);
            if (mDeviceInfo.getStatus() == 1) {
                mRealPlayQualityBtn.setEnabled(true);
            } else {
                mRealPlayQualityBtn.setEnabled(false);
            }
            mRealPlayFullPtzBtn.setEnabled(false);
            if (mDeviceInfo.getStatus() == 1) {
                mRealPlayPrivacyBtn.setEnabled(true);
                mRealPlaySslBtn.setEnabled(true);
            } else {
                mRealPlayPrivacyBtn.setEnabled(false);
                mRealPlaySslBtn.setEnabled(false);
            }

            mRealPlayFullPlayBtn.setBackgroundResource(R.drawable.selector_ez_play_full_play);
            mRealPlayFullCaptureBtn.setEnabled(false);
            mRealPlayFullRecordBtn.setEnabled(false);
            mRealPlayPtzBtn.setEnabled(false);
        }
    }
    // 停止加载
    public void setStopLoading() {
        mRealPlayLoadingRl.setVisibility(View.VISIBLE);
        mRealPlayTipTv.setVisibility(View.GONE);
        mRealPlayPlayLoading.setVisibility(View.GONE);
        mRealPlayPlayIv.setVisibility(View.VISIBLE);
        mRealPlayPlayPrivacyLy.setVisibility(View.GONE);
    }

    // 设置播放器sv布局
    private void setRealPlaySvLayout() {
        final int screenWidth = mLocalInfo.getScreenWidth();
        final int screenHeight = (mOrientation == Configuration.ORIENTATION_PORTRAIT) ? (mLocalInfo.getScreenHeight() - mLocalInfo
                .getNavigationBarHeight()) : mLocalInfo.getScreenHeight();
        final RelativeLayout.LayoutParams realPlaySvlp = Utils.getPlayViewLp(mRealRatio, mOrientation,
                mLocalInfo.getScreenWidth(), (int) (mLocalInfo.getScreenWidth() * Constant.LIVE_VIEW_RATIO),
                screenWidth, screenHeight);

        RelativeLayout.LayoutParams loadingR1Lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                realPlaySvlp.height);
        //        loadingR1Lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        //        mRealPlayLoadingRl.setLayoutParams(loadingR1Lp);
        //        mRealPlayPromptRl.setLayoutParams(loadingR1Lp);
        RelativeLayout.LayoutParams svLp = new RelativeLayout.LayoutParams(realPlaySvlp.width, realPlaySvlp.height);
        //mj svLp.addRule(RelativeLayout.CENTER_IN_PARENT);
        mRealPlaySv.setLayoutParams(svLp);

        if (mRtspUrl == null) {
            //            LinearLayout.LayoutParams realPlayPlayRlLp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
            //                    LayoutParams.WRAP_CONTENT);
            //            realPlayPlayRlLp.gravity = Gravity.CENTER;
            //            mRealPlayPlayRl.setLayoutParams(realPlayPlayRlLp);
        } else {
            LinearLayout.LayoutParams realPlayPlayRlLp = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            realPlayPlayRlLp.gravity = Gravity.CENTER;
            //realPlayPlayRlLp.weight = 1;
            mRealPlayPlayRl.setLayoutParams(realPlayPlayRlLp);
        }
        mRealPlayTouchListener.setSacaleRect(Constant.MAX_SCALE, 0, 0, realPlaySvlp.width, realPlaySvlp.height);
        setPlayScaleUI(1, null, null);
    }
    // 设置播放缩放ui
    private void setPlayScaleUI(float scale, CustomRect oRect, CustomRect curRect) {
        if (scale == 1) {
            if (mPlayScale == scale) {
                return;
            }
            mRealPlayRatioTv.setVisibility(View.GONE);
            try {
                if (mEZPlayer != null) {
                    mEZPlayer.setDisplayRegion(false, null, null);
                }
            } catch (BaseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            if (mPlayScale == scale) {
                try {
                    if (mEZPlayer != null) {
                        mEZPlayer.setDisplayRegion(true, oRect, curRect);
                    }
                } catch (BaseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return;
            }
            RelativeLayout.LayoutParams realPlayRatioTvLp = (RelativeLayout.LayoutParams) mRealPlayRatioTv
                    .getLayoutParams();
            if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
                realPlayRatioTvLp.setMargins(Utils.dip2px(this, 10), Utils.dip2px(this, 10), 0, 0);
            } else {
                realPlayRatioTvLp.setMargins(Utils.dip2px(this, 70), Utils.dip2px(this, 20), 0, 0);
            }
            mRealPlayRatioTv.setLayoutParams(realPlayRatioTvLp);
            String sacleStr = String.valueOf(scale);
            mRealPlayRatioTv.setText(sacleStr.subSequence(0, Math.min(3, sacleStr.length())) + "X");
            //mj mRealPlayRatioTv.setVisibility(View.VISIBLE);
            mRealPlayRatioTv.setVisibility(View.GONE);
            hideControlRlAndFullOperateBar(false);
            try {
                if (mEZPlayer != null) {
                    mEZPlayer.setDisplayRegion(true, oRect, curRect);
                }
            } catch (BaseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        mPlayScale = scale;
    }
    // 隐藏控制Rl和全操作栏
    private void hideControlRlAndFullOperateBar(boolean excludeLandscapeTitle) {
        //        mRealPlayControlRl.setVisibility(View.GONE);
        closeQualityPopupWindow();
        if (mRealPlayFullOperateBar != null) {
            mRealPlayFullOperateBar.setVisibility(View.GONE);
            if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
                mFullscreenFullButton.setVisibility(View.GONE);
            } else {
                if (!mIsOnTalk && !mIsOnPtz) {
                    mFullscreenFullButton.setVisibility(View.GONE);
                }
            }
        }
        if (excludeLandscapeTitle && mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (!mIsOnTalk && !mIsOnPtz) {
                mLandscapeTitleBar.setVisibility(View.VISIBLE);
            }
        } else {
            mLandscapeTitleBar.setVisibility(View.GONE);
        }
    }
    // 关闭质量弹出窗口
    private void closeQualityPopupWindow() {
        if (mQualityPopupWindow != null) {
            dismissPopWindow(mQualityPopupWindow);
            mQualityPopupWindow = null;
        }
    }
    // 停止播放器
    private void stopRealPlay() {
        Logger.d("stopRealPlay");
        mStatus = RealPlayStatus.STATUS_STOP;

        stopUpdateTimer();
        if (mEZPlayer != null) {
            stopRealPlayRecord();
            mEZPlayer.stopRealPlay();
        }
        mStreamFlow = 0;
    }
    // 停止播放器广播
    private void stopRealPlayRecord() {
        if (mEZPlayer == null || !mIsRecording) {
            return;
        }
        Toast.makeText(this, "已保存至相册", Toast.LENGTH_SHORT).show();

        // 设置录像按钮为check状态
        //Set the recording button to the check status
        if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
            if (!mIsOnStop) {
                mRecordRotateViewUtil.applyRotation(mRealPlayRecordContainer, mRealPlayRecordStartBtn,
                        mRealPlayRecordBtn, 0, 90);
            } else {
                mRealPlayRecordStartBtn.setVisibility(View.GONE);
                mRealPlayRecordBtn.setVisibility(View.VISIBLE);
            }
            mRealPlayFullRecordStartBtn.setVisibility(View.GONE);
            mRealPlayFullRecordBtn.setVisibility(View.VISIBLE);
        } else {
            if (!mIsOnStop) {
                mRecordRotateViewUtil.applyRotation(mRealPlayFullRecordContainer, mRealPlayFullRecordStartBtn,
                        mRealPlayFullRecordBtn, 0, 90);
            } else {
                mRealPlayFullRecordStartBtn.setVisibility(View.GONE);
                mRealPlayFullRecordBtn.setVisibility(View.VISIBLE);

            }
            mRealPlayRecordStartBtn.setVisibility(View.GONE);
            mRealPlayRecordBtn.setVisibility(View.VISIBLE);
        }
        mAudioPlayUtil.playAudioFile(AudioPlayUtil.RECORD_SOUND);
        mEZPlayer.stopLocalRecord();

        // 计时按钮不可见
        //The timed button is not visible
        mRealPlayRecordLy.setVisibility(View.GONE);
        //        mRealPlayCaptureRl.setVisibility(View.VISIBLE);
        mCaptureDisplaySec = 0;
        //        try {
        //            mRealPlayCaptureIv.setImageURI(Uri.parse(mRecordFilePath));
        //        } catch (OutOfMemoryError e) {
        //            e.printStackTrace();
        //        }
        //        mRealPlayCaptureWatermarkIv.setTag(mRecordFilePath);
        mIsRecording = false;
        updateCaptureUI();
    }

    // 停止更新定时器
    private void stopUpdateTimer() {
        mCaptureDisplaySec = 4;
        updateCaptureUI();
        mHandler.removeMessages(MSG_PLAY_UI_UPDATE);
        if (mUpdateTimer != null) {
            mUpdateTimer.cancel();
            mUpdateTimer = null;
        }

        if (mUpdateTimerTask != null) {
            mUpdateTimerTask.cancel();
            mUpdateTimerTask = null;
        }
    }
    // 更新抓图/录像显示UI
    //Update the capture / video display UI
    private void updateCaptureUI() {
        if (mRealPlayCaptureRl.getVisibility() == View.VISIBLE) {
            if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
                if (mRealPlayControlRl.getVisibility() == View.VISIBLE) {
                    mRealPlayCaptureRlLp.setMargins(0, 0, 0, Utils.dip2px(this, 40));
                } else {
                    mRealPlayCaptureRlLp.setMargins(0, 0, 0, 0);
                }
                mRealPlayCaptureRl.setLayoutParams(mRealPlayCaptureRlLp);
            } else {
                RelativeLayout.LayoutParams realPlayCaptureRlLp = new RelativeLayout.LayoutParams(
                        Utils.dip2px(this, 65), Utils.dip2px(this, 45));
                realPlayCaptureRlLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                realPlayCaptureRlLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                mRealPlayCaptureRl.setLayoutParams(realPlayCaptureRlLp);
            }
            if (mRealPlayCaptureWatermarkIv.getTag() != null) {
                mRealPlayCaptureWatermarkIv.setVisibility(View.VISIBLE);
                mRealPlayCaptureWatermarkIv.setTag(null);
            }
        }
        if (mCaptureDisplaySec >= 4) {
            initCaptureUI();
        }
    }
    private void closePtzPopupWindow() {
        mIsOnPtz = false;
        if (mPtzPopupWindow != null) {
            dismissPopWindow(mPtzPopupWindow);
            mPtzPopupWindow = null;
            mPtzControlLy = null;
            setForceOrientation(0);
        }
    }

    private void dismissPopWindow(PopupWindow popupWindow) {
        if (popupWindow != null && !isFinishing()) {
            try {
                popupWindow.dismiss();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    public void setForceOrientation(int orientation) {
        if (mForceOrientation == orientation) {
            Logger.i("setForceOrientation no change");
            return;
        }
        mForceOrientation = orientation;
        if (mForceOrientation != 0) {
            if (mForceOrientation != mOrientation) {
                if (mForceOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                    mScreenOrientationHelper.portrait();
                } else {
                    mScreenOrientationHelper.landscape();
                }
            }
            mScreenOrientationHelper.disableSensorOrientation();
        } else {
            updateOrientation();
        }
    }

    private void updateOrientation() {
        if (mIsOnTalk) {
            if (mEZPlayer != null && mDeviceInfo != null && mDeviceInfo.isSupportTalk() != EZConstants.EZTalkbackCapability.EZTalkbackNoSupport) {
                setOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            } else {
                setForceOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        } else {
            if (mStatus == RealPlayStatus.STATUS_PLAY) {
                setOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            } else {
                if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    setOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    setOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }
            }
        }
    }

    private void setOrientation(int sensor) {
        if (mForceOrientation != 0) {
            Logger.d("setOrientation mForceOrientation:" + mForceOrientation);
            return;
        }

        if (sensor == ActivityInfo.SCREEN_ORIENTATION_SENSOR)
            mScreenOrientationHelper.enableSensorOrientation();
        else
            mScreenOrientationHelper.disableSensorOrientation();
    }

    // 关闭对话弹出窗口
    private void closeTalkPopupWindow(boolean stopTalk, boolean startAnim) {
        if (mTalkPopupWindow != null) {
            Logger.d("closeTalkPopupWindow");
            dismissPopWindow(mTalkPopupWindow);
            mTalkPopupWindow = null;
        }
        mTalkRingView = null;
        if (stopTalk)
            stopVoiceTalk(startAnim);
    }

    // 停止语音通话
    private void stopVoiceTalk(boolean startAnim) {
        if (mCameraInfo == null || mEZPlayer == null) {
            return;
        }
        Logger.d("stopVoiceTalk");
        mEZPlayer.stopVoiceTalk();
        handleVoiceTalkStoped(startAnim);
    }

    // 处理语音通话停止
    private void handleVoiceTalkStoped(boolean startAnim) {
        if (mIsOnTalk) {
            mIsOnTalk = false;
            setForceOrientation(0);
        }
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (startAnim) {
                mRealPlayFullTalkAnimBtn.setVisibility(View.GONE);
                mFullscreenFullButton.setVisibility(View.GONE);
                mRealPlayFullAnimBtn.setBackgroundResource(R.drawable.speech_1);
                startFullBtnAnim(mRealPlayFullAnimBtn, mEndXy, mStartXy, new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mRealPlayFullAnimBtn.setVisibility(View.GONE);
                        onRealPlaySvClick();
                    }
                });
            } else {
                mRealPlayFullTalkAnimBtn.setVisibility(View.GONE);
                mFullscreenFullButton.setVisibility(View.GONE);
            }
        }

        mRealPlayTalkBtn.setEnabled(true);
        mRealPlayFullTalkBtn.setEnabled(true);
        mRealPlayFullTalkAnimBtn.setEnabled(true);

        if (mStatus == RealPlayStatus.STATUS_PLAY) {
            if (mEZPlayer != null) {
                if (mLocalInfo.isSoundOpen()) {
                    mEZPlayer.openSound();
                } else {
                    mEZPlayer.closeSound();
                }
            }
        }
    }
    // 设置完整的ptz停止ui
    private void setFullPtzStopUI(boolean startAnim) {
        mIsOnPtz = false;
        if (startAnim) {
            mRealPlayFullPtzAnimBtn.setVisibility(View.GONE);
            mFullscreenFullButton.setVisibility(View.GONE);
            mRealPlayFullAnimBtn.setBackgroundResource(R.drawable.yuntai_pressed);
            startFullBtnAnim(mRealPlayFullAnimBtn, mEndXy, mStartXy, new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mRealPlayFullAnimBtn.setVisibility(View.GONE);
                    onRealPlaySvClick();
                }
            });
        } else {
            mRealPlayFullPtzAnimBtn.setVisibility(View.GONE);
            mFullscreenFullButton.setVisibility(View.GONE);
        }
        mRealPlayFullPtzPromptIv.setVisibility(View.GONE);
        mHandler.removeMessages(MSG_CLOSE_PTZ_PROMPT);
    }

    private void startFullBtnAnim(final View animView, final int[] startXy, final int[] endXy,
                                  final Animation.AnimationListener animationListener) {
        animView.setVisibility(View.VISIBLE);
        TranslateAnimation anim = new TranslateAnimation(startXy[0], endXy[0], startXy[1], endXy[1]);
        anim.setAnimationListener(animationListener);
        anim.setDuration(ANIMATION_DURING_TIME);
        animView.startAnimation(anim);
    }

    private void initOperateBarUI(boolean bigScreen) {
        bigScreen = false;
        if (mRealPlayOperateBar != null) {
            mRealPlayOperateBar.setVisibility(View.GONE);
            mRealPlayOperateBar = null;
        }
        if (bigScreen) {
            mRealPlayOperateBar = (HorizontalScrollView) findViewById(R.id.ezopen_realplay_operate_bar2);
            findViewById(R.id.ezopen_realplay_operate_bar).setVisibility(View.GONE);

            mRealPlayPtzBtnLy = (LinearLayout) findViewById(R.id.realplay_ptz_btn_ly2);
            mRealPlayTalkBtnLy = (LinearLayout) findViewById(R.id.realplay_talk_btn_ly2);
            mRealPlaySslBtnLy = (LinearLayout) findViewById(R.id.realplay_ssl_btn_ly2);
            mRealPlayCaptureBtnLy = (LinearLayout) findViewById(R.id.realplay_previously_btn_ly2);
            mRealPlayRecordContainerLy = (LinearLayout) findViewById(R.id.realplay_video_container_ly2);

            mRealPlayTalkBtn = (ImageButton) findViewById(R.id.realplay_talk_btn2);
            mRealPlaySslBtn = (Button) findViewById(R.id.realplay_ssl_btn2);
            mRealPlayPrivacyBtn = (ImageButton) findViewById(R.id.realplay_privacy_btn2);
            mRealPlayCaptureBtn = (ImageButton) findViewById(R.id.realplay_previously_btn2);
            mRealPlayRecordContainer = findViewById(R.id.realplay_video_container2);
            mRealPlayRecordBtn = (ImageButton) findViewById(R.id.realplay_video_btn2);
            mRealPlayRecordStartBtn = (ImageButton) findViewById(R.id.realplay_video_start_btn2);
            mRealPlayPtzBtn = (ImageButton) findViewById(R.id.realplay_ptz_btn2);
        } else {
            mRealPlayOperateBar = (HorizontalScrollView) findViewById(R.id.ezopen_realplay_operate_bar);
            findViewById(R.id.ezopen_realplay_operate_bar2).setVisibility(View.GONE);

            mRealPlayPtzBtnLy = (LinearLayout) findViewById(R.id.realplay_ptz_btn_ly);
            mRealPlayTalkBtnLy = (LinearLayout) findViewById(R.id.realplay_talk_btn_ly);
            mRealPlaySslBtnLy = (LinearLayout) findViewById(R.id.realplay_ssl_btn_ly);
            mRealPlayCaptureBtnLy = (LinearLayout) findViewById(R.id.realplay_previously_btn_ly);
            mRealPlayRecordContainerLy = (LinearLayout) findViewById(R.id.realplay_video_container_ly);

            mRealPlayTalkBtn = (ImageButton) findViewById(R.id.realplay_talk_btn);
            mRealPlaySslBtn = (Button) findViewById(R.id.realplay_ssl_btn);
            mRealPlayPrivacyBtn = (ImageButton) findViewById(R.id.realplay_privacy_btn);
            mRealPlayCaptureBtn = (ImageButton) findViewById(R.id.realplay_previously_btn);
            mRealPlayRecordContainer = findViewById(R.id.realplay_video_container);
            mRealPlayRecordBtn = (ImageButton) findViewById(R.id.realplay_video_btn);
            mRealPlayRecordStartBtn = (ImageButton) findViewById(R.id.realplay_video_start_btn);
            mRealPlayPtzBtn = (ImageButton) findViewById(R.id.realplay_ptz_btn);
        }
        mRealPlayTalkBtn.setEnabled(false);
        mRealPlayOperateBar.setVisibility(View.VISIBLE);
    }
    // 更新加载进度
    private void updateLoadingProgress(final int progress) {
        mRealPlayPlayLoading.setTag(Integer.valueOf(progress));
        mRealPlayPlayLoading.setText(progress + "%");
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (mRealPlayPlayLoading != null) {
                    Integer tag = (Integer) mRealPlayPlayLoading.getTag();
                    if (tag != null && tag.intValue() == progress) {
                        Random r = new Random();
                        mRealPlayPlayLoading.setText((progress + r.nextInt(20)) + "%");
                    }
                }
            }

        }, 500);
    }
    // 获取相机属性成功
    private void handleGetCameraInfoSuccess() {
        Logger.d("handleGetCameraInfoSuccess");

        updateUI();

    }

    // 更新UI
    private void updateUI() {
        setRealPlayTalkUI();

        setVideoLevel();

/*
        if (mRealPlayMgr != null && mRealPlayMgr.getSupportPtzPrivacy() == 1) {
            mRealPlayPrivacyBtnLy.setVisibility(View.VISIBLE);
            if (mCameraInfo.getOnlineStatus() == 1 && mRealPlayMgr.getPrivacyStatus() != 1) {
                mRealPlayQualityBtn.setEnabled(true);
            } else {
                mRealPlayQualityBtn.setEnabled(false);
            }
        } else {
            mRealPlayPrivacyBtnLy.setVisibility(View.GONE);
        }
        */
        //        mRealPlayPrivacyBtnLy.setVisibility(View.GONE);
        //        setSoundLocalizationUI();

        {
            mRealPlaySslBtnLy.setVisibility(View.GONE);
        }

        if (getSupportPtz() == 1) {
            mRealPlayPtzBtnLy.setVisibility(View.VISIBLE);
            mRealPlayFullPtzBtn.setVisibility(View.VISIBLE);
        } else {
            //mRealPlayPtzBtnLy.setVisibility(View.GONE);
            //mRealPlayFullPtzBtn.setVisibility(View.GONE);
            mRealPlayPtzBtnLy.setEnabled(false);
            mRealPlayFullPtzBtn.setEnabled(false);
        }

        updatePermissionUI();
    }

    private void updatePermissionUI() {
        mRealPlayTalkBtnLy.setVisibility(View.VISIBLE);
    }
    private int getSupportPtz() {
        if (mEZPlayer == null || mDeviceInfo == null) {
            return 0;
        }
        if (mDeviceInfo.isSupportPTZ() || mDeviceInfo.isSupportZoom()) {
            return 1;
        } else {
            return 0;
        }
    }
    // 设置视频等级
    private void setVideoLevel() {
        if (mCameraInfo == null || mEZPlayer == null || mDeviceInfo == null) {
            return;
        }

        if (mDeviceInfo.getStatus() == 1) {
            mRealPlayQualityBtn.setEnabled(true);
        } else {
            mRealPlayQualityBtn.setEnabled(false);
        }

        /**************
         * 本地数据保存 需要更新之前获取到的设备列表信息，开发者自己设置
         *
         * Local data saved need to be updated before the obtained device list information, the developer's own settings
         * *********************/
        mCameraInfo.setVideoLevel(mCurrentQulityMode.getVideoLevel());

        //
        /**
         *
         * 视频质量，2-高清，1-标清，0-流畅
         * Video quality, 2-HD, 1-standard, 0- smooth
         *
         */
        if (mCurrentQulityMode.getVideoLevel() == EZConstants.EZVideoLevel.VIDEO_LEVEL_FLUNET.getVideoLevel()) {
            mRealPlayQualityBtn.setText("流畅");
        } else if (mCurrentQulityMode.getVideoLevel() == EZConstants.EZVideoLevel.VIDEO_LEVEL_BALANCED.getVideoLevel()) {
            mRealPlayQualityBtn.setText("标清");
        } else if (mCurrentQulityMode.getVideoLevel() == EZConstants.EZVideoLevel.VIDEO_LEVEL_HD.getVideoLevel()) {
            mRealPlayQualityBtn.setText("高清");
        }
    }

    private void setRealPlayTalkUI() {
        if (mEZPlayer != null && mDeviceInfo != null && (mDeviceInfo.isSupportTalk() != EZConstants.EZTalkbackCapability.EZTalkbackNoSupport)) {
            mRealPlayTalkBtnLy.setVisibility(View.VISIBLE);
            if (mCameraInfo != null && mDeviceInfo.getStatus() == 1) {
                mRealPlayTalkBtn.setEnabled(true);
            } else {
                mRealPlayTalkBtn.setEnabled(false);
            }
            if (mDeviceInfo.isSupportTalk() != EZConstants.EZTalkbackCapability.EZTalkbackNoSupport) {
                mRealPlayFullTalkBtn.setVisibility(View.VISIBLE);
            } else {
                mRealPlayFullTalkBtn.setVisibility(View.GONE);
            }
        } else {
            mRealPlayTalkBtnLy.setVisibility(View.GONE);
            mRealPlayFullTalkBtn.setVisibility(View.GONE);
        }
        mRealPlayTalkBtnLy.setVisibility(View.VISIBLE);
        //mRealPlayTalkBtn.setEnabled(false);
    }


    @Override
    protected SiteLiveView createView() {
        return this;
    }

    @Override
    protected SiteLivePresenter createPresenter() {
        return new SiteLivePresenter(this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (this.isFinishing()) {
            return false;
        }
        switch (msg.what) {
            case MSG_VIDEO_SIZE_CHANGED:
                Logger.d("MSG_VIDEO_SIZE_CHANGED");
                try {
                    String temp = (String) msg.obj;
                    String[] strings = temp.split(":");
                    int mVideoWidth = Integer.parseInt(strings[0]);
                    int mVideoHeight = Integer.parseInt(strings[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case EZConstants.EZRealPlayConstants.MSG_GET_CAMERA_INFO_SUCCESS:
                updateLoadingProgress(20);
                handleGetCameraInfoSuccess();
                break;
            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_PLAY_START:
                updateLoadingProgress(40);
                break;
            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_CONNECTION_START:
                updateLoadingProgress(60);
                break;
            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_CONNECTION_SUCCESS:
                updateLoadingProgress(80);
                break;
            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_PLAY_SUCCESS:
                handlePlaySuccess(msg);
                break;
            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_PLAY_FAIL:
                handlePlayFail(msg.obj);
                break;
            case EZConstants.EZRealPlayConstants.MSG_SET_VEDIOMODE_SUCCESS:
                handleSetVedioModeSuccess();
                break;
            case EZConstants.EZRealPlayConstants.MSG_SET_VEDIOMODE_FAIL:
                handleSetVedioModeFail(msg.arg1);
                break;
            case EZConstants.EZRealPlayConstants.MSG_PTZ_SET_FAIL:
                handlePtzControlFail(msg);
                break;
            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_VOICETALK_SUCCESS:
                handleVoiceTalkSucceed();
                break;
            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_VOICETALK_STOP:
                handleVoiceTalkStoped(false);
                break;
            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_VOICETALK_FAIL:
                ErrorInfo errorInfo = (ErrorInfo) msg.obj;
                handleVoiceTalkFailed(errorInfo);
                break;
            case MSG_PLAY_UI_UPDATE:
                updateRealPlayUI();
                break;
            case MSG_AUTO_START_PLAY:
                startRealPlay();
                break;
            case MSG_CLOSE_PTZ_PROMPT:
                mRealPlayFullPtzPromptIv.setVisibility(View.GONE);
                break;
            case MSG_HIDE_PTZ_DIRECTION:
                handleHidePtzDirection(msg);
                break;
            case MSG_HIDE_PAGE_ANIM:
                hidePageAnim();
                break;
            case MSG_PLAY_UI_REFRESH:
                initUI();
                break;
            case MSG_PREVIEW_START_PLAY:
                mPageAnimIv.setVisibility(View.GONE);
                mRealPlayPreviewTv.setVisibility(View.GONE);
                mStatus = RealPlayStatus.STATUS_INIT;
                startRealPlay();
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
    // 播放
    private void handlePlaySuccess(Message msg) {
        Logger.d("handlePlaySuccess");
        mStatus = RealPlayStatus.STATUS_PLAY;

        // 声音处理  Sound processing
        setRealPlaySound();

        // temp solution for OPENSDK-92
        // Android 预览3Q10的时候切到流畅之后 视频播放窗口变大了
        //        if (msg.arg1 != 0) {
        //            mRealRatio = (float) msg.arg2 / msg.arg1;
        //        } else {
        //            mRealRatio = Constant.LIVE_VIEW_RATIO;
        //        }
        mRealRatio = Constant.LIVE_VIEW_RATIO;

        boolean bSupport = true;//(float) mLocalInfo.getScreenHeight() / mLocalInfo.getScreenWidth() >= BIG_SCREEN_RATIO;
        if (bSupport) {
            initOperateBarUI(mRealRatio <= Constant.LIVE_VIEW_RATIO);
            initUI();
            if (mRealRatio <= Constant.LIVE_VIEW_RATIO) {
                setBigScreenOperateBtnLayout();
            }
        }
        setRealPlaySvLayout();
        setRealPlaySuccessUI();
        updatePtzUI();
        //        startPrivacyAnim();
        updateTalkUI();
        if (mDeviceInfo != null && mDeviceInfo.isSupportTalk() != EZConstants.EZTalkbackCapability.EZTalkbackNoSupport) {
            mRealPlayTalkBtn.setEnabled(true);
        }else{
            mRealPlayTalkBtn.setEnabled(false);
        }
        if (mEZPlayer != null) {
            mStreamFlow = mEZPlayer.getStreamFlow();
        }
    }

    private void updatePtzUI() {
        if (!mIsOnPtz) {
            return;
        }
        if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
            setFullPtzStopUI(false);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    openPtzPopupWindow(mRealPlayPlayRl);
                }
            });
        } else {
            closePtzPopupWindow();
            setFullPtzStartUI(false);
        }
    }
    // 设置播放器成功ui
    private void setRealPlaySuccessUI() {
        mStopTime = System.currentTimeMillis();
        showType();

        updateOrientation();
        setLoadingSuccess();
        mRealPlayFlowTv.setVisibility(View.VISIBLE);
        mRealPlayFullFlowLy.setVisibility(View.VISIBLE);
        mRealPlayBtn.setBackgroundResource(R.drawable.play_stop_selector);

        if (mCameraInfo != null && mDeviceInfo != null) {
            mRealPlayCaptureBtn.setEnabled(true);
            mRealPlayRecordBtn.setEnabled(true);
            if (mDeviceInfo.getStatus() == 1) {
                mRealPlayQualityBtn.setEnabled(true);
            } else {
                mRealPlayQualityBtn.setEnabled(false);
            }
            if (getSupportPtz() == 1) {
                mRealPlayPtzBtn.setEnabled(true);
            }

            mRealPlayFullPlayBtn.setBackgroundResource(R.drawable.play_full_stop_selector);
            mRealPlayFullCaptureBtn.setEnabled(true);
            mRealPlayFullRecordBtn.setEnabled(true);
            mRealPlayFullPtzBtn.setEnabled(true);
        }

//        setRealPlaySound();

        startUpdateTimer();
    }
    //显示类型
    private void showType() {
        if (Config.LOGGING && mEZPlayer != null) {
            Utils.showLog(this, "getType " + ",time：" + (mStopTime - mStartTime));
        }
    }
    // 设置loding成功
    private void setLoadingSuccess() {
        mRealPlayLoadingRl.setVisibility(View.INVISIBLE);
        mRealPlayTipTv.setVisibility(View.GONE);
        mRealPlayPlayLoading.setVisibility(View.GONE);
        mRealPlayPlayIv.setVisibility(View.GONE);
    }
    private void setBigScreenOperateBtnLayout() {
    }
    // 声音处理  Sound processing
    private void setRealPlaySound() {
        if (mEZPlayer != null) {
            if (mRtspUrl == null) {
                if (mLocalInfo.isSoundOpen()) {
                    mEZPlayer.openSound();
                } else {
                    mEZPlayer.closeSound();
                }
            } else {
                if (mRealPlaySquareInfo.mSoundType == 0) {
                    mEZPlayer.closeSound();
                } else {
                    mEZPlayer.openSound();
                }
            }
        }
    }
    private void initUI() {
        mPageAnimDrawable = null;
        mRealPlaySoundBtn.setVisibility(View.VISIBLE);
        if (mCameraInfo != null) {
            mPortraitTitleBar.setTitle(mCameraInfo.getCameraName());
            mLandscapeTitleBar.setTitle(mCameraInfo.getCameraName());
            setCameraInfoTiletRightBtn();
            if (mLocalInfo.isSoundOpen()) {
                mRealPlaySoundBtn.setBackgroundResource(R.drawable.selector_ez_vertical_preview_sound);
                mRealPlayFullSoundBtn.setBackgroundResource(R.drawable.selector_ez_play_full_soundon_btn);
            } else {
                mRealPlaySoundBtn.setBackgroundResource(R.drawable.selector_ez_vertical_preview_sound_off);
                mRealPlayFullSoundBtn.setBackgroundResource(R.drawable.selector_ez_play_full_soundoff_btn);
            }
            mRealPlayCaptureBtnLy.setVisibility(View.VISIBLE);
            mRealPlayFullCaptureBtn.setVisibility(View.VISIBLE);
            mRealPlayRecordContainerLy.setVisibility(View.VISIBLE);
            mRealPlayFullRecordContainer.setVisibility(View.VISIBLE);
            mRealPlayQualityBtn.setVisibility(View.VISIBLE);
            mRealPlayFullSoundBtn.setVisibility(View.VISIBLE);
            mRealPlayFullPtzAnimBtn.setVisibility(View.GONE);
            mRealPlayFullPtzPromptIv.setVisibility(View.GONE);
            updateUI();
        } else if (mRtspUrl != null) {
            if (!TextUtils.isEmpty(mRealPlaySquareInfo.mCameraName)) {
                mPortraitTitleBar.setTitle(mRealPlaySquareInfo.mCameraName);
                mLandscapeTitleBar.setTitle(mRealPlaySquareInfo.mCameraName);
            }
            mRealPlaySoundBtn.setVisibility(View.GONE);
            mRealPlayQualityBtn.setVisibility(View.GONE);
        }

        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            updateOperatorUI();
        }
    }

    private void updateOperatorUI() {
        if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
            fullScreen(false);
            updateOrientation();
            mPortraitTitleBar.setVisibility(View.VISIBLE);
            mLandscapeTitleBar.setVisibility(View.GONE);
            mRealPlayControlRl.setVisibility(View.VISIBLE);
            if (mRtspUrl == null) {
                mRealPlayPageLy.setBackgroundColor(getResources().getColor(R.color.common_bg));
                mRealPlayOperateBar.setVisibility(View.VISIBLE);
                mRealPlayFullOperateBar.setVisibility(View.GONE);
                mFullscreenFullButton.setVisibility(View.GONE);
                if (mIsRecording) {
                    mRealPlayRecordBtn.setVisibility(View.GONE);
                    mRealPlayRecordStartBtn.setVisibility(View.VISIBLE);
                } else {
                    mRealPlayRecordBtn.setVisibility(View.VISIBLE);
                    mRealPlayRecordStartBtn.setVisibility(View.GONE);
                }
            }
        } else {
            fullScreen(true);
            mPortraitTitleBar.setVisibility(View.GONE);
            // hide the
            mRealPlayControlRl.setVisibility(View.GONE);
            if (!mIsOnTalk && !mIsOnPtz) {
                mLandscapeTitleBar.setVisibility(View.VISIBLE);
            }
            if (mRtspUrl == null) {
                mRealPlayOperateBar.setVisibility(View.GONE);
                mRealPlayPageLy.setBackgroundColor(getResources().getColor(R.color.black_bg));
                mRealPlayFullOperateBar.setVisibility(View.GONE);
                if (!mIsOnTalk && !mIsOnPtz) {
                    mFullscreenFullButton.setVisibility(View.GONE);
                }
                if (mIsRecording) {
                    mRealPlayFullRecordBtn.setVisibility(View.GONE);
                    mRealPlayFullRecordStartBtn.setVisibility(View.VISIBLE);
                } else {
                    mRealPlayFullRecordBtn.setVisibility(View.VISIBLE);
                    mRealPlayFullRecordStartBtn.setVisibility(View.GONE);
                }
            }
        }

        //        mRealPlayControlRl.setVisibility(View.GONE);
        closeQualityPopupWindow();
        if (mStatus == RealPlayStatus.STATUS_START) {
            showControlRlAndFullOperateBar();
        }
    }
    // 全屏
    private void fullScreen(boolean enable) {
        if (enable) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
    // 设置相机信息Tilet右Btn
    private void setCameraInfoTiletRightBtn() {
        if (mTiletRightBtn != null && mDeviceInfo != null) {
            if (mDeviceInfo.getStatus() == 1) {
                mTiletRightBtn.setVisibility(View.VISIBLE);
            } else {
                mTiletRightBtn.setVisibility(View.GONE);
            }
        }
    }
    // 显示控制和全屏
    private void showControlRlAndFullOperateBar() {
        if (mRtspUrl != null || mOrientation == Configuration.ORIENTATION_PORTRAIT) {
            mRealPlayControlRl.setVisibility(View.VISIBLE);
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (!mIsOnTalk && !mIsOnPtz) {
                    mLandscapeTitleBar.setVisibility(View.VISIBLE);
                }
            } else {
                mLandscapeTitleBar.setVisibility(View.GONE);
            }
            mControlDisplaySec = 0;
        } else {
            if (!mIsOnTalk && !mIsOnPtz) {
                mRealPlayFullOperateBar.setVisibility(View.VISIBLE);
                //                mFullscreenFullButton.setVisibility(View.VISIBLE);
                mLandscapeTitleBar.setVisibility(View.VISIBLE);
            }
            mControlDisplaySec = 0;
        }
    }

    // 打开弹出框
    private void openPtzPopupWindow(View parent) {
        closePtzPopupWindow();
        mIsOnPtz = true;
        setPlayScaleUI(1, null, null);

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup layoutView = (ViewGroup) layoutInflater.inflate(R.layout.ez_realplay_ptz_wnd, null, true);

        mPtzControlLy = (LinearLayout) layoutView.findViewById(R.id.ptz_control_ly);
        ImageButton ptzCloseBtn = (ImageButton) layoutView.findViewById(R.id.ptz_close_btn);
        ptzCloseBtn.setOnClickListener(mOnPopWndClickListener);
        ImageButton ptzTopBtn = (ImageButton) layoutView.findViewById(R.id.ptz_top_btn);
        ptzTopBtn.setOnTouchListener(mOnTouchListener);
        ImageButton ptzBottomBtn = (ImageButton) layoutView.findViewById(R.id.ptz_bottom_btn);
        ptzBottomBtn.setOnTouchListener(mOnTouchListener);
        ImageButton ptzLeftBtn = (ImageButton) layoutView.findViewById(R.id.ptz_left_btn);
        ptzLeftBtn.setOnTouchListener(mOnTouchListener);
        ImageButton ptzRightBtn = (ImageButton) layoutView.findViewById(R.id.ptz_right_btn);
        ptzRightBtn.setOnTouchListener(mOnTouchListener);
        ImageButton ptzFlipBtn = (ImageButton) layoutView.findViewById(R.id.ptz_flip_btn);
        ptzFlipBtn.setOnClickListener(mOnPopWndClickListener);

        int height = mLocalInfo.getScreenHeight() - mPortraitTitleBar.getHeight() - mRealPlayPlayRl.getHeight()
                - (mRealPlayRect != null ? mRealPlayRect.top : mLocalInfo.getNavigationBarHeight());
        mPtzPopupWindow = new PopupWindow(layoutView, RelativeLayout.LayoutParams.MATCH_PARENT, height, true);
        mPtzPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPtzPopupWindow.setAnimationStyle(R.style.popwindowUpAnim);
        mPtzPopupWindow.setFocusable(true);
        mPtzPopupWindow.setOutsideTouchable(true);
        mPtzPopupWindow.showAsDropDown(parent);
        mPtzPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                Logger.d("KEYCODE_BACK DOWN");
                mPtzPopupWindow = null;
                mPtzControlLy = null;
                closePtzPopupWindow();
            }
        });
        mPtzPopupWindow.update();
    }
    // 点击事件
    private View.OnClickListener mOnPopWndClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.quality_hd_btn:
                    setQualityMode(EZConstants.EZVideoLevel.VIDEO_LEVEL_HD);
                    break;
                case R.id.quality_balanced_btn:
                    setQualityMode(EZConstants.EZVideoLevel.VIDEO_LEVEL_BALANCED);
                    break;
                case R.id.quality_flunet_btn:
                    setQualityMode(EZConstants.EZVideoLevel.VIDEO_LEVEL_FLUNET);
                    break;
                case R.id.ptz_close_btn:
                    closePtzPopupWindow();
                    break;
                case R.id.ptz_flip_btn:
                    //                    setPtzFlip();
                    break;
                case R.id.talkback_close_btn:
                    closeTalkPopupWindow(true, false);
                    break;
                default:
                    break;
            }
        }
    };
    // 设置画面质量
    private void setQualityMode(final EZConstants.EZVideoLevel mode) {
        // 检查网络是否可用 Check if the network is available
        if (!ConnectionDetector.isNetworkAvailable(this)) {
            // 提示没有连接网络 Prompt not to connect to the network
            Utils.showToast(this, "设置失败，请检查您的网络");
            return;
        }

        if (mEZPlayer != null) {
            mWaitDialog.setWaitText("正在设置画面质量…");
            mWaitDialog.show();

            Thread thr = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // need to modify by yudan at 08-11
                        MainApplication.getOpenSDK().setVideoLevel(mCameraInfo.getDeviceSerial(), mCameraInfo.getCameraNo(), mode.getVideoLevel());
                        mCurrentQulityMode = mode;
                        Message msg = Message.obtain();
                        msg.what = MSG_SET_VEDIOMODE_SUCCESS;
                        mHandler.sendMessage(msg);
                        Logger.i("setQualityMode success");
                    } catch (BaseException e) {
                        mCurrentQulityMode = EZConstants.EZVideoLevel.VIDEO_LEVEL_FLUNET;
                        e.printStackTrace();
                        Message msg = Message.obtain();
                        msg.what = MSG_SET_VEDIOMODE_FAIL;
                        mHandler.sendMessage(msg);
                        Logger.i( "setQualityMode fail");
                    }

                }
            }) {
            };
            thr.start();
        }
    }

    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent motionevent) {
            boolean ptz_result = false;
            int action = motionevent.getAction();
            final int speed = EZConstants.PTZ_SPEED_DEFAULT;
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    switch (view.getId()) {
                        case R.id.talkback_control_btn:
                            mTalkRingView.setVisibility(View.VISIBLE);
                            mEZPlayer.setVoiceTalkStatus(true);
                            break;
                        case R.id.ptz_top_btn:
                            mPtzControlLy.setBackgroundResource(R.drawable.ptz_up_sel);
                            setPtzDirectionIv(RealPlayStatus.PTZ_UP);
                            ptzOption(EZConstants.EZPTZCommand.EZPTZCommandUp, EZConstants.EZPTZAction.EZPTZActionSTART);
                            break;
                        case R.id.ptz_bottom_btn:
                            mPtzControlLy.setBackgroundResource(R.drawable.ptz_bottom_sel);
                            setPtzDirectionIv(RealPlayStatus.PTZ_DOWN);
                            ptzOption(EZConstants.EZPTZCommand.EZPTZCommandDown, EZConstants.EZPTZAction.EZPTZActionSTART);
                            break;
                        case R.id.ptz_left_btn:
                            mPtzControlLy.setBackgroundResource(R.drawable.ptz_left_sel);
                            setPtzDirectionIv(RealPlayStatus.PTZ_LEFT);
                            ptzOption(EZConstants.EZPTZCommand.EZPTZCommandLeft, EZConstants.EZPTZAction.EZPTZActionSTART);
                            break;
                        case R.id.ptz_right_btn:
                            mPtzControlLy.setBackgroundResource(R.drawable.ptz_right_sel);
                            setPtzDirectionIv(RealPlayStatus.PTZ_RIGHT);
                            ptzOption(EZConstants.EZPTZCommand.EZPTZCommandRight, EZConstants.EZPTZAction.EZPTZActionSTART);
                            break;
                        default:
                            break;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    switch (view.getId()) {
                        case R.id.talkback_control_btn:
                            mEZPlayer.setVoiceTalkStatus(false);
                            mTalkRingView.setVisibility(View.GONE);
                            break;
                        case R.id.ptz_top_btn:
                            mPtzControlLy.setBackgroundResource(R.drawable.ptz_bg);
                            ptzOption(EZConstants.EZPTZCommand.EZPTZCommandUp, EZConstants.EZPTZAction.EZPTZActionSTOP);
                            break;
                        case R.id.ptz_bottom_btn:
                            mPtzControlLy.setBackgroundResource(R.drawable.ptz_bg);
                            ptzOption(EZConstants.EZPTZCommand.EZPTZCommandDown, EZConstants.EZPTZAction.EZPTZActionSTOP);
                            break;
                        case R.id.ptz_left_btn:
                            mPtzControlLy.setBackgroundResource(R.drawable.ptz_bg);
                            ptzOption(EZConstants.EZPTZCommand.EZPTZCommandLeft, EZConstants.EZPTZAction.EZPTZActionSTOP);
                            break;
                        case R.id.ptz_right_btn:
                            mPtzControlLy.setBackgroundResource(R.drawable.ptz_bg);
                            ptzOption(EZConstants.EZPTZCommand.EZPTZCommandRight, EZConstants.EZPTZAction.EZPTZActionSTOP);
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    private void ptzOption(final EZConstants.EZPTZCommand command, final EZConstants.EZPTZAction action) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean ptz_result = false;
                try {
                    ptz_result = MainApplication.getOpenSDK().controlPTZ(mCameraInfo.getDeviceSerial(), mCameraInfo.getCameraNo(), command,
                            action, EZConstants.PTZ_SPEED_DEFAULT);
                } catch (BaseException e) {
                    e.printStackTrace();
                }
                Logger.i("controlPTZ ptzCtrl result: " + ptz_result);
            }
        }).start();
    }
    private void setPtzDirectionIv(int command) {
        setPtzDirectionIv(command, 0);
    }

    private void setPtzDirectionIv(int command, int errorCode) {
        if (command != -1 && errorCode == 0) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            switch (command) {
                case RealPlayStatus.PTZ_LEFT:
                    mRealPlayPtzDirectionIv.setBackgroundResource(R.drawable.left_twinkle);
                    params.addRule(RelativeLayout.CENTER_VERTICAL);
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    mRealPlayPtzDirectionIv.setLayoutParams(params);
                    break;
                case RealPlayStatus.PTZ_RIGHT:
                    mRealPlayPtzDirectionIv.setBackgroundResource(R.drawable.right_twinkle);
                    params.addRule(RelativeLayout.CENTER_VERTICAL);
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    mRealPlayPtzDirectionIv.setLayoutParams(params);
                    break;
                case RealPlayStatus.PTZ_UP:
                    mRealPlayPtzDirectionIv.setBackgroundResource(R.drawable.up_twinkle);
                    params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                    mRealPlayPtzDirectionIv.setLayoutParams(params);
                    break;
                case RealPlayStatus.PTZ_DOWN:
                    mRealPlayPtzDirectionIv.setBackgroundResource(R.drawable.down_twinkle);
                    params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    params.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.realplay_sv);
                    mRealPlayPtzDirectionIv.setLayoutParams(params);
                    break;
                default:
                    break;
            }
            mRealPlayPtzDirectionIv.setVisibility(View.VISIBLE);
            mHandler.removeMessages(MSG_HIDE_PTZ_DIRECTION);
            Message msg = new Message();
            msg.what = MSG_HIDE_PTZ_DIRECTION;
            msg.arg1 = 1;
            mHandler.sendMessageDelayed(msg, 500);
        } else if (errorCode != 0) {
            RelativeLayout.LayoutParams svParams = (RelativeLayout.LayoutParams) mRealPlaySv.getLayoutParams();
            RelativeLayout.LayoutParams params = null;
            switch (errorCode) {
                case ErrorCode.ERROR_CAS_PTZ_ROTATION_LEFT_LIMIT_FAILED:
                    params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, svParams.height);
                    mRealPlayPtzDirectionIv.setBackgroundResource(R.drawable.ptz_left_limit);
                    params.addRule(RelativeLayout.CENTER_VERTICAL);
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    mRealPlayPtzDirectionIv.setLayoutParams(params);
                    break;
                case ErrorCode.ERROR_CAS_PTZ_ROTATION_RIGHT_LIMIT_FAILED:
                    params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, svParams.height);
                    mRealPlayPtzDirectionIv.setBackgroundResource(R.drawable.ptz_right_limit);
                    params.addRule(RelativeLayout.CENTER_VERTICAL);
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    mRealPlayPtzDirectionIv.setLayoutParams(params);
                    break;
                case ErrorCode.ERROR_CAS_PTZ_ROTATION_UP_LIMIT_FAILED:
                    params = new RelativeLayout.LayoutParams(svParams.width, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    mRealPlayPtzDirectionIv.setBackgroundResource(R.drawable.ptz_top_limit);
                    params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                    mRealPlayPtzDirectionIv.setLayoutParams(params);
                    break;
                case ErrorCode.ERROR_CAS_PTZ_ROTATION_DOWN_LIMIT_FAILED:
                    params = new RelativeLayout.LayoutParams(svParams.width, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    mRealPlayPtzDirectionIv.setBackgroundResource(R.drawable.ptz_bottom_limit);
                    params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    params.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.realplay_sv);
                    mRealPlayPtzDirectionIv.setLayoutParams(params);
                    break;
                default:
                    break;
            }
            mRealPlayPtzDirectionIv.setVisibility(View.VISIBLE);
            mHandler.removeMessages(MSG_HIDE_PTZ_DIRECTION);
            Message msg = new Message();
            msg.what = MSG_HIDE_PTZ_DIRECTION;
            msg.arg1 = 1;
            mHandler.sendMessageDelayed(msg, 500);
        } else {
            mRealPlayPtzDirectionIv.setVisibility(View.GONE);
            mHandler.removeMessages(MSG_HIDE_PTZ_DIRECTION);
        }
    }

    // 播放失败
    private void handlePlayFail(Object obj) {
        int errorCode = 0;
        if (obj != null) {
            ErrorInfo errorInfo = (ErrorInfo) obj;
            errorCode = errorInfo.errorCode;
            Logger.d( "handlePlayFail:" + errorInfo.errorCode);
        }

        hidePageAnim();

        stopRealPlay();

        updateRealPlayFailUI(errorCode);
    }
    // 隐藏页面动画
    private void hidePageAnim() {
        mHandler.removeMessages(MSG_HIDE_PAGE_ANIM);
        if (mPageAnimDrawable != null) {
            if (mPageAnimDrawable.isRunning()) {
                mPageAnimDrawable.stop();
            }
            mPageAnimDrawable = null;
            mPageAnimIv.setBackgroundDrawable(null);
            mPageAnimIv.setVisibility(View.GONE);
        }
        if (mPageAnimIv != null) {
            mPageAnimIv.setBackgroundDrawable(null);
            mPageAnimIv.setVisibility(View.GONE);
        }
    }

}
