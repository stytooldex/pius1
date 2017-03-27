package nico.styTool.plus;

import android.annotation.TargetApi;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.webkit.*;
import android.net.http.*;
import android.app.*;
import android.os.*;
import android.view.*;

import java.io.*;

import android.graphics.*;
import android.graphics.drawable.*;
import android.widget.*;

import java.util.*;
import java.lang.reflect.*;

import android.content.*;

public class MainActivity extends Activity {
    private String webviewi = "sdk_data_2";
    WebView webview;

    private ImageView dip_a;

    MaterialDialog mMaterialDialog;
    private int mColor;
    private ImageView dip_b;
    private boolean isChange = false;
    private ImageView dip_c;
    private boolean isUsername = false;
    public static final int REQUEST_CODE = 9;
    public static final int RESULT_CODE = 10;
    private final String LIST = "List";
    private final String URL = "Url";
    private ImageView dip_d;
    private Bookmark m_bookmark = null;
    private SearchHistorysDao dao;
    private ArrayList<SearchHistorysBean> historywordsList = new ArrayList<SearchHistorysBean>();
    private SearchHistoryAdapter mAdapter;
    private int count;
    private LinearLayout dip_m;
    PopupWindow popupWindow;
    private ListView lv_history_word;

    private ImageView btn_search;

    private EditText et_search_keyword;
    private TextView Az;

    private SlideBottomPanel sbv;

    private Num bnp;  //private Timer timer;

    private void setButtonListeners(LinearLayout layout) {
        Button camera = (Button) layout.findViewById(R.id.camera);
        Button gallery = (Button) layout.findViewById(R.id.gallery);
        Button savepicture = (Button) layout.findViewById(R.id.savepicture);
        Button cancel = (Button) layout.findViewById(R.id.cancel);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    //在此处添加你的按键处理 xxx
                    popupWindow.dismiss();
                }
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    //在此处添加你的按键处理 xxx
                    popupWindow.dismiss();
                }
            }
        });
        savepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    //在此处添加你的按键处理 xxx
                    popupWindow.dismiss();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
    }

    void bottomwindow(View view) {
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.window_popup, null);
        popupWindow = new PopupWindow(layout,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //添加弹出、弹入的动画
        popupWindow.setAnimationStyle(R.style.Popupwindow);
        int[] location = new int[4];
        view.getLocationOnScreen(location);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, -location[1]);
        //添加按键事件监听
        setButtonListeners(layout);
        //添加pop窗口关闭事件，主要是实现关闭时改变背景的透明度
        popupWindow.setOnDismissListener(new poponDismissListener());
        backgroundAlpha(1f);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);

        }

    }

    private void mk() {
        lv_history_word = (ListView) findViewById(R.id.lv_history_word);
        et_search_keyword = (EditText) findViewById(R.id.et_search_keyword);
        et_search_keyword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                } else {
                }
            }
        });

        btn_search = (ImageView) findViewById(R.id.btn_search);
        mAdapter = new SearchHistoryAdapter(historywordsList);
        count = mAdapter.getCount();
        if (count > 1) {
        } else {
        }
        lv_history_word.setAdapter(mAdapter);
        lv_history_word.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SearchHistorysBean bean = (SearchHistorysBean) mAdapter.getItem(position);

                et_search_keyword.setText(bean.historyword);

                mAdapter.notifyDataSetChanged();
                btn_search.performClick();
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {

            private PopupMenu popupMenu;

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                String searchWord = et_search_keyword.getText().toString().trim();
                if (TextUtils.isEmpty(searchWord)) {
                } else {
                    final String a = et_search_keyword.getText().toString().trim();
                    popupMenu = new PopupMenu(MainActivity.this, v);
                    popupMenu.getMenuInflater().inflate(R.menu.headmenu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.camera:
                                    webview.loadUrl("http://www.wangpansou.cn/s.php?wp=0&ty=gn&op=gn&q=" + a);
                                    break;
                                case R.id.gallery:
                                    webview.loadUrl("https://m.baidu.com/s?from=2001a&bd_page_type=1&word=" + a);
                                    break;
                                case R.id.cancel:
                                    webview.loadUrl(a);
                                    break;
                                case R.id.ds://谷歌
                                    webview.loadUrl("https://www.google.com.hk/search?q=" + a);
                                    break;

                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            }
        });

        ImageView A = (ImageView) findViewById(R.id.ahostTextView1);
        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, GankIoActivity.class);
                startActivity(intent);
            }
        });
        ImageView C = (ImageView) findViewById(R.id.ahostTextView3);
        C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yr();
            }
        });
    }

    class SearchHistoryAdapter extends BaseAdapter {
        private ArrayList<SearchHistorysBean> historywordsList;

        public SearchHistoryAdapter(ArrayList<SearchHistorysBean> historywordsList) {
            this.historywordsList = historywordsList;
        }

        @Override
        public int getCount() {

            return historywordsList == null ? 0 : historywordsList.size();
        }

        @Override
        public Object getItem(int position) {

            return historywordsList.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_search_history_word, null);
                holder.tv_word = (TextView) convertView.findViewById(R.id.tv_search_record);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tv_word.setText(historywordsList.get(position).toString());

            return convertView;
        }

    }

    class ViewHolder {
        TextView tv_word;
    }

    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            // webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            webview.goBack();

        } else {
            yr();
            sbv.hide();
        }
    }

    private void initEvent() {
        Az.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE) {
            Bundle bundle = data.getExtras();
            if (isUsername) {
            } else {
            }
            isChange = true;

        }
    }

    private void yr() {
        mMaterialDialog.setTitle("是否退出Frin")
                .setMessage(webview.getUrl())
                .setPositiveButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();

                    }
                }).setNegativeButton("退出", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
                finish();
            }
        })
                .setCanceledOnTouchOutside(true)
                .setOnDismissListener(
                        new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                            }
                        }).show();


    }

    public void showHistory() {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        WebBackForwardList backlist = webview.copyBackForwardList();

        for (int i = 0; i < backlist.getSize(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("Title", backlist.getItemAtIndex(i).getTitle());
            map.put("Url", backlist.getItemAtIndex(i).getUrl());

            list.add(map);
        }

        Intent intent = new Intent(this, ListShower.class);
        Bundle bundler = new Bundle();

        bundler.putSerializable(LIST, list);
        intent.putExtras(bundler);

        startActivityForResult(intent, 1);
    }

    public static String getSharePreString(Context context, String field) {
        SharedPreferences sp = context.getSharedPreferences("nico.styTool.plus_preferences", Context.MODE_PRIVATE);
        String value = sp.getString(field, "");
        return value;
    }

    private void a() {

        ImageView dipud = (ImageView) findViewById(R.id.ahostImageView1);
        dipud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.setTitle("书签")
                        .setMessage("Go")
                        .setPositiveButton("查看书签", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ArrayList<HashMap<String, String>> list = null;
                                m_bookmark.openMyBookmark(MainActivity.this);
                                list = m_bookmark.getList();
                                m_bookmark.closeMyBookmark();
                                Intent intent = new Intent(MainActivity.this, ListShower.class);
                                Bundle bundler = new Bundle();
                                bundler.putSerializable(LIST, list);
                                intent.putExtras(bundler);

                                startActivityForResult(intent, 1);
                                mMaterialDialog.dismiss();
                            }
                        }).setNegativeButton("添加书签", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Title = webview.getTitle();
                        String Url = webview.getUrl();
                        m_bookmark.openMyBookmark(MainActivity.this);
                        m_bookmark.insert(Title, Url);
                        m_bookmark.closeMyBookmark();
                        mMaterialDialog.dismiss();
                    }
                })
                        .setCanceledOnTouchOutside(true)
                        .show();

            }
        });
        dip_d = (ImageView) findViewById(R.id.appbarmiuiImageView1);
        dip_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview.goBack();
            }
        });

        dip_c = (ImageView) findViewById(R.id.appbarmiuiImageView2);
        dip_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview.goForward();
            }
        });
        dip_b = (ImageView) findViewById(R.id.appbarmiuiImageView3);
        dip_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview.reload();
            }
        });

        dip_a = (ImageView) findViewById(R.id.appbarmiuiImageView4);
        dip_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(Constant.android, Activity.MODE_PRIVATE);
                String dt = sharedPreferences.getString(Constant.id, "");
                mMaterialDialog.setTitle("长按链接复制")
                        .setMessage(dt)
                        .setPositiveButton("第三方", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPreferences sharedPreferences = getSharedPreferences(Constant.android, Activity.MODE_PRIVATE);
                                String htmly = sharedPreferences.getString(Constant.id, "");
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, htmly);
                                sendIntent.setType("text/plain");
                                startActivity(sendIntent);
                                mMaterialDialog.dismiss();
                            }
                        }).setNegativeButton("浏览器打开", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                        SharedPreferences sharedPreferencesb = getSharedPreferences(Constant.android, Activity.MODE_PRIVATE);
                        String htmlyy = sharedPreferencesb.getString(Constant.id, "");
                        Uri uri = Uri.parse(htmlyy);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                })
                        .setCanceledOnTouchOutside(true)
                        .setOnDismissListener(
                                new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        //Toast.makeText(MainActivity.this,"onDismiss",Toast.LENGTH_SHORT).show();
                                    }
                                }).show();
            }
        });
    }

    private void e() {
        dip_m = (LinearLayout) findViewById(R.id.mainLinearLayout1);
        dip_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sbv.displayPanel();
            }
        });
    }

    private String initContent(String content, boolean night, boolean flag) {
        try {
            InputStream inputStream = getResources().getAssets().open(
                    "discover.html");
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream), 16 * 1024);
            StringBuilder sBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }
            String modelHtml = sBuilder.toString();
            inputStream.close();
            reader.close();

            String contentNew = modelHtml.replace(
                    "<--@#$%discoverContent@#$%-->", content);
            if (night) {
                contentNew = contentNew.replace("<--@#$%colorfontsize2@#$%-->",
                        "color:#8f8f8f ;");
            } else {
                contentNew = contentNew.replace("<--@#$%colorfontsize2@#$%-->",
                        "color:#333333 ;");
            }
            if (flag) {
                contentNew = contentNew.replace(
                        "<--@#$%colorbackground@#$%-->", "background:#B4CDE6");
            } else {
                contentNew = contentNew.replace(
                        "<--@#$%colorbackground@#$%-->", "background:#F9BADA");
            }
            return contentNew;

        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Deprecated
    private void initWebView() {
        sbv = (SlideBottomPanel) findViewById(R.id.sbv);
        m_bookmark = new Bookmark();
        m_bookmark.initDB(this);
        Az = (TextView) findViewById(R.id.ahostTextView4);
        webview = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setDomStorageEnabled(true);
        bnp = (Num) findViewById(R.id.numberbar1);
        webview.loadUrl("http://hao.xiaomi.com");

    }

    @Override
    protected void onDestroy() {
        if (webview != null) {
            webview.destroy();
        }
        super.onDestroy();
    }

    @JavascriptInterface
    public void openImage(String img) {
        System.out.println(img);
        System.out.println(img);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.a_host);
        mMaterialDialog = new MaterialDialog(this);
        dao = new SearchHistorysDao(this);
        historywordsList = dao.findAll();

        a();
        e();
        mk();
        initWebView();
        initEvent();
        webview.setDownloadListener(new MyDownLoadListener());
        webview.setWebViewClient(new MyWebViewClient());
        webview.setWebChromeClient(new MyWebChromeClient());

    }


    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            bnp.setProgress(newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {

            super.onReceivedTitle(view, title);

            TextView iconthis = (TextView) findViewById(R.id.appbarmiuiTextView1);
            iconthis.setText(title);
        }
    }

    private class MyDownLoadListener implements DownloadListener {
        @Override
        public void onDownloadStart(String r, String userAgent, String contentDisposition, String mimetype, long contentLength) {

        }
    }
}
