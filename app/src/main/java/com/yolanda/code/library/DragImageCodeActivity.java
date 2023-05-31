package com.yolanda.code.library;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yolanda.code.library.view.DiyStyleTextView;
import com.yolanda.code.library.widget.DragImageView;

/**
 * @author Created by Yolanda on 2018/10/31.
 * @description 滑动拼图验证码
 */
public class DragImageCodeActivity extends Activity {
    DragImageView dragView;
    DiyStyleTextView tvTest;

    private Handler handler;
    private Bitmap pic1;
    private Bitmap pic2;
    private Bitmap pic3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_image_code);
        initView();
        initEvent();
    }


    public void initView() {
        dragView = (DragImageView) findViewById(R.id.dragView);
        tvTest = (DiyStyleTextView) findViewById(R.id.tv_diy_test);
        getPic();

        // 设置需要改变颜色的部分
//        tvTest.setColorRegex("Yolanda", 0xfff75151);
//        tvTest.setText("android开发者论坛: Yolanda");
//        tvTest.setDiyTextImage("android开发者论坛: Yolanda 8833", "[\\d]+",
//                BitmapFactory.decodeResource(getResources(), R.drawable.drag_btn));
        tvTest.setDiyTextColor("Yolanda -> 1994", "[\\d]+", 0xfff75151);
    }

    protected void initEvent() {
        dragView.setDragListenner(new DragImageView.DragListenner() {
            @Override
            public void onDrag(float position) {
                Toast.makeText(DragImageCodeActivity.this, position + "", Toast.LENGTH_SHORT).show();
                if (Math.abs(position - 0.637) > 0.012)
                    dragView.fail();
                else {
                    dragView.ok();
                    runUIDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dragView.reset();
                        }
                    }, 2000);
                }
            }

        });
    }

    public void runUIDelayed(Runnable run, int de) {
        if (handler == null)
            handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(run, de);
    }

    public void getPic() {
        final Context context = this;
        Glide.with(this)
                .load("http://192.168.10.203:9090/shadow/temp/big_0_29_f7e6c85504ce6e82.png")
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Log.i("app", "111" + resource.toString());
                        pic1 = resource;
                        Glide.with(context)
                                .load("http://192.168.10.203:9090/shadow/temp/small_0_29_f7e6c85504ce6e82.png")
                                .asBitmap()
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        Log.i("app", "222" + resource.toString());
                                        pic2 = resource;

                                        Glide.with(context)
                                                .load("http://192.168.10.203:9090/shadow/0.png")
                                                .asBitmap()
                                                .into(new SimpleTarget<Bitmap>() {
                                                    @Override
                                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                                        Log.i("app", "333" + resource.toString());
                                                        pic3 = resource;
                                                        dragView.setUp(pic1,
                                                                pic2,
                                                                pic3,
                                                                0.377f);
                                                    }
                                                });
                                    }
                                });

                    }
                });


//        dragView.setUp(BitmapFactory.decodeResource(getResources(), R.drawable.drag_cover),
//                BitmapFactory.decodeResource(getResources(), R.drawable.drag_block),
//                BitmapFactory.decodeResource(getResources(), R.drawable.drag_cover_c),
//                0.377f);


    }

}
