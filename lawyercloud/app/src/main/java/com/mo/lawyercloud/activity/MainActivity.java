package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.fragment.AdvisoryFragment;
import com.mo.lawyercloud.fragment.HomeFragment;
import com.mo.lawyercloud.fragment.InformationFragment;
import com.mo.lawyercloud.fragment.MineUserFragment;
import com.mo.lawyercloud.fragment.NoviceFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_NOVICE = 1;
    private static final int FRAGMENT_MINE = 2;
    private static final int FRAGMENT_INFORMATION = 3;
    private static final int FRAGMENT_ADVISORY = 4;
    private static final String POSITION = "position";
    @BindView(R.id.radiogroup)
    RadioGroup mRadioGroup;

    private int position;
    private long exitTime = 0;
    private long firstClickTime = 0;
    private HomeFragment mHomeFragment;
    private NoviceFragment mNoviceFragment;
    private AdvisoryFragment mAdvisoryFragment;
    private InformationFragment mInformationFragment;
    private MineUserFragment mMineUserFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mHomeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag
                    (HomeFragment.class.getName());

            mNoviceFragment = (NoviceFragment) getSupportFragmentManager()
                    .findFragmentByTag(NoviceFragment.class.getName());

            mMineUserFragment = (MineUserFragment) getSupportFragmentManager()
                    .findFragmentByTag(MineUserFragment.class.getName());

            mInformationFragment = (InformationFragment) getSupportFragmentManager()
                    .findFragmentByTag(InformationFragment.class.getName());

            mAdvisoryFragment = (AdvisoryFragment) getSupportFragmentManager()
                    .findFragmentByTag(AdvisoryFragment.class.getName());

            // 恢复 recreate 前的位置
            showFragment(savedInstanceState.getInt(POSITION));
            mRadioGroup.check(position);
        } else {
            showFragment(FRAGMENT_HOME);

        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.controller_tab1:
                        showFragment(FRAGMENT_HOME);
                        break;
                    case R.id.controller_tab2:
                        showFragment(FRAGMENT_NOVICE);
                        break;
                    case R.id.controller_tab3:
                        showFragment(FRAGMENT_MINE);
                        break;
                    case R.id.controller_tab4:
                        showFragment(FRAGMENT_INFORMATION);
                        break;
                    case R.id.controller_tab5:
                        showFragment(FRAGMENT_ADVISORY);
                        break;

                }
            }
        });
    }

    private void showFragment(int index) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideFragment(ft);
        position = index;
        switch (index) {
            case FRAGMENT_HOME:
                /**
                 * 如果Fragment为空，就新建一个实例
                 * 如果不为空，就将它从栈中显示出来
                 */
                if (mHomeFragment == null) {
                    mHomeFragment = mHomeFragment.getInstance();
                    ft.add(R.id.container, mHomeFragment, HomeFragment.class.getName());
                } else {
                    ft.show(mHomeFragment);
                }
                break;

            case FRAGMENT_NOVICE:
                if (mNoviceFragment == null) {
                    mNoviceFragment = NoviceFragment.getInstance();
                    ft.add(R.id.container, mNoviceFragment, NoviceFragment.class.getName());
                } else {
                    ft.show(mNoviceFragment);
                }
                break;

            case FRAGMENT_MINE:
                if (mMineUserFragment == null) {
                    mMineUserFragment = MineUserFragment.getInstance();
                    ft.add(R.id.container, mMineUserFragment, MineUserFragment.class.getName());
                } else {
                    ft.show(mMineUserFragment);
                }
                break;

            case FRAGMENT_INFORMATION:
                if (mInformationFragment == null) {
                    mInformationFragment = InformationFragment.getInstance();
                    ft.add(R.id.container, mInformationFragment, InformationFragment.class.getName());
                } else {
                    ft.show(mInformationFragment);
                }
                break;
            case FRAGMENT_ADVISORY:
                if (mAdvisoryFragment == null) {
                    mAdvisoryFragment = AdvisoryFragment.getInstance();
                    ft.add(R.id.container, mAdvisoryFragment, AdvisoryFragment.class.getName());
                } else {
                    ft.show(mAdvisoryFragment);
                }
                break;
        }

        ft.commit();
    }

    private void hideFragment(FragmentTransaction ft) {
        // 如果不为空，就先隐藏起来
        if (mHomeFragment != null) {
            ft.hide(mHomeFragment);
        }
        if (mNoviceFragment != null) {
            ft.hide(mNoviceFragment);
        }
        if (mMineUserFragment != null) {
            ft.hide(mMineUserFragment);
        }
        if (mInformationFragment != null) {
            ft.hide(mInformationFragment);
        }
        if (mAdvisoryFragment != null) {
            ft.hide(mAdvisoryFragment);
        }
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - exitTime) < 2000) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, R.string.double_click_exit, Toast.LENGTH_SHORT).show();
            exitTime = currentTime;
        }
    }

}