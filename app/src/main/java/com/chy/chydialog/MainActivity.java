package com.chy.chydialog;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.chy.chydialog.databinding.ActivityMainBinding;
import com.chy.dialoglibrary.bean.ContentBean;
import com.chy.dialoglibrary.dialog.GlobalGridDialog;
import com.chy.dialoglibrary.dialog.GlobalItemDialog;
import com.chy.dialoglibrary.dialog.GlobalRegularDialog;
import com.chy.dialoglibrary.dialog.GlobalTextDialog;
import com.chy.dialoglibrary.dialog.LocalGridDialog;
import com.chy.dialoglibrary.dialog.LocalItemDialog;
import com.chy.dialoglibrary.dialog.LocalRegularDialog;
import com.chy.dialoglibrary.dialog.LocalTextDialog;
import com.chy.dialoglibrary.listener.CHYOnCancelClickListener;
import com.chy.dialoglibrary.listener.CHYOnGridClickListener;
import com.chy.dialoglibrary.listener.CHYOnItemClickListener;
import com.chy.dialoglibrary.listener.CHYOnRightClickListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;
    private String[] strs;
    private ArrayList<ContentBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        init();
    }

    private void init() {
        strs = new String[9];
        for (int i = 0; i < 9; i++) {
            strs[i] = "菜单" + i;
        }
        for (int i = 0; i < 20; i++) {
            list.add(new ContentBean("功能" + i, R.mipmap.ic_launcher_round));
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            //全局对话框
            case R.id.btn_text_dialog1:
                GlobalTextDialog globalTextDialog = GlobalTextDialog.getInstance(new CHYOnRightClickListener() {
                    @Override
                    public void onRightClick(View view) {
                        Toast.makeText(MainActivity.this, "点击了全局对话框的rightButton", Toast.LENGTH_SHORT).show();
                    }
                });
                //原始状态的设置内容
                startActivity(globalTextDialog.show(this, new ContentBean("我是内容区", "好的")));
                //改变文字颜色并设置内容
                //startActivity(globalTextDialog.show(this, new ContentBean("我是内容区", "好的"), new ColorBean(Color.RED, Color.BLUE)));
                //改变文字大小并设置内容
                //startActivity(globalTextDialog.show(this, new ContentBean("我是内容区", "好的"), new SizeBean(60f, 90f)));
                //同时改变文字大小和颜色并设置内容
                //startActivity(globalTextDialog.show(this, new ContentBean("我是内容区", "好的"), new ColorBean(Color.RED, Color.BLUE), new SizeBean(60f, 90f)));
                break;
            case R.id.btn_right_dialog1:
                startGlobalDialog(GlobalRegularDialog.DIALOG_TYPE.RIGHT_DIALOG);
                break;
            case R.id.btn_error_dialog1:
                startGlobalDialog(GlobalRegularDialog.DIALOG_TYPE.ERROR_DIALOG);
                break;
            case R.id.btn_warning_dialog1:
                startGlobalDialog(GlobalRegularDialog.DIALOG_TYPE.WARNING_DIALOG);
                break;
            case R.id.btn_information_dialog1:
                startGlobalDialog(GlobalRegularDialog.DIALOG_TYPE.INFORMATION_DIALOG);
                break;
            case R.id.btn_item_dialog1:
                GlobalItemDialog globalItemDialog = GlobalItemDialog.getInstance(null, new CHYOnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                    }
                });
                //原始状态的设置内容
                startActivity(globalItemDialog.show(this, strs));
                //改变文字颜色并设置内容
                //startActivity(globalItemDialog.show(this, strs,new ColorBean()));
                //改变文字大小并设置内容
                //startActivity(globalItemDialog.show(this, strs,new SizeBean(30f,0f)));
                //同时改变文字大小和颜色并设置内容
                //startActivity(globalItemDialog.show(this, strs,new ColorBean(),new SizeBean(30f,30f)));
                break;
            case R.id.btn_grid_dialog1:
                GlobalGridDialog globalGridDialog = GlobalGridDialog.getInstance(new CHYOnGridClickListener() {
                    @Override
                    public void onGridClick(View view, String title) {
                        Toast.makeText(MainActivity.this, title, Toast.LENGTH_SHORT).show();
                    }
                });
                //原始状态的设置内容
                startActivity(globalGridDialog.show(this, list));
                //改变文字颜色并设置内容
                //startActivity(globalGridDialog.show(this, list,Color.GREEN));
                //改变文字大小并设置内容
                //startActivity(globalGridDialog.show(this, list,30f));
                //同时改变文字大小和颜色并设置内容
                //startActivity(globalGridDialog.show(this, list,Color.GREEN,30f));
                break;
            case R.id.btn_delayed_start1:
                moveTaskToBack(true);
                Toast.makeText(MainActivity.this, "程序已进入后台状态，4s后将启动一个全局对话框", Toast.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GlobalTextDialog globalTextDialog = GlobalTextDialog.getInstance(new CHYOnRightClickListener() {
                            @Override
                            public void onRightClick(View view) {
                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);

                            }
                        });
                        startActivity(globalTextDialog.show(MainActivity.this, new ContentBean("点击\"启动\"按钮即可启动App", "启动")));
                    }
                }, 4000);
                break;
            //局部对话框
            case R.id.btn_text_dialog2:
                final LocalTextDialog localTextDialog = new LocalTextDialog(this);
                localTextDialog.createDialog(new ContentBean("我是内容", "好的"), new CHYOnRightClickListener() {
                    @Override
                    public void onRightClick(View view) {
                        Toast.makeText(MainActivity.this, "点击了局部对话框的rightButton", Toast.LENGTH_SHORT).show();
                        localTextDialog.dismiss();
                    }
                });
                //设置字体颜色
                //localTextDialog.setTextColor(new ColorBean(Color.RED, Color.BLUE, Color.GREEN, Color.RED));
                //设置字体大小
                //localTextDialog.setTextSize(new SizeBean(80f, 50f, 60f, 60f));
                //设置背景
                localTextDialog.setBackgroundResource(R.mipmap.ic_dialog, false);
                break;
            case R.id.btn_right_dialog2:
                startLocalDialog(LocalRegularDialog.DIALOG_TYPE.RIGHT_DIALOG);
                break;
            case R.id.btn_error_dialog2:
                startLocalDialog(LocalRegularDialog.DIALOG_TYPE.ERROR_DIALOG);
                break;
            case R.id.btn_warning_dialog2:
                startLocalDialog(LocalRegularDialog.DIALOG_TYPE.WARNING_DIALOG);
                break;
            case R.id.btn_information_dialog2:
                startLocalDialog(LocalRegularDialog.DIALOG_TYPE.INFORMATION_DIALOG);
                break;
            case R.id.btn_item_dialog2:
                LocalItemDialog localItemDialog = new LocalItemDialog(this);
                localItemDialog.createDialog(strs, "取消", new CHYOnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                    }
                });
                //localItemDialog.setCancelButtonColor(Color.RED);
                //localItemDialog.setCancelButtonSize(50f);
                //设置item字体颜色
                //localItemDialog.setContentColor(Color.GREEN);
                //设置item字体大小
                //localItemDialog.setContentSize(30f);
                break;
            case R.id.btn_grid_dialog2:
                //启动方式1
                LocalGridDialog fragment = LocalGridDialog.getInstance(list, new CHYOnGridClickListener() {
                    @Override
                    public void onGridClick(View view, String title) {
                        Toast.makeText(MainActivity.this, title, Toast.LENGTH_SHORT).show();
                    }
                });

                //启动方式2
                //Bundle bundle = new Bundle();
                //bundle.putFloat("size", 30f);
                //bundle.putInt("color", Color.GREEN);
                //bundle.putSerializable("content", list);
                //LocalGridDialog fragment = LocalGridDialog.getInstance(bundle, new CHYOnGridClickListener() {
                //    @Override
                //    public void onGridClick(View view, String title) {
                //        Toast.makeText(MainActivity.this, title, Toast.LENGTH_SHORT).show();
                //   }
                //});

                //启动方式3
                //LocalGridDialog fragment = LocalGridDialog.getInstance(Color.BLUE, 30f, list, new CHYOnGridClickListener() {
                //    @Override
                //    public void onGridClick(View view, String title) {
                //        Toast.makeText(MainActivity.this, title, Toast.LENGTH_SHORT).show();
                //    }
                //});
                fragment.show(getSupportFragmentManager(), null);
                break;
            case R.id.btn_delayed_start2:
                moveTaskToBack(true);
                Toast.makeText(MainActivity.this, "程序已进入后台状态，4s后将启动一个局部对话框", Toast.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "对话框已启动，请到程序内部查看", Toast.LENGTH_LONG).show();
                        final LocalTextDialog localTextDialog = new LocalTextDialog(MainActivity.this);
                        localTextDialog.createDialog(new ContentBean("我是内容", "好的"), new CHYOnRightClickListener() {
                            @Override
                            public void onRightClick(View view) {
                                Toast.makeText(MainActivity.this, "点击了局部对话框的rightButton", Toast.LENGTH_SHORT).show();
                                localTextDialog.dismiss();
                            }
                        });
                    }
                }, 4000);
                break;
        }
    }


    /**
     * 启动全局对话框
     *
     * @param dialogType 对话框类型
     */
    private void startGlobalDialog(final GlobalRegularDialog.DIALOG_TYPE dialogType) {
        GlobalRegularDialog globalRegularDialog = GlobalRegularDialog.getInstance(dialogType, new CHYOnRightClickListener() {
            @Override
            public void onRightClick(View view) {
                Toast.makeText(MainActivity.this, "点击了全局对话框的" + dialogType + "的rightButton", Toast.LENGTH_SHORT).show();
            }
        }, new CHYOnCancelClickListener() {
            @Override
            public void onCancelClick(View view) {
                Toast.makeText(MainActivity.this, "点击了全局对话框的" + dialogType + "的cancelButton", Toast.LENGTH_SHORT).show();
            }
        });
        //原始状态的设置内容
        //startActivity(globalRegularDialog.show(this, new ContentBean("我是内容区", "好的")));
        startActivity(globalRegularDialog.show(this, new ContentBean("我是标题", "我是内容区", "取消", "好的")));
        //改变文字颜色并设置内容
        //startActivity(globalRegularDialog.show(this, new ContentBean("我是标题", "我是内容区", "取消", "好的"), new ColorBean(Color.RED, Color.BLUE, Color.GREEN, Color.GRAY)));
        //改变文字大小并设置内容
        //startActivity(globalRegularDialog.show(this, new ContentBean("我是标题", "我是内容区", "取消", "好的"), new SizeBean(80f ,50f, 60f ,60f)));
        //同时改变文字大小和颜色并设置内容
        //startActivity(globalRegularDialog.show(this, new ContentBean("我是标题", "我是内容区", "取消", "好的"), new ColorBean(Color.RED, Color.BLUE, Color.GREEN, Color.GRAY), new SizeBean(80f ,50f, 60f ,60f)));

    }

    /**
     * 启动局部对话框
     *
     * @param type 对话框类型
     */
    private void startLocalDialog(final LocalRegularDialog.DIALOG_TYPE type) {
        final LocalRegularDialog localRegularDialog = new LocalRegularDialog(this);
        //ContentBean contentBean = new ContentBean( "我是内容",  "确定");
        ContentBean contentBean = new ContentBean("标题", "我是内容", "取消", "确定");
        localRegularDialog.createDialog(type, contentBean, new CHYOnCancelClickListener() {
            @Override
            public void onCancelClick(View view) {
                Toast.makeText(MainActivity.this, "点击了局部对话框的" + type + "的cancelButton", Toast.LENGTH_SHORT).show();
            }
        }, new CHYOnRightClickListener() {
            @Override
            public void onRightClick(View view) {
                Toast.makeText(MainActivity.this, "点击了局部对话框的" + type + "的rightButton", Toast.LENGTH_SHORT).show();
                localRegularDialog.dismiss();
            }
        });
        //设置字体颜色
        //localRegularDialog.setTextColor(new ColorBean(Color.RED, Color.BLUE, Color.GREEN, Color.RED));
        //设置字体大小
        //localRegularDialog.setTextSize(new SizeBean(80f, 50f, 60f, 60f));
        //设置背景
        //localRegularDialog.setBackgroundResource(R.mipmap.ic_dialog, false);
    }
}