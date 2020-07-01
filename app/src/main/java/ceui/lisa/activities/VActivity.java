package ceui.lisa.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;


import ceui.lisa.R;
import ceui.lisa.base.BaseActivity;
import ceui.lisa.core.Container;
import ceui.lisa.core.IDWithList;
import ceui.lisa.core.TimeRecord;
import ceui.lisa.databinding.ActivityViewPagerBinding;
import ceui.lisa.fragments.FragmentSingleIllust;
import ceui.lisa.models.IllustsBean;
import ceui.lisa.utils.Params;
import ceui.lisa.utils.PixivOperate;

public class VActivity extends BaseActivity<ActivityViewPagerBinding> {

    private String pageUUID = "";
    private int index = 0;

    @Override
    protected void initBundle(Bundle bundle) {
        pageUUID = bundle.getString(Params.PAGE_UUID);
        index = bundle.getInt(Params.POSITION);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_view_pager;
    }

    @Override
    protected void initView() {
        IDWithList<IllustsBean> idWithList = Container.get().getPage(pageUUID);
        if (idWithList != null) {
            final int pageSize = idWithList.getList() == null ? 0 : idWithList.getList().size();
            baseBind.viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager(), 0) {
                @NonNull
                @Override
                public Fragment getItem(int position) {
//                    return FragmentIllust.newInstance(idWithList.getList().get(position));
                    return FragmentSingleIllust.newInstance(idWithList.getList().get(position));
                }

                @Override
                public int getCount() {
                    return pageSize;
                }
            });
            baseBind.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (Shaft.sSettings.isSaveViewHistory()) {
                        PixivOperate.insertIllustViewHistory(idWithList.getList().get(position));
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            if (index < pageSize) {
                baseBind.viewPager.setCurrentItem(index);
            }
        } else {
            finish();
        }

        TimeRecord.end();
        TimeRecord.result();
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean hideStatusBar() {
        return true;
    }
}