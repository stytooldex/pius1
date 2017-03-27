package nico.styTool.plus;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.widget.Toast;
import android.app.*;
import android.widget.*;
import android.widget.CompoundButton.*;
import android.view.*;
import android.text.*;

public class GankIoActivity extends Activity
{
    public String getSharePreing(Context context, String field)
    {
	SharedPreferences sp = context.getSharedPreferences("nico.styTool", Context.MODE_PRIVATE);
	String value = sp.getString(field, "");
	return value;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.th);

    }}
