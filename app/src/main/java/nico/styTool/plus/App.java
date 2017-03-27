package nico.styTool.plus;
import android.app.*;
import android.content.*;

public class App extends Application
{

    public static App a(Context r2_Context)
    {
	Context r0_Context = r2_Context.getApplicationContext();
	if ((r0_Context instanceof App))
	{
	    return ((App) (r0_Context));
	}
	else
	{
	    throw new IllegalArgumentException("context must be from BrowserApp");
	}
    }}
