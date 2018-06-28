package me.iwf.photopicker.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import me.iwf.photopicker.R;
import me.iwf.photopicker.utils.AndroidLifecycleUtils;
import me.iwf.photopicker.utils.ImgUtil;

/**
 * Created by donglua on 15/6/21.
 */
public class PhotoPagerAdapter extends PagerAdapter {

  private List<String> paths = new ArrayList<>();
  private RequestManager mGlide;

  public PhotoPagerAdapter(RequestManager glide, List<String> paths) {
    this.paths = paths;
    this.mGlide = glide;

  }

  @Override public Object instantiateItem(ViewGroup container, int position) {
    final Context context = container.getContext();
    View itemView = LayoutInflater.from(context)
        .inflate(R.layout.__picker_picker_item_pager, container, false);

    final ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_pager);

    final String path = paths.get(position);
    final Uri uri;
    if (path.startsWith("http")) {
      uri = Uri.parse(path);
    } else {
      uri = Uri.fromFile(new File(path));
    }

    boolean canLoadImage = AndroidLifecycleUtils.canLoadImage(context);

    if (canLoadImage) {
      final RequestOptions options = new RequestOptions();
      options.dontAnimate()
          .dontTransform()
          .override(800, 800)
          .placeholder(R.drawable.__picker_ic_photo_black_48dp)
          .error(R.drawable.__picker_ic_broken_image_black_48dp);
      mGlide.setDefaultRequestOptions(options).load(uri)
              .thumbnail(0.1f)
              .into(imageView);
    }

    imageView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (context instanceof Activity) {
          if (!((Activity) context).isFinishing()) {
            ((Activity) context).onBackPressed();
          }
        }
      }
    });

    imageView.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        final BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        new AlertDialog.Builder(context).setMessage("是否保存图片").setPositiveButton("确定", new
                DialogInterface
                .OnClickListener() {

          @Override
          public void onClick(DialogInterface dialog, int which) {
            boolean isSaveSuccess = ImgUtil.saveImageToGallery(context,drawable.getBitmap());
            if (isSaveSuccess) {
              Toast.makeText(context, "保存图片成功", Toast.LENGTH_SHORT).show();
            } else {
              Toast.makeText(context, "保存图片失败，请稍后重试", Toast.LENGTH_SHORT).show();
            }
          }
        }).setNegativeButton("取消",null).show();

        return false;
      }
    });

    container.addView(itemView);

    return itemView;
  }


  @Override public int getCount() {
    return paths.size();
  }


  @Override public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }


  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((View) object);
    mGlide.clear((View) object);
  }

  @Override
  public int getItemPosition (Object object) { return POSITION_NONE; }

}
