package tahayasin.digitalscreen;

import android.graphics.Bitmap;

public interface ISaveImages{
    public void setBitmap(String name, Bitmap btm);
    public Bitmap getBitmap(String name);
}